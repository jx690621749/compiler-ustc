package edu.ustc.cs.compile.lexer;

import java.io.IOException;
/**<p>
 * ʶ�������ͷ���'+'�ļ򵥴ʷ����������̳���Lexer��
 * </p><p>
 * ��ʵ���У�����չnextToken������ʹ�ôʷ��������������ܹ�ʶ����ʽ�ĸ���
 * �ʷ��Ǻ�
 * </p>
 */
public class ExpressionLexer extends Lexer {
    private int state = 0;
    private int start = 0;
    
    private StringBuffer lexeme = new StringBuffer();
    
    private char c;
    
    /**
     * λ�ü�¼
     */
    private int line = 0;
    private int column = 0;;
    
    /**
     * �ʷ��������Ĺ��캯��
     * @param infile �����ļ���
     */
    public ExpressionLexer(String infile) {
        super(infile);
    }
    

    /**
     * ȡ����һ����ʼ״̬���ڸ���ĳת��ͼ����ʧ��ʱ�����ô˷�����
     * ��ȡ��һת��ͼ�Ŀ�ʼ״̬��
     * @return ��һ��ת��ͼ�Ŀ�ʼ״̬
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
     * �ָ��������˴����׳�һ���쳣��
     */
    private void recover() {
        throw new UnmatchedException();
    }
    
    /**
     * ȡ����һ���ַ�
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
     * ����һ���ַ�
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
     * ȡ�üǺţ�������״̬����
     * @param type ������ļǺ�����
     * @return �������ļǺ�
     * </p><p>
     * ʵ�������޸Ĵ˷���ʹ���ܹ���������Ĵʷ��Ǻ�
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
     * �ӵ�һ���ַ�����ʱ����Ӧ�û�δ��ʼ��ֻ��ѳ�����Ϊ0���ɣ�
     */
    private void dropChar() {
        lexeme.setLength(0);
    }
    
    /**
     * �ʷ������Ĺؼ�������ȡ��һ���ʷ��Ǻš���������Ĵʷ������Է�������Ĵʷ��Ǻš�
     * @return �������Ĵʷ��Ǻ�
     */
    public Symbol nextToken() {
        while (true) {
            switch (state) {
            case 0:
                // ��ȡ��һ���ַ�
                nextChar();
                
                // �ж��Ƿ�հ�
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
