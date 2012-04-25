type grammarCombinator = Concat | Alt | NA

type grammarElement = {comb : grammarCombinator option ; rule_desc :rule_type}
and rule_type =
  | Lit of string
  | Seq of (grammarElement list * string option)
  | Opt of grammarElement list
  | Ref of string
       
type grammarDef = { name : string ; defs : grammarElement list }
 
