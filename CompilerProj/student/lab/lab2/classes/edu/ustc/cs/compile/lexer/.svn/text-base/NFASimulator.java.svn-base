package edu.ustc.cs.compile.lexer;

import java.io.PushbackReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
/**<p>
 * NFASimulator类的作用是通过已生成的NFA来识别词法符号
 * </p><p>
 * 在实验过程中，您不需修改此类，但必须要结合课本熟悉它的工作原理
 * </p>
 */

public class NFASimulator {
    private int line, column;

    /**
     * 创建一个词法分析器.
     * @param in    要词法分析的输入文件.
     * @param state 词法分析器的NFA状态.
     */
    public NFASimulator(PushbackReader in, NFAState state) {
        line = 1;
        column = 1;
        this.in    = in;
        this.start = state;
    }
    
    private PushbackReader in;    /** 用于词法分析的输入文件 */
    private NFAState       start; /** NFA的初始状态 */

    /**
     * nextToken() 将使用最大匹配规则返回输入流中匹配的下一记号,
     * 在输入文件的结尾，nextToken()将返回Token.EOF.
     * 
     * @return 返回表示输入流中的下一记号. 
     * @throws 如果不匹配，则抛出LexerException.
     */
    public Symbol nextToken() throws UnmatchedException {
        /* 当前读入的字符 */
        char c = 0;
        /* 当前接受状态对应的Token的优先级 */
        int curPriority = 0;
        /* 若已有Token可以接受，则记录其尾部在curToken中的位置 */
        int curPosition = 0;
        /* 当前Token的暂存 */
        String curToken = "";
        /* 当前已有的Token对应的状态 */
        NFAState curState = null;
        /* 当前的状态集 */
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
            //达到输入流结尾
            //强制进行Token的提取
            if ((c & 0xff) == 0xff) {
                //输入流的最后一个字符也已分析完毕
                if (curToken.length() == 1) {
                    return new Symbol(Symbol.EOF, Character.toString(c),
                            line, column);
                }
                break;
            }

            //获得当前状态经过c的epsilon空转换后所得的状态集
            currentStates = NFAState.epsilonClosedNextStates(currentStates,
                    (char) c);

            //NFA可不能接受当前Token，需要回退。
            //此时curState为最终接受的Token对应的接受状态，
            //curPriority为该Token的优先权，
            //而curPosition则记录了该Token的长度，
            //即curToken中0 ~ curPosition-1长度的字符串为Token，
            //而curToken中多读进的部分则要在输入流中回退
            if (currentStates.isEmpty()) {
                break;
            }

            for (Iterator iter = currentStates.iterator(); iter.hasNext();) {
                NFAState s = (NFAState) iter.next();
                //如果当前状态集中有接受状态，则该状态对应的Token为NFA可匹配Token，需要对其进行记录
                if (s.isFinal()) {
                    //总是记录优先级最高的
                    if (s.getPriority() > curPriority) {
                        curState = s;
                        curPriority = s.getPriority();
                        curPosition = curToken.length();
                    }
                }
            }
            curPriority = 0;
        }
        //在输入流中回退curToken - Token的部分
        try {
            in.unread(curToken.toCharArray(), curPosition, curToken.length()
                    - curPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //达到不能接受的时候，NFA却未能匹配一个Token，需要抛出异常
        if (curState == null) {
            throw new UnmatchedException();
        }
        //获得Token
        Symbol t = new Symbol(curState.getTokenId(), curToken.substring(0,
                curPosition), line, column);

        //如果该Token需要忽略，则返回抛弃之，继续返回下一个Token
        if (t.getType() == Symbol.IGNORE) {
            return nextToken();
        } else {
            return t;
        } 
    }
}
