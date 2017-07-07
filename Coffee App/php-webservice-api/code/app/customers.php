<?php
// Coffee Cart Routes
$app->get(
    '/customers',
    function () use ($app) {
        //echo 'This should list all customers';
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('*');
        $query->from('#__customers');

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $data = $app->db->loadObjectList();

        if (!empty($data))
        {
            $app->response->setBody(json_encode($data, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Customer records are currently available.')));
        }

    }
);

$app->post(
    '/customers',
    function () use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (!empty($json))
        {
            if (isset($json->first_name, $json->last_name, $json->date_of_birth, $json->phone_number, $json->address1, $json->address2, $json->city, $json->state, $json->zip))
            {
                // Process data:
                $phone_number = preg_replace('/[^0-9]/','', $json->phone_number);


                // Create New Query:
                $query = $app->db->getQuery(true);

                // Build Query:
                $query->insert('#__customers');
                $query->set('first_name = ' . $app->db->quote($json->first_name));
                $query->set('last_name = ' . $app->db->quote($json->last_name));
                $query->set('date_of_birth = ' . $app->db->quote($json->date_of_birth));
                $query->set('phone_number = ' . $app->db->quote($phone_number));
                $query->set('address1 = ' . $app->db->quote($json->address1));
                $query->set('address2 = ' . $app->db->quote($json->address2));
                $query->set('city = ' . $app->db->quote($json->city));
                $query->set('state = ' . $app->db->quote($json->state));
                $query->set('zip = ' . $app->db->quote($json->zip));
                $query->set('created_date = NOW()');

                // Set Query:
                $app->db->setQuery($query);

                // Execute Query and Load Data:
                try {
                    $result = $app->db->execute();

                    if ($result)
                    {
                        $customer_id = $app->db->insertid();
                        $app->halt(201, json_encode(array('message' => 'Successfully created new Customer', 'customer_id' => $customer_id)));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'Failed to create new Customer')));
                    }
                } catch(RuntimeException $e) {
                    if ($e->getCode() == 1062)
                    {
                        $app->halt(403, json_encode(array('message' => 'Customer already exists (duplicate phone number)')));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
                    }
                }
            }
            else
            {
                $app->halt(400, json_encode(array('message' => 'Missing one or more required fields in JSON request (first_name, last_name, date_of_birth, phone_number, address1, address2, city, state, zip)')));
            }

        }
    }
);

$app->get(
    '/customers/:customer_id',
    function ($customer_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('*');
        $query->from('#__customers');
        $query->where('vip_card_number = ' . $app->db->quote($customer_id));

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $data = $app->db->loadObject();

        if (!empty($data))
        {
            $app->response->setBody(json_encode($data, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(404, json_encode(array('message' => 'Unknown VIP Card Number')));
        }
    }
);

$app->put(
    '/customers/:customer_id',
    function ($customer_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (!empty($json))
        {
            if (isset($json->first_name, $json->last_name, $json->date_of_birth, $json->phone_number, $json->address1, $json->address2, $json->city, $json->state, $json->zip))
            {
                // Process data:
                $phone_number = preg_replace('/[^0-9]/','', $json->phone_number);


                // Create New Query:
                $query = $app->db->getQuery(true);

                // Build Query:
                $query->update('#__customers');
                $query->set('first_name = ' . $app->db->quote($json->first_name));
                $query->set('last_name = ' . $app->db->quote($json->last_name));
                $query->set('date_of_birth = ' . $app->db->quote($json->date_of_birth));
                $query->set('phone_number = ' . $app->db->quote($phone_number));
                $query->set('address1 = ' . $app->db->quote($json->address1));
                $query->set('address2 = ' . $app->db->quote($json->address2));
                $query->set('city = ' . $app->db->quote($json->city));
                $query->set('state = ' . $app->db->quote($json->state));
                $query->set('zip = ' . $app->db->quote($json->zip));
                $query->where('vip_card_number = ' . $app->db->quote($customer_id));

                // Set Query:
                $app->db->setQuery($query);

                // Execute Query and Load Data:
                try {
                    $result = $app->db->execute();

                    if ($result)
                    {
                        $app->halt(200, json_encode(array('message' => 'Successfully updated Customer #' . $customer_id)));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'Failed to update Customer #' . $customer_id)));
                    }
                } catch(RuntimeException $e) {
                    if ($e->getCode() == 1062)
                    {
                        $app->halt(403, json_encode(array('message' => 'A customer is already using that phone number')));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
                    }

                }
            }
            else
            {
                $app->halt(400, json_encode(array('message' => 'Missing one or more required fields in JSON request (first_name, last_name, date_of_birth, phone_number, address1, address2, city, state, zip)')));
            }
        }
    }
);

$app->delete(
    '/customers/:customer_id',
    function ($customer_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (empty($json))
        {
            // Create New Query:
            $query = $app->db->getQuery(true);

            // Build Query:
            $query->delete('#__customers');
            $query->where('vip_card_number = ' . $app->db->quote($customer_id));

            // Set Query:
            $app->db->setQuery($query);

            // Execute Query and Load Data:
            try {
                $result = $app->db->execute();

                $affectedRows = $app->db->getAffectedRows();

                if ($affectedRows == 1)
                {
                    $app->halt(200, json_encode(array('message' => 'Successfully deleted Customer #' . $customer_id)));
                }
                else
                {
                    $app->halt(404, json_encode(array('message' => 'Customer #' . $customer_id . ' does not exist.')));
                }
            } catch(RuntimeException $e) {
                $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
            }
        }
    }
);

$app->get(
    '/customers/:customer_id/purchases',
    function ($customer_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('p.purchase_id, p.vip_card_number, p.purchase_amount, ROUND(purchase_amount) purchase_points, p.purchase_date, p.location_id');
        $query->from('#__purchases as p');
        $query->where('p.vip_card_number = ' . $app->db->quote($customer_id));

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
            $app->halt(204, json_encode(array('message' => 'No Purchase Data available for Customer #' . $customer_id)));
        }
    }
);

$app->get(
    '/customers/:customer_id/preorders',
    function ($customer_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('p.preorder_id, p.vip_card_number, p.requested_date, p.location_id');
        $query->from('#__preorders as p');
        $query->where('p.vip_card_number = ' . $app->db->quote($customer_id));

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
            $app->halt(204, json_encode(array('message' => 'No Preorder Data available for Customer #' . $customer_id)));
        }
    }
);

$app->get(
    '/customers/:customer_id/items',
    function ($customer_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('p.*, pi.*, i.item_name, i.item_cost current_cost, i.bestseller');
        $query->from('#__purchases as p');
        $query->innerJoin('#__purchase_items pi ON p.purchase_id = pi.purchase_id');
        $query->innerJoin('#__items i ON pi.item_id = i.item_id');
        $query->where('p.vip_card_number = ' . $app->db->quote($customer_id));

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $orderedItems = $app->db->loadObjectList();

        if (!empty($orderedItems))
        {
            $app->response->setBody(json_encode($orderedItems, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Items Ordered Data available for Customer #' . $customer_id)));
        }
    }
);

$app->get(
    '/customers/:customer_id/items-summary',
    function ($customer_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('i.item_id, i.item_name, SUM(pi.item_cost) total_spent, SUM(pi.item_quantity) total_purchased');
        $query->from('#__purchases as p');
        $query->innerJoin('#__purchase_items pi ON p.purchase_id = pi.purchase_id');
        $query->innerJoin('#__items i ON pi.item_id = i.item_id');
        $query->where('p.vip_card_number = ' . $app->db->quote($customer_id));
        $query->group('i.item_id');
        $query->group('i.item_name');

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $orderedItems = $app->db->loadObjectList('item_id');

        if (!empty($orderedItems))
        {
            $app->response->setBody(json_encode($orderedItems, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Items Ordered Summary Data available for Customer #' . $customer_id)));
        }
    }
);

$app->get(
    '/customers/:customer_id/total-points',
    function ($customer_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('SUM(ROUND(p.purchase_amount)) total_points');
        $query->from('#__purchases as p');
        $query->where('p.vip_card_number = ' . $app->db->quote($customer_id));

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $totalPoints = $app->db->loadObject();

        if (is_null($totalPoints->total_points))
        {
            $totalPoints->total_points = 0;
        }

        $app->response->setBody(json_encode($totalPoints, JSON_PRETTY_PRINT));
    }
);

$app->get(
    '/customers/:customer_id/points-earned-last-thirty-days',
    function ($customer_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('SUM(ROUND(p.purchase_amount)) total_points');
        $query->from('#__purchases as p');
        $query->where('p.vip_card_number = ' . $app->db->quote($customer_id));
        // Includes points from last 30 days + today's earned points:
        $query->where('p.purchase_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 30 DAY) AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)');

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $totalPoints = $app->db->loadObject();

        if (is_null($totalPoints->total_points))
        {
            $totalPoints->total_points = 0;
        }

        $app->response->setBody(json_encode($totalPoints, JSON_PRETTY_PRINT));
    }
);
