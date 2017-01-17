package edu.ustc.cs.compile.lexer;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

/**
 * 词法分析器抽象类
 */
public abstract class Lexer {

    /** 一个可回退的读取器 */
    protected PushbackReader in = null;
    
    /**
     * 取得下一个词法记号，请在子类中实现它。
     * @return 返回的词法记号
     */
    public abstract Symbol nextToken();
    
    /**
     * 词法分析器的构造函数
     * @param infile 输入文件名
     */
    public Lexer(String infile) {
        PushbackReader reader = null;
        try {
            reader = new PushbackReader(new FileReader(infile));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        in = reader;
    }
    
    /**
     * 词法分析器的构造函数
     * @param reader 一个可回退的读取器
     */
    public Lexer(PushbackReader reader) {
        in = reader;
    }
}