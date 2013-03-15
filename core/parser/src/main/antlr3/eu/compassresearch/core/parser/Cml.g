/* A note to other editors of this file:
 * Please try to match the indentation and format of the code that's already here.
 * Thanks, -jwc
 */
/* loose threads:
 *
 * 2013-01-22 jwc:
 *  as noted by AKM, the fat enums only take identifiers at the
 *  moment, and should take general channel notation.
 *
 * 2013-01-18 jwc:
 *  Note that the '[(' ')>' used by the CSP timeout cause the same
 *  problems as below in expressions & types (i.e. the expression
 *  len(list)>max is a syntax error, sadly).
 *
 * 2013-01-06 RWL: )\ used in the /( exp )\-csp construct conflicts with apply expressions
 * when doing e.g. dom(map)\{set-enum}
 *
 * 2013-01-06 RWL: when having a class ... end construct the parser stops after the end.
 * anything written after the end of the class will not be parser.
 *   2013-02-11 jwc: can you confirm if this is still true?
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
 * Note: don't use '()' as a token: it will probably end up
 * conflicting with '(' ')' in places where there is an optional
 * something inside the brackets.
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
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;

import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;

import static org.overture.ast.lex.Dialect.VDM_PP;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.definitions.*;
import org.overture.ast.expressions.*;
import org.overture.ast.lex.*;
import org.overture.ast.node.*;
import org.overture.ast.node.tokens.TAsync;
import org.overture.ast.node.tokens.TStatic;
import org.overture.ast.patterns.*;
// import org.overture.ast.preview.*;
import org.overture.ast.statements.*;
import org.overture.ast.types.*;
import org.overture.ast.typechecker.NameScope;
// import org.overture.ast.util.*;
import org.overture.ast.typechecker.Pass;

import eu.compassresearch.ast.actions.*;
import eu.compassresearch.ast.declarations.*;
import eu.compassresearch.ast.definitions.*;
import eu.compassresearch.ast.expressions.*;
import eu.compassresearch.ast.patterns.*;
import eu.compassresearch.ast.process.*;
import eu.compassresearch.ast.program.*;
import eu.compassresearch.ast.types.*;

// for the main() method
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
}

// @lexer::members {
// }

@parser::members {
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
public String getTokenErrorDisplay(CommonToken t) {
    return t.toString();
}

protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
    throw new MismatchedTokenException(ttype, input);
}

public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
    throw e;
}

private DecimalFormat decimalFormatParser = new DecimalFormat();
public static final String CML_LANG_VERSION = "CML M16";

public static char convertEscapeToChar(String escape) {
    if (escape.startsWith("\\")) {
        switch (escape.charAt(1)) {
        case 'n':  return '\n';
        case '\\': return '\\';
        case 'r':  return '\r';
        case 't':  return '\t';
        case 'f':  return '\f';
        case 'e':  return (char) 0x001B;
        case 'a':  return (char) 0x0007;
        case 'x':  return (char) new BigInteger(escape.substring(2), 16).intValue();
        case 'u':  return (char) new BigInteger(escape.substring(2), 16).intValue();
        case 'c':  return (char) (escape.charAt(2) - 'A' + 1);
        case '0':  return (char) new BigInteger(escape.substring(2), 8).intValue();
        case '\"': return '\"';
        case '\'': return '\'';
        default:   throw new RuntimeException("Illegal escape sequence: " + escape);
        }
    }
    return escape.charAt(0);
}

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

public AAccessSpecifierAccessSpecifier getDefaultAccessSpecifier(boolean isStatic, boolean isAsync, LexLocation loc) {
    /* return new AAccessSpecifierAccessSpecifier(new APublicAccess(), */
    /* (isStatic ? new TStatic() : null), */
    /* (isAsync ? new TAsync() : null),loc); */
    return new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                               (isStatic ? new TStatic() : null),
                                               (isAsync ? new TAsync() : null));
}

public AAccessSpecifierAccessSpecifier getPrivateAccessSpecifier(boolean isStatic, boolean isAsync, LexLocation loc) {
    /* return new AAccessSpecifierAccessSpecifier(new APrivateAccess(), */
    /* (isStatic ? new TStatic() : null), */
    /* (isAsync ? new TAsync() : null),loc); */
    return new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                               (isStatic ? new TStatic() : null),
                                               (isAsync ? new TAsync() : null));
}

/* A main method to trigger the parser directly on stdin
 */
public static void main(String[] args) throws Exception {
    ANTLRInputStream stdin = new ANTLRInputStream(System.in);
    CmlLexer lexer = new CmlLexer(stdin);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CmlParser parser = new CmlParser(tokens);

    // parser.exprbase();
    try {
        Object o = parser.source();
        // parser.bareTest();
        // System.out.println(parser.exprbase());
    } catch(MismatchedTokenException e) {
        System.out.println("Mismatched Token");
        System.out.println("Received: " + e.token);
        if (e.expecting > 0 && e.expecting < CmlParser.tokenNames.length)
            System.out.println("Expected: " + CmlParser.tokenNames[e.expecting]);
        System.out.println("---- ---- ----");
        throw e;
    } catch(NoViableAltException e) {
        System.out.println("No Viable Alternative at " + e.token);
        System.out.println("Grammar decision description: " + e.grammarDecisionDescription);
        System.out.println("Grammar state number: " + e.stateNumber);
        System.out.println("---- ---- ----");
        throw e;
    } catch(RecognitionException e) {
        System.out.println("Something generating RecognitionException (or subclass) at " + e.token);
        System.out.println("---- ---- ----");
        throw e;
    }
}

} // end @parser::members

@rulecatch {
catch (RecognitionException e) {
    throw e;
}
}

// -------------------------------------------------------------

// bareTest
//     : process EOF
//     ;

source returns[List<PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : ( programParagraph { $defs.add($programParagraph.defs); } )+ EOF
    ;

programParagraph returns[PDefinition defs]
    : classDefinition   { $defs = $classDefinition.def; }
    | processDefinition { $defs = $processDefinition.def; }
    | channelDefs       { $defs = $channelDefs.defs; }
    | chansetDefs       { $defs = $chansetDefs.defs; }
    | typeDefs          { $defs = $typeDefs.defs; }
    | valueDefs         { $defs = $valueDefs.defs; }
    | functionDefs      { $defs = $functionDefs.defs; }
    ;

classDefinition returns[AClassDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : 'class' id=IDENTIFIER ('extends' parent=IDENTIFIER)? '=' 'begin' classDefinitionBlockOptList 'end'
        {
            /* FIXME --- Will need to check AInitialDefinition defs
             * that their id matches the class id here.
             */
            $def = new AClassDefinition(); // FIXME
            $def.setName(new LexNameToken("", $id.getText(), extractLexLocation($id)));
            /* FIXME --- need to set the parent's name once we've
             * settled on how that works
             */
            // $def.setParent(new LexNameToken("", $parent.getText(), extractLexLocation($parent)));
            $def.setBody($classDefinitionBlockOptList.defs);
        }
    ;

processDefinition returns[AProcessDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : 'process' IDENTIFIER '=' (parametrisationList '@')? process
        {
            $def = new AProcessDefinition(); // FIXME
            $def.setProcess( $process.proc );
            LexLocation identifierLocation = extractLexLocation($IDENTIFIER);
            LexNameToken processName = new LexNameToken("", new LexIdentifierToken($IDENTIFIER.getText(), false, identifierLocation));
            $def.setName(processName);
            List<PParametrisation> paramList = $parametrisationList.params;
            if (paramList != null) {
                List<PSingleDeclaration> localState = new LinkedList<PSingleDeclaration>();
                for(PParametrisation p : paramList)
                {
                    ATypeSingleDeclaration decl = p.getDeclaration();
                    localState.add(decl);
                }
                $def.setLocalState(localState);
            }

        }
    ;

process returns[PProcess proc]
@after { $proc.setLocation(extractLexLocation($start, $stop)); }
    : process0 processSuffix?
        {
            $proc = $process0.proc;
            PProcess suffix = $processSuffix.suffix;
            if (suffix != null) {
                if (suffix instanceof AStartDeadlineProcess)
                    ((AStartDeadlineProcess)suffix).setLeft($proc);
                else if (suffix instanceof AEndDeadlineProcess)
                    ((AEndDeadlineProcess)suffix).setLeft($proc);
                else
                    ((AHidingProcess)suffix).setLeft($proc);
                suffix.setLocation(extractLexLocation($start,$processSuffix.stop));
                $proc = suffix;
            }
        }
    | processReplicated 
        {
            $proc = $processReplicated.proc;
        }
    ;

processSuffix returns[PProcess suffix]
    : 'startsby' expression     { $suffix = new AStartDeadlineProcess(null, null, $expression.exp); }
    | 'endsby' expression       { $suffix = new AEndDeadlineProcess(null, null, $expression.exp);   }
    | '\\\\' varsetExpr         { $suffix = new AHidingProcess(null, null, $varsetExpr.vexp);       }
    ;

processReplicated returns[PProcess proc]
    : processReplOp replicationDeclarationList '@' repld=process
        {
            SReplicatedProcess srp = $processReplOp.op;
            srp.setReplicationDeclaration($replicationDeclarationList.rdecls);
            srp.setReplicatedProcess($repld.proc);
            $proc = srp;
        }
    | '||' replicationDeclarationList '@' ( '[' varsetExpr ']' )? repld=process
        {
            if ($varsetExpr.vexp != null)
                $proc = new AAlphabetisedParallelismReplicatedProcess(null, $replicationDeclarationList.rdecls, $repld.proc, $varsetExpr.vexp);
            else
                $proc = new ASynchronousParallelismReplicatedProcess(null, $replicationDeclarationList.rdecls, $repld.proc);
        }
    ;

processReplOp returns[SReplicatedProcess op]
    : ';'       { $op = new ASequentialCompositionReplicatedProcess(); }
    | '[]'      { $op = new AExternalChoiceReplicatedProcess(); }
    | '|~|'     { $op = new AInternalChoiceReplicatedProcess(); }
    | '|||'     { $op = new AInterleavingReplicatedProcess(); }
    | '[|' varsetExpr '|]'
        {
            AGeneralisedParallelismReplicatedProcess gprp = new AGeneralisedParallelismReplicatedProcess();
            gprp.setChansetExpression($varsetExpr.vexp);
            $op = gprp;
        }
    ;

process0 returns[PProcess proc]
    : process1 (';' right=process0)?
        {
            $proc = $process1.proc;
            if ($right.proc != null) {
                ASequentialCompositionProcess op = new ASequentialCompositionProcess();
                op.setLeft($proc);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process1 returns[PProcess proc]
    : process2 ('||' right=process1)?
        {
            $proc = $process2.proc;
            if ($right.proc != null) {
                ASynchronousParallelismProcess op = new ASynchronousParallelismProcess();
                op.setLeft($proc);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process2 returns[PProcess proc]
    : process3 ('[|' varsetExpr '|]' right=process2)?
        {
            $proc = $process3.proc;
            if ($right.proc != null) {
                AGeneralisedParallelismProcess op = new AGeneralisedParallelismProcess();
                op.setLeft($proc);
                op.setChansetExpression($varsetExpr.vexp);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process3 returns[PProcess proc]
    : process4 ('[' lcs=varsetExpr '||' rcs=varsetExpr ']' right=process3)?
        {
            $proc = $process4.proc;
            if ($right.proc != null) {
                AAlphabetisedParallelismProcess op = new AAlphabetisedParallelismProcess();
                op.setLeft($proc);
                op.setLeftChansetExpression($lcs.vexp);
                op.setRightChansetExpression($rcs.vexp);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process4 returns[PProcess proc]
    : process5 ('|||' right=process4)?
        {
            $proc = $process5.proc;
            if ($right.proc != null) {
                AInterleavingProcess op = new AInterleavingProcess();
                op.setLeft($proc);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process5 returns[PProcess proc]
    : process6 ('|~|' right=process5)?
        {
            $proc = $process6.proc;
            if ($right.proc != null) {
                AInternalChoiceProcess op = new AInternalChoiceProcess();
                op.setLeft($proc);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process6 returns[PProcess proc]
    : process7 ('[]' right=process6)?
        {
            $proc = $process7.proc;
            if ($right.proc != null) {
                AExternalChoiceProcess op = new AExternalChoiceProcess();
                op.setLeft($proc);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process7 returns[PProcess proc]
@after { $proc.setLocation(extractLexLocation($start, $stop)); }
    : process8 (process7op right=process7)?
        {
            $proc = $process8.proc;
            if ($right.proc != null) {
                PProcess op = $process7op.op;
                if (op instanceof AInterruptProcess) {
                    ((AInterruptProcess)op).setLeft($proc);
                    ((AInterruptProcess)op).setRight($right.proc);
                } else if (op instanceof ATimedInterruptProcess) {
                    ((ATimedInterruptProcess)op).setLeft($proc);
                    ((ATimedInterruptProcess)op).setRight($right.proc);
                }
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process7op returns[PProcess op]
    : '/\\'
        {
            $op = new AInterruptProcess();
        }
    | '/(' expression ')\\'
        {
            $op = new ATimedInterruptProcess();
            ((ATimedInterruptProcess)$op).setTimeExpression($expression.exp);
        }
    ;

process8 returns[PProcess proc]
    : processbase (process8op right=process8)?
        {
            $proc = $processbase.proc;
            if ($right.proc != null) {
                PProcess op = $process8op.op;
                if (op instanceof AUntimedTimeoutProcess) {
                    ((AUntimedTimeoutProcess)op).setLeft($proc);
                    ((AUntimedTimeoutProcess)op).setRight($right.proc);
                } else if (op instanceof ATimeoutProcess) {
                    ((ATimeoutProcess)op).setLeft($proc);
                    ((ATimeoutProcess)op).setRight($right.proc);
                }
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process8op returns[PProcess op]
    : '[>'
        {
            $op = new AUntimedTimeoutProcess();
        }
    | '[(' expression ')>'
        {
            $op = new ATimeoutProcess();
            ((ATimeoutProcess)$op).setTimeoutExpression($expression.exp);
        }
    ;

processbase returns[PProcess proc]
@after { $proc.setLocation(extractLexLocation($start, $stop)); }
    : 'begin' actionParagraphOptList '@' action 'end'
        {
            $proc = new AActionProcess(null, $actionParagraphOptList.defs, $action.action);
        }
    | '(' parametrisationList '@' process ')' '(' expressionList? ')'
        {
            $proc = new AInstantiationProcess(null, $parametrisationList.params, $process.proc, $expressionList.exps);
        }
    | IDENTIFIER
        ( '(' expressionList? ')' 
        | renamingExpr
        )?
        {
            $proc = new AReferenceProcess(null, new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER)), $expressionList.exps);
            if ($renamingExpr.rexp != null) {
                $proc.setLocation(extractLexLocation($IDENTIFIER));
                $proc = new AChannelRenamingProcess(null, $proc, $renamingExpr.rexp);
            }
        }
    | '(' process ')' renamingExpr?
        {
            $proc = $process.proc;
            if ($renamingExpr.rexp != null) {
                $proc = new AChannelRenamingProcess(null, $proc, $renamingExpr.rexp);
            }
        }
    ;

parametrisationList returns[List<PParametrisation> params]
@init { $params = new ArrayList<PParametrisation>(); }
    : item=parametrisation { $params.add($item.param); } ( ',' item=parametrisation { $params.add($item.param); } )*
    ;

parametrisation returns[PParametrisation param]
@after { $param.setLocation(extractLexLocation($start, $stop)); }
    : PMODE? identifierList ':' type
        {
            if ($PMODE==null || $PMODE.getText().equals("val")) {
                $param = new AValParametrisation();
            } else if ($PMODE.getText().equals("res")) {
                $param = new AResParametrisation();
            } else if ($PMODE.getText().equals("vres")) {
                $param = new AVresParametrisation();
            } else {
                // FIXME --- log a never-happens
            }
            LexLocation loc = extractLexLocation($identifierList.start, $identifierList.stop);
            $param.setDeclaration(new ATypeSingleDeclaration(loc, NameScope.GLOBAL, $identifierList.ids, $type.type));
        }
    ;

replicationDeclarationList returns[List<PSingleDeclaration> rdecls]
@init { $rdecls = new ArrayList<PSingleDeclaration>(); }
    : item=replicationDeclaration { $rdecls.add($item.rdecl); } ( ',' item=replicationDeclaration { $rdecls.add($item.rdecl); } )*
    ;

replicationDeclaration returns[PSingleDeclaration rdecl]
    : identifierList ( ':' type | 'in' 'set' expression )
        {
            if ($type.type != null)
                $rdecl = new ATypeSingleDeclaration(extractLexLocation($identifierList.stop), NameScope.GLOBAL, $identifierList.ids, $type.type);
            else
                $rdecl = new AExpressionSingleDeclaration(extractLexLocation($identifierList.stop), NameScope.GLOBAL, $identifierList.ids, $expression.exp);
        }
    ;

renamingExpr returns[SRenameChannelExp rexp]
@after { $rexp.setLocation(extractLexLocation($start, $stop)); }
    : '[[' renamePair
        ( ',' renamePairList
            {
                List<ARenamePair> pairs = $renamePairList.pairs;
                pairs.add(0,$renamePair.pair);
                $rexp = new AEnumerationRenameChannelExp(null, pairs);
            }
        | setMapExprBinding
            {
                $rexp = new AComprehensionRenameChannelExp(null, $renamePair.pair, $setMapExprBinding.bindings, $setMapExprBinding.pred);
            }
        )?
        ']]'
        {
            if ($rexp == null) {
                List<ARenamePair> pairs = new ArrayList<ARenamePair>();
                pairs.add($renamePair.pair);
                $rexp = new AEnumerationRenameChannelExp(null, pairs);
            }
        }
    ;

renamePairList returns[List<ARenamePair> pairs]
@init { $pairs = new ArrayList<ARenamePair>(); }
    : item=renamePair { $pairs.add($item.pair); } ( ',' item=renamePair { $pairs.add($item.pair); } )*
    ;

renamePair returns[ARenamePair pair]
    : fid=IDENTIFIER ( '.' fexp=expression )? '<-' tid=IDENTIFIER ( '.' texp=expression )?
        {
            // FIXME --- We really ought take #Channel out of the exp tree in the AST
            LexLocation floc = extractLexLocation($fid);
            ANameChannelExp fromExp = new ANameChannelExp(floc, new LexNameToken("", $fid.getText(), floc), $fexp.exp);
            if ($fexp.exp != null)
                fromExp.setLocation(extractLexLocation($fid,$fexp.stop));

            LexLocation tloc = extractLexLocation($tid);
            ANameChannelExp toExp = new ANameChannelExp(tloc, new LexNameToken("", $tid.getText(), tloc), $texp.exp);
            if ($texp.exp != null)
                toExp.setLocation(extractLexLocation($tid,$texp.stop));

            $pair = new ARenamePair(false, fromExp, toExp);
        }
    ;

actionParagraphOptList returns[List<PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : ( actionParagraph { $defs.add($actionParagraph.defs); } )*
    ;

actionParagraph returns[PDefinition defs]
    : typeDefs          { $defs = $typeDefs.defs; }
    | valueDefs         { $defs = $valueDefs.defs; }
    | stateDefs         { $defs = $stateDefs.defs; }
    | functionDefs      { $defs = $functionDefs.defs; }
    | operationDefs     { $defs = $operationDefs.defs; }
    | actionDefs        { $defs = $actionDefs.defs; }
    | namesetDefs       { $defs = $namesetDefs.defs; }
    ;

actionDefs returns[AActionsDefinition defs]
@after { $defs.setLocation(extractLexLocation($start, $stop)); }
    : 'actions' actionDefOptList
        {
            $defs = new AActionsDefinition();
            $defs.setActions($actionDefOptList.defs);
        }
    ;

actionDefOptList returns[List<AActionDefinition> defs]
@init { $defs = new ArrayList<AActionDefinition>(); }
    : ( actionDef { $defs.add($actionDef.def); } )*
    ;

actionDef returns[AActionDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : IDENTIFIER '=' (parametrisationList '@')? action
        {
            AActionDefinition adef = new AActionDefinition();
            adef.setName(new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER)));
            adef.setAction($action.action);
            adef.setDeclarations($parametrisationList.params);
            $def = adef;
        }
    ;

actionList returns[List<PAction> actions]
@init { $actions = new ArrayList<PAction>(); }
    : item=action { $actions.add($item.action); } ( ',' item=action { $actions.add($item.action); } )*
    ;

action returns[PAction action]
    : action_m10 actionSuffix?
        {
            $action = $action_m10.action;
            PAction suffix = $actionSuffix.suffix;
            if (suffix != null) {
                if (suffix instanceof AStartDeadlineAction)
                    ((AStartDeadlineAction)suffix).setLeft($action);
                else if (suffix instanceof AEndDeadlineAction)
                    ((AEndDeadlineAction)suffix).setLeft($action);
                else
                    ((AHidingAction)suffix).setLeft($action);
                suffix.setLocation(extractLexLocation($start,$actionSuffix.stop));
                $action = suffix;
            }
        }
    | actionReplicated  { $action = $actionReplicated.action; }
    ;

actionSuffix returns[PAction suffix]
    : 'startsby' expression     { $suffix = new AStartDeadlineAction(null, null, $expression.exp); }
    | 'endsby' expression       { $suffix = new AEndDeadlineAction(null, null, $expression.exp);   }
    | '\\\\' varsetExpr         { $suffix = new AHidingAction(null, null, $varsetExpr.vexp);       }
    ;

actionReplicated returns[PAction action]
    : actionSimpleReplOp replicationDeclarationList '@' repld=action
        {
            SReplicatedAction sra = $actionSimpleReplOp.op;
            sra.setReplicationDeclaration($replicationDeclarationList.rdecls);
            sra.setReplicatedAction($repld.action);
            $action = sra;
        }
    | actionSetReplOp replicationDeclarationList '@' '[' varsetExpr ']' repld=action
        {
            SReplicatedAction sra = $actionSetReplOp.op;
            sra.setReplicationDeclaration($replicationDeclarationList.rdecls);
            sra.setReplicatedAction($repld.action);
            if (sra instanceof AInterleavingReplicatedAction)
                ((AInterleavingReplicatedAction)sra).setNamesetExpression($varsetExpr.vexp);
            else if (sra instanceof AGeneralisedParallelismReplicatedAction)
                ((AGeneralisedParallelismReplicatedAction)sra).setNamesetExpression($varsetExpr.vexp);
            else
                System.err.println("FIXME --- log a never-happens as we just got a class that shouldn't be possible");
            $action = sra;
        }
    | '||' replicationDeclarationList '@' '[' ns=varsetExpr ( '|' cs=varsetExpr )? ']' repld=action
        {
            if ($cs.vexp != null) {
                AAlphabetisedParallelismReplicatedAction raction = new AAlphabetisedParallelismReplicatedAction();
                raction.setReplicationDeclaration($replicationDeclarationList.rdecls);
                raction.setNamesetExpression($ns.vexp);
                raction.setChansetExpression($cs.vexp);
                raction.setReplicatedAction($repld.action);
                $action = raction;
            } else {
                ASynchronousParallelismReplicatedAction raction = new ASynchronousParallelismReplicatedAction();
                raction.setReplicationDeclaration($replicationDeclarationList.rdecls);
                raction.setNamesetExpression($ns.vexp);
                raction.setReplicatedAction($repld.action);
                $action = raction;
            }
        }
    ;

actionSimpleReplOp returns[SReplicatedAction op]
    : ';'       { $op = new ASequentialCompositionReplicatedAction(); }
    | '[]'      { $op = new AExternalChoiceReplicatedAction(); }
    | '|~|'     { $op = new AInternalChoiceReplicatedAction(); }
    | '[||' varsetExpr '||]'
        {
            $op = new ACommonInterleavingReplicatedAction();
            ((ACommonInterleavingReplicatedAction)$op).setNamesetExpression($varsetExpr.vexp);
        }
    ;

actionSetReplOp returns[SReplicatedAction op]
    : '|||'     { $op = new AInterleavingReplicatedAction(); }
    | '[|' varsetExpr '|]'
        {
            $op = new AGeneralisedParallelismReplicatedAction();
            ((AGeneralisedParallelismReplicatedAction)$op).setChansetExpression($varsetExpr.vexp);
        }
    ;

action_m10 returns[PAction action]
    : action_m9 ('||' right=action_m10)?
        {
            $action = $action_m9.action;
            if ($right.action != null) {
                ASynchronousParallelismParallelAction op = new ASynchronousParallelismParallelAction();
                op.setLeftAction($action);
                op.setRightAction($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action_m9 returns[PAction action]
    : action_m8 (action_m9op right=action_m9)?
        {
            $action = $action_m8.action;
            if ($right.action != null) {
                SParallelAction op = $action_m9op.op;
                op.setLeftAction($action);
                op.setRightAction($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action_m9op returns[SParallelAction op]
    : '[' ln=varsetExpr
        ( '||' rn=varsetExpr
            {
                AAlphabetisedParallelismParallelAction appa = new AAlphabetisedParallelismParallelAction();
                appa.setLeftNamesetExpression($ln.vexp);
                appa.setRightNamesetExpression($rn.vexp);
                $op = appa;
            }
        | '|' lc=varsetExpr '||' rc=varsetExpr '|' rn=varsetExpr
            {
                AAlphabetisedParallelismParallelAction appa = new AAlphabetisedParallelismParallelAction();
                appa.setLeftNamesetExpression($ln.vexp);
                appa.setLeftChansetExpression($lc.vexp);
                appa.setRightChansetExpression($rc.vexp);
                appa.setRightNamesetExpression($rn.vexp);
                $op = appa;
            }
        )
        ']'
    | '[|' first=varsetExpr ( '|' second=varsetExpr ( '|' third=varsetExpr )? )? '|]'
        {
            if ($second.vexp != null && $third.vexp == null) {
                ASynchronousParallelismParallelAction sppa = new ASynchronousParallelismParallelAction();
                sppa.setLeftNamesetExpression($first.vexp);
                sppa.setRightNamesetExpression($second.vexp);
                $op = sppa;
            } else {
                AGeneralisedParallelismParallelAction gppa = new AGeneralisedParallelismParallelAction();
                if ($third.vexp != null) {
                    gppa.setLeftNamesetExpression($first.vexp);
                    gppa.setChansetExpression($second.vexp);
                    gppa.setRightNamesetExpression($third.vexp);
                } else if ($second.vexp != null) {
                    gppa.setLeftNamesetExpression($first.vexp);
                    gppa.setRightNamesetExpression($second.vexp);
                } else {
                    gppa.setChansetExpression($first.vexp);
                }
                $op = gppa;
            }
        }
    ;

action_m8 returns[PAction action]
    : action_m7 ('|||' right=action_m8)?
        {
            $action = $action_m7.action;
            if ($right.action != null) {
                AInterleavingParallelAction op = new AInterleavingParallelAction();
                op.setLeftAction($action);
                op.setRightAction($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action_m7 returns[PAction action]
    : action_m6 ('[||' lns=varsetExpr '|' rns=varsetExpr '||]' right=action_m7)?
        {
            $action = $action_m6.action;
            if ($right.action != null) {
                AInterleavingParallelAction op = new AInterleavingParallelAction();
                op.setLeftAction($action);
                op.setLeftNamesetExpression($lns.vexp);
                op.setRightNamesetExpression($rns.vexp);
                op.setRightAction($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action_m6 returns[PAction action]
    : action_m5 ('|~|' right=action_m6)?
        {
            $action = $action_m5.action;
            if ($right.action != null) {
                AInternalChoiceAction op = new AInternalChoiceAction();
                op.setLeft($action);
                op.setRight($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action_m5 returns[PAction action]
    : action_m4 ('[]' right=action_m5)?
        {
            $action = $action_m4.action;
            if ($right.action != null) {
                AExternalChoiceAction op = new AExternalChoiceAction();
                op.setLeft($action);
                op.setRight($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action_m4 returns[PAction action]
    : action_m3 (action_m4op right=action_m4)?
        {
            $action = $action_m3.action;
            if ($right.action != null) {
                PAction op = $action_m4op.op;
                if (op instanceof AInterruptAction) {
                    ((AInterruptAction)op).setLeft($action);
                    ((AInterruptAction)op).setRight($right.action);
                } else if (op instanceof ATimedInterruptAction) {
                    ((ATimedInterruptAction)op).setLeft($action);
                    ((ATimedInterruptAction)op).setRight($right.action);
                }
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action_m4op returns[PAction op]
    : '/\\'
        {
            $op = new AInterruptAction();
        }
    | '/(' expression ')\\'
        {
            $op = new ATimedInterruptAction();
            ((ATimedInterruptAction)$op).setTimeExpression($expression.exp);
        }
    ;

action_m3 returns[PAction action]
    : action_m2 (action_m3op right=action_m3)?
        {
            $action = $action_m2.action;
            if ($right.action != null) {
                PAction op = $action_m3op.op;
                if (op instanceof AUntimedTimeoutAction) {
                    ((AUntimedTimeoutAction)op).setLeft($action);
                    ((AUntimedTimeoutAction)op).setRight($right.action);
                } else if (op instanceof ATimeoutAction) {
                    ((ATimeoutAction)op).setLeft($action);
                    ((ATimeoutAction)op).setRight($right.action);
                }
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action_m3op returns[PAction op]
    : '[>'
        {
            $op = new AUntimedTimeoutAction();
        }
    | '[(' expression ')>'
        {
            $op = new ATimeoutAction();
            ((ATimeoutAction)$op).setTimeoutExpression($expression.exp);
        }
    ;

action_m2 returns[PAction action]
    : action_m1 (';' right=action_m2)?
        {
            $action = $action_m1.action;
            if ($right.action != null) {
                ASequentialCompositionAction op = new ASequentialCompositionAction();
                op.setLeft($action);
                op.setRight($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action_m1 returns[PAction action]
    : actionbase                { $action = $actionbase.action; }
    | specOrGuardedAction       { $action = $specOrGuardedAction.action; }
    | leadingIdAction           { $action = $leadingIdAction.action; }
    | prefixStatement           { $action = $prefixStatement.action; }
    ;

specOrGuardedAction returns[PAction action]
@after { $action.setLocation(extractLexLocation($start,$stop)); }
    : '['
        ( ('frame' frameSpecList)? ('pre' pre=expression)? 'post' post=expression ']'
            {
                $action = new ASpecificationStatementAction(null, $frameSpecList.frameSpecs, $pre.exp, $post.exp);
            }
        | guard=expression ']' '&' guarded=action_m1
            {
                $action = new AGuardedAction(null, $guard.exp, $guarded.action);
            }
        )
    ;

/* This is a horrible, horrible mess, but it seems to be the only way
 * to resolve the whole set of ambiguities between communications,
 * action calls, operation calls, and assignments, plus the allowing
 * renaming expressions to be parsed. -jwc/15Mar2013
 */
leadingIdAction returns[PAction action]
@after { $action.setLocation(extractLexLocation($start,$stop)); }
    : id=IDENTIFIER
        // bare action call
        {
            LexNameToken name = new LexNameToken("", $id.getText(), extractLexLocation($start));
            $action = new AReferenceAction(null, name, new ArrayList<PExp>());
        }
        ( renamingExpr
            // action call plus rename
            {
                $action = new AChannelRenamingAction(null, $action, $renamingExpr.rexp);
            }
        | ( ('.' IDENTIFIER)=>'.' ids+=IDENTIFIER )*
            // pure dotted prefix, if ids+ is nonempty, but I don't believe this is allowed as an action call
            ( communicationOptList '->' after=action_m1
                // communications
                {
                    // Grab the initial channel name...
                    LexIdentifierToken firstId = new LexIdentifierToken($id.getText(), false, extractLexLocation($id));

                    // ...then sort out the communicationList with
                    // respect to the dotted prefix.  Unfortunately,
                    // we need to lift these IDENTIFIERS to
                    // ASignalCommunicationParameters, then prefix
                    // them to the list of communication parameters.
                    // This means that we iterate over the list of
                    // dotted identifiers *backwards*, prepending to
                    // the list of comm params.  We assume that the
                    // comm params list exists (though it may be
                    // empty).  -jwc/15Mar2013
                    List<PCommunicationParameter> comms = $communicationOptList.comms;
                    if ($ids.size() > 0) {
                        ListIterator<Object> rIter = $ids.listIterator($ids.size());
                        while (rIter.hasPrevious()) {
                            CommonToken dotId = (CommonToken)rIter.previous();
                            LexLocation dotLoc = extractLexLocation(dotId);
                            LexNameToken dotName = new LexNameToken("", dotId.getText(), dotLoc);
                            comms.add(0, new ASignalCommunicationParameter(dotLoc, new AVariableExp(dotLoc, dotName, "")));
                        }
                    }
                    $action = new ACommunicationAction(null, firstId, comms, $after.action);
                }
            | selectorList? 
                ( // If the selectorList is a single AApplyExp, then
                  // this is a bare operation call; otherwise error
                  // and some RecognitionException will be thrown
                    {
                        // create raw operation call
                        // $action = $callStatement.statement;
                        List<PExp> selectors = $selectorList.selectors;
                        if (selectors.size() != 1 || ! (selectors.get(0) instanceof AApplyExp)) {
                            // through RecognitionException
                        }
                        // need to merge the first identifier and the dotted identifiers into a name
                        // need to extract the expression list from the AApplyExp
                        // $action = new ACallStatementAction(null, null, $ name.name, $ expressionList.exps);
                        $action = new ASkipAction(extractLexLocation($start));
                    }
                | ':='
                    // At this point we know that we have an assignableExpression to the left, so assemble that here
                    ( 'new' name '(' expressionList? ')'
                        // object instantiation
                        {
                            // sort out the assignableExpression equivalent
                            // $action = new ANewStatementAction(null, $ assignableExpression.exp, $name.name, $expressionList.exps);
                            $action = new ASkipAction(extractLexLocation($start));
                        }
                    | expression
                        // assignment or operation call
                        // | (assignableExpression ':=')=> assignmentStatement
                        {
                            // $action = $ assignmentStatement.statement;
                            $action = new ASkipAction(extractLexLocation($start));
                        }
                    )
                )
            )
        )
    ;

prefixStatement returns[PAction action]
@after { $action.setLocation(extractLexLocation($start, $stop)); }
    : 'let' localDefinitionList 'in' body=action_m1
        {
            $action = new ALetStatementAction(null, $body.action, $localDefinitionList.defs);
        }
    | ('if' expression 'then')=> 'if' test=expression 'then' th=action_m1
        ( ('elseif')=> elseIfStmtOptList )
        ( ('else')=> 'else' el=action_m1)?
        // need the ()=> to match elseif and else clauses greedily
        {
            $action = new AIfStatementAction(null, $test.exp, $th.action, $elseIfStmtOptList.elseifs, $el.action);
        }
    | 'if' nonDetStmtAltList 'end'
        {
            $action = new ANonDeterministicIfStatementAction(null, $nonDetStmtAltList.alts);
        }
    | 'do' nonDetStmtAltList 'end'
        {
            $action = new ANonDeterministicDoStatementAction(null, $nonDetStmtAltList.alts);
        }
    | 'cases' expression ':' caseStmtAltList (',' 'others' '->' action)? 'end'
        {
            $action = new ACasesStatementAction(null, $expression.exp, $caseStmtAltList.alts, $action.action);
        }
    | forStatement
        {
            $action = $forStatement.action;
        }
    | 'while' expression 'do' body=action_m1
        {
            $action = new AWhileStatementAction(null, $expression.exp, $body.action);
        }
    ;

elseIfStmt returns[AElseIfStatementAction elseif]
@after { $elseif.setLocation(extractLexLocation($start, $stop)); }
    : 'elseif' test=expression 'then' th=action_m1
        {
            $elseif = new AElseIfStatementAction(null, $test.exp, $th.action);
        }
    ;

forStatement returns[PAction action]
options { k=3; } // k=3 is sufficient to disambiguate these (longest: for ID =)
@after { $action.setLocation(extractLexLocation($start, $stop)); }
    : 'for' IDENTIFIER '=' start=expression 'to' end=expression ( 'by' step=expression )? 'do' body=action_m1
        {
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            $action = new AForIndexStatementAction(null, name, $start.exp, $end.exp, $step.exp, $body.action);
        }
    | 'for' 'all' bindablePattern 'in' 'set' expression 'do' body=action_m1
        {
            $action = new AForSetStatementAction(null, $bindablePattern.pattern, $expression.exp, $body.action);
        }
    | 'for' bindablePattern (':' type)? 'in' expression 'do' body=action_m1
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
            $action = new AForSequenceStatementAction(null, patternBind, $expression.exp, $body.action);
        }
    ;

actionbase returns[PAction action]
@after { $action.setLocation(extractLexLocation($start,$stop)); }
    : 'Skip'            { $action = new ASkipAction(); }
    | 'Stop'            { $action = new AStopAction(); }
    | 'Chaos'           { $action = new AChaosAction(); }
    | 'Div'             { $action = new ADivAction(); }
    | 'Wait' expression { $action = new AWaitAction(null, $expression.exp); }
    | ('return' expression)=>'return' expression
        {
            $action = new AReturnStatementAction(null,$expression.exp);
        }
    | 'return'
        {
            $action = new AReturnStatementAction();
        }
    | 'atomic' '(' assignmentStatementList ')'
        {
            $action = new AMultipleGeneralAssignmentStatementAction(null, $assignmentStatementList.statements);
        }
    | ('(' 'dcl')=> '(' 'dcl' assignmentDefinitionList '@' action ')'
        {
            LexLocation dloc = extractLexLocation($assignmentDefinitionList.start, $assignmentDefinitionList.stop);
            ADeclareStatementAction dcls = new ADeclareStatementAction(dloc, $assignmentDefinitionList.defs);
            $action = new ABlockStatementAction(null, dcls, $action.action);
        }
    | ('(' parametrisationList '@')=> pl='(' parametrisationList '@' action pr=')' ( '(' expressionList ')' )?
        {
            $action = new AParametrisedAction(null, $parametrisationList.params, $action.action);
            if ($expressionList.exps!=null) {
                $action.setLocation(extractLexLocation($pl, $pr));
                $action = new AParametrisedInstantiatedAction(null, (AParametrisedAction)$action, $expressionList.exps);
            }
        }
    | '(' action ')' renamingExpr?
        {
            // FIXME --- maybe add renaming expr here?
            $action = new ABlockStatementAction(null, null, $action.action);
        }
    | 'mu' identifierList '@' '(' actionList close=')'
        {
            $action = new AMuAction(extractLexLocation($start,$close), $identifierList.ids, $actionList.actions);
        }
    ;

communicationOptList returns[List<PCommunicationParameter> comms]
@init { $comms = new ArrayList<PCommunicationParameter>(); }
    : ( communication { $comms.add($communication.comm); } )*
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
 *
 * We read "a!x.y" as a(!x)(.y); if you wanted to communicate the
 * value of "x.y", you need to use "a!(x.y)"
 */
communication returns[PCommunicationParameter comm]
@after { $comm.setLocation(extractLexLocation($start, $stop)); }
    : (   '.' { $comm = new ASignalCommunicationParameter(); }
        | '!' { $comm = new AWriteCommunicationParameter(); }
        )
        ( id=IDENTIFIER       { $comm.setExpression(new AVariableExp(extractLexLocation($id), new LexNameToken("", $id.getText(), extractLexLocation($id)), "")); }
        | '(' expression ')'  { $comm.setExpression($expression.exp); }
        | symbolicLiteralExpr { $comm.setExpression($symbolicLiteralExpr.exp); }
        | recordTupleExprs    { $comm.setExpression($recordTupleExprs.exp); }
        )
    | '?' bindablePattern ( 'in' 'set' ( setMapExpr | '(' expression ')' ) )?
        {
            // FIXME --- I also need to add simple identifiers to the set of things you can 'in set' from
            if ($setMapExpr.exp != null)
                $comm = new AReadCommunicationParameter(null, $setMapExpr.exp, $bindablePattern.pattern);
            else if ($expression.exp != null)
                $comm = new AReadCommunicationParameter(null, $expression.exp, $bindablePattern.pattern);
            else
                $comm = new AReadCommunicationParameter(null, null, $bindablePattern.pattern);
        }
    ;

// statement returns[PAction statement]
// @after { $statement.setLocation(extractLexLocation($start, $stop)); }
//     : '[' ('frame' frameSpecList)? ('pre' pre=expression)? 'post' post=expression ']'
//         {
//             $statement = new ASpecificationStatementAction(null, $frameSpecList.frameSpecs, $pre.exp, $post.exp);
//         }
//     | ('return' expression)=>'return' expression
//         {
//             $statement = new AReturnStatementAction(null,$expression.exp);
//         }
//     | 'return'
//         {
//             $statement = new AReturnStatementAction();
//         }
//     | 'atomic' '(' assignmentStatementList ')'
//         {
//             $statement = new AMultipleGeneralAssignmentStatementAction(null, $assignmentStatementList.statements);
//         }
//     | ('(' 'dcl')=> '(' 'dcl' assignmentDefinitionList '@' action ')'
//         {
//             LexLocation dloc = extractLexLocation($assignmentDefinitionList.start, $assignmentDefinitionList.stop);
//             ADeclareStatementAction dcls = new ADeclareStatementAction(dloc, $assignmentDefinitionList.defs);
//             $statement = new ABlockStatementAction(null, dcls, $action.action);
//         }
//     | ('(' parametrisationList '@')=> pl='(' parametrisationList '@' action pr=')' ( '(' expressionList ')' )?
//         {
//             $statement = new AParametrisedAction(null, $parametrisationList.params, $action.action);
//             if ($expressionList.exps!=null) {
//                 $statement.setLocation(extractLexLocation($pl, $pr));
//                 $statement = new AParametrisedInstantiatedAction(null, (AParametrisedAction)$statement, $expressionList.exps);
//             }
//         }
//     | '(' action ')' renamingExpr?
//         {
//             // FIXME --- maybe add renaming expr here?
//             $statement = new ABlockStatementAction(null, null, $action.action);
//         }
//     | (assignableExpression ':=' 'new')=> assignableExpression ':=' 'new' name '(' expressionList? ')'
//         {
//             $statement = new ANewStatementAction(null, $assignableExpression.exp, $name.name, $expressionList.exps);
//         }
//     | (assignableExpression ':=')=> assignmentStatement
//         {
//             $statement = $assignmentStatement.statement;
//         }
//     | callStatement
//         {
//             $statement = $callStatement.statement;
//         }
//     ;

assignmentStatementList returns[List<ASingleGeneralAssignmentStatementAction> statements]
@init { $statements = new ArrayList<ASingleGeneralAssignmentStatementAction>(); }
    : item=assignmentStatement { $statements.add($item.statement); } ( ';' item=assignmentStatement { $statements.add($item.statement); } )+
    ;

assignmentStatement returns[ASingleGeneralAssignmentStatementAction statement]
@after { $statement.setLocation(extractLexLocation($start, $stop)); }
    : assignableExpression ':=' expression
        {
            $statement = new ASingleGeneralAssignmentStatementAction(null, $assignableExpression.exp, $expression.exp);
        }
    ;

assignableExpression returns[PExp exp]
    : ( t=SELF | t=IDENTIFIER ) selectorOptList
        {
            LexLocation loc = extractLexLocation($t);
            if ($IDENTIFIER != null)
                $exp = new AVariableExp(loc, new LexNameToken("", $t.getText(), loc), "");
            else
                $exp = new ASelfExp(loc, new LexNameToken("", $t.getText(), loc));

            for (PExp sel : $selectorOptList.selectors) { // Iterate through the selectors, building a left->right tree
                loc = extractLexLocation($exp.getLocation(), sel.getLocation());
                if (sel instanceof AFieldNumberExp) { // FIXME --- probably shouldn't allow this
                    System.out.println("Syntax error: AFieldNumberExp in a simpleSelector for calls");
                    ((AFieldNumberExp)sel).setTuple($exp);
                    sel.setLocation(loc);
                    $exp = sel;
                } else if (sel instanceof AApplyExp) {
                    ((AApplyExp)sel).setRoot($exp);
                    sel.setLocation(loc);
                    $exp = sel;
                } else if (sel instanceof ASubseqExp) { // FIXME --- probably shouldn't allow this either.
                    System.out.println("Syntax error: ASubseqExp in a simpleSelector for calls");
                    ((ASubseqExp)sel).setSeq($exp);
                    sel.setLocation(loc);
                    $exp = sel;
                } else if (sel instanceof AUnresolvedPathExp) {
                    // selector was a ".IDENTIFIER"; now let's figure out what to do
                    AUnresolvedPathExp aupe = (AUnresolvedPathExp)sel; // avoid many casts
                    if ($exp instanceof AVariableExp) { // if the left was an IDENTIFIER, then coalesce...
                        AVariableExp ave = (AVariableExp)$exp;
                        aupe.setLocation(loc);
                        aupe.getIdentifiers().add(0, ave.getName().getIdentifier());
                        $exp = aupe;
                    } else if ($exp instanceof AUnresolvedPathExp) { // if it was an unresolved path, still coalesce...
                        if ($exp != null) $exp.setLocation(loc);
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

/* Note: RWL and JWC have deliberately omitted the objectDesignator
 * part of this, electing instead to have the TC/Interp parse through
 * the name.
 */
callStatement returns[PAction statement]
@after { $statement.setLocation(extractLexLocation($start, $stop)); }
    : name '(' expressionList? ')'
        {
            $statement = new ACallStatementAction(null, null, $name.name, $expressionList.exps);
        }
    ;

// need the ()=> to match elseifs greedily
elseIfStmtOptList returns[List<AElseIfStatementAction> elseifs]
@init { $elseifs = new ArrayList<AElseIfStatementAction>(); }
    : ( ('elseif')=>elseIfStmt { $elseifs.add($elseIfStmt.elseif); } )*
//    | /* empty match; we want a null list if no elseifs */
    ;

caseStmtAltList returns[List<ACaseAlternativeAction> alts]
@init { $alts = new ArrayList<ACaseAlternativeAction>(); }
    : ( item=caseStmtAlt { $alts.add($item.alt); } ( ',' item=caseStmtAlt { alts.add($item.alt); } )* )?
    ;

caseStmtAlt returns[ACaseAlternativeAction alt]
@after { $alt.setLocation(extractLexLocation($start, $stop)); }
    : patternList '->' action
        {
            $alt = new ACaseAlternativeAction(null, $patternList.patterns, $action.action);
        }
    ;

localDefinitionList returns[List<PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : item=localDefinition { $defs.add($item.def); } ( ',' item=localDefinition { $defs.add($item.def); } )*
    ;

localDefinition returns[PDefinition def]
    : (valueDefinition)=> valueDefinition { $def = $valueDefinition.def; }
    | functionDefinition                  { $def = $functionDefinition.def; }
    ;

nonDetStmtAltList returns[List<ANonDeterministicAltStatementAction> alts]
@init { $alts = new ArrayList<ANonDeterministicAltStatementAction>(); }
    : item=nonDetStmtAlt { $alts.add($item.alt); } ( '|' item=nonDetStmtAlt { $alts.add($item.alt); } )*
    ;

nonDetStmtAlt returns[ANonDeterministicAltStatementAction alt]
@after { $alt.setLocation(extractLexLocation($start, $stop)); }
    : expression '->' action
        {
            $alt = new ANonDeterministicAltStatementAction(null, $expression.exp, $action.action);
        }
    ;

frameSpecList returns[List<AExternalClause> frameSpecs]
@init { $frameSpecs = new ArrayList<AExternalClause>(); }
    : ( item=frameSpec { $frameSpecs.add($item.frameSpec); } )+
    ;

frameSpec returns[AExternalClause frameSpec]
    : FRAMEMODE nameList (':' type)?
        {
            LexToken mode = new LexToken(extractLexLocation($FRAMEMODE), VDMToken.lookup($FRAMEMODE.getText(), VDM_PP));
            //new LexToken(, VDMToken.READ);
            $frameSpec = new AExternalClause(mode, $nameList.names, $type.type);
        }
    ;

/* Ok, this is cute.  It works both with and without semicolons,
 * though it may not be visually obvious (without the semis) that it
 * is a space that separates untyped channels from typed ones. (-jwc)
 */
channelDefs returns[AChannelsDefinition defs]
@after { $defs.setLocation(extractLexLocation($start, $stop)); }
    : 'channels' channelDefOptList
        {
            $defs = new AChannelsDefinition();//location, NameScope.GLOBAL, false, access, null/* Pass */, chanNameDecls);
            $defs.setNameScope(NameScope.GLOBAL);
            $defs.setUsed(false);
            $defs.setAccess(getDefaultAccessSpecifier(true, false, extractLexLocation($channelDefs.start)));
            $defs.setChannelNameDeclarations($channelDefOptList.defs);
        }
    ;

channelDefOptList returns[List<AChannelNameDefinition> defs]
@init { $defs = new ArrayList<AChannelNameDefinition>(); }
    : ( channelDef { $defs.add($channelDef.def); } )*
    ;

channelDef returns[AChannelNameDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop));
          if ($def.getType() == null)
            $def.setType(new AChannelType($def.getLocation(), true));
        }
    : identifierList (':' type)?
        {
            AChannelType channelType = new AChannelType();
            $def = new AChannelNameDefinition();//, false, null, null, singleTypeDeclaration);
            //$def.setName(??); // not sure if this needs set; one cml.y case has an empty LexNameToken, the other uses the first element of the identifierList
            $def.setNameScope(NameScope.GLOBAL);
            $def.setUsed(false);
            $def.setSingleType(new ATypeSingleDeclaration(extractLexLocation($identifierList.stop), NameScope.GLOBAL, $identifierList.ids, channelType));
            if ($type.type!= null)
              channelType.setType($type.type);
        }
    ;

chansetDefs returns[AChansetsDefinition defs]
@after { $defs.setLocation(extractLexLocation($start, $stop)); }
    : 'chansets' chansetDefOptList
        {
            $defs = new AChansetsDefinition();
            $defs.setNameScope(NameScope.GLOBAL);
            $defs.setUsed(false);
            $defs.setAccess(getDefaultAccessSpecifier(true, false, extractLexLocation($chansetDefs.start)));
            $defs.setChansets($chansetDefOptList.defs);
        }
    ;

chansetDefOptList returns[List<AChansetDefinition> defs]
@init { $defs = new ArrayList<AChansetDefinition>(); }
    : ( chansetDef { $defs.add($chansetDef.def); } )*
    ;

chansetDef returns [AChansetDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : IDENTIFIER '=' varsetExpr
        {
            $def = new AChansetDefinition();
            $def.setIdentifier(new LexIdentifierToken($IDENTIFIER.getText(), false, extractLexLocation($IDENTIFIER)));
            $def.setChansetExpression($varsetExpr.vexp);
        }
    ;

varsetExpr returns[PVarsetExpression vexp]
    : l=varsetExpr0 varsetExprTailOptList
        {
            $vexp = $varsetExpr0.vexp;
            for (SVOpVarsetExpression right : $varsetExprTailOptList.vexps) {
                LexLocation loc = extractLexLocation($vexp.getLocation(), right.getLocation());
                right.setLocation(loc);
                right.setLeft($vexp);
                $vexp = right;
            }
        }
    ;

varsetExprTailOptList returns[List<SVOpVarsetExpression> vexps]
@init { $vexps = new ArrayList<SVOpVarsetExpression>(); }
    : ( op='union' varsetExpr0
            {
                AUnionVOpVarsetExpression vexp = new AUnionVOpVarsetExpression();
                vexp.setLocation(extractLexLocation(extractLexLocation($varsetExprTailOptList.start), extractLexLocation($varsetExpr0.stop)));
                vexp.setOp(new LexNameToken("", $op.getText(), extractLexLocation($op)));
                vexp.setRight($varsetExpr0.vexp);
                $vexps.add(vexp);
            }
        )*
    ;

varsetExpr0 returns[PVarsetExpression vexp]
    : varsetExpr1 varsetExpr0TailOptList
        {
            $vexp = $varsetExpr1.vexp;
            for (SVOpVarsetExpression right : $varsetExpr0TailOptList.vexps) {
                LexLocation loc = extractLexLocation($vexp.getLocation(), right.getLocation());
                right.setLocation(loc);
                right.setLeft($vexp);
                $vexp = right;
            }
        }
    ;

varsetExpr0TailOptList returns[List<SVOpVarsetExpression> vexps]
@init { $vexps = new ArrayList<SVOpVarsetExpression>(); }
    : ( op='inter' varsetExpr1
            {
                AInterVOpVarsetExpression vexp = new AInterVOpVarsetExpression();
                vexp.setLocation(extractLexLocation(extractLexLocation($varsetExpr0TailOptList.start), extractLexLocation($varsetExpr1.stop)));
                vexp.setOp(new LexNameToken("", $op.getText(), extractLexLocation($op)));
                vexp.setRight($varsetExpr1.vexp);
                $vexps.add(vexp);
            }
        )*
    ;

varsetExpr1 returns[PVarsetExpression vexp]
    : varsetExprbase varsetExpr1TailOptList
        {
            $vexp = $varsetExprbase.vexp;
            for (SVOpVarsetExpression right : $varsetExpr1TailOptList.vexps) {
                LexLocation loc = extractLexLocation($vexp.getLocation(), right.getLocation());
                right.setLocation(loc);
                right.setLeft($vexp);
                $vexp = right;
            }
        }
    ;

varsetExpr1TailOptList returns[List<SVOpVarsetExpression> vexps]
@init { $vexps = new ArrayList<SVOpVarsetExpression>(); }
    : ( op='\\' varsetExprbase
            {
                ASubVOpVarsetExpression vexp = new ASubVOpVarsetExpression();
                vexp.setLocation(extractLexLocation(extractLexLocation($varsetExpr1TailOptList.start), extractLexLocation($varsetExprbase.stop)));
                vexp.setOp(new LexNameToken("", $op.getText(), extractLexLocation($op)));
                vexp.setRight($varsetExprbase.vexp);
                $vexps.add(vexp);
            }
        )*
    ;

varsetExprbase returns[PVarsetExpression vexp]
@after { $vexp.setLocation(extractLexLocation($start, $stop)); }
    : IDENTIFIER
        {
            LexLocation loc = extractLexLocation($IDENTIFIER);
            $vexp = new AIdentifierVarsetExpression(loc, new LexIdentifierToken($IDENTIFIER.getText(), false, loc));
        }
    | '(' varsetExpr ')'
        {
            $vexp = $varsetExpr.vexp;
        }
    | '{' ( identifierList )? '}'
        {
            List<LexIdentifierToken> ids = ($identifierList.ids!=null) ? $identifierList.ids : new ArrayList<LexIdentifierToken>();
            $vexp = new AEnumVarsetExpression(null, ids);
        }
    | '{|' ( identifierList )? '|}'
        {
            List<LexIdentifierToken> ids = ($identifierList.ids!=null) ? $identifierList.ids : new ArrayList<LexIdentifierToken>();
            $vexp = new AFatEnumVarsetExpression(null, ids);
        }
    | '{|' IDENTIFIER ('.' expression)? setMapExprBinding '|}'
        {
            // FIXME --- 2nd null below needs to be some combination of the IDENTIFIER and expression
            $vexp = new AFatCompVarsetExpression(null, null, $setMapExprBinding.bindings, $setMapExprBinding.pred);
        }
    ;

namesetDefs returns[ANamesetsDefinition defs]
    : 'namesets' namesetDefOptList
        {
            $defs = new ANamesetsDefinition();
            $defs.setNameScope(NameScope.GLOBAL);
            $defs.setUsed(false);
            $defs.setAccess(getDefaultAccessSpecifier(true, false, extractLexLocation($namesetDefs.start)));
            $defs.setNamesets($namesetDefOptList.defs);
        }
    ;

namesetDefOptList returns[List<ANamesetDefinition> defs]
@init { $defs = new ArrayList<ANamesetDefinition>(); }
    : ( namesetDef { $defs.add($namesetDef.def); } )*
    ;

namesetDef returns [ANamesetDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : IDENTIFIER '=' varsetExpr
        {
            $def = new ANamesetDefinition();
            $def.setIdentifier(new LexIdentifierToken($IDENTIFIER.getText(), false, extractLexLocation($IDENTIFIER)));
            $def.setNamesetExpression($varsetExpr.vexp);
        }
    ;

classDefinitionBlockOptList returns[List<PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : ( classDefinitionBlock { $defs.add($classDefinitionBlock.defs); } )*
    ;

classDefinitionBlock returns[PDefinition defs]
    : typeDefs                  { $defs = $typeDefs.defs; }
    | valueDefs                 { $defs = $valueDefs.defs; }
    | stateDefs                 { $defs = $stateDefs.defs; }
    | functionDefs              { $defs = $functionDefs.defs; }
    | operationDefs             { $defs = $operationDefs.defs; }
    | 'initial' operationDef
        {
            AInitialDefinition def = new AInitialDefinition();
            def.setOperationDefinition($operationDef.def);
            def.setLocation(extractLexLocation(extractLexLocation($classDefinitionBlock.start), $operationDef.def.getLocation()));
            $defs = def;
        }
    ;

valueDefs returns[AValuesDefinition defs]
@after { $defs.setLocation(extractLexLocation($start, $stop)); }
    : 'values' qualValueDefinitionList?
        {
            AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, extractLexLocation($valueDefs.start));
            $defs = new AValuesDefinition(null, NameScope.NAMES, false, access, null, $qualValueDefinitionList.defs);
        }
    ;

qualValueDefinitionList returns[List<AValueDefinition> defs]
@init { defs = new ArrayList<AValueDefinition>(); }
    : ( item=qualValueDefinition { $defs.add($item.def); } )+
    ;

qualValueDefinition returns[AValueDefinition def]
@init { $def = new AValueDefinition(); }
    : QUALIFIER? valueDefinition
        {

            $def = $valueDefinition.def;
            if ($def != null) {
              $def.setAccess(extractQualifier($QUALIFIER));
              LexLocation loc = extractLexLocation(extractLexLocation($qualValueDefinition.start), $qualValueDefinition.def.getLocation()) ;
              $def.setLocation(loc);
            }
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

stateDefs returns[AStateDefinition defs]
@after { $defs.setLocation(extractLexLocation($start, $stop)); }
    : 'state' instanceVariableDefinitionList?
        {
            $defs = new AStateDefinition();
            if ($instanceVariableDefinitionList.defs != null)
                $defs.setStateDefs($instanceVariableDefinitionList.defs);
        }
    ;

instanceVariableDefinitionList returns[List<PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : ( item=instanceVariableDefinition { $defs.add($item.def); } )+
    ;

instanceVariableDefinition returns[PDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : QUALIFIER? assignmentDefinition
        {
            $def = $assignmentDefinition.def;
            $def.setAccess(extractQualifier($QUALIFIER));
        }
    | invariantDefinition
        {
            $def = $invariantDefinition.def;
        }
    ;

assignmentDefinitionList returns[List<AAssignmentDefinition> defs]
@init { $defs = new ArrayList<AAssignmentDefinition>(); }
    : item=assignmentDefinition { $defs.add($item.def); } ( ',' item=assignmentDefinition { $defs.add($item.def); } )*
    ;

assignmentDefinition returns[AAssignmentDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : IDENTIFIER ':' type ( ( det=':=' | nondet='in' ) expression )?
        {
            $def = new AAssignmentDefinition();//null, name, NameScope.GLOBAL, false, null, null, type, null, null, null);
            $def.setName(new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER)));
            $def.setNameScope(NameScope.STATE);
            $def.setType($type.type);
            $def.setPass(Pass.VALUES);
            // FIXME --- It can't be right that both the ':=' and 'in'
            // forms produce exactly the same result (that is what
            // cml.y did, but we need to clarify this). -jwc/20Dec2012
            if ($det != null)
                $def.setExpression($expression.exp);
            else if ($nondet != null)
                $def.setExpression($expression.exp);
        }
    ;

invariantDefinition returns[AClassInvariantDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : 'inv' expression
        {
            $def = new AClassInvariantDefinition();
            $def.setNameScope(NameScope.GLOBAL);
            $def.setUsed(true);
            $def.setPass(Pass.DEFS);
            $def.setExpression($expression.exp);
        }
    ;

functionDefs returns[AFunctionsDefinition defs]
@after { $defs.setLocation(extractLexLocation($start, $stop));
         LexNameToken name = new LexNameToken("", new LexIdentifierToken("", false, $defs.getLocation()));
         $defs.setName(name );
         }
    : 'functions' qualFunctionDefinitionOptList
        {
            AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, extractLexLocation($functionDefs.start));
            AFunctionsDefinition functions = new AFunctionsDefinition(null, NameScope.GLOBAL, false, access, null, $qualFunctionDefinitionOptList.defs);
            $defs = functions;
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
@after { $def.setLocation(extractLexLocation($start, $stop)); }
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
    : ':' type IDENTIFIER parameterGroupList '==' functionBody ('pre' pre=expression )? ('post' post=expression)? ('measure' name)?
        {
            LexLocation location = extractLexLocation($IDENTIFIER);
            $tail = new AExplicitFunctionDefinition();

            $tail.setName(new LexNameToken("", $IDENTIFIER.getText(), location));
            $tail.setParamPatternList($parameterGroupList.pgroups);
            $tail.setBody($functionBody.exp);
            $tail.setIsUndefined(false);
            $tail.setRecursive(false);
            if ($pre.exp != null)
                $tail.setPrecondition($pre.exp);
            else
                $tail.setPrecondition(AstFactory.newABooleanConstExp(new LexBooleanToken(true, extractLexLocation($functionBody.stop))));
            if ($post.exp != null)
                $tail.setPostcondition($post.exp);
            else
                $tail.setPostcondition(AstFactory.newABooleanConstExp(new LexBooleanToken(true, extractLexLocation($functionBody.stop))));

            LexNameToken preDefName = new LexNameToken("", new LexIdentifierToken("pre_"+$tail.getName().getName(), false, location));
            AFunctionType preDefType = AstFactory.newAFunctionType(location, true /* partial */, ((AFunctionType)$type.type).getParameters(), AstFactory.newABooleanBasicType(location));

            $tail.setType($type.type);
            List<List<PPattern>> predefParamPatterns = new LinkedList<List<PPattern>>();
            for(List<PPattern> list : $parameterGroupList.pgroups)
            {
                List<PPattern> l = new LinkedList<PPattern>();
                for(PPattern p : list)
                {
                    l.add(p.clone());
                }
                predefParamPatterns.add(l);
            }

            AExplicitFunctionDefinition predef = AstFactory.newAExplicitFunctionDefinition(
                                                    preDefName, // name pre_fn
                                                    NameScope.LOCAL, // LOCAL ofcause
                                                    null,  // typeParams
                                                    preDefType,  // the type of the precondition
                                                    predefParamPatterns /* params */,
                                                    $tail.getPrecondition() /* body */,
                                                    null /* precond */,
                                                    null /* postcond */,
                                                    false /* type invariant */,
                                                    null /* measure */);

            $tail.setPredef(predef);

            List<List<PPattern>> postdefParamPatterns = new LinkedList<List<PPattern>>();
            for(List<PPattern> list : $parameterGroupList.pgroups)
            {
                List<PPattern> l = new LinkedList<PPattern>();
                for(PPattern p : list)
                    l.add(p.clone());
                postdefParamPatterns.add(l);
            }


            LexNameToken postDefName = new LexNameToken("", new LexIdentifierToken("post_"+$tail.getName().getName(), false, location));
            AFunctionType postDefType = AstFactory.newAFunctionType(location, true /* partial */, ((AFunctionType)$type.type).getParameters(), AstFactory.newABooleanBasicType(location));
            AExplicitFunctionDefinition postdef = AstFactory.newAExplicitFunctionDefinition(
                                                    postDefName, // name pre_fn
                                                    NameScope.LOCAL, // LOCAL ofcause
                                                    null,  // typeParams
                                                    postDefType,  // the type of the precondition
                                                    postdefParamPatterns /* params */,
                                                    $tail.getPostcondition() /* body */,
                                                    null /* precond */,
                                                    null /* postcond */,
                                                    false /* type invariant */,
                                                    null /* measure */);

            $tail.setPostdef(postdef);

            $tail.setIsCurried(false);
            $tail.setMeasure($name.name);
            $tail.setAccess(getPrivateAccessSpecifier(false, false, extractLexLocation($IDENTIFIER)));
            $tail.setPass(Pass.DEFS); // what's this for? RWL: The Overture type checker runs in three PASSes (TYPES, VALUES, DEFS)
            // in order to make defined types and values available for function definitions PASS for functinos must be DEFS. :)
        }
    ;

parameterGroupList returns[List<List<PPattern>> pgroups]
@init { $pgroups = new ArrayList<List<PPattern>>(); }
    : item=parameterGroup { $pgroups.add($item.pgroup); } ( item=parameterGroup { $pgroups.add($item.pgroup); } )*
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
    : '(' parameterTypeList? ')' resultTypeList ('pre' pre=expression )? 'post' post=expression
        {
            $tail = new AImplicitFunctionDefinition();
            $tail.setNameScope(NameScope.LOCAL);
            $tail.setUsed(Boolean.FALSE);
            $tail.setAccess(getDefaultAccessSpecifier(false,false,null));
            $tail.setRecursive(false);

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

            PExp preExp = $tail.getPrecondition();
            if ($pre.exp != null)
                preExp = $pre.exp;
            else
                preExp = AstFactory.newABooleanConstExp(new LexBooleanToken(true, extractLexLocation($resultTypeList.stop)));
            $tail.setPrecondition(preExp);

            $tail.setPostcondition($post.exp);

            // figure out the overall function type
            List<PType> paramTypes = new ArrayList<PType>();
            for (APatternListTypePair pp : paramPatterns)
                for(PPattern ptrn : pp.getPatterns())
                    paramTypes.add(pp.getType());
            LexLocation typeloc = extractLexLocation($implicitFunctionDefinitionTail.start, $resultTypeList.stop);
            $tail.setType(AstFactory.newAFunctionType(typeloc, true, paramTypes, resultTypePair.getType()));

            // set predef
            LexNameToken prename = new LexNameToken("", new LexIdentifierToken("pre_", false, preExp.getLocation()));
            NameScope prescope = NameScope.LOCAL;
            List<LexNameToken> pretypeParams = new LinkedList<LexNameToken>();
            AFunctionType pretype = $tail.getType();
            PExp preprecondition = null;
            PExp prepostcondition = null;
            List<List<PPattern>> preparameterGroupList = new LinkedList<List<PPattern>>();
            for(APatternListTypePair pt : paramPatterns)
            {
                List<PPattern> current = new LinkedList<PPattern>();
                for(PPattern p : pt.getPatterns())
                        current.add(p.clone());
                preparameterGroupList.add(current);
            }
            AExplicitFunctionDefinition predef =  AstFactory.newAExplicitFunctionDefinition(prename, prescope, pretypeParams, pretype, preparameterGroupList, preExp, preprecondition, prepostcondition, false, null);
            $tail.setPredef(predef);

            // set postdef
            LexNameToken name = new LexNameToken("", new LexIdentifierToken("post_", false, $post.exp.getLocation()));
            NameScope scope = NameScope.LOCAL;
            List<LexNameToken> typeParams = null;
            AFunctionType type = $tail.getType();
            PExp body = $tail.getPostcondition();
            PExp precondition = null;
            PExp postcondition = null;
            List<List<PPattern>> postParameterGroupList = new LinkedList<List<PPattern>>();
            for(APatternListTypePair pt : paramPatterns)
            {
                List<PPattern> current = new LinkedList<PPattern>();
                for(PPattern p : pt.getPatterns())
                        current.add(p.clone());
                postParameterGroupList.add(current);
            }
            AExplicitFunctionDefinition postdef =  AstFactory.newAExplicitFunctionDefinition(name, scope, typeParams, type, postParameterGroupList, body, precondition, postcondition, false, null);
            $tail.setPostdef(postdef);
        }
    ;

parameterTypeList returns[List<APatternListTypePair> ptypes]
@init { $ptypes = new ArrayList<APatternListTypePair>(); }
    : item=parameterTypeGroup { $ptypes.add($item.ptype); } ( ',' item=parameterTypeGroup { $ptypes.add($item.ptype); } )*
    ;

parameterTypeGroup returns[APatternListTypePair ptype]
    : bindablePatternList ':' type
        {
            $ptype = new APatternListTypePair(false, $bindablePatternList.patterns, $type.type);
        }
    ;

resultTypeList returns[List<APatternTypePair> rtypes]
@init { $rtypes = new ArrayList<APatternTypePair>(); }
    : item=resultType { $rtypes.add($item.rtype); } ( ',' item=resultType { $rtypes.add($item.rtype); } )*
    ;

resultType returns[APatternTypePair rtype]
    : IDENTIFIER ':' type
        {
            LexLocation loc = extractLexLocation($IDENTIFIER);
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), loc, false, true); // ?? why is the last boolean true?
            $rtype = new APatternTypePair(false, new AIdentifierPattern(loc, null, true, name, false), $type.type);
        }
    ;

functionBody returns[PExp exp]
@after { if ($exp != null) $exp.setLocation(extractLexLocation($start, $stop)); }
    : expression                       { $exp = $expression.exp; }
    | 'is' 'not' 'yet' 'specified'     { $exp = new ANotYetSpecifiedExp(); }
    | 'is' 'subclass' 'responsibility' { $exp = new ASubclassResponsibilityExp(); }
    ;

operationDefs returns[AOperationsDefinition defs]
@after { $defs.setLocation(extractLexLocation($start, $stop));
         $defs.setName(new LexNameToken("", new LexIdentifierToken("", false, $defs.getLocation())));

        }
    : 'operations' qualOperationDefOptList
        {
            $defs = new AOperationsDefinition(); // FIXME
            $defs.setOperations($qualOperationDefOptList.defs);
            $defs.setNameScope(NameScope.LOCAL);
            $defs.setUsed(false);
            $defs.setAccess(getDefaultAccessSpecifier(true, false, extractLexLocation($operationDefs.start)));

        }
    ;

qualOperationDefOptList returns[List<SCmlOperationDefinition> defs]
@init { $defs = new ArrayList<SCmlOperationDefinition>(); }
    : ( qualOperationDef { $defs.add($qualOperationDef.def); } )*
    ;

qualOperationDef returns[SCmlOperationDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : QUALIFIER? operationDef
        {
            $def = $operationDef.def;
            $def.setAccess(extractQualifier($QUALIFIER));
        }
    ;

operationDef returns[SCmlOperationDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : id=IDENTIFIER
        ( ':' opType IDENTIFIER parameterGroup '==' operationBody ('pre' pre=expression)? ('post' post=expression)?
            {
                // FIXME --- check that the IDENTIFIERs match and
                // throw a MismatchedTokenException (if that's the
                // right exception)
                AExplicitCmlOperationDefinition opdef = new AExplicitCmlOperationDefinition();
                opdef.setName(new LexNameToken("", $id.getText(), extractLexLocation($id)));
                opdef.setType($opType.type);
                opdef.setParameterPatterns($parameterGroup.pgroup);
                opdef.setBody($operationBody.body);
                opdef.setPrecondition($pre.exp);
                opdef.setPostcondition($post.exp);
                opdef.setNameScope(NameScope.GLOBAL);
                opdef.setAccess(getDefaultAccessSpecifier(true, false, extractLexLocation($id)));
                opdef.setIsConstructor(false);
                $def = opdef;
            }
        | '(' parameterTypeList? ')' resultTypeList? ('frame' frameSpecList )? ('pre' pre=expression)? ('post' post=expression)
            {
                AImplicitCmlOperationDefinition opdef = new AImplicitCmlOperationDefinition();
                opdef.setName(new LexNameToken("", $id.getText(), extractLexLocation($id)));
                opdef.setParameterPatterns($parameterTypeList.ptypes);
                opdef.setResult($resultTypeList.rtypes);
                opdef.setExternals($frameSpecList.frameSpecs);
                opdef.setPrecondition($pre.exp);
                opdef.setPostcondition($post.exp);
                opdef.setNameScope(NameScope.GLOBAL);
                opdef.setAccess(getDefaultAccessSpecifier(true, false, extractLexLocation($id)));
                $def = opdef;
            }
        )
    ;

opType returns[PType type]
@after { $type.setLocation(extractLexLocation($start, $stop)); }
    : ( dom=type0 | vdom='(' vdom2=')' ) '==>' ( rng=type0 | vrng='(' vrng2=')' )
        {
            PType domType = ($dom.type != null ? $dom.type : new AVoidType(extractLexLocation($vdom,$vdom2),true));
            PType rngType = ($rng.type != null ? $rng.type : new AVoidType(extractLexLocation($vrng,$vrng2),true));
            LexLocation loc = extractLexLocation(domType.getLocation(),rngType.getLocation());
            List<PType> typeList = new ArrayList<PType>();
			if (domType instanceof AProductType) {
				AProductType apt = (AProductType)domType;
				for(PType t : apt.getTypes()) typeList.add(t);
			}else {
				if (!(domType instanceof AVoidType))  
                   typeList.add(domType);
             }
            $type = new AOperationType(loc, false, null, typeList, rngType);
        }
    ;

operationBody returns[PAction body]
@after { $body.setLocation(extractLexLocation($start, $stop)); }
    : action                           { $body = $action.action; }
    | 'is' 'not' 'yet' 'specified'     { $body = new ASubclassResponsibilityAction(); }
    | 'is' 'subclass' 'responsibility' { $body = new ANotYetSpecifiedStatementAction(); }
    ;


typeDefs returns[PDefinition defs]
@init {
    List<ATypeDefinition> typeDefList = new ArrayList<ATypeDefinition>();
    ATypeDefinition last = null;
}
    : t='types' ( def=typeDef { last = $def.def; typeDefList.add(last); } )*
        {
            LexLocation loc = extractLexLocation($t);
            if (typeDefList.size()>0)
                loc = extractLexLocation(loc,last.getLocation());
            $defs = new ATypesDefinition(loc, NameScope.LOCAL, false,
                                         getDefaultAccessSpecifier(true, false, loc),
                                         Pass.TYPES, typeDefList);
            $defs.setName(new LexNameToken("", $t.getText(), loc));
        }
    ;

typeDef returns[ATypeDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : QUALIFIER? IDENTIFIER '=' type invariant?
        {
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            ANamedInvariantType invType = AstFactory.newANamedInvariantType(name, $type.type);
            $def = AstFactory.newATypeDefinition(name,invType,$invariant.pattern,$invariant.exp);
            $def.setAccess(extractQualifier($QUALIFIER));

        }
    | QUALIFIER? IDENTIFIER '::' fieldList invariant?
        {
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            ARecordInvariantType invType = AstFactory.newARecordInvariantType(name, $fieldList.fieldList);
            $def = AstFactory.newATypeDefinition(name,invType,$invariant.pattern,$invariant.exp);
            $def.setAccess(extractQualifier($QUALIFIER));
        }
    ;

type returns[PType type]
@init { boolean totalFuncType=false; }
@after { $type.setLocation(extractLexLocation($start, $stop)); }
    : dom=type0 (( '->' | '+>' { totalFuncType=true; } ) rng=type0)?
        {
            if ($rng.type == null) {
                $type = $dom.type;
            } else {
                List<PType> params = new ArrayList<PType>();
                params.add($dom.type);
                $type = new AFunctionType(null, false, null, totalFuncType, params, $rng.type);
            }
        }
    | unit='('')' ( '->' | '+>' { totalFuncType=true; } ) rng=type0
        {
            List<PType> params = new ArrayList<PType>();
            params.add(new AVoidType(extractLexLocation($unit), true));
            $type = new AFunctionType(null, false, null, totalFuncType, params, $rng.type);
        }
    ;

type0 returns[PType type]
@init { List<PType> typeList = new ArrayList<PType>(); LexLocation last = null; }
@after { $type.setLocation(extractLexLocation($start, $stop)); }
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
@after { $type.setLocation(extractLexLocation($start, $stop)); }
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
@after { $type.setLocation(extractLexLocation($start, $stop)); }
    : basicType           { $type = $basicType.basicType; }
    | '(' inside=type ')' { $type = $inside.type; }
    | '[' inside=type ']' { $type = new AOptionalType(null, false, null, $inside.type); }
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
    | 'set' 'of' sub=typebase                   { $type = new ASetType(null, false, null, $sub.type, false, false); }
    | 'seq' 'of' sub=typebase                   { $type = new ASeqSeqType(null, false, null, $sub.type, false); }
    | 'seq1' 'of' sub=typebase                  { $type = new ASeq1SeqType(null, false, null, $sub.type, false); }
    | 'map' from=type 'to' to=typebase      { $type = new AMapMapType(null, false, null, $from.type, $to.type, false); }
    | 'inmap' from=type 'to' to=typebase    { $type = new AInMapMapType(null, false, null, $from.type, $to.type, false); }
    | 'compose' IDENTIFIER 'of' fieldList 'end'
        {
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            $type = new ARecordInvariantType(null, false, null, false, null, name, $fieldList.fieldList, false);
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
    : ( item=field { $fieldList.add($item.field); } )+
    ;

field returns[AFieldField field]
@init { AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(false,false,null); }
    : type
        {
            LexLocation loc = $type.type.getLocation();
            LexNameToken name = new LexNameToken("", new LexIdentifierToken("",false,loc));
            $field = new AFieldField(null, name, null, $type.type, false);
        }
    | IDENTIFIER ( ':' | eqAbs=':-' ) type
        {
            String idStr = $IDENTIFIER.getText();
            LexNameToken name = new LexNameToken("", idStr, extractLexLocation($IDENTIFIER));
            $field = new AFieldField(access, name, idStr, $type.type, (eqAbs!=null));
        }
    ;

invariant returns[PPattern pattern, PExp exp]
    : 'inv' bindablePattern '==' expression { $pattern = $bindablePattern.pattern; $exp = $expression.exp; }
    ;

pattern returns[PPattern pattern]
    : bindablePattern { $pattern = $bindablePattern.pattern; }
    | matchValue      { $pattern = $matchValue.pattern; }
    ;

bindablePatternList returns[List<PPattern> patterns]
@init { $patterns = new ArrayList<PPattern>(); }
    : item=bindablePattern { $patterns.add($item.pattern); } ( ',' item=bindablePattern { $patterns.add($item.pattern); } )*
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
            LexNameToken name = new LexNameToken("", $IDENTIFIER.getText(), loc, false, true); // ?? why is the last bool true?
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
            $literal = new LexCharacterToken(convertEscapeToChar(res), loc);
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
            ARecordPattern recPattern = new ARecordPattern(loc, null, true, $name.name, patList);
            recPattern.setType(AstFactory.newAUnresolvedType($name.name));
            $pattern = recPattern;
        }
    ;

expressionList returns[List<PExp> exps]
@init { $exps = new ArrayList<PExp>(); }
    : item=expression { $exps.add($item.exp); } ( ',' item=expression { $exps.add($item.exp); } )*
    ;

expression returns[PExp exp]
@after { if ($exp != null) $exp.setLocation(extractLexLocation($start, $stop)); }
    : expr0
        {
            $exp = $expr0.exp;
        }
    ;

elseIfExprOptList returns[List<AElseIfExp> elseifs]
@init { $elseifs = new ArrayList<AElseIfExp>(); }
    : ( elseIfExpr { $elseifs.add($elseIfExpr.elseif); } )*
    ;

elseIfExpr returns[AElseIfExp elseif]
@after { $elseif.setLocation(extractLexLocation($start, $stop)); }
    : 'elseif' test=expression 'then' th=expression
        {
            $elseif = new AElseIfExp(null, $test.exp, $th.exp);
        }
    ;

caseExprAltList returns[List<ACaseAlternative> alts]
@init { $alts = new ArrayList<ACaseAlternative>(); }
    : item=caseExprAlt { $alts.addAll($item.alts); } ( ',' item=caseExprAlt { alts.addAll($item.alts); } )*
    ;

caseExprAlt returns[List<ACaseAlternative> alts]
@init { $alts = new ArrayList<ACaseAlternative>(); }
    : patternList '->' expression
        {
            LexLocation eloc = $expression.exp.getLocation();
            for (PPattern p : $patternList.patterns) {
                LexLocation loc = extractLexLocation(p.getLocation(), eloc);
                ACaseAlternative alt = new ACaseAlternative(loc, null, p, $expression.exp, null);
                $alts.add(alt);
            }
        }
    ;

patternList returns[List<PPattern> patterns]
@init { $patterns = new ArrayList<PPattern>(); }
    : item=pattern { $patterns.add($item.pattern); } ( ',' item=pattern { $patterns.add($item.pattern); } )*
    ;

expr0 returns[PExp exp]
    : e1=expr1 (('<=>')=>o='<=>' e2=expr0)?
        {
            if ($e2.exp == null)
                $exp = $e1.exp;
            else
                $exp = new AEquivalentBooleanBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                                       $e1.exp,
                                                       extractLexToken($o.getText(), extractLexLocation($o)),
                                                       $e2.exp);
        }
    ;

expr1 returns[PExp exp]
    : e1=expr2 (('=>')=>o='=>' e2=expr1)?
        {
            if ($e2.exp == null)
                $exp = $e1.exp;
            else
                $exp = new AImpliesBooleanBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                                    $e1.exp,
                                                    extractLexToken($o.getText(), extractLexLocation($o)),
                                                    $e2.exp);
        }
    ;

expr2 returns[PExp exp]
    : e1=expr3 (('or')=>o='or' e2=expr2)?
        {
            if ($e2.exp == null)
                $exp = $e1.exp;
            else
                $exp = new AOrBooleanBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                               $e1.exp,
                                               extractLexToken($o.getText(), extractLexLocation($o)),
                                               $e2.exp);
        }
    ;

expr3 returns[PExp exp]
    : e1=expr4 (('and')=>o='and' e2=expr3)?
        {
            if ($e2.exp == null)
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
    : e1=expr5 ((binOpRel)=>binOpRel e2=expr4)?
        {
            if ($e2.exp == null) {
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
    : e1=expr6 ((binOpEval1)=>binOpEval1 e2=expr5)?
        {
            if ($e2.exp == null) {
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
    : e1=expr7 ((binOpEval2)=>binOpEval2 e2=expr6)?
        {
            if ($e2.exp == null) {
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
    : e1=expr8 ((binOpEval3)=>binOpEval3 e2=expr7)?
        {
            if ($e2.exp == null) {
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
    : e1=expr9 ((binOpEval4)=>binOpEval4 e2=expr8)?
        {
            if ($e2.exp == null) {
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
    : e1=expr10 (('comp')=>o='comp' e2=expr9)?
        {
            if ($e2.exp == null)
                $exp = $e1.exp;
            else
                $exp = new ACompBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                          $e1.exp,
                                          extractLexToken($o.getText(), extractLexLocation($o)),
                                          $e2.exp);
        }
    ;

expr10 returns[PExp exp]
    : e1=expr11 (('**')=>o='**' e2=expr10)?
        {
            if ($e2.exp == null)
                $exp = $e1.exp;
            else
                $exp = new AStarStarBinaryExp(extractLexLocation($e1.exp,$e2.exp),
                                              $e1.exp,
                                              extractLexToken($o.getText(), extractLexLocation($o)),
                                              $e2.exp);
        }
    ;

unaryOp returns[SUnaryExp op]
@after { $op.setLocation(extractLexLocation($o)); }
    : o='+'       { $op = new AUnaryPlusUnaryExp(); }
    | o='-'       { $op = new AUnaryMinusUnaryExp(); }
    | o='abs'     { $op = new AAbsoluteUnaryExp(); }
    | o='floor'   { $op = new AFloorUnaryExp(); }
    | o='not'     { $op = new ANotUnaryExp(); }
    | o='card'    { $op = new ACardinalityUnaryExp(); }
    | o='power'   { $op = new APowerSetUnaryExp(); }
    | o='dunion'  { $op = new ADistUnionUnaryExp(); }
    | o='dinter'  { $op = new ADistIntersectUnaryExp(); }
    | o='hd'      { $op = new AHeadUnaryExp(); }
    | o='tl'      { $op = new ATailUnaryExp(); }
    | o='len'     { $op = new ALenUnaryExp(); }
    | o='elems'   { $op = new AElementsUnaryExp(); }
    | o='inds'    { $op = new AIndicesUnaryExp(); }
    | o='reverse' { $op = new AReverseUnaryExp(); }
    | o='conc'    { $op = new ADistConcatUnaryExp(); }
    | o='dom'     { $op = new AMapDomainUnaryExp(); }
    | o='rng'     { $op = new AMapRangeUnaryExp(); }
    | o='merge'   { $op = new ADistMergeUnaryExp(); }
    | o='inverse' { $op = new AMapInverseUnaryExp(); }
    ;

expr11 returns[PExp exp]
@init { List<PExp> selectors = new ArrayList<PExp>(); }
@after { if ($exp != null) $exp.setLocation(extractLexLocation($start, $stop)); }
    : unaryOp operand=expr11
        {
            SUnaryExp unaryop = $unaryOp.op;
            PExp target = $operand.exp;
            unaryop.setExp(target);
            $exp = unaryop;
        }
    | 'if' test=expression 'then' th=expression elseIfExprOptList 'else' el=expression
        {
            $exp = new AIfExp(null, $test.exp, $th.exp, $elseIfExprOptList.elseifs, $el.exp);
        }
    | 'let' localDefinitionList 'in' body=expression
        {
            $exp = new ALetDefExp(null, $localDefinitionList.defs, $body.exp);
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
    | exprbase selectorOptList //( selector { selectors.add($selector.exp); } )*
        {
            $exp = $exprbase.exp; // Set the leftmost root
            for (PExp sel : $selectorOptList.selectors) { // Iterate through the selectors, building a left->right tree
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
                        if ($exp != null) $exp.setLocation(loc);
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

selectorOptList returns[List<PExp> selectors]
@init { $selectors = new ArrayList<PExp>(); }
    : ( selector { $selectors.add($selector.exp); } )*
    ;

selectorList returns[List<PExp> selectors]
@init { $selectors = new ArrayList<PExp>(); }
    : ( selector { $selectors.add($selector.exp); } )+
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
@after { if ($exp != null) $exp.setLocation(extractLexLocation($start, $stop)); }
    : '(' expression ')'
        {
            $exp = new ABracketedExp(null, $expression.exp);
        }
    | SELF
        {
            LexLocation loc = extractLexLocation($SELF);
            LexNameToken name = new LexNameToken("", $SELF.getText(), loc);
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
    | symbolicLiteralExpr
        {
            $exp = $symbolicLiteralExpr.exp;
        }
    | eseq='[]'
        {
            LexLocation loc = extractLexLocation($eseq);
            $exp = new ASeqEnumSeqExp(loc, new ArrayList<PExp>());
        }
    | '[' seqExpr? ']'
        {
            if ($seqExpr.seqExpr == null)
                $exp = new ASeqEnumSeqExp(null, new ArrayList<PExp>());
            else
                $exp = $seqExpr.seqExpr;
        }
    | recordTupleExprs
        {
            $exp = $recordTupleExprs.exp;
        }
    | setMapExpr
        {
            $exp = $setMapExpr.exp;
        }
    | ISOFCLASS '(' name ',' expression ')'
        {
            $exp = new AIsOfClassExp(null, $name.name, $expression.exp);
        }
    | ISUNDER '(' expression ',' type ')'
        {
            $exp = new AIsExp(null, null, null, $type.type, $expression.exp, null);
        }
    | ISUNDER basicType '(' expression ')'
        {
            $exp = new AIsExp(null, null, null, $basicType.basicType, $expression.exp, null);
        }
    | ISUNDER name '(' expression ')'
        {
            $exp = new AIsExp(null, null, $name.name, null, $expression.exp, null);
        }
    | PREUNDER '(' func=expression ( ',' expr=expression { exps.add($expr.exp); } )* ')'
        {
            $exp = new APreExp(null, $func.exp, exps);
        }
    | 'cases' cexp=expression ':' caseExprAltList ( ',' 'others' '->' oexp=expression )? 'end'
        {
            $exp = new ACasesExp(null, $cexp.exp, $caseExprAltList.alts, $oexp.exp);
        }
    ;

symbolicLiteralExpr returns[PExp exp]
    : lit=symbolicLiteral
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
                LexLocation location = $lit.literal.location;
                $exp = new ANilExp(location);
                $exp.setType(AstFactory.newAUnknownType(location));
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

    ;

seqExpr returns[SSeqExp seqExpr]
@init { List<PExp> exps = new ArrayList<PExp>(); }
@after { $seqExpr.setLocation(extractLexLocation($start, $stop)); }
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
@after { if ($exp != null) $exp.setLocation(extractLexLocation($start, $stop)); }
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
@after { if ($exp != null) $exp.setLocation(extractLexLocation($start, $stop)); }
    : '{|->}'   { $exp = new AMapEnumMapExp(null, new ArrayList<AMapletExp>()); }
    | l='{' ( empty='|->' | setMapExprGuts )? r='}'
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
@after { if ($exp != null) $exp.setLocation(extractLexLocation($start, $stop)); }
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
@init { bindings = new ArrayList<PMultipleBind>(); }
    : item=multipleBind { $bindings.add($item.bindings); } ( ',' item=multipleBind { bindings.add($item.bindings); } )*
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
    : item=typeBind { $bindings.add($item.binding); }  ( ',' item=typeBind { $bindings.add($item.binding); } )*
    ;

typeBind returns[ATypeBind binding]
    : bindablePattern ':' type
        {
            LexLocation loc = extractLexLocation($bindablePattern.pattern.getLocation(), $type.type.getLocation());
            $binding = new ATypeBind(loc, $bindablePattern.pattern, $type.type);
        }
    ;

identifierList returns[List<LexIdentifierToken> ids]
@init { $ids = new ArrayList<LexIdentifierToken>(); }
    : item=IDENTIFIER { $ids.add(new LexIdentifierToken($item.getText(), false, extractLexLocation($item))); }
        ( ',' item=IDENTIFIER { $ids.add(new LexIdentifierToken($item.getText(), false, extractLexLocation($item))); } )*
    ;

nameList returns[List<LexNameToken> names]
@init { $names = new ArrayList<LexNameToken>(); }
    : item=name { $names.add($item.name); } ( ',' item=name { $names.add($item.name); } )*
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
    : INITIAL_LETTER | DIGIT | '\u0027' | '\u005f'
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
    : '\'\\\\\'' | '\'\\r\'' | '\'\\n\'' | '\'\\t\'' | '\'\\f\'' | '\'\\e\'' | '\'\\a\'' | '\'\\"' | '\'\\\'\''
    | '\'\\x' HEXDIGIT HEXDIGIT '\''
    | '\'\\u' HEXDIGIT HEXDIGIT HEXDIGIT HEXDIGIT '\''
    | '\'\\' OCTDIGIT OCTDIGIT OCTDIGIT '\''
    | '\'' . '\''
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
