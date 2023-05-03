package program;

import java.util.ArrayList;

import tokens.Identifier;
import tokens.Literal;
import tokens.NumberLiteral;
import tokens.Token;
import tokens.BooleanLiteral;

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

    static Literal executeBuiltin(Identifier builtin, ArrayList<Literal> tokens, ProgramState state) throws SyntaxException {
        switch (builtin.getValue()) {
            case "plus": {
                if (tokens.size() != 2) {
                    throw new SyntaxException("plus expects two arguments, got " + tokens.size());
                } 
                for (int i = 0; i < tokens.size(); i++) {
                    var token = tokens.get(i);       
                    if (token.isLiteral()) {
                        var literal = (Literal) token;
                        if (literal.getLiteralType() != Literal.LiteralType.NUMBER) {
                            // throw a syntax error reporting the
                            // index of incorrect argument
                            throw new SyntaxException("plus expects a number at index " + i);
                        }
                    } else {
                        // throw a syntax error reporting the
                        // index of incorrect argument
                        throw new SyntaxException("plus expects a number at index " + i);
                    }
                
                }
                return new NumberLiteral(
                    ((NumberLiteral) tokens.get(0)).getValue() + ((NumberLiteral) tokens.get(1)).getValue()
                );
            }
            case "minus": {
                if (tokens.size() != 2) {
                    throw new SyntaxException("minus expects two arguments, got " + tokens.size());
                } 
                for (int i = 0; i < tokens.size(); i++) {
                    var token = tokens.get(i);       
                    if (token.isLiteral()) {
                        var literal = (Literal) token;
                        if (literal.getLiteralType() != Literal.LiteralType.NUMBER) {
                            // throw a syntax error reporting the
                            // index of incorrect argument
                            throw new SyntaxException("minus expects a number at index " + i);
                        }
                    } else {
                        // throw a syntax error reporting the
                        // index of incorrect argument
                        throw new SyntaxException("minus expects a number at index " + i);
                    }
                
                }
                return new NumberLiteral(
                    ((NumberLiteral) tokens.get(0)).getValue() - ((NumberLiteral) tokens.get(1)).getValue()
                );
            }
            case "divide": {
                if (tokens.size() != 2) {
                    throw new SyntaxException("divide expects two arguments, got " + tokens.size());
                } 
                for (int i = 0; i < tokens.size(); i++) {
                    var token = tokens.get(i);       
                    if (token.isLiteral()) {
                        var literal = (Literal) token;
                        if (literal.getLiteralType() != Literal.LiteralType.NUMBER) {
                            // throw a syntax error reporting the
                            // index of incorrect argument
                            throw new SyntaxException("divide expects a number at index " + i);
                        }
                    } else {
                        // throw a syntax error reporting the
                        // index of incorrect argument
                        throw new SyntaxException("divide expects a number at index " + i);
                    }
                
                }
                return new NumberLiteral(
                    ((NumberLiteral) tokens.get(0)).getValue() / ((NumberLiteral) tokens.get(1)).getValue()
                );
            }
            case "times": {
                if (tokens.size() != 2) {
                    throw new SyntaxException("times expects two arguments, got " + tokens.size());
                } 
                for (int i = 0; i < tokens.size(); i++) {
                    var token = tokens.get(i);       
                    if (token.isLiteral()) {
                        var literal = (Literal) token;
                        if (literal.getLiteralType() != Literal.LiteralType.NUMBER) {
                            // throw a syntax error reporting the
                            // index of incorrect argument
                            throw new SyntaxException("times expects a number at index " + i);
                        }
                    } else {
                        // throw a syntax error reporting the
                        // index of incorrect argument
                        throw new SyntaxException("times expects a number at index " + i);
                    }
                
                }
                return new NumberLiteral(
                    ((NumberLiteral) tokens.get(0)).getValue() * ((NumberLiteral) tokens.get(1)).getValue()
                );
            }
            case "head":{
                // FIXME
            }
            case "tail":{
                // FIXME
            }
            case "cons": {
                // FIXME
            }
            case "equal": {
                if (tokens.size() != 2) {
                    throw new SyntaxException("equal expects two arguments, got " + tokens.size());
                }

                var literal_one = tokens.get(0);  
                var literal_two = tokens.get(1);  
                if ((literal_one.getLiteralType() == Literal.LiteralType.STRING) && (literal_two.getLiteralType() == Literal.LiteralType.STRING)) {
                    // throw a syntax error 
                    throw new SyntaxException("equal expects a number or a boolean parameters ");
                }
                if (literal_one.getLiteralType() != literal_two.getLiteralType()) {
                    // throw a syntax error 
                    throw new SyntaxException("equal expects the same type of parameters");
                }
                    

                return new BooleanLiteral(
                    literal_one.getValue().equals(literal_two.getValue())
                );      
            }
            case "nonequal":{
                if (tokens.size() != 2) {
                    throw new SyntaxException("nonequal expects two arguments, got " + tokens.size());
                }
                var token_one = tokens.get(0);  
                var token_two = tokens.get(1);  

                if ((token_one.getLiteralType() == Literal.LiteralType.STRING) && (token_two.getLiteralType() == Literal.LiteralType.STRING)) {
                    // throw a syntax error 
                    throw new SyntaxException("nonequal expects a number or a boolean parameters ");
                }
                if (token_one.getLiteralType() != token_two.getLiteralType()) {
                    // throw a syntax error 
                    throw new SyntaxException("nonequal expects the same type of parameters");
                }
                
                return new BooleanLiteral(
                    !token_one.getValue().equals(token_two.getValue())
                );      
            }
            case "less":{
                if (tokens.size() != 2) {
                    throw new SyntaxException("less expects two arguments, got " + tokens.size());
                }
                var token_one = tokens.get(0);  
                var token_two = tokens.get(1);  
                if (token_one.isLiteral() && token_two.isLiteral()) {
                        var literal_one = new NumberLiteral((Literal) token_one);
                        var literal_two = new NumberLiteral((Literal)  token_two);
                        if ((literal_one.getLiteralType() == Literal.LiteralType.STRING) && (literal_two.getLiteralType() == Literal.LiteralType.STRING)) {
                            // throw a syntax error 
                            throw new SyntaxException("less expects a number or a boolean parameters ");
                        }
                        if (literal_one.getLiteralType() != literal_two.getLiteralType()) {
                            // throw a syntax error 
                            throw new SyntaxException("less expects the same type of parameters");
                        }
                        return new BooleanLiteral(
                            literal_one.getValue() < literal_two.getValue()
                        );

                } else {
                    // throw a syntax error 
                    throw new SyntaxException("incomparible types");
                }

               
            }
            case "lesseq": {
                if (tokens.size() != 2) {
                    throw new SyntaxException("lesseq expects two arguments, got " + tokens.size());
                }
                var token_one = tokens.get(0);  
                var token_two = tokens.get(1);  
                if (token_one.isLiteral() && token_two.isLiteral()) {
                        var literal_one = new NumberLiteral((Literal) token_one);
                        var literal_two = new NumberLiteral((Literal)  token_two);
                        if ((literal_one.getLiteralType() == Literal.LiteralType.STRING) && (literal_two.getLiteralType() == Literal.LiteralType.STRING)) {
                            // throw a syntax error 
                            throw new SyntaxException("lesseq expects a number or a boolean parameters ");
                        }
                        if (literal_one.getLiteralType() != literal_two.getLiteralType()) {
                            // throw a syntax error 
                            throw new SyntaxException("lesseq expects the same type of parameters");
                        }
                        return new BooleanLiteral(
                            literal_one.getValue() <= literal_two.getValue()
                        );

                } else {
                    // throw a syntax error 
                    throw new SyntaxException("incomparible types");
                }
            
            }
            case "greater": {
                if (tokens.size() != 2) {
                    throw new SyntaxException("greater expects two arguments, got " + tokens.size());
                }
                var token_one = tokens.get(0);  
                var token_two = tokens.get(1);  
                if (token_one.isLiteral() && token_two.isLiteral()) {
                        var literal_one = new NumberLiteral((Literal) token_one);
                        var literal_two = new NumberLiteral((Literal)  token_two);
                        if ((literal_one.getLiteralType() == Literal.LiteralType.STRING) && (literal_two.getLiteralType() == Literal.LiteralType.STRING)) {
                            // throw a syntax error 
                            throw new SyntaxException("greater expects a number or a boolean parameters ");
                        }
                        if (literal_one.getLiteralType() != literal_two.getLiteralType()) {
                            // throw a syntax error 
                            throw new SyntaxException("greater expects the same type of parameters");
                        }
                        return new BooleanLiteral(
                            literal_one.getValue() > literal_two.getValue()
                        );

                } else {
                    // throw a syntax error 
                    throw new SyntaxException("incomparible types");
                }  
            }
            case "greatereq": {
                if (tokens.size() != 2) {
                    throw new SyntaxException("greatereq expects two arguments, got " + tokens.size());
                }
                var token_one = tokens.get(0);  
                var token_two = tokens.get(1);  
                if (token_one.isLiteral() && token_two.isLiteral()) {
                        var literal_one = new NumberLiteral((Literal) token_one);
                        var literal_two = new NumberLiteral((Literal)  token_two);
                        if ((literal_one.getLiteralType() == Literal.LiteralType.STRING) && (literal_two.getLiteralType() == Literal.LiteralType.STRING)) {
                            // throw a syntax error 
                            throw new SyntaxException("greatereq expects a number or a boolean parameters ");
                        }
                        if (literal_one.getLiteralType() != literal_two.getLiteralType()) {
                            // throw a syntax error 
                            throw new SyntaxException("greatereq expects the same type of parameters");
                        }
                        return new BooleanLiteral(
                            literal_one.getValue() >= literal_two.getValue()
                        );

                } else {
                    // throw a syntax error 
                    throw new SyntaxException("incomparible types");
                }      
            }
            case "and": {
                if (tokens.size() != 2) {
                    throw new SyntaxException("and expects two arguments, got " + tokens.size());
                }
                var token_one = tokens.get(0);  
                var token_two = tokens.get(1);  

                // Checks if both literals are boolean literals
                if (token_one.getLiteralType() != Literal.LiteralType.BOOLEAN || token_one.getLiteralType() != Literal.LiteralType.BOOLEAN) {
                    // throw a syntax error 
                    throw new SyntaxException("and expects a boolean parameters");
                }
                return new BooleanLiteral(
                    ((BooleanLiteral) token_two).getValue() && ((BooleanLiteral) token_two).getValue()
                );
                
            }
            case "or": {
                if (tokens.size() != 2) {
                    throw new SyntaxException("or expects two arguments, got " + tokens.size());
                }
                var token_one = tokens.get(0);  
                var token_two = tokens.get(1);  

                // Checks if both literals are boolean literals
                if (token_one.getLiteralType() != Literal.LiteralType.BOOLEAN || token_one.getLiteralType() != Literal.LiteralType.BOOLEAN) {
                    // throw a syntax error 
                    throw new SyntaxException("or expects a boolean parameters");
                }
                return new BooleanLiteral(
                    ((BooleanLiteral) token_two).getValue() || ((BooleanLiteral) token_two).getValue()
                );
            }
            case "xor": {
                var token_one = tokens.get(0);  
                var token_two = tokens.get(1);  

                // Checks if both literals are boolean literals
                if (token_one.getLiteralType() != Literal.LiteralType.BOOLEAN || token_one.getLiteralType() != Literal.LiteralType.BOOLEAN) {
                    // throw a syntax error 
                    throw new SyntaxException("xor expects a boolean parameters");
                }
                return new BooleanLiteral(
                    ((BooleanLiteral) token_two).getValue() ^ ((BooleanLiteral) token_two).getValue()
                );
            }
            case "not": {
                if (tokens.size() != 1) {
                    throw new SyntaxException("not expects one argument, got " + tokens.size());
                }
                var token = tokens.get(0);
                if (token.getLiteralType() != Literal.LiteralType.BOOLEAN) {
                    // throw a syntax error 
                    throw new SyntaxException("not expects a boolean parameter");
                }
                return new BooleanLiteral(
                    !((BooleanLiteral) token).getValue()
                );
                
            }
            default:
                throw new IllegalStateException("Not a builtin: " + builtin.getValue());
        }
    
    }
}
