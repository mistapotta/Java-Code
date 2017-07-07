# Reports

Here is a summary of the available operations that are detailed in this document:

 - [Retrieve List of Purchases Made Today](#retrieve-list-of-purchases-made-today)
 - [Retrieve List of Pre-Orders Created Today](#retrieve-list-of-pre-orders-created-today)
 - [Retrieve List of Pre-Orders Requested for Today](#retrieve-list-of-pre-orders-requested-for-today)
 - [Retrieve Weekly Purchases Summary](#retrieve-weekly-purchases-summary)
 - [Retrieve Weekly Pre-Orders Summary](#retrieve-weekly-pre-orders-summary)
 - [Retrieve Monthly Purchases Summary](#retrieve-monthly-purchases-summary)
 - [Retrieve Monthly Pre-Orders Summary](#retrieve-monthly-pre-orders-summary)
 - [Retrieve Yearly Purchases Summary](#retrieve-yearly-purchases-summary)
 - [Retrieve Yearly Pre-Orders Summary](#retrieve-yearly-pre-orders-summary)
 - [Retrieve Top Customers by Purchases](#retrieve-top-customers-by-purchases)
 - [Retrieve Top Customers by Revenues](#retrieve-top-customers-by-revenues)
 - [Retrieve Top Items Purchased by Quantity](#retrieve-top-items-purchased-by-quantity)
 - [Retrieve Top Items Purchased by Revenues](#retrieve-top-items-purchased-by-revenues)
 
## Retrieve List of Purchases Made Today

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/purchases-made-today(/:direction)
```

**Optional Direction Options (default = ASC)**
```
ASC|DESC
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "24": {
        "purchase_id": "24",
        "vip_card_number": "16",
        "purchase_amount": "7.25",
        "purchase_points": "7",
        "purchase_date": "2014-07-13 10:47:58",
        "location_id": "1"
    },
	"25": {
        "purchase_id": "25",
        "vip_card_number": "16",
        "purchase_amount": "8.65",
        "purchase_points": "9",
        "purchase_date": "2014-07-13 14:47:58",
        "location_id": "1"
    }
}
```

## Retrieve List of Pre-Orders Created Today

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/preorders-created-today(/:direction)
```

**Optional Direction Options (default = ASC)**
```
ASC|DESC
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "2": {
        "preorder_id": "2",
        "vip_card_number": "16",
        "requested_date": "2014-07-16 00:00:00",
        "created_date": "2014-07-13 00:08:00",
        "location_id": "1",
        "purchase_id": "0",
        "items": {
            "2": {
                "preorder_id": "2",
                "item_id": "2",
                "item_quantity": "2",
                "item_name": "Coffee Refill",
                "item_type": "beverage",
                "item_cost": "1.25",
                "bestseller": "0"
            }
        }
    },
    "11": {
        "preorder_id": "11",
        "vip_card_number": "16",
        "requested_date": "2014-08-24 00:00:00",
        "created_date": "2014-07-13 12:51:20",
        "location_id": "1",
        "purchase_id": "0",
        "items": {
            "3": {
                "preorder_id": "11",
                "item_id": "3",
                "item_quantity": "1",
                "item_name": "Brownie",
                "item_type": "dessert",
                "item_cost": "3.50",
                "bestseller": "1"
            }
        }
    }
}
```

## Retrieve List of Pre-Orders Requested for Today

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/preorders-requested-for-today(/:direction)
```

**Optional Direction Options (default = ASC)**
```
ASC|DESC
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "1": {
        "preorder_id": "1",
        "vip_card_number": "16",
        "requested_date": "2014-07-13 00:00:00",
        "created_date": "0000-00-00 00:00:00",
        "location_id": "1",
        "purchase_id": "4",
        "items": {
            "3": {
                "preorder_id": "1",
                "item_id": "3",
                "item_quantity": "1",
                "item_name": "Brownie",
                "item_type": "dessert",
                "item_cost": "3.50",
                "bestseller": "1"
            }
        }
    }
}
```

## Retrieve Weekly Purchases Summary

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/weekly-purchases-summary(/:year_direction(/:week_direction))
```

**Optional Year Direction Options (default = ASC)**
```
ASC|DESC
```

**Optional Week Direction Options (default = ASC)**
```
ASC|DESC
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "year_week_name": "2013 - Week 22",
        "year": "2013",
        "week": "22",
        "number_of_purchases": "1",
        "total_revenue": "7.25",
        "location_id": "1"
    },
    {
        "year_week_name": "2014 - Week 26",
        "year": "2014",
        "week": "26",
        "number_of_purchases": "1",
        "total_revenue": "2.50",
        "location_id": "1"
    },
    {
        "year_week_name": "2014 - Week 27",
        "year": "2014",
        "week": "27",
        "number_of_purchases": "20",
        "total_revenue": "134.00",
        "location_id": "1"
    },
    {
        "year_week_name": "2014 - Week 28",
        "year": "2014",
        "week": "28",
        "number_of_purchases": "1",
        "total_revenue": "7.25",
        "location_id": "1"
    }
]
```

## Retrieve Weekly Pre-Orders Summary

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/weekly-preorders-summary(/:year_direction(/:week_direction))
```

**Optional Year Direction Options (default = ASC)**
```
ASC|DESC
```

**Optional Week Direction Options (default = ASC)**
```
ASC|DESC
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "year_week_name": "2014 - Week 27",
        "year": "2014",
        "week": "27",
        "number_of_preorders": "1",
        "number_of_fulfilled_preorders": "0",
        "location_id": "1"
    },
    {
        "year_week_name": "2014 - Week 28",
        "year": "2014",
        "week": "28",
        "number_of_preorders": "5",
        "number_of_fulfilled_preorders": "1",
        "location_id": "1"
    },
    {
        "year_week_name": "2014 - Week 32",
        "year": "2014",
        "week": "32",
        "number_of_preorders": "2",
        "number_of_fulfilled_preorders": "0",
        "location_id": "1"
    },
    {
        "year_week_name": "2014 - Week 34",
        "year": "2014",
        "week": "34",
        "number_of_preorders": "1",
        "number_of_fulfilled_preorders": "0",
        "location_id": "1"
    }
]
```

## Retrieve Monthly Purchases Summary

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/monthly-purchases-summary(/:year_direction(/:month_direction))
```

**Optional Year Direction Options (default = ASC)**
```
ASC|DESC
```

**Optional Month Direction Options (default = ASC)**
```
ASC|DESC
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "year_month_name": "June 2013",
        "year": "2013",
        "month": "6",
        "number_of_purchases": "1",
        "total_revenue": "7.25",
        "location_id": "1"
    },
    {
        "year_month_name": "July 2014",
        "year": "2014",
        "month": "7",
        "number_of_purchases": "22",
        "total_revenue": "143.75",
        "location_id": "1"
    }
]
```

## Retrieve Monthly Pre-Orders Summary

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/monthly-preorders-summary(/:year_direction(/:month_direction))
```

**Optional Year Direction Options (default = ASC)**
```
ASC|DESC
```

**Optional Month Direction Options (default = ASC)**
```
ASC|DESC
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "year_month_name": "July 2014",
        "year": "2014",
        "month": "7",
        "number_of_preorders": "6",
        "number_of_fulfilled_preorders": "1",
        "location_id": "1"
    },
    {
        "year_month_name": "August 2014",
        "year": "2014",
        "month": "8",
        "number_of_preorders": "3",
        "number_of_fulfilled_preorders": "0",
        "location_id": "1"
    }
]
```

## Retrieve Yearly Purchases Summary

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/yearly-purchases-summary(/:year_direction)
```

**Optional Year Direction Options (default = ASC)**
```
ASC|DESC
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "year": "2013",
        "number_of_purchases": "1",
        "total_revenue": "7.25",
        "location_id": "1"
    },
    {
        "year": "2014",
        "number_of_purchases": "22",
        "total_revenue": "143.75",
        "location_id": "1"
    }
]
```

## Retrieve Yearly Pre-Orders Summary

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/yearly-preorders-summary(/:year_direction)
```

**Optional Year Direction Options (default = ASC)**
```
ASC|DESC
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "year": "2014",
        "number_of_preorders": "9",
        "number_of_fulfilled_preorders": "1",
        "location_id": "1"
    }
]
```

## Retrieve Top Customers by Purchases

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/top-customers-by-purchases(/:from(/:to))
```

**Optional From Option (default = ignored)**
```
Please provide a valid MySQL date for this option.
```

**Optional To Option (default = ignored)**
```
Please provide a valid MySQL date for this option.
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "vip_card_number": "16",
        "first_name": "John",
        "last_name": "Doe",
        "date_of_birth": "1980-03-13",
        "phone_number": "7603558320",
        "address1": "123 Fake St.",
        "address2": "",
        "city": "El Centro",
        "state": "CA",
        "zip": "92243",
        "created_date": "2014-07-12 15:51:08",
        "total_purchases": "22"
    },
    {
        "vip_card_number": "17",
        "first_name": "Jane",
        "last_name": "Doe",
        "date_of_birth": "1982-08-24",
        "phone_number": "7604558132",
        "address1": "82 Fake St.",
        "address2": "",
        "city": "El Centro",
        "state": "CA",
        "zip": "92243",
        "created_date": "2014-07-12 15:51:08",
        "total_purchases": "1"
    }
]
```

## Retrieve Top Customers by Revenues

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/top-customers-by-revenues(/:from(/:to))
```

**Optional From Option (default = ignored)**
```
Please provide a valid MySQL date for this option.
```

**Optional To Option (default = ignored)**
```
Please provide a valid MySQL date for this option.
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "vip_card_number": "16",
        "first_name": "John",
        "last_name": "Doe",
        "date_of_birth": "1980-03-13",
        "phone_number": "7603558320",
        "address1": "123 Fake St.",
        "address2": "",
        "city": "El Centro",
        "state": "CA",
        "zip": "92243",
        "created_date": "2014-07-12 15:51:08",
        "total_revenues": "143.75"
    },
    {
        "vip_card_number": "17",
        "first_name": "Jane",
        "last_name": "Doe",
        "date_of_birth": "1982-08-24",
        "phone_number": "7604558132",
        "address1": "82 Fake St.",
        "address2": "",
        "city": "El Centro",
        "state": "CA",
        "zip": "92243",
        "created_date": "2014-07-12 15:51:08",
        "total_revenues": "7.25"
    }
]
```

## Retrieve Top Items Purchased by Quantity

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/top-items-purchased-by-quantity(/:from(/:to))
```

**Optional From Option (default = ignored)**
```
Please provide a valid MySQL date for this option.
```

**Optional To Option (default = ignored)**
```
Please provide a valid MySQL date for this option.
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "item_id": "1",
        "item_name": "Coffee",
        "revenues_generated": "50.00",
        "quantity_sold": "20"
    },
    {
        "item_id": "3",
        "item_name": "Brownie",
        "revenues_generated": "52.50",
        "quantity_sold": "15"
    },
    {
        "item_id": "2",
        "item_name": "Coffee Refill",
        "revenues_generated": "15.00",
        "quantity_sold": "12"
    }
]
```

## Retrieve Top Items Purchased by Revenues

#### Request URL
```
GET http://coffeecart.orware.com/api/reports/top-items-purchased-by-revenues(/:from(/:to))
```

**Optional From Option (default = ignored)**
```
Please provide a valid MySQL date for this option.
```

**Optional To Option (default = ignored)**
```
Please provide a valid MySQL date for this option.
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "item_id": "3",
        "item_name": "Brownie",
        "revenues_generated": "52.50",
        "quantity_sold": "15"
    },
    {
        "item_id": "1",
        "item_name": "Coffee",
        "revenues_generated": "50.00",
        "quantity_sold": "20"
    },
    {
        "item_id": "2",
        "item_name": "Coffee Refill",
        "revenues_generated": "15.00",
        "quantity_sold": "12"
    }
]
```

