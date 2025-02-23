package Library;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Borrowed_books_page extends JFrame implements Page_style
{
    private JPanel backgroundPanel;
    private Bookshelf_filters bookshelfFilters;
    private static List<Book> borrowed_books=Database_connector.getBorrowedBooksByReader(LoginPage.getUser_id());
    private static List<Book> borrowed_books_computted;
    static
    {
        borrowed_books_computted=List.copyOf(borrowed_books);
    }
    public Borrowed_books_page()
    {
        super("Books borrowed by reader");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bookshelfFilters = new Bookshelf_filters(borrowed_books_computted);
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
        displayBooks(borrowed_books_computted);

        for(Book book : borrowed_books_computted)
        {
            if(borrowed_books_computted.isEmpty())
            {
                System.out.println("The list of borrowed books is empty");
            }
            System.out.println(book);
        }
        refreshBorrowedBooks();
    }


    private JPanel createSearchPanel()
    {
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
            borrowed_books_computted = borrowed_books_computted.stream().filter(book -> bookMatchesSearch(book, searchText)).collect(Collectors.toList());
            displayBooks(borrowed_books_computted);
        });

        JButton Clear_button = new JButton("Clear");
        styleButton(Clear_button);
        Clear_button.addActionListener(e -> {
            Search_field.setText("");
            borrowed_books_computted = new ArrayList<>(borrowed_books_computted);
            displayBooks(borrowed_books_computted);
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

    private JPanel createSortButtons()
    {
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
                new Route("Reset Filters", Display_bookshelf_page.class, "Display_bookshelf_page(borrowed_books_computed);")
        };

        for (Route route : routes)
        {
            JButton button = new JButton(route.getPath());
            styleButton(button);
            button.addActionListener(e -> handleBookshelfAction(route));
            Button_panel.add(button);
        }
        return Button_panel;
    }

    private void handleBookshelfAction(Route route)
    {
        try {
            if ("Display_bookshelf_page(borrowed_books_computed);".equals(route.getMethod()))
            {
                borrowed_books_computted = new ArrayList<>(borrowed_books);
                displayBooks(borrowed_books_computted);
            }
            else
            {
                Method method = route.getActionClass().getMethod(route.getMethod());
                borrowed_books_computted = (List<Book>) method.invoke(bookshelfFilters);
            }
            displayBooks(borrowed_books_computted);
        } catch (Exception e)
        {
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
        if (books.isEmpty()) {
            JLabel noBooksLabel = new JLabel("There are no borrowed books.");
            styleLabel(noBooksLabel);
            noBooksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            backgroundPanel.add(noBooksLabel);
        } else {
            for (Book book : books) {
                JButton bookButton = new JButton(
                        String.format("%s | %s %s | %s | Borrowed on: %s | Return by: %s",
                                book.getBook_title(),
                                book.getBook_authors_name(),
                                book.getBook_authors_surname(),
                                book.getBook_genre(),
                                book.getBorrow_book_date(),
                                book.getBook_returning_date()
                        ));
                bookButton.setMaximumSize(new Dimension(1000, 50));
                bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                styleButton(bookButton);


                backgroundPanel.add(bookButton);
                backgroundPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }
    public void refreshBorrowedBooks()
    {
        this.borrowed_books_computted = Database_connector.getBorrowedBooksByReader(LoginPage.getUser_id());
        displayBooks(borrowed_books_computted);
    }
    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Page_style.text_color);
    }
    private void styleButton(JButton button)
    {
        button.setBackground(Page_style.button_background_color);
        button.setForeground(Page_style.button_text_color);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }
}