        (* File lexer.mll *)
  {
    open Parser        (* The type token is defined in parser.mli *)
    open Str           (* regexp *)
    exception Eof

    let extract_rulename s = 
      let first = (String.index s '{') + 1 in
      let last = String.rindex s '}' in
      String.sub s first (last - first) 
	
    let debug_helper debug tokenStr = 
      if debug then print_endline tokenStr else ()
  }

(*******************************************************************
 * Helpers                                                         *
 *******************************************************************)
let latin1_input_character = ['\000'- '\255']
let ht = '\t'
let lf = '\n'
let ff = '\012'
let cr = '\r'
let sp = ' '

let digit = ['0'-'9']

let latin1_letter =
       ['A'-'Z'] | ['a'-'z'] | ['\170'-'\170'] | ['\181'-'\181'] |
       ['\186'-'\186'] | ['\192'-'\214'] | ['\216'-'\246'] | ['\248'-'\255']

let letter = latin1_letter | '$' | '_' 
let letter_or_digit = latin1_letter | digit | '$' | '_' | '-'

let line_terminator = lf | cr | cr lf 

let comments = '%'[^'\n']*

(*let rulename = [^'\n''\r''\t'' ''\012''\\']['a'-'z' 'A'-'Z' ' ' '0'-'9' '-']+ *)


(*******************************************************************
 * Tokens                                                         *
 *******************************************************************)
    rule token debug = parse
      | eof            { EOF }
      | (sp | ht | ff)+     { token debug lexbuf }  (* skip blanks and newlines *)
      | line_terminator   {Lexing.new_line lexbuf;token debug lexbuf }  
      | comments as s     { let _ = debug_helper debug ("comment" ^ s) in token debug lexbuf }
      | "\\begin"[^'\n']+ as s { let _ = debug_helper debug ("begin: "^s) in 
			    Lexing.new_line lexbuf;token debug lexbuf}
      | "\\end"[^'\n']+ as s { let _ = debug_helper debug ("begin: "^s) in 
				 Lexing.new_line lexbuf;token debug lexbuf}
      | "\\RuleDef{" [^'}']+ '}' as s    { 
	let s' = extract_rulename s in
	(*let s_tokens = Str.split (Str.regexp "[\t\n]+") s' in
	let s' = if (List.length s_tokens) > 1 then 
	    List.fold_left 
	  else s' in*)
	let _ = debug_helper debug ("RULEDEF " ^ s') in RULEDEF s' 
      }
      | "\\RuleRef{" [^'}']+ '}' as s  { 
	let s' = extract_rulename s in
	let _ = debug_helper debug ("RULEREF " ^ s') in 
	RULEREF s'}
(*      | "\\Literal{" [^'}']+ '}'+ as s { *)
      | "\\Literal{" [^'}']+ '}' as s {
	let s' = extract_rulename s in
	let _ = debug_helper debug ("LITERAL " ^ s) in
	let _ = debug_helper debug ("LITERAL " ^ s') in 
	LITERAL s' }
      | "\\Sequence"   { let _ = debug_helper debug "SEQUENCE" in SEQUENCE }
      | "\\CommaSepList"   { let _ = debug_helper debug "COMMA_SEQUENCE" in COMMA_SEQUENCE }
      | "\\SemicolonSepList"   { let _ = debug_helper debug "SEMICOLON_SEQUENCE" in 
				 SEMICOLON_SEQUENCE }

      | "\\Alt"        { let _ = debug_helper debug "ALT" in ALT }
      | "\\Optional"   {let _ =  debug_helper debug "OPT" in OPT}
      | '\\'['a'-'z''A'-'Z']+ { token debug lexbuf }
      | '{'            { let _ = debug_helper debug "LCURLY" in LCURLY }
      | '}'            { let _ = debug_helper debug "RCURLY" in RCURLY }
      | ','            { let _ = debug_helper debug "CONCAT" in CONCAT }
      (*      
	      | letter (letter_or_digit | ' ')* as n  { 
	      let _ = debug_helper debug ("RULENAME: " ^ n) in 
	      RULENAME n 
	      }
      *)

      | _    { token debug lexbuf }
 (*     | ['0'-'9']+ as lxm { INT(int_of_string lxm) }*)
    
