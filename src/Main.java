
//import syntax.Parser.

import tokens.*;
import syntax.LISPParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;


public class Main {

    public static ArrayList<Token> tokens = new ArrayList<>();

//

    private static Literal validateAnd(Literal elem1, Literal elem2) {

        if (Objects.equals(elem1.content, "true") || Objects.equals(elem1.content, "false") && Objects.equals(elem2.content, "true") || Objects.equals(elem2.content, "false")) {
            var res = Boolean.valueOf(elem1.content) && Boolean.valueOf(elem2.content);
            return new Literal(String.valueOf(res));
        } else throw new IllegalStateException("And function required 2 Booleans");

    }

    private static Literal validateOr(Literal elem1, Literal elem2) {

        if (Objects.equals(elem1.content, "true") || Objects.equals(elem1.content, "false") && Objects.equals(elem2.content, "true") || Objects.equals(elem2.content, "false")) {
            var res = Boolean.parseBoolean(elem1.content) || Boolean.parseBoolean(elem2.content);
            return new Literal(String.valueOf(res));
        } else throw new IllegalStateException("Or function required 2 Booleans");

    }

    private static Literal validateXor(Literal elem1, Literal elem2) {

        if (Objects.equals(elem1.content, "true") || Objects.equals(elem1.content, "false") && Objects.equals(elem2.content, "true") || Objects.equals(elem2.content, "false")) {
            var res = !(Boolean.parseBoolean(elem1.content) || Boolean.parseBoolean(elem2.content));
            return new Literal(String.valueOf(res));
        } else throw new IllegalStateException("Xor function required 2 Booleans");

    }

    private static Literal validateNot(Literal elem1) {

        if (Objects.equals(elem1.content, "true") || Objects.equals(elem1.content, "false")) {
            var res = !Boolean.parseBoolean(elem1.content);
            return new Literal(String.valueOf(res));
        } else throw new IllegalStateException("Not function required Boolean");

    }

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
        } else if (Objects.equals(elem1.content, elem2.content)) {
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
    private static Literal validateSetQ(Token node){
        if(node instanceof Literal) return (Literal) node;
        else throw new IllegalStateException("Expression evaluation result must be literal");
    }

    /**
     * Isint
     * @param elem
     * @return
     */
    private static Literal validateIsInt(Literal elem) {

        if (((Literal) elem).content.chars().allMatch(Character::isDigit)) {
            return new Literal("true");
        } else return new Literal("false");

    }

    /**
     * IsBool
     * @param elem
     * @return
     */
    private static Literal validateIsBool(Literal elem) {

        if (Objects.equals(((Literal) elem).content, "true") || Objects.equals(((Literal) elem).content, "false")) {
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

                return validateSetQ(expr);

            }

            /**
             * Predicates
             */
            else if (Objects.equals(funcName, "isint")) {
                if (elementsChildren.size() != 2)
                    throw new IllegalStateException("Isint function needs 1 arguments, provided " + elementsChildren.size());

                var elem = recursivelyReturnToken(elementsChildren.get(1));
                if(elem instanceof Identifier){
                    // !!! здесь нужно пытаться вытащить переменную
                }
                else if (elem instanceof Literal) return validateIsInt((Literal) elem);
                else throw new IllegalStateException("Isint requires an Element");

            }
            else if (Objects.equals(funcName, "isbool")) {
                if (elementsChildren.size() != 2)
                    throw new IllegalStateException("Isbool function needs 1 arguments, provided " + elementsChildren.size());

                var elem = recursivelyReturnToken(elementsChildren.get(1));
                if(elem instanceof Identifier){
                    // !!! здесь нужно пытаться вытащить переменную
                }
                else if (elem instanceof Literal) return validateIsBool((Literal) elem);
                else throw new IllegalStateException("Isbool requires an Element");

            }
            else if (Objects.equals(funcName, "isatom")) {
                if (elementsChildren.size() != 2)
                    throw new IllegalStateException("Isatom function needs 1 arguments, provided " + elementsChildren.size());

                var elem = recursivelyReturnToken(elementsChildren.get(1));
                if(elem instanceof Identifier){
                    return new Literal("true");
                }
                else return new Literal("false");

            }
            else if (Objects.equals(funcName, "islist")) {
                if (elementsChildren.size() != 2)
                    throw new IllegalStateException("Islist function needs 1 arguments, provided " + elementsChildren.size());

                if(Objects.equals(elementsChildren.get(1).data, "List")){
                    return new Literal("true");
                }
                else return new Literal("false");

            }
            /**
             * Logical operations
             */
            else if (Objects.equals(funcName, "and")) {
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("And function needs 2 arguments, provided " + elementsChildren.size());

                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));

                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateAnd((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("And function required 2 Literals");
            }

            else if (Objects.equals(funcName, "or")) {
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("Or function needs 2 arguments, provided " + elementsChildren.size());

                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));

                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateOr((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("Or function required 2 Literals");
            }

            else if (Objects.equals(funcName, "xor")) {
                if (elementsChildren.size() != 3)
                    throw new IllegalStateException("Xor function needs 2 arguments, provided " + elementsChildren.size());

                // берем 2 слогаемых
                var elem1 = recursivelyReturnToken(elementsChildren.get(1));

                var elem2 = recursivelyReturnToken(elementsChildren.get(2));

                if (elem1 instanceof Literal && elem2 instanceof Literal) {
                    return validateXor((Literal) elem1, (Literal) elem2);
                } else throw new IllegalStateException("Xor function required 2 Literals");
            }

            else if (Objects.equals(funcName, "not")) {
                if (elementsChildren.size() != 2)
                    throw new IllegalStateException("Not function needs 1 argument, provided " + elementsChildren.size());

                var elem1 = recursivelyReturnToken(elementsChildren.get(1));


                if (elem1 instanceof Literal) {
                    return validateNot((Literal) elem1);
                } else throw new IllegalStateException("Not function required 2 Literals");
            }

            else if (Objects.equals(funcName, "eval")) {
                if (elementsChildren.size() != 2)
                    throw new IllegalStateException("Eval function needs 1 argument, provided " + elementsChildren.size());

                return recursivelyReturnToken(elementsChildren.get(1));



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
        var reader = new FileReader("/Users/k.tyulebaeva/Desktop/Functional-Compiler/src/input.txt");
        ArrayDeque queueue = new ArrayDeque<LISPParser.TreeNode>();
        var lexer = new LISPParser.LISPLexer(reader);
        var parser = new LISPParser(lexer);
        parser.parse();
        LISPParser.TreeNode node = LISPParser.node;
        queueue.add(node);
        var child = (LISPParser.TreeNode) node.children.get(0);
        var nextChild = (LISPParser.TreeNode) child.children.get(0);

        // var elements = ((LISPParser.TreeNode) nextChild.children.get(1));
        var test = validateElements(child);
        System.out.println(((Literal) test).content);
//
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
//        FileWriter dotOutput = new FileWriter("output.dot");
//        var dot = Visualize.TreeToPNG(LISPParser.node);
//        dotOutput.write(dot);
//        dotOutput.close();
//        String[] exec = {
//            "dot", "-Tpng", "-o output.png", "output.dot"
//        };
//        var p = Runtime.getRuntime().exec(exec);
//        try {
//            System.out.println(p.waitFor());
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        var child = (LISPParser.TreeNode) node.children.get(0);
//        var nextChild = (LISPParser.TreeNode) child.children.get(0);
//
//        // var elements = ((LISPParser.TreeNode) nextChild.children.get(1));
//        var test = validateElements(child);
//        System.out.println(((Literal) test).content);
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
