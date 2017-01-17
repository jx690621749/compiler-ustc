package edu.ustc.cs.compile.lexer;

%%
%line
%column
%public
%class SampleLexer
%type Symbol
%eofval{
return symbol(Symbol.EOF);
%eofval}
%{
    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.out.println  ("Usage: java SampleLexer inputfile");
        }
        else {
            SampleLexer l =null;
            try {
                l = new SampleLexer(new java.io.FileReader(argv[0]));
                Symbol s = l.yylex();
                while (s.getType() != Symbol.EOF) {
                    System.out.println(s);
                    s = l.yylex();
                }
            } catch (Exception e) {
                System.out.println("Unexpected exception:");
                e.printStackTrace();
            }
        }
    }

    private Symbol symbol(int type) {
        return new Symbol(type, yytext(), yyline, yycolumn);
    }
%}
%%
[:digit:]+  { return symbol(Symbol.INTEGER_LITERAL); }
[:letter:]+ { return symbol(Symbol.IDENTIFIER); }
.|\n        { }
