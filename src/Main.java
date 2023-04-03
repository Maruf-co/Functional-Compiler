
import syntax.TreeNode;
//import syntax.Parser.
import tokens.*;
import syntax.Parser.LISPLexer;
import syntax.Parser.LISPParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat.Style;
import java.util.ArrayDeque;
import java.util.ArrayList;


public class Main {
    
    public static ArrayList<Token> tokens = new ArrayList<>();
    public static void main(String[] args) throws IOException {
//       visualize();
        LISPLexer lexer = new LISPLexer(new FileReader("input.txt"));
        LISPParser parser = new LISPParser(lexer);
        ArrayDeque queueue = new ArrayDeque<TreeNode>();
        if (parser.parse()) {
//            System.out.println(node.data.getClass().getName());
            TreeNode node = LISPParser.node;
            queueue.add(node);
            while (!queueue.isEmpty()) {
                TreeNode p = (TreeNode) queueue.poll();
                System.out.println(p.data.toString());
                for (Object x: p.children) {
                    queueue.add(x);
                }
            }
        }
    }

    static void printTokens() {
        for (Token token: tokens){
            System.out.print(token.getName() + "");
        }
    }

    static void visualize() throws IOException {
        FileWriter dotOutput = new FileWriter("output.dot");
        LISPLexer lexer = new LISPLexer(new FileReader("input.txt"));
        LISPParser parser = new LISPParser(lexer);
        if (parser.parse()) {
            System.out.println("Parsing Result = SUCCESS");
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
        return;
    }
}
