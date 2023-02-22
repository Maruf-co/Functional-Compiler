package tokens;

public class Identifier extends Token {
    

    public Identifier() {
    }

    public String identifier = new String();

    public Identifier(String rawString) {
        super(rawString);
        this.identifier = rawString;
    }

    @Override
    public String getName() {
        return "IDENTIFIER";
    }
}
