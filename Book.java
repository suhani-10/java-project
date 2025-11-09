import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private String category;
    private boolean isAvailable;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String issuedTo;

    public Book(String isbn, String title, String author, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public String getIssuedTo() { return issuedTo; }
    public void setIssuedTo(String issuedTo) { this.issuedTo = issuedTo; }

    @Override
    public String toString() {
        return String.format("ISBN: %s | Title: %s | Author: %s | Category: %s | Available: %s", 
                           isbn, title, author, category, isAvailable ? "Yes" : "No");
    }
}