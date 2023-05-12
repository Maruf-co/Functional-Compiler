package tokens;

public class BooleanLiteral extends tokens.Literal {

    private Boolean literalValue;

    public BooleanLiteral(String rawString) {
        super(rawString);
        this.literalValue = (rawString.equals("true"));
    }

    public BooleanLiteral(Boolean literalValue) {
        super();
        this.literalValue = literalValue;
    }


    @Override
    public Boolean getValue() {
        return literalValue;
    }

    @Override
    public LiteralType getLiteralType() {
        return tokens.Literal.LiteralType.BOOLEAN;
    }
}