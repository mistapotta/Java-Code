<?php
// Coffee Cart Routes
$app->get(
    '/locations(/:location_id_or_order_by_field(/:direction))',
    function ($location_id_or_order_by_field = null, $direction = 'ASC') use ($app) {
        //echo 'This should list all customers';
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('*');
        $query->from('#__locations');

        if (is_null($location_id_or_order_by_field))
        {
            // Default Behavior: Get all Records, Sort by Default Field
            $order_by_field = 'location_name';
            $query->order($order_by_field . ' ' . $direction);
        }
        elseif(is_numeric($location_id_or_order_by_field))
        {
            // Get Single Record Behavior, No Sorting Required
            $query->where('location_id = ' . $app->db->quote($location_id_or_order_by_field));
        }
        else
        {
            // Get all Records, Sort by Provided Field / Direction
            $order_by_field = 'location_name';
            if ($location_id_or_order_by_field != 'name')
            {
                $order_by_field = 'location_neighborhood';
            }

            if ($direction != 'ASC')
            {
                $direction = 'DESC';
            }

            $query->order($order_by_field . ' ' . $direction);
        }

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        if (is_numeric($location_id_or_order_by_field))
        {
            $data = $app->db->loadObject();
        }
        else
        {
            $data = $app->db->loadObjectList();
        }


        if (!empty($data))
        {
            $app->response->setBody(json_encode($data, JSON_PRETTY_PRINT));
        }
        else
        {
            //$app->halt(404, json_encode(array('message' => is_numeric($location_id_or_order_by_field))));
            if (is_numeric($location_id_or_order_by_field))
            {
                $app->halt(404, json_encode(array('message' => 'Unknown Location ID')));
            }
            else
            {
                $app->halt(204, json_encode(array('message' => 'No Location records are currently available.')));
            }

        }
    }
);

$app->post(
    '/locations',
    function () use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (!empty($json))
        {
            if (isset($json->location_neighborhood, $json->location_name))
            {
                // Process data:

                // Create New Query:
                $query = $app->db->getQuery(true);

                // Build Query:
                $query->insert('#__locations');
                $query->set('location_neighborhood = ' . $app->db->quote($json->location_neighborhood));
                $query->set('location_name = ' . $app->db->quote($json->location_name));

                // Set Query:
                $app->db->setQuery($query);

                // Execute Query and Load Data:
                try {
                    $result = $app->db->execute();

                    $app->db->insertid();

                    if ($result)
                    {
                        $location_id = $app->db->insertid();
                        $app->halt(201, json_encode(array('message' => 'Successfully created new Location', 'location_id' => $location_id)));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'Failed to create new Location')));
                    }
                } catch(RuntimeException $e) {
                    if ($e->getCode() == 1062)
                    {
                        $app->halt(403, json_encode(array('message' => 'Location already exists (duplicate Location Neighborhood and Name combination)')));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
                    }
                }
            }
            else
            {
                $app->halt(400, json_encode(array('message' => 'Missing one or more required fields in JSON request (location_neighborhood, location_name)')));
            }

        }
    }
);

$app->put(
    '/locations/:location_id',
    function ($location_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (!empty($json))
        {
            if (isset($json->location_neighborhood, $json->location_name))
            {
                // Process data:

                // Create New Query:
                $query = $app->db->getQuery(true);

                // Build Query:
                $query->update('#__locations');
                $query->set('location_neighborhood = ' . $app->db->quote($json->location_neighborhood));
                $query->set('location_name = ' . $app->db->quote($json->location_name));
                $query->where('location_id = ' . $app->db->quote($location_id));

                // Set Query:
                $app->db->setQuery($query);

                // Execute Query and Load Data:
                try {
                    $result = $app->db->execute();

                    if ($result)
                    {
                        $app->halt(200, json_encode(array('message' => 'Successfully updated Location #' . $location_id)));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'Failed to update Location #' . $location_id)));
                    }
                } catch(RuntimeException $e) {
                    if ($e->getCode() == 1062)
                    {
                        $app->halt(403, json_encode(array('message' => 'A Location is already using that Location Neighborhood and Name Combination')));
                    }
                    else
                    {
                        $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
                    }

                }
            }
            else
            {
                $app->halt(400, json_encode(array('message' => 'Missing one or more required fields in JSON request (location_neighborhood, location_name)')));
            }
        }
    }
);

$app->delete(
    '/locations/:location_id',
    function ($location_id) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        $body = $app->request->getBody();

        $json = json_decode($body);

        if (empty($json))
        {
            // Create New Query:
            $query = $app->db->getQuery(true);

            // Build Query:
            $query->delete('#__locations');
            $query->where('location_id = ' . $app->db->quote($location_id));

            // Set Query:
            $app->db->setQuery($query);

            // Execute Query and Load Data:
            try {
                $result = $app->db->execute();

                $affectedRows = $app->db->getAffectedRows();

                if ($affectedRows == 1)
                {
                    $app->halt(200, json_encode(array('message' => 'Successfully deleted Location #' . $location_id)));
                }
                else
                {
                    $app->halt(404, json_encode(array('message' => 'Location #' . $location_id . ' does not exist.')));
                }
            } catch(RuntimeException $e) {
                $app->halt(403, json_encode(array('message' => 'An unknown error occurred')));
            }
        }
    }
);