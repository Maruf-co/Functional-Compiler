
import tokens.*;
import lex.Lexer;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    
    public static ArrayList<Token> tokens = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        Lexer lexer = new Lexer(new FileReader("input.txt"));
        while (!lexer.yyatEOF()) {
            Token token = lexer.yylex();
            if (token == null) {
                break;
            }
            System.out.print(token.getName() + " ");
        }
        printTokens();
    }

    static void printTokens() {
        for (Token token: tokens){
            System.out.print(token.getName() + "");
        }
    }
}
