package program;

import java.util.ArrayList;

import syntax.LISPParser.TreeNode;
import tokens.CompositeLiteral;
import tokens.Identifier;
import tokens.Literal;
import tokens.Token;

public class ProgramExecution {

    static Literal evaluateIdentifier(Identifier identifier, ProgramState state) throws SyntaxException {
        if (state.isDefined(identifier)) {
            return state.getValue(identifier);
        } else {
            throw new SyntaxException("Variable " + identifier.getValue() + " is not defined");
        }
    }

    static Literal evaluateElement(TreeNode element, ProgramState state) throws IllegalStateException, SyntaxException {
        if (element.isTerminal()) {
            var token = element.data;
            if (token.isIdentifier()) {
                return evaluateIdentifier((Identifier) token, state);
            } else {
                return (Literal) token;
            }
        } else {
            return evaluateElements(element, state);
        }
    }

    static Literal evaluateElements(TreeNode elements, ProgramState state) throws IllegalStateException, SyntaxException {
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
                        values.add(evaluateElement(elements.children.get(i), state));
                    }
                    return ProgramBuiltin.executeBuiltin((Identifier) token, values, state);
                } 
                else if (ProgramDeclaration.isDeclaration((Identifier) token)) {
                    return ProgramDeclaration.executeUtility((Identifier) token, elements, state);
                }
                else {
                    // TODO: Finish when user-defined
                    // functions are available
                    var evaluatedChildren = new ArrayList<Literal>();
                    for (var child : elements.children) {
                        evaluatedChildren.add(evaluateElement(child, state));
                    }

                    var compositeLiteral = new CompositeLiteral(evaluatedChildren);
                    return compositeLiteral;
                }
            }
            else /* if (token.isLiteral())  */{
                var values = new ArrayList<Literal>();
                for (int i = 1; i < elements.children.size(); ++i) {
                    values.add(evaluateElement(elements.children.get(i), state));
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
                values.add(evaluateElement(elements.children.get(i), state));
            }
            if (!values.isEmpty()) {
                return values.get(values.size() - 1);
            } else throw new IllegalStateException("Encountered not a built-in function");

        }
    }

    static public Literal execute(TreeNode node, ProgramState state) throws IllegalStateException, SyntaxException {
        var results = new ArrayList<Literal>();
        for (var child : node.children) {
            results.add(evaluateElement(child, state));
        }
        return results.get(results.size() - 1);
    }
}
