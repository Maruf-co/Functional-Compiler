package syntax;

import tokens.*;
import java.util.ArrayList;

/**
    Lexer class to parse the input program into comprehensive
    atom parts - tokens.
**/
%%
%public
%standalone

%line
%column

%{  // injected after class declaration
    StringBuffer stringLiteralBuffer = new StringBuffer();
    ArrayList<Token> tokenBuffer = new ArrayList();

    private void capture(Token token) {
        tokenBuffer.add(token);
    }

    private void expandLiteral(String text) {
        stringLiteralBuffer.append(text);
    }
%}

Identifier = [:jletter:][:jletterdigit:]*   // Variable/predefined function
EOLN = \r|\n|\r\n                           // End of line
Whitespace = {EOLN} | [ \t\f]               // Any space
Borders = "(" | ")"
Number = (\+|\-)?\d*(\.?\d+)    // Number literal

// Captures contents enclosed into string in ""
%state STRING

%%

<YYINITIAL> {
    "("                                         { capture(new LeftBracket()); return LISPLexer.LeftBracket; }
    ")"                                         { capture(new RightBracket()); return LISPLexer.RightBracket; }
    ({Whitespace}|{Borders})\"                  { stringLiteralBuffer.setLength(0); yybegin(STRING); }
    {Whitespace}                                { /* Skip */ }
    ({Whitespace}|{Borders}){Number}            { capture(new Literal(yytext().trim())); return LISPLexer.Literal; }
    {Identifier}                                { capture(new Identifier(yytext())); return LISPLexer.Identifier; }
}

<STRING> {
    \"                  { yybegin(YYINITIAL); capture(new Literal(stringLiteralBuffer.toString())); return LISPLexer.Literal; }
    \\t                 { stringLiteralBuffer.append('\t'); }
    \\n                 { stringLiteralBuffer.append('\n'); }
    \\r                 { stringLiteralBuffer.append('\r'); }
    \\\"                { stringLiteralBuffer.append('"');  }
    \\                  { stringLiteralBuffer.append('\\'); }
    [^\n\r\"\\]+        { stringLiteralBuffer.append(yytext()); }
}

[^]     { throw new Error("Unexpected character <"+yytext()+">"); }
