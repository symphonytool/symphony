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
 * statement precedence has still to be resolved
 *
 * make type not allow '()' unless coming from a function?
 *
 * I'm not sure if multiple binds are implemented. -jwc/14Dec2012
 *
 * Look into start/final rules to see if we can ease up the location bookkeeping
 *
 */
grammar Cml;
options {
    language=Java;
    TokenLabelType=CommonToken;
    // We're not really using ANTLR's AST building functionality,
    // except that I (jwc) want to be able to use list += operators.
    // So, we turn this on.  Maybe in the future we'll use the AST
    // builder properly.
    // Nope, that doesn't do it either
    // --> Sadly, the += syntax only works on token because we're not using the AST construction config
    // output=AST;
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

import java.lang.NumberFormatException;
import java.text.DecimalFormat;
import java.text.ParseException;
// import org.apache.commons.lang3.StringUtils;

import static org.overture.ast.lex.Dialect.VDM_PP;
import org.overture.ast.definitions.*;
import org.overture.ast.expressions.*;
import org.overture.ast.lex.*;
import org.overture.ast.node.*;
// import org.overture.ast.node.tokens.*;
import org.overture.ast.patterns.*;
// import org.overture.ast.preview.*;
// import org.overture.ast.statements.*;
import org.overture.ast.types.*;
// import org.overture.ast.typechecker.NameScope;
// import org.overture.ast.util.*;
// import org.overture.ast.typechecker.Pass;

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

private DecimalFormat decimalFormatParser = new DecimalFormat();

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
private LexLocation extractLexLocation(CommonToken start, CommonToken end) {
    int sline = start.getLine();
    int eline = end.getLine();
    int spos = start.getCharPositionInLine();
    int epos = end.getCharPositionInLine() + end.getText().length();
    int soffset = start.getStartIndex();
    int eoffset = end.getStopIndex();
    return new LexLocation("",// FIXME: filename --- was currentSource.toString(),
                           "",// FIXME: (local?) module name
                           sline, spos,
                           eline, epos,
                           soffset, eoffset);
}
public LexLocation extractLexLocation(PExp start, PExp end) {
    return extractLexLocation(start.getLocation(),end.getLocation());
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
    | '?' bindablePattern ( 'in' 'set' setMapExpr )?
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

type returns[PType type]
    : dom=type0 (('+>'|'->') rng=type0)?
        {
            $type = $dom.type;
        }
    | unit='()' (('+>'|'->') rng=type0)?
    ;

type0op
    : '*'
    | '|'
    ;

type0 returns[PType type]
    : l=type1 (type0op r=type1)*
        {
            $type = $l.type;
        }
    ;

type1 returns[PType type]
    : basicType
        {
            $type = $basicType.basicType;
        }
    | l='(' inside=type r=')'
        {
            $type = $inside.type;
            $type.setLocation(extractLexLocation($l,$r));
        }
    | l='[' inside=type r=']'
        {
            $type = new AOptionalType(extractLexLocation($l,$r), false, null, $inside.type);
        }
    | QUOTELITERAL
        {
            LexLocation loc = extractLexLocation($QUOTELITERAL);
            String str = $QUOTELITERAL.getText();
            str = str.substring(1,str.length()-1);
            $type = new AQuoteType(loc, false, null, new LexQuoteToken(str, loc));
        }
    | name //IDENTIFIER (('.'|'`') IDENTIFIER)*
        {
            LexNameToken tname = $name.name;
            LexLocation loc = tname.getLocation();
            $type = new ANamedInvariantType(loc, false, null, false, null, tname,
                                            new AUnresolvedType(loc, false, new ArrayList<PDefinition>(), tname));
        }
    | set='set' 'of' type1
    | seq='seq' 'of' type1
    | seq1='seq1' 'of' type1
    | map='map' type1 'to' type1
    | inmap='inmap' type1 'to' type1
    | compose='compose' IDENTIFIER 'of' field+ end='end'
    ;

basicType returns[PType basicType]
    : t='bool'  { $basicType = new ABooleanBasicType(extractLexLocation($t), false); }
    | t='nat'   { $basicType = new ANatNumericBasicType(extractLexLocation($t), false); }
    | t='nat1'  { $basicType = new ANatOneNumericBasicType(extractLexLocation($t), false); }
    | t='int'   { $basicType = new AIntNumericBasicType(extractLexLocation($t), false); }
    | t='rat'   { $basicType = new ARationalNumericBasicType(extractLexLocation($t), false); }
    | t='real'  { $basicType = new ARealNumericBasicType(extractLexLocation($t), false); }
    | t='char'  { $basicType = new ACharBasicType(extractLexLocation($t), false); }
    | t='token' { $basicType = new ATokenBasicType(extractLexLocation($t), false); }
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

symbolicLiteral returns[LexToken literal]
    : NUMERIC
        {
            LexLocation loc = extractLexLocation($NUMERIC);
            try {
                Number num = decimalFormatParser.parse($NUMERIC.getText());
                if (num instanceof Long) {
                    $literal = new LexIntegerToken((Long)num, loc);
                } else if (num instanceof Double) {
                    $literal = new LexRealToken((Double)num, loc);
                }
            } catch (ParseException e) {
                // FIXME log an error here!
            }
        }
    | HEXLITERAL
        {
            LexLocation loc = extractLexLocation($HEXLITERAL);
            try {
                long num = Long.parseLong($HEXLITERAL.getText().substring(2), 16);
                $literal = new LexIntegerToken(num, loc);
            } catch (NumberFormatException e) {
                // FIXME log an error here!
            }
        }
    | t='true'
        {
            $literal = new LexBooleanToken(VDMToken.TRUE, extractLexLocation($t));
        }
    | f='false'
        {
            $literal = new LexBooleanToken(VDMToken.FALSE, extractLexLocation($f));
        }
    | nil='nil'
        {
            $literal = new LexKeywordToken(VDMToken.NIL, extractLexLocation($nil));
        }
    | CHARLITERAL
        {
            LexLocation loc = extractLexLocation($CHARLITERAL);
            String res = $CHARLITERAL.getText();
            res = res.replace("'", "");
            $literal = new LexCharacterToken(CmlParserHelper.convertEscapeToChar(res), loc);
        }
    | TEXTLITERAL
        {
            LexLocation loc = extractLexLocation($TEXTLITERAL);
            String str = $TEXTLITERAL.getText();
            str = str.substring(1,str.length()-1);
            $literal = new LexStringToken(str, loc);
        }
    | QUOTELITERAL
        {
            LexLocation loc = extractLexLocation($QUOTELITERAL);
            String str = $QUOTELITERAL.getText();
            str = str.substring(1,str.length()-1);
            $literal = new LexQuoteToken(str, loc);
        }
    ;

tuplePattern
    : MKUNDER '(' pattern (',' pattern)* ')'
    ;

recordPattern
    : MKUNDER name '(' (pattern (',' pattern)*)? ')'
    ;

expression returns[PExp exp]
    : expr0
        {
            $exp = $expr0.exp;
        }
    | 'let' localDefinition (',' localDefinition)* 'in' expression
    | 'if' expression 'then' expression ('elseif' expression 'then' expression)* 'else' expression
    | 'cases' expression ':' (pattern (',' pattern)* '->' expression (',' pattern (',' pattern)* '->' expression)* )? (',' 'others' '->' expression)? 'end'
    | 'forall' bind (',' bind)* '@' expression
    | 'exists' bind (',' bind)* '@' expression
    | 'exists1' bind '@' expression
    | 'iota' bind '@' expression
    | 'lambda' typeBind (',' typeBind)* '@' expression
    ;

expr0 returns[PExp exp]
    : e1=expr1 (o='<=>' e2=expr0)?
        {
            if (e2 == null)
                $exp = $e1.exp;
            else
                $exp = new AEquivalentBooleanBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                                       $e1.exp,
                                                       new LexToken(extractLexLocation($o), VDMToken.lookup($o.getText(), VDM_PP)),
                                                       $e2.exp);
        }
    ;

expr1 returns[PExp exp]
    : e1=expr2 (o='=>' e2=expr1)?
        {
            if (e2 == null)
                $exp = $e1.exp;
            else
                $exp = new AImpliesBooleanBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                             $e1.exp,
                                             new LexToken(extractLexLocation($o), VDMToken.lookup($o.getText(), VDM_PP)),
                                             $e2.exp);
        }
    ;

expr2 returns[PExp exp]
    : e1=expr3 (o='or' e2=expr2)?
        {
            if (e2 == null)
                $exp = $e1.exp;
            else
                $exp = new AOrBooleanBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                               $e1.exp,
                                               new LexToken(extractLexLocation($o), VDMToken.lookup($o.getText(), VDM_PP)),
                                               $e2.exp);
        }
    ;

expr3 returns[PExp exp]
    : e1=expr4 (o='and' e2=expr3)?
        {
            if (e2 == null)
                $exp = $e1.exp;
            else
                $exp = new AAndBooleanBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                                $e1.exp,
                                                new LexToken(extractLexLocation($o), VDMToken.lookup($o.getText(), VDM_PP)),
                                                $e2.exp);
        }
    ;

binOpRel returns[SBinaryExpBase op]
@init { LexLocation loc = null; String opStr = null; }
@after { op.setLocation(loc); op.setOp(new LexToken(loc, VDMToken.lookup(opStr, VDM_PP))); }
    : o='<'                { $op = new ALessNumericBinaryExp();         loc = extractLexLocation($o);    opStr = $o.getText(); }
    | o='<='               { $op = new ALessEqualNumericBinaryExp();    loc = extractLexLocation($o);    opStr = $o.getText(); }
    | o='>'                { $op = new AGreaterNumericBinaryExp();      loc = extractLexLocation($o);    opStr = $o.getText(); }
    | o='>='               { $op = new AGreaterEqualNumericBinaryExp(); loc = extractLexLocation($o);    opStr = $o.getText(); }
    | o='='                { $op = new AEqualsBinaryExp();              loc = extractLexLocation($o);    opStr = $o.getText(); }
    | o='<>'               { $op = new ANotEqualBinaryExp();            loc = extractLexLocation($o);    opStr = $o.getText(); }
    | o='in' l='set'       { $op = new AInSetBinaryExp();               loc = extractLexLocation($o,$l); opStr = "in set"; }
    | o='not' 'in' l='set' { $op = new ANotInSetBinaryExp();            loc = extractLexLocation($o,$l); opStr = "not in set"; }
    | o='subset'           { $op = new ASubsetBinaryExp();              loc = extractLexLocation($o);    opStr = $o.getText(); }
    | o='psubset'          { $op = new AProperSubsetBinaryExp();        loc = extractLexLocation($o);    opStr = $o.getText(); }
    ;

expr4 returns[PExp exp]
    : e1=expr5 (binOpRel e2=expr4)?
        {
            if (e2 == null) {
                $exp = $e1.exp;
            } else {
                LexLocation loc = extractLexLocation($e1.exp,$e2.exp);
                SBinaryExpBase op = $binOpRel.op;
                op.setLocation(loc);
                op.setLeft($e1.exp);
                op.setRight($e2.exp);
                $exp = op;
            }
        }
    ;

binOpEval1 returns[SBinaryExpBase op]
@init { LexLocation loc = null; String opStr = null; }
@after { op.setLocation(loc); op.setOp(new LexToken(loc, VDMToken.lookup(opStr, VDM_PP))); }
    : o='+'      { $op = new APlusNumericBinaryExp();      loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='-'      { $op = new ASubstractNumericBinaryExp(); loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='union'  { $op = new ASetUnionBinaryExp();         loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='\\'     { $op = new ASetDifferenceBinaryExp();    loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='munion' { $op = new AMapUnionBinaryExp();         loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='++'     { $op = new APlusPlusBinaryExp();         loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='^'      { $op = new ASeqConcatBinaryExp();        loc = extractLexLocation($o); opStr = $o.getText(); }
    ;

expr5 returns[PExp exp]
    : e1=expr6 (binOpEval1 e2=expr5)?
        {
            if (e2 == null) {
                $exp = $e1.exp;
            } else {
                LexLocation loc = extractLexLocation($e1.exp,$e2.exp);
                SBinaryExpBase op = $binOpEval1.op;
                op.setLocation(loc);
                op.setLeft($e1.exp);
                op.setRight($e2.exp);
                $exp = op;
            }
        }
    ;

// FIXME --- doublecheck that / and div use the correct objects
binOpEval2 returns[SBinaryExpBase op]
@init { LexLocation loc = null; String opStr = null; }
@after { op.setLocation(loc); op.setOp(new LexToken(loc, VDMToken.lookup(opStr, VDM_PP))); }
    : o='*'     { $op = new ATimesNumericBinaryExp();  loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='/'     { $op = new ADivNumericBinaryExp();    loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='rem'   { $op = new ARemNumericBinaryExp();    loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='mod'   { $op = new AModNumericBinaryExp();    loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='div'   { $op = new ADivideNumericBinaryExp(); loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='inter' { $op = new ASetIntersectBinaryExp();  loc = extractLexLocation($o); opStr = $o.getText(); }
    ;

expr6 returns[PExp exp]
    : e1=expr7 (binOpEval2 e2=expr6)?
        {
            if (e2 == null) {
                $exp = $e1.exp;
            } else {
                LexLocation loc = extractLexLocation($e1.exp,$e2.exp);
                SBinaryExpBase op = $binOpEval2.op;
                op.setLocation(loc);
                op.setLeft($e1.exp);
                op.setRight($e2.exp);
                $exp = op;
            }
        }
    ;

binOpEval3 returns[SBinaryExpBase op]
@init { LexLocation loc = null; String opStr = null; }
@after { op.setLocation(loc); op.setOp(new LexToken(loc, VDMToken.lookup(opStr, VDM_PP))); }
    : o='<:'  { $op = new ADomainResToBinaryExp(); loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='<-:' { $op = new ADomainResByBinaryExp(); loc = extractLexLocation($o); opStr = $o.getText(); }
    ;

expr7 returns[PExp exp]
    : e1=expr8 (binOpEval3 e2=expr7)?
        {
            if (e2 == null) {
                $exp = $e1.exp;
            } else {
                LexLocation loc = extractLexLocation($e1.exp,$e2.exp);
                SBinaryExpBase op = $binOpEval3.op;
                op.setLocation(loc);
                op.setLeft($e1.exp);
                op.setRight($e2.exp);
                $exp = op;
            }
        }
    ;

binOpEval4 returns[SBinaryExpBase op]
@init { LexLocation loc = null; String opStr = null; }
@after { op.setLocation(loc); op.setOp(new LexToken(loc, VDMToken.lookup(opStr, VDM_PP))); }
    : o=':->' { $op = new ARangeResByBinaryExp(); loc = extractLexLocation($o); opStr = $o.getText(); }
    | o=':>'  { $op = new ARangeResToBinaryExp(); loc = extractLexLocation($o); opStr = $o.getText(); }
    ;

expr8 returns[PExp exp]
    : e1=expr9 (binOpEval4 e2=expr8)?
        {
            if (e2 == null) {
                $exp = $e1.exp;
            } else {
                LexLocation loc = extractLexLocation($e1.exp,$e2.exp);
                SBinaryExpBase op = $binOpEval4.op;
                op.setLocation(loc);
                op.setLeft($e1.exp);
                op.setRight($e2.exp);
                $exp = op;
            }
        }
    ;

expr9 returns[PExp exp]
    : e1=expr10 (o='comp' e2=expr9)?
        {
            if (e2 == null)
                $exp = $e1.exp;
            else
                $exp = new ACompBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                          $e1.exp,
                                          new LexToken(extractLexLocation($o), VDMToken.lookup($o.getText(), VDM_PP)),
                                          $e2.exp);
        }
    ;

expr10 returns[PExp exp]
    : e1=expr11 (o='**' e2=expr10)?
        {
            if (e2 == null)
                $exp = $e1.exp;
            else
                $exp = new AStarStarBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                              $e1.exp,
                                              new LexToken(extractLexLocation($o), VDMToken.lookup($o.getText(), VDM_PP)),
                                              $e2.exp);
        }
    ;

unaryOp returns[SUnaryExp op]
    : o='+'       { $op = new AUnaryPlusUnaryExp();     $op.setLocation(extractLexLocation($o)); }
    | o='-'       { $op = new AUnaryMinusUnaryExp();    $op.setLocation(extractLexLocation($o)); }
    | o='abs'     { $op = new AAbsoluteUnaryExp();      $op.setLocation(extractLexLocation($o)); }
    | o='floor'   { $op = new AFloorUnaryExp();         $op.setLocation(extractLexLocation($o)); }
    | o='not'     { $op = new ANotUnaryExp();           $op.setLocation(extractLexLocation($o)); }
    | o='card'    { $op = new ACardinalityUnaryExp();   $op.setLocation(extractLexLocation($o)); }
    | o='power'   { $op = new APowerSetUnaryExp();      $op.setLocation(extractLexLocation($o)); }
    | o='dunion'  { $op = new ADistUnionUnaryExp();     $op.setLocation(extractLexLocation($o)); }
    | o='dinter'  { $op = new ADistIntersectUnaryExp(); $op.setLocation(extractLexLocation($o)); }
    | o='hd'      { $op = new AHeadUnaryExp();          $op.setLocation(extractLexLocation($o)); }
    | o='tl'      { $op = new ATailUnaryExp();          $op.setLocation(extractLexLocation($o)); }
    | o='len'     { $op = new ALenUnaryExp();           $op.setLocation(extractLexLocation($o)); }
    | o='elems'   { $op = new AElementsUnaryExp();      $op.setLocation(extractLexLocation($o)); }
    | o='inds'    { $op = new AIndicesUnaryExp();       $op.setLocation(extractLexLocation($o)); }
    | o='reverse' { $op = new AReverseUnaryExp();       $op.setLocation(extractLexLocation($o)); }
    | o='conc'    { $op = new ADistConcatUnaryExp();    $op.setLocation(extractLexLocation($o)); }
    | o='dom'     { $op = new AMapDomainUnaryExp();     $op.setLocation(extractLexLocation($o)); }
    | o='rng'     { $op = new AMapRangeUnaryExp();      $op.setLocation(extractLexLocation($o)); }
    | o='merge'   { $op = new ADistMergeUnaryExp();     $op.setLocation(extractLexLocation($o)); }
    | o='inverse' { $op = new AMapInverseUnaryExp();    $op.setLocation(extractLexLocation($o)); }
    ;

expr11 returns[PExp exp]
    : unaryOp operand=expr11
        {
            SUnaryExp unaryop = $unaryOp.op;
            PExp target = $operand.exp;
            unaryop.setExp(target);
            unaryop.setLocation(extractLexLocation(unaryop.getLocation(), target.getLocation()));
            $exp = unaryop;
        }
    | exprbase selector*
        {
            // FIXME --- do something with the selector(s)
            $exp = $exprbase.exp;
        }
    ;

selector
    : '(' ( expression (',' '...' ',' expression | (',' expression)+ )? )? ')' // function application, sequence select and subsequence
    | TUPLESELECTOR // tuple select
    | '.' IDENTIFIER // field select, usually: it can only be a name *if* the thing immediately left of the dot is an identifier (but not guaranteed)
    ;

exprbase returns[PExp exp]
@init { List<PExp> exps = new ArrayList<PExp>(); }
    : l='(' expression r=')'
        {
            LexLocation loc = extractLexLocation($l,$r);
            $exp = new ABracketedExp(loc, $expression.exp);
        }
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
    | lit=symbolicLiteral
        {
            if ($lit.literal instanceof LexIntegerToken) {
                $exp = new AIntLiteralExp($lit.literal.location, (LexIntegerToken)$lit.literal);
            } else if ($lit.literal instanceof LexRealToken) {
                $exp = new ARealLiteralExp($lit.literal.location, (LexRealToken)$lit.literal);
            } else if ($lit.literal instanceof LexBooleanToken) {
                $exp = new ABooleanConstExp($lit.literal.location, (LexBooleanToken)$lit.literal);
            } else if ($lit.literal instanceof LexKeywordToken) {
                // Note, this assumes that lit only ever
                // gives a LexKeywordToken for 'nil'
                $exp = new ANilExp($lit.literal.location);
            } else if ($lit.literal instanceof LexCharacterToken) {
            } else if ($lit.literal instanceof LexStringToken) {
                ASeqSeqType charSeqType = new ASeqSeqType($lit.literal.location,
                                                          true, null,
                                                          new ACharBasicType(),
                                                          (((LexStringToken)$lit.literal).value.length() == 0));
                $exp = new AStringLiteralExp(charSeqType, $lit.literal.location, (LexStringToken)$lit.literal);
            } else if ($lit.literal instanceof LexQuoteToken) {
            } else {
                // FIXME log a never-happens error
            }
        }
    | eseq='[]'
        {
            LexLocation loc = extractLexLocation($eseq);
            $exp = new ASeqEnumSeqExp(loc, new ArrayList<PExp>());
        }
    | l='[' seqExpr? r=']'
        {
            LexLocation loc = extractLexLocation($l,$r);
            if ($seqExpr.seqExpr == null) {
                $exp = new ASeqEnumSeqExp(loc, new ArrayList<PExp>());
            } else {
                $seqExpr.seqExpr.setLocation(loc);
                $exp = $seqExpr.seqExpr;
            }
        }
    | recordTupleExprs
        {
            $exp = $recordTupleExprs.exp;
        }
    | setMapExpr
        {
            $exp = $setMapExpr.exp;
        }
    | ISOFCLASS '(' name ',' expression r=')'
        {
            LexLocation loc = extractLexLocation($ISOFCLASS,$r);
            $exp = new AIsOfClassExp(loc, $name.name, $expression.exp);
        }
    | ISUNDER '(' expression ',' type r=')'
        {
            LexLocation loc = extractLexLocation($ISUNDER,$r);
            $exp = new AIsExp(null, loc, null, $type.type, $expression.exp, null);
        }
    | ISUNDER basicType '(' expression r=')'
        {
            LexLocation loc = extractLexLocation($ISUNDER,$r);
            $exp = new AIsExp(null, loc, null, $basicType.basicType, $expression.exp, null);
        }
    | ISUNDER name '(' expression r=')'
        {
            LexLocation loc = extractLexLocation($ISUNDER,$r);
            $exp = new AIsExp(null, loc, $name.name, null, $expression.exp, null);
        }
    | PREUNDER '(' func=expression ( ',' expr=expression { exps.add($expr.exp); } )* ')'
        {
            LexLocation loc = extractLexLocation($PREUNDER,$r);
            $exp = new APreExp(loc, $func.exp, exps);
        }
    ;

seqExpr returns[SSeqExp seqExpr]
@init { List<PExp> exps = new ArrayList<PExp>(); }
    : exp=expression
        ( '|' binding=setBind ('@' pred=expression)?
        | (',' enumItem=expression { exps.add($enumItem.exp); } )*
            // Apparently, [1,...,5] is neither valid VDM nor CML
            // | ',' '...' ','  end=expression
        )
        {
            // This location doesn't matter --- it *will* be replaced
            // by the caller of seqExpr.
            LexLocation loc = new LexLocation();
            if ($setBind.bind != null) {
                $seqExpr = new ASeqCompSeqExp(loc, $exp.exp, $setBind.bind, $pred.exp);
            } else {
                exps.add(0, exp);
                $seqExpr = new ASeqEnumSeqExp(loc, exps);
            }
        }
    ;

recordTupleExprs returns[PExp exp]
@init { List<PExp> exps = new ArrayList<PExp>(); }
    : l=MKUNDER '(' first=expression ( ',' expItem=expression { exps.add($expItem.exp); } )+ r=')'
        {
            LexLocation loc = extractLexLocation($l,$r);
            exps.add(0,$first.exp);
            $exp = new ATupleExp(loc,exps);
        }
    | MKUNDER 'token' '(' expression r=')'
        {
            LexLocation loc = extractLexLocation($MKUNDER,$r);
            $exp = new AMkBasicExp(new ATokenBasicType(loc, true), loc, $expression.exp);
        }
    | MKUNDER name '(' ( first=expression ( ',' expItem=expression { exps.add($expItem.exp); } )* )? r=')'
        {
            LexLocation loc = extractLexLocation($MKUNDER,$r);
            exps.add(0,$first.exp);
            $exp = new AMkTypeExp(loc, $name.name, exps);
        }
    ;

setMapExpr returns[PExp exp]
    : l='{' ( empty='|->' | setMapExprGuts )? r='}'
        {
            LexLocation loc = extractLexLocation($l,$r);
            if ($setMapExprGuts.exp != null) {
                $setMapExprGuts.exp.setLocation(loc);
                $exp = $setMapExprGuts.exp;
            } else if ($empty != null) {
                $exp = new AMapEnumMapExp(loc, new ArrayList<AMapletExp>());
            } else {
                $exp = new ASetEnumSetExp(loc, new ArrayList<PExp>());
            }
        }
    ;

setMapExprGuts returns[PExp exp]
@init {
    List<PExp> exps = new ArrayList<PExp>();
    List<AMapletExp> mexps = new ArrayList<AMapletExp>();
    // This location doesn't matter --- it *will* be replaced
    // by the caller of seqExpr.
    LexLocation loc = new LexLocation();
}
    : first=expression
        ( ',' '...' ',' last=expression
            {
                $exp = new ASetRangeSetExp(loc, $first.exp, $last.exp);
            }
        | ( ',' setItem=expression { exps.add($setItem.exp); } )+ // taken care of below
        | '|->' firstto=expression
            ( mbinding=setMapExprBinding
            | ( ',' fexp=expression '|->' texp=expression
                    {
                        LexLocation mloc = extractLexLocation($fexp.exp.getLocation(), $texp.exp.getLocation());
                        mexps.add(new AMapletExp(mloc,$fexp.exp,$texp.exp));
                    }
                )+
            )?
            {
                LexLocation mloc = extractLexLocation($first.exp.getLocation(), $firstto.exp.getLocation());
                AMapletExp firstMaplet = new AMapletExp(mloc, $first.exp, $firstto.exp);
                if (mbinding != null) {
                    $exp = new AMapCompMapExp(loc, firstMaplet, $mbinding.bindings, $mbinding.pred);
                } else {
                    mexps.add(0, firstMaplet);
                    $exp = new AMapEnumMapExp(loc, mexps);
                }
            }
        | binding=setMapExprBinding
            {
                  $exp = new ASetCompSetExp(loc, $first.exp, $binding.bindings, $binding.pred);
            }
        )?
        {
            if ($exp == null) {
                exps.add(0, $first.exp);
                $exp = new ASetEnumSetExp(loc, exps);
            } else {
                // FIXME Log a never-happens
            }
        }
    ;

setMapExprBinding returns[List<PMultipleBind> bindings, PExp pred, LexLocation loc]
@init { List<PMultipleBind> bindList = new ArrayList<PMultipleBind>(); }
    : '|' first=bind
        ( ',' bindItem=bind { bindList.add($bindItem.binding); } )*
        ('@' expression)?
        {
            bindList.add(0, $first.binding);
            $bindings = bindList;
            $pred = $expression.exp;
        }
    ;

localDefinition
    : (valueDefinition)=> valueDefinition
    | functionDefinition
    ;

bind returns[PMultipleBind binding]
    : bindablePattern ('in' 'set' expression | ':' type)
        {
            // FIXME -- this is a placeholder to prevent NPEs
            // Note, also, the use of this in setMapExprBinding may not be quite right; I think it actually wants a multibind
            System.out.println("++ Implement the bind rule!");
            $binding = new ASetMultipleBind();
        }
    ;

setBind returns[ASetBind bind]
    : bindablePattern 'in' 'set' expression
    ;

typeBind
    : bindablePattern ':' type
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

PREUNDER
    : 'pre_'
    ;

MKUNDER
    : 'mk_'
    ;

ISOFCLASS
    : 'isofclass'
    ;

ISUNDER
    : 'is_'
    ;

ISUNDERBASIC
    : 'is_' ('bool' | 'nat' | 'nat1' | 'int' | 'rat' | 'real' | 'char' | 'token')
    ;

/* FIXME Need to fix this, yet --- right now "\"" will fail
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
    : ('mk_')=> 'mk_' { $type=MKUNDER; }
    | ('is_')=> 'is_' { $type=ISUNDER; }
    | INITIAL_LETTER FOLLOW_LETTER*
    ;

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
