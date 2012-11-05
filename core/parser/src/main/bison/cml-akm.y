
/* DEVIATION
 * PATH
 * CML_0:
 *   IDENTIFIER { COMMA IDENTIFIER } ':' expression
 * here:
 *   pathList 'in set' expression
 *
 * Likely to appear in CML_1; discussed by Joey, Alvaro; Skype 30 July 2012
 */
singleExpressionDeclaration :
  pathList INSET expression
{
  List<LexIdentifierToken> identifiers = convertPathListToIdentifiers((List<LexNameToken>)$pathList);
  PExp exp = (PExp)$expression;
  LexLocation location = combineLexLocation(extractFirstLexLocation(identifiers),
                                            exp.getLocation());
  $$ = new AExpressionSingleDeclaration(location,
                                        NameScope.LOCAL,
                                        identifiers,
                                        exp);
}
;

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
	comAction.getCommunicationParameters().add(new AWriteCommunicationParameter(location, 
										    exp));
    }
    catch(PathConvertException e) {
	e.printStackTrace();
	System.exit(-4);
    }
}
| communication[before] QUESTION pattern
{
    try{

    ACommunicationAction comAction = (ACommunicationAction)$before;
    PExp exp = null;
    PParameter parameter = ConvertUtil.convertPatternToPParameter((PPattern)$pattern);
    LexLocation location = extractLexLocation((CmlLexeme)$QUESTION,parameter.getLocation());
    AReadCommunicationParameter param = new AReadCommunicationParameter(location, 
									parameter, 
									exp);
    comAction.getCommunicationParameters().add(param);
    $$ = comAction;
    }
    catch(PathConvertException e) {
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
	AReadCommunicationParameter param = new AReadCommunicationParameter(location, 
									    parameter, 
									    (PExp)setbind.getSet());
	comAction.getCommunicationParameters().add(param);
	$$ = comAction;
    }
    catch(PathConvertException e) {
	e.printStackTrace();
	System.exit(-4);
    }
    
}
;

/* HERE ---- above: to fix later; below: still to merge ---- */






textLiteral :
 STRING
 {
     String value = ((CmlLexeme)$1).getValue();
     LexLocation loc = extractLexLocation((CmlLexeme)$STRING);
     $$ = new LexStringToken(value.substring(1, value.length()-2), loc);
 }
;

quoteLiteral :
  QUOTE_LITERAL
{
  CmlLexeme id = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation((CmlLexeme)$1);
  String value = id.getValue();
  $$ = new LexQuoteToken(value.substring(1, value.length()-2), loc);
}
;

/* symbolic literals end*/

localDefList :
  localDef
{
  List<PDefinition> res = new LinkedList<PDefinition>();
  res.add((PDefinition)$1);
  $$ = res;
}
| localDefList COMMA localDef
{
  PDefinition def = (PDefinition)$3;
  List<PDefinition> defs = (List<PDefinition>)$1;
  defs.add(def);
  $$ = defs;
}
;

localDef :
  valueDef
{
  $$ = $1;
}
| explicitFunctionDef
{
  $$ = $1;
}
;

ifExpr :
  IF expression THEN expression elseExprs
{
  CmlLexeme _if = (CmlLexeme)$1;
  Position sif = _if.getStartPos();
  Position eif = _if.getEndPos();
  PExp test = (PExp)$2;
  PExp then = (PExp)$4;
  List<PExp> elses = (List<PExp>)$5;
  AIfExp ifexp = new AIfExp();
  List<AElseIfExp> elseifs = new LinkedList<AElseIfExp>();
  for(PExp exp : elses)
    if (exp instanceof AElseIfExp) 
      elseifs.add((AElseIfExp)exp);
    else
      ifexp.setElse(exp);


  LexLocation  sifloc = new LexLocation(currentSource.toString(),
                                        "DEFAULT",
                                        sif.line, sif.column,
                                        sif.line, eif.column,
                                        sif.offset, eif.offset);
  ifexp.setTest(test);
  ifexp.setThen(then);
  ifexp.setElseList(elseifs);
  ifexp.setLocation(
                    combineLexLocation(sifloc,
                                       extractLastLexLocation(elses)));
  $$ = ifexp;
}
;

elseExprs :
  ELSE expression
{
  CmlLexeme elsetok = (CmlLexeme)$1;
  PExp exp = (PExp)$2;
  List<PExp> res = new LinkedList<PExp>();
  res.add(exp);
  $$ = res;
}
| ELSEIF expression THEN expression elseExprs
{
  CmlLexeme elseif = (CmlLexeme)$1;
  PExp elseIf = (PExp)$2;
  PExp then = (PExp)$4;
  List<PExp> tail = (List<PExp>)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(elseif), then.getLocation());
  AElseIfExp eie = new AElseIfExp();
  eie.setElseIf(elseIf);
  eie.setThen(then);
  eie.setLocation(loc);
  tail.add(eie);
  $$ = tail;
}
;

casesExpr :
  CASES expression COLON casesExprAltList END
{
  CmlLexeme cases = (CmlLexeme)$CASES;
  PExp exp = (PExp)$expression;
  ACasesExp bubbleUp = (ACasesExp)$casesExprAltList;
  CmlLexeme end = (CmlLexeme)$END;
  LexLocation lexLoc = combineLexLocation(extractLexLocation(cases), extractLexLocation(end));
  bubbleUp.setExpression(exp);
  bubbleUp.setLocation(lexLoc);
  $$ = bubbleUp;
}
| CASES expression COLON casesExprAltList COMMA OTHERS RARROW expression END
{
  CmlLexeme cases = (CmlLexeme)$CASES;
  PExp exp = (PExp)$2;
  ACasesExp bubbleUp = (ACasesExp)$casesExprAltList; // Others and Cases are taken care of
  CmlLexeme end = (CmlLexeme)$END;
  LexLocation lexLoc = combineLexLocation(extractLexLocation(cases), extractLexLocation(end));
  bubbleUp.setExpression(exp);
  bubbleUp.setLocation(lexLoc);
  bubbleUp.setOthers((PExp)$8);
  $$ = bubbleUp;
}
;

casesExprAltList :
  casesExprAlt
{
  ACasesExp casesExp = new ACasesExp();
  ACaseAlternative caseAlt = (ACaseAlternative)$1;
  casesExp.getCases().add(caseAlt);
  $$ = casesExp;
}
| casesExprAltList COMMA casesExprAlt
{
  ACasesExp casesExp = (ACasesExp)$1;
  ACaseAlternative altExp = (ACaseAlternative)$casesExprAlt;
  casesExp.getCases().add(altExp);
  $$ = casesExp;
}
;

casesExprAlt :
  patternList RARROW expression
{
  List<PPattern> patList = (List<PPattern>)$1;
  PExp exp = (PExp)$expression;
  LexLocation leftMost = extractLexLeftMostFromPatterns(patList);
  LexLocation loc = combineLexLocation(leftMost, exp.getLocation());
  ACaseAlternative res = new ACaseAlternative();
  res.setPattern(patList);
  res.setLocation(loc);
  res.setCexp(exp);
  $$ = res;
}
;

unaryExpr :
  PLUS expression %prec U-PLUS
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AUnaryPlusUnaryExp(location, exp);
}
| MINUS expression %prec U-MINUS
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AUnaryMinusUnaryExp(location, exp);
}
| ABS expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AAbsoluteUnaryExp(location, exp);
}
| FLOOR expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AFloorUnaryExp(location, exp);
}
| NOT expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ANotUnaryExp(location, exp);
}
| CARD expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ACardinalityUnaryExp(location, exp);
}
| POWER expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new APowerSetUnaryExp(location, exp);
}
| DUNION expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ADistUnionUnaryExp(location, exp);
}
| DINTER expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ADistIntersectUnaryExp(location, exp);
}
| HD expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AHeadUnaryExp(location, exp);
}
| TL expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ATailUnaryExp(location, exp);
}
| LEN expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ALenUnaryExp(location, exp);
}
| ELEMS expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AElementsUnaryExp(location, exp);
}
| INDS expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AIndicesUnaryExp(location, exp);
}
| REVERSE expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AReverseUnaryExp(location, exp);
}
| CONC expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ADistConcatUnaryExp(location, exp);
}
| DOM expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AMapDomainUnaryExp(location, exp);
}
| RNG expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AMapRangeUnaryExp(location, exp);
}
| MERGE expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new ADistMergeUnaryExp(location, exp);
}
| INVERSE expression
{
  PExp exp = (PExp)$2;
  LexLocation opLocation = extractLexLocation((CmlLexeme)$1);
  LexLocation location = combineLexLocation(opLocation, exp.getLocation());
  $$ = new AMapInverseUnaryExp(location, exp);
}
;

binaryExpr :
  expression PLUS expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new APlusNumericBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression STAR expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ATimesNumericBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression MINUS expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ASubstractNumericBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression DIV expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ADivideNumericBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression SLASH expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ADivNumericBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression REM expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ARemNumericBinaryExp(loc, (PExp)$1, null, (PExp)$3);
}
| expression MOD expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AModNumericBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression LT expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ALessNumericBinaryExp(loc, (PExp)$1,tok, (PExp)$3);
}
| expression LTE expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ALessEqualNumericBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression GT expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AGreaterNumericBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression GTE expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AGreaterEqualNumericBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression EQUALS expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AEqualsBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression NEQ expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ANotEqualBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression OR expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AOrBooleanBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression AND expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AAndBooleanBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression EQRARROW expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AImpliesBooleanBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression LTEQUALSGT expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AEquivalentBooleanBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression INSET expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AInSetBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression NOTINSET expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ANotInSetBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression SUBSET expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ASubsetBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression PSUBSET expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AProperSubsetBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression UNION expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ASetUnionBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression BACKSLASH expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ASetDifferenceBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression INTER expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ASetIntersectBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression CARET expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ASeqConcatBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression DPLUS expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new APlusPlusBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression MUNION expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AMapUnionBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression LTCOLON expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ADomainResToBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression LTDASHCOLON expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ADomainResByBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression COLONGT expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new APlusPlusBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression COLONDASHGT expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new APlusPlusBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression COMP expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new ACompBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
| expression DSTAR expression
{
  LexLocation loc = combineLexLocation(((PExp)$1).getLocation(), ((PExp)$3).getLocation());
  LexToken tok = extractLexToken( (CmlLexeme) $2 );
  $$ = new AStarStarBinaryExp(loc, (PExp)$1, tok, (PExp)$3);
}
;

mapletList :
  maplet
{
  List<AMapletExp> res = new LinkedList<AMapletExp>();
  res.add((AMapletExp)$1);
  $$ = res;
}
| mapletList COMMA maplet
{
  List<AMapletExp> maplets = (List<AMapletExp>)$1;
  AMapletExp hd = (AMapletExp)$3;
  maplets.add(hd);
  $$ = maplets;
}
;

maplet :
  expression BARRARROW expression
{
  PExp domValue = (PExp)$1;
  PExp rngValue = (PExp)$3;
  LexLocation loc = combineLexLocation(domValue.getLocation(), rngValue.getLocation());
  AMapletExp res = new AMapletExp(loc, domValue, rngValue);
  $$ = res;
}
;

generalIsExpr :
/* DEVIATION
 * CML_0:
 *   ISUNDER name LPAREN expression RPAREN
 * here:
 *   ISUNDERNAME LPAREN expression RPAREN
 *
 */
  ISUNDERNAME LPAREN expression RPAREN
{
  CmlLexeme isUnder = (CmlLexeme)$1;
  PExp exp = (PExp)$3;
  LexLocation loc = combineLexLocation(extractLexLocation(isUnder), exp.getLocation());
  LexNameToken typeName = extractNameFromUNDERNAMEToken(isUnder);
  AIsExp res = new AIsExp(loc, typeName, exp, null);
  $$ = res;
}
| ISUNDER basicType LPAREN expression RPAREN
{
  CmlLexeme isUnder = (CmlLexeme)$1;
  PType type = (PType)$2;
  PExp test = (PExp)$4;
  CmlLexeme rparen = (CmlLexeme)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(isUnder), extractLexLocation(rparen));
  AIsExp res = new AIsExp(loc, null, test, null);
  res.setBasicType(type);
  $$ = res;
}
| ISUNDER LPAREN expression COMMA type RPAREN
{
  CmlLexeme isUnder = (CmlLexeme)$1;
  PExp test = (PExp)$3;
  PType type = (PType)$5;
  CmlLexeme rparen = (CmlLexeme)$6;
  LexLocation loc = combineLexLocation(extractLexLocation(isUnder), extractLexLocation(rparen));
  AIsExp res = new AIsExp(loc, null, test, null);
  res.setBasicType(type);
  $$ = res;
}
;

nonDeterministicAltList :
  expression RARROW action
{
  PExp guard = (PExp)$expression;
  PAction action = (PAction)$action;
  LexLocation location = combineLexLocation(guard.getLocation(),
                                            action.getLocation());
  List<ANonDeterministicAltStatementAction> alts =
    new LinkedList<ANonDeterministicAltStatementAction>();
  alts.add(new ANonDeterministicAltStatementAction(location,
                                                            guard,
                                                            action));
  $$ = alts;
}
| nonDeterministicAltList BAR expression RARROW action
{
  PExp guard = (PExp)$expression;
  PAction action = (PAction)$action;
  LexLocation location = extractLexLocation((CmlLexeme)$BAR,
                                            action.getLocation());
  List<ANonDeterministicAltStatementAction> alts =
    (List<ANonDeterministicAltStatementAction>)$1;
  alts.add(new ANonDeterministicAltStatementAction(location,
                                                            guard,
                                                            action));
  $$ = alts;
}
;


assignmentDefList :
  assignmentDef
{
  List<AAssignmentDefinition> assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)$1);
  $$ = assignmentDefs;
}
| assignmentDefList COMMA assignmentDef
{
  List<AAssignmentDefinition> assignmentDefs = (List<AAssignmentDefinition>)$1;
  if (assignmentDefs == null)
    assignmentDefs = new Vector<AAssignmentDefinition>();
  assignmentDefs.add((AAssignmentDefinition)$3);
  $$ = assignmentDefs;
}
;

assignmentDef :
  IDENTIFIER COLON type
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$IDENTIFIER);
  PType type = (PType)$type;
  LexLocation location = combineLexLocation(name.location, type.getLocation());
  AAccessSpecifierAccessSpecifier access = null;
  $$ = new AAssignmentDefinition(location,
                                 name,
                                 NameScope.GLOBAL,
                                 false,
				 null,//VDM classDef
                                 access,
                                 type,
				 null,//Pass
                                 null,
                                 null);
}
| IDENTIFIER COLON type COLONEQUALS expression
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$IDENTIFIER);
  PType type = (PType)$type;
  PExp exp = (PExp)$expression;
  LexLocation location = combineLexLocation(name.location, type.getLocation());
  AAccessSpecifierAccessSpecifier access = null;
  $$ = new AAssignmentDefinition(location, name,
                                 NameScope.GLOBAL,
                                 false,
				 null,//VDM classDef
                                 access,
                                 type,
				 null,//Pass
                                 exp,
                                 null);
}
/*(AKM)
 *FIXME: This is probably not going to work since you can't see the difference
 *       Between 'id : type in exp' and 'id : type := exp'
 *
 */
| IDENTIFIER COLON type IN expression
{
    LexNameToken name = extractLexNameToken((CmlLexeme)$IDENTIFIER);
    PType type = (PType)$type;
    PExp exp = (PExp)$expression;
    LexLocation location = combineLexLocation(name.location, exp.getLocation());
    AAccessSpecifierAccessSpecifier access = null;
    $$ = new AAssignmentDefinition(location, name,
                                   NameScope.GLOBAL,
                                   false,
				   null,//VDM classDef
                                   access,
                                   type,
				   null,//Pass
                                   exp,
                                   null);
}
;

assignStatementList :
  assignStatement
{
  List<ASingleGeneralAssignmentStatementAction> assigns =
    new LinkedList<ASingleGeneralAssignmentStatementAction>();
  assigns.add((ASingleGeneralAssignmentStatementAction)$assignStatement);
  $$ = assigns;
}
| assignStatementList[list] SEMI assignStatement
{
  List<ASingleGeneralAssignmentStatementAction> assigns =
    (List<ASingleGeneralAssignmentStatementAction>)$list;
  assigns.add((ASingleGeneralAssignmentStatementAction)$assignStatement);
  $$ = assigns;
}
;

assignStatement :
/* DEVIATION
 * PATH
 * CML_0:
 *   stateDesignator ':=' expression
 * here:
 *   path ':=' expression
 */
  path COLONEQUALS expression
{
  Path path = (Path)$path;
  PStateDesignator stateDesignator = null;
  try {
    stateDesignator = path.convertToStateDesignator();
  } catch(PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
  PExp exp = (PExp)$expression;
  LexLocation location = combineLexLocation(stateDesignator.getLocation(), exp.getLocation());
  $$ = new ASingleGeneralAssignmentStatementAction(location, stateDesignator , exp);
}
;

ifStatement :
  IF expression THEN action elseStatements ELSE action
{
  PAction action = (PAction)$7;
  $$ = new AIfStatementAction(extractLexLocation((CmlLexeme)$1, action.getLocation()),
                                     (PExp)$2, (PAction)$4, (List<? extends AElseIfStatementAction>)$5, action);
}
| IF expression THEN action ELSE action
{
  PAction action = (PAction)$6;
  $$ = new AIfStatementAction(extractLexLocation((CmlLexeme)$1, action.getLocation()),
                                     (PExp)$2, (PAction)$4, null, action);
}
;

elseStatements :
  ELSEIF expression THEN action
{
  List<AElseIfStatementAction> elseStms = new Vector<AElseIfStatementAction>();
  PAction thenStm = (PAction)$4;
  LexLocation location = extractLexLocation((CmlLexeme)$1, thenStm.getLocation());
  elseStms.add(new AElseIfStatementAction(location, (PExp)$2, thenStm));
  $$ = elseStms;
}
| elseStatements ELSEIF expression THEN action
{
  PAction action = (PAction)$5;
  List<AElseIfStatementAction> elseStms = (List<AElseIfStatementAction>)$1;
  LexLocation location = combineLexLocation(extractLastLexLocation(elseStms), extractLexLocation((CmlLexeme)$4));
  elseStms.add(0, new AElseIfStatementAction(location, (PExp)$3, action));
  $$ = elseStms;
}
;

casesStatement :
  CASES expression COLON casesStatementAltList END
{
  LexLocation location = extractLexLocation((CmlLexeme)$CASES, (CmlLexeme)$END);
  ACasesStatementAction cases = (ACasesStatementAction)$casesStatementAltList;
  cases.setLocation(location);
  cases.setExp((PExp)$expression);
  $$ = cases;
}
| CASES expression COLON casesStatementAltList COMMA OTHERS RARROW action END
{
  LexLocation location = extractLexLocation((CmlLexeme)$CASES, (CmlLexeme)$END);
  ACasesStatementAction cases = (ACasesStatementAction)$casesStatementAltList;
  cases.setLocation(location);
  cases.setExp((PExp)$expression);
  PAction others = (PAction)$action;
  cases.setOthers(others);
  $$ = cases;
}
;

casesStatementAltList :
  casesStatementAlt
{
  List<ACaseAlternativeAction> casesList = new LinkedList<ACaseAlternativeAction>();
  casesList.add((ACaseAlternativeAction)$casesStatementAlt);
  $$ = new ACasesStatementAction(null,
                                        null,
                                        casesList,
                                        null);
}
| casesStatementAltList COMMA casesStatementAlt
{
  ACasesStatementAction cases = (ACasesStatementAction)$1;
  cases.getCases().add((ACaseAlternativeAction)$casesStatementAlt);
  $$ = cases;
}
;

casesStatementAlt :
  patternList RARROW action
{
  PAction action = (PAction)$action;
  List<PPattern> patterns = (List<PPattern>)$patternList;
  $$ = new ACaseAlternativeAction(combineLexLocation(extractFirstLexLocation(patterns),
                                                     action.getLocation()),
                                  patterns,
                                  (PAction)$action);
}
;

implicitOperationBody :
  externals_opt preExpr_opt postExpr
{
  PExp postcondition = (PExp)$postExpr;
  List<? extends AExternalClause> exts = (List<? extends AExternalClause>)$externals_opt;
  /* LexLocation location = combineLexLocation(extractFirstLexLocation(exts), */
  /*                                           postcondition.getLocation()); */
  LexLocation location = postcondition.getLocation();
  $$ = new ASpecificationStatementAction(location,
                                                exts,
                                                (PExp)$preExpr_opt,
                                                postcondition);
}
;

pattern :
  patternIdentifier
{
    $$ = $1;
}
| patternLessID
{
    $$ = $1;
}
;

patternLessID :
  matchValue
{
  $$ = $1;
}
/* tuple pattern */
| MKUNDER LPAREN patternList COMMA pattern RPAREN
{

    List<PPattern> plist = (List<PPattern>)$patternList;
    plist.add((PPattern)$pattern);
    $$ = new ATuplePattern(extractLexLocation((CmlLexeme)$MKUNDER,(CmlLexeme)$RPAREN), 
			   new LinkedList<PDefinition>(), 
			   false, 
			   plist);
}
/* record patterns */
/* DEVIATION
 * PATH
 * CML_0:
 *   MKUNDER name LPAREN expression RPAREN
 * here:
 *   MKUNDERNAME LPAREN expression RPAREN
 *
 */
| MKUNDERNAME LRPAREN
{
  List<? extends PPattern> plist = null;
  LexNameToken name = extractNameFromUNDERNAMEToken((CmlLexeme)$1);
  $$ = new ARecordPattern(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$2), null, false, name, plist);
}
| MKUNDERNAME LPAREN patternList RPAREN
{
  List<? extends PPattern> plist = (List<? extends PPattern>)$3;
  LexNameToken name = extractNameFromUNDERNAMEToken((CmlLexeme)$1);
  $$ = new ARecordPattern(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$4), null, false, name, plist);
}
;

patternList :
  pattern
{
  List<PPattern> patterns = new Vector<PPattern>();
  patterns.add((PPattern)$1);
  $$ = patterns;
}
| patternList COMMA pattern
{
  List<PPattern> patterns = (Vector<PPattern>)$1;
  patterns.add((PPattern)$3);
  $$ = patterns;
}
;

patternIdentifier :
  IDENTIFIER
{
  CmlLexeme lexeme = (CmlLexeme)$1;
  LexNameToken lnt = new LexNameToken("", lexeme.getValue(), extractLexLocation(lexeme), false, true);
  AIdentifierPattern res = new AIdentifierPattern();
  res.setName(lnt);
  res.setLocation(lnt.getLocation());
  $$ = res;
}
/* "don't care" identifier */
| MINUS
{
    $$ = new AIgnorePattern(extractLexLocation((CmlLexeme)$1),
                            new LinkedList<PDefinition>(),
                            true);
}
;

matchValue :
/* symbolic literal patterns*/
  numericLiteral
{
  PPattern pattern = null;
  if($1 instanceof LexIntegerToken) {
    LexIntegerToken lit = (LexIntegerToken)$1;
    pattern = new AIntegerPattern(lit.location,
                                  new LinkedList<PDefinition>(),
                                  true,
                                  lit);
  } else {
    LexRealToken lit = (LexRealToken)$1;
    pattern = new ARealPattern(lit.location,
                               new LinkedList<PDefinition>(),
                               true,
                               lit);
  }
  $$ = pattern;
}
| booleanLiteral
{
  LexBooleanToken lit = (LexBooleanToken)$1;
  $$ = new ABooleanPattern(lit.location,
                           new LinkedList<PDefinition>(),
                           true,
                           lit);
}
| nilLiteral
{
  LexKeywordToken tok = (LexKeywordToken)$1;
  $$ = new ANilPattern(tok.location,
                       new LinkedList<PDefinition>(),
                       true);
}
| characterLiteral
{
  LexCharacterToken token = (LexCharacterToken)$characterLiteral;
  $$ = new ACharacterPattern(token.location,
                             new LinkedList<PDefinition>(),
                             true,
                             token);
}
| textLiteral
{
  LexStringToken value = (LexStringToken)$textLiteral;
  $$ = new AStringPattern(value.location,
                          new LinkedList<PDefinition>(),
                          true,
                          value);
}
| quoteLiteral
{
  LexQuoteToken value = (LexQuoteToken)$1;
  $$ = new AQuotePattern(value.location,
                         new LinkedList<PDefinition>(),
                         true,
                         value);
}
| LPAREN expression RPAREN
{
  $$ = new AExpressionPattern(extractLexLocation((CmlLexeme)$LPAREN,(CmlLexeme)$RPAREN),
                              new LinkedList<PDefinition>(),
                              false,
                              (PExp)$expression);
}
;

bind :
  setBind
{
  $$ = $1;
}
| typeBind
{
  $$ = $1;
}
;

setBind :
  pattern INSET expression
{
  PPattern pattern = (PPattern)$1;
  PExp exp = (PExp)$3;
  LexLocation location = extractLexLocation((CmlLexeme)$2);
  $$ = new ASetBind(location, pattern, exp);
}
;

typeBind :
  pattern COLON type
{
  PPattern pattern = (PPattern)$1;
  PType type = (PType)$3;
  LexLocation location = extractLexLocation((CmlLexeme)$2);
  $$ = new ATypeBind(location, pattern, type);
}
;

bindList :
  multipleBind
{
  List<PMultipleBind> binds = new Vector<PMultipleBind>();
  binds.add((PMultipleBind)$1);
  $$ = binds;
}
| bindList COMMA multipleBind
{
  List<PMultipleBind> binds = (List<PMultipleBind>)$1;
  binds.add((PMultipleBind)$3);
  $$ = binds;
}
;

multipleBind :
  multipleSetBind
{
    $$ = $1;
}
| multipleTypeBind
{
    $$ = $1;
}
;

multipleSetBind :
  patternList INSET expression
{
  List<PPattern> patterns = (List<PPattern>)$1;
  PExp exp = (PExp)$3;
  LexLocation location = extractLexLocation((CmlLexeme)$2);;
  $$ = new ASetMultipleBind(location, patterns, exp);
}
;

multipleTypeBind :
  patternList COLON type
{
  List<PPattern> patterns = (List<PPattern>)$1;
  PType type = (PType)$3;
  LexLocation location = extractLexLocation((CmlLexeme)$2);;
  $$ = new ATypeMultipleBind(location, patterns, type);
}
;

typeBindList :
  typeBind
{
  ATypeBind tb = (ATypeBind)$1;
  List<ATypeBind> res = new LinkedList<ATypeBind>();
  res.add(tb);
  $$ = res;
}
| typeBindList COMMA typeBind
{
  ATypeBind hd = (ATypeBind)$3;
  List<ATypeBind> tbl = (List<ATypeBind>)$1;
  tbl.add(hd);
  $$ = tbl;
}
;

/* New path-based naming scheme, to replace *Designators and names ---
 * using this requires the use of a conversion function to take one of
 * these and generate the VDM-compatible bit of AST
 * -jwc/2012/06/20
 */
path[result] :
  unit
{
  $$ = new Path((Unit)$1);
}
| path TILDE
{
  Path path = (Path)$1;
  LexLocation location = extractLexLocation(path.location,(CmlLexeme)$TILDE);
  $$ = new Path(location,Path.PathKind.TILDE,path);
}
| path DOT unit
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

unit :
  SELF
{
  $$ = new Unit(Unit.UnitKind.SELF,
                extractLexIdentifierToken((CmlLexeme)$1));
}
| IDENTIFIER
{
  $$ = new Unit(Unit.UnitKind.IDENTIFIER,
                extractLexIdentifierToken((CmlLexeme)$1));
}
;

pathList :
  path
{
  try {
    LexNameToken lnt = ((Path)$path).convertToName();
    List<LexNameToken> names = new LinkedList<LexNameToken>();
    names.add(lnt);
    $$ = names;
  } catch(PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
}
| pathList COMMA path
{
  try {
    LexNameToken lnt = ((Path)$path).convertToName();
    List<LexNameToken> names = (List<LexNameToken>)$1;
    names.add(lnt);
    $$ = names;
  } catch(PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
}
;

// **********************
// *** END OF GRAMMAR ***
// **********************
