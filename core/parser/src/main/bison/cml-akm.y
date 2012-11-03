
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

singleTypeDeclaration :
/* DEVIATION
 * PATH
 * grammar:
 *   identifierList
 * here:
 *   pathList
 */
  pathList COLON type
{
  List<LexNameToken> nameList = (List<LexNameToken>)$1;
  List<LexIdentifierToken> ids = convertNameListToIdentifierList(nameList);
  ATypeSingleDeclaration singleTypeDeclaration =
    new ATypeSingleDeclaration(nameList.get(0).getLocation(), NameScope.LOCAL, ids, (PType)$3);
  $$ = singleTypeDeclaration;
}
;

chansetDefinitionParagraph :
  CHANSETS
{
  LexLocation loc = extractLexLocation((CmlLexeme)$CHANSETS);
  //AAccessSpecifierAccessSpecifier access = new AAccessSpecifierAccessSpecifier(new APublicAccess(), new TStatic(), new TAsync(),loc);
  AAccessSpecifierAccessSpecifier access = new AAccessSpecifierAccessSpecifier(new APublicAccess(), new TStatic(), new TAsync());
  AChansetParagraphDefinition chansetParagraph = new AChansetParagraphDefinition(loc, NameScope.GLOBAL, false, access, null/*Pass*/,new LinkedList<AChansetDefinition>() );
  $$ = chansetParagraph;
}
| CHANSETS chansetDefinitionList
{
  LexLocation loc = extractLexLocation((CmlLexeme)$CHANSETS);
  List<AChansetDefinition> chansetDefinitions = (List<AChansetDefinition>)$2;
  //AAccessSpecifierAccessSpecifier access = new AAccessSpecifierAccessSpecifier(new APublicAccess(), new TStatic(), new TAsync(),loc);
  AAccessSpecifierAccessSpecifier access = new AAccessSpecifierAccessSpecifier(new APublicAccess(), new TStatic(), new TAsync());
  AChansetParagraphDefinition chansetParagraph = new AChansetParagraphDefinition(loc, NameScope.GLOBAL, false, access,null/*Pass*/, chansetDefinitions);
  $$ = chansetParagraph;
 }
| CHANSETS chansetDefinitionList SEMI
{
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$CHANSETS),
                                       extractLexLocation((CmlLexeme)$SEMI));
  List<AChansetDefinition> chansetDefinitions = (List<AChansetDefinition>)$2;
  //AAccessSpecifierAccessSpecifier access = new AAccessSpecifierAccessSpecifier(new APublicAccess(), new TStatic(), new TAsync(),loc);
  AAccessSpecifierAccessSpecifier access = new AAccessSpecifierAccessSpecifier(new APublicAccess(), new TStatic(), new TAsync());
  
  AChansetParagraphDefinition chansetParagraph = new AChansetParagraphDefinition(loc, NameScope.GLOBAL, false, access,null/*Pass*/, chansetDefinitions);
  $$ = chansetParagraph;
 }
;

/* DEVIATION
 * chanset declarations are now separated by SEMIs, just like everything else.
 */
chansetDefinitionList :
  chansetDefinition
{
  List<AChansetDefinition> defs = new Vector<AChansetDefinition>();
  defs.add((AChansetDefinition)$chansetDefinition);
  $$ = defs;
}
| chansetDefinitionList[list] SEMI chansetDefinition
{
  List<AChansetDefinition> defs = (List<AChansetDefinition>)$list;
  defs.add((AChansetDefinition)$chansetDefinition);
  $$ = defs;
}
;

chansetDefinition :
/* CHANSET
 * expression was chansetExpr here
 */
  IDENTIFIER EQUALS expression
{
  LexIdentifierToken idToken = extractLexIdentifierToken((CmlLexeme)$1);
  PExp chansetExp = (PExp)$3;
  LexLocation location = combineLexLocation(idToken.getLocation(), chansetExp.getLocation());
  $$ = new AChansetDefinition(location, 
			      NameScope.GLOBAL, 
			      false/*used_*/, 
			      null/*AAccessSpecifierAccessSpecifierAccessSpecifier access_*/,  
			      null/*Pass*/,
			      idToken, 
			      chansetExp);
}
;

globalDefinitionParagraph :
  typeDefs
{
  $$ = $1;
}
| valueDefs
{
  $$ = $1;
}
| functionDefs
{
  $$ = $1;
}
;

classDefinitionBlock :
  classDefinitionBlockAlternative
{
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)$1);
  //List<PDefinition> defs = (List<PDefinition>)$1;
  $$ = defs;
}
| classDefinitionBlockAlternative classDefinitionBlock
{
  List<PDefinition> defs = (List<PDefinition>) $2;
  PDefinition newDefs = (PDefinition)$1;
  defs.add(newDefs);
  $$ = defs;
}
;

classDefinitionBlockAlternative :
  typeDefs
{
  $$ = $1;
}
| valueDefs
{
  $$ = $1;
}
| functionDefs
{
  $$ = $1;
}
| operationDefs
{
  $$ = $1;
}
| stateDefs
{
  $$ = $1;
}
/* UPCOMING --- CML_1
 * absent in CML_0
 *
 * This will be in the CML_1 grammar, and is defines the constructor for the class.
 * Confirmed between Joey, Alavro; Skype 30 July 2012
 */
| INITIAL operationDef
{
    PDefinition def = (PDefinition)$operationDef;
    LexLocation location = extractLexLocation((CmlLexeme)$INITIAL,def.getLocation());
    $$ = new AInitialParagraphDefinition(location,
                                         NameScope.GLOBAL,
                                         true,
                                         getDefaultAccessSpecifier(false,false,null),
					 null/*Pass*/,
                                         def);
}
| INITIAL operationDef SEMI
{
    PDefinition def = (PDefinition)$operationDef;
    LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$INITIAL),
                                              extractLexLocation((CmlLexeme)$SEMI));
    $$ = new AInitialParagraphDefinition(location,
                                         NameScope.GLOBAL,
                                         true,
                                         getDefaultAccessSpecifier(false,false,null),
					 null/*Pass*/,
                                         def);
}
;

typeDefs :
  TYPES
{
  LexLocation loc = extractLexLocation((CmlLexeme)$TYPES);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  $$ = new ATypesParagraphDefinition( loc, NameScope.LOCAL, false, access,null/*Pass*/, null);
}
| TYPES typeDefList
{
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)$typeDefList;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$TYPES),
                                       extractLastLexLocation(typeDefinitions));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  $$ = new ATypesParagraphDefinition( loc, NameScope.LOCAL, false, access,null/*Pass*/, typeDefinitions);
}
| TYPES typeDefList SEMI
{
  List<ATypeDefinition> typeDefinitions = (List<ATypeDefinition>)$typeDefList;
  LexLocation loc = combineLexLocation(extractLexLocation((CmlLexeme)$TYPES),
                                       extractLexLocation((CmlLexeme)$SEMI));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, loc);
  $$ = new ATypesParagraphDefinition( loc, NameScope.LOCAL, false, access,null/*Pass*/, typeDefinitions);
}
;

typeDefList :
  typeDef
{
  List<ATypeDefinition> list = new Vector<ATypeDefinition>();
  list.add((ATypeDefinition)$typeDef);
  $$ = list;
}
| typeDefList[list] SEMI typeDef
{
  List<ATypeDefinition> list = (List<ATypeDefinition>)$list;
  list.add((ATypeDefinition)$typeDef);
  $$ = list;
}
;

typeDef :
  qualifier IDENTIFIER EQUALS type invariant
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  AInvariantDefinition inv = (AInvariantDefinition)$5;
  //SInvariantType inv = (SInvariantType)$5;
  LexLocation location = null;
  /* if (access.getLocation() != null) { */
  /*   location = combineLexLocation(access.getLocation(), inv.getLocation()); */
  /* } else { */
  location = combineLexLocation(name.getLocation(), inv.getLocation());
  /* } */
  ATypeDefinition typeDef = new ATypeDefinition(location,
						NameScope.TYPENAME,
                                                false/*Boolean used_*/,
                                                null/*VDM ClassDef*/,
						access,
                                                (PType)$type,
						null/*Pass*/,
                                                null/*SInvariantType invType_*/,
                                                inv.getPattern()/*PPattern invPattern_*/,
                                                inv.getExpression()/*PExp invExpression_*/,
                                                null /*AExplicitFunctionDefinition invdef_*/,
                                                false/*Boolean infinite_*/,
						name);
  $$ = typeDef;
}
| qualifier IDENTIFIER EQUALS type
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  LexLocation location = null;
  /* if (access.getLocation() != null) { */
  /*   location = combineLexLocation(access.getLocation(), ((PType)$type).getLocation()); */
  /* } else { */
  location = combineLexLocation(name.getLocation(), ((PType)$type).getLocation());
  /* } */
  $$ = new ATypeDefinition(location,
			   NameScope.TYPENAME,
                           false/*Boolean used_*/,
			   null/*VDM ClassDef*/,
                           access,
                           (PType)$type,
			   null/*Pass*/,
                           null/*SInvariantType invType_*/,
                           null/*PPattern invPattern_*/,
                           null/*PExp invExpression_*/,
                           null /*AExplicitFunctionDefinition invdef_*/,
                           false/*Boolean infinite_*/,
			   name);
}
| qualifier IDENTIFIER DCOLON fieldList
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  CmlLexeme vdmrec = (CmlLexeme)$3;
  List<AFieldField> fields = (List<AFieldField>)$4;
  LexLocation loc = null;
  /* if(access.getLocation() != null) */
  /*   loc = combineLexLocation(access.getLocation(), extractLexLocation(vdmrec)); */
  /* else */
    loc = combineLexLocation(name.getLocation(), extractLexLocation(vdmrec));
  ARecordInvariantType recType = new ARecordInvariantType(loc, false, null, false, null, name, fields, true);
  ATypeDefinition res = new ATypeDefinition(loc,
					    NameScope.GLOBAL,
                                            false,
					    null/*VDM ClassDef*/,
                                            access,
                                            recType,
					    null/*Pass*/,
                                            null,
                                            null,
                                            null,
                                            null,
                                            true,
					    name);
  $$ = res;
}
| qualifier IDENTIFIER DCOLON fieldList invariant
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  CmlLexeme vdmrec = (CmlLexeme)$3;
  List<AFieldField> fields = (List<AFieldField>)$4;
  // TODO: Added AInvariantInvariant to the ARecordInvariantType replacing
  // the current AExplicitFunctionFunctionDefinition for inv.
  LexLocation loc = null;
  /* if(access.getLocation() != null) */
  /*   loc = combineLexLocation(access.getLocation(), extractLexLocation(vdmrec)); */
  /* else */
    loc = combineLexLocation(name.getLocation(), extractLexLocation(vdmrec));
  ARecordInvariantType recType = new ARecordInvariantType(loc, false, null, false, null, name, fields, true);
  ATypeDefinition res = new ATypeDefinition(loc,
                                            
                                            NameScope.TYPENAME,
                                            false,
					    null/*VDM ClassDef*/,
                                            access,
                                            recType,
					    null/*Pass*/,
                                            null,
                                            null,
                                            null,
                                            null,
                                            true,
					    name);
  $$ = res;
}
;

/* FUTURE
 *
 * (jwc) I think for this we really ought have the lexer give the
 * token 'QUALIFIER' and then querying its value (like with numeric
 * literals) to figure out which one we have.
 */
qualifier :
  PRIVATE
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
  res.setAccess(new APrivateAccess());
  //FIXME: We should add location to the Accessspecifier
  //res.setLocation(location);
  $$ = res;
}
| PROTECTED
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
  //FIXME: We should add location to the Accessspecifier
  //res.setLocation(location);
  res.setAccess(new AProtectedAccess());
  $$ = res;
}
| PUBLIC
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  AAccessSpecifierAccessSpecifier res = new AAccessSpecifierAccessSpecifier();
  //FIXME: We should add location to the Accessspecifier
  //res.setLocation(location);
  res.setAccess(new APublicAccess());
  $$ = res;
}
/* (RWL) It is not in overture why are we having it?
 *
 * (JWC) It is in CML, however.  Jim wants it in (for perfectly
 * cromulent reasons), and it's mostly harmless.  We just need to
 * filter it (and all places where it's used) out, or flip it to
 * public/global.  See me for an explanation.
 */
| LOGICAL
{
  LexLocation location = extractLexLocation((CmlLexeme)$1);
  //$$ = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(), null, null, location);
  $$ = new AAccessSpecifierAccessSpecifier(new ALogicalAccess(), null, null);
}
| /* empty */
{
  /*Default private*/
  AAccessSpecifierAccessSpecifier a = new AAccessSpecifierAccessSpecifier();
  a.setAccess(new APrivateAccess());
  $$ = a;
}
;

type :
  LPAREN type RPAREN // bracketedType
{
  $$ = $2;
}
| basicType
{
  $$ = $1;
}
| quoteLiteral // quoteType
{
  LexQuoteToken value = (LexQuoteToken)$1;
  $$ = new AQuoteType(value.location, false, null, value);
}
| COMPOSE IDENTIFIER OF fieldList END
{
    List<AFieldField> fields = (List<AFieldField>)$fieldList;
    $$ = new ARecordInvariantType(extractLexLocation((CmlLexeme)$1,(CmlLexeme)$END),
                                  false,
				  null,//definitions
				  false,//opaque
				  null,//invdef
                                  extractLexNameToken($IDENTIFIER),
                                  (List<? extends AFieldField>)$fieldList,
				  false/*infinite_*/);
}
| type BAR type // unionType
{
  /* FIXME --- Make union type concatenation smarter
   *
   * This is technically correct as far as it goes, but it creates a
   * tree of AUnionType objects, which is rather stupid.  It should
   * check the left type to see if it already *is* an AUnionType and
   * just add the right if so; otherwise, it should do as it does now.
   */
  PType fst = (PType)$1;
  PType snd = (PType)$3;
  LexLocation loc = combineLexLocation(fst.getLocation(), snd.getLocation());
  List<PType> types = new Vector<PType>();
  types.add(fst);
  types.add(snd);
  AUnionType utype = new AUnionType(loc, false, false, false);
  utype.setTypes(types);
  $$ = utype;
}
| type STAR type %prec T-STAR //productType
{
  /* FIXME --- Make product type concatenation smarter
   *
   * This is technically correct as far as it goes, but it creates a
   * tree of AProductType objects, which is rather stupid.  It should
   * check the left type to see if it already *is* an AProductType and
   * just add the right if so; otherwise, it should do as it does now.
   */
  List<PType> types = new Vector<PType>();
  PType left = (PType)$1;
  PType right = (PType)$3;
  types.add(left);
  types.add(right);
  LexLocation location = combineLexLocation(left.getLocation(), right.getLocation());
  $$ = new AProductType(location, false, null, types);
}
| LSQUARE type RSQUARE // optionalType
{
  $$ = new  AOptionalType(extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3), false, null, (PType)$2);
}
| SETOF type
{
  CmlLexeme setof = (CmlLexeme)$1;
  PType type = (PType)$2;
  LexLocation loc = combineLexLocation(extractLexLocation(setof), type.getLocation());
  ASetType res = new ASetType(loc, false, null, type, false, false);
  $$ = res;
}
| SEQOF type
{
  CmlLexeme seqof = (CmlLexeme)$1;
  PType type = (PType)$2;
  LexLocation loc = combineLexLocation(extractLexLocation(seqof), type.getLocation());
  ASeqSeqType res = new ASeqSeqType(loc, false, null, type, false);
  $$ = res;
}
| SEQ1OF type
{
  CmlLexeme seqof = (CmlLexeme)$1;
  PType type = (PType)$2;
  LexLocation loc = combineLexLocation(extractLexLocation(seqof), type.getLocation());
  ASeq1SeqType res = new ASeq1SeqType(loc, false, null, type, false);
  $$ = res;
}
| MAPOF type TO type
{
  CmlLexeme mapof = (CmlLexeme)$1;
  PType from = (PType)$2;
  PType to   = (PType)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(mapof), to.getLocation());
  AMapMapType res = new AMapMapType(loc, false, null, from, to, false);
  $$ = res;
}
| INMAPOF type TO type
{
  CmlLexeme mapof = (CmlLexeme)$1;
  PType from = (PType)$2;
  PType to   = (PType)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(mapof), to.getLocation());
  AMapMapType res = new AMapMapType(loc, false, null, from, to, false);
  $$ = res;
}
| functionType
{
  $$ = $1;
}
/* DEVIATION (x3)
 * CML_0:
 *   name
 * here:
 *   IDENTIFIER
 *   IDENTIFIER DOT IDENTIFIER
 *   IDENTIFIER BACKTICK IDENTIFIER
 */
| IDENTIFIER
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$1);
  ANamedInvariantType type = new ANamedInvariantType();
  type.setLocation(name.getLocation());
  type.setName(name);
  $$ = type;
}
| IDENTIFIER DOT IDENTIFIER // name is defined in CML_0 as using DOT
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$3);
  name = new LexNameToken(((CmlLexeme)$1).getValue(),name.getIdentifier());
  ANamedInvariantType type = new ANamedInvariantType();
  type.setLocation(name.getLocation());
  type.setName(name);
  $$ = type;
}
| IDENTIFIER BACKTICK IDENTIFIER
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$3);
  name = new LexNameToken(((CmlLexeme)$1).getValue(),name.getIdentifier());
  ANamedInvariantType type = new ANamedInvariantType();
  type.setLocation(name.getLocation());
  type.setName(name);
  $$ = type;
}
;

basicType :
  TBOOL
{
  $$ = new ABooleanBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TNAT
{
  $$ = new ANatNumericBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TNAT1
{
  $$ = new ANatOneNumericBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TINT
{
  $$ = new AIntNumericBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TRAT
{
  $$ = new ARationalNumericBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TREAL
{
  $$ = new ARealNumericBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TCHAR
{
  $$ = new ACharBasicType(extractLexLocation((CmlLexeme)$1), false);
}
| TTOKEN
{
  $$ = new ATokenBasicType(extractLexLocation((CmlLexeme)$1), false);
}
;

functionType :
  partialFunctionType
{
  $$ = $1;
}
| totalFunctionType
{
  $$ = $1;
}
;

partialFunctionType :
  type PLUSGT type
{
  PType domType = (PType)$1;
  PType rngType = (PType)$3;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, true, params, rngType);
}
| LRPAREN PLUSGT type // discretionary type
{
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)$1), true);
  PType rngType = (PType)$3;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, true, params, rngType);
}
;

totalFunctionType :
  type RARROW type
{
  PType domType = (PType)$1;
  PType rngType = (PType)$3;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, false, params, rngType);
}
| LRPAREN RARROW type // discretionary type
{
  PType domType = new AVoidType(extractLexLocation((CmlLexeme)$1), true);
  PType rngType = (PType)$3;
  LexLocation loc = combineLexLocation(domType.getLocation(), rngType.getLocation());
  List<PType> params = new LinkedList<PType>();
  params.add(domType);
  $$ = new AFunctionType(loc, false, null, false, params, rngType);
}
;

fieldList :
  field
{
  List<AFieldField> res = new LinkedList<AFieldField>();
  res.add((AFieldField)$1);
  $$ = res;
}
| fieldList field
{
  List<AFieldField> head = (List<AFieldField>)$1;
  head.add((AFieldField)$2);
  $$ = head;
}
;

field :
  type
{
    $$ = new AFieldField(getDefaultAccessSpecifier(false,false,null),
                         null, null, (PType)$1, false);
}
| IDENTIFIER COLON type
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$1);
  PType type = (PType) $3;
  $$ = new AFieldField(getDefaultAccessSpecifier(false,false,null), name, null, type, false);
}
| IDENTIFIER COLONDASH type
{
    LexNameToken name = extractLexNameToken((CmlLexeme)$1);
    PType type = (PType) $3;
    $$ = new AFieldField(getDefaultAccessSpecifier(false,false,null),
                         name, null, type, true);

}
;

invariant :
  INV pattern DEQUALS expression
{
  LexNameToken name = null; // cannot be desided here
  CmlLexeme vdmInvLexeme = (CmlLexeme)$1;
  PExp exp = (PExp)$4;
  LexLocation loc = extractLexLocation(vdmInvLexeme, exp.getLocation());
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier( true, true, loc );
  PType type = null; // will be decided later
  $$ = new AInvariantDefinition(loc,
                                name,
                                NameScope.LOCAL,
                                false,
				null,//VDM ClassDef
                                access,
                                type,
				null,//Pass
                                (PPattern)$2,
                                exp);
}
;


/* DEVIATION
 * CML_0:
 *   'values', qualifiedValueDef, { ‘;’, qualifiedValueDef }
 * here:
 *   'values', { qualifiedValueDef, { ‘;’, qualifiedValueDef } } [ ';' ]
 */
valueDefs :
  VALUES
{
  List<PDefinition> defs = new Vector<PDefinition>();
  LexLocation location = extractLexLocation((CmlLexeme)$VALUES);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  $$ = new AValueParagraphDefinition(location,
                                     NameScope.NAMES,
                                     false,
				     access,
                                     null,//Pass
				     defs);
}
| VALUES valueDefList
{
  List<PDefinition> defs = (List<PDefinition>)$valueDefList;
  LexLocation location = extractLexLocation((CmlLexeme)$VALUES,
                                            extractLastLexLocation(defs));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  $$ = new AValueParagraphDefinition(location,
                                     NameScope.NAMES,
                                     false,
                                     access,
				     null,//Pass
                                     defs);
}
| VALUES valueDefList SEMI
{
  List<PDefinition> defs = (List<PDefinition>)$valueDefList;
  LexLocation location = extractLexLocation((CmlLexeme)$VALUES, (CmlLexeme)$SEMI);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  $$ = new AValueParagraphDefinition(location,
                                     NameScope.NAMES,
                                     false,
                                     access,
				     null,//Pass
                                     defs);
}
;

valueDefList :
  qualifiedValueDef[def]
{
  List<PDefinition> defs = new LinkedList<PDefinition>();
  defs.add((PDefinition)$def);
  $$ = defs;
}
| valueDefList[list] SEMI qualifiedValueDef[def]
{
  PDefinition def = (PDefinition)$def;
  List<PDefinition> defs = (List<PDefinition>)$list;
  defs.add(0,def);
  $$ = defs;
}
;

qualifiedValueDef :
  qualifier valueDef
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  PDefinition def = (PDefinition)$2;
  def.setAccess(access);
  $$ = def;
}
;

valueDef :
  IDENTIFIER COLON type EQUALS expression
{
  LexNameToken lnt = extractLexNameToken((CmlLexeme)$IDENTIFIER);
  PType type = (PType)$type;
  PExp expression = (PExp)$expression;
  AIdentifierPattern idp = new AIdentifierPattern();
  idp.setLocation(lnt.location);
  idp.setName(lnt);
  // Build the resulting AValueDefinition
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(idp.getLocation(), expression.getLocation()));
  vdef.setName(lnt);
  vdef.setUsed(false);
  vdef.setAccess(getPrivateAccessSpecifier(false,false,lnt.location));
  vdef.setNameScope(NameScope.LOCAL);
  $$ = vdef;
}
| patternLessID COLON type EQUALS expression
{
  PPattern pattern = (PPattern)$1;
  PType type = (PType)$3;
  PExp expression = (PExp)$5;
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(type);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(pattern.getLocation(), expression.getLocation()));
  vdef.setName(new LexNameToken("Default", "plesstypeexp",vdef.getLocation(), false, false));
  vdef.setAccess(getPrivateAccessSpecifier(false,false,vdef.getLocation()));
  vdef.setNameScope(NameScope.LOCAL);
  $$ = vdef;
}
| IDENTIFIER EQUALS expression
{
  CmlLexeme id = (CmlLexeme)$1;
  PExp expression = (PExp)$3;
  LexNameToken lnt = extractLexNameToken(id);
  AIdentifierPattern idp = new AIdentifierPattern(lnt.location, null, false, lnt, false/*constrained*/);
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(idp);
  vdef.setType(null);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(idp.getLocation(), expression.getLocation()));
  vdef.setAccess(getPrivateAccessSpecifier(false,false,new LexLocation()));
  vdef.setNameScope(NameScope.LOCAL);
  $$ = vdef;
}
| patternLessID EQUALS expression
{
  PPattern pattern = (PPattern)$1;
  PExp expression = (PExp)$3;
  AValueDefinition vdef = new AValueDefinition();
  vdef.setPattern(pattern);
  vdef.setType(null);
  vdef.setExpression(expression);
  vdef.setDefs(null);
  vdef.setLocation(combineLexLocation(pattern.getLocation(), expression.getLocation()));
  vdef.setAccess(getPrivateAccessSpecifier(false,false,new LexLocation()));
  vdef.setNameScope(NameScope.LOCAL);
  $$ = vdef;
}
;

functionDefs :
  FUNCTIONS
{
  LexLocation location = extractLexLocation((CmlLexeme)$FUNCTIONS);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  List<PDefinition> functionDefs = new LinkedList<PDefinition>();
  $$ = new AFunctionParagraphDefinition(location,
                                        NameScope.GLOBAL,
                                        false,
                                        access,
					null,//Pass
                                        functionDefs);
}
| FUNCTIONS functionDefList
{
  LexLocation location = extractLexLocation((CmlLexeme)$FUNCTIONS);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  List<PDefinition> functionDefs = (List<PDefinition>)$functionDefList;
  $$ = new AFunctionParagraphDefinition(location,
                                        NameScope.GLOBAL,
                                        false,
                                        access,
					null,//Pass
                                        functionDefs);
}
| FUNCTIONS functionDefList SEMI
{
  LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$FUNCTIONS), extractLexLocation((CmlLexeme)$SEMI));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  List<PDefinition> functionDefs = (List<PDefinition>)$functionDefList;
  $$ = new AFunctionParagraphDefinition(location,
                                        NameScope.GLOBAL,
                                        false,
                                        access,
					null,//Pass
                                        functionDefs);
}
;

functionDefList :
  functionDef
{
  List<PDefinition> functionList = new Vector<PDefinition>();
  functionList.add((PDefinition)$functionDef);
  $$ = functionList;
}
| functionDefList[list] SEMI functionDef
{
  List<PDefinition> functionList = (List<PDefinition>)$list;
  functionList.add((PDefinition)$functionDef);
  $$ = functionList;
}
;

functionDef :
  implicitFunctionDef
{
  $$ = $1;
}
| qualifiedExplicitFunctionDef
{
  $$ = $1;
}
;

implicitFunctionDef :
  qualifier IDENTIFIER parameterTypes identifierTypePairList preExpr_opt postExpr
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  List<APatternListTypePair> paramPatterns = (List<APatternListTypePair>)$3;
  List<APatternTypePair> result = (List<APatternTypePair>)$4;
  PExp preExp = (PExp)$5;
  PExp postExp = (PExp)$6;
  LexLocation location = null;
  /* if (access.getLocation() != null) { */
  /*   location = combineLexLocation(access.getLocation(), postExp.getLocation()); */
  /* } else { */
    location = combineLexLocation(name.getLocation(), postExp.getLocation());
  /* } */
  AImplicitFunctionDefinition impFunc =
    new AImplicitFunctionDefinition(location, 
				    NameScope.LOCAL, 
				    false, 
				    access, 
				    null,//Pass
				    null, 
				    paramPatterns, 
				    result, 
				    preExp, 
				    postExp,
				    null/*LexNameToken measure*/);
  impFunc.setName(name);
  $$ = impFunc;
}
;

qualifiedExplicitFunctionDef :
  qualifier explicitFunctionDef
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$qualifier;
  AExplicitFunctionDefinition f = (AExplicitFunctionDefinition)$2;
  f.setAccess(access);
  $$ = f;
}
;

explicitFunctionDef :
  IDENTIFIER COLON functionType IDENTIFIER parameterList DEQUALS functionBody preExpr_opt postExpr_opt measureExpr
{
  LexNameToken name = extractLexNameToken((CmlLexeme)$1);
  LexLocation loc = extractLexLocation((CmlLexeme)$1);
  AFunctionType ftype = (AFunctionType)$3;
  PExp functionBody = (PExp)$functionBody;
  List<List<PPattern>> args = (List<List<PPattern>>)$parameterList;
  AExplicitFunctionDefinition res = new AExplicitFunctionDefinition();
  res.setName(name);
  res.setLocation(loc);
  res.setType(ftype);
  res.setBody(functionBody);
  res.setMeasure((LexNameToken)$measureExpr);
  res.setParamPatternList(args);
  $$ = res;
}
;

/* really? this is what a VDM function definition list looks like? */
parameterList :
  LRPAREN
{
  List<List<PPattern>> patternListList = new Vector<List<PPattern>>();
  $$ = patternListList;
}
| LPAREN patternList RPAREN
{
  List<PPattern> patternList = (List<PPattern>)$2;
  List<List<PPattern>> patternListList = new Vector<List<PPattern>>();
  patternListList.add(patternList);
  $$ = patternListList;
}
| parameterList LPAREN patternList RPAREN
{
  List<PPattern> patternList = (List<PPattern>)$3;
  List<List<PPattern>> patternListList = (List<List<PPattern>>)$1;
  patternListList.add(patternList);
  $$ = patternListList;
}
;

functionBody :
  expression
{
  $$ = $1;
}
| SUBCLASSRESP
{
  $$ = new ASubclassResponsibilityExp(extractLexLocation((CmlLexeme)$1));
}
| NOTYETSPEC
{
  $$ = new ANotYetSpecifiedExp(extractLexLocation((CmlLexeme)$1));
}
;

parameterTypes :
  LRPAREN
{
  $$ = new Vector<APatternListTypePair>();
}
| LPAREN patternListTypeList RPAREN
{
  $$ = $2;
}
;

patternListTypeList :
  patternList COLON type
{
  List<PPattern> patternList = (List<PPattern>)$1;
  List<APatternListTypePair> pltpl = new Vector<APatternListTypePair>();
  pltpl.add(new APatternListTypePair(false, patternList, (PType)$3));
  $$ = pltpl;
}
| patternListTypeList COMMA patternList COLON type
{
  List<APatternListTypePair> pltpl = (List<APatternListTypePair>)$1;
  List<PPattern> patternList = (List<PPattern>)$3;
  pltpl.add(new APatternListTypePair(false, patternList, (PType)$5));
  $$ = pltpl;
}
;

identifierTypePairList_opt :
  /* empty */
{
  $$ = null;
}
| identifierTypePairList
{
  $$ = $1;
}
;

identifierTypePairList :
  IDENTIFIER COLON type
{
  AIdentifierTypePair typePair = new AIdentifierTypePair(null, 
							 extractLexIdentifierToken((CmlLexeme)$1), 
							 (PType)$3);
  List<AIdentifierTypePair> typePairs = new Vector<AIdentifierTypePair>();
  typePairs.add(typePair);
  $$ = typePairs;
}
| identifierTypePairList COMMA IDENTIFIER COLON type
{
  AIdentifierTypePair typePair = new AIdentifierTypePair(null, 
							 extractLexIdentifierToken((CmlLexeme)$3), 
							 (PType)$5);
  List<AIdentifierTypePair> typePairs = (List<AIdentifierTypePair>)$1;
  typePairs.add(typePair);
  $$ = typePairs;
}
;

preExpr_opt :
  preExpr
{
  $$ = $1;
}
| /* empty */
{
  $$ = null;
}
;

preExpr :
  PRE expression
{
  $$ = $2;
}
;

postExpr_opt :
  postExpr
{
  $$ = $1;
}
| /* empty */
{
  $$ = null;
}
;

postExpr :
  POST expression
{
  $$ = $2;
}
;

measureExpr :
/* DEVIATION
 * PATH
 * CML_0:
 *   MEASURE name
 */
MEASURE path
{
    try{
	$$ = ((Path)$path).convertToName();
    }
    catch(PathConvertException e) {
        e.printStackTrace();
        System.exit(-4);
    }
}
| /* empty */ 
{
    $$ = null;
}
;

operationDefs :
  OPERATIONS
{
  LexLocation location = extractLexLocation((CmlLexeme)$OPERATIONS);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  $$ = new AOperationParagraphDefinition(location,
                                         NameScope.LOCAL,
                                         false,
                                         access,
					 null,//Pass
                                         null);
}
| OPERATIONS operationDefList
{
  LexLocation location = extractLexLocation((CmlLexeme)$OPERATIONS);
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  List<? extends SOperationDefinition> opDefinitions = (List<? extends SOperationDefinition>)$operationDefList;
  $$ = new AOperationParagraphDefinition(location,
                                         NameScope.LOCAL,
                                         false,
                                         access,
					 null,//Pass
                                         opDefinitions);
}
| OPERATIONS operationDefList SEMI
{
  LexLocation location = combineLexLocation(extractLexLocation((CmlLexeme)$OPERATIONS), extractLexLocation((CmlLexeme)$SEMI));
  AAccessSpecifierAccessSpecifier access = getDefaultAccessSpecifier(true, false, location);
  List<? extends SOperationDefinition> opDefinitions = (List<? extends SOperationDefinition>)$operationDefList;
  $$ = new AOperationParagraphDefinition(location,
                                         NameScope.LOCAL,
                                         false,
                                         access,
					 null,//Pass
                                         opDefinitions);
}
;

operationDefList :
  operationDef
{
  List<SOperationDefinition> opDefinitions = new Vector<SOperationDefinition>();
  opDefinitions.add((SOperationDefinition)$operationDef);
  $$ = opDefinitions;
}
| operationDefList[list] SEMI operationDef
{
  List<SOperationDefinition> opDefinitions = (List<SOperationDefinition>)$list;
  opDefinitions.add((SOperationDefinition)$operationDef);
  $$ = opDefinitions;
}
;

operationDef :
  implicitOperationDef
{
  $$ = $1;
}
| explicitOperationDef
{
  $$ = $1;
}
;

explicitOperationDef :
  qualifier IDENTIFIER COLON operationType IDENTIFIER parameterList DEQUALS operationBody preExpr_opt postExpr_opt
{
  LexLocation loc = extractLexLocation((CmlLexeme)$2);
  AExplicitOperationDefinition res = new AExplicitOperationDefinition();
  res.setName((LexNameToken)extractLexNameToken($2));
  res.setLocation(loc);
  res.setIsConstructor(false);
  res.setBody((SStatementAction)$operationBody);
  res.setType((PType)$operationType);
  $$ = res;
}
;

implicitOperationDef :
  qualifier IDENTIFIER parameterTypes identifierTypePairList_opt externals_opt preExpr_opt postExpr
{
  AAccessSpecifierAccessSpecifier access = (AAccessSpecifierAccessSpecifier)$1;
  LexNameToken name = extractLexNameToken((CmlLexeme)$2);
  List<? extends APatternListTypePair> parameterPatterns = (List<? extends APatternListTypePair>)$3;
   List<? extends AIdentifierTypePair> result = (List<? extends AIdentifierTypePair>)$4;
  List<? extends AExternalClause> externals = (List<? extends AExternalClause>)$5;
  PExp precondition = (PExp)$6;
  PExp postcondition = (PExp)$7;
  LexLocation location = null;
  /* if (access != null) */
    location = combineLexLocation(name.location, postcondition.getLocation());
  /* else */
  /*   location = combineLexLocation(access.getLocation(), postcondition.getLocation()); */
  AImplicitOperationDefinition ifunc =
    new AImplicitOperationDefinition(location,
                                     NameScope.GLOBAL,
                                     false,
                                     access,
				     null,//Pass
                                     parameterPatterns,
                                     result,
                                     externals,
                                     precondition,
                                     postcondition,
                                     null, null);
  ifunc.setName(name);
  $$ = ifunc;
}
;

operationType :
  type DEQRARROW type
  {
      List<PType> types = new LinkedList<PType>();
      PType left = (PType)$1;
      PType right = (PType)$3;
      types.add(left);
      $$ = new AOperationType(extractLexLocation(left.getLocation(),right.getLocation()),
                              false,
                              new LinkedList<PDefinition>(),
                              types,
                              right);
  }
| LRPAREN DEQRARROW type
  {
      List<PType> types = new LinkedList<PType>();
      PType right = (PType)$3;
      types.add(new AVoidType(extractLexLocation((CmlLexeme)$1),
                              true));
      $$ = new AOperationType(extractLexLocation((CmlLexeme)$1,right.getLocation()),
                              false,
                              new LinkedList<PDefinition>(),
                              types,
                              right);
  }
| type DEQRARROW LRPAREN
  {
      List<PType> types = new LinkedList<PType>();
      PType left = (PType)$1;
      types.add(left);
      $$ = new AOperationType(extractLexLocation(left.getLocation(),(CmlLexeme)$3),
                              false,
                              new LinkedList<PDefinition>(),
                              types,
                              new AVoidType(extractLexLocation((CmlLexeme)$3),
                                            true));
  }
| LRPAREN DEQRARROW LRPAREN
  {
      List<PType> types = new LinkedList<PType>();
      types.add(new AVoidType(extractLexLocation((CmlLexeme)$1),
                              true));

      $$ = new AOperationType(extractLexLocation((CmlLexeme)$1,(CmlLexeme)$3),
                              true,
                              new LinkedList<PDefinition>(),
                              types,
                              new AVoidType(extractLexLocation((CmlLexeme)$3),
                                            true));
  }
;

operationBody :
/* DEVIATION
 * CML_0:
 *   action
 * here:
 *   blockStatement
 *
 * Use of a SEMI to separate operation definitions conflicts with the
 * use of a SEMI for the CSP sequential combinator.
 */
  blockStatement
{
  $$ = $1;
}
| SUBCLASSRESP
{
  $$ = new ASubclassResponsibilityAction(extractLexLocation((CmlLexeme)$1));
}
| NOTYETSPEC
{
  $$ = new ANotYetSpecifiedAction(extractLexLocation((CmlLexeme)$1), null, null);
}
;

externals_opt :
  externals
{
  $$ = $1;
}
| /* empty */
{
  $$ = null;
}
;

externals :
  FRAME varInformationList
{
  $$ = $2;
}
;

varInformationList :
  varInformation
{
  List<AExternalClause> infoList = new Vector<AExternalClause>();
  infoList.add((AExternalClause)$1);
  $$ = infoList;
}
| varInformationList varInformation
{
  List<AExternalClause> infoList = (List<AExternalClause>)$1;
  infoList.add((AExternalClause)$2);
  $$ = infoList;
}
;

varInformation :
  mode pathList
{
  List<? extends LexNameToken> names = (List<? extends LexNameToken>)$pathList;
  //  PMode mode = (PMode)$mode;
  LexToken mode = (LexToken)$mode;
  LexLocation location = combineLexLocation(mode.location,
                                            extractLastLexLocation(names));
  //$$ = new AExternalClause(location,mode,names, null);
  $$ = new AExternalClause(mode,names, null);
}
| mode pathList COLON type
{
  List<? extends LexNameToken> names = (List<? extends LexNameToken>)$pathList;
  //Mode mode = (PMode)$mode;
  LexToken mode = (LexToken)$mode;
  LexLocation location = combineLexLocation(mode.location,
                                            extractLastLexLocation(names));

  $$ = new AExternalClause(mode,
                           names,
                           (PType)$type);

  /* $$ = new AExternalClause(location,
  /*                          mode, */
  /*                          names, */
  /*                          (PType)$type); */
}
;

mode :
  RD
{
  //$$ = new AReadMode(extractLexLocation((CmlLexeme)$RD));
  LexLocation loc = extractLexLocation((CmlLexeme)$1);
  $$ = new LexToken(loc,VDMToken.READ);
}
| WR
{
  //$$ = new AWriteMode(extractLexLocation((CmlLexeme)$WR));
  LexLocation loc = extractLexLocation((CmlLexeme)$1);
  $$ = new LexToken(loc,VDMToken.WRITE);
}
;

/* RWL, invariantDef
 *
 * In the AST PDefinition and PInvariant does not have a common
 * ancestor below Object ! Hence having a list containing both is
 * troublesome.
 *
 * Therefore, the stateDefs and stateDefList has been changed such
 * that invariantDef is separate from the stateDefList.
 *
 * FIXME: The invariantDef needs to be glued onto the tree.
 *
 * AKM, proposed fix:
 * The invariant in the state declaration would correspond to the old
 * "classInvariant" definition which is in the AST allready.
 * So maybe we should just find a more suitable name for it. For now
 * I have changed the grammar back and used the AClassInvariantDefinition
 * class witout a rename.
 */
stateDefs :
  STATE 
{
    AStateParagraphDefinition state = new AStateParagraphDefinition();
    state.setLocation(extractLexLocation((CmlLexeme)$STATE));
    $$  = state;
}
| STATE stateDefList 
{
    AStateParagraphDefinition state = (AStateParagraphDefinition)$stateDefList;
    state.setLocation(extractLexLocation((CmlLexeme)$STATE,
					 extractLastLexLocation(state.getStateDefs())));
    $$ = state;
}
| STATE stateDefList SEMI
{
    AStateParagraphDefinition state = (AStateParagraphDefinition)$stateDefList;
    state.setLocation(extractLexLocation((CmlLexeme)$STATE,(CmlLexeme)$SEMI));
    $$ = state;
}
;

stateDefList :
  stateDef
{
  AStateParagraphDefinition stateDef = new AStateParagraphDefinition();
  List<PDefinition> defs = new Vector<PDefinition>();
  defs.add((PDefinition)$stateDef);
  stateDef.setStateDefs(defs);
  $$ = stateDef;
}
| stateDefList[list] SEMI stateDef
{
  AStateParagraphDefinition stateDef = (AStateParagraphDefinition)$list;
  stateDef.getStateDefs().add((PDefinition)$stateDef);
  $$ = stateDef;
}
;

stateDef :
  qualifier assignmentDef
{
  $$ = $2;
}
| INV expression
{
  PExp exp = (PExp) $2;
  LexLocation location = extractLexLocation((CmlLexeme)$1, exp.getLocation());
  $$ = new AClassInvariantDefinition(location, NameScope.GLOBAL, true, null/*access*/,null/*Pass*/, exp);
}
;

expressionList :
  expression
{
  List<PExp> exps = new Vector<PExp>();
  exps.add((PExp)$1);
  $$ = exps;
}
| expressionList COMMA expression
{
  List<PExp> exps = (List<PExp>)$1;
  exps.add((PExp)$3);
  $$ = exps;
}
;

expression :
  LPAREN expression[exp] RPAREN
{
  LexLocation loc = extractLexLocation((CmlLexeme)$LPAREN, (CmlLexeme)$RPAREN);
  $$ = new ABracketedExp(loc, (PExp)$exp);
}
| LET localDefList IN expression
{
  List<PDefinition> l = (List<PDefinition>)$2;
  PExp e = (PExp)$4;
  LexLocation loc = extractLexLocation((CmlLexeme)$1, e.getLocation());
  $$ = new ALetDefExp(loc, l, e);
}
| ifExpr
{
  $$ = $1;
}
| casesExpr
{
  $$ = $1;
}
| unaryExpr
{
  $$ = $1;
}
| binaryExpr
{
  $$ = $1;
}
/* quantified expressions */
| FORALL bindList AT expression %prec FORALL
{
  CmlLexeme forall = (CmlLexeme)$1;
  List<PMultipleBind> binds = (List<PMultipleBind>)$2;
  CmlLexeme amp = (CmlLexeme)$3;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(forall), exp.getLocation());
  AForAllExp forallexp = new AForAllExp(loc, binds, exp);
  $$ = forallexp;
}
| EXISTS bindList AT expression %prec EXISTS
{
  CmlLexeme exists = (CmlLexeme)$1;
  List<PMultipleBind> binds = (List<PMultipleBind>)$2;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  AExistsExp existsExp = new AExistsExp(loc, binds, exp);
  $$ = existsExp;
}
| EXISTS1 bind AT expression %prec EXISTS1
{
  CmlLexeme exists = (CmlLexeme)$1;
  PBind bind = (PBind)$2;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(exists), exp.getLocation());
  AExists1Exp existsExp = new AExists1Exp(loc, bind, exp, null);
  $$ = existsExp;
}
| IOTA bind AT expression %prec IOTA
{
  CmlLexeme iota = (CmlLexeme)$1;
  PBind bind = (PBind)$2;
  PExp exp = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(iota), exp.getLocation());
  AIotaExp ioatexp = new AIotaExp(loc, bind, exp);
  $$ = ioatexp;
}
/* set enumeration */
| LCURLY RCURLY
{
  CmlLexeme lc = (CmlLexeme)$1;
  CmlLexeme rc = (CmlLexeme)$2;
  LexLocation loc = combineLexLocation(extractLexLocation(lc), extractLexLocation(rc));
  ASetEnumSetExp res = new ASetEnumSetExp();
  res.setLocation(loc);
  $$ = res;
}
| LCURLY expressionList RCURLY
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3);
  List<PExp> members = (List<PExp>)$2;
  $$ = new ASetEnumSetExp(location, members);
}
/* set comprehensions */
| LCURLY expression BAR bindList RCURLY
{
  CmlLexeme lcurly = (CmlLexeme)$1;
  PExp exp = (PExp)$2;
  List<PMultipleBind> binds = (List<PMultipleBind>)$4;
  CmlLexeme rcurly = (CmlLexeme)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  ASetCompSetExp setComp = new ASetCompSetExp(loc, exp, binds, null);
  $$ = setComp;
}
| LCURLY expression BAR bindList AT expression RCURLY
{
  CmlLexeme lcurly = (CmlLexeme)$1;
  PExp exp = (PExp)$2;
  List<PMultipleBind> binds = (List<PMultipleBind>)$4;
  PExp pred = (PExp)$6;
  CmlLexeme rcurly = (CmlLexeme)$7;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  ASetCompSetExp setComp = new ASetCompSetExp(loc, exp, binds, pred);
  $$ = setComp;
}
/* set range expression */
| LCURLY expression ELLIPSIS expression RCURLY
{
  CmlLexeme lcurly = (CmlLexeme)$1;
  PExp start = (PExp)$2;
  PExp end = (PExp)$4;
  CmlLexeme rcurly = (CmlLexeme)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  ASetRangeSetExp res = new ASetRangeSetExp(loc, start, end);
  $$ = res;
}
/* sequence enumerations */
| LRSQUARE
{
  CmlLexeme lrsqr = (CmlLexeme)$LRSQUARE;
  List<PExp> exps = new LinkedList<PExp>();
  LexLocation loc = extractLexLocation(lrsqr);
  ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
  $$ = exp;
}
/* ?FIXME: Sequences cannot handle '[[]]' since '[[' and ']]' will be
 * lexed as a DLSQUARE and DRSQUARE token because of the renaming
 * comprehension. For now we need spaces like '[ [] ]' to be able to
 * parse it correctly.
 *
 * (JWC) We definitely need to document this out for users; I don't
 * think we can necessarily fix it (though it should be noted that a
 * sequence enumeration that contains sequence enumerations is a
 * little unusual).
 */
| LSQUARE expressionList RSQUARE
{
  CmlLexeme lsqr = (CmlLexeme)$1;
  List<PExp> exps = (List<PExp>)$2;
  CmlLexeme rsqr = (CmlLexeme)$3;
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqEnumSeqExp exp = new ASeqEnumSeqExp(loc, exps);
  $$ = exp;
}
/* sequence comprehensions */
| LSQUARE expression BAR setBind RSQUARE
{
  CmlLexeme lsqr = (CmlLexeme)$1;
  PExp exp = (PExp)$2;
  ASetBind binds = (ASetBind)$4;
  CmlLexeme rsqr = (CmlLexeme)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqCompSeqExp res = new ASeqCompSeqExp(loc, exp, binds, null);
  $$ = res;
}
| LSQUARE expression BAR setBind AT expression RSQUARE
{
  CmlLexeme lsqr = (CmlLexeme)$1;
  PExp exp = (PExp)$2;
  ASetBind binds = (ASetBind)$4;
  PExp pred = (PExp)$6;
  CmlLexeme rsqr = (CmlLexeme)$7;
  LexLocation loc = combineLexLocation(extractLexLocation(lsqr), extractLexLocation(rsqr));
  ASeqCompSeqExp res = new ASeqCompSeqExp(loc, exp, binds, pred);
  $$ = res;
}
/* map enumerations */
| EMPTYMAP
{
  LexLocation loc = extractLexLocation((CmlLexeme)$EMPTYMAP);
  AMapEnumMapExp res = new AMapEnumMapExp(loc, new LinkedList<AMapletExp>());
  $$ = res;
}
| LCURLY mapletList RCURLY
{
  CmlLexeme lcurly = (CmlLexeme)$1;
  List<AMapletExp> maplets = (List<AMapletExp>)$2;
  CmlLexeme rcurly = (CmlLexeme)$3;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurly), extractLexLocation(rcurly));
  AMapEnumMapExp res = new AMapEnumMapExp(loc, maplets);
  $$ = res;
}
/* map comprehensions */
| LCURLY maplet BAR bindList RCURLY
{
  CmlLexeme lcurl = (CmlLexeme)$1;
  AMapletExp maplet = (AMapletExp)$2;
  List<PMultipleBind> binds = (List<PMultipleBind>)$4;
  CmlLexeme rcurl = (CmlLexeme)$5;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurl), extractLexLocation(rcurl));
  AMapCompMapExp res = new AMapCompMapExp(loc, maplet, binds, null);
  $$ = res;
}
| LCURLY maplet BAR bindList AT expression RCURLY
{
  CmlLexeme lcurl = (CmlLexeme)$1;
  AMapletExp maplet = (AMapletExp)$2;
  List<PMultipleBind> binds = (List<PMultipleBind>)$4;
  PExp pred = (PExp)$6;
  CmlLexeme rcurl = (CmlLexeme)$7;
  LexLocation loc = combineLexLocation(extractLexLocation(lcurl), extractLexLocation(rcurl));
  AMapCompMapExp res = new AMapCompMapExp(loc, maplet, binds, pred);
  $$ = res;
}
/* tuple constructor */
| MKUNDER LPAREN expressionList RPAREN
{
  CmlLexeme mku = (CmlLexeme)$1;
  List<PExp> exprs = (List<PExp>)$3;
  CmlLexeme rparen = (CmlLexeme)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(mku), extractLexLocation(rparen));
  ATupleExp res = new ATupleExp(loc, exprs);
  $$ = res;
}
/* recordConstructor */
| MKUNDERNAME LPAREN expressionList RPAREN
{
  CmlLexeme mku = (CmlLexeme)$1;
  LexNameToken name = extractNameFromUNDERNAMEToken(mku);
  List<PExp> exprs = (List<PExp>)$3;
  LexLocation loc = extractLexLocation(mku, (CmlLexeme)$4);

  PExp res = null;

  if ("token".equals(name.name ) && exprs != null && exprs.size() == 1)
    {
      ATokenBasicType type = new ATokenBasicType(loc,true) ;
      res = new AMkBasicExp(type, loc, exprs.get(0));
    }
  else
       res = new AMkTypeExp(loc, name, exprs);
  $$ = res;
}
/* lambda expression */
| LAMBDA typeBindList AT expression %prec LAMBDA
{
  CmlLexeme l = (CmlLexeme)$1;
  List<ATypeBind> binds = (List<ATypeBind>)$2;
  PExp body = (PExp)$4;
  LexLocation loc = combineLexLocation(extractLexLocation(l), body.getLocation());
  ALambdaExp res = new ALambdaExp(loc, binds, body, null, null);
  $$ = res;
}
| generalIsExpr
{
  $$ = $1;
}
/* precondition expression */
/* (JWC) first parameter of the precondition expression is the
 * function that we want the precondition of
 */
| PREUNDER LPAREN expressionList RPAREN
{
  CmlLexeme preu = (CmlLexeme)$1;
  List<PExp> exprs = (List<PExp>)$3;
  CmlLexeme rparen = (CmlLexeme)$4;
  PExp function = null;
  LexLocation loc = combineLexLocation(extractLexLocation(preu), extractLexLocation(rparen));
  APreExp res = new APreExp(loc, function, exprs);
  $$ = res;
}
/* DEVIATION
 * PATH
 * GRAMMAR ERROR: Missing COMMA
 * CML_0:
 *   ISOFCLASS LPAREN name expression RPAREN
 * here:
 *   ISOFCLASS LPAREN path COMMA expression RPAREN
 */
| ISOFCLASS LPAREN path COMMA expression RPAREN
{
    PExp exp = null;
    try{
        Path path = (Path)$path;
        exp = new AIsOfClassExp(extractLexLocation((CmlLexeme)$ISOFCLASS,(CmlLexeme)$RPAREN),
                                path.convertToName(),
                                (PExp)$5);
    }
    catch(PathConvertException e) {
        e.printStackTrace();
        System.exit(-4);
    }
    $$ = exp;
}
/* DEVIATION
 * PATH
 * CML_0:
 *   name
 *   IDENTIFIER TILDE // oldName
 *   expression LPAREN expression ELLIPSIS expression RPAREN // subsequence expression
 *   expression LPAREN expressionList RPAREN
 *   expression DOTHASH NUMERAL // tuple select
 *   expression DOT IDENTIFIER // field select
 *   SELF
 * 1) convert to a name
 * 2) convert to an oldName
 * 3) convert to a subsequence expression
 * 4) convert to a function application
 * 5) convert to a tuple select
 * 6) convert to a field select
 * 7) convert to a self expression
 *
 * (JWC) 3 through 5 need to be general expression rather than just
 * paths/names.  So, this is a problem for now.
 * e.g. we cannot do:
 *   (1,2,3).#2
 * but we can do
 *   a := (1,2,3)
 *   a.#2
 */
| path
{
  Path path = (Path)$1;
  PExp exp = null;
  try {
    exp = path.convertToExpression();
  } catch(PathConvertException e) {
    e.printStackTrace();
    System.exit(-4);
  }
  $$ = exp;
}
/* symbolic literal expressions*/
| numericLiteral
{
    PExp exp = null;
    if($1 instanceof LexIntegerToken) {
        LexIntegerToken lit = (LexIntegerToken)$1;
        exp = new AIntLiteralExp(lit.location, lit);
    } else {
        LexRealToken lit = (LexRealToken)$1;
        exp = new ARealLiteralExp(lit.location, lit);
    }
    $$ = exp;
}
| booleanLiteral
{
  LexBooleanToken lit = (LexBooleanToken)$1;
  $$ = new ABooleanConstExp(lit.location, lit);
}
| nilLiteral
{
    LexKeywordToken tok = (LexKeywordToken)$1;
    $$ = new ANilExp(tok.location);
}
| characterLiteral
{
    LexCharacterToken token = (LexCharacterToken)$characterLiteral;
    $$ = new ACharLiteralExp(token.location, token);
}
| textLiteral
{
    LexStringToken value = (LexStringToken)$textLiteral;
    ASeqSeqType t = new ASeqSeqType(value.location,
                                    true, null,
                                    new ACharBasicType(),
                                    value.value.length() == 0);
    $$ = new AStringLiteralExp(t,
                               value.location,
                               value);
}
| quoteLiteral
{
  LexQuoteToken value = (LexQuoteToken)$1;
  $$ = new AQuoteLiteralExp(value.location, value);
}
/* symbolic literal expressions end*/
/* chanset expressions */
| LCURLYBAR pathList BARRCURLY
{
  LexLocation location = extractLexLocation((CmlLexeme)$1, (CmlLexeme)$3);
  List<LexIdentifierToken> identifiers = (List<LexIdentifierToken>)$2;
  $$ = new AEnumChansetSetExp(location, identifiers);
}
| LCURLYBAR path BAR bindList BARRCURLY 
{
    try{
	LexLocation location = extractLexLocation((CmlLexeme)$LCURLYBAR, (CmlLexeme)$BARRCURLY);
	ANameChannelExp chanNameExp = (ANameChannelExp)((Path)$path).convertToChannelNameExpression();
	List<PMultipleBind> bindings = (List<PMultipleBind>)$bindList;
	$$ = new ACompChansetSetExp(location,chanNameExp , bindings, null);
    }
    catch(PathConvertException e) {
	e.printStackTrace();
	System.exit(-4);
    }
}
| LCURLYBAR path BAR bindList AT expression[exp] BARRCURLY
{
    try{
	LexLocation location = extractLexLocation((CmlLexeme)$LCURLYBAR,
						  (CmlLexeme)$BARRCURLY);
	ANameChannelExp chanNameExp = (ANameChannelExp)((Path)$path).convertToChannelNameExpression();
	List<PMultipleBind> bindings = (List<PMultipleBind>)$bindList;
	PExp pred = (PExp)$exp;
	$$ = new ACompChansetSetExp(location, chanNameExp, bindings, pred);
    }
    catch(PathConvertException e) {
	e.printStackTrace();
	System.exit(-4);
    }
}
/* chanset expressions end */
;

/* symbolic literals */
booleanLiteral:
  FALSE
{
  LexLocation loc = extractLexLocation( (CmlLexeme)$1 );
  $$ = new LexBooleanToken(VDMToken.FALSE, loc);
}
| TRUE
{
  LexLocation loc = extractLexLocation( (CmlLexeme)$1 );
  $$ = new LexBooleanToken(VDMToken.TRUE, loc);
}
;

characterLiteral :
  CHAR_LIT
{
  CmlLexeme lex = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation( lex );
  String res = lex.getValue();
  res = res.replace("'", "");
  $$ = new LexCharacterToken(convertEscapeToChar(res), loc);
}
;

nilLiteral :
  NIL
{
    $$ = new LexKeywordToken(VDMToken.NIL,
                             extractLexLocation((CmlLexeme)$1));
}
;

numericLiteral :
  NUMERAL
{
  CmlLexeme lexeme = (CmlLexeme)$NUMERAL;
  LexLocation loc = extractLexLocation(lexeme);
  $$ = new LexIntegerToken(Long.decode(lexeme.getValue()), loc);
}
| HEX_LITERAL
{
  CmlLexeme lexeme = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation(lexeme);
  BigInteger b = new BigInteger(lexeme.getValue().substring(2), 16);
  $$ = new LexIntegerToken(b.longValue(), loc);
}
| DECIMAL
{
  CmlLexeme lexeme = (CmlLexeme)$1;
  LexLocation loc = extractLexLocation(lexeme);
  try {
    DecimalFormat dec = new DecimalFormat();
    $$ = new LexRealToken(dec.parse(lexeme.getValue()).doubleValue(), loc);
  } catch (Exception e) {
    $$ = new LexRealToken(0, loc);
  }
}
;

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
