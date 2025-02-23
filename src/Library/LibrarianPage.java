package Library;
import javax.swing.*;
import java.awt.*;


public class LibrarianPage extends JFrame
{

    private JButton displayBooksButton;
    private JButton helpButton;
    private JButton returnBookButton;
    private JButton logoutButton;


    public LibrarianPage() {
        setTitle("Login Page");
        setSize(400, Toolkit.getDefaultToolkit().getScreenSize().height);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Page_style.panel_color);
        mainPanel.setLayout(new BorderLayout());

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Page_style.panel_color);
        logoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ImageIcon logo = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel(logo);
        logoPanel.add(logoLabel);

        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Page_style.panel_color);
        loginPanel.setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.lightGray);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel userInfoLabel = new JLabel("Logged as: " + LoginPage.getUser_name() + " " + LoginPage.getUser_surname()+", Worker id: "+ LoginPage.getUser_id());
        styleLabel(userInfoLabel);
        gbc.gridy = 0;
        loginPanel.add(userInfoLabel, gbc);
        displayBooksButton = new JButton("Display all available books");
        returnBookButton = new JButton("Books to be returned");
        helpButton = new JButton("Help");
        logoutButton = new JButton("Logout");


        styleButton(returnBookButton);
        styleButton(displayBooksButton);
        styleButton(helpButton);
        styleButton(logoutButton);


        gbc.gridy = 1;
        loginPanel.add(returnBookButton, gbc);

        gbc.gridy++;
        loginPanel.add(displayBooksButton, gbc);

        gbc.gridy++;
        loginPanel.add(helpButton, gbc);

        gbc.gridy++;
        loginPanel.add(logoutButton, gbc);

        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        add(mainPanel);

        returnBookButton.addActionListener(e -> openNewPage(new Book_returning_page(Display_bookshelf_page.getBorrowedBooks())));
       displayBooksButton.addActionListener(e -> openNewPage(new Display_bookshelf_page(Database_connector.getAvailableBooks())));
        helpButton.addActionListener(e -> {
            dispose();
            if ("Reader".equals(LoginPage.getUser_role())) {
                new Help_page().setVisible(true);
            } else {
                new Help_page().setVisible(true);
            }
        });
        logoutButton.addActionListener(e -> {setVisible(false);new LoginPage().setVisible(true);dispose();});
    }


    private void styleButton(JButton button) {
        button.setBackground(Page_style.button_background_color);
        button.setForeground(Page_style.button_text_color);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(300, 40));
    }

    private void styleLabel(JLabel label)
    {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Page_style.text_color);
    }

    private void openNewPage(JFrame newPage) {
        dispose();
        newPage.setVisible(true);
    }
}


