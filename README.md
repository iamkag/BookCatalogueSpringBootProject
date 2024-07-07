# Book Management System - README

## Overview

This project is a Spring MVC application designed for managing a collection of books. It provides functionality for listing, adding, editing, and deleting books. Each book can have multiple authors, and the system allows for filtering and sorting the list of books based on various criteria.

## Database Structure

The system uses two main tables:

- **Book**: Contains details about each book, such as title, publication date, and other relevant information.
- **Author**: Contains details about each author, such as first name, last name, and other relevant information.

These tables are joined by the **author_id** field, creating a many-to-many relationship between books and authors. This means each book can have multiple authors, and each author can be associated with multiple books.

The application uses the **H2 database** for data storage, which is an in-memory database solution ideal for development and testing.

## Endpoints

### Get All Books

**Endpoint:** `/all-books`  
**Method:** `GET`  
**Description:** Retrieves a list of all books with optional sorting and filtering parameters.

**Query Parameters:**
- `sortBy` (optional): Specifies the field to sort by (e.g., `title`).
- `direction` (optional, default=`asc`): Specifies the sort direction (`asc` or `desc`).
- `title` (optional): Filters books by title.
- `author` (optional): Filters books by author name.

**Response Model:**
- `books`: List of books.
- `sortDirection`: Current sort direction.
- `title`: Current title filter.
- `author`: Current author filter.
- `totalBooks`: Total number of books found.
- `errorMessage` (if applicable): Error message if no books are found.

### Add Book Page

**Endpoint:** `/add`  
**Method:** `GET`  
**Description:** Displays the form for adding a new book.

### Add Book

**Endpoint:** `/addbook`  
**Method:** `POST`  
**Description:** Adds a new book to the system.

**Request Model:**
- `Book`: The book object to be added.

**Response:**
- Redirects to `/all-books` on success.
- Displays an error message on failure.

### Edit Book Page

**Endpoint:** `/edit/{id}`  
**Method:** `GET`  
**Description:** Displays the form for editing an existing book.

**Path Variables:**
- `id`: The ID of the book to be edited.

### Update Book

**Endpoint:** `/updatebook`  
**Method:** `POST`  
**Description:** Updates an existing book in the system.

**Request Model:**
- `Book`: The book object with updated information.

**Response:**
- Redirects to `/all-books` on success.
- Displays an error message on failure.

### Delete Book

**Endpoint:** `/delete/{id}`  
**Method:** `GET`  
**Description:** Deletes a book from the system.

**Path Variables:**
- `id`: The ID of the book to be deleted.

### Get Book Info

**Endpoint:** `/info/{id}`  
**Method:** `GET`  
**Description:** Retrieves detailed information about a specific book, including related books by the same authors.

**Path Variables:**
- `id`: The ID of the book to retrieve information for.

**Response Model:**
- `book`: The book object.
- `relatedBooks`: Set of books by the same authors.

## Custom Data Binding

The `BookController` class includes custom data binding for handling dates and collections of authors. This ensures that dates are properly formatted and that authors are correctly parsed from input strings.

### Date Binding

Dates are parsed using the `yyyy-MM-dd` format.

### Authors Binding

Authors are parsed from a comma-separated string, and each author's name is split into first and last names.

## Running the Application

1. Clone the repository.
2. Ensure you have Java and Maven installed.
3. Navigate to the project directory.
4. Build the project using `mvn clean install`.
5. Run the application using `mvn spring-boot:run`.
6. Access the application in your web browser at `http://localhost:8080`.

Alternatively, you can run the application using Docker:

1. Clone the repository.
2. Navigate to the project directory.
3. Execute the script by running `./build_and_run.sh` in your terminal.

The application supports Docker, making it easy to deploy and run in any environment.

## Dependencies

- Spring Boot
- Spring MVC
- Spring Boot Starter Security
- Spring Boot Starter Thymeleaf
- Hibernate
- H2 Database

## Database Schema

Below is the database schema representing the relationship between the `Book` and `Author` tables.

### Book Table

| Column Name       | Data Type | Constraints              |
|-------------------|-----------|--------------------------|
| id                | Long      | Primary Key, Auto Increment |
| title             | String    | Not Null, Size (1, 250)  |
| version           | Integer   | Not Null                 |
| price             | Double    | Not Null                 |
| publishingDate    | Date      |                          |

### Author Table

| Column Name       | Data Type | Constraints              |
|-------------------|-----------|--------------------------|
| id                | Long      | Primary Key, Auto Increment |
| firstName         | String    | Not Null, Size (1, 250)  |
| lastName          | String    | Not Null, Size (1, 250)  |

### Author_Book Table

| Column Name       | Data Type | Constraints              |
|-------------------|-----------|--------------------------|
| BOOK_ID           | Long      | Foreign Key (Book.id)    |
| AUTHOR_ID         | Long      | Foreign Key (Author.id)  |

## AuditModel

The `AuditModel` class is a base class for entities to automatically manage creation and update timestamps. It includes the following fields:

- `createAt`: The date and time when the entity was created.
- `updateAt`: The date and time when the entity was last updated.

These fields are automatically populated and updated by Spring Data JPA's auditing features.


