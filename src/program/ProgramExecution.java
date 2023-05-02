package program;

import java.util.ArrayList;

import javax.swing.text.html.parser.Element;

import syntax.LISPParser.TreeNode;
import tokens.Identifier;
import tokens.Literal;
import tokens.StringLiteral;

public class ProgramExecution {
    static Literal evaluateIdentifier(Identifier identifier) {
        // TODO: Change to real evaluation
        // once `setq` is available
        return new StringLiteral("boo");
    }

    static Literal evaluateElement(TreeNode element) throws IllegalStateException, SyntaxException {
        if (element.isTerminal()) {
            var token = element.data;
            if (token.isIdentifier()) {
                return evaluateIdentifier((Identifier) token);
            } else {
                return (Literal) token;
            }
        } else {
            return evaluateElements(element);
        }
    }

    static Literal evaluateElements(TreeNode elements) throws IllegalStateException, SyntaxException {
        if (elements.children.isEmpty()) {
            throw new IllegalStateException("Evaluation called with an empty list");
        } 

        var firstElement = elements.children.get(0);

        if (firstElement.isTerminal()) {
            var token = firstElement.data;
            if (token.isIdentifier()) {
                if (ProgramBuiltin.isBuiltIn((Identifier) token)) {
                    var values = new ArrayList<Literal>();
                    for (int i = 1; i < elements.children.size(); ++i) {
                        values.add(evaluateElement(elements.children.get(i)));
                    }
                    return ProgramBuiltin.executeBuiltin((Identifier) token, values);
                } else {
                    // TODO: Finish when user-defined
                    // functions are available
                    throw new IllegalStateException("Encountered not a built-in function");
                }
            }
            else /* if (token.isLiteral())  */{
                var values = new ArrayList<Literal>();
                for (int i = 1; i < elements.children.size(); ++i) {
                    values.add(evaluateElement(elements.children.get(i)));
                }
                if (!values.isEmpty()) {
                    return values.get(values.size() - 1);
                } else throw new IllegalStateException("Encountered an empty list");
            }
        }
        // TODO: Enhance the code quality
        else {
            var values = new ArrayList<Literal>();
            for (int i = 1; i < elements.children.size(); ++i) {
                values.add(evaluateElement(elements.children.get(i)));
            }
            if (!values.isEmpty()) {
                return values.get(values.size() - 1);
            } else throw new IllegalStateException("Encountered not a built-in function");

        }
    }

    static public Literal execute(TreeNode node) throws IllegalStateException, SyntaxException {
        var results = new ArrayList<Literal>();
        for (var child : node.children) {
            results.add(evaluateElement(child));
        }
        return results.get(results.size() - 1);
    }
}
