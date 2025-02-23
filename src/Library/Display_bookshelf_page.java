package Library;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Display_bookshelf_page extends JFrame implements Page_style {
    private JPanel backgroundPanel;
    private Bookshelf_filters bookshelfFilters;
    private static List<Book> available_books = Database_connector.getAvailableBooks();
    private static List<Book> computed_available_books = new ArrayList<>(available_books);
    private static List<Book> unavailable_books = Database_connector.getBorrowedBooks();

    public Display_bookshelf_page(List<Book> books)
    {
        super("Bookshelf");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bookshelfFilters = new Bookshelf_filters(computed_available_books);
        backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Page_style.panel_color);
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        JPanel Search_panel = createSearchPanel();
        JPanel Button_panel = createSortButtons();

        JScrollPane scrollPane = new JScrollPane(backgroundPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel Filter_panel = new JPanel();
        Filter_panel.setLayout(new BoxLayout(Filter_panel, BoxLayout.Y_AXIS));
        Filter_panel.add(Search_panel);
        Filter_panel.add(Button_panel);

        JButton backButton = new JButton("Back to Menu");
        styleButton(backButton);
        backButton.addActionListener(e -> navigateToMenu());

        setLayout(new BorderLayout());
        add(Filter_panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        displayBooks(computed_available_books);
        refreshDisplayBookshelf();
    }




    private JPanel createSearchPanel() {

        JPanel Search_panel = new JPanel();
        Search_panel.setBackground(Page_style.page_background_color);
        Search_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel Search_label = new JLabel("Search: ");
        Search_label.setForeground(Page_style.text_color);
        Search_label.setFont(new Font("Arial", Font.BOLD, 16));

        JTextField Search_field = new JTextField(15);
        Search_field.setFont(new Font("Arial", Font.BOLD, 16));

        JButton Search_button = new JButton("Search");
        styleButton(Search_button);
        Search_button.addActionListener(e -> {
            String searchText = Search_field.getText().toLowerCase();
            computed_available_books = available_books.stream().filter(book -> bookMatchesSearch(book, searchText)).collect(Collectors.toList());
            displayBooks(computed_available_books);
        });

        JButton Clear_button = new JButton("Clear");
        styleButton(Clear_button);
        Clear_button.addActionListener(e -> {
            Search_field.setText("");
            computed_available_books = new ArrayList<>(available_books);
            displayBooks(computed_available_books);
        });

        Search_panel.add(Search_label);
        Search_panel.add(Search_field);
        Search_panel.add(Search_button);
        Search_panel.add(Clear_button);

        return Search_panel;
    }

    private boolean bookMatchesSearch(Book book, String Searched_text) {
        return book.getBook_title().toLowerCase().contains(Searched_text) ||
                book.getBook_authors_name().toLowerCase().contains(Searched_text) ||
                book.getBook_authors_surname().toLowerCase().contains(Searched_text) ||
                book.getBook_genre().toLowerCase().contains(Searched_text);
    }

    private JPanel createSortButtons() {
        JPanel Button_panel = new JPanel();
        Button_panel.setBackground(Page_style.page_background_color);
        Button_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        Route[] routes = {
                new Route("Sort by Title (A -> Z)", Bookshelf_filters.class, "sortByTitleAscending"),
                new Route("Sort by Title (Z -> A)", Bookshelf_filters.class, "sortByTitleDescending"),
                new Route("Sort by Author (A -> Z)", Bookshelf_filters.class, "sortByAuthorNameAscending"),
                new Route("Sort by Author (Z -> A)", Bookshelf_filters.class, "sortByAuthorNameDescending"),
                new Route("Sort by Genre (A -> Z)", Bookshelf_filters.class, "sortByGenreAscending"),
                new Route("Sort by Genre (Z -> A)", Bookshelf_filters.class, "sortByGenreDescending"),
                new Route("Reset Filters", Display_bookshelf_page.class, "Display_bookshelf_page(computed_available_books);")
        };

        for (Route route : routes) {
            JButton button = new JButton(route.getPath());
            styleButton(button);
            button.addActionListener(e -> handleBookshelfAction(route));
            Button_panel.add(button);
        }
        return Button_panel;
    }

    private void handleBookshelfAction(Route route) {
        try {
            if ("Display_bookshelf_page(computed_available_books);".equals(route.getMethod())) {
                computed_available_books = new ArrayList<>(available_books);
            } else {
                Method method = route.getActionClass().getMethod(route.getMethod());
                computed_available_books = (List<Book>) method.invoke(bookshelfFilters);
            }
            displayBooks(computed_available_books);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing action: " + route.getMethod());
        }
    }

    private void navigateToMenu() {
        dispose();
        if ("Reader".equals(LoginPage.getUser_role())) {
            new ReaderPage().setVisible(true);
        } else {
            new LibrarianPage().setVisible(true);
        }
    }

    private void displayBooks(List<Book> books) {
        backgroundPanel.removeAll();
        for (Book book : books) {
            backgroundPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            JButton bookButton = new JButton(
                    String.format("%s | %s %s | %s",
                            book.getBook_title(),
                            book.getBook_authors_name(),
                            book.getBook_authors_surname(),
                            book.getBook_genre()
                    ));
            bookButton.setMaximumSize(new Dimension(1000, 50));
            bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            styleButton(bookButton);

            if (!book.getBook_availability()) {
                bookButton.setEnabled(false);
            } else {
                bookButton.addActionListener(e -> handleBookBorrowing(book));
            }

            backgroundPanel.add(bookButton);
        }
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }

    private void handleBookBorrowing(Book book) {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Would you like to borrow the book: " + book.getBook_title() + "?",
                "Confirmation of book borrowing",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            Database_connector.updateBookUnavailability(book.getBook_identifier());
            book.setBook_availability(false);
            System.out.print(book.getBook_availability());
            LocalDate Borrow_book_date = book.setBorrow_book_date(LocalDate.now());
            LocalDate Deadline_for_returning_the_book = book.setBook_returning_date(Borrow_book_date.plusDays(30));
            Database_connector.addLoan(book.getBook_identifier(), LoginPage.getUser_id(), Borrow_book_date, Deadline_for_returning_the_book);
            unavailable_books.add(book);
            computed_available_books.remove(book);

            JOptionPane.showMessageDialog(
                    this,
                    "The book: " + book.getBook_title() + " has been borrowed.",
                    "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE
            );

            displayBooks(computed_available_books);
        }
    }
    public void refreshDisplayBookshelf()
    {
        this.computed_available_books = new ArrayList<>(available_books);
        displayBooks(computed_available_books);
    }
    private void styleButton(JButton button) {
        button.setBackground(Page_style.button_background_color);
        button.setForeground(Page_style.button_text_color);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    public static List<Book> getBorrowedBooks() {
        return unavailable_books;
    }
}
