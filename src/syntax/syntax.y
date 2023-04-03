%language "Java"

%define api.prefix {LISPParser}
%define api.parser.class {LISPParser}
%define api.parser.public
%define parse.error verbose
%define api.value.type {TreeNode}

%code imports{
  import java.io.InputStream;
  import java.io.InputStreamReader;
  import java.io.Reader;
  import java.io.IOException;
}

%code {
  public static TreeNode node;

  public static void main(String args[]) throws IOException {
    LISPLexer lexer = new LISPLexer(new FileReader("input.txt"));
    LISPParser parser = new LISPParser(lexer);
    if(parser.parse()) {
      System.out.println("Parsing Result = SUCCESS");
      System.out.println(node);
    }
    return;
  }
}

%token Literal
%token Identifier
%token LeftBracket
%token RightBracket

%%
Program: Elements { node = new TreeNode().addChild($1); $$ = node; };

List: LeftBracket Elements RightBracket { $$ = new TreeNode("List").addChild(new TreeNode("LeftBracket")).addChild($2).addChild(new TreeNode("RightBracket"));} ;

Elements: Element { $$ = new TreeNode("Elements").addChild($1); } | Elements Element { TreeNode<String> elem = (TreeNode<String>) $1; $$ = elem.addChild($2); } ;

Element: Atom { $$ = $1; } | List { $$ = $1; } | Literal { $$ = new TreeNode($1.token); } ;

Atom: Identifier { $$ = new TreeNode($1.token); }
;

%%

class LISPLexer implements LISPParser.Lexer {
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
    return null;
  }

  @Override
  public int yylex () throws IOException{
    return yylex.yylex();
  }
}

class TreeNode<T>{
    // hui
    T data;
    TreeNode<T> parent;
    List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
    }

    public TreeNode<T> addChild(Object obj) {
        var childNode = (TreeNode<T>) obj;
        childNode.parent = this;
        this.children.add(childNode);
        return this;
    }

}