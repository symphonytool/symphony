#!/bin/bash

export CLASSPATH="/Users/jwc/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:/Users/jwc/.m2/repository/org/antlr/antlr-runtime/3.4/antlr-runtime-3.4.jar:../ast/target/classes/:./target/classes/:$CLASSPATH"

java -cp $CLASSPATH eu.compassresearch.core.parser.CmlParser
