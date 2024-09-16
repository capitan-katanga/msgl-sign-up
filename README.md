# MSGL-SIGN-UP
REST service built with Spring Boot, Spring Security, and H2 database. It includes methods for sign-in, sign-up and userEntity detail.
## Requests
### sign-up
```bash
curl --location 'http://localhost:8080/api/v1/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "dummy",
    "email": "dummy@gmail.com",
    "password": "Password12",
    "phones": [
        {
            "number": 4161601,
            "citycode": 379,
            "countrycode": "+54"
        }
    ]
}'
```
### sign-in
```bash
curl --location 'http://localhost:8080/api/v1/sign-in' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"dummy@gmail.com",
    "password": "Password12"
}'
```
### getUserDetail
```bash
curl --location 'http://localhost:8080/api/v1/getUserDetail/6d6a6361-f4d0-4aee-949c-e03040ac3b20' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpZ25hY2lvZW5jaXpvQGdtYWlsLmNvbSIsImlzcyI6Imdsb2JhbCBsb2dpYyIsImlhdCI6MTcxNzg4NzE3OCwiZXhwIjoxNzE3ODk0Mzc4fQ.19VxXCBnmds-c6Bzy-hyWT8yGTTyoKlSa9FVIU8-MMs'
```
