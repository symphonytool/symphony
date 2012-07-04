#!/bin/bash
#
# Usage: ./run.sh <example-name>
#
# E.g. ./run.sh types.cml will run the parser on types.cml
# from the ../../docs/cml-examples directory.
#
CLASSPATH=target/classes/:~/.m2/repository/org/overturetool/core/ast-stripped/2.2.0/ast-stripped-2.2.0.jar:../ast/target/ast-0.0.1.jar:target/parser-0.0.1.jar
java -cp ${CLASSPATH} eu.compassresearch.core.parser.CmlParser  ../../docs/cml-examples/$1