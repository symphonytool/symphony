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
 *   are essentially ID '('expr*')'.  I've added "lhs '<-' call" as a
 *   rule alternative.
 *
 * expression/statement precedence has still to be resolved
 *
 * make type not allow '()' unless coming from a function?
 */
grammar Cml;
options {
    language=Java;
//    output=AST;
    TokenLabelType=CommonToken;
//    ASTLabelType=CmlTree;
}
/* This is necessary for the lexer to disambiguate QUOTELITERALS and a
 * '<' followed by an IDENTIFIER that is not followed by a '>'.
 */
tokens {
    LESSTHAN = '<';
}

@lexer::header {
package eu.compassresearch.core.parser;
}
@parser::header {
package eu.compassresearch.core.parser;

// import org.apache.commons.lang3.StringUtils;

// import org.overture.ast.definitions.*;
import org.overture.ast.expressions.*;
import org.overture.ast.lex.*;
//import org.overture.ast.node.*;
import org.overture.ast.node.tokens.*;
import org.overture.ast.patterns.*;
import org.overture.ast.preview.*;
import org.overture.ast.statements.*;
import org.overture.ast.types.*;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.util.*;
import org.overture.ast.typechecker.Pass;

import eu.compassresearch.ast.actions.*;
import eu.compassresearch.ast.declarations.*;
import eu.compassresearch.ast.definitions.*;
import eu.compassresearch.ast.expressions.*;
import eu.compassresearch.ast.patterns.*;
import eu.compassresearch.ast.process.*;
import eu.compassresearch.ast.program.*;
import eu.compassresearch.ast.types.*;
}

@members {
// public String getErrorMessage(RecognitionException e, String[] tokenNames) {
//     List stack = getRuleInvocationStack(e, this.getClass().getName());
//     String msg = null;
//     if (e instanceof NoViableAltException) {
//         NoViableAltException nvae = (NoViableAltException)e;
//         msg = " no viable alt; token="+e.token+
//             " (decision="+nvae.decisionNumber+
//             " state "+nvae.stateNumber+")"+
//             " decision=<<"+nvae.grammarDecisionDescription+">>";
//     } else {
//         msg = super.getErrorMessage(e, tokenNames);
//     }
//     return stack+" "+msg;
// }
// public String getTokenErrorDisplay(Token t) {
//     return t.toString();
// }

protected void mismatch(IntStream input, int ttype, BitSet follow) throws RecognitionException {
    throw new MismatchedTokenException(ttype, input);
}
// public void recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
//     throw e;
// }

private LexLocation extractLexLocation(CommonToken token) {
    String text = token.getText();
    int len = text.length();
    int line = token.getLine();
    int pos = token.getCharPositionInLine();
    int offset = token.getStartIndex();
    return new LexLocation("",// FIXME: filename --- was currentSource.toString(),
                           "",// FIXME: (local?) module name
                           line, //start line
                           pos, //start column
                           line, //end line (FIXME?)
                           pos+len, //end column
                           offset, //absolute start offset
                           offset+len); //absolute end offset
}
public LexLocation extractLexLocation(LexLocation start, LexLocation end) {
    return new LexLocation(start.file, "",
                           start.startLine, start.startPos,
                           end.endLine, end.endPos,
                           start.startOffset, end.endOffset);
}
}

@rulecatch {
catch (RecognitionException e) {
    throw e;
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
    : 'process' IDENTIFIER '=' ((procDeclarations)=>procDeclarations)? process
    ;

process
    : proc0
    | replOp replicationDeclaration '@' ( '[' chansetNamesetExpr ']' )? process
    ;

proc0
    : proc1 (proc0Ops process)?
    ;

proc0Ops
    : '[]'
    | '|~|'
    | '||'
    | '|||'
    | '/\\'
    | '//' expression '\\\\' // not sure if the empty /\ and [> should be here
    | '[>'
    | '[[' expression '>>'
    | '[' chansetNamesetExpr '||' chansetNamesetExpr ']'
    | '[|' chansetNamesetExpr '|]'
    | ';'
    ;

proc1
    : proc2 ('[[' renamingExpr ']]')?
    ;

replOp
    : '[]'
    | '|~|'
    | '||'
    | '|||'
    | '[|' chansetNamesetExpr '|]'
    | ';'
    ;

proc2
    : proc3
        ( ('startsby' | 'endsby') expression
        | '\\\\' chansetNamesetExpr
        )?
    ;

proc3
    : 'begin' processParagraph* '@' action 'end'
    // merge of (process) | identifier [({expression})] | (decl@proc)({expression})
    | ( IDENTIFIER | '(' ((procDeclarations)=>procDeclarations)? process ')' ) ( '(' ( expression ( ',' expression )* )? ')'  )?
    ;

procDeclarations
    : declaration (';' declaration)* '@'
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

// just expression is too broad; need to restrict it a bit
renamePair
    : IDENTIFIER ( '.' (IDENTIFIER | '(' expression ')' | symbolicLiteral ) )*
        '<-' IDENTIFIER ( '.' (IDENTIFIER | '(' expression ')' | symbolicLiteral ) )*
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
    : IDENTIFIER '=' action
    // the "declaration '@'" option is taken care of *in* the action rule
    // : IDENTIFIER '=' (declaration '@')? action
    ;

action
    : action0
    | '[' expression ']' '&' action
    | 'mu' IDENTIFIER (',' IDENTIFIER)* '@'  '(' action (',' action)* ')'
    | ( actionSimpleReplOp ) replicationDeclaration '@' action
    | ( actionSetReplOp ) replicationDeclaration '@' '[' chansetNamesetExpr ( '|' chansetNamesetExpr )? ']' action
    ;

actionSimpleReplOp
    : ';' | '[]' | '|~|' | '[||' chansetNamesetExpr '||]'
    ;

actionSetReplOp
    : '||' | '|||' | '[|' chansetNamesetExpr '|]'
    ;

action0
    // : action1 (action0Ops action)?
    : (action1 action0Ops)=>action1 action0Ops action
    | (action1 '[[')=>action1 '[[' renamingExpr ']]'
    | action1
    ;

action0Ops
    : ';'
    | '[]'
    | '|~|'
    | '/\\'
    | '//' expression '\\\\' // not sure if the empty /\ and [> should be here
    | '||'
    | '|||'
    | '[>'
    | '[[' expression '>>'
    | '[' chansetNamesetExpr ( '|' chansetNamesetExpr )? '||' chansetNamesetExpr ( '|' chansetNamesetExpr )? ']'
    | '[|' chansetNamesetExpr ( '|' chansetNamesetExpr ( '|' chansetNamesetExpr )? )? '|]'
    | '[||' chansetNamesetExpr '|' chansetNamesetExpr '||]'
    ;

action1
    : action2
        ( ('startsby' | 'endsby')=> ('startsby' | 'endsby') expression
        | ('\\\\')=> '\\\\' chansetNamesetExpr
        )?
    ;

action2
    : 'Skip'
    | 'Stop'
    | 'Chaos'
    | 'Div'
    | 'Wait' expression
    | IDENTIFIER (communication* '->' action)?
    /* The mess below includes parenthesized actions, block
     * statements, parametrised actions, instantiated actions.
     */
    | '('
        ( (declaration)=> declaration (';' declaration)* '@'
        | 'dcl' assignmentDefinition (';' assignmentDefinition)* '@'
        )?
        action ')'
        ( '(' expression (',' expression )* ')' )?
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
    | '[:' ('frame' frameSpec (',' frameSpec)* )? ('pre' expression)? 'post' expression ':]' // DEVIATION
    | 'do' nonDetStmtAlt ( '[]' nonDetStmtAlt )* 'end'
    | 'if' expression
        ( '->' action ( '[]' nonDetStmtAlt )* 'end'
        | 'then' action ( 'elseif' expression 'then' action )* 'else' action
        )
    | 'cases' expression ':' (pattern (',' pattern)* '->' action (',' pattern (',' pattern)* '->' action)* )? (',' 'others' '->' expression)? 'end'
    | 'for'
        ( bindablePattern (':' type)? 'in' expression // was pattern bind here only
        | 'all' bindablePattern 'in' 'set' expression
        | IDENTIFIER '=' expression 'to' expression ( 'by' expression )?
        ) 'do' action
    | ('return' expression)=>'return' expression
    | 'return'
    | 'while' expression 'do' action
    | 'atomic' '(' stateDesignator ':=' expression ( ';' stateDesignator ':=' expression )+ ')'
    | (callStatement)=> callStatement // More syntactic predicate magic :)
    | stateDesignator
        ( ':=' ( expression | 'new' name '(' ( expression ( ',' expression )* )? ')' )//| callStatement )
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
    : IDENTIFIER '=' chansetNamesetExpr
    ;

chansetNamesetExpr
    : chansetNamesetExprbase (cneOp chansetNamesetExpr)?
    ;

cneOp
    : 'union'
    | 'inter'
    | '\\'
    ;

chansetNamesetExprbase
    : name
    | '{' ( IDENTIFIER (',' IDENTIFIER)* )? '}'
    | '{|'
        ( IDENTIFIER
            ( (',' IDENTIFIER)+
            | ('.' expression)? ( setMapExprBinding )
            )?
        )?
        '|}'
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
    : '(' parameterTypeList? ')' IDENTIFIER ':' type (',' IDENTIFIER ':' type)* ('pre' expression )? 'post' expression
    ;

parameterTypeList
    : parameterTypeGroup (',' parameterTypeGroup)*
    ;

parameterTypeGroup
    : (bindablePattern (',' bindablePattern)* )? ':' type
    ;

parameterGroup
    : '(' (bindablePattern (',' bindablePattern)* )? ')'
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
        | '(' parameterTypeList? ')' (IDENTIFIER ':' type (',' IDENTIFIER ':' type)* )? ('frame' frameSpec+ )? ('pre' expression)? ('post' expression)
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
    | '(' ')' (('+>'|'->') type0)?
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
    | IDENTIFIER (('.'|'`') IDENTIFIER)*
    | 'compose' IDENTIFIER 'of' field+ 'end'
    | 'set' 'of' type1
    | 'seq' 'of' type1
    | 'seq1' 'of' type1
    | 'map' type1 'to' type1
    | 'inmap' type1 'to' type1
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
    : NUMERIC
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
    : unaryExpr1op expr1
    | ISOFCLASSLPAREN IDENTIFIER (('.'|'`') IDENTIFIER)* ',' expression ')'
    | ISUNDERLPAREN expression ',' type ')'
    | ISUNDERBASICLPAREN expression ')'
    | ISUNDERNAMELPAREN expression ')'
    | PREUNDERLPAREN expression (',' expression)* ')'
    | exprbase selector*
    ;

selector
    : '(' ( expression (',' '...' ',' expression | (',' expression)+ )? )? ')' // function application, sequence select and subsequence
    | TUPLESELECTOR // tuple select
    | '.' IDENTIFIER // field select
    ;

exprbase returns[PExp exp]
    : '(' expression ')'
    | self='self'
        {
            LexLocation loc = extractLexLocation($self);
            LexNameToken name = new LexNameToken("", $self.getText(), loc, true, false);
            $exp = new ASelfExp(loc, name);
        }
    | IDENTIFIER old='~'?
        {
            boolean isOld = (old != null);
            LexLocation loc = extractLexLocation($IDENTIFIER);
            if (isOld)
                loc = extractLexLocation(loc, extractLexLocation($old));
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), loc, isOld, false);
            $exp = new AVariableExp(loc, name, "");
        }
//    | name
//        {
//            System.out.println("name as module:"+$name.name.module+":");
//            if ($name.name.module.equals("")) {
//                $exp = new AVariableExp($name.name.location, $name.name, "");
//            } else {
//                // FIXME: this needs to be an AUnresolvedPathExpr, but I've already stringified the list of ids
//                $exp = null;
//            }
//            System.out.println("I have a name: " + $name.name + " / " + ($exp==null?"null":$exp.getClass()));
//        }
    | symbolicLiteral
    | setMapExprs
    | '[]'
    | '[' seqExpr? ']'
    | recordTupleExprs
    ;

name returns[LexNameToken name]
    : (ids+=IDENTIFIER ('.'|'`'))* identifier=IDENTIFIER
        {
            // FIXME: not setting the filename field
            // Grab the location of the last identifier as default
            LexLocation loc = extractLexLocation($identifier);
            // default to a blank module
            StringBuilder module = new StringBuilder();
            if ($ids != null) {
                // fix the name location
                LexLocation firstLoc = extractLexLocation((CommonToken)$ids.get(0));
                loc = new LexLocation(loc.file,
                                      "", //FIXME: I assume this is the local module name?
                                      firstLoc.startLine, firstLoc.startPos,
                                      loc.endLine, loc.endPos,
                                      firstLoc.startOffset, loc.endOffset);
                // create the module string
                for (Object t : $ids) {
                    module.append(((CommonToken)t).getText());
                    module.append('.');
                }
                module.deleteCharAt(module.length() - 1);
            }
            $name=new LexNameToken(module.toString(), $identifier.getText(), loc);
        }
    ;

setMapExprs
    : '{' ( '|->' | expression setMapExprTail? )? '}'
    ;

recordTupleExprs
    : MKUNDERLPAREN expression (',' expression)+ ')'
    | MKUNDERNAMELPAREN ( expression (',' expression)* )? ')'
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
    : '|' (bind (',' bind)* )? ('@' expression)?
    ;

/* sequence enumeration = '[', [ expression list ], ']' ;
 * sequence comprehension = '[', expression, '|', set bind, [ '@', expression ], ']' ;
 */
seqExpr
    : expression ( (',' expression)* | '|' setBind ('@' expression)? | ',' '...' ','  expression )
    ;

localDefinition
    : (valueDefinition)=> valueDefinition
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
    : 'mk_' IDENTIFIER (('.'|'`') IDENTIFIER)* '('
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
    : 'is_' IDENTIFIER (('.'|'`') IDENTIFIER)* '('
    ;

/* Need to fix this, yet
 */
TEXTLITERAL
    : '"' .* '"'
    ;

/* FIXME This only tracks the non-unicode chunk of the characters; this
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
    : INITIAL_LETTER | DIGIT | '\u005f'
    // : { input.LT(1) <  0x0100 }? =>
    //     ( UNICODE_Lo | UNICODE_Ll | UNICODE_Lm |  UNICODE_Lt | UNICODE_Lu
    //     | '\u0024' )
    // | { input.LT(1) >= 0x0100 }?
    //     ( UNICODE_Ps | UNICODE_Nl | UNICODE_No | UNICODE_Lo | UNICODE_Ll
    //     | UNICODE_Lm | UNICODE_Nd | UNICODE_Pc | UNICODE_Lt | UNICODE_Lu
    //     | UNICODE_Pf | UNICODE_Pd | UNICODE_Pe | UNICODE_Pi | UNICODE_Po
    //     | UNICODE_Me | UNICODE_Mc | UNICODE_Mn | UNICODE_Sk | UNICODE_So
    //     | UNICODE_Sm | UNICODE_Sc | UNICODE_Cf
    //     )
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

NUMERIC
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


