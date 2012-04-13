(* File main.ml *)

let parse_file file_name =
  try 
    let inch = open_in file_name in
    (*let () = print_endline ("Opening \"" ^ file_name ^ "\"") in*)
    let lexbuf = Lexing.from_channel inch in
    let lcp = lexbuf.Lexing.lex_curr_p in
    let () = lexbuf.Lexing.lex_curr_p <- { lcp with Lexing.pos_fname = file_name } in
    let result = Parser.main (Lexer.token false) lexbuf in
    let () = print_endline result in
    close_in inch;
    flush stdout;
  with
      (*   | End_of_file ->
	   let () = print_endline
           "Parse error: end of file from lexer"
      *)
    | Parsing.Parse_error ->
	print_endline ("Syntac error")
    |Sys_error msg ->
	let () = print_endline
          ("Unable to open file " ^ msg) in ()
	  
 	
let () =
  let arg = Sys.argv.(1) in parse_file arg
    

(*
let _ =
  try
    let lexbuf = Lexing.from_channel stdin in
    while true do
      let result = Parser.main Lexer.token lexbuf in
      print_int result; print_newline(); flush stdout
    done
  with Lexer.Eof ->
    exit 0
*)
