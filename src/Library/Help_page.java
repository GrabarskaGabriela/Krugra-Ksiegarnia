package Library;

import javax.swing.*;
import java.awt.*;

public class Help_page extends JFrame implements Page_style {

    private JButton backButton;

    public Help_page() {
        setTitle("Help Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Page_style.panel_color);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Page_style.panel_color);
        ImageIcon logo = new ImageIcon("logo_help.png");
        JLabel logoLabel = new JLabel(logo);
        logoPanel.add(logoLabel);
        mainPanel.add(logoPanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Page_style.panel_color);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        if ("Reader".equals(LoginPage.getUser_role())) {
            JLabel titleLabel = new JLabel("Help Page for readers");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(Page_style.text_color);
            infoPanel.add(titleLabel, gbc);

            gbc.gridy++;
            JTextArea helpText = new JTextArea(15, 100);
            helpText.setText(" Dear readers, welcome to our Krugra library bookstore.\n" +
                    "\n" +
                    "To borrow a book, click on the “Display all available books” button. This will open a page displaying all currently available books. To search for a book, enter the title of the book, the name of the author or the genre to which the book belongs in the search box. You can sort the currently displayed list of books by pressing the buttons below the search field.\n" +
                    "The “Sort by Title (A -> Z)” button will sort the list of books by title alphabetically.\n" +
                    "The “Sort by Title (Z -> A)” button will sort the list of books by title in reverse alphabetical order.\n" +
                    "The “Sort by Author (A -> Z)” button will sort the list of books by author data alphabetically.\n" +
                    "The “Sort by Author (Z -> A)” button will sort the list of books by author data in reverse alphabetical order.\n" +
                    "The “Sort by Genre (A -> Z)” button will sort the list of books by genre name alphabetically.\n" +
                    "The “Sort by Genre (Z -> A)” button will sort the list of books by genre name in reverse alphabetical order.\n" +
                    "\n" +
                    "When you locate the chosen book, click on its name. A window will appear asking if we are sure we want to borrow this book, if we select “Yes” the system will add the book to borrowed books and display a message that the book has been borrowed. If we select “No” the book will not be borrowed.\n" +
                    "\n" +
                    "You can display the list of borrowed books by clicking on the “My borrowed books” button in the reader menu. You can also sort the list of borrowed books by using the sort buttons.\n" +
                    "\n" +
                    "To return a book, please visit the nearest Krugra library, where our employees will return the book.\n" +
                    "\n" +
                    "We wish you a pleasant use of our application.\n" +
                    "~ Pawel Kruk and Gabriela Grabarska, Krugra");
            helpText.setLineWrap(true);
            helpText.setWrapStyleWord(true);
            helpText.setEditable(false);
            helpText.setFont(new Font("Arial", Font.PLAIN, 16));
            helpText.setForeground(Page_style.text_color);
            helpText.setBackground(Page_style.panel_color);
            JScrollPane textScrollPane = new JScrollPane(helpText);
            textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            infoPanel.add(textScrollPane, gbc);
            infoPanel.setBackground(Page_style.page_background_color);
            mainPanel.add(infoPanel, BorderLayout.CENTER);

        }
        else
        {
            JLabel titleLabel = new JLabel("Help Page for library workers");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(Page_style.text_color);
            infoPanel.add(titleLabel, gbc);

            gbc.gridy++;
            JTextArea helpText = new JTextArea(15, 100);
            helpText.setText(" Dear employees.\n" +
                    "\n" +
                    "To return a book, please click the “Books to be returned” button. This will open a page displaying all the books currently on loan. To search for a book, enter the book id number, book title, author's name or the genre to which the book belongs in the search box. You can sort the currently displayed list of books by pressing the buttons below the search field.\n" +
                    "The “Sort by Title (A -> Z)” button will sort the list of books by title alphabetically.\n" +
                    "The “Sort by Title (Z -> A)” button will sort the list of books by title in counter alphabetical order.\n" +
                    "The “Sort by Author (A -> Z)” button will sort the list of books by author data alphabetically.\n" +
                    "The “Sort by Author (Z -> A)” button will sort the list of books by author data in counter alphabetical order.\n" +
                    "The “Sort by Return Date (Asc)” button will sort the list of books by return date in descending order, from the earliest borrowed.\n" +
                    "The “Sort by Return Date (Desc)” button will sort the list of books by return date ascending, from the latest borrowed.\n" +
                    "\n" +
                    "When you find the book you need, click on its name. A window will appear asking if we are sure we want to return this book, if we select “Yes” the system will return the book to the list of available for loan and display a message that the book has been returned.\n" +
                    "\n" +
                    "From the employee's menu, we can see the current list of available books, to do this, click the “Display all available books” button. To search for a book, enter the title of the book, the name of the author or the genre to which the book belongs in the search box.\n" +
                    "You can sort the currently displayed list of books by pressing the buttons below the search field.\n" +
                    "The “Sort by Title (A -> Z)” button will sort the list of books by title alphabetically.\n" +
                    "The “Sort by Title (Z -> A)” button will sort the list of books by title in reverse alphabetical order.\n" +
                    "The “Sort by Author (A -> Z)” button will sort the list of books by author data alphabetically.\n" +
                    "The “Sort by Author (Z -> A)” button will sort the list of books by author data in reverse alphabetical order.\n" +
                    "The “Sort by Genre (A -> Z)” button will sort the list of books by genre name alphabetically.\n" +
                    "The “Sort by Genre (Z -> A)” button will sort the list of books by genre name in reverse alphabetical order.\n" +
                    "\n" +
                    "We wish you a pleasant use of our application.\n" +
                    "~ Pawel Kruk and Gabriela Grabarska, Krugra\n");
            helpText.setLineWrap(true);
            helpText.setWrapStyleWord(true);
            helpText.setEditable(false);
            helpText.setFont(new Font("Arial", Font.PLAIN, 16));
            helpText.setForeground(Page_style.text_color);
            helpText.setBackground(Page_style.panel_color);
            JScrollPane textScrollPane = new JScrollPane(helpText);
            textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            infoPanel.add(textScrollPane, gbc);
            infoPanel.setBackground(Page_style.page_background_color);
            mainPanel.add(infoPanel, BorderLayout.CENTER);
        }

        backButton = new JButton("Back to Menu");
        styleButton(backButton);
        backButton.addActionListener(e -> {
            dispose();
            if ("Reader".equals(LoginPage.getUser_role())) {
                new ReaderPage().setVisible(true);
            } else {
                new LibrarianPage().setVisible(true);
            }
        });

        mainPanel.add(backButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void styleButton(JButton button) {
        button.setBackground(Page_style.button_background_color);
        button.setForeground(Page_style.button_text_color);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(300, 50));
    }
}
