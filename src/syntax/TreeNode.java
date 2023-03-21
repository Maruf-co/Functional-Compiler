package syntax;

import java.util.LinkedList;
import java.util.List;


public class TreeNode<T>{

    public T data;
    public TreeNode<T> parent;
    public List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
    }

    public TreeNode<T> addChild(Object obj) {
        var childNode = (TreeNode<T>) obj;
        childNode.parent = this;
        this.children.add(childNode);
        return this;
    }

}
