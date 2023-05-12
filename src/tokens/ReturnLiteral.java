package tokens;

import static tokens.LiteralToken.LiteralType.RETURN;

public class ReturnLiteral extends LiteralToken {

    public LiteralToken value;

    public ReturnLiteral(LiteralToken value) {
        this.value = value;
    }

    @Override
    public LiteralType getLiteralType() {
        return RETURN;
    }

    @Override
    public LiteralToken getValue() {
        return value;
    }
}
