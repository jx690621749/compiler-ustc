/**
 *  ���ļ����ṩ��һ��MIPS�������������Ŀ�ܴ��롣
 *  <p>��Generator2��ʵ��ƽ̨�Ļ������������ӿ�GeneratorInterface��ʵ�֣�
 *  �����ͨ��Generator2Visitor���ɻ����롣
 *  ������������ܵĻ����ϲ���������һ��MIPS��������������
 *  ע�⣺��������û���ṩ�Ĵ�����������ݣ�����Ҫ���в�ȫ��</p>
 */
package edu.ustc.cs.compile.generator.mips;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jdt.core.dom.*;

import edu.ustc.cs.compile.platform.interfaces.GeneratorInterface;
import edu.ustc.cs.compile.platform.interfaces.GeneratorException;
import edu.ustc.cs.compile.platform.interfaces.ParserInterface;
import edu.ustc.cs.compile.platform.interfaces.ParserException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import edu.ustc.cs.compile.platform.util.ir.*;
import edu.ustc.cs.compile.platform.util.ASTView.core.*;
import edu.ustc.cs.compile.platform.util.ASTView.plugin.*;
import edu.ustc.cs.compile.arch.*;
import edu.ustc.cs.compile.arch.mips.*;
import edu.ustc.cs.compile.parser.skipoominijool.Parser;

public class Generator2 implements GeneratorInterface {
    private CompilationUnit cu = null;
    private Generator2Visitor visitor = new Generator2Visitor();
	
    public void generateAsm(File outFile, InterRepresent ir) throws GeneratorException {
        cu = (CompilationUnit)ir.getIR();           
        cu.accept(visitor);     
        AssemblySequence asm = visitor.getASM();
        try {
            emitASM(outFile, asm);
        } catch (Exception e) {
            throw new GeneratorException();
        }
    }
    
    /**
     * ��asm��ʾ�Ļ����������outFile��ʾ���ļ��С�
     * @param outFile ����ļ�
     * @param asm ��������м��ʾ
     */
    private void emitASM(File outFile, AssemblySequence asm) throws IOException {
        // ��asmд�뵽outFile��ʾ���ļ���
        FileWriter out = null;
        try {
            out = new FileWriter(outFile);
        } catch (IOException e) {
            System.err.println("Can't write to file "+outFile.getName()+".");
            e.printStackTrace();
            throw e;
        }
    	
    	String contents = asm.toString();
    	try {
    	    out.write(contents, 0, contents.length());
    	} catch (IOException e) {
    	    System.err.println("I/O errors while writing file "+outFile.getName()+".");
    	    e.printStackTrace();
    	    throw e;
    	}
    }
    
    public static void main(String[] args) {
        String srcFile = "test/test.mj";  // ���Դ�����ļ�����test/test.mj�����滻srcFile��ֵ
        File src = new File(srcFile);
        
        HIR hir = null;
        
        // ���������ṩ��SkipOOMiniJOOL���﷨����������м��ʾ��
        // ����Խ����滻���Լ����﷨��������
        ParserInterface parser = new Parser();
        try {
            hir = (HIR)parser.doParse(src);
        } catch (ParserException e) {
            System.err.println("Some exceptions happened in parser.");
            e.printStackTrace();
            System.exit(-1);
        }
        
        // ����ASTView
        ASTViewer astviewer = new ASTViewer((ASTNode)hir.getIR(), new GenericPropertyDump());
        astviewer.show();
        
        // ���û�������������
        Generator2 gen = new Generator2();
        File outFile = new File("a.s");  // ���Ҫ����������ļ������޸�outFile��ֵ
        try{
            gen.generateAsm(outFile, hir);
        }catch(Exception e){
            System.err.println("Some exceptions happened in generator.");
            e.printStackTrace();
        }
    }
}

class Generator2Visitor extends ASTVisitor {
    // text�洢����δ���
    private AssemblySequence text = new AssemblySequence();
    // data�洢���ݶδ���
    private AssemblySequence data = new AssemblySequence();

    
    /**
     * ��û�����
     * @return �õͼ��м��ʾ��ʾ��MIPS������
     */
    public AssemblySequence getASM() {
        AssemblySequence stmts = new AssemblySequence();
        // TODO: �����ݶδ���ʹ���δ���ϲ���stmts
        
        return stmts;
    }    

    public boolean visit(CompilationUnit n) { 
        // TODO: ��Ӵ�������CompilationUnit��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(TypeDeclaration n) {
        // TODO: ��Ӵ�������TypeDeclaration��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(MethodDeclaration n) {
        // TODO: ��Ӵ�������MethodDeclaration��Ӧ�Ļ�����
        // ����Բο����µĴ���
        
        // Ϊ��������������ʽ��MIPS�����룺
        //      .align 2
        //      .globl method_name
        //      .ent   method_name
        // method_name:
        //      method_body
        //      .end   method_name
        Directive direct = null;
        ArrayList<Directive.Argument> args = null;
        Instruct inst = null;
  
        // ".align 2"
        args = new ArrayList<Directive.Argument>();
        args.add(new Directive.Argument(new Integer(2)));
        direct = new Directive(MIPSDirector.ALIGN, args);
        text.add(direct);

        String methodName = n.getName().getIdentifier();
        
        // ".globl method_name"
        args = new ArrayList<Directive.Argument>();
        args.add(new Directive.Argument(new Label(methodName)));
        direct = new Directive(MIPSDirector.GLOBL, args);
        text.add(direct);

        // ".ent method_name"
        args = new ArrayList<Directive.Argument>();
        args.add(new Directive.Argument(new Label(methodName)));
        direct = new Directive(MIPSDirector.ENT, args);
        text.add(direct);
    
        // method_name:
        Label label = new Label(methodName);
        text.add(label);

        // method body
        n.getBody().accept(this);
    
        // ".end method_name"
        args = new ArrayList<Directive.Argument>();
        args.add(new Directive.Argument(new Label(methodName)));
        direct = new Directive(MIPSDirector.END, args);
        text.add(direct);
    
        return false;
    }

    public boolean visit(Block n) {
        // TODO: ��Ӵ�������Block��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(EmptyStatement n) {
        // TODO: ��Ӵ�������EmptyStatement��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(IfStatement n) {
        // TODO: ��Ӵ�������IfStatement��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(WhileStatement n) {
        // TODO: ��Ӵ�������WhileStatement��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(ExpressionStatement n) {
        // TODO: ��Ӵ�������ExpressionStatement��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(Assignment n) {
        // TODO: ��Ӵ�������Assignment��Ӧ�Ļ�����
        return true;
    }
    
    public boolean visit(MethodInvocation n) {
        // TODO: ��Ӵ�������MethodInvocation��Ӧ�Ļ�����
        return true;
    }
    
    public boolean visit(NumberLiteral n) {
        // TODO: ��Ӵ�������NumberLiteral��Ӧ�Ļ�����
        return true;
    }
    
    public boolean visit(PrefixExpression n) {
        // TODO: ��Ӵ�������PrefixExpression��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(InfixExpression n) {
        // TODO: ��Ӵ�������InfixExpression��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(SimpleName n) {
        // TODO: ��Ӵ�������SimpleName��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(PrimitiveType n) {
        // TODO: ��Ӵ�������PrimitiveType��Ӧ�Ļ�����
        return true;
    }

    public boolean visit(Modifier n) {
        // TODO: ��Ӵ�������Modifier��Ӧ�Ļ�����
        return true;
    }
    
    // TODO: �������visit����Ϊ�������͵�AST�ڵ����ɻ�����
}
