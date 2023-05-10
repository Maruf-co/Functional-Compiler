%language "Java"

%define api.prefix {LISPParser}
%define api.parser.class {LISPParser}
%define api.parser.public
%define api.value.type {Object}
%define parse.error verbose


%code imports{
    package syntax;
  import java.io.InputStream;
  import java.io.InputStreamReader;
  import java.io.Reader;
  import java.util.List;
  import java.util.LinkedList;
  import java.io.IOException;
  import java.io.FileReader;
  import java.io.FileWriter;
  import syntax.Yylex;
  import tokens.*;
}

%code {
  public static LISPParser.TreeNode node;
  public static Object yylval;

  public static class TreeNode {
    public Token data;
    public String nodeName;
    public List<LISPParser.TreeNode> children;


    public TreeNode(Token data) {
        this.data = data;
        this.children = new LinkedList<LISPParser.TreeNode>();
    }

    public TreeNode addChild(TreeNode obj) {
        var childNode = obj;
        this.children.add(childNode);
        return this;
    }

    public boolean isTerminal(){
      return false;
    }
  }

  public static class TerminalNode extends TreeNode{
    public TerminalNode(Token data) {
        super(data);
    }

    public boolean isTerminal(){
      return true;
    }
  }
  
  public static class NonTerminalNode extends TreeNode {

    
    public NonTerminalNode(String name) {
        super(null);
        this.nodeName = name;
    }

    public boolean isTerminal(){
      return false;
    }
  }


 
public static class LISPLexer implements LISPParser.Lexer {
  Reader it;
  Yylex yylex;
  

  public LISPLexer(Reader is){
    it = is;
    yylex = new Yylex(it);
  }

  @Override
  public void yyerror (String s){
    System.err.println(s);
  }

  @Override
  public Object getLVal() {
    return LISPParser.yylval;
  }

  @Override
  public int yylex () throws IOException{
    Token token = yylex.yylex();
    LISPParser.yylval = token;
    if (token instanceof Identifier) {
        return LISPParser.Lexer.Identifier;
    } else if (token instanceof LeftBracket) {
        return LISPParser.Lexer.LeftBracket;
    } else if (token instanceof RightBracket) {
        return LISPParser.Lexer.RightBracket;
    } else if (token instanceof Literal) {
        return LISPParser.Lexer.Literal;
    } 
    return 0;
  }
}
}

%token <Token> Literal
%token <Token> Identifier
%token <Token> LeftBracket
%token <Token> RightBracket

%type <LISPParser.TreeNode> Program
%type <LISPParser.TreeNode> List
%type <LISPParser.TreeNode> Elements
%type <LISPParser.TreeNode> Element
%type <LISPParser.TreeNode> Atom

%%
Program: Elements { node = $1; $$ = $1; };

List: LeftBracket Elements RightBracket { $$ = $2; } ;

Elements: Element { $$ = new NonTerminalNode("Elements").addChild($1); } | Elements Element { TreeNode elem = $1; $$ = elem.addChild($2); } ;

Element: Atom { $$ = $1; } | List { $$ = $1; } | Literal { $$ = new TerminalNode($1); } ;

Atom: Identifier { $$ = new TerminalNode($1); }
;

%%


