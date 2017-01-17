/**
 * 这个文件提供了一个解释器的框架代码。你需要补全这些代码，完成一个能够
 * 正确工作的SimpleMiniJOOL程序解释器。
 */
package edu.ustc.cs.compile.interpreter;

import org.eclipse.jdt.core.dom.*;
import edu.ustc.cs.compile.platform.interfaces.InterpreterInterface;
import edu.ustc.cs.compile.platform.interfaces.InterpreterException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import edu.ustc.cs.compile.platform.handler.MsgHandler;
import java.util.*;

/**
 * <p>解释器类。</p>
 * <p>它是实验平台提供的解释器接口InterpreterInterface的一个实现，用于从实验平台获取表示
 * SimpleMiniJOOL程序的中间表示，然后调用解释器的代码解释执行这个程序。在我们提供的框架代
 * 码中，实际解释SimpleMiniJOOL程序的代码封装在类InterpVisitor中。</p>
 */
public class Interpreter implements InterpreterInterface {
    /**
     * 在接口InterpreterInterface中要求实现的方法。用于从实验平台获取表示SimpleMiniJOOL
     * 程序的中间表示，调用解释器代码解释执行这个程序。
     * @param ir 需要执行的SimpleMiniJOOL程序的中间表示
     * @throws InterpreterException 抛出这个异常可以通知实验平台解释器发生错误，
     *                              实验平台捕获这个异常后会终止执行。
     */
    public void interpret(InterRepresent ir) throws InterpreterException {
        InterpVisitor visitor = new InterpVisitor();
        try {
            ((CompilationUnit)ir.getIR()).accept(visitor);  // 调用实际解释SimpleMiniJOOL程序的代码
        } catch (IllegalArgumentException e) {
            // TODO: 添加你自己的异常处理代码
            e.printStackTrace();
            throw new InterpreterException();
        } 
    }
}

/**
 * 封装实际解释执行SimpleMiniJOOL程序的代码。实际是ASTVisitor的
 * 一个子类，通过遍历AST完成解释器的功能。以下只是一个代码框架，
 * 你需要自行补全。
 */
class InterpVisitor extends ASTVisitor {
	
	HashMap<String, Integer> symTable = new HashMap<String, Integer>();
	Scanner scan = new Scanner(System.in);
	
    public boolean visit(CompilationUnit n) {
        // TODO: 添加代码解释CompilationUnit
    	System.out.println("Start interpret CompilationUnit, the whole program.");
        return true;
    }

    public boolean visit(TypeDeclaration n) {
        // TODO: 添加代码解释TypeDeclaration
    	System.out.println("Start interpret TypeDeclaration: class " + n.getName());
        return true;
    }

    public boolean visit(MethodDeclaration n) {
        // TODO: 添加代码解释MethodDeclaration
    	System.out.println("Start interpret MethodDeclaration, method " + n.getName() 
    														+ ", modifier: " + n.getModifiers() 
    														+ ", return type: " + n.getReturnType2());
        return true;
    }

    public boolean visit(Block n) {
        // TODO: 添加代码解释Block
    	System.out.println("Start interpret Block, including " + n.statements().size() + " statements.");
        return true;
    }

    public boolean visit(EmptyStatement n) {
        // TODO: 添加代码解释EmptyStatement
        return true;
    }

    public boolean visit(IfStatement n) {
        // TODO: 添加代码解释IfStatement
    	
    	n.getExpression().accept(this);
    	int expValue = symTable.get(n.getExpression().toString());
    	if(expValue != 0){
    		n.getThenStatement().accept(this);
    	}
    	else if(n.getElseStatement() != null){
    		n.getElseStatement().accept(this);
    	}
    	
        return false;
    }

    public boolean visit(WhileStatement n) {
        // TODO: 添加代码解释WhileStatement
    	
    	n.getExpression().accept(this);
    	int expValue = symTable.get(n.getExpression().toString());
    	while(expValue != 0){
    		n.getBody().accept(this);
    		n.getExpression().accept(this);
    		expValue = symTable.get(n.getExpression().toString());
    	}
    	
        return false;
    }

    public boolean visit(ExpressionStatement n) {
        // TODO: 添加代码解释ExpressionStatement
        return true;
    }

    public boolean visit(Assignment n) {
        // TODO: 添加代码解释Assignment。你可以参考以下代码框架。
        
        Assignment.Operator operator = n.getOperator();
        // NOTE: 你需要考虑操作数是print()的情况。此时，你可以考
        //       虑抛出异常VoidReferenceException。
        
        //System.out.println("inside assignment: " + n.toString());
        
        
        try{
        	if(n.getRightHandSide().getNodeType() == ASTNode.METHOD_INVOCATION && ((MethodInvocation)n.getRightHandSide()).getName().getIdentifier().equals("print")){
            	throw new VoidReferenceException();
            }
        }
        catch(VoidReferenceException e){
        	MsgHandler.errMsg("Void Reference Exception: print() has no return value.");               
    		
        } 
        
        n.getRightHandSide().accept(this);
        
        if (operator == Assignment.Operator.ASSIGN) {
        	//System.out.println("assignment lefthand side: " + n.getLeftHandSide().toString());
        	//System.out.println("assignment righthand side: " + n.getRightHandSide().toString());
        	//System.out.println("assignment righthandside value: " + symTable.get(n.getRightHandSide().toString()));
        	//System.out.println(symTable.toString());
        	
            symTable.put(n.getLeftHandSide().toString(), symTable.get(n.getRightHandSide().toString()));
            
        } else if (operator == Assignment.Operator.PLUS_ASSIGN) {
        	symTable.put(n.getLeftHandSide().toString(), symTable.get(n.getLeftHandSide().toString()) + symTable.get(n.getRightHandSide().toString()));
        	
        } else if (operator == Assignment.Operator.MINUS_ASSIGN) {
        	symTable.put(n.getLeftHandSide().toString(), symTable.get(n.getLeftHandSide().toString()) - symTable.get(n.getRightHandSide().toString()));        	
        
        } else if (operator == Assignment.Operator.TIMES_ASSIGN) {
        	symTable.put(n.getLeftHandSide().toString(), symTable.get(n.getLeftHandSide().toString()) * symTable.get(n.getRightHandSide().toString()));       	
        
        } else if (operator == Assignment.Operator.DIVIDE_ASSIGN) {
            // NOTE: 你需要考虑除数为0的情况。此时，你可以考虑抛出
            //       异常DividedByZeroException。
        	
        	try{
        		if(symTable.get(n.getRightHandSide().toString()) == 0){
            		throw new DividedByZeroException();
            	}
            	symTable.put(n.getLeftHandSide().toString(), symTable.get(n.getLeftHandSide().toString()) / symTable.get(n.getRightHandSide().toString()));       	
                
            }
            catch(DividedByZeroException e){
            	MsgHandler.errMsg("Divided By Zero Exception.");               
        		
            } 
        	
        } else if (operator == Assignment.Operator.REMAINDER_ASSIGN) {
            // NOTE: 你需要考虑除数为0的情况。此时，你可以考虑抛出
            //       异常DividedByZeroException。
        	
        	try{
        		if(symTable.get(n.getRightHandSide().toString()) == 0){
            		throw new DividedByZeroException();
            	}
            	symTable.put(n.getLeftHandSide().toString(), symTable.get(n.getLeftHandSide().toString()) % symTable.get(n.getRightHandSide().toString()));       	
                
            }
            catch(DividedByZeroException e){
            	MsgHandler.errMsg("Divided By Zero Exception.");               
        		
            } 
        	
        } // 继续...
        return false;
    }

    public boolean visit(MethodInvocation n) {
        // TODO: 添加代码解释MethodInvocation
    	
    	//System.out.println("inside method invocation: " + n.toString());
    	
    	if(n.getName().getIdentifier().equals("print")){
    		//System.out.println(n.arguments().get(0).toString());
    		((ASTNode)n.arguments().get(0)).accept(this);
    		System.out.println("print value: " + symTable.get(n.arguments().get(0).toString()));
    	}
    	else if(n.getName().getIdentifier().equals("read")){
    		
    		//int input = Integer.valueOf(scan.nextLine());
    		int input = scan.nextInt();
    		symTable.put(n.arguments().get(0).toString(), input);
    		
    	}
    	
        return false;
    }

    public boolean visit(NumberLiteral n) {
        // TODO: 添加代码解释NumberLiteral
    	
    	String s = n.getToken();
    	int value = 0;
    	if(s.startsWith("0x") || s.startsWith("0X")){
    		//hex number
    		value = Integer.valueOf(s.substring(2), 16);
    	}
    	else if(s.startsWith("0")  && s.length() > 1){
    		//oct number
    		value = Integer.valueOf(s.substring(1), 8);
    	}
    	else{
    		//decimal number
    		value = Integer.valueOf(s);
    	}
    	
    	symTable.put(n.toString(), value);
    	
        return false;
    }

    public boolean visit(PrefixExpression n) {
        // TODO: 添加代码解释PostfixExpression。你可以参考以下代码框架。
        
        PrefixExpression.Operator operator = n.getOperator();
        // NOTE: 你需要考虑操作数是print()的情况。此时，你可以考
        //       虑抛出异常VoidReferenceException。
        
        //System.out.println("inside prefix expression: " + n.toString());
        
        try{
        	if(n.getOperand().getNodeType() == ASTNode.METHOD_INVOCATION && ((MethodInvocation)n.getOperand()).getName().getIdentifier().equals("print")){
            	throw new VoidReferenceException();
            }
        }
        catch(VoidReferenceException e){
        	MsgHandler.errMsg("Void Reference Exception: print() has no return value.");               
    		
        }       
        
        n.getOperand().accept(this);
        
        int value = symTable.get(n.getOperand().toString());
        if (operator == PrefixExpression.Operator.PLUS) {
        	symTable.put(n.toString(), value);
        
        } else if (operator == PrefixExpression.Operator.MINUS) {
        	symTable.put(n.toString(), -value);
            
        } else if (operator == PrefixExpression.Operator.NOT) {
        	if(value == 0){
        		symTable.put(n.toString(), 1);
        	}
        	else{
        		symTable.put(n.toString(), 0);
        	}
            
        } // 继续...
        return false;
    }

    public boolean visit(InfixExpression n) {
        // TODO: 添加代码解释InfixExpression。你可以参考以下代码框架。
        
        InfixExpression.Operator operator = n.getOperator();
        // NOTE: 你需要考虑操作数是print()的情况。此时，你可以考
        //       虑抛出异常VoidReferenceException。
        
        //System.out.println("inside infix expression: " + n.toString());
        
        try{
        	if(n.getLeftOperand().getNodeType() == ASTNode.METHOD_INVOCATION && ((MethodInvocation)n.getLeftOperand()).getName().getIdentifier().equals("print")){
            	throw new VoidReferenceException();
            }
            else if(n.getRightOperand().getNodeType() == ASTNode.METHOD_INVOCATION && ((MethodInvocation)n.getRightOperand()).getName().getIdentifier().equals("print")){
            	throw new VoidReferenceException();
            }
        }
        catch(VoidReferenceException e){
        	MsgHandler.errMsg("Void Reference Exception: print() has no return value.");               
    		
        }
               
        n.getLeftOperand().accept(this);
        n.getRightOperand().accept(this);
        
        int lvalue = symTable.get(n.getLeftOperand().toString());
        int rvalue = symTable.get(n.getRightOperand().toString());
        
        //System.out.println("AND operator is: " + InfixExpression.Operator.CONDITIONAL_AND);
        //System.out.println("This operator is: " + operator);
        
        if (operator == InfixExpression.Operator.CONDITIONAL_AND) {
        	//System.out.println("This is AND operator: " + n.toString());
        	
        	if(lvalue == 0 || rvalue == 0){
        		symTable.put(n.toString(), 0);
        	}
        	else{
        		symTable.put(n.toString(), 1);
        	}
        
        }
        else if(operator == InfixExpression.Operator.CONDITIONAL_OR){
        	//System.out.println("This is OR operator: " + n.toString());        	
        	
        	if(lvalue == 0 && rvalue == 0){
        		symTable.put(n.toString(), 0);
        	}
        	else{
        		symTable.put(n.toString(), 1);
        	}
        }
        else if(operator == InfixExpression.Operator.PLUS){
        	//System.out.println("This is PLUS operator: " + n.toString());
        	
        	symTable.put(n.toString(), lvalue + rvalue);
        }
        else if (operator == InfixExpression.Operator.MINUS) {
        	symTable.put(n.toString(), lvalue - rvalue);
        
        } else if (operator == InfixExpression.Operator.TIMES) {
        	symTable.put(n.toString(), lvalue * rvalue);
        
        } else if (operator == InfixExpression.Operator.DIVIDE) {
            // NOTE: 你需要考虑除数为0的情况。此时，你可以考虑抛出
            //       异常DividedByZeroException。
        	        	
        	
        	try{
        		if(rvalue == 0){
            		throw new DividedByZeroException();
            	}
            	symTable.put(n.toString(), lvalue / rvalue);
        	}
        	catch(InterpreterException e){
        		//System.out.println("Divided By Zero Exception.");
        		MsgHandler.errMsg("Divided By Zero Exception.");               
        		//e.printStackTrace();
        	}
        	
        	
        } else if (operator == InfixExpression.Operator.REMAINDER) {
            // NOTE: 你需要考虑除数为0的情况。此时，你可以考虑抛出
            //       异常DividedByZeroException。
        	
        	
        	try{
        		if(rvalue == 0){
            		throw new DividedByZeroException();
            	}
            	symTable.put(n.toString(), lvalue % rvalue);
        	}
        	catch(InterpreterException e){
        		//System.out.println("Divided By Zero Exception.");
        		MsgHandler.errMsg("Divided By Zero Exception.");                       		
        		//e.printStackTrace();
        	}
        	
        	        	
        } // 继续....
        else if(operator == InfixExpression.Operator.GREATER){
        	if(lvalue > rvalue){
        		symTable.put(n.toString(), 1);
        	}
        	else{
        		symTable.put(n.toString(), 0);
        	}
        }
        else if(operator == InfixExpression.Operator.GREATER_EQUALS){
        	if(lvalue >= rvalue){
        		symTable.put(n.toString(), 1);
        	}
        	else{
        		symTable.put(n.toString(), 0);
        	}
        }
        else if(operator == InfixExpression.Operator.LESS){
        	if(lvalue < rvalue){
        		symTable.put(n.toString(), 1);
        	}
        	else{
        		symTable.put(n.toString(), 0);
        	}
        }
        else if(operator == InfixExpression.Operator.LESS_EQUALS){
        	if(lvalue <= rvalue){
        		symTable.put(n.toString(), 1);
        	}
        	else{
        		symTable.put(n.toString(), 0);
        	}
        }
        else if(operator == InfixExpression.Operator.EQUALS){
        	if(lvalue == rvalue){
        		symTable.put(n.toString(), 1);
        	}
        	else{
        		symTable.put(n.toString(), 0);
        	}
        }
        else if(operator == InfixExpression.Operator.NOT_EQUALS){
        	if(lvalue != rvalue){
        		symTable.put(n.toString(), 1);
        	}
        	else{
        		symTable.put(n.toString(), 0);
        	}
        }
        return false;
    }

    public boolean visit(SimpleName n) {
        // TODO: 添加代码解释SimpleName
    	
        return true;
    }

    public boolean visit(PrimitiveType n) {
        // TODO: 添加代码解释PrimitiveType
        return true;
    }

    public boolean visit(Modifier n) {
        // TODO: 添加代码解释Modifier
        return true;
    }

    // TODO: 添加代码解释其它需要解释的AST节点
}
