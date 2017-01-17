package edu.ustc.cs.compile.interpreter;

import java.io.File;
import org.eclipse.jdt.core.dom.*;
import edu.ustc.cs.compile.platform.interfaces.ParserInterface;
import edu.ustc.cs.compile.platform.interfaces.ParserException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import edu.ustc.cs.compile.platform.util.ir.HIR;

/**
 * <p>生成测试用AST的类
 * </p><p>其中一个例子createSampleAST已经给出，你需要在实验时完成createAST，
 * 并尽可能地使你创建的AST具有代表性
 * </p>
 */
public class TestCase implements ParserInterface {
    /**
     * ParserInterface要求实现的方法。在TestCase中，你需要在这个方法中
     * 手工生成并返回一个AST。
     * @param src 实验平台通过该参数将要处理的源文件传递给语法解析器。
     *                      在TestCase中，这个参数始终为null。
     * @return 返回手工构造的AST。
     */
    public InterRepresent doParse(File src) throws ParserException {
        HIR ir = new HIR();
        ir.setIR(createSampleAST());
        return ir;
    }

    /**
     * 产生一个如下程序的AST。<br>
     * class Program {
     *  static void main() {
     * 	  i = 10;
     *    print(i);
     *  }
     * }
     * @return 生成的CompilationUnit节点，即AST的根节点。
     */
    private CompilationUnit createSampleAST() {
        AST ast = AST.newAST(AST.JLS3);

        CompilationUnit cu = ast.newCompilationUnit();
        // 类声明
        TypeDeclaration td = ast.newTypeDeclaration();
        td.setName(ast.newSimpleName("Program"));
        cu.types().add(td);
        
        // main方法定义
        MethodDeclaration md = ast.newMethodDeclaration();
        td.bodyDeclarations().add(md);
        md.setName(ast.newSimpleName("main"));
        md.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.STATIC_KEYWORD));
        md.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));

        // main方法体
        Block mainBody = ast.newBlock();
        md.setBody(mainBody);

        // 赋值表达式
        Assignment assign = ast.newAssignment();
        assign.setLeftHandSide(ast.newSimpleName("i"));
        assign.setOperator(Assignment.Operator.ASSIGN);
        assign.setRightHandSide(ast.newNumberLiteral("10"));
        
        // 由赋值表达式构建语句，并把这个语句加入方法main()的函数体
        ExpressionStatement s1 = ast.newExpressionStatement(assign);
        mainBody.statements().add(s1);
        
        // print语句的AST节点
        MethodInvocation p1 = ast.newMethodInvocation();
        p1.setName(ast.newSimpleName("print"));
        p1.arguments().add(ast.newSimpleName("i"));
        s1 = ast.newExpressionStatement(p1);
        mainBody.statements().add(s1);
        
        return cu;
    }
        
    /**
     * 你需要填写这个方法的方法体，构造一个SimpleMiniJOOL程序的AST。
     * @return 生成的CompilationUnit节点，即AST的根节点。
     */
    private CompilationUnit createAST() {
        // TODO: add codes to create an AST
        return null;
    }        
}