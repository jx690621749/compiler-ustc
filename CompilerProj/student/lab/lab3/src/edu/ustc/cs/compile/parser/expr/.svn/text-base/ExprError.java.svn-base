package edu.ustc.cs.compile.parser.expr;

/**
 * <p>用于表示Expression语法分析中的错误。</p>
 * 一个错误至少需要包括错误类型，另外还可以包括错误提示信息、
 * 错误发生的行号、错误发生的列号等。
 */
public class ExprError {
    private static int errCounter = 0;
    
    /**
     * 错误类型：没有错误
     */
    public static final int NoErr = errCounter++;
    /**
     * 错误类型：未知错误
     */
    public static final int UnknownErr = errCounter++;
    /**
     * 错误类型：左值不为标识符
     */
    public static final int LvalNotIDErr = errCounter++;
    /**
     * 错误类型：相邻表达式
     */
    public static final int AdjExpErr = errCounter++;
    /**
     * 错误类型：括号不匹配
     */
    public static final int UnmatchedParenErr = errCounter++;
    /**
     * 错误类型：不可识别词法记号
     */
    public static final int UnknownTokenErr = errCounter++;
    /**
     * 错误类型：缺少操作数
     */
    public static final int NoOperandErr = errCounter++;
    
    private int errNo = UnknownErr;
    private String info = null;
    private int line = -1;
    private int column = -1;
    
    /**
     * 默认构造器：错误类型，错误信息、错误行号、错误列号为默认值。
     */
    public ExprError() {
    }
    
    /**
     * 构造器：错误类型为n，其它信息为默认值。
     * @param n 错误类型
     */
    public ExprError(int n) {
        this.errNo = n;
    }
    
    /**
     * 构造器：错误类型为n，错误信息为info，其它信息为默认值。
     * @param n 错误类型
     * @param info 错误信息
     */
    public ExprError(int n, String info) {
        this.errNo = n;
        this.info = info;
    }
    
    /**
     * 构造器：错误类型为n，错误信息为info，错误行号为line，错误列号为column
     * @param n 错误类型
     * @param info 错误信息
     * @param line 错误行号
     * @param column 错误列号
     */
    public ExprError(int n, String info, int line, int column) {
        this.errNo = n;
        this.info = info;
        this.line = line;
        this.column = column;
    }
    
    /**
     * 设置错误类型。
     * @param n 错误类型
     */
    public void setErrNo(int n) {
        this.errNo = n;
    }
    
    /**
     * 获得错误类型。
     * @return 错误类型
     */
    public int errNo() {
        return this.errNo;
    }
    
    /**
     * 设置错误信息。
     * @param info 错误信息
     */
    public void setInfo(String info) {
        this.info = info;
    }
    
    /**
     * 获得错误信息。
     * @return 错误信息
     */
    public String info() {
        return this.info;
    }
    
    /**
     * 设置错误行号。
     * @param line 错误行号
     */
    public void setLine(int line) {
        this.line = line;
    }
    
    /**
     * 获得错误行号。
     * @return 错误行号
     */
    public int line() {
        return this.line;
    }
    
    /**
     * 设置错误列号。
     * @param column 错误列号
     */
    public void setColumn(int column) {
        this.column = column;
    }
    
    /**
     * 获得错误列号。
     * @return 错误列号
     */
    public int column() {
        return this.column;
    }
    
    public String toString() {
        String str = new String();
        str += "ERROR(code="+errNo+") ";
        str += "@("+line+","+column+"): ";
        str += info;
        return str;
    }
}