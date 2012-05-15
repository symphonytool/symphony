/* Mini Calculator */
/* calc.y */

%start	input 

%token	<Integer>	INTEGER_LITERAL
%type	<Integer>	exp
%left	PLUS
%left	MULT

%%

input:		/* empty */
                | exp	{ System.out.println($1); }
		;

exp:		INTEGER_LITERAL	{ $$ = $1; }
		| exp PLUS exp	{ $$ = $1 + $3; }
		| exp MULT exp	{ $$ = $1 * $3; }
		;

%%

