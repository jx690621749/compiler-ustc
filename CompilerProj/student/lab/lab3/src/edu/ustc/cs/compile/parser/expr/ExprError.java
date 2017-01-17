package edu.ustc.cs.compile.parser.expr;

/**
 * <p>���ڱ�ʾExpression�﷨�����еĴ���</p>
 * һ������������Ҫ�����������ͣ����⻹���԰���������ʾ��Ϣ��
 * ���������кš����������кŵȡ�
 */
public class ExprError {
    private static int errCounter = 0;
    
    /**
     * �������ͣ�û�д���
     */
    public static final int NoErr = errCounter++;
    /**
     * �������ͣ�δ֪����
     */
    public static final int UnknownErr = errCounter++;
    /**
     * �������ͣ���ֵ��Ϊ��ʶ��
     */
    public static final int LvalNotIDErr = errCounter++;
    /**
     * �������ͣ����ڱ��ʽ
     */
    public static final int AdjExpErr = errCounter++;
    /**
     * �������ͣ����Ų�ƥ��
     */
    public static final int UnmatchedParenErr = errCounter++;
    /**
     * �������ͣ�����ʶ��ʷ��Ǻ�
     */
    public static final int UnknownTokenErr = errCounter++;
    /**
     * �������ͣ�ȱ�ٲ�����
     */
    public static final int NoOperandErr = errCounter++;
    
    private int errNo = UnknownErr;
    private String info = null;
    private int line = -1;
    private int column = -1;
    
    /**
     * Ĭ�Ϲ��������������ͣ�������Ϣ�������кš������к�ΪĬ��ֵ��
     */
    public ExprError() {
    }
    
    /**
     * ����������������Ϊn��������ϢΪĬ��ֵ��
     * @param n ��������
     */
    public ExprError(int n) {
        this.errNo = n;
    }
    
    /**
     * ����������������Ϊn��������ϢΪinfo��������ϢΪĬ��ֵ��
     * @param n ��������
     * @param info ������Ϣ
     */
    public ExprError(int n, String info) {
        this.errNo = n;
        this.info = info;
    }
    
    /**
     * ����������������Ϊn��������ϢΪinfo�������к�Ϊline�������к�Ϊcolumn
     * @param n ��������
     * @param info ������Ϣ
     * @param line �����к�
     * @param column �����к�
     */
    public ExprError(int n, String info, int line, int column) {
        this.errNo = n;
        this.info = info;
        this.line = line;
        this.column = column;
    }
    
    /**
     * ���ô������͡�
     * @param n ��������
     */
    public void setErrNo(int n) {
        this.errNo = n;
    }
    
    /**
     * ��ô������͡�
     * @return ��������
     */
    public int errNo() {
        return this.errNo;
    }
    
    /**
     * ���ô�����Ϣ��
     * @param info ������Ϣ
     */
    public void setInfo(String info) {
        this.info = info;
    }
    
    /**
     * ��ô�����Ϣ��
     * @return ������Ϣ
     */
    public String info() {
        return this.info;
    }
    
    /**
     * ���ô����кš�
     * @param line �����к�
     */
    public void setLine(int line) {
        this.line = line;
    }
    
    /**
     * ��ô����кš�
     * @return �����к�
     */
    public int line() {
        return this.line;
    }
    
    /**
     * ���ô����кš�
     * @param column �����к�
     */
    public void setColumn(int column) {
        this.column = column;
    }
    
    /**
     * ��ô����кš�
     * @return �����к�
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