package edu.ustc.cs.compile.checker.skipoominijool;

import org.eclipse.jdt.core.dom.*;

import edu.ustc.cs.compile.platform.interfaces.CheckerInterface;
import edu.ustc.cs.compile.platform.interfaces.CheckerException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;

/**
 * <p>�����������Խ��ܵ�һ��������м��ʾ��AST�����������顣
 * </p><p>
 * ʵ���У�����Ҫ��ɴ��࣬ʵ�����е�check������
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
        // TODO: ����﷨����������м��ʾir
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
    // ���������ʱ���ִ��󣬽�success��Ϊfalse��
    private boolean success = true;
    
    private InterRepresent ir = null;
    
    public boolean success() {
        return success;
    }
    
    public void setIR(InterRepresent ir) {
        this.ir = ir;
    }
    
    // TODO: ���visit()��������������AST�ڵ㣬����������
}