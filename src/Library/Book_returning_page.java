package Library;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Book_returning_page extends JFrame implements Page_style {

    private JPanel backgroundPanel;
    private List<Book> books_to_return;
    private List<Book> books_to_return_computed;

    public Book_returning_page(List<Book> books_to_be_returned)
    {
        super("Books to be Returned");
        this.books_to_return = books_to_be_returned;
        this.books_to_return_computed = new ArrayList<>(books_to_be_returned);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBackground(Page_style.panel_color);

        JPanel searchPanel = createSearchPanel();
        JPanel sortPanel = createSortButtons();

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBackground(Page_style.page_background_color);
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterPanel.add(searchPanel);
        filterPanel.add(sortPanel);

        JScrollPane scrollPane = new JScrollPane(backgroundPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton backButton = new JButton("Back to Menu");
        styleButton(backButton);
        backButton.addActionListener(e -> {
            dispose();
            if ("Reader".equals(LoginPage.getUser_role())) {
                new ReaderPage().setVisible(true);
            } else {
                new LibrarianPage().setVisible(true);
            }
        });

        setLayout(new BorderLayout());
        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        displayBooks(books_to_return_computed);
    }

    private JPanel createSearchPanel()
    {
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Page_style.page_background_color);
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setForeground(Page_style.text_color);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JTextField searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.BOLD, 16));

        JButton searchButton = new JButton("Search");
        styleButton(searchButton);
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().toLowerCase();
            books_to_return_computed = books_to_return.stream()
                    .filter(book -> bookMatchesSearch(book, searchText))
                    .collect(Collectors.toList());
            displayBooks(books_to_return_computed);
        });

        JButton clearButton = new JButton("Clear");
        styleButton(clearButton);
        clearButton.addActionListener(e -> {
            searchField.setText("");
            books_to_return_computed = new ArrayList<>(books_to_return);
            displayBooks(books_to_return_computed);
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        return searchPanel;
    }

    private boolean bookMatchesSearch(Book book, String searchedText) {
        try {
            int searchedId = Integer.parseInt(searchedText);
            return book.getBook_title().toLowerCase().contains(searchedText) ||
                    book.getBook_authors_name().toLowerCase().contains(searchedText) ||
                    book.getBook_authors_surname().toLowerCase().contains(searchedText) ||
                    book.getBook_genre().toLowerCase().contains(searchedText) ||
                    book.getBook_identifier() == searchedId;
        } catch (NumberFormatException e) {
            return book.getBook_title().toLowerCase().contains(searchedText) ||
                    book.getBook_authors_name().toLowerCase().contains(searchedText) ||
                    book.getBook_authors_surname().toLowerCase().contains(searchedText) ||
                    book.getBook_genre().toLowerCase().contains(searchedText);
        }
    }

    private JPanel createSortButtons() {
        JPanel sortPanel = new JPanel();
        sortPanel.setBackground(Page_style.page_background_color);
        sortPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton sortByTitleAsc = new JButton("Sort by Title (A -> Z)");
        styleButton(sortByTitleAsc);
        sortByTitleAsc.addActionListener(e -> {
            books_to_return_computed.sort((b1, b2) -> b1.getBook_title().compareToIgnoreCase(b2.getBook_title()));
            displayBooks(books_to_return_computed);
        });

        JButton sortByTitleDesc = new JButton("Sort by Title (Z -> A)");
        styleButton(sortByTitleDesc);
        sortByTitleDesc.addActionListener(e -> {
            books_to_return_computed.sort((b1, b2) -> b2.getBook_title().compareToIgnoreCase(b1.getBook_title()));
            displayBooks(books_to_return_computed);
        });

        JButton sortByAuthorAsc = new JButton("Sort by Author (A -> Z)");
        styleButton(sortByAuthorAsc);
        sortByAuthorAsc.addActionListener(e -> {
            books_to_return_computed.sort((b1, b2) -> b1.getBook_authors_name().compareToIgnoreCase(b2.getBook_authors_name()));
            displayBooks(books_to_return_computed);
        });

        JButton sortByAuthorDesc = new JButton("Sort by Author (Z -> A)");
        styleButton(sortByAuthorDesc);
        sortByAuthorDesc.addActionListener(e -> {
            books_to_return_computed.sort((b1, b2) -> b2.getBook_authors_name().compareToIgnoreCase(b1.getBook_authors_name()));
            displayBooks(books_to_return_computed);
        });

        JButton sortByReturnDateAscending = new JButton("Sort by Return Date (Asc)");
        styleButton(sortByReturnDateAscending);
        sortByReturnDateAscending.addActionListener(e -> {
            books_to_return_computed.sort((b1, b2) -> b2.getBook_returning_date().compareTo(b1.getBook_returning_date()));
            displayBooks(books_to_return_computed);
        });

        JButton sortByReturnDateDescending = new JButton("Sort by Return Date (Desc)");
        styleButton(sortByReturnDateDescending);
        sortByReturnDateDescending.addActionListener(e -> {
            books_to_return_computed.sort((b1, b2) -> b2.getBook_returning_date().compareTo(b1.getBook_returning_date()));
            displayBooks(books_to_return_computed);
        });

        sortPanel.add(sortByTitleAsc);
        sortPanel.add(sortByTitleDesc);
        sortPanel.add(sortByAuthorAsc);
        sortPanel.add(sortByAuthorDesc);
        sortPanel.add(sortByReturnDateAscending);
        sortPanel.add(sortByReturnDateDescending);
        return sortPanel;
    }

    private void displayBooks(List<Book> books) {
        backgroundPanel.removeAll();

        if (books.isEmpty()) {
            JLabel noBooksLabel = new JLabel("There's no book to be returned.");
            styleLabel(noBooksLabel);
            noBooksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            backgroundPanel.add(noBooksLabel);
        } else {
            for (Book book : books) {
                backgroundPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                JButton bookButton = new JButton(
                        "Borrowed by: " + Database_connector.getLoanUser_ID(book.getBook_identifier()) + " | Book id: " + book.getBook_identifier() + " | " +
                                book.getBook_title() + " | " + book.getBook_authors_name() + " " +
                                book.getBook_authors_surname() + " | " + book.getBook_genre() + " | " +
                                "Borrowed on: " + book.getBorrow_book_date() + ", Deadline: " +
                                book.getBook_returning_date()
                );
                bookButton.setMaximumSize(new Dimension(1400, 50));
                bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                styleButton(bookButton);
                backgroundPanel.add(bookButton);

                bookButton.addActionListener(e -> {
                    int response = JOptionPane.showConfirmDialog(this, "Would you like to return the book: " + book.getBook_title() + "?", "Confirmation of book returning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        Database_connector.updateLoanReturnStatus(book.getBook_identifier(), Database_connector.getLoanUser_ID(book.getBook_identifier()));
                        Database_connector.updateBookAvailability(book.getBook_identifier());
                        books_to_return.remove(book);
                        JOptionPane.showMessageDialog(this, "The book: " + book.getBook_title() + " has been returned.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                        new Book_returning_page(books_to_return).setVisible(true);
                        dispose();
                    }
                });
            }
        }

        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }

    private void styleButton(JButton button) {
        button.setBackground(Page_style.button_background_color);
        button.setForeground(Page_style.button_text_color);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Page_style.text_color);
    }
}
