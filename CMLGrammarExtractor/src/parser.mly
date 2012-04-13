/* File parser.mly */

%token LCURLY RCURLY SEQUENCE COMMA_SEQUENCE SEMICOLON_SEQUENCE CONCAT ALT OPT
%token <string> LITERAL RULEREF RULEDEF
%token EOF

%start main             /* the entry point */
%type <string> main

%{
 type grammarCombinator = Concat | Alt | NA

 type grammarElement = {comb : grammarCombinator option ; rule_desc :rule_type}
 and rule_type =
   | Lit of string
   | Seq of (grammarElement list * string option)
   | Opt of grammarElement list
   | Ref of string

 type grammarDef = { name : string ; defs : grammarElement list }


 let rec to_dotty_string (defs : grammarDef list) (out : string) : string =
   match defs with
     | [] -> out
     | hd::tl -> to_dotty_string tl (out ^ (process_elems ("\""^hd.name^"\"") hd.defs false ""))
 and process_elems rulename elems optional out =
   match elems with
     | [] -> out
     | hd::tl -> 
       ( match hd.rule_desc with
	 | Ref s -> 
	   process_elems rulename tl optional (out ^ "\n" ^ 
						 (rulename ^ "-> \"" ^ s ^ "\"" ^
						    if optional then " [style=dotted]" else "" 
						    ^ ";")) 
	 | Lit s -> 
	   process_elems rulename tl optional (out ^ "\n" ^ (rulename ^ "-> \"'" ^ s ^ "'\"" ^
							       if optional then " [style=dotted]" else "" 
								 ^ ";"))
	     
	 | Seq (els,sep_opt) -> 
	   
	   let out' = List.fold_left  
	     (fun a e ->
	       match e.rule_desc with
		 | Ref s 
		 | Lit s ->
		   a ^("\n"^ rulename ^" -> \""^s^"\"[label=\"*\" "^  
		       if optional then " style=dotted];" else "];") 
		   ^ (
		     match sep_opt with 
		       | None -> ""
		       | Some sep ->
			 ("\n"^ rulename ^" -> \"'"^sep^"'\"[label=\"*\" "^  
		       if optional then " style=dotted];" else "];")
		     )
		     
		 | _ -> a
	     )
	     out
	     els 
	   in
	   process_elems rulename tl optional out'
	 | Opt els ->
	   let out' = process_elems rulename els true "" in
	   process_elems rulename tl false out ^ out'
	   
       )
%}

%%

main:
ruledefs EOF              { "digraph CMLGrammar {\n" ^ (to_dotty_string $1 "") ^ "\n\n}"}
|  EOF                    { "EOF"  }
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
  { {name = $1 ; defs = $3} }
;


rules:
  RULEREF                       { {comb = None;
				   rule_desc= Ref($1)} } 
  | LITERAL                     { {comb = None;
				   rule_desc= Lit($1)} }
  | SEQUENCE LCURLY rules_list RCURLY { {comb = None;
					 rule_desc= Seq ($3,None) } } 
  | COMMA_SEQUENCE LCURLY rules_list RCURLY { {comb = None;
					 rule_desc= Seq ($3,Some ",") } } 
  | SEMICOLON_SEQUENCE LCURLY rules_list RCURLY { {comb = None;
					 rule_desc= Seq ($3,Some ";") } } 

  | OPT LCURLY rules_list RCURLY    { {comb = None;
					 rule_desc= Opt $3 } }
;

  combinator:
  | CONCAT            { Concat }
  | ALT               { Alt} 
  |                   { NA }
;

rules_list:
  rules { [$1] }
  | rules combinator rules_list { {$1 with comb=Some($2) }::$3 }
;
