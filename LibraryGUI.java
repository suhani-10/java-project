import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LibraryGUI extends JFrame {
    private LibraryService libraryService;
    private Student currentUser;
    private boolean isLibrarian;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public LibraryGUI() {
        this.libraryService = new LibraryService();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Library Book Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        createLoginPanel();
        createLibrarianPanel();
        createStudentPanel();
        
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    private void createLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(new Color(240, 248, 255));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Library Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.add(titleLabel);
        
        // Login Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField userIdField = new JTextField(15);
        userIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton librarianBtn = createStyledButton("Login as Librarian", new Color(34, 139, 34));
        JButton studentBtn = createStyledButton("Login as Student", new Color(30, 144, 255));
        JButton registerBtn = createStyledButton("Register Student", new Color(255, 140, 0));
        
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(userIdLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(userIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(librarianBtn, gbc);
        gbc.gridy = 3;
        formPanel.add(studentBtn, gbc);
        gbc.gridy = 4;
        formPanel.add(registerBtn, gbc);
        
        librarianBtn.addActionListener(e -> {
            String id = userIdField.getText().trim();
            String password = new String(passwordField.getPassword());
            Student user = libraryService.authenticateStudent(id, password);
            if (user != null && "LIB001".equals(id)) {
                currentUser = user;
                isLibrarian = true;
                cardLayout.show(mainPanel, "LIBRARIAN");
                JOptionPane.showMessageDialog(this, "Welcome Librarian!");
                userIdField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid librarian credentials!");
            }
        });
        
        studentBtn.addActionListener(e -> {
            String id = userIdField.getText().trim();
            String password = new String(passwordField.getPassword());
            currentUser = libraryService.authenticateStudent(id, password);
            if (currentUser != null && !currentUser.getStudentId().equals("LIB001")) {
                isLibrarian = false;
                cardLayout.show(mainPanel, "STUDENT");
                JOptionPane.showMessageDialog(this, "Welcome " + currentUser.getName() + "!");
                userIdField.setText("");
                passwordField.setText("");
            } else {
                currentUser = null;
                JOptionPane.showMessageDialog(this, "Invalid student credentials!");
            }
        });
        
        registerBtn.addActionListener(e -> showRegisterDialog());
        
        loginPanel.add(headerPanel, BorderLayout.NORTH);
        loginPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(loginPanel, "LOGIN");
    }

    private void createLibrarianPanel() {
        JPanel librarianPanel = new JPanel(new BorderLayout());
        librarianPanel.setBackground(new Color(245, 245, 245));
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Librarian Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        JButton logoutBtn = createStyledButton("Logout", new Color(220, 20, 60));
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN");
        });
        headerPanel.add(logoutBtn);
        
        // Menu Panel
        JPanel menuPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menuPanel.setBackground(new Color(245, 245, 245));
        
        JButton[] buttons = {
            createMenuButton("Add Book", "📚"),
            createMenuButton("Update Book", "✏️"),
            createMenuButton("Delete Book", "🗑️"),
            createMenuButton("Search Books", "🔍"),
            createMenuButton("Issue Book", "📤"),
            createMenuButton("Return Book", "📥"),
            createMenuButton("View All Books", "📖"),
            createMenuButton("View Issued Books", "📋"),
            createMenuButton("View Overdue Books", "⚠️"),
            createMenuButton("View Unpaid Fines", "💰"),
            createMenuButton("Create Backup", "💾"),
            createMenuButton("View Reports", "📊")
        };
        
        buttons[0].addActionListener(e -> showAddBookDialog());
        buttons[1].addActionListener(e -> showUpdateBookDialog());
        buttons[2].addActionListener(e -> showDeleteBookDialog());
        buttons[3].addActionListener(e -> showSearchDialog());
        buttons[4].addActionListener(e -> showIssueBookDialog());
        buttons[5].addActionListener(e -> showReturnBookDialog());
        buttons[6].addActionListener(e -> showAllBooksTable());
        buttons[7].addActionListener(e -> showIssuedBooksTable());
        buttons[8].addActionListener(e -> showOverdueBooksTable());
        buttons[9].addActionListener(e -> showUnpaidFinesTable());
        buttons[10].addActionListener(e -> {
            libraryService.createBackup();
            JOptionPane.showMessageDialog(this, "Backup created successfully!");
        });
        buttons[11].addActionListener(e -> showReportsDialog());
        
        for (JButton button : buttons) {
            menuPanel.add(button);
        }
        
        librarianPanel.add(headerPanel, BorderLayout.NORTH);
        librarianPanel.add(menuPanel, BorderLayout.CENTER);
        mainPanel.add(librarianPanel, "LIBRARIAN");
    }

    private void createStudentPanel() {
        JPanel studentPanel = new JPanel(new BorderLayout());
        studentPanel.setBackground(new Color(245, 245, 245));
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(30, 144, 255));
        JLabel titleLabel = new JLabel("Student Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        JButton logoutBtn = createStyledButton("Logout", new Color(220, 20, 60));
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN");
        });
        headerPanel.add(logoutBtn);
        
        // Menu Panel
        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        menuPanel.setBackground(new Color(245, 245, 245));
        
        JButton[] buttons = {
            createMenuButton("Search Books", "🔍"),
            createMenuButton("My Issued Books", "📚"),
            createMenuButton("My History", "📋"),
            createMenuButton("Available Books", "📖")
        };
        
        buttons[0].addActionListener(e -> showSearchDialog());
        buttons[1].addActionListener(e -> showMyIssuedBooksTable());
        buttons[2].addActionListener(e -> showMyHistoryTable());
        buttons[3].addActionListener(e -> showAvailableBooksTable());
        
        for (JButton button : buttons) {
            menuPanel.add(button);
        }
        
        studentPanel.add(headerPanel, BorderLayout.NORTH);
        studentPanel.add(menuPanel, BorderLayout.CENTER);
        mainPanel.add(studentPanel, "STUDENT");
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }

    private JButton createMenuButton(String text, String emoji) {
        JButton button = new JButton("<html><center>" + emoji + "<br>" + text + "</center></html>");
        button.setBackground(new Color(255, 255, 255));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(200, 100));
        button.setFocusPainted(false);
        return button;
    }

    private void showRegisterDialog() {
        JDialog dialog = new JDialog(this, "Register Student", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField idField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        dialog.add(idField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        dialog.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(passwordField, gbc);
        
        JButton registerBtn = createStyledButton("Register", new Color(34, 139, 34));
        registerBtn.addActionListener(e -> {
            libraryService.addStudent(idField.getText(), nameField.getText(), 
                                    emailField.getText(), new String(passwordField.getPassword()));
            JOptionPane.showMessageDialog(dialog, "Student registered successfully!");
            dialog.dispose();
        });
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        dialog.add(registerBtn, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showAddBookDialog() {
        JDialog dialog = new JDialog(this, "Add Book", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField isbnField = new JTextField(15);
        JTextField titleField = new JTextField(15);
        JTextField authorField = new JTextField(15);
        JTextField categoryField = new JTextField(15);
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        dialog.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        dialog.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        dialog.add(authorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        dialog.add(categoryField, gbc);
        
        JButton addBtn = createStyledButton("Add Book", new Color(34, 139, 34));
        addBtn.addActionListener(e -> {
            libraryService.addBook(isbnField.getText(), titleField.getText(), 
                                 authorField.getText(), categoryField.getText());
            JOptionPane.showMessageDialog(dialog, "Book added successfully!");
            dialog.dispose();
        });
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        dialog.add(addBtn, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showUpdateBookDialog() {
        String isbn = JOptionPane.showInputDialog(this, "Enter ISBN to update:");
        if (isbn != null && !isbn.trim().isEmpty()) {
            showAddBookDialog(); // Reuse add dialog for update
        }
    }

    private void showDeleteBookDialog() {
        String isbn = JOptionPane.showInputDialog(this, "Enter ISBN to delete:");
        if (isbn != null && !isbn.trim().isEmpty()) {
            libraryService.deleteBook(isbn);
            JOptionPane.showMessageDialog(this, "Book deleted successfully!");
        }
    }

    private void showIssueBookDialog() {
        JDialog dialog = new JDialog(this, "Issue Book", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField isbnField = new JTextField(15);
        JTextField studentIdField = new JTextField(15);
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        dialog.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        dialog.add(studentIdField, gbc);
        
        JButton issueBtn = createStyledButton("Issue Book", new Color(30, 144, 255));
        issueBtn.addActionListener(e -> {
            if (libraryService.issueBook(isbnField.getText(), studentIdField.getText())) {
                JOptionPane.showMessageDialog(dialog, "Book issued successfully!");
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to issue book!");
            }
            dialog.dispose();
        });
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        dialog.add(issueBtn, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showReturnBookDialog() {
        JDialog dialog = new JDialog(this, "Return Book", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField isbnField = new JTextField(15);
        JTextField studentIdField = new JTextField(15);
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        dialog.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        dialog.add(studentIdField, gbc);
        
        JButton returnBtn = createStyledButton("Return Book", new Color(255, 140, 0));
        returnBtn.addActionListener(e -> {
            if (libraryService.returnBook(isbnField.getText(), studentIdField.getText())) {
                JOptionPane.showMessageDialog(dialog, "Book returned successfully!");
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to return book!");
            }
            dialog.dispose();
        });
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        dialog.add(returnBtn, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showSearchDialog() {
        String query = JOptionPane.showInputDialog(this, "Enter search query (title/author/category):");
        if (query != null && !query.trim().isEmpty()) {
            List<Book> results = libraryService.searchBooks(query);
            showBooksTable(results, "Search Results");
        }
    }

    private void showAllBooksTable() {
        showBooksTable(libraryService.getAllBooks(), "All Books");
    }

    private void showIssuedBooksTable() {
        showBooksTable(libraryService.getIssuedBooks(), "Issued Books");
    }

    private void showAvailableBooksTable() {
        showBooksTable(libraryService.getAvailableBooks(), "Available Books");
    }

    private void showMyIssuedBooksTable() {
        List<String> issuedBooks = currentUser.getIssuedBooks();
        List<Book> books = libraryService.getAllBooks();
        books.removeIf(book -> !issuedBooks.contains(book.getIsbn()));
        showBooksTable(books, "My Issued Books");
    }

    private void showBooksTable(List<Book> books, String title) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(800, 400);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"ISBN", "Title", "Author", "Category", "Available"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Book book : books) {
            model.addRow(new Object[]{
                book.getIsbn(), book.getTitle(), book.getAuthor(), 
                book.getCategory(), book.isAvailable() ? "Yes" : "No"
            });
        }
        
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(table);
        
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void showOverdueBooksTable() {
        JDialog dialog = new JDialog(this, "Overdue Books", true);
        dialog.setSize(800, 400);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"Transaction ID", "Student ID", "ISBN", "Due Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Transaction transaction : libraryService.getOverdueTransactions()) {
            model.addRow(new Object[]{
                transaction.getTransactionId(), transaction.getStudentId(), 
                transaction.getIsbn(), transaction.getDueDate(), transaction.getStatus()
            });
        }
        
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void showUnpaidFinesTable() {
        JDialog dialog = new JDialog(this, "Unpaid Fines", true);
        dialog.setSize(800, 400);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"Fine ID", "Student ID", "ISBN", "Amount", "Overdue Days"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Fine fine : libraryService.getUnpaidFines()) {
            model.addRow(new Object[]{
                fine.getFineId(), fine.getStudentId(), fine.getIsbn(), 
                "$" + fine.getAmount(), fine.getOverdueDays()
            });
        }
        
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void showMyHistoryTable() {
        JDialog dialog = new JDialog(this, "My Transaction History", true);
        dialog.setSize(800, 400);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"Transaction ID", "ISBN", "Issue Date", "Due Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Transaction transaction : libraryService.getStudentHistory(currentUser.getStudentId())) {
            model.addRow(new Object[]{
                transaction.getTransactionId(), transaction.getIsbn(), 
                transaction.getIssueDate(), transaction.getDueDate(), transaction.getStatus()
            });
        }
        
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void showReportsDialog() {
        String report = String.format(
            "Library Statistics:\n\n" +
            "Total Books: %d\n" +
            "Available Books: %d\n" +
            "Issued Books: %d\n" +
            "Overdue Books: %d\n" +
            "Total Students: %d\n" +
            "Unpaid Fines: %d",
            libraryService.getAllBooks().size(),
            libraryService.getAvailableBooks().size(),
            libraryService.getIssuedBooks().size(),
            libraryService.getOverdueTransactions().size(),
            libraryService.getAllStudents().size(),
            libraryService.getUnpaidFines().size()
        );
        
        JOptionPane.showMessageDialog(this, report, "Library Reports", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryGUI().setVisible(true);
        });
    }
}