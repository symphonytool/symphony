%require "2.5"
%language "java"
%locations
%define parser_class_name "CmlParser"
%define package "eu.compassresearch.core.parser"

%token CLASS END PROCESS INITIAL EQUALS AT BEGIN ACTIONS BARTILDEBAR LRSQUARE TBAR LPAREN RPAREN LRPAREN LSQUARE RSQUARE CSPSKIP CSPSTOP CSPCHAOS CSPDIV CSPWAIT RARROW LARROW LCURLY RCURLY BAR DBAR CHANNELS CHANSETS TYPES SEMI DCOLON COMPOSE OF STAR TO INMAPOF MAPOF SEQOF SEQ1OF SETOF PLUSGT COLONDASH DEQUALS INV VALUES FUNCTIONS PRE POST MEASURE SUBCLASSRESP NOTYETSPEC OPERATIONS FRAME RD WR STATE LET IN IF THEN ELSEIF ELSE CASES OTHERS PLUS MINUS ABS FLOOR NOT CARD POWER DUNION DINTER HD TL LEN ELEMS INDS REVERSE CONC DOM RNG MERGE INVERSE ELLIPSIS BARRARROW MKUNDER MKUNDERNAME DOT DOTHASH NUMERAL LAMBDA NEW SELF ISUNDER PREUNDER ISOFCLASS TILDE DCL COLONEQUALS ATOMIC DEQRARROW RETURN IDENTIFIER BACKTICK
%token SLASH DIVIDE REM MOD LT LTE GT GTE NEQ OR AND EQRARROW LTEQUALSGT INSET NOTINSET SUBSET PROPER_SUBSET UNION BACKSLASH INTER CARET DPLUS MAPMERGE LTCOLON LTDASHCOLON COLONGT COLONDASHGT COMP DSTAR FORALL EXISTS EXISTS1 STRING VRES RES VAL
%token HEX_LITERAL QUOTE_LITERAL
%token AMP LSQUAREBAR DLSQUARE DRSQUARE BARRSQUARE COMMA LSQUAREDBAR DBARRSQUARE COLON LCURLYBAR BARRCURLY QUESTION BANG SLASHCOLON SLASHBACKSLASH COLONBACKSLASH LSQUAREGT BARGT ENDSBY STARTBY COLONINTER COLONUNION LCURLYCOLON COLONRCURLY LSQUARECOLON COLONRSQUARE MU
%token TBOOL TNAT TNAT1 TINT TRAT TREAL TCHAR TTOKEN PRIVATE PROTECTED PUBLIC LOGICAL
%token nameset namesetExpr

%right LPAREN
%right COMMA
%left SEQOF
%left BARTILDEBAR LRSQUARE TBAR AMP RARROW DLSQUARE LSQUAREBAR LSQUAREGT BARRSQUARE LSQUARE RSQUARE SETOF SEQ1OF MAPOF INMAPOF PLUSGT TO OF NEW COLONEQUALS SLASH BACKSLASH ENDSBY STARTBY LSQUAREDBAR DBARRSQUARE DBAR SLASHCOLON SLASHBACKSLASH COLONBACKSLASH SEMI COLONINTER COLONUNION BARGT
%right ELSE ELSEIF
%right UPLUS UMINUS ABS FLOOR NOT CARD POWER DUNION DINTER HD TL LEN ELEMS INDS REVERSE CONC DOM RNG MERGE INVERSE
%left PLUS MINUS DIVIDE REM MOD LT LTE GT GTE EQUALS NEQ OR AND EQRARROW LTEQUALSGT INSET STAR NOTINSET SUBSET PROPER_SUBSET UNION INTER CARET DPLUS MAPMERGE LTCOLON LTDASHCOLON COLONGT COLONDASHGT COMP DSTAR IN
%right LRPAREN
%left DOT DOTHASH

%start sourceFile

%%

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
  BEGIN processParagraphList AT action END
| BEGIN AT action END
| process SEMI process
| process LRSQUARE process
| process BARTILDEBAR process
| process LSQUAREBAR chansetExpr BARRSQUARE process
| process LSQUARE chansetExpr DBAR chansetExpr RSQUARE process
| process DBAR process
| process TBAR process
| process SLASHBACKSLASH process
| process SLASHCOLON expression COLONBACKSLASH process
| process LSQUAREGT process
| process LSQUARE expression BARGT process
| process COLONBACKSLASH chansetExpr
| process STARTBY expression
| process ENDSBY expression
| LPAREN declaration AT IDENTIFIER RPAREN LPAREN expression RPAREN
// FIXME --- causes s/r against everything in VDM
/* | IDENTIFIER LPAREN expression RPAREN */
// FIXME this rule is should probably be added
/* | IDENTIFIER */
| process renameExpression
| SEMI LCURLY replicationDeclaration AT process RCURLY 
| BARTILDEBAR LCURLY replicationDeclaration AT process RCURLY 
| LRSQUARE LCURLY replicationDeclaration AT process RCURLY 
| TBAR LCURLY replicationDeclaration AT process RCURLY 
| LSQUARECOLON chansetExpr COLONRSQUARE LCURLY replicationDeclaration AT process RCURLY 
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
/* IDENTIFIER COLON expression // TODO */
/* | singleExpressionDeclaration COMMA IDENTIFIER // TODO */
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
| IDENTIFIER RARROW action
| IDENTIFIER communicationParameterList RARROW action
| COLON expression AMP action
| action SEMI action
| action LRSQUARE action
| action BARTILDEBAR action
| action SLASHBACKSLASH action
| action SLASHCOLON expression COLONBACKSLASH action
| action LSQUAREGT action
| action LSQUARE expression BARGT action
| action COLONBACKSLASH chansetExpr
| action STARTBY expression
| action ENDSBY expression
| action renameExpression
/* | MU pathList AT LPAREN action RPAREN // TODO */
| parallelAction
| LPAREN parametrisationList AT action RPAREN 
| instantiatedAction 
| replicatedAction 
| letStatement 
| blockStatement 
| controlStatement 
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
/* | DOT expression */
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
| LPAREN parametrisationList AT action RPAREN LPAREN expressionList RPAREN 
;

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
  DLSQUARE renameList DRSQUARE
| DLSQUARE renameList BAR bindList DRSQUARE
| DLSQUARE renameList BAR bindList AT expression DRSQUARE
;

renameList :
  channelEvent LARROW channelEvent
| renameList COMMA channelEvent LARROW channelEvent
;

channelEvent :
  IDENTIFIER
/* | IDENTIFIER DOT dotted_expression */
;

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
  pathList
| singleTypeDecl
;

declaration :
  singleTypeDecl
| declaration SEMI singleTypeDecl
;

singleTypeDecl :
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
| LCURLYCOLON pathList COLONRCURLY
| LCURLYBAR pathList BARRCURLY
| chansetExpr COLONUNION chansetExpr
| chansetExpr COLONINTER chansetExpr
| chansetExpr COLONBACKSLASH chansetExpr
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
  LPAREN type RPAREN 
| basicType
| quoteLiteral 
| COMPOSE IDENTIFIER OF fieldList END 
| LPAREN unionType RPAREN
| productType
| LSQUARE type RSQUARE 
| SETOF type
| SEQOF type
| SEQ1OF type
| MAPOF type TO type
| INMAPOF type TO type
| functionType
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

unionType :
  type BAR type
| unionType BAR type
;

productType :
  type STAR type
// FIXME --- causes a s/r (within type, jwc thinks)
/* | productType STAR type  */
;

functionType :
  partialFunctionType
| totalFunctionType
;

partialFunctionType :
  type PLUSGT type
| LRPAREN PLUSGT type 
;

totalFunctionType :
  type RARROW type
| LRPAREN RARROW type 
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
  MEASURE path
| /* empty */
;

operationDefs :
  OPERATIONS operationDefList
| OPERATIONS
;

operationDefList :
  operationDef
/* | operationDef SEMI */
| operationDefList SEMI operationDef
;

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
| FORALL bindList AMP expression
| EXISTS bindList AMP expression
| EXISTS1 bind AMP expression
| LCURLY RCURLY
| LCURLY expressionList RCURLY
| LCURLY expression BAR bindList RCURLY
| LCURLY expression BAR bindList AMP expression RCURLY
| LCURLY expression ELLIPSIS expression RCURLY
| LSQUARE RSQUARE
| LSQUARE expressionList RSQUARE
| LSQUARE expression BAR setBind RSQUARE
| LSQUARE expression BAR setBind AMP expression RSQUARE
| LCURLY BARRARROW RCURLY
| LCURLY mapletList RCURLY
| LCURLY maplet BAR bindList RCURLY
| LCURLY maplet BAR bindList AMP expression RCURLY
| tupleConstructor
| recordConstructor
| lambdaExpr
| generalIsExpr
| preconditionExpr
| ISOFCLASS LPAREN path COMMA expression RPAREN
| path 
| symbolicLiteral 
;

symbolicLiteral :
  numericLiteral
// FIXME
//| booleanLiteral
//| nilLiteral
//| characterLiteral
//| textLiteral
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

 
lambdaExpr :
  LAMBDA typeBindList AMP expression
;

generalIsExpr :
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
/*| generalCasesIfStatement*/ //TODO
// FIXME --- path?
/* | callStatement */
// FIXME --- causes r/r conflict with objectDesignator(call)
| generalAssignStatement
| specificationStatement 
| returnStatement
// FIXEM --- new Statement
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

letStatement :
  LET localDefList IN action 
;

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

tuplePattern :
//  MKUNDER LPAREN patternList RPAREN // TODO
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

path
: unit
| path TILDE
| path DOT unit
| path BACKTICK unit
| path DOTHASH NUMERAL
| path LRPAREN
| path LPAREN expressionList RPAREN
| path LPAREN expression ELLIPSIS expression RPAREN
;

unit
: SELF
| IDENTIFIER
/* | IDENTIFIER TILDE */
/* | unit LRPAREN */
/* | unit LPAREN expressionList RPAREN */
/* | unit LPAREN expression ELLIPSIS expression RPAREN */
;

pathList
: path
| pathList COMMA path
;

// **********************
// *** END OF GRAMMAR ***
// **********************
