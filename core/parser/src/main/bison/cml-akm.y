
communication[result] :
  IDENTIFIER
{
  LexIdentifierToken lid = extractLexIdentifierToken($IDENTIFIER);
  $$ = new ACommunicationAction(lid.location, 
				lid, 
				new LinkedList<PCommunicationParameter>(), 
				null);
}
/* | communication DOT IDENTIFIER */
/* | communication DOT matchValue */
/* these DOT productions should be the same as the BANG productions */
| communication[before] BANG IDENTIFIER
{
  ACommunicationAction comAction = (ACommunicationAction)$before;
  LexNameToken name = extractLexNameToken($IDENTIFIER);
  //PExp exp = new ANameExp(name.location,name);
  PExp exp = new AVariableExp(name.location,name,"");
  LexLocation location = extractLexLocation((CmlLexeme)$BANG,exp.getLocation());
  comAction.getCommunicationParameters().add(new AWriteCommunicationParameter(location, 
									      exp));
}
| communication[before] BANG matchValue
{
  try{
    ACommunicationAction comAction = (ACommunicationAction)$before;
    PExp exp = ConvertUtil.convertPatternToExp((PPattern)$matchValue);
    LexLocation location = extractLexLocation((CmlLexeme)$BANG,exp.getLocation());
    comAction.getCommunicationParameters().add(new AWriteCommunicationParameter(location, exp));
  } catch(PathConvertException e) {
    e.printStackTrace();
  }
}
| communication[before] QUESTION pattern
{
  try{
    ACommunicationAction comAction = (ACommunicationAction)$before;
    PExp exp = null;
    PParameter parameter = ConvertUtil.convertPatternToPParameter((PPattern)$pattern);
    LexLocation location = extractLexLocation((CmlLexeme)$QUESTION,parameter.getLocation());
    AReadCommunicationParameter param = new AReadCommunicationParameter(location,  parameter,  exp);
    comAction.getCommunicationParameters().add(param);
    $$ = comAction;
  } catch(PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
}
| communication[before] QUESTION setBind
{
  try{
    ACommunicationAction comAction = (ACommunicationAction)$before;
    ASetBind setbind = (ASetBind)$setBind;
    PParameter parameter = ConvertUtil.convertPatternToPParameter(setbind.getPattern());
    LexLocation location = extractLexLocation((CmlLexeme)$QUESTION,setbind.getLocation());
    AReadCommunicationParameter param = new AReadCommunicationParameter(location,  parameter, (PExp)setbind.getSet());
    comAction.getCommunicationParameters().add(param);
    $$ = comAction;
  } catch(PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }    
}
;

path[result] :
  path DOT unit
{
  Path path = (Path)$1;
  Unit unit = (Unit)$3;
  LexLocation location = extractLexLocation(path.location,unit.location);
  $$ = new Path(location,Path.PathKind.DOT,path,unit);
}
| path BACKTICK unit
{
  Path path = (Path)$1;
  Unit unit = (Unit)$3;
  LexLocation location = extractLexLocation(path.location,unit.location);
  $$ = new Path(location,Path.PathKind.BACKTICK,path,unit);
}
| path DOTHASH NUMERAL
{
  CmlLexeme lexeme = (CmlLexeme)$NUMERAL;
  Path path = (Path)$1;
  LexLocation location = extractLexLocation(path.location,(CmlLexeme)$NUMERAL);
  $$ = new Path(location,Path.PathKind.DOTHASH,path,Integer.decode(lexeme.getValue()));
}
| path LRPAREN
{
  Path path = (Path)$1;
  LexLocation location = extractLexLocation(path.location,(CmlLexeme)$LRPAREN);
  $$ = new Path(location,Path.PathKind.APPLY,path);
}
| path LPAREN expressionList RPAREN
{
  Path path = (Path)$1;
  LexLocation location = extractLexLocation(path.location,(CmlLexeme)$RPAREN);
  $$ = new Path(location,Path.PathKind.APPLY,path,(List<PExp>)$expressionList);
}
| path LPAREN expression ELLIPSIS expression RPAREN
{
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)$3);
  exps.add((PExp)$5);
  Path path = (Path)$1;
  LexLocation location = extractLexLocation(path.location,(CmlLexeme)$RPAREN);
  $$ = new Path(location,Path.PathKind.SEQRANGE,path,exps);
}
/* Bits for CSP renaming (IDENTIFIER DOT IDENTIFIER is above as path DOT unit) */
/* channel name expression bits */
| path DOT matchValue 
{
    Path path = (Path)$1; 
    PPattern pattern = (PPattern)$3;
    LexLocation location = extractLexLocation(path.location,pattern.getLocation());
    $$ = new Path(location,Path.PathKind.DOT_MATCHVALUE,path, pattern);
}
/* channel name expression bits end*/
;



