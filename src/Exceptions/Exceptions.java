package Exceptions;

public abstract class Exceptions extends  Exception
{
    public Exceptions(String message) {
        super(message);
    }

    public boolean shouldBreak() {
        return false;
    }
}
