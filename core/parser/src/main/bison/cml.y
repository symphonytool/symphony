%require "2.5"
%language "java"
%locations
%define package "eu.compassresearch.core.parser"
%define public
%define parser_class_name "CmlParser"
%code imports{

  // ******************************
  // *** required local imports ***
  // ******************************

  // required standard Java definitions
  import java.text.DecimalFormat;
  import java.math.BigInteger;
  import java.util.*;
  import java.io.File;
  import java.io.FileReader;
  import java.io.FileNotFoundException;
  import java.io.InputStreamReader;
  import java.io.Reader;
  import java.lang.reflect.*;
  import eu.compassresearch.ast.definitions.*;
  import eu.compassresearch.ast.declarations.*;
  import eu.compassresearch.ast.expressions.*;
  import eu.compassresearch.ast.actions.*;
  import eu.compassresearch.ast.process.*;
  import eu.compassresearch.ast.patterns.*;
  import eu.compassresearch.ast.program.*;
  import eu.compassresearch.ast.types.*;
  import eu.compassresearch.ast.lex.*;
  import eu.compassresearch.ast.typechecker.NameScope;
  import eu.compassresearch.ast.node.*;
  import eu.compassresearch.ast.node.tokens.*;
  import eu.compassresearch.ast.preview.*;
  import eu.compassresearch.ast.util.*;
  import eu.compassresearch.core.lexer.CmlLexeme;
  import eu.compassresearch.core.lexer.CmlLexer;
  import eu.compassresearch.core.lexer.Position;
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

  private PSource currentSource = null;

  // *************************
  // *** PRIVATE OPERATIONS ***
  // *************************

  public static char convertEscapeToChar(String escape)
  {
    if (escape.startsWith("\\")) {
      switch(escape.charAt(1)) {
      case 'n':  return '\n';
      case '\\': return '\\';
      case 'r': return '\r';
      case 't': return '\t';
      case 'f': return '\f';
      case 'e': return (char)0x001B;
      case 'a': return (char)0x0007;
      case 'x': return (char)new BigInteger(escape.substring(2), 16).intValue();
      case 'u': return (char)new BigInteger(escape.substring(2), 16).intValue();
      case 'c': return (char)(escape.charAt(2) - 'A' + 1);
      case '0': return (char)new BigInteger(escape.substring(2), 8).intValue();
      case '\"' : return '\"';
      case '\'': return '\'';
      default:
	throw new RuntimeException("Illegal escape sequence: "+escape);
      }
    }
    return escape.charAt(0);
  }


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

  private LexNameToken extractNameFromUNDERNAMEToken(CmlLexeme mkUnder)
  {
      String nameString = mkUnder.getValue().substring(3);
      
      LexNameToken name = null;
      if(nameString.matches(".+['`''.'].+")){  
	  String[] tokens = nameString.split("['`''.']");
	  name = new LexNameToken(tokens[0],
				  tokens[1],
				  extractLexLocation(mkUnder),
				  false,
				  true);
      }
      else
	  name = new LexNameToken("Default",
				  nameString,
				  extractLexLocation(mkUnder),
				  false,
				  true);
      return name;
  }

  private AAccessSpecifier getDefaultAccessSpecifier(boolean isStatic, boolean isAsync, LexLocation loc)
  {
    return new AAccessSpecifier(new APublicAccess(),
				(isStatic ? new TStatic() : null),
				(isAsync ? new TAsync() : null),loc);

  }

  private LexLocation extractLexLocation(CmlLexeme lexeme)
  {
    return new LexLocation(currentSource.toString(), "Default",
			   lexeme.getStartPos().line,
			   lexeme.getStartPos().column,
			   lexeme.getEndPos().line,
			   lexeme.getEndPos().column,
			   lexeme.getStartPos().offset,
			   lexeme.getEndPos().offset);
  }

  private LexLocation extractLexLocation(CmlLexeme start, CmlLexeme end)
  {
    return new LexLocation(currentSource.toString(), "Default",
			   start.getStartPos().line,
			   start.getStartPos().column,
			   end.getEndPos().line,
			   end.getEndPos().column,
			   start.getStartPos().offset,
			   end.getEndPos().offset);
  }

  private LexLocation extractLexLocation(CmlLexeme start, LexLocation end)
  {

    return new LexLocation(currentSource.toString(), "Default",
			   start.getStartPos().line, start.getStartPos().column,
			   end.endLine,
			   end.endPos,
			   start.getStartPos().offset,
			   end.endOffset);
  }

  private LexLocation extractLexLocation(LexLocation start, CmlLexeme end)
  {

    return new LexLocation(currentSource.toString(), "Default",
			   start.startLine, start.startPos,
			   end.getEndPos().line,
			   end.getEndPos().column,
			   start.startOffset,
			   end.getEndPos().offset);
  }

  private LexLocation extractLexLocation(LexLocation start, LexLocation end)
  {
    return new LexLocation(currentSource.toString(), "Default",
			   start.startLine, start.startPos,
			   end.endLine,
			   end.endPos,
			   start.startOffset,
			   end.endOffset);
  }

  private LexLocation combineLexLocation(LexLocation start, LexLocation end)
  {
    return extractLexLocation(start,end);
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

  public static CmlParser newParserFromSource(PSource doc) throws FileNotFoundException
  {
    if (doc instanceof AFileSource) {
      AFileSource fs = (AFileSource)doc;
      File f= fs.getFile();
      FileReader reader = new FileReader(f);
      CmlLexer lexer = new CmlLexer(reader);
      CmlParser parser = new CmlParser(lexer);
      parser.setDocument(fs);
      return parser;
    }

    if (doc instanceof AInputStreamSource) {
      AInputStreamSource is = (AInputStreamSource)doc;
      InputStreamReader in = new InputStreamReader(is.getStream());
      CmlLexer lexer = new CmlLexer(in);
      CmlParser parser = new CmlParser(lexer);
      parser.setDocument(is);
      return parser;
    }
    return null;
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

  private List<LexIdentifierToken> convertPathListToIdentifiers(List<LexNameToken> pathList)
  {
    List<LexIdentifierToken> identifiers = new LinkedList<LexIdentifierToken>();
    for (LexNameToken name : pathList){
      if(name.explicit == true || !name.module.equals("Default"))
	throw new RuntimeException("A single expression declaration can only contain identifiers");
      identifiers.add(0,name.getIdentifier());
    }

    return identifiers;
  }

  private LexNameToken extractLexNameToken(CmlLexeme lexeme)
  {
    return new LexNameToken("Default",lexeme.getValue(), extractLexLocation(lexeme),false, true);
  }

  private LexNameToken extractLexNameToken(Object obj)
  {
    CmlLexeme lexeme = (CmlLexeme)obj;
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


  public static void main(String[] args) throws Exception
  {
    if (args.length == 0) {
      System.out.println("Usage : java CmlParser <inputfile>");
    } else {
      CmlLexer scanner = null;
      try {
	String filePath = args[0];
	ClonableFile file = new ClonableFile(filePath);
	AFileSource fileSource = new AFileSource();
	fileSource.setName(file.getName());
	scanner = new CmlLexer( new java.io.FileReader(file) );
	CmlParser cmlParser = new CmlParser(scanner);
	cmlParser.setDocument(fileSource);
	//cmlParser.setDebugLevel(1);

	//do {
	//System.out.println(scanner.yylex());
	boolean result = cmlParser.parse();
	if (result){
	  System.out.println("parsed!");

	  DotGraphVisitor dgv = new DotGraphVisitor();
	  INode node = cmlParser.getDocument();

	  node.apply(dgv,null);

	  File dotFile = new File("generatedAST.gv");
	  java.io.FileWriter fw = new java.io.FileWriter(dotFile);
	  fw.write(dgv.getResultString());
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

  // *************************
  // *** PUBLIC OPERATIONS ***
  // *************************

  public void setDocument(PSource doc)
  {
    this.currentSource = doc;
  }

  public PSource getDocument()
  {
    return currentSource;
  }
} /* End of code block */

/* ---------------------------------------------------------------- */
/* Token declarations                                               */
/* ---------------------------------------------------------------- */

%token CLASS END PROCESS INITIAL EQUALS AT BEGIN ACTIONS BARTILDEBAR LRSQUARE
%token TBAR LPAREN RPAREN LRPAREN LSQUARE RSQUARE CSPSKIP CSPSTOP CSPCHAOS
%token CSPDIV CSPWAIT RARROW LARROW LCURLY RCURLY BAR DBAR CHANNELS CHANSETS
%token TYPES SEMI DCOLON COMPOSE OF STAR TO INMAPOF MAPOF SEQOF SEQ1OF SETOF
%token PLUSGT COLONDASH DEQUALS INV VALUES FUNCTIONS PRE POST MEASURE IOTA
%token SUBCLASSRESP NOTYETSPEC OPERATIONS FRAME RD WR STATE LET IN IF THEN
%token ELSEIF ELSE CASES OTHERS PLUS MINUS ABS FLOOR NOT CARD POWER DUNION
%token DINTER HD TL LEN ELEMS INDS REVERSE CONC DOM RNG MERGE INVERSE
%token ELLIPSIS BARRARROW MKUNDER MKUNDERNAME DOT DOTHASH NUMERAL LAMBDA NEW
%token SELF ISUNDER PREUNDER ISOFCLASS TILDE DCL COLONEQUALS ATOMIC DEQRARROW
%token RETURN IDENTIFIER BACKTICK SLASH DIV REM MOD LT LTE GT GTE NEQ OR
%token AND EQRARROW LTEQUALSGT INSET NOTINSET SUBSET PSUBSET UNION BACKSLASH
%token INTER CARET DPLUS MUNION LTCOLON LTDASHCOLON COLONGT COLONDASHGT COMP
%token DSTAR FORALL EXISTS EXISTS1 STRING VRES RES VAL HEX_LITERAL
%token QUOTE_LITERAL AMP LSQUAREBAR DLSQUARE DRSQUARE BARRSQUARE COMMA
%token LSQUAREDBAR DBARRSQUARE COLON LCURLYBAR BARRCURLY QUESTION BANG
%token SLASHCOLON SLASHBACKSLASH COLONBACKSLASH LSQUAREGT BARGT ENDSBY DECIMAL
%token STARTBY MU PRIVATE PROTECTED PUBLIC LOGICAL DO FOR ALL BY WHILE
%token ISUNDERNAME EXTENDS EMPTYMAP DBACKSLASH
%token TBOOL TNAT TNAT1 TINT TRAT TREAL TCHAR TTOKEN TRUE FALSE TICK CHAR_LIT NIL

%token DUMMY
%token nameset

/* ---------------------------------------------------------------- */
/* Precidence declarations                                          */
/* ---------------------------------------------------------------- */

/* Note that U-PLUS, U-MINUS, U-SEMI, ... are tokens for
 * disambiguating precidence (shift/reduce conflicts, typically).
 */
/* Precidence from loosest to tightest; tokens on same line are equal precidence */
%left G-LOOSE
%right COMMA
%right MU LAMBDA
%right FORALL EXISTS EXISTS1 IOTA
%right LPAREN
%left OF COLONEQUALS
%nonassoc ELSE ELSEIF
%left DO
%right U-DO
%left IN COLON

// CSP prec START
%left TBAR
%left LSQUAREBAR BARRSQUARE LSQUARE DBAR RSQUARE
%left BARTILDEBAR 
%left LRSQUARE
%left SLASHCOLON SLASHBACKSLASH COLONBACKSLASH
%left LSQUAREGT BARGT
%left SEMI
%left AMP
%left DLSQUARE
%left ENDSBY STARTBY LSQUAREDBAR DBARRSQUARE DBACKSLASH
%right U-SEMI U-BARTILDEBAR U-DBAR U-TBAR U-LRSQUARE U-LSQUAREBAR U-LSQUAREDBAR
// CSP prec end

// VDM prec START
// VDM type operators
%right RARROW PLUSGT
%left BAR
%nonassoc T-STAR
%right INMAPOF MAPOF TO
%right SEQOF SEQ1OF SETOF
// VDM prec group connectives
%right LTEQUALSGT
%right EQRARROW
%right OR
%right AND
%right NOT
// VDM prec group relations
%left LT LTE GT GTE EQUALS NEQ INSET NOTINSET SUBSET PSUBSET
// VDM prec group evaluators
// VDM prec evaluators 1
%left PLUS MINUS
%left UNION BACKSLASH
%left MUNION DPLUS
%left CARET
// VDM prec evaluators 2
%left STAR SLASH DIV REM MOD
%left INTER
// VDM prec evaluators 3
%right INVERSE
// VDM prec evaluators 4
%right LTCOLON LTDASHCOLON
// VDM prec evaluators 5
%left COLONGT COLONDASHGT
// VDM prec evaluators 6
%right U-PLUS U-MINUS ABS FLOOR
%right CARD POWER DUNION DINTER
%right DOM RNG MERGE
%right LEN ELEMS HD TL INDS CONC REVERSE
// VDM prec group applicators
%left DOT
// VDM prec (highest) group combinators
%right COMP
%right DSTAR
// VDM prec DONE

%left DOTHASH
%left LRPAREN

%left G-HIGH

/* ---------------------------------------------------------------- */
/* Initial rule declaration                                         */
/* ---------------------------------------------------------------- */
%start source

%%

/* ---------------------------------------------------------------- */
/* The rules                                                        */
/* ---------------------------------------------------------------- */

source :
  programParagraphList
{
  List<SParagraphDefinition> paragraphs = (List<SParagraphDefinition>) $1;
  currentSource.setParagraphs(paragraphs);
}
;

programParagraphList :
  programParagraph
{
  List<PDefinition> programParagraphList = new LinkedList<PDefinition>();
  programParagraphList.add((PDefinition)$programParagraph);
  $$ = programParagraphList;
}
| programParagraphList programParagraph
{
  List<PDefinition> programParagraphList = (List<PDefinition>)$1;
  if (programParagraphList == null)
    programParagraphList = new Vector<PDefinition>();
  programParagraphList.add((PDefinition)$programParagraph);
  $$ = programParagraphList;
}
;

programParagraph :
  classDefinition                                       { $$ = $classDefinition; }
| processDefinition                                     { $$ = $processDefinition; }
| channelDefinition                                     { $$ = $channelDefinition; }
| chansetDefinitionParagraph                            { $$ = $chansetDefinitionParagraph; }
| globalDefinitionParagraph                             { $$ = $globalDefinitionParagraph; }
;

classDefinition :
  CLASS IDENTIFIER EQUALS BEGIN classDefinitionBlock END
{
  AClassParagraphDefinition clz = new AClassParagraphDefinition();
  LexNameToken lexName = extractLexNameToken((CmlLexeme)$IDENTIFIER);
  LexLocation loc = extractLexLocation((CmlLexeme)$CLASS,(CmlLexeme)$END);
  clz.setLocation(loc);
  clz.setName(lexName);
  clz.setDefinitions((List<PDefinition>)$classDefinitionBlock);
  clz.setNameScope(NameScope.CLASSNAME);
  $$ = clz;
}
| CLASS IDENTIFIER EQUALS EXTENDS IDENTIFIER BEGIN classDefinitionBlock END
{
    LexLocation location = extractLexLocation((CmlLexeme)$CLASS,(CmlLexeme)$END);
    List<LexNameToken> supernames = new LinkedList<LexNameToken>();
    supernames.add(extractLexNameToken($5));
    $$ = new AClassParagraphDefinition(location, 
				       extractLexNameToken($2), 
				       NameScope.CLASSNAME, 
				       false, 
				       getDefaultAccessSpecifier(false,false,null), 
				       null/*PType type_  should this be the namedInvariantType*/, 
				       (List<? extends PDefinition>)$classDefinitionBlock, 
				       new LinkedList<PType>() /* supertypes_*/, 
				       supernames, 
				       new LinkedList<PDefinition>()/* definitions_*/, 
				       new LinkedList<PDefinition>() /*allInheritedDefinitions_*/, 
				       new LinkedList<PDefinition>() /*localInheritedDefinitions_*/, 
				       null /*Boolean hasContructors_*/, 
				       null, 
				       new LinkedList<AClassParagraphDefinition>()/* superDefs_*/, 
				       true/*Boolean gettingInheritable_*/, 
				       new LinkedList<PDefinition>() /*superInheritedDefinitions_*/, 
				       null /*Boolean gettingInvDefs_*/, 
				       false, 
				       false /*Boolean isUndefined_*/, 
				       null/*PType classtype_*/, 
				       false /*Boolean isTypeChecked_*/, 
				       null/*AExplicitOperationDefinition invariant_*/);
}
;

processDefinition:
  PROCESS IDENTIFIER EQUALS processDef
{
  AProcessParagraphDefinition processDef = (AProcessParagraphDefinition)$processDef;
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$IDENTIFIER);
  LexLocation location = extractLexLocation((CmlLexeme)$PROCESS,
					    processDef.getLocation());
  processDef.setLocation(location);
  processDef.setName(id);
  $$ = processDef;
}
;

processDef :
  singleTypeDeclarationList AT process
{
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)$1;
  PProcess process = (PProcess)$3;
  List<PProcess> processes = new LinkedList<PProcess>();
  processes.add(process);
  LexLocation loc = combineLexLocation(extractFirstLexLocation(decls),
				       process.getLocation());
  // by default a process is public
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  $$ = new AProcessParagraphDefinition(loc,
				       NameScope.GLOBAL,
				       false,
				       access,
				       decls,
				       processes);
}
| process
{
  PProcess process = (PProcess)$1;
  List<PProcess> processes = new LinkedList<PProcess>();
  processes.add((PProcess)$1);
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, process.getLocation());
  $$ = new AProcessParagraphDefinition(process.getLocation(),
				       NameScope.GLOBAL,
				       false,
				       access,
				       null,
				       processes);
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
/* CHANSET
 * expression was chansetExpr here
 */
| process LSQUAREBAR expression BARRSQUARE process 
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$5;
  $$ = new AGeneralisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)$3, right);
}
/* CHANSET
 * expression was chansetExpr here
 */
| process LSQUARE expression DBAR expression RSQUARE process 
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$7;
  $$ = new AAlphabetisedParallelismProcess(combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)$3, (PExp)$5, right);
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
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
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
/* DEVIATION
 * grammar:
 *   process '[' expression '>' process
 * here:
 *   process '[' expression '|>' process
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
| process LSQUARE expression BARGT process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$5;
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ATimeoutProcess(location, left, (PExp)$3, right);
}
/* DEVIATION
 * grammar:
 *   process '\' expression
 * here:
 *   process '\\' expression
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
/* CHANSET
 * expression was chansetExpr here
 */
| process DBACKSLASH expression 
{
  PProcess left = (PProcess)$1;
  PExp cse = (PExp)$3;
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
| LPAREN singleTypeDeclarationList AT processDef RPAREN LPAREN expression RPAREN
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$8);
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)$singleTypeDeclarationList;
  //LexNameToken identifier = extractLexNameToken((CmlLexeme)$4);
  List<PExp> args = new LinkedList<PExp>();
  args.add((PExp)$expression);
  //TODO: The AST has to be changed to cope with the processDef
  $$ = new AInstantiationProcess(location, decls, null, args);
}
/* PATH
 * CML_0:
 *   IDENTIFIER
 *   IDENTIFIER LRPAREN
 *   IDENTIFIER LPAREN expressionList RPAREN
 * here:
 *   path
 */
| path
{
  try {
    Path path = (Path)$path;
    $$ = path.convertToProcess();
  } catch(Path.PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
}
| process renameExpression
{
  SRenameChannelExp renameExpression = (SRenameChannelExp)$2;
  PProcess process = (PProcess)$1;
  $$ = new AChannelRenamingProcess(combineLexLocation(process.getLocation(), renameExpression.getLocation()), process, renameExpression);
}
/* replicated processes */
| SEMI replicationDeclaration AT process %prec U-SEMI
{
  PProcess process = (PProcess)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new ASequentialCompositionReplicatedProcess(location,
						   (List<SSingleDeclaration>)$replicationDeclaration,
						   process);
}
| LRSQUARE replicationDeclaration AT process %prec U-LRSQUARE
{
  PProcess process = (PProcess)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new AExternalChoiceReplicatedProcess(location,
					    (List<SSingleDeclaration>)$replicationDeclaration,
					    process);
}
| BARTILDEBAR replicationDeclaration AT process %prec U-BARTILDEBAR
{
  PProcess process = (PProcess)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new AInternalChoiceReplicatedProcess(location,
					    (List<SSingleDeclaration>)$replicationDeclaration,
					    process);
}
/* CHANSET
 * expression was chansetExpr here
 */
| LSQUAREBAR expression BARRSQUARE replicationDeclaration AT process %prec U-LSQUAREBAR
{
  PProcess process = (PProcess)$6;
  PExp chansetExp = (PExp)$2;
  LexLocation location = extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new AGeneralisedParallelismReplicatedProcess(location,
						    (List<SSingleDeclaration>)$replicationDeclaration,
						    process,
						    chansetExp);
}
/* CHANSET
 * expression was chansetExpr here
 */
| DBAR replicationDeclaration AT LSQUAREBAR expression BARRSQUARE process %prec U-DBAR 
{
  PProcess process = (PProcess)$7;
  PExp chansetExp = (PExp)$expression;
  LexLocation location = extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new AAlphabetisedParallelismReplicatedProcess(location,
						     (List<SSingleDeclaration>)$replicationDeclaration,
						     process,
						     chansetExp);
}
| DBAR replicationDeclaration AT process %prec U-DBAR
{
  PProcess process = (PProcess)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new ASynchronousParallelismReplicatedProcess(location,
						    (List<SSingleDeclaration>)$replicationDeclaration,
						    process);
}
| TBAR replicationDeclaration AT process %prec U-TBAR
{
  PProcess process = (PProcess)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new AInterleavingReplicatedProcess(location,
					  (List<SSingleDeclaration>)$replicationDeclaration,
					  process);
}
;

/* DEVIATION
 * CML_0:
 *   ( singleTypeDeclaration | singleExpressionDeclaration ) ';' { ( singleTypeDeclaration | singleExpressionDeclaration ) }
 * here:
 *   ( singleTypeDeclaration | singleExpressionDeclaration )
 *   replicationDeclaration ',' ( singleTypeDeclaration | singleExpressionDeclaration )
 *
 * Two major points:
 * 1) we're using a COMMA as separator rather than a SEMI
 * 2) the SEMI in the rule as given should have been inside the {...}
 */
replicationDeclaration :
  singleTypeDeclaration
{
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)$singleTypeDeclaration);
  $$ = decls;
}
| singleExpressionDeclaration
{
  List<SSingleDeclaration> decls = new LinkedList<SSingleDeclaration>();
  decls.add((SSingleDeclaration)$singleExpressionDeclaration);
  $$ = decls;
}
| replicationDeclaration COMMA singleTypeDeclaration
{
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)$1;
  decls.add((SSingleDeclaration)$singleTypeDeclaration);
  $$ = decls;
}
| replicationDeclaration COMMA singleExpressionDeclaration
{
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)$1;
  decls.add((SSingleDeclaration)$singleExpressionDeclaration);
  $$ = decls;
}
;

/* DEVIATION
 * PATH
 * CML_0:
 *   IDENTIFIER { COMMA IDENTIFIER } ':' expression
 * here:
 *   pathList 'in set' expression
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
singleExpressionDeclaration :
  pathList INSET expression
{
  List<LexIdentifierToken> identifiers = convertPathListToIdentifiers((List<LexNameToken>)$pathList);
  PExp exp = (PExp)$expression;
  LexLocation location = combineLexLocation(extractFirstLexLocation(identifiers),
					    exp.getLocation());
  $$ = new AExpressionSingleDeclaration(location,
					NameScope.LOCAL,
					identifiers,
					exp);
}
;

processParagraphList :
  processParagraph
{
  List<PDefinition> processParagraphList = new Vector<PDefinition>();
  processParagraphList.add((PDefinition)$1);
  $$ = processParagraphList;
}
| processParagraphList processParagraph
{
  List<PDefinition> processParagraphList = (List<PDefinition>)$1;
  if (processParagraphList == null)
    processParagraphList = new Vector<PDefinition>();
  processParagraphList.add((PDefinition)$2);
  $$ = processParagraphList;
}
;

/* FIXME
 *
 * (JWC) I'm not convinced this matches the 'process paragraph' of the
 * grammar.
 */
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
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$1), extractLastLexLocation(actionDefinitions));
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  $$ = new AActionParagraphDefinition( loc, NameScope.LOCAL, false, access, actionDefinitions);
}
//| ACTIONS nameset IDENTIFIER EQUALS namesetExpression // TODO --- need feedback from Alvaro
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
  List<ATypeSingleDeclaration> declarations = (List<ATypeSingleDeclaration>)pa[0];
  PAction action = (PAction)pa[1];
  LexLocation defLocation = combineLexLocation(extractLexLocation((CmlLexeme)$1), action.getLocation());
  AActionDefinition actionDefinition = new AActionDefinition(defLocation, NameScope.LOCAL, false, null, declarations, action);
  $$ = actionDefinition;
}
;

paragraphAction :
  action
{
  $$ = new Object[]{new Vector<ATypeSingleDeclaration>(), $1};
}
| singleTypeDeclarationList AT action
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
| CSPWAIT expression
{
  PExp exp = (PExp)$expression;
  LexLocation location = extractLexLocation((CmlLexeme)$CSPWAIT, exp.getLocation());
  $$ = new AWaitAction(location, exp);
}
/* Communication rule start*/
/* DEVIATION
 * PATH
 * CML_0:
 *   action: ...| communication '->' action
 *   communication: IDENTIFIER { communicationParameter }
 *   communicationParameter: '?' parameter | '?' parameter ':' expression | '!' expression | '.' expression
 * here:
 *   path '->' action
 *
 * all of the communication machinery is in path, now
 * parameters are just patterns
 * parameter COLON expression is horribly broken
 * BANG/DOT expression both need params around general expressions, but literal values are ok
 */
| path RARROW action // TODO -- channel name expression
/* old rule for reference | IDENTIFIER communicationParameterList RARROW action */
/* { */
/*   LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1); */
/*   PAction action = (PAction)$4; */
/*   LexLocation location = combineLexLocation(id.getLocation(), action.getLocation()); */
/*   List<PCommunicationParameter> communicationParamters = (List<PCommunicationParameter>)$2; */
/*   $$ = new ACommunicationAction(location, id, communicationParamters, action); */
/* } */
/* Communication rule end*/
/* DEVIATON
 * grammar:
 *   expression '&' action
 * here:
 *   '[' expression ']' '&' action
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
| LSQUARE expression RSQUARE AMP action
{
  PExp exp = (PExp)$expression;
  PAction action = (PAction)$5;
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
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
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
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
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
 *   process '\' expression
 * here:
 *   process '\\' expression
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
/* CHANSET
 * expression was chansetExpr here
 */
| action DBACKSLASH expression 
{
  PAction left = (PAction)$1;
  PExp chansetExp = (PExp)$3;
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
  SRenameChannelExp renameExpression = (SRenameChannelExp)$renameExpression;
  PAction action = (PAction)$1;
  $$ = new AChannelRenamingAction(combineLexLocation(action.getLocation(), renameExpression.getLocation()), action, renameExpression);
}
/* DEVIATION
 * PATH
 * grammar:
 *   MU identifier {',' identifier} '@' action {',' action}
 * here:
 *   MU pathList '@' '(' actionList ')'
 */
| MU pathList AT LPAREN actionList RPAREN %prec MU // TODO JWC
/* parallel actions */
/* NAMESET
 * expression was namesetExpr here
 */
| action LSQUAREDBAR expression BAR expression DBARRSQUARE action 
{
    PAction leftAction = (PAction)$1;
    PAction rightAction = (PAction)$7;
    $$ = new AInterleavingParallelAction(extractLexLocation(leftAction.getLocation(),
							    rightAction.getLocation()), 
					 leftAction, 
					 (PExp)$3, 
					 (PExp)$5 , 
					 rightAction);
}
| action TBAR action 
{
    PAction leftAction = (PAction)$1;
    PAction rightAction = (PAction)$3;
    $$ = new AInterleavingParallelAction(extractLexLocation(leftAction.getLocation(),
							    rightAction.getLocation()), 
					 leftAction, 
					 null, 
					 null, 
					 rightAction);
}
/* NAMESET
 * expression was namesetExpr here
 */
| action LSQUAREBAR expression BAR expression BARRSQUARE action 
{
    PAction leftAction = (PAction)$1;
    PAction rightAction = (PAction)$7;
    $$ = new ASynchronousParallelismParallelAction(extractLexLocation(leftAction.getLocation(),
								      rightAction.getLocation()), 
						   leftAction, 
						   (PExp)$3, 
						   (PExp)$5, 
						   rightAction);
}
| action DBAR action 
{
    PAction leftAction = (PAction)$1;
    PAction rightAction = (PAction)$3;
    $$ = new ASynchronousParallelismParallelAction(extractLexLocation(leftAction.getLocation(),
								      rightAction.getLocation()), 
						   leftAction, 
						   null, 
						   null, 
						   rightAction);
}
/* CHANSET
 * NAMESET
 * expressions were namesetExpr|chansetExpr||chansetExpr|namesetExpr here
 */
| action LSQUARE expression BAR expression DBAR expression BAR expression RSQUARE action 
{
    PAction leftAction = (PAction)$1;
    PAction rightAction = (PAction)$11;
    LexLocation location = extractLexLocation(leftAction.getLocation(),
					      rightAction.getLocation());
    $$ = new AAlphabetisedParallelismParallelAction(location, 
						    leftAction, 
						    (PExp)$3, 
						    (PExp)$9,
						    rightAction, 
						    (PExp)$5, 
						    (PExp)$7);
}
/* CHANSET
 * expression was chansetExpr here
 */
| action LSQUARE expression DBAR expression RSQUARE action
{
    PAction leftAction = (PAction)$1;
    PAction rightAction = (PAction)$7;
    LexLocation location = extractLexLocation(leftAction.getLocation(),
					      rightAction.getLocation());
    $$ = new AAlphabetisedParallelismParallelAction(location, 
						    leftAction, 
						    null, 
						    null,
						    rightAction, 
						    (PExp)$3, 
						    (PExp)$5);
}
/* CHANSET
 * NAMESET
 * expressions were namesetExpr|chansetExpr|namesetExpr here
 */
| action LSQUAREBAR expression BAR expression BAR expression BARRSQUARE action 
{
    PAction leftAction = (PAction)$1;
    PAction rightAction = (PAction)$9;
    LexLocation location = extractLexLocation(leftAction.getLocation(),
					      rightAction.getLocation());
    $$ = new AGeneralisedParallelismParallelAction(location, 
						   leftAction, 
						   (PExp)$3, 
						   (PExp)$7, 
						   rightAction,
						   (PExp)$5);
}
/* CHANSET
 * expression was chansetExpr here
 */
| action LSQUAREBAR expression BARRSQUARE action 
{
    PAction leftAction = (PAction)$1;
    PAction rightAction = (PAction)$5;
    LexLocation location = extractLexLocation(leftAction.getLocation(),
					      rightAction.getLocation());
    $$ = new AGeneralisedParallelismParallelAction(location, 
						   leftAction, 
						   null, 
						   null, 
						   rightAction,
						   (PExp)$3);
}
/* parallel actions end */
/* DEVIATION
 * grammar: 
 *   parametrisation {';' parametrisation} '@' action
 * here:
 *   '(' parametrisation {';' parametrisation} '@' action ')'
 *
 * parametrised action 
 */
| LPAREN parametrisationList AT action RPAREN 
{
    $$ = new AParametrisedAction(extractLexLocation((CmlLexeme)$LPAREN,
						    (CmlLexeme)$RPAREN), 
						    (List<PParametrisation>)$parametrisationList, 
						    (PAction)$4);
}
/* instantiated actions */
| LPAREN singleTypeDeclarationList AT action RPAREN LPAREN expressionList RPAREN
{
  $$ = new ADeclarationInstantiatedAction(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$8),
					  (List<? extends ATypeSingleDeclaration>)$singleTypeDeclarationList,
					  (PAction)$4,
					  (List<PExp>)$expressionList);
}
| LPAREN parametrisationList AT action RPAREN LPAREN expressionList RPAREN 
{
    AParametrisedAction action = new AParametrisedAction(extractLexLocation((CmlLexeme)$1,
									    (CmlLexeme)$5), 
							 (List<PParametrisation>)$parametrisationList, 
							 (PAction)$4);
    $$ = new AParametrisedInstantiatedAction(extractLexLocation((CmlLexeme)$1,
								(CmlLexeme)$8),
					     action,
					     (List<PExp>)$7);
}
/* instantiated actions */
/* replicated actions */
| SEMI replicationDeclaration AT action %prec U-SEMI
{
  PAction action = (PAction)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,action.getLocation());
  $$ = new ASequentialCompositionReplicatedAction(location,
						  (List<SSingleDeclaration>)$replicationDeclaration,
						  action);
}
| LRSQUARE replicationDeclaration AT action %prec U-LRSQUARE
{
  PAction action = (PAction)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,action.getLocation());
  $$ = new AExternalChoiceReplicatedAction(location,
					   (List<SSingleDeclaration>)$replicationDeclaration,
					   action);
}
| BARTILDEBAR replicationDeclaration AT action %prec U-BARTILDEBAR
{
  PAction action = (PAction)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,action.getLocation());
  $$ = new AInternalChoiceReplicatedAction(location,
					   (List<SSingleDeclaration>)$replicationDeclaration,
					   action);
}
/* NAMESET
 * expression was namesetExpr here
 */
| LSQUAREDBAR expression DBARRSQUARE replicationDeclaration AT action %prec U-LSQUAREDBAR 
{
    PAction replicatedAction = (PAction)$6;
    LexLocation location = extractLexLocation((CmlLexeme)$1,
					      replicatedAction.getLocation());
    $$ = new AInterleavingReplicatedAction(location, 
					   (List<SSingleDeclaration>)$replicationDeclaration, 
					   replicatedAction,
					   (PExp)$expression);
}
/* NAMESET
 * expression was namesetExpr here
 */
| TBAR replicationDeclaration AT LSQUARE expression RSQUARE action %prec U-TBAR 
{
    PAction replicatedAction = (PAction)$7;
    LexLocation location = extractLexLocation((CmlLexeme)$1,
					      replicatedAction.getLocation());
    $$ = new AInterleavingReplicatedAction(location, 
					   (List<SSingleDeclaration>)$replicationDeclaration, 
					   replicatedAction,
					   (PExp)$expression);
}
/* CHANSET
 * NAMESET
 * expressions were namesetExpr, chansetExpr here
 */
| LSQUAREBAR expression BARRSQUARE replicationDeclaration AT LSQUARE expression RSQUARE action %prec U-LSQUAREBAR 
{
    PAction replicatedAction = (PAction)$9;
    LexLocation location = extractLexLocation((CmlLexeme)$1,
					      replicatedAction.getLocation());
    $$ = new AGeneralisedParallelismReplicatedAction(location, 
						     (List<SSingleDeclaration>)$replicationDeclaration, 
						     replicatedAction, 
						     (PExp)$2, 
						     (PExp)$7);
}
/* CHANSET
 * NAMESET
 * expressions were namesetExpr, chansetExpr here
 */
| DBAR replicationDeclaration AT LSQUARE expression BAR expression RSQUARE action %prec U-DBAR
{
    PAction replicatedAction = (PAction)$9;
    LexLocation location = extractLexLocation((CmlLexeme)$1,replicatedAction.getLocation());
    $$ = new AAlphabetisedParallelismReplicatedAction(location, 
						      (List<? extends SSingleDeclaration>)$replicationDeclaration, 
						      replicatedAction, 
						      (PExp)$5, 
						      (PExp)$7);
}
/* CHANSET
 * expression was namesetExpr here
 */
| DBAR replicationDeclaration AT LSQUARE expression RSQUARE action %prec U-DBAR 
{
    PAction replicatedAction = (PAction)$7;
    LexLocation location = extractLexLocation((CmlLexeme)$1,replicatedAction.getLocation());
    $$ = new ASynchronousParallelismReplicatedAction(location, 
						     (List<? extends SSingleDeclaration>)$replicationDeclaration, 
						     replicatedAction, 
						     (PExp)$5);
}
/* replicated actions end */
| letStatement 
{
    $$ = $1;
}
| blockStatement 
{
    $$ = $1;
}
| controlStatement
{
  $$ = $1;
}
;

actionList :
  action 
{
    List<PAction> actionList = new LinkedList<PAction>();
    actionList.add((PAction)$1);
    $$ = actionList;
}
| actionList COMMA action 
{
    List<PAction> actionList = (List<PAction>)$1;
    actionList.add(0,(PAction)$3);
    $$ = actionList;
}
;

parametrisationList :
  parametrisation 
{
    List<PParametrisation> plist = new LinkedList<PParametrisation>();
    plist.add((PParametrisation)$parametrisation);
    $$ = plist;
}
| parametrisationList SEMI parametrisation 
{
    List<PParametrisation> plist = new LinkedList<PParametrisation>();
    plist.add(0,(PParametrisation)$parametrisation);
    $$ = plist;
}
;

parametrisation :
  VAL singleTypeDeclaration
  {
      ATypeSingleDeclaration declaration = (ATypeSingleDeclaration)$singleTypeDeclaration;
      LexLocation location = extractLexLocation((CmlLexeme)$1, declaration.getLocation());
      $$ = new AValParametrisation(location, declaration);
  }
| RES singleTypeDeclaration 
{
    ATypeSingleDeclaration declaration = (ATypeSingleDeclaration)$singleTypeDeclaration;
    LexLocation location = extractLexLocation((CmlLexeme)$1, declaration.getLocation());
    $$ = new AResParametrisation(location, declaration);
}
| VRES singleTypeDeclaration 
{
    ATypeSingleDeclaration declaration = (ATypeSingleDeclaration)$singleTypeDeclaration;
    LexLocation location = extractLexLocation((CmlLexeme)$1, declaration.getLocation());
    $$ = new AVresParametrisation(location, declaration);
}
;

renameExpression :
/* rename enumeration */
  DLSQUARE renameList DRSQUARE 
{
    $$ = new AEnumerationRenameChannelExp(extractLexLocation((CmlLexeme)$DLSQUARE, 
							     (CmlLexeme)$DRSQUARE), 
					  (List<? extends ARenamePair>)$renameList);
}
/* rename comprehensions */
| DLSQUARE path[from] LARROW path[to] BAR bindList DRSQUARE // TODO
{
  ARenamePair pair = new ARenamePair(false,
				     (ANameChannelExp)$from,
				     (ANameChannelExp)$to);
  $$ = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)$DLSQUARE, (CmlLexeme)$DRSQUARE),
					  pair,
					  (List<? extends PMultipleBind>)$bindList,
					  null);
}
| DLSQUARE path[from] LARROW path[to] BAR bindList AT expression DRSQUARE // TODO
{
  ARenamePair pair = new ARenamePair(false,
				     (ANameChannelExp)$from,
				     (ANameChannelExp)$to);
  $$ = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)$DLSQUARE, (CmlLexeme)$DRSQUARE),
					  pair,
					  (List<? extends PMultipleBind>)$bindList,
					  (PExp)$expression);
}
;

/* DEVIATION
 * PATH
 * CML_0:
 *   renamingEnumeration: '[[' renamingPair { ',' renamingPair } ']]'
 *   renamingPair: IDENTIFIER { ',' expression } '<-' IDENTIFIER { ',' expression }
 * here:
 *   renamingPair: path '<-' path
 * 
 * Note that path requires expressions in (...) but allows literals without.
 */
renameList :
  path[from] LARROW path[to] // TODO -- channel name expression
{
  List<ARenamePair> renamePairs = new Vector<ARenamePair>();
  ARenamePair pair = new ARenamePair(false, (ANameChannelExp)$from, (ANameChannelExp)$to);
  renamePairs.add(pair);
  $$ = renamePairs;
}
| renameList COMMA path[from] LARROW path[to] // TODO -- channel name expression
{
  List<ARenamePair> renamePairs = (List<ARenamePair>)$1;
  ARenamePair pair = new ARenamePair(false, (ANameChannelExp)$from, (ANameChannelExp)$to);
  renamePairs.add(pair);
  $$ = renamePairs;
}
;

channelDefinition :
  CHANNELS channelDef
{
  List<AChannelNameDeclaration> chanNameDecls = (List<AChannelNameDeclaration>)$channelDef;
  LexLocation start = extractLexLocation((CmlLexeme)$1);
  LexLocation end = (chanNameDecls != null && chanNameDecls.size() > 0) ?
    chanNameDecls.get(chanNameDecls.size()-1).getLocation() : start;
  LexLocation location = combineLexLocation(start, end);
  AAccessSpecifier access = getDefaultAccessSpecifier( true,false,start);
  AChannelParagraphDefinition channelDefinition = new AChannelParagraphDefinition(location,
										  NameScope.GLOBAL,
										  false,
										  access,
										  chanNameDecls);
  $$ = channelDefinition;
}
;

channelDef :
  channelNameDecl
{
  List<AChannelNameDeclaration> decls = new Vector<AChannelNameDeclaration>();
  decls.add((AChannelNameDeclaration)$1);
  $$ = decls;
}
| channelDef SEMI channelNameDecl
{
  List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)$1;
  decls.add((AChannelNameDeclaration)$channelNameDecl);
  $$ = decls;
}
;

channelNameDecl :
/* DEVIATION
 * PATH
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
  ATypeSingleDeclaration singleTypeDeclaration = new ATypeSingleDeclaration(location, NameScope.GLOBAL, ids, null);
  AChannelNameDeclaration channelNameDecl = new AChannelNameDeclaration(location, NameScope.GLOBAL, null,  singleTypeDeclaration);
  $$ = channelNameDecl;
}
| singleTypeDeclaration
{
  ATypeSingleDeclaration singleTypeDeclaration = (ATypeSingleDeclaration)$1;
  AChannelNameDeclaration channelNameDecl = new AChannelNameDeclaration(singleTypeDeclaration.getLocation(), NameScope.GLOBAL, null, singleTypeDeclaration);
  $$ = channelNameDecl;
}
;

/* RENAME
 * declaration in CML_0 grammar corresponds to singleTypeDeclarationList
 */
singleTypeDeclarationList[result] :
  singleTypeDeclaration
{
  List<ATypeSingleDeclaration> decls = new Vector<ATypeSingleDeclaration>();
  decls.add((ATypeSingleDeclaration)$singleTypeDeclaration);
  $$ = decls;
}
| singleTypeDeclarationList[list] SEMI singleTypeDeclaration
{
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)$list;
  decls.add((ATypeSingleDeclaration)$singleTypeDeclaration);
  $$ = decls;
}
;

singleTypeDeclaration :
/* DEVIATION
 * PATH
 * grammar:
 *   identifierList
 * here:
 *   pathList
 */
  pathList COLON type
{
  List<LexNameToken> nameList = (List<LexNameToken>)$1;
  List<LexIdentifierToken> ids = convertNameListToIdentifierList(nameList);
  ATypeSingleDeclaration singleTypeDeclaration =
    new ATypeSingleDeclaration(nameList.get(0).getLocation(), NameScope.LOCAL, ids, (PType)$3);
  $$ = singleTypeDeclaration;
}
;

chansetDefinitionParagraph :
  CHANSETS
{
  CmlLexeme tok = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation(tok);
  AAccessSpecifier access = new AAccessSpecifier(new APublicAccess(), new TStatic(), new TAsync(),loc);
  AChansetParagraphDefinition chansetParagraph = new AChansetParagraphDefinition( loc, NameScope.GLOBAL, false, access, null  );
  $$ = chansetParagraph;
}
| CHANSETS chansetDefinitionList
{
  CmlLexeme tok = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation(tok);
  List<AChansetDefinition> chansetDefinitions = (List<AChansetDefinition>)$2;
  AAccessSpecifier access = new AAccessSpecifier(new APublicAccess(), new TStatic(), new TAsync(),loc);
  AChansetParagraphDefinition chansetParagraph = new AChansetParagraphDefinition( loc, NameScope.GLOBAL, false, access, chansetDefinitions );
  $$ = chansetParagraph;
 }
;

/* DEVIATION
 * chanset declarations are now separated by SEMIs, just like everything else.
 */
chansetDefinitionList :
  chansetDefinition
{
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)$1);
  $$ = defs;
}
| chansetDefinitionList SEMI chansetDefinition
{
  List<AChansetDefinition> defs = (List<AChansetDefinition>)$1;
  defs.add((AChansetDefinition)$2);
  $$ = defs;
}
;

chansetDefinition :
/* CHANSET
 * expression was chansetExpr here
 */
  IDENTIFIER EQUALS expression  
{
  LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)$1);
  PExp chansetExp = (PExp)$3;
  LexLocation location = combineLexLocation(idToken.getLocation(), chansetExp.getLocation());
  $$ = new AChansetDefinition(location, NameScope.GLOBAL, false/*used_*/, null, /*AAccessSpecifierAccessSpecifier access_*/ idToken, chansetExp);
}
;

chansetExpr :
  LCURLYBAR pathList BARRCURLY
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3);
  List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)$2;
  $$ = new AEnumChansetSetExp(location, identifiers);
}
| LCURLYBAR path BAR bindList BARRCURLY // TODO --- channelNameExpr
{
  LexLocation location = extractLexLocation((CmlLexeme)$LCURLYBAR, (CmlLexeme)$BARRCURLY);
  ANameChannelExp chanNameExp = (ANameChannelExp)$path;
  List<PMultipleBind> bindings = (List<PMultipleBind>)$bindList;
  $$ = new ACompChansetSetExp(location,chanNameExp , bindings, null);
}
| LCURLYBAR path BAR bindList AT expression BARRCURLY // TODO --- channelNameExpr
{
  LexLocation location = extractLexLocation((CmlLexeme)$LCURLYBAR,
					    (CmlLexeme)$BARRCURLY);
  ANameChannelExp chanNameExp = (ANameChannelExp)$path;
  List<PMultipleBind> bindings = (List<PMultipleBind>)$bindList;
  PExp pred = (PExp)$expression;
  $$ = new ACompChansetSetExp(location, chanNameExp, bindings, pred);
}
//| IDENTIFIER // now path
//| LCURLYCOLON pathList COLONRCURLY // now set enumeration
//| chansetExpr COLONUNION chansetExpr // now regular union
//| chansetExpr COLONINTER chansetExpr // now regular intersection
//| chansetExpr DBACKSLASH chansetExpr // now regular set subtraction
;

globalDefinitionParagraph :
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
  $$ = $1;
}
;

classDefinitionBlock :
  classDefinitionBlockAlternative
{
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)$1);
  //List<PDefinition> defs = (List<PDefinition>)$1;
  $$ = defs;
}
| classDefinitionBlockAlternative classDefinitionBlock
{
  List<PDefinition> defs = (List<PDefinition>) $2;
  PDefinition newDefs = (PDefinition)$1;
  defs.add(newDefs);
  $$ = defs;
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
  $$ = $1;
}
| operationDefs
{
  $$ = $1;
}
| stateDefs
{
  $$ = $1;
}
/* UPCOMING --- CML_1
 * absent in CML_0
 *
 * This will be in the CML_1 grammar, and is defines the constructor for the class.
 * Confirmed between Joey, Alavro; Skype 30 July 2012
 */
| INITIAL operationDef 
{
    PDefinition def = (PDefinition)$operationDef;
    LexLocation location = extractLexLocation((CmlLexeme)$INITIAL,def.getLocation());
    $$ = new AInitialParagraphDefinition(location, 
					 NameScope.GLOBAL, 
					 true, 
					 getDefaultAccessSpecifier(false,false,null), 
					 def);
}
;

typeDefs :
  TYPES
{
  LexLocation loc = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  $$ = new ATypesParagraphDefinition( loc, NameScope.LOCAL, false, access, null);
}
| TYPES typeDefList SEMI
{
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)$2;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$1),
				       extractLastLexLocation(typeDefinitions));
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  $$ = new ATypesParagraphDefinition( loc, NameScope.LOCAL, false, access, typeDefinitions);
}
| TYPES typeDefList
{
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)$2;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$1),
				       extractLastLexLocation(typeDefinitions));
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  $$ = new ATypesParagraphDefinition( loc, NameScope.LOCAL, false, access, typeDefinitions);
}
;

typeDefList :
  typeDef
{
  List<ATypeDefinition> list = new Vector<ATypeDefinition>();
  list.add((ATypeDefinition)$typeDef);
  $$ = list;
}
| typeDefList[list] SEMI typeDef
{
  List<ATypeDefinition> list = (List<ATypeDefinition>)$list;
  list.add((ATypeDefinition)$typeDef);
  $$ = list;
}
;

typeDef :
  qualifier IDENTIFIER EQUALS type invariant
{
  AAccessSpecifier access = (AAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  AInvariantDefinition inv = (AInvariantDefinition)$5;
  //SInvariantType inv = (SInvariantType)$5;
  LexLocation location = null;
  if (access.getLocation() != null) {
    location = combineLexLocation(access.getLocation(), inv.getLocation());
  } else {
    location = combineLexLocation(name.getLocation(), inv.getLocation());
  }
  ATypeDefinition typeDef = new ATypeDefinition(location,
						name,
						NameScope.TYPENAME,
						false/*Boolean used_*/,
						access,
						(PType)$type,
						null/*SInvariantType invType_*/,
						inv.getPattern()/*PPattern invPattern_*/,
						inv.getExpression()/*PExp invExpression_*/,
						null /*AExplicitFunctionDefinition invdef_*/,
						false/*Boolean infinite_*/);
  $$ = typeDef;
}
| qualifier IDENTIFIER EQUALS type
{
  AAccessSpecifier access = (AAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  LexLocation location = null;
  if (access.getLocation() != null) {
    location = combineLexLocation(access.getLocation(), ((PType)$type).getLocation());
  } else {
      location = combineLexLocation(name.getLocation(), ((PType)$type).getLocation());
  }
  $$ = new ATypeDefinition(location,
			   name,
			   NameScope.TYPENAME,
			   false/*Boolean used_*/,
			   access,
			   (PType)$type,
			   null/*SInvariantType invType_*/,
			   null/*PPattern invPattern_*/,
			   null/*PExp invExpression_*/,
			   null /*AExplicitFunctionDefinition invdef_*/,
			   false/*Boolean infinite_*/);
}
| qualifier IDENTIFIER DCOLON fieldList
{
  AAccessSpecifier access = (AAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  CmlLexeme vdmrec = (CmlLexeme)$3;
  List<AFieldField> fields = (List<AFieldField>)$4;
  LexLocation loc = null;
  if(access.getLocation() != null)
    loc = combineLexLocation(access.getLocation(), extractLexLocation(vdmrec));
  else
    loc = combineLexLocation(name.getLocation(), extractLexLocation(vdmrec));
  ARecordInvariantType recType = new ARecordInvariantType(loc, false, null, false, null, name, fields, true);
  ATypeDefinition res = new ATypeDefinition(loc, 
					    name, 
					    NameScope.GLOBAL, 
					    false, 
					    access, 
					    recType, 
					    null, 
					    null, 
					    null, 
					    null, 
					    true);
  $$ = res;
}
| qualifier IDENTIFIER DCOLON fieldList invariant
{
  AAccessSpecifier access = (AAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  CmlLexeme vdmrec = (CmlLexeme)$3;
  List<AFieldField> fields = (List<AFieldField>)$4;
  // TODO: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.
  LexLocation loc = null;
  if(access.getLocation() != null)
    loc = combineLexLocation(access.getLocation(), extractLexLocation(vdmrec));
  else
    loc = combineLexLocation(name.getLocation(), extractLexLocation(vdmrec));
  ARecordInvariantType recType = new ARecordInvariantType(loc, false, null, false, null, name, fields, true);
  ATypeDefinition res = new ATypeDefinition(loc, 
					    name, 
					    NameScope.TYPENAME, 
					    false, 
					    access, 
					    recType, 
					    null, 
					    null, 
					    null, 
					    null, 
					    true);
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
  AAccessSpecifier res = new AAccessSpecifier();
  res.setAccess(new APrivateAccess());
  res.setLocation(location);
  $$ = res;
}
| PROTECTED
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifier res = new AAccessSpecifier();
  res.setLocation(location);
  res.setAccess(new AProtectedAccess());
  $$ = res;
}
| PUBLIC
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifier res = new AAccessSpecifier();
  res.setLocation(location);
  res.setAccess(new APublicAccess());
  $$ = res;
}
/* (RWL) It is not in overture why are we having it?
 *
 * (JWC) It is in CML, however.  Jim wants it in (for perfectly
 * cromulent reasons), and it's mostly harmless.  We just need to
 * filter it (and all places where it's used) out, or flip it to
 * public/global.  See me for an explanation.
 */
| LOGICAL
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  $$ = new AAccessSpecifier(new ALogicalAccess(), null, null, location);
}
| /* empty */
{
  /*Default private*/
  AAccessSpecifier a = new AAccessSpecifier();
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
| COMPOSE IDENTIFIER OF fieldList END
{
    List<AFieldField> fields = (List<AFieldField>)$fieldList;
    $$ = new ARecordInvariantType(extractLexLocation((CmlLexeme)$1,(CmlLexeme)$END), 
				  false, 
				  extractLexNameToken($IDENTIFIER), 
				  (List<? extends AFieldField>)$fieldList);
}
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
| type STAR type %prec T-STAR //productType
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
 */
| IDENTIFIER
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$1);
  ANamedInvariantType type = new ANamedInvariantType();
  type.setLocation(name.getLocation());
  type.setName(name);
  $$ = type;
}
| IDENTIFIER DOT IDENTIFIER // name is defined in CML_0 as using DOT
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$3);
  name = new LexNameToken(((CmlLexeme)$1).getValue(),name.getIdentifier());
  ANamedInvariantType type = new ANamedInvariantType();
  type.setLocation(name.getLocation());
  type.setName(name);
  $$ = type;
}
| IDENTIFIER BACKTICK IDENTIFIER
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$3);
  name = new LexNameToken(((CmlLexeme)$1).getValue(),name.getIdentifier());
  ANamedInvariantType type = new ANamedInvariantType();
  type.setLocation(name.getLocation());
  type.setName(name);
  $$ = type;
}
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
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, true, params, rngType);
}
| LRPAREN PLUSGT type // discretionary type
{
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)$1), true);
  PType rngType = (PType)$3;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
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
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, false, params, rngType);
}
| LRPAREN RARROW type // discretionary type
{
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)$1), true);
  PType rngType = (PType)$3;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, false, params, rngType);
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
    $$ = new AFieldField(getDefaultAccessSpecifier(false,false,null), 
			 null, null, (PType)$1, false);
}
| IDENTIFIER COLON type
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$1);
  PType type = (PType) $3;
  $$ = new AFieldField(getDefaultAccessSpecifier(false,false,null), name, null, type, false);
}
| IDENTIFIER COLONDASH type
{
    LexNameToken name = extractLexNameToken((CmlLexeme)$1);
    PType type = (PType) $3;
    $$ = new AFieldField(getDefaultAccessSpecifier(false,false,null), 
			 name, null, type, true);

}
;

invariant :
  INV pattern DEQUALS expression
{
  LexIdentifierToken name = null; // cannot be desided here
  CmlLexeme vdmInvLexeme = (CmlLexeme)$1;
  PExp exp = (PExp)$4;
  LexLocation loc = extractLexLocation(vdmInvLexeme, exp.getLocation());
  AAccessSpecifier access = getDefaultAccessSpecifier( true, true, loc );
  PType type = null; // will be decided later
  $$ = new AInvariantDefinition(loc,
				name,
				NameScope.LOCAL,
				false,
				access,
				type,
				(PPattern)$2,
				exp);
}
;


/* DEVIATION
 * CML_0:
 *   'values', qualifiedValueDef, { ‘;’, qualifiedValueDef }
 * here:
 *   'values', { qualifiedValueDef, { ‘;’, qualifiedValueDef } }
 */
valueDefs :
  VALUES
{
  List<PDefinition> defs = new List<PDefinition>();
  LexLocation location = extractLexLocation((CmlLexeme)$VALUES);
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  $$ = new AValueParagraphDefinition(location,
				     NameScope.NAMES,
				     false,
				     access,
				     defs);
}
| VALUES valueDefList
{
  List<PDefinition> defs = (List<PDefinition>)$valueDefList;
  LexLocation location = extractLexLocation((CmlLexeme)$VALUES,
					    extractLastLexLocation(defs));
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  $$ = new AValueParagraphDefinition(location,
				     NameScope.NAMES,
				     false,
				     access,
				     defs);
}
;

/* DEVIATION
 * Trailing SEMI not optional
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
  defs.add(0,def);
  $$ = defs;
}
;

qualifiedValueDef :
  qualifier valueDef
{
  AAccessSpecifier access = (AAccessSpecifier)$1;
  PDefinition def = (PDefinition)$2;
  def.setAccess(access);
  $$ = def;
}
;

valueDef :
  IDENTIFIER COLON type EQUALS expression
{
  LexNameToken lnt = extractLexNameToken((CmlLexeme)$IDENTIFIER);
  PType type = (PType)$type;
  PExp expression = (PExp)$expression;
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
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  List<SFunctionDefinition> functionDefs = new LinkedList<SFunctionDefinition>();
  $$ = new AFunctionParagraphDefinition(location,
					NameScope.GLOBAL,
					false,
					access,
					functionDefs);
}
| FUNCTIONS functionDefList
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  List<SFunctionDefinition> functionDefs = (List<SFunctionDefinition>)$functionDefList;
  $$ = new AFunctionParagraphDefinition(location,
					NameScope.GLOBAL,
					false,
					access,
					functionDefs);
}
;

functionDefList :
  functionDef
{
  List<SFunctionDefinition> functionList = new Vector<SFunctionDefinition>();
  functionList.add((SFunctionDefinition)$functionDef);
  $$ = functionList;
}
| functionDefList[list] SEMI functionDef
{
  List<SFunctionDefinition> functionList = (List<SFunctionDefinition>)$list;
  functionList.add((SFunctionDefinition)$functionDef);
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
  AAccessSpecifier access = (AAccessSpecifier)$1;
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
  AImplicitFunctionDefinition impFunc =
    new AImplicitFunctionDefinition(location, null, false, access, null, paramPatterns, result, preExp, postExp);
  impFunc.setName(name);
  $$ = impFunc;
}
;

qualifiedExplicitFunctionDef :
  qualifier explicitFunctionDef
{
  AAccessSpecifier access = (AAccessSpecifier)$qualifier;
  AExplicitFunctionDefinition f = (AExplicitFunctionDefinition)$2;
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
  PExp functionBody = (PExp)$functionBody;
  List<List<PPattern>> args = (List<List<PPattern>>)$parameterList;
  AExplicitFunctionDefinition res = new AExplicitFunctionDefinition();
  res.setName(name);
  res.setLocation(loc);
  res.setType(ftype);
  res.setBody(functionBody);
  res.setParamPatternList(args);
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
/* DEVIATION
 * PATH
 * CML_0:
 *   MEASURE name
 * TODO: convert to a name
 */
  MEASURE path
{
  $$ = $2;
}
| /* empty */ // TODO
;

operationDefs :
  OPERATIONS operationDefList
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  List<? extends SOperationDefinition> opDefinitions = (List<? extends SOperationDefinition>)$2;
  $$ = new AOperationParagraphDefinition(location,
					 NameScope.LOCAL,
					 false,
					 access,
					 opDefinitions);
}
| OPERATIONS
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  $$ = new AOperationParagraphDefinition(location,
					 NameScope.LOCAL,
					 false,
					 access,
					 null);
}
;

/* DEVIATION !!
 * operationDefs no longer have semicolons as separators
 */
operationDefList :
  operationDef
{
  List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>();
  opDefinitions.add((SOperationDefinition)$operationDef);
  $$ = opDefinitions;
}
| operationDefList[list] SEMI operationDef 
{
  List<SOperationDefinition> opDefinitions = (List<SOperationDefinition>)$list;
  opDefinitions.add((SOperationDefinition)$operationDef);
  $$ = opDefinitions;
}
;

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
  AExplicitOperationDefinition res = new AExplicitOperationDefinition();
  res.setLocation(loc);
  res.setBody((SStatementAction)$operationBody);
  res.setType((PType)$operationType);
  $$ = res;
}
;

implicitOperationDef :
  qualifier IDENTIFIER parameterTypes identifierTypePairList_opt externals_opt preExpr_opt postExpr
{
  AAccessSpecifier access = (AAccessSpecifier)$1;
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
  AImplicitOperationDefinition ifunc =
    new AImplicitOperationDefinition(location,
				     NameScope.GLOBAL,
				     null,
				     access,
				     parameterPatterns,
				     result,
				     externals,
				     precondition,
				     postcondition,
				     null, null);
  ifunc.setName(name);
  $$ = ifunc;
}
;

operationType :
  type DEQRARROW type 
  {
      List<PType> types = new LinkedList<PType>(); 
      PType left = (PType)$1;
      PType right = (PType)$3;
      types.add(left);
      $$ = new AOperationType(extractLexLocation(left.getLocation(),right.getLocation()), 
			      false, 
			      new LinkedList<PDefinition>(), 
			      types, 
			      right);
  }
| LRPAREN DEQRARROW type 
  {
      List<PType> types = new LinkedList<PType>(); 
      PType right = (PType)$3;
      types.add(new AVoidType(extractLexLocation((CmlLexeme)$1), 
			      true));
      $$ = new AOperationType(extractLexLocation((CmlLexeme)$1,right.getLocation()), 
			      false, 
			      new LinkedList<PDefinition>(), 
			      types, 
			      right);
  }
| type DEQRARROW LRPAREN 
  {
      List<PType> types = new LinkedList<PType>(); 
      PType left = (PType)$1;
      types.add(left);
      $$ = new AOperationType(extractLexLocation(left.getLocation(),(CmlLexeme)$3), 
			      false, 
			      new LinkedList<PDefinition>(), 
			      types, 
			      new AVoidType(extractLexLocation((CmlLexeme)$3), 
					    true));
  }
| LRPAREN DEQRARROW LRPAREN 
  {
      List<PType> types = new LinkedList<PType>(); 
      types.add(new AVoidType(extractLexLocation((CmlLexeme)$1), 
			      true));
      
      $$ = new AOperationType(extractLexLocation((CmlLexeme)$1,(CmlLexeme)$3), 
			      true, 
			      new LinkedList<PDefinition>(), 
			      types, 
			      new AVoidType(extractLexLocation((CmlLexeme)$3), 
					    true));
  }
;

operationBody :
/* DEVIATION
 * CML_0:
 *   action
 * here:
 *   blockStatement
 *
 * Use of a SEMI to separate operation definitions conflicts with the
 * use of a SEMI for the CSP sequential combinator.
 */
  blockStatement
{
  $$ = $1;
}
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
  List<? extends LexNameToken> names = (List<? extends LexNameToken>)$pathList;
  PMode mode = (PMode)$mode;
  LexLocation location = combineLexLocation(mode.getLocation(),
					    extractLastLexLocation(names));
  $$ = new AExternalClause(location,mode,names, null);
}
| mode pathList COLON type
{
  List<? extends LexNameToken> names = (List<? extends LexNameToken>)$pathList;
  PMode mode = (PMode)$mode;
  LexLocation location = combineLexLocation(mode.getLocation(),
					    extractLastLexLocation(names));
  $$ = new AExternalClause(location,
			   mode,
			   names,
			   (PType)$type);
}
;

mode :
  RD
{
  $$ = new AReadMode(extractLexLocation((CmlLexeme)$RD));
}
| WR
{
  $$ = new AWriteMode(extractLexLocation((CmlLexeme)$WR));
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
  $$ = $2;
}
| STATE
{
  $$  = new AStateDefinition();
}
;

stateDefList :
  stateDef
{
  AStateDefinition stateDef = new AStateDefinition();
  List<PDefinition> defs = new Vector<PDefinition>();
  defs.add((PDefinition)$stateDef);
  stateDef.setStateDefs(defs);
  $$ = stateDef;
}
| stateDefList[list] SEMI stateDef
{
  AStateDefinition stateDef = (AStateDefinition)$list;
  stateDef.getStateDefs().add((PDefinition)$stateDef);
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
 LPAREN expression RPAREN
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
| FORALL bindList AT expression %prec FORALL
{
  CmlLexeme forall = (CmlLexeme)$1;
  List<PMultipleBind> binds = (List<PMultipleBind>)$2;
  CmlLexeme amp = (CmlLexeme)$3;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(forall), exp.getLocation());
  AForAllExp forallexp = new AForAllExp(loc, binds, exp);
  $$ = forallexp;
}
| EXISTS bindList AT expression %prec EXISTS
{
  CmlLexeme exists = (CmlLexeme)$1;
  List<PMultipleBind> binds = (List<PMultipleBind>)$2;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  AExistsExp existsExp = new AExistsExp(loc, binds, exp);
  $$ = existsExp;
}
| EXISTS1 bind AT expression %prec EXISTS1
{
  CmlLexeme exists = (CmlLexeme)$1;
  PBind bind = (PBind)$2;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  AExists1Exp existsExp = new AExists1Exp(loc, bind, exp, null);
  $$ = existsExp;
}
| IOTA bind AT expression %prec IOTA
{
  CmlLexeme iota = (CmlLexeme)$1;
  PBind bind = (PBind)$2;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(iota), exp.getLocation());
  AIotaExp ioatexp = new AIotaExp(loc, bind, exp);
  $$ = ioatexp;
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
| LCURLY expression BAR bindList AT expression RCURLY
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
| LRSQUARE
{
  CmlLexeme lrsqr = (CmlLexeme)$LRSQUARE;
  List<PExp> exps = new LinkedList<PExp>();
  LexLocation loc = extractLexLocation(lrsqr);
  ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
  $$ = exp;
}
/* ?FIXME: Sequences cannot handle '[[]]' since '[[' and ']]' will be
 * lexed as a DLSQUARE and DRSQUARE token because of the renaming
 * comprehension. For now we need spaces like '[ [] ]' to be able to
 * parse it correctly.
 *
 * (JWC) We definitely need to document this out for users; I don't
 * think we can necessarily fix it (though it should be noted that a
 * sequence enumeration that contains sequence enumerations is a
 * little unusual).
 */
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
| LSQUARE expression BAR setBind AT expression RSQUARE
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
| EMPTYMAP
{
  LexLocation loc = extractLexLocation((CmlLexeme)$EMPTYMAP);
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
| LCURLY maplet BAR bindList AT expression RCURLY
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
/* tuple constructor */
| MKUNDER LPAREN expressionList RPAREN
{
  CmlLexeme mku = (CmlLexeme)$1;
  List<PExp> exprs = (List<PExp>)$3;
  CmlLexeme rparen = (CmlLexeme)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(mku), extractLexLocation(rparen));
  ATupleExp res = new ATupleExp(loc, exprs);
  $$ = res;
}
/* recordConstructor */
| MKUNDERNAME LPAREN expressionList RPAREN
{
  CmlLexeme mku = (CmlLexeme)$1;
  LexNameToken name = extractNameFromUNDERNAMEToken(mku);
  List<PExp> exprs = (List<PExp>)$3;
  LexLocation loc = extractLexLocation(mku, (CmlLexeme)$4);
  ARecordExp res = new ARecordExp(loc, name, exprs);
  $$ = res;
}
/* lambda expression */
| LAMBDA typeBindList AT expression %prec LAMBDA
{
  CmlLexeme l = (CmlLexeme)$1;
  List<ATypeBind> binds = (List<ATypeBind>)$2;
  PExp body = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(l), body.getLocation());
  ALambdaExp res = new ALambdaExp(loc, binds, body, null, null);
  $$ = res;
}
| generalIsExpr
{
  $$ = $1;
}
/* precondition expression */
/* (JWC) first parameter of the precondition expression is the
 * function that we want the precondition of
 */
| PREUNDER LPAREN expressionList RPAREN
{
  CmlLexeme preu = (CmlLexeme)$1;
  List<PExp> exprs = (List<PExp>)$3;
  CmlLexeme rparen = (CmlLexeme)$4;
  PExp function = null;
  LexLocation loc = combineLexLocation(extractLexLocation(preu), extractLexLocation(rparen));
  APreExp res = new APreExp(loc, function, exprs);
  $$ = res;
}
/* DEVIATION
 * PATH
 * GRAMMAR ERROR: Missing COMMA
 * CML_0:
 *   ISOFCLASS LPAREN name expression RPAREN
 * here:
 *   ISOFCLASS LPAREN path COMMA expression RPAREN
 */
| ISOFCLASS LPAREN path COMMA expression RPAREN 
{
    PExp exp = null;
    try{
	Path path = (Path)$path;
	exp = new AIsOfClassExp(extractLexLocation((CmlLexeme)$ISOFCLASS,(CmlLexeme)$RPAREN), 
				path.convertToName(), 
				(PExp)$5);
    }
    catch(Path.PathConvertException e) {
	e.printStackTrace();
	System.exit(-4);
    }
    $$ = exp;
}
/* DEVIATION
 * PATH
 * CML_0:
 *   name
 *   IDENTIFIER TILDE // oldName
 *   expression LPAREN expression ELLIPSIS expression RPAREN // subsequence expression
 *   expression LPAREN expressionList RPAREN
 *   expression DOTHASH NUMERAL // tuple select
 *   expression DOT IDENTIFIER // field select
 *   SELF
 * 1) convert to a name
 * 2) convert to an oldName
 * 3) convert to a subsequence expression
 * 4) convert to a function application
 * 5) convert to a tuple select
 * 6) convert to a field select
 * 7) convert to a self expression
 *
 * (JWC) 3 through 5 need to be general expression rather than just
 * paths/names.  So, this is a problem for now.
 * e.g. we cannot do:
 *   (1,2,3).#2
 * but we can do
 *   a := (1,2,3)
 *   a.#2
 */
| path
{
  Path path = (Path)$1;
  PExp exp = null;
  try {
    exp = path.convertToExpression();
  } catch(Path.PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
  $$ = exp;
}
/* symbolic literal expressions*/
| numericLiteral
{
    PExp exp = null;
    if($1 instanceof LexIntegerToken){
	LexIntegerToken lit = (LexIntegerToken)$1;
	exp = new AIntLiteralExp(lit.location, lit);
    }
    else{
	LexRealToken lit = (LexRealToken)$1;
	exp = new ARealLiteralExp(lit.location, lit);
    }
    $$ = exp;
}
| booleanLiteral
{
  LexBooleanToken lit = (LexBooleanToken)$1;
  $$ = new ABooleanLiteralExp(lit.location, lit);
}
| nilLiteral
{
    LexKeywordToken tok = (LexKeywordToken)$1;
    $$ = new ANilExp(tok.location);
    
}
| characterLiteral 
{
    LexCharacterToken token = (LexCharacterToken)$characterLiteral;
    $$ = new ACharLiteralExp(token.location, token);
}
| textLiteral 
{
    LexStringToken value = (LexStringToken)$textLiteral;
    ASeqSeqType t = new ASeqSeqType(value.location, 
				    true, null, 
				    new ACharBasicType(), 
				    value.value.length() == 0);
    $$ = new AStringLiteralExp(t, 
			       value.location, 
			       value);
}
| quoteLiteral
{
  LexQuoteToken value = (LexQuoteToken)$1;
  $$ = new AQuoteLiteralExp(value.location, value);
}
/* symbolic literal expressions end*/
| chansetExpr
{
  $$ = $1;
}
;

/* symbolic literals */
booleanLiteral:
  FALSE
{
  LexLocation loc = extractLexLocation( (CmlLexeme)$1 );
  $$ = new LexBooleanToken(VDMToken.FALSE, loc);
}
| TRUE
{
  LexLocation loc = extractLexLocation( (CmlLexeme)$1 );
  $$ = new LexBooleanToken(VDMToken.TRUE, loc);
}
;

characterLiteral :
  CHAR_LIT
{
  CmlLexeme lex = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation( lex );
  String res = lex.getValue();
  res = res.replace("'", "");
  $$ = new LexCharacterToken(convertEscapeToChar(res), loc);
}
;

nilLiteral :
  NIL
{
    $$ = new LexKeywordToken(VDMToken.NIL, 
			     extractLexLocation((CmlLexeme)$1));
}
;

numericLiteral :
  NUMERAL
{
  CmlLexeme lexeme = (CmlLexeme)$NUMERAL;
  LexLocation loc = extractLexLocation(lexeme);
  $$ = new LexIntegerToken(Long.decode(lexeme.getValue()), loc);
}
| HEX_LITERAL
{
  CmlLexeme lexeme = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation(lexeme);
  BigInteger b = new BigInteger(lexeme.getValue().substring(2), 16);
  $$ = new LexIntegerToken(b.longValue(), loc);
}
| DECIMAL
{
  CmlLexeme lexeme = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation(lexeme);
  try {
    DecimalFormat dec = new DecimalFormat();
    $$ = new LexRealToken(dec.parse(lexeme.getValue()).doubleValue(), loc);
  } catch (Exception e) {
    $$ = new LexRealToken(0, loc);
  }
}
;

textLiteral : 
 STRING
 {
     String value = ((CmlLexeme)$1).getValue();
     LexLocation loc = extractLexLocation((CmlLexeme)$STRING);
     $$ = new LexStringToken(value.substring(1, value.length()-2), loc);
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

/* symbolic literals end*/

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
  LexLocation  sifloc = new LexLocation(currentSource.toString(),
					"DEFAULT",
					sif.line, sif.column,
					sif.line, eif.column,
					sif.offset, eif.offset);
  ifexp.setTest(test);
  ifexp.setThen(then);
  ifexp.setElseList(elses);
  ifexp.setLocation(
		    combineLexLocation(sifloc,
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
  CmlLexeme cases = (CmlLexeme)$CASES;
  PExp exp = (PExp)$expression;
  ACasesExp bubbleUp = (ACasesExp)$casesExprAltList;
  CmlLexeme end = (CmlLexeme)$END;
  LexLocation lexLoc = combineLexLocation(extractLexLocation(cases), extractLexLocation(end));
  bubbleUp.setExpression(exp);
  bubbleUp.setLocation(lexLoc);
  $$ = bubbleUp;
}
| CASES expression COLON casesExprAltList COMMA OTHERS RARROW expression END
{
  CmlLexeme cases = (CmlLexeme)$CASES;
  PExp exp = (PExp)$2;
  ACasesExp bubbleUp = (ACasesExp)$casesExprAltList; // Others and Cases are taken care of
  CmlLexeme end = (CmlLexeme)$END;
  LexLocation lexLoc = combineLexLocation(extractLexLocation(cases), extractLexLocation(end));
  bubbleUp.setExpression(exp);
  bubbleUp.setLocation(lexLoc);
  bubbleUp.setOthers((PExp)$8);
  $$ = bubbleUp;
}
;

casesExprAltList :
  casesExprAlt
{
  ACasesExp casesExp = new ACasesExp();
  ACaseAlternative caseAlt = (ACaseAlternative)$1;
  casesExp.getCases().add(caseAlt);
  $$ = casesExp;
}
| casesExprAltList COMMA casesExprAlt
{
  ACasesExp casesExp = (ACasesExp)$1;
  ACaseAlternative altExp = (ACaseAlternative)$casesExprAlt;
  casesExp.getCases().add(altExp);
  $$ = casesExp;
}
;

casesExprAlt :
  patternList RARROW expression
{
  List<PPattern> patList = (List<PPattern>)$1;
  PExp exp = (PExp)$expression;
  LexLocation leftMost = extractLexLeftMostFromPatterns(patList);
  LexLocation loc = combineLexLocation(leftMost, exp.getLocation());
  ACaseAlternative res = new ACaseAlternative();
  res.setPattern(patList);
  res.setLocation(loc);
  res.setCexp(exp);
  $$ = res;
}
;

unaryExpr :
  PLUS expression %prec U-PLUS
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AUnaryPlusUnaryExp(location, exp);
}
| MINUS expression %prec U-MINUS
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
| expression DIV expression
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
| expression PSUBSET expression
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
| expression MUNION expression
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

generalIsExpr :
/* DEVIATION
 * CML_0:
 *   ISUNDER name LPAREN expression RPAREN
 * here:
 *   ISUNDERNAME LPAREN expression RPAREN
 *
 */
  ISUNDERNAME LPAREN expression RPAREN
{
  CmlLexeme isUnder = (CmlLexeme)$1;
  PExp exp = (PExp)$3;
  LexLocation loc = combineLexLocation(extractLexLocation(isUnder), exp.getLocation());
  LexNameToken typeName = extractNameFromUNDERNAMEToken(isUnder);
  AIsExp res = new AIsExp(loc, typeName, exp, null);
  $$ = res;
}
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

controlStatement :
  ifStatement
{
  $$ = $1;
}
/* nondeterministic statements */
| IF nonDeterministicAltList END
{
  LexLocation location = extractLexLocation((CmlLexeme)$IF,(CmlLexeme)$END);
  List<ANonDeterministicAltStatementAction> alternatives =
    (List<ANonDeterministicAltStatementAction>)$nonDeterministicAltList;
  $$ = new ANonDeterministicIfStatementAction(location,
						     alternatives);
}
| DO nonDeterministicAltList END %prec U-DO 
{
    LexLocation location = extractLexLocation((CmlLexeme)$DO,(CmlLexeme)$END);
    List<ANonDeterministicAltStatementAction> alternatives =
	(List<ANonDeterministicAltStatementAction>)$nonDeterministicAltList;
    $$ = new ANonDeterministicDoStatementAction(location,
						       alternatives);
}
/* nondeterministic statements end */
/* DEVIATION --- PATH
 * CML_0:
 *   callStatement
 * here:
 *   We're missing explicit call statements, they're subsumed into path and assignment.
 */
/* general assign statement */
/* DEVIATION
 * callStatement --- with assignment
 * grammar:
 *   state designator ':=' call
 * here:
 *   subsumed into assignStatement
 *
 * The typechecker will have to look at the expression in the assign
 * and determine if it is actually an operation call; if it is, then
 * it must rewrite the AST to convert the assign into a call
 * statement.
 */
| assignStatement
{
  $$ = $assignStatement;
}
/* multiple assign statement */
| ATOMIC LPAREN assignStatementList RPAREN
{
  LexLocation location = extractLexLocation((CmlLexeme)$ATOMIC,(CmlLexeme)$RPAREN);
  $$ = new AMultipleGeneralAssignmentStatementAction(location,
							    (List<? extends ASingleGeneralAssignmentStatementAction>)$assignStatementList);
}
/* general assign statement end */
/* specification statement */
| LSQUARE implicitOperationBody RSQUARE
{
  $$ = $implicitOperationBody;
}
/* DEVIATION
 * CML_0
 *   RETURN [ expression ]
 * here:
 *   RETURN [ '(' expression ')' ]
 *
 * (JWC) For reasons I don't yet understand, not having () around the
 * return expression causes conflicts with either []/[ or with
 * identifiers (depending on whether or not, respectively, I've added
 * precidence annotations.  Removing one of the two return productions
 * allows it to work just fine, however, so there's an odd bit of
 * ambiguity at play here.
 */
| RETURN
{
  $$ = new AReturnStatementAction(extractLexLocation((CmlLexeme)$1),
					 null);
}
| RETURN LRPAREN
{
  $$ = new AReturnStatementAction(extractLexLocation((CmlLexeme)$1),
					 null);
}
| RETURN LPAREN expression RPAREN
{
  PExp exp = (PExp)$expression;
  $$ = new AReturnStatementAction(extractLexLocation((CmlLexeme)$RETURN,
							    exp.getLocation()),
					 exp);
}
/* DEVIATION
 * PATH
 * CML_0:
 *   stateDesignator ':=' 'new' name '(' { expression } ')'
 * here:
 *   path COLONEQUALS NEW path LRPAREN
 *   path COLONEQUALS NEW path LPAREN expressionList RPAREN
 *
 */
| path COLONEQUALS NEW path LRPAREN
{
  ANewStatementAction stm = null;
  try {
    Path statePath = (Path)$1;
    Path namePath = (Path)$4;
    List<? extends PExp> args = null;
    LexLocation location = extractLexLocation(statePath.location,(CmlLexeme)$LRPAREN);
    stm = new ANewStatementAction(location,
					 statePath.convertToStateDesignator(),
					 namePath.convertToName(),
					 args);
  } catch(Path.PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
  $$ = stm;
}
| path COLONEQUALS NEW path LPAREN expressionList RPAREN
{
  ANewStatementAction stm = null;
  try {
    Path statePath = (Path)$1;
    Path namePath = (Path)$4;
    List<? extends PExp> args = (List<? extends PExp>)$expressionList;
    LexLocation location = extractLexLocation(statePath.location,(CmlLexeme)$RPAREN);
    stm = new ANewStatementAction(location,
					 statePath.convertToStateDesignator(),
					 namePath.convertToName(),
					 args);
  } catch(Path.PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
  $$ = stm;
}
| casesStatement
{
  $$ = $casesStatement;
}
/* sequence for loop */
/* FIXME
 *
 * (JWC) The grammar allows reverse as a specific keyword to the for
 * loop, but reverse is also a unary expression operator.  I've no
 * idea what the semantic difference is.
 */
| FOR bind IN expression DO action
{
  PAction action = (PAction)$action;
  LexLocation location = extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  PBind bind = (PBind)$bind;
  ADefPatternBind patternBind = new ADefPatternBind(bind.getLocation(),
						    null,
						    bind,
						    null, null);
  $$ = new AForSequenceStatementAction(location,
					      patternBind,
					      (PExp)$expression,
					      action,
					      null);
}
/* | FOR bind IN REVERSE expression DO action */
| FOR pattern IN expression DO action
{
  PAction action = (PAction)$action;
  LexLocation location = extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  PPattern pattern = (PPattern)$pattern;
  ADefPatternBind patternBind = new ADefPatternBind(pattern.getLocation(),
						    pattern,
						    null,
						    null, null);
  $$ = new AForSequenceStatementAction(location,
					      patternBind,
					      (PExp)$expression,
					      action,
					      null);
}
/* | FOR pattern IN REVERSE expression DO action */
/* sequence for loop end */
/* set for loop */
| FOR ALL pattern INSET expression DO action
{
  PAction action = (PAction)$action;
  LexLocation location = extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  $$ = new AForSetStatementAction(location,
					 (PPattern)$pattern,
					 (PExp)$expression,
					 action);
}
/* index for loop */
| FOR IDENTIFIER EQUALS expression TO expression DO action
{
  PAction action = (PAction)$action;
  LexLocation location = extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  $$ = new AForIndexStatementAction(location,
					   extractLexNameToken($IDENTIFIER),
					   (PExp)$4 ,
					   (PExp)$6,
					   null,
					   action);
}
| FOR IDENTIFIER EQUALS expression TO expression BY expression DO action
{
  PAction action = (PAction)$action;
  LexLocation location = extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  $$ = new AForIndexStatementAction(location,
					   extractLexNameToken($IDENTIFIER),
					   (PExp)$4 ,
					   (PExp)$6,
					   (PExp)$8,
					   action);
}
/* index for loop end */
/* while loop */
| WHILE expression DO action
{
  PAction action = (PAction)$action;
  LexLocation location = extractLexLocation((CmlLexeme)$WHILE,action.getLocation());
  $$ = new AWhileStatementAction(location,
					(PExp)$expression,
					action);
}
/* DEVIATION
 * callStatement --- without assignment
 * grammar:
 *   call
 *   call : [ object designator '.' ] name '(' [ expressionList ] ')'
 * here:
 *   subsumed into path
 *
 * The typechecker will have to look at the expression in the assign
 * and determine if it is actually an operation call; if it is, then
 * it must rewrite the AST to convert the assign into a call
 * statement.
 */
| path
{
  Path path = (Path)$path;
  PAction action = null;
  try {
    action = path.convertToAction();
  } catch(Path.PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
  $$ = action;
}
;

nonDeterministicAltList :
  expression RARROW action
{
  PExp guard = (PExp)$expression;
  PAction action = (PAction)$action;
  LexLocation location = combineLexLocation(guard.getLocation(),
					    action.getLocation());
  List<ANonDeterministicAltStatementAction> alts =
    new LinkedList<ANonDeterministicAltStatementAction>();
  alts.add(new ANonDeterministicAltStatementAction(location,
							    guard,
							    action));
  $$ = alts;
}
| nonDeterministicAltList BAR expression RARROW action
{
  PExp guard = (PExp)$expression;
  PAction action = (PAction)$action;
  LexLocation location = extractLexLocation((CmlLexeme)$BAR,
					    action.getLocation());
  List<ANonDeterministicAltStatementAction> alts =
    (List<ANonDeterministicAltStatementAction>)$1;
  alts.add(new ANonDeterministicAltStatementAction(location,
							    guard,
							    action));
  $$ = alts;
}
;

letStatement :
  LET localDefList IN action
{
  PAction action = (PAction)$action;
  LexLocation location = extractLexLocation((CmlLexeme)$LET,
					    action.getLocation());
  $$ = new ALetStatementAction(location,
			       action,
			       (List<? extends PDefinition>)$localDefList);
}
;

blockStatement :
  LPAREN action RPAREN
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3);
  PAction action = (PAction)$2;
  $$ = new ABlockStatementAction(location, null, action);
}
| LPAREN DCL assignmentDefList AT action RPAREN
{
  LexLocation location = extractLexLocation((CmlLexeme)$LPAREN, (CmlLexeme)$RPAREN);
  ADeclareStatementAction dclStm = new ADeclareStatementAction(extractLexLocation((CmlLexeme)$DCL,
										  (CmlLexeme)$AT),
							       (List<? extends PDefinition>)$assignmentDefList);
  PAction action = (PAction)$action;
  $$ = new ABlockStatementAction(location, dclStm, action);
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
  LexNameToken name = extractLexNameToken((CmlLexeme)$IDENTIFIER);
  PType type = (PType)$type;
  LexLocation location = combineLexLocation(name.location, type.getLocation());
  AAccessSpecifier access = null;
  $$ = new AAssignmentDefinition(location, 
				 name, 
				 NameScope.GLOBAL, 
				 false, 
				 access, 
				 type, 
				 null, 
				 null);
}
| IDENTIFIER COLON type COLONEQUALS expression
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$IDENTIFIER);
  PType type = (PType)$type;
  PExp exp = (PExp)$expression;
  LexLocation location = combineLexLocation(name.location, type.getLocation());
  AAccessSpecifier access = null;
  $$ = new AAssignmentDefinition(location, name, 
				 NameScope.GLOBAL, 
				 false, 
				 access, 
				 type, 
				 exp, 
				 null);
}
/*(AKM)
 *FIXME: This is probably not going to work since you can't see the difference
 *       Between 'id : type in exp' and 'id : type := exp' 
 *
 */
| IDENTIFIER COLON type IN expression 
{
    LexNameToken name = extractLexNameToken((CmlLexeme)$IDENTIFIER);
    PType type = (PType)$type;
    PExp exp = (PExp)$expression;
    LexLocation location = combineLexLocation(name.location, exp.getLocation());
    AAccessSpecifier access = null;
    $$ = new AAssignmentDefinition(location, name, 
				   NameScope.GLOBAL, 
				   false, 
				   access, 
				   type, 
				   exp, 
				   null);
}
;

/* DEVIATION !!
 * assignment lists in atomic blocks no longer use semicolon separators
 */
assignStatementList :
  assignStatement
{
  List<ASingleGeneralAssignmentStatementAction> assigns =
    new LinkedList<ASingleGeneralAssignmentStatementAction>();
  assigns.add((ASingleGeneralAssignmentStatementAction)$assignStatement);
  $$ = assigns;
}
| assignStatementList[list] assignStatement
{
  List<ASingleGeneralAssignmentStatementAction> assigns =
    (List<ASingleGeneralAssignmentStatementAction>)$list;
  assigns.add((ASingleGeneralAssignmentStatementAction)$assignStatement);
  $$ = assigns;
}
;

assignStatement :
/* DEVIATION
 * PATH
 * CML_0:
 *   stateDesignator ':=' expression
 * here:
 *   path ':=' expression
 */
  path COLONEQUALS expression
{
  Path path = (Path)$path;
  PStateDesignator stateDesignator = null;
  try {
    stateDesignator = path.convertToStateDesignator();
  } catch(Path.PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
  PExp exp = (PExp)$expression;
  LexLocation location = combineLexLocation(stateDesignator.getLocation(), exp.getLocation());
  $$ = new ASingleGeneralAssignmentStatementAction(location, stateDesignator , exp);
}
;

ifStatement :
  IF expression THEN action elseStatements ELSE action
{
  PAction action = (PAction)$7;
  $$ = new AIfStatementAction(extractLexLocation((CmlLexeme)$1, action.getLocation()),
				     (PExp)$2, (PAction)$4, (List<? extends AElseIfStatementAction>)$5, action);
}
| IF expression THEN action ELSE action
{
  PAction action = (PAction)$6;
  $$ = new AIfStatementAction(extractLexLocation((CmlLexeme)$1, action.getLocation()),
				     (PExp)$2, (PAction)$4, null, action);
}
;

elseStatements :
  ELSEIF expression THEN action
{
  List<AElseIfStatementAction> elseStms = new Vector<AElseIfStatementAction>();
  PAction thenStm = (PAction)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1, thenStm.getLocation());
  elseStms.add(new AElseIfStatementAction(location, (PExp)$2, thenStm));
  $$ = elseStms;
}
| elseStatements ELSEIF expression THEN action
{
  PAction action = (PAction)$5;
  List<AElseIfStatementAction> elseStms = (List<AElseIfStatementAction>)$1;
  LexLocation location = combineLexLocation(extractLastLexLocation(elseStms), extractLexLocation((CmlLexeme)$4));
  elseStms.add(0, new AElseIfStatementAction(location, (PExp)$3, action));
  $$ = elseStms;
}
;

casesStatement :
  CASES expression COLON casesStatementAltList END
{
  LexLocation location = extractLexLocation((CmlLexeme)$CASES, (CmlLexeme)$END);
  ACasesStatementAction cases = (ACasesStatementAction)$casesStatementAltList;
  cases.setLocation(location);
  cases.setExp((PExp)$expression);
  $$ = cases;
}
| CASES expression COLON casesStatementAltList COMMA OTHERS RARROW action END
{
  LexLocation location = extractLexLocation((CmlLexeme)$CASES, (CmlLexeme)$END);
  ACasesStatementAction cases = (ACasesStatementAction)$casesStatementAltList;
  cases.setLocation(location);
  cases.setExp((PExp)$expression);
  PAction others = (PAction)$action;
  cases.setOthers(others);
  $$ = cases;
}
;

casesStatementAltList :
  casesStatementAlt
{
  List<ACaseAlternativeAction> casesList = new LinkedList<ACaseAlternativeAction>();
  casesList.add((ACaseAlternativeAction)$casesStatementAlt);
  $$ = new ACasesStatementAction(null,
					null,
					casesList,
					null);
}
| casesStatementAltList COMMA casesStatementAlt
{
  ACasesStatementAction cases = (ACasesStatementAction)$1;
  cases.getCases().add((ACaseAlternativeAction)$casesStatementAlt);
  $$ = cases;
}
;

casesStatementAlt :
  patternList RARROW action
{
  PAction action = (PAction)$action;
  List<PPattern> patterns = (List<PPattern>)$patternList;
  $$ = new ACaseAlternativeAction(combineLexLocation(extractFirstLexLocation(patterns),
						     action.getLocation()),
				  patterns,
				  (PAction)$action);
}
;

implicitOperationBody :
  externals_opt preExpr_opt postExpr
{
  PExp postcondition = (PExp)$postExpr;
  List<? extends AExternalClause> exts = (List<? extends AExternalClause>)$externals_opt;
  LexLocation location = combineLexLocation(extractFirstLexLocation(exts),
					    postcondition.getLocation());
  $$ = new ASpecificationStatementAction(location,
						exts,
						(PExp)$preExpr_opt,
						postcondition);
}
;

pattern :
  patternIdentifier
{
    $$ = $1;
}
| patternLessID 
{
    $$ = $1;
}
;

patternLessID :
  matchValue
{
  $$ = $1;
}
/* tuple pattern */
| MKUNDER LPAREN patternList COMMA pattern RPAREN // TODO
/* record patterns */
/* DEVIATION
 * PATH
 * CML_0:
 *   MKUNDER name LPAREN expression RPAREN
 * here:
 *   MKUNDERNAME LPAREN expression RPAREN
 *
 */
| MKUNDERNAME LRPAREN
{
  List<? extends PPattern> plist = null;
  LexNameToken name = extractNameFromUNDERNAMEToken((CmlLexeme)$1);
  $$ = new ARecordPattern(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$2), null, false, name, plist);
}
| MKUNDERNAME LPAREN patternList RPAREN
{
  List<? extends PPattern> plist = (List<? extends PPattern>)$3;
  LexNameToken name = extractNameFromUNDERNAMEToken((CmlLexeme)$1);
  $$ = new ARecordPattern(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$4), null, false, name, plist);
}
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
  LexNameToken lnt = new LexNameToken("", lexeme.getValue(), extractLexLocation(lexeme), false, true);
  AIdentifierPattern res = new AIdentifierPattern();
  res.setName(lnt);
  res.setLocation(lnt.getLocation());
  $$ = res;
}
/* "don't care" identifier */
| MINUS 
{
    $$ = new AIgnorePattern(extractLexLocation((CmlLexeme)$1), 
			    new LinkedList<PDefinition>(), 
			    true);
}
;

matchValue :
/* symbolic literal patterns*/
  numericLiteral
{
  PPattern pattern = null;
  if($1 instanceof LexIntegerToken) {
    LexIntegerToken lit = (LexIntegerToken)$1;
    pattern = new AIntegerPattern(lit.location, 
				  new LinkedList<PDefinition>(), 
				  true, 
				  lit);
  } else {
    LexRealToken lit = (LexRealToken)$1;
    pattern = new ARealPattern(lit.location, 
			       new LinkedList<PDefinition>(), 
			       true, 
			       lit);
  }
  $$ = pattern;
}
| booleanLiteral
{
  LexBooleanToken lit = (LexBooleanToken)$1;
  $$ = new ABooleanPattern(lit.location, 
			   new LinkedList<PDefinition>(), 
			   true, 
			   lit);
}
| nilLiteral
{
  LexKeywordToken tok = (LexKeywordToken)$1;
  $$ = new ANilPattern(tok.location, 
		       new LinkedList<PDefinition>(), 
		       true);    
}
| characterLiteral 
{
  LexCharacterToken token = (LexCharacterToken)$characterLiteral;
  $$ = new ACharacterPattern(token.location, 
			     new LinkedList<PDefinition>(), 
			     true, 
			     token);
}
| textLiteral 
{
  LexStringToken value = (LexStringToken)$textLiteral;
  $$ = new AStringPattern(value.location, 
			  new LinkedList<PDefinition>(), 
			  true, 
			  value);
}
| quoteLiteral
{
  LexQuoteToken value = (LexQuoteToken)$1;
  $$ = new AQuotePattern(value.location, 
			 new LinkedList<PDefinition>(), 
			 true, 
			 value);
}
| LPAREN expression RPAREN
{
  $$ = new AExpressionPattern(extractLexLocation((CmlLexeme)$LPAREN,(CmlLexeme)$RPAREN), 
			      new LinkedList<PDefinition>(), 
			      false, 
			      (PExp)$expression);
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
{
    $$ = $1;
}
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
path[result] :
  unit
{
  $$ = new Path((Unit)$1);
}
| path TILDE
{
  Path path = (Path)$1;
  LexLocation location = extractLexLocation(path.location,(CmlLexeme)$TILDE);
  $$ = new Path(location,Path.PathKind.TILDE,path);
}
| path DOT unit
{
  Path path = (Path)$1;
  Unit unit = (Unit)$3;
  LexLocation location = extractLexLocation(path.location,unit.location);
  $$ = new Path(location,Path.PathKind.DOT,path,unit);
}
| path BACKTICK unit
{
  Path path = (Path)$1;
  Unit unit = (Unit)$3;
  LexLocation location = extractLexLocation(path.location,unit.location);
  $$ = new Path(location,Path.PathKind.BACKTICK,path,unit);
}
| path DOTHASH NUMERAL
{
  CmlLexeme lexeme = (CmlLexeme)$NUMERAL;
  Path path = (Path)$1;
  LexLocation location = extractLexLocation(path.location,(CmlLexeme)$NUMERAL);
  $$ = new Path(location,Path.PathKind.DOTHASH,path,Integer.decode(lexeme.getValue()));
}
| path LRPAREN
{
  Path path = (Path)$1;
  LexLocation location = extractLexLocation(path.location,(CmlLexeme)$LRPAREN);
  $$ = new Path(location,Path.PathKind.APPLY,path);
}
| path LPAREN expressionList RPAREN
{
  Path path = (Path)$1;
  LexLocation location = extractLexLocation(path.location,(CmlLexeme)$RPAREN);
  $$ = new Path(location,Path.PathKind.APPLY,path,(List<PExp>)$expressionList);
}
| path LPAREN expression ELLIPSIS expression RPAREN
{
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)$3);
  exps.add((PExp)$5);
  Path path = (Path)$1;
  LexLocation location = extractLexLocation(path.location,(CmlLexeme)$RPAREN);
  $$ = new Path(location,Path.PathKind.SEQRANGE,path,exps);
}
/* Bits for CSP renaming (IDENTIFIER DOT IDENTIFIER is above as path DOT unit) */
/* channel name expression bits */
| path DOT matchValue // Sorry Anders -jwc
/* | path DOT nilLiteral  */
/* { */
/*     Path path = (Path)$1; */
/*     LexToken literal = (LexToken)$3;  */
/*     LexLocation location = extractLexLocation(path.location,literal.location); */
/*     $$ = new Path(location,Path.PathKind.DOT_LITERAL,path, literal); */
/* } */
/* | path DOT booleanLiteral  */
/* { */
/*     Path path = (Path)$1; */
/*     LexToken literal = (LexToken)$3;  */
/*     LexLocation location = extractLexLocation(path.location,literal.location); */
/*     $$ = new Path(location,Path.PathKind.DOT_LITERAL,path, literal); */
/* } */
/* | path DOT numericLiteral  */
/* { */
/*     Path path = (Path)$1; */
/*     LexToken literal = (LexToken)$3;  */
/*     LexLocation location = extractLexLocation(path.location,literal.location); */
/*     $$ = new Path(location,Path.PathKind.DOT_LITERAL,path, literal); */
/* } */
/* | path DOT quoteLiteral  */
/* { */
/*     Path path = (Path)$1; */
/*     LexToken literal = (LexToken)$3;  */
/*     LexLocation location = extractLexLocation(path.location,literal.location); */
/*     $$ = new Path(location,Path.PathKind.DOT_LITERAL,path, literal); */
/* } */
/* | path DOT textLiteral */
/* { */
/*     Path path = (Path)$1; */
/*     LexToken literal = (LexToken)$3;  */
/*     LexLocation location = extractLexLocation(path.location,literal.location); */
/*     $$ = new Path(location,Path.PathKind.DOT_LITERAL,path, literal); */
/* } */
/* | path DOT characterLiteral  */
/* { */
/*     Path path = (Path)$1; */
/*     LexToken literal = (LexToken)$3;  */
/*     LexLocation location = extractLexLocation(path.location,literal.location); */
/*     $$ = new Path(location,Path.PathKind.DOT_LITERAL,path, literal); */
/* } */
/* | path DOT LPAREN expression RPAREN // TODO -- channel name expression */
| path QUESTION unit // TODO -- channel name expression
| path QUESTION matchValue // TODO -- channel name expression
/* | path QUESTION bind */
| path BANG unit
| path BANG matchValue
/* channel name expression bits end*/
;

unit :
  SELF
{
  $$ = new Unit(Unit.UnitKind.SELF,
		extractLexIdentifierToken((CmlLexeme)$1));
}
| IDENTIFIER
{
  $$ = new Unit(Unit.UnitKind.IDENTIFIER,
		extractLexIdentifierToken((CmlLexeme)$1));
}
;

pathList :
  path
{
  try {
    LexNameToken lnt = ((Path)$path).convertToName();
    List<LexNameToken> names = new LinkedList<LexNameToken>();
    names.add(lnt);
    $$ = names;
  } catch(Path.PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
}
| pathList COMMA path
{
  try {
    LexNameToken lnt = ((Path)$path).convertToName();
    List<LexNameToken> names = (List<LexNameToken>)$1;
    names.add(lnt);
    $$ = names;
  } catch(Path.PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
}
;

// **********************
// *** END OF GRAMMAR ***
// **********************
