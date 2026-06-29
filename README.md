# InventarisGudang

Professional Java Swing Desktop Application for Warehouse/Inventory Management System.

## Project Overview

InventarisGudang is a modern desktop application built with Java Swing for managing warehouse inventory, product categories, and transactions. The application follows the MVC (Model-View-Controller) architecture pattern and uses FlatLaf for a contemporary user interface.

## Technology Stack

- **Java 24** - Latest Java version
- **Maven** - Build and dependency management
- **Swing** - Desktop GUI framework
- **FlatLaf** - Modern look and feel
- **MigLayout** - Advanced layout manager
- **MySQL** - Relational database
- **JDBC** - Database connectivity
- **SLF4J/Logback** - Logging framework

## Project Structure

```
InventarisGudang/
├── src/main/java/com/inventarisgudang/
│   ├── config/              # Configuration classes
│   ├── dao/                 # Data Access Objects
│   ├── model/               # Entity/Model classes
│   ├── service/             # Business logic services
│   ├── utils/               # Utility classes
│   ├── components/          # Custom Swing components
│   ├── view/                # UI views
│   │   ├── login/           # Login view
│   │   ├── dashboard/       # Dashboard view
│   │   ├── barang/          # Product management view
│   │   ├── kategori/        # Category management view
│   │   └── transaksi/       # Transaction view
│   └── Main.java            # Application entry point
├── src/main/resources/      # Resource files
├── pom.xml                  # Maven configuration
├── README.md                # This file
└── .gitignore               # Git ignore rules
```

## Packages Description

### config
Configuration and database connection management
- `DatabaseConnection.java` - Database connection pool and configuration

### dao (Data Access Objects)
Database operations for each entity
- `BarangDAO.java` - Product operations
- `KategoriDAO.java` - Category operations
- `TransaksiDAO.java` - Transaction operations

### model
Entity classes representing database tables
- `Barang.java` - Product model
- `Kategori.java` - Category model
- `Transaksi.java` - Transaction model
- `User.java` - User model

### service
Business logic and service layer
- Service classes for business operations

### utils
Utility and helper classes
- `ColorPalette.java` - Application color scheme
- `FontUtil.java` - Font management utilities
- `Validator.java` - Input validation utilities
- `IconUtil.java` - Icon and image utilities

### components
Custom Swing components
- Reusable UI components

### view
User interface views
- `login/` - Login screen
- `dashboard/` - Main dashboard
- `barang/` - Product management interface
- `kategori/` - Category management interface
- `transaksi/` - Transaction management interface

## Prerequisites

- Java 24 or higher
- Maven 3.9.0 or higher
- MySQL 8.0 or higher

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd InventarisGudang
```

### 2. Create MySQL Database
```sql
CREATE DATABASE inventaris_gudang;
USE inventaris_gudang;

-- Create tables here
```

### 3. Database Configuration
Update database connection properties in the appropriate configuration file.

### 4. Build the Project
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn exec:java -Dexec.mainClass="com.inventarisgudang.Main"
```

Or run the generated JAR:
```bash
java -jar target/InventarisGudang.jar
```

## Architecture

The application follows the **MVC (Model-View-Controller)** pattern:

- **Model**: Entity classes representing database data
- **View**: Swing UI components and views
- **Controller**: Service layer handling business logic
- **DAO**: Data persistence layer for database operations

## Database Schema

The application uses the following main tables:
- `barang` - Products/Items
- `kategori` - Product categories
- `transaksi` - Transactions/Inventory movements
- `users` - User accounts (for future authentication)

## Dependencies

- **FlatLaf** - Modern Swing look and feel
- **MigLayout** - Professional layout management
- **MySQL Connector/J** - MySQL database driver
- **SLF4J/Logback** - Logging framework

## Features (To Be Implemented)

- [ ] User authentication and login
- [ ] Product management (CRUD operations)
- [ ] Category management
- [ ] Transaction tracking
- [ ] Inventory reports
- [ ] User management
- [ ] Data backup and restoration

## Contributing

Guidelines for contributing to this project will be added here.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

Created as a professional Java desktop application template.

## Support

For issues and feature requests, please create an issue in the repository.
