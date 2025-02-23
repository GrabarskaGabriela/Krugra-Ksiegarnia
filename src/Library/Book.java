package Library;
import java.time.LocalDate;

public class Book
{
    protected int book_identifier;
    protected String book_title;
    protected String book_authors_name;
    protected String book_authors_surname;
    protected String book_genre;
    protected Boolean book_availability;
    protected LocalDate Borrow_book_date;
    protected LocalDate Book_returning_date;

    public Book(int book_identifier, String book_title, String book_authors_name, String book_authors_surname, String book_genre, Boolean book_availability) {
        this.book_identifier = book_identifier;
        this.book_title = book_title;
        this.book_authors_name = book_authors_name;
        this.book_authors_surname = book_authors_surname;
        this.book_genre = book_genre;
        this.book_availability = book_availability;
        this.Borrow_book_date = null;
        this.Book_returning_date = null;
    }

    public int getBook_identifier() {
        return book_identifier;
    }

    public String getBook_title() {
        return book_title;
    }

    public String getBook_authors_name() {
        return book_authors_name;
    }

    public String getBook_authors_surname() {
        return book_authors_surname;
    }

    public String getBook_genre() {
        return book_genre;
    }

    public Boolean getBook_availability() {
        return book_availability;
    }

    public void setBook_availability(Boolean book_availability) {
        this.book_availability = book_availability;
    }

    public LocalDate setBorrow_book_date(LocalDate borrowDate) {
        this.Borrow_book_date = borrowDate;
        return borrowDate;
    }

    public LocalDate setBook_returning_date(LocalDate returnDate) {
        this.Book_returning_date = returnDate;
        return returnDate;
    }
    public LocalDate getBorrow_book_date() {
        return Borrow_book_date;
    }

    public LocalDate getBook_returning_date() {
        return Book_returning_date;
    }

    @Override
    public String toString()
    {
        return book_identifier+". Title: " + book_title + " Author: "+ book_authors_name+" "+book_authors_surname + " Availability=" + book_availability;
    }
}
