@echo off
set ROOT_DIR=..\..\..\
set PLATFORM_DIR=%ROOT_DIR%\platform
set PLATFORM_LIB_DIR=%PLATFORM_DIR%\lib
set PLATFORM_LIB=%PLATFORM_LIB_DIR%\edu.ustc.cs.compile.platform.core.jar;%PLATFORM_LIB_DIR%\edu.ustc.cs.compile.platform.handler.jar;%PLATFORM_LIB_DIR%\edu.ustc.cs.compile.platform.util.jar

set AST_DIR=%ROOT_DIR%\lib\AST\3.1.2
set AST_LIB=%AST_DIR%\org.eclipse.core.resources_3.1.2.jar;%AST_DIR%\org.eclipse.core.runtime_3.1.2.jar;%AST_DIR%\org.eclipse.jdt.core_3.1.2.jar;%AST_DIR%\org.eclipse.jdt.ui_3.1.2.jar

set JAVACUP_DIR=%ROOT_DIR%\tools\java-cup
set JAVACUP_RUNTIME_LIB=%JAVACUP_DIR%\java-cup-11a-runtime.jar

set LIB_DIR=%ROOT_DIR%\lib
set PARSER_LIB=%LIB_DIR%\edu.ustc.cs.compile.parser.skipoominijool.jar
set ARCH_LIB=%LIB_DIR%\edu.ustc.cs.compile.arch.jar

set LAB_CLS_DIR=..\classes

set CLASSPATH=%PLATFORM_LIB%;%AST_LIB%;%JAVACUP_RUNTIME_LIB%;%PARSER_LIB%;%ARCH_LIB%;%LAB_CLS_DIR%

if {%1}=={} goto NA
java -classpath %CLASSPATH% edu.ustc.cs.compile.platform.Main %*
goto END

:NA
java -classpath %CLASSPATH% edu.ustc.cs.compile.platform.Main --cfg-file ..\config\lab5-1.xml

:END

@echo on