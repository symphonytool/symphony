grammar Cml;
options {
    language = Java;
    output=AST;
}

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
	:	'class' IDENTIFIER ('extends' IDENTIFIER)? '=' 'begin' classDefinitionBlock 'end'
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
    :	'types' typeDef*
	;

typeDef
	:	QUALIFIER? IDENTIFIER '=' type invariant?
	|	QUALIFIER? IDENTIFIER '::' field+ invariant?
	;

type
    :	type0 (('+>'|'->') type0)?
    |	'()' (('+>'|'->') type0)?
	;

type0op : '*' | '|' ;
type0
    :   type1 (type0op type1)*
    ;

type1
    :   basicType
	|	'(' type ')'
	|	'[' type ']'
	|	QUOTELITERAL
	|	IDENTIFIER ('.' IDENTIFIER)*
	|	'compose' IDENTIFIER 'of' field+ 'end'
	|	'set of' type1
	|	'seq of' type1
	|	'seq1 of' type1
	|	'map of' type1 'to' type1
	|	'inmap of' type1 'to' type1
	;

basicType
	:	'bool' | 'nat' | 'nat1' | 'int' | 'rat' | 'real' | 'char' | 'token'
	;

field
	:	type
	|	IDENTIFIER ':' type
	|	IDENTIFIER ':-' type
	;

invariant 
	:	'inv' pattern '==' expression
	;

pattern
    : patternIdentifier
    | matchValue
    | tuplePattern
    | recordPattern
    ;	

patternIdentifier
    : IDENTIFIER
    | '-'
    ;

matchValue
    : '(' expression ')'
    | symbolicLiteral
    ;

symbolicLiteral
    : numLiteral
    | boolLiteral
    | 'nil'
    | CHARLITERAL
    | TEXTLITERAL
    | QUOTELITERAL
    ;

numLiteral
    : DECIMAL
    | HEXLITERAL
    ;

boolLiteral
    : 'true' | 'false'
    ;

tuplePattern
    : MKUNDERLPAREN pattern (',' pattern)* ')'
    ;

recordPattern
    : MKUNDERNAMELPAREN (pattern (',' pattern)+)* ')'
    ;

expression
    : expr0
    | 'let' localDefinition (',' localDefinition)* 'in' expression
    | 'if' expression 'then' expression ('elseif' expression 'then' expression)* 'else' expression
    | 'cases' expression ':' (pattern (',' pattern)* '->' expression (',' pattern (',' pattern)* '->' expression)* )? (',' 'others' '->' expression)? 'end'
    | 'forall' bind+ '@' expression
    | 'exists' bind+ '@' expression
    | 'exists1' bind '@' expression
    | 'iota' bind '@' expression
    | 'lambda' typeBind+ '@' expression
    ;

expr0op : '+' | '-' ;
expr0
    : expr1 (expr0op expression)?
    ;

expr1
    : expr2 TUPLESELECTOR?
    ;

expr2
    : '{' setMapExpr? '}'
    | '[' seqExpr? ']'
    | MKUNDERLPAREN expression (',' expression)+ ')'
    | MKUNDERNAMELPAREN ( expression (',' expression)* )? ')'
    | ISOFCLASSLPAREN IDENTIFIER ('.' IDENTIFIER)* ',' expression ')'
    | ISUNDERLPAREN expression ',' type ')'
    | ISUNDERBASICLPAREN expression ')'
    | ISUNDERNAMELPAREN expression ')'
    | PREUNDERLPAREN expression (',' expression)* ')'
// | subsequence
// | apply
    | exprbase ( '(' ( expression (',' '...' ',' expression | (',' expression)+ )? )? ')' )?
    ;

exprbase
    : '(' expression ')'
    | 'self'
// | name
// | old name
// | field select
    | IDENTIFIER ('.' IDENTIFIER)* '~'?
    | symbolicLiteral
    ;


setMapExpr
    : expression setMapExprTail?
    | '|->'
    ;

setMapExprTail
    : ',' '...' ',' expression
    | ( ',' expression )+
    | '|->' expression mapExprTail?
    | setMapExprBinding
    ;

mapExprTail
    : ( ',' expression '|->' expression )+
    | setMapExprBinding
    ;

setMapExprBinding
    : '|' bind+ ('@' expression)? 
    ;

/* sequence enumeration = '[', [ expression list ], ']' ;
 * sequence comprehension = '[', expression, '|', set bind, [ '@', expression ], ']' ;
 */
seqExpr
    : expression ( (',' expression)* | '|' setBind ('@' expression)? | ',' '...' ','  expression )
    ;

localDefinition
    : IDENTIFIER
    ;
	
bind: IDENTIFIER
    ;

setBind
    : IDENTIFIER
    ;

typeBind
    : IDENTIFIER
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
    : 'public' | 'protected' | 'private' | 'logical' ;

// NILLITERAL
//     : 'nil'
//     ;

QUOTELITERAL
    : '<' IDENTIFIER '>'
    ;

CHARLITERAL
    : '\\\\' | '\\r' | '\\n' | '\\t' | '\\f' | '\\e' | '\\a' | '\\"' | '\\\''
    | '\\x' HEXDIGIT HEXDIGIT
    | '\\u' HEXDIGIT HEXDIGIT HEXDIGIT HEXDIGIT
    | '\\' OCTDIGIT OCTDIGIT OCTDIGIT
    // | '\\c' character
    ;

PREUNDERLPAREN
    : 'pre_('
    ;

MKUNDERLPAREN
    : 'mk_('
    ;

MKUNDERNAMELPAREN
    : 'mk_' IDENTIFIER ('.' IDENTIFIER)* '('
    ;

ISOFCLASSLPAREN
    : 'isofclass('
    ;

ISUNDERLPAREN
    : 'is_('
    ;

ISUNDERBASICLPAREN
    : 'is_' ('bool' | 'nat' | 'nat1' | 'int' | 'rat' | 'real' | 'char' | 'token') '('
    ;

ISUNDERNAMELPAREN
    : 'is_' IDENTIFIER ('.' IDENTIFIER)* '('
    ;

/* Need to fix this, yet
 */
TEXTLITERAL
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
fragment
INITIAL_LETTER
    : '\u0024' | '\u0041'..'\u005a' | '\u0061'..'\u007a' | '\u00AA' | '\u00BA' | '\u00B5' | '\u00c0'..'\u00FF'
    ;
fragment
FOLLOW_LETTER
    : INITIAL_LETTER | DIGIT
    ;

IDENTIFIER
    : INITIAL_LETTER FOLLOW_LETTER*
    ;

// NAME
//     : IDENTIFIER ('.' IDENTIFIER)?
//     ;

fragment
OCTDIGIT
    : '0'..'7'
    ;

fragment
DIGIT
    : '0'..'9'
    ;

fragment
HEXDIGIT
    : DIGIT
    | 'a'..'f'
    | 'A'..'F'
    ;

HEXLITERAL
    : ('0x'|'0X') HEXDIGIT+
    ;

DECIMAL
    : DIGIT+ ('.' DIGIT+)? ( ('E'|'e') ('+'|'-')? DIGIT+ )?
	;

TUPLESELECTOR
    : '.#' DIGIT+
    ;

// CSPCHAOS : 'Chaos';
// CSPSKIP : 'Skip';
// CSPSTOP : 'Stop';
// CSPWAIT : 'Wait';
// ABS : 'abs';
// ACTIONS : 'actions';
// ALL : 'all';
// AND : 'and';
// ATOMIC : 'atomic';
// BEGIN : 'begin';
// TBOOL : 'bool';
// BY : 'by';
// CARD : 'card';
// CASES : 'cases';
// CHANNELS : 'channels';
// CHANSETS : 'chansets';
// TCHAR : 'char';
// CLASS : 'class';
// COMP : 'comp';
// COMPOSE : 'compose';
// CONC : 'conc';
// DCL : 'dcl';
// DINTER : 'dinter';
// CSPDIV : 'Div';
// DIV : 'div';
// DO : 'do';
// DOM : 'dom';
// DUNION : 'dunion';
// ELEMS : 'elems';
// ELSE : 'else';
// ELSEIF : 'elseif';
// END : 'end';
// ENDSBY : 'endsby';
// EXISTS : 'exists';
// EXISTS1 : 'exists1';
// EXTENDS : 'extends';
// FALSE : 'false';
// FLOOR : 'floor';
// FOR : 'for';
// FORALL : 'forall';
// FRAME : 'frame';
// FUNCTIONS : 'functions';
// HD : 'hd';
// IF : 'if';
// INSET : 'in set';
// IN : 'in';
// INDS : 'inds';
// INITIAL : 'initial';
// INMAPOF : 'inmap';
// TINT : 'int';
// INTER : 'inter';
// INV : 'inv';
// INVERSE : 'inverse';
// IOTA : 'iota';
// ISOFCLASS : 'isofclass';
// NOTYETSPEC : 'is not yet specified';
// SUBCLASSRESP : 'is subclass responsibility';
// LAMBDA : 'lambda';
// LEN : 'len';
// LET : 'let';
// LOGICAL : 'logical';
// MAPOF : 'map';
// MEASURE : 'measure';
// MERGE : 'merge';
// MOD : 'mod';
// MU : 'mu';
// MUNION : 'munion';
// NAMESETS : 'namesets';
// TNAT : 'nat';
// TNAT1 : 'nat1';
// NEW : 'new';
// NIL : 'nil';
// NOTINSET : 'not in set';
// NOT : 'not';
// OPERATIONS : 'operations';
// OF : 'of';
// OR : 'or';
// OTHERS : 'others';
// POST : 'post';
// POWER : 'power';
// PRE : 'pre';
// PREUNDER : 'pre_';
// PRIVATE : 'private';
// PROCESS : 'process';
// PROTECTED : 'protected';
// PSUBSET : 'psubset';
// PUBLIC : 'public';
// TRAT : 'rat';
// RD : 'rd';
// TREAL : 'real';
// REM : 'rem';
// RES : 'res';
// RETURN : 'return';
// REVERSE : 'reverse';
// RNG : 'rng';
// SELF : 'self';
// SEQOF : 'seq of';
// SEQ1OF : 'seq1 of';
// SETOF : 'set of';
// STARTBY : 'startby';
// STATE : 'state';
// SUBSET : 'subset';
// THEN : 'then';
// TL : 'tl';
// TO : 'to';
// TTOKEN : 'token';
// TRUE : 'true';
// TYPES : 'types';
// UNION : 'union';
// VAL : 'val';
// VALUES : 'values';
// VRES : 'vres';
// WHILE : 'while';
// WR : 'wr';

// AMP : '&';
// AT : '@';
// BACKSLASH : '\\';
// BANG : '!';
// BAR : '|';
// BARRARROW : '|->';
// BARRCURLY : '|}';
// BARGT : '|>';
// BARRSQUARE : '|]';
// BARTILDEBAR : '|~|';
// CARET : '^';
// COLON : ':';
// COLONBACKSLASH : ':\\';
// COLONDASH : ':-';
// COLONDASHGT : ':->';
// COLONEQUALS : ':=';
// COLONGT : ':>';
// COMMA : ',';
// DBACKSLASH : '\\\\';
// DBAR : '||';
// DBARRSQUARE : '||]';
// DCOLON : '::';
// DEQRARROW : '==>';
// DEQUALS : '==';
// DLSQUARE : '[[';
// DOT : '.';
// DOTHASH : '.#';
// DPLUS : '++';
// DRSQUARE : ']]';
// DSTAR : '**';

// yes, the ellipsis includes the commas all as a single token
// ELLIPSIS
//     : ',' '...' ','
//     ;
// EMPTYMAP
//     : '{' '|->' '}'
//     ;

// EQRARROW : '=>';
// EQUALS : '=';
// GT : '>';
// GTE : '>=';
// LARROW : '<-';
// LCURLY : '{';
// LCURLYBAR : '{|';
// LPAREN : '(';

// LRPAREN
//     : '(' ')'
//     ;

// LRSQUARE : '[]';
// LSQUARE : '[';
// LSQUAREBAR : '[|';
// LSQUAREDBAR : '[||';
// LSQUAREGT : '[>';
// LT : '<';
// LTCOLON : '<:';
// LTDASHCOLON : '<-:';
// LTE : '<=';
// LTEQUALSGT : '<=>';
// MINUS : '-';
// NEQ : '<>';
// PLUS : '+';
// PLUSGT : '+>';
// QUESTION : '?';
// RARROW : '->';
// RCURLY : '}';
// RPAREN : ')';
// RSQUARE : ']';
// SEMI : ';';
// SLASH : '/';
// SLASHBACKSLASH : '/\\';
// SLASHCOLON : '/:';
// STAR : '*';
// TBAR : '|||';
// TILDE : '~';
// BACKTICK : '`';

/* ---- complex terminals below ---- */


