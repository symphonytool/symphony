/* A note to other editors of this file:
 * Please try to match the indentation and format of the code that's already here.
 * Thanks, -jwc
 */
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
 * action/statement precedence has still to be resolved
 *
 * make type not allow '()' unless coming from a function?
 *
 * I'm not sure if multiple binds are implemented. -jwc/14Dec2012
 *
 * Note: don't use '()' as a token: it will probably end up
 * conflicting with '(' ')' in places where there is an optional
 * something inside the brackets.
 *
 * LexLocations really ought to be handled as is done in the @after
 * block of the opType rule.  The $ruleName.start and $ruleName.stop
 * attributes give the first and last tokens matched by that rule (or
 * subrules), assuming I understand it correctly (and looking at the
 * generated code appear to confirm my understanding).  -jwc/18Dec2012
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
import org.overture.ast.factory.AstFactory;
import org.overture.ast.definitions.*;
import org.overture.ast.expressions.*;
import org.overture.ast.lex.*;
import org.overture.ast.node.*;
// import org.overture.ast.node.tokens.*;
import org.overture.ast.patterns.*;
// import org.overture.ast.preview.*;
import org.overture.ast.statements.*;
import org.overture.ast.types.*;
import org.overture.ast.typechecker.NameScope;
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

private LexToken extractLexToken(String str, LexLocation loc) {
	VDMToken tok = null;
	for (VDMToken t : VDMToken.values()) {
	    String tokenDisplay = t.toString();
	    if (tokenDisplay != null && tokenDisplay.equals(str)) {
            tok = t;
            break;
	    }
	}
	if (tok == null)
	    throw new RuntimeException("Cannot find VDM token for " + str);
	return new LexToken(loc, tok);
}
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
public AAccessSpecifierAccessSpecifier extractQualifier(CommonToken token) {
    String tokenStr = (token == null ? null : token.getText());
    if (token == null || tokenStr.equals("private")) {
        return new AAccessSpecifierAccessSpecifier(new APrivateAccess(), null, null);
    } else if (tokenStr.equals("protected")) {
        return new AAccessSpecifierAccessSpecifier(new AProtectedAccess(), null, null);
    } else if (tokenStr.equals("public")) {
        return new AAccessSpecifierAccessSpecifier(new APublicAccess(), null, null);
    } else if (tokenStr.equals("logical")) {
        return new AAccessSpecifierAccessSpecifier(new ALogicalAccess(), null, null);
    }
    throw new RuntimeException("The given token, "+token+" is not a qualifier.");
}

} // end @members

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
    : IDENTIFIER ( ',' IDENTIFIER )* ( ':' type | 'in' 'set' expression )  // FIXME -- looks like multiTypeBind | multiSetBind
    ;

renamingExpr
    : renamePair ( ( ',' renamePair )+ | '|' multipleBindList ('@' expression)? )?
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

action returns[PAction action]
    : action0
        { $action = $action0.action; } // FIXME
    | '[' expression ']' '&' action
        { $action = new AGuardedAction(); } // FIXME
    | 'mu' IDENTIFIER (',' IDENTIFIER)* '@'  '(' action (',' action)* ')'
        { $action = new AMuAction(); } // FIXME
    | ( actionSimpleReplOp ) replicationDeclaration '@' action
        { $action = new ASequentialCompositionReplicatedAction(); } // FIXME
    | ( actionSetReplOp ) replicationDeclaration '@' '[' chansetNamesetExpr ( '|' chansetNamesetExpr )? ']' action
        { $action = new ASequentialCompositionReplicatedAction(); } // FIXME
    ;

actionSimpleReplOp
    : ';' | '[]' | '|~|' | '[||' chansetNamesetExpr '||]'
    ;

actionSetReplOp
    : '||' | '|||' | '[|' chansetNamesetExpr '|]'
    ;

action0 returns[PAction action]
    // : action1 (action0Ops action)?
    : (action1 action0Ops)=>action1 action0Ops action
        { $action = $action1.action; } // FIXME
    | (action1 '[[')=>action1 '[[' renamingExpr ']]'
        { $action = $action1.action; } // FIXME
    | action1
        { $action = $action1.action; } // FIXME
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

action1 returns[PAction action]
    : action2
        ( ('startsby' | 'endsby')=> ('startsby' | 'endsby') expression
        | ('\\\\')=> '\\\\' chansetNamesetExpr
        )?
        { $action = $action2.action; } // FIXME
    ;

action2 returns[PAction action]
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
        {
            $action = $statement.statement; 
        }
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

statement returns[PAction statement]
@after { $statement.setLocation(extractLexLocation($statement.start, $statement.stop)); }
    : 'let' localDefinitionList 'in' action
        {
            $statement = new ALetStatementAction(null, $action.action, $localDefinitionList.defs);
        }
    | '[' ('frame' frameSpecList)? ('pre' pre=expression)? 'post' post=expression ']'
        {
            $statement = new ASpecificationStatementAction(null, $frameSpecList.frameSpecs, $pre.exp, $post.exp);
        }
    | ('if' expression 'then')=> 'if' test=expression 'then' th=action (('elseif')=>elseIfStmtOptList) (('else')=>'else' el=action)?
        // need the ()=> to match elseif and else clauses greedily
        {
            $statement = new AIfStatementAction(null, $test.exp, $th.action, $elseIfStmtOptList.elseifs, $el.action);
        }
    | 'if' nonDetStmtAltList 'end'
        {
            $statement = new ANonDeterministicIfStatementAction(null, $nonDetStmtAltList.alts);
        }
    | 'do' nonDetStmtAltList 'end'
        {
            $statement = new ANonDeterministicDoStatementAction(null, $nonDetStmtAltList.alts);
        }
    | 'cases' expression ':' caseStmtAltOptList (',' 'others' '->' action)? 'end'
        {
            $statement = new ACasesStatementAction(null, $expression.exp, $caseStmtAltOptList.alts, $action.action);
        }
    | ('for' 'all')=> 'for' 'all' bindablePattern 'in' 'set' expression 'do' action
        {
            $statement = new AForSetStatementAction(null, $bindablePattern.pattern, $expression.exp, $action.action);
        }
    | ('for' IDENTIFIER '=')=> 'for' IDENTIFIER '=' start=expression 'to' end=expression ( 'by' step=expression )? 'do' action
        {
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            $statement = new AForIndexStatementAction(null, name, $start.exp, $end.exp, $step.exp, $action.action);
        }
    | 'for' bindablePattern (':' type)? 'in' expression 'do' action // was pattern bind here only
        {
            ADefPatternBind patternBind = new ADefPatternBind();
            LexLocation pbloc = $bindablePattern.pattern.getLocation();
            if ($type.type != null) {
                pbloc = extractLexLocation(pbloc, $type.type.getLocation());
                patternBind.setBind(new ATypeBind(pbloc, $bindablePattern.pattern, $type.type));
            } else {
                patternBind.setPattern($bindablePattern.pattern);
            }
            patternBind.setLocation(pbloc); //depends on the if
            $statement = new AForSequenceStatementAction(null, patternBind, $expression.exp, $action.action);
        }
    | ('return' expression)=>'return' expression
        {
            $statement = new AReturnStatementAction(null,$expression.exp);
        }
    | 'return'
        {
            $statement = new AReturnStatementAction();
        }
    | 'while' expression 'do' action
        {
            $statement = new AWhileStatementAction(null, $expression.exp, $action.action);
        }
    | 'atomic' '(' stateDesignator ':=' expression ( ';' stateDesignator ':=' expression )+ ')'
        {
            $statement = new AMultipleGeneralAssignmentStatementAction(); // FIXME
        }
    | (callStatement)=> callStatement // More syntactic predicate magic :)
        {
            $statement = $callStatement.statement;
        }
    | stateDesignator
        ( ':=' 
            ( expression 
                {
                    // FIXME: might be an assignment, might be a call statement
                    $statement = new ASingleGeneralAssignmentStatementAction(); // FIXME
                }
            | 'new' name '(' ( expression ( ',' expression )* )? ')'
                {
                    $statement = new ANewStatementAction(); // FIXME
                }
            )//| callStatement )
        | '<-' callStatement
            {
                $statement = new ACallStatementAction(); // FIXME
            }
        )
    ;

// need the ()=> to match elseifs greedily
elseIfStmtOptList returns[List<AElseIfStatementAction> elseifs]
@init { $elseifs = new ArrayList<AElseIfStatementAction>(); }
    : ( ('elseif')=>elseIfStmt { $elseifs.add($elseIfStmt.elseif); } )*
//    | /* empty match; we want a null list if no elseifs */
    ;

elseIfStmt returns[AElseIfStatementAction elseif]
@after { $elseif.setLocation(extractLexLocation($elseIfStmt.start,$elseIfStmt.stop)); }
    : 'elseif' test=expression 'then' th=action
        {
            $elseif = new AElseIfStatementAction(null, $test.exp, $th.action);
        }
    ;

caseStmtAltOptList returns[List<ACaseAlternativeAction> alts]
@init { $alts = new ArrayList<ACaseAlternativeAction>(); }
    : first=caseStmtAlt { $alts.add($first.alt); } ( ',' altItem=caseStmtAlt { alts.add($altItem.alt); } )*
    | /* empty match; we want a null list if no alternative */
    ;

caseStmtAlt returns[ACaseAlternativeAction alt]
@after { $alt.setLocation(extractLexLocation($caseStmtAlt.start, $caseStmtAlt.stop)); }
    : patternList '->' action
        {
            $alt = new ACaseAlternativeAction(null, $patternList.patterns, $action.action);
        }
    ;

localDefinitionList returns[List<PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : first=localDefinition { $defs.add($first.def); } ( ',' defItem=localDefinition { $defs.add($defItem.def); } )*
    ;

localDefinition returns[PDefinition def]
    : (valueDefinition)=> valueDefinition { $def = $valueDefinition.def; }
    | functionDefinition                  { $def = $functionDefinition.def; }
    ;

nonDetStmtAltList returns[List<ANonDeterministicAltStatementAction> alts]
@init { $alts = new ArrayList<ANonDeterministicAltStatementAction>(); }
    : first=nonDetStmtAlt { $alts.add($first.alt); } ( '[]' altItem=nonDetStmtAlt { $alts.add($altItem.alt); } )*
    ;

nonDetStmtAlt returns[ANonDeterministicAltStatementAction alt]
@after { $alt.setLocation(extractLexLocation($nonDetStmtAlt.start, $nonDetStmtAlt.stop)); }
    : expression '->' action
        {
            $alt = new ANonDeterministicAltStatementAction(null, $expression.exp, $action.action);
        }
    ;

frameSpecList returns[List<AExternalClause> frameSpecs]
@init { $frameSpecs = new ArrayList<AExternalClause>(); }
    : first=frameSpec { $frameSpecs.add($first.frameSpec); } ( ',' frameItem=frameSpec { $frameSpecs.add($frameItem.frameSpec); } )*
    ;

frameSpec returns[AExternalClause frameSpec]
    : FRAMEMODE nameList (':' type)?
        {
            LexToken mode = new LexToken(extractLexLocation($FRAMEMODE), VDMToken.lookup($FRAMEMODE.getText(), VDM_PP));
            //new LexToken(, VDMToken.READ);
            $frameSpec = new AExternalClause(mode, $nameList.names, $type.type);
        }
    ;

stateDesignator returns[PStateDesignator sd]
//@after { $sd.setLocation(extractLexLocation($stateDesignator.start, $stateDesignator.stop)); }
    : name sDTail?
    ;

sDTail returns[List<PExp> tail] // type is incorrect
    : '(' expression ')' ( '.' stateDesignator | sDTail )?
    ;

/* This does not support the 'object apply' form of the
 * objectDesignator, but I don't know what that's for, anyway.
 * Chained calls?  That should be more general anyway.
 *
 * FIXME -- missing objectDesignator part of this
 */
callStatement returns[PAction statement]
@after { $statement.setLocation(extractLexLocation($callStatement.start, $callStatement.stop)); }
    : name '(' expressionList? ')'
        {
            $statement = new ACallStatementAction(null, null, $name.name, $expressionList.exps);
        }
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
    : IDENTIFIER '=' chansetNamesetExpr
    ;

classDefinitionBlock
    : typeDefs
    | valueDefs
    | stateDefs
    | functionDefs
    | operationDefs
    | 'initial' operationDef // why should this operation require a name, or be allowed to be explicit?
    ;

valueDefs returns[AValueParagraphDefinition defs]
@after { $defs.setLocation(extractLexLocation($valueDefs.start, $valueDefs.stop)); }
    : 'values' qualValueDefinitionList? ';'?
        {
            AAccessSpecifierAccessSpecifier access = CmlParserHelper.getDefaultAccessSpecifier(true, false, extractLexLocation($valueDefs.start));
            $defs = new AValueParagraphDefinition(null, NameScope.NAMES, false, access, null, $qualValueDefinitionList.defs);
        }
    ;

qualValueDefinitionList returns[List<AValueDefinition> defs]
@init { defs = new ArrayList<AValueDefinition>(); }
    : item=qualValueDefinition { $defs.add($item.def); } ( ';' item=qualValueDefinition { $defs.add($item.def); } )*
    ;

qualValueDefinition returns[AValueDefinition def]
    : QUALIFIER? valueDefinition
        {
            $def = $valueDefinition.def;
            $def.setAccess(extractQualifier($QUALIFIER));
            LexLocation loc = extractLexLocation(extractLexLocation($qualValueDefinition.start), $qualValueDefinition.def.getLocation()) ;
            $def.setLocation(loc);
        }

    ;
valueDefinition returns[AValueDefinition def]
// We've had requests for the "be st" type of value definitions, but
//  they're not in for now.  The Overture AST does this differently
//  than I'd like. -jwc/18Dec2012
//
// Also, apparently JPCW doesn't want the "be st" because they
//  introduce a type on non-determinism that he doesn't want to deal
//  with.  So, they'll not happen. -jwc/19Dec2012
//
//  : bindablePattern (':' type)? ( '=' | 'be' 'st' ) expression
    : bindablePattern (':' type)? '=' expression
        {
            $def = AstFactory.newAValueDefinition($bindablePattern.pattern, NameScope.LOCAL, $type.type, $expression.exp);
        }
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

functionDefs returns[AFunctionParagraphDefinition defs]
@after { $defs.setLocation(extractLexLocation($functionDefs.start, $functionDefs.stop)); }
    : 'functions' qualFunctionDefinitionOptList
        {
            AAccessSpecifierAccessSpecifier access = CmlParserHelper.getDefaultAccessSpecifier(true, false, extractLexLocation($functionDefs.start));
            $defs = new AFunctionParagraphDefinition(null, NameScope.GLOBAL, false, access, null, $qualFunctionDefinitionOptList.defs);
        }
    ;

qualFunctionDefinitionOptList returns[List<PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : (QUALIFIER? functionDefinition 
            {
                $functionDefinition.def.setAccess(extractQualifier($QUALIFIER));
                LexLocation loc = extractLexLocation(extractLexLocation($qualFunctionDefinitionOptList.start),
                                                     $functionDefinition.def.getLocation());
                $functionDefinition.def.setLocation(loc);
                $defs.add($functionDefinition.def);
            }
        )*
    ;

functionDefinition returns[PDefinition def]
@after { $def.setLocation(extractLexLocation($functionDefinition.start, $functionDefinition.stop)); }
    : IDENTIFIER (expl=explicitFunctionDefinitionTail | impl=implicitFunctionDefinitionTail)
        {
            if ($expl.tail != null) {
                $def = $expl.tail;
                if ( !$IDENTIFIER.getText().equals($def.getName().name) ) {
                    System.out.println("Mismatch in function definition.  Signature has " + $IDENTIFIER.getText() + ", definition has " + $def.getName().name);
                    // FIXME --- here we need some sort of exception (probably RecognitionException) to note the mismatch
                }
            } else {
                $def = $impl.tail;
            }
            $def.setName(new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER)));
        }
    ;

explicitFunctionDefinitionTail returns[AExplicitFunctionDefinition tail]
    : ':' type IDENTIFIER parameterGroupList '==' functionBody (pretok='pre' pre=expression )? ('post' post=expression)? ('measure' name)?
        {
            $tail = new AExplicitFunctionDefinition();
            $tail.setType($type.type);
            $tail.setName(new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER)));
            $tail.setParamPatternList($parameterGroupList.pgroups);
            $tail.setBody($functionBody.exp);
            $tail.setIsUndefined(false);
            $tail.setRecursive(false);
            $tail.setMeasure($name.name);
            $tail.setAccess(CmlParserHelper.getPrivateAccessSpecifier(false, false, extractLexLocation($IDENTIFIER)));
            //$tail.setPass(Pass.DEFS); // what's this for?
        }
    ;

parameterGroupList returns[List<List<PPattern>> pgroups]
@init { $pgroups = new ArrayList<List<PPattern>>(); }
    : first=parameterGroup { $pgroups.add($first.pgroup); } ( pgItem=parameterGroup { $pgroups.add($pgItem.pgroup); } )*
    ;

parameterGroup returns[List<PPattern> pgroup]
    : '(' bindablePatternList? ')'
        {
            $pgroup = $bindablePatternList.patterns;
            if ($pgroup == null)
                $pgroup = new ArrayList<PPattern>();
        }
    ;

implicitFunctionDefinitionTail returns[AImplicitFunctionDefinition tail]
    : '(' parameterTypeList? ')' resultTypeList ('pre' pre=expression )? posttok='post' post=expression
        {
            $tail = new AImplicitFunctionDefinition();
            $tail.setNameScope(NameScope.LOCAL);
            $tail.setUsed(Boolean.FALSE);
            $tail.setAccess(CmlParserHelper.getDefaultAccessSpecifier(false,false,null));

            List<APatternListTypePair> paramPatterns = $parameterTypeList.ptypes;
            if (paramPatterns == null)
                paramPatterns = new ArrayList<APatternListTypePair>();
            $tail.setParamPatterns(paramPatterns);

            List<APatternTypePair> resultList = $resultTypeList.rtypes;
            APatternTypePair resultTypePair = null;
            if (resultList.size() == 1) {
                resultTypePair = resultList.get(0);
            } else {
                // VDMJ needs a product type of all of the result types
                ATuplePattern tuple = new ATuplePattern();
                List<PPattern> plist = new ArrayList<PPattern>();
                for (APatternTypePair pair : resultList)
                    plist.add(pair.getPattern());
                tuple.setPlist(plist);
                resultTypePair = new APatternTypePair(false, tuple);
            }
            $tail.setResult(resultTypePair);
            
            if ($pre.exp != null)
                $tail.setPrecondition($pre.exp);
            else
                $tail.setPrecondition(AstFactory.newABooleanConstExp(new LexBooleanToken(true, extractLexLocation($posttok))));

            $tail.setPostcondition($post.exp);

            // figure out the overall function type
            List<PType> paramTypes = new ArrayList<PType>();
            for (APatternListTypePair pp : paramPatterns)
                paramTypes.add(pp.getType());
            LexLocation typeloc = extractLexLocation($implicitFunctionDefinitionTail.start, $resultTypeList.stop);
            $tail.setType(AstFactory.newAFunctionType(typeloc, true, paramTypes, resultTypePair.getType()));
        }
    ;

parameterTypeList returns[List<APatternListTypePair> ptypes]
@init { $ptypes = new ArrayList<APatternListTypePair>(); }
    : first=parameterTypeGroup { $ptypes.add($first.ptype); } ( ',' ptypeItem=parameterTypeGroup { $ptypes.add($ptypeItem.ptype); } )*
    ;

parameterTypeGroup returns[APatternListTypePair ptype]
    : bindablePatternList ':' type
        {
            $ptype = new APatternListTypePair(false, $bindablePatternList.patterns, $type.type);
        }
    ;

resultTypeList returns[List<APatternTypePair> rtypes]
@init { $rtypes = new ArrayList<APatternTypePair>(); }
    : first=resultType { $rtypes.add($first.rtype); } ( ',' resItem=resultType { $rtypes.add($resItem.rtype); } )*
    ;

resultType returns[APatternTypePair rtype]
    : IDENTIFIER ':' type
        {
            LexLocation loc = extractLexLocation($IDENTIFIER);
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), loc, false, true);
            $rtype = new APatternTypePair(false, new AIdentifierPattern(loc, null, true, name, false), $type.type);
        }
    ;

functionBody returns[PExp exp]
@after { $exp.setLocation(extractLexLocation($functionBody.start, $functionBody.stop)); }
    : expression                       { $exp = $expression.exp; }
    | 'is' 'not' 'yet' 'specified'     { $exp = new ANotYetSpecifiedExp(); }
    | 'is' 'subclass' 'responsibility' { $exp = new ASubclassResponsibilityExp(); }
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

opType returns[PType type]
@after { $type.setLocation(extractLexLocation($opType.start, $opType.stop)); }
    : ( dom=type0 | vdom='(' vdom2=')' ) '==>' ( rng=type0 | vrng='(' vrng2=')' )
        {
            PType domType = ($dom.type != null ? $dom.type : new AVoidType(extractLexLocation($vdom,$vdom2),true));
            PType rngType = ($rng.type != null ? $rng.type : new AVoidType(extractLexLocation($vrng,$vrng2),true));
            LexLocation loc = extractLexLocation(domType.getLocation(),rngType.getLocation());
            List<PType> typeList = new ArrayList<PType>();
            typeList.add(domType);
            $type = new AOperationType(loc, false, null, typeList, rngType);
        }
    ;

operationBody
    : action
    | 'is' 'not' 'yet' 'specified'
    | 'is' 'subclass' 'responsibility'
    ;

typeDefs returns[SParagraphDefinition para]
@init {
    List<ATypeDefinition> typeDefList = new ArrayList<ATypeDefinition>();
    ATypeDefinition last = null;
}
    : t='types' ( def=typeDef { last = $def.def; typeDefList.add(last); } )*
        {
            LexLocation loc = extractLexLocation($t);
            if (typeDefList.size()>0)
                loc = extractLexLocation(loc,last.getLocation());
            $para = new ATypesParagraphDefinition(loc, NameScope.LOCAL, false,
                                                  CmlParserHelper.getDefaultAccessSpecifier(true, false, loc),
                                                  null, typeDefList);
            $para.setName(new LexNameToken("", $t.getText(), loc));
        }
    ;

typeDef returns[ATypeDefinition def]
    : QUALIFIER? IDENTIFIER '=' type invariant?
        {
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            ANamedInvariantType invType = AstFactory.newANamedInvariantType(name, $type.type);
            PPattern invPat = ($invariant.inv != null ? $invariant.inv.getPattern() : null);
            PExp invExp = ($invariant.inv != null ? $invariant.inv.getExpression() : null);
            $def = AstFactory.newATypeDefinition(name,invType,invPat,invExp);
            $def.setAccess(extractQualifier($QUALIFIER));

        }
    | QUALIFIER? IDENTIFIER '::' fieldList invariant?
        {
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            ARecordInvariantType invType = AstFactory.newARecordInvariantType(name, $fieldList.fieldList);
            PPattern invPat = ($invariant.inv != null ? $invariant.inv.getPattern() : null);
            PExp invExp = ($invariant.inv != null ? $invariant.inv.getExpression() : null);
            $def = AstFactory.newATypeDefinition(name,invType,invPat,invExp);
            $def.setAccess(extractQualifier($QUALIFIER));
        }
    ;

type returns[PType type]
@init { boolean totalFuncType=false; }
    : dom=type0 (( '->' | '+>' { totalFuncType=true; } ) rng=type0)?
        {
            if ($rng.type == null) {
                $type = $dom.type;
            } else {
                LexLocation loc = extractLexLocation($dom.type.getLocation(), $rng.type.getLocation());
                List<PType> params = new ArrayList<PType>();
                params.add($dom.type);
                $type = new AFunctionType(loc, false, null, totalFuncType, params, $rng.type);
            }
        }
    | unit='('')' ( '->' | '+>' { totalFuncType=true; } ) rng=type0
        {
            LexLocation loc = extractLexLocation($dom.type.getLocation(), $rng.type.getLocation());
            List<PType> params = new ArrayList<PType>();
            params.add(new AVoidType(extractLexLocation($unit), true));
            $type = new AFunctionType(loc, false, null, totalFuncType, params, $rng.type);
        }
    ;

type0 returns[PType type]
@init { List<PType> typeList = new ArrayList<PType>(); LexLocation last = null; }
    : first=type1 ('|' typeItem=type1 { typeList.add($typeItem.type); last = $typeItem.type.getLocation(); } )*
        {
            if (typeList.size()==0) {
                $type = $first.type;
            } else {
                LexLocation loc = extractLexLocation($first.type.getLocation(), last);
                AUnionType union = new AUnionType(loc, false, false, false);
                typeList.add(0, $first.type);
                union.setTypes(typeList);
                $type = union;
            }
        }
    ;

type1 returns[PType type]
@init { List<PType> typeList = new ArrayList<PType>(); LexLocation last = null; }
    : first=typebase ('*' typeItem=typebase  { typeList.add($typeItem.type); last = $typeItem.type.getLocation(); } )*
        {
            if (typeList.size()==0) {
                $type = $first.type;
            } else {
                LexLocation loc = extractLexLocation($first.type.getLocation(), last);
                typeList.add(0, $first.type);
                $type = new AProductType(loc, false, null, typeList);
            }
        }
    ;

typebase returns[PType type]
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
    | name
        {
            LexNameToken tname = $name.name;
            LexLocation loc = tname.getLocation();
            $type = new ANamedInvariantType(loc, false, null, false, null, tname,
                                            new AUnresolvedType(loc, false, new ArrayList<PDefinition>(), tname));
        }
    | op='set' 'of' sub=typebase
        {
            LexLocation loc = extractLexLocation(extractLexLocation($op), $sub.type.getLocation());
            $type = new ASetType(loc, false, null, $sub.type, false, false);
        }
    | op='seq' 'of' sub=typebase
        {
            LexLocation loc = extractLexLocation(extractLexLocation($op), $sub.type.getLocation());
            $type = new ASeqSeqType(loc, false, null, $sub.type, false);
        }
    | op='seq1' 'of' sub=typebase
        {
            LexLocation loc = extractLexLocation(extractLexLocation($op), $sub.type.getLocation());
            $type = new ASeq1SeqType(loc, false, null, $sub.type, false);
        }
    | op='map' dom=typebase 'to' rng=typebase
        {
            LexLocation loc = extractLexLocation(extractLexLocation($op), $rng.type.getLocation());
            $type = new AMapMapType(loc, false, null, $dom.type, $rng.type, false);
        }
    | op='inmap' dom=typebase 'to' rng=typebase
        {
            LexLocation loc = extractLexLocation(extractLexLocation($op), $rng.type.getLocation());
            $type = new AInMapMapType(loc, false, null, $dom.type, $rng.type, false);
        }
    | op='compose' IDENTIFIER 'of' fieldList end='end'
        {
            LexLocation loc = extractLexLocation($op,$end);
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            $type = new ARecordInvariantType(loc, false, null, false, null, name, $fieldList.fieldList, false);
        }
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

fieldList returns[List<AFieldField> fieldList]
@init { $fieldList = new ArrayList<AFieldField>(); }
    : first=field { $fieldList.add($first.field); } ( fieldItem=field { $fieldList.add($fieldItem.field); } )*
    ;

field returns[AFieldField field]
@init { AAccessSpecifierAccessSpecifier access = CmlParserHelper.getDefaultAccessSpecifier(false,false,null); }
    : type
        {
            LexLocation loc = $type.type.getLocation();
            LexNameToken name = new LexNameToken("", new LexIdentifierToken("",false,loc));
            $field = new AFieldField(null, name, null, $type.type, false);
        }
    | IDENTIFIER ':' type
        {
            String idStr = $IDENTIFIER.getText();
            LexNameToken name = new LexNameToken("", idStr, extractLexLocation($IDENTIFIER));
            $field = new AFieldField(access, name, idStr, $type.type, false);
        }
    | IDENTIFIER ':-' type
        {
            String idStr = $IDENTIFIER.getText();
            LexNameToken name = new LexNameToken("", idStr, extractLexLocation($IDENTIFIER));
            $field = new AFieldField(access, name, idStr, $type.type, true);
        }
    ;

invariant returns[AInvariantDefinition inv]
    : t='inv' pat=bindablePattern '==' expression
        {
            LexLocation loc = extractLexLocation(extractLexLocation($t),$expression.exp.getLocation());
            AAccessSpecifierAccessSpecifier access = CmlParserHelper.getDefaultAccessSpecifier(true, true, loc);
            $inv = new AInvariantDefinition(loc, null, NameScope.LOCAL, false, null, access, null, null, $pat.pattern, $expression.exp);
        }
    ;

pattern returns[PPattern pattern]
    : bindablePattern { $pattern = $bindablePattern.pattern; }
    | matchValue      { $pattern = $matchValue.pattern; }
    ;

bindablePatternList returns[List<PPattern> patterns]
@init { $patterns = new ArrayList<PPattern>(); }
    : first=bindablePattern { $patterns.add($first.pattern); } ( ',' patItem=bindablePattern { $patterns.add($patItem.pattern); } )*
    ;

bindablePattern returns[PPattern pattern]
    : patternIdentifier { $pattern = $patternIdentifier.pattern; }
    | tuplePattern      { $pattern = $tuplePattern.pattern; }
    | recordPattern     { $pattern = $recordPattern.pattern; }
    ;

patternIdentifier returns[PPattern pattern]
    : IDENTIFIER
        {
            LexLocation loc = extractLexLocation($IDENTIFIER);
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), loc, false, true);
            $pattern = new AIdentifierPattern(loc, null, true, name, false);
        }
    | minus='-'
        {
            $pattern = new AIgnorePattern(extractLexLocation($minus), null, true);
        }
    ;

matchValue returns[PPattern pattern]
    : lit=symbolicLiteral
        {
            if ($lit.literal instanceof LexIntegerToken) {
                $pattern = new AIntegerPattern($lit.literal.location, null, true, (LexIntegerToken)$lit.literal);
            } else if ($lit.literal instanceof LexRealToken) {
                $pattern = new ARealPattern($lit.literal.location, null, true, (LexRealToken)$lit.literal);
            } else if ($lit.literal instanceof LexBooleanToken) {
                $pattern = new ABooleanPattern($lit.literal.location, null, true, (LexBooleanToken)$lit.literal);
            } else if ($lit.literal instanceof LexKeywordToken) {
                // Note, this assumes that lit only ever
                // gives a LexKeywordToken for 'nil'
                $pattern = new ANilPattern($lit.literal.location, null, true);
            } else if ($lit.literal instanceof LexCharacterToken) {
                $pattern = new ACharacterPattern($lit.literal.location, null, true, (LexCharacterToken)$lit.literal);
            } else if ($lit.literal instanceof LexStringToken) {
                $pattern = new AStringPattern($lit.literal.location, null, true, (LexStringToken)$lit.literal);
            } else if ($lit.literal instanceof LexQuoteToken) {
                $pattern = new AQuotePattern($lit.literal.location, null, true, (LexQuoteToken)$lit.literal);
            } else {
                // FIXME log a never-happens error
            }
        }
    | l='(' expression r=')'
        {
            LexLocation loc = extractLexLocation($l,$r);
            $pattern = new AExpressionPattern(loc, null, false, $expression.exp);
        }
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

tuplePattern returns[PPattern pattern]
@init { List<PPattern> patList = new ArrayList<PPattern>(); }
    : MKUNDER '(' first=pattern ( ',' patItem=pattern { patList.add($patItem.pattern); } )* end=')'
        {
            LexLocation loc = extractLexLocation($MKUNDER,$end);
            patList.add(0, $first.pattern);
            $pattern = new ATuplePattern(loc, null, true, patList);
        }
    ;

recordPattern returns[PPattern pattern]
@init { List<PPattern> patList = new ArrayList<PPattern>(); }
    : MKUNDER name '(' ( first=pattern { patList.add($first.pattern); } ( ',' patItem=pattern { patList.add($patItem.pattern); } )* )? end=')'
        {
            LexLocation loc = extractLexLocation($MKUNDER,$end);
            $pattern = new ARecordPattern(loc, null, true, $name.name, patList);
        }
    ;

expressionList returns[List<PExp> exps]
@init { $exps = new ArrayList<PExp>(); }
    : first=expression { $exps.add($first.exp); } ( ',' expItem=expression { $exps.add($expItem.exp); } )*
    ;

expression returns[PExp exp]
@after { $exp.setLocation(extractLexLocation($expression.start,$expression.stop)); }
    : expr0
        {
            $exp = $expr0.exp;
        }
    | 'let' localDefinitionList 'in' body=expression
        {
            $exp = new ALetDefExp(null, $localDefinitionList.defs, $body.exp);
        }
    | 'if' test=expression 'then' th=expression elseIfExprOptList 'else' el=expression
        {
            $exp = new AIfExp(null, $test.exp, $th.exp, $elseIfExprOptList.elseifs, $el.exp);
        }
    | 'cases' cexp=expression ':' caseExprAltOptList ( ',' 'others' '->' oexp=expression )? 'end'
        {
            $exp = new ACasesExp(null, $cexp.exp, $caseExprAltOptList.alts, $oexp.exp);
        }
    | 'forall' multipleBindList '@' body=expression
        {
            $exp = new AForAllExp(null, $multipleBindList.bindings, $body.exp);
        }
    | 'exists' multipleBindList '@' body=expression
        {
            $exp = new AExistsExp(null, $multipleBindList.bindings, $body.exp);
        }
    | 'exists1' singleBind '@' body=expression
        {
            $exp = new AExists1Exp(null, $singleBind.binding, $body.exp, null);
        }
    | 'iota' singleBind '@' body=expression
        {
            $exp = new AIotaExp(null, $singleBind.binding, $body.exp);
        }
    | 'lambda' typeBindList '@' body=expression
        {
            $exp = new ALambdaExp(null, $typeBindList.bindings, $body.exp, null, null);
        }
    ;

elseIfExprOptList returns[List<AElseIfExp> elseifs]
@init { $elseifs = new ArrayList<AElseIfExp>(); }
    : ( elseIfExpr { $elseifs.add($elseIfExpr.elseif); } )*
    ;

elseIfExpr returns[AElseIfExp elseif]
@after { $elseif.setLocation(extractLexLocation($elseIfExpr.start,$elseIfExpr.stop)); }
    : 'elseif' test=expression 'then' th=expression
        {
            $elseif = new AElseIfExp(null, $test.exp, $th.exp);
        }
    ;

caseExprAltOptList returns[List<ACaseAlternative> alts]
@init { $alts = new ArrayList<ACaseAlternative>(); }
    : first=caseExprAlt { $alts.addAll($first.alts); } ( ',' altItem=caseExprAlt { alts.addAll($altItem.alts); } )*
    | /* empty match; we want a null list if no alternative */
    ;

caseExprAlt returns[List<ACaseAlternative> alts]
@init { $alts = new ArrayList<ACaseAlternative>(); }
    : patternList '->' expression
        {
            LexLocation eloc = $expression.exp.getLocation();
            for (PPattern p : $patternList.patterns) {
                LexLocation loc = extractLexLocation(p.getLocation(), eloc);
                ACaseAlternative alt = new ACaseAlternative(loc, null, p, $expression.exp, null);
            }
        }
    ;

patternList returns[List<PPattern> patterns]
@init { $patterns = new ArrayList<PPattern>(); }
    : first=pattern { $patterns.add($first.pattern); } ( ',' patItem=pattern { $patterns.add($patItem.pattern); } )*
    ;

expr0 returns[PExp exp]
    : e1=expr1 (o='<=>' e2=expr0)?
        {
            if (e2 == null)
                $exp = $e1.exp;
            else
                $exp = new AEquivalentBooleanBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                                       $e1.exp,
                                                       extractLexToken($o.getText(), extractLexLocation($o)),
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
                                                    extractLexToken($o.getText(), extractLexLocation($o)),
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
                                               extractLexToken($o.getText(), extractLexLocation($o)),
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
                                                extractLexToken($o.getText(), extractLexLocation($o)),
                                                $e2.exp);
        }
    ;

binOpRel returns[SBinaryExpBase op]
@init { LexLocation loc = null; String opStr = null; }
@after { op.setLocation(loc); op.setOp(extractLexToken(opStr, loc)); }
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
@after { op.setLocation(loc); op.setOp(extractLexToken(opStr, loc)); }
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

binOpEval2 returns[SBinaryExpBase op]
@init { LexLocation loc = null; String opStr = null; }
@after { op.setLocation(loc); op.setOp(extractLexToken(opStr, loc)); }
    : o='*'     { $op = new ATimesNumericBinaryExp();  loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='/'     { $op = new ADivideNumericBinaryExp();    loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='rem'   { $op = new ARemNumericBinaryExp();    loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='mod'   { $op = new AModNumericBinaryExp();    loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='div'   { $op = new ADivNumericBinaryExp(); loc = extractLexLocation($o); opStr = $o.getText(); }
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
@after { op.setLocation(loc); op.setOp(extractLexToken(opStr, loc)); }
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
@after { op.setLocation(loc); op.setOp(extractLexToken(opStr, loc)); }
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
                                          extractLexToken($o.getText(), extractLexLocation($o)),
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
                                              extractLexToken($o.getText(), extractLexLocation($o)),
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
@init { List<PExp> selectors = new ArrayList<PExp>(); }
    : unaryOp operand=expr11
        {
            SUnaryExp unaryop = $unaryOp.op;
            PExp target = $operand.exp;
            unaryop.setExp(target);
            unaryop.setLocation(extractLexLocation(unaryop.getLocation(), target.getLocation()));
            $exp = unaryop;
        }
    | exprbase ( selector { selectors.add($selector.exp); } )*
        {
            $exp = $exprbase.exp; // Set the leftmost root
            for (PExp sel : selectors) { // Iterate through the selectors, building a left->right tree
                LexLocation loc = extractLexLocation($exp.getLocation(), sel.getLocation());
                if (sel instanceof AFieldNumberExp) {
                    ((AFieldNumberExp)sel).setTuple($exp);
                    sel.setLocation(loc);
                    $exp = sel;
                } else if (sel instanceof AApplyExp) {
                    ((AApplyExp)sel).setRoot($exp);
                    sel.setLocation(loc);
                    $exp = sel;
                } else if (sel instanceof ASubseqExp) {
                    ((ASubseqExp)sel).setSeq($exp);
                    sel.setLocation(loc);
                    $exp = sel;
                } else if (sel instanceof AUnresolvedPathExp) {
                    // selector was a ".IDENTIFIER"; now let's figure out what to do
                    AUnresolvedPathExp aupe = (AUnresolvedPathExp)sel; // avoid many casts
                    if ($exp instanceof AVariableExp) { // if the left was an IDENTIFIER, then...
                        AVariableExp ave = (AVariableExp)$exp;
                        aupe.setLocation(loc);
                        aupe.getIdentifiers().add(0, ave.getName().getIdentifier());
                        $exp = aupe;
                    } else if ($exp instanceof AUnresolvedPathExp) { // if it was an unresolved path...
                        $exp.setLocation(loc);
                        ((AUnresolvedPathExp)$exp).getIdentifiers().addAll(aupe.getIdentifiers());
                        // no need to assign to $exp here
                    } else { // otherwise it must be a field access
                        // the AUnresolvedPathExp we get from the list
                        // will only have a single Identifier in it.
                        LexIdentifierToken id = aupe.getIdentifiers().get(0);
                        $exp = AstFactory.newAFieldExp($exp,id);
                    }
                }
            }
        }
    ;

// Note that each selector expression is partial, returned with a null "root" expression
selector returns[PExp exp]
@init { List<PExp> paramList = new ArrayList<PExp>(); }
    : l='('
        ( first=expression
            ( ',' '...' ',' end=expression
            | (',' paramItem=expression { paramList.add($paramItem.exp); } )+
            )?
        )?
        r=')' // function application, sequence select and subsequence
        {
            LexLocation loc = extractLexLocation($l, $r);
            if ($first.exp != null) {
                if ($end.exp != null) {
                    $exp = new ASubseqExp(loc, null, $first.exp, $end.exp);
                } else {
                    // Single param in () is treated as apply, even when sequence select
                    paramList.add(0, $first.exp);
                    $exp = new AApplyExp(loc, null, paramList);
                }
            } else { // must be a empty apply expression
                $exp = new AApplyExp(loc, null, new ArrayList<PExp>());
            }
        }
    | TUPLESELECTOR // tuple select
        {
            LexLocation loc = extractLexLocation($TUPLESELECTOR);
            String num = $TUPLESELECTOR.getText().substring(2);
            LexIntegerToken fieldnum = new LexIntegerToken(Long.parseLong(num), loc);
            $exp = new AFieldNumberExp(loc, null, fieldnum);
        }
    | t=('.'|'`') IDENTIFIER // field select, usually: it can only be a name *if* the thing immediately left of the dot is an identifier (but not guaranteed)
        {
            LexLocation loc = extractLexLocation($t,$IDENTIFIER);
            LexLocation idloc = extractLexLocation($IDENTIFIER);
            List<LexIdentifierToken> idList = new ArrayList<LexIdentifierToken>();
            idList.add(new LexIdentifierToken($IDENTIFIER.getText(), false, idloc));
            $exp = new AUnresolvedPathExp(loc, idList);
        }
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
                $exp = new ACharLiteralExp($lit.literal.location, (LexCharacterToken)$lit.literal);
            } else if ($lit.literal instanceof LexStringToken) {
                ASeqSeqType charSeqType = new ASeqSeqType($lit.literal.location,
                                                          true, null,
                                                          new ACharBasicType(),
                                                          (((LexStringToken)$lit.literal).value.length() == 0));
                $exp = new AStringLiteralExp(charSeqType, $lit.literal.location, (LexStringToken)$lit.literal);
            } else if ($lit.literal instanceof LexQuoteToken) {
                $exp = new AQuoteLiteralExp($lit.literal.location, (LexQuoteToken)$lit.literal);
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
@after { $seqExpr.setLocation(extractLexLocation($seqExpr.start, $seqExpr.stop)); }
    : exp=expression
        ( '|' binding=setBind ('@' pred=expression)?
        | (',' enumItem=expression { exps.add($enumItem.exp); } )*
            // Apparently, [1,...,5] is neither valid VDM nor CML
            // | ',' '...' ','  end=expression
        )
        {
            if ($setBind.binding != null) {
                $seqExpr = new ASeqCompSeqExp(null, $exp.exp, $setBind.binding, $pred.exp);
            } else {
                exps.add(0, $exp.exp);
                $seqExpr = new ASeqEnumSeqExp(null, exps);
            }
        }
    ;

recordTupleExprs returns[PExp exp]
@after { $exp.setLocation(extractLexLocation($recordTupleExprs.start, $recordTupleExprs.stop)); }
    : MKUNDER '(' expression ',' expressionList ')'
        {
            $expressionList.exps.add(0,$expression.exp);
            $exp = new ATupleExp(null,$expressionList.exps);
        }
    | MKUNDER token='token' '(' expression ')'
        {
            $exp = new AMkBasicExp(new ATokenBasicType(extractLexLocation($token), true), null, $expression.exp);
        }
    | MKUNDER name '(' expressionList? ')'
        {
            List<PExp> exps = $expressionList.exps;
            if (exps == null)
                exps = new ArrayList<PExp>();
            $exp = new AMkTypeExp(null, $name.name, exps);
        }
    ;

setMapExpr returns[PExp exp]
@after { $exp.setLocation(extractLexLocation($setMapExpr.start, $setMapExpr.stop)); }
    : l='{' ( empty='|->' | setMapExprGuts )? r='}'
        {
            if ($setMapExprGuts.exp != null) {
                $exp = $setMapExprGuts.exp;
            } else if ($empty != null) {
                $exp = new AMapEnumMapExp(null, new ArrayList<AMapletExp>());
            } else {
                $exp = new ASetEnumSetExp(null, new ArrayList<PExp>());
            }
        }
    ;

setMapExprGuts returns[PExp exp]
@init {
    List<PExp> exps = new ArrayList<PExp>();
    List<AMapletExp> mexps = new ArrayList<AMapletExp>();
}
@after { $exp.setLocation(extractLexLocation($setMapExprGuts.start, $setMapExprGuts.stop)); }
    : first=expression
        ( ',' '...' ',' last=expression
            {
                $exp = new ASetRangeSetExp(null, $first.exp, $last.exp);
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
                    $exp = new AMapCompMapExp(null, firstMaplet, $mbinding.bindings, $mbinding.pred);
                } else {
                    mexps.add(0, firstMaplet);
                    $exp = new AMapEnumMapExp(null, mexps);
                }
            }
        | binding=setMapExprBinding
            {
                  $exp = new ASetCompSetExp(null, $first.exp, $binding.bindings, $binding.pred);
            }
        )?
        {
            if ($exp == null) {
                exps.add(0, $first.exp);
                $exp = new ASetEnumSetExp(null, exps);
            } else {
                // FIXME Log a never-happens
            }
        }
    ;

setMapExprBinding returns[List<PMultipleBind> bindings, PExp pred]
    : '|' multipleBindList ('@' expression)?
        {
            $bindings = $multipleBindList.bindings;
            $pred = $expression.exp;
        }
    ;

multipleBindList returns[List<PMultipleBind> bindings]
@init { List<PMultipleBind> bindingList = new ArrayList<PMultipleBind>(); }
    : first=multipleBind ( ',' bindingListItem=multipleBind { bindingList.add($bindingListItem.bindings); } )*
        {
            bindingList.add(0, $first.bindings);
            $bindings = bindingList;
        }
    ;

multipleBind returns[PMultipleBind bindings]
@init { List<PPattern> patList = new ArrayList<PPattern>(); }
    : first=bindablePattern ( ',' patItem=bindablePattern { patList.add($patItem.pattern); } )* ('in' 'set' expression | ':' type)
        {
            patList.add($first.pattern);
            LexLocation loc = $first.pattern.getLocation();
            if ($expression.exp != null) {
                loc = extractLexLocation(loc, $expression.exp.getLocation());
                $bindings = new ASetMultipleBind(loc, patList, $expression.exp);
            } else if ($type.type != null) {
                loc = extractLexLocation(loc, $type.type.getLocation());
                $bindings = new ATypeMultipleBind(loc, patList, $type.type);
            } else {
                // FIXME Log a never-happens
            }
        }
    ;

singleBind returns[PBind binding]
    : bindablePattern ('in' 'set' expression | ':' type)
        {
            LexLocation loc = $bindablePattern.pattern.getLocation();
            if ($expression.exp != null) {
                loc = extractLexLocation(loc, $expression.exp.getLocation());
                $binding = new ASetBind(loc, $bindablePattern.pattern, $expression.exp);
            } else if ($type.type != null) {
                loc = extractLexLocation(loc, $type.type.getLocation());
                $binding = new ATypeBind(loc, $bindablePattern.pattern, $type.type);
            } else {
                // FIXME Log a never-happens
            }
        }
    ;

// only used in seq comprehension
setBind returns[ASetBind binding]
    : bindablePattern 'in' 'set' expression
        {
            LexLocation loc = extractLexLocation($bindablePattern.pattern.getLocation(), $expression.exp.getLocation());
            $binding = new ASetBind(loc, $bindablePattern.pattern, $expression.exp);
        }
    ;

// only used by lambda, and could be a multi type bind
typeBindList returns[List<ATypeBind> bindings]
@init { $bindings = new ArrayList<ATypeBind>(); }
    : first=typeBind { $bindings.add($first.binding); }  ( ',' bindItem=typeBind { $bindings.add($bindItem.binding); } )*
    ;

typeBind returns[ATypeBind binding]
    : bindablePattern ':' type
        {
            LexLocation loc = extractLexLocation($bindablePattern.pattern.getLocation(), $type.type.getLocation());
            $binding = new ATypeBind(loc, $bindablePattern.pattern, $type.type);
        }
    ;

nameList returns[List<LexNameToken> names]
@init { $names = new ArrayList<LexNameToken>(); }
    : first=name { $names.add($first.name); } ( ',' nameItem=name { $names.add($nameItem.name); } )*
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

SELF
    : 'self' 
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
