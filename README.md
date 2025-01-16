# Flagak -  E-commerce Application

## Overview

This project is an implementation for an e-commerce application developed using Spring Boot and PostgreSQL and Flutter. It includes essential modules for customer and vendor management, product handling, and cart functionality.

## Features

- **Customer Management:** Registration, login, and management of customer data.

- **Vendor Management:** Registration and management of vendors and their products.

- **Product Management:** CRUD operations for products associated with vendors.

- **Cart Management:** A customer cart system supporting product additions.


# Backend Setup

## Prerequisites

Ensure the following tools are installed:

- Java 21+

- Spring Boot 3.x

- PostgreSQL 17+

- Maven 3.x

## Project Structure
```
src/
└── main/
    └── java/
        └── com/
            └── flagak/
                └── task_backend/
                    ├── components/
                    │   ├── customers/
                    │   │   ├── controllers/
                    │   │   ├── dtos/
                    │   │   ├── services/
                    │   ├── vendors/
                    │   │   ├── controllers/
                    │   │   ├── dtos/
                    │   │   ├── services/
                    │   ├── products/
                    │   │   ├── controllers/
                    │   │   ├── dtos/
                    │   │   ├── services/
                    │   ├── carts/
                    │   │   ├── controllers/
                    │   │   ├── dtos/
                    │   │   ├── services/
                    │   ├── orders/
                    │   │   ├── controllers/
                    │   │   ├── dtos/
                    │   │   ├── services/
                    ├── models/
                    │   ├── entities/
                    │   │   ├── CustomerEntity.java
                    │   │   ├── VendorEntity.java
                    │   │   ├── ProductEntity.java
                    │   │   ├── CartEntity.java
                    │   │   ├── CartItemEntity.java
                    │   │   ├── OrderEntity.java
                    │   │   ├── OrderItemEntity.java
                    │   ├── dtos/
                    │   │   ├── LoginRequestDTO.java
                    ├── repos/
                    │   ├── CustomerRepo.java
                    │   ├── VendorRepo.java
                    │   ├── ProductRepo.java
                    │   ├── CartRepo.java
                    │   ├── CartItemRepo.java
                    │   ├── OrderRepo.java
                    │   ├── OrderItemRepo.java
                    ├── config/
                    │   ├── JwtAuthenticationFilter.java
                    │   ├── SecurityConfig.java
                    ├── utils/
                    │   ├── JwtUtil.java

```
## API End Points found @
```
src/main/resources/docs/API_Documentation.postman_collection.json
```
# Database Setup

## Schema

### Tables

&nbsp;1. Customers
```
customer_id: UUID (Primary Key, auto-generated)
name: VARCHAR(255)
email: VARCHAR(255) (Unique)
password: VARCHAR(255)
created_at: TIMESTAMP (Default: Current timestamp)
```

&nbsp;2. Vendors
```
vendor_id: UUID (Primary Key, auto-generated)
business_name: VARCHAR(255)
business_certificate_number: VARCHAR(50) (Unique)
billing_address: TEXT
email: VARCHAR(255) (Unique)
password: VARCHAR(255)
created_at: TIMESTAMP (Default: Current timestamp)
```

&nbsp;3. Products
```
product_id: UUID (Primary Key, auto-generated)
product_name: VARCHAR(255)
product_description: VARCHAR(1000)
price: DECIMAL(10,2)
stock_quantity: INT
vendor_id: UUID (Foreign Key referencing vendors.vendor_id)
created_at: TIMESTAMP (Default: Current timestamp)
```

&nbsp;4. Carts
```
cart_id: UUID (Primary Key, auto-generated)
customer_id: UUID (Foreign Key referencing customers.customer_id)
created_at: TIMESTAMP (Default: Current timestamp)
```

&nbsp;5. Cart Items
```
cart_item_id: UUID (Primary Key, auto-generated)
cart_id: UUID (Foreign Key referencing carts.cart_id)
product_id: UUID (Foreign Key referencing products.product_id)
quantity: INT
added_at: TIMESTAMP (Default: Current timestamp)
```

&nbsp;4. Orders
```
order_id: UUID (Primary Key)
customer_id: UUID (FK referencing customers.customer_id)
total_price: DECIMAL(10,2)
payment_type: VARCHAR(6)
created_at: TIMESTAMP (Default: Current timestamp)
```

&nbsp;5. Order Items
```
order_item_id: UUID (Primary Key)
order_id: UUID (FK referencing orders.order_id)
product_id: UUID (FK referencing products.product_id)
quantity: INT
price: DECIMAL(10,2) (Snapshot of product price at checkout)
```

### SQL Files
```
Schema Initialization (schema.sql)
Contains the SQL commands to create the above tables.
```
```
Test Data (data.sql)
Populates the database with sample data for testing purposes.
```

## Database Configuration

&nbsp;1. Create the PostgreSQL database:
```
psql -U postgres
CREATE DATABASE flagak;
```
&nbsp;2. Update the application.properties file in the Spring Boot project:
```
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Tech Stack

**Client:** Flutter

**Server:** Springboot, PostgreSQL

