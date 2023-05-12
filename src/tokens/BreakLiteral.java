package tokens;

public class BreakLiteral extends LiteralToken {
    @Override
    public LiteralType getLiteralType() {
        return LiteralType.BREAK;
    }
}
