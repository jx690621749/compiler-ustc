package edu.ustc.cs.compile.lexer;

import java.io.IOException;
/**<p>
 * 识别整数和符号'+'的简单词法分析器，继承自Lexer。
 * </p><p>
 * 在实验中，请扩展nextToken方法，使该词法分析器真正地能够识别表达式的各种
 * 词法记号
 * </p>
 */
public class ExpressionLexer extends Lexer {
    private int state = 0;
    private int start = 0;
    
    private StringBuffer lexeme = new StringBuffer();
    
    private char c;
    
    /**
     * 位置记录
     */
    private int line = 0;
    private int column = 0;;
    
    /**
     * 词法分析器的构造函数
     * @param infile 输入文件名
     */
    public ExpressionLexer(String infile) {
        super(infile);
    }
    

    /**
     * 取得下一个开始状态。在根据某转换图分析失败时，调用此方法来
     * 获取下一转换图的开始状态。
     * @return 下一个转换图的开始状态
     */
    private int fail() {
        while (lexeme.length() > 0) 
            pushbackChar();
        switch (start) {
        case 0:
            start = 3;
            break;
        case 3:
            recover();
            break;
        default:
            throw new UnmatchedException();
        }
        return start;
    }
    
    /**
     * 恢复方法，此处仅抛出一个异常。
     */
    private void recover() {
        throw new UnmatchedException();
    }
    
    /**
     * 取得下一个字符
     */
    private void nextChar() {
        try {
            c = (char)in.read();
            lexeme.append(c);
            column++;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * 回退一个字符
     */
    private void pushbackChar() {
        try {
            in.unread(lexeme.charAt(lexeme.length() - 1));
            lexeme.deleteCharAt(lexeme.length() - 1);
            column--;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**<p>
     * 取得记号，并重置状态变量
     * @param type 待构造的记号类型
     * @return 分析出的记号
     * </p><p>
     * 实验中请修改此方法使得能够分析更多的词法记号
     * </p>
     */
    private Symbol getToken(int type) {
        state = 0;
        start = 0;
        String t = lexeme.toString();
        lexeme.setLength(0);
        
        return new Symbol(type, t, line + 1, column - t.length() + 1);
    }
    
    /**
     * 扔掉一个字符（此时单词应该还未开始，只需把长度设为0即可）
     */
    private void dropChar() {
        lexeme.setLength(0);
    }
    
    /**
     * 词法分析的关键方法，取得一个词法记号。请加入更多的词法类型以分析更多的词法记号。
     * @return 分析出的词法记号
     */
    public Symbol nextToken() {
        while (true) {
            switch (state) {
            case 0:
                // 获取下一个字符
                nextChar();
                
                // 判断是否空白
                if (Character.isWhitespace(c)) {
                    if (c == '\n') {
                        line++;
                        column = 0;
                    }
                    dropChar();
                } else if (Character.isDigit(c)) {
                    state = 1;
                } else if ((c & 0xff) == 0xff) {
                    return getToken(Symbol.EOF);
                } else {
                    state = fail();
                }
                break;
            case 1:
                nextChar();
                if (Character.isDigit(c)) {
                    state = 1;
                } else {
                    state = 2;
                }
                break;
            case 2:
                pushbackChar();
                return getToken(Symbol.INTEGER_LITERAL);
            case 3:
                nextChar();
                if (c == '+') {
                    state = 4;
                } else {
                    state = fail();
                }
                break;
            case 4:
                return getToken(Symbol.PLUS);
            }
        }
    }
}
