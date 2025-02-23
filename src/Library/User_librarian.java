package Library;
public class User_librarian extends Library_user {
    protected String librarian_identifier;
    protected String librarian_email;
    private String librarian_password;

    public User_librarian(String librarian_identifier, String librarian_name, String librarian_surname, String librarian_email, String librarian_password) {
        super(librarian_name, librarian_surname);
        this.librarian_identifier = librarian_identifier;
        this.librarian_email = librarian_email;
        this.librarian_password = librarian_password;
    }

    public String getLibrarian_identifier() {
        return librarian_identifier;
    }

    public void setLibrarian_identifier(String librarian_identifier) {
        this.librarian_identifier = librarian_identifier;
    }

    public String getLibrarian_email() {
        return librarian_email;
    }

    public void setLibrarian_email(String librarian_email) {
        this.librarian_email = librarian_email;
    }

    public String getLibrarian_password() {
        return librarian_password;
    }

    public void setLibrarian_password(String librarian_password) {
        this.librarian_password = librarian_password;
    }


    @Override
    public String toString() {
        return getIdentifier();
    }

    public String getIdentifier() {
        return "Librarian: " + getUser_name() + " " + getUser_surname() + ", worker identifier: " + this.librarian_identifier;
    }
}
