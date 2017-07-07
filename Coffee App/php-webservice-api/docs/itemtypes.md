# Item Types

Here is a summary of the available operations that are detailed in this document:

 - [Retrieve List of Dessert Items](#retrieve-list-of-dessert-items)
 - [Retrieve List of Bestseller Items](#retrieve-list-of-bestseller-items)
 - [Retrieve List of Beverage Items](#retrieve-list-of-beverage-items)
 
## Retrieve List of Dessert Items

#### Request URL
```
GET http://coffeecart.orware.com/api/desserts
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Dessert Item Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Dessert Item Records Are Available

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
        "item_type": "dessert",
        "item_cost": "3.50",
        "bestseller": "1"
    },
    {
        "item_id": "5",
        "item_name": "Chocolate Chip Cookie",
        "item_type": "dessert",
        "item_cost": "1.50",
        "bestseller": "1"
    },
	{
        "item_id": "7",
        "item_name": "Vegan Brownie",
        "item_type": "dessert",
        "item_cost": "4.00",
        "bestseller": "0"
    }
]
```

## Retrieve List of Bestseller Items

#### Request URL
```
GET http://coffeecart.orware.com/api/bestsellers
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Bestseller Item Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Bestseller Item Records Are Available

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
        "item_type": "dessert",
        "item_cost": "3.50",
        "bestseller": "1"
    },
    {
        "item_id": "5",
        "item_name": "Chocolate Chip Cookie",
        "item_type": "dessert",
        "item_cost": "1.50",
        "bestseller": "1"
    }
]
```

## Retrieve List of Beverage Items

#### Request URL
```
GET http://coffeecart.orware.com/api/beverages
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Beverage Item Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Beverage Item Records Are Available

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
    }
]
```