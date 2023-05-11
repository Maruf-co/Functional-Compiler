package tokens;

import static tokens.Literal.LiteralType.RETURN;

public class ReturnLiteral extends tokens.Literal{

    public Literal value;

    public ReturnLiteral(Literal value) {
        this.value = value;
    }

    @Override
    public LiteralType getLiteralType() {
        return RETURN;
    }

    @Override
    public Literal getValue() {
        return value;
    }
}
