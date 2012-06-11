%require "2.5"
//%define api.pure
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
    import org.overture.ast.definitions.*;
    import org.overture.ast.declarations.*;
    import org.overture.ast.expressions.*;
    import org.overture.ast.statements.*;
    import org.overture.ast.actions.*;
    import org.overture.ast.process.*;
    import org.overture.ast.patterns.*;
    import org.overture.ast.program.*;
    import org.overture.ast.types.*;
    import org.overturetool.vdmj.lex.*;
    import org.overturetool.vdmj.typechecker.*;
    import org.overture.ast.node.*;
    import org.overture.transforms.*;
    import org.overturetool.util.*;
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

   // **************************
  // *** PARSER INTERNAL DS ***
  // **************************
  class PatternWithVDMType {
    public PType type;
    public PPattern pattern;
  };

    // ************************
    // *** MEMBER VARIABLES ***
    // ************************

    //private List<PDefinition> documentDefs = new Vector<PDefinition>();
    private ASourcefileSourcefile currentSourceFile = null;

    // *************************
    // *** PRIVATE OPERATIONS ***
    // *************************
     
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

    private LexLocation combineLexLocation(LexLocation start, LexLocation end)
    {
      return new LexLocation(currentSourceFile.getFile(), "Default",
			     start.startLine, start.startPos, 
			     end.endLine, end.endPos,0,0);
    }


    private LexLocation extractLastLexLocation ( List<?> fields )
    {
      try 
	{
	  Object o = fields.get(0);
	  Class<?> clz = o.getClass();
	  
	  Method locMethod = clz.getMethod("getLocation", new Class<?>[] {} );
	  
	  LexLocation candidate = (LexLocation)locMethod.invoke( o, null );
	  for(Object p : fields)
	    {
	      LexLocation pLoc = (LexLocation)locMethod.invoke( o, null );
	      if (pLoc.endOffset > candidate.endOffset)
		candidate = pLoc;
	    }
	  return candidate;
	} catch (Exception e)
	    {
	      throw new RuntimeException(e);
	    }
    }

    private LexLocation extractFirstLexLocation ( List<?> fields )
    {
      try 
	{
	  Object o = fields.get(0);
	  Class<?> clz = o.getClass();
	  
	  Method locMethod = clz.getMethod("getLocation", new Class<?>[] {} );
	  
	  LexLocation candidate = (LexLocation)locMethod.invoke( o, null );
	  for(Object p : fields)
	    {
	      LexLocation pLoc = (LexLocation)locMethod.invoke( o, null );
	      if (pLoc.startOffset < candidate.startOffset)
		candidate = pLoc;
	    }
	  return candidate;
	} catch (Exception e)
	    {
	      throw new RuntimeException(e);
	    }
    }

    private< T extends PPattern> LexLocation extractLexLeftMostFromPatterns(List<T> ptrns )
    {
      LexLocation candidate = ptrns.get(0).getLocation();
      for(PPattern p : ptrns)
	if (p.getLocation().endOffset > candidate.endOffset)
	  candidate = p.getLocation();
      return candidate;
    }
    //    private LexToken makeLexToken()
    
    private LexNameToken extractLexNameToken(CmlLexeme lexeme)
    {
      return new LexNameToken("Default",lexeme.getValue(), extractLexLocation(lexeme),false, true);
    }

    private LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme)
    {
      return new LexIdentifierToken(lexeme.getValue(), false, extractLexLocation(lexeme));
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
	}
	else {
      
	    CmlLexer scanner = null;
	    try {
	      String filePath = args[0];
	      ClonableFile file = new ClonableFile(filePath); 
	      ASourcefileSourcefile currentSourceFile = new ASourcefileSourcefile();
	      currentSourceFile.setName(file.getName());
	      currentSourceFile.setFile(file);
	      scanner = new CmlLexer( new java.io.FileReader(file) );
	      CmlParser cmlParser = new CmlParser(scanner);
	      cmlParser.setDocument(currentSourceFile);
	      //cmlParser.setDebugLevel(1);
	  
	      //do {
	      //System.out.println(scanner.yylex());
	      boolean result = cmlParser.parse();
	      if (result){
		System.out.println("parsed!");
				
		DotGraphVisitor dgv = new DotGraphVisitor();
		INode node = cmlParser.getDocument();

		node.apply(dgv,"");
				
		File dotFile = new File("generatedAST.gv");
		java.io.FileWriter fw = new java.io.FileWriter(dotFile);
		fw.write(dgv.getResultString());
		fw.close();

		//System.out.println(dgv.getResultString());
	    
	      }
	      else
		{
		  System.out.println("Not parsed!");
		  System.exit(-1);
		}
		
	      //} while (!scanner.zzAtEOF);

	    }
	    catch (java.io.FileNotFoundException e) {
		System.out.println("File not found : \""+args[0]+"\"");
		System.exit(-2);
	    }
	    catch (java.io.IOException e) {
		System.out.println("IO error scanning file \""+args[0]+"\"");
		System.out.println(e);
		System.exit(-3);
	    }
	    catch (Exception e) {
		System.out.println("Unexpected exception:");
		e.printStackTrace();
		System.exit(-4);
	    }
	    
	}
    }
    
 }


/* General notes/FIXMEs:
 *
 * 1) At the moment there are a lot of shift/reduce conflicts "hidden"
 * by setting a left or right associativity.  For some this is surely
 * correct, for others it's a bit suspect
 *
 * 2) We need to go over the usage of [] and {} in the
 * CMLLanguageDef.pdf document and double-check their semantics.
 *
 */

%token CLASS END PROCESS INITIAL EQUALS AT BEGIN CSP_ACTIONS CSPSEQ CSPINTCH CSPEXTCH CSPLCHSYNC CSPRCHSYNC CSPINTERLEAVE CSPHIDE LPAREN RPAREN CSPRENAME LSQUARE RSQUARE CSPSKIP CSPSTOP CSPCHAOS CSPDIV CSPWAIT RARROW LCURLY RCURLY CSPAND BAR DBAR CHANNELS CHANSETS TYPES SEMI VDMRECORDDEF VDMCOMPOSE OF VDMTYPEUNION STAR TO VDMINMAPOF VDMMAPOF VDMSEQOF VDMSEQ1OF VDMSETOF VDMPFUNCARROW VDMTFUNCARROW VDMUNITTYPE VDMTYPENCMP DEQUALS VDMINV VALUES FUNCTIONS PRE POST MEASURE VDM_SUBCLASSRESP VDM_NOTYETSPEC OPERATIONS VDM_FRAME VDM_RD VDM_WR STATE LET IN IF THEN ELSEIF ELSE CASES OTHERS PLUS MINUS ABS FLOOR NOT CARD POWER DUNION DINTER HD TL LEN ELEMS INDS REVERSE DCONC DOM RNG MERGE INVERSE ELLIPSIS MAPLETARROW MKUNDER MKUNDERNAME DOT DOTHASH NUMERAL LAMBDA NEW SELF ISUNDER PREUNDER ISOFCLASS BACKTICK TILDE DCL ASSIGN ATOMIC OPERATIONARROW RETURN VDMDONTCARE IDENTIFIER
%token DIVIDE DIV REM MOD LT LTE GT GTE NEQ OR AND IMPLY BIMPLY INSET NOTINSET SUBSET PROPER_SUBSET UNION SETDIFF INTER CONC OVERWRITE MAPMERGE DOMRES VDM_MAP_DOMAIN_RESTRICT_BY RNGRES RNGSUB COMP ITERATE FORALL EXISTS EXISTS1 STRING


%token HEX_LITERAL

%token AMP CSPBARGT CSPLSQUAREBAR DLSQUARE DRSQUARE CSPBARRSQUARE COMMA CSPSAMEAS CSPLSQUAREDBAR CSPDBARRSQUARE CSPDBAR COLON CHANSET_SETEXP_BEGIN CHANSET_SETEXP_END CSP_CHANNEL_READ CSP_CHANNEL_WRITE CSP_OPS_COM CSP_CHANNEL_DOT CSP_SLASH CSP_BACKSLASH CSPLSQUAREGT CSP_LSQUARE CSP_RSQUARE CSP_GT CSP_ENDBY CSP_STARTBY
%token TBOOL TNAT TNAT1 TINT TRAT TREAL TCHAR TTOKEN PRIVATE PROTECTED PUBLIC LOGICAL

%token nameset namesetExpr typeVarIdentifier  
 //localDef
 //quoteLiteral

 /* type op precidence */
%left VDMSEQOF

/* CSP ops and more */
%right CSPSEQ CSPINTCH CSPEXTCH CSPLCHSYNC CSPRCHSYNC CSPINTERLEAVE CSPHIDE CSPAND AMP RARROW DLSQUARE CSPBARGT CSPLSQUAREBAR CSPLSQUAREGT CSPBARRSQUARE LSQUARE RSQUARE CSPRENAME VDMTYPEUNION STAR VDMSETOF VDMSEQ1OF VDMMAPOF VDMINMAPOF VDMPFUNCARROW VDMTFUNCARROW TO OF NEW ASSIGN CSP_SLASH CSP_BACKSLASH CSP_LSQUARE CSP_RSQUARE CSP_GT CSP_ENDBY CSP_STARTBY CSPLSQUAREDBAR CSPDBARRSQUARE CSPDBAR

%right ELSE ELSEIF

/* unary ops */
%right UPLUS UMINUS ABS FLOOR NOT CARD POWER DUNION DINTER HD TL LEN ELEMS INDS REVERSE DCONC DOM RNG MERGE INVERSE 
/* binary ops */
%left PLUS MINUS DIVIDE DIV REM MOD LT LTE GT GTE EQUALS NEQ OR AND IMPLY BIMPLY INSET NOTINSET SUBSET PROPER_SUBSET UNION SETDIFF INTER CONC OVERWRITE MAPMERGE DOMRES VDM_MAP_DOMAIN_RESTRICT_BY RNGRES RNGSUB COMP ITERATE IN DOT DOTHASH

 /* other hacks */
%right LPAREN

%start sourceFile

%%

/* 2 CML Grammar */

sourceFile
: programParagraphList                            
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

programParagraphList: 
  programParagraph                                
  {  
      List<PDeclaration> programParagraphList = 
	  new Vector<PDeclaration>();
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

programParagraph 
: classDecl                                       { $$ = $1; }
| processDecl                                     { $$ = $1; }
| channelDecl                                     { $$ = $1; }
| chansetDecl                                     { $$ = $1; }
//| globalDecl                                     { $$ = $1; } //TODO
 ;

/* 2.1 Classes */
classDecl 
: CLASS IDENTIFIER EQUALS classBody
{ 
  AClassbodyDefinition c = new AClassbodyDefinition();
  Position startPos =  ((CmlLexeme)$1).getStartPos();
  Position endPos = ((CmlLexeme)$3).getEndPos(); // TODO Fix me, the ending position is the 
  LexNameToken lexName = extractLexNameToken((CmlLexeme)$2); 
  LexLocation loc = new LexLocation(null,"DEFAULT", 
				    startPos.line, 
				    startPos.column, 
				    endPos.line, 
				    endPos.column, 
				    startPos.offset, endPos.offset);
  
  c.setLocation(loc); 
  c.setName(lexName);
  c.setDeclarations( (List<PDeclaration>)$4 );
  //  c.setDefinitions((List)$4);
  AClassDeclaration res = new AClassDeclaration();
  res.setClassBody( c );
  $$ = res;
}
;

/* 2.2 Processes */

processDecl :
  PROCESS IDENTIFIER EQUALS processDef
  {
      LexLocation processLoc = extractLexLocation((CmlLexeme)$1);
      AProcessDefinition processDef = (AProcessDefinition)$4;
      LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$2);
      LexLocation location = combineLexLocation(processLoc,processDef.getLocation());
      $$ = new AProcessDeclaration(location, NameScope.GLOBAL, id, processDef);
  }
  ;

processDef :
declaration AT process
{ 
    PProcess process = (PProcess)$3;
    List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)$1;
    LexLocation loc = combineLexLocation(extractFirstLexLocation(decls),
					 process.getLocation());
    $$ = new AProcessDefinition(loc, 
				NameScope.GLOBAL, 
				false, 
				null, 
				decls,
				process); 
}
| process
{
    PProcess process = (PProcess)$1;
    $$ = new AProcessDefinition(process.getLocation(), 
				NameScope.GLOBAL, 
				false, 
				null, 
				null,
				process);
}
;

process :
  BEGIN processParagraphList AT action END
  {
      LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$5);
      List<PDeclaration> processDeclarations = (List<PDeclaration>)$2;
      PAction action = (PAction)$4;
      $$ = new AStateProcess(location,processDeclarations,action);
  }
| BEGIN AT action END
  {
      LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$4);
      List<PDeclaration> processDeclarations = null;
      PAction action = (PAction)$3;
      $$ = new AStateProcess(location,processDeclarations,action);
  }
| process CSPSEQ process
{
    PProcess left = (PProcess)$1;
    PProcess right = (PProcess)$3;
    $$ = new ASequentialCompositionProcess(combineLexLocation(left.getLocation(),
							      right.getLocation()), 
					   left, 
					   right);
}
| process CSPEXTCH process
{
    PProcess left = (PProcess)$1;
    PProcess right = (PProcess)$3;
    $$ = new AExternalChoiceProcess(combineLexLocation(left.getLocation(),
						       right.getLocation()), 
				    left, 
				    right);
}
| process CSPINTCH process
{
    PProcess left = (PProcess)$1;
    PProcess right = (PProcess)$3;
    $$ = new AInternalChoiceProcess(combineLexLocation(left.getLocation(),
						       right.getLocation()), 
				    left, 
				    right);
}
| process CSPLSQUAREBAR chansetExpr CSPBARRSQUARE process
{
    PProcess left = (PProcess)$1;
    PProcess right = (PProcess)$5;
    $$ = new AGeneralisedParallelismProcess(combineLexLocation(left.getLocation(),
							       right.getLocation()), 
					    left,
					    (SChansetSetExp)$3,
					    right);
}
| process CSP_LSQUARE chansetExpr CSPDBAR chansetExpr CSP_RSQUARE process
{
    PProcess left = (PProcess)$1;
    PProcess right = (PProcess)$7;
    $$ = new AAlphabetisedParallelismProcess(combineLexLocation(left.getLocation(),
							       right.getLocation()), 
					    left,
					    (SChansetSetExp)$3,
					    (SChansetSetExp)$5,
					    right);
}
| process CSPDBAR process
{
    PProcess left = (PProcess)$1;
    PProcess right = (PProcess)$3;
    $$ = new ASynchronousParallelismProcess(combineLexLocation(left.getLocation(),
							       right.getLocation()), 
					    left, 
					    right);
}
| process CSPINTERLEAVE process
{
    PProcess left = (PProcess)$1;
    PProcess right = (PProcess)$3;
    $$ = new AInterleavingProcess(combineLexLocation(left.getLocation(),
						       right.getLocation()), 
				    left, 
				    right);
}
| process CSP_SLASH CSP_BACKSLASH process
{
    PProcess left = (PProcess)$1;
    PProcess right = (PProcess)$4;
    LexLocation location = combineLexLocation(left.getLocation(),
					      right.getLocation());
    $$ = new AInterruptProcess(location, 
			      left, 
			      right);
}
| process CSP_SLASH expression CSP_BACKSLASH process
{
    PProcess left = (PProcess)$1;
    PProcess right = (PProcess)$5;
    LexLocation location = combineLexLocation(left.getLocation(),
					      right.getLocation());
    $$ = new ATimedInterruptProcess(location, 
				    left, 
				    (PExp)$3,
				    right);
}
| process CSPLSQUAREGT process
{
    PProcess left = (PProcess)$1;
    PProcess right = (PProcess)$3;
    LexLocation location = combineLexLocation(left.getLocation(),
					      right.getLocation());
    $$ = new AUntimedTimeoutProcess(location, 
				   left, 
				   right);
}
// | process CSP_LSQUARE expression CSP_GT process //TODO this gives conflicts!
// {
//     PProcess left = (PProcess)$1;
//     PProcess right = (PProcess)$5;
//     LexLocation location = combineLexLocation(left.getLocation(),
// 					      right.getLocation());
//     $$ = new ATimeoutProcess(location, 
// 			     left,
// 			     (PExp)$3,
// 			     right);
// }

| process CSP_BACKSLASH chansetExpr
{
    PProcess left = (PProcess)$1;
    SChansetSetExp cse = (SChansetSetExp)$3;
    LexLocation location = combineLexLocation(left.getLocation(),
					      cse.getLocation());
    $$ = new AHidingProcess(location, 
			    left, 
			    cse);
}
| process CSP_STARTBY expression
{
    PProcess left = (PProcess)$1;
    PExp exp = (PExp)$3;
    LexLocation location = combineLexLocation(left.getLocation(),
					      exp.getLocation());
    $$ = new AStartDeadlineProcess(location, 
			     left, 
			     exp);
}
| process CSP_ENDBY expression
{
    PProcess left = (PProcess)$1;
    PExp exp = (PExp)$3;
    LexLocation location = combineLexLocation(left.getLocation(),
					      exp.getLocation());
    $$ = new AEndDeadlineProcess(location, 
			   left, 
			   exp);
}
/*
 * This does not follow the grammar from the document. processDef 
 * has been replaced by IDENTIFIER.
 */
| LPAREN declaration AT IDENTIFIER RPAREN LPAREN expression RPAREN 
{
    LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$8); 
    List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)$2;
    LexNameToken identifier = extractLexNameToken((CmlLexeme)$4);
    $$ = new AInstantiationProcess(location, 
				   decls,
				   identifier,
				   (PExp)$7);
}
| IDENTIFIER LPAREN expression RPAREN
{
    LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$4); 
    List<ASingleTypeDeclaration> decls = null;
    LexNameToken identifier = extractLexNameToken((CmlLexeme)$1);
    $$ = new AInstantiationProcess(location, 
				   decls,
				   identifier,
				   (PExp)$3);
}
//this rule is added, hence it it not in the grammar document.
| IDENTIFIER
{
    LexNameToken identifier = extractLexNameToken((CmlLexeme)$1);
    $$ = new AIdentifierProcess(identifier.getLocation(), 
				identifier);
}
| process renameExpression
{
    SRenameChannelExp renameExpression = (SRenameChannelExp)$2;
    PProcess process = (PProcess)$1;
    
    $$ = new AChannelRenamingProcess(combineLexLocation(process.getLocation(),
						       renameExpression.getLocation()), 
				    process, 
				    renameExpression);
}
//| LPAREN process RPAREN LSQUARE identifierList CSPRENAME identifierList RSQUARE //TODO
| CSPSEQ LCURLY declaration AT process RCURLY //TODO
| CSPINTCH LCURLY declaration AT process RCURLY //TODO
| CSPEXTCH LCURLY declaration AT process RCURLY //TODO
| LSQUARE LCURLY chansetExpr RSQUARE declaration AT process RCURLY //TODO
| CSPINTERLEAVE LCURLY declaration AT process RCURLY //TODO
;

//This is how it is defined in the grammar but this gives reduce/reduce conflict
//Since expression and type both can be a name.
// replicationDeclaration:
//   replicationDeclarationAlt
// | replicationDeclarationAlt SEMI replicationDeclaration
// ;

// replicationDeclarationAlt:
//   singleTypeDecl
// | singleExpressionDeclaration
// ;

// singleExpressionDeclaration:
// IDENTIFIER COLON expression
// {
//     // LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
//     // List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
//     // ids.add(id);
//     // ASingleTypeDeclaration singleTypeDeclaration = 
//     //   new ASingleTypeDeclaration(id.getLocation(),NameScope.GLOBAL,ids,(PType)$3);
//     // $$ = singleTypeDeclaration;
// }
// | IDENTIFIER COMMA singleExpressionDeclaration
// {
//     // LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
//     // ASingleTypeDeclaration singleTypeDeclaration = (ASingleTypeDeclaration)$3;
    
//     // singleTypeDeclaration.getIdentifiers().add(id);
//     // $$ = singleTypeDeclaration;
// }
// ;



processParagraphList:
processParagraph
{
    List<PDeclaration> processParagraphList = 
	  new Vector<PDeclaration>();
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

actionDefinition:
IDENTIFIER EQUALS paragraphAction
{
    Object[] pa = (Object[])$3;
    List<ASingleTypeDeclaration> declarations = 
	(List<ASingleTypeDeclaration>)pa[0];
    PAction action = (PAction)pa[1];
    LexLocation defLocation = combineLexLocation(extractLexLocation((CmlLexeme)$1),
						 action.getLocation());
    AActionDefinition actionDefinition = new AActionDefinition(defLocation, 
							       NameScope.GLOBAL, 
							       false, 
							       null, 
							       declarations, 
							       action);
    $$ = actionDefinition;
}
;
actionParagraph:
 CSP_ACTIONS actionDefinition
  {
      AActionDefinition actionDefinition = (AActionDefinition)$2;
      LexLocation declLoc = combineLexLocation(extractLexLocation((CmlLexeme)$1),
					       actionDefinition.getLocation());;
      $$ = new AActionDeclaration(declLoc, 
				  NameScope.GLOBAL, 
				  actionDefinition);
  }
| CSP_ACTIONS nameset IDENTIFIER EQUALS namesetExpr //TODO
;

paragraphAction :
action
{
    $$ = new Object[]{new Vector<ASingleTypeDeclaration>(),$1};
}
| declaration AT action
{
    $$ = new Object[]{$1,$2};
} 
;

action
: CSPSKIP 
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
| CSPWAIT expression  //TODO
  /* Communication rule start*/
| IDENTIFIER RARROW action
{
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
    PAction action = (PAction)$3;
    LexLocation location = combineLexLocation(id.getLocation(),action.getLocation());
    $$ = new ACommunicationAction(location, id, null,action);
}
| IDENTIFIER communicationParameterUseList RARROW action
{
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
    PAction action = (PAction)$4;
    LexLocation location = combineLexLocation(id.getLocation(),action.getLocation());
    List<PCommunicationParameter> communicationParamters = (List<PCommunicationParameter>)$2;
    $$ = new ACommunicationAction(location, id, 
				  communicationParamters,
				  action);
}
  /* Communication rule end*/
| COLON expression AMP action
{
    PAction action = (PAction)$4;
    LexLocation location = extractLexLocation((CmlLexeme)$1,action.getLocation());
    $$ = new AGuardedAction(location, (PExp)$2, action);
}
| action CSPSEQ action
{
    PAction left = (PAction)$1;
    PAction right = (PAction)$3;
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    $$ = new ASequentialCompositionAction(location, left, right);
}
| action CSPEXTCH action
{
    PAction left = (PAction)$1;
    PAction right = (PAction)$3;
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    $$ = new AExternalChoiceAction(location, left, right);
}
| action CSPINTCH action
{
    PAction left = (PAction)$1;
    PAction right = (PAction)$3;
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    $$ = new AInternalChoiceAction(location, left, right);
}
| action CSP_SLASH CSP_BACKSLASH action
{
    PAction left = (PAction)$1;
    PAction right = (PAction)$4;
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    $$ = new AInterruptAction(location, left, right);
}
| action CSP_SLASH expression CSP_BACKSLASH action
{
    PAction left = (PAction)$1;
    PAction right = (PAction)$5;
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    $$ = new ATimedInterruptAction(location, left, right,(PExp)$3);
}
| action CSPLSQUAREGT action
{
    PAction left = (PAction)$1;
    PAction right = (PAction)$3;
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    $$ = new AUntimedTimeoutAction(location, left, right);
}
| action CSP_LSQUARE expression CSP_GT action
{
    PAction left = (PAction)$1;
    PAction right = (PAction)$5;
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    $$ = new ATimeoutAction(location, left, right, (PExp)$3);
}
| action CSP_BACKSLASH chansetExpr 
{
    PAction left = (PAction)$1;
    SChansetSetExp chansetExp = (SChansetSetExp)$3;
    LexLocation location = combineLexLocation(left.getLocation(),chansetExp.getLocation());
    $$ = new AHidingAction(location, left, chansetExp);
}
| action CSP_STARTBY expression 
{
    PAction left = (PAction)$1;
    PExp exp = (PExp)$3;
    LexLocation location = combineLexLocation(left.getLocation(),exp.getLocation());
    $$ = new AStartDeadlineAction(location, left, exp);
}
| action CSP_ENDBY expression 
{
    PAction left = (PAction)$1;
    PExp exp = (PExp)$3;
    LexLocation location = combineLexLocation(left.getLocation(),exp.getLocation());
    $$ = new AEndDeadlineAction(location, left, exp);
}
| action renameExpression
{
    SRenameChannelExp renameExpression = (SRenameChannelExp)$2;
    PAction action = (PAction)$1;
    
    $$ = new AChannelRenamingAction(combineLexLocation(action.getLocation(),
						       renameExpression.getLocation()), 
				    action, 
				    renameExpression);
}
  //| put u action here
| parallelAction
  //| parametrisedAction
  //| instantiatedAction
  //| replicatedAction

  //| action LSQUARE identifierList CSPRENAME identifierList RSQUARE
  
/*statements*/
  //| letStatement
| blockStatement
| controlStatements
{
  $$ = $1;
}
| IDENTIFIER 
{ 
    LexLocation location = extractLexLocation((CmlLexeme)$1);
    $$ = new AIdentifierAction(location);  
}
;

communicationParameterUseList :
  communicationParameter
  {
      List<PCommunicationParameter> comParamList = 
	  new Vector<PCommunicationParameter>();
      comParamList.add((PCommunicationParameter)$1);
      $$ = comParamList;
  }
| communicationParameter communicationParameterUseList
{
    List<PCommunicationParameter> comParamList = 
	(List<PCommunicationParameter>)$1;

    if (comParamList == null) 
	comParamList = new Vector<PCommunicationParameter>();
    
    comParamList.add((PCommunicationParameter)$2);
    $$ = comParamList;
}
  ;

communicationParameter :
  CSP_CHANNEL_READ parameter
  {
      PParameter parameter = (PParameter)$2;
      LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$1),
								   parameter.getLocation());
      $$ = new AReadCommunicationParameter(location, parameter, null);
  }
| CSP_CHANNEL_READ parameter COLON expression 
{
    PParameter parameter = (PParameter)$2;
    PExp exp = (PExp)$4;
    LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$1),
								 exp.getLocation());
    $$ = new AReadCommunicationParameter(location, parameter, exp);
}
| CSP_CHANNEL_WRITE expression
{
    PExp exp = (PExp)$2;
    LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$1),
								 exp.getLocation());
    $$ = new AWriteCommunicationParameter(location, exp);
}
| CSP_CHANNEL_DOT expression  
{
    PExp exp = (PExp)$2;
    LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$1),
								 exp.getLocation());
    $$ = new AReferenceCommunicationParameter(location, exp);
}
  ;

parameter :
IDENTIFIER
{
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
    $$ = new AIdentifierParameter(id.getLocation(),id);
}
| MKUNDER LPAREN paramList RPAREN
{
    $$ = new ATupleParameter(extractLexLocation((CmlLexeme)$1,
						(CmlLexeme)$4), 
			     (List<? extends PParameter>)$3);
}
| MKUNDER LPAREN RPAREN 
{
    $$ = new ATupleParameter(extractLexLocation((CmlLexeme)$1,
						(CmlLexeme)$3), 
			     null);
}
| MKUNDERNAME LPAREN paramList RPAREN 
{
    CmlLexeme mkUnderName = (CmlLexeme)$1;
    LexNameToken name = new LexNameToken("Default",
    					 mkUnderName.getValue().split("_")[1], 
    					 extractLexLocation(mkUnderName),
    					 false, 
    					 true);
    
    $$ = new ARecordParameter(extractLexLocation((CmlLexeme)$1,
    						 (CmlLexeme)$4), 
    			      name, 
    			      (List<? extends PParameter>)$3);
}
| MKUNDERNAME LPAREN RPAREN
{
    CmlLexeme mkUnderName = (CmlLexeme)$1;
    LexNameToken name = new LexNameToken("Default",
    					 mkUnderName.getValue().split("_")[1], 
    					 extractLexLocation(mkUnderName),
    					 false, 
    					 true);
    
    $$ = new ARecordParameter(extractLexLocation((CmlLexeme)$1,
    						 (CmlLexeme)$3), 
    			      name, 
    			      null);
}
;

paramList :
parameter 
{
    List<PParameter> parameters = new Vector<PParameter>();
    parameters.add((PParameter)$1);
    $$ = parameters;
}
| parameter COMMA paramList 
{
    List<PParameter> parameters = (List<PParameter>)$3;
    parameters.add((PParameter)$1);
    $$ = parameters;
}
;

parallelAction:
action CSPLSQUAREDBAR namesetExpr BAR namesetExpr CSPDBARRSQUARE action //TODO
 | action CSPINTERLEAVE action //TODO
 | action CSPLSQUAREBAR namesetExpr BAR namesetExpr CSPBARRSQUARE action //TODO
 | action CSPDBAR action //TODO
  //| action CSP_LSQUARE namesetExpr BAR chansetExpr CSPDBAR chansetExpr BAR namesetExpr CSP_RSQUARE action //TODO
  //| action CSP_LSQUARE chansetExpr CSPDBAR chansetExpr CSP_RSQUARE action //TODO
 | action CSPLSQUAREBAR namesetExpr BAR chansetExpr BAR namesetExpr CSPBARRSQUARE action //TODO
 | action CSPLSQUAREBAR chansetExpr CSPBARRSQUARE action //TODO
;

//parametrisedAction:
//;

//instantiatedAction:
//;


replicatedAction :
 CSPSEQ LCURLY declaration AT action RCURLY //TODO
| CSPINTCH LCURLY declaration AT action RCURLY //TODO
| CSPEXTCH LCURLY declaration AT action RCURLY //TODO
| CSPLSQUAREDBAR nameset CSPDBARRSQUARE LPAREN declaration AT action RPAREN //TODO
| CSPLSQUAREBAR nameset BAR chansetExpr CSPBARRSQUARE LPAREN declaration AT action RPAREN //TODO
| CSPDBAR declaration AT LSQUARE nameset BAR chansetExpr RSQUARE action //TODO
| LSQUARE renameList RSQUARE LPAREN declaration AT action RPAREN //TODO
    ;

renameExpression:
 renameEnumeration
 {
     $$ = $1;
 }
 | renameComprehension
;

renameEnumeration:
DLSQUARE renameList DRSQUARE
{
    $$ = new AEnumerationRenameChannelExp(null, 
					  extractLexLocation((CmlLexeme)$1,(CmlLexeme)$3), 
					  (List<? extends ARenamePair>)$2); 
}
;

renameComprehension:
DLSQUARE renameList BAR bindList DRSQUARE
{
    $$ = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)$1,(CmlLexeme)$5), 
					    (List<? extends ARenamePair>)$2, 
					    (List<? extends PMultipleBind>)$4, 
					    null);
}
| DLSQUARE renameList BAR bindList AT expression DRSQUARE
{
    $$ = new AComprehensionRenameChannelExp(extractLexLocation((CmlLexeme)$1,(CmlLexeme)$7), 
					    (List<? extends ARenamePair>)$2, 
					    (List<? extends PMultipleBind>)$4, 
					    (PExp)$6);
}
;

renameList :
renamePair
{
    List<ARenamePair> renamePairs = 
	new Vector<ARenamePair>();
    renamePairs.add((ARenamePair)$1);
    $$ = renamePairs;
}
| renamePair COMMA renameList
{
    List<ARenamePair> renamePairs = (List<ARenamePair>)$3;
    renamePairs.add((ARenamePair)$1);
    $$ = renamePairs;
}
;

renamePair:
channelEvent CSPSAMEAS channelEvent
{
    $$ = new ARenamePair(false, 
			 (AEventChannelExp)$1, 
			 (AEventChannelExp)$3);
}
;

channelEvent:
IDENTIFIER
{
    LexNameToken id = extractLexNameToken((CmlLexeme)$1);
    List<? extends PExp> dotExpression = null;
    $$ = new AEventChannelExp(id.getLocation(), 
			      id, 
			      dotExpression);
}
| IDENTIFIER dotted_expression
{
    LexNameToken id = extractLexNameToken((CmlLexeme)$1);
    List<? extends PExp> dotExpression = (List<? extends PExp>)$2 ;
    $$ = new AEventChannelExp(id.getLocation(), 
			      id, 
			      dotExpression);
}
;

dotted_expression:
CSP_CHANNEL_DOT expression
{
    List<PExp> expTokens = new Vector<PExp>();
    expTokens.add((PExp)$2);
    $$ = expTokens;
}
| dotted_expression CSP_CHANNEL_DOT expression
{
    List<PExp> expTokens = (List<PExp>)$1;
    PExp exp = (PExp)$3;
    expTokens.add(exp);
    $$ = expTokens;
}
;

/* 2.3 Channel Definitions */

channelDecl :
 CHANNELS channelDef                              
 {
     List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)$2;
     LexLocation start = decls.get(0).getLocation();
     LexLocation end = decls.get(decls.size()-1).getLocation();
     LexLocation location = combineLexLocation(start,end);

     AChannelDefinition channelDefinition = 
	 new AChannelDefinition(location,null,null,null,decls);
     AChannelDeclaration channelDecl = new AChannelDeclaration(location,
							       NameScope.GLOBAL,
							       channelDefinition);
     $$ = channelDecl;
 }
;

channelDef: 
  channelNameDecl
  {
      List<AChannelNameDeclaration> decls = new Vector<AChannelNameDeclaration>();
      decls.add((AChannelNameDeclaration)$1);
      AChannelDefinition channelDefinition = new AChannelDefinition();
      $$ = decls;
  }                                 
 |channelNameDecl SEMI channelDef
 {
     List<AChannelNameDeclaration> decls = (List<AChannelNameDeclaration>)$3;
     decls.add((AChannelNameDeclaration)$1);
     $$ = decls;
 }
;

channelNameDecl: 
  identifierList
  {
      List<LexIdentifierToken> ids = (List<LexIdentifierToken>)$1;

      LexLocation start = ids.get(0).getLocation();
      LexLocation end = ids.get(ids.size()-1).getLocation();
      LexLocation location = combineLexLocation(start,end);

      ASingleTypeDeclaration singleTypeDeclaration = new ASingleTypeDeclaration(location,NameScope.GLOBAL,ids,null);
            
      AChannelNameDeclaration channelNameDecl = new AChannelNameDeclaration(location,NameScope.GLOBAL,singleTypeDeclaration);
      
      $$ = channelNameDecl;
  }
 |singleTypeDecl
 {
     ASingleTypeDeclaration singleTypeDeclaration = (ASingleTypeDeclaration)$1;

     AChannelNameDeclaration channelNameDecl = 
       new AChannelNameDeclaration(singleTypeDeclaration.getLocation(),NameScope.GLOBAL,singleTypeDeclaration);
      
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
| singleTypeDecl SEMI declaration
{
  List<ASingleTypeDeclaration> decls = (List<ASingleTypeDeclaration>)$3;
  decls.add((ASingleTypeDeclaration)$1);
  $$ = decls;
}
;

singleTypeDecl :
IDENTIFIER COLON type
{
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
    List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
    ids.add(id);
    ASingleTypeDeclaration singleTypeDeclaration = 
      new ASingleTypeDeclaration(id.getLocation(),NameScope.GLOBAL,ids,(PType)$3);
    $$ = singleTypeDeclaration;
}
| IDENTIFIER COMMA singleTypeDecl
{
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
    ASingleTypeDeclaration singleTypeDeclaration = (ASingleTypeDeclaration)$3;
    
    singleTypeDeclaration.getIdentifiers().add(id);
    $$ = singleTypeDeclaration;
}
;

/* 2.4 Chanset Definitions */

chansetDecl:
CHANSETS
{
    LexIdentifierToken id = extractLexIdentifierToken((CmlLexeme)$1);
    $$ = new AChansetDeclaration(id.getLocation(), NameScope.GLOBAL, null, null);
}
| CHANSETS IDENTIFIER EQUALS chansetExpr
{
    LexIdentifierToken channelsToken = extractLexIdentifierToken((CmlLexeme)$1);
    LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)$2);
    SChansetSetBase chansetExp = (SChansetSetBase)$4;
    LexLocation location = combineLexLocation(channelsToken.getLocation(),
					      chansetExp.getLocation());
    $$ = new AChansetDeclaration(location, NameScope.GLOBAL, idToken, chansetExp);
}
;

chansetExpr : 
 IDENTIFIER
 {
   LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)$1);
   $$ = new AIdentifierChansetSetExp(idToken.getLocation(),idToken);
 }
| LCURLY identifierList RCURLY
{
    LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$3);
    List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)$2;
    $$ = new AEnumChansetSetExp(location,identifiers);
}
| CHANSET_SETEXP_BEGIN identifierList CHANSET_SETEXP_END
{
    LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$3);
    List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)$2;
    $$ = new AEnumChansetSetExp(location,identifiers);
}
| chansetExpr UNION chansetExpr
{
    PExp left = (PExp)$1;
    PExp right = (PExp)$3;
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    $$ = new ASetUnionBinaryExp(location,left, null, right);
}
| chansetExpr INTER chansetExpr
{
    PExp left = (PExp)$1;
    PExp right = (PExp)$3;
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    $$ = new ASetIntersectBinaryExp(location,left, null, right);
}
| chansetExpr SETDIFF chansetExpr
{
    PExp left = (PExp)$1;
    PExp right = (PExp)$3;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$2);
    LexToken lexToken = new LexToken(opLocation,VDMToken.SETDIFF);
    LexLocation location = combineLexLocation(left.getLocation(),right.getLocation());
    $$ = new ASetDifferenceBinaryExp(location, left, lexToken, right);
}
| CHANSET_SETEXP_BEGIN IDENTIFIER BAR bindList CHANSET_SETEXP_END
{
    LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$5);
    LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)$2);
    List<PExp> dotted_expression = new Vector<PExp>();
    List<PMultipleBind> bindings = (List<PMultipleBind>)$4;
    $$ = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,null);
}
| CHANSET_SETEXP_BEGIN IDENTIFIER dotted_expression BAR bindList CHANSET_SETEXP_END
{
    LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$6);
    LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)$2);
    List<PExp> dotted_expression = (List<PExp>)$3;
    List<PMultipleBind> bindings = (List<PMultipleBind>)$5;
    $$ = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,null);
}
| CHANSET_SETEXP_BEGIN IDENTIFIER BAR bindList AT expression CHANSET_SETEXP_END
{
    LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$7);
    LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)$2);
    List<PExp> dotted_expression = new Vector<PExp>();
    List<PMultipleBind> bindings = (List<PMultipleBind>)$4;
    PExp pred = (PExp)$6;
    $$ = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,pred);
}
| CHANSET_SETEXP_BEGIN IDENTIFIER dotted_expression BAR bindList AT expression CHANSET_SETEXP_END
{
    LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$8);
    LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)$2);
    List<PExp> dotted_expression = (List<PExp>)$3;
    List<PMultipleBind> bindings = (List<PMultipleBind>)$5;
    PExp pred = (PExp)$7;
    $$ = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,pred);
}
;

/* 2.5 Global Definitions */

globalDecl :
globalDefinitionBlock // TODO
  ;

globalDefinitionBlock 
: globalDefinitionBlockAlternative
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
    if (declBlockList != null) if (globalDecl != null) declBlockList.add(globalDecl);
    $$ = declBlockList;
}
;

globalDefinitionBlockAlternative
: typeDefs             
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

/* 3 Definitions */

classBody: 
BEGIN classDefinitionBlock END
{
  $$ = $2;
}
;

/*classBody 
: classDefinitionBlock                       
{ 
  $$ = (List)$1; 
}
|
{
  $$ = new Vector<PDefinition>();
}
;
*/
classDefinitionBlock 
: classDefinitionBlockAlternative
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

classDefinitionBlockAlternative
: typeDefs             
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
/*
| initialDef // TODO is this a thing?
{
  
}*/
;

/* 3.1 Type Definitions */

typeDefs 
: TYPES 
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
  LexLocation loc = extractLexLocation(typesLexeme,semiLexeme);
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions((List<ATypeDefinition>)$2);
  td.setLocation(loc);
  $$ = td;
}
| TYPES typeDefList                          
{
  CmlLexeme typesLexeme = (CmlLexeme)$1;
  List<ATypeDefinition> tdefs = (List<ATypeDefinition>)$2;
  LexLocation loc = extractLexLocation(typesLexeme,tdefs.get(tdefs.size()-1).getLocation());
  ATypeDeclaration td = new ATypeDeclaration();
  td.setTypeDefinitions(tdefs);
  td.setLocation(loc);
  $$ = td;
}
;

typeDefList
: typeDefList SEMI typeDef                   
{
    List<ATypeDefinition> list = (List<ATypeDefinition>)$1;
    list.add((ATypeDefinition)$3);
    $$ = list;
}
| typeDef                                    
{
    List<ATypeDefinition> list = new Vector<ATypeDefinition>(); 
    list.add((ATypeDefinition)$1);
    $$ = list;
} 
;

typeDef 
: qualifier IDENTIFIER EQUALS type invariant
{
    AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
    LexNameToken name = extractLexNameToken((CmlLexeme)$2);
    AInvariantInvariant inv = (AInvariantInvariant)$5;
    LexLocation location = null;
    if (access.getLocation() != null)
	location = combineLexLocation(access.getLocation(),inv.getLocation());
    else
    {
	location = combineLexLocation(name.getLocation(),inv.getLocation());
    }
    
    

    $$ = new ATypeDefinition(location,null /*NameScope nameScope_*/, false, 
			     null/*SClassDefinition classDefinition_*/,access, 
			     (PType)$4,null,inv.getInvPattern(),inv.getInvExpression(), 
			     null, true, name); 
    
}
| qualifier IDENTIFIER EQUALS type                        
{ 
    AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
    LexNameToken name = extractLexNameToken((CmlLexeme)$2);
    LexLocation location = null;
    if (access.getLocation() != null)
	location = combineLexLocation(access.getLocation(),((PType)$4).getLocation());
    else
    {
	location = combineLexLocation(name.getLocation(),((PType)$4).getLocation());
    }
        
    $$ = new ATypeDefinition(location,null /*NameScope nameScope_*/, false, 
			     null/*SClassDefinition classDefinition_*/,access, 
			     (PType)$4, null, null, null, 
			     null, true, name); 
}
| qualifier IDENTIFIER VDMRECORDDEF fieldList
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken( (CmlLexeme)$2 );
  CmlLexeme vdmrec = (CmlLexeme)$3;
  List<AFieldField> fields = (List<AFieldField>)$4;

  LexLocation loc = combineLexLocation( access.getLocation(),
					extractLexLocation( vdmrec ) );
					

  //
  ARecordInvariantType recType = new ARecordInvariantType( loc, false, null, false, null, name, fields, true );
							   

  ATypeDefinition res = new ATypeDefinition( loc, NameScope.GLOBAL, 
					     false, null, access,
					     recType, null, null, null,
					     null, true, name );

  $$ = res;
}
| qualifier IDENTIFIER VDMRECORDDEF fieldList invariant 
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken( (CmlLexeme)$2 );
  CmlLexeme vdmrec = (CmlLexeme)$3;
  List<AFieldField> fields = (List<AFieldField>)$4;
  // FIXME: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.


  LexLocation loc = combineLexLocation( access.getLocation(),
					extractLexLocation( vdmrec ));
					

  //
  ARecordInvariantType recType = new ARecordInvariantType( loc, false, null, false, null, name, fields, true );
							   

  ATypeDefinition res = new ATypeDefinition( loc, NameScope.GLOBAL, 
					     false, null, access,
					     recType, null, null, null,
					     null, true, name );

  $$ = res;
}

qualifier
: PRIVATE 
{ 
    LexLocation location = extractLexLocation((CmlLexeme)$1);
    $$ = new AAccessSpecifierAccessSpecifier(new APrivateAccess(),null,null,location);
}
| PROTECTED 
{ 
    LexLocation location = extractLexLocation((CmlLexeme)$1);
    $$ = new AAccessSpecifierAccessSpecifier(new AProtectedAccess(),null,null,location);
}
| PUBLIC  
{ 
    LexLocation location = extractLexLocation((CmlLexeme)$1);
    $$ = new AAccessSpecifierAccessSpecifier(new APublicAccess(),null,null,location);
}
| LOGICAL 
{ 
    LexLocation location = extractLexLocation((CmlLexeme)$1);
    $$ = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(),null,null,location);
}
| /* empty */
{
    /*Default public?????*/
    $$ = new AAccessSpecifierAccessSpecifier(new APublicAccess(),null,null,null);
}
;

type 
: LPAREN type RPAREN
{ 
    $$ = $2;
}
| TBOOL
{ 
    $$ = new ABooleanBasicType(extractLexLocation((CmlLexeme)$1) , false);
}
| TNAT                                                
{ 
    $$ = new ANatNumericBasicType(extractLexLocation((CmlLexeme)$1) , false);
}                                         
| TNAT1
{ 
    $$ = new ANatOneNumericBasicType(extractLexLocation((CmlLexeme)$1) , false);
}
| TINT
{ 
    $$ = new AIntNumericBasicType(extractLexLocation((CmlLexeme)$1) , false);
}
| TRAT 
{ 
    $$ = new ARationalNumericBasicType(extractLexLocation((CmlLexeme)$1) , false);
}
| TREAL
{ 
     $$ = new ARealNumericBasicType(extractLexLocation((CmlLexeme)$1) , false);
}
| TCHAR
{ 
    $$ = new ACharBasicType(extractLexLocation((CmlLexeme)$1) , false);
}
| TTOKEN
{ 
    $$ = new ATokenBasicType(extractLexLocation((CmlLexeme)$1) , false);
}
| quoteLiteral // TODO
| VDMCOMPOSE IDENTIFIER OF fieldList END // TODO
| LPAREN type BAR type RPAREN
{
  CmlLexeme lp = (CmlLexeme)$1;
  CmlLexeme rp = (CmlLexeme)$5;
  PType fst = (PType)$2;
  PType snd = (PType)$4;

  LexLocation loc = combineLexLocation ( extractLexLocation ( lp ),
					 extractLexLocation ( rp ) );

  AUnionType utype = new AUnionType(loc, false, false, false );
  $$ = utype;
}
| type STAR type // TODO
| LSQUARE type RSQUARE // TODO
| VDMSETOF type
{
  // Get Constituents
  CmlLexeme setof = (CmlLexeme)$1;
  PType type = (PType)$2;

  LexLocation loc = combineLexLocation( extractLexLocation ( setof ),
					type.getLocation() );

  // Build ASetType
  ASetType res = new ASetType( loc, false, null, type, false, false );
  $$ = res;
}
| VDMSEQOF type  
{
  CmlLexeme seqof = (CmlLexeme)$1;
  PType type = (PType)$2;
  LexLocation loc = combineLexLocation( extractLexLocation ( seqof ),
					type.getLocation() );

  // Build ASetType
  ASeqSeqType res = new ASeqSeqType( loc, false, null, type, false );
  $$ = res;
}
| VDMSEQ1OF type
{
  CmlLexeme seqof = (CmlLexeme)$1;
  PType type = (PType)$2;
  LexLocation loc = combineLexLocation( extractLexLocation ( seqof ),
					type.getLocation() );

  // Build ASetType
  ASeq1SeqType res = new ASeq1SeqType( loc, false, null, type, false );
  $$ = res;
}
| VDMMAPOF type TO type
{
  CmlLexeme mapof = (CmlLexeme)$1;
  PType from = (PType)$2;
  // $3 TO
  PType to   = (PType)$4;
  
  LexLocation loc = combineLexLocation ( extractLexLocation ( mapof ),
					 to.getLocation() );

  // Build res
  AMapMapType res = new AMapMapType( loc, false, null, from, to, false );
  $$ = res;
}
| VDMINMAPOF type TO type
{
  CmlLexeme mapof = (CmlLexeme)$1;
  PType from = (PType)$2;
  // $3 TO
  PType to   = (PType)$4;
  
  LexLocation loc = combineLexLocation ( extractLexLocation ( mapof ),
					 to.getLocation() );


  // Build res
  AMapMapType res = new AMapMapType( loc, false, null, from, to, false );
  $$ = res;
}
| type RARROW type
{
  PType domType = (PType)$1;
  PType rngType = (PType)$3;
  
  LexLocation loc = combineLexLocation ( domType.getLocation(),
					 rngType.getLocation() ) ;

  // [CONSIDER,RWL] The domain type of a function is not a list, 
  // I think the AST is wrong taking a list of types for params
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  AFunctionType res = new AFunctionType(loc, false, null, false, params, rngType );
  $$ = res;
}
| type VDMPFUNCARROW type // TODO
| VDMUNITTYPE VDMPFUNCARROW type // TODO
| type VDMTFUNCARROW type // TODO
| VDMUNITTYPE VDMTFUNCARROW type // TODO
| name
{
  LexNameToken lnt = (LexNameToken)$1; 
  $$ = new AUnresolvedType(lnt.location,false /*resolved*/, null/*defs*/,lnt);
}
| typeVarIdentifier // TODO ?
{
  
}
  ;

typeUnionList :
type SEMI
{
  PType type = (PType)$1;
  LexLocation loc = type.getLocation();
  AUnionType res = new AUnionType(loc,false,false,false);
  $$ = res;
}
|
type BAR typeUnionList
{

  PType type = (PType)$1;
  AUnionType tl = (AUnionType)$3;
  LexLocation updatedLocation  =combineLexLocation( tl.getLocation(),
						     type.getLocation() );
  tl.getTypes().add(type);
  tl.setLocation(updatedLocation);
  $$ = tl;
}

quoteLiteral:
LT IDENTIFIER GT
{
  CmlLexeme lt = (CmlLexeme)$1;
  CmlLexeme id = (CmlLexeme)$2;
  CmlLexeme gt = (CmlLexeme)$3;

  LexLocation loc = extractLexLocation ( lt );

  LexQuoteToken value = new LexQuoteToken( id.getValue(), loc );
  AQuoteType qt = new AQuoteType( loc, false, null, value );
  $$ = qt;
}


fieldList : 
  field
  {
    List<AFieldField> res = new LinkedList<AFieldField>();
    res.add ( (AFieldField) $1 );
    $$ = res;
  }
| field fieldList
{
  List<AFieldField> tail = (List<AFieldField>)$2;
  tail.add( (AFieldField) $1 );
  $$ = tail;
}
;

field :
  type
  {
    $$ = new AFieldField( null, null, null, (PType) $1, null );
  }
| IDENTIFIER COLON type
{
  LexNameToken name = extractLexNameToken( (CmlLexeme) $1 );
  PType type = (PType) $3;

  $$ = new AFieldField( null, name, null, type, null );
}
| IDENTIFIER VDMTYPENCMP type
{
  throw new RuntimeException("No way");
}
  ;

invariant :
 VDMINV pattern DEQUALS expression
 {
   CmlLexeme vdmInvLexeme = (CmlLexeme)$1;
   PExp exp = (PExp)$4;
   LexLocation loc = extractLexLocation(vdmInvLexeme,exp.getLocation());
   $$ = new AInvariantInvariant(loc,(PPattern)$2,exp);
 }
  ;

/* 3.2 Value Definitions */

valueDefs :
  VALUES valueDefList
  {
    List<PDefinition> defs = (List<PDefinition>)$2;
    AValueDeclaration valueDecl = new AValueDeclaration();
    valueDecl.setDefinitions( defs );
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
   // Build resulting list 
   List<PDefinition> defs = new LinkedList<PDefinition>();
   defs.add((PDefinition)$1);
   $$ = defs;
}
| qualifiedValueDef SEMI 
 {
   // This case allows tailing SEMI in value def. list, comment out to
   // enforce no tailing SEMI.

   // Build resulting list 
   List<PDefinition> defs = new LinkedList<PDefinition>();
   defs.add((PDefinition)$1);
   $$ = defs;
 }
| qualifiedValueDef SEMI valueDefList
{
  // Get constituents
  PDefinition def = (PDefinition)$1;
  List<PDefinition> defs = (List<PDefinition>)$3;
  
  // add hd to tl
  defs.add(def);
  $$ = defs;
}
 ;

qualifiedValueDef:
qualifier valueDef {
  // Get constituents
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  PDefinition def = (PDefinition)$2;
  
  // set qualifier
  def.setAccess(access);
  $$ = def;
}
;

valueDef :
IDENTIFIER COLON type EQUALS expression
{
   // Get constituent elements
  CmlLexeme id = (CmlLexeme)$1;
  // $2 COLON
  PType type = (PType)$3;
  // $4 EQUALS
  PExp expression = (PExp)$5;
  
  // Make pattern
  CmlLexeme lexeme = (CmlLexeme)$1;
  LexNameToken lnt = extractLexNameToken(lexeme);
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location,null,false,lnt);
  
  // Build the resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation( idp.getLocation(), 
				       expression.getLocation() ) );
  $$ = vdef;
}
|
patternLessID COLON type EQUALS expression 
{
  // Get constituent elements
  PPattern pattern = (PPattern)$1;
  // $2 COLON
  PType type = (PType)$3;
  // $4 EQUALS
  PExp expression = (PExp)$5;

  // Build resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation( pattern.getLocation(), 
				       expression.getLocation() ) );
  $$ = vdef;
}
;

/* FIXME the optional trailing semicolon in the values definitions is presently not optional */

/* 3.3 Function Definitions */

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
  fdecl.setLocation(extractLexLocation(functionsLexeme,
				       functionDefs.get(functionDefs.size()-1).getLocation()));
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

functionDef:
implicitFunctionDef 
{
    $$ = $1;
}
| qualifiedExplicitFunctionDef
{
    $$ = $1;
}
;

implicitFunctionDef:
qualifier IDENTIFIER parameterTypes identifierTypePairList preExpr_opt postExpr
{
  
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);

  List<APatternListTypePair> paramPatterns = (List<APatternListTypePair>)$3;
  List<APatternTypePair> result = (List<APatternTypePair>)$4;  
  PExp preExp = (PExp)$5;
  PExp postExp = (PExp)$6;

  LexLocation location = null;
  if (access.getLocation() != null){
      location = combineLexLocation(access.getLocation(),postExp.getLocation());
  }
  else{
      location = combineLexLocation(name.getLocation(),postExp.getLocation());
  }
  AImplicitFunctionFunctionDefinition impFunc = 
      new AImplicitFunctionFunctionDefinition(location, null/*nameScope */, false/*used_*/, 
					      access, null /* typeParams*/, 
					      paramPatterns, result, preExp, postExp);
  impFunc.setName(name);
  $$ = impFunc;
}
;

qualifiedExplicitFunctionDef:
qualifier explicitFunctionDef
  {
    AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
    AExplicitFunctionFunctionDefinition f = (AExplicitFunctionFunctionDefinition)$2;
    f.setAccess(access);
    $$ = f;
  }
;

explicitFunctionDef:
IDENTIFIER COLON type IDENTIFIER parameterList DEQUALS functionBody preExpr_opt postExpr_opt measureExpr
  {
    LexNameToken name = extractLexNameToken( (CmlLexeme) $1 );
    PType type = (PType)$3;
    LexLocation loc = extractLexLocation ( (CmlLexeme) $1 );
    AExplicitFunctionFunctionDefinition res = new AExplicitFunctionFunctionDefinition();
    res.setLocation(loc);
    $$ = res; 
  }
;

/* really? this is what a VDM function definition list looks like? */
parameterList :
  LPAREN patternList RPAREN
| LPAREN patternList RPAREN parameterList
  ;

functionBody :
  expression // TODO
| VDM_SUBCLASSRESP // TODO
| VDM_NOTYETSPEC // TODO
  ;

parameterTypes : 
LPAREN RPAREN
{
    $$ = new Vector<APatternListTypePair>();
}
|LPAREN patternList COLON type RPAREN
{
    List<PPattern> patternList = (List<PPattern>)$2;
    List<APatternListTypePair> pltpl = new Vector<APatternListTypePair>();
    pltpl.add(new APatternListTypePair(false /*resolved*/, patternList, (PType)$4));
    $$ = pltpl;
}
| LPAREN patternList COLON type COMMA parameterTypes RPAREN
{
    List<PPattern> patternList = (List<PPattern>)$2;
    List<APatternListTypePair> pltpl = (List<APatternListTypePair>)$6;
    pltpl.add(new APatternListTypePair(false /*resolved*/, patternList, (PType)$4));
    $$ = pltpl;
}
; 

identifierTypePairList_opt 
: /* empty */
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
    AIdentifierTypePair typePair = 
	new AIdentifierTypePair(null /*resolved*/, 
				extractLexIdentifierToken((CmlLexeme)$1), 
				(PType)$3
				);
    List<AIdentifierTypePair> typePairs = new Vector<AIdentifierTypePair>();
    typePairs.add(typePair);
    $$ = typePairs;
}
| IDENTIFIER COLON type COMMA identifierTypePairList
{
    AIdentifierTypePair typePair = 
	new AIdentifierTypePair(null /*resolved*/, 
				extractLexIdentifierToken((CmlLexeme)$1), 
				(PType)$3
				);
    List<AIdentifierTypePair> typePairs = (List<AIdentifierTypePair>)$5;
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
  MEASURE expression
| /* empty */
  ;

/* 3.4 Operation Definitions */

operationDefs :
  OPERATIONS operationDefList
  {
      List<? extends SOperationDefinition> opDefinitions = 
	  (List<? extends SOperationDefinition>)$2;
      LexLocation lastInListLoc = 
	  opDefinitions.get(opDefinitions.size()-1).getLocation();
      LexLocation location = extractLexLocation((CmlLexeme)$1,
						lastInListLoc);
      $$ = new AOperationDeclaration(location, 
				     NameScope.GLOBAL,
				     opDefinitions);
  }
  ;
  
operationDefList :
  operationDef
{
    List<SOperationDefinition> opDefinitions = 
	new Vector<SOperationDefinition>();
    opDefinitions.add((SOperationDefinition)$1);
    $$ = opDefinitions;
}
| operationDef SEMI operationDefList
{
    List<SOperationDefinition> opDefinitions = 
	  (List<SOperationDefinition>)$3;
    opDefinitions.add((SOperationDefinition)$1);
    $$ = opDefinitions;
}
;

/* FIXME the optional trailing semicolon in the operations definitions is presently not optional */

operationDef 
: implicitOperationDef 
{
    $$ = $1;
}
| explicitOperationDef // TODO
;

 explicitOperationDef
 : qualifier IDENTIFIER COLON operationType IDENTIFIER parameterList DEQUALS operationBody externals_opt preExpr_opt postExpr_opt
 {
   LexLocation loc = extractLexLocation ( (CmlLexeme)$2 );
   AExplicitOperationOperationDefinition res = new AExplicitOperationOperationDefinition();
   res.setLocation( loc );
   $$ = res;
 }
;

implicitOperationDef
: qualifier IDENTIFIER parameterTypes identifierTypePairList_opt externals_opt preExpr_opt postExpr
{
    AAccessSpecifierAccessSpecifier access = 
	(AAccessSpecifierAccessSpecifier)$1;
    LexNameToken name = extractLexNameToken((CmlLexeme)$2);
    List<? extends APatternListTypePair> parameterPatterns = 
	(List<? extends APatternListTypePair>)$3; 
    List<? extends AIdentifierTypePair> result = 
	(List<? extends AIdentifierTypePair>)$4;
    List<? extends AExternalClause> externals = 
	(List<? extends AExternalClause>)$5;
    PExp precondition = (PExp)$6;
    PExp postcondition = (PExp)$7;
    LexLocation location = null;
    if (access != null)
	location = combineLexLocation(name.location,
				      postcondition.getLocation());
    else
	location = combineLexLocation(access.getLocation(),
				      postcondition.getLocation());
					      
    AImplicitOperationOperationDefinition ifunc = 
	new AImplicitOperationOperationDefinition(location, 
						  NameScope.GLOBAL, 
						  null /*used*/, 
						  access, 
						  parameterPatterns, 
						  result, 
						  externals, 
						  precondition, 
						  postcondition, 
						  null/*errors*/, 
						  null/*isConstructor_*/);
    $$ = ifunc;
}
;

operationType :
  type OPERATIONARROW type // TODO
| VDMUNITTYPE OPERATIONARROW type // TODO
| type OPERATIONARROW VDMUNITTYPE // TODO
| VDMUNITTYPE OPERATIONARROW VDMUNITTYPE // TODO
  ;

operationBody :
  action // TODO
| VDM_SUBCLASSRESP // TODO
| VDM_NOTYETSPEC // TODO
  ;

externals_opt:
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
  VDM_FRAME varInformationList
  {
      $$ = $2;
  }
  ;

varInformationList :
  varInformation
  {
      List<AExternalClause> infoList = 
	  new Vector<AExternalClause>();
      infoList.add((AExternalClause)$1);
      $$ = infoList;
  }
| varInformation varInformationList
{
    List<AExternalClause> infoList = 
	(List<AExternalClause>)$2;
    infoList.add((AExternalClause)$1);
    $$ = infoList;
}
  ;

varInformation:
  mode nameList
  {
      $$ = new AExternalClause((LexToken)$1, 
       			       (List<? extends LexNameToken>)$2);
  }
| mode nameList COLON type
{
    $$ = new AExternalClause((LexToken)$1, 
     			     (List<? extends LexNameToken>)$2,
     			     (PType)$4);
}
;

mode :
VDM_RD
{
    $$ = new LexToken(extractLexLocation((CmlLexeme)$1),
    					 VDMToken.READ); 
}
| VDM_WR
{
    $$ = new LexToken(extractLexLocation((CmlLexeme)$1),
    					 VDMToken.WRITE); 
}
;

initialDef : 
INITIAL operationDef // TODO where's the semi?
;

/* 3.5 Instance Variable Definitions */

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
      
      // LexLocation lastInListLoc = 
      AStateDefinition stateDef = (AStateDefinition)$2;
      // LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$1),
      // 					   stateDef.getLocation());
      $$  = new AStateDeclaration(null,
				  NameScope.GLOBAL,
				  stateDef);
  }
| STATE 
  {
      $$  = new AStateDeclaration(extractLexLocation((CmlLexeme)$1),NameScope.GLOBAL,null);
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
|  stateDef SEMI
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

stateDef:
qualifier assignmentDef
{
    $$ = $2;
}
| invariantDef
{
    $$ = $1;
}
;

invariantDef :
VDMINV expression 
 {
     //  if (42 > 2) throw new RuntimeException("In expression");
     PExp exp = (PExp) $2;
     LexLocation location = extractLexLocation((CmlLexeme)$1,exp.getLocation());
     $$ = new AClassInvariantDefinition(location, 
					NameScope.GLOBAL, 
					true, 
					null/*AAccessSpecifierAccessSpecifier access_*/,
					exp);
 }
;

/* 4 Expressions */

expressionList :
expression
{
    List<PExp> exps = new Vector<PExp>();
    exps.add((PExp)$1);
    $$ = exps;    
}
| expression COMMA expressionList
{
    List<PExp> exps = (List<PExp>)$3;
    exps.add((PExp)$1);
    $$ = exps;    
}
;

expression :
/* RWL, tokens? 
 * 
 *  Ohh, Tokens as in <connecting> are not in the gramma?
 *
 */
LT IDENTIFIER GT 
{
  
  LexLocation loc = combineLexLocation( extractLexLocation ( (CmlLexeme)$1 ),
				    extractLexLocation ( (CmlLexeme)$3 ) );

  // TODO construct a LexQuoteToken
  AQuoteLiteralSymbolicLiteralExp res = new AQuoteLiteralSymbolicLiteralExp( loc, null );
  $$ = res;
}
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
| STRING 
{
  // Get a whole STRING from the lexer  
  CmlLexeme s = (CmlLexeme)$1;

  LexLocation sl = extractLexLocation ( s );
  
  // extract the string and convert it to a char array
  String str = s.getValue();
  char[] chrs = str.toCharArray();

  // build a list of ACharLiteralSymbolicLiteralExp from the lexer String
  List<PExp> members = new LinkedList<PExp>();
  for(int i = 0; i < chrs.length;i++)
    {
      LexLocation cl = new LexLocation(currentSourceFile.getFile(), "Default",
				       sl.startLine, sl.startPos + i,
				       sl.startLine, sl.startPos + (i + 1),0,0);
      members.add(new ACharLiteralSymbolicLiteralExp(cl, new LexCharacterToken( chrs[i], cl )) ); 
    }

  // Build the ASeqEnumSeqExp as usual
  ASeqEnumSeqExp res = new ASeqEnumSeqExp( sl, members );
  $$ = res;
}
|
  LPAREN expression RPAREN
  {
      LexLocation loc = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$3);
      $$ = new ABracketedExp(loc,(PExp)$2);
  }
| LET localDefList IN expression
{
  List<PDefinition> l = (List<PDefinition>)$2;
  PExp e = (PExp)$4;
  LexLocation loc = extractLexLocation( (CmlLexeme) $1, e.getLocation());
  $$ = new ALetDefExp( loc, l, e );
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
| quantifiedExpr
{
  $$ = $1;
}
| setEnumeration
{
  $$ = $1;
}
| setComprehension
{
  $$ = $1;
}
| setRangeExpr
{
  $$ = $1;
}
| sequenceEnumeration
{
  $$ = $1;
}
| sequenceComprehension
{
  $$ = $1;
}
| subsequence
{
  $$ = $1;
}
| mapEnumeration
{
  $$ = $1;
}
| mapComprehension
{
  $$ = $1;
}
| tupleConstructor
{
  $$ = $1;
}
| recordConstructor
{
  $$ = $1;
}
| apply
{
  $$ = $1;
}
| fieldSelect
{
  $$ = $1;
}
| tupleSelect
{
  $$ = $1;
}
| lambdaExpr
{
  $$ = $1;
}

| newExpr
{
  $$ = $1;
}
| SELF
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
| ISOFCLASS LPAREN name COMMA expression RPAREN
{
  $$ = $1;
}
| name
{
     LexNameToken lnt = (LexNameToken)$1;
    $$ = new ANameExp(lnt.location,lnt);
}
| oldName
{
    LexNameToken lnt = (LexNameToken)$1;
    //FIXME: this is not correct!
    $$ = new ANameExp(lnt.location,lnt);
}
| symbolicLiteral
  ;

symbolicLiteral:
numericLiteral
{
    LexIntegerToken lit = (LexIntegerToken)$1;
    $$ = new AIntLiteralSymbolicLiteralExp(lit.location,lit);
}
 // TODO
/*| booleanLiteral
| nilLiteral
| characterLiteral
| textLiteral
| quoteLiteral*/
;

numericLiteral:
 NUMERAL 
 {
    CmlLexeme lexeme = (CmlLexeme)$1;
    LexLocation loc = extractLexLocation(lexeme);
    $$ = new LexIntegerToken(Long.decode(lexeme.getValue()),loc);   
 }
| HEX_LITERAL
{
    CmlLexeme lexeme = (CmlLexeme)$1;
    LexLocation loc = extractLexLocation(lexeme);
    $$ = new LexIntegerToken(Long.decode(lexeme.getValue()),loc);   
}
;

localDefList :
  localDef
  {
    List<PDefinition> res = new LinkedList<PDefinition>();
    res.add((PDefinition)$1);
    $$ = res;
  }
| localDef COMMA localDefList
{
  PDefinition def = (PDefinition)$1;
  List<PDefinition> defs = (List<PDefinition>)$3;
  defs.add(def);
  $$ = defs;
}
;

localDef :
valueDef
{
  $$ = $1;
}
|
explicitFunctionDef
{
  $$ = $1;
}
;

/* 4.3 Conditional Expressions */

ifExpr :
  IF expression THEN expression elseExprs
  {
    // Get constituents 
    // $1 IF
    CmlLexeme _if = (CmlLexeme)$1;

    Position sif = _if.getStartPos();
    Position eif = _if.getEndPos();

    PExp test = (PExp)$2;
    // $3 THEN
    PExp then = (PExp)$4;
    List<AElseIfExp> elses = (List<AElseIfExp>)$5;
    
    // Build an AIfExp ast node
    AIfExp ifexp = new AIfExp();
    ifexp.setTest(test);
    ifexp.setThen(then);
    ifexp.setElseList(elses);
    ifexp.setLocation( 
		      combineLexLocation( new LexLocation(null, 
							  "DEFAULT", 
							  sif.line, 
							  sif.column, 
							  sif.line, 
							  eif.column, 
							  sif.offset, 
							  eif.offset ),  
					  extractLastLexLocation( elses ) ));
    $$ = ifexp;
    
  }
  ;

elseExprs :
  ELSE expression
  {

    CmlLexeme elsetok = (CmlLexeme)$1;
    PExp exp = (PExp)$2;

    AElseIfExp eie = new AElseIfExp();
    eie.setLocation( 
		    combineLexLocation(  extractLexLocation( elsetok ), 
					 exp.getLocation() )
		     );
    List<AElseIfExp> res = new LinkedList<AElseIfExp>();
    res.add(eie);
    $$ =res;
  }
| ELSEIF expression THEN expression elseExprs
{
  // Get constituents
  CmlLexeme elseif = (CmlLexeme)$1;
  PExp elseIf = (PExp)$2;
  // $3 THEN
  PExp then = (PExp)$4;
  List<AElseIfExp> tail = (List<AElseIfExp>)$5;
  
  LexLocation loc = combineLexLocation( extractLexLocation( elseif ),
					then.getLocation() );

  // Build result
  AElseIfExp eie = new AElseIfExp();
  eie.setElseIf(elseIf);
  eie.setThen(then);
  eie.setLocation( loc );
  tail.add(eie);
  $$ = tail;
}
  ;

casesExpr :
  CASES expression COLON casesExprAltList END
  {
    // Get Constituents
    CmlLexeme cases = (CmlLexeme)$1;
    PExp exp = (PExp)$2;
    // $3 COLON
    ACasesExp bubbleUp = (ACasesExp)$4; // Others and Cases are taken care of
    CmlLexeme end = (CmlLexeme)$5;
    LexLocation lexLoc = combineLexLocation( extractLexLocation( cases ),
					     extractLexLocation( end ) );
					     
    // Set expression and location
    bubbleUp.setExpression(exp);
    bubbleUp.setLocation(lexLoc);
   
    $$ = bubbleUp;
  }
  ;

casesExprAltList :
  casesExprAlt
  {
    // Get Constituent
    ACasesExp casesExp = new ACasesExp();

    // Set up a CasesExp and add this alternative to its list
    ACaseAlternative caseAlt = (ACaseAlternative)$1;
    casesExp.getCases().add(caseAlt);
    $$ = casesExp;
  }
| casesExprAlt OTHERS RARROW expression
{
  // Get constituents
  ACaseAlternative altExp = (ACaseAlternative)$1;
  // $2 OTHERS
  // $3 RARROW
  PExp othExp = (PExp)$4;

  // Build ACasesExp
  List<ACaseAlternative> altList = new LinkedList<ACaseAlternative>();
  altList.add(altExp);
  ACasesExp casesExp = new ACasesExp();
  casesExp.setCases(altList);
  casesExp.setOthers(othExp);

  $$ = casesExp;
}
| casesExprAlt casesExprAltList
{
  
  // Get constituents
  ACaseAlternative altExp = (ACaseAlternative)$1;
  ACasesExp casesExp = (ACasesExp)$2;

  // Add altExp to tail
  casesExp.getCases().add(altExp);
  $$ = casesExp;
}
  ;

casesExprAlt :
  patternList RARROW expression SEMI
  {
    List<PPattern> patList = (List<PPattern>)$1;
    // $2 RARROW
    PExp exp = (PExp)$3;
    CmlLexeme semi = (CmlLexeme)$4;

    LexLocation leftMost = extractLexLeftMostFromPatterns( patList );
    LexLocation loc = combineLexLocation ( leftMost, 
					   extractLexLocation( semi )
					   );
    
    // Build res
    ACaseAlternative res = new ACaseAlternative();
    res.setPattern(patList);
    res.setLocation(loc);
    res.setCexp( exp );

    $$ = res;
  }
  ;

/* 4.4 Unary Expressions */

/* FIXME this hack makes me really tempted to see if I can use the lexer to define PLUS, MINUS, etc and just call the terminals UNARYOP and BINARYOP */
/* turns out that terminals embedded in a rule that only lists terminals ends up dropping precedence info */

/* unaryExpr : */
/*     unaryOperator expression */
/*   | INVERSE expression */
/*     ; */

unaryExpr :
PLUS expression %prec UPLUS
{
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
	combineLexLocation(opLocation,exp.getLocation());
    $$ = new AUnaryPlusUnaryExp(location,exp);
}
| MINUS expression %prec UMINUS
  {
      PExp exp = (PExp)$2;
      LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
      LexLocation location = 
	  combineLexLocation(opLocation,exp.getLocation());
      $$ = new AUnaryMinusUnaryExp(location,exp);
  }
| ABS expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new AAbsoluteUnaryExp(location,exp);
  }
| FLOOR expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new AFloorUnaryExp(location,exp);
  }
| NOT expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new ANotUnaryExp(location,exp);
  }
| CARD expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new ACardinalityUnaryExp(location,exp);
  }
| POWER expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new APowerSetUnaryExp(location,exp);
  }
| DUNION expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new ADistUnionUnaryExp(location,exp);
  }
| DINTER expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new ADistIntersectUnaryExp(location,exp);
  }
| HD expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new AHeadUnaryExp(location,exp);
  }
| TL expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new ATailUnaryExp(location,exp);
  }
| LEN expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new ALenUnaryExp(location,exp);
  }
| ELEMS expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new AElementsUnaryExp(location,exp);
  }
| INDS expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new AIndicesUnaryExp(location,exp);
  }
| REVERSE expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new AReverseUnaryExp(location,exp);
  }
| DCONC expression
{
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
	combineLexLocation(opLocation,exp.getLocation());
    $$ = new ADistConcatUnaryExp(location,exp);
}
| DOM expression
{
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
	combineLexLocation(opLocation,exp.getLocation());
    $$ = new AMapDomainUnaryExp(location,exp);
}
| RNG expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new AMapRangeUnaryExp(location,exp);
  }
| MERGE expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new ADistMergeUnaryExp(location,exp);
  }
| INVERSE expression
  {
    PExp exp = (PExp)$2;
    LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
    LexLocation location = 
      combineLexLocation(opLocation,exp.getLocation());
    $$ = new AMapInverseUnaryExp(location,exp);
  }
  ;

/* 4.5 Binary Expressions */

/* binaryExpr : */
/*     expression binaryOperator expression */
/*     ; */

binaryExpr :
  expression PLUS expression
  {
      LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
      $$ = new APlusNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);
  }
| expression STAR expression
  {
      LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
      $$ = new ATimesNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);  
  }
| expression MINUS expression
  {
      LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
      $$ = new ASubstractNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);
  }
| expression DIVIDE expression
  {
      LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
      $$ = new ADivideNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);
  }
| expression DIV expression
  {
      LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
      $$ = new ADivNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);
  }
| expression REM expression
  {
      LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
      $$ = new ARemNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);
  }
| expression MOD expression
  {
      LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
      $$ = new AModNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);
  }
| expression LT expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new ALessNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression LTE expression 
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new ALessEqualNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression GT expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AGreaterNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression GTE expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AGreaterEqualNumericBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression EQUALS expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AEqualsBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression NEQ expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new ANotEqualBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression OR expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AOrBooleanBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression AND expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AAndBooleanBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression IMPLY expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AImpliesBooleanBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression BIMPLY expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AEquivalentBooleanBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression INSET expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AInSetBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression NOTINSET expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new ANotInSetBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression SUBSET expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new ASubsetBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression PROPER_SUBSET expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AProperSubsetBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression UNION expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new ASetUnionBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression SETDIFF expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new ASetDifferenceBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression INTER expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new ASetIntersectBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression CONC expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new ASeqConcatBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression OVERWRITE expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AModifyBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}
| expression MAPMERGE expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AMapUnionBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}

| expression DOMRES expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new ADomainResToBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}

| expression VDM_MAP_DOMAIN_RESTRICT_BY expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AModifyBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}

| expression RNGRES expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AModifyBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}

| expression RNGSUB expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AModifyBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}

| expression COMP expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AModifyBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}

| expression ITERATE expression
{
    LexLocation loc = combineLexLocation(((PExp)$1).getLocation(),((PExp)$3).getLocation());
    $$ = new AModifyBinaryExp(loc,(PExp)$1,null,(PExp)$3);
}

  ;

/* 4.6 Quantified Expressions */

quantifiedExpr :
  FORALL bindList AMP expression
  {
    // Get constituents
    CmlLexeme forall = (CmlLexeme)$1;
    List<PMultipleBind> binds = (List<PMultipleBind>)$2;
    CmlLexeme amp = ( CmlLexeme)$3;
    PExp exp = (PExp)$4;
    
    LexLocation loc = combineLexLocation( extractLexLocation ( forall ),
					  exp.getLocation() );

    // Build forall expression
    AForAllExp forallexp = new AForAllExp( loc, binds, exp );
    $$ = forallexp;
    
  }
| EXISTS bindList AMP expression
{
  // Get constituents
  CmlLexeme exists = (CmlLexeme)$1;
  List<PMultipleBind> binds = (List<PMultipleBind>)$2;
  // CmlLexeme amp = (CmlLexeme)$3; AMP $3
  PExp exp = (PExp)$4;

  LexLocation loc = combineLexLocation( extractLexLocation( exists ),
					exp.getLocation() );

  // Build exists expression
  AExistsExp existsExp = new AExistsExp( loc, binds, exp );
  $$ = existsExp;
}
| EXISTS1 bind AMP expression
{
  // Get constituents
  CmlLexeme exists = (CmlLexeme)$1;
  PBind bind = (PBind)$2;
  // CmlLexeme amp = (CmlLexeme)$3; AMP $3
  PExp exp = (PExp)$4;

  LexLocation loc = combineLexLocation( extractLexLocation( exists ),
					exp.getLocation() );

  // Build exists expression
  AExists1Exp existsExp = new AExists1Exp( loc, bind, exp, null );
  $$ = existsExp;
}
  ;

/* 4.7 Set Expressions */

setEnumeration :
LCURLY RCURLY
{
  CmlLexeme lc = (CmlLexeme)$1;
  CmlLexeme rc = (CmlLexeme)$2;
  LexLocation loc = combineLexLocation ( extractLexLocation ( lc ),
					 extractLexLocation ( rc ) );
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation( loc );
  $$ = res;
}
| LCURLY expressionList RCURLY
{
    LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3);
    List<PExp> members = (List<PExp>)$2;
    $$ = new ASetEnumSetExp(location,members);
}
;

setComprehension :
  LCURLY expression BAR bindList RCURLY
  {
    // Get Constituents
    CmlLexeme lcurly = (CmlLexeme)$1;
    PExp exp = (PExp)$2;
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)$4;
    CmlLexeme rcurle = (CmlLexeme)$5;

    LexLocation loc = combineLexLocation( extractLexLocation( lcurly ), 
					  extractLexLocation( rcurle ) );

    // Build result
    ASetCompSetExp setComp = new ASetCompSetExp( loc, exp, binds, null );
    $$ = setComp;
    
  }
| LCURLY expression BAR bindList AT expression RCURLY
{
    // Get Constituents
    CmlLexeme lcurly = (CmlLexeme)$1;
    PExp exp = (PExp)$2;
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)$4;
    // $5 AMP
    PExp pred = (PExp)$6;
    CmlLexeme rcurle = (CmlLexeme)$7;

    LexLocation loc = combineLexLocation( extractLexLocation( lcurly ), 
					  extractLexLocation( rcurle ) );

    // Build result
    ASetCompSetExp setComp = new ASetCompSetExp( loc, exp, binds, pred );
    $$ = setComp;
}
  ;

setRangeExpr : 
  LCURLY expression COMMA ELLIPSIS COMMA expression RCURLY
  {
    // Get constituents
    CmlLexeme lcurly = (CmlLexeme)$1;
    PExp start = (PExp)$2;
    // COMMA $3
    // ELLIPSIS $4
    // COMMA $5
    PExp end = (PExp)$6;
    CmlLexeme rcurly = (CmlLexeme)$7;

    LexLocation loc = combineLexLocation( extractLexLocation( lcurly ),
					  extractLexLocation( rcurly ) );

    // Build ASetRangeSetExpr
    ASetRangeSetExp res = new ASetRangeSetExp( loc, start, end );
    $$ = res;
  }
  ;

/* 4.8 Sequence Expression */

sequenceEnumeration :
  LSQUARE RSQUARE
  {
    CmlLexeme lsqr = (CmlLexeme)$1;
    CmlLexeme rsqr = (CmlLexeme)$2;
    List<PExp> exps = new LinkedList<PExp>();
    LexLocation loc = combineLexLocation( extractLexLocation( lsqr ),
					  extractLexLocation( rsqr ) );
    
    ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
    $$ = exp;
  }
| LSQUARE expressionList RSQUARE
{
    CmlLexeme lsqr = (CmlLexeme)$1;
    List<PExp> exps = (List<PExp>)$2;
    CmlLexeme rsqr = (CmlLexeme)$3;
    
    LexLocation loc = combineLexLocation( extractLexLocation( lsqr ),
					  extractLexLocation( rsqr ) );
    
    ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
    $$ = exp;
}
  ;


sequenceComprehension :
  LSQUARE expression BAR setBind RSQUARE
  {
    CmlLexeme lsqr = (CmlLexeme)$1;
    PExp exp = (PExp)$2;
    // BAR $3
    ASetBind binds = (ASetBind)$4;
    CmlLexeme rsqr = (CmlLexeme)$5;

    LexLocation loc = combineLexLocation ( extractLexLocation ( lsqr ),
					   extractLexLocation ( rsqr ) );


    // Build response
    ASeqCompSeqExp res = new ASeqCompSeqExp( loc, exp, binds, null );
    $$ = res;
  }
|
 LSQUARE expression BAR setBind AMP expression RSQUARE
{
    CmlLexeme lsqr = (CmlLexeme)$1;
    PExp exp = (PExp)$2;
    // BAR $3
    ASetBind binds = (ASetBind)$4;
    // AMP $5
    PExp pred = (PExp)$6;
    CmlLexeme rsqr = (CmlLexeme)$7;

    LexLocation loc = combineLexLocation ( extractLexLocation ( lsqr ),
					   extractLexLocation ( rsqr ) );

    // Build response
    ASeqCompSeqExp res = new ASeqCompSeqExp( loc, exp, binds, pred );
    $$ = res;
}
  ;

subsequence :
  expression LPAREN expression COMMA ELLIPSIS COMMA expression RPAREN
  {
    PExp seq = (PExp)$1;
    // $2 LPAREN
    PExp from = (PExp)$3;
    // $4 COMMA
    // $5 ELLIPSIS
    // $6 COMMA
    PExp to   = (PExp)$7;
    CmlLexeme rparen = (CmlLexeme)$8;

    LexLocation loc = combineLexLocation(seq.getLocation(),
					 extractLexLocation( rparen ) );
    
    // Build result
    ASubseqExp res = new ASubseqExp( loc, seq, from , to );
    $$ = res;
  }
  ;

mapEnumeration :
  LCURLY MAPLETARROW RCURLY
  {
    CmlLexeme lcurly = (CmlLexeme)$1;
    CmlLexeme rcurly = (CmlLexeme)$2;

    LexLocation loc = combineLexLocation( extractLexLocation ( lcurly ),
					  extractLexLocation ( rcurly ) );

    AMapEnumMapExp res = new AMapEnumMapExp( loc, new LinkedList<AMapletExp>() );
    $$ = res;
  }
| LCURLY mapletList RCURLY
{
    CmlLexeme lcurly = (CmlLexeme)$1;
    List<AMapletExp> maplets = (List<AMapletExp>)$2;
    CmlLexeme rcurly = (CmlLexeme)$3;

    LexLocation loc = combineLexLocation( extractLexLocation ( lcurly ),
					  extractLexLocation ( rcurly ) );

    AMapEnumMapExp res = new AMapEnumMapExp( loc, maplets );
    $$ = res;
}
  ;

mapletList :
  maplet
  {
    List<AMapletExp> res = new LinkedList<AMapletExp>();
    res.add( (AMapletExp) $1 );
    $$ = res;
  }
| maplet COMMA mapletList
  {
    AMapletExp hd = (AMapletExp)$1;
    // $2 COMMA
    List<AMapletExp> tail = (List<AMapletExp>)$3;
    
    tail.add(hd);
    $$ = tail;
  }
  ;

maplet :
  expression MAPLETARROW expression
  {
    PExp domValue = (PExp)$1;
    // MAPLETARROW
    PExp rngValue = (PExp)$3;

    LexLocation loc = combineLexLocation( domValue.getLocation(),
					  rngValue.getLocation() );

    AMapletExp res = new AMapletExp(loc, domValue, rngValue);
    $$ = res;
  }
  ;

mapComprehension :
  LCURLY maplet BAR bindList RCURLY  
  {
    CmlLexeme lcurl = (CmlLexeme)$1;
    AMapletExp maplet = (AMapletExp)$2;
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)$4;
    CmlLexeme rcurl = (CmlLexeme)$5;

    LexLocation loc = combineLexLocation( extractLexLocation ( lcurl ),
					  extractLexLocation ( rcurl ) );

    AMapCompMapExp res = new AMapCompMapExp( loc, maplet, binds, null);
    $$ = res;

  }
| LCURLY maplet BAR bindList AMP expression RCURLY
{

    CmlLexeme lcurl = (CmlLexeme)$1;
    AMapletExp maplet = (AMapletExp)$2;
    // $3 BAR
    List<PMultipleBind> binds = (List<PMultipleBind>)$4;
    // $5 AMP
    PExp pred = (PExp)$6;
    CmlLexeme rcurl = (CmlLexeme)$7;

    LexLocation loc = combineLexLocation( extractLexLocation ( lcurl ),
					  extractLexLocation ( rcurl ) );


    AMapCompMapExp res = new AMapCompMapExp( loc, maplet, binds, pred);
    $$ = res;
}
  ;

/* 4.10 Tuple Constructor Expression */

tupleConstructor :
  MKUNDER LPAREN expressionList RPAREN
  {
    CmlLexeme mku = (CmlLexeme)$1;
    // $2 LPAREN
    List<PExp> exprs = (List<PExp>)$3;
    CmlLexeme rparen = (CmlLexeme)$4;

    LexLocation loc = combineLexLocation( extractLexLocation ( mku ),
					  extractLexLocation ( rparen ) );

    ATupleExp res = new ATupleExp( loc, exprs );
    $$ = res;
  }
  ;

/* 4.11 Record Expressions */

recordConstructor :
  MKUNDER name LPAREN expressionList RPAREN
  {
    CmlLexeme mku = (CmlLexeme)$1;
    LexNameToken name = (LexNameToken)$2;
    // $3 LPAREN
    List<PExp> exprs = (List<PExp>)$4;
    CmlLexeme rparen = (CmlLexeme)$5;

    LexLocation loc = combineLexLocation( extractLexLocation ( mku ) ,
					  extractLexLocation ( rparen ) );

    ARecordExp res = new ARecordExp(loc, name, exprs );
    $$ = res;
  }
  ;

/* 4.12 Apply Expressions */

apply :
  expression LPAREN expressionList RPAREN
  {
      PExp root = (PExp)$1;
      List<? extends PExp> args = (List<? extends PExp>)$3;
      
      LexLocation location = combineLexLocation(root.getLocation(),
						extractLexLocation((CmlLexeme)$4));
      $$ = new AApplyExp(location, root, args);
  }
  ;

fieldSelect :
  expression DOT IDENTIFIER
  {
    PExp obj = (PExp)$1;
    // $2 DOT
    CmlLexeme id = (CmlLexeme)$3;
    LexNameToken name = extractLexNameToken( id );

    LexLocation loc = combineLexLocation ( obj.getLocation(),
					   extractLexLocation( id ) );

    AFieldExp res = new AFieldExp( loc, obj, name, null );
    $$ = res;
  }
  ;

tupleSelect :
  expression DOTHASH NUMERAL
  {
    PExp tup = (PExp)$1;
    // $2 DOTHASH
    CmlLexeme lexNum = (CmlLexeme)$3;

    int num = Integer.parseInt(lexNum.getValue());

    LexLocation loc = combineLexLocation ( tup.getLocation(),
					   extractLexLocation ( lexNum ) );

    ATupleSelectExp res = new ATupleSelectExp( loc, tup, num );
    $$ = res;
  }
  ;

/* 4.13 The Lambda Expression */

lambdaExpr :
  LAMBDA typeBindList AMP expression
  {
    CmlLexeme l = (CmlLexeme)$1;
    List<ATypeBind> binds = (List<ATypeBind>)$2;
    // AMP $3
    PExp body = (PExp)$4;

    LexLocation loc = combineLexLocation ( extractLexLocation ( l ),
					   body.getLocation() );

    ALambdaExp res = new ALambdaExp( loc, binds, body, null, null );
    $$ = res;
  }
  ;

/* 4.14 The New Expression */

/* FIXME there is a reduce/reduce with <apply> if you take out the PARENs */
// RWL: For now we are happy with IDENTIFIERS after NEW, if need be may
// introduce the paren's for reflection like instantiation of objects.
newExpr :
//   NEW LPAREN expression LPAREN expressionList RPAREN RPAREN
NEW IDENTIFIER LPAREN expressionList RPAREN
{
  CmlLexeme wen = (CmlLexeme)$1;
  CmlLexeme lexId  = (CmlLexeme)$2;
  // $3 LPAREN
  List<PExp> exprs = (List<PExp>)$4;
  CmlLexeme rparen = (CmlLexeme)$5;

  LexLocation loc = combineLexLocation( extractLexLocation ( wen ),
					extractLexLocation ( rparen ) );

  LexIdentifierToken id = extractLexIdentifierToken ( lexId );

  ANewExp res = new ANewExp( loc, id, exprs );
}
  ;

/* 4.16 The Is Expression */

generalIsExpr :
  ISUNDER name LPAREN expression RPAREN
  {
    CmlLexeme isUnder = (CmlLexeme)$1;
    LexNameToken typeName = (LexNameToken)$2;
    // $3 LPAREN
    PExp test = (PExp)$4;
    CmlLexeme rparen = (CmlLexeme)$5;

    LexLocation loc = combineLexLocation ( extractLexLocation ( isUnder ),
					   extractLexLocation ( rparen  ) );

    AIsExp res = new AIsExp( loc,typeName, test, null );
    $$ = res;

  }
| ISUNDER basicType LPAREN expression RPAREN
{
   CmlLexeme isUnder = (CmlLexeme)$1;
   PType type = (PType)$2;
   // LPAREN $3
   PExp test = (PExp)$4;
   CmlLexeme rparen = (CmlLexeme)$5;

   LexLocation loc = combineLexLocation ( extractLexLocation ( isUnder ),
					  extractLexLocation ( rparen ) );

   AIsExp res = new AIsExp( loc, null, test, null );
   res.setBasicType( type );
   $$ = res;

}
| ISUNDER LPAREN expression COMMA type RPAREN
{
   CmlLexeme isUnder = (CmlLexeme)$1;
   // LPAREN $2
   PExp test = (PExp)$3;
   // COMMA $4
   PType type = (PType)$5;
   CmlLexeme rparen = (CmlLexeme)$6;

   LexLocation loc = combineLexLocation ( extractLexLocation ( isUnder ),
					  extractLexLocation ( rparen ) );


   AIsExp res = new AIsExp( loc, null, test, null);
   res.setBasicType( type );
   $$ = res;
}
  ;

basicType :
  TBOOL
  {
    CmlLexeme bool = (CmlLexeme)$1;
    LexLocation loc = extractLexLocation( bool );

    ABooleanBasicType res = new ABooleanBasicType( loc, false, null );
    $$ = res;
  }
| TNAT
  {
    CmlLexeme bool = (CmlLexeme)$1;
    LexLocation loc = extractLexLocation( bool );
    ANatNumericBasicType res = new ANatNumericBasicType( loc, false, null );
    $$ = res;
  }
| TNAT1
  {
    CmlLexeme bool = (CmlLexeme)$1;
    LexLocation loc = extractLexLocation( bool );
    ANatOneNumericBasicType res = new ANatOneNumericBasicType( loc, false, null );
    $$ = res;
  }
| TINT
  {
    CmlLexeme bool = (CmlLexeme)$1;
    LexLocation loc = extractLexLocation( bool );
    AIntNumericBasicType res = new AIntNumericBasicType( loc, false, null );
    $$ = res;
  }

| TRAT
  {
    CmlLexeme bool = (CmlLexeme)$1;
    LexLocation loc = extractLexLocation( bool );
    ARationalNumericBasicType res = new ARationalNumericBasicType( loc, false );
    $$ = res;
  }

| TREAL
  {
    CmlLexeme bool = (CmlLexeme)$1;
    LexLocation loc = extractLexLocation( bool );
    ARealNumericBasicType res = new ARealNumericBasicType( loc, false, null );
    $$ = res;
  }

| TCHAR
  {
    CmlLexeme bool = (CmlLexeme)$1;
    LexLocation loc = extractLexLocation( bool );
    ACharBasicType res = new ACharBasicType( loc, false, null );
    $$ = res;
  }

| TTOKEN
  {
    CmlLexeme bool = (CmlLexeme)$1;
    LexLocation loc = extractLexLocation( bool );
    ATokenBasicType res = new ATokenBasicType( loc, false );
    $$ = res;
  }

  ;

/* 4.17 The Precondition Expression */

preconditionExpr :
  PREUNDER LPAREN expressionList RPAREN
  {
    CmlLexeme preu = (CmlLexeme)$1;
    // LPAREN $2
    List<PExp> exprs = (List<PExp>)$3;
    CmlLexeme rparen = (CmlLexeme)$4;

    // RWL FIXME: Either this is right because we dedeuce the
    // function in a later phase where we know more or
    // the production above should be PREUNDER exp LPAREN expList RPAREN
    // however that introduces 36 reduce/reduce conflicts at this time.

    PExp function = null;
    LexLocation loc = combineLexLocation( extractLexLocation ( preu ),
					 extractLexLocation ( rparen ) );

    APreExp res = new APreExp( loc, function, exprs );
    $$ = res;
  }
  ;

/* 4.19 Names */

name :
IDENTIFIER
{
    $$ = extractLexNameToken((CmlLexeme)$1);
}
|IDENTIFIER BACKTICK IDENTIFIER
{
    $$ = extractLexNameToken((CmlLexeme)$3);
}
;

nameList :
name
{
    List<LexNameToken> identifiers = 
	new Vector<LexNameToken>();
    identifiers.add((LexNameToken)$1);
    $$ = identifiers;
}
| name COMMA nameList
{
    List<LexNameToken> identifiers = 
	(List<LexNameToken>)$3;
    identifiers.add((LexNameToken)$1);
    $$ = identifiers;
}
  ;

oldName :
  IDENTIFIER TILDE
  {
    $$ = extractLexNameToken((CmlLexeme)$1);
  }
  ;

/* 5 State Designators */
stateDesignator :
  name // TODO
| stateDesignator DOT IDENTIFIER // TODO
| stateDesignator LPAREN expression RPAREN // TODO
  ;

/* 6 Statements */

controlStatements :
/* non-deterministicIfStatement*/
  ifStatement
| casesStatement
 /*|generalCasesIfStatement*/
| callStatement
| specificationStatement
| returnStatement
/*| newStatement*/
 /*| non-deterministicDoStatement */
 /*| SequenceForLoop */
 /*| setForLoop */
 /*| indexForLoop*/
 /*| whileLoop */
;

  ;

/* 6.2 Block and Assignment Statements
 * to be clarified
 */

/* FIXME trailing semicolon not optional */
blockStatement :
  LPAREN action RPAREN
  {
      LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$3);
      PAction action = (PAction)$2;
      $$ = new ABlockAction(location, 
			    null, 
			    action);
  }
| LPAREN dclStatement action RPAREN
{
    LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$4);
    ADeclareStatementDeclareStatement dclStm = (ADeclareStatementDeclareStatement)$2;
    PAction action = (PAction)$3;
    $$ = new ABlockAction(location, 
			  dclStm, 
			  action);
}
  ;

dclStatement :
DCL assignmentDefList AT
{
    $$ = new ADeclareStatementDeclareStatement(extractLexLocation((CmlLexeme)$1,(CmlLexeme)$3), 
					       (List<? extends PDefinition>) $2);
}
;
  
assignmentDefList :
assignmentDef
{
    List<AAssignmentDefinition> assignmentDefs = 
	new Vector<AAssignmentDefinition>();
    assignmentDefs.add((AAssignmentDefinition)$1);
    $$ = assignmentDefs; 
}
| assignmentDef COMMA assignmentDefList
{
    List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)$3;
    
    if (assignmentDefs == null) 
	assignmentDefs = new Vector<AAssignmentDefinition>();
    
    assignmentDefs.add((AAssignmentDefinition)$1);
    $$ = assignmentDefs;
}
  ;

assignmentDef :
 IDENTIFIER COLON type
  {
      LexNameToken name = extractLexNameToken((CmlLexeme)$1);
      PType type = (PType)$3;
      LexLocation location = combineLexLocation(name.location,type.getLocation());
      AAccessSpecifierAccessSpecifier access = null;
      $$ = new AAssignmentDefinition(location, 
				     name, 
				     NameScope.LOCAL, 
				     false /*Boolean used_*/, 
				     null /*PDeclaration declaration_*/, 
				     access, 
				     type, 
				     null /*PExp expression_*/, 
				     null /*PType expType_*/);
  }
| IDENTIFIER COLON type ASSIGN expression
| IDENTIFIER COLON type IN expression
  ;

generalAssignStatement :
  assignStatement // TODO
| multiAssignStatement // TODO
  ;

assignStatement :
  stateDesignator ASSIGN expression // TODO
  | stateDesignator ASSIGN callStatement // TODO
  ;

assignStatementList :
  assignStatement // TODO
| assignStatement SEMI assignStatementList // TODO
  ;

multiAssignStatement :
  ATOMIC LPAREN assignStatement SEMI assignStatementList RPAREN // TODO
  ;

/* 6.3 Conditional Statements */
ifStatement :
  IF expression THEN action elseStatements // TODO
  {
  }
  ;

elseStatements :
  ELSE action // TODO
| ELSEIF expression THEN action elseStatements // TODO
  ;

casesStatement :
  CASES expression COLON casesStatementAltList END // TODO
  ;

casesStatementAltList :
  casesStatementAlt // TODO
| casesStatementAlt OTHERS RARROW action // TODO
| casesStatementAlt casesExprAltList // TODO
  ;

casesStatementAlt :
  patternList RARROW action // TODO
  
  ;

/* 6.4 Call and Return Statements */

/* FIXME the CURLYs are there there to avoid several whatever/reduce conflicts with the assignment statement */

callStatement :
  call // TODO
| objectDesignator ASSIGN call
  {
      ACallCallStatementControlStatementAction call = 
	  (ACallCallStatementControlStatementAction)$3;
      PObjectDesignator designator = (PObjectDesignator)$1;
      LexLocation location = combineLexLocation(designator.getLocation(),call.getLocation());
      $$ = new AAssignmentCallCallStatementControlStatementAction(location,
								  designator, 
								  call);
  }
;

call :
  STAR IDENTIFIER LPAREN expressionList RPAREN
  {
      LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$5);
      //PObjectDesignator designator = null;
      LexIdentifierToken name = extractLexIdentifierToken((CmlLexeme)$2);
      List<PExp> args = (List<PExp>)$4;
      $$ = new ACallCallStatementControlStatementAction(location, 
					   null, 
					 name,  
					 args);
  }
| STAR IDENTIFIER LPAREN RPAREN
  {
      LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$4);
      //PObjectDesignator designator = null;
      LexIdentifierToken name = extractLexIdentifierToken((CmlLexeme)$2);
      List<PExp> args = null;
      $$ = new ACallCallStatementControlStatementAction(location, 
					   null, 
					 name,  
					 args);
  }
| objectDesignator DOT STAR IDENTIFIER LPAREN expressionList RPAREN // TODO
{

}
| objectDesignator DOT STAR IDENTIFIER LPAREN RPAREN // TODO
{

}

objectDesignator :
  SELF
  {
      LexNameToken self = extractLexNameToken((CmlLexeme)$1);
      $$ = new ASelfObjectDesignator(self.location, self);
  }
| name
{
    LexNameToken name = (LexNameToken)$1;
    $$ = new ANameObjectDesignator(name.location, name, null);
}
| objectFieldReference // TODO
| objectApply // TODO
  ;

objectFieldReference :
objectDesignator DOT IDENTIFIER  // TODO
    ;

objectApply:
  objectDesignator LPAREN RPAREN // TODO
| objectDesignator LPAREN expressionList RPAREN
  {
      System.out.println("objectApply : objectDesignator LPAREN expressionList RPAREN");
  }
    ;

/* RWL, so the returnStatement production rule turned out to be:
 * RETURN RETURN expression, rathern than RETURN | RETURN
 * expression. Chaning the returnStatement into the latter introduces
 * 5 shift/reduce conflicts.
 *
 *
 */
returnStatement :
 RETURN  SEMI // TODO
| RETURN  expression SEMI // TODO
     ;
/* return inline above */

/* 6.5 The Specification Statement */

specificationStatement :
  LSQUARE implicitOperationBody RSQUARE // TODO
  ;

implicitOperationBody :
externals_opt preExpr_opt  postExpr // TODO
;

/* 7 Patterns and Bindings */

/* 7.1  */

pattern :
patternIdentifier // TODO
| patternLessID // TODO
;

patternLessID 
:
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
| pattern COMMA patternList
{
    List<PPattern> patterns = (Vector<PPattern>)$3;
    patterns.add((PPattern)$1);
    $$ = patterns;
}
  ;

patternIdentifier :
  IDENTIFIER
  {
      CmlLexeme lexeme = (CmlLexeme)$1;
      LexNameToken lnt = extractLexNameToken(lexeme);
      $$ = new AIdentifierPattern(lnt.location,null,false,lnt);
  }
| VDMDONTCARE // TODO
  ;

/* FIXME shift/reduce conflict from a bracketed expression */
matchValue :
  symbolicLiteral
  {
    PExp exp = (PExp)$1;
    if (exp instanceof AIntLiteralSymbolicLiteralExp)
      {
	AIntLiteralSymbolicLiteralExp intExp = (AIntLiteralSymbolicLiteralExp)exp;
	AIntegerPattern res = new AIntegerPattern();
	res.setLocation(intExp.getLocation());
	res.setValue(intExp.getValue());
	$$ = res;
      }
    else
      throw new RuntimeException("Unhandled expression type in pattern. ("+exp.getClass()+")"); // TODO RWL
  }
/* | LPAREN expression RPAREN */
  ;

/* FIXME not sure if if this is a minimum of one pattern or two; if the latter */
tuplePattern :
  MKUNDER LPAREN patternList RPAREN // TODO
  /* MKUNDER LPAREN pattern COMMA patternList RPAREN */
  ;

recordPattern :
  MKUNDERNAME LPAREN RPAREN // TODO
| MKUNDERNAME LPAREN patternList RPAREN // TODO
  ;


/* 7.2 Bindings */

/* FIXME where is patternBind used? */
/* patternBind : */
/*   pattern  */
/* | bind */
/*   ; */

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
| typeBind COMMA typeBindList
    {
      ATypeBind hd = (ATypeBind)$1;
      List<ATypeBind> tl = (List<ATypeBind>)$3;
      tl.add(hd);
      $$ = tl;
    }
  ;

/* Things not in the CML(-1) spec */

identifierList :
  IDENTIFIER
  {
      CmlLexeme cmlLex = (CmlLexeme) $1;
      LexLocation location = extractLexLocation(cmlLex);
      LexIdentifierToken lexIdToken = new LexIdentifierToken(cmlLex.getValue(),false,location);
      List<LexIdentifierToken> ids = new Vector<LexIdentifierToken>();
      ids.add(lexIdToken);
      $$ = ids;
  }

| IDENTIFIER COMMA identifierList
{

    List<LexIdentifierToken> ids = (List<LexIdentifierToken>)$3;

    if (ids == null)
	ids = new Vector<LexIdentifierToken>();

    CmlLexeme cmlLex = (CmlLexeme) $1;
    LexLocation location = extractLexLocation(cmlLex);
    LexIdentifierToken lexIdToken = 
	new LexIdentifierToken(cmlLex.getValue(),false,location);
    ids.add(lexIdToken);
    $$ = ids;
}
  ;

// **********************
// *** END OF GRAMMAR ***
// **********************
