# Pre-Orders

Here is a summary of the available operations that are detailed in this document:

 - [Retrieve List of Pre-Orders](#retrieve-list-of-pre-orders)
 - [Create New Pre-Order](#create-new-pre-order)
 - [Retrieve Single Pre-Order](#retrieve-single-pre-order)
 - [Update Single Pre-Order](#update-single-pre-order)
 - [Delete Single Pre-Order](#delete-single-pre-order)
 - [Is the Requested Date a Valid Pre-Order Date](#is-the-requested-date-a-valid-pre-order-date)
 - [Can this Item be Pre-Ordered?](#can-this-item-be-pre-ordered)
 
## Retrieve List of Pre-Orders

#### Request URL
```
GET http://coffeecart.orware.com/api/preorders
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Pre-Order Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Pre-Order Records Are Available

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
    },
    "6": {
        "preorder_id": "6",
        "vip_card_number": "16",
        "requested_date": "2014-07-13 00:00:00",
        "location_id": "1",
        "purchase_id": "0",
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

## Create New Pre-Order

#### Request URL
```
POST http://coffeecart.orware.com/api/preorders
```

#### Request Body
```json
{
	"vip_card_number": "16",
	"requested_date": "2014-08-10 00:00:00",
	"location_id": "1",
	"items": [
		{
			"item_id": "3",
			"item_quantity": "1"
		}
	]
}
```

#### Response #1: New Pre-Order is Created Successfully

**Response Code:**
```
HTTP Status Code = 201 (Created)
```

**Example Response:**
```json
{
	"message":"Successfully created new Pre-Order Record (#7) with Associated Items",
	"preorder_id":7
}
```

#### Response #2: Incomplete request (missing Pre-Order fields)

**Response Code:**
```
HTTP Status Code = 403 (Forbidden)
```

**Example Response:**
```json
{
	"message":"Missing one or more required fields in JSON request (vip_card_number, requested_date, location_id)"
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
	message: "One of the items was missing a required value for Pre-Order #9 (item_id, item_quantity)"
}
```

## Retrieve Single Pre-Order

#### Request URL
```
GET http://coffeecart.orware.com/api/preorders/:preorder_id
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Pre-Order is Retrieved Successfully

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "preorder_id": "1",
    "vip_card_number": "16",
    "requested_date": "2014-08-10 00:00:00",
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
```

#### Response #2: Unknown Pre-Order ID

**Response Code:**
```
HTTP Status Code = 404 (Not Found)
```

**Example Response:**
```json
{
	"message":"Unknown Pre-Order ID (#100)"
}
```

## Update Single Pre-Order

#### Request URL
```
PUT http://coffeecart.orware.com/api/preorders/:preorder_id
```

#### Request Body
```
{
	"vip_card_number": "16",
	"requested_date": "2014-07-13 00:00:00",
	"location_id": "1",
	"purchase_id": "0",
	"items": [
		{
			"item_id": "2",
			"item_quantity": "2"
		}
	]
}
```

Tip: The items list and purchase_id fields are optional. If an Items List is provided then it will replace the existing Items List currently associated with the Pre-Order.

#### Response #1: Successfully Updated Pre-Order

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
	"message":"Successfully updated Pre-Order #9"
}
```

#### Response #2: Incomplete request (missing Pre-Order fields)

**Response Code:**
```
HTTP Status Code = 400 (Bad Request)
```

**Example Response:**
```json
{
	"message":"Missing one or more required fields in JSON request (vip_card_number, requested_date, location_id)"
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
	"message":"One of the items was missing a required value for Pre-Order #6 (item_id, item_quantity)"
}
```

## Delete Single Pre-Order

#### Request URL
```
DELETE http://coffeecart.orware.com/api/preorders/:preorder_id
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Successfully Deleted Pre-Order

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
	"message":"Successfully deleted Pre-Order #9"
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
	"message":"Pre-Order #18 does not exist"
}
```

## Is the Requested Date a Valid Pre-Order Date

#### Request URL
```
GET http://coffeecart.orware.com/api/preorders/valid-preorder-date/:requested_date
```

#### Request Body
```
An empty request body is allowable for this API call.
```

Tip: The requested_date option should be in the MySQL Date Format (e.g. 2014-08-20)

#### Response #1: Is Valid Date Information

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "is_valid_date": "1",
    "requested_date": "2014-07-28"
}
```

#### Response #2: Invalid Requested Date

**Response Code:**
```
HTTP Status Code = 400 (Bad Request)
```

**Example Response:**
```json
{
"message":"Invalid requested_date was provided"
}
```

## Can this Item be Pre-Ordered?

#### Request URL
```
GET http://coffeecart.orware.com/api/preorders/can-preorder/:item_id(/:requested_date)
```

#### Request Body
```
An empty request body is allowable for this API call.
```

Tip: The requested_date option should be in the MySQL Date Format (e.g. 2014-08-20)

#### Response #1: No Pre-Orders Have Been Made for the Item ID

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

#### Response #2: List of Current Pre-Orders for Item

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "item_id": "3",
        "requested_date": "2014-07-18",
        "used_preorder_slots": "1",
        "available_preorder_slots": "3",
        "can_preorder": "1"
    },
    {
        "item_id": "3",
        "requested_date": "2014-08-10",
        "used_preorder_slots": "1",
        "available_preorder_slots": "3",
        "can_preorder": "1"
    }
]
```

#### Response #3: Pre-Order Information for Item on a Requested Date

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "item_id": "3",
    "requested_date": "2014-07-18",
    "used_preorder_slots": "1",
    "available_preorder_slots": "3",
    "can_preorder": "1"
}
```