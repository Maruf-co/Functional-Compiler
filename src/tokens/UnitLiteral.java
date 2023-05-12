package tokens;

public class UnitLiteral extends LiteralToken {
    @Override
    public LiteralType getLiteralType() {
        return LiteralType.UNIT;
    }
}
