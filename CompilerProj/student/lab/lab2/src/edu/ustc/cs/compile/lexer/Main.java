package edu.ustc.cs.compile.lexer;

import java.io.*;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;

import edu.ustc.cs.compile.parser.mlexspec.Parser;
import edu.ustc.cs.compile.platform.interfaces.ParserException;
import edu.ustc.cs.compile.platform.util.ir.HIR;
import edu.ustc.cs.compile.platform.util.ASTView.core.ASTViewer; 

public class Main {

    /**
     * ����������Ҫ�����Ǽ��������ɵĴ������ȷ�ԣ����ֱ���{#runPart2}��{#runPart3}��{#runPart4}��ɡ�
     * ����ʵ����ɵĲ�ͬ�׶�ʹ�ö�Ӧ�ļ��runPart����
     */
    
    //minijoolԴ���Բ����������ڵ��ļ�
    public final static String file = "e:/CompilerProj/student/lab/lab2/test/test.mj";/*����Ҫ����������Ҫ�������ļ�*/
    
    
    public static void main(String args[]) {
        int runWhich = 2;
        if ( args.length == 0 || args[0].equals("2")) {
            runPart2();
        } else if ( args[0].equals("3")) {
            runPart3();
        } else if ( args[0].equals("4")) {
            runPart4();
        }
    }
    
    public static void runPart2() {
        ExpressionLexer l = new ExpressionLexer("e:/CompilerProj/student/lab/lab2/test/expr.txt");
        Symbol s = l.nextToken();
        while (s.getType() != Symbol.EOF) {
            System.out.println(s);
            s = l.nextToken();
        }

    }
   
    public static void runPart3() {
    	Parser parser = new Parser();
        File in = new File("E:/CompilerProj/student/lab/lab2/config/MLex/MiniJOOL.mlex"); //����Ҫ����������Ҫ�������ļ�
        
        HIR ir = null;
        try {
            ir = (HIR)(parser.doParse(in));
        } catch (ParserException e) {
            System.err.println("Some errors happened in the parser.");
            e.printStackTrace();
            System.exit(-1);
        }
        
        ASTNode cu = (ASTNode)ir.getIR();
        assert (cu instanceof CompilationUnit);
        
        if (true) {
        	ASTViewer astviewer = new ASTViewer(cu);
        	astviewer.show();
        }
/* ȡ��ע����������Ӧ�Ŀγ���� 
        NFAGenerator nfaGen = new NFAGenerator();
        cu.accept(nfaGen);
        PushbackReader reader = null;
        try {
            reader = new PushbackReader(new FileReader(file));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        NFASimulator sim = new NFASimulator(reader, nfaGen.getNFA());
        
        Symbol s = sim.nextToken();
        while (s.getType() != Symbol.EOF) {
            System.out.println(s);
            s = sim.nextToken();
        }
/*    */
    }
    
    public static void runPart4() {
    /* ȡ��ע����������Ӧ�Ŀγ����
        PushbackReader reader = null;
        
        try {
            reader = new PushbackReader(new FileReader(file));
        } catch(IOException e) {
            System.out.println(e);
            System.exit(-1);
        }
        
        NFASimulator lexer = new NFASimulator(reader, (new LexerCode()).getNFA());
        
        Symbol s = lexer.nextToken();
        while(s.getType() != Symbol.EOF) {
            System.out.println(s);
            s = lexer.nextToken();
        } 
    */
    }    
}
