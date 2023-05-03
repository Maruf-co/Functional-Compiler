package program;

import java.util.ArrayList;

import tokens.Identifier;
import tokens.Literal;

public class ProgramState {
    // A storage of all the variables
    // and their values
    class Variable {
        Identifier identifier;
        Literal value;

        Variable(Identifier identifier, Literal value) {
            this.identifier = identifier;
            this.value = value;
        }
    }

    // Map of all the variables
    // and their values
    ArrayList<Variable> variables = new ArrayList<>();

    // Checks if a variable is defined
    boolean isDefined(Identifier identifier) {
        for (var variable : variables) {
            if (variable.identifier.getValue().equals(identifier.getValue())) {
                return true;
            }
        } return false;
    }

    // Gets the value of a variable
    Literal getValue(Identifier identifier) {
        for (var variable : variables) {
            if (variable.identifier.getValue().equals(identifier.getValue())) {
                return variable.value;
            }
        } return null;
    }

    // Sets the value of a variable
    void setValue(Identifier identifier, Literal value) {
        for (var variable : variables) {
            if (variable.identifier.getValue().equals(identifier.getValue())) {
                variable.value = value;
                return;
            }
        } variables.add(new Variable(identifier, value));
    }
    
}
