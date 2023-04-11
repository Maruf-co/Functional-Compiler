
//import syntax.Parser.
import tokens.*;
import syntax.LISPParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;


public class Main {
    
    public static ArrayList<Token> tokens = new ArrayList<>();
    public static void main(String[] args) throws IOException {
//       visualize();
        var reader = new FileReader("input.txt");
        ArrayDeque queueue = new ArrayDeque<LISPParser.TreeNode>();
        var lexer = new LISPParser.LISPLexer(reader);
        var parser = new LISPParser(lexer);
        parser.parse();
        LISPParser.TreeNode node = LISPParser.node;
        queueue.add(node);
        while (!queueue.isEmpty()) {
            LISPParser.TreeNode p = (LISPParser.TreeNode) queueue.poll();
            if (p.data != null) {
                if (p.data instanceof Identifier) {
                    System.out.printf("Identifier@%s\n", ((Identifier) p.data).identifier) ;
                }
                else if (p.data instanceof Literal) {
                    System.out.printf("Literal[%s]\n", ((Literal) p.data).content) ;
                } else {
                    System.out.println(p.data.toString());
                }
            }
            for (Object x: p.children) {
                queueue.add(x);
            }
        }
        FileWriter dotOutput = new FileWriter("output.dot");
        var dot = Visualize.TreeToPNG(LISPParser.node);
        dotOutput.write(dot);
        dotOutput.close();
        String[] exec = {
            "dot", "-Tpng", "-o output.png", "output.dot"       
        };
        var p = Runtime.getRuntime().exec(exec);
        try {
            System.out.println(p.waitFor());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    static void printTokens() {
        for (Token token: tokens){
            System.out.print(token.getName() + "");
        }
    }

    // static void visualize() throws IOException {
    //     FileWriter dotOutput = new FileWriter("output.dot");
    //     LISPLexer lexer = new LISPLexer(new FileReader("input.txt"));
    //     LISPParser parser = new LISPParser(lexer);
    //     if (parser.parse()) {
    //         System.out.println("Parsing Result = SUCCESS");
    //         var dot = Visualize.TreeToPNG(LISPParser.node);
    //         dotOutput.write(dot);
    //         dotOutput.close();
    //         String[] exec = {
    //             "dot", "-Tpng", "-o output.png", "output.dot"       
    //         };
    //         var p = Runtime.getRuntime().exec(exec);
    //         try {
    //             System.out.println(p.waitFor());
    //         } catch (InterruptedException e) {
    //             // TODO Auto-generated catch block
    //             e.printStackTrace();
    //         }
    //     }   
    //     return;
    // }
}
