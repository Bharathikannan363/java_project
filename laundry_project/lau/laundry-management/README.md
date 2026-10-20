# Laundry Management Microservice

Spring Boot 3 + Spring MVC (REST) + JPA/Hibernate + JDBC (JdbcTemplate) + MySQL

## Run
1. Start MySQL. Edit the password in `src/main/resources/application.properties`.
2. Open the project folder in IntelliJ (pom.xml -> Open as Project), wait for Maven to finish.
3. Run `LaundryManagementApplication`. Server: http://localhost:8080
   (Database `laundry_db` and all tables are created automatically.)

Open the web page: http://localhost:8080/  (file: src/main/resources/static/index.html)

## Modules / team split
| Member | Package | Base URL |
|--------|---------|----------|
| 1 | `com.laundry.customer` | /api/customers |
| 2 | `com.laundry.catalog` | /api/services |
| 3 | `com.laundry.order` (+ `report`) | /api/orders, /api/reports |

Shared: `com.laundry.common` (exceptions + global handler).

## Postman test order
1. POST /api/customers
   {"name":"Arun","phone":"9876543210","email":"arun@gmail.com","address":"Chennai"}
2. POST /api/services
   {"serviceName":"Washing","price":50}
3. POST /api/orders
   {"customerId":1,"serviceId":1,"quantity":5}      -> total 250, status RECEIVED
4. PUT /api/orders/1/status   {"status":"WASHING"}   (then READY, then DELIVERED)
5. GET /api/orders, GET /api/orders/1, GET /api/orders?status=READY
6. GET /api/reports/summary, GET /api/reports/customers   (plain JDBC)
