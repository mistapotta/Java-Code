# Items

Here is a summary of the available operations that are detailed in this document:

 - [Retrieve List of Items](#retrieve-list-of-items)
 - [Create New Item](#create-new-item)
 - [Retrieve Single Item](#retrieve-single-item)
 - [Update Single Item](#update-single-item)
 - [Delete Single Item](#delete-single-item)
 
## Retrieve List of Items

#### Request URL
```
GET http://coffeecart.orware.com/api/items
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Item Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Item Records Are Available

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
        "item_type": "beverage",
        "item_cost": "2.50",
        "bestseller": "0"
    },
    {
        "item_id": "2",
        "item_name": "Coffee Refill",
        "item_type": "beverage",
        "item_cost": "1.25",
        "bestseller": "0"
    },
    {
        "item_id": "3",
        "item_name": "Brownie",
        "item_type": "dessert",
        "item_cost": "3.50",
        "bestseller": "1"
    }
]
```

## Create New Item

#### Request URL
```
POST http://coffeecart.orware.com/api/items
```

#### Request Body
```json
{
	"item_name": "Cheesecake",
	"item_type": "dessert",
	"item_cost": "3.75",
	"bestseller": "0"
}
```

#### Response #1: New Item is Created Successfully

**Response Code:**
```
HTTP Status Code = 201 (Created)
```

**Example Response:**
```json
{
	"message":"Successfully created new Item",
	"item_id":9
}
```

#### Response #2: Item already exists (duplicate item name)

**Response Code:**
```
HTTP Status Code = 403 (Forbidden)
```

**Example Response:**
```json
{
	"message":"Item already exists (duplicate item name)"
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
	"message":"Missing one or more required fields in JSON request (item_name, item_type, item_cost, bestseller)"
}
```

## Retrieve Single Item

#### Request URL
```
GET http://coffeecart.orware.com/api/items/:item_id
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Item is Retrieved Successfully

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "item_id": "9",
    "item_name": "Cheesecake",
    "item_type": "dessert",
    "item_cost": "3.75",
    "bestseller": "0"
}
```

#### Response #2: Unknown Item ID

**Response Code:**
```
HTTP Status Code = 404 (Not Found)
```

**Example Response:**
```json
{
	"message":"Unknown Item ID"
}
```

## Update Single Item

#### Request URL
```
PUT http://coffeecart.orware.com/api/items/:item_id
```

#### Request Body
```
{
    "item_name": "Cheesecake",
    "item_type": "dessert",
    "item_cost": "3.75",
    "bestseller": "1"
}
```

#### Response #1: Successfully Updated Item

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
	"message":"Successfully updated Item #9"
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
	"message":"Missing one or more required fields in JSON request (item_name, item_type, item_cost, bestseller)"
}
```

#### Response #3: Attempting to Update Item Name to One Already in Use

**Response Code:**
```
HTTP Status Code = 403 (Forbidden)
```

**Example Response:**
```json
{
	"message":"An Item is already using that Item Name"
}
```

## Delete Single Item

#### Request URL
```
DELETE http://coffeecart.orware.com/api/items/:item_id
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Successfully Deleted Item

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
	"message":"Successfully deleted Item #9"
}
```

#### Response #2: Unknown Item ID

**Response Code:**
```
HTTP Status Code = 404 (Not Found)
```

**Example Response:**
```json
{
	"message":"Item #18 does not exist"
}
```