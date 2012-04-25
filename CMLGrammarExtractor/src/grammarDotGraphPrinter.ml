let rec to_dotty_string (defs : Ast.grammarDef list) (out : string) : string =
   match defs with
     | [] -> out
     | hd::tl -> to_dotty_string tl (out ^ (process_elems ("\""^hd.Ast.name^"\"") hd.Ast.defs false ""))
 and process_elems rulename elems optional out =
   match elems with
     | [] -> out
     | hd::tl -> 
       ( match hd.Ast.rule_desc with
	 | Ast.Ref s -> 
	   process_elems rulename tl optional (out ^ "\n" ^ 
						 (rulename ^ "-> \"" ^ s ^ "\"" ^
						    if optional then " [style=dotted]" else "" 
						    ^ ";")) 
	 | Ast.Lit s -> 
	   process_elems rulename tl optional (out ^ "\n" ^ (rulename ^ "-> \"'" ^ s ^ "'\"" ^
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
	   process_elems rulename tl optional out'
	 | Ast.Opt els ->
	   let out' = process_elems rulename els true "" in
	   process_elems rulename tl false out ^ out'
	   
       )

let makeParse ast = 
  let () = print_endline ("digraph CMLGrammar {\n" ^ (to_dotty_string ast "") ^ "\n\n}") in ()
