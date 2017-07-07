<?php
// Coffee Cart Routes
$app->get(
    '/desserts',
    function () use ($app) {
        //echo 'This should list all customers';
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('*');
        $query->from('#__items');
        $query->where('item_type = ' . $app->db->quote('dessert'));

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
            $app->halt(204, json_encode(array('message' => 'No Dessert Items records are currently available.')));
        }
    }
);

$app->get(
    '/bestsellers',
    function () use ($app) {
        //echo 'This should list all customers';
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('*');
        $query->from('#__items');
        $query->where('item_type = ' . $app->db->quote('dessert'));
        $query->where('bestseller = ' . $app->db->quote(1));

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
            $app->halt(204, json_encode(array('message' => 'No Bestseller Items records are currently available.')));
        }
    }
);

$app->get(
    '/beverages',
    function () use ($app) {
        //echo 'This should list all customers';
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('*');
        $query->from('#__items');
        $query->where('item_type = ' . $app->db->quote('beverage'));

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
            $app->halt(204, json_encode(array('message' => 'No Beverage Items records are currently available.')));
        }
    }
);
