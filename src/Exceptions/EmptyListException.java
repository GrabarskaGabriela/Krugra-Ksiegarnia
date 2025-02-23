package Exceptions;

public class EmptyListException extends Exceptions
{
    public EmptyListException (String message) {
        super(message);
    }

    public boolean shouldBreak()
    {
        return true;
    }
}
