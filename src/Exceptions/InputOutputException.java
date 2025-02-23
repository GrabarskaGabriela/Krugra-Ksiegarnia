package Exceptions;

import java.io.IOException;

public class InputOutputException extends Exceptions{
    public InputOutputException (String message) {
        super(message);
    }

    public boolean shouldBreak()
    {
        return true;
    }
}
