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
    import org.overture.ast.definitions.*;
    import org.overture.ast.declarations.*;
    import org.overture.ast.expressions.*;
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

    public
}


%code{
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

    //    private LexToken makeLexToken()
    
    private LexNameToken extractLexNameToken(CmlLexeme lexeme)
    {
      return new LexNameToken("Default",lexeme.getValue(), extractLexLocation(lexeme),false, true);
    }

    private LexIdentifierToken extractLexIdentifierToken(CmlLexeme lexeme)
    {
      return new LexIdentifierToken(lexeme.getValue(), false, extractLexLocation(lexeme));
    }

    private APatternTypePair createPatternTypePair(CmlLexeme idLexeme, PType type)
    {
	LexNameToken name = extractLexNameToken(idLexeme);
	LexLocation location = extractLexLocation(idLexeme);
	AIdentifierPattern id = new AIdentifierPattern(location, null/*definitions_*/, 
						   false /*resolved_*/, name);
	return new APatternTypePair(false /* resolved_*/, id, type);
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

		System.out.println(dgv.getResultString());
	    
	      }
	      else
		System.out.println("Not parsed!");
		
	      //} while (!scanner.zzAtEOF);

	    }
	    catch (java.io.FileNotFoundException e) {
		System.out.println("File not found : \""+args[0]+"\"");
	    }
	    catch (java.io.IOException e) {
		System.out.println("IO error scanning file \""+args[0]+"\"");
		System.out.println(e);
	    }
	    catch (Exception e) {
		System.out.println("Unexpected exception:");
		e.printStackTrace();
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

%token CLASS END PROCESS INITIAL EQUALS AT BEGIN CSP_ACTIONS CSPSEQ CSPINTCH CSPEXTCH CSPLCHSYNC CSPRCHSYNC CSPINTERLEAVE CSPHIDE LPAREN RPAREN CSPRENAME LSQUARE RSQUARE CSPSKIP CSPSTOP CSPCHAOS CSPDIV CSPWAIT RARROW LCURLY RCURLY CSPAND BAR DBAR CHANNELS CHANSETS TYPES SEMI VDMRECORDDEF VDMCOMPOSE OF VDMTYPEUNION STAR TO VDMINMAPOF VDMMAPOF VDMSEQOF VDMSEQ1OF VDMSETOF VDMPFUNCARROW VDMTFUNCARROW VDMUNITTYPE VDMTYPE VDMTYPENCMP DEQUALS VDMINV VALUES FUNCTIONS PRE POST MEASURE VDM_SUBCLASSRESP VDM_NOTYETSPEC OPERATIONS VDM_EXT VDM_RD VDM_WR STATE LET IN IF THEN ELSEIF ELSE CASES OTHERS PLUS MINUS ABS FLOOR NOT CARD POWER DUNION DINTER HD TL LEN ELEMS INDS REVERSE DCONC DOM RNG MERGE INVERSE ELLIPSIS MAPLETARROW MKUNDER DOT DOTHASH NUMERAL LAMBDA NEW SELF ISUNDER PREUNDER ISOFCLASS BACKTICK TILDE DCL ASSIGN ATOMIC OPERATIONARROW RETURN VDMDONTCARE IDENTIFIER
%token DIVIDE DIV REM MOD LT LTE GT GTE NEQ OR AND IMPLY BIMPLY INSET NOTINSET SUBSET PROPER_SUBSET UNION SETDIFF INTER CONC OVERWRITE MAPMERGE DOMRES VDM_MAP_DOMAIN_RESTRICT_BY RNGRES RNGSUB COMP ITERATE FORALL EXISTS EXISTS1 

%token NUMERAL HEX_LITERAL

%token AMP THREEBAR CSPBARGT CSPLSQUAREBAR CSPLSQUAREGT DLSQUARE DRSQUARE CSPBARRSQUARE COMMA CSPSAMEAS CSPLSQUAREDBAR CSPDBARRSQUARE CSPDBAR COLON CHANSET_SETEXP_BEGIN CHANSET_SETEXP_END CSP_CHANNEL_READ CSP_CHANNEL_WRITE CSP_VARDECL CSP_OPS_COM CSP_CHANNEL_DOT
%token TBOOL TNAT TNAT1 TINT TRAT TREAL TCHAR TTOKEN PRIVATE PROTECTED PUBLIC LOGICAL

%token nameset namesetExpr typeVarIdentifier quoteLiteral functionType localDef  implicitOperationBody

/* CSP ops and more */
%right CSPSEQ CSPINTCH CSPEXTCH CSPLCHSYNC CSPRCHSYNC CSPINTERLEAVE CSPHIDE CSPAND AMP THREEBAR RARROW DLSQUARE CSPBARGT CSPLSQUAREBAR CSPLSQUAREGT CSPBARRSQUARE LSQUARE RSQUARE CSPRENAME VDMTYPEUNION STAR VDMSETOF VDMSEQOF VDMSEQ1OF VDMMAPOF VDMINMAPOF VDMPFUNCARROW VDMTFUNCARROW TO OF NEW ASSIGN
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
//| globalDecl                                     { $$ = $1; }
 ;

/* 2.1 Classes */
classDecl 
: CLASS IDENTIFIER EQUALS classBody        
{ 
    Position classStartPos =  ((CmlLexeme)$1).getStartPos();
    Position classEndPos = ((CmlLexeme)$4).getEndPos();
    LexLocation loc = new LexLocation(null, "Default", classStartPos.line,classStartPos.column,classEndPos.line,classEndPos.column,0,0);
    LexNameToken lexName = extractLexNameToken((CmlLexeme)$2); 
    // $$ = new AClassClassDefinition(loc, lexName , /*NameScope nameScope_*/ null, /*Boolean used_*/ null, 
    // 				   /*AAccessSpecifierAccessSpecifier*/ null,/* List<? extends LexNameToken> supernames_*/ new Vector<LexNameToken>(), 
    // 				   null /*hasContructors_*/, /*ClassDefinitionSettings settingHierarchy_*/null, 
    // 				   null/*Boolean gettingInheritable_*/, null/*Boolean gettingInvDefs_*/, 
    // 				   /*Boolean isAbstract_*/null, /*Boolean isUndefined_*/null); 
    AClassClassDefinition c = new AClassClassDefinition();
    c.setLocation(loc);
    c.setName(lexName);
    c.setIsAbstract(false);
    c.setDefinitions((List)$3);
    $$ = c;
}
;

/* 2.2 Processes */

processDecl :
  PROCESS IDENTIFIER EQUALS processDef
  {
      LexLocation processLoc = extractLexLocation((CmlLexeme)$1);
      AProcessDefinition processDef = (AProcessDefinition)$4;
      LexIdentifierToken id = (LexIdentifierToken)$2;
      LexLocation location = combineLexLocation(processLoc,processDef.getLocation());
      $$ = new AProcessDeclaration(location, NameScope.GLOBAL, id, processDef);
  }
  ;

processDef :
declaration AT process
{
    //$$ = new 
}
| process
{
    //$$ = 
}
;

process :
  BEGIN processParagraph AT action END
  {
      LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$5);
      List<PDeclaration> processDeclarations = (List<PDeclaration>)$2;
      PAction action = (PAction)$4;
      $$ = new AStateProcess(location,processDeclarations,action);
  }
| process CSPSEQ process
| process CSPINTCH process
| process CSPEXTCH process
| process CSPLCHSYNC chansetExpr CSPRCHSYNC process
| process CSPINTERLEAVE process
| LPAREN declaration AT processDef RPAREN LPAREN expression RPAREN
| IDENTIFIER LPAREN expression RPAREN
| IDENTIFIER
| LPAREN process RPAREN LSQUARE identifierList CSPRENAME identifierList RSQUARE
| CSPSEQ LCURLY declaration AT process RCURLY
| CSPINTCH LCURLY declaration AT process RCURLY
| CSPEXTCH LCURLY declaration AT process RCURLY
| LSQUARE LCURLY chansetExpr RSQUARE declaration AT process RCURLY
| CSPINTERLEAVE LCURLY declaration AT process RCURLY
      ;

processParagraph :
//  programParagraph
  classDefinitionBlock
| CSP_ACTIONS IDENTIFIER EQUALS paragraphAction
  {
      //   LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$5);
  }
| CSP_ACTIONS nameset IDENTIFIER EQUALS namesetExpr
| stateDefs  
  ;

paragraphAction :
  action
| declaration AT paragraphAction
  ;

action
: CSPSKIP { }
| CSPSTOP
| CSPCHAOS
| CSPDIV 
| CSPWAIT
| action LSQUARE identifierList CSPRENAME identifierList RSQUARE
| communication RARROW action
| action CSPAND action
| action CSPSEQ action

| action DLSQUARE renameList DRSQUARE
| action CSPHIDE action

| action CSPINTCH action
| action CSPEXTCH action
| action CSPLSQUAREGT action
| action CSPLSQUAREBAR IDENTIFIER CSPBARGT action
/*statements*/
| blockStatement
| controlStatements
  /*-----*/
| expression AMP action
| expression THREEBAR action
| action CSPLSQUAREBAR namesetExpr BAR chansetExpr BAR namesetExpr CSPBARRSQUARE action
| action CSPLSQUAREBAR namesetExpr BAR chansetExpr DBAR chansetExpr BAR namesetExpr CSPBARRSQUARE action
/* | action LSQUARE renameList RSQUARE action /\* FIXME shift/reduce because of rule 'action' case 4 *\/ */
| replicatedAction
  //| IDENTIFIER { /*new CMLIdentifier($1);*/ }
  ;

parallelAction:
;

parametrisedAction:
;

instantiatedAction:
;


replicatedAction :
 CSPSEQ LCURLY declaration AT action RCURLY
| CSPINTCH LCURLY declaration AT action RCURLY
| CSPEXTCH LCURLY declaration AT action RCURLY
| CSPLSQUAREDBAR nameset CSPDBARRSQUARE LPAREN declaration AT action RPAREN
| CSPLSQUAREBAR nameset BAR chansetExpr CSPBARRSQUARE LPAREN declaration AT action RPAREN
| CSPDBAR declaration AT LSQUARE nameset BAR chansetExpr RSQUARE action
| LSQUARE renameList RSQUARE LPAREN declaration AT action RPAREN
    ;

renameList :
  IDENTIFIER CSPSAMEAS IDENTIFIER
| IDENTIFIER CSPSAMEAS IDENTIFIER COMMA renameList
  ;

communication :
  IDENTIFIER
| IDENTIFIER communicationParameterUseList
  ;

communicationParameterUseList :
  communicationParameterUse
| communicationParameterUse communicationParameterUseList
  ;

communicationParameterUse :
  CSP_CHANNEL_WRITE communicationParameter
| CSP_CHANNEL_WRITE communicationParameter COLON expression 
| CSP_CHANNEL_READ expression
| CSP_CHANNEL_DOT expression  
  ;

communicationParameter :
 IDENTIFIER
| MKUNDER LPAREN communicationParameterList RPAREN
| MKUNDER name LPAREN communicationParameterList RPAREN
| MKUNDER LPAREN RPAREN
| MKUNDER name LPAREN RPAREN
  ;

communicationParameterList :
communicationParameter
| communicationParameter COMMA communicationParameterList
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
| CHANSET_SETEXP_BEGIN IDENTIFIER dotted_expression BAR bindList CHANSET_SETEXP_END
{
    LexLocation location = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$6);
    LexIdentifierToken identifier = extractLexIdentifierToken((CmlLexeme)$2);
    List<PExp> dotted_expression = (List<PExp>)$3;
    List<PMultipleBind> bindings = (List<PMultipleBind>)$5;
    $$ = new ACompChansetSetExp(location, identifier, dotted_expression, bindings,null);
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

dotted_expression:
/*empty*/
{
    $$ = new Vector<PExp>();
}
| dotted_expression DOT expression
{
    List<PExp> expTokens = (List<PExp>)$1;
    PExp exp = (PExp)$3;
    expTokens.add(exp);
    $$ = expTokens;
}
;

/* 2.5 Global Definitions */

globalDecl :
  globalDefinitionBlock
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

classBody 
: classDefinitionBlock                       
{ 
  $$ = (List)$1; 
}
|
{
  $$ = new Vector<PDefinition>();
}
;

classDefinitionBlock 
: classDefinitionBlockAlternative
{
    List<PDefinition> defBlockList = new Vector<PDefinition>();
    List<PDefinition> defBlock = (List<PDefinition>)$1;
    if (defBlockList != null) if (defBlock != null) defBlockList.addAll(defBlock);
    $$ = defBlockList;
}

| classDefinitionBlock classDefinitionBlockAlternative        
{ 
    List<PDefinition> defBlockList = (List<PDefinition>)$1;
    List<PDefinition> defBlock = (List<PDefinition>)$2;
    if (defBlockList != null) if (defBlock != null) defBlockList.addAll(defBlock);
    $$ = defBlockList;
}
;

classDefinitionBlockAlternative
: typeDefs             
{
  
}
| valueDefs
{
  
}
| functionDefs
{
  
}
| operationDefs
{
  
}
/*| stateDefs
{
  
}
| initialDef
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
    LexLocation location = null;
    if (access.getLocation() != null)
	location = combineLexLocation(access.getLocation(),((PTypeBase)$4).getLocation());
    else
    {
	location = combineLexLocation(name.getLocation(),((PTypeBase)$4).getLocation());
    }
    
    AInvariantInvariant inv = (AInvariantInvariant)$5;

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
	location = combineLexLocation(access.getLocation(),((PTypeBase)$4).getLocation());
    else
    {
	location = combineLexLocation(name.getLocation(),((PTypeBase)$4).getLocation());
    }
        
    $$ = new ATypeDefinition(location,null /*NameScope nameScope_*/, false, 
			     null/*SClassDefinition classDefinition_*/,access, 
			     (PType)$4, null, null, null, 
			     null, true, name); 
}
| qualifier IDENTIFIER VDMRECORDDEF fieldList invariant
  ;

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
| quoteLiteral /* replace me! */
| VDMCOMPOSE IDENTIFIER OF fieldList END
| type VDMTYPEUNION type
| type STAR type
| LSQUARE type RSQUARE
| VDMSETOF type
| VDMSEQOF type
| VDMSEQ1OF type
| VDMMAPOF type TO type
| VDMINMAPOF type TO type
| type VDMPFUNCARROW type
| VDMUNITTYPE VDMPFUNCARROW type
| type VDMTFUNCARROW type
| VDMUNITTYPE VDMTFUNCARROW type
| name
{
  LexNameToken lnt = (LexNameToken)$1; 
  $$ = new AUnresolvedType(lnt.location,false /*resolved*/, null/*defs*/,lnt);
}
| typeVarIdentifier
  ;

fieldList : 
  field
| field fieldList
  ;

field :
  type
| IDENTIFIER VDMTYPE type
| IDENTIFIER VDMTYPENCMP type
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
  ;

valueDefList :
  pattern EQUALS expression SEMI valueDefList
| pattern VDMTYPE type EQUALS expression SEMI valueDefList
| /* empty */
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
| functionDefList SEMI functionDef
{
    List<SFunctionDefinition> functionList = (List<SFunctionDefinition>)$1;
    functionList.add((SFunctionDefinition)$3);
    $$ = functionList;
}
;

functionDef:
 implicitFunctionDef
| explicitFunctionDef
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

explicitFunctionDef:
qualifier IDENTIFIER VDMTYPE functionType IDENTIFIER parameterList DEQUALS functionBody preExpr_opt postExpr_opt measureExpr
  {
    $$ = new AExplicitFunctionFunctionDefinition();
  }
;


/* really? this is what a VDM function definition list looks like? */
parameterList :
  LPAREN patternList RPAREN
| LPAREN patternList RPAREN parameterList
  ;

functionBody :
  expression
| VDM_SUBCLASSRESP
| VDM_NOTYETSPEC
  ;

parameterTypes : 
LPAREN RPAREN
{
    $$ = new Vector<APatternListTypePair>();
}
|LPAREN patternList VDMTYPE type RPAREN
{
    List<PPattern> patternList = (List<PPattern>)$2;
    List<APatternListTypePair> pltpl = new Vector<APatternListTypePair>();
    pltpl.add(new APatternListTypePair(false /*resolved*/, patternList, (PType)$4));
    $$ = pltpl;
}
| LPAREN patternList VDMTYPE type COMMA parameterTypes RPAREN
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
IDENTIFIER VDMTYPE type
{
    APatternTypePair typePair = createPatternTypePair((CmlLexeme)$1,(PType)$3);
    List<APatternTypePair> typePairs = new Vector<APatternTypePair>();
    typePairs.add(typePair);
    $$ = typePairs;
}
| IDENTIFIER VDMTYPE type COMMA identifierTypePairList
{
    APatternTypePair typePair = createPatternTypePair((CmlLexeme)$1,(PType)$3);
    List<APatternTypePair> typePairs = (List<APatternTypePair>)$5;
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
  ;
  
operationDefList :
  operationDef SEMI operationDefList
| /* empty */
  ;

/* FIXME the optional trailing semicolon in the operations definitions is presently not optional */

operationDef 
: implicitOperationDef
| explicitOperationDef
;

 explicitOperationDef
: qualifier IDENTIFIER VDMTYPE operationType IDENTIFIER parameterList DEQUALS operationBody externals preExpr_opt postExpr_opt
;

implicitOperationDef
: qualifier IDENTIFIER parameterTypes identifierTypePairList_opt externals_opt preExpr_opt postExpr
;

operationType :
  type OPERATIONARROW type
| VDMUNITTYPE OPERATIONARROW type
| type OPERATIONARROW VDMUNITTYPE
| VDMUNITTYPE OPERATIONARROW VDMUNITTYPE
  ;

operationBody :
  action
| VDM_SUBCLASSRESP
| VDM_NOTYETSPEC
  ;

externals_opt:
externals
| /* empty */
;

externals :
  VDM_EXT varInformationList
  ;

/* FIXME this needs to be non-empty */
varInformationList :
  mode nameList varInformationList
| mode nameList VDMTYPE type varInformationList
| /* empty */
  ;

mode :
  VDM_RD
| VDM_WR
  ;

initialDef : 
INITIAL operationDef

/* 3.5 Instance Variable Definitions */

stateDefs :
  STATE stateDefList
  ;

/* FIXME this needs to be non-empty */
stateDefList :
  assignmentDef stateDefList
| invariantDef stateDefList
| /* empty */
  ;

invariantDef :
 VDMINV expression
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
  LPAREN expression RPAREN
  {
      LexLocation loc = extractLexLocation((CmlLexeme)$1,(CmlLexeme)$3);
      $$ = new ABracketedExp(loc,(PExp)$2);
  }
| LET localDefList IN expression
| ifExpr
| casesExpr
| unaryExpr
| binaryExpr
{
    $$ = $1;
}
| quantifiedExpr
| setEnumeration
| setComprehension
| setRangeExpr
| sequenceEnumeration
| sequenceComprehension
| subsequence
| mapEnumeration
| mapComprehension
| tupleConstructor
| recordConstructor
| apply
| fieldSelect
| tupleSelect
| lambdaExpr
| newExpr
| SELF
| generalIsExpr
| preconditionExpr
| ISOFCLASS LPAREN name COMMA expression RPAREN
| name
{
    LexNameToken lnt = (LexNameToken)$1;
    $$ = new ANameExp(lnt.location,lnt);
}
| oldName
| symbolicLiteral
  ;

symbolicLiteral:
numericLiteral
{
    LexIntegerToken lit = (LexIntegerToken)$1;
    $$ = new AIntLiteralSymbolicLiteralExp(lit.location,lit);
}
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
| localDef COMMA localDefList
  ;

/* 4.3 Conditional Expressions */

ifExpr :
  IF expression THEN expression elseExprs
  ;

elseExprs :
  ELSE expression
| ELSEIF expression THEN expression elseExprs
  ;

casesExpr :
  CASES expression COLON casesExprAltList END
  ;

casesExprAltList :
  casesExprAlt
| casesExprAlt OTHERS RARROW expression
| casesExprAlt casesExprAltList
  ;

casesExprAlt :
  patternList RARROW expression
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
| expression MINUS expression
| expression DIVIDE expression
| expression DIV expression
| expression REM expression
| expression MOD expression
| expression LT expression
{
  //CmlLexeme ltLexeme = 
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
| expression DOMRES expression
| expression VDM_MAP_DOMAIN_RESTRICT_BY expression
| expression RNGRES expression
| expression RNGSUB expression
| expression COMP expression
| expression ITERATE expression
  ;

/* 4.6 Quantified Expressions */

quantifiedExpr :
  FORALL bindList AMP expression
| EXISTS bindList AMP expression
| EXISTS1 bind AMP expression
  ;

/* 4.7 Set Expressions */

setEnumeration :
LCURLY RCURLY
{
    $$ = new ASetEnumSetExp();   
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
| LCURLY expression BAR bindList AMP expression RCURLY
  ;

setRangeExpr : 
  LCURLY expression COMMA ELLIPSIS COMMA expression RCURLY
  ;

/* 4.8 Sequence Expression */

sequenceEnumeration :
  LSQUARE RSQUARE
| LSQUARE expressionList RSQUARE
  ;

sequenceComprehension :
  LSQUARE expression BAR setBind RSQUARE
| LSQUARE expression BAR setBind AMP expression RSQUARE
  ;

subsequence :
  expression LPAREN expression COMMA ELLIPSIS COMMA expression RPAREN
  ;

mapEnumeration :
  LCURLY MAPLETARROW RCURLY
| LCURLY mapletList RCURLY
  ;

mapletList :
  maplet
| maplet COMMA mapletList
  ;

maplet :
  expression MAPLETARROW expression
  ;

mapComprehension :
  LCURLY maplet BAR bindList RCURLY
| LCURLY maplet BAR bindList AMP expression RCURLY
  ;

/* 4.10 Tuple Constructor Expression */

tupleConstructor :
  MKUNDER LPAREN expression COMMA expression RPAREN
  ;

/* 4.11 Record Expressions */

recordConstructor :
  MKUNDER name LPAREN expressionList RPAREN
  ;

/* 4.12 Apply Expressions */

apply :
  expression LPAREN expressionList RPAREN
  ;

fieldSelect :
  expression DOT IDENTIFIER
  ;

tupleSelect :
  expression DOTHASH NUMERAL
  ;

/* 4.13 The Lambda Expression */

lambdaExpr :
  LAMBDA typeBindList AMP expression
  ;

/* 4.14 The New Expression */

/* FIXME there is a reduce/reduce with <apply> if you take out the PARENs */

newExpr :
  NEW LPAREN expression LPAREN expressionList RPAREN RPAREN
  ;

/* 4.16 The Is Expression */

generalIsExpr :
  ISUNDER name LPAREN expression RPAREN
| ISUNDER basicType LPAREN expression RPAREN
| ISUNDER LPAREN expression COMMA type RPAREN
  ;

basicType :
  TBOOL
| TNAT
| TNAT1
| TINT
| TRAT
| TREAL
| TCHAR
| TTOKEN
  ;

/* 4.17 The Precondition Expression */

preconditionExpr :
  PREUNDER LPAREN expressionList RPAREN
  ;

/* 4.19 Names */

name :
  IDENTIFIER
  {
      LexNameToken name = extractLexNameToken((CmlLexeme)$1);
      $$ = name;
  }
| IDENTIFIER BACKTICK IDENTIFIER
  ;

nameList :
  name
| name COMMA nameList
  ;

oldName :
  IDENTIFIER TILDE
  ;


/* 5 State Designators */
stateDesignator :
  name
| stateDesignator DOT IDENTIFIER
| stateDesignator LPAREN expression RPAREN
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
| LPAREN dclStatement action RPAREN
  ;

dclStatement :
  DCL assignmentDefList AT
  ;

assignmentDefList :
  assignmentDef
| assignmentDef COMMA assignmentDefList
  ;

assignmentDef :
  IDENTIFIER VDMTYPE type
| IDENTIFIER VDMTYPE type ASSIGN expression
| IDENTIFIER VDMTYPE type IN  expression
  ;

generalAssignStatement :
  assignStatement
| multiAssignStatement
  ;

assignStatement :
  stateDesignator ASSIGN expression
  /*| stateDesignator ASSIGN callStatement*/
  ;

assignStatementList :
  assignStatement
| assignStatement SEMI assignStatementList
  ;

multiAssignStatement :
  ATOMIC LPAREN assignStatement SEMI assignStatementList RPAREN
  ;

/* 6.3 Conditional Statements */
ifStatement :
  IF expression THEN action elseStatements
  ;

elseStatements :
  ELSE action
| ELSEIF expression THEN action elseStatements
  ;

casesStatement :
  CASES expression COLON casesStatementAltList END
  ;

casesStatementAltList :
  casesStatementAlt
| casesStatementAlt OTHERS RARROW action
| casesStatementAlt casesExprAltList
  ;

casesStatementAlt :
  patternList RARROW action
  ;

/* 6.4 Call and Return Statements */

/* FIXME the CURLYs are there there to avoid several whatever/reduce conflicts with the assignment statement */

callStatement :
name LPAREN RPAREN
//| name LPAREN expressionList RPAREN
;
/*
objectDesignator :
  SELF
| name
| objectFieldReference
| callStatement
  ;

objectFieldReference :
objectDesignator DOT IDENTIFIER
    ;
*/
returnStatement :
/* RETURN*/
 RETURN expression
     ;
/* return inline above */


/* 6.5 The Specification Statement */

specificationStatement :
  LSQUARE implicitOperationBody RSQUARE
  ;


/* 7 Patterns and Bindings */

/* 7.1  */

pattern :
  patternIdentifier
| matchValue
| tuplePattern
| recordPattern
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
| VDMDONTCARE
  ;

/* FIXME shift/reduce conflict from a bracketed expression */
matchValue :
  symbolicLiteral
/* | LPAREN expression RPAREN */
  ;

/* FIXME not sure if if this is a minimum of one pattern or two; if the latter */
tuplePattern :
  MKUNDER LPAREN patternList RPAREN
  /* MKUNDER LPAREN pattern COMMA patternList RPAREN */
  ;

recordPattern :
  MKUNDER name LPAREN RPAREN
| MKUNDER name LPAREN patternList RPAREN
  ;


/* 7.2 Bindings */

/* FIXME where is patternBind used? */
/* patternBind : */
/*   pattern  */
/* | bind */
/*   ; */

bind :
  setBind
| typeBind
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
pattern VDMTYPE type
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
patternList VDMTYPE type 
{
    List<PPattern> patterns = (List<PPattern>)$1;
    PType type = (PType)$3;
    LexLocation location = extractLexLocation((CmlLexeme)$2);;
    $$ = new ATypeMultipleBind(location, patterns, type);
}
; 

typeBindList :
  typeBind
| typeBind COMMA typeBindList
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