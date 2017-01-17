package edu.ustc.cs.compile.lexer;

import java.io.PushbackReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
/**<p>
 * NFASimulator���������ͨ�������ɵ�NFA��ʶ��ʷ�����
 * </p><p>
 * ��ʵ������У��������޸Ĵ��࣬������Ҫ��Ͽα���Ϥ���Ĺ���ԭ��
 * </p>
 */

public class NFASimulator {
    private int line, column;

    /**
     * ����һ���ʷ�������.
     * @param in    Ҫ�ʷ������������ļ�.
     * @param state �ʷ���������NFA״̬.
     */
    public NFASimulator(PushbackReader in, NFAState state) {
        line = 1;
        column = 1;
        this.in    = in;
        this.start = state;
    }
    
    private PushbackReader in;    /** ���ڴʷ������������ļ� */
    private NFAState       start; /** NFA�ĳ�ʼ״̬ */

    /**
     * nextToken() ��ʹ�����ƥ����򷵻���������ƥ�����һ�Ǻ�,
     * �������ļ��Ľ�β��nextToken()������Token.EOF.
     * 
     * @return ���ر�ʾ�������е���һ�Ǻ�. 
     * @throws �����ƥ�䣬���׳�LexerException.
     */
    public Symbol nextToken() throws UnmatchedException {
        /* ��ǰ������ַ� */
        char c = 0;
        /* ��ǰ����״̬��Ӧ��Token�����ȼ� */
        int curPriority = 0;
        /* ������Token���Խ��ܣ����¼��β����curToken�е�λ�� */
        int curPosition = 0;
        /* ��ǰToken���ݴ� */
        String curToken = "";
        /* ��ǰ���е�Token��Ӧ��״̬ */
        NFAState curState = null;
        /* ��ǰ��״̬�� */
        Set currentStates = start.epsilonClose();

        while (true) {
            try {
                c = (char) in.read();
                curToken += c;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (c == '\n') {
                line++;
                column = 1;
            } else {
                column++;
            }
            //�ﵽ��������β
            //ǿ�ƽ���Token����ȡ
            if ((c & 0xff) == 0xff) {
                //�����������һ���ַ�Ҳ�ѷ������
                if (curToken.length() == 1) {
                    return new Symbol(Symbol.EOF, Character.toString(c),
                            line, column);
                }
                break;
            }

            //��õ�ǰ״̬����c��epsilon��ת�������õ�״̬��
            currentStates = NFAState.epsilonClosedNextStates(currentStates,
                    (char) c);

            //NFA�ɲ��ܽ��ܵ�ǰToken����Ҫ���ˡ�
            //��ʱcurStateΪ���ս��ܵ�Token��Ӧ�Ľ���״̬��
            //curPriorityΪ��Token������Ȩ��
            //��curPosition���¼�˸�Token�ĳ��ȣ�
            //��curToken��0 ~ curPosition-1���ȵ��ַ���ΪToken��
            //��curToken�ж�����Ĳ�����Ҫ���������л���
            if (currentStates.isEmpty()) {
                break;
            }

            for (Iterator iter = currentStates.iterator(); iter.hasNext();) {
                NFAState s = (NFAState) iter.next();
                //�����ǰ״̬�����н���״̬�����״̬��Ӧ��TokenΪNFA��ƥ��Token����Ҫ������м�¼
                if (s.isFinal()) {
                    //���Ǽ�¼���ȼ���ߵ�
                    if (s.getPriority() > curPriority) {
                        curState = s;
                        curPriority = s.getPriority();
                        curPosition = curToken.length();
                    }
                }
            }
            curPriority = 0;
        }
        //���������л���curToken - Token�Ĳ���
        try {
            in.unread(curToken.toCharArray(), curPosition, curToken.length()
                    - curPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //�ﵽ���ܽ��ܵ�ʱ��NFAȴδ��ƥ��һ��Token����Ҫ�׳��쳣
        if (curState == null) {
            throw new UnmatchedException();
        }
        //���Token
        Symbol t = new Symbol(curState.getTokenId(), curToken.substring(0,
                curPosition), line, column);

        //�����Token��Ҫ���ԣ��򷵻�����֮������������һ��Token
        if (t.getType() == Symbol.IGNORE) {
            return nextToken();
        } else {
            return t;
        } 
    }
}
