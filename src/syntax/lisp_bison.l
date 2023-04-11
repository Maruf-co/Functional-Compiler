package syntax;

import tokens.*;
import java.util.ArrayList;

/**
    Lexer class to parse the input program into comprehensive 
    atom parts - tokens. 
**/
%%
%public
%final
%unicode
%type Token

%{  // injected after class declaration
    StringBuffer stringLiteralBuffer = new StringBuffer();

    private Token capture(Token token) {
        return token;
    }

    private void expandLiteral(String text) {
        stringLiteralBuffer.append(text);
    }
%}

EOLN = \r|\n|\r\n                           // End of line
Whitespace = {EOLN} | [ \t\f]               // Any space
Boolean = true|false
Borders = "(" | ")"
Number = (\+|\-)?\d*(\.?\d+)    // Number literal
Identifier = [a-zA-Z\_$][a-zA-Z0-9\_$]*   // Variable/predefined function

// Captures contents enclosed into string in ""
%state STRING   

%%

<YYINITIAL> { 
    "("                                                 { return capture(new LeftBracket()); }
    ")"                                                 { return capture(new RightBracket()); }
    ({Whitespace}|{Borders})\"                          { stringLiteralBuffer.setLength(0); yybegin(STRING); }
    {Whitespace}                                        { /* Skip */ }
    {Number}|{Boolean}                                  { return capture(new Literal(yytext())); }
    {Identifier}                                        { return capture(new Identifier(yytext())); }
}

<STRING> {
    \"                  { yybegin(YYINITIAL); return capture(new Literal(stringLiteralBuffer.toString())); }
    \\t                 { stringLiteralBuffer.append('\t'); }
    \\n                 { stringLiteralBuffer.append('\n'); }
    \\r                 { stringLiteralBuffer.append('\r'); }
    \\\"                { stringLiteralBuffer.append('"');  }
    \\                  { stringLiteralBuffer.append('\\'); }
    [^\n\r\"\\]+        { stringLiteralBuffer.append(yytext()); }
}

[^]     { throw new Error("Unexpected character <"+yytext()+">"); }