#!/bin/bash
CORE_LIB_DIR=../core/lib
CORE_DEV_DIR=../../../core
cp ${CORE_DEV_DIR}/ast/target/ast*.jar ${CORE_LIB_DIR}
cp ${CORE_DEV_DIR}/parser/target/parser*.jar ${CORE_LIB_DIR}
cp ${CORE_DEV_DIR}/typechecker/target/typechecker*.jar ${CORE_LIB_DIR}