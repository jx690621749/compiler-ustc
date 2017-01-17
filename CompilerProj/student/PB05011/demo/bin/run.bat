rem ���ļ���һ��dos�������ļ���ʾ�������ڵ���ʵ��ƽ̨��
rem ����Ҫ�����Լ��ĳ�������Ӧ���޸ġ�

@echo off

rem ����11������һ������������޸�

set ROOT_DIR=..\..\..\
set PLATFORM_DIR=%ROOT_DIR%\platform
set PLATFORM_LIB_DIR=%PLATFORM_DIR%\lib
set PLATFORM_LIB=%PLATFORM_LIB_DIR%\edu.ustc.cs.compile.platform.core.jar;%PLATFORM_LIB_DIR%\edu.ustc.cs.compile.platform.interfaces.jar;%PLATFORM_LIB_DIR%\edu.ustc.cs.compile.platform.util.jar

set AST_DIR=%ROOT_DIR%\lib\AST\3.1.2
set AST_LIB=%AST_DIR%\org.eclipse.core.resources_3.1.2.jar;%AST_DIR%\org.eclipse.core.runtime_3.1.2.jar;%AST_DIR%\org.eclipse.jdt.core_3.1.2.jar;%AST_DIR%\org.eclipse.jdt.ui_3.1.2.jar

set JAVACUP_DIR=%ROOT_DIR%\tools\java-cup
set JAVACUP_RUNTIME_LIB=%JAVACUP_DIR%\java-cup-11a-runtime.jar

set JAVACC_DIR=%ROOT_DIR%\tools\javacc
set JAVACC_RUNTIME_LIB=%JAVACC_DIR%\javacc.jar

set SELF_DIR=..\classes

rem ����11������һ������������޸�

rem �����ʵ�ֵ���ǰ�ˣ���������ָ�����ʹ�õĺ��
set FE_LIB=..\lib\demo_fe.jar

rem �����ʵ�ֵ��Ǻ�ˣ���������ָ�����ʹ�õ�ǰ��
set BE_LIB=..\lib\demo_be.jar

rem ����Ҫ����ʵ����Ҫ�޸ı���CLASSPATH��CLASSPATH�б����������ʵ��ƽ̨��ǰ�ˡ��������Ҫ
rem ���������λ�á�
set CLASSPATH=%PLATFORM_LIB%;%AST_LIB%;%JAVACUP_RUNTIME_LIB%;%FE_LIB%;%BE_LIB%;%SELF_DIR%

if {%1}=={} goto NA
java -classpath %CLASSPATH% edu.ustc.cs.compile.platform.Main %*
goto END

:NA
rem ����Ҫ��..\config\demo.xml�޸�Ϊ�Լ���ʵ��ƽ̨�����ļ�
java -classpath %CLASSPATH% edu.ustc.cs.compile.platform.Main --cfg-file ..\config\demo.xml

:END

@echo on