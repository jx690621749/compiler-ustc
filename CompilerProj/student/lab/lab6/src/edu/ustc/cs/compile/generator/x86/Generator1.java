/**
 * 一个x86汇编代码生成器的框架代码。<p>
 * 其中，类Generator1是实验平台的汇编代码生成器接口GeneratorInterface的实现，
 * 这个类通过Generator1Visitor生成汇编代码。
 * 你可以在这个框架的基础上补充代码完成一个x86汇编代码生成器。
 * 代码框架中调用我们提供的寄存器分配器进行寄存器分配。你在生成汇
 * 编代码时可以用寄存器变量代替寄存器，我们的寄存器分配器会为这些寄存
 * 器变量分配寄存器。</p>
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
import edu.ustc.cs.compile.arch.x86.*;
import edu.ustc.cs.compile.arch.x86.regalloc.*;

import java.io.FileWriter;
import edu.ustc.cs.compile.parser.skipoominijool.*;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;


import edu.ustc.cs.compile.parser.skipoominijool.Parser;

public class Generator1 implements GeneratorInterface {
    CompilationUnit cu = null;
    Generator1Visitor visitor = new Generator1Visitor();

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
		try{
			FileWriter fw = new FileWriter(outFile);
			fw.write(asm.toString(),0, asm.toString().length());
			fw.flush();
	    	//asm.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        HIR hir = null;
        // TODO: 添加语法分析器，获得AST的根节点cu
		Parser parser = new Parser();
		try{
			hir = (HIR)parser.doParse(new File("D:\\test.mj"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
        Generator1 gen = new Generator1();
        File outFile = new File("d:\\a.s");
        try{
            gen.generateAsm(outFile, hir);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

class Generator1Visitor extends ASTVisitor {
    // text存储代码段代码
    private AssemblySequence text = new AssemblySequence();
    // data存储数据段代码
    private AssemblySequence data = new AssemblySequence();
    // methodStmts存放单个过程的代码，用于寄存器分配
    private AssemblySequence methodStmts = null;
	
	
	//tempAssSeq 存放单个过程的中间表示代码，没有做寄存器分配。
	private AssemblySequence tempAssSeq = new AssemblySequence();
	//SymTable 存放过程中出现的变量名称。
	List<String> symTable = new ArrayList<String>();
    
    /**
     * 获得汇编代码
     * @return 用低级中间表示表示的MIPS汇编代码
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

		tempAssSeq = new AssemblySequence();

		Label label = new Label("_main");
		tempAssSeq.add(label);
		
		// method body
		n.getBody().accept(this);

        // 寄存器分配, 这里要遍历一次生成的AssemblySequence，然后将各个ID换成寄存器。
		//这里不用RegisterVariable，但是在遍历的时候要用到symtable.
        RegAllocator ra =  new RegAllocator();
		AssemblySequence result = new AssemblySequence();
		int counter = 0;
		for (counter = 0; counter < tempAssSeq.size(); counter++){
			AssemblyElement ae = tempAssSeq.get(counter);
			if (ae instanceof Label){
				result.add(ae);
			}
			if (ae instanceof Directive){
				result.add(ae);
			}
			if (ae instanceof Instruct){
				//从这里开始，如果该Instruct有ID作为操作数使用，则应该换成寄存器
				List<Instruct.Operand> ops = ((Instruct)ae).operands();
				for (int i = 0; i < ops.size(); i++){
					Instruct.Operand op = ops.get(i);
					if (op.label()!= null){
						Label opl = op.label();
						//为了不再加入数据结构，我们用Label来存储变量的名称，所以在这里要考虑LABEL是不是一个变量的名称。
						//加入一个符号表，用来区分普通的label和变量的名称。请注意，在实际使用中需要考虑变量的重名问题。
						if (symTable.contains(opl)){
							//如果是一个变量的名称，我们就试着给它分配一个寄存器。
							X86Register reg = ra.getReg(opl.toString());
							if (reg == null){//如果当前没有空的寄存器，就spill一个新的出来。
								reg = ra.selectReg();
								result.addAll(ra.spill(reg));//spill会自动生成一段代码，直接导入result中。
							}
							//现在reg是可以用的了，我们把原来的变量名称换成reg。
							ops.set(i, new Instruct.Operand(reg));
						}
					}
				}
				result.add(ae);
			}
		}
        
        text.addAll(result);
    
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
		if (!symTable.contains(n.getIdentifier())){
			symTable.add(n.getIdentifier());
		}
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
