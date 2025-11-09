# Library Book Management System

A comprehensive Java-based Library Management System that automates book tracking, student management, and library operations.

## Features

### Core Functionality
- **Book Catalog Management**: Add, update, delete, and search books
- **Student Management**: Registration and authentication system
- **Book Issuance & Return**: Track book loans with automatic due date calculation
- **Overdue Management**: Automatic fine calculation for late returns
- **Role-based Access**: Separate interfaces for librarians and students
- **Data Persistence**: File-based storage with backup functionality

### Key Capabilities
- Search books by title, author, or category
- Track student borrowing history
- Generate reports on available, issued, and overdue books
- Automatic fine calculation ($1 per day overdue)
- 14-day loan period for all books
- Secure data backup and restore

## System Architecture

### Models
- `Book.java` - Book entity with availability tracking
- `Student.java` - Student entity with authentication
- `Transaction.java` - Book issuance/return tracking
- `Fine.java` - Overdue penalty management

### Services
- `LibraryService.java` - Core business logic
- `DataManager.java` - File-based data persistence

### Application
- `LibraryManagementSystem.java` - Main console interface

## Usage

### Default Credentials
- **Librarian**: ID: `LIB001`, Password: `admin123`

### Running the Application
```bash
javac *.java
java LibraryManagementSystem
```

### Librarian Functions
1. Add/Update/Delete books
2. Issue and return books
3. View all reports (available, issued, overdue books)
4. Manage student accounts
5. Create data backups

### Student Functions
1. Search available books
2. View personal issued books
3. Check borrowing history
4. View available books in library

## Data Storage
- Books: `books.dat`
- Students: `students.dat`
- Transactions: `transactions.dat`
- Fines: `fines.dat`
- Backups: `backup/` directory

## Technical Details
- **Language**: Java
- **Storage**: Serialized object files
- **Architecture**: Service-oriented with data persistence layer
- **Interface**: Console-based menu system
- **Security**: Password-based authentication with role separation

## System Requirements
- Java 8 or higher
- File system write permissions for data storage
- Console/terminal for user interface

This system provides a complete solution for library management with scalability for multiple concurrent users and robust data handling.