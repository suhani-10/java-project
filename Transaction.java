import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable {
    private String transactionId;
    private String studentId;
    private String isbn;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status; // ISSUED, RETURNED, OVERDUE

    public Transaction(String transactionId, String studentId, String isbn, LocalDate issueDate, LocalDate dueDate) {
        this.transactionId = transactionId;
        this.studentId = studentId;
        this.isbn = isbn;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = "ISSUED";
    }

    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate) && "ISSUED".equals(status);
    }

    @Override
    public String toString() {
        return String.format("Transaction ID: %s | Student: %s | Book: %s | Status: %s | Due: %s", 
                           transactionId, studentId, isbn, status, dueDate);
    }
}