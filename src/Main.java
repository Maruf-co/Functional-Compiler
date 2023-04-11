
//import syntax.Parser.

import tokens.*;
import syntax.LISPParser;

import javax.swing.tree.TreeNode;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;


public class Main {

    public static ArrayList<Token> tokens = new ArrayList<>();

//

    private static Literal validatePlus(Literal elem1, Literal elem2) {

        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit)) {
            var res = Integer.parseInt(elem1.content) + Integer.parseInt(elem2.content);
            return new Literal(String.valueOf(res));
        } else throw new IllegalStateException("Plus function required 2 Integers");

    }

    private static Literal validateMinus(Literal elem1, Literal elem2) {

        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit)) {
            var res = Integer.parseInt(elem1.content) - Integer.parseInt(elem2.content);
            return new Literal(String.valueOf(res));
        } else throw new IllegalStateException("Minus function required 2 Integers");

    }

    private static Literal validateTimes(Literal elem1, Literal elem2) {

        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit)) {
            var res = Integer.parseInt(elem1.content) * Integer.parseInt(elem2.content);
            return new Literal(String.valueOf(res));
        } else throw new IllegalStateException("Times function required 2 Integers");

    }

    private static Literal validateDivide(Literal elem1, Literal elem2) {

        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit)) {
            var res = Integer.parseInt(elem1.content) * Integer.parseInt(elem2.content);
            return new Literal(String.valueOf(res));
        } else throw new IllegalStateException("Divide function required 2 Integers");

    }

    /**
     * Equal
     * @param elem1
     * @param elem2
     * @return
     */
    private static Literal validateEqual(Literal elem1, Literal elem2) {
        // Проверяем, что сравнивают 2 разных типа
        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && !((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");
        if (!((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");


        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit)) {
            if (Integer.parseInt(elem1.content) == Integer.parseInt(elem2.content)) return new Literal("true");
            else return new Literal("false");
        } else if (elem1.content == elem2.content) {
            return new Literal("true");
        }
        else return new Literal("false");

    }

    /**
     * NonEqual
     * @param elem1
     * @param elem2
     * @return
     */
    private static Literal validateNonEqual(Literal elem1, Literal elem2) {
        // Проверяем, что сравнивают 2 разных типа
        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && !((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");
        if (!((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");


        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit)) {
            if (Integer.parseInt(elem1.content) != Integer.parseInt(elem2.content)) return new Literal("true");
            else return new Literal("false");
        } else if (elem1.content != elem2.content) {
            return new Literal("true");
        }
        else return new Literal("false");

    }

    /**
     * Less
     * @param elem1
     * @param elem2
     * @return
     */
    private static Literal validateLess(Literal elem1, Literal elem2) {
        // Проверяем, что сравнивают 2 разных типа
        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && !((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");
        if (!((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");


        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit)) {
            if (Integer.parseInt(elem1.content) < Integer.parseInt(elem2.content)) return new Literal("true");
            else return new Literal("false");
        }
        else throw new IllegalStateException("Less function required 2 Integers");

    }

    /**
     * LessEq
     * @param elem1
     * @param elem2
     * @return
     */
    private static Literal validateLessEq(Literal elem1, Literal elem2) {
        // Проверяем, что сравнивают 2 разных типа
        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && !((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");
        if (!((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");


        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit)) {
            if (Integer.parseInt(elem1.content) <= Integer.parseInt(elem2.content)) return new Literal("true");
            else return new Literal("false");
        }
        else throw new IllegalStateException("LessEq function required 2 Integers");

    }

    /**
     * Greater
     * @param elem1
     * @param elem2
     * @return
     */
    private static Literal validateGreater(Literal elem1, Literal elem2) {
        // Проверяем, что сравнивают 2 разных типа
        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && !((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");
        if (!((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");


        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit)) {
            if (Integer.parseInt(elem1.content) > Integer.parseInt(elem2.content)) return new Literal("true");
            else return new Literal("false");
        }
        else throw new IllegalStateException("Greater function required 2 Integers");

    }

    /**
     * GreaterEq
     * @param elem1
     * @param elem2
     * @return
     */
    private static Literal validateGreateEq(Literal elem1, Literal elem2) {
        // Проверяем, что сравнивают 2 разных типа
        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && !((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");
        if (!((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit))
            throw new IllegalStateException("Comparison two different types is not allowed");


        if (((Literal) elem1).content.chars().allMatch(Character::isDigit) && ((Literal) elem2).content.chars().allMatch(Character::isDigit)) {
            if (Integer.parseInt(elem1.content) >= Integer.parseInt(elem2.content)) return new Literal("true");
            else return new Literal("false");
        }
        else throw new IllegalStateException("GreaterEq function required 2 Integers");

    }

    private static Token recursivelyReturnToken(LISPParser.TreeNode<Token> node) {
        if (node.data instanceof Identifier) {
            return node.data;
        } else if (node.data instanceof Literal) {
            return node.data;
        } else if (Objects.equals(node.data, "List")) { // а кому сейчас легко?

            var listChildren = node.children;

            // check brackets
            if (!(Objects.equals(listChildren.get(0).data, "LeftBracket") && Objects.equals(listChildren.get(listChildren.size() - 1).data, "RightBracket")))
                throw new IllegalStateException("List must contain brackets");

            // check list consist of 3 elements (lb, elements, rb)
            if (node.children.size() != 3) throw new IllegalStateException("List must contain Elements");

            return validateElements(node.children.get(1));

        }
        System.out.println("Should not happen, bro!");
        return new Literal("pizdec!");
    }

    /**
     * Setq
     * @param node
     * @return
     */
    private static Literal evalSetq(Token node){
        if(node instanceof Literal) return (Literal) node;
        else throw new IllegalStateException("Expression evaluation result must be literal");
    }

    /**
     * Isint
     * @param elem
     * @return
     */
    private static Literal isInt(Literal elem) {

        if (((Literal) elem).content.chars().allMatch(Character::isDigit)) {
            return new Literal("true");
        } else return new Literal("false");

    }

    public static Token validateElements(LISPParser.TreeNode<Token> node) {
        var elementsChildren = node.children;

//        if (elementsChildren.get(1).data instanceof Literal && elementsChildren.get(2).data instanceof Literal)
//          System.out.println("Validating " + ((Literal) elementsChildren.get(1).data).content + " and " + ((Literal) elementsChildren.get(2).data).content);
        // Пытаемся понять, является ли это функцией
        if (elementsChildren.get(0).data instanceof Identifier) {
            var funcName = ((Identifier) elementsChildren.get(0).data).identifier;

            /**
             * Arithmetics
             */
            if (Objects.equals(funcName, "plus")) {
                // проверяем, что размер оставшихся элементов 2
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("Plus function needs 2 arguments, provided " + elementsChildren.size());


                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));


                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validatePlus((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("Plus function required 2 Literals");
            } else if (Objects.equals(funcName, "minus")) {
                // проверяем, что размер оставшихся элементов 2
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("Minus function needs 2 arguments, provided " + elementsChildren.size());


                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));


                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateMinus((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("Minus function required 2 Literals");

            } else if (Objects.equals(funcName, "times")) {
                // проверяем, что размер оставшихся элементов 2
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("Times function needs 2 arguments, provided " + elementsChildren.size());


                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));


                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateTimes((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("Times function required 2 Literals");

            } else if (Objects.equals(funcName, "divide")) {
                // проверяем, что размер оставшихся элементов 2
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("Divide function needs 2 arguments, provided " + elementsChildren.size());


                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));


                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateDivide((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("Divide function required 2 Literals");

            }

            /**
             * Comparison
             * true false неправильно парсится!!!
             */
            else if (Objects.equals(funcName, "equal")) {
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("Equal function needs 2 arguments, provided " + elementsChildren.size());

                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));

                System.out.println(elem1.getName());
                System.out.println(elem2.getName());
                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateEqual((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("Equal function required 2 Literals");
            }
            else if (Objects.equals(funcName, "nonequal")) {
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("Nonequal function needs 2 arguments, provided " + elementsChildren.size());

                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));

                System.out.println(elem1.getName());
                System.out.println(elem2.getName());
                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateNonEqual((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("Nonequal function required 2 Literals");
            }
            else if (Objects.equals(funcName, "less")) {
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("Less function needs 2 arguments, provided " + elementsChildren.size());

                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));

                System.out.println(elem1.getName());
                System.out.println(elem2.getName());
                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateLess((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("Less function required 2 Literals");
            }
            else if (Objects.equals(funcName, "lesseq")) {
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("LessEq function needs 2 arguments, provided " + elementsChildren.size());

                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));

                System.out.println(elem1.getName());
                System.out.println(elem2.getName());
                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateLessEq((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("LessEq function required 2 Literals");
            }
            else if (Objects.equals(funcName, "greater")) {
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("Greater function needs 2 arguments, provided " + elementsChildren.size());

                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));

                System.out.println(elem1.getName());
                System.out.println(elem2.getName());
                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateGreater((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("Greater function required 2 Literals");
            }
            else if (Objects.equals(funcName, "greatereq")) {
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("GreaterEq function needs 2 arguments, provided " + elementsChildren.size());

                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));

                System.out.println(elem1.getName());
                System.out.println(elem2.getName());
                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateGreateEq((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("GreaterEq function required 2 Literals");
            }

            else if (Objects.equals(funcName, "setq")) {
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("GreaterEq function needs 2 arguments, provided " + elementsChildren.size());

                // проверяем, что атом верный
                if(elementsChildren.get(1).data instanceof Identifier){}
                else throw new IllegalStateException("Setq operation needs Identifier as an Atom");

                var expr = recursivelyReturnToken(elementsChildren.get(2));

                return evalSetq(expr);

            }
            else if (Objects.equals(funcName, "isint")) {
                if (elementsChildren.size() != 2)
                    throw new IllegalStateException("Isint function needs 1 arguments, provided " + elementsChildren.size());

                var elem = recursivelyReturnToken(elementsChildren.get(1))
                if(elem instanceof Identifier){
                    // !!! здесь нужно пытаться вытащить переменную
                }
                else if (elem instanceof Literal) return isInt((Literal) elem);
                else throw new IllegalStateException("Isint requires an Element");

            }


            // если это не функция, а просто Identifier
            else {
                System.out.println(funcName);
                System.out.println("Not a function");
                return elementsChildren.get(0).data;
            }
        } else if (node.data instanceof Identifier) {
            return node.data;
        } else if (node.data instanceof Literal) {
            return node.data;
        }
        // иначе быть не должно
        System.out.println("???");
        return node.data;
    }

    public static void main(String[] args) throws IOException {
//       visualize();
        var reader = new FileReader("input.txt");
        ArrayDeque queueue = new ArrayDeque<LISPParser.TreeNode>();
        var lexer = new LISPParser.LISPLexer(reader);
        var parser = new LISPParser(lexer);
        parser.parse();
        LISPParser.TreeNode node = LISPParser.node;
        queueue.add(node);
        while (!queueue.isEmpty()) {
            LISPParser.TreeNode p = (LISPParser.TreeNode) queueue.poll();
            if (p.data != null) {
                if (p.data instanceof Identifier) {
                    System.out.printf("Identifier@%s\n", ((Identifier) p.data).identifier) ;
                }
                else if (p.data instanceof Literal) {
                    System.out.printf("Literal[%s]\n", ((Literal) p.data).content) ;
                } else {
                    System.out.println(p.data.toString());
                }
            }
            for (Object x: p.children) {
                queueue.add(x);
            }
        }
        FileWriter dotOutput = new FileWriter("output.dot");
        var dot = Visualize.TreeToPNG(LISPParser.node);
        dotOutput.write(dot);
        dotOutput.close();
        String[] exec = {
            "dot", "-Tpng", "-o output.png", "output.dot"
        };
        var p = Runtime.getRuntime().exec(exec);
        try {
            System.out.println(p.waitFor());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        var child = (LISPParser.TreeNode) node.children.get(0);
        var nextChild = (LISPParser.TreeNode) child.children.get(0);

        // var elements = ((LISPParser.TreeNode) nextChild.children.get(1));
        var test = validateElements(child);
        System.out.println(((Literal) test).content);
//        while (!queueue.isEmpty()) {
//            LISPParser.TreeNode p = (LISPParser.TreeNode) queueue.poll();
//            if (p.data != null) {
//                if (p.data instanceof Identifier) {
//                    System.out.printf("Identifier@%s\n", ((Identifier) p.data).identifier) ;
//                }
//                else if (p.data instanceof Literal) {
//                    System.out.printf("Literal[%s]\n", ((Literal) p.data).content) ;
//                } else {
//                    System.out.println(p.data.toString());
//                }
//            }
//            for (Object x: p.children) {
//                queueue.add(x);
//            }
//        }

    }
    static void printTokens() {
        for (Token token : tokens) {
            System.out.print(token.getName() + "");
        }
    }

    // static void visualize() throws IOException {
    //     FileWriter dotOutput = new FileWriter("output.dot");
    //     LISPLexer lexer = new LISPLexer(new FileReader("input.txt"));
    //     LISPParser parser = new LISPParser(lexer);
    //     if (parser.parse()) {
    //         System.out.println("Parsing Result = SUCCESS");
    //         var dot = Visualize.TreeToPNG(LISPParser.node);
    //         dotOutput.write(dot);
    //         dotOutput.close();
    //         String[] exec = {
    //             "dot", "-Tpng", "-o output.png", "output.dot"       
    //         };
    //         var p = Runtime.getRuntime().exec(exec);
    //         try {
    //             System.out.println(p.waitFor());
    //         } catch (InterruptedException e) {
    //             // TODO Auto-generated catch block
    //             e.printStackTrace();
    //         }
    //     }   
    //     return;
    // }
}
