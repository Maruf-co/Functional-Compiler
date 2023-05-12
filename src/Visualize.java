import java.util.ArrayDeque;
import java.util.ArrayList;

import syntax.LISPParser.NonTerminalNode;
import syntax.LISPParser.TerminalNode;
import syntax.LISPParser.TreeNode;


public class Visualize {
   public static String TreeToPNG(TreeNode tree) {
       var queue = new ArrayDeque<Integer>();
       var nodes = new ArrayList<TreeNode>();
       int cnt = 0;
       nodes.add(tree);
       queue.add(0);
       String dotOutput = String.format("digraph G{%d[label=\"%s\"];$}", 0, "root");
       while (!queue.isEmpty()) {
            var topID = queue.poll();
            System.out.print(topID + " ");
            var top = nodes.get(topID);
            // Print memory address
            System.out.println(top);
            for (var child : top.children) {
                nodes.add((TreeNode) child);
                queue.add(++cnt);
                String nodeContent = "";
                if (!child.isTerminal()) {
                    nodeContent = ((NonTerminalNode) child).nodeName;
                } else {
                    nodeContent = ((TerminalNode) child).data.getValue().toString();
                }
                String toReplace = String.format("%d[label=\"%s\"];%d->%d;$", cnt, nodeContent, topID, cnt);
                dotOutput = dotOutput.replace("$", toReplace);
            }
       }
       dotOutput = dotOutput.replace("$", "");
       return dotOutput;
   }
}
