package program;

import java.util.ArrayList;

import syntax.LISPParser.TreeNode;
import tokens.*;

public class ProgramExecution {

    static LiteralToken evaluateIdentifier(Identifier identifier, ProgramState state) throws SyntaxException {
        if (state.isDefined(identifier)) {
            return state.getValue(identifier);
        } else {
            throw new SyntaxException("Variable " + identifier.getValue() + " is not defined");
        }
    }


    static LiteralToken evaluateElement(TreeNode element, ProgramState state) throws IllegalStateException, SyntaxException {
        if (element.isTerminal()) {
            var token = element.data;
            if (token.isIdentifier()) {
                return evaluateIdentifier((Identifier) token, state);
            } else {
                return (LiteralToken) token;
            }
        } else {
            return evaluateElements(element, state);
        }
    }

    static LiteralToken evaluateElementInLocalContext(TreeNode element, ProgramState state) throws IllegalStateException, SyntaxException {
        var result = evaluateElement(element, state);
        return result.getLiteralType() == LiteralToken.LiteralType.RETURN ? ((ReturnLiteral) result).getValue() : result;
    }

    static LiteralToken evaluateElements(TreeNode elements, ProgramState state) throws IllegalStateException, SyntaxException {
        if (elements.children.isEmpty()) {
            throw new IllegalStateException("Evaluation called with an empty list");
        }

        var firstElement = elements.children.get(0);

        if (firstElement.isTerminal()) {
            var token = firstElement.data;
            if (token.isIdentifier()) {
                var identifierToken = (Identifier) token;
                if (ProgramBuiltin.isBuiltIn(identifierToken)) {
                    return ProgramBuiltin.executeBuiltin(identifierToken, elements, state);
                } else if (ProgramBuiltin.isListOperation(identifierToken)) {
                    return ProgramBuiltin.executeListOperations(identifierToken, elements, state);
                } else if (ProgramBuiltin.isLoop((Identifier) token)) {
                    return ProgramBuiltin.executeLoop(elements, state);
                } else if (ProgramDeclaration.isDeclaration(identifierToken)) {
                    return ProgramDeclaration.executeUtility(identifierToken, elements, state);
                } else if (state.isFunctionDefined(identifierToken)) {
                   return ProgramDeclaration.executeFunction(identifierToken, elements, state);
                } else {
                    var evaluatedChildren = new ArrayList<LiteralToken>();
                    for (var child : elements.children) {
                        evaluatedChildren.add(evaluateElement(child, state));
                    }

                    if (evaluatedChildren.isEmpty()) throw new IllegalStateException("Encountered an empty list");

                    var compositeLiteral = new CompositeLiteral(evaluatedChildren);
                    return compositeLiteral;
                }
            } else /* if (token.isLiteral())  */ {
                var evaluatedChildren = new ArrayList<LiteralToken>();
                for (var child : elements.children) {
                    evaluatedChildren.add(evaluateElement(child, state));
                }

                var compositeLiteral = new CompositeLiteral(evaluatedChildren);
                if (evaluatedChildren.isEmpty()) throw new IllegalStateException("Encountered an empty list");

                return compositeLiteral;

            }
        }
        // TODO: Enhance the code quality
        else {
            var values = new ArrayList<LiteralToken>();
            for (int i = 0; i < elements.children.size(); ++i) {
                var evaluationResult = evaluateElement(elements.children.get(i), state);
                if (evaluationResult instanceof ReturnLiteral) {
                    return evaluationResult;
                }
                values.add(evaluationResult);
            }
            if (!values.isEmpty()) {
                return values.get(values.size() - 1);
            } else throw new IllegalStateException("Encountered not a built-in function");

        }
    }

    static public LiteralToken execute(TreeNode node, ProgramState state) throws IllegalStateException, SyntaxException {
        var results = new ArrayList<LiteralToken>();
        for (var child : node.children) {
            var evaluationResult = evaluateElement(child, state);
            if (evaluationResult instanceof ReturnLiteral) {
                return evaluationResult;
            }
            results.add(evaluationResult);
        }
        return results.get(results.size() - 1);
    }
}
