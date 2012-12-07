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

  import org.overture.ast.factory.AstFactory;
  import org.overture.ast.definitions.*;
  import org.overture.ast.expressions.*;
  import org.overture.ast.lex.*;
  import org.overture.ast.node.*;
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
  import eu.compassresearch.core.lexer.CmlLexeme;
  import eu.compassresearch.core.lexer.CmlLexer;
  import eu.compassresearch.core.lexer.Position;
  import eu.compassresearch.core.parser.util.*;

}

%code{
  // **************************
  // *** PARSER PUBLIC INFO ***
  // **************************
  public static class Info {
    public static final String CML_LANG_VERSION = "CML 0";
  };

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
  private CmlParserHelper util = null;

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

  // *************************
  // *** PUBLIC OPERATIONS ***
  // *************************

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

          //INode node = cmlParser.getDocument();
	  //DotGraphVisitor dgv = new DotGraphVisitor();
          //node.apply(dgv,null);

          //File dotFile = new File("generatedAST.gv");
          //java.io.FileWriter fw = new java.io.FileWriter(dotFile);
          //fw.write(dgv.getResultString());
          //fw.close();

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

  public void setDocument(PSource doc)
  {
    this.currentSource = doc;
    this.util = new CmlParserHelper(this.currentSource);
  }

  public PSource getDocument()
  {
    return currentSource;
  }

} /* End of code block */

/* ---------------------------------------------------------------- */
/* Token declarations                                               */
/* ---------------------------------------------------------------- */

%token TYPES CHANNELS CHANSETS NAMESETS FUNCTIONS OPERATIONS ACTIONS INITIAL
%token STATE

%token EQUALS AT BARTILDEBAR LRSQUARE TBAR LPAREN RPAREN LRPAREN LSQUARE
%token RSQUARE CSPSKIP CSPSTOP CSPCHAOS CSPDIV CSPWAIT RARROW LARROW LCURLY
%token RCURLY BAR DBAR SEMI DCOLON COMPOSE OF STAR TO INMAPOF MAPOF SEQOF
%token SEQ1OF SETOF PLUSGT COLONDASH DEQUALS INV VALUES PRE POST MEASURE IOTA
%token SUBCLASSRESP NOTYETSPEC FRAME RD WR LET IN IF THEN ELSEIF ELSE CASES
%token OTHERS PLUS MINUS ABS FLOOR NOT CARD POWER DUNION DINTER HD TL LEN
%token ELEMS INDS REVERSE CONC DOM RNG MERGE INVERSE ELLIPSIS BARRARROW
%token MKUNDER MKUNDERNAME DOT DOTHASH NUMERAL LAMBDA NEW ISUNDER PREUNDER
%token ISOFCLASS TILDE DCL COLONEQUALS ATOMIC DEQRARROW RETURN BACKTICK SLASH
%token DIV REM MOD LT LTE GT GTE NEQ OR AND EQRARROW LTEQUALSGT INSET
%token NOTINSET SUBSET PSUBSET UNION BACKSLASH INTER CARET DPLUS MUNION
%token LTCOLON LTDASHCOLON COLONGT COLONDASHGT COMP DSTAR FORALL EXISTS
%token EXISTS1 STRING VRES RES VAL AMP LSQUAREBAR DLSQUARE DRSQUARE
%token BARRSQUARE COMMA LSQUAREDBAR DBARRSQUARE COLON LCURLYBAR BARRCURLY
%token QUESTION BANG SLASHCOLON SLASHBACKSLASH COLONBACKSLASH LSQUAREGT BARGT
%token ENDSBY DECIMAL STARTBY MU DO FOR ALL BY WHILE ISUNDERNAME EXTENDS
%token EMPTYMAP DBACKSLASH BEGIN END CLASS PROCESS PRIVATE PROTECTED PUBLIC
%token LOGICAL
%token IDENTIFIER SELF TRUE FALSE CHAR_LIT NIL QUOTE_LITERAL HEX_LITERAL
%token TBOOL TNAT TNAT1 TINT TRAT TREAL TCHAR TTOKEN

%token DUMMY

/* ---------------------------------------------------------------- */
/* Precidence declarations                                          */
/* ---------------------------------------------------------------- */

/* TODO (JWC) --- eventual precendence ordering
 * From loose -> tight
 *
 * 5. ifs and loops (15.7, 15.8, 15.9), let statement (let x = e in A), block statement (dcl x: T @ A), parametrised actions (val x: T @ A)
 * 4. parallelism ([|ns1|ns2|], ||, [ns1|cs1||cs2|ns2], [cs1||cs2], [|ns1|cs|ns2|], [|cs|]), interleaving ([||ns1|ns2||],|||)
 * 3. timeout ([>), interrupt (/\), external choice ([]), internal choice (|~|)
 * 2. prefixing (->), guard (&), sequence (;)
 * 1. VDM statements
 */

/* Note that U-PLUS, U-MINUS, U-SEMI, ... are tokens for
 * disambiguating precidence (shift/reduce conflicts, typically).
 */
/* Precidence from loosest to tightest; tokens on same line are equal precidence */
%left G-LOOSE
%right RETURN
%right S-LET
%right LET //needed to make let expressions recurse correctly
%right LPAREN // also needed to make parenthesized expressions recurse
%right MU LAMBDA AT
%right COMMA
%right FORALL EXISTS EXISTS1 IOTA
//%right LPAREN
//%left OF
%nonassoc COLONEQUALS
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
%right QUESTIONALONE
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
// VDM prec group applicators ---- plus CSP communication elements
%right DOT BACKTICK BANG QUESTION
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
  $$ = util.caseFirstListElement((PDefinition)$programParagraph);
}
| programParagraphList[list] programParagraph
{
  $$ = util.caseNextListElement($list,(PDefinition)$programParagraph);
}
;

programParagraph :
  classDefinition            { $$ = $1; }
| processDefinition          { $$ = $1; }
| channelDefinition          { $$ = $1; }
| chansetDefinitionParagraph { $$ = $1; }
| globalDefinitionParagraph  { $$ = $1; }
;

classDefinition :
  CLASS IDENTIFIER EQUALS BEGIN classDefinitionBlock END
{
  AClassParagraphDefinition clz = new AClassParagraphDefinition();
  LexNameToken lexName = util.extractLexNameToken((CmlLexeme)$IDENTIFIER);
  LexLocation loc = util.extractLexLocation((CmlLexeme)$CLASS,(CmlLexeme)$END);
  clz.setLocation(loc);
  clz.setName(lexName);
  clz.setDefinitions((List<PDefinition>)$classDefinitionBlock);
  clz.setNameScope(NameScope.CLASSNAME);
  clz.setAccess(util.getDefaultAccessSpecifier(true, false, loc));
  AClassType ct = new AClassType(clz.getLocation(), false, clz.getDefinitions(), clz.getName(), null);
  clz.setType(ct);
  for(PDefinition def : clz.getDefinitions())
  {
    	if (def instanceof AClassInvariantDefinition)
		{
			AClassInvariantDefinition cinv = (AClassInvariantDefinition)def;
			cinv.setName(new LexNameToken("", new LexIdentifierToken("inv"+lexName.getName(), false, loc) ));
		}
  }
  $$ = clz;
}
/* DEVIATION
 * EXTENDS moved to the left of the EQUALS
 */
| CLASS IDENTIFIER[id] EXTENDS IDENTIFIER[parent] EQUALS BEGIN classDefinitionBlock END
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$CLASS,(CmlLexeme)$END);
  List<LexNameToken> supernames = new LinkedList<LexNameToken>();
  supernames.add(util.extractLexNameToken($parent));
  AClassParagraphDefinition clz = new AClassParagraphDefinition(location,
                                     util.extractLexNameToken($id),
                                     NameScope.CLASSNAME,
                                     false,
                                     null,//ClassDefinition
                                     util.getDefaultAccessSpecifier(false,false,null),
                                     null/*PType type_  should this be the namedInvariantType*/,
                                     null,//Pass
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
   clz.setAccess(util.getDefaultAccessSpecifier(true, false, location));
   $$ = clz;
}
;

processDefinition:
  PROCESS IDENTIFIER EQUALS process
{
  PProcess process = (PProcess)$process;
  LexNameToken id = util.extractLexNameToken((CmlLexeme)$IDENTIFIER);
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, process.getLocation());
  AProcessDefinition processDef = new AProcessDefinition(process.getLocation(),
                                                         NameScope.GLOBAL,
                                                         false,
                                                         access,
                                                         null,//Pass
                                                         null,
                                                         process);
  LexLocation location = util.extractLexLocation((CmlLexeme)$PROCESS,
                                            processDef.getLocation());                                                         
                                                         
  if (process instanceof AStateProcess)
  {
    AStateProcess stateProcess = (AStateProcess)process;
    List defs = stateProcess.getDefinitionParagraphs();
    for(Object def : defs)
    {
    	if (def instanceof AClassInvariantDefinition)
			{
				
				AClassInvariantDefinition cinv = (AClassInvariantDefinition)def;
				cinv.setName(new LexNameToken("", new LexIdentifierToken("inv_"+id.getName(),false,location)));
			}
    }
  }
                                                         
  
  processDef.setName(id);
  
  access = util.getDefaultAccessSpecifier(true, false, location);
  processDef.setName(id);
  AProcessParagraphDefinition p = new AProcessParagraphDefinition(location,
                                                                  id,
                                                                  NameScope.PROCESSNAME,
                                                                  false,
                                                                  null,//VDM ClassDefinition
                                                                  access,
                                                                  null,//Type
                                                                  null,//Pass
                                                                  processDef);
  $$ = p;
}
| PROCESS IDENTIFIER EQUALS singleTypeDeclarationList[list] AT process
{
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)$list;
  PProcess process = (PProcess)$process;
  LexLocation loc = util.combineLexLocation(util.extractFirstLexLocation(decls),
                                       process.getLocation());
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, loc);
  AProcessDefinition processDef = new AProcessDefinition(loc,
                                                         NameScope.GLOBAL,
                                                         false,
                                                         access,
                                                         null,//Pass
                                                         decls,
                                                         process);
  LexNameToken id = util.extractLexNameToken((CmlLexeme)$IDENTIFIER);
  processDef.setName(id);
  LexLocation location = util.extractLexLocation((CmlLexeme)$PROCESS,
                                            processDef.getLocation());
  if (process instanceof AStateProcess)
  {
    AStateProcess stateProcess = (AStateProcess)process;
    List defs = stateProcess.getDefinitionParagraphs();
    for(Object def : defs)
    {
    	if (def instanceof AClassInvariantDefinition)
			{
				
				AClassInvariantDefinition cinv = (AClassInvariantDefinition)def;
				cinv.setName(new LexNameToken("", new LexIdentifierToken("inv_"+id.getName(),false,location)));
			}
    }
  }
                                            
  access = util.getDefaultAccessSpecifier(true, false, location);
  processDef.setName(id);
  AProcessParagraphDefinition p = new AProcessParagraphDefinition(location,
                                                                  id,
                                                                  NameScope.PROCESSNAME,
                                                                  false,
                                                                  null,//VDM ClassDefinition
                                                                  access,
                                                                  null,//Type
                                                                  null,//Pass
                                                                  processDef);
  $$ = p;
}
;

process :
/* actions */
  BEGIN AT action END
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$1, (CmlLexeme)$4);
  List<SParagraphDefinition> processParagraphs = new LinkedList<SParagraphDefinition>();
  PAction action = (PAction)$3;
  $$ = new AStateProcess(location, processParagraphs, action);
}
| BEGIN processParagraphList AT action END
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$1, (CmlLexeme)$5);
  List<SParagraphDefinition> processParagraphs = (List<SParagraphDefinition>)$processParagraphList;
  PAction action = (PAction)$4;
  $$ = new AStateProcess(location, processParagraphs, action);
}
/* actions end */
| process SEMI process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  $$ = new ASequentialCompositionProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
}
| process LRSQUARE process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  $$ = new AExternalChoiceProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
}
| process BARTILDEBAR process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  $$ = new AInternalChoiceProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
}
/* CHANSET
 * expression was chansetExpr here
 */
| process LSQUAREBAR expression BARRSQUARE process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$5;
  $$ = new AGeneralisedParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)$3, right);
}
/* CHANSET
 * expression was chansetExpr here
 */
| process LSQUARE expression DBAR expression RSQUARE process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$7;
  $$ = new AAlphabetisedParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, (PExp)$3, (PExp)$5, right);
}
| process DBAR process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  $$ = new ASynchronousParallelismProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
}
| process TBAR process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  $$ = new AInterleavingProcess(util.combineLexLocation(left.getLocation(), right.getLocation()), left, right);
}
| process SLASHBACKSLASH process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
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
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ATimedInterruptProcess(location, left, (PExp)$3, right);
}
| process LSQUAREGT process
{
  PProcess left = (PProcess)$1;
  PProcess right = (PProcess)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
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
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ATimeoutProcess(location, left, (PExp)$3, right);
}
/* DEVIATION
 * grammar:
 *   process '\' expression
 * here:
 *   process '\\' expression
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 *
 * CHANSET
 * expression was chansetExpr here
 */
| process DBACKSLASH expression
{
  PProcess left = (PProcess)$1;
  PExp cse = (PExp)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), cse.getLocation());
  $$ = new AHidingProcess(location, left, cse);
}
| process STARTBY expression
{
  PProcess left = (PProcess)$1;
  PExp exp = (PExp)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), exp.getLocation());
  $$ = new AStartDeadlineProcess(location, left, exp);
}
| process ENDSBY expression
{
  PProcess left = (PProcess)$1;
  PExp exp = (PExp)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), exp.getLocation());
  $$ = new AEndDeadlineProcess(location, left, exp);
}
/* DEVIATION
 * Unfolded the definition a little bit to avoid a conflict
 */
| LPAREN[start] singleTypeDeclarationList AT process[proc] RPAREN LPAREN expression RPAREN[end]
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$start, (CmlLexeme)$end);
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)$singleTypeDeclarationList;
  List<PExp> args = new LinkedList<PExp>();
  args.add((PExp)$expression);
  PProcess proc = (PProcess)$proc;
  $$ = new AInstantiationProcess(location,
				 decls,
				 proc,
				 args);
}
| IDENTIFIER
{
  LexNameToken name = util.extractLexNameToken($IDENTIFIER);
  $$ = new AReferenceProcess(name.location,
			     name,
			     new LinkedList<PExp>());
  /* $$ = new AInstantiationProcess(name.location, */
  /*                                null, */
  /*                                name, */
  /*                                null, */
  /*                                null); */
}
| IDENTIFIER LRPAREN
{
  LexNameToken name = util.extractLexNameToken($IDENTIFIER);
  LexLocation location = util.extractLexLocation(name.location,(CmlLexeme)$LRPAREN);
  $$ = new AReferenceProcess(location,
			     name,
			     new LinkedList<PExp>());

  /* $$ = new AInstantiationProcess(location, */
  /*                                null, */
  /*                                name, */
  /*                                null, */
  /*                                null); */
}
| IDENTIFIER LPAREN expressionList RPAREN
{
  LexNameToken name = util.extractLexNameToken($IDENTIFIER);
  LexLocation location = util.extractLexLocation(name.location,(CmlLexeme)$RPAREN);
  $$ = new AReferenceProcess(location,
			     name,
			     (List<PExp>)$expressionList);

  /* $$ = new AInstantiationProcess(location, */
  /*                                null, */
  /*                                name, */
  /*                                null, */
  /*                                (List<PExp>)$expressionList); */
}
| process[proc] renameExpression[rexp]
{
  SRenameChannelExp renameExpression = (SRenameChannelExp)$rexp;
  PProcess process = (PProcess)$proc;
  $$ = new AChannelRenamingProcess(util.combineLexLocation(process.getLocation(),
                                                      renameExpression.getLocation()),
                                   process,
                                   renameExpression);
}
/* replicated processes */
| SEMI replicationDeclaration AT process %prec U-SEMI
{
  PProcess process = (PProcess)$4;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new ASequentialCompositionReplicatedProcess(location,
                                                   (List<SSingleDeclaration>)$replicationDeclaration,
                                                   process);
}
| LRSQUARE replicationDeclaration AT process %prec U-LRSQUARE
{
  PProcess process = (PProcess)$4;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new AExternalChoiceReplicatedProcess(location,
                                            (List<SSingleDeclaration>)$replicationDeclaration,
                                            process);
}
| BARTILDEBAR replicationDeclaration AT process %prec U-BARTILDEBAR
{
  PProcess process = (PProcess)$4;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,process.getLocation());
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
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,process.getLocation());
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
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new AAlphabetisedParallelismReplicatedProcess(location,
                                                     (List<SSingleDeclaration>)$replicationDeclaration,
                                                     process,
                                                     chansetExp);
}
| DBAR replicationDeclaration AT process %prec U-DBAR
{
  PProcess process = (PProcess)$4;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new ASynchronousParallelismReplicatedProcess(location,
                                                    (List<SSingleDeclaration>)$replicationDeclaration,
                                                    process);
}
| TBAR replicationDeclaration AT process %prec U-TBAR
{
  PProcess process = (PProcess)$4;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,process.getLocation());
  $$ = new AInterleavingReplicatedProcess(location,
                                          (List<SSingleDeclaration>)$replicationDeclaration,
                                          process);
}
;

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
| replicationDeclaration SEMI singleTypeDeclaration
{
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)$1;
  decls.add((SSingleDeclaration)$singleTypeDeclaration);
  $$ = decls;
}
| replicationDeclaration SEMI singleExpressionDeclaration
{
  List<SSingleDeclaration> decls = (List<SSingleDeclaration>)$1;
  decls.add((SSingleDeclaration)$singleExpressionDeclaration);
  $$ = decls;
}
;

/* DEVIATION
 * CML_0:
 *   IDENTIFIER { COMMA IDENTIFIER } ':' expression
 * here:
 *   IDENTIFIER { COMMA IDENTIFIER } INSET expression
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
singleExpressionDeclaration :
  IDENTIFIER[id] INSET expression
{
  LexNameToken id = util.extractLexNameToken((CmlLexeme)$id);
  List<LexNameToken> ids = new Vector<LexNameToken>();
  ids.add(id);
  PExp exp = (PExp)$expression;
  LexLocation location = util.extractLexLocation(id.getLocation(),
                                            exp.getLocation());
  $$ = new AExpressionSingleDeclaration(location,
                                        NameScope.LOCAL,
                                        ids,
                                        exp);
}
| IDENTIFIER[id] COMMA singleExpressionDeclaration[decls]
{
  LexNameToken id = util.extractLexNameToken((CmlLexeme)$id);
  AExpressionSingleDeclaration decls = (AExpressionSingleDeclaration)$decls;
  decls.getIdentifiers().add(0, id);
  decls.setLocation(util.extractLexLocation(id.getLocation(),
                                       decls.getLocation()));
  $$ = decls;
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
  /* if (processParagraphList == null) // FIXME: surely this cannot possibly happen? -jwc */
  /*   processParagraphList = new Vector<PDefinition>(); */
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
| ACTIONS actionDefinitionList
{
  List<AActionDefinition> actionDefinitions = (List<AActionDefinition>)$2;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$1), util.extractLastLexLocation(actionDefinitions));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, loc);
  $$ = new AActionParagraphDefinition( loc, NameScope.LOCAL, false, access,null/*Pass*/, actionDefinitions);
}
| NAMESETS
{
  /* --- TODO --- */
}
| NAMESETS namesetDefList
{
  /* --- TODO --- */
}
;

actionDefinitionList :
  IDENTIFIER EQUALS paragraphAction
{
  $$ = util.caseFirstListElement(util.caseActionDefinition($IDENTIFIER, $EQUALS,$paragraphAction));
}
| actionDefinitionList[list] IDENTIFIER EQUALS paragraphAction
{
  $$ = util.caseNextListElement($list,util.caseActionDefinition($IDENTIFIER, $EQUALS,$paragraphAction));
}
;

/* Note that the expressions here are nameset expressions */
/* DEVIATION
 *
 * The whole structure of the nameset definitions (including the
 * keyword itself!) can be considered a deviation.
 */
namesetDefList :
  IDENTIFIER EQUALS expression
{
  /* --- TODO --- */
}
| namesetDefList IDENTIFIER EQUALS expression
{
  /* --- TODO --- */
}
;

paragraphAction :
  action
{
  $$ = new Object[]{new Vector<ATypeSingleDeclaration>(), $action};
}
| singleTypeDeclarationList AT action
{
  $$ = new Object[]{$singleTypeDeclarationList, $action};
}
;

action :
  CSPSKIP
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$1);
  $$ = new ASkipAction(location);
}
| CSPSTOP
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$1);
  $$ = new AStopAction(location);
}
| CSPCHAOS
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$1);
  $$ = new AChaosAction(location);
}
| CSPDIV
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$1);
  $$ = new ADivAction(location);
}
| CSPWAIT expression
{
  PExp exp = (PExp)$expression;
  LexLocation location = util.extractLexLocation((CmlLexeme)$CSPWAIT, exp.getLocation());
  $$ = new AWaitAction(location, exp);
}
| LPAREN[start] action[act] RPAREN[end]
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$start, (CmlLexeme)$end);
  PAction action = (PAction)$act;
  $$ = new ABlockStatementAction(location, null, action);
}
/* DEVIATION
 * CML_0:
 *   action: ...| communication '->' action
 *   communication: IDENTIFIER { communicationParameter }
 *   communicationParameter: '?' parameter | '?' parameter ':' expression | '!' expression | '.' expression
 * here:
 *   '[' expression ']' '->' action
 *
 * Need to verify that this matches all desired communication formats;
 * BANG and QUESTION are now integrated into expression
 */
| LSQUARE expression[comm] RSQUARE RARROW action[to]
{
  $$ = util.caseCommunicationAction($comm,(PAction)$to);
  
  /* need to merge in old action rule RARROW parser action and the old communication rule actions; along with an expression -> communication conversion?
   */
  //ACommunicationAction comAction = (ACommunicationAction)$communication;
  //PAction to = (PAction)$to;
  //LexLocation location = util.extractLexLocation(comAction.getLocation(), to.getLocation());
  // comAction.setAction(to);
  // comAction.setLocation(location);
  // $$ = comAction;
}
/* DEVIATION
 * CML_0:
 *   expression '&' action
 * here:
 *   LSQUARE expression RSQUARE AMP action
 *
 * A bare expression here conflicts with the dottedIdentifiers that
 * start off assignments, calls, and new statements.
 *
 * agreed: Alvaro & Joey, Skype, 30Jul2012
 */
| LSQUARE expression RSQUARE AMP action[after]
{
  PExp exp = (PExp)$expression;
  PAction action = (PAction)$after;
  LexLocation location = util.combineLexLocation(exp.getLocation(), action.getLocation());
  $$ = new AGuardedAction(location, exp, action);
}
| action SEMI action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ASequentialCompositionAction(location, left, right);
}
| action LRSQUARE action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new AExternalChoiceAction(location, left, right);
}
| action BARTILDEBAR action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new AInternalChoiceAction(location, left, right);
}
| action SLASHBACKSLASH action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
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
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ATimedInterruptAction(location, left, right, (PExp)$3);
}
| action LSQUAREGT action
{
  PAction left = (PAction)$1;
  PAction right = (PAction)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
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
  LexLocation location = util.combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new ATimeoutAction(location, left, right, (PExp)$3);
}
/* DEVIATION
 * CHANSET
 * grammar:
 *   action '\' chansetExpr
 * here:
 *   action '\\' expression
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
| action DBACKSLASH expression
{
  PAction left = (PAction)$1;
  PExp chansetExp = (PExp)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), chansetExp.getLocation());
  $$ = new AHidingAction(location, left, chansetExp);
}
| action STARTBY expression
{
  PAction left = (PAction)$1;
  PExp exp = (PExp)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), exp.getLocation());
  $$ = new AStartDeadlineAction(location, left, exp);
}
| action ENDSBY expression
{
  PAction left = (PAction)$1;
  PExp exp = (PExp)$3;
  LexLocation location = util.combineLexLocation(left.getLocation(), exp.getLocation());
  $$ = new AEndDeadlineAction(location, left, exp);
}
| action renameExpression
{
  SRenameChannelExp renameExpression = (SRenameChannelExp)$renameExpression;
  PAction action = (PAction)$1;
  $$ = new AChannelRenamingAction(util.combineLexLocation(action.getLocation(), renameExpression.getLocation()), action, renameExpression);
}
/* DEVIATION
 * grammar:
 *   MU identifier {',' identifier} '@' action {',' action}
 * here:
 *   MU expressionList '@' '(' actionList ')'
 */
| MU[start] expressionList AT LPAREN actionList RPAREN[end]
{
  $$ = util.caseMuAction($start,$expressionList,$actionList,$end);
}
/* NAMESET
 * expression was namesetExpr here
 */
| action LSQUAREDBAR expression BAR expression DBARRSQUARE action
{
  PAction leftAction = (PAction)$1;
  PAction rightAction = (PAction)$7;
  $$ = new AInterleavingParallelAction(util.extractLexLocation(leftAction.getLocation(),
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
  $$ = new AInterleavingParallelAction(util.extractLexLocation(leftAction.getLocation(),
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
  $$ = new ASynchronousParallelismParallelAction(util.extractLexLocation(leftAction.getLocation(),
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
  $$ = new ASynchronousParallelismParallelAction(util.extractLexLocation(leftAction.getLocation(),
                                                                    rightAction.getLocation()),
                                                 leftAction,
                                                 null,
                                                 null,
                                                 rightAction);
}
/* CHANSET NAMESET
 * expressions were namesetExpr|chansetExpr||chansetExpr|namesetExpr here
 */
| action LSQUARE expression BAR expression DBAR expression BAR expression RSQUARE action
{
    PAction leftAction = (PAction)$1;
    PAction rightAction = (PAction)$11;
    LexLocation location = util.extractLexLocation(leftAction.getLocation(),
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
  LexLocation location = util.extractLexLocation(leftAction.getLocation(),
                                            rightAction.getLocation());
  $$ = new AAlphabetisedParallelismParallelAction(location,
                                                  leftAction,
                                                  null,
                                                  null,
                                                  rightAction,
                                                  (PExp)$3,
                                                  (PExp)$5);
}
/* CHANSET NAMESET
 * expressions were namesetExpr|chansetExpr|namesetExpr here
 */
| action LSQUAREBAR expression BAR expression BAR expression BARRSQUARE action
{
  PAction leftAction = (PAction)$1;
  PAction rightAction = (PAction)$9;
  LexLocation location = util.extractLexLocation(leftAction.getLocation(),
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
  LexLocation location = util.extractLexLocation(leftAction.getLocation(),
                                            rightAction.getLocation());
  $$ = new AGeneralisedParallelismParallelAction(location,
                                                 leftAction,
                                                 null,
                                                 null,
                                                 rightAction,
                                                 (PExp)$3);
}
/* DEVIATION
 * CML_0:
 *   parametrisationList AT action
 * here:
 *   LPAREN parametrisationList AT action RPAREN
 */
| dottedIdentifier
{
  $$ = util.caseDottedIdentifierToRefAction($dottedIdentifier);
}
| LPAREN parametrisationList AT action[pAction] RPAREN
{
  $$ = new AParametrisedAction(util.extractLexLocation((CmlLexeme)$LPAREN,
                                                  (CmlLexeme)$RPAREN),
                               (List<PParametrisation>)$parametrisationList,
                               (PAction)$pAction);
}
| LPAREN[start] parametrisationList[params] AT action[pAction] RPAREN[pEnd] LPAREN expressionList[args] RPAREN[end]
{
  AParametrisedAction action = new AParametrisedAction(util.extractLexLocation((CmlLexeme)$start,
                                                                          (CmlLexeme)$pEnd),
                                                       (List<PParametrisation>)$params,
                                                       (PAction)$pAction);
  $$ = new AParametrisedInstantiatedAction(util.extractLexLocation((CmlLexeme)$start,
                                                              (CmlLexeme)$end),
                                           action,
                                           (List<PExp>)$args);
}
| LPAREN[start] singleTypeDeclarationList[decls] AT action[pAction] RPAREN LPAREN expressionList[args] RPAREN[end]
{
  $$ = new ADeclarationInstantiatedAction(util.extractLexLocation((CmlLexeme)$start, (CmlLexeme)$end),
                                          (List<? extends ATypeSingleDeclaration>)$decls,
                                          (PAction)$pAction,
                                          (List<PExp>)$args);
}
| SEMI replicationDeclaration[decl] AT action %prec U-SEMI
{
  PAction action = (PAction)$4;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,action.getLocation());
  $$ = new ASequentialCompositionReplicatedAction(location,
                                                  (List<SSingleDeclaration>)$decl,
                                                  action);
}
| LRSQUARE replicationDeclaration[decl] AT action %prec U-LRSQUARE
{
  PAction action = (PAction)$4;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,action.getLocation());
  $$ = new AExternalChoiceReplicatedAction(location,
                                           (List<SSingleDeclaration>)$decl,
                                           action);
}
| BARTILDEBAR replicationDeclaration[decl] AT action %prec U-BARTILDEBAR
{
  PAction action = (PAction)$4;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,action.getLocation());
  $$ = new AInternalChoiceReplicatedAction(location,
                                           (List<SSingleDeclaration>)$decl,
                                           action);
}
/* NAMESET
 * expression was namesetExpr here
 */
| LSQUAREDBAR expression DBARRSQUARE replicationDeclaration AT action %prec U-LSQUAREDBAR
{
  PAction replicatedAction = (PAction)$6;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,
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
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,
                                            replicatedAction.getLocation());
  $$ = new AInterleavingReplicatedAction(location,
                                         (List<SSingleDeclaration>)$replicationDeclaration,
                                         replicatedAction,
                                         (PExp)$expression);
}
/* CHANSET NAMESET
 * expressions were namesetExpr, chansetExpr here
 */
| LSQUAREBAR expression BARRSQUARE replicationDeclaration AT LSQUARE expression RSQUARE action %prec U-LSQUAREBAR
{
  PAction replicatedAction = (PAction)$9;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,
                                            replicatedAction.getLocation());
  $$ = new AGeneralisedParallelismReplicatedAction(location,
                                                   (List<SSingleDeclaration>)$replicationDeclaration,
                                                   replicatedAction,
                                                   (PExp)$2,
                                                   (PExp)$7);
}
/* CHANSET NAMESET
 * expressions were namesetExpr, chansetExpr here
 */
| DBAR replicationDeclaration AT LSQUARE expression BAR expression RSQUARE action %prec U-DBAR
{
  PAction replicatedAction = (PAction)$9;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,replicatedAction.getLocation());
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
  LexLocation location = util.extractLexLocation((CmlLexeme)$1,replicatedAction.getLocation());
  $$ = new ASynchronousParallelismReplicatedAction(location,
                                                   (List<? extends SSingleDeclaration>)$replicationDeclaration,
                                                   replicatedAction,
                                                   (PExp)$5);
}
/*
 * controlStatements
 */
| LET localDefList IN action[lAction] %prec S-LET
{
  PAction action = (PAction)$lAction;
  LexLocation location = util.extractLexLocation((CmlLexeme)$LET,
                                            action.getLocation());
  $$ = new ALetStatementAction(location,
                               action,
                               (List<? extends PDefinition>)$localDefList);
}
| LPAREN DCL assignmentDefList AT action[bAction] RPAREN
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$LPAREN, (CmlLexeme)$RPAREN);
  ADeclareStatementAction dclStm =
    new ADeclareStatementAction(util.extractLexLocation((CmlLexeme)$DCL,
                                                   (CmlLexeme)$AT),
                                (List<? extends PDefinition>)$assignmentDefList);
  PAction action = (PAction)$bAction;
  $$ = new ABlockStatementAction(location, dclStm, action);
}
| ifStatement
{
  $$ = $1;
}
| IF nonDeterministicAltList END
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$IF,(CmlLexeme)$END);
  List<ANonDeterministicAltStatementAction> alternatives =
    (List<ANonDeterministicAltStatementAction>)$nonDeterministicAltList;
  $$ = new ANonDeterministicIfStatementAction(location,
                                              alternatives);
}
| DO nonDeterministicAltList END %prec U-DO
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$DO,(CmlLexeme)$END);
  List<ANonDeterministicAltStatementAction> alternatives =
    (List<ANonDeterministicAltStatementAction>)$nonDeterministicAltList;
  $$ = new ANonDeterministicDoStatementAction(location,
                                              alternatives);
}
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
  $$ = $1;
}
| ATOMIC LPAREN assignStatementList RPAREN
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$ATOMIC,(CmlLexeme)$RPAREN);
  $$ = new AMultipleGeneralAssignmentStatementAction(location,
                                                     (List<? extends ASingleGeneralAssignmentStatementAction>)$assignStatementList);
}
/* call statements without assignment */
| dottedIdentifier LRPAREN
{
  $$ = util.caseDottedIdentifierLRPARENToCallAction($dottedIdentifier, $LRPAREN,new LinkedList<PExp>());
}
| dottedIdentifier LPAREN expressionList RPAREN
{
  $$ = util.caseDottedIdentifierLRPARENToCallAction($dottedIdentifier, $RPAREN,$expressionList);
}
| LSQUARE implicitOperationBody RSQUARE
{
  $$ = $implicitOperationBody;
}
/* DEVIATION
 * RETURN needs some sort of following value to avoid conflict with actionDefinitionList
 */
// | RETURN
// {
//   $$ = new AReturnStatementAction(util.extractLexLocation((CmlLexeme)$1), null);
// }
| RETURN LRPAREN
{
  $$ = new AReturnStatementAction(util.extractLexLocation((CmlLexeme)$1), null);
}
| RETURN LPAREN expression RPAREN
{
  PExp exp = (PExp)$expression;
  $$ = new AReturnStatementAction(util.extractLexLocation((CmlLexeme)$RETURN,
                                                     exp.getLocation()),
                                  exp);
}
/* DEVIATION
 * CML_0:
 *   stateDesignator ':=' 'new' name '(' { expression } ')'
 * here:
 *   expression COLONEQUALS NEW expression
 *
 * The expression production deals with ensuring we have something
 * that looks like a name + params; so we will have to add TC checks
 * here.
 */
| dottedIdentifier[stateDesignator] COLONEQUALS NEW expression[new]
{
  $$ = util.caseNewStatementAction($stateDesignator, $NEW, $new);
}
// --- FIXME delete this; in with above rule; here for reference until merged ---
// | path COLONEQUALS NEW path LPAREN expressionList RPAREN
// {
//   ANewStatementAction stm = null;
//   try {
//     Path statePath = (Path)$1;
//     Path namePath = (Path)$4;
//     List<? extends PExp> args = (List<? extends PExp>)$expressionList;
//     LexLocation location = util.extractLexLocation(statePath.location,(CmlLexeme)$RPAREN);
//     stm = new ANewStatementAction(location,
//                                          statePath.convertToStateDesignator(),
//                                          namePath.convertToName(),
//                                          args);
//   } catch(PathConvertException e) {
//     e.printStackTrace();
//     System.exit(-4);
//   }
//   $$ = stm;
// }
| casesStatement
{
  $$ = $1;
}
/* FIXME
 *
 * (JWC) The grammar allows reverse as a specific keyword to the for
 * loop, but reverse is also a unary expression operator.  I've no
 * idea what the semantic difference is.
 */
| FOR bind IN expression DO action[fAction]
{
  PAction action = (PAction)$fAction;
  LexLocation location = util.extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  PBind bind = (PBind)$bind;
  ADefPatternBind patternBind = new ADefPatternBind(bind.getLocation(),
                                                    null,
                                                    bind,
                                                    null,
                                                    null);
  $$ = new AForSequenceStatementAction(location,
                                       patternBind,
                                       (PExp)$expression,
                                       action);
}
// | FOR bind IN REVERSE expression DO action
| FOR pattern IN expression DO action[fAction]
{
  PAction action = (PAction)$fAction;
  LexLocation location = util.extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  PPattern pattern = (PPattern)$pattern;
  ADefPatternBind patternBind = new ADefPatternBind(pattern.getLocation(),
                                                    pattern,
                                                    null,
                                                    null,
                                                    null);
  $$ = new AForSequenceStatementAction(location,
                                       patternBind,
                                       (PExp)$expression,
                                       action);
}
// | FOR pattern IN REVERSE expression DO action */
| FOR ALL pattern INSET expression DO action[fAction]
{
  PAction action = (PAction)$fAction;
  LexLocation location = util.extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  $$ = new AForSetStatementAction(location,
                                  (PPattern)$pattern,
                                  (PExp)$expression,
                                  action);
}
| FOR IDENTIFIER EQUALS expression[start] TO expression[end] DO action[fAction]
{
  PAction action = (PAction)$fAction;
  LexLocation location = util.extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  $$ = new AForIndexStatementAction(location,
                                    util.extractLexNameToken($IDENTIFIER),
                                    (PExp)$start ,
                                    (PExp)$end,
                                    null,
                                    action);
}
| FOR IDENTIFIER EQUALS expression[start] TO expression[end] BY expression[step] DO action[fAction]
{
  PAction action = (PAction)$fAction;
  LexLocation location = util.extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  $$ = new AForIndexStatementAction(location,
                                    util.extractLexNameToken($IDENTIFIER),
                                    (PExp)$start ,
                                    (PExp)$end,
                                    (PExp)$step,
                                    action);
}
| WHILE expression DO action[wAction]
{
  PAction action = (PAction)$wAction;
  LexLocation location = util.extractLexLocation((CmlLexeme)$WHILE,action.getLocation());
  $$ = new AWhileStatementAction(location,
                                 (PExp)$expression,
                                 action);
}
/* This is the old way we managed to have bare calls in actions; we
 * now use the dottedIdentifier LPAREN... production
 */
//| expression
;

actionList :
  action
{
  List<PAction> actionList = new LinkedList<PAction>();
  actionList.add((PAction)$action);
  $$ = actionList;
}
| actionList[list] COMMA action
{
  List<PAction> actionList = (List<PAction>)$list;
  actionList.add((PAction)$action);
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
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)$singleTypeDeclaration;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$1, decl.getLocation());
  $$ = new AValParametrisation(loc, decl);
}
| RES singleTypeDeclaration
{
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)$singleTypeDeclaration;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$1, decl.getLocation());
  $$ = new AResParametrisation(loc, decl);
}
| VRES singleTypeDeclaration
{
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)$singleTypeDeclaration;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$1, decl.getLocation());
  $$ = new AVresParametrisation(loc, decl);
}
;

renameExpression :
  DLSQUARE renameList DRSQUARE
{
  $$ = new AEnumerationRenameChannelExp(util.extractLexLocation((CmlLexeme)$DLSQUARE,
                                                           (CmlLexeme)$DRSQUARE),
                                        (List<? extends ARenamePair>)$renameList);
}
| DLSQUARE expression[from] LARROW expression[to] BAR bindList DRSQUARE
{
  $$ = util.caseRenameExpressionAComprehensionRenameChannelExp($DLSQUARE,$from,$to,$bindList,null,$DRSQUARE);
}
| DLSQUARE expression[from] LARROW expression[to] BAR bindList AT expression[pred] DRSQUARE
{
  $$ = util.caseRenameExpressionAComprehensionRenameChannelExp($DLSQUARE,$from,$to,$bindList,$pred,$DRSQUARE);
}
;

/* DEVIATION
 * CML_0:
 *   renamingEnumeration: '[[' renamingPair { ',' renamingPair } ']]'
 *   renamingPair: IDENTIFIER { '.' expression } '<-' IDENTIFIER { '.' expression }
 * here:
 *   renamingPair: expression LARROW expression
 *
 * Note that path requires expressions in (...) but allows literals without.
 */
renameList :
  expression[from] LARROW expression[to]
{
  $$ = util.caseARenamePair($from,$to);
}
| renameList[list] COMMA expression[from] LARROW expression[to]
{
  $$ = util.caseRenameList($list,$from,$to);
}
;

/* DEVIATION
 * CML_0:
 *   doesn't exist as such
 * here:
 *   'channels', { channelDefinition, { ‘;’, channelDefinition } } [ ';' ]
 */
channelDefinition :
  CHANNELS
{
  List<AChannelNameDefinition> chanNameDecls = new Vector<AChannelNameDefinition>();
  LexLocation location = util.extractLexLocation((CmlLexeme)$CHANNELS);
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, location);
  AChannelParagraphDefinition channelDefinition =
    new AChannelParagraphDefinition(location,
                                    NameScope.GLOBAL,
                                    false,
                                    access,
                                    null,//Pass
                                    chanNameDecls);
  channelDefinition.setName(new LexNameToken("","",new LexLocation()));
  $$ = channelDefinition;
}
| CHANNELS channelDef
{
  List<AChannelNameDefinition> chanNameDecls = (List<AChannelNameDefinition>)$channelDef;
  LexLocation start = util.extractLexLocation((CmlLexeme)$1);
  LexLocation end = (chanNameDecls != null && chanNameDecls.size() > 0) ?
    chanNameDecls.get(chanNameDecls.size()-1).getLocation() : start;
  LexLocation location = util.combineLexLocation(start, end);
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, start);
  AChannelParagraphDefinition channelDefinition =
    new AChannelParagraphDefinition(location,
                                    NameScope.GLOBAL,
                                    false,
                                    access,
                                    null,//Pass
                                    chanNameDecls);
  channelDefinition.setName(new LexNameToken("","",new LexLocation()));
  $$ = channelDefinition;
}
;

channelDef :
  channelNameDecl
{
  List<AChannelNameDefinition> decls = new Vector<AChannelNameDefinition>();
  decls.add((AChannelNameDefinition)$channelNameDecl);
  $$ = decls;
}
| channelDef[def] channelNameDecl
{
  List<AChannelNameDefinition> decls = (List<AChannelNameDefinition>)$def;
  decls.add((AChannelNameDefinition)$channelNameDecl);
  $$ = decls;
}
;

channelNameDecl :
/* DEVIATION
 * grammar:
 *   identifierList
 * here:
 *   IDENTIFIER
 * So, it's not possible to have a comma-separated list of untyped channel names
 * FIXME we could, however, eliminate the SEMI separator entirely
 */
  IDENTIFIER[decl]
{
  /* --- TODO --- */
  /* I (jwc) have no idea if this is correct.  When did
   * AChannelNameDefinition become AChannelNameDeclaration?
   */
  LexIdentifierToken decl = util.extractLexIdentifierToken((CmlLexeme)$decl);
  LexLocation location = decl.getLocation();
  List<LexIdentifierToken> ids = new LinkedList<LexIdentifierToken>();
  ids.add(decl);
  ATypeSingleDeclaration singleTypeDeclaration =
    new ATypeSingleDeclaration(location, NameScope.GLOBAL, ids, null);
  AChannelNameDefinition channelNameDecl =
    new AChannelNameDefinition(location,
                                NameScope.GLOBAL,
                                false, 
                                null, 
                                null, 
                                singleTypeDeclaration);
  channelNameDecl.setName(new LexNameToken("", decl));
  $$ = channelNameDecl;
}
| singleTypeDeclaration[decl]
{
  /* --- TODO --- */
  /* I (jwc) have no idea if this is correct.  When did
   * AChannelNameDefinition become AChannelNameDeclaration?
   */
  ATypeSingleDeclaration singleTypeDeclaration = (ATypeSingleDeclaration)$decl;
  AChannelNameDefinition channelNameDecl =
   new AChannelNameDefinition(singleTypeDeclaration.getLocation(),
                               NameScope.GLOBAL,
                               false,
                               null,
                               null,
                               singleTypeDeclaration);
   LexNameToken name = new LexNameToken("",singleTypeDeclaration.getIdentifiers().get(0));
   channelNameDecl.setName(name);
  $$ = channelNameDecl;
};

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
| singleTypeDeclarationList[list] singleTypeDeclaration
{
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)$list;
  decls.add((ATypeSingleDeclaration)$singleTypeDeclaration);
  $$ = decls;
}
;

singleTypeDeclaration :
  IDENTIFIER[id] COLON type
{
  LexIdentifierToken id = util.extractLexIdentifierToken((CmlLexeme)$id);
  LexLocation location = id.getLocation();
  List<LexIdentifierToken> ids = new LinkedList<LexIdentifierToken>();
  ids.add(id);
  ATypeSingleDeclaration singleTypeDeclaration =
    new ATypeSingleDeclaration(location,
                               NameScope.LOCAL,
                               ids,
                               (PType)$type);
  $$ = singleTypeDeclaration;
}
| IDENTIFIER[id] COMMA singleTypeDeclaration[decl]
{
  LexIdentifierToken id = util.extractLexIdentifierToken((CmlLexeme)$id);
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)$decl;
  decl.setLocation(util.combineLexLocation(id.getLocation(), decl.getLocation()));
  decl.getIdentifiers().addFirst(id);
  $$ = decl;
};

chansetDefinitionParagraph :
  CHANSETS
{
  LexLocation loc = util.extractLexLocation((CmlLexeme)$CHANSETS);
  AAccessSpecifierAccessSpecifier access =
    new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                        new TStatic(),
                                        new TAsync());
  AChansetParagraphDefinition chansetParagraph =
    new AChansetParagraphDefinition(loc,
                                    NameScope.GLOBAL,
                                    false,
                                    access,
                                    null/*Pass*/,
                                    new LinkedList<AChansetDefinition>());
  chansetParagraph.setName(util.extractLexNameToken((CmlLexeme)$1));
  $$ = chansetParagraph;
}
| CHANSETS chansetDefinitionList[list]
{
  LexLocation loc = util.extractLexLocation((CmlLexeme)$CHANSETS);
  List<AChansetDefinition> chansetDefinitions = (List<AChansetDefinition>)$list;
  AAccessSpecifierAccessSpecifier access =
    new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                        new TStatic(),
                                        new TAsync());
  AChansetParagraphDefinition chansetParagraph =
    new AChansetParagraphDefinition(loc,
                                    NameScope.GLOBAL,
                                    false,
                                    access,
                                    null/*Pass*/,
                                    chansetDefinitions);
  chansetParagraph.setName(util.extractLexNameToken((CmlLexeme)$1));
  $$ = chansetParagraph;
}
;

chansetDefinitionList :
  chansetDefinition[def]
{
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)$def);
  $$ = defs;
}
| chansetDefinitionList[list] SEMI chansetDefinition[def]
{
  List<AChansetDefinition> defs = (List<AChansetDefinition>)$list;
  defs.add((AChansetDefinition)$def);
  $$ = defs;
}
;

chansetDefinition :
/* CHANSET
 * expression was chansetExpr here
 */
  IDENTIFIER[id] EQUALS expression[exp]
{
  LexIdentifierToken id = util.extractLexIdentifierToken((CmlLexeme)$id);
  PExp exp = (PExp)$exp;
  LexLocation location = util.combineLexLocation(id.getLocation(), exp.getLocation());
  $$ = new AChansetDefinition(location,
                              NameScope.GLOBAL,
                              false/*used*/,
                              null/*access*/,
                              null/*Pass*/,
                              id,
                              exp);
}
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
  classDefinitionBlockAlternative[alt]
{
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)$alt);
  $$ = defs;
}
| classDefinitionBlockAlternative[alt] classDefinitionBlock[defs]
{
  List<PDefinition> defs = (List<PDefinition>)$defs;
  defs.add((PDefinition)$alt);
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
  LexLocation location = util.extractLexLocation((CmlLexeme)$INITIAL,def.getLocation());
  AInitialParagraphDefinition initDef = new AInitialParagraphDefinition(location,
                                       NameScope.GLOBAL,
                                       true,
                                       util.getDefaultAccessSpecifier(false,false,null),
                                       null/*Pass*/,
                                       def);
  initDef.setName(new LexNameToken("", new LexIdentifierToken("Initialiser", false, location)));
  $$ = initDef;                 
}
;

typeDefs :
  TYPES
{
  LexLocation loc = util.extractLexLocation((CmlLexeme)$TYPES);
  ATypesParagraphDefinition typesDef = new ATypesParagraphDefinition(loc,
                                     NameScope.LOCAL,
                                     false,
                                     util.getDefaultAccessSpecifier(true, false, loc),
                                     null/*Pass*/,
                                     null);
  typesDef.setName(util.extractLexNameToken((CmlLexeme)$1));
  $$ = typesDef;
  
}
| TYPES typeDefList
{
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)$typeDefList;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$TYPES),
                                       util.extractLastLexLocation(typeDefinitions));
  ATypesParagraphDefinition typesDef = new ATypesParagraphDefinition(loc,
                                     NameScope.LOCAL,
                                     false,
                                     util.getDefaultAccessSpecifier(true, false, loc),
                                     null/*Pass*/,
                                     typeDefinitions);
  typesDef.setName(util.extractLexNameToken((CmlLexeme)$1));
  $$ = typesDef;
}
| TYPES typeDefList SEMI
{
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)$typeDefList;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$TYPES),
                                       util.extractLexLocation((CmlLexeme)$SEMI));
  ATypesParagraphDefinition typesDef =new ATypesParagraphDefinition(loc,
                                     NameScope.LOCAL,
                                     false,
                                     util.getDefaultAccessSpecifier(true, false, loc),
                                     null/*Pass*/,
                                     typeDefinitions);
  typesDef.setName(util.extractLexNameToken((CmlLexeme)$1));
  $$ = typesDef;
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
  qualifier IDENTIFIER[id] EQUALS type
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qualifier;
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$id);
  LexLocation location = null;
  location = util.combineLexLocation(name.getLocation(), ((PType)$type).getLocation());
  PType type = (PType)$4;
  ANamedInvariantType invType = 
    AstFactory.newANamedInvariantType(name, type);
  $$ = new ATypeDefinition(location,
                           NameScope.TYPENAME,
                           false/*Boolean used_*/,
                           null/*VDM ClassDef*/,
                           access,
                           null,
                           null/*Pass*/,
                           invType/*SInvariantType invType_*/,
                           null/*PPattern invPattern_*/,
                           null/*PExp invExpression_*/,
                           null /*AExplicitFunctionDefinition invdef_*/,
                           false/*Boolean infinite_*/,
                           name);
}
| qualifier IDENTIFIER[id] EQUALS type invariant
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qualifier;
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$id);
  AInvariantDefinition inv = (AInvariantDefinition)$invariant;
  LexLocation location = null;
  location = util.combineLexLocation(name.getLocation(), inv.getLocation());
  PType type = (PType)$4;
  ANamedInvariantType invType = 
    AstFactory.newANamedInvariantType(name, type);

  $$ = new ATypeDefinition(location,
                           NameScope.TYPENAME,
                           false/*Boolean used_*/,
                           null/*VDM ClassDef*/,
                           access,
                           null,
                           null/*Pass*/,
                           invType,/*SInvariantType invType_*/
                           inv.getPattern()/*PPattern invPattern_*/,
                           inv.getExpression()/*PExp invExpression_*/,
                           null /*AExplicitFunctionDefinition invdef_*/,
                           false/*Boolean infinite_*/,
                           name);
}
| qualifier IDENTIFIER[id] DCOLON fieldList
{
  $$ = util.caseRecordTypeDefinition($qualifier, $id, $fieldList);
}
| qualifier IDENTIFIER[id] DCOLON fieldList invariant
{
  /* --- TODO --- */
  /* Not sure why, but we're not actually using the invariant
   * definition here.
   */
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qualifier;
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$id);
  CmlLexeme vdmrec = (CmlLexeme)$DCOLON;
  List<AFieldField> fields = (List<AFieldField>)$fieldList;
  AInvariantDefinition inv = (AInvariantDefinition)$invariant;
  // TODO: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.
  LexLocation loc = util.combineLexLocation(name.getLocation(), util.extractLexLocation(vdmrec));
  ARecordInvariantType recType =  AstFactory.newARecordInvariantType(name, fields);
  PPattern invPattern = null;
  PExp invExp = null;
  if (inv != null)
  {
     invPattern = inv.getPattern();
     invExp = inv.getExpression();
  }
  // AstFactory.newATypeDefinition(name, recType, invPattern, invExp);                                                          
  ATypeDefinition tdef = AstFactory.newATypeDefinition(name,recType,invPattern, invExp);
  $$ = tdef;
}
;

/* FUTURE
 *
 * (jwc) I think for this we really ought have the lexer give the
 * token 'QUALIFIER' and then querying its value (like with numeric
 * literals) to figure out which one we have.
 *
 * FIXME the AAccessSpecifierAccessSpecifier class should have a location field
 */
qualifier :
  PRIVATE
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$1);
  $$ = new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                           null,
                                           null);//, location);
}
| PROTECTED
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$1);
  $$ = new AAccessSpecifierAccessSpecifier(new AProtectedAccess(),
                                           null,
                                           null);//, location);
}
| PUBLIC
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$1);
  $$ = new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                           null,
                                           null);//, location);
}
| LOGICAL
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$1);
  $$ = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(),
                                           null,
                                           null);//, location);
}
| /* empty */
{
  /*Default private*/
  $$ = new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                           null,
                                           null);//, location);
}
;

type :
  LPAREN[start] type[t] RPAREN[end] // bracketedType
{
  PType type = (PType)$t;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$start,(CmlLexeme)$end);
  type.setLocation(loc);
  $$ = type;
}
| basicType
{
  $$ = $basicType;
}
| quoteLiteral // quoteType
{
  LexQuoteToken quote = (LexQuoteToken)$quoteLiteral;
  $$ = new AQuoteType(quote.location, false, null, quote);
}
| COMPOSE[start] IDENTIFIER[id] OF fieldList END[end]
{
  List<AFieldField> fields = (List<AFieldField>)$fieldList;
  LexLocation location = util.extractLexLocation((CmlLexeme)$1);
  $$ = AstFactory.newARecordInvariantType(location, fields);
}
| type[first] BAR type[second] // unionType
{
  PType first = (PType)$first;
  PType second = (PType)$second;
  LexLocation loc = util.combineLexLocation(first.getLocation(), second.getLocation());
  AUnionType utype;
  LinkedList<PType> types;
  if (first instanceof AUnionType) {
    // First is a UnionType
    utype = (AUnionType)first;
    types = utype.getTypes();
    if (second instanceof AUnionType) {
      // Second is also a union type, so merge the type lists
      types.addAll(((AUnionType)second).getTypes());
    } else {
      // Second is not a union type, so just add it to the type list
      types.add(second);
    }
  } else if (second instanceof AUnionType) {
    // First isn't union, but Second is, so just add first to the type list
    utype = (AUnionType)second;
    types = utype.getTypes();
    types.add(first);
  } else {
    // Neither is a union type
    types = new LinkedList<PType>();
    types.add(first);
    types.add(second);
    utype = new AUnionType(loc, false, false, false);
    utype.setTypes(types);
  }
  utype.setLocation(loc);
  $$ = utype;
}
| type[first] STAR type[second] %prec T-STAR //productType
{
  PType first = (PType)$first;
  PType second = (PType)$second;
  LexLocation loc = util.combineLexLocation(first.getLocation(), second.getLocation());
  AProductType ptype;
  LinkedList<PType> types;
  if (first instanceof AProductType) {
    ptype = (AProductType)first;
    types = ptype.getTypes();
    if (second instanceof AProductType) {
      types.addAll(((AProductType)second).getTypes());
    } else {
      types.add(second);
    }
  } else if (second instanceof AProductType) {
    ptype = (AProductType)second;
    types = ptype.getTypes();
    types.add(first);
  } else {
    types = new LinkedList<PType>();
    types.add(first);
    types.add(second);
    ptype = new AProductType(loc, false, null, types);
  }
  ptype.setLocation(loc);
  $$ = ptype;
}
| LSQUARE[start] type[otype] RSQUARE[end] // optionalType
{
  $$ = new AOptionalType(util.extractLexLocation((CmlLexeme)$start, (CmlLexeme)$end),
                         false,
                         null,
                         (PType)$otype);
}
| SETOF[prefix] type[stype]
{
  PType type = (PType)$stype;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$prefix),
                                       type.getLocation());
  $$ = new ASetType(loc, false, null, type, false, false);
}
| SEQOF[prefix] type[stype]
{
  PType type = (PType)$stype;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$prefix),
                                       type.getLocation());
  $$ = new ASeqSeqType(loc, false, null, type, false);
}
| SEQ1OF[prefix] type[stype]
{
  PType type = (PType)$stype;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$prefix),
                                       type.getLocation());
  $$ = new ASeq1SeqType(loc, false, null, type, false);
}
| MAPOF[prefix] type[dom] TO type[rng]
{
  PType dom = (PType)$dom;
  PType rng = (PType)$rng;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$prefix),
                                       rng.getLocation());
  $$ = new AMapMapType(loc, false, null, dom, rng, false);
}
| INMAPOF[prefix] type[dom] TO type[rng]
{
  PType dom = (PType)$dom;
  PType rng = (PType)$rng;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$prefix),
                                       rng.getLocation());
  $$ = new AMapMapType(loc, false, null, dom, rng, false);
}
| functionType
{
  $$ = $functionType;
}
/* DEVIATION (x3)
 * CML_0:
 *   name
 * here:
 *   dottedIdentifier
 *
 * note that 'dottedIdentifier' includes backtick
 */
| dottedIdentifier
{
  $$ = util.caseDottedIdentifierToNamedType($dottedIdentifier);
}
;

/* dottedIdentifier is used in many contexts; possibly we just want to
 * return a List<LexIdentifierToken> and convert at place of use.
 * Also need to check that we're not losing location info.
 */
dottedIdentifier :
  IDENTIFIER[id]
{
  List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
  ids.add(util.extractLexIdentifierToken((CmlLexeme)$id));
  $$ = ids;
}
| dottedIdentifier[ids] DOT IDENTIFIER[id]
{
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)$ids;
  ids.add(util.extractLexIdentifierToken((CmlLexeme)$id));
  $$ = ids;
}
| dottedIdentifier[ids] BACKTICK IDENTIFIER[id]
{
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)$ids;
  ids.add(util.extractLexIdentifierToken((CmlLexeme)$id));
  $$ = ids;
}
;

basicType :
  TBOOL
{
  $$ = new ABooleanBasicType(util.extractLexLocation((CmlLexeme)$1), false);
}
| TNAT
{
  $$ = new ANatNumericBasicType(util.extractLexLocation((CmlLexeme)$1), false);
}
| TNAT1
{
  $$ = new ANatOneNumericBasicType(util.extractLexLocation((CmlLexeme)$1), false);
}
| TINT
{
  $$ = new AIntNumericBasicType(util.extractLexLocation((CmlLexeme)$1), false);
}
| TRAT
{
  $$ = new ARationalNumericBasicType(util.extractLexLocation((CmlLexeme)$1), false);
}
| TREAL
{
  $$ = new ARealNumericBasicType(util.extractLexLocation((CmlLexeme)$1), false);
}
| TCHAR
{
  $$ = new ACharBasicType(util.extractLexLocation((CmlLexeme)$1), false);
}
| TTOKEN
{
  $$ = new ATokenBasicType(util.extractLexLocation((CmlLexeme)$1), false);
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
  type[dom] RARROW type[rng]
{
  PType domType = (PType)$dom;
  PType rngType = (PType)$rng;
  LexLocation loc = util.combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, true, params, rngType);
}
| LRPAREN[dom] RARROW type[rng] // discretionary type
{
  PType domType = new AVoidType(util.extractLexLocation((CmlLexeme)$dom), true);
  PType rngType = (PType)$rng;
  LexLocation loc = util.combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, true, params, rngType);
}
;

totalFunctionType :
  type[dom] PLUSGT type[rng]
{
  PType domType = (PType)$dom;
  PType rngType = (PType)$rng;
  LexLocation loc = util.combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, false, params, rngType);
}
| LRPAREN[dom] PLUSGT type[rng] // discretionary type
{
  PType domType = new AVoidType(util.extractLexLocation((CmlLexeme)$dom), true);
  PType rngType = (PType)$rng;
  LexLocation loc = util.combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, false, params, rngType);
}
;

fieldList :
  field
{
  List<AFieldField> res = new LinkedList<AFieldField>();
  res.add((AFieldField)$field);
  $$ = res;
}
| fieldList[list] field
{
  List<AFieldField> res = (List<AFieldField>)$list;
  res.add((AFieldField)$field);
  $$ = res;
}
;

field :
  type
{
  PType type = (PType)$1;
  String tag = "";
  LexNameToken tagname = new LexNameToken("", new LexIdentifierToken(tag,false,type.getLocation()));
  AFieldField res = AstFactory.newAFieldField(tagname, tag, type, false);
}
| IDENTIFIER[id] COLON type
{
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$id);
  PType type = (PType)$type;
  $$ = new AFieldField(util.getDefaultAccessSpecifier(false,false,null),
                       name,
                       name.getName(),
                       type,
                       false);
}
| IDENTIFIER[id] COLONDASH type
{
    LexNameToken name = util.extractLexNameToken((CmlLexeme)$id);
    PType type = (PType)$type;
    $$ = new AFieldField(util.getDefaultAccessSpecifier(false,false,null),
                         name,
                         null,
                         type,
                         true);
}
;

invariant :
  INV pattern DEQUALS expression
{
  PExp exp = (PExp)$expression;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$INV, exp.getLocation());
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, true, loc);
  $$ = new AInvariantDefinition(loc,
                                null, // decided later
                                NameScope.LOCAL,
                                false,
                                null, // VDM ClassDef
                                access,
                                null, // decided later
                                null, // Pass
                                (PPattern)$pattern,
                                exp);
}
;


/* DEVIATION
 * CML_0:
 *   'values', qualifiedValueDef, { ‘;’, qualifiedValueDef }
 * here:
 *   'values', { qualifiedValueDef, { ‘;’, qualifiedValueDef } } [ ';' ]
 *
 * *** Actually semicolons are eliminated for now.
 */
valueDefs :
  VALUES
{
  List<PDefinition> defs = new Vector<PDefinition>();
  LexLocation location = util.extractLexLocation((CmlLexeme)$VALUES);
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, location);
  $$ = new AValueParagraphDefinition(location,
                                     NameScope.NAMES,
                                     false,
                                     access,
                                     null, // Pass
                                     defs);
}
| VALUES valueDefList
{
  List<PDefinition> defs = (List<PDefinition>)$valueDefList;
  LexLocation location = util.extractLexLocation((CmlLexeme)$VALUES,
                                            util.extractLastLexLocation(defs));
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true,
                                                                     false,
                                                                     location);
  $$ = new AValueParagraphDefinition(location,
                                     NameScope.NAMES,
                                     false,
                                     access,
                                     null,//Pass
                                     defs);
}
//| VALUES valueDefList SEMI
//{
//  List<PDefinition> defs = (List<PDefinition>)$valueDefList;
//  LexLocation location = util.extractLexLocation((CmlLexeme)$VALUES, (CmlLexeme)$SEMI);
//  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, location);
//  $$ = new AValueParagraphDefinition(location,
//                                     NameScope.NAMES,
//                                     false,
//                                     access,
//                                   null,//Pass
//                                     defs);
//}
;

valueDefList :
  qualifiedValueDef[def]
{
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)$def);
  $$ = defs;
}
| valueDefList[list] SEMI qualifiedValueDef[def]
{
  PDefinition def = (PDefinition)$def;
  List<PDefinition> defs = (List<PDefinition>)$list;
  defs.add(def);
  $$ = defs;
}
;

qualifiedValueDef :
  qualifier valueDef
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qualifier;
  PDefinition def = (PDefinition)$valueDef;
  def.setAccess(access);
  $$ = def;
}
;

valueDef :
  IDENTIFIER[id] EQUALS expression
{
  PExp expression = (PExp)$expression;
  LexNameToken lnt = util.extractLexNameToken((CmlLexeme)$id);
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,
                                                  null,
                                                  false,
                                                  lnt,
                                                  false/*constrained*/);
  AValueDefinition vdef =
    new AValueDefinition(util.combineLexLocation(idp.getLocation(),
                                            expression.getLocation()),
                            NameScope.LOCAL,
                            false, // used
                            util.getPrivateAccessSpecifier(false, false, lnt.location),
                            null, // pass
                            idp,
                            expression,
                            null // defs
                            );
  vdef.setName(lnt);
  vdef.setType(null);
  $$ = vdef;
}
| IDENTIFIER[id] COLON type EQUALS expression
{
  LexNameToken lnt = util.extractLexNameToken((CmlLexeme)$id);
  PType type = (PType)$type;
  PExp expression = (PExp)$expression;
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,
                                                  null,
                                                  false,
                                                  lnt,
                                                  false/*constrained*/);
  // Build the resulting AValueDefinition
  AValueDefinition vdef =
    new AValueDefinition(util.combineLexLocation(idp.getLocation(),
                                            expression.getLocation()),
                         NameScope.LOCAL,
                         false, // used
                         util.getPrivateAccessSpecifier(false, false, lnt.location),
                         null, // pass
                         idp,
                         expression,
                         null // defs
                         );
  vdef.setName(lnt);
  vdef.setType(type);
  $$ = vdef;
}
| patternLessID[pat] EQUALS expression
{
  PPattern pattern = (PPattern)$pat;
  PExp expression = (PExp)$expression;
  LexLocation loc = util.combineLexLocation(pattern.getLocation(),
                                       expression.getLocation());
  AValueDefinition vdef =
    new AValueDefinition(loc,
                         NameScope.LOCAL,
                         false, // used
                         util.getPrivateAccessSpecifier(false, false, loc),
                         null, // pass
                         pattern,
                         expression,
                         null // defs
                         );
  vdef.setName(new LexNameToken("", new LexIdentifierToken("pattern", false, loc)));
  vdef.setType(new AUnknownType(loc, true));
  $$ = vdef;
}
| patternLessID[pat] COLON type EQUALS expression
{
  PPattern pattern = (PPattern)$pat;
  PType type = (PType)$type;
  PExp expression = (PExp)$expression;
  LexLocation loc = util.combineLexLocation(pattern.getLocation(),
                                       expression.getLocation());
  AValueDefinition vdef =
    new AValueDefinition(loc,
                         NameScope.LOCAL,
                         false, // used
                         util.getPrivateAccessSpecifier(false, false, loc),
                         null, // pass
                         pattern,
                         expression,
                         null // defs
                         );
  // FIXE wtf.setName -- esp. w.r.t. previous action
  vdef.setName(new LexNameToken("Default", "plesstypeexp", loc, false, false));
  vdef.setType(type);
  $$ = vdef;
}
;

functionDefs :
  FUNCTIONS
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$FUNCTIONS);
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true,
                                                                     false,
                                                                     location);
  List<PDefinition> functionDefs = new LinkedList<PDefinition>();
  $$ = new AFunctionParagraphDefinition(location,
                                        NameScope.GLOBAL,
                                        false,
                                        access,
                                        null,//Pass
                                        functionDefs);
}
| FUNCTIONS functionDefList
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$FUNCTIONS);
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true,
                                                                     false,
                                                                     location);
  List<PDefinition> functionDefs = (List<PDefinition>)$functionDefList;
  $$ = new AFunctionParagraphDefinition(location,
                                        NameScope.GLOBAL,
                                        false,
                                        access,
                                        null,//Pass
                                        functionDefs);
}
//| FUNCTIONS functionDefList SEMI
//{
//  LexLocation location = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$FUNCTIONS), util.extractLexLocation((CmlLexeme)$SEMI));
//  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true, false, location);
//  List<PDefinition> functionDefs = (List<PDefinition>)$functionDefList;
//  $$ = new AFunctionParagraphDefinition(location,
//                                        NameScope.GLOBAL,
//                                        false,
//                                        access,
//                                      null,//Pass
//                                        functionDefs);
//}
;

functionDefList :
  functionDef
{
  List<PDefinition> functionList = new Vector<PDefinition>();
  functionList.add((PDefinition)$functionDef);
  $$ = functionList;
}
| functionDefList[list] functionDef
{
  List<PDefinition> functionList = (List<PDefinition>)$list;
  functionList.add((PDefinition)$functionDef);
  $$ = functionList;
}
;

functionDef :
  qualifier[qual] IDENTIFIER[id] parameterTypes[ptypes] identifierTypePairList[retvals] preExpr_opt[pre] postExpr[post]
{
  $$ = util.caseImplicitFunctionDefinition($qual,$id,$ptypes,$retvals,$pre,$post);
}
| qualifier[qual] explicitFunctionDef[def]
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qual;
  AExplicitFunctionDefinition func = (AExplicitFunctionDefinition)$def;
  func.setAccess(access);
  $$ = func;
}
;

explicitFunctionDef :
  IDENTIFIER[id] COLON functionType IDENTIFIER[checkId] parameterList DEQUALS functionBody preExpr_opt postExpr_opt measureExpr
{
  $$ = util.caseExplicitFunctionDefinition($id,$functionType,$checkId,$parameterList, 
					   $functionBody,$preExpr_opt,$postExpr_opt,$measureExpr);
}
;



/* parameterList : */
/*   LRPAREN */
/* { */
/*   $$ =  new Vector<List<PPattern>>(); */
/* } */
/* | LPAREN patternList[patList] RPAREN */
/* { */
/*   List<PPattern> patternList = (List<PPattern>)$patList; */
/*   List<List<PPattern>> paramList = new Vector<List<PPattern>>(); */
/*   paramList.add(patternList); */
/*   $$ = paramList; */
/* } */
/* | parameterList[paramList] LPAREN patternList[patList] RPAREN */
/* { */
/*   List<PPattern> patternList = (List<PPattern>)$patList; */
/*   List<List<PPattern>> paramList = (List<List<PPattern>>)$paramList; */
/*   paramList.add(patternList); */
/*   $$ = paramList; */
/* } */
/* ; */

parameterList :
  parameters[params]
{
  List<List<PPattern>> paramsList = new LinkedList<List<PPattern>>();
  paramsList.add((List<PPattern>)$params);
  $$ = paramsList;
}
| parameterList[paramsList] parameters[params]
{
  List<List<PPattern>> paramsList = (List<List<PPattern>>)$paramsList;
  paramsList.add((List<PPattern>)$params);
  $$ = paramsList;
}
;

parameters :
  LRPAREN
{
  $$ =  new LinkedList<PPattern>();
}
| LPAREN patternList[patList] RPAREN
{
  $$ = $patList;
}
;

functionBody :
  expression
{
  $$ = $1;
}
| SUBCLASSRESP
{
  $$ = new ASubclassResponsibilityExp(util.extractLexLocation((CmlLexeme)$1));
}
| NOTYETSPEC
{
  $$ = new ANotYetSpecifiedExp(util.extractLexLocation((CmlLexeme)$1));
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
  patternList[patList] COLON type
{
  List<PPattern> patternList = (List<PPattern>)$patList;
  List<APatternListTypePair> pltpl = new Vector<APatternListTypePair>();
  pltpl.add(new APatternListTypePair(false, patternList, (PType)$type));
  $$ = pltpl;
}
| patternListTypeList[pltList] COMMA patternList[patList] COLON type
{
  List<APatternListTypePair> pltpl = (List<APatternListTypePair>)$pltList;
  List<PPattern> patternList = (List<PPattern>)$patList;
  pltpl.add(new APatternListTypePair(false, patternList, (PType)$type));
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
  IDENTIFIER[id] COLON type
{
  List<APatternTypePair> typePairs = new LinkedList<APatternTypePair>();
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$id);
  APatternTypePair typePair = 
      new APatternTypePair(false,
			   new AIdentifierPattern(name.getLocation(),
						  new LinkedList<PDefinition>(),
						  false,
						  name,
						  false /*FIXME what is constrained_??*/),
			   (PType)$type);
  typePairs.add(typePair);
  $$ = typePairs;
}
| identifierTypePairList[idtpList] COMMA IDENTIFIER[id] COLON type
{
  List<APatternTypePair> typePairs = (List<APatternTypePair>)$idtpList;
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$id);
  APatternTypePair typePair =
      new APatternTypePair(null,
			   new AIdentifierPattern(name.getLocation(),
						  new LinkedList<PDefinition>(),
						  false,
						  name,
						  false /*FIXME what is constrained_??*/),
			   (PType)$type);
  typePairs.add(typePair);
  $$ = typePairs;
}
;

preExpr_opt :
  preExpr                       { $$ = $1; }
| /* empty */                   { $$ = null; }
;

preExpr :
  PRE expression                { $$ = $2; }
;

postExpr_opt :
  postExpr                      { $$ = $1; }
| /* empty */                   { $$ = null; }
;

postExpr :
  POST expression                { $$ = $2; }
;

measureExpr :
/* DEVIATION
 * CML_0:
 *   MEASURE name
 * here:
 *   MEASURE dottedIdentifier
 */
  MEASURE dottedIdentifier
{
  $$ = util.caseMeasure($dottedIdentifier);
}
| /* empty */
{
  $$ = null;
}
;

operationDefs :
  OPERATIONS
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$OPERATIONS);
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true,
                                                                     false,
                                                                     location);
  $$ = new AOperationParagraphDefinition(location,
                                         NameScope.LOCAL,
                                         false,
                                         access,
                                         null,//Pass
                                         null);
}
| OPERATIONS operationDefList
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$OPERATIONS);
  AAccessSpecifierAccessSpecifier access = util.getDefaultAccessSpecifier(true,
                                                                     false,
                                                                     location);
  List<? extends SOperationDefinition> opDefinitions =
    (List<? extends SOperationDefinition>)$operationDefList;
  $$ = new AOperationParagraphDefinition(location,
                                         NameScope.LOCAL,
                                         false,
                                         access,
                                         null,//Pass
                                         opDefinitions);
}
;

/* DEVIATION
 * require *no* separator between operations
 */
operationDefList :
  operationDef
{
  List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>();
  opDefinitions.add((SOperationDefinition)$operationDef);
  $$ = opDefinitions;
}
| operationDefList[list] operationDef
{
  List<SOperationDefinition> opDefinitions = (List<SOperationDefinition>)$list;
  opDefinitions.add((SOperationDefinition)$operationDef);
  $$ = opDefinitions;
}
;

operationDef :
  qualifier[qual] IDENTIFIER[id] parameterTypes[ptypes] identifierTypePairList_opt[retvals] externals_opt[exts] preExpr_opt[pre] postExpr[post]
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qual;
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$id);
  List<? extends APatternListTypePair> parameterPatterns =
    (List<? extends APatternListTypePair>)$ptypes;
   List<? extends APatternTypePair> result =
     (List<? extends APatternTypePair>)$retvals;
  List<? extends AExternalClause> externals = (List<? extends AExternalClause>)$exts;
  PExp precondition = (PExp)$pre;
  PExp postcondition = (PExp)$post;
  LexLocation location = util.combineLexLocation(name.location, postcondition.getLocation());
  eu.compassresearch.ast.definitions.AImplicitOperationDefinition res =
    new eu.compassresearch.ast.definitions.AImplicitOperationDefinition(location,
                                                                        NameScope.GLOBAL,
                                     false,
                                     access,
                                     null,//Pass
                                     parameterPatterns,
                                     result,
                                     externals,
                                     precondition,
                                     postcondition,
                                     null, null);
  res.setName(name);
  $$ = res;
}
| qualifier[qual] IDENTIFIER[id] COLON operationType[opType] IDENTIFIER[checkId] parameters[params] DEQUALS operationBody[body] preExpr_opt[pre] postExpr_opt[post]
{
  $$ = util.caseExplicitOperationDefinition($qual,$id,$COLON,$opType,$checkId,$params,$DEQUALS,$body,$pre,$post );
}
;

operationType :
  type[dom] DEQRARROW type[rng]
{
  PType dom = (PType)$dom;
  PType rng = (PType)$rng;
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  $$ = new AOperationType(util.extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
}
| LRPAREN[dom] DEQRARROW type[rng]
{
  PType dom = new AVoidType(util.extractLexLocation((CmlLexeme)$dom), true);
  PType rng = (PType)$rng;
  List<PType> types = new LinkedList<PType>();
  // RWL: Overture Type Checker expects the list of domain types to be empty 
  // when there is none. Adding the Void type is causing it to mismatch
  // the number of patterns and the number of formal types in apply expressions.
  //
  //types.add(dom);
  //
  $$ = new AOperationType(util.extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
}
| type[dom] DEQRARROW LRPAREN[rng]
{
  PType dom = (PType)$dom;
  PType rng = new AVoidType(util.extractLexLocation((CmlLexeme)$rng), true);
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  $$ = new AOperationType(util.extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
}
| LRPAREN[dom] DEQRARROW LRPAREN[rng]
{
  PType dom = new AVoidType(util.extractLexLocation((CmlLexeme)$dom), true);
  PType rng = new AVoidType(util.extractLexLocation((CmlLexeme)$rng), true);
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  $$ = new AOperationType(util.extractLexLocation(dom.getLocation(), rng.getLocation()),
                          false,
                          new LinkedList<PDefinition>(),
                          types,
                          rng);
};

operationBody :
  action
{
  $$ = $1;
}
| SUBCLASSRESP
{
  $$ = new ASubclassResponsibilityAction(util.extractLexLocation((CmlLexeme)$1));
}
| NOTYETSPEC
{
  $$ = new ANotYetSpecifiedStatementAction(util.extractLexLocation((CmlLexeme)$1), null, null);
}
;

externals_opt :
  externals                     { $$ = $1; }
| /* empty */                   { $$ = null; }
;

externals :
  FRAME varInformationList      { $$ = $2; }
;

varInformationList :
  varInformation
{
  List<AExternalClause> infoList = new Vector<AExternalClause>();
  infoList.add((AExternalClause)$varInformation);
  $$ = infoList;
}
| varInformationList[list] varInformation
{
  List<AExternalClause> infoList = (Vector<AExternalClause>)$list;
  infoList.add((AExternalClause)$varInformation);
  $$ = infoList;
}
;

/* DEVIATION
 * CML_0:
 *   mode, name list, [ ':', type ]
 * here:
 *   mode dottedIdentifier [ COLON type ] [ COMMA dottedIdentifier [ COLON type ] ]
 */
/* JWC FIXME
 * There should only be one type per varInformation list.  We should
 * have:
 *   mode dottedIdentifier { COMMA dottedIdentifier } [ COLON type ]
 */
varInformation :
  mode dottedIdentifier[id]
{
  $$ = util.caseVarInformation($mode,$id,null);
}
| mode dottedIdentifier[id] COLON type
{
  $$ = util.caseVarInformation($mode,$id,$type);
}
| varInformation[info] COMMA dottedIdentifier[id]
{
  $$ = util.caseMultiVarInformation($info,$id,null);
}
| varInformation[info] COMMA dottedIdentifier[id] COLON type
{
  $$ = util.caseMultiVarInformation($info,$id,$type);
}
;

mode :
  RD
{
  $$ = new LexToken(util.extractLexLocation((CmlLexeme)$RD), VDMToken.READ);
}
| WR
{
  $$ = new LexToken(util.extractLexLocation((CmlLexeme)$WR), VDMToken.WRITE);
}
;

/* --- TODO --- */
/* Determine the validity of the below comment and adjust as
 * necessary.
 */
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
  STATE
{
    AStateParagraphDefinition state = new AStateParagraphDefinition();
    state.setLocation(util.extractLexLocation((CmlLexeme)$STATE));
    $$  = state;
}
| STATE stateDefList
{
    AStateParagraphDefinition state = (AStateParagraphDefinition)$stateDefList;
    state.setLocation(util.extractLexLocation((CmlLexeme)$STATE,
                                         util.extractLastLexLocation(state.getStateDefs())));
    $$ = state;
}
//| STATE stateDefList SEMI
//{
//    AStateParagraphDefinition state = (AStateParagraphDefinition)$stateDefList;
//    state.setLocation(util.extractLexLocation((CmlLexeme)$STATE,(CmlLexeme)$SEMI));
//    $$ = state;
//}
;

stateDefList :
  stateDef
{
  List<PDefinition> defs = new Vector<PDefinition>();
  defs.add((PDefinition)$stateDef);
  AStateParagraphDefinition stateDef = new AStateParagraphDefinition();
  stateDef.setStateDefs(defs);
  $$ = stateDef;
}
| stateDefList[list] SEMI stateDef
{
  AStateParagraphDefinition stateDef = (AStateParagraphDefinition)$list;
  stateDef.getStateDefs().add((PDefinition)$stateDef);
  $$ = stateDef;
}
;

stateDef :
  qualifier assignmentDef
{
  AAssignmentDefinition adef = (AAssignmentDefinition)$assignmentDef;
  adef.setAccess((AAccessSpecifierAccessSpecifier)$qualifier);
  $$ = adef;
}
| INV expression
{
  PExp exp = (PExp) $expression;
  LexLocation location = util.extractLexLocation((CmlLexeme)$INV, exp.getLocation());
  AClassInvariantDefinition cinv = new AClassInvariantDefinition(location,
                                     NameScope.GLOBAL,
                                     true,
                                     null/*access*/,
                                     Pass.DEFS/*Pass*/,
                                     exp);
  cinv.setName(new LexNameToken("", new LexIdentifierToken("inv", false, location)));
  $$ = cinv;  		                                  
}
;

expressionList :
  expression
{
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)$expression);
  $$ = exps;
}
| expressionList[list] COMMA expression
{
  List<PExp> exps = (List<PExp>)$list;
  exps.add((PExp)$expression);
  $$ = exps;
}
;

/* --- TODO --- */
/* Old comment about paths and their conversion, below.
 *
 * We need to go through it an ensure that all the cases are being
 * generated/handled somehow.
 */
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

expression :
/* symbolic literal expressions*/
  numericLiteral
{
    PExp exp = null;
    if($numericLiteral instanceof LexIntegerToken) {
        LexIntegerToken lit = (LexIntegerToken)$numericLiteral;
        exp = new AIntLiteralExp(lit.location, lit);
    } else {
        LexRealToken lit = (LexRealToken)$numericLiteral;
        exp = new ARealLiteralExp(lit.location, lit);
    }
    $$ = exp;
}
| booleanLiteral
{
  LexBooleanToken lit = (LexBooleanToken)$booleanLiteral;
  $$ = new ABooleanConstExp(lit.location, lit);
}
| nilLiteral
{
    $$ = new ANilExp(((LexKeywordToken)$nilLiteral).location);
}
| characterLiteral
{
    LexCharacterToken lit = (LexCharacterToken)$characterLiteral;
    $$ = new ACharLiteralExp(lit.location, lit);
}
| textLiteral
{
    LexStringToken lit = (LexStringToken)$textLiteral;
    ASeqSeqType charSeq = new ASeqSeqType(lit.location,
                                    true,
                                    null,
                                    new ACharBasicType(),
                                    lit.value.length() == 0);
    $$ = new AStringLiteralExp(charSeq,
                               lit.location,
                               lit);
}
| quoteLiteral
{
  LexQuoteToken lit = (LexQuoteToken)$quoteLiteral;
  $$ = new AQuoteLiteralExp(lit.location, lit);
}
/* symbolic literal expressions end*/
| LPAREN[s] expression[exp] RPAREN[e]
{
  $$ = new ABracketedExp(util.extractLexLocation((CmlLexeme)$s, (CmlLexeme)$e), (PExp)$exp);
}
| SELF
{
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$SELF);
  $$ = new ASelfExp(name.location, name);
}
| IDENTIFIER
{
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$IDENTIFIER);
  $$ = new AVariableExp(name.location, name,"");
}
| IDENTIFIER TILDE
{
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$IDENTIFIER, true);
  $$ = new AVariableExp(name.location, name,"");
}
| expression[prefix] DOT IDENTIFIER
{
  $$ = util.caseExpDotIdentifier((PExp)$prefix, util.extractLexIdentifierToken($IDENTIFIER));
}
| expression[prefix] BACKTICK IDENTIFIER
{
  $$ = util.caseExpDotIdentifier((PExp)$prefix, util.extractLexIdentifierToken($IDENTIFIER));
}
| expression[tuple] DOTHASH NUMERAL
{
  $$ = util.expressionDotHashNumeralToFieldNumberExp($tuple, $NUMERAL);
}
| expression[rootExp] LRPAREN
{
  $$ = util.caseExpressionApply($rootExp, new LinkedList<PExp>(), $LRPAREN);
}
| expression[rootExp] LPAREN expressionList RPAREN
{
  $$ = util.caseExpressionApply($rootExp, $expressionList, $RPAREN);
}
| expression[seq] LPAREN expression[from] ELLIPSIS expression[to] RPAREN
{
  PExp seq = (PExp)$seq;
  PExp from = (PExp)$from;
  PExp to = (PExp)$to;
  $$ = AstFactory.newASubseqExp(seq,from,to);
}
| expression[exp] DOT matchValue
{
  $$ = util.caseExpDotMatchValue($exp,$matchValue);
}
/* communication structure */
| expression[exp] BANG IDENTIFIER
{
  $$ = util.caseExpBangIdentifier($exp,$IDENTIFIER);
}
| expression[exp] BANG matchValue
{
  $$ = util.caseExpBangMatchValue($exp,$matchValue);
}
| expression[exp] QUESTION pattern %prec QUESTIONALONE
{
  $$ = util.caseExpQuestionPattern($exp,$pattern);
}
/*
 * DEVIATION
 * The LSQUARE RSQUARE are a deviation
 */
| expression[exp] QUESTION LSQUARE setBind RSQUARE
{
  $$ = util.caseExpQuestionSetBind($exp,$setBind);
}
/* end communication structure*/
| LET localDefList[defs] IN expression[exp] %prec LET
{
  List<PDefinition> defs = (List<PDefinition>)$defs;
  PExp exp = (PExp)$exp;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LET, exp.getLocation());
  $$ = new ALetDefExp(loc, defs, exp);
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
| FORALL bindList[binds] AT expression[exp] %prec FORALL
{
  CmlLexeme forall = (CmlLexeme)$FORALL;
  List<PMultipleBind> binds = (List<PMultipleBind>)$binds;
  PExp exp = (PExp)$exp;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(forall), exp.getLocation());
  $$ = new AForAllExp(loc, binds, exp);
}
| EXISTS bindList[binds] AT expression[exp] %prec EXISTS
{
  CmlLexeme exists = (CmlLexeme)$EXISTS;
  List<PMultipleBind> binds = (List<PMultipleBind>)$binds;
  PExp exp = (PExp)$exp;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(exists), exp.getLocation());
  $$ = new AExistsExp(loc, binds, exp);
}
| EXISTS1 bind AT expression[exp] %prec EXISTS1
{
  CmlLexeme exists = (CmlLexeme)$EXISTS1;
  PBind bind = (PBind)$bind;
  PExp exp = (PExp)$exp;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(exists), exp.getLocation());
  $$ = new AExists1Exp(loc, bind, exp, null);
}
| IOTA bind AT expression[exp] %prec IOTA
{
  CmlLexeme iota = (CmlLexeme)$IOTA;
  PBind bind = (PBind)$bind;
  PExp exp = (PExp)$exp;
  LexLocation loc = util.combineLexLocation(util.extractLexLocation(iota), exp.getLocation());
  $$ = new AIotaExp(loc, bind, exp);
}
/* set enumeration */
| LCURLY RCURLY
{
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation(loc);
  $$ = res;
}
| LCURLY expressionList[list] RCURLY
{
  LexLocation location = util.extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  List<PExp> members = (List<PExp>)$list;
  $$ = new ASetEnumSetExp(location, members);
}
/* set comprehensions */
| LCURLY expression[exp] BAR bindList[binds] RCURLY
{
  PExp exp = (PExp)$exp;
  List<PMultipleBind> binds = (List<PMultipleBind>)$binds;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new ASetCompSetExp(loc, exp, binds, null);
}
| LCURLY expression[exp] BAR bindList[binds] AT expression[pred] RCURLY
{
  PExp exp = (PExp)$exp;
  List<PMultipleBind> binds = (List<PMultipleBind>)$binds;
  PExp pred = (PExp)$pred;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new ASetCompSetExp(loc, exp, binds, pred);
}
/* set range expression */
| LCURLY expression[start] ELLIPSIS expression[end] RCURLY
{
  PExp start = (PExp)$start;
  PExp end = (PExp)$end;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new ASetRangeSetExp(loc, start, end);
}
/* sequence enumerations */
| LRSQUARE
{
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
  List<PExp> exps = new LinkedList<PExp>();
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LRSQUARE);
  $$ = new ASeqEnumSeqExp(loc, exps);
}
| LSQUARE expressionList[list] RSQUARE
{
  List<PExp> exps = (List<PExp>)$list;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LSQUARE, (CmlLexeme)$RSQUARE);
  $$ = new ASeqEnumSeqExp(loc, exps);
}
/* sequence comprehensions */
| LSQUARE expression[exp] BAR setBind[bind] RSQUARE
{
  PExp exp = (PExp)$exp;
  ASetBind binds = (ASetBind)$bind;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LSQUARE, (CmlLexeme)$RSQUARE);
  $$ = new ASeqCompSeqExp(loc, exp, binds, null);
}
| LSQUARE expression[exp] BAR setBind[bind] AT expression[pred] RSQUARE
{
  PExp exp = (PExp)$exp;
  ASetBind binds = (ASetBind)$bind;
  PExp pred = (PExp)$pred;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LSQUARE, (CmlLexeme)$RSQUARE);
  $$ = new ASeqCompSeqExp(loc, exp, binds, pred);
}
/* map enumerations */
| EMPTYMAP // LRCURLY
{
  LexLocation loc = util.extractLexLocation((CmlLexeme)$EMPTYMAP);
  $$ = new AMapEnumMapExp(loc, new LinkedList<AMapletExp>());
}
| LCURLY mapletList[list] RCURLY
{
  List<AMapletExp> maplets = (List<AMapletExp>)$list;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new AMapEnumMapExp(loc, maplets);
}
/* map comprehensions */
| LCURLY maplet BAR bindList RCURLY
{
  AMapletExp maplet = (AMapletExp)$maplet;
  List<PMultipleBind> binds = (List<PMultipleBind>)$bindList;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new AMapCompMapExp(loc, maplet, binds, null);
}
| LCURLY maplet BAR bindList AT expression[exp] RCURLY
{
  AMapletExp maplet = (AMapletExp)$maplet;
  List<PMultipleBind> binds = (List<PMultipleBind>)$bindList;
  PExp pred = (PExp)$exp;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new AMapCompMapExp(loc, maplet, binds, pred);
}
/* tuple constructor */
| MKUNDER LPAREN expressionList[list] RPAREN
{
  List<PExp> exprs = (List<PExp>)$list;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$MKUNDER, (CmlLexeme)$RPAREN);
  $$ = new ATupleExp(loc, exprs);
}
/* recordConstructor */
| MKUNDERNAME LPAREN expressionList[list] RPAREN
{
  LexNameToken name = util.extractNameFromUNDERNAMEToken((CmlLexeme)$MKUNDERNAME);
  List<PExp> exprs = (List<PExp>)$list;
  LexLocation loc = util.extractLexLocation(((CmlLexeme)$MKUNDERNAME), (CmlLexeme)$RPAREN);
  PExp res = null;
  if ("token".equals(name.name) && exprs != null && exprs.size() == 1) {
    ATokenBasicType type = new ATokenBasicType(loc, true);
    $$ = new AMkBasicExp(type, loc, exprs.get(0));
  } else {
    $$ = new AMkTypeExp(loc, name, exprs);
  }
}
/* lambda expression */
| LAMBDA typeBindList[binds] AT expression[exp] %prec LAMBDA
{
  List<ATypeBind> binds = (List<ATypeBind>)$binds;
  PExp body = (PExp)$exp;
  LexLocation loc = util.extractLexLocation((CmlLexeme)$LAMBDA, body.getLocation());
  $$ = new ALambdaExp(loc, binds, body, null, null);
}
/* general Is Expressions */
/* DEVIATION
 * CML_0:
 *   ISUNDER name LPAREN expression RPAREN
 * here:
 *   ISUNDERNAME LPAREN expression RPAREN
 *
 */
| ISUNDERNAME LPAREN expression[exp] RPAREN
{
  $$ = new AIsExp(null, // tc type
                  util.extractLexLocation((CmlLexeme)$ISUNDERNAME,
                                     (CmlLexeme)$RPAREN),
                  util.extractNameFromUNDERNAMEToken((CmlLexeme)$ISUNDERNAME),
                  null, //basicType
                  (PExp)$exp,
                  null); //PDef
}
| ISUNDER basicType[type] LPAREN expression[exp] RPAREN
{
  $$ = new AIsExp(null,
                  util.extractLexLocation((CmlLexeme)$ISUNDER,
                                     (CmlLexeme)$RPAREN),
                  null,
                  (PType)$type,
                  (PExp)$exp,
                  null);
}
| ISUNDER LPAREN expression[exp] COMMA type RPAREN
{
  $$ = new AIsExp(null,
                  util.extractLexLocation((CmlLexeme)$ISUNDER,
                                     (CmlLexeme)$RPAREN),
                  null,
                  (PType)$type,
                  (PExp)$exp,
                  null);
}
/* precondition expression */
/* (JWC) first parameter of the precondition expression is the
 * function that we want the precondition of
 */
| PREUNDER LPAREN expressionList[list] RPAREN
{
  $$ = util.caseExpressionPrecondition($PREUNDER,$list,$RPAREN);
}
/* DEVIATION
 * GRAMMAR ERROR: Missing COMMA
 * CML_0:
 *   ISOFCLASS LPAREN name expression RPAREN
 * here:
 *   ISOFCLASS LPAREN dottedIdentifier COMMA expression RPAREN
 */
| ISOFCLASS LPAREN dottedIdentifier[id] COMMA expression[exp] RPAREN
{
  $$ = util.caseExpressionIsOfBaseClass($ISOFCLASS,$id,$exp,$RPAREN);
}
/* chanset expressions */
| LCURLYBAR expressionList[list] BARRCURLY
{
  $$ = util.caseAEnumChansetSetExp($LCURLYBAR,$list,$BARRCURLY);
}
| LCURLYBAR expression[chanexp] BAR bindList BARRCURLY
{
  $$ = util.caseACompChansetSetExp($LCURLYBAR, $chanexp,$bindList, null, $BARRCURLY);
}
| LCURLYBAR expression[chanexp] BAR bindList AT expression[exp] BARRCURLY
{
  $$ = util.caseACompChansetSetExp($LCURLYBAR, $chanexp,$bindList, $exp, $BARRCURLY);
}
/* chanset expressions end */
;


/* symbolic literals */
booleanLiteral:
  FALSE
{
  $$ = new LexBooleanToken(VDMToken.FALSE, util.extractLexLocation((CmlLexeme)$FALSE));
}
| TRUE
{
  $$ = new LexBooleanToken(VDMToken.TRUE, util.extractLexLocation((CmlLexeme)$TRUE));
}
;

characterLiteral :
  CHAR_LIT
{
  CmlLexeme lex = (CmlLexeme)$CHAR_LIT;
  LexLocation loc = util.extractLexLocation(lex);
  String res = lex.getValue();
  res = res.replace("'", ""); // FIXME: what is this for? is it correct?
  $$ = new LexCharacterToken(util.convertEscapeToChar(res), loc);
}
;

nilLiteral :
  NIL
{
  $$ = new LexKeywordToken(VDMToken.NIL, util.extractLexLocation((CmlLexeme)$NIL));
}
;

numericLiteral :
  NUMERAL
{
  $$ = util.CmlLexemeToLexIntegerToken($NUMERAL);
}
| HEX_LITERAL
{
  CmlLexeme lexeme = (CmlLexeme)$HEX_LITERAL;
  LexLocation loc = util.extractLexLocation(lexeme);
  BigInteger b = new BigInteger(lexeme.getValue().substring(2), 16);
  $$ = new LexIntegerToken(b.longValue(), loc);
}
| DECIMAL
{
  CmlLexeme lexeme = (CmlLexeme)$DECIMAL;
  LexLocation loc = util.extractLexLocation(lexeme);
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
  String lit = ((CmlLexeme)$STRING).getValue();
  $$ = new LexStringToken(lit.substring(1, lit.length()-1),
                          util.extractLexLocation((CmlLexeme)$STRING));
}
;

quoteLiteral :
  QUOTE_LITERAL[lit]
{
  String lit = ((CmlLexeme)$lit).getValue();
  $$ = new LexQuoteToken(lit.substring(1, lit.length()-1),
                         util.extractLexLocation((CmlLexeme)$lit));
}
;

/* symbolic literals end*/

localDefList :
  localDef
{
  List<PDefinition> res = new LinkedList<PDefinition>();
  res.add((PDefinition)$localDef);
  $$ = res;
}
| localDefList[list] COMMA localDef
{
  List<PDefinition> defs = (List<PDefinition>)$list;
  defs.add((PDefinition)$localDef);
  $$ = defs;
}
;

localDef :
  valueDef                      { $$ = $1; }
| explicitFunctionDef           { $$ = $1; }
;

ifExpr :
  IF expression[test] THEN expression[then] elseExprs
{
  PExp test = (PExp)$test;
  PExp then = (PExp)$then;
  List<PExp> elses = (List<PExp>)$elseExprs;
  LexLocation loc = null;
  if (elses.size() > 0) {
    loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$IF),
                             util.extractLastLexLocation(elses));
  } else {
    loc = util.combineLexLocation(util.extractLexLocation((CmlLexeme)$IF),
                             then.getLocation());
  }
  AIfExp ifexp = new AIfExp(loc,
                            test,
                            then,
                            null, // elseIfs
                            null); // else
  List<AElseIfExp> elseifs = new LinkedList<AElseIfExp>();
  ifexp.setElseList(elseifs);
  for(PExp exp : elses) {
    if (exp instanceof AElseIfExp) {
      elseifs.add((AElseIfExp)exp);
    } else {
      ifexp.setElse(exp);
    }
  }
  $$ = ifexp;
}
;

elseExprs :
  ELSE expression
{
  PExp exp = (PExp)$expression;
  List<PExp> res = new LinkedList<PExp>();
  res.add(exp);
  $$ = res;
}
| ELSEIF expression[test] THEN expression[then] elseExprs[tail]
{
  PExp test = (PExp)$test;
  PExp then = (PExp)$then;
  List<PExp> tail = (List<PExp>)$tail;
  tail.add(new AElseIfExp(util.extractLexLocation(util.extractLexLocation((CmlLexeme)$ELSEIF),
						  then.getLocation()),
                          test,
                          then));
  $$ = tail;
}
;

casesExpr :
  CASES expression[test] COLON casesExprAltList[alts] END
{
  ACasesExp alts = new ACasesExp();//(ACasesExp)$alts;
  alts.setCases((List<ACaseAlternative>)$alts);
  alts.setExpression((PExp)$test);
  alts.setLocation(util.extractLexLocation((CmlLexeme)$CASES, (CmlLexeme)$END));
  $$ = alts;
}
| CASES expression[test] COLON casesExprAltList[alts] COMMA OTHERS RARROW expression[others] END
{
  ACasesExp alts = new ACasesExp();//(ACasesExp)$alts;
  alts.setCases((List<ACaseAlternative>)$alts);
  alts.setExpression((PExp)$test);
  alts.setLocation(util.extractLexLocation((CmlLexeme)$CASES, (CmlLexeme)$END));
  alts.setOthers((PExp)$others);
  $$ = alts;
}
;

casesExprAltList :
  casesExprAlt[alt]
{
  $$ = $alt; 
  //ACasesExp casesExp = new ACasesExp();
  //casesExp.getCases().add((ACaseAlternative)$alt);
  //$$ = casesExp;
}
| casesExprAltList[alts] COMMA casesExprAlt[alt]
{
  List<ACaseAlternative> alts = (List<ACaseAlternative>)$alts;
  alts.addAll((List<ACaseAlternative>)$alt);
  $$ = alts;
  //ACasesExp casesExp = (ACasesExp)$alts;
  //casesExp.getCases().add((ACaseAlternative)$alt);
  //$$ = casesExp;
}
;

/* --- TODO --- */
/* I think we can merge this upwards */
casesExprAlt :
  patternList RARROW expression
{
   List<ACaseAlternative> res = new LinkedList<ACaseAlternative>();
   List<PPattern> patList = (List<PPattern>)$patternList;
   PExp exp = (PExp)$expression;
   LexLocation leftMost = util.extractLexLeftMostFromPatterns(patList);
   LexLocation loc = util.combineLexLocation(leftMost, exp.getLocation());
   for(PPattern p : patList)
   {
     ACaseAlternative r = new ACaseAlternative();
     r.setPattern(p);
     r.setLocation(loc);
     r.setResult(exp);
     res.add(r);
    }
   $$ = res;
}
;

unaryExpr :
  PLUS[op] expression[exp] %prec U-PLUS
{
  $$ = new AUnaryPlusUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                 ((PExp)$exp).getLocation()),
                              (PExp)$exp);
}
| MINUS[op] expression[exp] %prec U-MINUS
{
  $$ = new AUnaryMinusUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                  ((PExp)$exp).getLocation()),
                               (PExp)$exp);
}
| ABS[op] expression[exp]
{
  $$ = new AAbsoluteUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                ((PExp)$exp).getLocation()),
                             (PExp)$exp);
}
| FLOOR[op] expression[exp]
{
  $$ = new AFloorUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                             ((PExp)$exp).getLocation()),
                          (PExp)$exp);
}
| NOT[op] expression[exp]
{
  $$ = new ANotUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                           ((PExp)$exp).getLocation()),
                        (PExp)$exp);
}
| CARD[op] expression[exp]
{
  $$ = new ACardinalityUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                   ((PExp)$exp).getLocation()),
                                (PExp)$exp);
}
| POWER[op] expression[exp]
{
  $$ = new APowerSetUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                ((PExp)$exp).getLocation()),
                             (PExp)$exp);
}
| DUNION[op] expression[exp]
{
  $$ = new ADistUnionUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                 ((PExp)$exp).getLocation()),
                              (PExp)$exp);
}
| DINTER[op] expression[exp]
{
  $$ = new ADistIntersectUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                     ((PExp)$exp).getLocation()),
                                  (PExp)$exp);
}
| HD[op] expression[exp]
{
  $$ = new AHeadUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                            ((PExp)$exp).getLocation()),
                         (PExp)$exp);
}
| TL[op] expression[exp]
{
  $$ = new ATailUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                            ((PExp)$exp).getLocation()),
                         (PExp)$exp);
}
| LEN[op] expression[exp]
{
  $$ = new ALenUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                           ((PExp)$exp).getLocation()),
                        (PExp)$exp);
}
| ELEMS[op] expression[exp]
{
  $$ = new AElementsUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                ((PExp)$exp).getLocation()),
                             (PExp)$exp);
}
| INDS[op] expression[exp]
{
  $$ = new AIndicesUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                               ((PExp)$exp).getLocation()),
                            (PExp)$exp);
}
| REVERSE[op] expression[exp]
{
  $$ = new AReverseUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                               ((PExp)$exp).getLocation()),
                            (PExp)$exp);
}
| CONC[op] expression[exp]
{
  $$ = new ADistConcatUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                  ((PExp)$exp).getLocation()),
                               (PExp)$exp);
}
| DOM[op] expression[exp]
{
  $$ = new AMapDomainUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                 ((PExp)$exp).getLocation()),
                              (PExp)$exp);
}
| RNG[op] expression[exp]
{
  $$ = new AMapRangeUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                ((PExp)$exp).getLocation()),
                             (PExp)$exp);
}
| MERGE[op] expression[exp]
{
  $$ = new ADistMergeUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                 ((PExp)$exp).getLocation()),
                              (PExp)$exp);
}
| INVERSE[op] expression[exp]
{
  $$ = new AMapInverseUnaryExp(util.extractLexLocation((CmlLexeme)$op,
                                                  ((PExp)$exp).getLocation()),
                               (PExp)$exp);
}
;

binaryExpr :
  expression[left] PLUS[op] expression[right]
{
  $$ = new APlusNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                    ((PExp)$right).getLocation()),
                                 (PExp)$left,
                                 util.extractLexToken((CmlLexeme)$op),
                                 (PExp)$right);
}
| expression[left] STAR[op] expression[right]
{
  $$ = new ATimesNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                     ((PExp)$right).getLocation()),
                                  (PExp)$left,
                                  util.extractLexToken((CmlLexeme)$op),
                                  (PExp)$right);
}
| expression[left] MINUS[op] expression[right]
{
  $$ = new ASubstractNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                         ((PExp)$right).getLocation()),
                                      (PExp)$left,
                                      util.extractLexToken((CmlLexeme)$op),
                                      (PExp)$right);
}
| expression[left] DIV[op] expression[right]
{
  $$ = new ADivideNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                      ((PExp)$right).getLocation()),
                                   (PExp)$left,
                                   util.extractLexToken((CmlLexeme)$op),
                                   (PExp)$right);
}
| expression[left] SLASH[op] expression[right]
{
  $$ = new ADivNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                   ((PExp)$right).getLocation()),
                                (PExp)$left,
                                util.extractLexToken((CmlLexeme)$op),
                                (PExp)$right);
}
| expression[left] REM[op] expression[right]
{
  $$ = new ARemNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                   ((PExp)$right).getLocation()),
                                (PExp)$left,
                                util.extractLexToken((CmlLexeme)$op),
                                (PExp)$right);
}
| expression[left] MOD[op] expression[right]
{
  $$ = new AModNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                   ((PExp)$right).getLocation()),
                                (PExp)$left,
                                util.extractLexToken((CmlLexeme)$op),
                                (PExp)$right);
}
| expression[left] LT[op] expression[right]
{
  $$ = new ALessNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                    ((PExp)$right).getLocation()),
                                 (PExp)$left,
                                 util.extractLexToken((CmlLexeme)$op),
                                 (PExp)$right);
}
| expression[left] LTE[op] expression[right]
{
  $$ = new ALessEqualNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                         ((PExp)$right).getLocation()),
                                      (PExp)$left,
                                      util.extractLexToken((CmlLexeme)$op),
                                      (PExp)$right);
}
| expression[left] GT[op] expression[right]
{
  $$ = new AGreaterNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                       ((PExp)$right).getLocation()),
                                    (PExp)$left,
                                    util.extractLexToken((CmlLexeme)$op),
                                    (PExp)$right);
}
| expression[left] GTE[op] expression[right]
{
  $$ = new AGreaterEqualNumericBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                            ((PExp)$right).getLocation()),
                                         (PExp)$left,
                                         util.extractLexToken((CmlLexeme)$op),
                                         (PExp)$right);
}
| expression[left] EQUALS[op] expression[right]
{
  $$ = new AEqualsBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                               ((PExp)$right).getLocation()),
                            (PExp)$left,
                            util.extractLexToken((CmlLexeme)$op),
                            (PExp)$right);
}
| expression[left] NEQ[op] expression[right]
{
  $$ = new ANotEqualBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                 ((PExp)$right).getLocation()),
                              (PExp)$left,
                              util.extractLexToken((CmlLexeme)$op),
                              (PExp)$right);
}
| expression[left] OR[op] expression[right]
{
  $$ = new AOrBooleanBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                  ((PExp)$right).getLocation()),
                               (PExp)$left,
                               util.extractLexToken((CmlLexeme)$op),
                               (PExp)$right);
}
| expression[left] AND[op] expression[right]
{
  $$ = new AAndBooleanBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                   ((PExp)$right).getLocation()),
                                (PExp)$left,
                                util.extractLexToken((CmlLexeme)$op),
                                (PExp)$right);
}
| expression[left] EQRARROW[op] expression[right]
{
  $$ = new AImpliesBooleanBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                       ((PExp)$right).getLocation()),
                                    (PExp)$left,
                                    util.extractLexToken((CmlLexeme)$op),
                                    (PExp)$right);
}
| expression[left] LTEQUALSGT[op] expression[right]
{
  $$ = new AEquivalentBooleanBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                          ((PExp)$right).getLocation()),
                                       (PExp)$left,
                                       util.extractLexToken((CmlLexeme)$op),
                                       (PExp)$right);
}
| expression[left] INSET[op] expression[right]
{
  $$ = new AInSetBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                              ((PExp)$right).getLocation()),
                           (PExp)$left,
                           util.extractLexToken((CmlLexeme)$op),
                           (PExp)$right);
}
| expression[left] NOTINSET[op] expression[right]
{
  $$ = new ANotInSetBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                 ((PExp)$right).getLocation()),
                              (PExp)$left,
                              util.extractLexToken((CmlLexeme)$op),
                              (PExp)$right);
}
| expression[left] SUBSET[op] expression[right]
{
  $$ = new ASubsetBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                               ((PExp)$right).getLocation()),
                            (PExp)$left,
                            util.extractLexToken((CmlLexeme)$op),
                            (PExp)$right);
}
| expression[left] PSUBSET[op] expression[right]
{
  $$ = new AProperSubsetBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                     ((PExp)$right).getLocation()),
                                  (PExp)$left,
                                  util.extractLexToken((CmlLexeme)$op),
                                  (PExp)$right);
}
| expression[left] UNION[op] expression[right]
{
  $$ = new ASetUnionBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                 ((PExp)$right).getLocation()),
                              (PExp)$left,
                              util.extractLexToken((CmlLexeme)$op),
                              (PExp)$right);
}
| expression[left] BACKSLASH[op] expression[right]
{
  $$ = new ASetDifferenceBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                      ((PExp)$right).getLocation()),
                                   (PExp)$left,
                                   util.extractLexToken((CmlLexeme)$op),
                                   (PExp)$right);
}
| expression[left] INTER[op] expression[right]
{
  $$ = new ASetIntersectBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                     ((PExp)$right).getLocation()),
                                  (PExp)$left,
                                  util.extractLexToken((CmlLexeme)$op),
                                  (PExp)$right);
}
| expression[left] CARET[op] expression[right]
{
  $$ = new ASeqConcatBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                  ((PExp)$right).getLocation()),
                               (PExp)$left,
                               util.extractLexToken((CmlLexeme)$op),
                               (PExp)$right);
}
| expression[left] DPLUS[op] expression[right]
{
  $$ = new APlusPlusBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                 ((PExp)$right).getLocation()),
                              (PExp)$left,
                              util.extractLexToken((CmlLexeme)$op),
                              (PExp)$right);
}
| expression[left] MUNION[op] expression[right]
{
  $$ = new AMapUnionBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                 ((PExp)$right).getLocation()),
                              (PExp)$left,
                              util.extractLexToken((CmlLexeme)$op),
                              (PExp)$right);
}
| expression[left] LTCOLON[op] expression[right]
{
  $$ = new ADomainResToBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                    ((PExp)$right).getLocation()),
                                 (PExp)$left,
                                 util.extractLexToken((CmlLexeme)$op),
                                 (PExp)$right);
}
| expression[left] LTDASHCOLON[op] expression[right]
{
  $$ = new ADomainResByBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                    ((PExp)$right).getLocation()),
                                 (PExp)$left,
                                 util.extractLexToken((CmlLexeme)$op),
                                 (PExp)$right);
}
| expression[left] COLONGT[op] expression[right]
{
  $$ = new APlusPlusBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                 ((PExp)$right).getLocation()),
                              (PExp)$left,
                              util.extractLexToken((CmlLexeme)$op),
                              (PExp)$right);
}
| expression[left] COLONDASHGT[op] expression[right]
{
  $$ = new APlusPlusBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                 ((PExp)$right).getLocation()),
                              (PExp)$left,
                              util.extractLexToken((CmlLexeme)$op),
                              (PExp)$right);
}
| expression[left] COMP[op] expression[right]
{
  $$ = new ACompBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                             ((PExp)$right).getLocation()),
                          (PExp)$left,
                          util.extractLexToken((CmlLexeme)$op),
                          (PExp)$right);
}
| expression[left] DSTAR[op] expression[right]
{
  $$ = new AStarStarBinaryExp(util.extractLexLocation(((PExp)$left).getLocation(),
                                                 ((PExp)$right).getLocation()),
                              (PExp)$left,
                              util.extractLexToken((CmlLexeme)$op),
                              (PExp)$right);
}
;

mapletList :
  maplet
{
  List<AMapletExp> res = new LinkedList<AMapletExp>();
  res.add((AMapletExp)$maplet);
  $$ = res;
}
| mapletList[list] COMMA maplet
{
  List<AMapletExp> maplets = (List<AMapletExp>)$list;
  maplets.add((AMapletExp)$maplet);
  $$ = maplets;
};

maplet :
  expression[dom] BARRARROW expression[rng]
{
  PExp dom = (PExp)$dom;
  PExp rng = (PExp)$rng;
  $$ = new AMapletExp(util.extractLexLocation(dom.getLocation(),
                                         rng.getLocation()),
                      dom,
                      rng);
}
;

nonDeterministicAltList :
  expression RARROW action
{
  PExp guard = (PExp)$expression;
  PAction action = (PAction)$action;
  LexLocation location = util.extractLexLocation(guard.getLocation(),
                                            action.getLocation());
  List<ANonDeterministicAltStatementAction> alts =
    new LinkedList<ANonDeterministicAltStatementAction>();
  alts.add(new ANonDeterministicAltStatementAction(location,
                                                   guard,
                                                   action));
  $$ = alts;
}
| nonDeterministicAltList[list] BAR expression RARROW action
{
  PExp guard = (PExp)$expression;
  PAction action = (PAction)$action;
  LexLocation location = util.extractLexLocation(guard.getLocation(),
                                            action.getLocation());
  List<ANonDeterministicAltStatementAction> alts =
    (List<ANonDeterministicAltStatementAction>)$list;
  alts.add(new ANonDeterministicAltStatementAction(location,
                                                   guard,
                                                   action));
  $$ = alts;
}
;

assignmentDefList :
  assignmentDef
{
  List<AAssignmentDefinition> assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)$assignmentDef);
  $$ = assignmentDefs;
}
| assignmentDefList[list] COMMA assignmentDef
{
  List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)$list;
  assignmentDefs.add((AAssignmentDefinition)$assignmentDef);
  $$ = assignmentDefs;
}
;

assignmentDef :
  IDENTIFIER COLON type
{
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$IDENTIFIER);
  PType type = (PType)$type;
  LexLocation location = util.extractLexLocation(name.location, type.getLocation());
  AAccessSpecifierAccessSpecifier access = null;
  $$ = new AAssignmentDefinition(location,
                                 name,
                                 NameScope.GLOBAL,
                                 false,
                                 null,//VDM classDef
                                 access,
                                 type,
                                 null,//Pass
                                 null,
                                 null);
}
| IDENTIFIER COLON type COLONEQUALS expression
{
  LexNameToken name = util.extractLexNameToken((CmlLexeme)$IDENTIFIER);
  PType type = (PType)$type;
  PExp exp = (PExp)$expression;
  LexLocation location = util.combineLexLocation(name.location, exp.getLocation());
  $$ = new AAssignmentDefinition(location, name,
                                 NameScope.GLOBAL,
                                 false,
                                 null,//VDM classDef
                                 null,//access
                                 type,
                                 null,//Pass
                                 exp,
                                 null);
}
| IDENTIFIER COLON type IN expression
{
    LexNameToken name = util.extractLexNameToken((CmlLexeme)$IDENTIFIER);
    PType type = (PType)$type;
    PExp exp = (PExp)$expression;
    LexLocation location = util.combineLexLocation(name.location, exp.getLocation());
    $$ = new AAssignmentDefinition(location, name,
                                   NameScope.GLOBAL,
                                   false,
                                   null,//VDM classDef
                                   null,//access
                                   type,
                                   null,//Pass
                                   exp,
                                   null);
}
;

/* DEVIATION
 * Typechecker will have to ensure that all of the assigns in the list
 * are genuine assigments rather than operation calls.
 */
assignStatementList :
  assignStatement
{
  List<ASingleGeneralAssignmentStatementAction> assigns =
    new LinkedList<ASingleGeneralAssignmentStatementAction>();
  assigns.add((ASingleGeneralAssignmentStatementAction)$assignStatement);
  $$ = assigns;
}
| assignStatementList[list] SEMI assignStatement
{
  List<ASingleGeneralAssignmentStatementAction> assigns =
    (List<ASingleGeneralAssignmentStatementAction>)$list;
  assigns.add((ASingleGeneralAssignmentStatementAction)$assignStatement);
  $$ = assigns;
}
;

assignStatement :
/* DEVIATION
 * CML_0:
 *   stateDesignator ':=' expression
 * here:
 *   dottedIdentifier ':=' expression
 *
 * Kill 'em all and let the typechecker sort them out
 */
  dottedIdentifier[id] COLONEQUALS expression
{
  $$ = util.caseDottedIdentifierToAssignmentStm($id,$COLONEQUALS,$expression);
}
;

ifStatement :
  IF expression THEN action[then] elseStatements ELSE action[else]
{
  $$ = new AIfStatementAction(util.extractLexLocation((CmlLexeme)$IF,
                                                 ((PAction)$else).getLocation()),
                              (PExp)$expression,
                              (PAction)$then,
                              (List<? extends AElseIfStatementAction>)$elseStatements,
                              (PAction)$else);
}
| IF expression THEN action[then] ELSE action[else]
{
  $$ = new AIfStatementAction(util.extractLexLocation((CmlLexeme)$IF,
                                                 ((PAction)$else).getLocation()),
                              (PExp)$expression,
                              (PAction)$then,
                              null,
                              (PAction)$else);
}
;

elseStatements :
  ELSEIF expression THEN action
{
  List<AElseIfStatementAction> elseStms = new Vector<AElseIfStatementAction>();
  AElseIfStatementAction elseif =
    new AElseIfStatementAction(util.extractLexLocation((CmlLexeme)$ELSEIF,
                                                  ((PAction)$action).getLocation()),
                               (PExp)$expression,
                               (PAction)$action);
  elseStms.add(elseif);
  $$ = elseStms;
}
| elseStatements[elses] ELSEIF expression THEN action
{
  List<AElseIfStatementAction> elseStms = (List<AElseIfStatementAction>)$elses;
  AElseIfStatementAction elseif =
    new AElseIfStatementAction(util.extractLexLocation((CmlLexeme)$ELSEIF,
                                                  ((PAction)$action).getLocation()),
                               (PExp)$expression,
                               (PAction)$action);
  elseStms.add(elseif);
  $$ = elseStms;
}
;

casesStatement :
  CASES expression COLON casesStatementAltList[alts] END
{
  ACasesStatementAction cases = (ACasesStatementAction)$alts;
  cases.setLocation(util.extractLexLocation((CmlLexeme)$CASES, (CmlLexeme)$END));
  cases.setExp((PExp)$expression);
  $$ = cases;
}
| CASES expression COLON casesStatementAltList[alts] COMMA OTHERS RARROW action[others] END
{
  ACasesStatementAction cases = (ACasesStatementAction)$alts;
  cases.setLocation(util.extractLexLocation((CmlLexeme)$CASES, (CmlLexeme)$END));
  cases.setExp((PExp)$expression);
  cases.setOthers((PAction)$others);
  $$ = cases;
}
;

casesStatementAltList :
  casesStatementAlt
{
  List<ACaseAlternativeAction> casesList = new LinkedList<ACaseAlternativeAction>();
  casesList.add((ACaseAlternativeAction)$casesStatementAlt);
  $$ = new ACasesStatementAction(null, null, casesList, null);
}
| casesStatementAltList[cases] COMMA casesStatementAlt
{
  ACasesStatementAction cases = (ACasesStatementAction)$cases;
  cases.getCases().add((ACaseAlternativeAction)$casesStatementAlt);
  $$ = cases;
}
;

/* TODO merge upward?
 */
casesStatementAlt :
  patternList RARROW action
{
  List<PPattern> patterns = (List<PPattern>)$patternList;
  PAction action = (PAction)$action;
  $$ = new ACaseAlternativeAction(util.combineLexLocation(util.extractFirstLexLocation(patterns),
                                                     action.getLocation()),
                                  patterns,
                                  action);
}
;

implicitOperationBody :
  externals_opt[exts] preExpr_opt[pre] postExpr[post]
{
  $$ = util.caseImplicitOperationBody($exts, $pre, $post);
}
;

pattern :
  patternIdentifier             { $$ = $1; }
| patternLessID                 { $$ = $1; }
;

patternLessID :
  matchValue
{
  $$ = $1;
}
| MKUNDER LPAREN patternList COMMA pattern RPAREN // tuple patterns need at least 2
{
  List<PPattern> plist = (List<PPattern>)$patternList;
  plist.add((PPattern)$pattern);
  $$ = new ATuplePattern(util.extractLexLocation((CmlLexeme)$MKUNDER,
                                            (CmlLexeme)$RPAREN),
                         new LinkedList<PDefinition>(),
                         false,
                         plist);
}
/* DEVIATION
 * CML_0:
 *   MKUNDER name LPAREN expression RPAREN
 * here:
 *   MKUNDERNAME LPAREN expression RPAREN
 *
 */
| MKUNDERNAME LRPAREN
{
  List<PPattern> plist = new LinkedList<PPattern>();
  LexNameToken name = util.extractNameFromUNDERNAMEToken((CmlLexeme)$MKUNDERNAME);
  $$ = AstFactory.newARecordPattern(name, plist);
}
| MKUNDERNAME LPAREN patternList RPAREN
{
  List<PPattern> plist = (List<PPattern>)$patternList;
  LexNameToken name = util.extractNameFromUNDERNAMEToken((CmlLexeme)$MKUNDERNAME);
  $$ = AstFactory.newARecordPattern(name, plist);
}
;

patternList :
  pattern
{
  List<PPattern> patterns = new Vector<PPattern>();
  patterns.add((PPattern)$pattern);
  $$ = patterns;
}
| patternList[list] COMMA pattern
{
  List<PPattern> patterns = (List<PPattern>)$list;
  patterns.add((PPattern)$pattern);
  $$ = patterns;
}
;

patternIdentifier :
  IDENTIFIER
{
  CmlLexeme lexeme = (CmlLexeme)$IDENTIFIER;
  LexLocation loc = util.extractLexLocation(lexeme);
  LexNameToken lnt = new LexNameToken("",
                                      lexeme.getValue(),
                                      loc,
                                      false,
                                      true);
  AIdentifierPattern res = new AIdentifierPattern();
  res.setName(lnt);
  res.setLocation(loc);
  $$ = res;
}
| MINUS // "don't care" pattern
{
  $$ = new AIgnorePattern(util.extractLexLocation((CmlLexeme)$MINUS),
                          new LinkedList<PDefinition>(),
                          true);
}
;

matchValue :
/* symbolic literal patterns*/
  numericLiteral[lit]
{
  if ($lit instanceof LexIntegerToken) {
    LexIntegerToken lit = (LexIntegerToken)$lit;
    $$ = new AIntegerPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
  } else {
    LexRealToken lit = (LexRealToken)$lit;
    $$ = new ARealPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
  }
}
| booleanLiteral[lit]
{
  LexBooleanToken lit = (LexBooleanToken)$lit;
  $$ = new ABooleanPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
}
| nilLiteral[lit]
{
  LexKeywordToken lit = (LexKeywordToken)$lit;
  $$ = new ANilPattern(lit.location, new LinkedList<PDefinition>(), true);
}
| characterLiteral[lit]
{
  LexCharacterToken lit = (LexCharacterToken)$lit;
  $$ = new ACharacterPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
}
| textLiteral[lit]
{
  LexStringToken lit = (LexStringToken)$lit;
  $$ = new AStringPattern(lit.location, new LinkedList<PDefinition>(), true, lit);
}
| quoteLiteral[lit]
{
  LexQuoteToken lit = (LexQuoteToken)$lit;
  $$ = new AQuotePattern(lit.location, new LinkedList<PDefinition>(), true, lit);
}
| LPAREN expression RPAREN
{
  $$ = new AExpressionPattern(util.extractLexLocation((CmlLexeme)$LPAREN,
                                                 (CmlLexeme)$RPAREN),
                              new LinkedList<PDefinition>(),
                              false,
                              (PExp)$expression);
}
;

bind :
  setBind                       { $$ = $1; }
| typeBind                      { $$ = $1; }
;

setBind :
  pattern INSET expression
{
  PPattern pattern = (PPattern)$pattern;
  PExp exp = (PExp)$expression;
  LexLocation location = util.extractLexLocation(pattern.getLocation(),
                                            exp.getLocation());
  $$ = new ASetBind(location, pattern, exp);
}
;

typeBind :
  pattern COLON type
{
  PPattern pattern = (PPattern)$pattern;
  PType type = (PType)$type;
  LexLocation location = util.extractLexLocation(pattern.getLocation(),
                                            type.getLocation());
  $$ = new ATypeBind(location, pattern, type);
}
;

bindList :
  multipleBind
{
  List<PMultipleBind> binds = new Vector<PMultipleBind>();
  binds.add((PMultipleBind)$multipleBind);
  $$ = binds;
}
| bindList[binds] COMMA multipleBind
{
  List<PMultipleBind> binds = (List<PMultipleBind>)$binds;
  binds.add((PMultipleBind)$multipleBind);
  $$ = binds;
}
;

multipleBind :
  multipleSetBind               { $$ = $1; }
| multipleTypeBind              { $$ = $1; }
;

multipleSetBind :
  patternList INSET expression
{
  List<PPattern> patterns = (List<PPattern>)$patternList;
  PExp exp = (PExp)$expression;
  LexLocation loc = util.extractLexLocation(util.extractFirstLexLocation(patterns),
                                       exp.getLocation());
  $$ = new ASetMultipleBind(loc, patterns, exp);
}
;

multipleTypeBind :
  patternList COLON type
{
  List<PPattern> patterns = (List<PPattern>)$patternList;
  PType type = (PType)$type;
  LexLocation loc = util.extractLexLocation(util.extractFirstLexLocation(patterns),
                                       type.getLocation());
  $$ = new ATypeMultipleBind(loc, patterns, type);
}
;

typeBindList :
  typeBind
{
  List<ATypeBind> list = new LinkedList<ATypeBind>();
  ATypeBind bind = (ATypeBind)$typeBind;
  list.add(bind);
  $$ = list;
}
| typeBindList[list] COMMA typeBind
{
  List<ATypeBind> list = (List<ATypeBind>)$list;
  ATypeBind bind = (ATypeBind)$typeBind;
  list.add(bind);
  $$ = list;
}
;

// **********************
// *** END OF GRAMMAR ***
// **********************
