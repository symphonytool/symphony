%require "2.5"
%language "java"
%locations
%define parser_class_name "CmlParser"
//%define access "public"
%define package "eu.compassresearch.core.parser"
%code imports{

// ******************************
// *** required local imports ***
// ******************************

// required standard Java definitions
    import java.util.*;
    import java.io.File;
    import java.lang.reflect.*;
    import eu.compassresearch.ast.definitions.*;
    import eu.compassresearch.ast.declarations.*;
    import eu.compassresearch.ast.expressions.*;
    import eu.compassresearch.ast.statements.*;
    import eu.compassresearch.ast.actions.*;
    import eu.compassresearch.ast.process.*;
    import eu.compassresearch.ast.patterns.*;
    import eu.compassresearch.ast.program.*;
    import eu.compassresearch.ast.types.*;
    import eu.compassresearch.ast.lex.*;
    import eu.compassresearch.ast.typechecker.NameScope;
    import eu.compassresearch.ast.node.*;
    // import org.overture.transforms.*;
    import eu.compassresearch.ast.util.*;
    import eu.compassresearch.core.lexer.CmlLexeme;
    import eu.compassresearch.core.lexer.CmlLexer;
    import eu.compassresearch.core.lexer.Position;
    public
}

%code{
  // **************************
  // *** PARSER PUBLIC INFO ***
  // **************************
  public static class Info {
    public static final String CML_LANG_VERSION = "CML 0";
  };

  public class CustomSyntaxErrorException extends RuntimeException
  {
  }

  // **************************
  // *** PARSER INTERNAL DS ***
  // **************************
  class PatternWithVDMType {
    public PType type;
    public PPattern pattern;
  }

  // ************************
  // *** MEMBER VARIABLES ***
  // ************************

  private ASourcefileSourcefile currentSourceFile = null;

  // *************************
  // *** PRIVATE OPERATIONS ***
  // *************************

  /* FIXME
   * needs to throw an error if the name is multipart
   */
  private List<LexIdentifierToken> convertNameListToIdentifierList(List<LexNameToken> nameList) {
    List<LexIdentifierToken> out = new Vector<LexIdentifierToken>();
    for (LexNameToken name : nameList) {
      out.add(extractLexIdentifierToken(name));
    }
    return out;
  }

  private PStateDesignator convertToStateDesignator(PDesignator designator)
  {
    PStateDesignator sd = null;
    if(designator instanceof ANameDesignator) {
      ANameDesignator ns = (ANameDesignator)designator;
      LexNameToken name = extractLexNameToken(ns.getName());
      sd = new AIdentifierStateDesignator(name.getLocation(), null, name);
    } else if (designator instanceof APrimaryDesignator) {
      APrimaryDesignator primary = (APrimaryDesignator)designator;
      if(primary.getPrimary() instanceof ACallPrimary) {
	ACallCallStatementControlStatementAction call = (ACallCallStatementControlStatementAction) ((ACallPrimary)primary.getPrimary()).getCall();
	if(call.getArgs().size() != 1) {
	  throw new RuntimeException("Missing implementation in convertToStateDesignator method. Throw nice error about map ot sequence apply only takes one arg");
	}
	sd = new AMapSeqStateDesignator(call.getLocation(), null, call.getArgs().get(0));
      } else {
	throw new RuntimeException("Missing implementation in convertToStateDesignator method. Currently only ACallPrimary is implemented");
      }
    } else {
      throw new RuntimeException("Missing implementation in convertToStateDesignator method. sd was null");
    }
    return sd;
  }

  private LexNameToken getNameTokenFromMKUNDERNAME(CmlLexeme mkUnderName)
  {
    LexNameToken name = new LexNameToken("Default",
					 mkUnderName.getValue().split("_")[1],
					 extractLexLocation(mkUnderName),
					 false,
					 true);
    return name;
  }

  private LexNameToken extractLexNameToken(ASimpleName sn)
  {
    LexNameToken lnt = null;
    if (sn.getIdentifiers().size() > 2) {
      throw new CustomSyntaxErrorException();
    } else if (sn.getIdentifiers().size() == 2) {
      lnt = new LexNameToken(sn.getIdentifiers().get(0).getName(), sn.getIdentifiers().get(1).getName(),
			     combineLexLocation(extractFirstLexLocation(sn.getIdentifiers()),
						extractLastLexLocation(sn.getIdentifiers())));
    } else {
      lnt = new LexNameToken("Default", sn.getIdentifiers().get(0));
    }
    return lnt;
  }

  private LexLocation extractLexLocation(CmlLexeme lexeme)
  {
    return new LexLocation(currentSourceFile.getFile(), "Default",
			   lexeme.getStartPos().line, lexeme.getStartPos().column,
			   lexeme.getEndPos().line, lexeme.getEndPos().column,0,0);
  }

  private LexLocation extractLexLocation(CmlLexeme start, CmlLexeme end)
  {
    return new LexLocation(currentSourceFile.getFile(), "Default",
			   start.getStartPos().line, start.getStartPos().column,
			   end.getEndPos().line, end.getEndPos().column,0,0);
  }

  private LexLocation extractLexLocation(CmlLexeme start, LexLocation end)
  {
    return new LexLocation(currentSourceFile.getFile(), "Default",
			   start.getStartPos().line, start.getStartPos().column,
			   end.endLine, end.endPos,0,0);
  }

  private LexLocation extractLexLocation(LexLocation start, CmlLexeme end)
  {
    return new LexLocation(currentSourceFile.getFile(), "Default",
			   start.endLine, start.endPos,
			   end.getStartPos().line, end.getStartPos().column,0,0);
  }

  private LexLocation combineLexLocation(LexLocation start, LexLocation end)
  {
    return new LexLocation(currentSourceFile.getFile(), "Default",
			   start.startLine, start.startPos,
			   end.endLine, end.endPos,0,0);
  }

  private LexLocation extractLastLexLocation(List<?> fields)
  {
    try {
      Object o = fields.get(0);
      Class<?> clz = o.getClass();

      Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});

      LexLocation candidate = (LexLocation)locMethod.invoke(o, null);
      for(Object p : fields) {
	LexLocation pLoc = (LexLocation)locMethod.invoke(o, null);
	if (pLoc.endOffset > candidate.endOffset)
	  candidate = pLoc;
      }
      return candidate;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private LexLocation extractFirstLexLocation(List<?> fields)
  {
    try {
      Object o = fields.get(0);
      Class<?> clz = o.getClass();

      Method locMethod = clz.getMethod("getLocation", new Class<?>[] {});

      LexLocation candidate = (LexLocation)locMethod.invoke(o, null);
      for(Object p : fields) {
	LexLocation pLoc = (LexLocation)locMethod.invoke(o, null);
	if (pLoc.startOffset < candidate.startOffset)
	  candidate = pLoc;
      }
      return candidate;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private< T extends PPattern> LexLocation extractLexLeftMostFromPatterns(List<T> ptrns)
  {
    LexLocation candidate = ptrns.get(0).getLocation();
    for(PPattern p : ptrns) {
      if (p.getLocation().endOffset > candidate.endOffset)
	candidate = p.getLocation();
    }
    return candidate;
  }

  private LexNameToken extractLexNameToken(CmlLexeme lexeme)
  {
    return new LexNameToken("Default",lexeme.getValue(), extractLexLocation(lexeme),false, true);
  }

  private LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme)
  {
    return new LexIdentifierToken(lexeme.getValue(), false, extractLexLocation(lexeme));
  }

  private LexIdentifierToken extractLexIdentifierToken(LexNameToken name)
  {
    return new LexIdentifierToken(name.getName(), false, name.getLocation());
  }

  // *************************
  // *** PUBLIC OPERATIONS ***
  // *************************

  public void setDocument(ASourcefileSourcefile doc)
  {
    currentSourceFile = doc;
  }

  public ASourcefileSourcefile getDocument()
  {
    return currentSourceFile;
  }

  public static void main(String[] args) throws Exception
  {
    if (args.length == 0) {
      System.out.println("Usage : java CmlParser <inputfile>");
    } else {
      CmlLexer scanner = null;
      try {
	String filePath = args[0];
	ClonableFile file = new ClonableFile(filePath);
	ASourcefileSourcefile currentSourceFile = new ASourcefileSourcefile();
	currentSourceFile.setName(file.getName());
	currentSourceFile.setFile(file);
	scanner = new CmlLexer(new java.io.FileReader(file));
	CmlParser cmlParser = new CmlParser(scanner);
	cmlParser.setDocument(currentSourceFile);
	//cmlParser.setDebugLevel(1);

	//do {
	//System.out.println(scanner.yylex());
	boolean result = cmlParser.parse();
	if (result) {
	  System.out.println("parsed!");

	  //DotGraphVisitor dgv = new DotGraphVisitor();
	  INode node = cmlParser.getDocument();

	  //node.apply(dgv,null);

	  File dotFile = new File("generatedAST.gv");
	  java.io.FileWriter fw = new java.io.FileWriter(dotFile);
	  //fw.write(dgv.getResultString());
	  fw.close();

	  //System.out.println(dgv.getResultString());
	} else {
	  System.out.println("Not parsed!");
	  System.exit(-1);
	}

	//} while (!scanner.zzAtEOF);
      } catch (java.io.FileNotFoundException e) {
	System.out.println("File not found : \""+args[0]+"\"");
	System.exit(-2);
      } catch (java.io.IOException e) {
	System.out.println("IO error scanning file \""+args[0]+"\"");
	System.out.println(e);
	System.exit(-3);
      } catch (Exception e) {
	System.out.println("Unexpected exception:");
	e.printStackTrace();
	System.exit(-4);
      }
    }
  }

/* End of code block */
}

/* ---------------------------------------------------------------- */
/* Token declarations                                               */
/* ---------------------------------------------------------------- */

%token CLASS END PROCESS INITIAL EQUALS AT BEGIN ACTIONS BARTILDEBAR LRSQUARE
%token TBAR LPAREN RPAREN LRPAREN LSQUARE RSQUARE CSPSKIP CSPSTOP CSPCHAOS
%token CSPDIV CSPWAIT RARROW LARROW LCURLY RCURLY BAR DBAR CHANNELS CHANSETS
%token TYPES SEMI DCOLON COMPOSE OF STAR TO INMAPOF MAPOF SEQOF SEQ1OF SETOF
%token PLUSGT COLONDASH DEQUALS INV VALUES FUNCTIONS PRE POST MEASURE
%token SUBCLASSRESP NOTYETSPEC OPERATIONS FRAME RD WR STATE LET IN IF THEN
%token ELSEIF ELSE CASES OTHERS PLUS MINUS ABS FLOOR NOT CARD POWER DUNION
%token DINTER HD TL LEN ELEMS INDS REVERSE CONC DOM RNG MERGE INVERSE
%token ELLIPSIS BARRARROW MKUNDER MKUNDERNAME DOT DOTHASH NUMERAL LAMBDA NEW
%token SELF ISUNDER PREUNDER ISOFCLASS TILDE DCL COLONEQUALS ATOMIC DEQRARROW
%token RETURN IDENTIFIER BACKTICK SLASH DIVIDE REM MOD LT LTE GT GTE NEQ OR
%token AND EQRARROW LTEQUALSGT INSET NOTINSET SUBSET PROPER_SUBSET UNION
%token BACKSLASH INTER CARET DPLUS MAPMERGE LTCOLON LTDASHCOLON COLONGT
%token COLONDASHGT COMP DSTAR FORALL EXISTS EXISTS1 STRING VRES RES VAL
%token HEX_LITERAL QUOTE_LITERAL AMP LSQUAREBAR DLSQUARE DRSQUARE BARRSQUARE
%token COMMA LSQUAREDBAR DBARRSQUARE COLON LCURLYBAR BARRCURLY QUESTION BANG
%token SLASHCOLON SLASHBACKSLASH COLONBACKSLASH LSQUAREGT BARGT ENDSBY
%token STARTBY COLONINTER COLONUNION LCURLYCOLON COLONRCURLY MU PRIVATE
%token PROTECTED PUBLIC LOGICAL DOTCOLON
%token TBOOL TNAT TNAT1 TINT TRAT TREAL TCHAR TTOKEN

%token nameset namesetExpr booleanLiteral nilLiteral characterLiteral textLiteral

/* ---------------------------------------------------------------- */
/* Precidence declarations                                          */
/* ---------------------------------------------------------------- */

/* Precidence from loosest to tightest; tokens on same line are equal precidence */
%right LPAREN
%right COMMA
%left SEQOF
%left BARTILDEBAR LRSQUARE TBAR AMP RARROW DLSQUARE LSQUAREBAR LSQUAREGT
      BARRSQUARE LSQUARE RSQUARE SETOF SEQ1OF MAPOF INMAPOF PLUSGT TO OF
      NEW COLONEQUALS SLASH BACKSLASH ENDSBY STARTBY LSQUAREDBAR DBARRSQUARE
      DBAR SLASHCOLON SLASHBACKSLASH COLONBACKSLASH SEMI COLONINTER
      COLONUNION BARGT
%nonassoc ELSE ELSEIF
%left BAR
/* unary ops */
%right UPLUS UMINUS ABS FLOOR NOT CARD POWER DUNION DINTER HD TL LEN ELEMS
       INDS REVERSE CONC DOM RNG MERGE INVERSE
/* binary ops */
%left PLUS MINUS DIVIDE REM MOD LT LTE GT GTE EQUALS NEQ OR AND EQRARROW
      LTEQUALSGT INSET STAR NOTINSET SUBSET PROPER_SUBSET UNION INTER CARET
      DPLUS MAPMERGE LTCOLON LTDASHCOLON COLONGT COLONDASHGT COMP DSTAR IN
%left DOT DOTHASH DOTCOLON
%left LRPAREN

/* ---------------------------------------------------------------- */
/* Initial rule declaration                                         */
/* ---------------------------------------------------------------- */
%start sourceFile
%%

/* ---------------------------------------------------------------- */
/* The rules                                                        */
/* ---------------------------------------------------------------- */

sourceFile :
  programParagraphList
{
    List<PDeclaration> decls = (List<PDeclaration>) $1;
    currentSourceFile.setDecls(decls);
}
| globalDecl programParagraphList
{
    List<PDeclaration> globalDecls = (List<PDeclaration>)$1;
    List<PDeclaration> decls = (List<PDeclaration>) $2;
    decls.addAll(globalDecls);
    currentSourceFile.setDecls(decls);
}
| globalDecl
{
    List<PDeclaration> globalDecls = (List<PDeclaration>)$1;
    currentSourceFile.setDecls(globalDecls);
}
;

programParagraphList :
  programParagraph
{
  List<PDeclaration> programParagraphList = new Vector<PDeclaration>();
  programParagraphList.add((PDeclaration)$1);
  $$ = programParagraphList;
}
| programParagraphList programParagraph
{
  List<PDeclaration> programParagraphList = (List<PDeclaration>)$1;
  if (programParagraphList == null)
    programParagraphList = new Vector<PDeclaration>();
  programParagraphList.add((PDeclaration)$2);
  $$ = programParagraphList;
}
;

programParagraph :
  classDecl                                       { $$ = $1; }
| processDecl                                     { $$ = $1; }
| channelDecl                                     { $$ = $1; }
| chansetDecl                                     { $$ = $1; }
;

classDecl :
  CLASS IDENTIFIER EQUALS classBody
{
  AClassbodyDefinition c = new AClassbodyDefinition();
  CmlLexeme id = (CmlLexeme)$2;
  Position startPos =  ((CmlLexeme)$1).getStartPos();
  Position endPos = ((CmlLexeme)$3).getEndPos(); // TODO Fix me, the ending position is the
  LexNameToken lexName = extractLexNameToken(id);
  LexIdentifierToken classIdent = extractLexIdentifierToken(id);
  LexLocation loc = new LexLocation(currentSourceFile.getFile(), id.getValue(), startPos.line, startPos.column,
				    endPos.line, endPos.column, startPos.offset, endPos.offset);
  c.setLocation(loc);
  c.setName(lexName);
  c.setDeclarations((List<PDeclaration>)$4);
  //  c.setDefinitions((List)$4);
  AClassDeclaration res = new AClassDeclaration();
  res.setClassBody(c);
  res.setLocation(loc);
  res.setIdentifier(classIdent);
  res.setNameScope(NameScope.CLASSNAME);
  $$ = res;
}
;

processDecl :
  PROCESS IDENTIFIER EQUALS processDef
{
  LexLocation processLoc = extractLexLocation((CmlLexeme)$1);
  AProcessDefinition processDef = (AProcessDefinition)$4;
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$2);
  LexLocation location = combineLexLocation(processLoc, processDef.getLocation());
  $$ = new AProcessDeclaration(location, NameScope.GLOBAL, id, processDef);
}
;

processDef :
  declaration AT process
{
  PProcess process = (PProcess)$3;
  List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)$1;
  LexLocation loc = combineLexLocation(extractFirstLexLocation(decls), process.getLocation());
  $$ = new AProcessDefinition(loc, NameScope.GLOBAL, false, null, decls, process);
}
| process
{
  PProcess process = (PProcess)$1;
  $$ = new AProcessDefinition(process.getLocation(), NameScope.GLOBAL, false, null, null, process);
}
;

process :
/* actions */
  BEGIN AT action END
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$4);
  List<PDeclaration> processDeclarations = null;
  PAction action = (PAction)$3;
  $$ = new AStateProcess(location, processDeclarations, action);
}
| BEGIN processParagraphList AT action END
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$5);
  List<PDeclaration> processDeclarations = (List<PDeclaration>)$2;
  PAction action = (PAction)$4;
  $$ = new AStateProcess(location, processDeclarations, action);
}
/* actions end */
| process SEMI process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  $$ = new ASequentialCompositionProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
}
| process LRSQUARE process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  $$ = new AExternalChoiceProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
}
| process BARTILDEBAR process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  $$ = new AInternalChoiceProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
}
| process LSQUAREBAR chansetExpr BARRSQUARE process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$5;
  $$ = new AGeneralisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (SChansetSetExp)$3, right);
}
| process LSQUARE chansetExpr DBAR chansetExpr RSQUARE process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$7;
  $$ = new AAlphabetisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (SChansetSetExp)$3, (SChansetSetExp)$5, right);
}
| process DBAR process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  $$ = new ASynchronousParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
}
| process TBAR process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  $$ = new AInterleavingProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, right);
}
| process SLASHBACKSLASH process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new AInterruptProcess(location, left, right);
}
/* DEVIATION
 * grammar:
 *   process '/' expression '\' process
 * this conflicts all over, so
 *   process '/:' expression ':\' process
 * (FIXME)
 */
| process SLASHCOLON expression COLONBACKSLASH process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$5;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ATimedInterruptProcess(location, left, (PExp)$3, right);
}
| process LSQUAREGT process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new AUntimedTimeoutProcess(location, left, right);
}
| process LSQUARE expression BARGT process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$5;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ATimeoutProcess(location, left, (PExp)$3, right);
}
/* DEVIATION
 * grammar:
 *   process '\' chansetExpr
 * here:
 *   process ':\' chansetExpr
 */
| process COLONBACKSLASH chansetExpr
{
  PProcess left = (PProcess)$1;
  SChansetSetExp cse = (SChansetSetExp)$3;
  LexLocation location = combineLexLocation(left.getLocation(), cse.getLocation());
  $$ = new AHidingProcess(location, left, cse);
}
| process STARTBY expression
{
  PProcess left = (PProcess)$1;
  PExp exp = (PExp)$3;
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  $$ = new AStartDeadlineProcess(location, left, exp);
}
| process ENDSBY expression
{
  PProcess left = (PProcess)$1;
  PExp exp = (PExp)$3;
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  $$ = new AEndDeadlineProcess(location, left, exp);
}
| LPAREN declaration AT processDef RPAREN LPAREN expression RPAREN
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$8);
  List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)$2;
  LexNameToken identifier = extractLexNameToken((CmlLexeme)$4);
  $$ = new AInstantiationProcess(location, decls, identifier, (PExp)$7);
}
/* DEVIATION
 * CML_0:
 *   IDENTIFIER
 *   IDENTIFIER LRPAREN
 *   IDENTIFIER LPAREN expressionList RPAREN
 * here:
 *   path
 *
 * TODO: need to convert the path to an instantiated process?
 */
| path
| process renameExpression
{
  SRenameChannelExp renameExpression = (SRenameChannelExp)$2;
  PProcess process = (PProcess)$1;
  $$ = new AChannelRenamingProcess(combineLexLocation(process.getLocation(), renameExpression.getLocation()), process, renameExpression);
}
/* DEVIATION (x4)
 * all of the replicated processes
 * grammar:
 *   <replOp> replicationDeclaration '@' process
 * here:
 *   <replOp> '{' replicationDeclaration '}' '@' process
 */
| SEMI LCURLY replicationDeclaration AT process RCURLY //TODO
| BARTILDEBAR LCURLY replicationDeclaration AT process RCURLY //TODO
| LRSQUARE LCURLY replicationDeclaration AT process RCURLY //TODO
| TBAR LCURLY replicationDeclaration AT process RCURLY //TODO
| LSQUARE chansetExpr RSQUARE LCURLY replicationDeclaration AT process RCURLY //TODO
;

replicationDeclaration :
  replicationDeclarationAlt // TODO
| replicationDeclaration SEMI replicationDeclarationAlt // TODO
;

replicationDeclarationAlt :
  singleTypeDecl // TODO
// FIXME --- this causes a s/r conflict due to the name production
/* | singleExpressionDeclaration */
;

/* singleExpressionDeclaration : */
/* IDENTIFIER COLON expression // TODO */
/* { */
/*   LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1); */
/*   List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>(); */
/*   ids.add(id); */
/*   ASingleTypeDeclaration singleTypeDeclaration = new ASingleTypeDeclaration(id.getLocation(), NameScope.GLOBAL, ids, (PType)$3); */
/*   $$ = singleTypeDeclaration; */
/* } */
/* | singleExpressionDeclaration COMMA IDENTIFIER // TODO */
/* { */
/*   LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1); */
/*   ASingleTypeDeclaration singleTypeDeclaration = (ASingleTypeDeclaration)$3;     */
/*   singleTypeDeclaration.getIdentifiers().add(id); */
/*   $$ = singleTypeDeclaration; */
/* } */
/* ; */

processParagraphList :
  processParagraph
{
  List<PDeclaration> processParagraphList = new Vector<PDeclaration>();
  processParagraphList.add((PDeclaration)$1);
  $$ = processParagraphList;
}
| processParagraphList processParagraph
{
  List<PDeclaration> processParagraphList = (List<PDeclaration>)$1;
  if (processParagraphList == null)
    processParagraphList = new Vector<PDeclaration>();
  processParagraphList.add((PDeclaration)$2);
  $$ = processParagraphList;
}
;

processParagraph :
 classDefinitionBlockAlternative
{
  $$ = $1;
}
| actionParagraph
{
  $$ = $1;
}
;

actionParagraph :
  ACTIONS actionDefinitionList
{
  List<AActionDefinition> actionDefinitions = (List<AActionDefinition>)$2;
  LexLocation declLoc = combineLexLocation(extractLexLocation((CmlLexeme)$1), extractLastLexLocation(actionDefinitions));
  $$ = new AActionDeclaration(declLoc, NameScope.GLOBAL, actionDefinitions);
}
| ACTIONS nameset IDENTIFIER EQUALS namesetExpr //TODO
;

actionDefinitionList :
  actionDefinition
{
  List<AActionDefinition> actionDefs = new Vector<AActionDefinition>();
  actionDefs.add((AActionDefinition)$1);
  $$ = actionDefs;
}
| actionDefinitionList actionDefinition
{
  List<AActionDefinition> actionDefs = (List<AActionDefinition>)$1;
  actionDefs.add((AActionDefinition)$2);
  $$ = actionDefs;
}
;

actionDefinition :
  IDENTIFIER EQUALS paragraphAction
{
  Object[] pa = (Object[])$3;
  List<ASingleTypeDeclaration> declarations = (List<ASingleTypeDeclaration>)pa[0];
  PAction action = (PAction)pa[1];
  LexLocation defLocation = combineLexLocation(extractLexLocation((CmlLexeme)$1), action.getLocation());
  AActionDefinition actionDefinition = new AActionDefinition(defLocation, NameScope.GLOBAL, false, null, declarations, action);
  $$ = actionDefinition;
}
;

paragraphAction :
  action
{
  $$ = new Object[]{new Vector<ASingleTypeDeclaration>(), $1};
}
| declaration AT action
{
  $$ = new Object[]{$1, $3};
}
;

action :
  CSPSKIP
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  $$ = new ASkipAction(location);
}
| CSPSTOP
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  $$ = new AStopAction(location);
}
| CSPCHAOS
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  $$ = new AChaosAction(location);
}
| CSPDIV
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  $$ = new ADivAction(location);
}
| CSPWAIT LPAREN expression RPAREN
{
  PExp exp = (PExp)$2;
  LexLocation location = extractLexLocation((CmlLexeme)$1, exp.getLocation());
  $$ = new AWaitAction(location, exp);
}
/* Communication rule start*/
| IDENTIFIER RARROW action
{
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
  PAction action = (PAction)$3;
  LexLocation location = combineLexLocation(id.getLocation(), action.getLocation());
  $$ = new ACommunicationAction(location, id, null, action);
}
| IDENTIFIER communicationParameterList RARROW action
{
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
  PAction action = (PAction)$4;
  LexLocation location = combineLexLocation(id.getLocation(), action.getLocation());
  List<PCommunicationParameter> communicationParamters = (List<PCommunicationParameter>)$2;
  $$ = new ACommunicationAction(location, id, communicationParamters, action);
}
/* Communication rule end*/
/* DEVIATON
 * grammar:
 *   expression '&' action
 * here:
 *   ':' expression '&' action
 */
| COLON expression AMP action
{
  PExp exp = (PExp)$2;
  PAction action = (PAction)$4;
  LexLocation location = combineLexLocation(exp.getLocation(), action.getLocation());
  $$ = new AGuardedAction(location, exp, action);
}
| action SEMI action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$3;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ASequentialCompositionAction(location, left, right);
}
| action LRSQUARE action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$3;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new AExternalChoiceAction(location, left, right);
}
| action BARTILDEBAR action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$3;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new AInternalChoiceAction(location, left, right);
}
| action SLASHBACKSLASH action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$3;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new AInterruptAction(location, left, right);
}
/* DEVIATION
 * grammar:
 *   process '/' expression '\' process
 * this conflicts all over, so
 *   process '/:' expression ':\' process
 */
| action SLASHCOLON expression COLONBACKSLASH action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$5;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ATimedInterruptAction(location, left, right, (PExp)$3);
}
| action LSQUAREGT action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$3;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new AUntimedTimeoutAction(location, left, right);
}
/* DEVIATION
 * grammar:
 *   action '[' expression '>' action
 * here:
 *   action '[' expression '|>' action
 */
| action LSQUARE expression BARGT action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$5;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ATimeoutAction(location, left, right, (PExp)$3);
}
/* DEVIATION
 * grammar:
 *   process '\' chansetExpr
 * here:
 *   process ':\' chansetExpr
 */
| action COLONBACKSLASH chansetExpr
{
  PAction left = (PAction)$1;
  SChansetSetExp chansetExp = (SChansetSetExp)$3;
  LexLocation location = combineLexLocation(left.getLocation(), chansetExp.getLocation());
  $$ = new AHidingAction(location, left, chansetExp);
}
| action STARTBY expression
{
  PAction left = (PAction)$1;
  PExp exp = (PExp)$3;
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  $$ = new AStartDeadlineAction(location, left, exp);
}
| action ENDSBY expression
{
  PAction left = (PAction)$1;
  PExp exp = (PExp)$3;
  LexLocation location = combineLexLocation(left.getLocation(), exp.getLocation());
  $$ = new AEndDeadlineAction(location, left, exp);
}
| action renameExpression
{
  SRenameChannelExp renameExpression = (SRenameChannelExp)$2;
  PAction action = (PAction)$1;
  $$ = new AChannelRenamingAction(combineLexLocation(action.getLocation(), renameExpression.getLocation()), action, renameExpression);
}
/* DEVIATION
 * grammar:
 *  'mu' <identifierList> '@' action
 * here:
 *  'mu' <pathList> '@' '(' action ')'
 *
 * Also, this is apparently not yet in our AST
 */
| MU pathList AT LPAREN action RPAREN // TODO
| parallelAction
{
    $$ = $1;
}
| LPAREN parametrisationList AT action RPAREN // parametrisedAction TODO
| instantiatedAction // TODO
| replicatedAction // TODO
| letStatement // TODO
| blockStatement // TODO
| controlStatement // TODO
// FIXME this is the missing IDENTIFIER in action; should probably be a name anyway
/* DEVIATION
 * CML_0:
 *   name
 * here:
 *   path
 * TODO: convert to a name
 */
| path // TODO
/* { */
/*   LexNameToken lnt = extractLexNameToken((ASimpleName)$1); */
/*   // FIXME -- apparently AIdentifierAction doesn't have any fields to store the *name* of the action? */
/*   $$ = new AIdentifierAction(lnt.location);   */
/* } */
;

communicationParameterList :
  communicationParameter
{
  List<PCommunicationParameter> comParamList = new Vector<PCommunicationParameter>();
  comParamList.add((PCommunicationParameter)$1);
  $$ = comParamList;
}
| communicationParameterList communicationParameter
{
  List<PCommunicationParameter> comParamList = (List<PCommunicationParameter>)$1;
  if (comParamList == null)
    comParamList = new Vector<PCommunicationParameter>();
  comParamList.add((PCommunicationParameter)$2);
  $$ = comParamList;
}
;

communicationParameter :
  QUESTION parameter
{
  PParameter parameter = (PParameter)$2;
  LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$1), parameter.getLocation());
  $$ = new AReadCommunicationParameter(location, parameter, null);
}
| QUESTION parameter COLON expression
{
  PParameter parameter = (PParameter)$2;
  PExp exp = (PExp)$4;
  LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$1), exp.getLocation());
  $$ = new AReadCommunicationParameter(location, parameter, exp);
}
| BANG expression
{
  PExp exp = (PExp)$2;
  LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$1), exp.getLocation());
  $$ = new AWriteCommunicationParameter(location, exp);
}
/* DEVIATION --- related to channelNameExpr
 * CML_0:
 *   '.' expression
 * here:
 *   '.:' expression
 *
 * This runs into trouble with the DOT in paths that are used in
 * expressions.  This could be difficult to resolve.
 */
| DOTCOLON expression
{
  PExp exp = (PExp)$2;
  LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$1), exp.getLocation());
  $$ = new AReferenceCommunicationParameter(location, exp);
}
;

parameter :
  IDENTIFIER
{
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
  $$ = new AIdentifierParameter(id.getLocation(), id);
}
| MKUNDER LPAREN paramList RPAREN
{
  $$ = new ATupleParameter(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$4), (List<? extends PParameter>)$3);
}
| MKUNDER LRPAREN
{
  $$ = new ATupleParameter(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$2), null);
}
| MKUNDERNAME LPAREN paramList RPAREN
{
  LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)$1);
  $$ = new ARecordParameter(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$4), name, (List<? extends PParameter>)$3);
}
| MKUNDERNAME LRPAREN
{
  LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)$1);
  $$ = new ARecordParameter(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$2), name, null);
}
;

paramList :
parameter
{
  List<PParameter> parameters = new Vector<PParameter>();
  parameters.add((PParameter)$1);
  $$ = parameters;
}
| paramList COMMA parameter
{
  List<PParameter> parameters = (List<PParameter>)$1;
  parameters.add((PParameter)$3);
  $$ = parameters;
}
;

parallelAction :
  action LSQUAREDBAR namesetExpr BAR namesetExpr DBARRSQUARE action //TODO
| action TBAR action //TODO
| action LSQUAREBAR namesetExpr BAR namesetExpr BARRSQUARE action //TODO
| action DBAR action //TODO
| action LSQUARE namesetExpr BAR chansetExpr DBAR chansetExpr BAR namesetExpr RSQUARE action //TODO
| action LSQUARE chansetExpr DBAR chansetExpr RSQUARE action //TODO
| action LSQUAREBAR namesetExpr BAR chansetExpr BAR namesetExpr BARRSQUARE action //TODO
| action LSQUAREBAR chansetExpr BARRSQUARE action //TODO
;

parametrisationList :
  parametrisation // TODO
| parametrisationList SEMI parametrisation // TODO
;

parametrisation :
  VAL singleTypeDecl // TODO
| RES singleTypeDecl // TODO
| VRES singleTypeDecl // TODO
;

instantiatedAction :
  LPAREN declaration AT action RPAREN LPAREN expressionList RPAREN
{
  $$ = new ADeclarationInstantiatedAction(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$8), (List<? extends ASingleTypeDeclaration>)$2, (PAction)$4, (List<PExp>)$7);
}
| LPAREN parametrisationList AT action RPAREN LPAREN expressionList RPAREN // parametrisedAction TODO
;

/* DEVIATION
 * all of the replicated actions
 * grammar:
 *   <replOp> replicationDeclaration '@' process
 * here:
 *   <replOp> '{' replicationDeclaration '}' '@' process
 */
replicatedAction :
  SEMI LCURLY replicationDeclaration AT action RCURLY //TODO
| LRSQUARE LCURLY replicationDeclaration AT action RCURLY //TODO
| BARTILDEBAR LCURLY replicationDeclaration AT action RCURLY //TODO
| LSQUAREDBAR nameset DBARRSQUARE LPAREN replicationDeclaration AT action RPAREN //TODO
| TBAR replicationDeclaration AT LSQUARE namesetExpr RSQUARE action // TODO
| LSQUAREBAR chansetExpr BARRSQUARE replicationDeclaration AT LSQUARE namesetExpr RSQUARE action //TODO
| DBAR replicationDeclaration AT LSQUARE namesetExpr BAR chansetExpr RSQUARE action //TODO
| DBAR replicationDeclaration AT LSQUARE namesetExpr RSQUARE action //TODO
;

renameExpression :
/* rename enumeration */
  DLSQUARE renameList DRSQUARE
{
    $$ = new AEnumerationRenameChannelExp(null, extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3), (List<? extends ARenamePair>)$2);
}
/* rename comprehensions */
| DLSQUARE renameList BAR bindList DRSQUARE
{
  $$ = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$5), (List<? extends ARenamePair>)$2, (List<? extends PMultipleBind>)$4, null);
}
| DLSQUARE renameList BAR bindList AT expression DRSQUARE
{
    $$ = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$7), (List<? extends ARenamePair>)$2, (List<? extends PMultipleBind>)$4, (PExp)$6);
}
;

renameList :
  channelNameExpr LARROW channelNameExpr
{
  List<ARenamePair> renamePairs = new Vector<ARenamePair>();
  ARenamePair pair = new ARenamePair(false, (AEventChannelExp)$1, (AEventChannelExp)$3);
  renamePairs.add(pair);
  $$ = renamePairs;
}
| renameList COMMA channelNameExpr LARROW channelNameExpr
{
  List<ARenamePair> renamePairs = (List<ARenamePair>)$1;
  ARenamePair pair = new ARenamePair(false, (AEventChannelExp)$3, (AEventChannelExp)$5);
  renamePairs.add(pair);
  $$ = renamePairs;
}
;

/* DEVIATION
 *
 * There's no single rule, but this applies whenever we might see
 * "IDENTIFIER { '.' expression }", we are instead requiring that
 * "IDENTIFIER { '.:' expression }" be used instead.  It's ugly, but
 * it disambiguates channel names from regular paths.  (Channel names
 * may have expressions in them, paths cannot.)
 */
channelNameExpr :
  IDENTIFIER
| IDENTIFIER DOTCOLON channelNameExprTail
;

channelNameExprTail :
  expression
{
  List<PExp> expTokens = new Vector<PExp>();
  expTokens.add((PExp)$1);
  $$ = expTokens;
}
| channelNameExprTail DOTCOLON expression
{
  List<PExp> expTokens = (List<PExp>)$1;
  PExp exp = (PExp)$3;
  expTokens.add(exp);
  $$ = expTokens;
}
;

channelDecl :
  CHANNELS channelDef
{
  List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)$2;
  LexLocation start = decls.get(0).getLocation();
  LexLocation end = decls.get(decls.size()-1).getLocation();
  LexLocation location = combineLexLocation(start, end);
  AChannelDefinition channelDefinition = new AChannelDefinition(location, null, null, null, decls);
  AChannelDeclaration channelDecl = new AChannelDeclaration(location, NameScope.GLOBAL, channelDefinition);
  $$ = channelDecl;
}
;

channelDef :
  channelNameDecl
{
  List<AChannelNameDeclaration> decls = new Vector<AChannelNameDeclaration>();
  decls.add((AChannelNameDeclaration)$1);
  AChannelDefinition channelDefinition = new AChannelDefinition();
  $$ = decls;
}
| channelDef SEMI channelNameDecl
{
  List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)$1;
  decls.add((AChannelNameDeclaration)$3);
  $$ = decls;
}
;

channelNameDecl :
/* DEVIATION
 * grammar:
 *   identifierList
 * here:
 *   pathList
 * So, we need to cast the list of names down to a list of identifiers
 */
  pathList
{
  List<LexNameToken> nameList = (List<LexNameToken>)$1;
  List<LexIdentifierToken> ids = convertNameListToIdentifierList(nameList);
  LexLocation start = nameList.get(0).getLocation();
  LexLocation end = nameList.get(ids.size()-1).getLocation();
  LexLocation location = combineLexLocation(start, end);
  ASingleTypeDeclaration singleTypeDeclaration = new ASingleTypeDeclaration(location, NameScope.GLOBAL, ids, null);
  AChannelNameDeclaration channelNameDecl = new AChannelNameDeclaration(location, NameScope.GLOBAL, singleTypeDeclaration);
  $$ = channelNameDecl;
}
| singleTypeDecl
{
  ASingleTypeDeclaration singleTypeDeclaration = (ASingleTypeDeclaration)$1;
  AChannelNameDeclaration channelNameDecl = new AChannelNameDeclaration(singleTypeDeclaration.getLocation(), NameScope.GLOBAL, singleTypeDeclaration);
  $$ = channelNameDecl;
}
;

declaration :
  singleTypeDecl
{
  List<ASingleTypeDeclaration> decls = new Vector<ASingleTypeDeclaration>();
  decls.add((ASingleTypeDeclaration)$1);
  $$ = decls;
}
| declaration SEMI singleTypeDecl
{
  List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)$1;
  decls.add((ASingleTypeDeclaration)$3);
  $$ = decls;
}
;

singleTypeDecl :
/* DEVIATION
 * grammar:
 *   identifierList
 * here:
 *   pathList
 */
  pathList COLON type
{
  List<LexNameToken> nameList = (List<LexNameToken>)$1;
  List<LexIdentifierToken> ids = convertNameListToIdentifierList(nameList);
  ASingleTypeDeclaration singleTypeDeclaration =
    new ASingleTypeDeclaration(nameList.get(0).getLocation(), NameScope.GLOBAL, ids, (PType)$3);
  $$ = singleTypeDeclaration;
}
;

chansetDecl :
  CHANSETS
{
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
    $$ = new AChansetDeclaration(id.getLocation(), NameScope.GLOBAL, null);
}
| CHANSETS chansetDefinitionList
{
    List<AChansetDefinition> defs = (List<AChansetDefinition>)$2;
    $$ = new AChansetDeclaration(combineLexLocation(extractLexLocation((CmlLexeme)$1), extractLastLexLocation(defs)), NameScope.GLOBAL, defs);
}
;

chansetDefinitionList :
  chansetDefinition
{
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)$1);
  $$ = defs;
}
| chansetDefinitionList chansetDefinition
{
  List<AChansetDefinition> defs = (List<AChansetDefinition>)$1;
  defs.add((AChansetDefinition)$2);
  $$ = defs;
}
;

chansetDefinition :
  IDENTIFIER EQUALS chansetExpr
{
  LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)$1);
  SChansetSetBase chansetExp = (SChansetSetBase)$3;
  LexLocation location = combineLexLocation(idToken.getLocation(), chansetExp.getLocation());
  $$ = new AChansetDefinition(location, NameScope.GLOBAL, false/*used_*/, null, /*AAccessSpecifierAccessSpecifier access_*/ idToken, chansetExp);
}
;

chansetExpr :
  IDENTIFIER
{
  LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)$1);
  $$ = new AIdentifierChansetSetExp(idToken.getLocation(), idToken);
}
/* DEVIATION
 * grammar:
 *   '{' identifierList '}'
 * here:
 *   '{:' pathList ':}'
 */
| LCURLYCOLON pathList COLONRCURLY
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3);
  List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)$2;
  $$ = new AEnumChansetSetExp(location, identifiers);
}
| LCURLYBAR pathList BARRCURLY
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3);
  List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)$2;
  $$ = new AEnumChansetSetExp(location, identifiers);
}
/* DEVIATION
 * grammar:
 *   chansetExpr 'union' chansetExpr
 * here:
 *   chansetExpr ':union' chansetExpr
 */
| chansetExpr COLONUNION chansetExpr
{
  PExp left = (PExp)$1;
  PExp right = (PExp)$3;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ASetUnionBinaryExp(location, left, null, right);
}
/* DEVIATION
 * grammar:
 *   chansetExpr 'inter' chansetExpr
 * here:
 *   chansetExpr ':inter' chansetExpr
 */
| chansetExpr COLONINTER chansetExpr
{
  PExp left = (PExp)$1;
  PExp right = (PExp)$3;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ASetIntersectBinaryExp(location, left, null, right);
}
/* DEVIATION
 * grammar:
 *   chansetExpr '\' chansetExpr
 * here:
 *   chansetExpr ':\' chansetExpr
 */
| chansetExpr COLONBACKSLASH chansetExpr
{
  PExp left = (PExp)$1;
  PExp right = (PExp)$3;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$2);
  /* LexToken lexToken = new LexToken(opLocation, VDMToken.BACKSLASH); */
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ASetDifferenceBinaryExp(location, left, /*lexToken*/null, right);
}
/* DEVIATION --- see channelNameExpr
 */
| LCURLYBAR channelNameExpr BAR bindList BARRCURLY
/* { */
/*   LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$6); */
/*   LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)$2); */
/*   List<PExp> dotted_expression = (List<PExp>)$3; */
/*   List<PMultipleBind> bindings = (List<PMultipleBind>)$5; */
/*   $$ = new ACompChansetSetExp(location, identifier, dotted_expression, bindings, null); */
/* } */
| LCURLYBAR channelNameExpr BAR bindList AT expression BARRCURLY
/* { */
/*   LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$8); */
/*   LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)$2); */
/*   List<PExp> dotted_expression = (List<PExp>)$3; */
/*   List<PMultipleBind> bindings = (List<PMultipleBind>)$5; */
/*   PExp pred = (PExp)$7; */
/*   $$ = new ACompChansetSetExp(location, identifier, dotted_expression, bindings, pred); */
/* } */
;

globalDecl :
  globalDefinitionBlock
{
  $$ = $1;
}
;

globalDefinitionBlock :
  globalDefinitionBlockAlternative
{
  List<PDeclaration> declBlockList = new Vector<PDeclaration>();
  PDeclaration globalDecl = (PDeclaration)$1;
  if (globalDecl != null) declBlockList.add(globalDecl);
  $$ = declBlockList;
}
| globalDefinitionBlock globalDefinitionBlockAlternative
{
  List<PDeclaration> declBlockList = (List<PDeclaration>)$1;
  PDeclaration globalDecl = (PDeclaration)$2;
  if (declBlockList != null && globalDecl != null)
      declBlockList.add(globalDecl);
  $$ = declBlockList;
}
;

globalDefinitionBlockAlternative :
  typeDefs
{
  ATypeDeclaration typeDeclaration = (ATypeDeclaration)$1;
  typeDeclaration.setNameScope(NameScope.GLOBAL);
  $$ = typeDeclaration;
}
| valueDefs
{
  AValueDeclaration valueGlobalDeclaration = new AValueDeclaration();
  $$ = valueGlobalDeclaration;
}
| functionDefs
{
  AFunctionDeclaration functionGlobalDeclaration = (AFunctionDeclaration)$1;
  functionGlobalDeclaration.setNameScope(NameScope.GLOBAL);
  $$ = functionGlobalDeclaration;
}
;

classBody :
  BEGIN classDefinitionBlock END
{
  $$ = $2;
}
;

classDefinitionBlock :
  classDefinitionBlockAlternative
{
  List<PDeclaration> decls = new LinkedList<PDeclaration>();
  PDeclaration decl = (PDeclaration)$1;
  decls.add(decl);
  $$ = decls;
}
| classDefinitionBlockAlternative classDefinitionBlock
{
  List<PDeclaration> decls = (List<PDeclaration>)$2;
  PDeclaration decl = (PDeclaration)$1;
  decls.add(decl);
  $$ = decls;
}
;

classDefinitionBlockAlternative :
  typeDefs
{
  $$ = $1;
}
| valueDefs
{
  $$ = $1;
}
| functionDefs
{
  AFunctionDeclaration functionDeclaration = (AFunctionDeclaration)$1;
  functionDeclaration.setNameScope(NameScope.GLOBAL);
  $$ = functionDeclaration;
}
| operationDefs
{
  AOperationDeclaration operationDeclaration = (AOperationDeclaration)$1;
  operationDeclaration.setNameScope(NameScope.GLOBAL);
  $$ = operationDeclaration;
}
| stateDefs
{
  $$ = $1;
}
/* DEVIATION
 * This is not yet in the (CML0) grammar, but the need for it has been recognised
 */
| INITIAL operationDef // TODO
;

typeDefs :
  TYPES
{
  CmlLexeme typesLexeme = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation(typesLexeme);
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions(new Vector<ATypeDefinition>());
  td.setLocation(loc);
  $$ = td;
}
| TYPES typeDefList SEMI
{
  CmlLexeme typesLexeme = (CmlLexeme)$1;
  CmlLexeme semiLexeme = (CmlLexeme)$3;
  LexLocation loc = extractLexLocation(typesLexeme, semiLexeme);
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions((List<ATypeDefinition>)$2);
  td.setLocation(loc);
  $$ = td;
}
| TYPES typeDefList
{
  CmlLexeme typesLexeme = (CmlLexeme)$1;
  List<ATypeDefinition> tdefs = (List<ATypeDefinition>)$2;
  LexLocation loc = extractLexLocation(typesLexeme, tdefs.get(tdefs.size()-1).getLocation());
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions(tdefs);
  td.setLocation(loc);
  $$ = td;
}
;

typeDefList :
  typeDef
{
  List<ATypeDefinition> list = new Vector<ATypeDefinition>();
  list.add((ATypeDefinition)$1);
  $$ = list;
}
| typeDefList SEMI typeDef
{
  List<ATypeDefinition> list = (List<ATypeDefinition>)$1;
  list.add((ATypeDefinition)$3);
  $$ = list;
}
;

typeDef :
  qualifier IDENTIFIER EQUALS type invariant
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  AInvariantInvariant inv = (AInvariantInvariant)$5;
  LexLocation location = null;
  if (access.getLocation() != null) {
    location = combineLexLocation(access.getLocation(), inv.getLocation());
  } else {
    location = combineLexLocation(name.getLocation(), inv.getLocation());
  }
  $$ = new ATypeDefinition(location, null /*NameScope nameScope_*/, false, null/*SClassDefinition classDefinition_*/, access,
			   (PType)$4, null, inv.getInvPattern(), inv.getInvExpression(), null, true, name);
}
| qualifier IDENTIFIER EQUALS type
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  LexLocation location = null;
  if (access.getLocation() != null) {
    location = combineLexLocation(access.getLocation(), ((PType)$4).getLocation());
  } else {
      location = combineLexLocation(name.getLocation(), ((PType)$4).getLocation());
  }
  $$ = new ATypeDefinition(location, null /*NameScope nameScope_*/, false, null/*SClassDefinition classDefinition_*/, access,
			   (PType)$4, null, null, null, null, true, name);
}
| qualifier IDENTIFIER DCOLON fieldList
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  CmlLexeme vdmrec = (CmlLexeme)$3;
  List<AFieldField> fields = (List<AFieldField>)$4;
  LexLocation loc = combineLexLocation(access.getLocation(), extractLexLocation(vdmrec));
  ARecordInvariantType recType = new ARecordInvariantType(loc, false, null, false, null, name, fields, true);
  ATypeDefinition res = new ATypeDefinition(loc, NameScope.GLOBAL, false, null, access, recType, null, null, null, null, true, name);
  $$ = res;
}
| qualifier IDENTIFIER DCOLON fieldList invariant
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  CmlLexeme vdmrec = (CmlLexeme)$3;
  List<AFieldField> fields = (List<AFieldField>)$4;
  // TODO: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.
  LexLocation loc = combineLexLocation(access.getLocation(), extractLexLocation(vdmrec));
  //
  ARecordInvariantType recType = new ARecordInvariantType(loc, false, null, false, null, name, fields, true);
  ATypeDefinition res = new ATypeDefinition(loc, NameScope.GLOBAL, false, null, access, recType, null, null, null, null, true, name);
  $$ = res;
}
;

/* FUTURE
 *
 * (jwc) I think for this we really ought have the lexer give the
 * token 'QUALIFIER' and then querying its value (like with numeric
 * literals) to figure out which one we have.
 */
qualifier :
  PRIVATE
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
  res.setAccess(new APrivateAccess());
  res.setLocation(location);
  $$ = res;
}
| PROTECTED
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
  res.setLocation(location);
  res.setAccess(new AProtectedAccess());
  $$ = res;
}
| PUBLIC
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
  res.setLocation(location);
  res.setAccess(new APublicAccess());
  $$ = res;
}
| LOGICAL
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  $$ = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(), null, null, location);
}
| /* empty */
{
  /*Default private*/
  AAccessSpecifierAccessSpecifier a = new AAccessSpecifierAccessSpecifier();
  a.setAccess(new APrivateAccess());
  $$ = a;
}
;

type :
  LPAREN type RPAREN // bracketedType
{
  $$ = $2;
}
| basicType
{
  $$ = $1;
}
| quoteLiteral // quoteType
{
  LexQuoteToken value = (LexQuoteToken)$1;
  $$ = new AQuoteType(value.location, false, null, value);
}
| COMPOSE IDENTIFIER OF fieldList END // TODO
| type BAR type // unionType
{
  /* FIXME --- Make union type concatenation smarter
   *
   * This is technically correct as far as it goes, but it creates a
   * tree of AUnionType objects, which is rather stupid.  It should
   * check the left type to see if it already *is* an AUnionType and
   * just add the right if so; otherwise, it should do as it does now.
   */
  PType fst = (PType)$1;
  PType snd = (PType)$3;
  LexLocation loc = combineLexLocation(fst.getLocation(), snd.getLocation());
  List<PType> types = new Vector<PType>();
  types.add(fst);
  types.add(snd);
  AUnionType utype = new AUnionType(loc, false, false, false);
  utype.setTypes(types);
  $$ = utype;
}
| type STAR type //productType
{
  /* FIXME --- Make product type concatenation smarter
   *
   * This is technically correct as far as it goes, but it creates a
   * tree of AProductType objects, which is rather stupid.  It should
   * check the left type to see if it already *is* an AProductType and
   * just add the right if so; otherwise, it should do as it does now.
   */
  List<PType> types = new Vector<PType>();
  PType left = (PType)$1;
  PType right = (PType)$3;
  types.add(left);
  types.add(right);
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new AProductType(location, false, null, types);
}
| LSQUARE type RSQUARE // optionalType
{
  $$ = new  AOptionalType(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3), false, null, (PType)$2);
}
| SETOF type
{
  CmlLexeme setof = (CmlLexeme)$1;
  PType type = (PType)$2;
  LexLocation loc = combineLexLocation(extractLexLocation(setof), type.getLocation());
  ASetType res = new ASetType(loc, false, null, type, false, false);
  $$ = res;
}
| SEQOF type
{
  CmlLexeme seqof = (CmlLexeme)$1;
  PType type = (PType)$2;
  LexLocation loc = combineLexLocation(extractLexLocation(seqof), type.getLocation());
  ASeqSeqType res = new ASeqSeqType(loc, false, null, type, false);
  $$ = res;
}
| SEQ1OF type
{
  CmlLexeme seqof = (CmlLexeme)$1;
  PType type = (PType)$2;
  LexLocation loc = combineLexLocation(extractLexLocation(seqof), type.getLocation());
  ASeq1SeqType res = new ASeq1SeqType(loc, false, null, type, false);
  $$ = res;
}
| MAPOF type TO type
{
  CmlLexeme mapof = (CmlLexeme)$1;
  PType from = (PType)$2;
  PType to   = (PType)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(mapof), to.getLocation());
  AMapMapType res = new AMapMapType(loc, false, null, from, to, false);
  $$ = res;
}
| INMAPOF type TO type
{
  CmlLexeme mapof = (CmlLexeme)$1;
  PType from = (PType)$2;
  PType to   = (PType)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(mapof), to.getLocation());
  AMapMapType res = new AMapMapType(loc, false, null, from, to, false);
  $$ = res;
}
| functionType
{
  $$ = $1;
}
/* DEVIATION (x3)
 * CML_0:
 *   name
 * here:
 *   IDENTIFIER
 *   IDENTIFIER DOT IDENTIFIER
 *   IDENTIFIER BACKTICK IDENTIFIER
 * TODO: convert these into to names
 */
| IDENTIFIER
| IDENTIFIER DOT IDENTIFIER
| IDENTIFIER BACKTICK IDENTIFIER
;

basicType :
  TBOOL
{
  $$ = new ABooleanBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TNAT
{
  $$ = new ANatNumericBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TNAT1
{
  $$ = new ANatOneNumericBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TINT
{
  $$ = new AIntNumericBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TRAT
{
  $$ = new ARationalNumericBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TREAL
{
  $$ = new ARealNumericBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TCHAR
{
  $$ = new ACharBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TTOKEN
{
  $$ = new ATokenBasicType(extractLexLocation((CmlLexeme)$1), false);
}
;

functionType :
  partialFunctionType
{
  $$ = $1;
}
| totalFunctionType
{
  $$ = $1;
}
;

partialFunctionType :
  type PLUSGT type
{
  PType domType = (PType)$1;
  PType rngType = (PType)$3;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  // TODO --- is this a dead comment?
  // [CONSIDER,RWL] The domain type of a function is not a list,
  // I think the AST is wrong taking a list of types for params
  // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, true, params, rngType);
}
| LRPAREN PLUSGT type // discretionary type
{
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)$1), true);
  PType rngType = (PType)$3;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  // TODO --- is this a dead comment?
  // [CONSIDER,RWL] The domain type of a function is not a list,
  // I think the AST is wrong taking a list of types for params
  // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, true, params, rngType);
}
;

totalFunctionType :
  type RARROW type
{
  PType domType = (PType)$1;
  PType rngType = (PType)$3;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  // TODO --- is this a dead comment?
  // [CONSIDER,RWL] The domain type of a function is not a list,
  // I think the AST is wrong taking a list of types for params
  // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, false, params, rngType);
}
| LRPAREN RARROW type // discretionary type
{
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)$1), true);
  PType rngType = (PType)$3;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  // TODO --- is this a dead comment?
  // [CONSIDER,RWL] The domain type of a function is not a list,
  // I think the AST is wrong taking a list of types for params
  // AKM: Your right that is strange, but when it is changed the AstCreator is failing??
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, false, params, rngType);
}
;

quoteLiteral :
  QUOTE_LITERAL
{
  CmlLexeme id = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation((CmlLexeme)$1);
  String value = id.getValue();
  $$ = new LexQuoteToken(value.substring(1, value.length()-2), loc);
}
;

fieldList :
  field
{
  List<AFieldField> res = new LinkedList<AFieldField>();
  res.add((AFieldField)$1);
  $$ = res;
}
| fieldList field
{
  List<AFieldField> head = (List<AFieldField>)$1;
  head.add((AFieldField)$2);
  $$ = head;
}
;

field :
  type
{
  $$ = new AFieldField(null, null, null, (PType)$1, null);
}
| IDENTIFIER COLON type
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$1);
  PType type = (PType) $3;
  $$ = new AFieldField(null, name, null, type, null);
}
| IDENTIFIER COLONDASH type
{
  // TODO --- dude, that's harsh
  throw new RuntimeException("No way");
}
;

invariant :
  INV pattern DEQUALS expression
{
  CmlLexeme vdmInvLexeme = (CmlLexeme)$1;
  PExp exp = (PExp)$4;
  LexLocation loc = extractLexLocation(vdmInvLexeme, exp.getLocation());
  $$ = new AInvariantInvariant(loc, (PPattern)$2, exp);
}
;

valueDefs :
  VALUES valueDefList
{
  List<PDefinition> defs = (List<PDefinition>)$2;
  AValueDeclaration valueDecl = new AValueDeclaration();
  valueDecl.setDefinitions(defs);
  $$ = valueDecl;
}
;

/* RWL. On tailing SEMI:
 *
 * Lists definition like valueDefs below has an element and a
 * separater. Ofter it is convenient for the language that the
 * separater can be added to the end of the list optionally. Like:
 *
 * class valuelist =
 * begin
 *    values
 *       a : int = 1;
 *       b : int = 2;
 *       c : int = 3
 * end
 *
 * The list "c : int = 3" could be followed by a SEMI (;) as in:
 *
 *
 * class valuelist =
 * begin
 *     values
 *       a : int = 1;
 *       b : int = 2;
 *       c : int = 3;
 * end
 *
 * To relax the parser to accept both cases we add two "base-cases"
 * for the list, one without SEMI and one with SEMI.
 *
 * This production-rule-pattern should work for any list where there
 * is no conflict between element definition and the separator.
 */
valueDefList :
  qualifiedValueDef
{
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)$1);
  $$ = defs;
}
| qualifiedValueDef SEMI
{
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)$1);
  $$ = defs;
}
| qualifiedValueDef SEMI valueDefList
{
  PDefinition def = (PDefinition)$1;
  List<PDefinition> defs = (List<PDefinition>)$3;
  defs.add(def);
  $$ = defs;
}
;

qualifiedValueDef :
  qualifier valueDef
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  PDefinition def = (PDefinition)$2;
  def.setAccess(access);
  $$ = def;
}
;

valueDef :
  IDENTIFIER COLON type EQUALS expression
{
  CmlLexeme id = (CmlLexeme)$1;
  PType type = (PType)$3;
  PExp expression = (PExp)$5;
  LexNameToken lnt = extractLexNameToken(id);
  AIdentifierPattern idp = new AIdentifierPattern();
  idp.setLocation(lnt.location);
  // Build the resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(idp.getLocation(), expression.getLocation()));
  $$ = vdef;
}
| patternLessID COLON type EQUALS expression
{
  PPattern pattern = (PPattern)$1;
  PType type = (PType)$3;
  PExp expression = (PExp)$5;
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(pattern.getLocation(), expression.getLocation()));
  $$ = vdef;
}
| IDENTIFIER EQUALS expression
{
  CmlLexeme id = (CmlLexeme)$1;
  PExp expression = (PExp)$3;
  LexNameToken lnt = extractLexNameToken(id);
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location, null, false, lnt);
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(null);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(idp.getLocation(), expression.getLocation()));
  $$ = vdef;
}
| patternLessID EQUALS expression
{
  PPattern pattern = (PPattern)$1;
  PExp expression = (PExp)$3;
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(null);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(pattern.getLocation(), expression.getLocation()));
  $$ = vdef;
}
;

functionDefs :
  FUNCTIONS
{
  CmlLexeme functionsLexeme = (CmlLexeme)$1;
  AFunctionDeclaration fdecl = new AFunctionDeclaration();
  fdecl.setLocation(extractLexLocation(functionsLexeme));
  $$ = fdecl;
}
| FUNCTIONS functionDefList
{
  CmlLexeme functionsLexeme = (CmlLexeme)$1;
  List<SFunctionDefinition> functionDefs = (List<SFunctionDefinition>) $2;
  AFunctionDeclaration fdecl = new AFunctionDeclaration();
  fdecl.setLocation(extractLexLocation(functionsLexeme, functionDefs.get(functionDefs.size()-1).getLocation()));
  fdecl.setFunctionDefinitions(functionDefs);
  $$ = fdecl;
}
;

functionDefList :
  functionDef
{
  List<SFunctionDefinition> functionList = new Vector<SFunctionDefinition>();
  functionList.add((SFunctionDefinition)$1);
  $$ = functionList;
}
| functionDef SEMI
{
  List<SFunctionDefinition> functionList = new Vector<SFunctionDefinition>();
  functionList.add((SFunctionDefinition)$1);
  $$ = functionList;
}
| functionDef SEMI functionDefList
{
  List<SFunctionDefinition> functionList = (List<SFunctionDefinition>)$3;
  functionList.add((SFunctionDefinition)$1);
  $$ = functionList;
}
;

functionDef :
  implicitFunctionDef
{
  $$ = $1;
}
| qualifiedExplicitFunctionDef
{
  $$ = $1;
}
;

implicitFunctionDef :
  qualifier IDENTIFIER parameterTypes identifierTypePairList preExpr_opt postExpr
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  List<APatternListTypePair> paramPatterns = (List<APatternListTypePair>)$3;
  List<APatternTypePair> result = (List<APatternTypePair>)$4;
  PExp preExp = (PExp)$5;
  PExp postExp = (PExp)$6;
  LexLocation location = null;
  if (access.getLocation() != null) {
    location = combineLexLocation(access.getLocation(), postExp.getLocation());
  } else {
    location = combineLexLocation(name.getLocation(), postExp.getLocation());
  }
  AImplicitFunctionFunctionDefinition impFunc =
    new AImplicitFunctionFunctionDefinition(location, null, false, access, null, paramPatterns, result, preExp, postExp);
  impFunc.setName(name);
  $$ = impFunc;
}
;

qualifiedExplicitFunctionDef :
  qualifier explicitFunctionDef
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  AExplicitFunctionFunctionDefinition f = (AExplicitFunctionFunctionDefinition)$2;
  f.setAccess(access);
  $$ = f;
}
;

explicitFunctionDef :
  IDENTIFIER COLON functionType IDENTIFIER parameterList DEQUALS functionBody preExpr_opt postExpr_opt measureExpr
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$1);
  LexLocation loc = extractLexLocation((CmlLexeme)$1);
  AFunctionType ftype = (AFunctionType)$3;
  AExplicitFunctionFunctionDefinition res = new AExplicitFunctionFunctionDefinition();
  res.setName(name);
  res.setLocation(loc);
  res.setType(ftype);
  $$ = res;
}

;

/* really? this is what a VDM function definition list looks like? */
parameterList :
  LRPAREN
{
  List<List<PPattern>> patternListList = new Vector<List<PPattern>>();
  $$ = patternListList;
}
| LPAREN patternList RPAREN
{
  List<PPattern> patternList = (List<PPattern>)$2;
  List<List<PPattern>> patternListList = new Vector<List<PPattern>>();
  patternListList.add(patternList);
  $$ = patternListList;
}
| parameterList LPAREN patternList RPAREN
{
  List<PPattern> patternList = (List<PPattern>)$3;
  List<List<PPattern>> patternListList = (List<List<PPattern>>)$1;
  patternListList.add(patternList);
  $$ = patternListList;
}
;

functionBody :
  expression
{
  $$ = $1;
}
| SUBCLASSRESP
{
  $$ = new ASubclassResponsibilityExp(extractLexLocation((CmlLexeme)$1));
}
| NOTYETSPEC
{
  $$ = new ANotYetSpecifiedExp(extractLexLocation((CmlLexeme)$1));
}
;

parameterTypes :
  LRPAREN
{
  $$ = new Vector<APatternListTypePair>();
}
| LPAREN patternListTypeList RPAREN
{
  $$ = $2;
}
;

patternListTypeList :
  patternList COLON type
{
  List<PPattern> patternList = (List<PPattern>)$1;
  List<APatternListTypePair> pltpl = new Vector<APatternListTypePair>();
  pltpl.add(new APatternListTypePair(false, patternList, (PType)$3));
  $$ = pltpl;
}
| patternListTypeList COMMA patternList COLON type
{
  List<APatternListTypePair> pltpl = (List<APatternListTypePair>)$1;
  List<PPattern> patternList = (List<PPattern>)$3;
  pltpl.add(new APatternListTypePair(false, patternList, (PType)$5));
  $$ = pltpl;
}
;

identifierTypePairList_opt :
  /* empty */
{
  $$ = null;
}
| identifierTypePairList
{
  $$ = $1;
}
;

identifierTypePairList :
  IDENTIFIER COLON type
{
  AIdentifierTypePair typePair = new AIdentifierTypePair(null, extractLexIdentifierToken((CmlLexeme)$1), (PType)$3);
  List<AIdentifierTypePair> typePairs = new Vector<AIdentifierTypePair>();
  typePairs.add(typePair);
  $$ = typePairs;
}
| identifierTypePairList COMMA IDENTIFIER COLON type
{
  AIdentifierTypePair typePair = new AIdentifierTypePair(null, extractLexIdentifierToken((CmlLexeme)$3), (PType)$5);
  List<AIdentifierTypePair> typePairs = (List<AIdentifierTypePair>)$1;
  typePairs.add(typePair);
  $$ = typePairs;
}
;

preExpr_opt :
  preExpr
{
  $$ = $1;
}
| /* empty */
{
  $$ = null;
}
;

preExpr :
  PRE expression
{
  $$ = $2;
}
;

postExpr_opt :
  postExpr
{
  $$ = $1;
}
| /* empty */
{
    $$ = null;
}
;

postExpr :
  POST expression
{
  $$ = $2;
}
;

measureExpr :
/* DEVIATION --- PATH
 * CML_0:
 *   MEASURE name
 * TODO: convert to a name
 */
  MEASURE path
{
  $$ = $2;
}
| /* empty */
;

operationDefs :
  OPERATIONS operationDefList
{
  List<? extends SOperationDefinition> opDefinitions = (List<? extends SOperationDefinition>)$2;
  LexLocation lastInListLoc = opDefinitions.get(opDefinitions.size()-1).getLocation();
  LexLocation location = extractLexLocation((CmlLexeme)$1, lastInListLoc);
  $$ = new AOperationDeclaration(location, NameScope.GLOBAL, opDefinitions);
}
| OPERATIONS
{
    LexLocation location = extractLexLocation((CmlLexeme)$1);
    $$ = new AOperationDeclaration(location, NameScope.GLOBAL, null);
}
;

operationDefList :
  operationDef
{
    List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>();
    opDefinitions.add((SOperationDefinition)$1);
    $$ = opDefinitions;
}
// FIXME --- making the SEMI optional causes s/r
/* | operationDef SEMI */
/* { */
/*     List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>(); */
/*     opDefinitions.add((SOperationDefinition)$1); */
/*     $$ = opDefinitions; */
/* } */
| operationDefList SEMI operationDef
{
    List<SOperationDefinition> opDefinitions = (List<SOperationDefinition>)$1;
    opDefinitions.add((SOperationDefinition)$3);
    $$ = opDefinitions;
}
;

/* FIXME the optional trailing semicolon in the operations definitions is presently not optional */

operationDef :
  implicitOperationDef
{
  $$ = $1;
}
| explicitOperationDef
{
  $$ = $1;
}
;

explicitOperationDef :
  qualifier IDENTIFIER COLON operationType IDENTIFIER parameterList DEQUALS operationBody preExpr_opt postExpr_opt
{
  LexLocation loc = extractLexLocation((CmlLexeme)$2);
  AExplicitOperationOperationDefinition res = new AExplicitOperationOperationDefinition();
  res.setLocation(loc);
  $$ = res;
}
;

implicitOperationDef :
  qualifier IDENTIFIER parameterTypes identifierTypePairList_opt externals_opt preExpr_opt postExpr
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  List<? extends APatternListTypePair> parameterPatterns = (List<? extends APatternListTypePair>)$3;
  List<? extends AIdentifierTypePair> result = (List<? extends AIdentifierTypePair>)$4;
  List<? extends AExternalClause> externals = (List<? extends AExternalClause>)$5;
  PExp precondition = (PExp)$6;
  PExp postcondition = (PExp)$7;
  LexLocation location = null;
  if (access != null)
    location = combineLexLocation(name.location, postcondition.getLocation());
  else
    location = combineLexLocation(access.getLocation(), postcondition.getLocation());
  AImplicitOperationOperationDefinition ifunc =
    new AImplicitOperationOperationDefinition(location, NameScope.GLOBAL, null, access, parameterPatterns, result,
					      externals, precondition, postcondition, null, null);
  $$ = ifunc;
}
;

operationType :
  type DEQRARROW type // TODO
| LRPAREN DEQRARROW type // TODO
| type DEQRARROW LRPAREN // TODO
| LRPAREN DEQRARROW LRPAREN // TODO
;

operationBody :
  letStatement // TODO
| blockStatement // TODO
| controlStatement // TODO
| SUBCLASSRESP
{
  $$ = new ASubclassResponsibilityAction(extractLexLocation((CmlLexeme)$1));
}
| NOTYETSPEC
{
  $$ = new ANotYetSpecifiedAction(extractLexLocation((CmlLexeme)$1), null, null);
}
;

externals_opt :
  externals
{
  $$ = $1;
}
| /* empty */
{
  $$ = null;
}
;

externals :
  FRAME varInformationList
{
  $$ = $2;
}
;

varInformationList :
  varInformation
{
  List<AExternalClause> infoList = new Vector<AExternalClause>();
  infoList.add((AExternalClause)$1);
  $$ = infoList;
}
| varInformationList varInformation
{
  List<AExternalClause> infoList = (List<AExternalClause>)$1;
  infoList.add((AExternalClause)$2);
  $$ = infoList;
}
;

varInformation :
  mode pathList
{
  $$ = new AExternalClause((LexToken)$1, (List<? extends LexNameToken>)$2);
}
| mode pathList COLON type
{
  $$ = new AExternalClause((LexToken)$1, (List<? extends LexNameToken>)$2, (PType)$4);
}
;

mode :
  RD
{
  $$ = new LexToken(extractLexLocation((CmlLexeme)$1), VDMToken.READ); // TODO why are we using VDMToken?
}
| WR
{
  $$ = new LexToken(extractLexLocation((CmlLexeme)$1), VDMToken.WRITE); // TODO why are we using VDMToken?
}
;

/* RWL, invariantDef
 *
 * In the AST PDefinition and PInvariant does not have a common
 * ancestor below Object ! Hence having a list containing both is
 * troublesome.
 *
 * Therefore, the stateDefs and stateDefList has been changed such
 * that invariantDef is separate from the stateDefList.
 *
 * FIXME: The invariantDef needs to be glued onto the tree.
 *
 * AKM, proposed fix:
 * The invariant in the state declaration would correspond to the old
 * "classInvariant" definition which is in the AST allready.
 * So maybe we should just find a more suitable name for it. For now
 * I have changed the grammar back and used the AClassInvariantDefinition
 * class witout a rename.
 */
stateDefs :
  STATE stateDefList
{
  AStateDefinition stateDef = (AStateDefinition)$2;
  $$  = new AStateDeclaration(null, NameScope.GLOBAL, stateDef);
}
| STATE
{
  $$  = new AStateDeclaration(extractLexLocation((CmlLexeme)$1), NameScope.GLOBAL, null);
}
;

stateDefList :
  stateDef
{
  AStateDefinition stateDef = new AStateDefinition();
  List<PDefinition> defs = new Vector<PDefinition>();
  defs.add((PDefinition)$1);
  stateDef.setStateDefs(defs);
  $$ = stateDef;
}
| stateDef SEMI
{
  AStateDefinition stateDef = new AStateDefinition();
  List<PDefinition> defs = new Vector<PDefinition>();
  defs.add((PDefinition)$1);
  stateDef.setStateDefs(defs);
  $$ = stateDef;
}
| stateDef SEMI stateDefList
{
  AStateDefinition stateDef = (AStateDefinition)$3;
  stateDef.getStateDefs().add((PDefinition)$1);
  $$ = stateDef;
}
;

stateDef :
  qualifier assignmentDef
{
  $$ = $2;
}
| INV expression
{
  PExp exp = (PExp) $2;
  LexLocation location = extractLexLocation((CmlLexeme)$1, exp.getLocation());
  $$ = new AClassInvariantDefinition(location, NameScope.GLOBAL, true, null, exp);
}
;

expressionList :
  expression
{
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)$1);
  $$ = exps;
}
| expressionList COMMA expression
{
  List<PExp> exps = (List<PExp>)$1;
  exps.add((PExp)$3);
  $$ = exps;
}
;

expression :
/* RWL On strings:
 *
 * In the lexer whole strings are matched up because it is easy given
 * its state machine functionality. At the same time string handling
 * is captured within a few lines of gammar (in the lexers STRING
 * states). However, building a string as a "seq of char" would
 * otherwise have been done by the parser and therefore the expected
 * result is a sequence of char. In this rule we take the lexer STRING
 * a part and creates the corresponding character expressions.
 *
 */
  STRING
{
  // Get a whole STRING from the lexer
  CmlLexeme s = (CmlLexeme)$1;
  LexLocation sl = extractLexLocation(s);

  // extract the string and convert it to a char array
  String str = s.getValue();
  char[] chrs = str.toCharArray();

  // build a list of ACharLiteralSymbolicLiteralExp from the lexer String
  List<PExp> members = new LinkedList<PExp>();
  for(int i = 0; i < chrs.length;i++) {
    LexLocation cl = new LexLocation(currentSourceFile.getFile(), "Default",
				     sl.startLine, sl.startPos + i,
				     sl.startLine, sl.startPos + (i + 1), 0, 0);
    members.add(new ACharLiteralSymbolicLiteralExp(cl, new LexCharacterToken(chrs[i], cl)));
  }

  // Build the ASeqEnumSeqExp as usual
  ASeqEnumSeqExp res = new ASeqEnumSeqExp(sl, members);
  $$ = res;
}
| LPAREN expression RPAREN
{
  LexLocation loc = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3);
  $$ = new ABracketedExp(loc, (PExp)$2);
}
| LET localDefList IN expression
{
  List<PDefinition> l = (List<PDefinition>)$2;
  PExp e = (PExp)$4;
  LexLocation loc = extractLexLocation((CmlLexeme)$1, e.getLocation());
  $$ = new ALetDefExp(loc, l, e);
}
| ifExpr
{
  $$ = $1;
}
| casesExpr
{
  $$ = $1;
}
| unaryExpr
{
  $$ = $1;
}
| binaryExpr
{
  $$ = $1;
}
/* quantified expressions */
| FORALL bindList AMP expression
{
  CmlLexeme forall = (CmlLexeme)$1;
  List<PMultipleBind> binds = (List<PMultipleBind>)$2;
  CmlLexeme amp = (CmlLexeme)$3;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(forall), exp.getLocation());
  AForAllExp forallexp = new AForAllExp(loc, binds, exp);
  $$ = forallexp;
}
| EXISTS bindList AMP expression
{
  CmlLexeme exists = (CmlLexeme)$1;
  List<PMultipleBind> binds = (List<PMultipleBind>)$2;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  AExistsExp existsExp = new AExistsExp(loc, binds, exp);
  $$ = existsExp;
}
| EXISTS1 bind AMP expression
{
  CmlLexeme exists = (CmlLexeme)$1;
  PBind bind = (PBind)$2;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  AExists1Exp existsExp = new AExists1Exp(loc, bind, exp, null);
  $$ = existsExp;
}
/* set enumeration */
| LCURLY RCURLY
{
  CmlLexeme lc = (CmlLexeme)$1;
  CmlLexeme rc = (CmlLexeme)$2;
  LexLocation loc = combineLexLocation(extractLexLocation(lc), extractLexLocation(rc));
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation(loc);
  $$ = res;
}
| LCURLY expressionList RCURLY
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3);
  List<PExp> members = (List<PExp>)$2;
  $$ = new ASetEnumSetExp(location, members);
}
/* set comprehensions */
| LCURLY expression BAR bindList RCURLY
{
  CmlLexeme lcurly = (CmlLexeme)$1;
  PExp exp = (PExp)$2;
  List<PMultipleBind> binds = (List<PMultipleBind>)$4;
  CmlLexeme rcurly = (CmlLexeme)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  ASetCompSetExp setComp = new ASetCompSetExp(loc, exp, binds, null);
  $$ = setComp;
}
| LCURLY expression BAR bindList AMP expression RCURLY
{
    CmlLexeme lcurly = (CmlLexeme)$1;
    PExp exp = (PExp)$2;
    List<PMultipleBind> binds = (List<PMultipleBind>)$4;
    PExp pred = (PExp)$6;
    CmlLexeme rcurly = (CmlLexeme)$7;
    LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
    ASetCompSetExp setComp = new ASetCompSetExp(loc, exp, binds, pred);
    $$ = setComp;
}
/* set range expression */
| LCURLY expression ELLIPSIS expression RCURLY
{
  CmlLexeme lcurly = (CmlLexeme)$1;
  PExp start = (PExp)$2;
  PExp end = (PExp)$4;
  CmlLexeme rcurly = (CmlLexeme)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  ASetRangeSetExp res = new ASetRangeSetExp(loc, start, end);
  $$ = res;
}
/* sequence enumerations */
| LSQUARE RSQUARE
{
  CmlLexeme lsqr = (CmlLexeme)$1;
  CmlLexeme rsqr = (CmlLexeme)$2;
  List<PExp> exps = new LinkedList<PExp>();
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
  $$ = exp;
}
| LSQUARE expressionList RSQUARE
{
  CmlLexeme lsqr = (CmlLexeme)$1;
  List<PExp> exps = (List<PExp>)$2;
  CmlLexeme rsqr = (CmlLexeme)$3;
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
  $$ = exp;
}
/* sequence comprehensions */
| LSQUARE expression BAR setBind RSQUARE
{
  CmlLexeme lsqr = (CmlLexeme)$1;
  PExp exp = (PExp)$2;
  ASetBind binds = (ASetBind)$4;
  CmlLexeme rsqr = (CmlLexeme)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqCompSeqExp res = new ASeqCompSeqExp(loc, exp, binds, null);
  $$ = res;
}
| LSQUARE expression BAR setBind AMP expression RSQUARE
{
  CmlLexeme lsqr = (CmlLexeme)$1;
  PExp exp = (PExp)$2;
  ASetBind binds = (ASetBind)$4;
  PExp pred = (PExp)$6;
  CmlLexeme rsqr = (CmlLexeme)$7;
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqCompSeqExp res = new ASeqCompSeqExp(loc, exp, binds, pred);
  $$ = res;
}
/* map enumerations */
| LCURLY BARRARROW RCURLY
{
  CmlLexeme lcurly = (CmlLexeme)$1;
  CmlLexeme rcurly = (CmlLexeme)$2;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  AMapEnumMapExp res = new AMapEnumMapExp(loc, new LinkedList<AMapletExp>());
  $$ = res;
}
| LCURLY mapletList RCURLY
{
  CmlLexeme lcurly = (CmlLexeme)$1;
  List<AMapletExp> maplets = (List<AMapletExp>)$2;
  CmlLexeme rcurly = (CmlLexeme)$3;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  AMapEnumMapExp res = new AMapEnumMapExp(loc, maplets);
  $$ = res;
}
/* map comprehensions */
| LCURLY maplet BAR bindList RCURLY
{
  CmlLexeme lcurl = (CmlLexeme)$1;
  AMapletExp maplet = (AMapletExp)$2;
  List<PMultipleBind> binds = (List<PMultipleBind>)$4;
  CmlLexeme rcurl = (CmlLexeme)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurl), extractLexLocation(rcurl));
  AMapCompMapExp res = new AMapCompMapExp(loc, maplet, binds, null);
  $$ = res;
}
| LCURLY maplet BAR bindList AMP expression RCURLY
{
  CmlLexeme lcurl = (CmlLexeme)$1;
  AMapletExp maplet = (AMapletExp)$2;
  List<PMultipleBind> binds = (List<PMultipleBind>)$4;
  PExp pred = (PExp)$6;
  CmlLexeme rcurl = (CmlLexeme)$7;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurl), extractLexLocation(rcurl));
  AMapCompMapExp res = new AMapCompMapExp(loc, maplet, binds, pred);
  $$ = res;
}
| tupleConstructor
{
  $$ = $1;
}
| recordConstructor
{
  $$ = $1;
}
| lambdaExpr
{
  $$ = $1;
}
| generalIsExpr
{
  $$ = $1;
}
| preconditionExpr
{
  $$ = $1;
}
/* DEVIATION --- PATH
 * CML_0:
 *   ISOFCLASS LPAREN name COMMA expression RPAREN
 * TODO: convert to a name
 */
| ISOFCLASS LPAREN path COMMA expression RPAREN
{
  $$ = $1;
}
/* DEVIATION --- PATH
 * CML_0:
 *   name
 *   IDENTIFIER TILDE // oldName
 *   expression LPAREN expression ELLIPSIS expression RPAREN // subsequence expression
 *   expression DOTHASH NUMERAL // tuple select
 *   expression DOT IDENTIFIER // field select
 *   SELF
 * TODO:
 * 1) convert to a name
 * 2) convert to an oldName
 * 3) convert to a subsequence expression
 * 4) convert to a tuple select
 * 5) convert to a field select
 * 6) convert to a self expression
 */
| path // TODO
| symbolicLiteral // TODO
;

/* TODO --- verify
 *
 * We need to check that these are, indeed, the right possible literals.
 */
symbolicLiteral :
  numericLiteral
{
  LexIntegerToken lit = (LexIntegerToken)$1;
  $$ = new AIntLiteralSymbolicLiteralExp(lit.location, lit);
}
| booleanLiteral
| nilLiteral
| characterLiteral
| textLiteral
| quoteLiteral
{
  LexQuoteToken value = (LexQuoteToken)$1;
  $$ = new AQuoteLiteralSymbolicLiteralExp(value.location, value);
}
;

numericLiteral :
  NUMERAL
{
  CmlLexeme lexeme = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation(lexeme);
  $$ = new LexIntegerToken(Long.decode(lexeme.getValue()), loc);
}
| HEX_LITERAL
{
  CmlLexeme lexeme = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation(lexeme);
  $$ = new LexIntegerToken(Long.decode(lexeme.getValue()), loc);
}
;

localDefList :
  localDef
{
  List<PDefinition> res = new LinkedList<PDefinition>();
  res.add((PDefinition)$1);
  $$ = res;
}
| localDefList COMMA localDef
{
  PDefinition def = (PDefinition)$3;
  List<PDefinition> defs = (List<PDefinition>)$1;
  defs.add(def);
  $$ = defs;
}
;

localDef :
  valueDef
{
  $$ = $1;
}
| explicitFunctionDef
{
  $$ = $1;
}
;

ifExpr :
  IF expression THEN expression elseExprs
{
  CmlLexeme _if = (CmlLexeme)$1;
  Position sif = _if.getStartPos();
  Position eif = _if.getEndPos();
  PExp test = (PExp)$2;
  PExp then = (PExp)$4;
  List<AElseIfExp> elses = (List<AElseIfExp>)$5;
  AIfExp ifexp = new AIfExp();
  ifexp.setTest(test);
  ifexp.setThen(then);
  ifexp.setElseList(elses);
  ifexp.setLocation(combineLexLocation(new LexLocation(null, "DEFAULT", sif.line, sif.column, sif.line, eif.column,
						       sif.offset, eif.offset),
				       extractLastLexLocation(elses)));
  $$ = ifexp;
}
;

elseExprs :
  ELSE expression
{
  CmlLexeme elsetok = (CmlLexeme)$1;
  PExp exp = (PExp)$2;
  AElseIfExp eie = new AElseIfExp();
  eie.setLocation(combineLexLocation(extractLexLocation(elsetok), exp.getLocation()));
  List<AElseIfExp> res = new LinkedList<AElseIfExp>();
  res.add(eie);
  $$ = res;
}
| ELSEIF expression THEN expression elseExprs
{
  CmlLexeme elseif = (CmlLexeme)$1;
  PExp elseIf = (PExp)$2;
  PExp then = (PExp)$4;
  List<AElseIfExp> tail = (List<AElseIfExp>)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(elseif), then.getLocation());
  AElseIfExp eie = new AElseIfExp();
  eie.setElseIf(elseIf);
  eie.setThen(then);
  eie.setLocation(loc);
  tail.add(eie);
  $$ = tail;
}
;

casesExpr :
  CASES expression COLON casesExprAltList END
{
  CmlLexeme cases = (CmlLexeme)$1;
  PExp exp = (PExp)$2;
  ACasesExp bubbleUp = (ACasesExp)$4; // Others and Cases are taken care of
  CmlLexeme end = (CmlLexeme)$5;
  LexLocation lexLoc = combineLexLocation(extractLexLocation(cases), extractLexLocation(end));
  bubbleUp.setExpression(exp);
  bubbleUp.setLocation(lexLoc);
  $$ = bubbleUp;
}
| CASES expression COLON casesExprAltList OTHERS RARROW expression END
/* { // from casesExprAlt OTHERS RARROW expression --- this clearly isn't correct here */
/*   ACaseAlternative altExp = (ACaseAlternative)$1; */
/*   PExp othExp = (PExp)$4; */
/*   List<ACaseAlternative> altList = new LinkedList<ACaseAlternative>(); */
/*   altList.add(altExp); */
/*   ACasesExp casesExp = new ACasesExp(); */
/*   casesExp.setCases(altList); */
/*   casesExp.setOthers(othExp); */
/*   $$ = casesExp; */
/* } */
;

casesExprAltList :
  casesExprAlt
/* { */
/*   ACasesExp casesExp = new ACasesExp(); */
/*   ACaseAlternative caseAlt = (ACaseAlternative)$1; */
/*   casesExp.getCases().add(caseAlt); */
/*   $$ = casesExp; */
/* } */
| casesExprAltList casesExprAlt
/* { */
/*   ACasesExp casesExp = (ACasesExp)$1; */
/*   ACaseAlternative altExp = (ACaseAlternative)$2; */
/*   casesExp.getCases().add(altExp); */
/*   $$ = casesExp; */
/* } */
;

casesExprAlt :
  patternList RARROW expression SEMI
{
  List<PPattern> patList = (List<PPattern>)$1;
  PExp exp = (PExp)$3;
  CmlLexeme semi = (CmlLexeme)$4;
  LexLocation leftMost = extractLexLeftMostFromPatterns(patList);
  LexLocation loc = combineLexLocation(leftMost, extractLexLocation(semi));
  ACaseAlternative res = new ACaseAlternative();
  res.setPattern(patList);
  res.setLocation(loc);
  res.setCexp(exp);
  $$ = res;
}
;

unaryExpr :
  PLUS expression %prec UPLUS
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AUnaryPlusUnaryExp(location, exp);
}
| MINUS expression %prec UMINUS
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AUnaryMinusUnaryExp(location, exp);
}
| ABS expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AAbsoluteUnaryExp(location, exp);
}
| FLOOR expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AFloorUnaryExp(location, exp);
}
| NOT expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ANotUnaryExp(location, exp);
}
| CARD expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ACardinalityUnaryExp(location, exp);
}
| POWER expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new APowerSetUnaryExp(location, exp);
}
| DUNION expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ADistUnionUnaryExp(location, exp);
}
| DINTER expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ADistIntersectUnaryExp(location, exp);
}
| HD expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AHeadUnaryExp(location, exp);
}
| TL expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ATailUnaryExp(location, exp);
}
| LEN expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ALenUnaryExp(location, exp);
}
| ELEMS expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AElementsUnaryExp(location, exp);
}
| INDS expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AIndicesUnaryExp(location, exp);
}
| REVERSE expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AReverseUnaryExp(location, exp);
}
| CONC expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ADistConcatUnaryExp(location, exp);
}
| DOM expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AMapDomainUnaryExp(location, exp);
}
| RNG expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AMapRangeUnaryExp(location, exp);
}
| MERGE expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ADistMergeUnaryExp(location, exp);
}
| INVERSE expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AMapInverseUnaryExp(location, exp);
}
  ;

binaryExpr :
  expression PLUS expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new APlusNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression STAR expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new ATimesNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression MINUS expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new ASubstractNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression DIVIDE expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new ADivideNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression SLASH expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new ADivNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression REM expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new ARemNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression MOD expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new AModNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression LT expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new ALessNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression LTE expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new ALessEqualNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression GT expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new AGreaterNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression GTE expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new AGreaterEqualNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression EQUALS expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new AEqualsBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression NEQ expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new ANotEqualBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression OR expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new AOrBooleanBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression AND expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new AAndBooleanBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression EQRARROW expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new AImpliesBooleanBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression LTEQUALSGT expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new AEquivalentBooleanBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression INSET expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new AInSetBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression NOTINSET expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new ANotInSetBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression SUBSET expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new ASubsetBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression PROPER_SUBSET expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new AProperSubsetBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression UNION expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new ASetUnionBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression BACKSLASH expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new ASetDifferenceBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression INTER expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new ASetIntersectBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression CARET expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new ASeqConcatBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression DPLUS expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new AModifyBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression MAPMERGE expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
    $$ = new AMapUnionBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression LTCOLON expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new ADomainResToBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression LTDASHCOLON expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new AModifyBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression COLONGT expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new AModifyBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression COLONDASHGT expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new AModifyBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression COMP expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new AModifyBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression DSTAR expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  $$ = new AModifyBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
;

mapletList :
  maplet
{
  List<AMapletExp> res = new LinkedList<AMapletExp>();
  res.add((AMapletExp)$1);
  $$ = res;
}
| mapletList COMMA maplet
{
  List<AMapletExp> maplets = (List<AMapletExp>)$1;
  AMapletExp hd = (AMapletExp)$3;
  maplets.add(hd);
  $$ = maplets;
}
;

maplet :
  expression BARRARROW expression
{
  PExp domValue = (PExp)$1;
  PExp rngValue = (PExp)$3;
  LexLocation loc = combineLexLocation(domValue.getLocation(), rngValue.getLocation());
  AMapletExp res = new AMapletExp(loc, domValue, rngValue);
  $$ = res;
}
;

tupleConstructor :
  MKUNDER LPAREN expressionList RPAREN
{
  CmlLexeme mku = (CmlLexeme)$1;
  List<PExp> exprs = (List<PExp>)$3;
  CmlLexeme rparen = (CmlLexeme)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(mku), extractLexLocation(rparen));
  ATupleExp res = new ATupleExp(loc, exprs);
  $$ = res;
}
;

recordConstructor :
  MKUNDERNAME LPAREN expressionList RPAREN
{

  CmlLexeme mku = (CmlLexeme)$1;
  LexNameToken name = getNameTokenFromMKUNDERNAME(mku);
  List<PExp> exprs = (List<PExp>)$3;
  LexLocation loc = combineLexLocation(extractLexLocation(mku), extractLexLocation((CmlLexeme)$4));
  ARecordExp res = new ARecordExp(loc, name, exprs);
  $$ = res;
}
;

/* DEVIATION
 * CML_0:
 *   'lambda' typeBindList '@' expression
 * here:
 *   'lambda' typeBindList '&' expression
 *
 * Using an @ causes a lot of s/r conflicts
 */
lambdaExpr :
  LAMBDA typeBindList AMP expression
{
  CmlLexeme l = (CmlLexeme)$1;
  List<ATypeBind> binds = (List<ATypeBind>)$2;
  PExp body = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(l), body.getLocation());
  ALambdaExp res = new ALambdaExp(loc, binds, body, null, null);
  $$ = res;
}
;

generalIsExpr :
/* DEVIATION --- PATH
 * CML_0:
 *   ISUNDER name LPAREN expression RPAREN
 * here:
 *   ISUNDER IDENTIFIER LPAREN expression RPAREN
 *   ISUNDER IDENTIFIER DOT IDENTIFIER LPAREN expression RPAREN
 *   ISUNDER IDENTIFIER BACKTICK IDENTIFIER LPAREN expression RPAREN
 * TODO: convert to a name
 *
 * I'm not sure the syntax of this is quite correct: I don't think we
 * want to allow "is_ NAME ( ... )" with the space between is_ and the
 * NAME.  We may need to do something more with the lexer, here.
 */
  ISUNDER IDENTIFIER LPAREN expression RPAREN
| ISUNDER IDENTIFIER DOT IDENTIFIER LPAREN expression RPAREN
| ISUNDER IDENTIFIER BACKTICK IDENTIFIER LPAREN expression RPAREN
/* { */
/*   CmlLexeme isUnder = (CmlLexeme)$1; */
/*   LexNameToken typeName = (LexNameToken)$2; */
/*   PExp test = null;//(PExp)$4; */
/*   LexLocation loc = combineLexLocation(extractLexLocation(isUnder), typeName.getLocation()); */
/*   AIsExp res = new AIsExp(loc, typeName, test, null); */
/*   $$ = res; */
/* } */
| ISUNDER basicType LPAREN expression RPAREN
{
  CmlLexeme isUnder = (CmlLexeme)$1;
  PType type = (PType)$2;
  PExp test = (PExp)$4;
  CmlLexeme rparen = (CmlLexeme)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(isUnder), extractLexLocation(rparen));
  AIsExp res = new AIsExp(loc, null, test, null);
  res.setBasicType(type);
  $$ = res;
}
| ISUNDER LPAREN expression COMMA type RPAREN
{
  CmlLexeme isUnder = (CmlLexeme)$1;
  PExp test = (PExp)$3;
  PType type = (PType)$5;
  CmlLexeme rparen = (CmlLexeme)$6;
  LexLocation loc = combineLexLocation(extractLexLocation(isUnder), extractLexLocation(rparen));
  AIsExp res = new AIsExp(loc, null, test, null);
  res.setBasicType(type);
  $$ = res;
}
;

preconditionExpr :
  PREUNDER LPAREN expressionList RPAREN
{
  CmlLexeme preu = (CmlLexeme)$1;
  List<PExp> exprs = (List<PExp>)$3;
  CmlLexeme rparen = (CmlLexeme)$4;
  // RWL FIXME: Either this is right because we dedeuce the
  // function in a later phase where we know more or
  // the production above should be PREUNDER exp LPAREN expList RPAREN
  // however that introduces 36 reduce/reduce conflicts at this time.
  PExp function = null;
  LexLocation loc = combineLexLocation(extractLexLocation(preu), extractLexLocation(rparen));
  APreExp res = new APreExp(loc, function, exprs);
  $$ = res;
}
;

controlStatement :
  nonDeterministicIfStatement
{
  $$ = $1;
}
| ifStatement
{
  $$ = $1;
}
| casesStatement
// FIXME --- is/was this a rule?
/*| generalCasesIfStatement*/ //TODO
  /* DEVIATION --- PATH
   * CML_0:
   *  callStatement
   * TODO: this gets merged with generalAssignStatement
   */
/* | callStatement */
// FIXME --- causes r/r conflict with objectDesignator(call)
| generalAssignStatement
{
  $$ = $1;
}
| specificationStatement // TODO
| returnStatement
{
  $$ = $1;
}
/* DEVIATION --- PATH
 * CML_0:
 *   stateDesignator COLONEQUALS NEW name LRPAREN
 *   stateDesignator COLONEQUALS NEW name LPAREN expressionList RPAREN
 *
 * TODO: need the convert the paths to stateDesignator and name, resp.
 */
/*   path COLONEQUALS NEW path LRPAREN */
/* | path COLONEQUALS NEW path LPAREN expressionList RPAREN */
/*| non-deterministicDoStatement */ // TODO
/*| SequenceForLoop */ // TODO
/*| setForLoop */ // TODO
/*| indexForLoop*/ // TODO
/*| whileLoop */ // TODO
;

nonDeterministicIfStatement :
  IF expression RARROW action END
{
  $$ = new ANonDeterministicIfControlStatementAction(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$5), (PExp)$2, (PAction)$4, null);
}
| IF expression RARROW action nonDeterministicIfAltList END
{
  $$ = new ANonDeterministicIfControlStatementAction(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$5), (PExp)$2, (PAction)$4, (List<ANonDeterministicElseIfControlStatementAction>)$5);
}
;

nonDeterministicIfAlt :
  BAR expression RARROW action
{
  PAction thenStm = (PAction)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1, thenStm.getLocation());
  $$ = new ANonDeterministicElseIfControlStatementAction(location, (PExp)$2, thenStm);
}
;

nonDeterministicIfAltList :
  nonDeterministicIfAlt
{
  List<ANonDeterministicElseIfControlStatementAction> alts = new Vector<ANonDeterministicElseIfControlStatementAction>();
  alts.add((ANonDeterministicElseIfControlStatementAction)$1);
  $$ = alts;
}
| nonDeterministicIfAltList nonDeterministicIfAlt
{
  List<ANonDeterministicElseIfControlStatementAction> alts = (List<ANonDeterministicElseIfControlStatementAction>)$1;
  alts.add((ANonDeterministicElseIfControlStatementAction)$2);
  $$ = alts;
}
;

/* let statements */

letStatement :
  LET localDefList IN action // TODO
;

/* 6.2 Block and Assignment Statements
 * to be clarified
 */

/* FIXME trailing semicolon not optional */
blockStatement :
  LPAREN action RPAREN
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3);
  PAction action = (PAction)$2;
  $$ = new ABlockAction(location, null, action);
}
| LPAREN dclStatement action RPAREN
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$4);
  ADeclareStatementDeclareStatement dclStm = (ADeclareStatementDeclareStatement)$2;
  PAction action = (PAction)$3;
  $$ = new ABlockAction(location, dclStm, action);
}
  ;

dclStatement :
  DCL assignmentDefList AT
{
  $$ = new ADeclareStatementDeclareStatement(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3),
					     (List<? extends PDefinition>)$2);
}
;

assignmentDefList :
  assignmentDef
{
  List<AAssignmentDefinition> assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)$1);
  $$ = assignmentDefs;
}
| assignmentDefList COMMA assignmentDef
{
  List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)$1;
  if (assignmentDefs == null)
    assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)$3);
  $$ = assignmentDefs;
}
;

assignmentDef :
  IDENTIFIER COLON type
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$1);
  PType type = (PType)$3;
  LexLocation location = combineLexLocation(name.location, type.getLocation());
  AAccessSpecifierAccessSpecifier access = null;
  $$ = new AAssignmentDefinition(location, name, NameScope.LOCAL, false, null, access, type, null, null);
}
| IDENTIFIER COLON type COLONEQUALS expression // TODO
| IDENTIFIER COLON type IN expression // TODO
;

generalAssignStatement :
  assignStatement
{
  $$ = $1;
}
| multiAssignStatement // TODO
;

assignStatementList :
  assignStatement // TODO
| assignStatementList SEMI assignStatement // TODO
;

multiAssignStatement :
  ATOMIC LPAREN assignStatementList RPAREN // TODO
;

assignStatement :
/* DEVIATION --- PATH
 * CML_0:
 *   stateDesignator COLONEQUALS expression // was stateDesignator
 * TODO: convert to a stateDesignator
 */
  path COLONEQUALS expression
{
  PStateDesignator stateDesignator = convertToStateDesignator((PDesignator)$1);
  PExp exp = (PExp)$3;
  LexLocation location = combineLexLocation(stateDesignator.getLocation(), exp.getLocation());
  $$ = new ASingleGeneralAssignmentControlStatementAction(location, stateDesignator, (PExp)$3);
}
;

ifStatement :
  IF expression THEN action elseStatements ELSE action
{
  PAction action = (PAction)$7;
  $$ = new AIfControlStatementAction(extractLexLocation((CmlLexeme)$1, action.getLocation()),
				     (PExp)$2, (PAction)$4, (List<? extends AElseIfControlStatementAction>)$5, action);
}
| IF expression THEN action ELSE action
{
  PAction action = (PAction)$6;
  $$ = new AIfControlStatementAction(extractLexLocation((CmlLexeme)$1, action.getLocation()),
				     (PExp)$2, (PAction)$4, null, action);
}
;

elseStatements :
  ELSEIF expression THEN action
{
  List<AElseIfControlStatementAction> elseStms = new Vector<AElseIfControlStatementAction>();
  PAction thenStm = (PAction)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1, thenStm.getLocation());
  elseStms.add(new AElseIfControlStatementAction(location, (PExp)$2, thenStm));
  $$ = elseStms;
}
| elseStatements ELSEIF expression THEN action
{
  PAction action = (PAction)$5;
  List<AElseIfControlStatementAction> elseStms = (List<AElseIfControlStatementAction>)$1;
  LexLocation location = combineLexLocation(extractLastLexLocation(elseStms), extractLexLocation((CmlLexeme)$4));
  elseStms.add(0, new AElseIfControlStatementAction(location, (PExp)$3, action));
  $$ = elseStms;
}

;

casesStatement :
  CASES expression COLON casesStatementAltList END
{
    LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$5);
    ACasesControlStatementAction cases = (ACasesControlStatementAction)$4;
    cases.setLocation(location);
    cases.setExp((PExp)$2);
    $$ = cases;
}
| CASES expression COLON casesStatementAltList OTHERS RARROW action END
/*   // FROM | casesStatementAlt COMMA OTHERS RARROW action */
/* { */
/*   List<ACaseAlternativeAction> casesList = new Vector<ACaseAlternativeAction>(); */
/*   casesList.add((ACaseAlternativeAction)$1); */
/*   ACasesControlStatementAction cases = new ACasesControlStatementAction(null, null, casesList, (PAction)$5); */
/*   $$ = cases; */
/* } */
;

casesStatementAltList :
  casesStatementAlt
{
  List<ACaseAlternativeAction> casesList = new Vector<ACaseAlternativeAction>();
  casesList.add((ACaseAlternativeAction)$1);
  ACasesControlStatementAction cases = new ACasesControlStatementAction(null, null, casesList, null);
  $$ = cases;
}
| casesStatementAlt COMMA casesStatementAltList
{
  ACasesControlStatementAction cases = (ACasesControlStatementAction)$3;
  cases.getCases().add((ACaseAlternativeAction)$1);
  $$ = cases;
}
;

casesStatementAlt :
  patternList RARROW action
{
    PAction action = (PAction)$3;
    List<PPattern> patterns = (List<PPattern>)$1;
    $$ = new ACaseAlternativeAction(combineLexLocation(extractFirstLexLocation(patterns), action.getLocation()),
				    patterns, (PAction)$3);
}
;

returnStatement :
  RETURN SEMI  // TODO
| RETURN expression
{
  PExp exp = (PExp)$2;
  $$ = new AReturnControlStatementAction(extractLexLocation((CmlLexeme)$1, exp.getLocation()), exp);
}
;

specificationStatement :
  LSQUARE implicitOperationBody RSQUARE // TODO
;

implicitOperationBody :
  externals_opt preExpr_opt postExpr // TODO
;

pattern :
  patternIdentifier // TODO
| patternLessID // TODO
;

patternLessID :
  matchValue // TODO
| tuplePattern // TODO
| recordPattern // TODO
;

patternList :
  pattern
{
  List<PPattern> patterns = new Vector<PPattern>();
  patterns.add((PPattern)$1);
  $$ = patterns;
}
| patternList COMMA pattern
{
  List<PPattern> patterns = (Vector<PPattern>)$1;
  patterns.add((PPattern)$3);
  $$ = patterns;
}
  ;

patternIdentifier :
  IDENTIFIER
{
  CmlLexeme lexeme = (CmlLexeme)$1;
  LexNameToken lnt = extractLexNameToken(lexeme);
  AIdentifierPattern res = new AIdentifierPattern();
  res.setName(lnt);
  res.setLocation(lnt.getLocation());
  $$ = res;
}
// FIXME -- shouldn't this be in patternLessID?
| MINUS // TODO: Implement "don't care" pattern
;

matchValue :
  symbolicLiteral
{
  PExp exp = (PExp)$1;
  if (exp instanceof AIntLiteralSymbolicLiteralExp) {
    AIntLiteralSymbolicLiteralExp intExp = (AIntLiteralSymbolicLiteralExp)exp;
    AIntegerPattern res = new AIntegerPattern();
    res.setLocation(intExp.getLocation());
    res.setValue(intExp.getValue());
    $$ = res;
  } else {
    throw new RuntimeException("Unhandled expression type in pattern. ("+exp.getClass()+")"); // TODO RWL
  }
}
| LPAREN expression RPAREN //TODO
;

/* FIXME not sure if if this is a minimum of one pattern or two; if the latter */
tuplePattern :
//  MKUNDER LPAREN patternList RPAREN // TODO
  MKUNDER LPAREN patternList COMMA pattern RPAREN
;

recordPattern :
  MKUNDERNAME LRPAREN
{
  List<? extends PPattern> plist = null;
  LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)$1);
  $$ = new ARecordPattern(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$2), null, false, name, plist);
}
| MKUNDERNAME LPAREN patternList RPAREN
{
    List<? extends PPattern> plist = (List<? extends PPattern>)$3;
    LexNameToken name = getNameTokenFromMKUNDERNAME((CmlLexeme)$1);
    $$ = new ARecordPattern(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$4), null, false, name, plist);
}
;

bind :
  setBind
{
  $$ = $1;
}
| typeBind
{
  $$ = $1;
}
;

setBind :
  pattern INSET expression
{
  PPattern pattern = (PPattern)$1;
  PExp exp = (PExp)$3;
  LexLocation location = extractLexLocation((CmlLexeme)$2);
  $$ = new ASetBind(location, pattern, exp);
}
;

typeBind :
  pattern COLON type
{
  PPattern pattern = (PPattern)$1;
  PType type = (PType)$3;
  LexLocation location = extractLexLocation((CmlLexeme)$2);
  $$ = new ATypeBind(location, pattern, type);
}
;

bindList :
  multipleBind
{
  List<PMultipleBind> binds = new Vector<PMultipleBind>();
  binds.add((PMultipleBind)$1);
  $$ = binds;
}
| bindList COMMA multipleBind
{
  List<PMultipleBind> binds = (List<PMultipleBind>)$1;
  binds.add((PMultipleBind)$3);
  $$ = binds;
}
;

multipleBind :
  multipleSetBind
{
  $$ = $1;
}
| multipleTypeBind
;

multipleSetBind :
  patternList INSET expression
{
  List<PPattern> patterns = (List<PPattern>)$1;
  PExp exp = (PExp)$3;
  LexLocation location = extractLexLocation((CmlLexeme)$2);;
  $$ = new ASetMultipleBind(location, patterns, exp);
}
;

multipleTypeBind :
  patternList COLON type
{
  List<PPattern> patterns = (List<PPattern>)$1;
  PType type = (PType)$3;
  LexLocation location = extractLexLocation((CmlLexeme)$2);;
  $$ = new ATypeMultipleBind(location, patterns, type);
}
;

typeBindList :
  typeBind
{
  ATypeBind tb = (ATypeBind)$1;
  List<ATypeBind> res = new LinkedList<ATypeBind>();
  res.add(tb);
  $$ = res;
}
| typeBindList COMMA typeBind
{
  ATypeBind hd = (ATypeBind)$3;
  List<ATypeBind> tbl = (List<ATypeBind>)$1;
  tbl.add(hd);
  $$ = tbl;
}
;

/* New path-based naming scheme, to replace *Designators and names ---
 * using this requires the use of a conversion function to take one of
 * these and generate the VDM-compatible bit of AST
 * -jwc/2012/06/20
 */
path :
  unit
| path TILDE
| path DOT unit
| path BACKTICK unit
| path DOTHASH NUMERAL
| path LRPAREN
| path LPAREN expressionList RPAREN
| path LPAREN expression ELLIPSIS expression RPAREN
;

unit :
  SELF
| IDENTIFIER
;

pathList :
  path
/* { */
/*   LexNameToken lnt = extractLexNameToken((ASimpleName)$1); */
/*   List<LexNameToken> identifiers = new Vector<LexNameToken>(); */
/*   identifiers.add(lnt); */
/*   $$ = identifiers; */
/* } */
| pathList COMMA path
/* { */
/*   LexNameToken lnt = extractLexNameToken((ASimpleName)$3); */
/*   List<LexNameToken> identifiers = (List<LexNameToken>)$1; */
/*   identifiers.add(lnt); */
/*   $$ = identifiers; */
/* } */
;

// **********************
// *** END OF GRAMMAR ***
// **********************
