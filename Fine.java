import java.io.Serializable;
import java.time.LocalDate;

public class Fine implements Serializable {
    private String fineId;
    private String studentId;
    private String isbn;
    private double amount;
    private LocalDate fineDate;
    private boolean isPaid;
    private int overdueDays;

    public Fine(String fineId, String studentId, String isbn, double amount, int overdueDays) {
        this.fineId = fineId;
        this.studentId = studentId;
        this.isbn = isbn;
        this.amount = amount;
        this.overdueDays = overdueDays;
        this.fineDate = LocalDate.now();
        this.isPaid = false;
    }

    // Getters and Setters
    public String getFineId() { return fineId; }
    public void setFineId(String fineId) { this.fineId = fineId; }
    
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public LocalDate getFineDate() { return fineDate; }
    public void setFineDate(LocalDate fineDate) { this.fineDate = fineDate; }
    
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }
    
    public int getOverdueDays() { return overdueDays; }
    public void setOverdueDays(int overdueDays) { this.overdueDays = overdueDays; }

    @Override
    public String toString() {
        return String.format("Fine ID: %s | Student: %s | Amount: $%.2f | Days Overdue: %d | Paid: %s", 
                           fineId, studentId, amount, overdueDays, isPaid ? "Yes" : "No");
    }
}