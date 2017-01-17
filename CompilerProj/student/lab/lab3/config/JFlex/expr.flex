package edu.ustc.cs.compile.parser.expr;

import java_cup.runtime.*;

%%

%public
%class ExprLexer

%unicode

%line
%column

%cupsym ExprSymbol
%cup
%cupdebug

%{
  StringBuffer string = new StringBuffer();
  
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }
  
  /** 
   * assumes correct representation of a long value for 
   * specified radix in scanner buffer from <code>start</code> 
   * to <code>end</code> 
   */
  private long parseLong(int start, int end, int radix) {
    long result = 0;
    long digit;

    for (int i = start; i < end; i++) {
      digit  = Character.digit(yycharat(i),radix);
      result*= radix;
      result+= digit;
    }

    return result;
  }

%}

/* main character classes */
Printable = [ -~]

WhiteSpace = [ \t\f\n\r]| \r\n

/* comments */

Comment = "//" ({Printable} | [\t])*

/* identifiers */
Identifier = [:letter:] ([:letter:] | [:digit:])*

/* integer literals */
IntegerLiteral = [:digit:]+

%%

<YYINITIAL> {

/* separators */
"("                            { return symbol(ExprSymbol.LPAREN); }
")"                            { return symbol(ExprSymbol.RPAREN); }
";"                            { return symbol(ExprSymbol.SEMICOLON); }
  
/* operators */
"+"                            { return symbol(ExprSymbol.PLUS); }
"-"                            { return symbol(ExprSymbol.MINUS); }
"*"                            { return symbol(ExprSymbol.MULT); }
"/"                            { return symbol(ExprSymbol.DIV); }
"="                            { return symbol(ExprSymbol.EQ); }

/* numeric literals */

{IntegerLiteral}            { return symbol(ExprSymbol.INTEGER_LITERAL, new Integer(yytext())); }

/* comments */
{Comment}                      { /* ignore */ }

/* whitespace */
{WhiteSpace}                   { /* ignore */ }

/* identifiers */ 
{Identifier}                   { return symbol(ExprSymbol.IDENTIFIER, yytext()); }  

}

/* error fallback */
.|\n                             { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }

<<EOF>>                          { return symbol(ExprSymbol.EOF); }