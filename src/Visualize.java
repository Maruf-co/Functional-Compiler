import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import javax.management.Query;

import syntax.LISPParser.TreeNode;
import tokens.Identifier;
import tokens.Literal;

public class Visualize {
   public static String TreeToPNG(TreeNode tree) {
       var queue = new ArrayDeque<Integer>();
       var nodes = new ArrayList<TreeNode>();
       int cnt = 0;
       nodes.add(tree);
       queue.add(0);
       String dotOutput = String.format("digraph G{%d[label=%s];$}", 0, nodes.get(0).getClass().getName().toString());
       while (!queue.isEmpty()) {
           var topID = queue.pollFirst();
           var top = nodes.get(topID);
           for (var child : top.children) {
               nodes.add((TreeNode) child);
               queue.add(++cnt);
               String nodeContent = child.getClass().getName().toString();
               String toReplace = String.format("%d[label=\"%s\"];%d->%d;$", cnt, nodeContent, topID, cnt);
               dotOutput = dotOutput.replace("$", toReplace);
           }
       }
       dotOutput = dotOutput.replace("$", "");
       return dotOutput;
   }
}
