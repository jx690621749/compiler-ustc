/* Generated By:JavaCC: Do not edit this line. ExprParser.java */
package  edu.ustc.cs.compile.parser.jjexpr;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.LinkedList;

import org.eclipse.jdt.core.dom.*;
import edu.ustc.cs.compile.platform.interfaces.ParserInterface;
import edu.ustc.cs.compile.platform.interfaces.ParserException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import edu.ustc.cs.compile.platform.util.ir.HIR;
import edu.ustc.cs.compile.platform.util.ASTView.core.*;
import edu.ustc.cs.compile.platform.util.ASTView.plugin.*;

public class ExprParser implements ParserInterface, ExprParserConstants {
        private boolean success = false;
    private boolean debug = false;

        private AST ast = AST.newAST(AST.JLS3);
    private ASTNode root = null;

    public ExprParser(){
    }

        public ASTNode getAST() {
                if (success) {
                        return root;
                }
                return null;
        }

    public InterRepresent doParse(File src) throws ParserException {
        try{
            jj_input_stream = new SimpleCharStream(new FileReader(src), 1, 1);
        } catch(Exception e){
            e.printStackTrace();
            throw new ParserException();
        }
        token_source = new ExprParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 6; i++) jj_la1[i] = -1;

        try {
                        root = sequence();
        } catch (Exception e) {
            System.err.println("Parser Exception.");
            if (debug) {
                e.printStackTrace();
            }
            throw new ParserException();
        }

        HIR ir = new HIR();
        ir.setIR(root);

        success = true;
        return ir;
        }

        public static void main(String argv[]){
        String srcFileName = "E:/CompilerProj/student/lab/lab3/test/exp_list.txt";  // source file name
        File srcFile = new File(srcFileName);
        ExprParser parser = null;
        try {
                parser = new ExprParser(new FileReader(srcFileName));
        } catch (Exception e) {
            System.err.println("ParserException");
            e.printStackTrace();
            System.exit(-1);
        }
        HIR ir = null;
        try {
            ir = (HIR)parser.doParse(srcFile);
        } catch (ParserException e) {
            System.err.println("ParserException");
            e.printStackTrace();
            System.exit(-1);
        }
        ASTViewer astviewer = new ASTViewer((ASTNode)ir.getIR(), new GenericPropertyDump());
        astviewer.show();
        }

  final public Block sequence() throws ParseException {
        Block block;
        LinkedList seq;
        Assignment as;
                block = ast.newBlock();
                seq = new LinkedList();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      as = assignment();
      jj_consume_token(SEMICOLON);
                        seq.add(ast.newExpressionStatement(as));
    }
    jj_consume_token(0);
          block.statements().addAll(seq);
          success = true;
          {if (true) return block;}
    throw new Error("Missing return statement in function");
  }

  final public Assignment assignment() throws ParseException {
        Token id;
        Assignment as;
        Expression e;
                as = ast.newAssignment();
    id = jj_consume_token(IDENTIFIER);
    jj_consume_token(EQ);
    e = expression();
                as.setLeftHandSide(ast.newSimpleName(id.image));
                as.setOperator(Assignment.Operator.ASSIGN);
                as.setRightHandSide(e);
                {if (true) return as;}
    throw new Error("Missing return statement in function");
  }

  final public Expression expression() throws ParseException {
        Expression e, e1,e2;
        InfixExpression ie;
        Token t;
    e1 = term();
                e = e1;
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
      case MINUS:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
                        e = ie = ast.newInfixExpression();
                        ie.setLeftOperand(e1);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        jj_consume_token(PLUS);
                                ie.setOperator(InfixExpression.Operator.PLUS);
        break;
      case MINUS:
        jj_consume_token(MINUS);
                                ie.setOperator(InfixExpression.Operator.MINUS);
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      e2 = term();
                        ie.setRightOperand(e2);
                        e1=ie;
    }
                {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Expression term() throws ParseException {
        Expression e1,e2,e;
        InfixExpression ie;
        Token t;
    e1 = factor();
                e = e1;
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULT:
      case DIV:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
                        e = ie = ast.newInfixExpression();
                        ie.setLeftOperand(e1);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULT:
        jj_consume_token(MULT);
                                ie.setOperator(InfixExpression.Operator.TIMES);
        break;
      case DIV:
        jj_consume_token(DIV);
                                ie.setOperator(InfixExpression.Operator.DIVIDE);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      e2 = factor();
                        ie.setRightOperand(e2);
                        e1 = ie;
    }
                {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Expression factor() throws ParseException {
        Expression e;
        Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
      t = jj_consume_token(IDENTIFIER);
                e = ast.newSimpleName(t.image);
                {if (true) return e;}
      break;
    case INTEGER_LITERAL:
      t = jj_consume_token(INTEGER_LITERAL);
                e = ast.newNumberLiteral();
                ((NumberLiteral)e).setToken(t.image);
                {if (true) return e;}
      break;
    case LPAREN:
      jj_consume_token(LPAREN);
      e = expression();
      jj_consume_token(RPAREN);
                {if (true) return e;}
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  public ExprParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[6];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x20,0xc00,0xc00,0x3000,0x3000,0xe0,};
   }

  public ExprParser(java.io.InputStream stream) {
     this(stream, null);
  }
  public ExprParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ExprParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  public ExprParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ExprParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  public ExprParser(ExprParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  public void ReInit(ExprParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[15];
    for (int i = 0; i < 15; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 6; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 15; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

}
