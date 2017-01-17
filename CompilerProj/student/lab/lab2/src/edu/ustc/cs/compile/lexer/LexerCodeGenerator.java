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
 * 根据AST来生成对应的词法分析器代码，即产生NFA的代码，需要编译运行此类中的main
 * 函数生成LexerCode类。我们已经在LexerCode.java.sample中提供了一个已生成的
 * 代码样本，供你参考。
 * </p><p>
 * 在实验过程中，你需要完成此类，以适应整个minijool的需要，注意缩进。你可以在此类中添加
 * 适当的私有变量来辅助您的方法，但不允许改变类的接口
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
        // TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }

    /**
     * 类型定义的访问函数，即类定义的访问函数。 
     */
    public boolean visit(TypeDeclaration t) {
        t.getMethods()[0].accept(this);
        return false;
    }
    
    /**
     * 方法声明的访问函数
     */
    public boolean visit(MethodDeclaration m) {
        m.getBody().accept(this);
        return false;
    }
    
    /**
     * if语句的访问函数
     */
    public boolean visit(IfStatement s) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }
    
    /**
     * 赋值语句的访问函数
     */
    public boolean visit(Assignment s) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }
    /**
     * return语句的访问函数
     */
    public boolean visit(ReturnStatement s) {
        // TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }    
    /**
     * 中缀表达式的访问函数
     */
    public boolean visit(InfixExpression s) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }

    /**
     * 字符串的访问函数
     */
    public boolean visit(StringLiteral s) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }
    
    /**
     * 字符的访问函数
     */
    public boolean visit(CharacterLiteral c) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }
    
    /**
     * 标识符的访问函数
     */
    public boolean visit(SimpleName n) {
        //TODO 添加完访问方法后清把下面的异常抛出删去。异常在这里只是用于警示作用。
        throw new RuntimeException("TODO");
    }
    
    //TODO 加入Array访问支持
}
