grammar Cml;

source
    :	programParagraph+
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

typeDefs
    :	TYPES typeDef*
	;

typeDef
	:	QUALIFIER? IDENTIFIER EQUALS type invariant?
	|	QUALIFIER? IDENTIFIER '::' field+ invariant?
	;

type:	type0 (BAR type0)*
	;

type0
    :   type1 (STAR type1)*
    ;

type1
    :   basicType
	|	LPAREN type RPAREN
	|	QUOTELITERAL
	|	COMPOSE IDENTIFIER OF field+ END
	|	LSQUARE type RSQUARE
	|	SETOF type1
	|	SEQOF type1
	|	SEQ1OF type1
	|	MAPOF type1 TO type1
	|	INMAPOF type1 TO type1
//	|	functionType
	|	NAME
	;

basicType
	:	TBOOL | TNAT | TNAT1 | TINT | TRAT | TREAL | TCHAR | TTOKEN
	;

field
	:	type
	|	IDENTIFIER ':' type
	|	IDENTIFIER COLONDASH type
	;

invariant 
	:	IDENTIFIER
//	:	INV pattern DEQUALS expression
	;
	

	
/* ********************************************************** */
/* ***              SCANNER PRODUCTION RULES              *** */
/* ********************************************************** */


WHITESPACE
    : (' ' | '\t' | '\r' | '\n')+ { $channel=HIDDEN; }
    ;
LINECOMMENT
    : ( '//' | '--' ) .* '\n' { $channel=HIDDEN; }
    ;
MLINECOMMENT
    : '/*' .* '*/' { $channel=HIDDEN; }
    ;

QUALIFIER
    : 'public'
    | 'protected'
    | 'private'
    | 'logical'
    ;

CSPCHAOS : 'Chaos';
CSPSKIP : 'Skip';
CSPSTOP : 'Stop';
CSPWAIT : 'Wait';
ABS : 'abs';
ACTIONS : 'actions';
ALL : 'all';
AND : 'and';
ATOMIC : 'atomic';
BEGIN : 'begin';
TBOOL : 'bool';
BY : 'by';
CARD : 'card';
CASES : 'cases';
CHANNELS : 'channels';
CHANSETS : 'chansets';
TCHAR : 'char';
CLASS : 'class';
COMP : 'comp';
COMPOSE : 'compose';
CONC : 'conc';
DCL : 'dcl';
DINTER : 'dinter';
CSPDIV : 'Div';
DIV : 'div';
DO : 'do';
DOM : 'dom';
DUNION : 'dunion';
ELEMS : 'elems';
ELSE : 'else';
ELSEIF : 'elseif';
END : 'end';
ENDSBY : 'endsby';
EXISTS : 'exists';
EXISTS1 : 'exists1';
EXTENDS : 'extends';
FALSE : 'false';
FLOOR : 'floor';
FOR : 'for';
FORALL : 'forall';
FRAME : 'frame';
FUNCTIONS : 'functions';
HD : 'hd';
IF : 'if';
INSET : 'in set';
IN : 'in';
INDS : 'inds';
INITIAL : 'initial';
INMAPOF : 'inmap';
TINT : 'int';
INTER : 'inter';
INV : 'inv';
INVERSE : 'inverse';
IOTA : 'iota';
ISOFCLASS : 'isofclass';
NOTYETSPEC : 'is not yet specified';
SUBCLASSRESP : 'is subclass responsibility';
LAMBDA : 'lambda';
LEN : 'len';
LET : 'let';
//LOGICAL : 'logical';
MAPOF : 'map';
MEASURE : 'measure';
MERGE : 'merge';
MOD : 'mod';
MU : 'mu';
MUNION : 'munion';
NAMESETS : 'namesets';
TNAT : 'nat';
TNAT1 : 'nat1';
NEW : 'new';
NIL : 'nil';
NOTINSET : 'not in set';
NOT : 'not';
OPERATIONS : 'operations';
OF : 'of';
OR : 'or';
OTHERS : 'others';
POST : 'post';
POWER : 'power';
PRE : 'pre';
PREUNDER : 'pre_';
//PRIVATE : 'private';
PROCESS : 'process';
//PROTECTED : 'protected';
PSUBSET : 'psubset';
//PUBLIC : 'public';
TRAT : 'rat';
RD : 'rd';
TREAL : 'real';
REM : 'rem';
RES : 'res';
RETURN : 'return';
REVERSE : 'reverse';
RNG : 'rng';
SELF : 'self';
SEQOF : 'seq of';
SEQ1OF : 'seq1 of';
SETOF : 'set of';
STARTBY : 'startby';
STATE : 'state';
SUBSET : 'subset';
THEN : 'then';
TL : 'tl';
TO : 'to';
TTOKEN : 'token';
TRUE : 'true';
TYPES : 'types';
UNION : 'union';
VAL : 'val';
VALUES : 'values';
VRES : 'vres';
WHILE : 'while';
WR : 'wr';

QUOTELITERAL
    : '<' IDENTIFIER '>'
    ;

MKUNDER
    : 'mk_'
    ;
MKUNDERNAME
    : 'mk_' NAME
    ;

ISUNDER
    : 'is_'
    ;
ISUNDERNAME
    : 'is_' NAME
    ;

AMP : '&';
AT : '@';
BACKSLASH : '\\';
BANG : '!';
BAR : '|';
BARRARROW : '|->';
BARRCURLY : '|}';
BARGT : '|>';
BARRSQUARE : '|]';
BARTILDEBAR : '|~|';
CARET : '^';
//COLON : ':';
COLONBACKSLASH : ':\\';
COLONDASH : ':-';
COLONDASHGT : ':->';
COLONEQUALS : ':=';
COLONGT : ':>';
COMMA : ',';
DBACKSLASH : '\\\\';
DBAR : '||';
DBARRSQUARE : '||]';
DCOLON : '::';
DEQRARROW : '==>';
DEQUALS : '==';
DLSQUARE : '[[';
DOT : '.';
DOTHASH : '.#';
DPLUS : '++';
DRSQUARE : ']]';
DSTAR : '**';

// yes, the ellipsis includes the commas all as a single token
ELLIPSIS
    : ',' '...' ','
    ;
EMPTYMAP
    : '{' '|->' '}'
    ;


EQRARROW : '=>';
EQUALS : '=';
GT : '>';
GTE : '>=';
LARROW : '<-';
LCURLY : '{';
LCURLYBAR : '{|';
LPAREN : '(';

LRPAREN
    : '(' ')'
    ;

LRSQUARE : '[]';
LSQUARE : '[';
LSQUAREBAR : '[|';
LSQUAREDBAR : '[||';
LSQUAREGT : '[>';
LT : '<';
LTCOLON : '<:';
LTDASHCOLON : '<-:';
LTE : '<=';
LTEQUALSGT : '<=>';
MINUS : '-';
NEQ : '<>';
PLUS : '+';
PLUSGT : '+>';
QUESTION : '?';
RARROW : '->';
RCURLY : '}';
RPAREN : ')';
RSQUARE : ']';
SEMI : ';';
SLASH : '/';
SLASHBACKSLASH : '/\\';
SLASHCOLON : '/:';
STAR : '*';
TBAR : '|||';
TILDE : '~';
BACKTICK : '`';

/* ---- complex terminals below ---- */

/* Need to fix this, yet
 */
STRING
    : '"' .* '"'
    ;

/* This only tracks the non-unicode chunk of the characters; this
 * needs to be extended into the unicode set per the basic VDM spec.
 *
 * initial letter:
 *   if codepoint < U+0100
 *   then Any character in categories Ll, Lm, Lo, Lt, Lu or U+0024 (a dollar sign)
 *   else Any character except categories Cc, Zl, Zp, Zs, Cs, Cn, Nd, Pc
 * following letter:
 *   if codepoint < U+0100
 *   then Any character in categories Ll, Lm, Lo, Lt, Lu, Nd or U+0024 (a dollar sign) or U+005F (underscore) or U+0027 (apostrophe)
 *   else Any character except categories Cc, Zl, Zp, Zs, Cs, Cn
 */
IDENTIFIER
    : ( '\u0024' | '\u0041'..'\u005a' | '\u0061'..'\u007a' | '\u00AA' | '\u00BA' | '\u00B5' | '\u00c0'..'\u00FF' )
      ( '\u0024' | '\u0041'..'\u005a' | '\u0061'..'\u007a' | '\u00AA' | '\u00BA' | '\u00B5' | '\u00c0'..'\u00FF' | DIGIT )*
    ;
NAME: IDENTIFIER '`' IDENTIFIER ;


DIGIT
    : '0'..'9'
    ;

NUMERAL
    : DIGIT+
    ;

HEXLITERAL
    : (DIGIT|'a'..'f')+
    ;

DECIMAL
    : NUMERAL '.' NUMERAL ( ('E'|'e') ('+'|'-') NUMERAL )?
	;

