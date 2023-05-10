package program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sun.source.tree.Tree;
import syntax.LISPParser;
import tokens.Identifier;
import tokens.Literal;

public class ProgramState {

    public ProgramState withVariables(List<Variable> variables) {
        this.variables = new ArrayList<>(variables);
        return this;
    }

    public ProgramState withFunctions(Map<String, Function> functions) {
        this.functions = new HashMap<>(functions);
        return this;
    }

    // A storage of all the variables
    // and their values
    static class Variable {
        Identifier identifier;
        Literal value;

        Variable(Identifier identifier, Literal value) {
            this.identifier = identifier;
            this.value = value;
        }
    }

    static class Function {
        Identifier identifier;
        List<Identifier> arguments;
        LISPParser.TreeNode elements;

        Function(Identifier identifier, List<Identifier> arguments, LISPParser.TreeNode elements) {
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
    boolean isDefined(Identifier identifier) {
        for (var variable : variables) {
            if (variable.identifier.getValue().equals(identifier.getValue())) {
                return true;
            }
        }
        return false;
    }

    // Gets the value of a variable
    Literal getValue(Identifier identifier) {
        for (var variable : variables) {
            if (variable.identifier.getValue().equals(identifier.getValue())) {
                return variable.value;
            }
        }
        return null;
    }

    // Sets the value of a variable
    void setValue(Identifier identifier, Literal value) {
        for (var variable : variables) {
            if (variable.identifier.getValue().equals(identifier.getValue())) {
                variable.value = value;
                return;
            }
        }
        variables.add(new Variable(identifier, value));
    }

    boolean isFunctionDefined(Identifier identifier) {
        return functions.containsKey(identifier.getValue());
    }

    Function getFunction(Identifier identifier) {
        return functions.get(identifier.getValue());
    }

    void setFunction(Identifier identifier, List<Identifier> arguments, LISPParser.TreeNode node) {
        functions.put(identifier.getValue(), new Function(identifier, arguments, node));
    }

}
