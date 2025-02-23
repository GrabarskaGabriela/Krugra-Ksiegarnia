package Exceptions;

public class LoginErrorException extends Exceptions
{
    public LoginErrorException()
    {
        super("Incorrect login data was provided or the wrong role was selected.");

    }
    public boolean shouldBreak() {
        return true;
    }
}
