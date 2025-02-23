package Library;
import Exceptions.LoginErrorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends JFrame implements Page_style {
    private JButton exitButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JComboBox<String> roleComboBox;
    private static String user_name;
    private static String user_surname;
    private static int user_id;
    private static String user_email;
    private static String user_phone_number;
    private static String user_role;

    public LoginPage()
    {
        setTitle("Login Page");
        setSize(400, Toolkit.getDefaultToolkit().getScreenSize().height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Page_style.page_background_color);
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel usernameLabel = new JLabel("Enter email adress:");
        usernameLabel.setBackground(Page_style.text_color);
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        JLabel roleLabel = new JLabel("Login as:");
        roleComboBox = new JComboBox<>(new String[]{"Reader", "Librarian"});

        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");

        styleLabel(usernameLabel);
        styleLabel(passwordLabel);
        styleLabel(roleLabel);
        styleTextField(usernameField);
        styleTextField(passwordField);
        styleComboBox(roleComboBox);
        styleButton(loginButton);
        styleButton(exitButton);

        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);

        gbc.gridy++;
        loginPanel.add(usernameField, gbc);

        gbc.gridy++;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridy++;
        loginPanel.add(passwordField, gbc);

        gbc.gridy++;
        loginPanel.add(roleLabel, gbc);

        gbc.gridy++;
        loginPanel.add(roleComboBox, gbc);

        gbc.gridy++;
        loginPanel.add(loginButton, gbc);
        gbc.gridy++;
        loginPanel.add(exitButton, gbc);
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        add(mainPanel);

        loginButton.addActionListener(new Login());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void styleLabel(JLabel label)
    {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Page_style.text_color);
    }

    private void styleTextField(JTextField textField)
    {
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setFont(new Font("Arial", Font.BOLD, 14));
        textField.setForeground(Color.BLACK);
    }

    private void styleComboBox(JComboBox<String> select_role)
    {
        select_role.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void styleButton(JButton button)
    {
        button.setBackground(Page_style.button_background_color);
        button.setForeground(Page_style.button_text_color);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(250, 30));
    }

    private class Login implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String user_email = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            try (Connection connection = Database_connector.getConnection())
            {
                String query;
                if ("Reader".equals(role))
                {
                    query = "SELECT * FROM readers WHERE reader_email = ? AND reader_password = ?";
                } else {
                    query = "SELECT * FROM library_workers WHERE librarian_email = ? AND librarian_password = ?";
                }

                try (PreparedStatement try_to_log_in = connection.prepareStatement(query))
                {
                    try_to_log_in.setString(1, user_email);
                    try_to_log_in.setString(2, password);

                    try (ResultSet resultSet = try_to_log_in.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            if ("Reader".equals(role))
                            {
                                user_id = resultSet.getInt("id_reader");
                                user_name = resultSet.getString("reader_name");
                                user_surname = resultSet.getString("reader_surname");
                                user_email = resultSet.getString("reader_email");
                                user_phone_number = resultSet.getString("reader_phone_number");
                                user_role = role;
                                openNewPage(new ReaderPage());
                            }
                            else
                            {
                                user_id = resultSet.getInt("id_librarian");
                                user_name = resultSet.getString("librarian_name");
                                user_surname = resultSet.getString("librarian_surname");
                                user_email = resultSet.getString("librarian_email");
                                user_role = role;
                                openNewPage(new LibrarianPage());
                            }
                        } else
                        {
                            JOptionPane.showMessageDialog(LoginPage.this,
                                    "Invalid login or password.",
                                    "Login error",
                                    JOptionPane.ERROR_MESSAGE);
                            throw new LoginErrorException();
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(LoginPage.this,
                        "An error occurred while connecting to the database.",
                        "Database error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        private void openNewPage(JFrame newPage)
        {
            dispose();
            newPage.setVisible(true);
        }
    }

    private void openNewPage(JFrame newPage) {
        dispose();
        newPage.setVisible(true);
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public static String getUser_name() {
        return user_name;
    }

    public static String getUser_surname() {
        return user_surname;
    }

    public static int getUser_id() {
        return user_id;
    }

    public static String getUser_email() {
        return user_email;
    }

    public static String getUser_phone_number() {
        return user_phone_number;
    }

    public static String getUser_role() {
        return user_role;
    }
}

