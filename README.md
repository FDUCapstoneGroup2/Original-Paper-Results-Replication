
# Capstone AutomaticED Project

## Overview
The AutomaticED project is a Spring Boot application designed to demonstrate encryption, decryption, and order management functionalities. The system allows users to encrypt and decrypt sensitive data, save order information into a database, and retrieve it for processing. The project integrates **DES (Data Encryption Standard)** to secure user information and provides a convenient API for managing these features.

## Features
- **Encryption and Decryption**: Encrypt and decrypt sensitive data using the DES algorithm.
- **Order Management**: Save and retrieve orders, automatically encrypting sensitive information.
- **Pagination Support**: Fetch paginated order data for efficient data handling.
- **Performance Logging**: Track execution time for operations like saving orders.

## Prerequisites
- Java 11+
- Maven 3.x
- A MySQL or PostgreSQL database (or your choice of database) for storing the data
- Postman or any API testing tool (for testing the endpoints)

## Setup

### Step 1: Clone the repository
```bash
git clone https://github.com/your-repo/automaticed.git
cd automaticed
```

### Step 2: Set up the database
Create a database named `capstoneDatabase`. Make sure the database connection details in `application.properties` are properly configured.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/capstoneDatabase
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

### Step 3: Build the project
```bash
mvn clean install
```

### Step 4: Run the application
```bash
mvn spring-boot:run
```

## API Endpoints

### 1. Encrypt Data (DES)
Encrypt sensitive data using the DES algorithm.

- **Endpoint**: `/encrypt`
- **Method**: `GET`
- **Parameters**:
  - `plaintext` (String): The text to be encrypted.
  - `algorithm` (String): The encryption algorithm, but currently DES is used regardless of the input.
- **Example**: `/encrypt?plaintext=HelloWorld&algorithm=DES`

### 2. Decrypt Data (DES)
Decrypt DES-encrypted data.

- **Endpoint**: `/decrypt`
- **Method**: `GET`
- **Parameters**:
  - `ciphertext` (String): The encrypted text.
  - `algorithm` (String): The decryption algorithm, but currently DES is used regardless of the input.
- **Example**: `/decrypt?ciphertext=GeXlTVTmmwZZ2FKFmUCVpQ==&algorithm=DES`

### 3. Save Orders
Save multiple orders with randomly generated `user_name` values. This will encrypt the `user_name` before saving to the database.

- **Endpoint**: `/saveAll`
- **Method**: `GET`
- **Parameters**:
  - `count` (Integer): Number of random orders to generate and save.
- **Example**: `/saveAll?count=100`

### 4. Get Order by ID
Retrieve the decrypted `user_name` for a specific order.

- **Endpoint**: `/get`
- **Method**: `GET`
- **Parameters**:
  - `orderId` (Long): The ID of the order to retrieve.
- **Example**: `/get?orderId=100`

### 5. Get Paginated Orders
Retrieve a paginated list of orders.

- **Endpoint**: `/getAllByPage`
- **Method**: `GET`
- **Parameters**:
  - `pageNo` (int): The page number to retrieve.
  - `pageSize` (int): The size of the page.
- **Example**: `/getAllByPage?pageNo=0&pageSize=10`

### 6. Save Orders Manually
Save a list of orders with their details manually provided by the user.

- **Endpoint**: `/saveByOneself`
- **Method**: `POST`
- **Request Body**: A list of `OrderEntity` objects in JSON format.
- **Example**: `/saveByOneself`

## Testing the Application
You can test the API endpoints using tools like Postman or Curl. For example, to encrypt a string using DES:

```bash
curl -X GET "http://localhost:8080/encrypt?plaintext=HelloWorld&algorithm=DES"
```

To save 100 random orders:

```bash
curl -X GET "http://localhost:8080/saveAll?count=100"
```

## How It Works
1. **Encryption and Decryption**: The application uses the `CryptoUtil` class to encrypt and decrypt sensitive information (e.g., `user_name`) using the DES algorithm.
2. **Order Management**: Orders are saved and retrieved through `OrderService`, which interacts with the database. Sensitive information is encrypted before saving.
3. **Performance**: The `saveAll` endpoint tracks the execution time for performance monitoring.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.
