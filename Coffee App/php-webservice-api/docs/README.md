# Coffee Cart Management System PHP Web Service API

**API URL:** http://coffeecart.orware.com/api

**API KEY:** vfWHncuKQmFY4ezEmVUTSSsAaGeufzJgTb2n4Zb8Vk8HfzPSuZy9KXRSD6DguxDG

This API has been created to allow us to provide an added feature for our Android Application in Project 02.

While normally we would have to either use an in-memory saving mechanism, or the built-in SQLite Database within Android, we thought it would be fun to experiment with creating an actual RESTful Web Service API that would allow us to have a centralized database so that the Coffee Cart Management System data could be shared amongst all team members.

In the other files within this directory you will find explanations on how to create the HTTP Requests required to interact with the various entities available within the API:

 - Customers
 - Items
 - Item Types
 - Locations
 - Pre-Orders
 - Purchases
 - Reports

While we were not able to create a fully fledged authentication scheme, we have made it so that the API can only be interacted with if each request provides the **API KEY** above within the **Authorization** HTTP Header.

Additionally, if we were to put this API into production for an actual customer we would need to add an SSL Certificate to our API site so that the requests/responses would be encrypted.