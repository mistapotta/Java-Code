<?php
$app->get(
    '/purchases',
    function () use ($app) {
        //echo 'This should list all customers';
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('p.purchase_id, p.vip_card_number, p.purchase_amount, ROUND(purchase_amount) purchase_points, p.purchase_date, p.location_id');
        $query->from('#__purchases p');

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $purchases = $app->db->loadObjectList('purchase_id');

        if (!empty($purchases))
        {
            // Retrieve Items associated with each purchase:
            $purchaseIds = array_keys($purchases);

            // Create New Query:
            $query = $app->db->getQuery(true);

            // Build Query:
            $query->select('*');
            $query->from('#__purchase_items as pi');
            $query->innerJoin('#__items as i ON pi.item_Id = i.item_id');
            $query->where('pi.purchase_id IN (' . implode(',', $purchaseIds) . ')');

            // Set Query:
            $app->db->setQuery($query);

            // Execute Query and Load Data:
            $purchaseItems = $app->db->loadObjectList();

            if (!empty($purchaseItems))
            {
                foreach($purchaseItems as $purchaseItem)
                {
                    $purchase_id = $purchaseItem->purchase_id;
                    $item_id = $purchaseItem->item_id;

                    if (isset($purchases[$purchase_id]) && !isset($purchases[$purchase_id]->items))
                    {
                        $purchases[$purchase_id]->items = array();
                    }

                    $purchases[$purchase_id]->items[$item_id] = $purchaseItem;
                }
            }

            $app->response->setBody(json_encode($purchases, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Purchase Data available')));
        }
    }
);

$app->post(
    '/purchases',
    function () use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (!empty($json))
        {
            if (isset($json->items))
            {
                if (isset($json->vip_card_number, $json->purchase_amount, $json->location_id))
                {
                    // Process data (if needed):

                    // Create New Query:
                    $query = $app->db->getQuery(true);

                    // Build Query:
                    $query->insert('#__purchases');
                    $query->set('vip_card_number = ' . $app->db->quote($json->vip_card_number));
                    $query->set('purchase_amount = ' . $app->db->quote($json->purchase_amount));
                    $query->set('purchase_date = NOW()');
                    $query->set('location_id = ' . $app->db->quote($json->location_id));

                    // Set Query:
                    $app->db->setQuery($query);

                    // Execute Query and Load Data:
                    try {
                        $result = $app->db->execute();

                        $purchase_id = $app->db->insertid();

                        if ($result)
                        {
                            foreach($json->items as $item)
                            {
                                if (isset($item->item_id) && isset($item->item_cost) && isset($item->item_quantity))
                                {
                                    // Create New Query:
                                    $query = $app->db->getQuery(true);

                                    // Build Query:
                                    $query->insert('#__purchase_items');
                                    $query->set('purchase_id = ' . $app->db->quote($purchase_id));
                                    $query->set('item_id = ' . $app->db->quote($item->item_id));
                                    $query->set('item_cost = ' . $app->db->quote($item->item_cost));
                                    $query->set('item_quantity = ' . $app->db->quote($item->item_quantity));

                                    // Set Query:
                                    $app->db->setQuery($query);

                                    try {
                                        $result = $app->db->execute();
                                    } catch (Exception $e) {
                                        $app->halt(403, json_encode(array('message' => 'Failed to add one of the items for Purchase #' . $purchase_id, 'purchase_id' => $purchase_id)));
                                    }
                                }
                                else
                                {
                                    $app->halt(403, json_encode(array('message' => 'One of the items was missing a required value for Purchase #' . $purchase_id . ' (item_id, item_cost, item_quantity)')));
                                }
                            }

                            $app->halt(201, json_encode(array('message' => 'Successfully created new Purchase Record (#' . $purchase_id . ') with Associated Items')));
                        }
                        else
                        {
                            $app->halt(403, json_encode(array('message' => 'Failed to create new Purchase')));
                        }
                    } catch(RuntimeException $e) {
                        if ($e->getCode() == 1062)
                        {
                            $app->halt(403, json_encode(array('message' => 'Failed to Create new Purchase for Customer #' . $json->vip_card_number)));
                        }
                        else
                        {
                            $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
                        }
                    }
                }
                else
                {
                    $app->halt(400, json_encode(array('message' => 'Missing one or more required fields in JSON request (vip_card_number, purchase_amount, location_id)')));
                }
            }
            else
            {
                $app->halt(403, json_encode(array('message' => 'A new Purchase must contain Items')));
            }
        }
    }
);

$app->get(
    '/purchases/:purchase_id',
    function ($purchase_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('*');
        $query->from('#__purchases p');
        $query->where('p.purchase_id = ' . $app->db->quote($purchase_id));

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $purchase = $app->db->loadObject();

        if (!empty($purchase))
        {
            // Create New Query:
            $query = $app->db->getQuery(true);

            // Build Query:
            $query->select('*');
            $query->from('#__purchase_items as pi');
            $query->innerJoin('#__items as i ON pi.item_Id = i.item_id');
            $query->where('pi.purchase_id = ' . $purchase_id);

            // Set Query:
            $app->db->setQuery($query);

            // Execute Query and Load Data:
            $purchaseItems = $app->db->loadObjectList();

            if (!empty($purchaseItems))
            {
                foreach($purchaseItems as $purchaseItem)
                {
                    $item_id = $purchaseItem->item_id;

                    if (!isset($purchase->items))
                    {
                        $purchase->items = array();
                    }

                    $purchase->items[$item_id] = $purchaseItem;
                }
            }

            $app->response->setBody(json_encode($purchase, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(404, json_encode(array('message' => 'Unknown Purchase ID (#' . $purchase_id . ')')));
        }
    }
);

$app->put(
    '/purchases/:purchase_id',
    function ($purchase_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (!empty($json))
        {
            if (isset($json->vip_card_number, $json->purchase_amount, $json->location_id))
            {
                // Process data:

                // Create New Query:
                $query = $app->db->getQuery(true);

                // Build Query:
                $query->update('#__purchases');
                $query->set('vip_card_number = ' . $app->db->quote($json->vip_card_number));
                $query->set('purchase_amount = ' . $app->db->quote($json->purchase_amount));
                $query->set('location_id = ' . $app->db->quote($json->location_id));
                $query->where('purchase_id = ' . $app->db->quote($purchase_id));

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
                            $query->delete('#__purchase_items');
                            $query->where('purchase_id = ' . $app->db->quote($purchase_id));

                            // Set Query:
                            $app->db->setQuery($query);

                            try {
                                $result = $app->db->execute();
                            } catch (Exception $e) {
                                $app->halt(403, json_encode(array('message' => 'Failed to delete existing Purchase Items for Purchase #' . $purchase_id)));
                            }

                            foreach($json->items as $item)
                            {
                                if (isset($item->item_id) && isset($item->item_cost) && isset($item->item_quantity))
                                {
                                    // Create New Query:
                                    $query = $app->db->getQuery(true);

                                    // Build Query:
                                    $query->insert('#__purchase_items');
                                    $query->set('purchase_id = ' . $app->db->quote($purchase_id));
                                    $query->set('item_id = ' . $app->db->quote($item->item_id));
                                    $query->set('item_cost = ' . $app->db->quote($item->item_cost));
                                    $query->set('item_quantity = ' . $app->db->quote($item->item_quantity));

                                    // Set Query:
                                    $app->db->setQuery($query);

                                    try {
                                        $result = $app->db->execute();
                                    } catch (Exception $e) {
                                        $app->halt(403, json_encode(array('message' => 'Failed to add one of the items for Purchase #' . $purchase_id)));
                                    }
                                }
                                else
                                {
                                    $app->halt(403, json_encode(array('message' => 'One of the items was missing a required value for Purchase #' . $purchase_id . ' (item_id, item_cost, item_quantity)')));
                                }
                            }
                        }
                        $app->halt(200, json_encode(array('message' => 'Successfully updated Purchase #' . $purchase_id)));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'Failed to update Purchase #' . $purchase_id)));
                    }
                } catch(RuntimeException $e) {
                    $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
                }
            }
            else
            {
                $app->halt(400, json_encode(array('message' => 'Missing one or more required fields in JSON request (vip_card_number, purchase_amount, location_id)')));
            }
        }
    }
);

$app->delete(
    '/purchases/:purchase_id',
    function ($purchase_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (empty($json))
        {
            // Create New Query:
            $query = $app->db->getQuery(true);

            // Build Query:
            $query->delete('#__purchases');
            $query->where('purchase_id = ' . $app->db->quote($purchase_id));

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
                    $query->delete('#__purchase_items');
                    $query->where('purchase_id = ' . $app->db->quote($purchase_id));

                    // Set Query:
                    $app->db->setQuery($query);

                    try {
                        $result = $app->db->execute();
                    } catch (Exception $e) {
                        $app->halt(403, json_encode(array('message' => 'Failed to delete Purchase Items for Purchase #' . $purchase_id)));
                    }

                    $app->halt(200, json_encode(array('message' => 'Successfully deleted Purchase #' . $purchase_id)));
                }
                else
                {
                    $app->halt(404, json_encode(array('message' => 'Purchase #' . $purchase_id . ' does not exist.')));
                }
            } catch(RuntimeException $e) {
                $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
            }
        }
    }
);