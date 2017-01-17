package edu.ustc.cs.compile.lexer;

/**
 * <p>词法记号类，它是构成词法记号流的基本单位。</p>
 * <p>为了方便你的词法分析器的生成，大部分已经通过静态变量给出，
 * 如果您需要或者希望加入新的词法记号，请按形如下面的方式添加<br>
 * "public static int /symbolname/=counter++;"</p>
 */
public class Symbol {
    public static int counter = 0;
    
    public static final int IGNORE = counter++;/** 需要忽略的字符 */

    public static final int IDENTIFIER = counter++;/** 标识符 */
    public static final int INTEGER_LITERAL = counter++;/** 整数常量*/
    
    public static final int PLUS = counter++;/** + */
    public static final int MINUS = counter++;/** - */
    public static final int MULT = counter++;/** * */
    public static final int DIV = counter++;/** / */
    public static final int LPAREN = counter++;/** ( */
    public static final int RPAREN = counter++;/** ) */
    public static final int EQ = counter++;/** = */
    public static final int SEMICOLON = counter++;/** ; */
 
    public static final int EOF = counter++;/** 文件结尾符 */
    
    //请在以下的空当加入新的词法记号：
    
    
    
    private int type;
    private String token;
    private int line, column;

    /**
     * 构造器，给出类型、名称、所在行数和列数，产生一个相应的Symbol对象。
     * @param type 词法记号的类型，如PLUS, MINUS等等。
     * @param token 
     * @param line   词法记号所在的行号，只用于调试和检查输出。
     * @param column 词法记号所在的列号，只用于调试和检查输出。
     */
    public Symbol(int type, String token, int line, int column) {
        this.type = type;
        this.token = token;
        this.line = line;
        this.column = column;
    }
    
    /**
     * 拷贝构造器，它复制出一个与输入符号完全一样的Symbol对象。
     * @param sym 待复制的Symbol对象。
     */
    public Symbol(Symbol sym) {
        this.type = sym.type;
        this.token = sym.token;
        this.line = sym.line;
        this.column = sym.column;
    }
    
    /**
     * 返回当前符号的类型。
     * @return 这个符号的类型
     */
    public int getType() {
        return type;
    }

    /**
     * 输出当前符号的信息，包括类型、名称、行号和列号。
     * @return 字符串表示的记法符号信息
     */
    public String toString() {
        return type + " " + token + "   (" + line + ", " + column + ")";
    }

    /**
     * 返回该符号的单词文本
     * @return 符号的单词文本
     */
    public String getLexeme(){
        return token;
    }
}
