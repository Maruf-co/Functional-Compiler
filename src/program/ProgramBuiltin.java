package program;

import java.util.ArrayList;

import tokens.Identifier;
import tokens.Literal;
import tokens.NumberLiteral;

public class ProgramBuiltin {
    static String[] builtins = {
        "plus",
        "minus",
        "divide",
        "times",
        "head",
        "tail",
        "cons", 
        "equal",
        "nonequal",
        "less",
        "lesseq",
        "greater",
        "greatereq",
        "isint",
        "isreal",
        "isbool",
        "isnull",
        "isatom",
        "islist",
        "and",
        "or",
        "xor",
        "not",
        "eval"
    };

    static boolean isBuiltIn(Identifier identifier) {
        for (var builtin : ProgramBuiltin.builtins) {
            if (builtin.equals(identifier.getValue())) {
                return true;
            }
        } return false;

    }

    static Literal executeBuiltin(Identifier builtin, ArrayList<Literal> literals) throws SyntaxException {
        switch (builtin.getValue()) {
            case "plus": {
                if (literals.size() != 2) {
                    throw new SyntaxException("plus expects two arguments, got " + literals.size());
                } 
                for (int i = 0; i < literals.size(); i++) {
                    var literal = literals.get(i);
                    if (literal.getLiteralType() != Literal.LiteralType.NUMBER) {
                        // throw a syntax error reporting the
                        // index of incorrect argument
                        throw new SyntaxException("plus expects a number at index " + i);
                    }
                }
                return new NumberLiteral(
                    ((NumberLiteral) literals.get(0)).getValue() + ((NumberLiteral) literals.get(1)).getValue()
                );
            }
            case "minus": {
                if (literals.size() != 2) {
                    throw new SyntaxException("minus expects two arguments, got " + literals.size());
                } 
                for (int i = 0; i < literals.size(); i++) {
                    var literal = literals.get(i);
                    if (literal.getLiteralType() != Literal.LiteralType.NUMBER) {
                        // throw a syntax error reporting the
                        // index of incorrect argument
                        throw new SyntaxException("minus expects a number at index " + i);
                    }
                }
                return new NumberLiteral(
                    ((NumberLiteral) literals.get(0)).getValue() - ((NumberLiteral) literals.get(1)).getValue()
                );
            }
            case "divide": {
                if (literals.size() != 2) {
                    throw new SyntaxException("divide expects two arguments, got " + literals.size());
                } 
                for (int i = 0; i < literals.size(); i++) {
                    var literal = literals.get(i);
                    if (literal.getLiteralType() != Literal.LiteralType.NUMBER) {
                        // throw a syntax error reporting the
                        // index of incorrect argument
                        throw new SyntaxException("divide expects a number at index " + i);
                    }
                }
                return new NumberLiteral(
                    ((NumberLiteral) literals.get(0)).getValue() / ((NumberLiteral) literals.get(1)).getValue()
                );
            }
            case "times": {
                if (literals.size() != 2) {
                    throw new SyntaxException("times expects two arguments, got " + literals.size());
                } 
                for (int i = 0; i < literals.size(); i++) {
                    var literal = literals.get(i);
                    if (literal.getLiteralType() != Literal.LiteralType.NUMBER) {
                        // throw a syntax error reporting the
                        // index of incorrect argument
                        throw new SyntaxException("times expects a number at index " + i);
                    }
                }
                return new NumberLiteral(
                    ((NumberLiteral) literals.get(0)).getValue() * ((NumberLiteral) literals.get(1)).getValue()
                );
            }
            default:
                throw new IllegalStateException("Not a builtin: " + builtin.getValue());
        }
    
    }
}
