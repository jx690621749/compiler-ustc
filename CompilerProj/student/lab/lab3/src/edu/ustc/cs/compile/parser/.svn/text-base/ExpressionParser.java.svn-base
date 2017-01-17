package edu.ustc.cs.compile.parser;

import java.io.File;

import org.eclipse.jdt.core.dom.*;

import edu.ustc.cs.compile.platform.interfaces.ParserInterface;
import edu.ustc.cs.compile.platform.interfaces.ParserException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import edu.ustc.cs.compile.platform.util.ir.HIR;

import edu.ustc.cs.compile.lexer.Lexer;
import edu.ustc.cs.compile.lexer.ExpressionLexer;
import edu.ustc.cs.compile.lexer.Symbol;

/**<p>
 * 分析赋值表达式语句序列的语法分析器
 * </p>
 */
public class ExpressionParser extends RDParser implements ParserInterface {
	private ASTNode root = null;
	private boolean success = false;
	
	public ExpressionParser() {
	}
	
	public ExpressionParser(Lexer lexer) {
		super(lexer);
	}

	/**
	 * 实现接口ParserInterface中的InterRepresent doParser(File)
	 */
	public InterRepresent doParse(File srcFile) throws ParserException {
	    lexer = new ExpressionLexer(srcFile.getAbsolutePath());
	    this.parse();	    
	    
	    HIR ir = new HIR();
	    CompilationUnit cu = null;
	    // TODO: 添加代码将cu设为构造出的AST的根节点
	    ir.setIR(cu);
	    
	    return ir;
	}
	
	public ASTNode parse() {
		success = sequence();
		if (success){
			return root;
		} else {
			System.out.println("Parsing Error!");
			return null;
		}
	}
	
	private boolean sequence() {
		int isave = next;
		int stackSize = getStackSize();
		if (sequence1()) {
			return true;
		}
		next = isave;
		setStackSize(stackSize);
		if (sequence2()) {
			return true;
		}
		return false;
	}
	
	private boolean sequence1() {
		return assignment() && sequence();
	}
	
	private boolean sequence2() {
		return token(Symbol.EOF);
	}
	
	private boolean assignment() {
		return assignment1();
	}
	
	private boolean assignment1() {
		return token(Symbol.IDENTIFIER) && token(Symbol.EQ)
					&& expression() && token(Symbol.SEMICOLON);
	}
	
	private boolean expression() {
		return expression1();
	}
	
	private boolean expression1() {
		return term() && expression_1();
	}
	
	private boolean expression_1() {
		int isave = next;
		int stackSize = getStackSize();
		if (expression_11()) {
			return true;
		}
		next = isave;
		setStackSize(stackSize);
		if (expression_12()) {
			return true;
		}
		next = isave;
		setStackSize(stackSize);
		if (expression_13()) {
			return true;
		}
		return false;
	}
	
	private boolean expression_11() {
		return token(Symbol.PLUS) && term() && expression_1();
	}
	
	private boolean expression_12() {
		return token(Symbol.MINUS) && term() && expression_1();
	}
	
	private boolean expression_13() {
		return tokenEpsilon();
	}
	
	private boolean term() {
		return term1();
	}
	
	private boolean term1() {
		return factor() && term_1();
	}
	
	private boolean term_1() {
		int isave = next;
		int stackSize = getStackSize();
		if (term_11()) {
			return true;
		}
		next = isave;
		setStackSize(stackSize);
		if (term_12()) {
			return true;
		}
		next = isave;
		setStackSize(stackSize);
		if (term_13()) {
			return true;
		}
		return false;
	}
	
	private boolean term_11() {
		return token(Symbol.MULT) && factor() && term_1();
	}
	
	private boolean term_12() {
		return token(Symbol.DIV) && factor() && term_1();
	}
	
	private boolean term_13() {
		return tokenEpsilon();
	}
	
	private boolean factor() {
		int isave = next;
		int stackSize = getStackSize();
		if (factor1()) {
			return true;
		}
		next = isave;
		setStackSize(stackSize);
		if (factor2()) {
			return true;
		}
		next = isave;
		setStackSize(stackSize);
		if (factor3()) {
			return true;
		}
		return false;
	}
	
	private boolean factor1() {
		return token(Symbol.LPAREN) && expression() && token(Symbol.RPAREN);
	}
	
	private boolean factor2() {
		return token(Symbol.IDENTIFIER);
	}
	
	private boolean factor3() {
		return token(Symbol.INTEGER_LITERAL);
	}
}
