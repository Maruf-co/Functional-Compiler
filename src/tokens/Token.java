package tokens;

abstract public class Token {
    Token() {}
    Token(String rawString) {}
    abstract public String getName();
}
