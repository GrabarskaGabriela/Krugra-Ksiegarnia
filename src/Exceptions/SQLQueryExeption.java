package Exceptions;

public class SQLQueryExeption extends Exceptions {
    public SQLQueryExeption()
    {
        super("The SQL query cannot be executed, check the accuracy of the data.");

    }
    public boolean shouldBreak() {
        return false;
    }
}

