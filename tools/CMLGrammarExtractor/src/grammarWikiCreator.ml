let rec to_wiki_pages (defs : Ast.grammarDef list) =
  match defs with
    | [] -> ()
    | hd::tl -> 
      begin 
	to_wiki_page hd;
	to_wiki_pages tl
      end

and to_wiki_page def =
  begin
    print_endline def.Ast.name;
    let fileName = Str.global_replace (Str.regexp " ") "_" def.Ast.name in
    let outChan = open_out (fileName ^".rule") in
    output_string outChan ("== " ^def.Ast.name ^ " ==");
    output_string outChan ("\n;[[" ^ def.Ast.name ^ "]] <nowiki>:=</nowiki> \n" ^ (elems_to_wiki_string def.Ast.defs)); 
    output_string outChan "\n\n<pre>";
    output_string outChan (to_latex_string def);
    output_string outChan "\n</pre>";
    output_string outChan "\n\n[[Category:Rule]]";
    close_out outChan;
  end
and elems_to_wiki_string elems = 
  List.fold_left 
    ( fun acc elem -> 
      let combString =
	(match elem.Ast.comb with
	  | None -> ""
	  | Some comb ->
	    (match comb with
	      | Ast.Concat -> " <nowiki>,</nowiki> " 
	      | Ast.Alt -> "\n;| "
	      | Ast.NA -> " <nowiki>,</nowiki> "
	    )
	)
      in
      acc ^ 
	(match elem.Ast.rule_desc with
	  | Ast.Lit s ->
	    "'''" ^ s ^ "'''"
	  | Ast.Seq (elems',_) ->
	    "{" ^ (elems_to_wiki_string elems') ^ "}"
	  | Ast.Opt elems' -> 
	    "''[''" ^ (elems_to_wiki_string elems') ^ "'']''"
	  | Ast.Ref s ->
	    let s = Str.global_replace (Str.regexp "['\n''\r']") "" s in
	    "[[" ^ s ^ "]]"
	) ^ combString
    )
    ""
    elems
and to_latex_string def = 
  "\n\\RuleDef{" ^ def.Ast.name ^ "}{\n" ^ elems_to_latex_string def.Ast.defs ^ "\n}"
    
and elems_to_latex_string elems = 
  List.fold_left 
    ( fun acc elem -> 
      let combString =
	(match elem.Ast.comb with
	  | None -> ""
	  | Some comb ->
	    (match comb with
	      | Ast.Concat -> ", " 
	      | Ast.Alt -> "\n\\Alt\\ "
	      | Ast.NA -> ", "
	    )
	)
      in
      acc ^ 
	(match elem.Ast.rule_desc with
	  | Ast.Lit s ->
	    "\\Literal{" ^ s ^ "}"
	  | Ast.Seq (elems',_) ->
	    "\\Sequence{" ^ (elems_to_latex_string elems') ^ "}"
	  | Ast.Opt elems' -> 
	    "\n\\Optional{\n" ^ (elems_to_latex_string elems') ^ "\n}"
	  | Ast.Ref s ->
	    "\\RuleRef{" ^ s ^ "}"
	) ^ combString
    )
    ""
    elems

let makeParse ast = to_wiki_pages ast
