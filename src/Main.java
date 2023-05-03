import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;

import program.ProgramExecution;
import program.ProgramState;
import program.SyntaxException;
import syntax.LISPParser;

public class Main {
    public static void main(String[] args) throws IOException, IllegalStateException, SyntaxException {
        var reader = new FileReader("./input.txt");
        var lexer = new LISPParser.LISPLexer(reader);
        var parser = new LISPParser(lexer);
        parser.parse();


        var result = ProgramExecution.execute(LISPParser.node, new ProgramState());
        System.out.println(result.getValue());
        
        System.out.println(LISPParser.node);

        var queue = new ArrayDeque<LISPParser.TreeNode>();
        var level = new ArrayDeque<Integer>();
        queue.add(LISPParser.node);
        level.add(0);
        while (!queue.isEmpty()) {
            LISPParser.TreeNode p = queue.poll();
            var lvl = level.poll();
            if (p.isTerminal()) {
                System.out.println(lvl + ":| " + p.data.getValue());
            } else {
                System.out.println(lvl + ":> " + p.nodeName);
            }


            for (var x: p.children) {
                queue.add(x);
                level.add(lvl + 1);
            }
        }
        visualize();
    }
    static void visualize() throws IOException {
        FileWriter dotOutput = new FileWriter("output.dot");
        LISPParser.LISPLexer lexer = new LISPParser.LISPLexer(new FileReader("input.txt"));
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
