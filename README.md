# Dealership Management System

## Overview
The **Dealership Management System** is a Java-based application designed to manage car dealership operations. It allows users to manage vehicle inventory, handle sales and lease contracts, and interact with the dealership database seamlessly through a CLI interface.

The system leverages **JDBC** for database interactions and is structured with a clear separation of concerns, using Data Access Objects (DAO) to interact with a **MySQL** database.

---

## Features
- **Vehicle Management**:
  - Add, remove, and list vehicles.
  - Filter vehicles by price, make/model, year, color, mileage, and type.
- **Contract Management**:
  - Create sales contracts with detailed breakdowns of payments, taxes, and fees.
  - Generate lease contracts with monthly payments and terms.
  - View all sales and lease contracts.
- **Database-Backed Design**:
  - Data is stored persistently in a MySQL database.
  - Efficient queries to fetch filtered data based on user preferences.

---

## Technologies
- **Java**: Core programming language.
- **JDBC**: For database interaction.
- **MySQL**: Backend database for persistent data storage.
- **Apache Commons DBCP**: Connection pooling for efficient database connectivity.

---

## Project Structure
src/ 
├── com.pluralsight.dealership │ 
├── dao │ │ 
  ├── LeaseDaoSqlImpl.java # Manages Lease Contracts 
  ├── SalesDaoMySqlImpl.java # Manages Sales Contracts 
  │ └── VehicleDaoMySqlImpl.java # Manages Vehicle Data 
├── model │ │ 
  ├── Vehicle.java # Vehicle entity class 
  ├── SalesContract.java # Sales contract model │ 
  │ └── LeaseContract.java # Lease contract model │ 
├── utils | |
  ├── InputValidation.java # Validates user inputs │ 
  │ └── ColorCodes.java # Handles CLI color coding │ 
└── view │ 
  └── UserInterface.java # User-facing CLI interface 
└── com.pluralsight └── Main.java # Entry point for the application


---

## DAO Pattern and JDBC Usage

The application uses the **DAO pattern** to interact with the MySQL database. This approach provides a clean separation between the application's business logic and database operations, ensuring maintainability and scalability.

### DAO Classes

#### 1. `VehicleDaoMySqlImpl`
- Handles CRUD operations for vehicle data in the database.
- Example methods:
  - `getAllVehicles()`: Retrieves all vehicles from the database.
  - `addVehicle(String vin, int year, String make, String model, String type, String color, int mileage, double price, boolean sold)`: Adds a new vehicle to the inventory.
  - `removeVehicleByVin(String vin)`: Removes a vehicle by its VIN.

#### 2. `SalesDaoMySqlImpl`
- Manages sales contracts.
- Example methods:
  - `addSalesContract(String vin, LocalDate date, String name, String email, double price, double paymentAmount, double taxes, double fees, double balanceDue)`: Creates a new sales contract and updates the vehicle's sold status.
  - `getVehiclesSold()`: Retrieves a list of all sold vehicles.

#### 3. `LeaseDaoSqlImpl`
- Handles lease contracts.
- Example methods:
  - `addLeaseContract(String vin, LocalDate date, String name, String email, double price, double monthlyPayment, int leaseTerm)`: Creates a new lease contract.
  - `getAllLeaseContracts()`: Retrieves all lease contracts.

### JDBC in Action

#### Connection Pooling
- The application uses **Apache Commons DBCP** for managing database connections. The pooling ensures efficient usage of database resources and reduces overhead in creating connections.

#### Prepared Statements
- DAO classes use **PreparedStatements** to execute SQL queries. This approach prevents SQL injection and ensures optimized query execution.
- Example:
  ```java
  String sql = "INSERT INTO vehicles (vin, year, make, model, type, color, mileage, price, sold) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
  try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, vin);
      stmt.setInt(2, year);
      stmt.setString(3, make);
      stmt.setString(4, model);
      stmt.setString(5, type);
      stmt.setString(6, color);
      stmt.setInt(7, mileage);
      stmt.setDouble(8, price);
      stmt.setBoolean(9, sold);
      stmt.executeUpdate();
  }
  ```

## Transactions
- For operations that involve multiple queries (e.g., adding a sales contract and marking a vehicle as sold), transactions ensure data consistency.
ResultSet Handling
- Results from queries are processed using ResultSet objects, which map database rows to Java objects like Vehicle, SalesContract, and LeaseContract.

### Key Classes
## 1. UserInterface
The central class for handling user interactions.
Uses DAO classes to fetch, display, and modify data in the database.
Implements methods like:
- processGetByPriceRequest(Scanner scanner): Filters vehicles by price range.
- processAddVehicleRequest(Scanner scanner): Adds a new vehicle to the inventory.
- processVehicleContract(Scanner scanner): Handles the creation of sales or lease contracts.
## 2. Main
Entry point of the application.
Initializes the database connection pool and starts the UserInterface.
## 3. ColorCodes and InputValidation
ColorCodes: Enhances the CLI with color-coded output using ANSI escape codes.
InputValidation: Provides utility methods to validate and parse user inputs.

### Database Schema
Tables
- vehicles
- ```java
CREATE TABLE vehicles (
    vin VARCHAR(17) PRIMARY KEY,
    year INT NOT NULL,
    make VARCHAR(50),
    model VARCHAR(50),
    type VARCHAR(50),
    color VARCHAR(50),
    mileage INT,
    price DOUBLE,
    sold BOOLEAN
);
```

- sales_contracts
- ```java
CREATE TABLE sales_contracts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vin VARCHAR(17) NOT NULL,
    contract_date DATE,
    customer_name VARCHAR(100),
    customer_email VARCHAR(100),
    total_price DOUBLE,
    down_payment DOUBLE,
    taxes DOUBLE,
    fees DOUBLE,
    balance_due DOUBLE,
    FOREIGN KEY (vin) REFERENCES vehicles(vin)
);
```

- lease_contracts
- ```java
CREATE TABLE lease_contracts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  vin VARCHAR(17) NOT NULL,
  contract_date DATE,
  customer_name VARCHAR(100),
  customer_email VARCHAR(100),
  total_price DOUBLE,
  monthly_payment DOUBLE,
  lease_term INT,
  FOREIGN KEY (vin) REFERENCES vehicles(vin)
);
```
```


### Screenshots
# Home Screen
![image](https://github.com/user-attachments/assets/8fd2e482-f20d-46e5-bdac-3a5641094305)

# Vehicle Display Screen
![image](https://github.com/user-attachments/assets/5beb744f-034e-4fd0-bc4a-700092c18984)

# Interesting Code
![image](https://github.com/user-attachments/assets/e6f4da3b-5604-45a8-a002-5271b4936be4)

This code is interesting for several reasons:

1. Modern SQL String Handling
The use of a multi-line string (""") for the SQL query is a modern and clean approach introduced in Java 15 with text blocks. This makes the SQL query highly readable and reduces errors related to formatting.
2. Prepared Statements for Security
The method uses PreparedStatement, which is a best practice in JDBC. This prevents SQL injection attacks by separating SQL logic from data values through parameterized queries (? placeholders).
3. Effective Resource Management
The try-with-resources statement ensures that the Connection and PreparedStatement are automatically closed after use, even if an exception occurs. This avoids resource leaks, a common pitfall in database programming.
4. Encapsulation of Business Logic
The method encapsulates all logic for adding a vehicle into a single function. This is a clear application of the single responsibility principle and promotes code reuse.




