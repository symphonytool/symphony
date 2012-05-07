let rec to_dotty_string (defs : Ast.grammarDef list) (nodeCounter : int) (out : string) : string =
   match defs with
     | [] -> out
     | hd::tl -> to_dotty_string tl nodeCounter (out ^ (process_elems ("\""^hd.Ast.name^"\"") hd.Ast.defs false nodeCounter ""))
 and process_elems rulename elems optional nodeCounter out =
   match elems with
     | [] -> out
     | hd::tl -> 
       ( match hd.Ast.rule_desc with
	 | Ast.Ref s -> 
	   let s = Str.global_replace (Str.regexp "['\n''\r']") "" s in
	   process_elems rulename tl optional nodeCounter (out ^ "\n" ^ 
						 (rulename ^ "-> \"" ^ s ^ "\"" ^
						    if optional then " [style=dotted]" else "" 
						    ^ ";")) 
	 | Ast.Lit s -> 
	   process_elems rulename tl optional nodeCounter (out ^ "\n" ^ (rulename ^ "-> \"'" ^ s ^ "'\"" ^
							       if optional then " [style=dotted]" else "" 
								 ^ ";"))
	     
	 | Ast.Seq (els,sep_opt) -> 
	   
	   let out' = List.fold_left  
	     (fun a e ->
	       match e.Ast.rule_desc with
		 | Ast.Ref s 
		 | Ast.Lit s ->
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
	   process_elems rulename tl optional nodeCounter out'
	 | Ast.Opt els ->
	   let out' = process_elems rulename els true "" in
	   process_elems rulename tl false nodeCounter (out ^ out')
	   
       )

let makeParse ast = 
  let () = print_endline ("digraph CMLGrammar {\n" ^ (to_dotty_string ast 0 "") ^ "\n\n}") in ()
