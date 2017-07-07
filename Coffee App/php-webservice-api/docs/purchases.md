# Purchases

Here is a summary of the available operations that are detailed in this document:

 - [Retrieve List of Purchases](#retrieve-list-of-purchases)
 - [Create New Purchase](#create-new-purchase)
 - [Retrieve Single Purchase](#retrieve-single-purchase)
 - [Update Single Purchase](#update-single-purchase)
 - [Delete Single Purchase](#delete-single-purchase)
 
## Retrieve List of Purchases

#### Request URL
```
GET http://coffeecart.orware.com/api/purchases
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Purchase Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Purchase Records Are Available

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

## Create New Purchase

#### Request URL
```
POST http://coffeecart.orware.com/api/purchases
```

#### Request Body
```json
{
	"vip_card_number": "16",
	"purchase_amount": "7.25",
	"location_id": "1",
	"items": {
		"1": {
			"item_id": "1",
			"item_cost": "2.50",
			"item_quantity": "1"
		},
		"2": {
			"item_id": "2",
			"item_cost": "1.25",
			"item_quantity": "1"
		},
		"3": {
			"item_id": "3",
			"item_cost": "3.50",
			"item_quantity": "1"
		}
	}
}
```

#### Response #1: New Purchase is Created Successfully

**Response Code:**
```
HTTP Status Code = 201 (Created)
```

**Example Response:**
```json
{
	"message":"Successfully created new Purchase Record (#23) with Associated Items"
}
```

#### Response #2: Incomplete request (missing Purchase fields)

**Response Code:**
```
HTTP Status Code = 403 (Forbidden)
```

**Example Response:**
```json
{
	"message":"Missing one or more required fields in JSON request (vip_card_number, purchase_amount, location_id)"
}
```

#### Response #3: Incomplete request (missing Item fields)

**Response Code:**
```
HTTP Status Code = 400 (Bad Request)
```

**Example Response:**
```json
{
	"message":"One of the items was missing a required value for Purchase #24 (item_id, item_cost, item_quantity)"
}
```

## Retrieve Single Purchase

#### Request URL
```
GET http://coffeecart.orware.com/api/purchases/:purchase_id
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Purchase is Retrieved Successfully

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "purchase_id": "23",
    "vip_card_number": "16",
    "purchase_amount": "7.25",
    "purchase_date": "2014-07-13 10:40:49",
    "location_id": "1",
    "items": {
        "1": {
            "purchase_id": "23",
            "item_id": "1",
            "item_cost": "2.50",
            "item_quantity": "1",
            "item_name": "Coffee",
            "item_type": "beverage",
            "bestseller": "0"
        },
        "2": {
            "purchase_id": "23",
            "item_id": "2",
            "item_cost": "1.25",
            "item_quantity": "1",
            "item_name": "Coffee Refill",
            "item_type": "beverage",
            "bestseller": "0"
        },
        "3": {
            "purchase_id": "23",
            "item_id": "3",
            "item_cost": "3.50",
            "item_quantity": "1",
            "item_name": "Brownie",
            "item_type": "dessert",
            "bestseller": "1"
        }
    }
}
```

#### Response #2: Unknown Purchase ID

**Response Code:**
```
HTTP Status Code = 404 (Not Found)
```

**Example Response:**
```json
{
	"message":"Unknown Purchase ID (#26)"
}
```

## Update Single Purchase

#### Request URL
```
PUT http://coffeecart.orware.com/api/purchases/:purchase_id
```

#### Request Body
```
{
    "vip_card_number": "16",
    "purchase_amount": "7.25",
    "location_id": "1",
    "items": [
        {
            "item_id": "1",
            "item_cost": "2.50",
            "item_quantity": "1"
        },
        {
            "item_id": "3",
            "item_cost": "3.50",
            "item_quantity": "1"
        }
    ]
}
```

Tip: The items list is optional. If an Items List is provided then it will replace the existing Items List currently associated with the Purchase.

#### Response #1: Successfully Updated Purchase

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
	"message":"Successfully updated Purchase #23"
}
```

#### Response #2: Incomplete request (missing Purchase fields)

**Response Code:**
```
HTTP Status Code = 400 (Bad Request)
```

**Example Response:**
```json
{
	"message":"Missing one or more required fields in JSON request (vip_card_number, purchase_amount, location_id)"
}
```

#### Response #3: Incomplete request (missing Item fields)

**Response Code:**
```
HTTP Status Code = 400 (Bad Request)
```

**Example Response:**
```json
{
	"message":"One of the items was missing a required value for Purchase #23 (item_id, item_cost, item_quantity)"
}
```

## Delete Single Purchase

#### Request URL
```
DELETE http://coffeecart.orware.com/api/purchases/:purchase_id
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Successfully Deleted Purchase

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
	"message":"Successfully deleted Purchase #23"
}
```

#### Response #2: Unknown Location ID

**Response Code:**
```
HTTP Status Code = 404 (Not Found)
```

**Example Response:**
```json
{
	"message":"Purchase #23 does not exist."
}
```