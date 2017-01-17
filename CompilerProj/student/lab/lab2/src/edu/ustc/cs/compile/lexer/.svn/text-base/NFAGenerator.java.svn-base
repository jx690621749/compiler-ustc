package edu.ustc.cs.compile.lexer;

import java.util.List;

import org.eclipse.core.internal.runtime.Assert;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.internal.corext.dom.GenericVisitor;

/**<p>
 * 将从词法描述文件得到的AST转化为对应的NFA
 * </p><p>
 * 实验中，您需要完成此类，词法描述我们在MJLex/MiniJOOL.mlex中给出
 * 您可以自由地在此类中添加私有变量来辅助您的方法
 * </p>
 */
public class NFAGenerator extends GenericVisitor {

    /** 你需要实现一个记号映射类，并声明一个实例用于保存记号名与记号ID之间的映射 */
    //private TokenMap    tokenMap;
    
    /** true 已经生成NFA */
    private boolean traversed;

    /** 所生成的NFA的初始状态 */
    private NFAState start;

    //private NFAState end = new NFAState();
    //private NFAState tempStart, tempEnd;

    /**
     * 
     */
    public NFAGenerator() {
        this.traversed = false;
    }

    /**
     * 取得产生的NFA;
     */
    public NFAState getNFA() {
        Assert.isTrue(traversed);

        return start;
    }

    /**
     * 类型定义的访问方法。在本实验中，类型定义即唯一的一个类定义。
     * 访问Main方法，结束后将traversed变量设置为true.
     */
    public boolean visit(TypeDeclaration t) {
        assert t.getMethods().length==1;
        t.getMethods()[0].accept(this);
        traversed = true;
        return false;
    }

    /**
     * 方法定义的访问方法，访问方法体即可。
     */
    public boolean visit(MethodDeclaration m) {
        m.getBody().accept(this);
        return false;
    }

    /**
     * if语句的访问方法。
     */
    public boolean visit(IfStatement s) {
        s.getExpression().accept(this);

        List l = ((Block)s.getThenStatement()).statements();
        
        ReturnStatement statement = null;
        try {
            statement = (ReturnStatement)l.get(l.size() - 1);
        } catch (ClassCastException e) {
            System.out.println("Error in lexer specification action block!");
            System.exit(1);
        }
        String tokenName = ((SimpleName)statement.getExpression()).getIdentifier();

        return false;
    }

    /**
     * 赋值语句的访问方法。
     */
    public boolean visit(Assignment s) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }
    
    /**
     * return语句的访问方法
     */
    public boolean visit(ReturnStatement s) {
        // TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }

    /**
     * 中缀表达式的访问方法
     */
    public boolean visit(InfixExpression s) {
        //TODO  添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }

    /**
     * 字符串的访问方法
     */
    public boolean visit(StringLiteral s) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }

    /**
     * 字符的访问方法
     */
    public boolean visit(CharacterLiteral c) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }

    /**
     * 简单名字的访问方法
     */
    public boolean visit(SimpleName n) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }
    
    /**
     * 程序块的访问方法，包括函数体块
     */
    public boolean visit(Block b) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }
}
