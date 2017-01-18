package edu.ustc.cs.compile.parser;

import java.io.File;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;

import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import edu.ustc.cs.compile.platform.interfaces.ParserException;
import edu.ustc.cs.compile.platform.util.ASTView.core.*;
import edu.ustc.cs.compile.platform.util.ASTView.plugin.*;

import edu.ustc.cs.compile.lexer.ExpressionLexer;
//import edu.ustc.cs.compile.parser.expr.ExprParser;
import edu.ustc.cs.compile.parser.jjexpr.ExprParser;

public class Main {
	private static boolean viewAST = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//runPart1();
		runPart2();
	}
	
	/**
	 * ���пγ����3-1
	 */
    public static void runPart1() {
        String srcFileName = "/Users/wenzhao/workplace/compiler-ustc/CompilerProj/student/lab/lab3/test/exp_list.txt"; // ����Ҫ��srcFileName��Ϊ��Ҫ�������ļ�
        File srcFile = new File(srcFileName);
        ExpressionLexer lexer = new ExpressionLexer(srcFile.getAbsolutePath());
        ExpressionParser parser = new ExpressionParser(lexer);
        InterRepresent ir = null;
        try {
            ir = parser.doParse(srcFile);
        } catch (ParserException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        // ����ASTViewer��ʾ���ɵ�AST	
        if (viewAST){
            ASTNode cu = (ASTNode)ir.getIR();
            ASTViewer astviewer = new ASTViewer(cu);
            astviewer.show();
        }
    }
    
	/**
	 * ���пγ����3-2��3-3
	 * ������пγ����3-2����import edu.ustc.cs.compile.parser.expr.ExprParser;
	 * �������пγ����3-3����import edu.ustc.cs.compile.parser.jjexpr.ExprParser;
	 */
	public static void runPart2() {
		boolean viewAST = true;
		
        String srcFileName = "/Users/wenzhao/workplace/compiler-ustc/CompilerProj/student/lab/lab3/test/exp_list.txt"; // ����Ҫ��srcFileName��Ϊ��Ҫ�������ļ�
        File srcFile = new File(srcFileName);
		ASTNode block;
		ExprParser parser = new ExprParser();
		InterRepresent ir = null;
        try {
            ir = parser.doParse(srcFile);
        } catch (ParserException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        // ����ASTViewer��ʾ���ɵ�AST	
        if (viewAST){
            ASTViewer astviewer = new ASTViewer((ASTNode)ir.getIR());
            astviewer.show();
        }
	}

}