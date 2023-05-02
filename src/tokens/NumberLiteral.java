package tokens;

public class NumberLiteral extends tokens.Literal {

    private Double literalValue;

    public NumberLiteral(String rawString) {
        super(rawString);
        this.literalValue = Double.parseDouble(rawString);
    }

    public NumberLiteral(Double value) {
        super(value.toString());
        this.literalValue = value;
    }

    @Override
    public Double getValue() {
        return literalValue;
    }

    @Override
    public LiteralType getLiteralType() {
        return tokens.Literal.LiteralType.NUMBER;
    }
}
