package Library;
public class User_reader extends Library_user {
    protected String reader_identifier;
    protected String reader_phone_number;
    protected String reader_email;
    private String reader_password;

    public User_reader(String reader_identifier, String reader_name, String reader_surname, String reader_email, String reader_phone_number, String reader_password) {
        super(reader_name, reader_surname);
        this.reader_identifier = reader_identifier;
        this.reader_email = reader_email;
        this.reader_phone_number = reader_phone_number;
        this.reader_password = reader_password;
    }

    public String getReader_identifier() {
        return reader_identifier;
    }

    public void setReader_identifier(String reader_identifier) {
        this.reader_identifier = reader_identifier;
    }

    public String getReader_phone_number() {
        return reader_phone_number;
    }

    public void setReader_phone_number(String reader_phone_number) {
        this.reader_phone_number = reader_phone_number;
    }

    public String getReader_email() {
        return reader_email;
    }

    public void setReader_email(String reader_email) {
        this.reader_email = reader_email;
    }

    public String getReader_password() {
        return reader_password;
    }

    public void setReader_password(String reader_password) {
        this.reader_password = reader_password;
    }

    @Override
    public String toString() {
        return getIdentifier();
    }

    public String getIdentifier() {
        return "Reader: " + getUser_name() + " " + getUser_surname() + ", library card number: " + this.reader_identifier;
    }
}
