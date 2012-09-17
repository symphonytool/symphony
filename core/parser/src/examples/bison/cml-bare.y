%require "2.5"
%language "java"
%locations
%define package "eu.compassresearch.core.parser"
%define public
%define parser_class_name "CmlParser"

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
%token SLASHCOLON SLASHBACKSLASH COLONBACKSLASH LSQUAREGT BARGT ENDSBY DECIMAL
%token STARTBY COLONINTER COLONUNION LCURLYCOLON COLONRCURLY MU PRIVATE
%token PROTECTED PUBLIC LOGICAL DOTCOLON DO FOR ALL BY WHILE ISUNDERNAME
%token EXTENDS
%token TBOOL TNAT TNAT1 TINT TRAT TREAL TCHAR TTOKEN TRUE FALSE TICK CHAR_LIT

%token nameset namesetExpr nilLiteral textLiteral

%right LPAREN
%right COMMA
%left SEQOF
%left BARTILDEBAR LRSQUARE TBAR AMP RARROW DLSQUARE LSQUAREBAR LSQUAREGT
      BARRSQUARE LSQUARE RSQUARE SETOF SEQ1OF MAPOF INMAPOF PLUSGT TO OF
      NEW COLONEQUALS SLASH BACKSLASH ENDSBY STARTBY LSQUAREDBAR DBARRSQUARE
      DBAR SLASHCOLON SLASHBACKSLASH COLONBACKSLASH COLONINTER COLONUNION
      BARGT
%right SEMI
%right U-SEMI U-BARTILDEBAR U-DBAR U-TBAR U-LRSQUARE U-LSQUARE U-LSQUAREBAR
       U-LSQUAREDBAR
%nonassoc ELSE ELSEIF
%left BAR
%left DO
%right U-DO
%left PLUS MINUS DIVIDE REM MOD LT LTE GT GTE EQUALS NEQ OR AND EQRARROW
      LTEQUALSGT INSET STAR NOTINSET SUBSET PROPER_SUBSET UNION INTER CARET
      DPLUS MAPMERGE LTCOLON LTDASHCOLON COLONGT COLONDASHGT COMP DSTAR IN COLON
%right U-PLUS U-MINUS ABS FLOOR NOT CARD POWER DUNION DINTER HD TL LEN ELEMS
       INDS REVERSE CONC DOM RNG MERGE INVERSE
%left DOT DOTHASH DOTCOLON
%left LRPAREN

%start source

%%

source :
  programParagraphList
;

programParagraphList :
  programParagraph
| programParagraphList programParagraph
;

programParagraph :
  classDefinition
| processDefinition
| channelDefinition
| chansetDefinitionParagraph
| globalDefinitionParagraph
;

classDefinition :
  CLASS IDENTIFIER EQUALS BEGIN classDefinitionBlock END
| CLASS IDENTIFIER EQUALS EXTENDS IDENTIFIER BEGIN classDefinitionBlock END
;

processDefinition:
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
| process SLASHCOLON expression COLONBACKSLASH process
| process LSQUAREGT process
| process LSQUARE expression BARGT process
| process COLONBACKSLASH chansetExpr
| process STARTBY expression
| process ENDSBY expression
| LPAREN declaration AT processDef RPAREN LPAREN expression RPAREN
| path
| process renameExpression
| SEMI replicationDeclaration AT process %prec U-SEMI
| BARTILDEBAR replicationDeclaration AT process %prec U-BARTILDEBAR
| TBAR replicationDeclaration AT process %prec U-TBAR
| LRSQUARE replicationDeclaration AT process %prec U-LRSQUARE
| LSQUARE chansetExpr RSQUARE replicationDeclaration AT process %prec U-LSQUARE
;

replicationDeclaration :
  replicationDeclarationAlt
| replicationDeclaration SEMI replicationDeclarationAlt
;

replicationDeclarationAlt :
  singleTypeDecl
| singleExpressionDeclaration
;

singleExpressionDeclaration :
  IDENTIFIER IN expression
| singleExpressionDeclaration COMMA IDENTIFIER
;

processParagraphList :
  processParagraph
| processParagraphList processParagraph
;

processParagraph :
 classDefinitionBlockAlternative
| INITIAL operationDef
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
| LSQUARE expression RSQUARE AMP action
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
| MU pathList AT LPAREN action RPAREN
| action LSQUAREDBAR namesetExpr BAR namesetExpr DBARRSQUARE action
| action TBAR action
| action LSQUAREBAR namesetExpr BAR namesetExpr BARRSQUARE action
| action DBAR action
| action LSQUARE namesetExpr BAR chansetExpr DBAR chansetExpr BAR namesetExpr RSQUARE action
| action LSQUARE chansetExpr DBAR chansetExpr RSQUARE action
| action LSQUAREBAR namesetExpr BAR chansetExpr BAR namesetExpr BARRSQUARE action
| action LSQUAREBAR chansetExpr BARRSQUARE action
| LPAREN parametrisationList AT action RPAREN
| LPAREN declaration AT action RPAREN LPAREN expressionList RPAREN
| LPAREN parametrisationList AT action RPAREN LPAREN expressionList RPAREN
| SEMI replicationDeclaration AT action %prec U-SEMI
| LRSQUARE LCURLY replicationDeclaration AT action RCURLY %prec U-LRSQUARE
| BARTILDEBAR LCURLY replicationDeclaration AT action RCURLY %prec U-BARTILDEBAR
| LSQUAREDBAR nameset DBARRSQUARE LPAREN replicationDeclaration AT action RPAREN %prec U-LSQUAREDBAR
| TBAR replicationDeclaration AT LSQUARE namesetExpr RSQUARE action %prec U-TBAR
| LSQUAREBAR chansetExpr BARRSQUARE replicationDeclaration AT LSQUARE namesetExpr RSQUARE action %prec U-LSQUAREBAR
| DBAR replicationDeclaration AT LSQUARE namesetExpr BAR chansetExpr RSQUARE action %prec U-DBAR
| DBAR replicationDeclaration AT LSQUARE namesetExpr RSQUARE action %prec U-DBAR
| letStatement
| blockStatement
| controlStatement
;

communicationParameterList :
  communicationParameter
| communicationParameterList communicationParameter
;

communicationParameter :
  QUESTION parameter
| QUESTION parameter COLON expression
| BANG expression
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

parametrisationList :
  parametrisation
| parametrisationList SEMI parametrisation
;

parametrisation :
  VAL singleTypeDecl
| RES singleTypeDecl
| VRES singleTypeDecl
;

renameExpression :
  DLSQUARE renameList DRSQUARE
| DLSQUARE renameList BAR bindList DRSQUARE
| DLSQUARE renameList BAR bindList AT expression DRSQUARE
;

renameList :
  channelNameExpr LARROW channelNameExpr
| renameList COMMA channelNameExpr LARROW channelNameExpr
;

channelNameExpr :
  IDENTIFIER
| IDENTIFIER DOTCOLON channelNameExprTail
;

channelNameExprTail :
  expression
| channelNameExprTail DOTCOLON expression
;

channelDefinition :
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

chansetDefinitionParagraph :
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
| LCURLYBAR channelNameExpr BAR bindList BARRCURLY
| LCURLYBAR channelNameExpr BAR bindList AT expression BARRCURLY
;

globalDefinitionParagraph :
  globalDefinitionBlockAlternative
;

globalDefinitionBlockAlternative :
  typeDefs
| valueDefs
| functionDefs
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
| type BAR type
| type STAR type
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
| operationDefList SEMI operationDef
| operationDefList SEMI operationDef SEMI
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
  /* action */
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
| MKUNDER LPAREN expressionList RPAREN
| MKUNDERNAME LPAREN expressionList RPAREN
| LAMBDA typeBindList AMP expression
| generalIsExpr
| PREUNDER LPAREN expressionList RPAREN
| ISOFCLASS LPAREN path COMMA expression RPAREN
| path
| symbolicLiteral
;

booleanLiteral:
  FALSE
| TRUE
;

symbolicLiteral :
  numericLiteral
| booleanLiteral
| nilLiteral
| characterLiteral
| textLiteral
| quoteLiteral
;

characterLiteral :
CHAR_LIT

numericLiteral :
  NUMERAL
| HEX_LITERAL
| DECIMAL
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

generalIsExpr :
  ISUNDERNAME LPAREN expression RPAREN
| ISUNDER basicType LPAREN expression RPAREN
| ISUNDER LPAREN expression COMMA type RPAREN
;

controlStatement :
  ifStatement
| IF nonDeterministicAltList END
| DO nonDeterministicAltList END %prec U-DO
| assignStatement
| ATOMIC LPAREN assignStatementList RPAREN
| LSQUARE implicitOperationBody RSQUARE
| RETURN
| RETURN LPAREN expression RPAREN
| path COLONEQUALS NEW path LRPAREN
| path COLONEQUALS NEW path LPAREN expressionList RPAREN
| casesStatement
| FOR bind IN expression DO action
| FOR pattern IN expression DO action
| FOR ALL pattern INSET expression DO action
| FOR IDENTIFIER EQUALS expression TO expression DO action
| FOR IDENTIFIER EQUALS expression TO expression BY expression DO action
| WHILE expression DO action
| path
;

nonDeterministicAltList :
  expression RARROW action
| nonDeterministicAltList BAR expression RARROW action
;

letStatement :
  LET localDefList IN action
;

blockStatement :
  LPAREN action RPAREN
| LPAREN DCL assignmentDefList AT action RPAREN
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

assignStatementList :
  assignStatement
| assignStatementList SEMI assignStatement
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

implicitOperationBody :
  externals_opt preExpr_opt postExpr
;

pattern :
  patternIdentifier
| patternLessID
;

patternLessID :
  matchValue
| MKUNDER LPAREN patternList COMMA pattern RPAREN
| MKUNDERNAME LRPAREN
| MKUNDERNAME LPAREN patternList RPAREN
;

patternList :
  pattern
| patternList COMMA pattern
;

patternIdentifier :
  IDENTIFIER
| MINUS
;

matchValue :
  symbolicLiteral
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




