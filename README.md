# Exchange Backend API
Exchange project aims that users can do BUY and SELL operations after getting authenticated. The Exchange project is containerized. There is docker compose file in the root directory which helps you to run both Exchnage project and database services together.
Unit tests are under the test folder.

## Note:
To be able to sell or buy any share you need to be logged in first. If you use the postman collection which attached to the repo, you would not need to grab the token and add it to header. Test script handles it automatically. You just need to be logged in with one of these users below, then execute sell or buy post requests.

username : omer@outlook.com passowrd: 12345

username : ismail@outlook.com password: 12345

## Includes:
Java, Spring boot, Spring security, Docker, Postgres

## To run the project:
After downloading the project, if you have Postgres on your machine which runs on port 5432 you can directly run the project any ide.
Or you can go to root directory of project and open the console and run the commands:
 - mvn clean install
 - docker compose up or docker-compose up (only if you have docker on your machine)

## Endpoints:
The project runs on port 8080 as default.

HTTP Request type : POST
Endpoint : localhost:8080/exchange/v1/auth
Example paylod:

{
    "email" : "omer@outlook.com",
    "password" : "12345"
}

Example response:
{
    "token": "eyJhbGciOiJIUzI1...."
}

***********************************************************

HTTP Request type : POST
Endpoint : localhost:8080/exchange/v1/buy

Example paylod:

{
    "quantity" : 1,
    "shareId" : 1
}

Example response:
Buy operation is successfully.

***********************************************************

HTTP Request type : POST
Endpoint : localhost:8080/exchange/v1/sell

Example payload:
{
    "quantity" : 1,
    "shareId" : 10,
    "price" : 300
}

Example response:
Buy operation is successfully.
