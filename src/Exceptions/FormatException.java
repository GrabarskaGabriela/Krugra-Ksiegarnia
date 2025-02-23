package Exceptions;

public class FormatException extends Exceptions {
    public FormatException(String message) {
        super(message);
    }

    public boolean shouldBreak()
    {
        return true;
    }
}
