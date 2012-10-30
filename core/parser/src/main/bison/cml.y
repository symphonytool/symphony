%require "2.5"
%language "java"
%locations
%define package "eu.compassresearch.core.parser"
%define public
%define parser_class_name "CmlParser"


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
%right LET //needed to make let expressions recurse correctly
%right LPAREN // also needed to make parenthesized expressions recurse
%right MU LAMBDA AT
%right COMMA
%right FORALL EXISTS EXISTS1 IOTA
//%right LPAREN
//%left OF COLONEQUALS
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
/* %start source */
%start processDefinition

%%

/* ---------------------------------------------------------------- */
/* The rules                                                        */
/* ---------------------------------------------------------------- */

/* source : */
/*   programParagraphList */
/* ; */

/* programParagraphList : */
/*   programParagraph */
/* | programParagraphList programParagraph */
/* ; */

/* programParagraph : */
/*   classDefinition */
/* | processDefinition */
/* | channelDefinition */
/* | chansetDefinitionParagraph */
/* | globalDefinitionParagraph */
/* ; */

/* classDefinition : */
/*   CLASS IDENTIFIER EQUALS BEGIN classDefinitionBlock END */
/* | CLASS IDENTIFIER EQUALS EXTENDS IDENTIFIER BEGIN classDefinitionBlock END */
/* ; */

processDefinition:
  PROCESS IDENTIFIER EQUALS process
| PROCESS IDENTIFIER EQUALS singleTypeDeclarationList AT process
;

process :
/* actions */
  BEGIN AT action END
| BEGIN processParagraphList AT action END
/* actions end */
| process SEMI process
| process LRSQUARE process
| process BARTILDEBAR process
/* CHANSET
 * expression was chansetExpr here
 */
| process LSQUAREBAR expression BARRSQUARE process
/* CHANSET
 * expression was chansetExpr here
 */
| process LSQUARE expression DBAR expression RSQUARE process
| process DBAR process
| process TBAR process
| process SLASHBACKSLASH process
/* DEVIATION
 * grammar:
 *   process '/' expression '\' process
 * this conflicts all over, so
 *   process '/:' expression ':\' process
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
| process SLASHCOLON expression COLONBACKSLASH process
| process LSQUAREGT process
/* DEVIATION
 * grammar:
 *   process '[' expression '>' process
 * here:
 *   process '[' expression '|>' process
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
| process LSQUARE expression BARGT process
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
| process STARTBY expression
| process ENDSBY expression
/* DEVIATION
 * Unfolded the definition a little bit to avoid a conflict
 */
| LPAREN singleTypeDeclarationList AT process RPAREN LPAREN expression RPAREN
/* PATH
 * CML_0:
 *   IDENTIFIER
 *   IDENTIFIER LRPAREN
 *   IDENTIFIER LPAREN expressionList RPAREN
 * here:
 *   path
 */
| IDENTIFIER
| IDENTIFIER LRPAREN
| IDENTIFIER LPAREN expressionList RPAREN
| process renameExpression
/* replicated processes */
| SEMI replicationDeclaration AT process %prec U-SEMI
| LRSQUARE replicationDeclaration AT process %prec U-LRSQUARE
| BARTILDEBAR replicationDeclaration AT process %prec U-BARTILDEBAR
/* CHANSET
 * expression was chansetExpr here
 */
| LSQUAREBAR expression BARRSQUARE replicationDeclaration AT process %prec U-LSQUAREBAR
/* CHANSET
 * expression was chansetExpr here
 */
| DBAR replicationDeclaration AT LSQUARE expression RSQUARE process %prec U-DBAR
| DBAR replicationDeclaration AT process %prec U-DBAR
| TBAR replicationDeclaration AT process %prec U-TBAR
;

/* FIXME
 */
replicationDeclaration :
  singleTypeDeclaration
/* | singleExpressionDeclaration */
| replicationDeclaration SEMI singleTypeDeclaration
/* | replicationDeclaration SEMI singleExpressionDeclaration */
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
/* singleExpressionDeclaration : */
/*   expressionList INSET expression */
/* ; */

processParagraphList :
  processParagraph
| processParagraphList processParagraph
;

/* FIXME
 *
 * (JWC) I'm not convinced this matches the 'process paragraph' of the
 * grammar.
 */
processParagraph :
  classDefinitionBlockAlternative
| ACTIONS actionDefinitionList
| NAMESETS
| NAMESETS namesetDefList
;

actionDefinitionList :
  IDENTIFIER EQUALS paragraphAction
| actionDefinitionList IDENTIFIER EQUALS paragraphAction
;

/* actionDefinition : */
/*   IDENTIFIER EQUALS paragraphAction */
/* ; */

/* Note that the expressions here are nameset expressions */
/* DEVIATION
 *
 * The whole structure of the nameset definitions (including the
 * keyword itself!) can be considered a deviation.
 */
namesetDefList :
  IDENTIFIER EQUALS expression // TODO
| namesetDefList IDENTIFIER EQUALS expression // TODO
| namesetDefList SEMI IDENTIFIER EQUALS expression // TODO
;

paragraphAction :
  action
| singleTypeDeclarationList AT action
;

action :
  CSPSKIP
| CSPSTOP
| CSPCHAOS
| CSPDIV
| CSPWAIT expression
/* Communication rule start*/
/* DEVIATION
 * PATH
 * CML_0:
 *   action: ...| communication '->' action
 *   communication: IDENTIFIER { communicationParameter }
 *   communicationParameter: '?' parameter | '?' parameter ':' expression | '!' expression | '.' expression
 * here:
 *   expression '->' action
 *
 * Need to verify that this matches all desired communication formats; see sub-production
 */
| expression RARROW action[to]
/* guards */
| expression AMP action
| action SEMI action
| action LRSQUARE action
| action BARTILDEBAR action
| action SLASHBACKSLASH action
/* DEVIATION
 * grammar:
 *   process '/' expression '\' process
 * this conflicts all over, so
 *   process '/:' expression ':\' process
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
| action SLASHCOLON expression COLONBACKSLASH action
| action LSQUAREGT action
/* DEVIATION
 * grammar:
 *   action '[' expression '>' action
 * here:
 *   action '[' expression '|>' action
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
| action LSQUARE expression BARGT action
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
| action STARTBY expression
| action ENDSBY expression
| action renameExpression
/* DEVIATION
 * grammar:
 *   MU identifier {',' identifier} '@' action {',' action}
 * here:
 *   MU expressionList '@' '(' actionList ')'
 */
| MU expressionList AT LPAREN actionList RPAREN
/* parallel actions */
/* NAMESET
 * expression was namesetExpr here
 */
| action LSQUAREDBAR expression BAR expression DBARRSQUARE action
| action TBAR action
/* NAMESET
 * expression was namesetExpr here
 */
| action LSQUAREBAR expression BAR expression BARRSQUARE action
| action DBAR action
/* CHANSET
 * NAMESET
 * expressions were namesetExpr|chansetExpr||chansetExpr|namesetExpr here
 */
| action LSQUARE expression BAR expression DBAR expression BAR expression RSQUARE action
/* CHANSET
 * expression was chansetExpr here
 */
| action LSQUARE expression DBAR expression RSQUARE action
/* CHANSET
 * NAMESET
 * expressions were namesetExpr|chansetExpr|namesetExpr here
 */
| action LSQUAREBAR expression BAR expression BAR expression BARRSQUARE action
/* CHANSET
 * expression was chansetExpr here
 */
| action LSQUAREBAR expression BARRSQUARE action
/* parallel actions end */
/* parametrised action */
| parametrisationList AT action
/* instantiated actions */
| LPAREN singleTypeDeclarationList AT action RPAREN LPAREN expressionList RPAREN
/* | LPAREN parametrisationList AT action RPAREN LPAREN expressionList RPAREN */
/* instantiated actions end */
/* replicated actions */
| SEMI replicationDeclaration AT action %prec U-SEMI
| LRSQUARE replicationDeclaration AT action %prec U-LRSQUARE
| BARTILDEBAR replicationDeclaration AT action %prec U-BARTILDEBAR
/* NAMESET
 * expression was namesetExpr here
 */
| LSQUAREDBAR expression DBARRSQUARE replicationDeclaration AT action %prec U-LSQUAREDBAR
/* NAMESET
 * expression was namesetExpr here
 */
| TBAR replicationDeclaration AT LSQUARE expression RSQUARE action %prec U-TBAR
/* CHANSET
 * NAMESET
 * expressions were namesetExpr, chansetExpr here
 */
| LSQUAREBAR expression BARRSQUARE replicationDeclaration AT LSQUARE expression RSQUARE action %prec U-LSQUAREBAR
/* CHANSET
 * NAMESET
 * expressions were namesetExpr, chansetExpr here
 */
| DBAR replicationDeclaration AT LSQUARE expression BAR expression RSQUARE action %prec U-DBAR
/* CHANSET
 * expression was namesetExpr here
 */
| DBAR replicationDeclaration AT LSQUARE expression RSQUARE action %prec U-DBAR
/* replicated actions end */
| letStatement
| blockStatement
| controlStatement
/* This is in blockStatement | LPAREN action RPAREN */
;

actionList :
  action
| actionList[list] COMMA action
;

parametrisationList :
  parametrisation
| parametrisationList SEMI parametrisation
;

parametrisation :
  VAL singleTypeDeclaration
| RES singleTypeDeclaration
| VRES singleTypeDeclaration
;

renameExpression :
/* rename enumeration */
  DLSQUARE renameList DRSQUARE
/* rename comprehensions */
| DLSQUARE expression[from] LARROW expression[to] BAR bindList DRSQUARE
| DLSQUARE expression[from] LARROW expression[to] BAR bindList AT expression DRSQUARE
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
  expression[from] LARROW expression[to]
| renameList COMMA expression[from] LARROW expression[to]
;

/* DEVIATION
 * CML_0:
 *   doesn't exist as such
 * here:
 *   'channels', { channelDefinition, { ‘;’, channelDefinition } } [ ';' ]
 */
/* channelDefinition : */
/*   CHANNELS */
/* | CHANNELS channelDef */
/* | CHANNELS channelDef SEMI */
/* ; */

/* channelDef : */
/*   channelNameDecl */
/* | channelDef SEMI channelNameDecl */
/* ; */

/* channelNameDecl : */
/* /\* DEVIATION */
/*  * PATH */
/*  * grammar: */
/*  *   identifierList */
/*  * here: */
/*  *   pathList */
/*  * So, we need to cast the list of names down to a list of identifiers */
/*  *\/ */
/*   expressionList */
/* | singleTypeDeclaration */
/* ; */

/* RENAME
 * declaration in CML_0 grammar corresponds to singleTypeDeclarationList
 */
singleTypeDeclarationList[result] :
  singleTypeDeclaration
| singleTypeDeclarationList[list] SEMI singleTypeDeclaration
;

singleTypeDeclaration :
  IDENTIFIER COLON type
| IDENTIFIER COMMA singleTypeDeclaration
;

/* chansetDefinitionParagraph : */
/*   CHANSETS */
/* | CHANSETS chansetDefinitionList */
/* | CHANSETS chansetDefinitionList SEMI */
/* ; */

/* DEVIATION
 * chanset declarations are now separated by SEMIs, just like everything else.
 */
/* chansetDefinitionList : */
/*   chansetDefinition */
/* | chansetDefinitionList[list] SEMI chansetDefinition */
/* ; */

/* chansetDefinition : */
/* /\* CHANSET */
/*  * expression was chansetExpr here */
/*  *\/ */
/*   IDENTIFIER EQUALS expression */
/* ; */

/* globalDefinitionParagraph : */
/*   typeDefs */
/* | valueDefs */
/* | functionDefs */
/* ; */

/* classDefinitionBlock : */
/*   classDefinitionBlockAlternative */
/* | classDefinitionBlockAlternative classDefinitionBlock */
/* ; */

classDefinitionBlockAlternative :
  typeDefs
| valueDefs
| functionDefs
| operationDefs
| stateDefs
/* /\* UPCOMING --- CML_1 */
/*  * absent in CML_0 */
/*  * */
/*  * This will be in the CML_1 grammar, and is defines the constructor for the class. */
/*  * Confirmed between Joey, Alavro; Skype 30 July 2012 */
/*  *\/ */
| INITIAL operationDef
;

typeDefs :
  TYPES
| TYPES typeDefList
| TYPES typeDefList SEMI
;

typeDefList :
  typeDef
| typeDefList[list] SEMI typeDef
;

typeDef :
  qualifier IDENTIFIER EQUALS type invariant
| qualifier IDENTIFIER EQUALS type
| qualifier IDENTIFIER DCOLON fieldList
| qualifier IDENTIFIER DCOLON fieldList invariant
;

/* FUTURE
 *
 * (jwc) I think for this we really ought have the lexer give the
 * token 'QUALIFIER' and then querying its value (like with numeric
 * literals) to figure out which one we have.
 */
qualifier :
  PRIVATE
| PROTECTED
| PUBLIC
/* (RWL) It is not in overture why are we having it?
 *
 * (JWC) It is in CML, however.  Jim wants it in (for perfectly
 * cromulent reasons), and it's mostly harmless.  We just need to
 * filter it (and all places where it's used) out, or flip it to
 * public/global.  See me for an explanation.
 */
| LOGICAL
| /* empty */
;

type :
  LPAREN type RPAREN // bracketedType
| basicType
| quoteLiteral // quoteType
| COMPOSE IDENTIFIER OF fieldList END
| type BAR type // unionType
| type STAR type %prec T-STAR //productType
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
 */
| IDENTIFIER
| IDENTIFIER DOT IDENTIFIER // name is defined in CML_0 as using DOT
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


/* DEVIATION
 * CML_0:
 *   'values', qualifiedValueDef, { ‘;’, qualifiedValueDef }
 * here:
 *   'values', { qualifiedValueDef, { ‘;’, qualifiedValueDef } } [ ';' ]
 */
valueDefs :
  VALUES
| VALUES valueDefList
| VALUES valueDefList SEMI
;

valueDefList :
  qualifiedValueDef[def]
| valueDefList[list] SEMI qualifiedValueDef[def]
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
| FUNCTIONS functionDefList SEMI
;

functionDefList :
  functionDef
| functionDefList[list] SEMI functionDef
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
/* DEVIATION
 * PATH
 * CML_0:
 *   MEASURE name
 */
  MEASURE expression
| /* empty */ 
;

operationDefs :
  OPERATIONS
| OPERATIONS operationDefList
;

/* DEVIATION
 * require *no* separator between operations
 */
operationDefList :
  operationDef
| operationDefList[list] operationDef
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
  action
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
  mode expressionList
| mode expressionList COLON type
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
  STATE
| STATE stateDefList
| STATE stateDefList SEMI
;

stateDefList :
  stateDef
| stateDefList[list] SEMI stateDef
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
/* symbolic literal expressions*/
  numericLiteral
| booleanLiteral
| nilLiteral
| characterLiteral
| textLiteral
| quoteLiteral
/* symbolic literal expressions end*/
| LPAREN expression RPAREN
| SELF
| IDENTIFIER
| IDENTIFIER TILDE
| expression DOT IDENTIFIER
| expression BACKTICK IDENTIFIER
| expression DOTHASH NUMERAL
| expression LRPAREN
| expression LPAREN expressionList RPAREN
| expression LPAREN expression ELLIPSIS expression RPAREN
| expression DOT matchValue 
/* communication structure */
| expression BANG IDENTIFIER
| expression BANG matchValue
| expression QUESTION pattern
| expression QUESTION setBind
/* end communication structure*/
| LET localDefList IN expression %prec LET
| ifExpr
| casesExpr
| unaryExpr
| binaryExpr
/* quantified expressions */
| FORALL bindList AT expression %prec FORALL
| EXISTS bindList AT expression %prec EXISTS
| EXISTS1 bind AT expression %prec EXISTS1
| IOTA bind AT expression %prec IOTA
/* set enumeration */
| LCURLY RCURLY
| LCURLY expressionList RCURLY
/* set comprehensions */
| LCURLY expression BAR bindList RCURLY
| LCURLY expression BAR bindList AT expression RCURLY
/* set range expression */
| LCURLY expression ELLIPSIS expression RCURLY
/* sequence enumerations */
| LRSQUARE
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
/* sequence comprehensions */
| LSQUARE expression BAR setBind RSQUARE
| LSQUARE expression BAR setBind AT expression RSQUARE
/* map enumerations */
| EMPTYMAP
| LCURLY mapletList RCURLY
/* map comprehensions */
| LCURLY maplet BAR bindList RCURLY
| LCURLY maplet BAR bindList AT expression RCURLY
/* tuple constructor */
| MKUNDER LPAREN expressionList RPAREN
/* recordConstructor */
| MKUNDERNAME LPAREN expressionList RPAREN
/* lambda expression */
| LAMBDA typeBindList AT expression %prec LAMBDA
/* general Is Expressions */
/* DEVIATION
 * CML_0:
 *   ISUNDER name LPAREN expression RPAREN
 * here:
 *   ISUNDERNAME LPAREN expression RPAREN
 *
 */
| ISUNDERNAME LPAREN expression RPAREN
| ISUNDER basicType LPAREN expression RPAREN
| ISUNDER LPAREN expression COMMA type RPAREN
/* precondition expression */
/* (JWC) first parameter of the precondition expression is the
 * function that we want the precondition of
 */
| PREUNDER LPAREN expressionList RPAREN
/* DEVIATION
 * GRAMMAR ERROR: Missing COMMA
 * CML_0:
 *   ISOFCLASS LPAREN name expression RPAREN
 * here:
 *   ISOFCLASS LPAREN path COMMA expression RPAREN
 */
| ISOFCLASS LPAREN expression COMMA expression RPAREN
/* chanset expressions */
| LCURLYBAR expressionList BARRCURLY
| LCURLYBAR expression BAR bindList BARRCURLY
| LCURLYBAR expression BAR bindList AT expression[exp] BARRCURLY
/* chanset expressions end */
;

/* symbolic literals */
booleanLiteral:
  FALSE
| TRUE
;

characterLiteral :
  CHAR_LIT
;

nilLiteral :
  NIL
;

numericLiteral :
  NUMERAL
| HEX_LITERAL
| DECIMAL
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

controlStatement :
  ifStatement
/* nondeterministic statements */
| IF nonDeterministicAltList END
| DO nonDeterministicAltList END %prec U-DO
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
// | assignStatement
/* multiple assign statement */
// | ATOMIC LPAREN assignStatementList RPAREN
/* general assign statement end */
/* specification statement */
| LSQUARE implicitOperationBody RSQUARE
/* return statement */
/* DEVIATION
 * RETURN needs some sort of following value to avoid conflict with actionDefinitionList
 */
// | RETURN
| RETURN LRPAREN
| RETURN expression
/* DEVIATION
 * PATH
 * CML_0:
 *   stateDesignator ':=' 'new' name '(' { expression } ')'
 * here:
 *   path COLONEQUALS NEW path LRPAREN
 *   path COLONEQUALS NEW path LPAREN expressionList RPAREN
 *
 */
// | expression COLONEQUALS NEW expression LRPAREN
// | expression COLONEQUALS NEW expression LPAREN expressionList RPAREN
| casesStatement
/* sequence for loop */
/* FIXME
 *
 * (JWC) The grammar allows reverse as a specific keyword to the for
 * loop, but reverse is also a unary expression operator.  I've no
 * idea what the semantic difference is.
 */
| FOR bind IN expression DO action
// | FOR bind IN REVERSE expression DO action
| FOR pattern IN expression DO action
// | FOR pattern IN REVERSE expression DO action */
/* sequence for loop end */
/* set for loop */
| FOR ALL pattern INSET expression DO action
/* index for loop */
| FOR IDENTIFIER EQUALS expression TO expression DO action
| FOR IDENTIFIER EQUALS expression TO expression BY expression DO action
/* index for loop end */
/* while loop */
| WHILE expression DO action
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
// | expression
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
/*(AKM)
 *FIXME: This is probably not going to work since you can't see the difference
 *       Between 'id : type in exp' and 'id : type := exp'
 *
 */
| IDENTIFIER COLON type IN expression
;

/* assignStatementList : */
/*   assignStatement */
/* | assignStatementList[list] SEMI assignStatement */
/* ; */

/* assignStatement : */
/* /\* DEVIATION */
/*  * PATH */
/*  * CML_0: */
/*  *   stateDesignator ':=' expression */
/*  * here: */
/*  *   path ':=' expression */
/*  *\/ */
/*   expression COLONEQUALS expression */
/* ; */

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
 * PATH
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


/* identifierList: */
/*   IDENTIFIER */
/* | identifierList COMMA IDENTIFIER */
/* ; */

// **********************
// *** END OF GRAMMAR ***
// **********************
