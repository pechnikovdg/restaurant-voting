## Graduation project for the course Topjava
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

## API

| URL                                                                      | Http Method |     Role     |                       Description                       |
|--------------------------------------------------------------------------|:-----------:|:------------:|:-------------------------------------------------------:|
| /api/account/register                                                    |     POST    |   ANONYMOUS  | register new user                                       |
| /api/account                                                             |     GET     |     USER     | get authorized user                                     |
| /api/account                                                             |    DELETE   |     USER     | delete authorized user                                  |
| /api/account                                                             |     PUT     |     USER     | update authorized user                                  |
| /api/dishes/{id}                                                         |     GET     | ADMIN / USER | get dish                                                |
| /api/dishes/{id}                                                         |    DELETE   |     ADMIN    | delete dish                                             |
| /api/dishes/{id}                                                         |     PUT     |     ADMIN    | update dish                                             |
| /api/dishes/                                                             |     POST    |     ADMIN    | create dish                                             |
| /api/dishes/today                                                        |     GET     | ADMIN / USER | get dishes on current date                              |
| /api/dishes/filter?restaurantId={id}                                     |     GET     | ADMIN / USER | get dishes by restaurant                                |
| /api/dishes/filter?restaurantId={id}&date={value}                        |     GET     | ADMIN / USER | get dishes by restaurant and date                       |
| /api/dishes/filter?restaurantId={id}&startDate={value1}&endDate={value2} |     GET     | ADMIN / USER | get dishes by restaurant and between two dates included |
| /api/dishes/today/filter?restaurantId={id}                               |     GET     | ADMIN / USER | get dishes by restaurant on current date                |
| /api/restaurants                                                         |     GET     | ADMIN / USER | get all restaurants                                     |
| /api/restaurants/{id}                                                    |     GET     | ADMIN / USER | get restaurant                                          |
| /api/restaurants/{id}                                                    |    DELETE   |     ADMIN    | delete restaurant                                       |
| /api/restaurants/{id}                                                    |     PUT     |     ADMIN    | update restaurant                                       |
| /api/restaurants                                                         |     POST    |     ADMIN    | create restaurant                                       |
| /api/votes/user/today                                                    |     GET     |     USER     | get today's vote for authorized user                    |
| /api/votes/user                                                          |     GET     |     USER     | get votes for authorized user                           |
| /api/votes/amount/filter?restaurantId={restaurantId}&date={date}         |     GET     |     USER     | get amount of votes for restaurant on date              |
| /api/votes/amount/today/filter?restaurantId={restaurantId}               |     GET     |     USER     | get amount of votes for restaurant today                |
| /api/votes                                                               |     POST    |     USER     | create vote                                             |
| /api/votes/{id}                                                          |     PUT     |     USER     | update vote                                             |

## cURL requests

* register new user

`curl -X POST 'localhost:8080/api/account/register' \
-H 'Content-Type: application/json' \
--data-raw '{
"firstName":"John",
"lastName":"Doe",
"email":"johndoe1@gmail.com",
"password":"password"
}'`

* get authorized user

`curl -X GET 'http://localhost:8080/api/account' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

* delete authorized user

`curl -X DELETE 'http://localhost:8080/api/account' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

* update authorized user

`curl -X PUT 'http://localhost:8080/api/account' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ=' \
-H 'Content-Type: application/json' \
--data-raw '{
"firstName":"newFirstName",
"lastName":"newLastName",
"email":"newemail@gmail.com",
"password":"newpassword"
}'`

* get dish

`curl -X GET 'localhost:8080/api/dishes/100003' \
-H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

* delete dish

`curl -X DELETE 'localhost:8080/api/dishes/100003' \
-H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

* update dish

`curl -X PUT 'localhost:8080/api/dishes/100003' \
-H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
-H 'Content-Type: application/json' \
--data-raw '{
"description":"newdescription",
"price":1111,
"restaurantId":100002,
"date":"1999-01-01"
}'`

* create dish

`curl -X POST 'localhost:8080/api/dishes' \
-H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
-H 'Content-Type: application/json' \
--data-raw '{
"description":"description",
"price":100,
"restaurantId":100000,
"date":"2021-05-01"
}'`

* get dishes on current date

`curl -X GET 'localhost:8080/api/dishes/today' \
-H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

* get dishes by restaurant

`curl -X GET 'localhost:8080/api/dishes/filter?restaurantId=100002' \
  -H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

* get dishes by restaurant and date

`curl -X GET 'localhost:8080/api/dishes/filter?restaurantId=100002&date=2021-06-23' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

* get dishes by restaurant and between 2 dates included

`curl -X GET 'localhost:8080/api/dishes/filter?restaurantId=100002&startDate=2021-06-22&endDate=2021-06-23' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

* get dishes by restaurant on current date

`curl -X GET 'localhost:8080/api/dishes/today/filter?restaurantId=100002' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

* get all restaurants

`curl -X GET 'localhost:8080/api/restaurants' \
-H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

* get restaurant

`curl -X GET 'localhost:8080/api/restaurants/100000' \
-H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

* delete restaurant

`curl -X DELETE 'localhost:8080/api/restaurants/100000' \
-H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

* update restaurant

`curl -X PUT 'localhost:8080/api/restaurants/100000' \
-H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
-H 'Content-Type: application/json' \
--data-raw '{
"name":"newname"
}'`

* create restaurant

`curl -X POST 'localhost:8080/api/restaurants' \
-H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
-H 'Content-Type: application/json' \
--data-raw '{
"name":"newname"
}'`

* get today's vote for authorized user

`curl -X GET 'localhost:8080/api/votes/user/today' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

* get votes for authorized user

`curl -X GET 'localhost:8080/api/votes/user' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

* get amount of votes for restaurant on date

`curl -X GET 'localhost:8080/api/votes/amount/filter?restaurantId=100000&date=2021-05-14' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

* get amount of votes for restaurant today

`curl -X GET 'localhost:8080/api/votes/amount/today/filter?restaurantId=100000' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ='`

* create vote

`curl -X POST 'localhost:8080/api/votes' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ=' \
-H 'Content-Type: application/json' \
--data-raw '{
"restaurantId":100000
}'`

* update vote

`curl -X PUT 'localhost:8080/api/votes/100037' \
-H 'Authorization: Basic dXNlckBnbWFpbC5jb206cGFzc3dvcmQ=' \
-H 'Content-Type: application/json' \
--data-raw '{
"restaurantId":100002
}'`