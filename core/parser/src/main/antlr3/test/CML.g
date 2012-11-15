grammar CML;

source	:	programParagraph+
	;
	
programParagraph
	:	classDefinition
//	|	processDefinition
//	|	channelDefinition
//	|	chansetDefinitionParagraph
//	|	globalDefinitionParagraph
	;

classDefinition
	:	CLASS IDENTIFIER (EXTENDS IDENTIFIER)? EQUALS BEGIN classDefinitionBlock END
	;

classDefinitionBlock
	:	classDefinitionBlockAlternative*
	;
	
classDefinitionBlockAlternative
	:	typeDefs
//	|	valueDefs
//	|	functionDefs
//	|	operationDefs
//	|	stateDefs
//	|	INITIAL operationDef
	;

typeDefs:	TYPES typeDef*
	;

typeDef	:	QUALIFIER? IDENTIFIER EQUALS type invariant?
	|	QUALIFIER? IDENTIFIER DCOLON field+ invariant?
	;

type	:	LPAREN type RPAREN
	|	basicType
//	|	quoteLiteral
	|	COMPOSE IDENTIFIER OF field+ END
	|	type BAR type
	|	type STAR type
	|	LSQUARE type RSQUARE
	|	SETOF type
	|	SEQOF type
	|	SEQ1OF type
	|	MAPOF type TO type
	|	INMAPOF type TO type
//	|	functionType
	|	dottedIdentifier
	;

basicType
	:	TBOOL | TNAT | TNAT1 | TINT | TRAT | TREAL | TCHAR | TTOKEN
	;

field	:	type
	|	IDENTIFIER COLON type
	|	IDENTIFIER COLONDASH type
	;
	
	
invariant 
	:	IDENTIFIER
//	:	INV pattern DEQUALS expression
	;
	
dottedIdentifier
	:	IDENTIFIER (DOT IDENTIFIER)*
	;
	
IDENTIFIER
	:	('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'|'\''|'_'|'-')* ;
QUOTE_LITERAL
	:	'<' IDENTIFIER '>' ;

BAR	:	'|' ;
BEGIN	:	'begin' ;
CLASS	:	'class' ;
COMPOSE	:	'compose' ;
COLON	:	':' ;
COLONDASH
	:	':-' ;
DCOLON	:	'::' ;
DOT	:	'.' ;
END	:	'end' ;
EQUALS	:	'=' ;
EXTENDS	:	'extends' ;
INITIAL	:	'initial' ;
INMAPOF	:	'inmap' ;
LPAREN	:	'(' ;
LSQUARE :	'[' ;
MAPOF	:	'map' ;
OF	:	'of' ;
QUALIFIER
	:	'private' | 'protected' | 'public' | 'logical' ;
RPAREN 	:	')' ;
RSQUARE	:	']' ;
SEQOF	:	'seq of' ;
SEQ1OF	:	'seq1 of' ;
SETOF	:	'set of' ;
STAR	:	'*';
TBOOL	:	'bool' ;
TCHAR	:	'char' ;
TINT	:	'int' ;
TNAT	:	'nat' ;
TNAT1	:	'nat1' ;
TRAT	:	'rat' ;
TREAL	:	'real' ;
TTOKEN	:	'token' ;
TO	:	'to' ;
TYPES	:	'types' ;
