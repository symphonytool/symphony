/* loose threads:
 *
 * communication prefixes using '.' separators are still causing problems
 * -> restriction in place: '.','!' may only be followed by ids,
 *    (expr), symLit, and records/tuples; '?' is only followed by a
 *    bindablePattern and optionally a set/map expr that delimited by
 *    curlies
 *
 * conflict on call/new/assign; really, really ought to re-think how
 * that is assembled.
 * ... We still have an inherent conflict between opcalls and fncalls,
 *   as syntactically there's no difference in the basic cases.  Both
 *   are essentially ID '('expr*')'.  I've added lhs '<-' call as a
 *   rule alternative.
 *
 * expression/statement precedence has still to be resolved
 *
 * make type not allow '()' unless coming from a function?
 */
grammar Cml;
options {
    language=Java;
    output=AST;
}
/* This is necessary for the lexer to disambiguate QUOTELITERALS and a
 * '<' followed by an IDENTIFIER that is not followed by a '>'.
 */
tokens {
    LESSTHAN = '<';
}

@members {
public String getErrorMessage(RecognitionException e, String[] tokenNames) {
    List stack = getRuleInvocationStack(e, this.getClass().getName());
    String msg = null;
    if (e instanceof NoViableAltException) {
        NoViableAltException nvae = (NoViableAltException)e;
        msg = " no viable alt; token="+e.token+
            " (decision="+nvae.decisionNumber+
            " state "+nvae.stateNumber+")"+
            " decision=<<"+nvae.grammarDecisionDescription+">>";
    } else {
        msg = super.getErrorMessage(e, tokenNames);
    }
    return stack+" "+msg;
}
public String getTokenErrorDisplay(Token t) {
    return t.toString();
}
}

source
    : programParagraph+
    ;

programParagraph
    : classDefinition
    | processDefinition
    | channelDefs
    | chansetDefs
    | typeDefs
    | valueDefs
    | functionDefs
    ;

classDefinition
    : 'class' IDENTIFIER ('extends' IDENTIFIER)? '=' 'begin' classDefinitionBlock* 'end'
    ;

processDefinition
    : 'process' IDENTIFIER '=' (declaration (';' declaration)* '@')? process
    ;

process
    : proc0 procOps process
    | replOp replicationDeclaration '@' ( '[' expression ']' )? process
    ;

procOps
    : ';' | '[]' | '|~|' | '||' | '|||'
    | '/\\' | '//' expression '\\\\' // not sure if the empty /\ and [> should be here
    | '[>' | '[[' expression '>>'
    | '[|' expression '|]'
    | '[' expression '||' expression ']'
    ;

proc0
    : proc1 ('[[' renamingExpr ']]')?
    ;

replOp
    : ';' | '[]' | '|~|' | '||' | '|||'
    | '[|' expression '|]'
    ;

proc1
    : proc2 proc1ops expression
    ;

proc1ops
    : '\\\\' | 'startsby' | 'endsby'
    ;

proc2
    : 'begin' processParagraph* '@' action 'end'
    // merge of (process) | identifier [({expression})] | (decl@proc)({expression})
    | ( IDENTIFIER | '(' (declaration (';' declaration)* '@')? process ')' ) ( '(' ( expression ( ',' expression )* )? ')'  )?
    ;

declaration
    : PMODE? IDENTIFIER (',' IDENTIFIER)* (':' type)?
    ;

replicationDeclaration
    : replicationDecl (';' replicationDecl)*
    ;

replicationDecl
    : IDENTIFIER ( ',' IDENTIFIER )* ( ':' type | 'in' 'set' expression )  // FIXME
    ;

renamingExpr
    : renamePair ( ( ',' renamePair )+ | '|' bind+ ('@' expression)? )?
    ;

renamePair
    : IDENTIFIER ( '.' '(' expression ')' )* '<-' IDENTIFIER ( '.' '(' expression ')' )*
    ;

processParagraph
    : typeDefs
    | valueDefs
    | stateDefs
    | functionDefs
    | operationDefs
    | actionDefs
    | namesetDefs
    ;

actionDefs
    : 'actions' actionDef*
    ;

actionDef
    : IDENTIFIER '=' (declaration '@')? action
    ;

action
    : action0 actionOps action
    | ( replOp | actionReplOp ) replicationDeclaration '@' ( '[' expression ( '|' expression )? ']' )? process
    ;

actionOps
    : ';' | '[]' | '|~|'
    | '/\\' | '//' expression '\\\\' // not sure if the empty /\ and [> should be here
    | '[>' | '[[' expression '>>'
    | '||' | '|||'
    | '['  expression ( '|'  expression )? '||'  expression ( '|'  expression )? ']'
    | '[|' expression ( '|'  expression ( '|'  expression )? )? '|]'
    | '[||' expression '|' expression '||]'
    ;

actionReplOp
    : '[||' expression '||]'
    ;

action0
    : action1 ( '[[' renamingExpr ']]' )?
    ;

action1
    : action2 action1Ops expression
    ;

action1Ops
    : '\\\\' | 'startsby' | 'endsby'
    ;

action2
    : 'Skip' | 'Stop' | 'Chaos' | 'Div' | 'Wait' expression
    /* The mess below includes parenthesized actions, block
     * statements, parametrised actions, instantiated actions.
     */
    | '(' ( ( declaration (';' declaration)* | 'dcl' assignmentDefinition (';' assignmentDefinition)* ) '@' )? action ')' ( '(' expression (',' expression )* ')' )?
    | IDENTIFIER communication* '->' action
    | '[' expression ']' '&' action // Still need [] around the expr; conflict: action2 -> (action) and expression...-> (expression)
    | 'mu' IDENTIFIER '@'  action (',' action)*
    | statement
    ;

/* FIXME Ok, dots are still fragile
 *
 * In this case, it's IDENTIFIER '.' IDENTIFIER that kills us.  I
 * think we could get away with splitting the '.' case so that
 * arbitary expressions are ()'d, but constants and identifiers are
 * clear.
 *
 * ( '!' expression '.' IDENTIFER ) still creates an ambiguity as to
 * whether, say, ...!x.y was really ...(!x).y or ...!(x.y)
 */
communication
    : ('.' | '!') ( IDENTIFIER | '(' expression ')' | symbolicLiteral | recordTupleExprs )
    | '?' bindablePattern ( 'in' 'set' setMapExprs )?
    ;

statement
    : 'let' localDefinition (',' localDefinition)* 'in' action
    | '[' ('frame' frameSpec (',' frameSpec)* )? ('pre' expression)? 'post' expression ']'
    | 'do' nonDetStmtAlt ( '[]' nonDetStmtAlt )* 'end'
    | 'if' expression
        ( '->' action ( '[]' nonDetStmtAlt )* 'end'
        | 'then' action ( 'elseif' expression 'then' action )* 'else' action
        )
    | 'cases' expression ':' (pattern (',' pattern)* '->' action (',' pattern (',' pattern)* '->' action)* )? (',' 'others' '->' expression)? 'end'
    | 'for'
        ( bindablePattern 'in' expression // was pattern bind here only
        | 'all' bindablePattern 'in' 'set' expression
        | IDENTIFIER '=' expression 'to' expression ( 'by' expression )?
        ) 'do' action
    | 'return' expression?
    | 'while' expression 'do' action
    | 'atomic' '(' stateDesignator ':=' expression ( ';' stateDesignator ':=' expression )+ ')'
    | (callStatement)=> callStatement // More syntactic predicate magic :)
    | stateDesignator
        ( ':=' ( expression | 'new' name '(' expression ( ',' expression )* ')' )//| callStatement )
        | '<-' callStatement
        )
    ;

nonDetStmtAlt
    : expression '->' action
    ;

frameSpec
    : FRAMEMODE name (',' name)* (':' type)?
    ;

stateDesignator
    : name sDTail?
    ;

sDTail
    : '(' expression ')' ( '.' stateDesignator | sDTail )?
    ;

/* This does not support the 'object apply' form of the
 * objectDesignator, but I don't know what that's for, anyway.
 * Chained calls?  That should be more general anyway.
 *
 */
callStatement
    : name '(' ( expression ( ',' expression )* )? ')'
    ;

/* Ok, this is cute.  It works both with and without semicolons,
 * though it may not be visually obvious (without the semis) that it
 * is a space that separates untyped channels from typed ones. (-jwc)
 */
channelDefs
    : 'channels' channelDef*
    // : 'channels' ( channelDef (';' channelDef)+ )?
    ;

channelDef
    : IDENTIFIER (',' IDENTIFIER)* (':' type)?
    ;

chansetDefs
    : 'chansets' chansetDef*
    // : 'chansets' ( chansetDef (';' chansetDef)+ )?
    ;

chansetDef
    : IDENTIFIER '=' expression
    // : IDENTIFIER '=' chansetExpr
    ;

namesetDefs
    : 'namesets' namesetDef*
    // : 'chansets' ( chansetDef (';' chansetDef)+ )?
    ;

namesetDef
    : IDENTIFIER '=' expression
    // : IDENTIFIER '=' namesetExpr
    ;

classDefinitionBlock
    : typeDefs
    | valueDefs
    | stateDefs
    | functionDefs
    | operationDefs
    | 'initial' operationDef // why should this operation require a name, or be allowed to be explicit?
    ;

valueDefs
    : 'values' ( QUALIFIER? valueDefinition (';' QUALIFIER? valueDefinition)* )? ';'?
    ;

stateDefs
    : 'state' ( instanceVariableDefinition (';' instanceVariableDefinition)* )? ';'?
    ;

instanceVariableDefinition
    : QUALIFIER? assignmentDefinition
    | invariantDefinition
    ;

assignmentDefinition
    : bindablePattern ':' type ( ( ':=' | 'in' ) expression )?
    ;

invariantDefinition
    : 'inv' expression
    ;

functionDefs
    : 'functions' (QUALIFIER? functionDefinition)*
    ;

valueDefinition
    : bindablePattern (':' type)? ( '=' | 'be' 'st' ) expression
    ;

functionDefinition
    : IDENTIFIER (explicitFunctionDefintionTail | implicitFunctionDefintionTail)
    ;

explicitFunctionDefintionTail
    : ':' type IDENTIFIER parameterGroup+ '==' functionBody ('pre' expression )? ('post' expression)? ('measure' name)?
    ;

implicitFunctionDefintionTail
    : '(' parameterTypeList ')' IDENTIFIER ':' type (',' IDENTIFIER ':' type)* ('pre' expression )? 'post' expression
    ;

parameterTypeList
    : parameterTypeGroup (',' parameterTypeGroup)*
    ;

parameterTypeGroup
    : (bindablePattern (',' bindablePattern)* )? ':' type
    ;

parameterGroup
    : '(' bindablePattern (',' bindablePattern)* ')'
    ;

functionBody
    : expression
    | 'is' 'not' 'yet' 'specified'
    | 'is' 'subclass' 'responsibility'
    ;

operationDefs
    : 'operations' (QUALIFIER? operationDef)*
    ;

operationDef
    : IDENTIFIER
        ( ':' opType IDENTIFIER parameterGroup '==' operationBody ('pre' expression)? ('post' expression)?
        | '(' parameterTypeList ')' IDENTIFIER ':' type (',' IDENTIFIER ':' type)* ('frame' frameSpec (',' frameSpec)* )? ('pre' expression)? ('post' expression)
        )
    ;

opType
    : ( type0 | '(' ')' ) '==>' ( type0 | '(' ')' )
    ;

operationBody
    : action
    | 'is' 'not' 'yet' 'specified'
    | 'is' 'subclass' 'responsibility'
    ;

typeDefs
    : 'types' typeDef*
    ;

typeDef
    : QUALIFIER? IDENTIFIER '=' type invariant?
    | QUALIFIER? IDENTIFIER '::' field+ invariant?
    ;

type
    : type0 (('+>'|'->') type0)?
    | '()' (('+>'|'->') type0)?
    ;

type0op : '*' | '|' ;
type0
    : type1 (type0op type1)*
    ;

type1
    : basicType
    | '(' type ')'
    | '[' type ']'
    | QUOTELITERAL
    | IDENTIFIER ('.' IDENTIFIER)*
    | 'compose' IDENTIFIER 'of' field+ 'end'
    | 'set' 'of' type1
    | 'seq' 'of' type1
    | 'seq1' 'of' type1
    | 'map' 'of' type1 'to' type1
    | 'inmap' 'of' type1 'to' type1
    ;

basicType
    : 'bool' | 'nat' | 'nat1' | 'int' | 'rat' | 'real' | 'char' | 'token'
    ;

field
    : type
    | IDENTIFIER ':' type
    | IDENTIFIER ':-' type
    ;

invariant
    : 'inv' bindablePattern '==' expression
    ;

pattern
    : bindablePattern
    | matchValue
    ;

bindablePattern
    : patternIdentifier
    | tuplePattern
    | recordPattern
    ;

patternIdentifier
    : IDENTIFIER
    | '-'
    ;

matchValue
    : symbolicLiteral
    | '(' expression ')'
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
    : MKUNDERNAMELPAREN (pattern (',' pattern)*)? ')'
    ;

expression
    : expr0
    | 'let' localDefinition (',' localDefinition)* 'in' expression
    | 'if' expression 'then' expression ('elseif' expression 'then' expression)* 'else' expression
    | 'cases' expression ':' (pattern (',' pattern)* '->' expression (',' pattern (',' pattern)* '->' expression)* )? (',' 'others' '->' expression)? 'end'
    | 'forall' bind (',' bind)* '@' expression
    | 'exists' bind (',' bind)* '@' expression
    | 'exists1' bind '@' expression
    | 'iota' bind '@' expression
    | 'lambda' typeBind (',' typeBind)* '@' expression
    ;

binExpr0op
    : '+' | '-' | '*' | '/' | 'div' | 'rem' | 'mod' | '<' | '<=' | '>' | '>='
    | '=' | '<>' | 'or' | 'and' | '=>' | '<=>' | 'in' 'set' | 'not' 'in' 'set'
    | 'subset' | 'psubset' | 'union' | '\\' | 'inter' | '^' | '++' | 'munion'
    | '<:' | '<-:' | ':->' | ':>' | 'comp' | '**'
    ;

expr0
    : expr1 (binExpr0op expression)?
    ;

unaryExpr1op
    : '+' | '-' | 'abs' | 'floor' | 'not' | 'card' | 'power' | 'dunion'
    | 'dinter' | 'hd' | 'tl' | 'len' | 'elems' | 'inds' | 'reverse'
    | 'conc' | 'dom' | 'rng' | 'merge' | 'inverse'
    ;

expr1
    : unaryExpr1op exprbase
    | setMapExprs
    | '[' seqExpr? ']'
    | recordTupleExprs
    | ISOFCLASSLPAREN IDENTIFIER ('.' IDENTIFIER)* ',' expression ')'
    | ISUNDERLPAREN expression ',' type ')'
    | ISUNDERBASICLPAREN expression ')'
    | ISUNDERNAMELPAREN expression ')'
    | PREUNDERLPAREN expression (',' expression)* ')'
    | expr2 TUPLESELECTOR?
    ;

setMapExprs
    : '{' ( '|->' | expression setMapExprTail? )? '}'
    ;

recordTupleExprs
    : MKUNDERLPAREN expression (',' expression)+ ')'
    | MKUNDERNAMELPAREN ( expression (',' expression)* )? ')'
    ;

expr2
// | subsequence
// | apply
    : exprbase ( '(' ( expression (',' '...' ',' expression | (',' expression)+ )? )? ')' )?
    ;

exprbase
    : '(' expression ')'
    | 'self'
// | name
// | old name
// | field select
    | name '~'?
    | symbolicLiteral
    ;

name
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

setMapExprTail
    : ',' '...' ',' expression
    | ( ',' expression )+
    | '|->' expression mapExprTail?
    | setMapExprBinding
    ;

mapExprTail
    : setMapExprBinding
    | ( ',' expression '|->' expression )+
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
    : (valueDefinition)=> valueDefinition // This.  THIS!  This is awesome.  Full of awesome.  Now I need to figure out Why it works. --jwc/26Nov2012
    | functionDefinition
    ;

bind: bindablePattern ('in' 'set' expression | ':' type)
    ;

setBind
    : bindablePattern 'in' 'set' expression
    ;

typeBind
    : bindablePattern ':' type
    ;

/* ********************************************************** */
/* ***               LEXER PRODUCTION RULES               *** */
/* ********************************************************** */


QUALIFIER
    : 'public' | 'protected' | 'private' | 'logical'
    ;

// in/out/bidi params
PMODE
    : 'val' | 'res' | 'vres'
    ;

FRAMEMODE
    : 'rd' | 'wr'
    ;


// NILLITERAL
//     : 'nil'
//     ;

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

/* Ok, this appears to solve the problem with "0< x" being ok, but
 * "0<x" failing to match a QUOTELITERAL token.  I don't know how much
 * effect it has on lexer performance, though.
 *
 * QUOTELITERAL doesn't do the right thing with "0<x" (but "0< x" is fine)
 * -> For now, I have a parser rule to match this, but I expect weirdnesses
 */
QUOTELITERAL
	: ('<' INITIAL_LETTER FOLLOW_LETTER* '>')=> '<' INITIAL_LETTER FOLLOW_LETTER* '>'
    | '<' { $type=LESSTHAN; }
    ;


CHARLITERAL
    : '\\\\' | '\\R' | '\\n' | '\\t' | '\\f' | '\\e' | '\\a' | '\\"' | '\\\''
    | '\\x' HEXDIGIT HEXDIGIT
    | '\\u' HEXDIGIT HEXDIGIT HEXDIGIT HEXDIGIT
    | '\\' OCTDIGIT OCTDIGIT OCTDIGIT
    // | '\\c' character
    ;

/* { $channel=HIDDEN; } is great for debugging, but swap the action to
 * { skip(); } for production as it will then not create Token objects
 * for whitespace.
 */
WHITESPACE
    : (' ' | '\t' | '\r' | '\n')+ { $channel=HIDDEN; }
    ;

LINECOMMENT
    : ( '//' | '--' ) .* '\n' { $channel=HIDDEN; }
    ;

MLINECOMMENT
    : '/*' .* '*/' { $channel=HIDDEN; }
    ;
