%require "2.5"
%language "java"
%locations
%define parser_class_name "CmlParser"
%define package "eu.compassresearch.core.parser"

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
| globalDecl programParagraphList
| globalDecl
;

programParagraphList :
  programParagraph
| programParagraphList programParagraph
;

programParagraph :
  classDecl
| processDecl
| channelDecl
| chansetDecl
;

classDecl :
  CLASS IDENTIFIER EQUALS classBody
;

processDecl :
  PROCESS IDENTIFIER EQUALS processDef
;

processDef :
  declaration AT process
| process
;

process :
  BEGIN AT action END
| BEGIN processParagraphList AT action END
| process SEMI process
| process LRSQUARE process
| process BARTILDEBAR process
| process LSQUAREBAR chansetExpr BARRSQUARE process
| process LSQUARE chansetExpr DBAR chansetExpr RSQUARE process
| process DBAR process
| process TBAR process
| process SLASHBACKSLASH process
/* DEVIATION
 * grammar:
 *   process '/' expression '\' process
 * this conflicts all over, so
 *   process '/:' expression ':\' process
 */
| process SLASHCOLON expression COLONBACKSLASH process
| process LSQUAREGT process
| process LSQUARE expression BARGT process
/* DEVIATION
 * grammar:
 *   process '\' chansetExpr
 * here:
 *   process ':\' chansetExpr
 */
| process COLONBACKSLASH chansetExpr
| process STARTBY expression
| process ENDSBY expression
| LPAREN declaration AT processDef RPAREN LPAREN expression RPAREN
| path
| process renameExpression
/* DEVIATION (x4)
 * all of the replicated processes
 * grammar:
 *   <replOp> replicationDeclaration '@' process
 * here:
 *   <replOp> '{' replicationDeclaration '}' '@' process
 */
| SEMI LCURLY replicationDeclaration AT process RCURLY
| BARTILDEBAR LCURLY replicationDeclaration AT process RCURLY
| LRSQUARE LCURLY replicationDeclaration AT process RCURLY
| TBAR LCURLY replicationDeclaration AT process RCURLY
| LSQUARE chansetExpr RSQUARE LCURLY replicationDeclaration AT process RCURLY
;

replicationDeclaration :
  replicationDeclarationAlt
| replicationDeclaration SEMI replicationDeclarationAlt
;

replicationDeclarationAlt :
  singleTypeDecl
// FIXME --- this causes a s/r conflict due to the name production
/* | singleExpressionDeclaration */
;

/* singleExpressionDeclaration : */
/* IDENTIFIER COLON expression */
/* | singleExpressionDeclaration COMMA IDENTIFIER */
/* ; */

processParagraphList :
  processParagraph
| processParagraphList processParagraph
;

processParagraph :
 classDefinitionBlockAlternative
| actionParagraph
;

actionParagraph :
  ACTIONS actionDefinitionList
| ACTIONS nameset IDENTIFIER EQUALS namesetExpr
;

actionDefinitionList :
  actionDefinition
| actionDefinitionList actionDefinition
;

actionDefinition :
  IDENTIFIER EQUALS paragraphAction
;

paragraphAction :
  action
| declaration AT action
;

action :
  CSPSKIP
| CSPSTOP
| CSPCHAOS
| CSPDIV
| CSPWAIT LPAREN expression RPAREN
/* Communication rule start*/
| IDENTIFIER RARROW action
| IDENTIFIER communicationParameterList RARROW action
/* Communication rule end*/
/* DEVIATON
 * grammar:
 *   expression '&' action
 * here:
 *   ':' expression '&' action
 */
| COLON expression AMP action
| action SEMI action
| action LRSQUARE action
| action BARTILDEBAR action
| action SLASHBACKSLASH action
/* DEVIATION
 * grammar:
 *   process '/' expression '\' process
 * this conflicts all over, so
 *   process '/:' expression ':\' process
 */
| action SLASHCOLON expression COLONBACKSLASH action
| action LSQUAREGT action
/* DEVIATION
 * grammar:
 *   action '[' expression '>' action
 * here:
 *   action '[' expression '|>' action
 */
| action LSQUARE expression BARGT action
/* DEVIATION
 * grammar:
 *   process '\' chansetExpr
 * here:
 *   process ':\' chansetExpr
 */
| action COLONBACKSLASH chansetExpr
| action STARTBY expression
| action ENDSBY expression
| action renameExpression
/* DEVIATION
 * grammar:
 *  'mu' <identifierList> '@' action
 * here:
 *  'mu' <pathList> '@' '(' action ')'
 *
 * Also, this is apparently not yet in our AST
 */
| MU pathList AT LPAREN action RPAREN
| parallelAction
| LPAREN parametrisationList AT action RPAREN
| instantiatedAction
| replicatedAction
| letStatement
| blockStatement
| controlStatement
// FIXME this is the missing IDENTIFIER in action; should probably be a name anyway
/* DEVIATION
 * CML_0:
 *   name
 * here:
 *   path
 * TODO: convert to a name
 */
| path
;

communicationParameterList :
  communicationParameter
| communicationParameterList communicationParameter
;

communicationParameter :
  QUESTION parameter
| QUESTION parameter COLON expression
| BANG expression
/* DEVIATION
 * CML_0:
 *   '.' expression
 * here:
 *   '.:' expression
 *
 * This runs into trouble with the DOT in paths that are used in
 * expressions.  This could be difficult to resolve.
 */
| DOTCOLON expression
;

parameter :
  IDENTIFIER
| MKUNDER LPAREN paramList RPAREN
| MKUNDER LRPAREN
| MKUNDERNAME LPAREN paramList RPAREN
| MKUNDERNAME LRPAREN
;

paramList :
parameter
| paramList COMMA parameter
;

parallelAction :
  action LSQUAREDBAR namesetExpr BAR namesetExpr DBARRSQUARE action
| action TBAR action
| action LSQUAREBAR namesetExpr BAR namesetExpr BARRSQUARE action
| action DBAR action
| action LSQUARE namesetExpr BAR chansetExpr DBAR chansetExpr BAR namesetExpr RSQUARE action
| action LSQUARE chansetExpr DBAR chansetExpr RSQUARE action
| action LSQUAREBAR namesetExpr BAR chansetExpr BAR namesetExpr BARRSQUARE action
| action LSQUAREBAR chansetExpr BARRSQUARE action
;

parametrisationList :
  parametrisation
| parametrisationList SEMI parametrisation
;

parametrisation :
  VAL singleTypeDecl
| RES singleTypeDecl
| VRES singleTypeDecl
;

instantiatedAction :
  LPAREN declaration AT action RPAREN LPAREN expressionList RPAREN
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
  SEMI LCURLY replicationDeclaration AT action RCURLY
| LRSQUARE LCURLY replicationDeclaration AT action RCURLY
| BARTILDEBAR LCURLY replicationDeclaration AT action RCURLY
| LSQUAREDBAR nameset DBARRSQUARE LPAREN replicationDeclaration AT action RPAREN
| TBAR replicationDeclaration AT LSQUARE namesetExpr RSQUARE action
| LSQUAREBAR chansetExpr BARRSQUARE replicationDeclaration AT LSQUARE namesetExpr RSQUARE action
| DBAR replicationDeclaration AT LSQUARE namesetExpr BAR chansetExpr RSQUARE action
| DBAR replicationDeclaration AT LSQUARE namesetExpr RSQUARE action
;

renameExpression :
/* rename enumeration */
  DLSQUARE renameList DRSQUARE
/* rename comprehensions */
| DLSQUARE renameList BAR bindList DRSQUARE
| DLSQUARE renameList BAR bindList AT expression DRSQUARE
;

renameList :
  channelEvent LARROW channelEvent
| renameList COMMA channelEvent LARROW channelEvent
;

channelEvent :
  IDENTIFIER
// FIXME this hits the DOT in path
/* | IDENTIFIER DOT dotted_expression */
;

// FIXME
/* dotted_expression : */
/*   expression */
/* | dotted_expression DOT expression */
/* ; */

channelDecl :
  CHANNELS channelDef
;

channelDef :
  channelNameDecl
| channelDef SEMI channelNameDecl
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
| singleTypeDecl
;

declaration :
  singleTypeDecl
| declaration SEMI singleTypeDecl
;

singleTypeDecl :
/* DEVIATION
 * grammar:
 *   identifierList
 * here:
 *   pathList
 */
  pathList COLON type
;

chansetDecl :
  CHANSETS
| CHANSETS chansetDefinitionList
;

chansetDefinitionList :
  chansetDefinition
| chansetDefinitionList chansetDefinition
;

chansetDefinition :
  IDENTIFIER EQUALS chansetExpr
;

chansetExpr :
  IDENTIFIER
/* DEVIATION
 * grammar:
 *   '{' identifierList '}'
 * here:
 *   '{:' pathList ':}'
 */
| LCURLYCOLON pathList COLONRCURLY
| LCURLYBAR pathList BARRCURLY
/* DEVIATION
 * grammar:
 *   chansetExpr 'union' chansetExpr
 * here:
 *   chansetExpr ':union' chansetExpr
 */
| chansetExpr COLONUNION chansetExpr
/* DEVIATION
 * grammar:
 *   chansetExpr 'inter' chansetExpr
 * here:
 *   chansetExpr ':inter' chansetExpr
 */
| chansetExpr COLONINTER chansetExpr
/* DEVIATION
 * grammar:
 *   chansetExpr '\' chansetExpr
 * here:
 *   chansetExpr ':\' chansetExpr
 */
| chansetExpr COLONBACKSLASH chansetExpr
/* these hit the DOT in paths */
/* | LCURLYBAR IDENTIFIER BAR bindList BARRCURLY */
/* | LCURLYBAR IDENTIFIER dotted_expression BAR bindList BARRCURLY */
/* | LCURLYBAR IDENTIFIER BAR bindList AT expression BARRCURLY */
/* | LCURLYBAR IDENTIFIER dotted_expression BAR bindList AT expression BARRCURLY */
;

globalDecl :
  globalDefinitionBlock
;

globalDefinitionBlock :
  globalDefinitionBlockAlternative
| globalDefinitionBlock globalDefinitionBlockAlternative
;

globalDefinitionBlockAlternative :
  typeDefs
| valueDefs
| functionDefs
;

classBody :
  BEGIN classDefinitionBlock END
;

classDefinitionBlock :
  classDefinitionBlockAlternative
| classDefinitionBlockAlternative classDefinitionBlock
;

classDefinitionBlockAlternative :
  typeDefs
| valueDefs
| functionDefs
| operationDefs
| stateDefs
/* DEVIATION
 * This is not yet in the (CML0) grammar, but the need for it has been recognised
 */
| INITIAL operationDef
;

typeDefs :
  TYPES
| TYPES typeDefList SEMI
| TYPES typeDefList
;

typeDefList :
  typeDef
| typeDefList SEMI typeDef
;

typeDef :
  qualifier IDENTIFIER EQUALS type invariant
| qualifier IDENTIFIER EQUALS type
| qualifier IDENTIFIER DCOLON fieldList
| qualifier IDENTIFIER DCOLON fieldList invariant
;

qualifier :
  PRIVATE
| PROTECTED
| PUBLIC
| LOGICAL
| /* empty */
;

type :
  LPAREN type RPAREN // bracketedType
| basicType
| quoteLiteral // quoteType
| COMPOSE IDENTIFIER OF fieldList END
| type BAR type // unionType
| type STAR type //productType
| LSQUARE type RSQUARE // optionalType
| SETOF type
| SEQOF type
| SEQ1OF type
| MAPOF type TO type
| INMAPOF type TO type
| functionType
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
| TNAT
| TNAT1
| TINT
| TRAT
| TREAL
| TCHAR
| TTOKEN
;

functionType :
  partialFunctionType
| totalFunctionType
;

partialFunctionType :
  type PLUSGT type
| LRPAREN PLUSGT type // discretionary type
;

totalFunctionType :
  type RARROW type
| LRPAREN RARROW type // discretionary type
;

quoteLiteral :
  QUOTE_LITERAL
;

fieldList :
  field
| fieldList field
;

field :
  type
| IDENTIFIER COLON type
| IDENTIFIER COLONDASH type
;

invariant :
  INV pattern DEQUALS expression
;

valueDefs :
  VALUES valueDefList
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
| qualifiedValueDef SEMI
| qualifiedValueDef SEMI valueDefList
;

qualifiedValueDef :
  qualifier valueDef
;

valueDef :
  IDENTIFIER COLON type EQUALS expression
| patternLessID COLON type EQUALS expression
| IDENTIFIER EQUALS expression
| patternLessID EQUALS expression
;

functionDefs :
  FUNCTIONS
| FUNCTIONS functionDefList
;

functionDefList :
  functionDef
| functionDef SEMI
| functionDef SEMI functionDefList
;

functionDef :
  implicitFunctionDef
| qualifiedExplicitFunctionDef
;

implicitFunctionDef :
  qualifier IDENTIFIER parameterTypes identifierTypePairList preExpr_opt postExpr
;

qualifiedExplicitFunctionDef :
  qualifier explicitFunctionDef
;

explicitFunctionDef :
  IDENTIFIER COLON functionType IDENTIFIER parameterList DEQUALS functionBody preExpr_opt postExpr_opt measureExpr

;

/* really? this is what a VDM function definition list looks like? */
parameterList :
  LRPAREN
| LPAREN patternList RPAREN
| parameterList LPAREN patternList RPAREN
;

functionBody :
  expression
| SUBCLASSRESP
| NOTYETSPEC
;

parameterTypes :
  LRPAREN
| LPAREN patternListTypeList RPAREN
;

patternListTypeList :
  patternList COLON type
| patternListTypeList COMMA patternList COLON type
;

identifierTypePairList_opt :
  /* empty */
| identifierTypePairList
;

identifierTypePairList :
  IDENTIFIER COLON type
| identifierTypePairList COMMA IDENTIFIER COLON type
;

preExpr_opt :
  preExpr
| /* empty */
;

preExpr :
  PRE expression
;

postExpr_opt :
  postExpr
| /* empty */
;

postExpr :
  POST expression
;

measureExpr :
/* DEVIATION --- PATH
 * CML_0:
 *   MEASURE name
 * TODO: convert to a name
 */
  MEASURE path
| /* empty */
;

operationDefs :
  OPERATIONS operationDefList
| OPERATIONS
;

operationDefList :
  operationDef
// FIXME --- making the SEMI optional causes s/r
/* | operationDef SEMI */
| operationDefList SEMI operationDef
;

/* FIXME the optional trailing semicolon in the operations definitions is presently not optional */

operationDef :
  implicitOperationDef
| explicitOperationDef
;

explicitOperationDef :
  qualifier IDENTIFIER COLON operationType IDENTIFIER parameterList DEQUALS operationBody preExpr_opt postExpr_opt
;

implicitOperationDef :
  qualifier IDENTIFIER parameterTypes identifierTypePairList_opt externals_opt preExpr_opt postExpr
;

operationType :
  type DEQRARROW type
| LRPAREN DEQRARROW type
| type DEQRARROW LRPAREN
| LRPAREN DEQRARROW LRPAREN
;

operationBody :
  letStatement
| blockStatement
| controlStatement
| SUBCLASSRESP
| NOTYETSPEC
;

externals_opt :
  externals
| /* empty */
;

externals :
  FRAME varInformationList
;

varInformationList :
  varInformation
| varInformationList varInformation
;

varInformation :
  mode pathList
| mode pathList COLON type
;

mode :
  RD
| WR
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
| STATE
;

stateDefList :
  stateDef
| stateDef SEMI
| stateDef SEMI stateDefList
;

stateDef :
  qualifier assignmentDef
| INV expression
;

expressionList :
  expression
| expressionList COMMA expression
;

expression :
  STRING
| LPAREN expression RPAREN
| LET localDefList IN expression
| ifExpr
| casesExpr
| unaryExpr
| binaryExpr
/* quantified expressions */
| FORALL bindList AMP expression
| EXISTS bindList AMP expression
| EXISTS1 bind AMP expression
/* set enumeration */
| LCURLY RCURLY
| LCURLY expressionList RCURLY
/* set comprehensions */
| LCURLY expression BAR bindList RCURLY
| LCURLY expression BAR bindList AMP expression RCURLY
/* set range expression */
| LCURLY expression ELLIPSIS expression RCURLY
/* sequence enumerations */
| LSQUARE RSQUARE
| LSQUARE expressionList RSQUARE
/* sequence comprehensions */
| LSQUARE expression BAR setBind RSQUARE
| LSQUARE expression BAR setBind AMP expression RSQUARE
/* map enumerations */
| LCURLY BARRARROW RCURLY
| LCURLY mapletList RCURLY
/* map comprehensions */
| LCURLY maplet BAR bindList RCURLY
| LCURLY maplet BAR bindList AMP expression RCURLY
| tupleConstructor
| recordConstructor
| lambdaExpr
| generalIsExpr
| preconditionExpr
/* DEVIATION --- PATH
 * CML_0:
 *   ISOFCLASS LPAREN name COMMA expression RPAREN
 * TODO: convert to a name
 */
| ISOFCLASS LPAREN path COMMA expression RPAREN
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
| path
| symbolicLiteral
;

/* TODO --- verify
 *
 * We need to check that these are, indeed, the right possible literals.
 */
symbolicLiteral :
  numericLiteral
| booleanLiteral
| nilLiteral
| characterLiteral
| textLiteral
| quoteLiteral
;

numericLiteral :
  NUMERAL
| HEX_LITERAL
;

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
| CASES expression COLON casesExprAltList OTHERS RARROW expression END
;

casesExprAltList :
  casesExprAlt
| casesExprAltList casesExprAlt
;

casesExprAlt :
  patternList RARROW expression SEMI
;

unaryExpr :
  PLUS expression %prec UPLUS
| MINUS expression %prec UMINUS
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
| expression DIVIDE expression
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
| expression PROPER_SUBSET expression
| expression UNION expression
| expression BACKSLASH expression
| expression INTER expression
| expression CARET expression
| expression DPLUS expression
| expression MAPMERGE expression
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

tupleConstructor :
  MKUNDER LPAREN expressionList RPAREN
;

recordConstructor :
  MKUNDERNAME LPAREN expressionList RPAREN
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
| ISUNDER basicType LPAREN expression RPAREN
| ISUNDER LPAREN expression COMMA type RPAREN
;

preconditionExpr :
  PREUNDER LPAREN expressionList RPAREN
;

controlStatement :
  nonDeterministicIfStatement
| ifStatement
| casesStatement
// FIXME --- is/was this a rule?
/*| generalCasesIfStatement*/
/* DEVIATION --- PATH
 * CML_0:
 *  callStatement
 * TODO: this gets merged with generalAssignStatement
 */
/* | callStatement */
// FIXME --- causes r/r conflict with objectDesignator(call)
| generalAssignStatement
| specificationStatement
| returnStatement
/* DEVIATION --- PATH
 * CML_0:
 *   stateDesignator COLONEQUALS NEW name LRPAREN
 *   stateDesignator COLONEQUALS NEW name LPAREN expressionList RPAREN
 *
 * TODO: need the convert the paths to stateDesignator and name, resp.
 */
/*   path COLONEQUALS NEW path LRPAREN */
/* | path COLONEQUALS NEW path LPAREN expressionList RPAREN */
/*| non-deterministicDoStatement */
/*| SequenceForLoop */
/*| setForLoop */
/*| indexForLoop*/
/*| whileLoop */
;

nonDeterministicIfStatement :
  IF expression RARROW action END
| IF expression RARROW action nonDeterministicIfAltList END
;

nonDeterministicIfAlt :
  BAR expression RARROW action
;

nonDeterministicIfAltList :
  nonDeterministicIfAlt
| nonDeterministicIfAltList nonDeterministicIfAlt
;

/* let statements */

letStatement :
  LET localDefList IN action
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
| assignmentDefList COMMA assignmentDef
;

assignmentDef :
  IDENTIFIER COLON type
| IDENTIFIER COLON type COLONEQUALS expression
| IDENTIFIER COLON type IN expression
;

generalAssignStatement :
  assignStatement
| multiAssignStatement
;

assignStatementList :
  assignStatement
| assignStatementList SEMI assignStatement
;

multiAssignStatement :
  ATOMIC LPAREN assignStatementList RPAREN
;

assignStatement :
/* DEVIATION --- PATH
 * CML_0:
 *   stateDesignator COLONEQUALS expression // was stateDesignator
 * TODO: convert to a stateDesignator
 */
  path COLONEQUALS expression
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
| CASES expression COLON casesStatementAltList OTHERS RARROW action END
/*   // FROM | casesStatementAlt COMMA OTHERS RARROW action */
;

casesStatementAltList :
  casesStatementAlt
| casesStatementAlt COMMA casesStatementAltList
;

casesStatementAlt :
  patternList RARROW action
;

returnStatement :
  RETURN SEMI 
| RETURN expression
;

specificationStatement :
  LSQUARE implicitOperationBody RSQUARE
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
| tuplePattern
| recordPattern
;

patternList :
  pattern
| patternList COMMA pattern
  ;

patternIdentifier :
  IDENTIFIER
// FIXME -- shouldn't this be in patternLessID?
| MINUS // TODO: Implement "don't care" pattern
;

matchValue :
  symbolicLiteral
| LPAREN expression RPAREN
;

/* FIXME not sure if if this is a minimum of one pattern or two; if the latter */
tuplePattern :
//  MKUNDER LPAREN patternList RPAREN
  MKUNDER LPAREN patternList COMMA pattern RPAREN
;

recordPattern :
  MKUNDERNAME LRPAREN
| MKUNDERNAME LPAREN patternList RPAREN
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
| pathList COMMA path
;

// **********************
// *** END OF GRAMMAR ***
// **********************

