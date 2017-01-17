/**
 *  该文件中提供了一个x86汇编代码生成器的框架代码。
 *  <p>类Generator2是实验平台的汇编代码生成器接口GeneratorInterface的实现，
 *  这个类通过Generator2Visitor生成汇编代码。
 *  你可以在这个框架的基础上补充代码完成一个x86汇编代码生成器。
 *  注意：代码框架中没有提供寄存器分配的内容，你需要自行补全。</p>
 */
package edu.ustc.cs.compile.generator.x86;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.File;

import org.eclipse.jdt.core.dom.*;

import edu.ustc.cs.compile.platform.interfaces.GeneratorInterface;
import edu.ustc.cs.compile.platform.interfaces.GeneratorException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import edu.ustc.cs.compile.platform.util.ir.*;
import edu.ustc.cs.compile.arch.*;


import edu.ustc.cs.compile.parser.skipoominijool.Parser;

public class Generator2 implements GeneratorInterface {
    CompilationUnit cu = null;
    Generator2Visitor visitor = new Generator2Visitor();

    public void generateAsm(File outFile, InterRepresent ir) throws GeneratorException {
    	cu = (CompilationUnit)ir.getIR();   
        cu.accept(visitor);     
        AssemblySequence asm = visitor.getASM();
        emitASM(outFile, asm);
    }

    
    /**
     * 
     * @param outFile
     * @param asm
     */
    private void emitASM(File outFile, AssemblySequence asm) {
        // 将asm写入到outFile表示的文件中
    }
    
    public static void main(String[] args) {
        HIR hir = null;
        // TODO: 添加语法分析器，获得AST的根节点cu
		
        Generator2 gen = new Generator2();
        File outFile = new File("a.s");
        try{
            gen.generateAsm(outFile, hir);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

class Generator2Visitor extends ASTVisitor {
    // text存储代码段代码
    private AssemblySequence text = new AssemblySequence();
    // data存储数据段代码
    private AssemblySequence data = new AssemblySequence();
    // methodStmts存放单个过程的代码，用于寄存器分配
    private AssemblySequence methodStmts = null;
	
    /**
     * 获得汇编代码
     * @return 用低级中间表示表示的X86汇编代码
     */
    public AssemblySequence getASM() {
        AssemblySequence stmts = new AssemblySequence();
        // TODO: 将数据段代码和代码段代码合并到stmts
        stmts.addAll(data);
		stmts.addAll(text);
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

		//TODO:首先生成方法的头部：
		
		// 其次，遍历方法体来获得生成的代码。
		n.getBody().accept(this);

        // TODO:寄存器分配, 这里要遍历一次生成的AssemblySequence，然后将各个ID或者寄存器变量换成寄存器。		
		
		//把结果加入text中。
        //text.addAll(result);    
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
