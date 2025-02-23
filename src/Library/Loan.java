package Library;
import java.time.LocalDate;

public class Loan
{
    protected int loan_id;
    protected String book_id;
    protected String user_id;
    protected  LocalDate loan_date;
    protected  LocalDate return_date;
    protected Boolean is_returned;

    public Loan(int loan_id, String book_id, String user_id, LocalDate loan_date, LocalDate return_date, Boolean is_returned) {
        this.loan_id = loan_id;
        this.book_id = book_id;
        this.user_id = user_id;
        this.loan_date = loan_date;
        this.return_date = return_date;
        this.is_returned = is_returned;
    }

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LocalDate getLoan_date() {
        return loan_date;
    }

    public void setLoan_date(LocalDate loan_date) {
        this.loan_date = loan_date;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }

    public Boolean getIs_returned() {
        return is_returned;
    }

    public void setIs_returned(Boolean is_returned) {
        this.is_returned = is_returned;
    }
}
