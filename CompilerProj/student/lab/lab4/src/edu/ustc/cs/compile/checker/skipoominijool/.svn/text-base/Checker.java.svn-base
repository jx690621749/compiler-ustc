package edu.ustc.cs.compile.checker.skipoominijool;

import org.eclipse.jdt.core.dom.*;

import edu.ustc.cs.compile.platform.interfaces.CheckerInterface;
import edu.ustc.cs.compile.platform.interfaces.CheckerException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;

/**
 * <p>语义检查器。对接受的一个程序的中间表示（AST）进行语义检查。
 * </p><p>
 * 实验中，你需要完成此类，实现其中的check方法。
 * </p>
 */
public class Checker implements CheckerInterface {
    public boolean check(InterRepresent ir) throws CheckerException {
        CompilationUnit cu = (CompilationUnit)ir.getIR();
        CheckerVisitor visitor = new CheckerVisitor();
        visitor.setIR(ir);
        cu.accept(visitor);
        return visitor.success();
    }
    
    public static void main(String[] args) {
        InterRepresent ir = null;
        // TODO: 添加语法分析器获得中间表示ir
        CompilationUnit cu = (CompilationUnit)ir.getIR();
        CheckerVisitor visitor = new CheckerVisitor();
        visitor.setIR(ir);
        cu.accept(visitor);
        if (visitor.success()) {
            System.out.println("Checker has not found any errors.");
        } else {
            System.out.println("Checker has found some errors.");
        }
    }
}

class CheckerVisitor extends ASTVisitor {
    // 如果语义检查时发现错误，将success置为false。
    private boolean success = true;
    
    private InterRepresent ir = null;
    
    public boolean success() {
        return success;
    }
    
    public void setIR(InterRepresent ir) {
        this.ir = ir;
    }
    
    // TODO: 添加visit()方法，遍历各个AST节点，进行语义检查
}