import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private List<Book> books;
    private List<Student> students;
    private List<Transaction> transactions;
    private List<Fine> fines;
    private static final int LOAN_PERIOD_DAYS = 14;
    private static final double FINE_PER_DAY = 1.0;

    public LibraryService() {
        this.books = DataManager.loadBooks();
        this.students = DataManager.loadStudents();
        this.transactions = DataManager.loadTransactions();
        this.fines = DataManager.loadFines();
        initializeDefaultData();
    }

    private void initializeDefaultData() {
        // Always ensure librarian exists
        boolean hasLibrarian = students.stream().anyMatch(s -> "LIB001".equals(s.getStudentId()));
        if (!hasLibrarian) {
            students.add(new Student("LIB001", "Admin Librarian", "admin@library.com", "admin123"));
        }
        
        // Always ensure sample books exist
        if (books.isEmpty()) {
            books.add(new Book("978-0134685991", "Effective Java", "Joshua Bloch", "Programming"));
            books.add(new Book("978-0596009205", "Head First Design Patterns", "Eric Freeman", "Programming"));
            books.add(new Book("978-0321356680", "Effective C++", "Scott Meyers", "Programming"));
            books.add(new Book("978-0132350884", "Clean Code", "Robert Martin", "Programming"));
            books.add(new Book("978-0201633610", "Design Patterns", "Gang of Four", "Programming"));
        }
        saveData();
    }

    // Book Management
    public void addBook(String isbn, String title, String author, String category) {
        books.add(new Book(isbn, title, author, category));
        saveData();
    }

    public void updateBook(String isbn, String title, String author, String category) {
        Book book = findBookByIsbn(isbn);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setCategory(category);
            saveData();
        }
    }

    public void deleteBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
        saveData();
    }

    public List<Book> searchBooks(String query) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                               book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                               book.getCategory().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Student Management
    public void addStudent(String studentId, String name, String email, String password) {
        students.add(new Student(studentId, name, email, password));
        saveData();
    }

    public Student authenticateStudent(String studentId, String password) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId) && s.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    // Book Issuance & Return
    public boolean issueBook(String isbn, String studentId) {
        Book book = findBookByIsbn(isbn);
        Student student = findStudentById(studentId);
        
        if (book != null && student != null && book.isAvailable()) {
            LocalDate issueDate = LocalDate.now();
            LocalDate dueDate = issueDate.plusDays(LOAN_PERIOD_DAYS);
            
            book.setAvailable(false);
            book.setIssueDate(issueDate);
            book.setDueDate(dueDate);
            book.setIssuedTo(studentId);
            
            student.addIssuedBook(isbn);
            
            String transactionId = "TXN" + System.currentTimeMillis();
            transactions.add(new Transaction(transactionId, studentId, isbn, issueDate, dueDate));
            
            saveData();
            return true;
        }
        return false;
    }

    public boolean returnBook(String isbn, String studentId) {
        Book book = findBookByIsbn(isbn);
        Student student = findStudentById(studentId);
        Transaction transaction = findActiveTransaction(isbn, studentId);
        
        if (book != null && student != null && transaction != null) {
            LocalDate returnDate = LocalDate.now();
            
            book.setAvailable(true);
            book.setIssueDate(null);
            book.setDueDate(null);
            book.setIssuedTo(null);
            
            student.returnBook(isbn);
            
            transaction.setReturnDate(returnDate);
            transaction.setStatus("RETURNED");
            
            // Calculate fine if overdue
            if (returnDate.isAfter(transaction.getDueDate())) {
                long overdueDays = ChronoUnit.DAYS.between(transaction.getDueDate(), returnDate);
                double fineAmount = overdueDays * FINE_PER_DAY;
                String fineId = "FINE" + System.currentTimeMillis();
                fines.add(new Fine(fineId, studentId, isbn, fineAmount, (int) overdueDays));
            }
            
            saveData();
            return true;
        }
        return false;
    }

    // Reports & Analytics
    public List<Book> getAvailableBooks() {
        return books.stream().filter(Book::isAvailable).collect(Collectors.toList());
    }

    public List<Book> getIssuedBooks() {
        return books.stream().filter(book -> !book.isAvailable()).collect(Collectors.toList());
    }

    public List<Transaction> getOverdueTransactions() {
        return transactions.stream()
                .filter(Transaction::isOverdue)
                .collect(Collectors.toList());
    }

    public List<Fine> getUnpaidFines() {
        return fines.stream().filter(fine -> !fine.isPaid()).collect(Collectors.toList());
    }

    public List<Transaction> getStudentHistory(String studentId) {
        return transactions.stream()
                .filter(t -> t.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    // Utility Methods
    private Book findBookByIsbn(String isbn) {
        return books.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst().orElse(null);
    }

    private Student findStudentById(String studentId) {
        return students.stream().filter(s -> s.getStudentId().equals(studentId)).findFirst().orElse(null);
    }

    private Transaction findActiveTransaction(String isbn, String studentId) {
        return transactions.stream()
                .filter(t -> t.getIsbn().equals(isbn) && t.getStudentId().equals(studentId) && "ISSUED".equals(t.getStatus()))
                .findFirst().orElse(null);
    }

    private void saveData() {
        DataManager.saveBooks(books);
        DataManager.saveStudents(students);
        DataManager.saveTransactions(transactions);
        DataManager.saveFines(fines);
    }

    public void createBackup() {
        DataManager.createBackup();
    }

    // Getters for UI
    public List<Book> getAllBooks() { return books; }
    public List<Student> getAllStudents() { return students; }
    public List<Transaction> getAllTransactions() { return transactions; }
    public List<Fine> getAllFines() { return fines; }
}