package tokens;

abstract public class Token {
    
    Token() {}
    Token(String rawString) {}

    public boolean isIdentifier() {
        return false;
    }

    public boolean isLiteral() {
        return false;
    }

    public Object getValue() {
        return null;
    }
}