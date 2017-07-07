<?php
// Coffee Cart Routes
$app->get(
    '/items',
    function () use ($app) {
        //echo 'This should list all customers';
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('*');
        $query->from('#__items');

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
            $app->halt(204, json_encode(array('message' => 'No Items records are currently available.')));
        }
    }
);

$app->post(
    '/items',
    function () use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (!empty($json))
        {
            if (isset($json->item_name, $json->item_type, $json->item_cost, $json->bestseller))
            {
                // Process data:

                // Create New Query:
                $query = $app->db->getQuery(true);

                // Build Query:
                $query->insert('#__items');
                $query->set('item_name = ' . $app->db->quote($json->item_name));
                $query->set('item_type = ' . $app->db->quote($json->item_type));
                $query->set('item_cost = ' . $app->db->quote($json->item_cost));
                $query->set('bestseller = ' . $app->db->quote($json->bestseller));

                // Set Query:
                $app->db->setQuery($query);

                // Execute Query and Load Data:
                try {
                    $result = $app->db->execute();

                    $app->db->insertid();

                    if ($result)
                    {
                        $item_id = $app->db->insertid();
                        $app->halt(201, json_encode(array('message' => 'Successfully created new Item', 'item_id' => $item_id)));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'Failed to create new Item')));
                    }
                } catch(RuntimeException $e) {
                    if ($e->getCode() == 1062)
                    {
                        $app->halt(403, json_encode(array('message' => 'Item already exists (duplicate item name)')));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
                    }
                }
            }
            else
            {
                $app->halt(400, json_encode(array('message' => 'Missing one or more required fields in JSON request (item_name, item_type, item_cost, bestseller)')));
            }
        }
    }
);

$app->get(
    '/items/:item_id',
    function ($item_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('*');
        $query->from('#__items');
        $query->where('item_id = ' . $app->db->quote($item_id));

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
            $app->halt(404, json_encode(array('message' => 'Unknown Item ID')));
        }
    }
);

$app->put(
    '/items/:item_id',
    function ($item_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (!empty($json))
        {
            if (isset($json->item_name, $json->item_type, $json->item_cost, $json->bestseller))
            {
                // Process data:

                // Create New Query:
                $query = $app->db->getQuery(true);

                // Build Query:
                $query->update('#__items');
                $query->set('item_name = ' . $app->db->quote($json->item_name));
                $query->set('item_type = ' . $app->db->quote($json->item_type));
                $query->set('item_cost = ' . $app->db->quote($json->item_cost));
                $query->set('bestseller = ' . $app->db->quote($json->bestseller));
                $query->where('item_id = ' . $app->db->quote($item_id));

                // Set Query:
                $app->db->setQuery($query);

                // Execute Query and Load Data:
                try {
                    $result = $app->db->execute();

                    if ($result)
                    {
                        $app->halt(200, json_encode(array('message' => 'Successfully updated Item #' . $item_id)));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'Failed to update Item #' . $item_id)));
                    }
                } catch(RuntimeException $e) {
                    if ($e->getCode() == 1062)
                    {
                        $app->halt(403, json_encode(array('message' => 'An Item is already using that Item Name')));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
                    }

                }
            }
            else
            {
                $app->halt(400, json_encode(array('message' => 'Missing one or more required fields in JSON request (item_name, item_type, item_cost, bestseller)')));
            }
        }
    }
);

$app->delete(
    '/items/:item_id',
    function ($item_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (empty($json))
        {
            // Create New Query:
            $query = $app->db->getQuery(true);

            // Build Query:
            $query->delete('#__items');
            $query->where('item_id = ' . $app->db->quote($item_id));

            // Set Query:
            $app->db->setQuery($query);

            // Execute Query and Load Data:
            try {
                $result = $app->db->execute();

                $affectedRows = $app->db->getAffectedRows();

                if ($affectedRows == 1)
                {
                    $app->halt(200, json_encode(array('message' => 'Successfully deleted Item #' . $item_id)));
                }
                else
                {
                    $app->halt(404, json_encode(array('message' => 'Item #' . $item_id . ' does not exist')));
                }
            } catch(RuntimeException $e) {
                $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
            }
        }
    }
);