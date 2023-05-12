package tokens;

public class BooleanLiteralToken extends LiteralToken {

    private Boolean literalValue;

    public BooleanLiteralToken(String rawString) {
        super(rawString);
        this.literalValue = (rawString.equals("true"));
    }

    public BooleanLiteralToken(Boolean literalValue) {
        super();
        this.literalValue = literalValue;
    }


    @Override
    public Boolean getValue() {
        return literalValue;
    }

    @Override
    public LiteralType getLiteralType() {
        return LiteralToken.LiteralType.BOOLEAN;
    }
}