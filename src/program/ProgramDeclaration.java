package program;

import java.util.ArrayList;
import java.util.List;

import syntax.LISPParser.TreeNode;
import tokens.Identifier;
import tokens.Literal;
import tokens.UnitLiteral;

import static program.ProgramExecution.evaluateElement;
import static program.ProgramExecution.evaluateElementInLocalContext;

public class ProgramDeclaration {
    static String[] declarations = {
       "setq", "func"
    };

    // Checks if it is utility 
    // function
    public static boolean isDeclaration(Identifier identifier) {
        for (var utility : ProgramDeclaration.declarations) {
            if (utility.equals(identifier.getValue())) {
                return true;
            }
        } return false;
    }

    public static Literal setq(TreeNode node, ProgramState state) throws SyntaxException {
        Literal variableValue = evaluateElement(node.children.get(2), state);
        if (node.children.get(1).isTerminal()) {
            Identifier variableName = (Identifier) node.children.get(1).data;
            state.setValue(variableName, variableValue);
            return variableValue;
        } else {
            throw new SyntaxException("Variable name must be an identifier");
        }
    }

    public static Literal func(TreeNode node, ProgramState state) throws SyntaxException {
        var arguments = new ArrayList<Identifier>();
        for (var argument : node.children.get(2).children) {
            if (!argument.isTerminal()) {
                throw new SyntaxException("Function arguments must be identifiers");
            }
            Identifier arg = (Identifier) argument.data;
            arguments.add(arg);
        }

        if (node.children.get(1).isTerminal()) {
            Identifier variableName = (Identifier) node.children.get(1).data;
            state.setFunction(variableName, arguments, node.children.get(3));
            return new UnitLiteral();
        } else {
            throw new SyntaxException("Function name must be an identifier");
        }
    }

    public static Literal executeFunction(Identifier identifier, TreeNode node, ProgramState state) throws SyntaxException {
        ProgramState.Function function = state.getFunction(identifier);

        var values = new ArrayList<Literal>();
        for (int i = 1; i < node.children.size(); ++i) {
            values.add(evaluateElement(node.children.get(i), state));
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
        return evaluateElementInLocalContext(function.elements, localState);
    }

    // Executes the utility function
    public static Literal executeUtility(Identifier utility, TreeNode node, ProgramState state) throws SyntaxException {
        switch (utility.getValue()) {
            case "setq": {
                return setq(node, state);
            }
            case "func": {
                return func(node, state);
            }
            default: return null;
        }
    }      
}