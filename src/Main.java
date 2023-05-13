import java.io.FileReader;
import java.io.IOException;
import program.SyntaxException;
import redesign.List;
import syntax.LISPParser;

import static redesign.ProgramBuilderKt.getProgram;
import static redesign.ProgramExecutionKt.executeProgram;

public class Main {
    public static void main(String[] args) throws IOException, IllegalStateException, SyntaxException {
        var reader = new FileReader("/Users/k.tyulebaeva/compiler/Functional-Compiler/src/input.txt");
        var lexer = new LISPParser.LISPLexer(reader);
        var parser = new LISPParser(lexer);
        parser.parse();

        var program = (List) getProgram(LISPParser.node);
        var result = executeProgram(program);
        System.out.println(result);
        /*
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
        FileWriter dotOutput = new FileWriter("/Users/r-shakirova/IdeaProjects/Functional-Compiler/src/output.dot");
        LISPParser.LISPLexer lexer = new LISPParser.LISPLexer(new FileReader("/Users/r-shakirova/IdeaProjects/Functional-Compiler/src/input.txt"));
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
        return;*/
    }
}
