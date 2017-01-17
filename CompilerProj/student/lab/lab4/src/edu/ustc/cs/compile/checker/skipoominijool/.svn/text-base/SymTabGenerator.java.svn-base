package edu.ustc.cs.compile.checker.skipoominijool;

import org.eclipse.jdt.core.dom.*;

import edu.ustc.cs.compile.platform.util.ir.HIR;

/**
 * <p>符号表生成器。
 * </p><p>
 * 实验中，你需要对接收的AST构造全局以及各个block的符号表并返回之。
 * </p>
 */
public class SymTabGenerator {
    public MSymTable getSymTable(HIR ir) {
        CompilationUnit cu = (CompilationUnit)ir.getIR();
        SymTabGeneratorVisitor visitor = new SymTabGeneratorVisitor();
        if (cu != null) {
            cu.accept(visitor);
        }
        return visitor.getSymTab();
    }
}

class SymTabGeneratorVisitor extends ASTVisitor {
    private MSymTable symTab = new MSymTable();
    
    public MSymTable getSymTab() {
        return this.symTab;
    }
    
    // TODO: 添加visit()方法，遍历各个AST节点，收集符号表信息，
    //       添加到symTab中。
}