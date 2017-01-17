package edu.ustc.cs.compile.lexer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.internal.corext.dom.GenericVisitor; 

import edu.ustc.cs.compile.platform.util.ir.HIR;
import edu.ustc.cs.compile.platform.interfaces.ParserException;
import edu.ustc.cs.compile.parser.mlexspec.Parser;

/**<p>
 * ����AST�����ɶ�Ӧ�Ĵʷ����������룬������NFA�Ĵ��룬��Ҫ�������д����е�main
 * ��������LexerCode�ࡣ�����Ѿ���LexerCode.java.sample���ṩ��һ�������ɵ�
 * ��������������ο���
 * </p><p>
 * ��ʵ������У�����Ҫ��ɴ��࣬����Ӧ����minijool����Ҫ��ע��������������ڴ��������
 * �ʵ���˽�б������������ķ�������������ı���Ľӿ�
 * </p>
 */
public class LexerCodeGenerator extends GenericVisitor {
    private PrintStream out;/** output stream for generated code */
        
    public static void main(String args[]) {
        if (args.length != 2) {
            System.err.println("Unmatched arguments!");
            System.err.println("The first arguement should be the location of a mlex file.");
            System.err.println("The second argument should be the location to store the lexer code.");
            System.exit(-1);
        }
        String mlexFile = args[0];
        String lexerFile = args[1];
        
        PrintStream outfile = null;
        try {
            outfile = new PrintStream(new FileOutputStream(lexerFile));
        } catch(IOException e) {
            System.out.println(e);
            System.exit(-1);
        }
        
        LexerCodeGenerator codeGen = new LexerCodeGenerator(outfile);

        Parser parser = new Parser();
        HIR ir = null;
        try {
            ir = (HIR)parser.doParse(new File(mlexFile));
        } catch (ParserException e) {
            System.err.println("Some errors happened in the parser.");
            e.printStackTrace();
            System.exit(-1);
        }
        
        CompilationUnit cu = (CompilationUnit)ir.getIR();
        
        codeGen.emitCode(cu);
    }

    /**
     * Create a LexerCodeGenerator
     * @param out Stream to output generated code to.
     */
    public LexerCodeGenerator(PrintStream out) {
        if(out != null) {
            this.out = out;
        } else {
            this.out = System.out;
        }
    }

    /**
     * Generate code for the given AST
     * @param lexSpec AST tree to emit Lexer code for
     */
    public void emitCode(ASTNode lexSpec) {
        // TODO �������ʷ��������������쳣�׳�ɾȥ���쳣������ֻ�����ھ�ʾ���á�
        throw new RuntimeException("TODO");
    }

    /**
     * ���Ͷ���ķ��ʺ��������ඨ��ķ��ʺ����� 
     */
    public boolean visit(TypeDeclaration t) {
        t.getMethods()[0].accept(this);
        return false;
    }
    
    /**
     * ���������ķ��ʺ���
     */
    public boolean visit(MethodDeclaration m) {
        m.getBody().accept(this);
        return false;
    }
    
    /**
     * if���ķ��ʺ���
     */
    public boolean visit(IfStatement s) {
        //TODO �������ʷ��������������쳣�׳�ɾȥ���쳣������ֻ�����ھ�ʾ���á�
        throw new RuntimeException("TODO");
    }
    
    /**
     * ��ֵ���ķ��ʺ���
     */
    public boolean visit(Assignment s) {
        //TODO �������ʷ��������������쳣�׳�ɾȥ���쳣������ֻ�����ھ�ʾ���á�
        throw new RuntimeException("TODO");
    }
    /**
     * return���ķ��ʺ���
     */
    public boolean visit(ReturnStatement s) {
        // TODO �������ʷ��������������쳣�׳�ɾȥ���쳣������ֻ�����ھ�ʾ���á�
        throw new RuntimeException("TODO");
    }    
    /**
     * ��׺���ʽ�ķ��ʺ���
     */
    public boolean visit(InfixExpression s) {
        //TODO �������ʷ��������������쳣�׳�ɾȥ���쳣������ֻ�����ھ�ʾ���á�
        throw new RuntimeException("TODO");
    }

    /**
     * �ַ����ķ��ʺ���
     */
    public boolean visit(StringLiteral s) {
        //TODO �������ʷ��������������쳣�׳�ɾȥ���쳣������ֻ�����ھ�ʾ���á�
        throw new RuntimeException("TODO");
    }
    
    /**
     * �ַ��ķ��ʺ���
     */
    public boolean visit(CharacterLiteral c) {
        //TODO �������ʷ��������������쳣�׳�ɾȥ���쳣������ֻ�����ھ�ʾ���á�
        throw new RuntimeException("TODO");
    }
    
    /**
     * ��ʶ���ķ��ʺ���
     */
    public boolean visit(SimpleName n) {
        //TODO �������ʷ��������������쳣�׳�ɾȥ���쳣������ֻ�����ھ�ʾ���á�
        throw new RuntimeException("TODO");
    }
    
    //TODO ����Array����֧��
}
