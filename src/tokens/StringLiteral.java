package tokens;

public class StringLiteral extends LiteralToken {

    private String literalValue;

    public StringLiteral(String rawString) {
        super(rawString);
        this.literalValue = rawString;
    }

    @Override
    public String getValue() {
        return literalValue;
    }

    @Override
    public LiteralType getLiteralType() {
        return LiteralToken.LiteralType.STRING;
    }
}
