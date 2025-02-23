package Library;
public abstract class Library_user
{
    protected String user_name;
    protected String user_surname;

    public Library_user(String user_surname, String user_name)
    {
        this.user_surname = user_surname;
        this.user_name = user_name;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public String getUser_surname()
    {
        return user_surname;
    }
    @Override
    public String toString() {
        return getIdentifier();
    }
    abstract protected String getIdentifier();
}
