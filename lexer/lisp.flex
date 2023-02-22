package lex;

import tokens.*;
import java.util.ArrayList;

/**
    Lexer class to parse the input program into comprehensive 
    atom parts - tokens. 
**/
%%
%class   Lexer      /* Output class name */
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

Identifier = [:jletter:][:jletterdigit:]*   // Variable/predefined function
EOLN = \r|\n|\r\n                           // End of line
Whitespace = {EOLN} | [ \t\f]               // Any space
Borders = "(" | ")"
Number = (\+|\-)?\d*(\.?\d+)    // Number literal

// Captures contents enclosed into string in ""
%state STRING   

%%

<YYINITIAL> { 
    "("                                         { return capture(new LeftBracket()); }
    ")"                                         { return capture(new RightBracket()); }
    ({Whitespace}|{Borders})\"                  { stringLiteralBuffer.setLength(0); yybegin(STRING); }
    {Whitespace}                                { /* Skip */ }
    ({Whitespace}|{Bord`ers}){Number}            { return capture(new Literal(yytext().trim())); }
    {Identifier}                                { return capture(new Identifier(yytext())); }
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

