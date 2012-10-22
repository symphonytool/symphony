To get the Ui building at this time the core componenets needs to be
copied:

For the core plugin copy:

core/
        typechecker/target/typechecker*.jar
        parser/target/parser*.jar
        ast/target/ast*.jar

To:
        ./core/lib

For the RT Tester plugin copy:


core/analysis
        rtt-mbt/target/rtt-mbt*.jar 

To:
        ./rtt-plugin/lib
