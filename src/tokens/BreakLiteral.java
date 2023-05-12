package tokens;

public class BreakLiteral extends tokens.Literal{
    @Override
    public LiteralType getLiteralType() {
        return LiteralType.BREAK;
    }
}
