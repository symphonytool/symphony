/* File parser.mly */
  
  
  
%token LCURLY RCURLY SEQUENCE COMMA_SEQUENCE SEMICOLON_SEQUENCE CONCAT ALT OPT
%token <string> LITERAL RULEREF RULEDEF
%token EOF

%start main             /* the entry point */
%type <Ast.grammarDef list> main

%{
(*
 type grammarCombinator = Concat | Alt | NA

 type grammarElement = {comb : grammarCombinator option ; rule_desc :rule_type}
 and rule_type =
   | Lit of string
   | Seq of (grammarElement list * string option)
   | Opt of grammarElement list
   | Ref of string

 type grammarDef = { name : string ; defs : grammarElement list }
*)
%}

%%
/* "digraph CMLGrammar {\n" ^ (to_dotty_string $1 "") ^ "\n\n}"*/
main:
ruledefs EOF              { $1 }
|  EOF                    { [] }
  ;

irrelevant:
LCURLY { "" }
|RCURLY { "" }
| CONCAT { "" }
;

ruledefs:
  | ruledef              { [$1] }  
  | irrelevant           { [] }
  | ruledef ruledefs     { $1::$2 }
  | irrelevant ruledefs     { $2 }
;

ruledef:
  RULEDEF LCURLY rules_list RCURLY 
  { {Ast.name = $1 ; Ast.defs = $3} }
;


rules:
  RULEREF                       { {Ast.comb = None;
				   rule_desc= Ast.Ref($1)} } 
  | LITERAL                     { {Ast.comb = None;
				   Ast.rule_desc= Ast.Lit($1)} }
  | SEQUENCE LCURLY rules_list RCURLY { {Ast.comb = None;
					 Ast.rule_desc= Ast.Seq ($3,None) } } 
  | COMMA_SEQUENCE LCURLY rules_list RCURLY { {Ast.comb = None;
					 Ast.rule_desc= Ast.Seq ($3,Some ",") } } 
  | SEMICOLON_SEQUENCE LCURLY rules_list RCURLY { {Ast.comb = None;
					 Ast.rule_desc= Ast.Seq ($3,Some ";") } } 

  | OPT LCURLY rules_list RCURLY    { {Ast.comb = None;
					 Ast.rule_desc= Ast.Opt $3 } }
;

  combinator:
  | CONCAT            { Ast.Concat }
  | ALT               { Ast.Alt} 
  |                   { Ast.NA }
;

rules_list:
  rules { [$1] }
  | rules combinator rules_list { {$1 with Ast.comb=Some($2) }::$3 }
;
