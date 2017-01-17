package edu.ustc.cs.compile.parser.expr;

import java.util.Stack;

import java_cup.runtime.*;

%%

%public
%class ExprELexer

%unicode

%line
%column

%cupsym ExprESymbol
%cup
%cupdebug

%{
  private StringBuffer string = new StringBuffer();
  private boolean debug = true;
  private Stack<LParen> lparenStack = new Stack<LParen>();
  private int preSymbol = -1;
  private List<ExprError> errors = null;
  
  private class LParen {
    private int line = -1;
    private int column = -1;
    
    public LParen(int line, int column) {
        this.line = line;
        this.column = column;
    }
    
    public int line() {
        return this.line;
    }
    
    public int column() {
        return this.column;
    }
  }  
  
  public ExprELexer(java.io.Reader in, List<ExprError> errList) {
    this(in);
    this.errors = errList;
  }
  
  private void addError(int errNo, String info, int line, int column) {
    ExprError error = new ExprError(errNo, info, line, column);
    errors.add(error);
  }  
  
  private void debugMsg(String msg) {
    if (debug) {
        System.err.print("DEBUG: ");        
        System.err.println(msg);
    }
  }
  
  private Symbol symbol(int type) {
    preSymbol = type;
    debugMsg("TOKEN: "+yytext());
    return new Symbol(type, yyline+1, yycolumn+1);
  }
  
  private Symbol symbol(int type, int preType) {
    preSymbol = preType;
    debugMsg("TOKEN: "+yytext());
    return new Symbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
    preSymbol = type;
    debugMsg("TOKEN: "+yytext());
    return new Symbol(type, yyline+1, yycolumn+1, value);
  } 
  
  private Symbol symbol(int type, Object value, int preType) {
    preSymbol = preType;
    debugMsg("TOKEN: "+yytext());
    return new Symbol(type, yyline+1, yycolumn+1, value);
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
"("                            { 
                                 lparenStack.push(new LParen(yyline+1, yycolumn+1));
                                 
                                 /* The first three if-else statements detect whether there are ( before which are 
                                  * identfiers, integer literals or )'s. If so, return a NOP and push ( back into
                                  * the input stream.
                                  */
                                 if (preSymbol == ExprESymbol.IDENTIFIER) {
                                    addError(ExprError.AdjExpErr, 
                                             "Expect an operator between identifier and parenthesized expression.",
                                             yyline+1, yycolumn+1);
                                    yypushback(1);
                                    return symbol(ExprESymbol.NOP);
                                 } else if (preSymbol == ExprESymbol.INTEGER_LITERAL) {
                                    addError(ExprError.AdjExpErr, 
                                             "Expect an operator between integer and parenthesized expression.",
                                             yyline+1, yycolumn+1);
                                    yypushback(1);
                                    return symbol(ExprESymbol.NOP);
                                 } else if (preSymbol == ExprESymbol.RPAREN) {
                                    addError(ExprError.AdjExpErr, 
                                             "Expect an operator between parenthesized expressions.",
                                             yyline+1, yycolumn+1);
                                    yypushback(1);
                                    return symbol(ExprESymbol.NOP);
                                 }
                                 
                                 return symbol(ExprESymbol.LPAREN); 
                               }
                               
")"                            {                                  
                                 /*
                                  * The first if-else statement detect whether the ) is unmatched. If so, return a NOP.
                                  */
                                 if (lparenStack.empty()) {
                                    addError(ExprError.UnmatchedParenErr, 
                                             "Unmatched ). Expect { somewhere before.", yyline+1, yycolumn+1);
                                    return symbol(ExprESymbol.NOP);
                                 }
                                 
                                 lparenStack.pop();
                                 return symbol(ExprESymbol.RPAREN); 
                               }
                               
";"                            { 
                                 /* The first if-else statement detect whether there are unmatched ('s. If so, pop
                                  * a (, return a pseudo ) and push the ; back into the input stream.
                                  */
                                 if (!lparenStack.empty()) {
                                    LParen lparen = parenStack.pop();
                                    addError(ExprError.UnmatchedParenErr,
                                             "Unmatched (. Expect ) somewhere after.", yyline+1, yycolumn+1);
                                    yypushback(1);                                    
                                    return symbol(ExprESymbol.PSEUDO_RPAREN);
                                 }
                                 
                                 // TODO: miss right operand.
                                 
                                 return symbol(ExprESymbol.SEMICOLON, -1); 
                               }
  
/* operators */
"+"                            { 
                                 if (preSymbol == ExprESymbol.NOP) {
                                    // Do nothing.
                                 } else {
                                    boolean preSymbolNotExp = !(preSymbol == ExprESymbol.RPAREN
                                                                || preSymbol == ExprESymbol.INTEGER_LITERL
                                                                || preSymbol == ExprESymbol.IDENTIFIER);
                                    if (preSymbolNotExp) {
                                        addError(ExprError.NoOperandErr,
                                                 "Expect an integer literal, an identifier or"+
                                                 " a parenthesized expression as the left operand of +.",
                                                 yyline+1, yycolumn+1);
                                        yypushback(1);
                                        return symbol(ExprESymbol.NOP);
                                    }
                                 }
                                 
                                 return symbol(ExprESymbol.PLUS); 
                               }
                                
"-"                            { 
                                 if (preSymbol == ExprESymbol.NOP) {
                                    // Do nothing.
                                 } else {
                                    boolean preSymbolNotExp = !(preSymbol == ExprESymbol.RPAREN
                                                                || preSymbol == ExprESymbol.INTEGER_LITERL
                                                                || preSymbol == ExprESymbol.IDENTIFIER);
                                    if (preSymbolNotExp) {
                                        addError(ExprError.NoOperandErr,
                                                 "Expect an integer literal, an identifier or"+
                                                 " a parenthesized expression as the left operand of -.",
                                                 yyline+1, yycolumn+1);
                                        yypushback(1);
                                        return symbol(ExprESymbol.NOP);
                                    }
                                 }
                                 
                                 return symbol(ExprESymbol.MINUS); }

"*"                            {
                                 if (preSymbol == ExprESymbol.NOP) {
                                    // Do nothing.
                                 } else {
                                    boolean preSymbolNotExp = !(preSymbol == ExprESymbol.RPAREN
                                                                || preSymbol == ExprESymbol.INTEGER_LITERL
                                                                || preSymbol == ExprESymbol.IDENTIFIER);
                                    if (preSymbolNotExp) {
                                        addError(ExprError.NoOperandErr,
                                                 "Expect an integer literal, an identifier or"+
                                                 " a parenthesized expression as the left operand of *.",
                                                 yyline+1, yycolumn+1);
                                        yypushback(1);
                                        return symbol(ExprESymbol.NOP);
                                    }
                                 }
                                 
                                 return symbol(ExprESymbol.MULT); 
                               }

"/"                            {
                                 if (preSymbol == ExprESymbol.NOP) {
                                    // Do nothing.
                                 } else {
                                    boolean preSymbolNotExp = !(preSymbol == ExprESymbol.RPAREN
                                                                || preSymbol == ExprESymbol.INTEGER_LITERL
                                                                || preSymbol == ExprESymbol.IDENTIFIER);
                                    if (preSymbolNotExp) {
                                        addError(ExprError.NoOperandErr,
                                                 "Expect an integer literal, an identifier or"+
                                                 " a parenthesized expression as the left operand of /.",
                                                 yyline+1, yycolumn+1);
                                        yypushback(1);
                                        return symbol(ExprESymbol.NOP);
                                    }
                                 }
                                 
                                 return symbol(ExprESymbol.DIV); 
                               }

"="                            {
                                 if (preSymbol == ExprESymbol.NOP) {
                                    // Do nothing.
                                 } else {
                                    boolean preSymbolNotExp = !(preSymbol == ExprESymbol.RPAREN
                                                                || preSymbol == ExprESymbol.INTEGER_LITERL
                                                                || preSymbol == ExprESymbol.IDENTIFIER);
                                    if (preSymbolNotExp) {
                                        addError(ExprError.NoOperandErr,
                                                 "Expect an integer literal, an identifier or"+
                                                 " a parenthesized expression as the left operand of =.",
                                                 yyline+1, yycolumn+1);
                                        yypushback(1);
                                        return symbol(ExprESymbol.NOP);
                                    }
                                 }
                                 
                                 return symbol(ExprESymbol.EQ); 
                               }

/* numeric literals */

{IntegerLiteral}               { 
                                 /* The first three if-else statements detect whether there are integer literals
                                  * before which are identifiers, integer literals or )'s. If so, return a NOP 
                                  * and push integer literal back into the input stream.
                                  */
                                 if (preSymbol == ExprESymbol.IDENTIFIER) {
                                    addError(ExprError.AdjExpErr, 
                                             "Expect an operator between identifier and integer.",
                                             yyline+1, yycolumn+1);
                                    yypushback(yytext().length());
                                    return symbol(ExprESymbol.NOP);
                                 } else if (preSymbol == ExprESymbol.INTEGER_LITERAL) {
                                    addError(ExprError.AdjExpErr, 
                                             "Expect an operator between integers.",
                                             yyline+1, yycolumn+1);
                                    yypushback(yytext().length());
                                    return symbol(ExprESymbol.NOP);
                                 } else if (preSymbol == ExprESymbol.RPAREN) {
                                    addError(ExprError.AdjExpErr, 
                                             "Expect an operator between integer and parenthesized expression.",
                                             yyline+1, yycolumn+1);
                                    yypushback(yytext().length());
                                    return symbol(ExprESymbol.NOP);
                                 }
                                  
                                 return symbol(ExprESymbol.INTEGER_LITERAL, new Integer(yytext())); 
                               }

/* comments */
{Comment}                      { /* ignore */ }

/* whitespace */
{WhiteSpace}                   { /* ignore */ }

/* identifiers */ 
{Identifier}                   { 
                                 /*
                                  * The first if-else statements detect whether there are identifiers before which
                                  * are identifiers, integer literals or )'s. If so, return a NOP and push the
                                  * identifier back into the input stream.
                                  */
                                 if (preSymbol == ExprESymbol.IDENTIFIER) {
                                    addError(ExprError.AdjExpErr,
                                             "Expect an operator or a semicolon between identifiers.",
                                             yyline+1, yycolumn+1);
                                    yypushback(yytext().length());
                                    return symbol(ExprESymbol.NOP);                                    
                                 } else if (preSymbol == ExprESymbol.INTEGER_LITERAL) {
                                    addError(ExprError.AdjExpErr,
                                             "Expect an operator or a semicolon between integer and identifier.",
                                             yyline+1, yycolumn+1);
                                    yypushback(yytext().length());
                                    return symbol(ExprESymbol.NOP);                                    
                                 } else if (preSymbol == ExprESymbol.RPAREN) {
                                    addError(ExprError.AdjExpErr,
                                             "Expect an operator or a semicolon between parenthesized expression and identifier.",
                                             yyline+1, yycolumn+1);
                                    yypushback(yytext().length());
                                    return symbol(ExprESymbol.NOP);                                    
                                 } 
                                 
                                 return symbol(ExprESymbol.IDENTIFIER, yytext()); 
                               }  

}

/* error fallback */
.|\n                           { throw new RuntimeException("Illegal character \""+yytext()+
                                                            "\" at line "+yyline+", column "+yycolumn); }

<<EOF>>                        { return symbol(ExprESymbol.EOF); }