import java.io.*;
import java.util.*;

public class DataManager {
    private static final String BOOKS_FILE = "books.dat";
    private static final String STUDENTS_FILE = "students.dat";
    private static final String TRANSACTIONS_FILE = "transactions.dat";
    private static final String FINES_FILE = "fines.dat";
    private static final String BACKUP_DIR = "backup/";

    @SuppressWarnings("unchecked")
    public static List<Book> loadBooks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKS_FILE))) {
            return (List<Book>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveBooks(List<Book> books) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKS_FILE))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Student> loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENTS_FILE))) {
            return (List<Student>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveStudents(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENTS_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Transaction> loadTransactions() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TRANSACTIONS_FILE))) {
            return (List<Transaction>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveTransactions(List<Transaction> transactions) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TRANSACTIONS_FILE))) {
            oos.writeObject(transactions);
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Fine> loadFines() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FINES_FILE))) {
            return (List<Fine>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveFines(List<Fine> fines) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FINES_FILE))) {
            oos.writeObject(fines);
        } catch (IOException e) {
            System.err.println("Error saving fines: " + e.getMessage());
        }
    }

    public static void createBackup() {
        try {
            new File(BACKUP_DIR).mkdirs();
            copyFile(BOOKS_FILE, BACKUP_DIR + "books_backup.dat");
            copyFile(STUDENTS_FILE, BACKUP_DIR + "students_backup.dat");
            copyFile(TRANSACTIONS_FILE, BACKUP_DIR + "transactions_backup.dat");
            copyFile(FINES_FILE, BACKUP_DIR + "fines_backup.dat");
            System.out.println("Backup created successfully!");
        } catch (Exception e) {
            System.err.println("Backup failed: " + e.getMessage());
        }
    }

    private static void copyFile(String source, String dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }
    }
}