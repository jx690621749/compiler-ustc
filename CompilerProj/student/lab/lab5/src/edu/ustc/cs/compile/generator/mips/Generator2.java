/**
 *  该文件中提供了一个MIPS汇编代码生成器的框架代码。
 *  <p>类Generator2是实验平台的汇编代码生成器接口GeneratorInterface的实现，
 *  这个类通过Generator2Visitor生成汇编代码。
 *  你可以在这个框架的基础上补充代码完成一个MIPS汇编代码生成器。
 *  注意：代码框架中没有提供寄存器分配的内容，你需要自行补全。</p>
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
     * 将asm表示的汇编代码输出到outFile表示的文件中。
     * @param outFile 输出文件
     * @param asm 汇编代码的中间表示
     */
    private void emitASM(File outFile, AssemblySequence asm) throws IOException {
        // 将asm写入到outFile表示的文件中
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
        String srcFile = "test/test.mj";  // 如果源程序文件不是test/test.mj，请替换srcFile的值
        File src = new File(srcFile);
        
        HIR hir = null;
        
        // 调用我们提供的SkipOOMiniJOOL的语法分析器获得中间表示。
        // 你可以将它替换成自己的语法分析器。
        ParserInterface parser = new Parser();
        try {
            hir = (HIR)parser.doParse(src);
        } catch (ParserException e) {
            System.err.println("Some exceptions happened in parser.");
            e.printStackTrace();
            System.exit(-1);
        }
        
        // 调用ASTView
        ASTViewer astviewer = new ASTViewer((ASTNode)hir.getIR(), new GenericPropertyDump());
        astviewer.show();
        
        // 调用汇编代码生成器。
        Generator2 gen = new Generator2();
        File outFile = new File("a.s");  // 如果要输出到其它文件，请修改outFile的值
        try{
            gen.generateAsm(outFile, hir);
        }catch(Exception e){
            System.err.println("Some exceptions happened in generator.");
            e.printStackTrace();
        }
    }
}

class Generator2Visitor extends ASTVisitor {
    // text存储代码段代码
    private AssemblySequence text = new AssemblySequence();
    // data存储数据段代码
    private AssemblySequence data = new AssemblySequence();

    
    /**
     * 获得汇编代码
     * @return 用低级中间表示表示的MIPS汇编代码
     */
    public AssemblySequence getASM() {
        AssemblySequence stmts = new AssemblySequence();
        // TODO: 将数据段代码和代码段代码合并到stmts
        
        return stmts;
    }    

    public boolean visit(CompilationUnit n) { 
        // TODO: 添加代码生成CompilationUnit对应的汇编代码
        return true;
    }

    public boolean visit(TypeDeclaration n) {
        // TODO: 添加代码生成TypeDeclaration对应的汇编代码
        return true;
    }

    public boolean visit(MethodDeclaration n) {
        // TODO: 添加代码生成MethodDeclaration对应的汇编代码
        // 你可以参考如下的代码
        
        // 为方法生成如下形式的MIPS汇编代码：
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
        // TODO: 添加代码生成Block对应的汇编代码
        return true;
    }

    public boolean visit(EmptyStatement n) {
        // TODO: 添加代码生成EmptyStatement对应的汇编代码
        return true;
    }

    public boolean visit(IfStatement n) {
        // TODO: 添加代码生成IfStatement对应的汇编代码
        return true;
    }

    public boolean visit(WhileStatement n) {
        // TODO: 添加代码生成WhileStatement对应的汇编代码
        return true;
    }

    public boolean visit(ExpressionStatement n) {
        // TODO: 添加代码生成ExpressionStatement对应的汇编代码
        return true;
    }

    public boolean visit(Assignment n) {
        // TODO: 添加代码生成Assignment对应的汇编代码
        return true;
    }
    
    public boolean visit(MethodInvocation n) {
        // TODO: 添加代码生成MethodInvocation对应的汇编代码
        return true;
    }
    
    public boolean visit(NumberLiteral n) {
        // TODO: 添加代码生成NumberLiteral对应的汇编代码
        return true;
    }
    
    public boolean visit(PrefixExpression n) {
        // TODO: 添加代码生成PrefixExpression对应的汇编代码
        return true;
    }

    public boolean visit(InfixExpression n) {
        // TODO: 添加代码生成InfixExpression对应的汇编代码
        return true;
    }

    public boolean visit(SimpleName n) {
        // TODO: 添加代码生成SimpleName对应的汇编代码
        return true;
    }

    public boolean visit(PrimitiveType n) {
        // TODO: 添加代码生成PrimitiveType对应的汇编代码
        return true;
    }

    public boolean visit(Modifier n) {
        // TODO: 添加代码生成Modifier对应的汇编代码
        return true;
    }
    
    // TODO: 添加其它visit方法为其它类型的AST节点生成汇编代码
}
