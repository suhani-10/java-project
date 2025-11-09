import java.util.*;

public class LibraryManagementSystem {
    private LibraryService libraryService;
    private Scanner scanner;
    private Student currentUser;
    private boolean isLibrarian;

    public LibraryManagementSystem() {
        this.libraryService = new LibraryService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Library Book Management System ===");
        
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else if (isLibrarian) {
                showLibrarianMenu();
            } else {
                showStudentMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n1. Login as Librarian");
        System.out.println("2. Login as Student");
        System.out.println("3. Register New Student");
        System.out.println("4. Exit");
        System.out.print("Choose option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: loginAsLibrarian(); break;
            case 2: loginAsStudent(); break;
            case 3: registerStudent(); break;
            case 4: System.exit(0); break;
            default: System.out.println("Invalid option!");
        }
    }

    private void loginAsLibrarian() {
        System.out.print("Enter Librarian ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        if ("LIB001".equals(id) && "admin123".equals(password)) {
            currentUser = libraryService.authenticateStudent(id, password);
            isLibrarian = true;
            System.out.println("Librarian login successful!");
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private void loginAsStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        currentUser = libraryService.authenticateStudent(id, password);
        if (currentUser != null && !currentUser.getStudentId().equals("LIB001")) {
            isLibrarian = false;
            System.out.println("Student login successful!");
        } else {
            currentUser = null;
            System.out.println("Invalid credentials!");
        }
    }

    private void registerStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        libraryService.addStudent(id, name, email, password);
        System.out.println("Student registered successfully!");
    }

    private void showLibrarianMenu() {
        System.out.println("\n=== Librarian Dashboard ===");
        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Delete Book");
        System.out.println("4. Search Books");
        System.out.println("5. Issue Book");
        System.out.println("6. Return Book");
        System.out.println("7. View All Books");
        System.out.println("8. View Issued Books");
        System.out.println("9. View Overdue Books");
        System.out.println("10. View Unpaid Fines");
        System.out.println("11. Create Backup");
        System.out.println("12. Logout");
        System.out.print("Choose option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: addBook(); break;
            case 2: updateBook(); break;
            case 3: deleteBook(); break;
            case 4: searchBooks(); break;
            case 5: issueBook(); break;
            case 6: returnBook(); break;
            case 7: viewAllBooks(); break;
            case 8: viewIssuedBooks(); break;
            case 9: viewOverdueBooks(); break;
            case 10: viewUnpaidFines(); break;
            case 11: libraryService.createBackup(); break;
            case 12: logout(); break;
            default: System.out.println("Invalid option!");
        }
    }

    private void showStudentMenu() {
        System.out.println("\n=== Student Dashboard ===");
        System.out.println("1. Search Books");
        System.out.println("2. View My Issued Books");
        System.out.println("3. View My History");
        System.out.println("4. View Available Books");
        System.out.println("5. Logout");
        System.out.print("Choose option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: searchBooks(); break;
            case 2: viewMyIssuedBooks(); break;
            case 3: viewMyHistory(); break;
            case 4: viewAvailableBooks(); break;
            case 5: logout(); break;
            default: System.out.println("Invalid option!");
        }
    }

    private void addBook() {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        
        libraryService.addBook(isbn, title, author, category);
        System.out.println("Book added successfully!");
    }

    private void updateBook() {
        System.out.print("Enter ISBN to update: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter new Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new Category: ");
        String category = scanner.nextLine();
        
        libraryService.updateBook(isbn, title, author, category);
        System.out.println("Book updated successfully!");
    }

    private void deleteBook() {
        System.out.print("Enter ISBN to delete: ");
        String isbn = scanner.nextLine();
        libraryService.deleteBook(isbn);
        System.out.println("Book deleted successfully!");
    }

    private void searchBooks() {
        System.out.print("Enter search query (title/author/category): ");
        String query = scanner.nextLine();
        List<Book> results = libraryService.searchBooks(query);
        
        if (results.isEmpty()) {
            System.out.println("No books found!");
        } else {
            System.out.println("\nSearch Results:");
            results.forEach(System.out::println);
        }
    }

    private void issueBook() {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        
        if (libraryService.issueBook(isbn, studentId)) {
            System.out.println("Book issued successfully!");
        } else {
            System.out.println("Failed to issue book. Check availability and student ID.");
        }
    }

    private void returnBook() {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        
        if (libraryService.returnBook(isbn, studentId)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Failed to return book. Check ISBN and student ID.");
        }
    }

    private void viewAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in library!");
        } else {
            System.out.println("\nAll Books:");
            books.forEach(System.out::println);
        }
    }

    private void viewIssuedBooks() {
        List<Book> issuedBooks = libraryService.getIssuedBooks();
        if (issuedBooks.isEmpty()) {
            System.out.println("No books currently issued!");
        } else {
            System.out.println("\nIssued Books:");
            issuedBooks.forEach(System.out::println);
        }
    }

    private void viewAvailableBooks() {
        List<Book> availableBooks = libraryService.getAvailableBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("No books available!");
        } else {
            System.out.println("\nAvailable Books:");
            availableBooks.forEach(System.out::println);
        }
    }

    private void viewOverdueBooks() {
        List<Transaction> overdueTransactions = libraryService.getOverdueTransactions();
        if (overdueTransactions.isEmpty()) {
            System.out.println("No overdue books!");
        } else {
            System.out.println("\nOverdue Books:");
            overdueTransactions.forEach(System.out::println);
        }
    }

    private void viewUnpaidFines() {
        List<Fine> unpaidFines = libraryService.getUnpaidFines();
        if (unpaidFines.isEmpty()) {
            System.out.println("No unpaid fines!");
        } else {
            System.out.println("\nUnpaid Fines:");
            unpaidFines.forEach(System.out::println);
        }
    }

    private void viewMyIssuedBooks() {
        List<String> issuedBooks = currentUser.getIssuedBooks();
        if (issuedBooks.isEmpty()) {
            System.out.println("You have no issued books!");
        } else {
            System.out.println("\nYour Issued Books:");
            for (String isbn : issuedBooks) {
                libraryService.getAllBooks().stream()
                    .filter(book -> book.getIsbn().equals(isbn))
                    .forEach(System.out::println);
            }
        }
    }

    private void viewMyHistory() {
        List<Transaction> history = libraryService.getStudentHistory(currentUser.getStudentId());
        if (history.isEmpty()) {
            System.out.println("No transaction history!");
        } else {
            System.out.println("\nYour Transaction History:");
            history.forEach(System.out::println);
        }
    }

    private void logout() {
        currentUser = null;
        isLibrarian = false;
        System.out.println("Logged out successfully!");
    }

    public static void main(String[] args) {
        new LibraryManagementSystem().start();
    }
}