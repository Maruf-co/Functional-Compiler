package tokens;

public class NumberLiteral extends LiteralToken {

    private Double literalValue;

    public NumberLiteral(String rawString) {
        super(rawString);
        this.literalValue = Double.parseDouble(rawString);
    }

    public NumberLiteral(Double value) {
        super(value.toString());
        this.literalValue = value;
    }

    public NumberLiteral(LiteralToken value) {
        super(value.toString());
        if (value.getLiteralType() == LiteralType.BOOLEAN) {
            this.literalValue = ((BooleanLiteralToken) value).getValue() ? 0. : 1.;
        } else {
            this.literalValue = ((NumberLiteral) value).getValue();
        }
    }


    @Override
    public Double getValue() {
        return literalValue;
    }

    @Override
    public LiteralType getLiteralType() {
        return LiteralToken.LiteralType.NUMBER;
    }
}
