package program;

import java.util.List;

import syntax.LISPParser.TreeNode;
import tokens.Identifier;
import tokens.Literal;

public class ProgramDeclaration {
    static String[] declarations = {
       "setq"
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
        Literal variableValue = ProgramExecution.evaluateElement(node.children.get(2), state);
        if (node.children.get(1).isTerminal()) {
            Identifier variableName = (Identifier) node.children.get(1).data;
            state.setValue(variableName, variableValue);
            return variableValue;
        } else {
            throw new SyntaxException("Variable name must be an identifier");
        }
    }

    // Executes the utility function
    public static Literal executeUtility(Identifier utility, TreeNode node, ProgramState state) throws SyntaxException {
        switch (utility.getValue()) {
            case "setq": {
                return setq(node, state);
            }
            default: return null;
        }
    }      
}