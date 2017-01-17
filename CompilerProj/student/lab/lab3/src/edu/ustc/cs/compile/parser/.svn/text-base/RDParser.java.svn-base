package edu.ustc.cs.compile.parser;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.io.FileReader;

import org.eclipse.jdt.core.dom.*;

import edu.ustc.cs.compile.lexer.Lexer;
import edu.ustc.cs.compile.lexer.Symbol;

/**<p>
 * 语法分析器的基类
 * </p><p>
 * 在实验中，请在你编写的语法分析器子类中实现parse方法
 * </p>
 */
public abstract class RDParser {

	/**
	 * 词法分析器
	 */
	protected Lexer lexer;
	
	/**
	 * 在最近的输入点期望但还没有出现的记号集合
	 */
	private final Set missedToks = new LinkedHashSet();

	/**
	 * 期望但还没有出现的记号的最近位置
	 */
	private int maxErrorNext = -1;		
	
	public RDParser() {
	}
	
	public RDParser(Lexer lexer) {
		this.lexer = lexer;
	}

	// 以下成员与输入的记号流有关
	/**
	 * 下一个记号在输入串中的位置
	 */
	protected int next = 0; 
	
	/**
	 * 保存输入的所有记号
	 */
	protected Vector in = new Vector();

	/**
	 * 取一个输入记号
	 * @param i 记号的位置
	 * @return 记号
	 */
	public Symbol tokenAt(int i) {
		if (i >= in.size())
			in.addElement(lexer.nextToken());
		return (Symbol) in.get(i);
	}

	/**
	 * 用于语法分析的栈
	 */	
	private Stack stack = new Stack();

	/**
	 * 取在栈顶元素之下的第i个元素 
	 * 
	 * @param i 偏移量,必须是非正数
	 * @return 对应的元素
	 */
	public Object peek(int i) {
		return stack.elementAt(stack.size() - 1 + i);
	}

	/**
	 * 将元素入栈
	 * @param o 待入栈的元素
	 */
	protected void push(Object o) {
		stack.push(o);
	}

	/**
	 * 取得当前栈的大小
	 * @return 当前栈的大小
	 */
	protected int getStackSize() {
		return stack.size();
	}

	/**
	 * 设置栈的大小
	 * @param s 待设置的大小
	 */
	protected void setStackSize(int s) {
		stack.setSize(s);
	}

	/**
	 * 从栈中弹出若干个元素
	 * @param x 弹出的元素个数
	 */
	protected void pop(int x) {
		stack.setSize(stack.size() - x);
	}

	/**
	 * 匹配一个记号
	 * @param t 记号的编号值
	 * @return 如果记号匹配，则为<code>true</code>, 否则为<code>false</code>
	 */
	protected boolean token(int t) {
		Symbol tok = tokenAt(next);
		int temp = tok.getType();
		if (t == tok.getType()) {
			// 将记号入栈
			stack.push(tokenAt(next));
			if (next >= maxErrorNext) {
				maxErrorNext = -1;
				missedToks.clear();				
			}
			next++;
			return true;
		} else {
			if (next >= maxErrorNext) {
				if (next > maxErrorNext) {
					maxErrorNext = next;
					missedToks.clear();
				}
				missedToks.add(new Integer(t));
			}
			return false;
		}
	}

	/**
	 * 等价于token(epsilon)
	 * @return <code>true</code>
	 */
	protected boolean tokenEpsilon() {
		stack.push(stack.size() == 0 ? null : peek(0));
		return true;
	}

	/**
	 * 对输入进行语法分析
	 * @return 分析所得的AST的根节点
	 */
	public abstract ASTNode parse();
	
    /**
     * @return
     */
    public Set getMissedToks() {
        return missedToks;
    }

    /**
     * @return
     */
    public int getMaxErrorNext() {
        return maxErrorNext;
    }
}
