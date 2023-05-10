package tokens;

public class UnitLiteral extends tokens.Literal {
    @Override
    public LiteralType getLiteralType() {
        return LiteralType.UNIT;
    }
}
