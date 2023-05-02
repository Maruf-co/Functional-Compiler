package program;

// Write a custom exception that identifies
// syntax errors in the program.

public class SyntaxException extends Exception {
    public SyntaxException(String message) {
        super(message);
    }
}
