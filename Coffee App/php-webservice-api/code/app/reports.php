<?php
$app->get(
    '/reports/purchases-made-today(/:direction)',
    function ($direction = 'ASC') use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        if ($direction != 'ASC')
        {
            $direction = 'DESC';
        }

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('p.purchase_id, p.vip_card_number, p.purchase_amount, ROUND(purchase_amount) purchase_points, p.purchase_date, p.location_id');
        $query->from('#__purchases p');
        $query->where('purchase_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)');
        $query->order('p.purchase_id ' . $direction);

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
            $app->halt(204, json_encode(array('message' => 'No Purchase Data available for Today')));
        }
    }
);

$app->get(
    '/reports/preorders-created-today(/:direction)',
    function ($direction = 'ASC') use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        if ($direction != 'ASC')
        {
            $direction = 'DESC';
        }

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('p.preorder_id, p.vip_card_number, p.requested_date, p.created_date, p.location_id, p.purchase_id');
        $query->from('#__preorders p');
        $query->where('p.created_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)');
        $query->order('p.preorder_id ' . $direction);

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
            $app->halt(204, json_encode(array('message' => 'No Pre-Orders were Created Today')));
        }
    }
);

$app->get(
    '/reports/preorders-requested-for-today(/:direction)',
    function ($direction = 'ASC') use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        if ($direction != 'ASC')
        {
            $direction = 'DESC';
        }

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('p.preorder_id, p.vip_card_number, p.requested_date, p.created_date, p.location_id, p.purchase_id');
        $query->from('#__preorders p');
        $query->where('p.requested_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 DAY)');
        $query->order('p.requested_date ' . $direction);

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
            $app->halt(204, json_encode(array('message' => 'No Pre-Order Data available')));
        }
    }
);

$app->get(
    '/reports/weekly-purchases-summary(/:year_direction(/:week_direction))',
    function ($year_direction = 'ASC', $week_direction = 'ASC') use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        if ($year_direction != 'ASC')
        {
            $year_direction = 'DESC';
        }

        if ($week_direction != 'ASC')
        {
            $week_direction = 'DESC';
        }

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('CONCAT(YEAR(p.purchase_date), \' - Week \', WEEK(p.purchase_date)) year_week_name');
        $query->select('YEAR(p.purchase_date) year');
        $query->select('WEEK(p.purchase_date) week');
        $query->select('COUNT(p.purchase_id) number_of_purchases, SUM(p.purchase_amount) total_revenue, location_id');
        $query->from('#__purchases p');
        $query->group('p.location_id, year, week');
        $query->order('year ' . $year_direction . ', week ' . $week_direction);

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $summary = $app->db->loadObjectList();

        if (!empty($summary))
        {
            $app->response->setBody(json_encode($summary, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Weekly Summary Purchase Data available')));
        }
    }
);

$app->get(
    '/reports/weekly-preorders-summary(/:year_direction(/:week_direction))',
    function ($year_direction = 'ASC', $week_direction = 'ASC') use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        if ($year_direction != 'ASC')
        {
            $year_direction = 'DESC';
        }

        if ($week_direction != 'ASC')
        {
            $week_direction = 'DESC';
        }

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('CONCAT(YEAR(p.requested_date), \' - Week \', WEEK(p.requested_date)) year_week_name');
        $query->select('YEAR(p.requested_date) year');
        $query->select('WEEK(p.requested_date) week');
        $query->select('COUNT(p.requested_date) number_of_preorders, SUM(IF(p.purchase_id > 0,1,0)) number_of_fulfilled_preorders, location_id');
        $query->from('#__preorders p');
        $query->group('p.location_id, year, week');
        $query->order('year ' . $year_direction . ', week ' . $week_direction);

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $summary = $app->db->loadObjectList();

        if (!empty($summary))
        {
            $app->response->setBody(json_encode($summary, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Weekly Summary Pre-Order Data available')));
        }
    }
);

$app->get(
    '/reports/monthly-purchases-summary(/:year_direction(/:month_direction))',
    function ($year_direction = 'ASC', $month_direction = 'ASC') use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        if ($year_direction != 'ASC')
        {
            $year_direction = 'DESC';
        }

        if ($month_direction != 'ASC')
        {
            $month_direction = 'DESC';
        }

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('CONCAT(MONTHNAME(p.purchase_date), \' \', YEAR(p.purchase_date)) year_month_name');
        $query->select('YEAR(p.purchase_date) "year"');
        $query->select('MONTH(p.purchase_date) "month"');
        $query->select('COUNT(p.purchase_id) number_of_purchases, SUM(p.purchase_amount) total_revenue, location_id');
        $query->from('#__purchases p');
        $query->group('p.location_id, YEAR(p.purchase_date), MONTH(p.purchase_date)');
        $query->order('YEAR(p.purchase_date) ' . $year_direction . ', MONTH(p.purchase_date) ' . $month_direction);

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $summary = $app->db->loadObjectList();

        if (!empty($summary))
        {
            $app->response->setBody(json_encode($summary, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Monthly Summary Purchase Data available')));
        }
    }
);

$app->get(
    '/reports/monthly-preorders-summary(/:year_direction(/:month_direction))',
    function ($year_direction = 'ASC', $month_direction = 'ASC') use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        if ($year_direction != 'ASC')
        {
            $year_direction = 'DESC';
        }

        if ($month_direction != 'ASC')
        {
            $month_direction = 'DESC';
        }

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('CONCAT(MONTHNAME(p.requested_date), \' \', YEAR(p.requested_date)) year_month_name');
        $query->select('YEAR(p.requested_date) "year"');
        $query->select('MONTH(p.requested_date) "month"');
        $query->select('COUNT(p.preorder_id) number_of_preorders, SUM(IF(p.purchase_id > 0,1,0)) number_of_fulfilled_preorders, location_id');
        $query->from('#__preorders p');
        $query->group('p.location_id, YEAR(p.requested_date), MONTH(p.requested_date)');
        $query->order('YEAR(p.requested_date) ' . $year_direction . ', MONTH(p.requested_date) ' . $month_direction);

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $summary = $app->db->loadObjectList();

        if (!empty($summary))
        {
            $app->response->setBody(json_encode($summary, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Monthly Summary Pre-Order Data available')));
        }
    }
);

$app->get(
    '/reports/yearly-purchases-summary(/:year_direction)',
    function ($year_direction = 'ASC') use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        if ($year_direction != 'ASC')
        {
            $year_direction = 'DESC';
        }

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('YEAR(p.purchase_date) year');
        $query->select('COUNT(p.purchase_id) number_of_purchases, SUM(p.purchase_amount) total_revenue, location_id');
        $query->from('#__purchases p');
        $query->group('p.location_id, YEAR(p.purchase_date)');
        $query->order('YEAR(p.purchase_date) ' . $year_direction);

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $summary = $app->db->loadObjectList();

        if (!empty($summary))
        {
            $app->response->setBody(json_encode($summary, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Yearly Summary Purchase Data available')));
        }
    }
);

$app->get(
    '/reports/yearly-preorders-summary(/:year_direction)',
    function ($year_direction = 'ASC') use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        if ($year_direction != 'ASC')
        {
            $year_direction = 'DESC';
        }

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('YEAR(p.requested_date) year');
        $query->select('COUNT(p.purchase_id) number_of_preorders, SUM(IF(p.purchase_id > 0,1,0)) number_of_fulfilled_preorders, location_id');
        $query->from('#__preorders p');
        $query->group('p.location_id, YEAR(p.requested_date)');
        $query->order('YEAR(p.requested_date) ' . $year_direction);

        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $summary = $app->db->loadObjectList();

        if (!empty($summary))
        {
            $app->response->setBody(json_encode($summary, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No Yearly Summary Pre-Order Data available')));
        }
    }
);

$app->get(
    '/reports/top-customers-by-purchases(/:from(/:to))',
    function ($from = null, $to = null) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('c.*, COUNT(p.purchase_id) total_purchases');
        $query->from('#__customers c');
        $query->innerJoin('#__purchases p ON c.vip_card_number = p.vip_card_number');

        if (!empty($from) && !empty($to))
        {
            $query->where('p.purchase_date BETWEEN ' . $app->db->quote($from) . ' AND ' . $app->db->quote($to));
        }

        $query->group('c.vip_card_number');
        $query->order('total_purchases DESC');


        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $topCustomers = $app->db->loadObjectList();

        if (!empty($topCustomers))
        {
            $app->response->setBody(json_encode($topCustomers, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No "Top Customers in Purchases" Data available')));
        }
    }
);

$app->get(
    '/reports/top-customers-by-revenues(/:from(/:to))',
    function ($from = null, $to = null) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('c.*, SUM(p.purchase_amount) total_revenues');
        $query->from('#__customers c');
        $query->innerJoin('#__purchases p ON c.vip_card_number = p.vip_card_number');

        if (!empty($from) && !empty($to))
        {
            $query->where('p.purchase_date BETWEEN ' . $app->db->quote($from) . ' AND ' . $app->db->quote($to));
        }

        $query->group('c.vip_card_number');
        $query->order('total_revenues DESC');


        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $topCustomers = $app->db->loadObjectList();

        if (!empty($topCustomers))
        {
            $app->response->setBody(json_encode($topCustomers, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No "Top Customers in Purchases" Data available')));
        }
    }
);

$app->get(
    '/reports/top-items-purchased-by-quantity(/:from(/:to))',
    function ($from = null, $to = null) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('i.item_id, i.item_name, SUM(pi.item_cost) revenues_generated, SUM(pi.item_quantity) quantity_sold');
        $query->from('#__items i');
        $query->innerJoin('#__purchase_items pi ON i.item_id = pi.item_id');
        $query->innerJoin('#__purchases p ON p.purchase_id = pi.purchase_id');

        if (!empty($from) && !empty($to))
        {
            $query->where('p.purchase_date BETWEEN ' . $app->db->quote($from) . ' AND ' . $app->db->quote($to));
        }

        $query->group('i.item_id');
        $query->order('quantity_sold DESC');


        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $topItems = $app->db->loadObjectList();

        if (!empty($topItems))
        {
            $app->response->setBody(json_encode($topItems, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No "Top Items by Quantity" Data available')));
        }
    }
);

$app->get(
    '/reports/top-items-purchased-by-revenues(/:from(/:to))',
    function ($from = null, $to = null) use ($app) {
        $app->response->headers->set('Content-Type', 'application/json');

        // Create New Query:
        $query = $app->db->getQuery(true);

        // Build Query:
        $query->select('i.item_id, i.item_name, SUM(pi.item_cost) revenues_generated, SUM(pi.item_quantity) quantity_sold');
        $query->from('#__items i');
        $query->innerJoin('#__purchase_items pi ON i.item_id = pi.item_id');
        $query->innerJoin('#__purchases p ON p.purchase_id = pi.purchase_id');

        if (!empty($from) && !empty($to))
        {
            $query->where('p.purchase_date BETWEEN ' . $app->db->quote($from) . ' AND ' . $app->db->quote($to));
        }

        $query->group('i.item_id');
        $query->order('revenues_generated DESC');


        // Set Query:
        $app->db->setQuery($query);

        // Execute Query and Load Data:
        $topItems = $app->db->loadObjectList();

        if (!empty($topItems))
        {
            $app->response->setBody(json_encode($topItems, JSON_PRETTY_PRINT));
        }
        else
        {
            $app->halt(204, json_encode(array('message' => 'No "Top Items by Quantity" Data available')));
        }
    }
);