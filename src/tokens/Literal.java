package tokens;

public class Literal extends Token {
    
    public Literal() {
    }

    public Literal(String rawString) {
        super(rawString);
    }

    @Override
    public String getName() {
        return "LITERAL";
    }
}
