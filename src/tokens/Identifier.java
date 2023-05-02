package tokens;

public class Identifier extends Token {
    public String identifier;

    public Identifier(String rawString) {
        super(rawString);
        this.identifier = rawString;
    }

    public boolean isIdentifier() {
        return true;
    }

    public String toString() {
        return String.format("Identifier [%s]", this.getValue().toString());
    }

    @Override
    public String getValue() {
        return this.identifier;
    }
}
