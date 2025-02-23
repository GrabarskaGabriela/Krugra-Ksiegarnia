package Exceptions;

public class InvalidParameterException extends Exceptions
{
    public InvalidParameterException(String message)
    {
        super(message);
    }
    public boolean shouldBreak()
    {
        return false;
    }
}