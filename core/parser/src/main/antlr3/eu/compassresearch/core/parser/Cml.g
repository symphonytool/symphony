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
 * communication prefixes using '.' separators are still causing problems
 * -> restriction in place: '.','!' may only be followed by ids,
 *    (expr), symLit, and records/tuples; '?' is only followed by a
 *    bindablePattern and optionally a set/map expr that delimited by
 *    curlies
 *
 * new/call/assign/communicate are all in leadingIdAction --- it's a
 * mess, and gives a cryptic error on cases where there's a dotted
 * prefix followed by any list of selectors that's not just a single
 * AApplyExp.
 * ... We also still have an inherent conflict between opcalls and
 *   fncalls, as syntactically there's no difference in the basic
 *   cases.  Both are essentially ID '('expr*')'.  I've added "lhs
 *   '<-' call" as a rule alternative.
 *
 * Note: don't use '()' as a token: it will probably end up
 * conflicting with '(' ')' in places where there is an optional
 * something inside the brackets.
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
import eu.compassresearch.core.parser.CmlParserError;
}
@parser::header {
package eu.compassresearch.core.parser;

import java.lang.NumberFormatException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Vector;

import static org.overture.ast.lex.Dialect.VDM_PP;
import org.overture.ast.assistant.definition.PDefinitionAssistant;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.definitions.*;
import org.overture.ast.expressions.*;
import org.overture.ast.intf.lex.*;
import org.overture.ast.lex.LexBooleanToken;
import org.overture.ast.lex.LexCharacterToken;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexIntegerToken;
import org.overture.ast.lex.LexKeywordToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexQuoteToken;
import org.overture.ast.lex.LexRealToken;
import org.overture.ast.lex.LexStringToken;
import org.overture.ast.lex.LexToken;
import org.overture.ast.lex.VDMToken;
import org.overture.ast.node.*;
import org.overture.ast.node.tokens.TAsync;
import org.overture.ast.node.tokens.TStatic;
import org.overture.ast.patterns.*;
import org.overture.ast.statements.*;
import org.overture.ast.types.*;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.typechecker.ClassDefinitionSettings;

import eu.compassresearch.ast.*;
import eu.compassresearch.ast.actions.*;
import eu.compassresearch.ast.declarations.*;
import eu.compassresearch.ast.definitions.*;
import eu.compassresearch.ast.expressions.*;
import eu.compassresearch.ast.statements.*;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.patterns.*;
import eu.compassresearch.ast.process.*;
import eu.compassresearch.ast.types.*;

// for the main() method
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;



}



@lexer::members {

public String sourceFileName = "";
private List<CmlParserError> errors = new java.util.LinkedList<CmlParserError>();

@Override
public void displayRecognitionError(String[] tokenNames, RecognitionException e)
{
    //TODO see http://www.antlr.org/wiki/display/ANTLR3/Error+reporting+and+recovery
    //String hdr = getErrorHeader(e);
    String msg = getErrorMessage(e, tokenNames);
    errors.add(new CmlParserError(msg,e,sourceFileName,getLine(),getCharPositionInLine(),getCharIndex(),getCharIndex()));
}

public List<CmlParserError> getErrors() {
    return errors;
}

}

@parser::members {

private PAction stm2action(PStm stm)
{
	return new AStmAction((stm!=null?stm.getLocation():null),stm);
}

private PStm action2stm(PAction action)
{
	return new AActionStm((action!=null?action.getLocation():null),null,action);
}


private void configureClass(SClassDefinition c)
{
    /* FIXME --- Will need to check AInitialDefinition defs
    * that their id matches the class id here.
    */
    if(c.getDefinitions()!=null)
    {
        for (PDefinition p : c.getDefinitions())
        {
            p.parent(c);
            if(p instanceof AClassInvariantDefinition)
            {
                p.setName(c.getName().getInvName(p.getLocation()));
            }
            else if(p instanceof AInitialDefinition)
            {
                p.setName(new CmlLexNameToken("", c.getName().getName() + "_initial",p.getLocation()));
                ((AInitialDefinition)p).getOperationDefinition().setClassDefinition(c);
            }
            else
            {
                p.setClassDefinition(c);
            }

        }
    }

    // Classes are all effectively public types
    PDefinitionAssistant.setClassDefinition(c.getDefinitions(),c);
}

private List<CmlParserError> errors = new java.util.LinkedList<CmlParserError>();

@Override
public void displayRecognitionError(String[] tokenNames, RecognitionException e)
{
    //TODO see http://www.antlr.org/wiki/display/ANTLR3/Error+reporting+and+recovery
    //String hdr = getErrorHeader(e);
    String msg = getErrorMessage(e, tokenNames);
    if (e.token == null) {
        errors.add(new CmlParserError( msg, e,sourceFileName, e.line, e.charPositionInLine, e.index, e.index));
    } else {
        errors.add(new CmlParserError( msg, e,sourceFileName, (CommonToken)e.token));
    }
}

public List<CmlParserError> getErrors() {
    return errors;
}

public String sourceFileName = "";

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
    return /*stack+" "+*/msg;
}
public String getTokenErrorDisplay(CommonToken t) {
    return t.toString();
}

//protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
//    throw new MismatchedTokenException(ttype, input);
//}

//public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
//    throw e;
//}

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

private ILexToken extractLexToken(String str, ILexLocation loc) {
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

private ILexLocation extractLexLocation(CommonToken token) {
    String text = token.getText();
    int len = text.length();
    int line = token.getLine();
    int pos = token.getCharPositionInLine();
    int offset = token.getStartIndex();
    return new LexLocation(this.sourceFileName,
                           "",
                           line, //start line
                           pos, //start column
                           line, //end line (FIXME?)
                           pos+len, //end column
                           offset, //absolute start offset
                           offset+len/*is it on purpose that the StopIndex is not used here*/); //absolute end offset
}

private ILexLocation extractLexLocation(CommonToken start, CommonToken end) {
    int sline = start.getLine();
    int eline = end.getLine();
    int spos = start.getCharPositionInLine();
    int epos = end.getCharPositionInLine() + end.getText().length();
    int soffset = start.getStartIndex();
    int eoffset = end.getStopIndex();
    return new LexLocation(this.sourceFileName,
                           "",
                           sline, spos,
                           eline, epos,
                           soffset, eoffset);
}

public ILexLocation extractLexLocation(PExp start, PExp end) {
    return extractLexLocation(start.getLocation(),end.getLocation());
}

public ILexLocation extractLexLocation(ILexLocation start, ILexLocation end) {
    return new LexLocation(start.getFile(), "",
                           start.getStartLine(), start.getStartPos(),
                           end.getEndLine(), end.getEndPos(),
                           start.getStartOffset(), end.getEndOffset());
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

public AAccessSpecifierAccessSpecifier getDefaultAccessSpecifier(boolean isStatic, boolean isAsync, ILexLocation loc) {
    /* return new AAccessSpecifierAccessSpecifier(new APublicAccess(), */
    /* (isStatic ? new TStatic() : null), */
    /* (isAsync ? new TAsync() : null),loc); */
    return new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                               (isStatic ? new TStatic() : null),
                                               (isAsync ? new TAsync() : null));
}

public AAccessSpecifierAccessSpecifier getPrivateAccessSpecifier(boolean isStatic, boolean isAsync, ILexLocation loc) {
    /* return new AAccessSpecifierAccessSpecifier(new APrivateAccess(), */
    /* (isStatic ? new TStatic() : null), */
    /* (isAsync ? new TAsync() : null),loc); */
    return new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                               (isStatic ? new TStatic() : null),
                                               (isAsync ? new TAsync() : null));
}

private PExp selectorListAssignableBuilder(PExp base, List<PExp> selectors) {
    PExp tree = base;

    for (PExp sel : selectors) { // Iterate through the selectors, building a left->right tree
        ILexLocation loc = extractLexLocation(base.getLocation(), sel.getLocation());
        if (sel instanceof AFieldNumberExp) { // FIXME --- probably shouldn't allow this
            ((AFieldNumberExp)sel).setTuple(tree);
            sel.setLocation(loc);
            tree = sel;
            throw new java.lang.RuntimeException("Syntax error: AFieldNumberExp in a simpleSelector for calls");

        } else if (sel instanceof AApplyExp) {
            ((AApplyExp)sel).setRoot(tree);
            sel.setLocation(loc);
            tree = sel;

        } else if (sel instanceof ASubseqExp) { // FIXME --- probably shouldn't allow this either.
            System.out.println("Syntax error: ASubseqExp in a simpleSelector for calls");
            ((ASubseqExp)sel).setSeq(base);
            sel.setLocation(loc);
            tree = sel;
        } else if (sel instanceof AUnresolvedPathExp) {
            // selector was a ".IDENTIFIER"; now let's figure out what to do
            AUnresolvedPathExp aupe = (AUnresolvedPathExp)sel; // avoid many casts
            if (tree instanceof AVariableExp) { // if the left was an IDENTIFIER, then coalesce...
                AVariableExp ave = (AVariableExp)tree;
                aupe.setLocation(loc);
                aupe.getIdentifiers().add(0, ave.getName().getIdentifier());
                tree = aupe;
            } else if (tree instanceof AUnresolvedPathExp) { // if it was an unresolved path, still coalesce...
                if (tree != null)
                    tree.setLocation(loc);
                ((AUnresolvedPathExp)tree).getIdentifiers().addAll(aupe.getIdentifiers());
                // no need to assign to tree here
            } else { // otherwise it must be a field access
                // the AUnresolvedPathExp we get from the list will
                // only have a single Identifier in it.
                ILexIdentifierToken id = aupe.getIdentifiers().get(0);
                tree = AstFactory.newAFieldExp(tree,id);
            }
        }
    }

    return tree;
}

private Collection<? extends PType> getTypeList(APatternListTypePair node) {
    List<PType> list = new Vector<PType>();

    for (int i = 0; i < node.getPatterns().size(); i++) {
        PType type = (PType) node.getType();
        list.add(type);
    }

    return list;
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

    System.out.println("Lexer Errors:");
    for (CmlParserError e : lexer.getErrors()) {
        System.out.println(e);
        System.out.println("---- ---- ----");
    }
    System.out.println("Parser Errors:");
    for (CmlParserError e : parser.getErrors()) {
        System.out.println(e);
        System.out.println("---- ---- ----");
    }
}

} // end @parser::members

@rulecatch {
catch (RecognitionException re) {
    reportError(re);
    //recover(input,re);
    throw re;
}
}

// -------------------------------------------------------------

// bareTest
//     : process EOF
//     ;

source returns[List<PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : ( programParagraph { $defs.addAll($programParagraph.defs); } )* EOF
    ;

programParagraph returns[List<? extends PDefinition> defs]
    : classDefinition   { $defs = Arrays.asList(new PDefinition[]{$classDefinition.def}); }
    | processDefinition { $defs = Arrays.asList(new PDefinition[]{$processDefinition.def}); }
    | channelDefs       { $defs = $channelDefs.defs; }
    | chansetDefs       { $defs = $chansetDefs.defs; }
    | typeDefs          { $defs = $typeDefs.defs; }
    | valueDefs         { $defs = $valueDefs.defs; }
    | functionDefs      { $defs = $functionDefs.defs; }
    | configDefinition  { $defs = new ArrayList<PDefinition>(); } // FIXME: Arrays.asList(new PDefinition[]{$configDefinition.def}); }
    ;

configDefinition //returns[AConfigDefinition def]
    : 'configuration' IDENTIFIER 'includes' identifierList 'end'
    ;

classDefinition returns[AClassClassDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : 'class' id=IDENTIFIER ('extends' parent=IDENTIFIER)? '=' 'begin' classDefinitionBlockOptList 'end'
        {
            AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(false, false, extractLexLocation($start));
            CmlLexNameToken className = new CmlLexNameToken("CLASS", $id.getText(), extractLexLocation($id));
            List<PDefinition> members = $classDefinitionBlockOptList.defs;
            List<ILexNameToken> superclasses = new ArrayList<ILexNameToken>();

            if($parent!=null)
            {
                CmlLexNameToken superClassName = new CmlLexNameToken("CLASS", $parent.getText(), extractLexLocation($parent));
                superclasses.add(superClassName);
            }

            AClassClassDefinition cDef = AstFactory.newAClassClassDefinition(className, superclasses, members);
            configureClass(cDef);

            $def = cDef;
        }
    ;

processDefinition returns[AProcessDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : 'process' IDENTIFIER '=' (parametrisationList '@')? process
        {
            $def = new AProcessDefinition(); // FIXME
            $def.setProcess( $process.proc );
            ILexLocation identifierLocation = extractLexLocation($IDENTIFIER);
            CmlLexNameToken processName = new CmlLexNameToken("", new LexIdentifierToken($IDENTIFIER.getText(), false, identifierLocation));
            $def.setName(processName);
            List<PParametrisation> paramList = $parametrisationList.params;
            if (paramList != null) {
                $def.setLocalState(paramList);
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
    | ';' seqReplicationDeclarationList '@' repld=process
        {
            ASequentialCompositionReplicatedProcess ascrp = new ASequentialCompositionReplicatedProcess();
            ascrp.setReplicationDeclaration($seqReplicationDeclarationList.rdecls);
            ascrp.setReplicatedProcess($repld.proc);
            $proc = ascrp;
        }
    | '||' replicationDeclarationList '@' '[' varsetExpr ']' repld=process
        {
            $proc = new AAlphabetisedParallelismReplicatedProcess(null, $replicationDeclarationList.rdecls, $repld.proc, $varsetExpr.vexp);
        }
    ;

processReplOp returns[SReplicatedProcess op]
    : '[]'      { $op = new AExternalChoiceReplicatedProcess(); }
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
    : process1 ('[|' varsetExpr '|]' right=process0)?
        {
            $proc = $process1.proc;
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

process1 returns[PProcess proc]
    : process2 ('[' lcs=varsetExpr '||' rcs=varsetExpr ']' right=process1)?
        {
            $proc = $process2.proc;
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

process2 returns[PProcess proc]
    : process3 ('|||' right=process2)?
        {
            $proc = $process3.proc;
            if ($right.proc != null) {
                AInterleavingProcess op = new AInterleavingProcess();
                op.setLeft($proc);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process3 returns[PProcess proc]
    : process4 ('|~|' right=process3)?
        {
            $proc = $process4.proc;
            if ($right.proc != null) {
                AInternalChoiceProcess op = new AInternalChoiceProcess();
                op.setLeft($proc);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process4 returns[PProcess proc]
    : process5 ('[]' right=process4)?
        {
            $proc = $process5.proc;
            if ($right.proc != null) {
                AExternalChoiceProcess op = new AExternalChoiceProcess();
                op.setLeft($proc);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

process5 returns[PProcess proc]
@after { $proc.setLocation(extractLexLocation($start, $stop)); }
    : process6 (process5op right=process5)?
        {
            $proc = $process6.proc;
            if ($right.proc != null) {
                PProcess op = $process5op.op;
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

process5op returns[PProcess op]
    : '/_\\'
        {
            $op = new AInterruptProcess();
        }
    | '/_' expression '_\\'
        {
            $op = new ATimedInterruptProcess();
            ((ATimedInterruptProcess)$op).setTimeExpression($expression.exp);
        }
    ;

process6 returns[PProcess proc]
    : process7 (process6op right=process6)?
        {
            $proc = $process7.proc;
            if ($right.proc != null) {
                PProcess op = $process6op.op;
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

process6op returns[PProcess op]
    : '[_>'
        {
            $op = new AUntimedTimeoutProcess();
        }
    | '[_' expression '_>'
        {
            $op = new ATimeoutProcess();
            ((ATimeoutProcess)$op).setTimeoutExpression($expression.exp);
        }
    ;

process7 returns[PProcess proc]
    : processbase (';' right=process7)?
        {
            $proc = $processbase.proc;
            if ($right.proc != null) {
                ASequentialCompositionProcess op = new ASequentialCompositionProcess();
                op.setLeft($proc);
                op.setRight($right.proc);
                op.setLocation(extractLexLocation($start,$right.stop));
                $proc = op;
            }
        }
    ;

processbase returns[PProcess proc]
@after { $proc.setLocation(extractLexLocation($start, $stop)); }
    : beginT='begin' actionParagraphOptList atT='@' action endT='end'
        {
            List<PDefinition> members = $actionParagraphOptList.defs;

            SClassDefinition cDef = CmlAstFactory.newAActionClassDefinition(extractLexLocation($beginT,$atT), members);
            configureClass(cDef);

            $proc = new AActionProcess(extractLexLocation($beginT,$endT), cDef, $action.action);
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
            $proc = new AReferenceProcess(null, new CmlLexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER)), $expressionList.exps);
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
    : item=parametrisation { $params.addAll($item.params); } ( ',' item=parametrisation { $params.addAll($item.params); } )*
    ;

parametrisation returns[List<PParametrisation> params]
@init { $params = new ArrayList<PParametrisation>(); }
    : PMODE? identifierList ':' type
        {
            ILexLocation ploc = ($PMODE!=null?extractLexLocation($PMODE, $type.stop):extractLexLocation($identifierList.start, $type.stop));

            ILexLocation loc = extractLexLocation($identifierList.start, $identifierList.stop);

            //List<ALocalDefinition> locals = new Vector<ALocalDefinition>();

            for (ILexIdentifierToken id : $identifierList.ids)
            {
                CmlLexNameToken idName = new CmlLexNameToken("", id);
                ALocalDefinition def = AstFactory.newALocalDefinition(loc, idName, NameScope.LOCAL, $type.type);
                //locals.add(def);
                PParametrisation param = null;

                if ($PMODE==null || $PMODE.getText().equals("val"))
                {
                    param = new AValParametrisation();
                } else if ($PMODE.getText().equals("res"))
                {
                    param = new AResParametrisation();
                } else if ($PMODE.getText().equals("vres"))
                {
                    param = new AVresParametrisation();
                } else
                {
                // FIXME --- log a never-happens
                }
                param.setDeclaration(def);
                param.setLocation(ploc);
                params.add(param);
            }


            //$param.setDeclaration(locals);//new ATypeSingleDeclaration(loc, NameScope.GLOBAL, $identifierList.ids, $type.type));
        }
    ;

replicationDeclarationList returns[List<PSingleDeclaration> rdecls]
@init { $rdecls = new ArrayList<PSingleDeclaration>(); }
    : item=replicationDeclaration { $rdecls.addAll($item.rdecls); } ( ',' item=replicationDeclaration { $rdecls.addAll($item.rdecls); } )*
    ;

replicationDeclaration returns[List<PSingleDeclaration> rdecls]
@init { $rdecls = new ArrayList<PSingleDeclaration>(); }
    : identifierList ( ':' type | 'in' 'set' expression )
        {
            ILexLocation loc = extractLexLocation($identifierList.stop);
            $rdecls.addAll(CmlAstFactory.generateSingleTypeDeclerations(loc,$identifierList.ids,$type.type,$expression.exp));
            /*ILexLocation loc = extractLexLocation($identifierList.stop);
            if ($type.type != null)
            {
                for(ILexIdentifierToken id: $identifierList.ids)
                {
                    $rdecls.add(new ATypeSingleDeclaration(loc, NameScope.GLOBAL, id, $type.type));
                }
            }
            else
            {
                $rdecls.add(new AExpressionSingleDeclaration(loc, NameScope.GLOBAL, $identifierList.ids, $expression.exp));
            }*/
        }
    ;

seqReplicationDeclarationList returns[List<PSingleDeclaration> rdecls]
@init { $rdecls = new ArrayList<PSingleDeclaration>(); }
    : item=seqReplicationDeclaration { $rdecls.addAll($item.rdecls); } ( ',' item=seqReplicationDeclaration { $rdecls.addAll($item.rdecls); } )*
    ;

seqReplicationDeclaration returns[List<PSingleDeclaration> rdecls]
@init { $rdecls = new ArrayList<PSingleDeclaration>(); }
    : identifierList ( ':' type | 'in' 'seq' expression )
        {
            ILexLocation loc = extractLexLocation($identifierList.stop);
            $rdecls.addAll(CmlAstFactory.generateSingleTypeDeclerations(loc,$identifierList.ids,$type.type,$expression.exp));
          /*  if ($type.type != null)
                $rdecls = new ATypeSingleDeclaration(extractLexLocation($identifierList.stop), NameScope.GLOBAL, $identifierList.ids, $type.type);
            else
                $rdecls = new AExpressionSingleDeclaration(extractLexLocation($identifierList.stop), NameScope.GLOBAL, $identifierList.ids, $expression.exp);
                */
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
            ILexLocation floc = extractLexLocation($fid);
            List<PExp> fexprs = new ArrayList<PExp>();
            if ($fexp.exp != null)
                fexprs.add($fexp.exp);
            ANameChannelExp fromExp = new ANameChannelExp(floc, new CmlLexNameToken("", $fid.getText(), floc), fexprs);
            if ($fexp.exp != null)
                fromExp.setLocation(extractLexLocation($fid,$fexp.stop));

            ILexLocation tloc = extractLexLocation($tid);
            List<PExp> texprs = new ArrayList<PExp>();
            if ($texp.exp != null)
                texprs.add($texp.exp);
            ANameChannelExp toExp = new ANameChannelExp(tloc, new CmlLexNameToken("", $tid.getText(), tloc), texprs);
            if ($texp.exp != null)
                toExp.setLocation(extractLexLocation($tid,$texp.stop));

            $pair = new ARenamePair(false, fromExp, toExp);
        }
    ;

actionParagraphOptList returns[List<PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : ( actionParagraph
            {
                if ($actionParagraph.defs != null) {
                    $defs.addAll($actionParagraph.defs);
                }
            }
        )*
    ;

actionParagraph returns[List<? extends PDefinition> defs]
    : typeDefs          { $defs = $typeDefs.defs; }
    | valueDefs         { $defs = $valueDefs.defs; }
    | functionDefs      { $defs = $functionDefs.defs; }
    | stateDefs         { if ($stateDefs.defs != null) $defs = $stateDefs.defs.getStateDefs(); }
    | operationDefs     { $defs = $operationDefs.defs; }
    | actionDefs        { $defs = $actionDefs.defs; }
    | namesetDefs       { $defs = $namesetDefs.defs; }
    ;

actionDefs returns[List<AActionDefinition> defs]
    : 'actions' actionDefOptList { $defs = $actionDefOptList.defs; }
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
            adef.setDeclarations($parametrisationList.params);
            adef.setName(new CmlLexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER)));
            if(adef.getDeclarations() != null && adef.getDeclarations().size() > 0)
            {
                List<PType> typeQualifiers = new LinkedList<PType>();
                for(PParametrisation p : adef.getDeclarations())
                    typeQualifiers.add(p.getDeclaration().getType());
                adef.getName().setTypeQualifier(typeQualifiers);
            }
            adef.setAction($action.action);

            $def = adef;
        }
    ;

actionList returns[List<PAction> actions]
@init { $actions = new ArrayList<PAction>(); }
    : item=action { $actions.add($item.action); } ( ',' item=action { $actions.add($item.action); } )*
    ;

action returns[PAction action]
    : action0 actionSuffix?
        {
            $action = $action0.action;
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
    | actionReplicated  {$actionReplicated.action.setLocation(extractLexLocation($start,$actionReplicated.stop));$action = $actionReplicated.action; }
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
    | ';' seqReplicationDeclarationList '@' repld=action
        {
            ASequentialCompositionReplicatedAction ascra = new ASequentialCompositionReplicatedAction();
            ascra.setReplicationDeclaration($seqReplicationDeclarationList.rdecls);
            ascra.setReplicatedAction($repld.action);
            $action = ascra;
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
    | '||' replicationDeclarationList '@' '[' ns=varsetExpr '|' cs=varsetExpr ']' repld=action
        {
            AAlphabetisedParallelismReplicatedAction raction = new AAlphabetisedParallelismReplicatedAction();
            raction.setReplicationDeclaration($replicationDeclarationList.rdecls);
            raction.setNamesetExpression($ns.vexp);
            raction.setChansetExpression($cs.vexp);
            raction.setReplicatedAction($repld.action);
            $action = raction;
        }
    ;

actionSimpleReplOp returns[SReplicatedAction op]
    : '[]'      { $op = new AExternalChoiceReplicatedAction(); }
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

action0 returns[PAction action]
    : action1 (action0op right=action0)?
        {
            $action = $action1.action;
            if ($right.action != null) {
                SParallelAction op = $action0op.op;
                op.setLeftAction($action);
                op.setRightAction($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action0op returns[SParallelAction op]
    : '[' ln=varsetExpr
        ( '||' rn=varsetExpr
            {
                AAlphabetisedParallelismParallelAction appa = new AAlphabetisedParallelismParallelAction();
                appa.setLeftChansetExpression($ln.vexp);
                appa.setRightChansetExpression($rn.vexp);
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
    | '[|' first=varsetExpr ( '|' cs=varsetExpr '|' third=varsetExpr )? '|]'
        {
            AGeneralisedParallelismParallelAction gppa = new AGeneralisedParallelismParallelAction();
            if ($cs.vexp != null) {
                gppa.setLeftNamesetExpression($first.vexp);
                gppa.setChansetExpression($cs.vexp);
                gppa.setRightNamesetExpression($third.vexp);
            } else {
                gppa.setChansetExpression($first.vexp);
            }
            $op = gppa;
        }
    ;

action1 returns[PAction action]
    : action2 ('|||' right=action1)?
        {
            $action = $action2.action;
            if ($right.action != null) {
                AInterleavingParallelAction op = new AInterleavingParallelAction();
                op.setLeftAction($action);
                op.setRightAction($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action2 returns[PAction action]
    : action3 ('[||' lns=varsetExpr '|' rns=varsetExpr '||]' right=action2)?
        {
            $action = $action3.action;
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

action3 returns[PAction action]
    : action4 ('|~|' right=action3)?
        {
            $action = $action4.action;
            if ($right.action != null) {
                AInternalChoiceAction op = new AInternalChoiceAction();
                op.setLeft($action);
                op.setRight($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action4 returns[PAction action]
    : action5 ('[]' right=action4)?
        {
            $action = $action5.action;
            if ($right.action != null) {
                AExternalChoiceAction op = new AExternalChoiceAction();
                op.setLeft($action);
                op.setRight($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action5 returns[PAction action]
    : action6 (action5op right=action5)?
        {
            $action = $action6.action;
            if ($right.action != null) {
                PAction op = $action5op.op;
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

action5op returns[PAction op]
    : '/_\\'
        {
            $op = new AInterruptAction();
        }
    | '/_' expression '_\\'
        {
            $op = new ATimedInterruptAction();
            ((ATimedInterruptAction)$op).setTimeExpression($expression.exp);
        }
    ;

action6 returns[PAction action]
    : action7 (action6op right=action6)?
        {
            $action = $action7.action;
            if ($right.action != null) {
                PAction op = $action6op.op;
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

action6op returns[PAction op]
    : '[_>'
        {
            $op = new AUntimedTimeoutAction();
        }
    | '[_' expression '_>'
        {
            $op = new ATimeoutAction();
            ((ATimeoutAction)$op).setTimeoutExpression($expression.exp);
        }
    ;

action7 returns[PAction action]
    : action8 (';' right=action7)?
        {
            $action = $action8.action;
            if ($right.action != null) {
                ASequentialCompositionAction op = new ASequentialCompositionAction();
                op.setLeft($action);
                op.setRight($right.action);
                op.setLocation(extractLexLocation($start,$right.stop));
                $action = op;
            }
        }
    ;

action8 returns[PAction action]
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
                $action = stm2action(AstFactory.newASpecificationStm(null,
                $frameSpecList.frameSpecs, $pre.exp, $post.exp,null));
            }
        | guard=expression ']' '&' guarded=action8
            {
                $action = new AGuardedAction(null, $guard.exp, $guarded.action);
            }
        )
    ;

/* This is a horrible, horrible mess, but it seems to be the only way
 * to resolve the whole set of ambiguities between communications,
 * action calls, operation calls, and assignments, plus the allowing
 * renaming expressions to be parsed. -jwc/15Mar2013
 *
 * Touch this only at risk of your sanity.  -jwc/17Mar2013
 */
leadingIdAction returns[PAction action]
@init { PExp assignable = null; }
@after { $action.setLocation(extractLexLocation($start,$stop)); }
    : id=IDENTIFIER
        // bare action call
        {
            CmlLexNameToken name = new CmlLexNameToken("", $id.getText(), extractLexLocation($start));
            $action = new AReferenceAction(null, name, new ArrayList<PExp>());
        }
        ( renamingExpr
            // action call plus rename
            {
                $action = new AChannelRenamingAction(null, $action, $renamingExpr.rexp);
            }
        | ( ('.' IDENTIFIER)=>'.' ids+=IDENTIFIER )*
            // pure dotted prefix, if ids+ is nonempty, but I don't believe this is allowed as an action call
            ( communicationOptList '->' after=action8
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
                    if ($ids != null && $ids.size() > 0) {
                        ListIterator<Object> rIter = $ids.listIterator($ids.size());
                        while (rIter.hasPrevious()) {
                            CommonToken dotId = (CommonToken)rIter.previous();
                            ILexLocation dotLoc = extractLexLocation(dotId);
                            CmlLexNameToken dotName = new CmlLexNameToken("", dotId.getText(), dotLoc);
                            comms.add(0, new ASignalCommunicationParameter(dotLoc, new AVariableExp(dotLoc, dotName, dotName.getName())));
                        }
                    }
                    $action = new ACommunicationAction(null, firstId, comms, $after.action);
                }
            | selectorOptList
                ( // If the selectorList is empty, we have the base
                  // action call created above, and this action should
                  // be a no-op; if the selectorList is a single
                  // AApplyExp, then this is a bare operation call;
                  // otherwise error -> RecognitionException
                    {
                        // create raw operation call, if there
                        List<PExp> selectors = $selectorOptList.selectors;

                        if (selectors.size() > 0) {
                            if (selectors.size() != 1 || ! (selectors.get(0) instanceof AApplyExp)) {
                                // If this is a raw operation call,
                                // and there are extra selectors, or
                                // the wrong selector, the best that
                                // can be done right now is to throw
                                // an Exception.  This should be
                                // factored out, above, to look for an
                                // explicit (exprList) directly.
                                throw new RecognitionException(input);
                            }

                            List<LexIdentifierToken> idList = new ArrayList<LexIdentifierToken>();

                            // need to merge the first identifier and
                            // any dotted identifiers into a name
                            String module = "";
                            CommonToken dotId = $id;
                            if ($ids != null && $ids.size() > 0) {
                                StringBuilder sb = new StringBuilder($id.getText());
                                idList.add(new LexIdentifierToken($id.getText(),false,extractLexLocation($id)));
                                ListIterator<Object> iter = $ids.listIterator();
                                while (iter.hasNext()) {
                                    dotId = (CommonToken)iter.next();
                                    idList.add(new LexIdentifierToken(dotId.getText(),false,extractLexLocation(dotId)));
                                    if (iter.hasNext()) {
                                        sb.append(".");
                                        sb.append(dotId.getText());
                                    }
                                }
                                module = sb.toString();
                            }

                            if(!idList.isEmpty())
                            {
                                //we dont want the op name in here
                                idList = idList.subList(0,idList.size()-1);
                            }
                            CmlLexNameToken name = new CmlLexNameToken(module, dotId.getText(), extractLexLocation($id,dotId));

                            // grab the AApplyExp directly
                            AApplyExp apply = (AApplyExp)selectors.get(0);

                            //FIXME this is the hacked version of call with dots in the module. We should properly use CallObject and place an unresolved state designator with the path instead
                            //$action = stm2action(AstFactory.newACallStm(name, apply.getArgs()));

                            if($ids== null || $ids.size()==0)
                            {
                                $action = stm2action(AstFactory.newACallStm(name, apply.getArgs()));
                            }else
                            {
                                //we have an unresolved path
                                PExp path = new AUnresolvedPathExp(extractLexLocation($id,dotId),idList);
                                //the object designator the therefore also unresolved
                                PObjectDesignator designator = new AUnresolvedObjectDesignator(extractLexLocation($id,dotId),path);

                                $action = stm2action(AstFactory.newACallObjectStm(designator,name.getIdentifier(),apply.getArgs()));
                            }
                        }else
                        {
                            //  This is from having something like 'x.a' as a statement on its own --- this cannot be a AReferenceAction as actions cannot be referenced with dots, and it cannot be a operation call as it is missing () at the end (the selectorOptList was empty) --- so this is a Parse Error -jwc/29Oct2013
                            //FIXME throw new RecognitionException(input);
                        }
                    }
                | ':='
                    // At this point we know that we have an assignableExpression to the left, so assemble that here
                    {
                        AVariableExp firstIdExp = new AVariableExp(extractLexLocation($id), new CmlLexNameToken("", $id.getText(), extractLexLocation($id)), $id.getText());
                        List<PExp> selectors = $selectorOptList.selectors;

                        if ($ids != null && $ids.size() > 0) {
                            ListIterator<Object> rIter = $ids.listIterator($ids.size());
                            while (rIter.hasPrevious()) {
                                CommonToken dotId = (CommonToken)rIter.previous();
                                ILexLocation dotLoc = extractLexLocation(dotId);
                                List<LexIdentifierToken> idList = new ArrayList<LexIdentifierToken>();
                                idList.add(new LexIdentifierToken(dotId.getText(),false,dotLoc));
                                selectors.add(0,new AUnresolvedPathExp(dotLoc, idList));
                            }
                        }

                        assignable = selectorListAssignableBuilder(firstIdExp, selectors);
                    }
                    ( newT='new' name '(' expressionList? rbT=')'
                        // object instantiation
                        {
                            // sort out the assignableExpression equivalent
                            AUnresolvedStateDesignator designator = new AUnresolvedStateDesignator(assignable.getLocation(),assignable);
                            LexIdentifierToken className = new LexIdentifierToken(($name.name).getName(),false,($name.name).getLocation());
                            $action = stm2action(new ANewStm(extractLexLocation($newT,$rbT), designator, className, $expressionList.exps));
                        }
                    | expression
                        // assignment or operation call (but that's sorted out in the TC)
                        {
                        AUnresolvedStateDesignator designator = new AUnresolvedStateDesignator(assignable.getLocation(),assignable);
                            $action =stm2action(AstFactory.newAAssignmentStm(extractLexLocation($expression.start,$expression.stop), designator, $expression.exp));
                        }
                    )
                )
            )
        )
    ;

prefixStatement returns[PAction action]
@after {
    $action.setLocation(extractLexLocation($start, $stop));
    if($action instanceof AStmAction)
        ((AStmAction)$action).getStatement().setLocation(extractLexLocation($start, $stop));
    }
    : 'let' localDefinitionList 'in' body=action8
        {
            $action = stm2action(AstFactory.newALetStm(extractLexLocation($start,$body.stop), $localDefinitionList.defs, action2stm($body.action)));
        }
    | ('if' expression 'then')=> 'if' test=expression 'then' th=action8
        ( ('elseif')=> elseIfStmtOptList )
        ( ('else')=> 'else' el=action8)?
        // need the ()=> to match elseif and else clauses greedily
        {
            $action = stm2action(new AIfStm(null, $test.exp, action2stm($th.action), $elseIfStmtOptList.elseifs, ($el.action!=null?action2stm($el.action):null)));
        }
    | 'if' nonDetStmtAltList endT='end'
        {
            $action = stm2action(new AIfNonDeterministicStm(extractLexLocation($start,$endT), $nonDetStmtAltList.alts));
        }
    | 'do' nonDetStmtAltList endT='end'
        {
            $action = stm2action(new ADoNonDeterministicStm(extractLexLocation($start,$endT), $nonDetStmtAltList.alts));
        }
    | 'cases' expression ':' caseStmtAltList (',' 'others' '->' action)? 'end'
        {
            $action = stm2action(AstFactory.newACasesStm(null, $expression.exp, $caseStmtAltList.alts, action2stm($action.action)));
        }
    | forStatement
        {
            $action = $forStatement.action;
        }
    | 'while' expression 'do' body=action8
        {
            $action = stm2action(AstFactory.newAWhileStm(extractLexLocation($start,$body.stop), $expression.exp, action2stm($body.action)));
        }
    ;

elseIfStmt returns[AElseIfStm elseif]
@after { $elseif.setLocation(extractLexLocation($start, $stop)); }
    : 'elseif' test=expression 'then' th=action8
        {
            $elseif = new AElseIfStm(null, $test.exp,action2stm( $th.action));
        }
    ;

forStatement returns[PAction action]
options { k=3; } // k=3 is sufficient to disambiguate these (longest: for ID =)
@after { $action.setLocation(extractLexLocation($start, $stop)); }
    : 'for' IDENTIFIER '=' start=expression 'to' end=expression ( 'by' step=expression )? 'do' body=action8
        {
            CmlLexNameToken name = new CmlLexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            $action = stm2action(AstFactory.newAForIndexStm(extractLexLocation($start,$body.stop), name, $start.exp, $end.exp, $step.exp, action2stm($body.action)));
        }
    | 'for' 'all' bindablePattern 'in' 'set' expression 'do' body=action8
        {
            $action = stm2action(AstFactory.newAForAllStm(extractLexLocation($start,$body.stop), $bindablePattern.pattern, $expression.exp, action2stm($body.action)));
        }
    | 'for' bindablePattern (':' type)? 'in' expression 'do' body=action8
        {
            ADefPatternBind patternBind = new ADefPatternBind();
            ILexLocation pbloc = $bindablePattern.pattern.getLocation();
            if ($type.type != null) {
                pbloc = extractLexLocation(pbloc, $type.type.getLocation());
                patternBind.setBind(new ATypeBind(pbloc, $bindablePattern.pattern, $type.type));
            } else {
                patternBind.setPattern($bindablePattern.pattern);
            }
            patternBind.setLocation(pbloc); //depends on the if
            $action = stm2action(AstFactory.newAForPatternBindStm(extractLexLocation($start,$body.stop), patternBind,false ,$expression.exp, action2stm($body.action)));
        }
    ;

actionbase returns[PAction action]
@after { $action.setLocation(extractLexLocation($start,$stop)); }
    : 'Skip'            { $action = new ASkipAction(); }
    | 'Stop'            { $action = new AStopAction(); }
    | 'Chaos'           { $action = new AChaosAction(); }
    | 'Diverge'             { $action = new ADivAction(); }
    | 'Wait' expression { $action = new AWaitAction(null, $expression.exp); }
    | ('return' expression)=>'return' expression
        {
            $action = stm2action(AstFactory.newAReturnStm(extractLexLocation($start,$expression.stop),$expression.exp));
        }
    | 'return'
        {
            $action = stm2action(AstFactory.newAReturnStm(extractLexLocation($start)));
        }
    | 'atomic' '(' assignmentStatementList rbT=')'
        {
            $action = stm2action(AstFactory.newAAtomicStm(extractLexLocation($start,$rbT), $assignmentStatementList.statements));
        }
    | ( '(' 'dcl')=> '(' 'dcl' assignmentDefinitionList '@' action rpT=')'
        {
            ILexLocation dloc = extractLexLocation($assignmentDefinitionList.start, $assignmentDefinitionList.stop);
            //ADeclareStatementAction dcls = new ADeclareStatementAction(dloc, $assignmentDefinitionList.defs);

            ABlockSimpleBlockStm block = AstFactory.newABlockSimpleBlockStm(extractLexLocation($start,$rpT), $assignmentDefinitionList.defs);
            block.getStatements().add(action2stm($action.action));
            $action = stm2action(block);
            //$action = new ABlockStatementAction(null, dcls, $action.action);
        }
    | ('(' parametrisationList '@')=> '(' parametrisationList '@' action pr=')' ( '(' expressionList ')' )?
        {
            $action = new AParametrisedAction(null, $parametrisationList.params, $action.action);
            if ($expressionList.exps!=null) {
                $action.setLocation(extractLexLocation($start, $pr));
                $action = new AParametrisedInstantiatedAction(null, (AParametrisedAction)$action, $expressionList.exps);
            }
        }
    | '(' action rpT=')' renamingExpr?
        {
            ABlockSimpleBlockStm block = AstFactory.newABlockSimpleBlockStm(extractLexLocation($start,$rpT), new ArrayList<AAssignmentDefinition>());
            block.getStatements().add(action2stm($action.action));
            $action = stm2action( block);
            if ($renamingExpr.rexp != null) {
                $action = new AChannelRenamingAction(null, $action, $renamingExpr.rexp);
            }
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
        ( id=IDENTIFIER
            {
                ILexLocation loc = extractLexLocation($id);
                CmlLexNameToken name = new CmlLexNameToken("", $id.getText(), loc);
                $comm.setExpression(new AVariableExp(loc, name, $id.getText()));
            }
        | '(' expression ')'            { $comm.setExpression($expression.exp); }
        | symbolicLiteralExpr           { $comm.setExpression($symbolicLiteralExpr.exp); }
        | recordTupleExprs              { $comm.setExpression($recordTupleExprs.exp); }
        | eseq='[]'                     { $comm.setExpression(new ASeqEnumSeqExp(extractLexLocation($eseq), new ArrayList<PExp>())); }
        | '[' seqExpr? ']'              { $comm.setExpression($seqExpr.seqExpr); }
        )
    | '?' bindablePattern ( ':' '(' expression ')' )?
        {
            $comm = new AReadCommunicationParameter(null, $expression.exp, $bindablePattern.pattern);
        }
    ;

assignmentStatementList returns[List<AAssignmentStm> statements]
@init { $statements = new ArrayList<AAssignmentStm>(); }
    : item=assignmentStatement { $statements.add($item.statement); } ( ';' item=assignmentStatement { $statements.add($item.statement); } )+
    ;

assignmentStatement returns[AAssignmentStm statement]
@after { $statement.setLocation(extractLexLocation($start, $stop)); }
    : assignableExpression ':=' expression
        {
            AUnresolvedStateDesignator designator = new AUnresolvedStateDesignator($assignableExpression.exp.getLocation(),$assignableExpression.exp);
            $statement=AstFactory.newAAssignmentStm(extractLexLocation($expression.start,$expression.stop), designator, $expression.exp);
        }
    ;

assignableExpression returns[PExp exp]
    : ( t=SELF | t=IDENTIFIER ) selectorOptList
        {
            ILexLocation loc = extractLexLocation($t);
            if ($IDENTIFIER != null)
                $exp = new AVariableExp(loc, new CmlLexNameToken("", $t.getText(), loc), $t.getText());
            else
                $exp = new ASelfExp(loc, new CmlLexNameToken("", $t.getText(), loc));

            $exp = selectorListAssignableBuilder($exp,$selectorOptList.selectors);
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
            $statement = stm2action(AstFactory.newACallStm($name.name, $expressionList.exps));
        }
    ;

// need the ()=> to match elseifs greedily
elseIfStmtOptList returns[List<AElseIfStm> elseifs]
@init { $elseifs = new ArrayList<AElseIfStm>(); }
    : ( ('elseif')=>elseIfStmt { $elseifs.add($elseIfStmt.elseif); } )*
//    | /* empty match; we want a null list if no elseifs */
    ;

caseStmtAltList returns[List<ACaseAlternativeStm> alts]
@init { $alts = new ArrayList<ACaseAlternativeStm>(); }
    : ( item=caseStmtAlt { $alts.addAll($item.alts); } ( ',' item=caseStmtAlt { $alts.addAll($item.alts); } )* )?
    ;

caseStmtAlt returns[List<ACaseAlternativeStm> alts]
@init { $alts = new ArrayList<ACaseAlternativeStm>(); }
@after {
    for(ACaseAlternativeStm alt : $alts) {
        alt.setLocation(extractLexLocation($start, $stop));
    }
}
    : patternList '->' action
        {
            for (PPattern p : $patternList.patterns) {
                p.getLocation().executable(true);
                $alts.add(AstFactory.newACaseAlternativeStm(p.clone(), action2stm(($action.action).clone())));
            }
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

nonDetStmtAltList returns[List<AAltNonDeterministicStm> alts]
@init { $alts = new ArrayList<AAltNonDeterministicStm>(); }
    : item=nonDetStmtAlt { $alts.add($item.alt); } ( '|' item=nonDetStmtAlt { $alts.add($item.alt); } )*
    ;

nonDetStmtAlt returns[AAltNonDeterministicStm alt]
@after { $alt.setLocation(extractLexLocation($start, $stop)); }
    : expression '->' action
        {
            $alt = new AAltNonDeterministicStm(extractLexLocation($expression.start,$action.stop), $expression.exp, action2stm($action.action));
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
            $frameSpec = AstFactory.newAExternalClause(mode, $nameList.names, $type.type);
        }
    ;

/* Ok, this is cute.  It works both with and without semicolons,
 * though it may not be visually obvious (without the semis) that it
 * is a space that separates untyped channels from typed ones. (-jwc)
 */
channelDefs returns[List<AChannelDefinition> defs]
    : 'channels' ( '[' IDENTIFIER ']' )? channelDefOptList { $defs = $channelDefOptList.defs; }
    ;

channelDefOptList returns[List<AChannelDefinition> defs]
@init { $defs = new ArrayList<AChannelDefinition>(); }
    : ( channelDef { $defs.addAll($channelDef.def); } )*
    ;

channelDef returns[List<AChannelDefinition> def]
@init { $def = new ArrayList<AChannelDefinition>(); }
    : identifierList (':' type)?
        {
            for (ILexIdentifierToken id : $identifierList.ids) {
                List<ILexIdentifierToken> idList = new ArrayList<ILexIdentifierToken>();
                idList.add(id);
                ILexLocation loc = id.getLocation();

                AChannelDefinition chanDecl = new AChannelDefinition();
                chanDecl.setName(new CmlLexNameToken("", id)); // this is ok, as each identifier in the identifierList gets its own ACNDef
                chanDecl.setNameScope(NameScope.GLOBAL);
                chanDecl.setUsed(false);
                chanDecl.setLocation(id.getLocation());

                List<PType> types = new Vector<PType>();
                ILexLocation typeLocation = loc;

                if ($type.type != null) {
                    PType type = $type.type;
                    typeLocation = type.getLocation();

                    if(type instanceof AProductType)
                    {
                        for(PType t : ((AProductType)type).getTypes())
                        {
                            types.add(t.clone());
                        }
                    }else
                    {
                        types.add(type.clone());
                    }


                } else {
                    //types.add(AstFactory.newAVoidType(loc));
                }

                AChannelType t = new AChannelType(typeLocation, false, types);
                chanDecl.setType(t);


                $def.add(chanDecl);
            }
        }
    ;

chansetDefs returns[List<AChansetDefinition> defs]
    : 'chansets' ( '[' IDENTIFIER ']' )? chansetDefOptList { $defs = $chansetDefOptList.defs; }
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
            $def.setName(new CmlLexNameToken("", $def.getIdentifier()));
            $def.setLocation(extractLexLocation($IDENTIFIER));
        }
    ;

varsetExpr returns[PVarsetExpression vexp]
    : l=varsetExpr0 varsetExprTailOptList
        {
            $vexp = $varsetExpr0.vexp;
            for (SVOpVarsetExpression right : $varsetExprTailOptList.vexps) {
                ILexLocation loc = extractLexLocation($vexp.getLocation(), right.getLocation());
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
                vexp.setOp(new CmlLexNameToken("", $op.getText(), extractLexLocation($op)));
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
                ILexLocation loc = extractLexLocation($vexp.getLocation(), right.getLocation());
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
                vexp.setOp(new CmlLexNameToken("", $op.getText(), extractLexLocation($op)));
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
                ILexLocation loc = extractLexLocation($vexp.getLocation(), right.getLocation());
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
                vexp.setOp(new CmlLexNameToken("", $op.getText(), extractLexLocation($op)));
                vexp.setRight($varsetExprbase.vexp);
                $vexps.add(vexp);
            }
        )*
    ;

varsetExprbase returns[PVarsetExpression vexp]
@after { $vexp.setLocation(extractLexLocation($start, $stop)); }
    : IDENTIFIER
        {
            ILexLocation loc = extractLexLocation($IDENTIFIER);
            $vexp = new AIdentifierVarsetExpression(loc, new LexIdentifierToken($IDENTIFIER.getText(), false, loc));
        }
    | '(' varsetExpr ')'
        {
            $vexp = $varsetExpr.vexp;
        }
    | '{' '}'
        {
            $vexp = new AEnumVarsetExpression(null, new ArrayList<ANameChannelExp>());
        }
    | '{' varsetName ( ',' varsetNameList | setMapExprBinding )? '}'
        {
            if ($setMapExprBinding.bindings != null) {
                // literal varset comprehension
                $vexp = new ACompVarsetExpression(null, $varsetName.name, $setMapExprBinding.bindings, $setMapExprBinding.pred);
            } else {
                // literal varset enumeration
                List<ANameChannelExp> names = $varsetNameList.names == null ? new ArrayList<ANameChannelExp>() : $varsetNameList.names;
                names.add(0, $varsetName.name);
                $vexp = new AEnumVarsetExpression(null, names);
            }
        }
    | '{|' '|}'
        {
            $vexp = new AFatEnumVarsetExpression(null, new ArrayList<ANameChannelExp>());
        }
    | '{|' varsetName ( ',' varsetNameList | setMapExprBinding )? '|}'
        {
            if ($setMapExprBinding.bindings != null) {
                // prefix-wise (fat) varset comprehension
                // 2nd null needs to be the channel name (varsetName)
                $vexp = new AFatCompVarsetExpression(null, $varsetName.name, $setMapExprBinding.bindings, $setMapExprBinding.pred);
                //$vexp = new AFatCompVarsetExpression(null, $varsetName.name, $setMapExprBinding.bindings, $setMapExprBinding.pred);
            } else {
                // prefix-wise (fat) varset enumeration
                List<ANameChannelExp> names = $varsetNameList.names == null ? new ArrayList<ANameChannelExp>() : $varsetNameList.names;
                names.add(0, $varsetName.name);
                $vexp = new AFatEnumVarsetExpression(null, names);
            }
        }
    ;

varsetNameList returns[List<ANameChannelExp> names]
@init { $names = new ArrayList<ANameChannelExp>(); }
    : item=varsetName { $names.add($item.name); } ( ',' item=varsetName { $names.add($item.name); } )*
    ;

varsetName returns[ANameChannelExp name]
@init {
    ILexLocation loc;
    CmlLexNameToken lex;
    List<PExp> exprs = new ArrayList<PExp>();
}
@after { $name.setLocation(extractLexLocation($start,$stop)); }
    : base=IDENTIFIER
        ( '.'
            ( id=IDENTIFIER
                {
                    loc = extractLexLocation($id);
                    ILexNameToken lexname = new CmlLexNameToken("", $id.getText(), loc, false, false);
                    exprs.add(new AVariableExp(loc, lexname, lexname.getName()));
                }
            | '(' expression ')'    { exprs.add($expression.exp); }
            | symbolicLiteralExpr   { exprs.add($symbolicLiteralExpr.exp); }
            | recordTupleExprs      { exprs.add($recordTupleExprs.exp); }
            )
        )*
        {
            loc = extractLexLocation($base);
            LexIdentifierToken lexid = new LexIdentifierToken($base.getText(), false, loc);
            $name = new ANameChannelExp();
            $name.setIdentifier(lexid);
            $name.setExpressions(exprs);
        }
    ;

namesetDefs returns[List<ANamesetDefinition> defs]
    : 'namesets' namesetDefOptList { $defs = $namesetDefOptList.defs; }
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
            $def.setName(new CmlLexNameToken("",new LexIdentifierToken($IDENTIFIER.getText(), false, extractLexLocation($IDENTIFIER))));
            $def.setNamesetExpression($varsetExpr.vexp);
        }
    ;

classDefinitionBlockOptList returns[List<PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : ( classDefinitionBlock { $defs.addAll($classDefinitionBlock.defs); } )*
    ;

classDefinitionBlock returns[List<? extends PDefinition> defs]
@init { $defs = new ArrayList<PDefinition>(); }
    : typeDefs                  { $defs = $typeDefs.defs; }
    | valueDefs                 { $defs = $valueDefs.defs; }
    | functionDefs              { $defs = $functionDefs.defs; }
    | stateDefs                 { if ($stateDefs.defs != null) $defs = $stateDefs.defs.getStateDefs(); }
    | operationDefs             { $defs = $operationDefs.defs; }
    | 'initial' operationDef
        {
            AInitialDefinition def = new AInitialDefinition();
            def.setOperationDefinition($operationDef.def);
            def.setLocation(extractLexLocation(extractLexLocation($classDefinitionBlock.start), $operationDef.def.getLocation()));
            LinkedList<PDefinition> dl = new LinkedList<PDefinition>();
            dl.add(def);
            $defs = dl;
        }
    ;

valueDefs returns[List<AValueDefinition> defs]
    : 'values' ( '[' IDENTIFIER ']' )? qualValueDefinitionOptList { $defs = $qualValueDefinitionOptList.defs; }
    ;

qualValueDefinitionOptList returns[List<AValueDefinition> defs]
@init { defs = new ArrayList<AValueDefinition>(); }
    : ( item=qualValueDefinition { $defs.add($item.def); } )*
    ;

qualValueDefinition returns[AValueDefinition def]
@init { $def = new AValueDefinition(); }
    : QUALIFIER? valueDefinition
        {
            $def = $valueDefinition.def;
            if ($def != null) {
              $def.setAccess(extractQualifier($QUALIFIER));
              $def.getAccess().setStatic(new TStatic());
              ILexLocation loc = extractLexLocation(extractLexLocation($qualValueDefinition.start), $qualValueDefinition.def.getLocation()) ;
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
// I just noticed that CML0 has an 'in' form present; this is not from
// the VDM side, but has never been implmented. -jwc/18Jun2013
//
//  : bindablePattern (':' type)? ( '=' | 'be' 'st' ) expression
    : bindablePattern (':' type)? '=' expression
        {
            $def = AstFactory.newAValueDefinition($bindablePattern.pattern, NameScope.LOCAL, $type.type, $expression.exp);
            // This almost works, but causes a TC crash for some reason; related to bug #91 -jwc/3Oct2013
            //$def.setName(new CmlLexNameToken("", $bindablePattern.pattern.toString(), $bindablePattern.pattern.getLocation()));
        }
    ;

stateDefs returns[AStateDefinition defs]
@after {
    if ($defs != null) {
        $defs.setLocation(extractLexLocation($start, $stop));
    }
}
    : 'state' instanceVariableDefinitionList?
        {
            if ($instanceVariableDefinitionList.defs != null && $instanceVariableDefinitionList.defs.size()>0) {
                $defs = new AStateDefinition();
                // This almost works, but causes a TC crash for some reason; related to bug #91 -jwc/3Oct2013
                //$defs.setName(new CmlLexNameToken("", "State", extractLexLocation($start)));
                $defs.setStateDefs($instanceVariableDefinitionList.defs);
            }
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
            AInstanceVariableDefinition ivd = AstFactory.newAInstanceVariableDefinition($def.getName(), $def.getType(), ((AAssignmentDefinition) $def).getExpression());
            $def.getName().parent(ivd);//the type of ivd is graph but we through away the assignment
            $def.getType().parent(ivd);//the type of ivd is graph but we through away the assignment
            ivd.setAccess($def.getAccess());
            $def = ivd;
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
            CmlLexNameToken name = new CmlLexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));

            PExp exp = null;

            if ($det != null || $nondet != null)
            {
                exp = $expression.exp;
            }else
            {
                exp = AstFactory.newAUndefinedExp(name.getLocation());
            }

            $def = AstFactory.newAAssignmentDefinition(name, $type.type, exp);
            /*$def = new AAssignmentDefinition();//null, name, NameScope.GLOBAL, false, null, null, type, null, null, null);
            $def.setName(new CmlLexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER)));
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

           if($def.getExpression()==null)
           {
            $def.setExpression(AstFactory.newAUndefinedExp($def.getName().getLocation()));
           }
           */

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

functionDefs returns[List<SFunctionDefinition> defs]
    : 'functions' ( '[' IDENTIFIER ']' )? qualFunctionDefinitionOptList { $defs = $qualFunctionDefinitionOptList.defs; }
    ;

qualFunctionDefinitionOptList returns[List<SFunctionDefinition> defs]
@init { $defs = new ArrayList<SFunctionDefinition>(); }
    : (QUALIFIER? functionDefinition
            {
                $functionDefinition.def.getAccess().setAccess(extractQualifier($QUALIFIER).getAccess());
                if ($QUALIFIER != null) {
                    ILexLocation loc = extractLexLocation(extractLexLocation($QUALIFIER), $functionDefinition.def.getLocation());
                    $functionDefinition.def.setLocation(loc);
                }
                $defs.add($functionDefinition.def);
                // This resets the recognition of the QUALIFIER token,
                // which ANTLR doesn't seem to do for us.  This way
                // *only* the function for which there was a qualifier
                // will get tagged with that qualifier, rather than
                // qualifiers acting as section markers.  I think this
                // is a bug in ANTLR. -jwc/10Oct2013
                $QUALIFIER=null;
            }
        )*
    ;

functionDefinition returns[SFunctionDefinition def]
@after {
    $def.setLocation(extractLexLocation($start, $stop));

    if ($def instanceof AExplicitFunctionDefinition) {
        AExplicitFunctionDefinition f = (AExplicitFunctionDefinition)$def;
        f.setUsed(false);
        if (f.getPredef() != null) {
            f.getPredef().setName(
                                  new CmlLexNameToken("", new LexIdentifierToken("pre_"+f.getName().getName(), false, f.getLocation())));
            // f.parent($def);
        }
        if (f.getPostdef() != null) {
            f.getPostdef().setName(new CmlLexNameToken("", new LexIdentifierToken("post_"+f.getName().getName(), false, f.getLocation())));
            //f.parent($def);
        }
    }

    if ($def instanceof AImplicitFunctionDefinition) {
        AImplicitFunctionDefinition f = (AImplicitFunctionDefinition)$def;
        f.setUsed(false);
        if (f.getPredef() != null) f.getPredef().setName(new CmlLexNameToken("", new LexIdentifierToken("pre_"+f.getName().getName(), false, f.getLocation())));
        if (f.getPostdef() != null) f.getPostdef().setName(new CmlLexNameToken("", new LexIdentifierToken("post_"+f.getName().getName(), false, f.getLocation())));
    }
}
    : IDENTIFIER (expl=explicitFunctionDefinitionTail | impl=implicitFunctionDefinitionTail)
        {
            if ($expl.tail != null) {
                $def = $expl.tail;
                if ( !$IDENTIFIER.getText().equals($def.getName().getName()) ) {
                    //fixes bug 172
			         String msg = "Mismatch in function definition.  Signature has " + $IDENTIFIER.getText() + ", definition has " + $def.getName().getName();
			         errors.add(new CmlParserError(msg, new RecognitionException(), sourceFileName, $IDENTIFIER));

                }
            } else {
                $def = $impl.tail;
            }
            $def.setName(new CmlLexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER)));
        }
    ;

explicitFunctionDefinitionTail returns[AExplicitFunctionDefinition tail]
    : ':' type IDENTIFIER parameterGroupList '==' functionBody ('pre' pre=expression )? ('post' post=expression)? ('measure' name)?
        {
            ILexLocation location = extractLexLocation($IDENTIFIER);

            /*
            ILexNameToken name,
            NameScope scope,
            List<ILexNameToken> typeParams,
            AFunctionType type,
            List<List<PPattern>> parameters,
            PExp body,
            PExp precondition,
            PExp postcondition,
            boolean typeInvariant,
            ILexNameToken measure
            */
            ILexNameToken name = new CmlLexNameToken("", $IDENTIFIER.getText(), location);

            $tail = AstFactory.newAExplicitFunctionDefinition(
                name,
                NameScope.GLOBAL,
                null,//typeParams
                (AFunctionType)$type.type,
                $parameterGroupList.pgroups,//parameters
                $functionBody.exp,
                $pre.exp,
                $post.exp,
                false,//typeInvariant
                $name.name
            );


            //$tail = new AExplicitFunctionDefinition();

            //$tail.setName(new CmlLexNameToken("", $IDENTIFIER.getText(), location));
            //$tail.setParamPatternList($parameterGroupList.pgroups);
            //$tail.setBody($functionBody.exp);
            //$tail.setIsUndefined(false);
            //$tail.setRecursive(false);
            //$tail.setPrecondition($pre.exp);
            //$tail.setPostcondition($post.exp);
            //$tail.setType($type.type);
            //$tail.setIsCurried(false);
            //$tail.setMeasure($name.name);
            //$tail.setAccess(getPrivateAccessSpecifier(false, false, extractLexLocation($IDENTIFIER)));

            // Force all functions to be static for VDM-10
            $tail.getAccess().setStatic(new TStatic());

            //$tail.setPass(Pass.DEFS); // what's this for? RWL: The Overture type checker runs in three PASSes (TYPES, VALUES, DEFS)
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

            ILexLocation typeloc = extractLexLocation($implicitFunctionDefinitionTail.start, $resultTypeList.stop);


            ILexNameToken name = new CmlLexNameToken("-place-holder","-place-holder",typeloc);// remember to reset name and location later when it is parsed

            List<APatternListTypePair> paramPatterns = $parameterTypeList.ptypes;
            if (paramPatterns == null)
            {
                paramPatterns = new ArrayList<APatternListTypePair>();
            }


            // figure out the overall function type
            List<PType> paramTypes = new ArrayList<PType>();
            for (APatternListTypePair pp : paramPatterns)
            {
                for(PPattern ptrn : pp.getPatterns())
                {
                    paramTypes.add(pp.getType());
                }
            }


            PExp body = null;

            //implicit function interpretation
            if($post.exp != null && $post.exp instanceof AInSetBinaryExp
                && ((AInSetBinaryExp)$post.exp).getLeft() instanceof AVariableExp)
            {
               AInSetBinaryExp exp = (AInSetBinaryExp)$post.exp;
               List<PPattern> patterns = new LinkedList<PPattern>();
               AVariableExp varExp = (AVariableExp)exp.getLeft();
               patterns.add(AstFactory.newAIdentifierPattern(varExp.getName().clone()));
               ASetMultipleBind bind = AstFactory.newASetMultipleBind(patterns, exp.getRight().clone());
               body = AstFactory.newALetBeStExp(exp.getLocation(), bind, null, exp.getLeft().clone());
            }


             List<APatternTypePair> resultList = $resultTypeList.rtypes;
            APatternTypePair resultTypePair = null;
            if (resultList.size() == 1)
            {
                resultTypePair = resultList.get(0);
            } else {
                // VDMJ needs a product type of all of the result types
                ATuplePattern tuple = new ATuplePattern();
                List<PPattern> plist = new ArrayList<PPattern>();
                for (APatternTypePair pair : resultList)
                {
                    plist.add(pair.getPattern());
                }
                tuple.setPlist(plist);
                resultTypePair = new APatternTypePair(false, tuple);
            }

            /*
            ILexNameToken name,
            NameScope scope,
            List<ILexNameToken> typeParams,
            List<APatternListTypePair> parameterPatterns,
            APatternTypePair resultPattern,
            PExp body,
            PExp precondition,
            PExp postcondition,
            ILexNameToken measure
            */

            $tail = AstFactory.newAImplicitFunctionDefinition(
                name, //temp name
                NameScope.GLOBAL,
                null,//paramTypes,
                paramPatterns,
                resultTypePair,
                body,
                $pre.exp,
                $post.exp,
                null // no measure
            );



            /*$tail = new AImplicitFunctionDefinition();
            $tail.setNameScope(NameScope.LOCAL);
            $tail.setIsUndefined(false);
            $tail.setUsed(Boolean.FALSE);
            $tail.setAccess(getDefaultAccessSpecifier(false,false,null));
            */
            // Force all functions to be static for VDM-10
            $tail.getAccess().setStatic(new TStatic());

            //$tail.setRecursive(false);


            /*$tail.setParamPatterns(paramPatterns);


            // pre may be null, but that is ok
            $tail.setPrecondition($pre.exp);
            $tail.setPostcondition($post.exp);





  */



            //$tail.setType(AstFactory.newAFunctionType(typeloc, true, paramTypes, resultTypePair.getType()));

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
            ILexLocation loc = extractLexLocation($IDENTIFIER);
            CmlLexNameToken name = new CmlLexNameToken("", $IDENTIFIER.getText(), loc, false, true); // ?? why is the last boolean true?
            $rtype = new APatternTypePair(false, new AIdentifierPattern(loc, null, true, name, false), $type.type);
        }
    ;

functionBody returns[PExp exp]
@after { if ($exp != null) $exp.setLocation(extractLexLocation($start, $stop)); }
    : expression                       { $exp = $expression.exp; }
    | 'is' 'not' 'yet' 'specified'     { $exp = new ANotYetSpecifiedExp(); }
    | 'is' 'subclass' 'responsibility' { $exp = new ASubclassResponsibilityExp(); }
    ;

operationDefs returns[List<SOperationDefinition> defs]
    : 'operations' qualOperationDefOptList { $defs = $qualOperationDefOptList.defs; }
    ;

qualOperationDefOptList returns[List<SOperationDefinition> defs]
@init { $defs = new ArrayList<SOperationDefinition>(); }
    : ( qualOperationDef { $defs.add($qualOperationDef.def); } )*
    ;

qualOperationDef returns[SOperationDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : QUALIFIER? operationDef
        {
            $def = $operationDef.def;
            $def.setAccess(extractQualifier($QUALIFIER));
        }
    ;

operationDef returns[SOperationDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : id=IDENTIFIER
        ( ':' opType secondId=IDENTIFIER parameterGroup '==' operationBody ('pre' pre=expression)? ('post' post=expression)?
            {
                // FIXME --- check that the IDENTIFIERs match and
                // throw a MismatchedTokenException (if that's the
                // right exception)
				if(!$id.getText().equals($secondId.getText()))
				{
					 //relates to bug 172
			         String msg = "Mismatch in operation definition.  Signature has " + $id.getText() + ", definition has " + $secondId.getText();
			         errors.add(new CmlParserError(msg, new RecognitionException(), sourceFileName, $secondId));
				}

                AActionStm bodyWrapper = new AActionStm();
				bodyWrapper.setAction($operationBody.body);
				bodyWrapper.setLocation($operationBody.body.getLocation());
               
                AExplicitOperationDefinition opdef =
                    AstFactory.newAExplicitOperationDefinition(
                        new CmlLexNameToken("", $id.getText(), extractLexLocation($id)),
                        (AOperationType)$opType.type, $parameterGroup.pgroup, $pre.exp, $post.exp, bodyWrapper);

                opdef.setAccess(getDefaultAccessSpecifier(true, false, extractLexLocation($id)));
                opdef.setIsConstructor(false);

                $def = opdef;
            }
        | '(' parameterTypeList? ')' resultTypeList? ('frame' frameSpecList )? ('pre' pre=expression)? ('post' post=expression)
            {
                ILexLocation location = extractLexLocation($id);
                ILexNameToken name=new CmlLexNameToken("", $id.getText(), location);
                List<APatternListTypePair> parameterPatterns= ($parameterTypeList.ptypes==null? new ArrayList<APatternListTypePair>():$parameterTypeList.ptypes);
                List<APatternTypePair> result = $resultTypeList.rtypes;

                List<PPattern> resultNames = new Vector<PPattern>();
                List<PType> resultTypes = new Vector<PType>();

                APatternTypePair resultPattern = null;

                if (result!=null) {
                    for (APatternTypePair plt : result) {
                        resultNames.add(plt.getPattern().clone());
                        resultTypes.add(plt.getType().clone());
                    }

                    if (resultNames.size() > 1) {
                        resultPattern = AstFactory.newAPatternTypePair(AstFactory.newATuplePattern(resultNames.get(0).getLocation(), resultNames), AstFactory.newAProductType(resultNames.get(0).getLocation(), resultTypes));
                    } else if (!resultNames.isEmpty()) {
                        resultPattern = AstFactory.newAPatternTypePair(resultNames.get(0), resultTypes.get(0));
                    }
                }

                List<PType> ptypes = new Vector<PType>();

                for (APatternListTypePair ptp : parameterPatterns) {
                    ptypes.addAll(getTypeList(ptp));
                }

                AOperationType operationType = AstFactory.newAOperationType(location, ptypes, (result == null ? AstFactory.newAVoidType(location) : resultPattern.getType()));

                PStm body=null;
                ASpecificationStm spec=AstFactory.newASpecificationStm(location, $frameSpecList.frameSpecs, $pre.exp, $post.exp, null);
                AImplicitOperationDefinition opdef = AstFactory.newAImplicitOperationDefinition(name, parameterPatterns, resultPattern, body, spec);
                opdef.setType(operationType);
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
            ILexLocation loc = extractLexLocation(domType.getLocation(),rngType.getLocation());
            List<PType> typeList = new ArrayList<PType>();
            if (domType instanceof AProductType) {
                AProductType apt = (AProductType)domType;
                for (PType t : apt.getTypes()) {
                    typeList.add(t);
                }
            } else {
                if (!(domType instanceof AVoidType))
                   typeList.add(domType);
             }
            AOperationType opType = AstFactory.newAOperationType(loc, typeList, rngType);

            //the parameter types are graph so reset parent - because of product rewrite
            for (PType t : opType.getParameters()) {
                t.parent(opType);
            }

            $type = opType;
        }
    ;

operationBody returns[PAction body]
@after { $body.setLocation(extractLexLocation($start, $stop)); }
    : action                           { $body = $action.action; }
    | isT='is' 'not' 'yet' specT='specified'     { $body = stm2action(AstFactory.newANotYetSpecifiedStm(extractLexLocation($isT,$specT))); }
    | isT='is' 'subclass' resT='responsibility' { $body = stm2action(AstFactory.newASubclassResponsibilityStm(extractLexLocation($isT,$resT))); }
    ;


typeDefs returns[List<ATypeDefinition> defs]
@init { $defs = new ArrayList<ATypeDefinition>(); }
    : 'types' ( '[' IDENTIFIER ']' )? ( typeDef { $defs.add($typeDef.def); } )*
    ;

typeDef returns[ATypeDefinition def]
@after { $def.setLocation(extractLexLocation($start, $stop)); }
    : QUALIFIER? IDENTIFIER '=' type invariant?
        {
            CmlLexNameToken name = new CmlLexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            ANamedInvariantType invType = AstFactory.newANamedInvariantType(name, $type.type);
            $def = AstFactory.newATypeDefinition(name,invType,$invariant.pattern,$invariant.exp);
            $def.setAccess(extractQualifier($QUALIFIER));

        }
    | QUALIFIER? IDENTIFIER '::' fieldOptList invariant?
        {
            CmlLexNameToken name = new CmlLexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            ARecordInvariantType invType = AstFactory.newARecordInvariantType(name, $fieldOptList.fieldList);
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
@init { List<PType> typeList = new ArrayList<PType>(); ILexLocation last = null; }
@after { $type.setLocation(extractLexLocation($start, $stop)); }
    : first=type1 ('|' typeItem=type1 { typeList.add($typeItem.type); last = $typeItem.type.getLocation(); } )*
        {
            if (typeList.size()==0) {
                $type = $first.type;
            } else {
                ILexLocation loc = extractLexLocation($first.type.getLocation(), last);
                AUnionType union = new AUnionType(loc, false, false, false);
                union.setProdCard(-1);
                typeList.add(0, $first.type);
                union.setTypes(typeList);
                $type = union;
            }
        }
    ;

type1 returns[PType type]
@init { List<PType> typeList = new ArrayList<PType>(); ILexLocation last = null; }
@after { $type.setLocation(extractLexLocation($start, $stop)); }
    : first=typebase ('*' typeItem=typebase  { typeList.add($typeItem.type); last = $typeItem.type.getLocation(); } )*
        {
            if (typeList.size()==0) {
                $type = $first.type;
            } else {
                ILexLocation loc = extractLexLocation($first.type.getLocation(), last);
                typeList.add(0, $first.type);
                $type = new AProductType(loc, false, null, typeList);
            }
        }
    ;

typebase returns[PType type]
@after { $type.setLocation(extractLexLocation($start, $stop)); }
    : basicType           { $type = $basicType.basicType; }
    | '(' inside=type ')' { $type = new ABracketType(null, false, null, $inside.type); }
    | '[' inside=type ']' { $type = new AOptionalType(null, false, null, $inside.type); }
    | QUOTELITERAL
        {
            ILexLocation loc = extractLexLocation($QUOTELITERAL);
            String str = $QUOTELITERAL.getText();
            str = str.substring(1,str.length()-1);
            $type = new AQuoteType(loc, false, null, new LexQuoteToken(str, loc));
        }
    | name
        {
            CmlLexNameToken tname = $name.name;
            ILexLocation loc = tname.getLocation();
            $type = new ANamedInvariantType(loc, false, null, false, false, null, tname,
                                            new AUnresolvedType(loc, false, new ArrayList<PDefinition>(), tname));
        }
    | 'set' 'of' sub=typebase                   { $type = AstFactory.newASetType(null,  $sub.type); }
    | 'seq' 'of' sub=typebase                   { $type = new ASeqSeqType(null, false, null, $sub.type, false); }
    | 'seq1' 'of' sub=typebase                  { $type = new ASeq1SeqType(null, false, null, $sub.type, false); }
    | 'map' from=type 'to' to=typebase      { $type = new AMapMapType(null, false, null, $from.type, $to.type, false); }
    | 'inmap' from=type 'to' to=typebase    { $type = new AInMapMapType(null, false, null, $from.type, $to.type, false); }
    | 'compose' IDENTIFIER 'of' fieldOptList 'end'
        {
            CmlLexNameToken name = new CmlLexNameToken("", $IDENTIFIER.getText(), extractLexLocation($IDENTIFIER));
            $type = AstFactory.newARecordInvariantType(name, $fieldOptList.fieldList);
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

fieldOptList returns[List<AFieldField> fieldList]
@init { $fieldList = new ArrayList<AFieldField>(); }
    : ( item=field { $fieldList.add($item.field); } )*
    ;

field returns[AFieldField field]
@init { AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(false,false,null); }
    : type
        {
            ILexLocation loc = $type.type.getLocation();
            CmlLexNameToken name = new CmlLexNameToken("", new LexIdentifierToken("",false,loc));
            $field = new AFieldField(null, name, null, $type.type, false);
        }
    | IDENTIFIER ( ':' | eqAbs=':-' ) type
        {
            String idStr = $IDENTIFIER.getText();
            CmlLexNameToken name = new CmlLexNameToken("", idStr, extractLexLocation($IDENTIFIER));
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
            ILexLocation loc = extractLexLocation($IDENTIFIER);
            CmlLexNameToken name = new CmlLexNameToken("", $IDENTIFIER.getText(), loc, false, true); // ?? why is the last bool true?
            $pattern = new AIdentifierPattern(loc, null, true, name, false);
        }
    | minus='-'
        {
            $pattern = new AIgnorePattern(extractLexLocation($minus), null, true,null);
        }
    ;

matchValue returns[PPattern pattern]
    : lit=symbolicLiteral
        {
            if ($lit.literal instanceof LexIntegerToken) {
                $pattern = new AIntegerPattern($lit.literal.getLocation(), null, true, (LexIntegerToken)$lit.literal);
            } else if ($lit.literal instanceof LexRealToken) {
                $pattern = new ARealPattern($lit.literal.getLocation(), null, true, (LexRealToken)$lit.literal);
            } else if ($lit.literal instanceof LexBooleanToken) {
                $pattern = new ABooleanPattern($lit.literal.getLocation(), null, true, (LexBooleanToken)$lit.literal);
            } else if ($lit.literal instanceof LexKeywordToken) {
                // Note, this assumes that lit only ever
                // gives a LexKeywordToken for 'nil'
                $pattern = new ANilPattern($lit.literal.getLocation(), null, true);
            } else if ($lit.literal instanceof LexCharacterToken) {
                $pattern = new ACharacterPattern($lit.literal.getLocation(), null, true, (LexCharacterToken)$lit.literal);
            } else if ($lit.literal instanceof LexStringToken) {
                $pattern = new AStringPattern($lit.literal.getLocation(), null, true, (LexStringToken)$lit.literal);
            } else if ($lit.literal instanceof LexQuoteToken) {
                $pattern = new AQuotePattern($lit.literal.getLocation(), null, true, (LexQuoteToken)$lit.literal);
            } else {
                // FIXME log a never-happens error
            }
        }
    | l='(' expression r=')'
        {
            ILexLocation loc = extractLexLocation($l,$r);
            $pattern = new AExpressionPattern(loc, null, false, $expression.exp);
        }
    ;

symbolicLiteral returns[ILexToken literal]
    : NUMERIC
        {
            ILexLocation loc = extractLexLocation($NUMERIC);
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
            ILexLocation loc = extractLexLocation($HEXLITERAL);
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
            ILexLocation loc = extractLexLocation($CHARLITERAL);
            String res = $CHARLITERAL.getText();
            res = res.replace("'", "");
            $literal = new LexCharacterToken(convertEscapeToChar(res), loc);
        }
    | TEXTLITERAL
        {
            ILexLocation loc = extractLexLocation($TEXTLITERAL);
            String str = $TEXTLITERAL.getText();
            str = str.substring(1,str.length()-1);
            $literal = new LexStringToken(str, loc);
        }
    | QUOTELITERAL
        {
            ILexLocation loc = extractLexLocation($QUOTELITERAL);
            String str = $QUOTELITERAL.getText();
            str = str.substring(1,str.length()-1);
            $literal = new LexQuoteToken(str, loc);
        }
    ;

tuplePattern returns[PPattern pattern]
@init { List<PPattern> patList = new ArrayList<PPattern>(); }
    : MKUNDER '(' first=pattern ( ',' patItem=pattern { patList.add($patItem.pattern); } )* end=')'
        {
            ILexLocation loc = extractLexLocation($MKUNDER,$end);
            patList.add(0, $first.pattern);
            $pattern = new ATuplePattern(loc, null, true, patList);
        }
    ;

recordPattern returns[PPattern pattern]
@init { List<PPattern> patList = new ArrayList<PPattern>(); }
    : MKUNDER name '(' ( first=pattern { patList.add($first.pattern); } ( ',' patItem=pattern { patList.add($patItem.pattern); } )* )? end=')'
        {
            ILexLocation loc = extractLexLocation($MKUNDER,$end);
            ARecordPattern recPattern = new ARecordPattern(loc, null, false, $name.name, patList);
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
            ILexLocation eloc = $expression.exp.getLocation();
            for (PPattern p : $patternList.patterns) {
                ILexLocation loc = extractLexLocation(p.getLocation(), eloc);
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
@init { ILexLocation loc = null; String opStr = null; }
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
                ILexLocation loc = extractLexLocation($e1.exp,$e2.exp);
                SBinaryExpBase op = $binOpRel.op;
                op.setLocation(loc);
                op.setLeft($e1.exp);
                op.setRight($e2.exp);
                $exp = op;
            }
        }
    ;

binOpEval1 returns[SBinaryExpBase op]
@init { ILexLocation loc = null; String opStr = null; }
@after { op.setLocation(loc); op.setOp(extractLexToken(opStr, loc)); }
    : o='+'      { $op = new APlusNumericBinaryExp();      loc = extractLexLocation($o); opStr = $o.getText(); }
    | o='-'      { $op = new ASubtractNumericBinaryExp(); loc = extractLexLocation($o); opStr = $o.getText(); }
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
                ILexLocation loc = extractLexLocation($e1.exp,$e2.exp);
                SBinaryExpBase op = $binOpEval1.op;
                op.setLocation(loc);
                op.setLeft($e1.exp);
                op.setRight($e2.exp);
                $exp = op;
            }
        }
    ;

binOpEval2 returns[SBinaryExpBase op]
@init { ILexLocation loc = null; String opStr = null; }
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
                ILexLocation loc = extractLexLocation($e1.exp,$e2.exp);
                SBinaryExpBase op = $binOpEval2.op;
                op.setLocation(loc);
                op.setLeft($e1.exp);
                op.setRight($e2.exp);
                $exp = op;
            }
        }
    ;

binOpEval3 returns[SBinaryExpBase op]
@init { ILexLocation loc = null; String opStr = null; }
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
                ILexLocation loc = extractLexLocation($e1.exp,$e2.exp);
                SBinaryExpBase op = $binOpEval3.op;
                op.setLocation(loc);
                op.setLeft($e1.exp);
                op.setRight($e2.exp);
                $exp = op;
            }
        }
    ;

binOpEval4 returns[SBinaryExpBase op]
@init { ILexLocation loc = null; String opStr = null; }
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
                ILexLocation loc = extractLexLocation($e1.exp,$e2.exp);
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
            //FIXME find out how close this is to selectorListAssignableBuilder
            for (PExp sel : $selectorOptList.selectors) { // Iterate through the selectors, building a left->right tree
                ILexLocation loc = extractLexLocation($exp.getLocation(), sel.getLocation());
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
                        ILexIdentifierToken id = aupe.getIdentifiers().get(0);
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
            ILexLocation loc = extractLexLocation($l, $r);
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
            ILexLocation loc = extractLexLocation($TUPLESELECTOR);
            String num = $TUPLESELECTOR.getText().substring(2);
            LexIntegerToken fieldnum = new LexIntegerToken(Long.parseLong(num), loc);
            $exp = new AFieldNumberExp(loc, null, fieldnum);
        }
    | t=('.'|'`') IDENTIFIER // field select, usually: it can only be a name *if* the thing immediately left of the dot is an identifier (but not guaranteed)
        {
            ILexLocation loc = extractLexLocation($t,$IDENTIFIER);
            ILexLocation idloc = extractLexLocation($IDENTIFIER);
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
            ILexLocation loc = extractLexLocation($SELF);
            CmlLexNameToken name = new CmlLexNameToken("", $SELF.getText(), loc);
            $exp = new ASelfExp(loc, name);
        }
    | IDENTIFIER old='~'?
        {
            boolean isOld = ($old != null);
            String origName = $IDENTIFIER.getText();
            ILexLocation loc = extractLexLocation($IDENTIFIER);
            if (isOld) {
                loc = extractLexLocation(loc, extractLexLocation($old));
                origName = origName + "~";
            }
            CmlLexNameToken name = new CmlLexNameToken("", $IDENTIFIER.getText(), loc, isOld, false);
            $exp = new AVariableExp(loc, name, origName);
        }
    | symbolicLiteralExpr
        {
            $exp = $symbolicLiteralExpr.exp;
        }
    | eseq='[]'
        {
            ILexLocation loc = extractLexLocation($eseq);
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
            for (ACaseAlternative alt : $caseExprAltList.alts) {
                alt.setCexp($cexp.exp.clone());
            }
            $exp = new ACasesExp(null, $cexp.exp, $caseExprAltList.alts, $oexp.exp);
        }
    ;

symbolicLiteralExpr returns[PExp exp]
    : lit=symbolicLiteral
        {
            if ($lit.literal instanceof LexIntegerToken) {
                $exp = new AIntLiteralExp($lit.literal.getLocation(), (LexIntegerToken)$lit.literal);
            } else if ($lit.literal instanceof LexRealToken) {
                $exp = new ARealLiteralExp($lit.literal.getLocation(), (LexRealToken)$lit.literal);
            } else if ($lit.literal instanceof LexBooleanToken) {
                $exp = new ABooleanConstExp($lit.literal.getLocation(), (LexBooleanToken)$lit.literal);
            } else if ($lit.literal instanceof LexKeywordToken) {
                // Note, this assumes that lit only ever
                // gives a LexKeywordToken for 'nil'
                ILexLocation location = $lit.literal.getLocation();
                $exp = new ANilExp(location);
                $exp.setType(AstFactory.newAUnknownType(location));
            } else if ($lit.literal instanceof LexCharacterToken) {
                $exp = new ACharLiteralExp($lit.literal.getLocation(), (LexCharacterToken)$lit.literal);
            } else if ($lit.literal instanceof LexStringToken) {
                ASeqSeqType charSeqType = new ASeqSeqType($lit.literal.getLocation(),
                                                          true, null,
                                                          new ACharBasicType(),
                                                          (((LexStringToken)$lit.literal).value.length() == 0));
                $exp = new AStringLiteralExp(charSeqType, $lit.literal.getLocation(), (LexStringToken)$lit.literal);
            } else if ($lit.literal instanceof LexQuoteToken) {
                $exp = new AQuoteLiteralExp($lit.literal.getLocation(), (LexQuoteToken)$lit.literal);
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
                        ILexLocation mloc = extractLexLocation($fexp.exp.getLocation(), $texp.exp.getLocation());
                        mexps.add(new AMapletExp(mloc,$fexp.exp,$texp.exp));
                    }
                )+
            )?
            {
                ILexLocation mloc = extractLexLocation($first.exp.getLocation(), $firstto.exp.getLocation());
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
            ILexLocation loc = $first.pattern.getLocation();
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
            ILexLocation loc = $bindablePattern.pattern.getLocation();
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
            ILexLocation loc = extractLexLocation($bindablePattern.pattern.getLocation(), $expression.exp.getLocation());
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
            ILexLocation loc = extractLexLocation($bindablePattern.pattern.getLocation(), $type.type.getLocation());
            $binding = new ATypeBind(loc, $bindablePattern.pattern, $type.type);
        }
    ;

identifierList returns[List<LexIdentifierToken> ids]
@init { $ids = new ArrayList<LexIdentifierToken>(); }
    : item=IDENTIFIER { $ids.add(new LexIdentifierToken($item.getText(), false, extractLexLocation($item))); }
        ( ',' item=IDENTIFIER { $ids.add(new LexIdentifierToken($item.getText(), false, extractLexLocation($item))); } )*
    ;

nameList returns[List<CmlLexNameToken> names]
@init { $names = new ArrayList<CmlLexNameToken>(); }
    : item=name { $names.add($item.name); } ( ',' item=name { $names.add($item.name); } )*
    ;

name returns[CmlLexNameToken name]
    : (ids+=IDENTIFIER ('.'|'`'))* identifier=IDENTIFIER
        {
            // FIXME: not setting the filename field
            // Grab the location of the last identifier as default
            ILexLocation loc = extractLexLocation($identifier);
            // default to a blank module
            StringBuilder module = new StringBuilder();
            if ($ids != null) {
                // fix the name location
                ILexLocation firstLoc = extractLexLocation((CommonToken)$ids.get(0));
                loc = new LexLocation(loc.getFile(),
                                      "", //FIXME: I assume this is the local module name?
                                      firstLoc.getStartLine(), firstLoc.getStartPos(),
                                      loc.getEndLine(), loc.getEndPos(),
                                      firstLoc.getStartOffset(), loc.getEndOffset());
                // create the module string
                for (Object t : $ids) {
                    module.append(((CommonToken)t).getText());
                    module.append('.');
                }
                module.deleteCharAt(module.length() - 1);
            }
            $name = new CmlLexNameToken(module.toString(), $identifier.getText(), loc);
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

// ISUNDERBASIC
//     : 'is_' ('bool' | 'nat' | 'nat1' | 'int' | 'rat' | 'real' | 'char' | 'token')
//     ;

/* FIXME Need to fix this, yet
 * We should have a java method that converts things like \n into newline and such.
 */
TEXTLITERAL
    : '"' ('\\"' | ~'"' )* '"'
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
    : ( '//' | '--' ) ~('\r'|'\n')*  { $channel=HIDDEN; }
    ;

MLINECOMMENT
    : '/*' .* '*/' { $channel=HIDDEN; }
    ;
