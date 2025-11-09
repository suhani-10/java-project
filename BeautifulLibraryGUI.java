import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BeautifulLibraryGUI extends JFrame {
    private LibraryService libraryService;
    private Student currentUser;
    private boolean isLibrarian;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public BeautifulLibraryGUI() {
        this.libraryService = new LibraryService();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("📚 Beautiful Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
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
        JPanel loginPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 250), 0, getHeight(), new Color(255, 182, 193));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Beautiful Header
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(72, 61, 139), 0, getHeight(), new Color(123, 104, 238));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        JLabel titleLabel = new JLabel("📚 Beautiful Library System 📖", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        headerPanel.add(titleLabel);
        
        // Login Form with beautiful styling
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel userIdLabel = new JLabel("👤 User ID:");
        userIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userIdLabel.setForeground(new Color(25, 25, 112));
        JTextField userIdField = createStyledTextField();
        
        JLabel passwordLabel = new JLabel("🔐 Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordLabel.setForeground(new Color(25, 25, 112));
        JPasswordField passwordField = createStyledPasswordField();
        
        JButton librarianBtn = createGradientButton("👨‍💼 Login as Librarian", new Color(34, 139, 34), new Color(50, 205, 50));
        JButton studentBtn = createGradientButton("🎓 Login as Student", new Color(30, 144, 255), new Color(135, 206, 250));
        JButton registerBtn = createGradientButton("📝 Register Student", new Color(255, 140, 0), new Color(255, 165, 0));
        
        gbc.insets = new Insets(15, 15, 15, 15);
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
                showSuccessMessage("🎉 Welcome Librarian!");
                userIdField.setText("");
                passwordField.setText("");
            } else {
                showErrorMessage("❌ Invalid librarian credentials!");
            }
        });
        
        studentBtn.addActionListener(e -> {
            String id = userIdField.getText().trim();
            String password = new String(passwordField.getPassword());
            currentUser = libraryService.authenticateStudent(id, password);
            if (currentUser != null && !currentUser.getStudentId().equals("LIB001")) {
                isLibrarian = false;
                cardLayout.show(mainPanel, "STUDENT");
                showSuccessMessage("🎉 Welcome " + currentUser.getName() + "!");
                userIdField.setText("");
                passwordField.setText("");
            } else {
                currentUser = null;
                showErrorMessage("❌ Invalid student credentials!");
            }
        });
        
        registerBtn.addActionListener(e -> showRegisterDialog());
        
        loginPanel.add(headerPanel, BorderLayout.NORTH);
        loginPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(loginPanel, "LOGIN");
    }

    private void createLibrarianPanel() {
        JPanel librarianPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(), new Color(230, 230, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Beautiful Header
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(72, 61, 139), 0, getHeight(), new Color(123, 104, 238));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        JLabel titleLabel = new JLabel("👨‍💼 Librarian Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JButton logoutBtn = createGradientButton("🚪 Logout", new Color(220, 20, 60), new Color(255, 69, 0));
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN");
        });
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(logoutBtn, BorderLayout.EAST);
        
        // Beautiful Menu Panel
        JPanel menuPanel = new JPanel(new GridLayout(4, 3, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        menuPanel.setOpaque(false);
        
        JButton[] buttons = {
            createBeautifulMenuButton("📚 Add Book", new Color(46, 125, 50), new Color(76, 175, 80)),
            createBeautifulMenuButton("✏️ Update Book", new Color(251, 140, 0), new Color(255, 193, 7)),
            createBeautifulMenuButton("🗑️ Delete Book", new Color(211, 47, 47), new Color(244, 67, 54)),
            createBeautifulMenuButton("🔍 Search Books", new Color(57, 73, 171), new Color(63, 81, 181)),
            createBeautifulMenuButton("📤 Issue Book", new Color(0, 121, 107), new Color(0, 150, 136)),
            createBeautifulMenuButton("📥 Return Book", new Color(123, 31, 162), new Color(156, 39, 176)),
            createBeautifulMenuButton("📖 All Books", new Color(3, 155, 229), new Color(33, 150, 243)),
            createBeautifulMenuButton("📋 Issued Books", new Color(255, 111, 97), new Color(255, 138, 101)),
            createBeautifulMenuButton("⚠️ Overdue Books", new Color(255, 87, 34), new Color(255, 112, 67)),
            createBeautifulMenuButton("💰 Unpaid Fines", new Color(139, 69, 19), new Color(205, 133, 63)),
            createBeautifulMenuButton("💾 Create Backup", new Color(105, 105, 105), new Color(128, 128, 128)),
            createBeautifulMenuButton("📊 View Reports", new Color(72, 61, 139), new Color(123, 104, 238))
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
            showSuccessMessage("💾 Backup created successfully!");
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
        JPanel studentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(), new Color(230, 230, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Beautiful Header
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 144, 255), 0, getHeight(), new Color(135, 206, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        JLabel titleLabel = new JLabel("🎓 Student Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JButton logoutBtn = createGradientButton("🚪 Logout", new Color(220, 20, 60), new Color(255, 69, 0));
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN");
        });
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(logoutBtn, BorderLayout.EAST);
        
        // Beautiful Menu Panel
        JPanel menuPanel = new JPanel(new GridLayout(2, 3, 25, 25));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        menuPanel.setOpaque(false);
        
        JButton[] buttons = {
            createBeautifulMenuButton("🔍 Search Books", new Color(57, 73, 171), new Color(63, 81, 181)),
            createBeautifulMenuButton("📚 My Books", new Color(46, 125, 50), new Color(76, 175, 80)),
            createBeautifulMenuButton("📋 My History", new Color(251, 140, 0), new Color(255, 193, 7)),
            createBeautifulMenuButton("📖 Available Books", new Color(3, 155, 229), new Color(33, 150, 243)),
            createBeautifulMenuButton("📤 Issue Book", new Color(0, 121, 107), new Color(0, 150, 136))
        };
        
        buttons[0].addActionListener(e -> showSearchDialog());
        buttons[1].addActionListener(e -> showMyIssuedBooksTable());
        buttons[2].addActionListener(e -> showMyHistoryTable());
        buttons[3].addActionListener(e -> showAvailableBooksTable());
        buttons[4].addActionListener(e -> showStudentIssueBookDialog());
        
        for (JButton button : buttons) {
            menuPanel.add(button);
        }
        
        studentPanel.add(headerPanel, BorderLayout.NORTH);
        studentPanel.add(menuPanel, BorderLayout.CENTER);
        mainPanel.add(studentPanel, "STUDENT");
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }

    private JButton createGradientButton(String text, Color color1, Color color2) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(200, 45));
        return button;
    }

    private JButton createBeautifulMenuButton(String text, Color color1, Color color2) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(250, 120));
        return button;
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showRegisterDialog() {
        JDialog dialog = createStyledDialog("📝 Register New Student");
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField idField = createStyledTextField();
        JTextField nameField = createStyledTextField();
        JTextField emailField = createStyledTextField();
        JPasswordField passwordField = createStyledPasswordField();
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(createStyledLabel("👤 Student ID:"), gbc);
        gbc.gridx = 1;
        dialog.add(idField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(createStyledLabel("👨‍🎓 Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(createStyledLabel("📧 Email:"), gbc);
        gbc.gridx = 1;
        dialog.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(createStyledLabel("🔐 Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(passwordField, gbc);
        
        JButton registerBtn = createGradientButton("✅ Register", new Color(34, 139, 34), new Color(50, 205, 50));
        registerBtn.addActionListener(e -> {
            libraryService.addStudent(idField.getText(), nameField.getText(), 
                                    emailField.getText(), new String(passwordField.getPassword()));
            showSuccessMessage("🎉 Student registered successfully!");
            dialog.dispose();
        });
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        dialog.add(registerBtn, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showAddBookDialog() {
        JDialog dialog = createStyledDialog("📚 Add New Book");
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField isbnField = createStyledTextField();
        JTextField titleField = createStyledTextField();
        JTextField authorField = createStyledTextField();
        JTextField categoryField = createStyledTextField();
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(createStyledLabel("📖 ISBN:"), gbc);
        gbc.gridx = 1;
        dialog.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(createStyledLabel("📚 Title:"), gbc);
        gbc.gridx = 1;
        dialog.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(createStyledLabel("✍️ Author:"), gbc);
        gbc.gridx = 1;
        dialog.add(authorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(createStyledLabel("📂 Category:"), gbc);
        gbc.gridx = 1;
        dialog.add(categoryField, gbc);
        
        JButton addBtn = createGradientButton("➕ Add Book", new Color(34, 139, 34), new Color(50, 205, 50));
        addBtn.addActionListener(e -> {
            libraryService.addBook(isbnField.getText(), titleField.getText(), 
                                 authorField.getText(), categoryField.getText());
            showSuccessMessage("📚 Book added successfully!");
            dialog.dispose();
        });
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        dialog.add(addBtn, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showUpdateBookDialog() {
        String isbn = JOptionPane.showInputDialog(this, "📖 Enter ISBN to update:");
        if (isbn != null && !isbn.trim().isEmpty()) {
            showAddBookDialog();
        }
    }

    private void showDeleteBookDialog() {
        String isbn = JOptionPane.showInputDialog(this, "🗑️ Enter ISBN to delete:");
        if (isbn != null && !isbn.trim().isEmpty()) {
            libraryService.deleteBook(isbn);
            showSuccessMessage("🗑️ Book deleted successfully!");
        }
    }

    private void showIssueBookDialog() {
        JDialog dialog = createStyledDialog("📤 Issue Book");
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField isbnField = createStyledTextField();
        JTextField studentIdField = createStyledTextField();
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(createStyledLabel("📖 ISBN:"), gbc);
        gbc.gridx = 1;
        dialog.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(createStyledLabel("👤 Student ID:"), gbc);
        gbc.gridx = 1;
        dialog.add(studentIdField, gbc);
        
        JButton issueBtn = createGradientButton("📤 Issue Book", new Color(30, 144, 255), new Color(135, 206, 250));
        issueBtn.addActionListener(e -> {
            if (libraryService.issueBook(isbnField.getText(), studentIdField.getText())) {
                showSuccessMessage("📤 Book issued successfully!");
            } else {
                showErrorMessage("❌ Failed to issue book!");
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
        JDialog dialog = createStyledDialog("📥 Return Book");
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField isbnField = createStyledTextField();
        JTextField studentIdField = createStyledTextField();
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(createStyledLabel("📖 ISBN:"), gbc);
        gbc.gridx = 1;
        dialog.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(createStyledLabel("👤 Student ID:"), gbc);
        gbc.gridx = 1;
        dialog.add(studentIdField, gbc);
        
        JButton returnBtn = createGradientButton("📥 Return Book", new Color(255, 140, 0), new Color(255, 165, 0));
        returnBtn.addActionListener(e -> {
            if (libraryService.returnBook(isbnField.getText(), studentIdField.getText())) {
                showSuccessMessage("📥 Book returned successfully!");
            } else {
                showErrorMessage("❌ Failed to return book!");
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
        String query = JOptionPane.showInputDialog(this, "🔍 Enter search query (title/author/category):");
        if (query != null && !query.trim().isEmpty()) {
            List<Book> results = libraryService.searchBooks(query);
            showBooksTable(results, "🔍 Search Results");
        }
    }

    private void showAllBooksTable() {
        showBooksTable(libraryService.getAllBooks(), "📖 All Books");
    }

    private void showIssuedBooksTable() {
        showBooksTable(libraryService.getIssuedBooks(), "📋 Issued Books");
    }

    private void showAvailableBooksTable() {
        showBooksTable(libraryService.getAvailableBooks(), "📚 Available Books");
    }

    private void showMyIssuedBooksTable() {
        List<String> issuedBooks = currentUser.getIssuedBooks();
        List<Book> books = libraryService.getAllBooks();
        books.removeIf(book -> !issuedBooks.contains(book.getIsbn()));
        showBooksTable(books, "📚 My Issued Books");
    }

    private JDialog createStyledDialog(String title) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.getContentPane().setBackground(new Color(245, 250, 255));
        return dialog;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(25, 25, 112));
        return label;
    }

    private void showBooksTable(List<Book> books, String title) {
        JDialog dialog = createStyledDialog(title);
        dialog.setSize(900, 500);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"📖 ISBN", "📚 Title", "✍️ Author", "📂 Category", "✅ Available"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Book book : books) {
            model.addRow(new Object[]{
                book.getIsbn(), book.getTitle(), book.getAuthor(), 
                book.getCategory(), book.isAvailable() ? "✅ Yes" : "❌ No"
            });
        }
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(123, 104, 238));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void showOverdueBooksTable() {
        JDialog dialog = createStyledDialog("⚠️ Overdue Books");
        dialog.setSize(900, 500);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"🆔 Transaction ID", "👤 Student ID", "📖 ISBN", "📅 Due Date", "⚠️ Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Transaction transaction : libraryService.getOverdueTransactions()) {
            model.addRow(new Object[]{
                transaction.getTransactionId(), transaction.getStudentId(), 
                transaction.getIsbn(), transaction.getDueDate(), "⚠️ " + transaction.getStatus()
            });
        }
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(255, 87, 34));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void showUnpaidFinesTable() {
        JDialog dialog = createStyledDialog("💰 Unpaid Fines");
        dialog.setSize(900, 500);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"🆔 Fine ID", "👤 Student ID", "📖 ISBN", "💰 Amount", "📅 Overdue Days"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Fine fine : libraryService.getUnpaidFines()) {
            model.addRow(new Object[]{
                fine.getFineId(), fine.getStudentId(), fine.getIsbn(), 
                "💰 $" + fine.getAmount(), "📅 " + fine.getOverdueDays()
            });
        }
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(139, 69, 19));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void showMyHistoryTable() {
        JDialog dialog = createStyledDialog("📋 My Transaction History");
        dialog.setSize(900, 500);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"🆔 Transaction ID", "📖 ISBN", "📅 Issue Date", "📅 Due Date", "✅ Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Transaction transaction : libraryService.getStudentHistory(currentUser.getStudentId())) {
            model.addRow(new Object[]{
                transaction.getTransactionId(), transaction.getIsbn(), 
                transaction.getIssueDate(), transaction.getDueDate(), 
                "RETURNED".equals(transaction.getStatus()) ? "✅ " + transaction.getStatus() : "📤 " + transaction.getStatus()
            });
        }
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(30, 144, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void showStudentIssueBookDialog() {
        JDialog dialog = createStyledDialog("📤 Issue Book");
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField isbnField = createStyledTextField();
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(createStyledLabel("📖 ISBN:"), gbc);
        gbc.gridx = 1;
        dialog.add(isbnField, gbc);
        
        JButton issueBtn = createGradientButton("📤 Issue Book", new Color(0, 121, 107), new Color(0, 150, 136));
        issueBtn.addActionListener(e -> {
            if (libraryService.issueBook(isbnField.getText(), currentUser.getStudentId())) {
                showSuccessMessage("📤 Book issued successfully!");
            } else {
                showErrorMessage("❌ Failed to issue book! Check if book is available.");
            }
            dialog.dispose();
        });
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        dialog.add(issueBtn, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showReportsDialog() {
        String report = String.format(
            "📊 Library Statistics:\n\n" +
            "📚 Total Books: %d\n" +
            "✅ Available Books: %d\n" +
            "📤 Issued Books: %d\n" +
            "⚠️ Overdue Books: %d\n" +
            "👥 Total Students: %d\n" +
            "💰 Unpaid Fines: %d",
            libraryService.getAllBooks().size(),
            libraryService.getAvailableBooks().size(),
            libraryService.getIssuedBooks().size(),
            libraryService.getOverdueTransactions().size(),
            libraryService.getAllStudents().size(),
            libraryService.getUnpaidFines().size()
        );
        
        JOptionPane.showMessageDialog(this, report, "📊 Library Reports", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BeautifulLibraryGUI().setVisible(true);
        });
    }
}