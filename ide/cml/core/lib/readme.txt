The ide.cml.core component exposes the following libraries 
through the OSGi container to all other bundles:

eu.compassresearch.core.*

This means that Ast, Lexer, Paser and Type Checker jar must be available to 
this core project. This is achieved by including these dependencies in 
this lib folder. 