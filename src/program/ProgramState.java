package program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sun.source.tree.Tree;
import syntax.LISPParser;
import tokens.IdentifierToken;
import tokens.LiteralToken;

public class ProgramState {

    public ProgramState withVariables(List<Variable> variables) {
        this.variables = new ArrayList<>();
        for (var variable : variables) {
            this.variables.add(new Variable(variable.identifier, variable.value));
        }
        return this;
    }

    public ProgramState withFunctions(Map<String, Function> functions) {
        this.functions = new HashMap<>(functions);
        return this;
    }

    // A storage of all the variables
    // and their values
    static class Variable {
        IdentifierToken identifier;
        LiteralToken value;

        Variable(IdentifierToken identifier, LiteralToken value) {
            this.identifier = identifier;
            this.value = value;
        }
    }

    static class Function {
        IdentifierToken identifier;
        List<IdentifierToken> arguments;
        LISPParser.TreeNode elements;

        Function(IdentifierToken identifier, List<IdentifierToken> arguments, LISPParser.TreeNode elements) {
            this.identifier = identifier;
            this.arguments = arguments;
            this.elements = elements;
        }
    }

    // Map of all the variables
    // and their values
    ArrayList<Variable> variables = new ArrayList<>();

    HashMap<String, Function> functions = new HashMap<>();

    // Checks if a variable is defined
    boolean isDefined(IdentifierToken identifier) {
        for (var variable : variables) {
            if (variable.identifier.getValue().equals(identifier.getValue())) {
                return true;
            }
        }
        return false;
    }

    // Gets the value of a variable
    LiteralToken getValue(IdentifierToken identifier) {
        for (var variable : variables) {
            if (variable.identifier.getValue().equals(identifier.getValue())) {
                return variable.value;
            }
        }
        return null;
    }

    // Sets the value of a variable
    void setValue(IdentifierToken identifier, LiteralToken value) {
        for (var variable : variables) {
            if (variable.identifier.getValue().equals(identifier.getValue())) {
                variable.value = value;
                return;
            }
        }
        variables.add(new Variable(identifier, value));
    }

    boolean isFunctionDefined(IdentifierToken identifier) {
        return functions.containsKey(identifier.getValue());
    }

    Function getFunction(IdentifierToken identifier) {
        return functions.get(identifier.getValue());
    }

    void setFunction(IdentifierToken identifier, List<IdentifierToken> arguments, LISPParser.TreeNode node) {
        functions.put(identifier.getValue(), new Function(identifier, arguments, node));
    }

}