rem 该文件是一个dos批处理文件的示例，用于调用实验平台。
rem 你需要根据自己的程序做相应的修改。

@echo off

rem 以下11个变量一般情况下无需修改

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

rem 以上11个变量一般情况下无需修改

rem 如果你实现的是前端，请在这里指定配合使用的后端
set FE_LIB=..\lib\demo_fe.jar

rem 如果你实现的是后端，请在这里指定配合使用的前端
set BE_LIB=..\lib\demo_be.jar

rem 你需要根据实际需要修改变量CLASSPATH。CLASSPATH中必须包含运行实验平台、前端、后端所需要
rem 的所有类的位置。
set CLASSPATH=%PLATFORM_LIB%;%AST_LIB%;%JAVACUP_RUNTIME_LIB%;%FE_LIB%;%BE_LIB%;%SELF_DIR%

if {%1}=={} goto NA
java -classpath %CLASSPATH% edu.ustc.cs.compile.platform.Main %*
goto END

:NA
rem 你需要将..\config\demo.xml修改为自己的实验平台配置文件
java -classpath %CLASSPATH% edu.ustc.cs.compile.platform.Main --cfg-file ..\config\demo.xml

:END

@echo on