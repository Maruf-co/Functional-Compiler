import tokens.Token;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Token> tokens = new ArrayList<>();

    }

    void printTokens(ArrayList<Token> tokens) {
        for (Token token: tokens){
            System.out.print(token.getName());
        }
    }
}
