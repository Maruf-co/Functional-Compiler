package program;

import java.util.ArrayList;

import syntax.LISPParser.TreeNode;
import tokens.*;

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
                var identifierToken = (Identifier) token;
                if (ProgramBuiltin.isBuiltIn(identifierToken)) {
                    return ProgramBuiltin.executeBuiltin(identifierToken, elements, state);
                }
                else if (ProgramBuiltin.isListOperation(identifierToken)){
                    switch(identifierToken.getValue()){
                        case "head": {
                            if (elements.children.size() != 2)
                                throw new SyntaxException("head expected 1 element, got " + elements.children.size());
                            var list = elements.children.get(1);

                            if (list.isTerminal()) throw new SyntaxException("head expected list, got terminal");

                            if (list.children.get(0).isTerminal() && list.children.get(0).data.isIdentifier() && (ProgramBuiltin.isBuiltIn((Identifier) list.children.get(0).data) || ProgramBuiltin.isLoop((Identifier) list.children.get(0).data) || ProgramBuiltin.isListOperation((Identifier) list.children.get(0).data) || ProgramDeclaration.isDeclaration((Identifier) list.children.get(0).data)))
                                throw new SyntaxException("head expected sample list without buildin functions");



                            return evaluateElement(list.children.get(0), state);
                        }
                        case "tail": {
                            if (elements.children.size() != 2)
                                throw new SyntaxException("tail expected 1 element, got " + elements.children.size());

                            var list = elements.children.get(1);
                            if (list.isTerminal()) throw new SyntaxException("tail expected list, got terminal");
                            else {
                                var evaluatedChildren = new ArrayList<Literal>();
                                for (int i = 1; i < list.children.size(); i++) {
                                    evaluatedChildren.add(evaluateElement(list.children.get(i), state));
                                }
                                if (evaluatedChildren.isEmpty())
                                    throw new IllegalStateException("Encountered an empty list");

                                var compositeLiteral = new CompositeLiteral(evaluatedChildren);
                                return compositeLiteral;
                            }
                        }
                    }
                    return new NumberLiteral(3.0);
                }
                else if(ProgramBuiltin.isLoop((Identifier) token)){
                    if(elements.children.size() != 3) throw new SyntaxException("while expects two arguments, got "+ elements.children.size());

                    var condition = elements.children.get(1);
                    var loopBody = elements.children.get(2);

                    if(evaluateElement(condition, state).getLiteralType() != Literal.LiteralType.BOOLEAN) throw new SyntaxException("while expects bool expression in condition statement");

                    while((Boolean) evaluateElement(condition, state).getValue()){
                        System.out.println("hello");
                        evaluateElement(loopBody, state);
                    }
                    return new NumberLiteral(0.0);
                }
                else if (ProgramDeclaration.isDeclaration(identifierToken)) {
                    return ProgramDeclaration.executeUtility(identifierToken, elements, state);
                }
                else if (state.isFunctionDefined(identifierToken)) {
                    ProgramState.Function function = state.getFunction(identifierToken);

                    var values = new ArrayList<Literal>();
                    for (int i = 1; i < elements.children.size(); ++i) {
                        values.add(evaluateElement(elements.children.get(i), state));
                    }
                    if (values.size() != function.arguments.size()) {
                        throw new SyntaxException("Function requires " + function.arguments.size() + " arguments");
                    }

                    var localState = new ProgramState()
                            .withVariables(state.variables)
                            .withFunctions(state.functions);
                    for (int i = 0; i < values.size(); i++) {
                        localState.setValue(function.arguments.get(i), values.get(i));
                    }
                    return evaluateElement(function.elements, localState);
                } else {
                    var evaluatedChildren = new ArrayList<Literal>();
                    for (var child : elements.children) {
                        evaluatedChildren.add(evaluateElement(child, state));
                    }

                    if (evaluatedChildren.isEmpty()) throw new IllegalStateException("Encountered an empty list");

                    var compositeLiteral = new CompositeLiteral(evaluatedChildren);
                    return compositeLiteral;
                }
            } else /* if (token.isLiteral())  */ {
                var evaluatedChildren = new ArrayList<Literal>();
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
            var values = new ArrayList<Literal>();
            for (int i = 0; i < elements.children.size(); ++i) {
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
