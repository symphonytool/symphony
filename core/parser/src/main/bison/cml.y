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

  private AAccessSpecifierAccessSpecifier getDefaultAccessSpecifier(boolean isStatic, boolean isAsync, LexLocation loc)
  {
    /* return new AAccessSpecifierAccessSpecifier(new APublicAccess(), */
    /*                             (isStatic ? new TStatic() : null), */
    /*                             (isAsync ? new TAsync() : null),loc); */

    return new AAccessSpecifierAccessSpecifier(new APublicAccess(),
                                (isStatic ? new TStatic() : null),
                                (isAsync ? new TAsync() : null));

  }

  private AAccessSpecifierAccessSpecifier getPrivateAccessSpecifier(boolean isStatic, boolean isAsync, LexLocation loc)
  {
    /* return new AAccessSpecifierAccessSpecifier(new APrivateAccess(), */
    /*                             (isStatic ? new TStatic() : null), */
    /*                             (isAsync ? new TAsync() : null),loc); */
    return new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
                                (isStatic ? new TStatic() : null),
                                (isAsync ? new TAsync() : null));
  }

  private LexToken extractLexToken(CmlLexeme lexeme)
  {
    LexLocation loc = extractLexLocation(lexeme);
    VDMToken tok = null;
    for(VDMToken t : VDMToken.values())
      {
        String tokenDisplay = t.toString();
        if (tokenDisplay != null && tokenDisplay.equals(lexeme.getValue())) { tok = t; break; }
      }
    if (tok == null) throw new RuntimeException("Cannot find VDM token for "+lexeme.getValue());
    return new LexToken(loc, tok);
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

  private LexIdentifierToken extractLexIdentifierToken(Object obj)
  {
      return extractLexIdentifierToken((CmlLexeme)obj);
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
%left DOT BACKTICK BANG QUESTION
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
                                     null,//ClassDefinition
                                     getDefaultAccessSpecifier(false,false,null),
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
};

processDefinition:
  PROCESS IDENTIFIER EQUALS process
{
  PProcess process = (PProcess)$process;
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, process.getLocation());
  AProcessDefinition processDef = new AProcessDefinition(process.getLocation(),
                                                         NameScope.GLOBAL,
                                                         false,
                                                         access,
                                                         null,//Pass
                                                         null,
                                                         process);
  LexNameToken id = extractLexNameToken((CmlLexeme)$IDENTIFIER);
  processDef.setName(id);
  LexLocation location = extractLexLocation((CmlLexeme)$PROCESS,
                                            processDef.getLocation());
  access = getDefaultAccessSpecifier(true, false, location);
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
  LexLocation loc = combineLexLocation(extractFirstLexLocation(decls),
                                       process.getLocation());
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  AProcessDefinition processDef = new AProcessDefinition(loc,
                                                         NameScope.GLOBAL,
                                                         false,
                                                         access,
                                                         null,//Pass
                                                         decls,
                                                         process);
  LexNameToken id = extractLexNameToken((CmlLexeme)$IDENTIFIER);
  processDef.setName(id);
  LexLocation location = extractLexLocation((CmlLexeme)$PROCESS,
                                            processDef.getLocation());
  access = getDefaultAccessSpecifier(true, false, location);
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
};

process :
/* actions */
  BEGIN AT action END
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$4);
  List<SParagraphDefinition> processParagraphs = null;
  PAction action = (PAction)$3;
  $$ = new AStateProcess(location, processParagraphs, action);
}
| BEGIN processParagraphList AT action END
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$5);
  List<SParagraphDefinition> processParagraphs = (List<SParagraphDefinition>)$processParagraphList;
  PAction action = (PAction)$4;
  $$ = new AStateProcess(location, processParagraphs, action);
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
 *
 * CHANSET
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
/* DEVIATION
 * Unfolded the definition a little bit to avoid a conflict
 */
| LPAREN[start] singleTypeDeclarationList AT process[proc] RPAREN LPAREN expression RPAREN[end]
{
  LexLocation location = extractLexLocation((CmlLexeme)$start, (CmlLexeme)$end);
  List<ATypeSingleDeclaration> decls = (List<ATypeSingleDeclaration>)$singleTypeDeclarationList;
  List<PExp> args = new LinkedList<PExp>();
  args.add((PExp)$expression);
  PProcess proc = (PProcess)$proc;
  /* FIXME: (->AKM) process was a processDefinition */
  /* $$ = new AInstantiationProcess(location,  */
  /* 				 decls,  */
  /* 				 null, */
  /* 				 (AProcessDefinition)$proc,  */
  /* 				 args); */
}
| IDENTIFIER
{
  LexNameToken name = extractLexNameToken($IDENTIFIER);
  $$ = new AInstantiationProcess(name.location, 
				 null, 
				 name,
				 null,
				 null); 
}
| IDENTIFIER LRPAREN
{
  LexNameToken name = extractLexNameToken($IDENTIFIER);
  LexLocation location = extractLexLocation(name.location,(CmlLexeme)$LRPAREN);
  $$ = new AInstantiationProcess(location, 
				 null, 
				 name,
				 null,
				 null); 
}
| IDENTIFIER LPAREN expressionList RPAREN
{
  LexNameToken name = extractLexNameToken($IDENTIFIER);
  LexLocation location = extractLexLocation(name.location,(CmlLexeme)$RPAREN);
  $$ = new AInstantiationProcess(location, 
				 null, 
				 name,
				 null,
				 (List<PExp>)$expressionList); 
}
| process[proc] renameExpression[rexp]
{
  SRenameChannelExp renameExpression = (SRenameChannelExp)$rexp;
  PProcess process = (PProcess)$proc;
  $$ = new AChannelRenamingProcess(combineLexLocation(process.getLocation(),
						      renameExpression.getLocation()),
				   process,
				   renameExpression);
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
  IDENTIFIER INSET expression
{
  /* --- TODO --- */
}
| IDENTIFIER COMMA singleExpressionDeclaration
{
  /* --- TODO --- */
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
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$1), extractLastLexLocation(actionDefinitions));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
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
  Object[] pa = (Object[])$paragraphAction;
  List<ATypeSingleDeclaration> declarations = (List<ATypeSingleDeclaration>)pa[0];
  PAction action = (PAction)pa[1];
  LexLocation defLocation = combineLexLocation(extractLexLocation((CmlLexeme)$IDENTIFIER), action.getLocation());
  AActionDefinition actionDefinition = new AActionDefinition(defLocation, 
							     NameScope.LOCAL, 
							     false, 
							     null,//Access
							     null,//Pass
							     declarations, 
							     action);
  List<AActionDefinition> actionDefs = new Vector<AActionDefinition>();
  actionDefs.add(actionDefinition);
  $$ = actionDefs;
}
| actionDefinitionList[list] IDENTIFIER EQUALS paragraphAction
{
  List<AActionDefinition> actionDefs = (List<AActionDefinition>)$list;
  Object[] pa = (Object[])$paragraphAction;
  List<ATypeSingleDeclaration> declarations = (List<ATypeSingleDeclaration>)pa[0];
  PAction action = (PAction)pa[1];
  LexLocation defLocation = combineLexLocation(extractLexLocation((CmlLexeme)$IDENTIFIER), action.getLocation());
  AActionDefinition actionDefinition = new AActionDefinition(defLocation, 
							     NameScope.LOCAL, 
							     false, 
							     null,//Access
							     null,//Pass
							     declarations, 
							     action);
  actionDefs.add(actionDefinition);
  $$ = actionDefs;
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
| LPAREN[start] action[act] RPAREN[end]
{
  LexLocation location = extractLexLocation((CmlLexeme)$start, (CmlLexeme)$end);
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
  /* --- TODO --- */
  /* need to merge in old action rule RARROW parser action and the old communication rule actions; along with an expression -> communication conversion?
   */  
  //ACommunicationAction comAction = (ACommunicationAction)$communication;
  //PAction to = (PAction)$to;
  //LexLocation location = extractLexLocation(comAction.getLocation(), to.getLocation());
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
 * grammar:
 *   MU identifier {',' identifier} '@' action {',' action}
 * here:
 *   MU expressionList '@' '(' actionList ')'
 */
| MU[start] expressionList AT LPAREN actionList RPAREN[end]
{
  /* --- TODO --- */
  /* that expressionList needs to be converted down into a nameList?
   */
  List<LexNameToken> nameList = (List<LexNameToken>)$expressionList;
  List<LexIdentifierToken> ids = convertNameListToIdentifierList(nameList);
  $$ = new AMuAction(extractLexLocation((CmlLexeme)$start,(CmlLexeme)$end), 
		     ids, 
		     (List<PAction>)$actionList);
}
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
/* CHANSET NAMESET
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
/* CHANSET NAMESET
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
/* DEVIATION
 * CML_0:
 *   parametrisationList AT action
 * here:
 *   LPAREN parametrisationList AT action RPAREN
 */
| LPAREN parametrisationList AT action[pAction] RPAREN
{
  $$ = new AParametrisedAction(extractLexLocation((CmlLexeme)$LPAREN,
						  (CmlLexeme)$RPAREN),
			       (List<PParametrisation>)$parametrisationList,
			       (PAction)$pAction);
}
| LPAREN[start] parametrisationList[params] AT action[pAction] RPAREN[pEnd] LPAREN expressionList[args] RPAREN[end]
{
  AParametrisedAction action = new AParametrisedAction(extractLexLocation((CmlLexeme)$start,
									  (CmlLexeme)$pEnd),
						       (List<PParametrisation>)$params,
						       (PAction)$pAction);
  $$ = new AParametrisedInstantiatedAction(extractLexLocation((CmlLexeme)$start,
							      (CmlLexeme)$end),
					   action,
					   (List<PExp>)$args);
}
| LPAREN[start] singleTypeDeclarationList[decls] AT action[pAction] RPAREN LPAREN expressionList[args] RPAREN[end]
{
  $$ = new ADeclarationInstantiatedAction(extractLexLocation((CmlLexeme)$start, (CmlLexeme)$end),
                                          (List<? extends ATypeSingleDeclaration>)$decls,
                                          (PAction)$pAction,
                                          (List<PExp>)$args);
}
| SEMI replicationDeclaration[decl] AT action %prec U-SEMI
{
  PAction action = (PAction)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,action.getLocation());
  $$ = new ASequentialCompositionReplicatedAction(location,
                                                  (List<SSingleDeclaration>)$decl,
                                                  action);
}
| LRSQUARE replicationDeclaration[decl] AT action %prec U-LRSQUARE
{
  PAction action = (PAction)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,action.getLocation());
  $$ = new AExternalChoiceReplicatedAction(location,
                                           (List<SSingleDeclaration>)$decl,
                                           action);
}
| BARTILDEBAR replicationDeclaration[decl] AT action %prec U-BARTILDEBAR
{
  PAction action = (PAction)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1,action.getLocation());
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
/* CHANSET NAMESET
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
/* CHANSET NAMESET
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
/*
 * controlStatements
 */
| LET localDefList IN action[lAction] %prec S-LET
{
  PAction action = (PAction)$lAction;
  LexLocation location = extractLexLocation((CmlLexeme)$LET,
                                            action.getLocation());
  $$ = new ALetStatementAction(location,
                               action,
                               (List<? extends PDefinition>)$localDefList);
}
| LPAREN DCL assignmentDefList AT action[bAction] RPAREN
{
  LexLocation location = extractLexLocation((CmlLexeme)$LPAREN, (CmlLexeme)$RPAREN);
  ADeclareStatementAction dclStm =
    new ADeclareStatementAction(extractLexLocation((CmlLexeme)$DCL,
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
  LexLocation location = extractLexLocation((CmlLexeme)$ATOMIC,(CmlLexeme)$RPAREN);
  $$ = new AMultipleGeneralAssignmentStatementAction(location,
						     (List<? extends ASingleGeneralAssignmentStatementAction>)$assignStatementList);
}
/* call statements without assignment */
| dottedIdentifier LRPAREN
{
  /* --- TODO --- */
}
| dottedIdentifier LPAREN expressionList RPAREN
{
  /* --- TODO --- */
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
//   $$ = new AReturnStatementAction(extractLexLocation((CmlLexeme)$1), null);
// }
| RETURN LRPAREN
{
  $$ = new AReturnStatementAction(extractLexLocation((CmlLexeme)$1), null);
}
| RETURN LPAREN expression RPAREN
{
  PExp exp = (PExp)$expression;
  $$ = new AReturnStatementAction(extractLexLocation((CmlLexeme)$RETURN,
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
| dottedIdentifier COLONEQUALS NEW expression
{
  /* --- TODO --- */
  /* Need to rip out the path-based stuff here.
   * rule was: | path COLONEQUALS NEW path LRPAREN
   */
  ANewStatementAction stm = null;
  // these were Paths
  PExp target = (PExp)$dottedIdentifier; //should probably be more specific, typewise
  PExp newExp = (PExp)$expression;
  List<? extends PExp> args = null;
  LexLocation location = combineLexLocation(target.getLocation(),newExp.getLocation());
  //stm = new ANewStatementAction(location,
  //				  target.convertToStateDesignator(),
  //				  newExp.convertToName(),
  //				  args);
  $$ = stm;
}
// --- FIXME delete this; in with above rule; here for reference until merged ---
// | path COLONEQUALS NEW path LPAREN expressionList RPAREN
// {
//   ANewStatementAction stm = null;
//   try {
//     Path statePath = (Path)$1;
//     Path namePath = (Path)$4;
//     List<? extends PExp> args = (List<? extends PExp>)$expressionList;
//     LexLocation location = extractLexLocation(statePath.location,(CmlLexeme)$RPAREN);
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
  LexLocation location = extractLexLocation((CmlLexeme)$FOR,action.getLocation());
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
  LexLocation location = extractLexLocation((CmlLexeme)$FOR,action.getLocation());
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
  LexLocation location = extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  $$ = new AForSetStatementAction(location,
				  (PPattern)$pattern,
				  (PExp)$expression,
				  action);
}
| FOR IDENTIFIER EQUALS expression[start] TO expression[end] DO action[fAction]
{
  PAction action = (PAction)$fAction;
  LexLocation location = extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  $$ = new AForIndexStatementAction(location,
				    extractLexNameToken($IDENTIFIER),
				    (PExp)$start ,
				    (PExp)$end,
				    null,
				    action);
}
| FOR IDENTIFIER EQUALS expression[start] TO expression[end] BY expression[step] DO action[fAction]
{
  PAction action = (PAction)$fAction;
  LexLocation location = extractLexLocation((CmlLexeme)$FOR,action.getLocation());
  $$ = new AForIndexStatementAction(location,
				    extractLexNameToken($IDENTIFIER),
				    (PExp)$start ,
				    (PExp)$end,
				    (PExp)$step,
				    action);
}
| WHILE expression DO action[wAction]
{
  PAction action = (PAction)$wAction;
  LexLocation location = extractLexLocation((CmlLexeme)$WHILE,action.getLocation());
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
  LexLocation loc = extractLexLocation((CmlLexeme)$1, decl.getLocation());
  $$ = new AValParametrisation(loc, decl);
}
| RES singleTypeDeclaration
{
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)$singleTypeDeclaration;
  LexLocation loc = extractLexLocation((CmlLexeme)$1, decl.getLocation());
  $$ = new AResParametrisation(loc, decl);
}
| VRES singleTypeDeclaration
{
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)$singleTypeDeclaration;
  LexLocation loc = extractLexLocation((CmlLexeme)$1, decl.getLocation());
  $$ = new AVresParametrisation(loc, decl);
}
;

renameExpression :
  DLSQUARE renameList DRSQUARE
{
  $$ = new AEnumerationRenameChannelExp(extractLexLocation((CmlLexeme)$DLSQUARE,
							   (CmlLexeme)$DRSQUARE),
					(List<? extends ARenamePair>)$renameList);
}
| DLSQUARE expression[from] LARROW expression[to] BAR bindList DRSQUARE
{
  /* --- TODO --- */
  /* path elimination */
  ARenamePair pair = new ARenamePair(false,
				     (ANameChannelExp)$from,
				     (ANameChannelExp)$to);
  $$ = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)$DLSQUARE,
							     (CmlLexeme)$DRSQUARE),
					  pair,
					  (List<? extends PMultipleBind>)$bindList,
					  null);
}
| DLSQUARE expression[from] LARROW expression[to] BAR bindList AT expression[pred] DRSQUARE
{
  /* --- TODO --- */
  /* path elimination */
  ARenamePair pair = new ARenamePair(false,
				     (ANameChannelExp)$from,
				     (ANameChannelExp)$to);
  $$ = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)$DLSQUARE,
							     (CmlLexeme)$DRSQUARE),
					  pair,
					  (List<? extends PMultipleBind>)$bindList,
					  (PExp)$pred);
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
  /* --- TODO --- */
  /* path elimination: from, to */
  List<ARenamePair> renamePairs = new Vector<ARenamePair>();
  ARenamePair pair = new ARenamePair(false, 
				     (ANameChannelExp)$from, 
				     (ANameChannelExp)$to);
  renamePairs.add(pair);
  $$ = renamePairs;
}
| renameList COMMA expression[from] LARROW expression[to]
{
  /* --- TODO --- */
  /* path elimination: from, to */
  List<ARenamePair> renamePairs = (List<ARenamePair>)$1;
  ARenamePair pair = new ARenamePair(false, 
				     (ANameChannelExp)$from, 
				     (ANameChannelExp)$to);
  renamePairs.add(pair);
  $$ = renamePairs;
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
  List<AChannelNameDeclaration> chanNameDecls = new Vector<AChannelNameDeclaration>();
  LexLocation location = extractLexLocation((CmlLexeme)$CHANNELS);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
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
  List<AChannelNameDeclaration> chanNameDecls = (List<AChannelNameDeclaration>)$channelDef;
  LexLocation start = extractLexLocation((CmlLexeme)$1);
  LexLocation end = (chanNameDecls != null && chanNameDecls.size() > 0) ?
    chanNameDecls.get(chanNameDecls.size()-1).getLocation() : start;
  LexLocation location = combineLexLocation(start, end);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, start);
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
  List<AChannelNameDeclaration> decls = new Vector<AChannelNameDeclaration>();
  decls.add((AChannelNameDeclaration)$channelNameDecl);
  $$ = decls;
}
| channelDef[def] channelNameDecl
{
  List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)$def;
  decls.add((AChannelNameDeclaration)$channelNameDecl);
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
  LexIdentifierToken decl = extractLexIdentifierToken((CmlLexeme)$decl);
  LexLocation location = decl.getLocation();
  List<LexIdentifierToken> ids = new LinkedList<LexIdentifierToken>();
  ids.add(decl);
  ATypeSingleDeclaration singleTypeDeclaration =
    new ATypeSingleDeclaration(location, NameScope.GLOBAL, ids, null);
  AChannelNameDeclaration channelNameDecl =
    new AChannelNameDeclaration(location,
				NameScope.GLOBAL, 
				/* false, */
				/* null, */
				/* null, */
				singleTypeDeclaration);
  $$ = channelNameDecl;
}
| singleTypeDeclaration[decl]
{
  /* --- TODO --- */
  /* I (jwc) have no idea if this is correct.  When did
   * AChannelNameDefinition become AChannelNameDeclaration?
   */
  ATypeSingleDeclaration singleTypeDeclaration = (ATypeSingleDeclaration)$decl;
  AChannelNameDeclaration channelNameDecl =
    new AChannelNameDeclaration(singleTypeDeclaration.getLocation(),
			       NameScope.GLOBAL,
			       /* false, */
			       /* null, */
			       /* null, */
			       singleTypeDeclaration);
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
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$id);
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
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$id);
  ATypeSingleDeclaration decl = (ATypeSingleDeclaration)$decl;
  decl.setLocation(combineLexLocation(id.getLocation(), decl.getLocation()));
  decl.getIdentifiers().addFirst(id);
  $$ = decl;
};

chansetDefinitionParagraph :
  CHANSETS
{
  LexLocation loc = extractLexLocation((CmlLexeme)$CHANSETS);
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
  $$ = chansetParagraph;
}
| CHANSETS chansetDefinitionList[list]
{
  LexLocation loc = extractLexLocation((CmlLexeme)$CHANSETS);
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
| chansetDefinitionList[list] chansetDefinition[def]
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
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$id);
  PExp exp = (PExp)$exp;
  LexLocation location = combineLexLocation(id.getLocation(), exp.getLocation());
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
  LexLocation location = extractLexLocation((CmlLexeme)$INITIAL,def.getLocation());
  $$ = new AInitialParagraphDefinition(location,
				       NameScope.GLOBAL,
				       true,
				       getDefaultAccessSpecifier(false,false,null),
				       null/*Pass*/,
				       def);
}
;

typeDefs :
  TYPES
{
  LexLocation loc = extractLexLocation((CmlLexeme)$TYPES);
  $$ = new ATypesParagraphDefinition(loc,
				     NameScope.LOCAL,
				     false,
				     getDefaultAccessSpecifier(true, false, loc),
				     null/*Pass*/,
				     null);
}
| TYPES typeDefList
{
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)$typeDefList;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$TYPES),
                                       extractLastLexLocation(typeDefinitions));
  $$ = new ATypesParagraphDefinition(loc,
				     NameScope.LOCAL,
				     false,
				     getDefaultAccessSpecifier(true, false, loc),
				     null/*Pass*/,
				     typeDefinitions);
}
| TYPES typeDefList SEMI
{
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)$typeDefList;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$TYPES),
                                       extractLexLocation((CmlLexeme)$SEMI));
  $$ = new ATypesParagraphDefinition(loc,
				     NameScope.LOCAL,
				     false,
				     getDefaultAccessSpecifier(true, false, loc),
				     null/*Pass*/,
				     typeDefinitions);
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
  LexNameToken name = extractLexNameToken((CmlLexeme)$id);
  LexLocation location = null;
  location = combineLexLocation(name.getLocation(), ((PType)$type).getLocation());
  $$ = new ATypeDefinition(location,
			   NameScope.TYPENAME,
                           false/*Boolean used_*/,
			   null/*VDM ClassDef*/,
                           access,
                           (PType)$type,
			   null/*Pass*/,
                           null/*SInvariantType invType_*/,
                           null/*PPattern invPattern_*/,
                           null/*PExp invExpression_*/,
                           null /*AExplicitFunctionDefinition invdef_*/,
                           false/*Boolean infinite_*/,
			   name);
}
| qualifier IDENTIFIER[id] EQUALS type invariant
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qualifier;
  LexNameToken name = extractLexNameToken((CmlLexeme)$id);
  AInvariantDefinition inv = (AInvariantDefinition)$invariant;
  LexLocation location = null;
  location = combineLexLocation(name.getLocation(), inv.getLocation());
  $$ = new ATypeDefinition(location,
			   NameScope.TYPENAME,
			   false/*Boolean used_*/,
			   null/*VDM ClassDef*/,
			   access,
			   (PType)$type,
			   null/*Pass*/,
			   null/*SInvariantType invType_*/,
			   inv.getPattern()/*PPattern invPattern_*/,
			   inv.getExpression()/*PExp invExpression_*/,
			   null /*AExplicitFunctionDefinition invdef_*/,
			   false/*Boolean infinite_*/,
			   name);
}
| qualifier IDENTIFIER[id] DCOLON fieldList
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qualifier;
  LexNameToken name = extractLexNameToken((CmlLexeme)$id);
  CmlLexeme vdmrec = (CmlLexeme)$DCOLON;
  List<AFieldField> fields = (List<AFieldField>)$fieldList;
  LexLocation loc = combineLexLocation(name.getLocation(), extractLexLocation(vdmrec));
  ARecordInvariantType recType = new ARecordInvariantType(loc,
							  false,
							  null,
							  false,
							  null,
							  name,
							  fields,
							  true);
  $$ = new ATypeDefinition(loc, /* FIXME: this should end with the fieldList */
			   NameScope.GLOBAL,
			   false,
			   null/*VDM ClassDef*/,
			   access,
			   recType,
			   null/*Pass*/,
			   null,
			   null,
			   null,
			   null,
			   true,
			   name);
}
| qualifier IDENTIFIER[id] DCOLON fieldList invariant
{
  /* --- TODO --- */
  /* Not sure why, but we're not actually using the invariant
   * definition here.
   */
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qualifier;
  LexNameToken name = extractLexNameToken((CmlLexeme)$id);
  CmlLexeme vdmrec = (CmlLexeme)$DCOLON;
  List<AFieldField> fields = (List<AFieldField>)$fieldList;
  AInvariantDefinition inv = (AInvariantDefinition)$invariant;
  // TODO: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.
  LexLocation loc = combineLexLocation(name.getLocation(), extractLexLocation(vdmrec));
  ARecordInvariantType recType = new ARecordInvariantType(loc,
							  false,
							  null,
							  false,
							  null, /* invdef */
							  name,
							  fields,
							  true);
  $$ = new ATypeDefinition(loc,                                            
			   NameScope.TYPENAME,
			   false,
			   null/*VDM ClassDef*/,
			   access,
			   recType,
			   null/*Pass*/,
			   null,
			   null,
			   null,
			   null,
			   true,
			   name);
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
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  $$ = new AAccessSpecifierAccessSpecifier(new APrivateAccess(),
					   null, 
					   null);//, location);
}
| PROTECTED
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  $$ = new AAccessSpecifierAccessSpecifier(new AProtectedAccess(),
					   null, 
					   null);//, location);
}
| PUBLIC
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  $$ = new AAccessSpecifierAccessSpecifier(new APublicAccess(),
					   null, 
					   null);//, location);
}
| LOGICAL
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
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
  LexLocation loc = extractLexLocation((CmlLexeme)$start,(CmlLexeme)$end);
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
  $$ = new ARecordInvariantType(extractLexLocation((CmlLexeme)$start,(CmlLexeme)$end),
				false,
				null,//definitions
				false,//opaque
				null,//invdef
				extractLexNameToken($id),
				(List<? extends AFieldField>)$fieldList,
				false/*infinite_*/);
}
| type[first] BAR type[second] // unionType
{
  PType first = (PType)$first;
  PType second = (PType)$second;
  LexLocation loc = combineLexLocation(first.getLocation(), second.getLocation());
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
  LexLocation loc = combineLexLocation(first.getLocation(), second.getLocation());
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
  $$ = new AOptionalType(extractLexLocation((CmlLexeme)$start, (CmlLexeme)$end),
			 false,
			 null,
			 (PType)$otype);
}
| SETOF[prefix] type[stype]
{
  PType type = (PType)$stype;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$prefix),
				       type.getLocation());
  $$ = new ASetType(loc, false, null, type, false, false);
}
| SEQOF[prefix] type[stype]
{
  PType type = (PType)$stype;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$prefix),
				       type.getLocation());
  $$ = new ASeqSeqType(loc, false, null, type, false);
}
| SEQ1OF[prefix] type[stype]
{
  PType type = (PType)$stype;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$prefix),
				       type.getLocation());
  $$ = new ASeq1SeqType(loc, false, null, type, false);
}
| MAPOF[prefix] type[dom] TO type[rng]
{
  PType dom = (PType)$dom;
  PType rng = (PType)$rng;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$prefix),
				       rng.getLocation());
  $$ = new AMapMapType(loc, false, null, dom, rng, false);
}
| INMAPOF[prefix] type[dom] TO type[rng]
{
  PType dom = (PType)$dom;
  PType rng = (PType)$rng;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$prefix),
				       rng.getLocation());
  AMapMapType res = new AMapMapType(loc, false, null, dom, rng, false);
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
  /* --- TODO --- */
  /* Convert the dottedIdentifier into a LexNameToken?
   * Old code from the IDENTIFIER DOT IDENTIFIER production below.
   */
  /* LexNameToken name = extractLexNameToken((CmlLexeme)$3); */
  /* name = new LexNameToken(((CmlLexeme)$1).getValue(),name.getIdentifier()); */
  /* ANamedInvariantType type = new ANamedInvariantType(); */
  /* type.setLocation(name.getLocation()); */
  /* type.setName(name); */
  /* $$ = type; */
}
;

/* dottedIdentifier is used in many contexts; possibly we just want to
 * return a List<LexIdentifierToken> and convert at place of use.
 * Also need to check that we're not losing location info.
 */
dottedIdentifier :
  IDENTIFIER[id]
{
  /* --- TODO --- */
  List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$id);
  ids.add(id);
  $$ = ids;
}
| dottedIdentifier[ids] DOT IDENTIFIER[id]
{
  /* --- TODO --- */
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)$ids;
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$id);
  ids.add(id);
  $$ = ids;
}
| dottedIdentifier[ids] BACKTICK IDENTIFIER[id]
{
  /* --- TODO --- */
  List<LexIdentifierToken> ids = (List<LexIdentifierToken>)$ids;
  LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$id);
  ids.add(id);
  $$ = ids;
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
  type[dom] PLUSGT type[rng]
{
  PType domType = (PType)$dom;
  PType rngType = (PType)$rng;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, true, params, rngType);
}
| LRPAREN[dom] PLUSGT type[rng] // discretionary type
{
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)$dom), true);
  PType rngType = (PType)$rng;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, true, params, rngType);
}
;

totalFunctionType :
  type[dom] RARROW type[rng]
{
  PType domType = (PType)$dom;
  PType rngType = (PType)$rng;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, false, params, rngType);
}
| LRPAREN[dom] RARROW type[rng] // discretionary type
{
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)$dom), true);
  PType rngType = (PType)$rng;
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
  res.add((AFieldField)$field);
  $$ = res;
}
| fieldList[list] field
{
  List<AFieldField> res = (List<AFieldField>)$field;
  res.add((AFieldField)$list);
  $$ = res;
}
;

field :
  type
{
  $$ = new AFieldField(getDefaultAccessSpecifier(false,false,null),
		       null,
		       null,
		       (PType)$type,
		       false);
}
| IDENTIFIER[id] COLON type
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$id);
  PType type = (PType)$type;
  $$ = new AFieldField(getDefaultAccessSpecifier(false,false,null),
		       name,
		       null,
		       type,
		       false);
}
| IDENTIFIER[id] COLONDASH type
{
    LexNameToken name = extractLexNameToken((CmlLexeme)$id);
    PType type = (PType)$type;
    $$ = new AFieldField(getDefaultAccessSpecifier(false,false,null),
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
  LexLocation loc = extractLexLocation((CmlLexeme)$INV, exp.getLocation());
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, true, loc);
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
  LexLocation location = extractLexLocation((CmlLexeme)$VALUES);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
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
  LexLocation location = extractLexLocation((CmlLexeme)$VALUES,
                                            extractLastLexLocation(defs));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true,
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
//  LexLocation location = extractLexLocation((CmlLexeme)$VALUES, (CmlLexeme)$SEMI);
//  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
//  $$ = new AValueParagraphDefinition(location,
//                                     NameScope.NAMES,
//                                     false,
//                                     access,
//				     null,//Pass
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
| valueDefList[list] qualifiedValueDef[def]
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
  LexNameToken lnt = extractLexNameToken((CmlLexeme)$id);
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,
						  null,
						  false,
						  lnt,
						  false/*constrained*/);
  AValueDefinition vdef =
    new AValueDefinition(combineLexLocation(idp.getLocation(),
					    expression.getLocation()),
			    NameScope.LOCAL,
			    false, // used
			    getPrivateAccessSpecifier(false, false, lnt.location),
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
  LexNameToken lnt = extractLexNameToken((CmlLexeme)$id);
  PType type = (PType)$type;
  PExp expression = (PExp)$expression;
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,
						  null,
						  false,
						  lnt,
						  false/*constrained*/);
  // Build the resulting AValueDefinition
  AValueDefinition vdef =
    new AValueDefinition(combineLexLocation(idp.getLocation(),
					    expression.getLocation()),
			 NameScope.LOCAL,
			 false, // used
			 getPrivateAccessSpecifier(false, false, lnt.location),
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
  LexLocation loc = combineLexLocation(pattern.getLocation(),
				       expression.getLocation());
  AValueDefinition vdef =
    new AValueDefinition(loc,
			 NameScope.LOCAL,
			 false, // used
			 getPrivateAccessSpecifier(false, false, loc),
			 null, // pass
			 pattern,
			 expression,
			 null // defs
			 );
  vdef.setName(null);
  vdef.setType(null);
  $$ = vdef;
}
| patternLessID[pat] COLON type EQUALS expression
{
  PPattern pattern = (PPattern)$pat;
  PType type = (PType)$type;
  PExp expression = (PExp)$expression;
  LexLocation loc = combineLexLocation(pattern.getLocation(),
				       expression.getLocation());
  AValueDefinition vdef =
    new AValueDefinition(loc,
			 NameScope.LOCAL,
			 false, // used
			 getPrivateAccessSpecifier(false, false, loc),
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
  LexLocation location = extractLexLocation((CmlLexeme)$FUNCTIONS);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true,
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
  LexLocation location = extractLexLocation((CmlLexeme)$FUNCTIONS);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true,
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
//  LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$FUNCTIONS), extractLexLocation((CmlLexeme)$SEMI));
//  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
//  List<PDefinition> functionDefs = (List<PDefinition>)$functionDefList;
//  $$ = new AFunctionParagraphDefinition(location,
//                                        NameScope.GLOBAL,
//                                        false,
//                                        access,
//					null,//Pass
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
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qual;
  LexNameToken name = extractLexNameToken((CmlLexeme)$id);
  List<APatternListTypePair> paramPatterns = (List<APatternListTypePair>)$ptypes;
  List<APatternTypePair> result = (List<APatternTypePair>)$retvals;
  PExp preExp = (PExp)$pre;
  PExp postExp = (PExp)$post;
  LexLocation location = combineLexLocation(name.getLocation(), postExp.getLocation());
  AImplicitFunctionDefinition impFunc =
    new AImplicitFunctionDefinition(location, 
				    NameScope.LOCAL, 
				    false, 
				    access, 
				    null,//Pass
				    null, 
				    paramPatterns, 
				    result, 
				    preExp, 
				    postExp,
				    null/*LexNameToken measure*/);
  impFunc.setName(name);
  $$ = impFunc;
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
  LexNameToken name = extractLexNameToken((CmlLexeme)$id);
  /* --- TODO --- */
  /* We should be checking that the two IDENTIFIERS are equivalent
   */
  LexLocation loc = extractLexLocation((CmlLexeme)$id);
  AFunctionType ftype = (AFunctionType)$functionType;
  PExp functionBody = (PExp)$functionBody;
  List<List<PPattern>> args = (List<List<PPattern>>)$parameterList;
  AExplicitFunctionDefinition res = new AExplicitFunctionDefinition();
  res.setName(name);
  res.setLocation(loc);
  res.setType(ftype);
  res.setBody(functionBody);
  res.setMeasure((LexNameToken)$measureExpr);
  res.setParamPatternList(args);
  $$ = res;
}
;

parameterList :
  LRPAREN
{
  $$ =  new Vector<List<PPattern>>();
}
| LPAREN patternList[patList] RPAREN
{
  List<PPattern> patternList = (List<PPattern>)$patList;
  List<List<PPattern>> paramList = new Vector<List<PPattern>>();
  paramList.add(patternList);
  $$ = paramList;
}
| parameterList[paramList] LPAREN patternList[patList] RPAREN
{
  List<PPattern> patternList = (List<PPattern>)$patList;
  List<List<PPattern>> paramList = (List<List<PPattern>>)$paramList;
  paramList.add(patternList);
  $$ = paramList;
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
  List<AIdentifierTypePair> typePairs = new Vector<AIdentifierTypePair>();
  AIdentifierTypePair typePair =
    new AIdentifierTypePair(null, 
			    extractLexIdentifierToken((CmlLexeme)$id), 
			    (PType)$type);
  typePairs.add(typePair);
  $$ = typePairs;
}
| identifierTypePairList[idtpList] COMMA IDENTIFIER[id] COLON type
{
  List<AIdentifierTypePair> typePairs = (List<AIdentifierTypePair>)$idtpList;
  AIdentifierTypePair typePair =
    new AIdentifierTypePair(null, 
			    extractLexIdentifierToken((CmlLexeme)$id), 
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
  /* --- TODO --- */
  /* dottedIdentifier should be a list of LexIdentifierWhatsits, and
   * we need a LexName here.
   */
}
| /* empty */
{
  $$ = null;
}
;

operationDefs :
  OPERATIONS
{
  LexLocation location = extractLexLocation((CmlLexeme)$OPERATIONS);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true,
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
  LexLocation location = extractLexLocation((CmlLexeme)$OPERATIONS);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true,
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
  LexNameToken name = extractLexNameToken((CmlLexeme)$id);
  List<? extends APatternListTypePair> parameterPatterns =
    (List<? extends APatternListTypePair>)$ptypes;
   List<? extends AIdentifierTypePair> result =
     (List<? extends AIdentifierTypePair>)$retvals;
  List<? extends AExternalClause> externals = (List<? extends AExternalClause>)$exts;
  PExp precondition = (PExp)$pre;
  PExp postcondition = (PExp)$post;
  LexLocation location = combineLexLocation(name.location, postcondition.getLocation());
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
| qualifier[qual] IDENTIFIER[id] COLON operationType[opType] IDENTIFIER[checkId] parameterList[paramList] DEQUALS operationBody[body] preExpr_opt[pre] postExpr_opt[post]
{
  /* --- TODO --- */ 
  /* We shold check id against checkId for equality */
  LexNameToken name = extractLexNameToken($id); 
  SStatementAction body = (SStatementAction)$body;
  LexLocation loc = extractLexLocation(name.location,
				       body.getLocation());
  eu.compassresearch.ast.definitions.AExplicitOperationDefinition res =
    new eu.compassresearch.ast.definitions.AExplicitOperationDefinition();
  res.setLocation(loc);
  res.setAccess((AAccessSpecifierAccessSpecifier)$qual);
  res.setName(name);
  res.setType((PType)$opType);
  res.setParameterPatterns((List<? extends PPattern>)$paramList);
  res.setBody(body);
  res.setPrecondition((PExp)$pre);
  res.setPostcondition((PExp)$post);
  res.setIsConstructor(false);
  $$ = res;
}
;

operationType :
  type[dom] DEQRARROW type[rng]
{
  PType dom = (PType)$dom;
  PType rng = (PType)$rng;
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  $$ = new AOperationType(extractLexLocation(dom.getLocation(), rng.getLocation()),
			  false,
			  new LinkedList<PDefinition>(),
			  types,
			  rng);
}
| LRPAREN[dom] DEQRARROW type[rng]
{
  PType dom = new AVoidType(extractLexLocation((CmlLexeme)$dom), true);
  PType rng = (PType)$rng;
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  $$ = new AOperationType(extractLexLocation(dom.getLocation(), rng.getLocation()),
			  false,
			  new LinkedList<PDefinition>(),
			  types,
			  rng);
}
| type[dom] DEQRARROW LRPAREN[rng]
{
  PType dom = (PType)$dom;
  PType rng = new AVoidType(extractLexLocation((CmlLexeme)$rng), true);
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  $$ = new AOperationType(extractLexLocation(dom.getLocation(), rng.getLocation()),
			  false,
			  new LinkedList<PDefinition>(),
			  types,
			  rng);
}
| LRPAREN[dom] DEQRARROW LRPAREN[rng]
{
  PType dom = new AVoidType(extractLexLocation((CmlLexeme)$dom), true);
  PType rng = new AVoidType(extractLexLocation((CmlLexeme)$rng), true);
  List<PType> types = new LinkedList<PType>();
  types.add(dom);
  $$ = new AOperationType(extractLexLocation(dom.getLocation(), rng.getLocation()),
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
  $$ = new ASubclassResponsibilityAction(extractLexLocation((CmlLexeme)$1));
}
| NOTYETSPEC
{
  $$ = new ANotYetSpecifiedAction(extractLexLocation((CmlLexeme)$1), null, null);
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
  /* --- TODO --- */
  LexToken mode = (LexToken)$mode;
  List<? extends LexNameToken> ids = null;
  // FIXME: dottedIdentifier
  //ids.add(convertDottedIdentifierToLexNameToken((List<? extends LexNameToken>)$id));
  $$ = new AExternalClause(mode, ids, null);
}
| mode dottedIdentifier[id] COLON type
{
  /* --- TODO --- */
  LexToken mode = (LexToken)$mode;
  List<? extends LexNameToken> ids = null;
  // FIXME: dottedIdentifier
  //ids.add(convertDottedIdentifierToLexNameToken((List<? extends LexNameToken>)$id));
  $$ = new AExternalClause(mode, ids, (PType)$type);
}
| varInformation[info] COMMA dottedIdentifier[id]
{
  /* --- TODO --- */
  AExternalClause info = (AExternalClause)$info;
  List<? extends LexNameToken> ids = info.getIdentifiers();
  // FIXME: dottedIdentifier
  //ids.add(convertDottedIdentifierToLexNameToken((List<? extends LexNameToken>)$id));
  $$ = info;
}
| varInformation[info] COMMA dottedIdentifier[id] COLON type
{
  /* --- TODO --- */
  AExternalClause info = (AExternalClause)$info;
  List<? extends LexNameToken> ids = info.getIdentifiers();
  // FIXME: dottedIdentifier
  //ids.add(convertDottedIdentifierToLexNameToken((List<? extends LexNameToken>)$id));
  $$ = info;
}
;

mode :
  RD
{
  $$ = new LexToken(extractLexLocation((CmlLexeme)$RD), VDMToken.READ);
}
| WR
{
  $$ = new LexToken(extractLexLocation((CmlLexeme)$WR), VDMToken.WRITE);
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
    state.setLocation(extractLexLocation((CmlLexeme)$STATE));
    $$  = state;
}
| STATE stateDefList
{
    AStateParagraphDefinition state = (AStateParagraphDefinition)$stateDefList;
    state.setLocation(extractLexLocation((CmlLexeme)$STATE,
					 extractLastLexLocation(state.getStateDefs())));
    $$ = state;
}
//| STATE stateDefList SEMI
//{
//    AStateParagraphDefinition state = (AStateParagraphDefinition)$stateDefList;
//    state.setLocation(extractLexLocation((CmlLexeme)$STATE,(CmlLexeme)$SEMI));
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
| stateDefList[list] stateDef
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
  LexLocation location = extractLexLocation((CmlLexeme)$INV, exp.getLocation());
  $$ = new AClassInvariantDefinition(location,
				     NameScope.GLOBAL,
				     true,
				     null/*access*/,
				     null/*Pass*/,
				     exp);
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
  $$ = new ABracketedExp(extractLexLocation((CmlLexeme)$s, (CmlLexeme)$e), (PExp)$exp);
}
| SELF
{
  /* --- TODO --- */
}
| IDENTIFIER
{
  /* --- TODO --- */
}
| IDENTIFIER TILDE
{
  /* --- TODO --- */
}
| expression DOT IDENTIFIER
{
  /* --- TODO --- */
}
| expression BACKTICK IDENTIFIER
{
  /* --- TODO --- */
}
| expression DOTHASH NUMERAL
{
  /* --- TODO --- */
}
| expression LRPAREN
{
  /* --- TODO --- */
}
| expression LPAREN expressionList RPAREN
{
  /* --- TODO --- */
}
| expression LPAREN expression ELLIPSIS expression RPAREN
{
  /* --- TODO --- */
}
| expression DOT matchValue
{
  /* --- TODO --- */
}
/* communication structure */
| expression BANG IDENTIFIER
{
  /* --- TODO --- */
}
| expression BANG matchValue
{
  /* --- TODO --- */
}
| expression QUESTION pattern
{
  /* --- TODO --- */
}
| expression QUESTION setBind
{
  /* --- TODO --- */
}
/* end communication structure*/
| LET localDefList[defs] IN expression[exp] %prec LET
{
  List<PDefinition> defs = (List<PDefinition>)$defs;
  PExp exp = (PExp)$exp;
  LexLocation loc = extractLexLocation((CmlLexeme)$LET, exp.getLocation());
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
  LexLocation loc = combineLexLocation(extractLexLocation(forall), exp.getLocation());
  $$ = new AForAllExp(loc, binds, exp);
}
| EXISTS bindList[binds] AT expression[exp] %prec EXISTS
{
  CmlLexeme exists = (CmlLexeme)$EXISTS;
  List<PMultipleBind> binds = (List<PMultipleBind>)$binds;
  PExp exp = (PExp)$exp;
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  $$ = new AExistsExp(loc, binds, exp);
}
| EXISTS1 bind AT expression[exp] %prec EXISTS1
{
  CmlLexeme exists = (CmlLexeme)$EXISTS1;
  PBind bind = (PBind)$bind;
  PExp exp = (PExp)$exp;
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  $$ = new AExists1Exp(loc, bind, exp, null);
}
| IOTA bind AT expression[exp] %prec IOTA
{
  CmlLexeme iota = (CmlLexeme)$IOTA;
  PBind bind = (PBind)$bind;
  PExp exp = (PExp)$exp;
  LexLocation loc = combineLexLocation(extractLexLocation(iota), exp.getLocation());
  $$ = new AIotaExp(loc, bind, exp);
}
/* set enumeration */
| LCURLY RCURLY
{
  LexLocation loc = extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation(loc);
  $$ = res;
}
| LCURLY expressionList[list] RCURLY
{
  LexLocation location = extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  List<PExp> members = (List<PExp>)$list;
  $$ = new ASetEnumSetExp(location, members);
}
/* set comprehensions */
| LCURLY expression[exp] BAR bindList[binds] RCURLY
{
  PExp exp = (PExp)$exp;
  List<PMultipleBind> binds = (List<PMultipleBind>)$binds;
  LexLocation loc = extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new ASetCompSetExp(loc, exp, binds, null);
}
| LCURLY expression[exp] BAR bindList[binds] AT expression[pred] RCURLY
{
  PExp exp = (PExp)$exp;
  List<PMultipleBind> binds = (List<PMultipleBind>)$binds;
  PExp pred = (PExp)$pred;
  LexLocation loc = extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new ASetCompSetExp(loc, exp, binds, pred);
}
/* set range expression */
| LCURLY expression[start] ELLIPSIS expression[end] RCURLY
{
  PExp start = (PExp)$start;
  PExp end = (PExp)$end;
  LexLocation loc = extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
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
  LexLocation loc = extractLexLocation((CmlLexeme)$LRSQUARE);
  $$ = new ASeqEnumSeqExp(loc, exps);
}
| LSQUARE expressionList[list] RSQUARE
{
  List<PExp> exps = (List<PExp>)$list;
  LexLocation loc = extractLexLocation((CmlLexeme)$LSQUARE, (CmlLexeme)$RSQUARE);
  $$ = new ASeqEnumSeqExp(loc, exps);
}
/* sequence comprehensions */
| LSQUARE expression[exp] BAR setBind[bind] RSQUARE
{
  PExp exp = (PExp)$exp;
  ASetBind binds = (ASetBind)$bind;
  LexLocation loc = extractLexLocation((CmlLexeme)$LSQUARE, (CmlLexeme)$RSQUARE);
  $$ = new ASeqCompSeqExp(loc, exp, binds, null);
}
| LSQUARE expression[exp] BAR setBind[bind] AT expression[pred] RSQUARE
{
  PExp exp = (PExp)$exp;
  ASetBind binds = (ASetBind)$bind;
  PExp pred = (PExp)$pred;
  LexLocation loc = extractLexLocation((CmlLexeme)$LSQUARE, (CmlLexeme)$RSQUARE);
  $$ = new ASeqCompSeqExp(loc, exp, binds, pred);
}
/* map enumerations */
| EMPTYMAP // LRCURLY
{
  LexLocation loc = extractLexLocation((CmlLexeme)$EMPTYMAP);
  $$ = new AMapEnumMapExp(loc, new LinkedList<AMapletExp>());
}
| LCURLY mapletList[list] RCURLY
{
  List<AMapletExp> maplets = (List<AMapletExp>)$list;
  LexLocation loc = extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new AMapEnumMapExp(loc, maplets);
}
/* map comprehensions */
| LCURLY maplet BAR bindList RCURLY
{
  AMapletExp maplet = (AMapletExp)$maplet;
  List<PMultipleBind> binds = (List<PMultipleBind>)$bindList;
  LexLocation loc = extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new AMapCompMapExp(loc, maplet, binds, null);
}
| LCURLY maplet BAR bindList AT expression[exp] RCURLY
{
  AMapletExp maplet = (AMapletExp)$maplet;
  List<PMultipleBind> binds = (List<PMultipleBind>)$bindList;
  PExp pred = (PExp)$exp;
  LexLocation loc = extractLexLocation((CmlLexeme)$LCURLY, (CmlLexeme)$RCURLY);
  $$ = new AMapCompMapExp(loc, maplet, binds, pred);
}
/* tuple constructor */
| MKUNDER LPAREN expressionList[list] RPAREN
{
  List<PExp> exprs = (List<PExp>)$list;
  LexLocation loc = extractLexLocation((CmlLexeme)$MKUNDER, (CmlLexeme)$RPAREN);
  $$ = new ATupleExp(loc, exprs);
}
/* recordConstructor */
| MKUNDERNAME LPAREN expressionList[list] RPAREN
{
  LexNameToken name = extractNameFromUNDERNAMEToken((CmlLexeme)$MKUNDERNAME);
  List<PExp> exprs = (List<PExp>)$list;
  LexLocation loc = extractLexLocation(((CmlLexeme)$MKUNDERNAME), (CmlLexeme)$RPAREN);
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
  LexLocation loc = extractLexLocation((CmlLexeme)$LAMBDA, body.getLocation());
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
| ISUNDERNAME LPAREN expression RPAREN
{
  /* --- TODO --- */
}
| ISUNDER basicType LPAREN expression RPAREN
{
  /* --- TODO --- */
}
| ISUNDER LPAREN expression COMMA type RPAREN
{
  /* --- TODO --- */
}
/* precondition expression */
/* (JWC) first parameter of the precondition expression is the
 * function that we want the precondition of
 */
| PREUNDER LPAREN expressionList[list] RPAREN
{
  /* --- TODO --- */
  /* This instantiation can't be correct.
   */
  List<PExp> exprs = (List<PExp>)$list;
  PExp function = null;
  LexLocation loc = extractLexLocation((CmlLexeme)$PREUNDER, (CmlLexeme)$RPAREN);
  $$ = new APreExp(loc, function, exprs);
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
  /* --- TODO --- */
  LexLocation loc = extractLexLocation((CmlLexeme)$ISOFCLASS, (CmlLexeme)$RPAREN);
  List<LexIdentifierToken> dotted = (List<LexIdentifierToken>)$id;
  // FIXME
  LexNameToken name = null;
  //LexNameToken name = convertDottedIdentifierToLexNameToken(dotted);
  $$ = new AIsOfClassExp(loc,
			 name,
			 (PExp)$exp);
}
/* chanset expressions */
| LCURLYBAR expressionList[list] BARRCURLY
{
  /* --- TODO --- */
  LexLocation loc = extractLexLocation((CmlLexeme)$LCURLYBAR, (CmlLexeme)$BARRCURLY);
  List<PExp> exprs = (List<PExp>)$list;
  // FIXME
  List<LexIdentifierToken> ids = null;
  //List<LexIdentifierToken> ids = convertExpressionListToLexNameTokenList(exprs);    
  $$ = new AEnumChansetSetExp(loc, ids);
}
| LCURLYBAR expression BAR bindList BARRCURLY
{
  /* --- TODO --- */
  /* below is from the old path-based code
   */
  //LexLocation loc = extractLexLocation((CmlLexeme)$LCURLYBAR, (CmlLexeme)$BARRCURLY);
  //ANameChannelExp chanNameExp =
  //  (ANameChannelExp)((Path)$path).convertToChannelNameExpression();
  //List<PMultipleBind> bindings = (List<PMultipleBind>)$bindList;
  //$$ = new ACompChansetSetExp(loc, chanNameExp , bindings, null);
}
| LCURLYBAR expression BAR bindList AT expression[exp] BARRCURLY
{
  /* --- TODO --- */
  //LexLocation loc = extractLexLocation((CmlLexeme)$LCURLYBAR, (CmlLexeme)$BARRCURLY);
  //ANameChannelExp chanNameExp = (ANameChannelExp)((Path)$path).convertToChannelNameExpression();
  //List<PMultipleBind> bindings = (List<PMultipleBind>)$bindList;
  //PExp pred = (PExp)$exp;
  //$$ = new ACompChansetSetExp(loc, chanNameExp, bindings, pred);
}
/* chanset expressions end */
;


/* symbolic literals */
booleanLiteral:
  FALSE
{
  $$ = new LexBooleanToken(VDMToken.FALSE, extractLexLocation((CmlLexeme)$FALSE));
}
| TRUE
{
  $$ = new LexBooleanToken(VDMToken.TRUE, extractLexLocation((CmlLexeme)$TRUE));
}
;

characterLiteral :
  CHAR_LIT
{
  CmlLexeme lex = (CmlLexeme)$CHAR_LIT;
  LexLocation loc = extractLexLocation(lex);
  String res = lex.getValue();
  res = res.replace("'", ""); // FIXME: what is this for? is it correct?
  $$ = new LexCharacterToken(convertEscapeToChar(res), loc);
}
;

nilLiteral :
  NIL
{
  $$ = new LexKeywordToken(VDMToken.NIL, extractLexLocation((CmlLexeme)$NIL));
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
  CmlLexeme lexeme = (CmlLexeme)$HEX_LITERAL;
  LexLocation loc = extractLexLocation(lexeme);
  BigInteger b = new BigInteger(lexeme.getValue().substring(2), 16);
  $$ = new LexIntegerToken(b.longValue(), loc);
}
| DECIMAL
{
  CmlLexeme lexeme = (CmlLexeme)$DECIMAL;
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
;

quoteLiteral :
  QUOTE_LITERAL
;

/* symbolic literals end*/

localDefList :
  localDef
| localDefList COMMA localDef
;

localDef :
  valueDef
| explicitFunctionDef
;

ifExpr :
  IF expression THEN expression elseExprs
;

elseExprs :
  ELSE expression
| ELSEIF expression THEN expression elseExprs
;

casesExpr :
  CASES expression COLON casesExprAltList END
| CASES expression COLON casesExprAltList COMMA OTHERS RARROW expression END
;

casesExprAltList :
  casesExprAlt
| casesExprAltList COMMA casesExprAlt
;

casesExprAlt :
  patternList RARROW expression
;

unaryExpr :
  PLUS expression %prec U-PLUS
| MINUS expression %prec U-MINUS
| ABS expression
| FLOOR expression
| NOT expression
| CARD expression
| POWER expression
| DUNION expression
| DINTER expression
| HD expression
| TL expression
| LEN expression
| ELEMS expression
| INDS expression
| REVERSE expression
| CONC expression
| DOM expression
| RNG expression
| MERGE expression
| INVERSE expression
;

binaryExpr :
  expression PLUS expression
| expression STAR expression
| expression MINUS expression
| expression DIV expression
| expression SLASH expression
| expression REM expression
| expression MOD expression
| expression LT expression
| expression LTE expression
| expression GT expression
| expression GTE expression
| expression EQUALS expression
| expression NEQ expression
| expression OR expression
| expression AND expression
| expression EQRARROW expression
| expression LTEQUALSGT expression
| expression INSET expression
| expression NOTINSET expression
| expression SUBSET expression
| expression PSUBSET expression
| expression UNION expression
| expression BACKSLASH expression
| expression INTER expression
| expression CARET expression
| expression DPLUS expression
| expression MUNION expression
| expression LTCOLON expression
| expression LTDASHCOLON expression
| expression COLONGT expression
| expression COLONDASHGT expression
| expression COMP expression
| expression DSTAR expression
;

mapletList :
  maplet
| mapletList COMMA maplet
;

maplet :
  expression BARRARROW expression
;

nonDeterministicAltList :
  expression RARROW action
| nonDeterministicAltList BAR expression RARROW action
;

assignmentDefList :
  assignmentDef
| assignmentDefList COMMA assignmentDef
;

assignmentDef :
  IDENTIFIER COLON type
| IDENTIFIER COLON type COLONEQUALS expression
/*(AKM)
 *FIXME: This is probably not going to work since you can't see the difference
 *       Between 'id : type in exp' and 'id : type := exp'
 *
 */
| IDENTIFIER COLON type IN expression
;

/* Typechecker will have to ensure that all of the assigns in the list
 * are genuine assigments rather than operation calls.
 */
assignStatementList :
  assignStatement
| assignStatementList[list] SEMI assignStatement
;

assignStatement :
/* DEVIATION
 * PATH
 * CML_0:
 *   stateDesignator ':=' expression
 * here:
 *   expression ':=' expression
 *
 * Kill 'em all and let the typechecker sort them out
 */
  dottedIdentifier COLONEQUALS expression
;

ifStatement :
  IF expression THEN action elseStatements ELSE action
| IF expression THEN action ELSE action
;

elseStatements :
  ELSEIF expression THEN action
| elseStatements ELSEIF expression THEN action
;

casesStatement :
  CASES expression COLON casesStatementAltList END
| CASES expression COLON casesStatementAltList COMMA OTHERS RARROW action END
;

casesStatementAltList :
  casesStatementAlt
| casesStatementAltList COMMA casesStatementAlt
;

casesStatementAlt :
  patternList RARROW action
;

implicitOperationBody :
  externals_opt preExpr_opt postExpr
;

pattern :
  patternIdentifier
| patternLessID
;

patternLessID :
  matchValue
/* tuple pattern */
| MKUNDER LPAREN patternList COMMA pattern RPAREN
/* record patterns */
/* DEVIATION
 * CML_0:
 *   MKUNDER name LPAREN expression RPAREN
 * here:
 *   MKUNDERNAME LPAREN expression RPAREN
 *
 */
| MKUNDERNAME LRPAREN
| MKUNDERNAME LPAREN patternList RPAREN
;

patternList :
  pattern
| patternList COMMA pattern
;

patternIdentifier :
  IDENTIFIER
/* "don't care" identifier */
| MINUS
;

matchValue :
/* symbolic literal patterns*/
  numericLiteral
| booleanLiteral
| nilLiteral
| characterLiteral
| textLiteral
| quoteLiteral
| LPAREN expression RPAREN
;

bind :
  setBind
| typeBind
;

setBind :
  pattern INSET expression
;

typeBind :
  pattern COLON type
;

bindList :
  multipleBind
| bindList COMMA multipleBind
;

multipleBind :
  multipleSetBind
| multipleTypeBind
;

multipleSetBind :
  patternList INSET expression
;

multipleTypeBind :
  patternList COLON type
;

typeBindList :
  typeBind
| typeBindList COMMA typeBind
;

// **********************
// *** END OF GRAMMAR ***
// **********************
