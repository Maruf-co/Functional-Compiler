package tokens;

public class Literal extends Token {
    
    public String content = new String();
    public Literal() {
    }

    public Literal(String rawString) {
        super(rawString);
        this.content = rawString;
    }

    @Override
    public String getName() {
        return "LITERAL";
    }
}
