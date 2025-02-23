package Library;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Bookshelf_filters {
    private List<Book> books;

    public Bookshelf_filters(List<Book> books)
    {
        this.books = new ArrayList<>(books);
    }

    public List<Book> sortByAuthorNameAscending() {
        return books.stream().sorted(Comparator.comparing(Book::getBook_authors_name)).collect(Collectors.toList());
    }

    public List<Book> sortByAuthorNameDescending() {
        return books.stream().sorted(Comparator.comparing(Book::getBook_authors_name).reversed()).collect(Collectors.toList());
    }

    public List<Book> sortByTitleAscending()
    {
        return books.stream().sorted(Comparator.comparing(Book::getBook_title)).collect(Collectors.toList());
    }

    public List<Book> sortByTitleDescending() {
        return books.stream().sorted(Comparator.comparing(Book::getBook_title).reversed()).collect(Collectors.toList());
    }

    public List<Book> sortByGenreAscending() {
        return books.stream().sorted(Comparator.comparing(Book::getBook_genre)).collect(Collectors.toList());
    }

    public List<Book> sortByGenreDescending() {
        return books.stream().sorted(Comparator.comparing(Book::getBook_genre).reversed()).collect(Collectors.toList());
    }

    public List<Book> sortByReturnDateAscending()
    {
        return books.stream().sorted(Comparator.comparing(Book::getBook_returning_date)).collect(Collectors.toList());
    }

    public List<Book> sortByReturnDateDescending()
    {
        return books.stream().sorted(Comparator.comparing(Book::getBook_returning_date).reversed()).collect(Collectors.toList());
    }

}
