let rec to_csv_string (defs : Ast.grammarDef list) (out : string) : string =
   match defs with
     | [] -> out
     | hd::tl -> to_csv_string tl (out ^"\n"^ hd.Ast.name ^ "\nå" ^ (process_elems hd.Ast.defs ""))
 and process_elems elems out =
   match elems with
     | [] -> out
     | hd::tl -> 
       let out' =
       ( match hd.Ast.rule_desc with
	 | Ast.Ref s -> 
	   let s' = Str.global_replace (Str.regexp "['\n''\r']") "" s in
	   out ^ s'
	 | Ast.Lit s -> out ^ " '" ^ s ^ "' " 
	 | Ast.Opt elems -> 
	   let s = process_elems elems "" in
	   out ^ " [" ^ s ^ "] " 
	 | Ast.Seq (elems,opt)-> 
	   let s = process_elems elems "" in
	   out ^ " {" ^ s ^ "} " 
       ) in
       let out'' = 
       (match hd.Ast.comb with
	 | None -> out'
	 | Some x -> 
	   (
	     match x with 
	       | Ast.Alt -> out' ^ "\nå "
	       | Ast.Concat -> out' ^ " , "
	       | Ast.NA -> out'
	   )
       ) 
       in
       process_elems tl out''



let makeParse ast = to_csv_string ast ""
