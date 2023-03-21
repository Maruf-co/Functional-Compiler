# Functional-Compiler
Compiler intended to compile functional language - reduced version of LISP

# Lexer
Lexical analysis is performed with a help of JFlex utility. To be able to run the program you should firstly generated a `Lexer` class. To do it you should have JFlex install and configured on your computer.

```bash
jflex ./lexer/lisp.flex -d ./src/lex
```

In case you are not able to run jflex, consider using pre-generated `Lexer.java` file 

# Visualizing 

To visualize the output of a parser, you should have graphviz utility pre-installed and `dot` command available in your command-line interface. Run `Main.java` having a language snippet stored in `input.txt`. Results will be two files: `output.dot` that contains an input string in DOT language for the graphviz and `output.png`. 