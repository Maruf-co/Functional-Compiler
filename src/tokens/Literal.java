package tokens;

public abstract class Literal extends Token {

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

    Literal(String literalValue) {
        this.literal = literalValue;
    }

    Literal() {}

    @Override
    public boolean isLiteral() {
        return true;
    }  

    public String toString() {
        return String.format("Literal [%s]", this.getValue().toString());
    }

    abstract public LiteralType getLiteralType();

}