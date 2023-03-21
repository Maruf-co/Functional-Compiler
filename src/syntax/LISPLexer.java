package syntax;

import java.io.IOException;
import java.io.Reader;

public class LISPLexer implements LISPParser.Lexer {
    Reader it;
    Yylex yylex;

    public LISPLexer(Reader is) {
        it = is;
        yylex = new Yylex(it);
    }

    @Override
    public void yyerror(String s) {
        System.err.println(s);
    }

    @Override
    public Object getLVal() {
        return null;
    }

    @Override
    public int yylex() throws IOException {
        return yylex.yylex();
    }
}
