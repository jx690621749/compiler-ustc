package edu.ustc.cs.compile.lexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>NFAState表示NFA中的状态。</p><p>这个NFA表示成一个图，
 * 其中节点是状态，边是状态转换。</p>
 */
public class NFAState {
    private boolean isFinal; /** 该NFA状态是否是接受状态 */
    private boolean mark = false; /** 用于打印NFA图 */
    private int priority; /** 接受状态的优先级，数越大则优先级越高 */
    private int tokenId; /** 接受状态的记号id，即Symbol.java中对应的编号值 */
    private int id = idCounter++; /** 这个节点的id */
    private Set[] transitions = new Set[256]; /** 转换表 */
    
    private static int idCounter = 0;/**分配ID记号的种子*/
    private static ArrayList stateList = new ArrayList();/**状态列表*/

    public final static char EPSILON = 0x00; /** epsilon */
    
    /**
     * 创建一个接受状态.
     * @param priority 表示优先级
     * @param tokenId 接受状态的记号Id.
     */
    public NFAState(int priority, int tokenId) {
        this(true, priority, tokenId);
    }

    /**
     * 创建一个非接受状态.
     */
    public NFAState() {
        this(false, 0, 0);
    }

    /**
     * Protected构造器.
     * @param isFinal 表示该状态是否是接受状态
     * @param priority 表示优先级
     * @param tokenId 接受状态的记号Id
     */
    protected NFAState(boolean isFinal, int priority, int tokenId) {
        this.isFinal = isFinal;
        this.priority = priority;
        this.tokenId = tokenId;

        stateList.add(this);
    }

    /**
     * @return 是否是接受状态.
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * 使这个状态成为接受状态
     * @param priority 优先级
     * @param tokenId 关联的记号Id
     */
    public void makeFinal(int priority, int tokenId) {
        this.isFinal = true;
        this.priority = priority;
        this.tokenId = tokenId;
    }

    /**
     * @return 返回优先级
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @return 返回关联的记号Id.
     */
    public int getTokenId() {
        return tokenId;
    }
    
    /**
     * @return 返回NFA的字符串表示. 
     */
    public String toString() {
        for(Iterator itr = stateList.iterator(); 
            itr.hasNext();) {
            
            NFAState s = (NFAState) itr.next();
            s.mark = false;
        }
        return this.getStringRep();
    }
    
    /**
     * @return 返回NFA对应的字符串.
     */
    protected String getStringRep() {
        if (mark) {
            return "";
        } 
        
        mark = true;
        String str = "\nNode(" + id + ")";
        if (isFinal()) {
            str += "Final ";
        }
         
        for (int i = 0; i < transitions.length; i++) {
            if (transitions[i] != null) {
                str += "(" + (char)i + ": ";

                for (Iterator li = transitions[i].iterator();
                    li.hasNext();
                    ) {
                    NFAState s = (NFAState) li.next();
                    str += s.id + ", ";
                }
                str += ")";
            }
        }
        
        for (int i = 0; i < transitions.length; i++) {
            if (transitions[i] != null) {
                for (Iterator li = transitions[i].iterator();
                    li.hasNext();
                    ) {
                    NFAState s = (NFAState) li.next();
                    str += s.getStringRep();
                }
            }
        }
        return str;
    }

    /**
     * 增加一个从这个NFA状态到另一个NFA状态的转换.
     * @param in 转换字符, NFAState.EPSILON表示epsilon.
     * @param s  转换到的NFA状态.
     */
    public void addTransition(char in, NFAState s) {
        if (transitions[in] == null) {
            // we use a linked hash set for predictable iteration order,
            // simplifying testing
            transitions[in] = new LinkedHashSet();
        }
        transitions[in].add(s);
    }

    /**
     * 增加一个从这个NFA状态到另一个NFA状态的转换范围. 
     * 例如：为增加字符'a'到'z'的一个转换, 可以调用
     * addTransitionRange('a', 'z', state);
     * 
     * @param lo 字符范围的最小字符.
     * @param hi 字符范围的最大字符.
     * @param s  转换到的状态.
     */
    public void addTransitionRange(char lo, char hi, NFAState s) {
        for (char c = lo; c <= hi; c++)
            this.addTransition(c, s);
    }

    /**
     * 从这个状态经某一字符所能转换到的状态集.
     * @param c 标在转换边上的字符(对于epsilon是NFAState.EPSILON)
     * @return 一组下一个状态
     */
    public Set nextStates(char c) {
        return transitions[c] == null ? Collections.EMPTY_SET : transitions[c];
    }
    
    /**
     * 计算状态的epsilon闭包, 即从这个状态经过epsilon转换可达的状态集
     * @return 可达的状态集
     */
    public Set epsilonClose() {
        Set closure = new LinkedHashSet();
        epsilonCloseHelper(closure);
        return closure;
    }
    
    /**
     * {#epsilonClose}的辅助方法
     * @param closure the closure thus far
     * @return 闭包
     */
    private void epsilonCloseHelper(Set closure) {
        // if we're already in the closure, do nothing
        if (closure.add(this)) {
            Set immediatelyReachable = nextStates(NFAState.EPSILON);
            for (Iterator iter = immediatelyReachable.iterator();
                iter.hasNext();
                ) {
                NFAState state = (NFAState) iter.next();
                // add the closure from next states
                state.epsilonCloseHelper(closure);
            }
        }
    }

    /**
     * 给定一个状态集, 求得这个它在面临一个特定字符下，所能转换到的状态集
     * 执行epsilon状态闭包
     * @param states 初始的状态集
     * @param c 用于转换的字符
     * @return 到达的状态集
     */
    public static Set epsilonClosedNextStates(Set states, char c) {
        // use a linked hash set for predictable iteration order
        Set reachable = new LinkedHashSet();
        for (Iterator iter = states.iterator(); iter.hasNext();) {
            NFAState curState = (NFAState) iter.next();
            Set reachableOnTransition = curState.nextStates(c);
            // add epsilon closure of all these states
            for (Iterator iterator = reachableOnTransition.iterator();
                iterator.hasNext();
                ) {
                NFAState nextState = (NFAState) iterator.next();
                reachable.addAll(nextState.epsilonClose());
            }
        }
        return reachable;
    }

}