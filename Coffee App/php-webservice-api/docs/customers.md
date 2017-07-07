# Customers

Here is a summary of the available operations that are detailed in this document:

 - [Retrieve List of Customers](#retrieve-list-of-customers)
 - [Create New Customer](#create-new-customer)
 - [Retrieve Single Customer](#retrieve-single-customer)
 - [Update Single Customer](#update-single-customer)
 - [Delete Single Customer](#delete-single-customer)
 - [Retrieve a Single Customer's Purchases](#retrieve-a-single-customers-purchases)
 - [Retrieve a Single Customer's Pre-Orders](#retrieve-a-single-customers-pre-orders)
 - [Retrieve a Single Customer's Ordered Items List](#retrieve-a-single-customers-ordered-items-list)
 - [Retrieve a Single Customer's Ordered Items Summary List](#retrieve-a-single-customers-ordered-items-summary-list)
 - [Retrieve a Single Customer's Total Points](#retrieve-a-single-customers-total-points)
 - [Retrieve a Single Customer's Total Points Earned Over Last Thirty Days (Includes Points Earned Today)](#retrieve-a-single-customers-total-points-earned-over-last-thirty-days-includes-points-earned-today)

## Retrieve List of Customers

#### Request URL
```
GET http://coffeecart.orware.com/api/customers
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Customer Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Customer Records Are Available

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
        "date_of_birth": "1985-05-11",
        "phone_number": "7603538100",
        "address1": "1161 Pine St.",
        "address2": "",
        "city": "El Centro",
        "state": "CA",
        "zip": "92243",
		"created_date": "2014-07-11 11:31:27"
    },
    {
        "vip_card_number": "17",
        "first_name": "Jane",
        "last_name": "Doe",
        "date_of_birth": "1958-07-30",
        "phone_number": "7603528320",
        "address1": "380 E. Aten Rd.",
        "address2": "",
        "city": "Imperial",
        "state": "CA",
        "zip": "92251",
		"created_date": "2014-07-11 13:26:27"
    }
]
```

## Create New Customer

#### Request URL
```
POST http://coffeecart.orware.com/api/customers
```

#### Request Body
```json
{
	"first_name": "John",
	"last_name": "Doe",
	"date_of_birth": "1985-05-11",
	"phone_number": "7603538100",
	"address1": "1161 Pine St.",
	"address2": "",
	"city": "El Centro",
	"state": "CA",
	"zip": "92243"
}
```

#### Response #1: New Customer is Created Successfully

**Response Code:**
```
HTTP Status Code = 201 (Created)
```

**Example Response:**
```json
{
	"message":"Successfully created new Customer",
	"customer_id":18
}
```

#### Response #2: Customer already exists (duplicate phone number)

**Response Code:**
```
HTTP Status Code = 403 (Forbidden)
```

**Example Response:**
```json
{
	"message":"Customer already exists (duplicate phone number)"
}
```

#### Response #3: Incomplete request (missing fields)

**Response Code:**
```
HTTP Status Code = 400 (Bad Request)
```

**Example Response:**
```json
{
	"message":"Missing one or more required fields in JSON request (first_name, last_name, date_of_birth, phone_number, address1, address2, city, state, zip)"
}
```

## Retrieve Single Customer

#### Request URL
```
GET http://coffeecart.orware.com/api/customers/:vip_card_number
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Customer is Retrieved Successfully

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "vip_card_number": "18",
    "first_name": "John",
    "last_name": "Doe",
    "date_of_birth": "1985-05-11",
    "phone_number": "7603538100",
    "address1": "1161 Pine St.",
    "address2": "",
    "city": "El Centro",
    "state": "CA",
    "zip": "92243",
    "created_date": "2014-07-12 15:51:27"
}
```

#### Response #2: Unknown VIP Card Number

**Response Code:**
```
HTTP Status Code = 404 (Not Found)
```

**Example Response:**
```json
{
	"message":"Unknown VIP Card Number"
}
```

## Update Single Customer

#### Request URL
```
PUT http://coffeecart.orware.com/api/customers/:vip_card_number
```

#### Request Body
```
{
	"first_name": "John",
	"last_name": "Doe",
	"date_of_birth": "1985-05-11",
	"phone_number": "7603538100",
	"address1": "100 New Address Ave.",
	"address2": "",
	"city": "New Citytown",
	"state": "CA",
	"zip": "92243"
}
```

#### Response #1: Successfully Updated Customer

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
	"message":"Successfully updated Customer #18"
}
```

#### Response #2: Incomplete request (missing fields)

**Response Code:**
```
HTTP Status Code = 400 (Bad Request)
```

**Example Response:**
```json
{
	"message":"Missing one or more required fields in JSON request (first_name, last_name, date_of_birth, phone_number, address1, address2, city, state, zip)"
}
```

#### Response #3: Attempting to Update Phone Number to One Already in Use

**Response Code:**
```
HTTP Status Code = 403 (Forbidden)
```

**Example Response:**
```json
{
	"message":"A customer is already using that phone number"
}
```

## Delete Single Customer

#### Request URL
```
DELETE http://coffeecart.orware.com/api/customers/:vip_card_number
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Successfully Deleted Customer

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
	"message":"Successfully deleted Customer #18"
}
```

#### Response #2: Unknown VIP Card Number

**Response Code:**
```
HTTP Status Code = 404 (Not Found)
```

**Example Response:**
```json
{
	"message":"Customer #18 does not exist"
}
```

## Retrieve a Single Customer's Purchases

#### Request URL
```
GET http://coffeecart.orware.com/api/customers/:vip_card_number/purchases
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Successfully Retrieved Customer's Purchases

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "1": {
        "purchase_id": "1",
        "vip_card_number": "16",
        "purchase_amount": "7.25",
        "purchase_points": "7",
        "purchase_date": "2013-06-03 21:55:16",
        "location_id": "1",
        "items": {
            "1": {
                "purchase_id": "1",
                "item_id": "1",
                "item_cost": "2.50",
                "item_quantity": "1",
                "item_name": "Coffee",
                "item_type": "beverage",
                "bestseller": "0"
            },
            "2": {
                "purchase_id": "1",
                "item_id": "2",
                "item_cost": "1.25",
                "item_quantity": "1",
                "item_name": "Coffee Refill",
                "item_type": "beverage",
                "bestseller": "0"
            },
            "3": {
                "purchase_id": "1",
                "item_id": "3",
                "item_cost": "3.50",
                "item_quantity": "1",
                "item_name": "Brownie",
                "item_type": "dessert",
                "bestseller": "1"
            }
        }
    },
    "2": {
        "purchase_id": "2",
        "vip_card_number": "16",
        "purchase_amount": "2.50",
        "purchase_points": "3",
        "purchase_date": "2014-07-05 22:58:11",
        "location_id": "1",
        "items": {
            "1": {
                "purchase_id": "2",
                "item_id": "1",
                "item_cost": "2.50",
                "item_quantity": "1",
                "item_name": "Coffee",
                "item_type": "beverage",
                "bestseller": "0"
            }
        }
    }
}
```

#### Response #2: No Purchase Data Available for Customer

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

## Retrieve a Single Customer's Pre-Orders

#### Request URL
```
GET http://coffeecart.orware.com/api/customers/:vip_card_number/preorders
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Successfully Retrieved Customer's Pre-Orders

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
        "requested_date": "2014-08-10 00:00:00",
        "location_id": "1",
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
    },
    "6": {
        "preorder_id": "6",
        "vip_card_number": "16",
        "requested_date": "2014-07-13 00:00:00",
        "location_id": "1",
        "items": {
            "3": {
                "preorder_id": "6",
                "item_id": "3",
                "item_quantity": "2",
                "item_name": "Brownie",
                "item_type": "dessert",
                "item_cost": "3.50",
                "bestseller": "1"
            }
        }
    }
}
```

#### Response #2: No Pre-Order Data Available for Customer

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

## Retrieve a Single Customer's Ordered Items List

#### Request URL
```
GET http://coffeecart.orware.com/api/customers/:vip_card_number/items
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Successfully Retrieved Customer's Ordered Items List

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "purchase_id": "1",
        "vip_card_number": "16",
        "purchase_amount": "7.25",
        "purchase_date": "2013-06-03 21:55:16",
        "location_id": "1",
        "item_id": "1",
        "item_cost": "2.50",
        "item_quantity": "1",
        "item_name": "Coffee",
        "current_cost": "2.50",
        "bestseller": "0"
    },
    {
        "purchase_id": "2",
        "vip_card_number": "16",
        "purchase_amount": "2.50",
        "purchase_date": "2014-07-05 22:58:11",
        "location_id": "1",
        "item_id": "1",
        "item_cost": "2.50",
        "item_quantity": "1",
        "item_name": "Coffee",
        "current_cost": "2.50",
        "bestseller": "0"
    },
    {
        "purchase_id": "5",
        "vip_card_number": "16",
        "purchase_amount": "7.25",
        "purchase_date": "2014-07-11 08:20:07",
        "location_id": "1",
        "item_id": "1",
        "item_cost": "2.50",
        "item_quantity": "1",
        "item_name": "Coffee",
        "current_cost": "2.50",
        "bestseller": "0"
    }
]
```

#### Response #2: No Ordered Items Data Available for Customer

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

## Retrieve a Single Customer's Ordered Items Summary List

#### Request URL
```
GET http://coffeecart.orware.com/api/customers/:vip_card_number/items-summary
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Successfully Retrieved Customer's Ordered Items Summary List

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "1": {
        "item_id": "1",
        "item_name": "Coffee",
        "total_spent": "47.50",
        "total_purchased": "19"
    },
    "2": {
        "item_id": "2",
        "item_name": "Coffee Refill",
        "total_spent": "13.75",
        "total_purchased": "11"
    },
    "3": {
        "item_id": "3",
        "item_name": "Brownie",
        "total_spent": "49.00",
        "total_purchased": "14"
    }
}
```

#### Response #2: No Ordered Items Summary Data Available for Customer

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

## Retrieve a Single Customer's Total Points

#### Request URL
```
GET http://coffeecart.orware.com/api/customers/:vip_card_number/total-points
```

#### Request Body
```
An empty request body is allowable for this API call.
```

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "total_points": "133"
}
```

## Retrieve a Single Customer's Total Points Earned Over Last Thirty Days (Includes Points Earned Today)

#### Request URL
```
GET http://coffeecart.orware.com/api/customers/:vip_card_number/points-earned-last-thirty-days
```

#### Request Body
```
An empty request body is allowable for this API call.
```

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "total_points": "126"
}
```