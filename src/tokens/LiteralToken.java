package tokens;

public abstract class LiteralToken extends Token {

    public static enum LiteralType {
        STRING,
        NUMBER,
        BOOLEAN,
        COMPOSITE,
        UNIT,
        BREAK,
        RETURN
    }

    protected String literal;

    LiteralToken(String literalValue) {
        this.literal = literalValue;
    }

    LiteralToken() {}

    @Override
    public boolean isLiteral() {
        return true;
    }  

    public String toString() {
        return String.format("Literal [%s]", this.getValue().toString());
    }

    abstract public LiteralType getLiteralType();

}