package tokens;

public class Identifier extends Token {
    

    public Identifier() {
    }

    String identifier = new String();

    public Identifier(String rawString) {
        super(rawString);
        this.identifier = rawString;
    }

    @Override
    public String getName() {
        return "IDENTIFIER";
    }
}
