package tokens;

public class LeftBracket extends Token {
    
    public LeftBracket() {
    }

    public LeftBracket(String rawString) {
        super(rawString);
    }

    @Override
    public String getName() {
        return "LBRK";
    }
}
