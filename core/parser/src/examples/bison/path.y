/* This is just a quick experiment to make sure that I (jwc) can make
 * a (sub)grammar that parses every possible VDM stateDesignator,
 * objectDesignator, name, etc without any conflicts
 *
 * I think I even have the precedence bits correct :).
 *
 * Integrating it into the main CML grammar will require conversion of
 * the nice clean structure into whichever random bit of the VDM AST
 * we've got.
 */
%language "java"
%locations
%define parser_class_name "Path"

%token IDENTIFIER STRING NUMERAL SELF
%token LPAREN LRPAREN RPAREN ELLIPSIS DOT COMMA DOTHASH TILDE
%token UPLUS UMINUS ABS REV NOT
%token PLUS MINUS STAR SLASH AND

%left AND
%left STAR
%left MINUS
%left UMINUS NOT

%start expression
%%
expression
: STRING
| path 
| LPAREN expression RPAREN
| unaryExpression
| binaryExpression
;

unaryExpression
: MINUS expression %prec UMINUS
| ABS expression
| NOT expression
;

binaryExpression
: expression MINUS expression
| expression STAR expression
| expression AND expression
;

expressionList
: expression
| expressionList COMMA expression
;

path
: unit
| path DOT unit
| path DOTHASH NUMERAL
;

unit
: SELF
| IDENTIFIER
| IDENTIFIER TILDE
| unit LRPAREN
| unit LPAREN expressionList RPAREN
| unit LPAREN expression ELLIPSIS expression RPAREN
;
