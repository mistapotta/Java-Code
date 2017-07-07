<?php
$app->get(
    '/preorders',
    function () use ($app) {
        //echo 'This should list all customers';
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('p.preorder_id, p.vip_card_number, p.requested_date, p.created_date, p.location_id, p.purchase_id');
        $query->from('#__preorders p');

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $preorders = $app->db->loadObjectList('preorder_id');

        if (!empty($preorders))
        {
            // Retrieve Items associated with each purchase:
            $preorderIds = array_keys($preorders);

            // Create New Query:
            $query = $app->db->getQuery(true);

            // Build Query:
            $query->select('*');
            $query->from('#__preorder_items as pi');
            $query->innerJoin('#__items as i ON pi.item_Id = i.item_id');
            $query->where('pi.preorder_id IN (' . implode(',', $preorderIds) . ')');

            // Set Query:
            $app->db->setQuery($query);

            // Execute Query and Load Data:
            $preorderItems = $app->db->loadObjectList();

            if (!empty($preorderItems))
            {
                foreach($preorderItems as $preorderItem)
                {
                    $preorder_id = $preorderItem->preorder_id;
                    $item_id = $preorderItem->item_id;

                    if (isset($preorders[$preorder_id]) && !isset($preorders[$preorder_id]->items))
                    {
                        $preorders[$preorder_id]->items = array();
                    }

                    $preorders[$preorder_id]->items[$item_id] = $preorderItem;
                }
            }

            $app->response->setBody(json_encode($preorders, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Purchase Data available')));
        }
    }
);

$app->post(
    '/preorders',
    function () use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (!empty($json))
        {
            if (isset($json->items))
            {
                if (isset($json->vip_card_number, $json->requested_date, $json->location_id))
                {
                    // Process data (if needed):

                    // Create New Query:
                    $query = $app->db->getQuery(true);

                    // Build Query:
                    $query->insert('#__preorders');
                    $query->set('vip_card_number = ' . $app->db->quote($json->vip_card_number));
                    $query->set('requested_date = ' . $app->db->quote($json->requested_date));
                    $query->set('created_date = NOW()');
                    $query->set('location_id = ' . $app->db->quote($json->location_id));

                    // Set Query:
                    $app->db->setQuery($query);

                    // Execute Query and Load Data:
                    try {
                        $result = $app->db->execute();

                        $preorder_id = $app->db->insertid();

                        if ($result)
                        {
                            foreach($json->items as $item)
                            {
                                if (isset($item->item_id) && isset($item->item_quantity))
                                {
                                    // Create New Query:
                                    $query = $app->db->getQuery(true);

                                    // Build Query:
                                    $query->insert('#__preorder_items');
                                    $query->set('preorder_id = ' . $app->db->quote($preorder_id));
                                    $query->set('item_id = ' . $app->db->quote($item->item_id));
                                    $query->set('item_quantity = ' . $app->db->quote($item->item_quantity));

                                    // Set Query:
                                    $app->db->setQuery($query);

                                    try {
                                        $result = $app->db->execute();
                                    } catch (Exception $e) {
                                        $app->halt(403, json_encode(array('message' => 'Failed to add one of the items for Pre-Order #' . $preorder_id)));
                                    }
                                }
                                else
                                {
                                    $app->halt(403, json_encode(array('message' => 'One of the items was missing a required value for Pre-Order #' . $preorder_id . ' (item_id, item_quantity)')));
                                }
                            }

                            $app->halt(201, json_encode(array('message' => 'Successfully created new Pre-Order Record (#' . $preorder_id . ') with Associated Items', 'preorder_id' => $preorder_id)));
                        }
                        else
                        {
                            $app->halt(403, json_encode(array('message' => 'Failed to create new Pre-Order')));
                        }
                    } catch(RuntimeException $e) {
                        if ($e->getCode() == 1062)
                        {
                            $app->halt(403, json_encode(array('message' => 'Failed to Create new Pre-Order for Customer #' . $json->vip_card_number)));
                        }
                        else
                        {
                            $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
                        }
                    }
                }
                else
                {
                    $app->halt(400, json_encode(array('message' => 'Missing one or more required fields in JSON request (vip_card_number, requested_date, location_id)')));
                }
            }
            else
            {
                $app->halt(403, json_encode(array('message' => 'A new Pre-Order must contain Items')));
            }
        }
    }
);

$app->get(
    '/preorders/:preorder_id',
    function ($preorder_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('*');
        $query->from('#__preorders p');
        $query->where('p.preorder_id = ' . $app->db->quote($preorder_id));

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $preorder = $app->db->loadObject();

        if (!empty($preorder))
        {
            // Create New Query:
            $query = $app->db->getQuery(true);

            // Build Query:
            $query->select('*');
            $query->from('#__preorder_items as pi');
            $query->innerJoin('#__items as i ON pi.item_Id = i.item_id');
            $query->where('pi.preorder_id = ' . $preorder_id);

            // Set Query:
            $app->db->setQuery($query);

            // Execute Query and Load Data:
            $preorderItems = $app->db->loadObjectList();

            if (!empty($preorderItems))
            {
                foreach($preorderItems as $preorderItem)
                {
                    $item_id = $preorderItem->item_id;

                    if (!isset($preorder->items))
                    {
                        $preorder->items = array();
                    }

                    $preorder->items[$item_id] = $preorderItem;
                }
            }

            $app->response->setBody(json_encode($preorder, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(404, json_encode(array('message' => 'Unknown Pre-Order ID (#' . $preorder_id . ')')));
        }
    }
);

$app->put(
    '/preorders/:preorder_id',
    function ($preorder_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (!empty($json))
        {
            if (isset($json->vip_card_number, $json->requested_date, $json->location_id))
            {
                // Process data:

                // Create New Query:
                $query = $app->db->getQuery(true);

                // Build Query:
                $query->update('#__preorders');
                $query->set('vip_card_number = ' . $app->db->quote($json->vip_card_number));
                $query->set('requested_date = ' . $app->db->quote($json->requested_date));
                $query->set('location_id = ' . $app->db->quote($json->location_id));

                if (isset($json->purchase_id))
                {
                    $query->set('purchase_id = ' . $app->db->quote($json->purchase_id));
                }

                $query->where('preorder_id = ' . $app->db->quote($preorder_id));

                // Set Query:
                $app->db->setQuery($query);

                // Execute Query and Load Data:
                try {
                    $result = $app->db->execute();

                    if ($result)
                    {
                        if (isset($json->items) && !empty($json->items))
                        {
                            // Create New Query:
                            $query = $app->db->getQuery(true);

                            // Build Query:
                            $query->delete('#__preorder_items');
                            $query->where('preorder_id = ' . $app->db->quote($preorder_id));

                            // Set Query:
                            $app->db->setQuery($query);

                            try {
                                $result = $app->db->execute();
                            } catch (Exception $e) {
                                $app->halt(403, json_encode(array('message' => 'Failed to delete existing Pre-Order Items for Pre-Order #' . $preorder_id)));
                            }

                            foreach($json->items as $item)
                            {
                                if (isset($item->item_id) && isset($item->item_quantity))
                                {
                                    // Create New Query:
                                    $query = $app->db->getQuery(true);

                                    // Build Query:
                                    $query->insert('#__preorder_items');
                                    $query->set('preorder_id = ' . $app->db->quote($preorder_id));
                                    $query->set('item_id = ' . $app->db->quote($item->item_id));
                                    $query->set('item_quantity = ' . $app->db->quote($item->item_quantity));

                                    // Set Query:
                                    $app->db->setQuery($query);

                                    try {
                                        $result = $app->db->execute();
                                    } catch (Exception $e) {
                                        $app->halt(403, json_encode(array('message' => 'Failed to add one of the items for Pre-Order #' . $preorder_id)));
                                    }
                                }
                                else
                                {
                                    $app->halt(403, json_encode(array('message' => 'One of the items was missing a required value for Pre-Order #' . $preorder_id . ' (item_id, item_quantity)')));
                                }
                            }
                        }
                        $app->halt(200, json_encode(array('message' => 'Successfully updated Pre-Order #' . $preorder_id)));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'Failed to update Pre-Order #' . $preorder_id)));
                    }
                } catch(RuntimeException $e) {
                    $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
                }
            }
            else
            {
                $app->halt(400, json_encode(array('message' => 'Missing one or more required fields in JSON request (vip_card_number, requested_date, location_id)')));
            }
        }
    }
);

$app->delete(
    '/preorders/:preorder_id',
    function ($preorder_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (empty($json))
        {
            // Create New Query:
            $query = $app->db->getQuery(true);

            // Build Query:
            $query->delete('#__preorders');
            $query->where('preorder_id = ' . $app->db->quote($preorder_id));

            // Set Query:
            $app->db->setQuery($query);

            // Execute Query and Load Data:
            try {
                $result = $app->db->execute();

                $affectedRows = $app->db->getAffectedRows();

                if ($affectedRows == 1)
                {
                    // Create New Query:
                    $query = $app->db->getQuery(true);

                    // Build Query:
                    $query->delete('#__preorder_items');
                    $query->where('preorder_id = ' . $app->db->quote($preorder_id));

                    // Set Query:
                    $app->db->setQuery($query);

                    try {
                        $result = $app->db->execute();
                    } catch (Exception $e) {
                        $app->halt(403, json_encode(array('message' => 'Failed to delete Pre-Order Items for Pre-Order #' . $preorder_id)));
                    }

                    $app->halt(200, json_encode(array('message' => 'Successfully deleted Pre-Order #' . $preorder_id)));
                }
                else
                {
                    $app->halt(404, json_encode(array('message' => 'Pre-Order #' . $preorder_id . ' does not exist.')));
                }
            } catch(RuntimeException $e) {
                $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
            }
        }
    }
);

$app->get(
    '/preorders/valid-preorder-date/:requested_date',
    function ($requested_date) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('IF((' . $app->db->quote($requested_date) . '  < DATE_ADD(CURDATE(), INTERVAL 1 MONTH)) AND ' . $app->db->quote($requested_date) . ' > CURDATE(), 1, 0) is_valid_date');
        $query->from('dual');

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        try {
            $canPreorder = $app->db->loadObject();

            if (!empty($canPreorder))
            {
                $canPreorder->requested_date = $requested_date;
                $app->response->setBody(json_encode($canPreorder, JSON_PRETTY_PRINT));
            }
        } catch (RuntimeException $e) {
            $app->halt(400, json_encode(array('message' => 'Invalid requested_date was provided')));
        }
    }
);

$app->get(
    '/preorders/can-preorder/:item_id(/:requested_date)',
    function ($item_id, $requested_date = null) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('i.item_id');
        $query->select('DATE(p.requested_date) requested_date');
        $query->select('COUNT(i.item_id) used_preorder_slots');
        $query->select('IF(bestseller = 1, 3, 5) available_preorder_slots');
        $query->select('IF(COUNT(i.item_id) > IF(bestseller = 1, 3, 5), 0, 1) can_preorder');

        $query->from('#__items i');
        $query->innerJoin('#__preorder_items pi ON pi.item_id = i.item_id');
        $query->innerJoin('#__preorders p ON p.preorder_id = pi.preorder_id');

        $query->where('i.item_id = ' . $app->db->quote($item_id));
        $query->where('i.item_type = ' . $app->db->quote('dessert'));
        $query->where('p.requested_date > CURDATE()');

        if (!empty($requested_date))
        {
            $query->where('p.requested_date = ' . $app->db->quote($requested_date));
        }

        $query->group('DATE(p.requested_date), i.item_id');

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        if (!empty($requested_date))
        {
            try {
                $canPreorder = $app->db->loadObject();
            } catch (RuntimeException $e) {
                $app->halt(400, json_encode(array('message' => 'Invalid requested_date was provided')));
            }
        }
        else
        {
            $canPreorder = $app->db->loadObjectList();
        }


        if (!empty($canPreorder))
        {
            $app->response->setBody(json_encode($canPreorder, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Pre-Orders have been made for Item ID (#' . $item_id . ')')));
        }
    }
);