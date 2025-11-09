import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {
    private String studentId;
    private String name;
    private String email;
    private String password;
    private List<String> issuedBooks;
    private List<String> bookHistory;

    public Student(String studentId, String name, String email, String password) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.issuedBooks = new ArrayList<>();
        this.bookHistory = new ArrayList<>();
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public List<String> getIssuedBooks() { return issuedBooks; }
    public void setIssuedBooks(List<String> issuedBooks) { this.issuedBooks = issuedBooks; }
    
    public List<String> getBookHistory() { return bookHistory; }
    public void setBookHistory(List<String> bookHistory) { this.bookHistory = bookHistory; }

    public void addIssuedBook(String isbn) {
        issuedBooks.add(isbn);
        bookHistory.add(isbn);
    }

    public void returnBook(String isbn) {
        issuedBooks.remove(isbn);
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Email: %s | Books Issued: %d", 
                           studentId, name, email, issuedBooks.size());
    }
}