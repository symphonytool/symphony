#!/bin/bash
CLASSPATH=target:.:../ast/target/ast-0.0.1.jar:target/parser-0.0.1.jar
java -cp ${CLASSPATH} eu.compassresearch.core.parser.CmlParser  ../../docs/cml-examples/$1