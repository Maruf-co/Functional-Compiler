package tokens;

import java.util.ArrayList;

public class CompositeLiteral extends LiteralToken {

    public ArrayList<LiteralToken> literals = new ArrayList<>();

    public CompositeLiteral(ArrayList<LiteralToken> value) {
        super();
        this.literals = value;
    }

    @Override
    public LiteralType getLiteralType() {
        return LiteralType.COMPOSITE;
    }

    @Override
    public ArrayList<LiteralToken> getValue() {
        return this.literals;
    }
}
