#!/bin/sh

ROOT_DIR=../../../
PLATFORM_DIR=${ROOT_DIR}/platform
PLATFORM_LIB_DIR=${PLATFORM_DIR}/lib
PLATFORM_LIB=${PLATFORM_LIB_DIR}/edu.ustc.cs.compile.platform.core.jar:\
${PLATFORM_LIB_DIR}/edu.ustc.cs.compile.platform.handler.jar:\
${PLATFORM_LIB_DIR}/edu.ustc.cs.compile.platform.util.jar

AST_DIR=${ROOT_DIR}/lib/AST/3.1.2
AST_LIB=${AST_DIR}/org.eclipse.core.resources_3.1.2.jar:\
${AST_DIR}/org.eclipse.core.runtime_3.1.2.jar:\
${AST_DIR}/org.eclipse.jdt.core_3.1.2.jar:\
${AST_DIR}/org.eclipse.jdt.ui_3.1.2.jar

JAVACUP_RUNTIME_LIB=${ROOT_DIR}/tools/java-cup/java-cup-11a-runtime.jar
JAVACC_RUNTIME_LIB=${ROOT_DIR}/tools/javacc/javacc.jar

LIB_DIR=${ROOT_DIR}/lib
PARSER_LIB=${LIB_DIR}/edu.ustc.cs.compile.parser.skipoominijool.jar

LAB_CLS_DIR=../classes

CLASSPATH=${PLATFORM_LIB}:${AST_LIB}:${JAVACUP_RUNTIME_LIB}:${JAVACC_RUNTIME_LIB}:${PARSER_LIB}:${LAB_CLS_DIR}

if [ $# -lt 1 ]; then
    java -classpath ${CLASSPATH} \
         edu.ustc.cs.compile.platform.Main \
         --cfg-file ../config/configure.xml
else
    java -classpath ${CLASSPATH} \
         edu.ustc.cs.compile.platform.Main $*
fi