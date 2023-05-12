package tokens;

import java.util.ArrayList;

public class CompositeLiteral extends Literal {

    public ArrayList<Literal> literals = new ArrayList<>();

    public CompositeLiteral(ArrayList<Literal> value) {
        super();
        this.literals = value;
    }

    @Override
    public LiteralType getLiteralType() {
        return LiteralType.COMPOSITE;
    }

    @Override
    public ArrayList<Literal> getValue() {
        return this.literals;
    }
}
