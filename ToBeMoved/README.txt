COMPASS Modeling Language

... Table of content ...

  1.  Building CML
  1.1  Building the CML Compiler
  1.1.1.  Lexer
  1.1.2.  Parser
  1.1.3.  Type-Checker
  1.1.4.  Other Analysis
  
  1.2.  Building the CML Compiler Eclipse plugin
  1.2.1  Dependencies
  1.2.2  Building the plug-in
  1.2.3  Installing the plug-in in Eclipse


------------------------------------------------------------
------------------------------------------------------------

1. BUILDING THE CML COMPILER
------------------------------

The CML Compiler is build using Maven, that in turn invokes Ant to
generate and compile the Lexer and the Parser code. The Maven takes
over again building the java sources for the Type-Checker and other
analytic phases.

1.1 BUILDING THE CML COMPILER
------------------------------

To build the CML compiler change to the compiler directory and type

mvn 

Following this step an executable jar can be found packaged in the
dist directory.

1.1.1 LEXER
------------------------------

The Lexer is build using Jlex controlled by an Ant build script.  The
Lexer can be generated separately by changing to the compiler directory
and typing:

 ant generate-lexer


It is compiled together with the Parser by typing:

 ant compile-lexer-parser

However the parser must be built beforehand.

1.1.2 PARSER
------------------------------

The Parser is build using GNU Bison 2.5 controlled by an Ant build
script.  To only generate the Parser change to the compiler directory
and type 

    ant generate-parser

This will also build the Lexer as the parser depends on classes from
the lexer.

To compile both Lexer and Parser type:
   
   ant compile-lexer-parser

Note the entire compiler is built typing:

     mvn

1.1.3 Type Checker
------------------------------

The Type Checker is build using javac invoked by Maven.

1.1.4 Other Analysis
------------------------------
These phases may evolve to use other tools than those mentioned above,
requiring changing the build process. However, the easiest is to
compile it using Maven and javac.

------------------------------------------------------------
------------------------------------------------------------

1.2 Building the CML Compiler 
    Eclipse plugin
------------------------------

To be written

