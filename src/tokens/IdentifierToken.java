package tokens;

public class IdentifierToken extends Token {
    public String identifier;

    public IdentifierToken(String rawString) {
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
