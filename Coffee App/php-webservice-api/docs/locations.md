# Locations

Here is a summary of the available operations that are detailed in this document:

 - [Retrieve List of Locations](#retrieve-list-of-locations)
 - [Create New Location](#create-new-location)
 - [Retrieve Single Location](#retrieve-single-location)
 - [Update Single Location](#update-single-location)
 - [Delete Single Location](#delete-single-location)
 
## Retrieve List of Locations

#### Request URL
```
GET http://coffeecart.orware.com/api/locations(/:order_by_field(/:direction))
```

**Optional Order By Field Options (default = name)**
```
name|neighborhood
```

**Optional Direction Options (default = ASC)**
```
ASC|DESC
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Location Records Are Not Available

**Response Code:**
```
HTTP Status Code = 204 (No Content)
```

**Example Response:**
```
Response does not contain any data.
```

Tip: You should simply be able to check if this response code has been returned. If so, then there were no records available to be returned.

#### Response #2: Location Records Are Available

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
[
    {
        "location_id": "9",
        "location_neighborhood": "Midtown",
        "location_name": "Atlantic Station"
    },
    {
        "location_id": "8",
        "location_neighborhood": "Sherwood Forest",
        "location_name": "Buckhead"
    },
    {
        "location_id": "1",
        "location_neighborhood": "Downtown",
        "location_name": "Centennial Hill"
    },
    {
        "location_id": "2",
        "location_neighborhood": "Georgia Tech",
        "location_name": "Midtown"
    }
]
```

## Create New Location

#### Request URL
```
POST http://coffeecart.orware.com/api/locations
```

#### Request Body
```json
{
	"location_neighborhood": "Midtown",
	"location_name": "Atlantic Station"
}
```

#### Response #1: New Location is Created Successfully

**Response Code:**
```
HTTP Status Code = 201 (Created)
```

**Example Response:**
```json
{
	"message":"Successfully created new Location",
	"location_id":12
}
```

#### Response #2: Location already exists (duplicate Location Neighborhood and Name combination)

**Response Code:**
```
HTTP Status Code = 403 (Forbidden)
```

**Example Response:**
```json
{
	"message":"Location already exists (duplicate Location Neighborhood and Name combination)"
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
	"message":"Missing one or more required fields in JSON request (location_neighborhood, location_name)"
}
```

## Retrieve Single Location

#### Request URL
```
GET http://coffeecart.orware.com/api/locations/:location_id
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Location is Retrieved Successfully

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
    "location_id": "2",
    "location_neighborhood": "Georgia Tech",
    "location_name": "Midtown"
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
	"message":"Unknown Location ID"
}
```

## Update Single Location

#### Request URL
```
PUT http://coffeecart.orware.com/api/locations/:location_id
```

#### Request Body
```
{
	"location_neighborhood": "Midtown",
	"location_name": "Updated Location Name Example"
}
```

#### Response #1: Successfully Updated Location

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
	"message":"Successfully updated Location #9"
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
	"message":"Missing one or more required fields in JSON request (location_neighborhood, location_name)"
}
```

#### Response #3: Attempting to Update Location Neighborhood and Name Combination to One Already in Use

**Response Code:**
```
HTTP Status Code = 403 (Forbidden)
```

**Example Response:**
```json
{
	"message":"A Location is already using that Location Neighborhood and Name Combination"
}
```

## Delete Single Location

#### Request URL
```
DELETE http://coffeecart.orware.com/api/locations/:location_id
```

#### Request Body
```
An empty request body is allowable for this API call.
```

#### Response #1: Successfully Deleted Location

**Response Code:**
```
HTTP Status Code = 200 (OK)
```

**Example Response:**
```json
{
	"message":"Successfully deleted Location #9"
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
	"message":"Location #18 does not exist"
}
```