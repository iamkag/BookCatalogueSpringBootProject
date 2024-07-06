
# Book App  [![Build Status](https://travis-ci.org/geraldoms/book-app.svg?branch=master)](https://travis-ci.org/geraldoms/book-app)  
=======================

This is a basic example of web application using Spring Boot 2. 
The app uses the MySQL database for storage and the Flyway tool for the database migration. 
It provides some RESTful APIs to access the information related to the Book and Author, a ManyToMany relationship. For the integration tests, an embedded H2 (in-memory) database is being used.   

## Requirements
* MySQL 5.6 or later
* JDK 8 or later
* Maven 3.2 or later

## Installation 
`$ mvn package`

## Usage

Make sure that the MySQL server is running, and the 
database `db_books` and the user (`book-user`) have been successfully created. 

After running the command above (in the installation section), a jar file should be in the `target` folder. Run the command below using that jar file.   
 
`jar -jar target/book-app-0.0.1-SNAPSHOT.jar`

## Running the tests

For the unit and integration test, run the command below: 

`$ mvn test`

## Request samples

##### Getting a book by ID.

Request:
```bash
curl -X GET -H 'Cache-Control: no-cache' -H 'Content-Type: application/json' http://localhost:8080/api/v1/books/1
```

Response:
```json
{
    "createAt": "2018-07-10T02:33:58.000+0000",
    "updateAt": "2018-07-10T02:33:58.000+0000",
    "id": 1,
    "title": "Clean Code: A Handbook of Agile Software Craftsmanship",
    "version": 1,
    "price": 18.45,
    "publishingDate": "2013-01-10",
    "authors": [
        {
            "createAt": "2018-07-10T02:33:58.000+0000",
            "updateAt": "2018-07-10T02:33:58.000+0000",
            "id": 1,
            "firstName": "Robert",
            "lastName": "Martin"
        }
    ]
}
```

##### Getting the authors by book.

Request:
```bash
curl -X GET -H 'Cache-Control: no-cache' -H 'Content-Type: application/json' http://localhost:8080/api/v1/books/2/authors
```

Response:
```json
[
    {
        "createAt": "2018-07-10T02:33:58.000+0000",
        "updateAt": "2018-07-10T02:33:58.000+0000",
        "id": 4,
        "firstName": "George",
        "lastName": "Spafford"
    },
    {
        "createAt": "2018-07-10T02:33:58.000+0000",
        "updateAt": "2018-07-10T02:33:58.000+0000",
        "id": 3,
        "firstName": "Kevin",
        "lastName": "Behr"
    },
    {
        "createAt": "2018-07-10T02:33:58.000+0000",
        "updateAt": "2018-07-10T02:33:58.000+0000",
        "id": 2,
        "firstName": "Gene",
        "lastName": "Kin"
    }
]
```

##### Trying to get a non-existent book.

Request:
```bash
curl -X GET -H 'Cache-Control: no-cache' -H 'Content-Type: application/json' http://localhost:8080/api/v1/books/4
```

Response:
```json
{
    "timestamp": "2018-07-10T02:56:09.803+0000",
    "status": 404,
    "error": "Not Found",
    "message": "Book not found - field id=[4]",
    "path": "/api/v1/books/4"
}
```