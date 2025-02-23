package Library;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database_connector
{
    private static final String URL = "jdbc:mysql://localhost:3306/Library_project_ppo";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e)
        {
            throw new SQLException("Failed to connect to the database.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<Book> getAvailableBooks()
    {
        List<Book> books = new ArrayList<>();

        String query = "SELECT * FROM books WHERE book_availability = 'true';";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int book_identifier = resultSet.getInt("id_book");
                String book_title = resultSet.getString("book_title");
                String book_author_name = resultSet.getString("book_author_name");
                String book_author_surname = resultSet.getString("book_author_surname");
                String book_genre = resultSet.getString("book_genre");

                books.add(new Book(book_identifier, book_title, book_author_name, book_author_surname, book_genre, true));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static List<Book> getBorrowedBooksByReader(int readerId) {
        List<Book> books = new ArrayList<>();

        String query = "SELECT b.id_book, b.book_title, b.book_author_name, b.book_author_surname, b.book_genre, l.loan_date, l.return_date FROM books b JOIN loans l ON b.id_book = l.book_id WHERE l.user_id = ? AND l.is_returned = 'false';";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, readerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int bookIdentifier = resultSet.getInt("id_book");
                    String bookTitle = resultSet.getString("book_title");
                    String bookAuthorName = resultSet.getString("book_author_name");
                    String bookAuthorSurname = resultSet.getString("book_author_surname");
                    String bookGenre = resultSet.getString("book_genre");
                    LocalDate loanDate = resultSet.getDate("loan_date").toLocalDate();
                    LocalDate returnDate = resultSet.getDate("return_date").toLocalDate();

                    Book book = new Book(bookIdentifier, bookTitle, bookAuthorName, bookAuthorSurname, bookGenre, false);
                    book.setBorrow_book_date(loanDate);
                    book.setBook_returning_date(returnDate);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
    public static List<Book> getBorrowedBooks() {
        List<Book> books = new ArrayList<>();

        String query = "SELECT books.id_book, books.book_title, books.book_author_name, books.book_author_surname, books.book_genre FROM loans INNER JOIN readers ON readers.id_reader = loans.user_id INNER JOIN books ON books.id_book = loans.book_id WHERE loans.is_returned = 'false';";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int book_identifier = resultSet.getInt("id_book");
                String book_title = resultSet.getString("book_title");
                String book_author_name = resultSet.getString("book_author_name");
                String book_author_surname = resultSet.getString("book_author_surname");
                String book_genre = resultSet.getString("book_genre");

                books.add(new Book(book_identifier, book_title, book_author_name, book_author_surname, book_genre, false));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }


    public static void updateBookAvailability(int bookId) {
        String query = "UPDATE books SET book_availability = 'true' WHERE id_book = ?;";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
            System.out.println("The availability of the book has been successfully updated. The book is now available for loan.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateBookUnavailability(int bookId)
    {
        String query = "UPDATE books SET book_availability = 'false' WHERE id_book = ?;";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book availability updated successfully. The book is now unavailable to borrow.");
            } else {
                System.out.println("No rows were updated. Check if the book ID exists in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getLoanUser_ID(int book_id) {
        int user_id = 0;

        String query = "SELECT user_id FROM loans WHERE book_id = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, book_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next())
                {
                    user_id = resultSet.getInt("user_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user_id;
    }


    public static void addLoan(int bookId, int userId, LocalDate loanDate, LocalDate returnDate)
    {
        String query = "INSERT INTO loans (id, book_id, user_id, loan_date, return_date, is_returned) VALUES (NULL, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookId);
            statement.setInt(2, userId);
            statement.setDate(3, Date.valueOf(loanDate));
            statement.setDate(4, Date.valueOf(returnDate));
            statement.setString(5, "false");

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateLoanReturnStatus(int bookId, int userId) {
        String query = "UPDATE loans SET is_returned = true WHERE book_id = ? AND user_id = ? AND is_returned = 'false';";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Nie znaleziono rekordu wypożyczenia do aktualizacji dla książki ID: " + bookId + " i użytkownika ID: " + userId);
            } else {
                System.out.println("Status zwrotu książki został pomyślnie zaktualizowany.");
                updateBookAvailability(bookId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
