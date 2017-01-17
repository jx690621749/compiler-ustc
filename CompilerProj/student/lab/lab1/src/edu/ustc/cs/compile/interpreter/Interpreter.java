/**
 * ����ļ��ṩ��һ���������Ŀ�ܴ��롣����Ҫ��ȫ��Щ���룬���һ���ܹ�
 * ��ȷ������SimpleMiniJOOL�����������
 */
package edu.ustc.cs.compile.interpreter;

import org.eclipse.jdt.core.dom.*;
import edu.ustc.cs.compile.platform.interfaces.InterpreterInterface;
import edu.ustc.cs.compile.platform.interfaces.InterpreterException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import edu.ustc.cs.compile.platform.handler.MsgHandler;
import java.util.*;

/**
 * <p>�������ࡣ</p>
 * <p>����ʵ��ƽ̨�ṩ�Ľ������ӿ�InterpreterInterface��һ��ʵ�֣����ڴ�ʵ��ƽ̨��ȡ��ʾ
 * SimpleMiniJOOL������м��ʾ��Ȼ����ý������Ĵ������ִ����������������ṩ�Ŀ�ܴ�
 * ���У�ʵ�ʽ���SimpleMiniJOOL����Ĵ����װ����InterpVisitor�С�</p>
 */
public class Interpreter implements InterpreterInterface {
    /**
     * �ڽӿ�InterpreterInterface��Ҫ��ʵ�ֵķ��������ڴ�ʵ��ƽ̨��ȡ��ʾSimpleMiniJOOL
     * ������м��ʾ�����ý������������ִ���������
     * @param ir ��Ҫִ�е�SimpleMiniJOOL������м��ʾ
     * @throws InterpreterException �׳�����쳣����֪ͨʵ��ƽ̨��������������
     *                              ʵ��ƽ̨��������쳣�����ִֹ�С�
     */
    public void interpret(InterRepresent ir) throws InterpreterException {
        InterpVisitor visitor = new InterpVisitor();
        try {
            ((CompilationUnit)ir.getIR()).accept(visitor);  // ����ʵ�ʽ���SimpleMiniJOOL����Ĵ���
        } catch (IllegalArgumentException e) {
            // TODO: ������Լ����쳣�������
            e.printStackTrace();
            throw new InterpreterException();
        } 
    }
}

/**
 * ��װʵ�ʽ���ִ��SimpleMiniJOOL����Ĵ��롣ʵ����ASTVisitor��
 * һ�����࣬ͨ������AST��ɽ������Ĺ��ܡ�����ֻ��һ�������ܣ�
 * ����Ҫ���в�ȫ��
 */
class InterpVisitor extends ASTVisitor {
	
	HashMap<String, Integer> symTable = new HashMap<String, Integer>();
	Scanner scan = new Scanner(System.in);
	
    public boolean visit(CompilationUnit n) {
        // TODO: ��Ӵ������CompilationUnit
    	System.out.println("Start interpret CompilationUnit, the whole program.");
        return true;
    }

    public boolean visit(TypeDeclaration n) {
        // TODO: ��Ӵ������TypeDeclaration
    	System.out.println("Start interpret TypeDeclaration: class " + n.getName());
        return true;
    }

    public boolean visit(MethodDeclaration n) {
        // TODO: ��Ӵ������MethodDeclaration
    	System.out.println("Start interpret MethodDeclaration, method " + n.getName() 
    														+ ", modifier: " + n.getModifiers() 
    														+ ", return type: " + n.getReturnType2());
        return true;
    }

    public boolean visit(Block n) {
        // TODO: ��Ӵ������Block
    	System.out.println("Start interpret Block, including " + n.statements().size() + " statements.");
        return true;
    }

    public boolean visit(EmptyStatement n) {
        // TODO: ��Ӵ������EmptyStatement
        return true;
    }

    public boolean visit(IfStatement n) {
        // TODO: ��Ӵ������IfStatement
    	
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
        // TODO: ��Ӵ������WhileStatement
    	
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
        // TODO: ��Ӵ������ExpressionStatement
        return true;
    }

    public boolean visit(Assignment n) {
        // TODO: ��Ӵ������Assignment������Բο����´����ܡ�
        
        Assignment.Operator operator = n.getOperator();
        // NOTE: ����Ҫ���ǲ�������print()���������ʱ������Կ�
        //       ���׳��쳣VoidReferenceException��
        
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
            // NOTE: ����Ҫ���ǳ���Ϊ0���������ʱ������Կ����׳�
            //       �쳣DividedByZeroException��
        	
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
            // NOTE: ����Ҫ���ǳ���Ϊ0���������ʱ������Կ����׳�
            //       �쳣DividedByZeroException��
        	
        	try{
        		if(symTable.get(n.getRightHandSide().toString()) == 0){
            		throw new DividedByZeroException();
            	}
            	symTable.put(n.getLeftHandSide().toString(), symTable.get(n.getLeftHandSide().toString()) % symTable.get(n.getRightHandSide().toString()));       	
                
            }
            catch(DividedByZeroException e){
            	MsgHandler.errMsg("Divided By Zero Exception.");               
        		
            } 
        	
        } // ����...
        return false;
    }

    public boolean visit(MethodInvocation n) {
        // TODO: ��Ӵ������MethodInvocation
    	
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
        // TODO: ��Ӵ������NumberLiteral
    	
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
        // TODO: ��Ӵ������PostfixExpression������Բο����´����ܡ�
        
        PrefixExpression.Operator operator = n.getOperator();
        // NOTE: ����Ҫ���ǲ�������print()���������ʱ������Կ�
        //       ���׳��쳣VoidReferenceException��
        
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
            
        } // ����...
        return false;
    }

    public boolean visit(InfixExpression n) {
        // TODO: ��Ӵ������InfixExpression������Բο����´����ܡ�
        
        InfixExpression.Operator operator = n.getOperator();
        // NOTE: ����Ҫ���ǲ�������print()���������ʱ������Կ�
        //       ���׳��쳣VoidReferenceException��
        
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
            // NOTE: ����Ҫ���ǳ���Ϊ0���������ʱ������Կ����׳�
            //       �쳣DividedByZeroException��
        	        	
        	
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
            // NOTE: ����Ҫ���ǳ���Ϊ0���������ʱ������Կ����׳�
            //       �쳣DividedByZeroException��
        	
        	
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
        	
        	        	
        } // ����....
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
        // TODO: ��Ӵ������SimpleName
    	
        return true;
    }

    public boolean visit(PrimitiveType n) {
        // TODO: ��Ӵ������PrimitiveType
        return true;
    }

    public boolean visit(Modifier n) {
        // TODO: ��Ӵ������Modifier
        return true;
    }

    // TODO: ��Ӵ������������Ҫ���͵�AST�ڵ�
}
