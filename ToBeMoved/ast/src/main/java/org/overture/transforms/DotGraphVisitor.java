package org.overture.transforms;

import org.overture.ast.analysis.QuestionAdaptor;
import org.overture.ast.declarations.AChannelDeclaration;
import org.overture.ast.declarations.AChannelNameDeclaration;
import org.overture.ast.declarations.AChansetDeclaration;
import org.overture.ast.declarations.AClassDeclaration;
import org.overture.ast.declarations.AGlobalDeclaration;
import org.overture.ast.declarations.AProcessDeclaration;
import org.overture.ast.declarations.ASingleTypeDeclaration;
import org.overture.ast.declarations.PDeclaration;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AChannelDefinition;
import org.overture.ast.definitions.AChansetDefinition;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AEqualsDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AExternalDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AImportedDefinition;
import org.overture.ast.definitions.AInheritedDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.AMultiBindListDefinition;
import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.AProcessDefinition;
import org.overture.ast.definitions.AProtectedAccess;
import org.overture.ast.definitions.APublicAccess;
import org.overture.ast.definitions.ARenamedDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.AThreadDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AUntypedDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PAccess;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.*;
import org.overture.ast.modules.AAllExport;
import org.overture.ast.modules.AAllImport;
import org.overture.ast.modules.AFromModuleImports;
import org.overture.ast.modules.AFunctionExport;
import org.overture.ast.modules.AFunctionValueImport;
import org.overture.ast.modules.AModuleExports;
import org.overture.ast.modules.AModuleImports;
import org.overture.ast.modules.AModuleModules;
import org.overture.ast.modules.AOperationExport;
import org.overture.ast.modules.AOperationValueImport;
import org.overture.ast.modules.ATypeExport;
import org.overture.ast.modules.ATypeImport;
import org.overture.ast.modules.AValueExport;
import org.overture.ast.modules.AValueValueImport;
import org.overture.ast.modules.PExport;
import org.overture.ast.modules.PExports;
import org.overture.ast.modules.PImport;
import org.overture.ast.modules.PImports;
import org.overture.ast.modules.PModules;
import org.overture.ast.modules.SValueImport;
import org.overture.ast.node.INode;
import org.overture.ast.node.IToken;
import org.overture.ast.node.tokens.TAsync;
import org.overture.ast.node.tokens.TStatic;
import org.overture.ast.patterns.ABooleanPattern;
import org.overture.ast.patterns.ACharacterPattern;
import org.overture.ast.patterns.AConcatenationPattern;
import org.overture.ast.patterns.ADefPatternBind;
import org.overture.ast.patterns.AExpressionPattern;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.AIgnorePattern;
import org.overture.ast.patterns.AIntegerPattern;
import org.overture.ast.patterns.ANilPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.AQuotePattern;
import org.overture.ast.patterns.ARealPattern;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.ASeqPattern;
import org.overture.ast.patterns.ASetBind;
import org.overture.ast.patterns.ASetMultipleBind;
import org.overture.ast.patterns.ASetPattern;
import org.overture.ast.patterns.AStringPattern;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.patterns.ATypeBind;
import org.overture.ast.patterns.ATypeMultipleBind;
import org.overture.ast.patterns.AUnionPattern;
import org.overture.ast.patterns.PBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.patterns.PPatternBind;
import org.overture.ast.program.AProgramProgram;
import org.overture.ast.program.ASourcefileSourcefile;
import org.overture.ast.program.PProgram;
import org.overture.ast.program.PSourcefile;
import org.overture.ast.statements.AAlwaysStm;
import org.overture.ast.statements.AApplyObjectDesignator;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.AAtomicStm;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.ACallObjectStm;
import org.overture.ast.statements.ACallStm;
import org.overture.ast.statements.ACaseAlternativeStm;
import org.overture.ast.statements.ACasesStm;
import org.overture.ast.statements.AClassInvariantStm;
import org.overture.ast.statements.ADefLetDefStm;
import org.overture.ast.statements.AElseIfStm;
import org.overture.ast.statements.AErrorCase;
import org.overture.ast.statements.AErrorStm;
import org.overture.ast.statements.AExitStm;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.AFieldObjectDesignator;
import org.overture.ast.statements.AFieldStateDesignator;
import org.overture.ast.statements.AForAllStm;
import org.overture.ast.statements.AForIndexStm;
import org.overture.ast.statements.AForPatternBindStm;
import org.overture.ast.statements.AIdentifierObjectDesignator;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.ast.statements.AIfStm;
import org.overture.ast.statements.ALetBeStStm;
import org.overture.ast.statements.AMapSeqStateDesignator;
import org.overture.ast.statements.ANewObjectDesignator;
import org.overture.ast.statements.ANonDeterministicSimpleBlockStm;
import org.overture.ast.statements.ANotYetSpecifiedStm;
import org.overture.ast.statements.AReturnStm;
import org.overture.ast.statements.ASelfObjectDesignator;
import org.overture.ast.statements.ASkipStm;
import org.overture.ast.statements.ASpecificationStm;
import org.overture.ast.statements.AStartStm;
import org.overture.ast.statements.ASubclassResponsibilityStm;
import org.overture.ast.statements.ATixeStmtAlternative;
import org.overture.ast.statements.ATrapStm;
import org.overture.ast.statements.AWhileStm;
import org.overture.ast.statements.PAlternativeStm;
import org.overture.ast.statements.PCase;
import org.overture.ast.statements.PClause;
import org.overture.ast.statements.PObjectDesignator;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.statements.PStm;
import org.overture.ast.statements.PStmtAlternative;
import org.overture.ast.statements.SLetDefStm;
import org.overture.ast.statements.SSimpleBlockStm;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.ABracketType;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.AInMapMapType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.AMapMapType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.ANatOneNumericBasicType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AOptionalType;
import org.overture.ast.types.AParameterType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.ARationalNumericBasicType;
import org.overture.ast.types.ARealNumericBasicType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.ASeq1SeqType;
import org.overture.ast.types.ASeqSeqType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.ATokenBasicType;
import org.overture.ast.types.AUndefinedType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.AUnresolvedType;
import org.overture.ast.types.AVoidReturnType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PAccessSpecifier;
import org.overture.ast.types.PField;
import org.overture.ast.types.PType;
import org.overture.ast.types.SBasicType;
import org.overture.ast.types.SInvariantType;
import org.overture.ast.types.SMapType;
import org.overture.ast.types.SNumericBasicType;
import org.overture.ast.types.SSeqType;
import org.overturetool.util.ClonableFile;
import org.overturetool.util.ClonableString;
import org.overturetool.vdmj.lex.LexBooleanToken;
import org.overturetool.vdmj.lex.LexCharacterToken;
import org.overturetool.vdmj.lex.LexIdentifierToken;
import org.overturetool.vdmj.lex.LexIntegerToken;
import org.overturetool.vdmj.lex.LexLocation;
import org.overturetool.vdmj.lex.LexNameToken;
import org.overturetool.vdmj.lex.LexQuoteToken;
import org.overturetool.vdmj.lex.LexRealToken;
import org.overturetool.vdmj.lex.LexStringToken;
import org.overturetool.vdmj.lex.LexToken;
import org.overturetool.vdmj.typechecker.ClassDefinitionSettings;
import org.overturetool.vdmj.typechecker.NameScope;

public class DotGraphVisitor extends QuestionAdaptor<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 637147601437624885L;
	
	private StringBuilder resultString;
	private int nodeCount = 0;

	private String makeNewNodeName()
	{
		return "n" + nodeCount++;
		
	}
	
	public DotGraphVisitor()
	{
		resultString = new StringBuilder();
		resultString.append("\tnode [shape=record];\n");
	}
	
	public String getResultString()
	{
		return "\ndigraph ast\n{\n" + resultString.toString() + "\n}";
		
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}


	@Override
	public void caseLexToken(LexToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexToken(node, question);
	}


	@Override
	public void caseLexNameToken(LexNameToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexNameToken(node, question);
	}


	@Override
	public void caseLexIdentifierToken(LexIdentifierToken node, String question) {
				
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>LexIdentifierToken|<f1>Name:"+node.getName()+"}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
	}


	@Override
	public void caseLexBooleanToken(LexBooleanToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexBooleanToken(node, question);
	}


	@Override
	public void caseLexCharacterToken(LexCharacterToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexCharacterToken(node, question);
	}


	@Override
	public void caseLexIntegerToken(LexIntegerToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexIntegerToken(node, question);
	}


	@Override
	public void caseLexQuoteToken(LexQuoteToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexQuoteToken(node, question);
	}


	@Override
	public void caseLexRealToken(LexRealToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexRealToken(node, question);
	}


	@Override
	public void caseLexStringToken(LexStringToken node, String question) {
		// TODO Auto-generated method stub
		super.caseLexStringToken(node, question);
	}


	@Override
	public void caseClonableFile(ClonableFile node, String question) {
		// TODO Auto-generated method stub
		super.caseClonableFile(node, question);
	}


	@Override
	public void caseClonableString(ClonableString node, String question) {
		// TODO Auto-generated method stub
		super.caseClonableString(node, question);
	}


	@Override
	public void caseClassDefinitionSettings(ClassDefinitionSettings node,
			String question) {
		// TODO Auto-generated method stub
		super.caseClassDefinitionSettings(node, question);
	}


	@Override
	public void caseLexLocation(LexLocation node, String question) {
		// TODO Auto-generated method stub
		super.caseLexLocation(node, question);
	}


	@Override
	public void caseNameScope(NameScope node, String question) {
		// TODO Auto-generated method stub
		super.caseNameScope(node, question);
	}


	@Override
	public void caseBoolean(Boolean node, String question) {
		// TODO Auto-generated method stub
		super.caseBoolean(node, question);
	}


	@Override
	public void caseInteger(Integer node, String question) {
		// TODO Auto-generated method stub
		super.caseInteger(node, question);
	}


	@Override
	public void caseString(String node, String question) {
		// TODO Auto-generated method stub
		super.caseString(node, question);
	}


	@Override
	public void caseLong(Long node, String question) {
		// TODO Auto-generated method stub
		super.caseLong(node, question);
	}


	@Override
	public void caseTStatic(TStatic node, String question) {
		// TODO Auto-generated method stub
		super.caseTStatic(node, question);
	}


	@Override
	public void caseTAsync(TAsync node, String question) {
		// TODO Auto-generated method stub
		super.caseTAsync(node, question);
	}


	@Override
	public void defaultPProgram(PProgram node, String question) {
		// TODO Auto-generated method stub
		super.defaultPProgram(node, question);
	}


	@Override
	public void caseAProgramProgram(AProgramProgram node, String question) {
		// TODO Auto-generated method stub
		super.caseAProgramProgram(node, question);
	}


	@Override
	public void defaultPSourcefile(PSourcefile node, String question) {
		// TODO Auto-generated method stub
		super.defaultPSourcefile(node, question);
	}


	@Override
	public void caseASourcefileSourcefile(ASourcefileSourcefile node,
			String question) {
		
		String nodeName = makeNewNodeName();
		
		String line = nodeName  + " [label=\"{<f0>ASourcefileSourcefile|<f1>Name:"+node.getName()+"}\"];\n";
		
		resultString.append("\t"+line);
		
		for(PDeclaration decl : node.getDecls())
		{
			decl.apply(this,nodeName);
		}
	
	}


	@Override
	public void defaultPDeclaration(PDeclaration node, String question) {
		// TODO Auto-generated method stub
		super.defaultPDeclaration(node, question);
	}


	@Override
	public void caseAClassDeclaration(AClassDeclaration node, String question) {
		// TODO Auto-generated method stub
		super.caseAClassDeclaration(node, question);
	}


	@Override
	public void caseAProcessDeclaration(AProcessDeclaration node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAProcessDeclaration(node, question);
	}


	@Override
	public void caseAChannelDeclaration(AChannelDeclaration node,
			String question) {
		
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>AChannelDeclaration|<f1>Name:channels}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		node.getChannelDefinition().apply(this,nodeName);
	}


	@Override
	public void caseAChannelNameDeclaration(AChannelNameDeclaration node,
			String question) {
		
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>AChannelNameDeclaration}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		node.getDeclaration().apply(this, nodeName);
		
	}


	@Override
	public void caseAChansetDeclaration(AChansetDeclaration node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAChansetDeclaration(node, question);
	}


	@Override
	public void caseAGlobalDeclaration(AGlobalDeclaration node, String question) {
		// TODO Auto-generated method stub
		super.caseAGlobalDeclaration(node, question);
	}


	@Override
	public void caseASingleTypeDeclaration(ASingleTypeDeclaration node,
			String question) {
		
		String nodeName = makeNewNodeName();
		
		String typeName = "null";
		
		if (node.getType() != null)
			typeName = node.getType().toString(); 
		
		resultString.append("\t" + nodeName + " [label=\"{<f0>ASingleTypeDeclaration |<f1>Type: "+
				typeName +" }\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		
		for(LexIdentifierToken id : node.getIdentifiers())
		{
			id.apply(this, nodeName);
		}
		
	}


	@Override
	public void defaultPDefinition(PDefinition node, String question) {
		// TODO Auto-generated method stub
		super.defaultPDefinition(node, question);
	}


	@Override
	public void caseAAssignmentDefinition(AAssignmentDefinition node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAAssignmentDefinition(node, question);
	}


	@Override
	public void caseAInstanceVariableDefinition(
			AInstanceVariableDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAInstanceVariableDefinition(node, question);
	}


	@Override
	public void defaultSClassDefinition(SClassDefinition node, String question) {
		// TODO Auto-generated method stub
		super.defaultSClassDefinition(node, question);
	}


	@Override
	public void caseAProcessDefinition(AProcessDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAProcessDefinition(node, question);
	}


	@Override
	public void caseAChansetDefinition(AChansetDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAChansetDefinition(node, question);
	}


	@Override
	public void caseAChannelDefinition(AChannelDefinition node, String question) {
		
		String nodeName = makeNewNodeName();
		resultString.append("\t" + nodeName + " [label=\"{<f0>AChannelDefinition}\"];\n");
		resultString.append("\t" + question + " -> " + nodeName +"\n");
		
		for(AChannelNameDeclaration cnd : node.getChannelNameDecls())
		{
			cnd.apply(this,nodeName);
		}
		
		
	}


	@Override
	public void caseAClassInvariantDefinition(AClassInvariantDefinition node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAClassInvariantDefinition(node, question);
	}


	@Override
	public void caseAEqualsDefinition(AEqualsDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAEqualsDefinition(node, question);
	}


	@Override
	public void caseAExplicitFunctionDefinition(
			AExplicitFunctionDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAExplicitFunctionDefinition(node, question);
	}


	@Override
	public void caseAExternalDefinition(AExternalDefinition node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAExternalDefinition(node, question);
	}


	@Override
	public void caseAImplicitFunctionDefinition(
			AImplicitFunctionDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAImplicitFunctionDefinition(node, question);
	}


	@Override
	public void caseAExplicitOperationDefinition(
			AExplicitOperationDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAExplicitOperationDefinition(node, question);
	}


	@Override
	public void caseAImplicitOperationDefinition(
			AImplicitOperationDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAImplicitOperationDefinition(node, question);
	}


	@Override
	public void caseAImportedDefinition(AImportedDefinition node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAImportedDefinition(node, question);
	}


	@Override
	public void caseAInheritedDefinition(AInheritedDefinition node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAInheritedDefinition(node, question);
	}


	@Override
	public void caseALocalDefinition(ALocalDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseALocalDefinition(node, question);
	}


	@Override
	public void caseAMultiBindListDefinition(AMultiBindListDefinition node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAMultiBindListDefinition(node, question);
	}


	@Override
	public void caseARenamedDefinition(ARenamedDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseARenamedDefinition(node, question);
	}


	@Override
	public void caseAStateDefinition(AStateDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAStateDefinition(node, question);
	}


	@Override
	public void caseAThreadDefinition(AThreadDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAThreadDefinition(node, question);
	}


	@Override
	public void caseATypeDefinition(ATypeDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseATypeDefinition(node, question);
	}


	@Override
	public void caseAUntypedDefinition(AUntypedDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAUntypedDefinition(node, question);
	}


	@Override
	public void caseAValueDefinition(AValueDefinition node, String question) {
		// TODO Auto-generated method stub
		super.caseAValueDefinition(node, question);
	}


	@Override
	public void caseAClassClassDefinition(AClassClassDefinition node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAClassClassDefinition(node, question);
	}


	@Override
	public void defaultPExp(PExp node, String question) {
		// TODO Auto-generated method stub
		super.defaultPExp(node, question);
	}


	@Override
	public void caseAApplyExp(AApplyExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAApplyExp(node, question);
	}


	@Override
	public void defaultSUnaryExp(SUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.defaultSUnaryExp(node, question);
	}


	@Override
	public void defaultSBinaryExp(SBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.defaultSBinaryExp(node, question);
	}


	@Override
	public void caseABooleanConstExp(ABooleanConstExp node, String question) {
		// TODO Auto-generated method stub
		super.caseABooleanConstExp(node, question);
	}


	@Override
	public void caseACasesExp(ACasesExp node, String question) {
		// TODO Auto-generated method stub
		super.caseACasesExp(node, question);
	}


	@Override
	public void caseACharLiteralExp(ACharLiteralExp node, String question) {
		// TODO Auto-generated method stub
		super.caseACharLiteralExp(node, question);
	}


	@Override
	public void caseAElseIfExp(AElseIfExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAElseIfExp(node, question);
	}


	@Override
	public void caseAExists1Exp(AExists1Exp node, String question) {
		// TODO Auto-generated method stub
		super.caseAExists1Exp(node, question);
	}


	@Override
	public void caseAExistsExp(AExistsExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAExistsExp(node, question);
	}


	@Override
	public void caseAFieldExp(AFieldExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAFieldExp(node, question);
	}


	@Override
	public void caseAFieldNumberExp(AFieldNumberExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAFieldNumberExp(node, question);
	}


	@Override
	public void caseAForAllExp(AForAllExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAForAllExp(node, question);
	}


	@Override
	public void caseAFuncInstatiationExp(AFuncInstatiationExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAFuncInstatiationExp(node, question);
	}


	@Override
	public void caseAHistoryExp(AHistoryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAHistoryExp(node, question);
	}


	@Override
	public void caseAIfExp(AIfExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAIfExp(node, question);
	}


	@Override
	public void caseAIntLiteralExp(AIntLiteralExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAIntLiteralExp(node, question);
	}


	@Override
	public void caseAIotaExp(AIotaExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAIotaExp(node, question);
	}


	@Override
	public void caseAIsExp(AIsExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAIsExp(node, question);
	}


	@Override
	public void caseAIsOfBaseClassExp(AIsOfBaseClassExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAIsOfBaseClassExp(node, question);
	}


	@Override
	public void caseAIsOfClassExp(AIsOfClassExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAIsOfClassExp(node, question);
	}


	@Override
	public void caseALambdaExp(ALambdaExp node, String question) {
		// TODO Auto-generated method stub
		super.caseALambdaExp(node, question);
	}


	@Override
	public void caseALetBeStExp(ALetBeStExp node, String question) {
		// TODO Auto-generated method stub
		super.caseALetBeStExp(node, question);
	}


	@Override
	public void caseALetDefExp(ALetDefExp node, String question) {
		// TODO Auto-generated method stub
		super.caseALetDefExp(node, question);
	}


	@Override
	public void caseADefExp(ADefExp node, String question) {
		// TODO Auto-generated method stub
		super.caseADefExp(node, question);
	}


	@Override
	public void defaultSMapExp(SMapExp node, String question) {
		// TODO Auto-generated method stub
		super.defaultSMapExp(node, question);
	}


	@Override
	public void caseAMapletExp(AMapletExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAMapletExp(node, question);
	}


	@Override
	public void caseAMkBasicExp(AMkBasicExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAMkBasicExp(node, question);
	}


	@Override
	public void caseAMkTypeExp(AMkTypeExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAMkTypeExp(node, question);
	}


	@Override
	public void caseAMuExp(AMuExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAMuExp(node, question);
	}


	@Override
	public void caseANewExp(ANewExp node, String question) {
		// TODO Auto-generated method stub
		super.caseANewExp(node, question);
	}


	@Override
	public void caseANilExp(ANilExp node, String question) {
		// TODO Auto-generated method stub
		super.caseANilExp(node, question);
	}


	@Override
	public void caseANotYetSpecifiedExp(ANotYetSpecifiedExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseANotYetSpecifiedExp(node, question);
	}


	@Override
	public void caseAPostOpExp(APostOpExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAPostOpExp(node, question);
	}


	@Override
	public void caseAPreExp(APreExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAPreExp(node, question);
	}


	@Override
	public void caseAPreOpExp(APreOpExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAPreOpExp(node, question);
	}


	@Override
	public void caseAQuoteLiteralExp(AQuoteLiteralExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAQuoteLiteralExp(node, question);
	}


	@Override
	public void caseARealLiteralExp(ARealLiteralExp node, String question) {
		// TODO Auto-generated method stub
		super.caseARealLiteralExp(node, question);
	}


	@Override
	public void caseASameBaseClassExp(ASameBaseClassExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASameBaseClassExp(node, question);
	}


	@Override
	public void caseASameClassExp(ASameClassExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASameClassExp(node, question);
	}


	@Override
	public void caseASelfExp(ASelfExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASelfExp(node, question);
	}


	@Override
	public void defaultSSeqExp(SSeqExp node, String question) {
		// TODO Auto-generated method stub
		super.defaultSSeqExp(node, question);
	}


	@Override
	public void defaultSSetExp(SSetExp node, String question) {
		// TODO Auto-generated method stub
		super.defaultSSetExp(node, question);
	}


	@Override
	public void caseAStateInitExp(AStateInitExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAStateInitExp(node, question);
	}


	@Override
	public void caseAStringLiteralExp(AStringLiteralExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAStringLiteralExp(node, question);
	}


	@Override
	public void caseASubclassResponsibilityExp(ASubclassResponsibilityExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseASubclassResponsibilityExp(node, question);
	}


	@Override
	public void caseASubseqExp(ASubseqExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASubseqExp(node, question);
	}


	@Override
	public void caseAThreadIdExp(AThreadIdExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAThreadIdExp(node, question);
	}


	@Override
	public void caseATimeExp(ATimeExp node, String question) {
		// TODO Auto-generated method stub
		super.caseATimeExp(node, question);
	}


	@Override
	public void caseATupleExp(ATupleExp node, String question) {
		// TODO Auto-generated method stub
		super.caseATupleExp(node, question);
	}


	@Override
	public void caseAUndefinedExp(AUndefinedExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAUndefinedExp(node, question);
	}


	@Override
	public void caseAVariableExp(AVariableExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAVariableExp(node, question);
	}


	@Override
	public void caseAAbsoluteUnaryExp(AAbsoluteUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAAbsoluteUnaryExp(node, question);
	}


	@Override
	public void caseACardinalityUnaryExp(ACardinalityUnaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseACardinalityUnaryExp(node, question);
	}


	@Override
	public void caseADistConcatUnaryExp(ADistConcatUnaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseADistConcatUnaryExp(node, question);
	}


	@Override
	public void caseADistIntersectUnaryExp(ADistIntersectUnaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseADistIntersectUnaryExp(node, question);
	}


	@Override
	public void caseADistMergeUnaryExp(ADistMergeUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseADistMergeUnaryExp(node, question);
	}


	@Override
	public void caseADistUnionUnaryExp(ADistUnionUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseADistUnionUnaryExp(node, question);
	}


	@Override
	public void caseAElementsUnaryExp(AElementsUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAElementsUnaryExp(node, question);
	}


	@Override
	public void caseAFloorUnaryExp(AFloorUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAFloorUnaryExp(node, question);
	}


	@Override
	public void caseAHeadUnaryExp(AHeadUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAHeadUnaryExp(node, question);
	}


	@Override
	public void caseAIndicesUnaryExp(AIndicesUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAIndicesUnaryExp(node, question);
	}


	@Override
	public void caseALenUnaryExp(ALenUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseALenUnaryExp(node, question);
	}


	@Override
	public void caseAMapDomainUnaryExp(AMapDomainUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAMapDomainUnaryExp(node, question);
	}


	@Override
	public void caseAMapInverseUnaryExp(AMapInverseUnaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAMapInverseUnaryExp(node, question);
	}


	@Override
	public void caseAMapRangeUnaryExp(AMapRangeUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAMapRangeUnaryExp(node, question);
	}


	@Override
	public void caseANotUnaryExp(ANotUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseANotUnaryExp(node, question);
	}


	@Override
	public void caseAPowerSetUnaryExp(APowerSetUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAPowerSetUnaryExp(node, question);
	}


	@Override
	public void caseAReverseUnaryExp(AReverseUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAReverseUnaryExp(node, question);
	}


	@Override
	public void caseATailUnaryExp(ATailUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseATailUnaryExp(node, question);
	}


	@Override
	public void caseAUnaryMinusUnaryExp(AUnaryMinusUnaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAUnaryMinusUnaryExp(node, question);
	}


	@Override
	public void caseAUnaryPlusUnaryExp(AUnaryPlusUnaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAUnaryPlusUnaryExp(node, question);
	}


	@Override
	public void defaultSBooleanBinaryExp(SBooleanBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.defaultSBooleanBinaryExp(node, question);
	}


	@Override
	public void caseACompBinaryExp(ACompBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseACompBinaryExp(node, question);
	}


	@Override
	public void caseADomainResByBinaryExp(ADomainResByBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseADomainResByBinaryExp(node, question);
	}


	@Override
	public void caseADomainResToBinaryExp(ADomainResToBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseADomainResToBinaryExp(node, question);
	}


	@Override
	public void caseAEqualsBinaryExp(AEqualsBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAEqualsBinaryExp(node, question);
	}


	@Override
	public void caseAInSetBinaryExp(AInSetBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAInSetBinaryExp(node, question);
	}


	@Override
	public void caseAMapUnionBinaryExp(AMapUnionBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAMapUnionBinaryExp(node, question);
	}


	@Override
	public void caseANotEqualBinaryExp(ANotEqualBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseANotEqualBinaryExp(node, question);
	}


	@Override
	public void caseANotInSetBinaryExp(ANotInSetBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseANotInSetBinaryExp(node, question);
	}


	@Override
	public void defaultSNumericBinaryExp(SNumericBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.defaultSNumericBinaryExp(node, question);
	}


	@Override
	public void caseAPlusPlusBinaryExp(APlusPlusBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAPlusPlusBinaryExp(node, question);
	}


	@Override
	public void caseAProperSubsetBinaryExp(AProperSubsetBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAProperSubsetBinaryExp(node, question);
	}


	@Override
	public void caseARangeResByBinaryExp(ARangeResByBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseARangeResByBinaryExp(node, question);
	}


	@Override
	public void caseARangeResToBinaryExp(ARangeResToBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseARangeResToBinaryExp(node, question);
	}


	@Override
	public void caseASeqConcatBinaryExp(ASeqConcatBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseASeqConcatBinaryExp(node, question);
	}


	@Override
	public void caseASetDifferenceBinaryExp(ASetDifferenceBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseASetDifferenceBinaryExp(node, question);
	}


	@Override
	public void caseASetIntersectBinaryExp(ASetIntersectBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseASetIntersectBinaryExp(node, question);
	}


	@Override
	public void caseASetUnionBinaryExp(ASetUnionBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASetUnionBinaryExp(node, question);
	}


	@Override
	public void caseAStarStarBinaryExp(AStarStarBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAStarStarBinaryExp(node, question);
	}


	@Override
	public void caseASubsetBinaryExp(ASubsetBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASubsetBinaryExp(node, question);
	}


	@Override
	public void caseAAndBooleanBinaryExp(AAndBooleanBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAAndBooleanBinaryExp(node, question);
	}


	@Override
	public void caseAEquivalentBooleanBinaryExp(
			AEquivalentBooleanBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAEquivalentBooleanBinaryExp(node, question);
	}


	@Override
	public void caseAImpliesBooleanBinaryExp(AImpliesBooleanBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAImpliesBooleanBinaryExp(node, question);
	}


	@Override
	public void caseAOrBooleanBinaryExp(AOrBooleanBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAOrBooleanBinaryExp(node, question);
	}


	@Override
	public void caseADivNumericBinaryExp(ADivNumericBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseADivNumericBinaryExp(node, question);
	}


	@Override
	public void caseADivideNumericBinaryExp(ADivideNumericBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseADivideNumericBinaryExp(node, question);
	}


	@Override
	public void caseAGreaterEqualNumericBinaryExp(
			AGreaterEqualNumericBinaryExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAGreaterEqualNumericBinaryExp(node, question);
	}


	@Override
	public void caseAGreaterNumericBinaryExp(AGreaterNumericBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAGreaterNumericBinaryExp(node, question);
	}


	@Override
	public void caseALessEqualNumericBinaryExp(ALessEqualNumericBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseALessEqualNumericBinaryExp(node, question);
	}


	@Override
	public void caseALessNumericBinaryExp(ALessNumericBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseALessNumericBinaryExp(node, question);
	}


	@Override
	public void caseAModNumericBinaryExp(AModNumericBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAModNumericBinaryExp(node, question);
	}


	@Override
	public void caseAPlusNumericBinaryExp(APlusNumericBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAPlusNumericBinaryExp(node, question);
	}


	@Override
	public void caseARemNumericBinaryExp(ARemNumericBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseARemNumericBinaryExp(node, question);
	}


	@Override
	public void caseASubstractNumericBinaryExp(ASubstractNumericBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseASubstractNumericBinaryExp(node, question);
	}


	@Override
	public void caseATimesNumericBinaryExp(ATimesNumericBinaryExp node,
			String question) {
		// TODO Auto-generated method stub
		super.caseATimesNumericBinaryExp(node, question);
	}


	@Override
	public void caseAMapCompMapExp(AMapCompMapExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAMapCompMapExp(node, question);
	}


	@Override
	public void caseAMapEnumMapExp(AMapEnumMapExp node, String question) {
		// TODO Auto-generated method stub
		super.caseAMapEnumMapExp(node, question);
	}


	@Override
	public void caseASeqCompSeqExp(ASeqCompSeqExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASeqCompSeqExp(node, question);
	}


	@Override
	public void caseASeqEnumSeqExp(ASeqEnumSeqExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASeqEnumSeqExp(node, question);
	}


	@Override
	public void caseASetCompSetExp(ASetCompSetExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASetCompSetExp(node, question);
	}


	@Override
	public void caseASetEnumSetExp(ASetEnumSetExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASetEnumSetExp(node, question);
	}


	@Override
	public void caseASetRangeSetExp(ASetRangeSetExp node, String question) {
		// TODO Auto-generated method stub
		super.caseASetRangeSetExp(node, question);
	}


	@Override
	public void defaultPModifier(PModifier node, String question) {
		// TODO Auto-generated method stub
		super.defaultPModifier(node, question);
	}


	@Override
	public void caseARecordModifier(ARecordModifier node, String question) {
		// TODO Auto-generated method stub
		super.caseARecordModifier(node, question);
	}


	@Override
	public void defaultPAlternative(PAlternative node, String question) {
		// TODO Auto-generated method stub
		super.defaultPAlternative(node, question);
	}


	@Override
	public void caseACaseAlternative(ACaseAlternative node, String question) {
		// TODO Auto-generated method stub
		super.caseACaseAlternative(node, question);
	}


	@Override
	public void defaultPType(PType node, String question) {
		// TODO Auto-generated method stub
		super.defaultPType(node, question);
	}


	@Override
	public void defaultSBasicType(SBasicType node, String question) {
		// TODO Auto-generated method stub
		super.defaultSBasicType(node, question);
	}


	@Override
	public void caseABracketType(ABracketType node, String question) {
		// TODO Auto-generated method stub
		super.caseABracketType(node, question);
	}


	@Override
	public void caseAClassType(AClassType node, String question) {
		// TODO Auto-generated method stub
		super.caseAClassType(node, question);
	}


	@Override
	public void caseAFunctionType(AFunctionType node, String question) {
		// TODO Auto-generated method stub
		super.caseAFunctionType(node, question);
	}


	@Override
	public void defaultSInvariantType(SInvariantType node, String question) {
		// TODO Auto-generated method stub
		super.defaultSInvariantType(node, question);
	}


	@Override
	public void defaultSMapType(SMapType node, String question) {
		// TODO Auto-generated method stub
		super.defaultSMapType(node, question);
	}


	@Override
	public void caseAOperationType(AOperationType node, String question) {
		// TODO Auto-generated method stub
		super.caseAOperationType(node, question);
	}


	@Override
	public void caseAOptionalType(AOptionalType node, String question) {
		// TODO Auto-generated method stub
		super.caseAOptionalType(node, question);
	}


	@Override
	public void caseAParameterType(AParameterType node, String question) {
		// TODO Auto-generated method stub
		super.caseAParameterType(node, question);
	}


	@Override
	public void caseAProductType(AProductType node, String question) {
		// TODO Auto-generated method stub
		super.caseAProductType(node, question);
	}


	@Override
	public void caseAQuoteType(AQuoteType node, String question) {
		// TODO Auto-generated method stub
		super.caseAQuoteType(node, question);
	}


	@Override
	public void defaultSSeqType(SSeqType node, String question) {
		// TODO Auto-generated method stub
		super.defaultSSeqType(node, question);
	}


	@Override
	public void caseASetType(ASetType node, String question) {
		// TODO Auto-generated method stub
		super.caseASetType(node, question);
	}


	@Override
	public void caseAUndefinedType(AUndefinedType node, String question) {
		// TODO Auto-generated method stub
		super.caseAUndefinedType(node, question);
	}


	@Override
	public void caseAUnionType(AUnionType node, String question) {
		// TODO Auto-generated method stub
		super.caseAUnionType(node, question);
	}


	@Override
	public void caseAUnknownType(AUnknownType node, String question) {
		// TODO Auto-generated method stub
		super.caseAUnknownType(node, question);
	}


	@Override
	public void caseAUnresolvedType(AUnresolvedType node, String question) {
		// TODO Auto-generated method stub
		super.caseAUnresolvedType(node, question);
	}


	@Override
	public void caseAVoidReturnType(AVoidReturnType node, String question) {
		// TODO Auto-generated method stub
		super.caseAVoidReturnType(node, question);
	}


	@Override
	public void caseAVoidType(AVoidType node, String question) {
		// TODO Auto-generated method stub
		super.caseAVoidType(node, question);
	}


	@Override
	public void caseASeqSeqType(ASeqSeqType node, String question) {
		// TODO Auto-generated method stub
		super.caseASeqSeqType(node, question);
	}


	@Override
	public void caseASeq1SeqType(ASeq1SeqType node, String question) {
		// TODO Auto-generated method stub
		super.caseASeq1SeqType(node, question);
	}


	@Override
	public void caseAInMapMapType(AInMapMapType node, String question) {
		// TODO Auto-generated method stub
		super.caseAInMapMapType(node, question);
	}


	@Override
	public void caseAMapMapType(AMapMapType node, String question) {
		// TODO Auto-generated method stub
		super.caseAMapMapType(node, question);
	}


	@Override
	public void caseANamedInvariantType(ANamedInvariantType node,
			String question) {
		// TODO Auto-generated method stub
		super.caseANamedInvariantType(node, question);
	}


	@Override
	public void caseARecordInvariantType(ARecordInvariantType node,
			String question) {
		// TODO Auto-generated method stub
		super.caseARecordInvariantType(node, question);
	}


	@Override
	public void caseABooleanBasicType(ABooleanBasicType node, String question) {
		// TODO Auto-generated method stub
		super.caseABooleanBasicType(node, question);
	}


	@Override
	public void caseACharBasicType(ACharBasicType node, String question) {
		// TODO Auto-generated method stub
		super.caseACharBasicType(node, question);
	}


	@Override
	public void defaultSNumericBasicType(SNumericBasicType node, String question) {
		// TODO Auto-generated method stub
		super.defaultSNumericBasicType(node, question);
	}


	@Override
	public void caseATokenBasicType(ATokenBasicType node, String question) {
		// TODO Auto-generated method stub
		super.caseATokenBasicType(node, question);
	}


	@Override
	public void caseAIntNumericBasicType(AIntNumericBasicType node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAIntNumericBasicType(node, question);
	}


	@Override
	public void caseANatOneNumericBasicType(ANatOneNumericBasicType node,
			String question) {
		// TODO Auto-generated method stub
		super.caseANatOneNumericBasicType(node, question);
	}


	@Override
	public void caseANatNumericBasicType(ANatNumericBasicType node,
			String question) {
		// TODO Auto-generated method stub
		super.caseANatNumericBasicType(node, question);
	}


	@Override
	public void caseARationalNumericBasicType(ARationalNumericBasicType node,
			String question) {
		// TODO Auto-generated method stub
		super.caseARationalNumericBasicType(node, question);
	}


	@Override
	public void caseARealNumericBasicType(ARealNumericBasicType node,
			String question) {
		// TODO Auto-generated method stub
		super.caseARealNumericBasicType(node, question);
	}


	@Override
	public void defaultPField(PField node, String question) {
		// TODO Auto-generated method stub
		super.defaultPField(node, question);
	}


	@Override
	public void caseAFieldField(AFieldField node, String question) {
		// TODO Auto-generated method stub
		super.caseAFieldField(node, question);
	}


	@Override
	public void defaultPAccessSpecifier(PAccessSpecifier node, String question) {
		// TODO Auto-generated method stub
		super.defaultPAccessSpecifier(node, question);
	}


	@Override
	public void caseAAccessSpecifierAccessSpecifier(
			AAccessSpecifierAccessSpecifier node, String question) {
		// TODO Auto-generated method stub
		super.caseAAccessSpecifierAccessSpecifier(node, question);
	}


	@Override
	public void defaultPAccess(PAccess node, String question) {
		// TODO Auto-generated method stub
		super.defaultPAccess(node, question);
	}


	@Override
	public void caseAPublicAccess(APublicAccess node, String question) {
		// TODO Auto-generated method stub
		super.caseAPublicAccess(node, question);
	}


	@Override
	public void caseAProtectedAccess(AProtectedAccess node, String question) {
		// TODO Auto-generated method stub
		super.caseAProtectedAccess(node, question);
	}


	@Override
	public void caseAPrivateAccess(APrivateAccess node, String question) {
		// TODO Auto-generated method stub
		super.caseAPrivateAccess(node, question);
	}


	@Override
	public void defaultPPattern(PPattern node, String question) {
		// TODO Auto-generated method stub
		super.defaultPPattern(node, question);
	}


	@Override
	public void caseABooleanPattern(ABooleanPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseABooleanPattern(node, question);
	}


	@Override
	public void caseACharacterPattern(ACharacterPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseACharacterPattern(node, question);
	}


	@Override
	public void caseAConcatenationPattern(AConcatenationPattern node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAConcatenationPattern(node, question);
	}


	@Override
	public void caseAExpressionPattern(AExpressionPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseAExpressionPattern(node, question);
	}


	@Override
	public void caseAIdentifierPattern(AIdentifierPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseAIdentifierPattern(node, question);
	}


	@Override
	public void caseAIgnorePattern(AIgnorePattern node, String question) {
		// TODO Auto-generated method stub
		super.caseAIgnorePattern(node, question);
	}


	@Override
	public void caseAIntegerPattern(AIntegerPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseAIntegerPattern(node, question);
	}


	@Override
	public void caseANilPattern(ANilPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseANilPattern(node, question);
	}


	@Override
	public void caseAQuotePattern(AQuotePattern node, String question) {
		// TODO Auto-generated method stub
		super.caseAQuotePattern(node, question);
	}


	@Override
	public void caseARealPattern(ARealPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseARealPattern(node, question);
	}


	@Override
	public void caseARecordPattern(ARecordPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseARecordPattern(node, question);
	}


	@Override
	public void caseASeqPattern(ASeqPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseASeqPattern(node, question);
	}


	@Override
	public void caseASetPattern(ASetPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseASetPattern(node, question);
	}


	@Override
	public void caseAStringPattern(AStringPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseAStringPattern(node, question);
	}


	@Override
	public void caseATuplePattern(ATuplePattern node, String question) {
		// TODO Auto-generated method stub
		super.caseATuplePattern(node, question);
	}


	@Override
	public void caseAUnionPattern(AUnionPattern node, String question) {
		// TODO Auto-generated method stub
		super.caseAUnionPattern(node, question);
	}


	@Override
	public void defaultPPair(PPair node, String question) {
		// TODO Auto-generated method stub
		super.defaultPPair(node, question);
	}


	@Override
	public void caseAPatternTypePair(APatternTypePair node, String question) {
		// TODO Auto-generated method stub
		super.caseAPatternTypePair(node, question);
	}


	@Override
	public void caseAPatternListTypePair(APatternListTypePair node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAPatternListTypePair(node, question);
	}


	@Override
	public void defaultPBind(PBind node, String question) {
		// TODO Auto-generated method stub
		super.defaultPBind(node, question);
	}


	@Override
	public void caseASetBind(ASetBind node, String question) {
		// TODO Auto-generated method stub
		super.caseASetBind(node, question);
	}


	@Override
	public void caseATypeBind(ATypeBind node, String question) {
		// TODO Auto-generated method stub
		super.caseATypeBind(node, question);
	}


	@Override
	public void defaultPMultipleBind(PMultipleBind node, String question) {
		// TODO Auto-generated method stub
		super.defaultPMultipleBind(node, question);
	}


	@Override
	public void caseASetMultipleBind(ASetMultipleBind node, String question) {
		// TODO Auto-generated method stub
		super.caseASetMultipleBind(node, question);
	}


	@Override
	public void caseATypeMultipleBind(ATypeMultipleBind node, String question) {
		// TODO Auto-generated method stub
		super.caseATypeMultipleBind(node, question);
	}


	@Override
	public void defaultPPatternBind(PPatternBind node, String question) {
		// TODO Auto-generated method stub
		super.defaultPPatternBind(node, question);
	}


	@Override
	public void caseADefPatternBind(ADefPatternBind node, String question) {
		// TODO Auto-generated method stub
		super.caseADefPatternBind(node, question);
	}


	@Override
	public void defaultPModules(PModules node, String question) {
		// TODO Auto-generated method stub
		super.defaultPModules(node, question);
	}


	@Override
	public void caseAModuleModules(AModuleModules node, String question) {
		// TODO Auto-generated method stub
		super.caseAModuleModules(node, question);
	}


	@Override
	public void defaultPImports(PImports node, String question) {
		// TODO Auto-generated method stub
		super.defaultPImports(node, question);
	}


	@Override
	public void caseAModuleImports(AModuleImports node, String question) {
		// TODO Auto-generated method stub
		super.caseAModuleImports(node, question);
	}


	@Override
	public void caseAFromModuleImports(AFromModuleImports node, String question) {
		// TODO Auto-generated method stub
		super.caseAFromModuleImports(node, question);
	}


	@Override
	public void defaultPImport(PImport node, String question) {
		// TODO Auto-generated method stub
		super.defaultPImport(node, question);
	}


	@Override
	public void caseAAllImport(AAllImport node, String question) {
		// TODO Auto-generated method stub
		super.caseAAllImport(node, question);
	}


	@Override
	public void caseATypeImport(ATypeImport node, String question) {
		// TODO Auto-generated method stub
		super.caseATypeImport(node, question);
	}


	@Override
	public void defaultSValueImport(SValueImport node, String question) {
		// TODO Auto-generated method stub
		super.defaultSValueImport(node, question);
	}


	@Override
	public void caseAValueValueImport(AValueValueImport node, String question) {
		// TODO Auto-generated method stub
		super.caseAValueValueImport(node, question);
	}


	@Override
	public void caseAFunctionValueImport(AFunctionValueImport node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAFunctionValueImport(node, question);
	}


	@Override
	public void caseAOperationValueImport(AOperationValueImport node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAOperationValueImport(node, question);
	}


	@Override
	public void defaultPExports(PExports node, String question) {
		// TODO Auto-generated method stub
		super.defaultPExports(node, question);
	}


	@Override
	public void caseAModuleExports(AModuleExports node, String question) {
		// TODO Auto-generated method stub
		super.caseAModuleExports(node, question);
	}


	@Override
	public void defaultPExport(PExport node, String question) {
		// TODO Auto-generated method stub
		super.defaultPExport(node, question);
	}


	@Override
	public void caseAAllExport(AAllExport node, String question) {
		// TODO Auto-generated method stub
		super.caseAAllExport(node, question);
	}


	@Override
	public void caseAFunctionExport(AFunctionExport node, String question) {
		// TODO Auto-generated method stub
		super.caseAFunctionExport(node, question);
	}


	@Override
	public void caseAOperationExport(AOperationExport node, String question) {
		// TODO Auto-generated method stub
		super.caseAOperationExport(node, question);
	}


	@Override
	public void caseATypeExport(ATypeExport node, String question) {
		// TODO Auto-generated method stub
		super.caseATypeExport(node, question);
	}


	@Override
	public void caseAValueExport(AValueExport node, String question) {
		// TODO Auto-generated method stub
		super.caseAValueExport(node, question);
	}


	@Override
	public void defaultPStm(PStm node, String question) {
		// TODO Auto-generated method stub
		super.defaultPStm(node, question);
	}


	@Override
	public void caseAAlwaysStm(AAlwaysStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAAlwaysStm(node, question);
	}


	@Override
	public void caseAAssignmentStm(AAssignmentStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAAssignmentStm(node, question);
	}


	@Override
	public void caseAAtomicStm(AAtomicStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAAtomicStm(node, question);
	}


	@Override
	public void caseACallObjectStm(ACallObjectStm node, String question) {
		// TODO Auto-generated method stub
		super.caseACallObjectStm(node, question);
	}


	@Override
	public void caseACallStm(ACallStm node, String question) {
		// TODO Auto-generated method stub
		super.caseACallStm(node, question);
	}


	@Override
	public void caseACasesStm(ACasesStm node, String question) {
		// TODO Auto-generated method stub
		super.caseACasesStm(node, question);
	}


	@Override
	public void caseAClassInvariantStm(AClassInvariantStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAClassInvariantStm(node, question);
	}


	@Override
	public void caseAElseIfStm(AElseIfStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAElseIfStm(node, question);
	}


	@Override
	public void caseAErrorStm(AErrorStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAErrorStm(node, question);
	}


	@Override
	public void caseAExitStm(AExitStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAExitStm(node, question);
	}


	@Override
	public void caseAForAllStm(AForAllStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAForAllStm(node, question);
	}


	@Override
	public void caseAForIndexStm(AForIndexStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAForIndexStm(node, question);
	}


	@Override
	public void caseAForPatternBindStm(AForPatternBindStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAForPatternBindStm(node, question);
	}


	@Override
	public void caseAIfStm(AIfStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAIfStm(node, question);
	}


	@Override
	public void caseALetBeStStm(ALetBeStStm node, String question) {
		// TODO Auto-generated method stub
		super.caseALetBeStStm(node, question);
	}


	@Override
	public void defaultSLetDefStm(SLetDefStm node, String question) {
		// TODO Auto-generated method stub
		super.defaultSLetDefStm(node, question);
	}


	@Override
	public void caseANotYetSpecifiedStm(ANotYetSpecifiedStm node,
			String question) {
		// TODO Auto-generated method stub
		super.caseANotYetSpecifiedStm(node, question);
	}


	@Override
	public void caseAReturnStm(AReturnStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAReturnStm(node, question);
	}


	@Override
	public void defaultSSimpleBlockStm(SSimpleBlockStm node, String question) {
		// TODO Auto-generated method stub
		super.defaultSSimpleBlockStm(node, question);
	}


	@Override
	public void caseASkipStm(ASkipStm node, String question) {
		// TODO Auto-generated method stub
		super.caseASkipStm(node, question);
	}


	@Override
	public void caseASpecificationStm(ASpecificationStm node, String question) {
		// TODO Auto-generated method stub
		super.caseASpecificationStm(node, question);
	}


	@Override
	public void caseAStartStm(AStartStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAStartStm(node, question);
	}


	@Override
	public void caseASubclassResponsibilityStm(ASubclassResponsibilityStm node,
			String question) {
		// TODO Auto-generated method stub
		super.caseASubclassResponsibilityStm(node, question);
	}


	@Override
	public void caseATrapStm(ATrapStm node, String question) {
		// TODO Auto-generated method stub
		super.caseATrapStm(node, question);
	}


	@Override
	public void caseAWhileStm(AWhileStm node, String question) {
		// TODO Auto-generated method stub
		super.caseAWhileStm(node, question);
	}


	@Override
	public void caseADefLetDefStm(ADefLetDefStm node, String question) {
		// TODO Auto-generated method stub
		super.caseADefLetDefStm(node, question);
	}


	@Override
	public void caseABlockSimpleBlockStm(ABlockSimpleBlockStm node,
			String question) {
		// TODO Auto-generated method stub
		super.caseABlockSimpleBlockStm(node, question);
	}


	@Override
	public void caseANonDeterministicSimpleBlockStm(
			ANonDeterministicSimpleBlockStm node, String question) {
		// TODO Auto-generated method stub
		super.caseANonDeterministicSimpleBlockStm(node, question);
	}


	@Override
	public void defaultPStateDesignator(PStateDesignator node, String question) {
		// TODO Auto-generated method stub
		super.defaultPStateDesignator(node, question);
	}


	@Override
	public void caseAFieldStateDesignator(AFieldStateDesignator node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAFieldStateDesignator(node, question);
	}


	@Override
	public void caseAIdentifierStateDesignator(AIdentifierStateDesignator node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAIdentifierStateDesignator(node, question);
	}


	@Override
	public void caseAMapSeqStateDesignator(AMapSeqStateDesignator node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAMapSeqStateDesignator(node, question);
	}


	@Override
	public void defaultPObjectDesignator(PObjectDesignator node, String question) {
		// TODO Auto-generated method stub
		super.defaultPObjectDesignator(node, question);
	}


	@Override
	public void caseAApplyObjectDesignator(AApplyObjectDesignator node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAApplyObjectDesignator(node, question);
	}


	@Override
	public void caseAFieldObjectDesignator(AFieldObjectDesignator node,
			String question) {
		// TODO Auto-generated method stub
		super.caseAFieldObjectDesignator(node, question);
	}


	@Override
	public void caseAIdentifierObjectDesignator(
			AIdentifierObjectDesignator node, String question) {
		// TODO Auto-generated method stub
		super.caseAIdentifierObjectDesignator(node, question);
	}


	@Override
	public void caseANewObjectDesignator(ANewObjectDesignator node,
			String question) {
		// TODO Auto-generated method stub
		super.caseANewObjectDesignator(node, question);
	}


	@Override
	public void caseASelfObjectDesignator(ASelfObjectDesignator node,
			String question) {
		// TODO Auto-generated method stub
		super.caseASelfObjectDesignator(node, question);
	}


	@Override
	public void defaultPAlternativeStm(PAlternativeStm node, String question) {
		// TODO Auto-generated method stub
		super.defaultPAlternativeStm(node, question);
	}


	@Override
	public void caseACaseAlternativeStm(ACaseAlternativeStm node,
			String question) {
		// TODO Auto-generated method stub
		super.caseACaseAlternativeStm(node, question);
	}


	@Override
	public void defaultPStmtAlternative(PStmtAlternative node, String question) {
		// TODO Auto-generated method stub
		super.defaultPStmtAlternative(node, question);
	}


	@Override
	public void caseATixeStmtAlternative(ATixeStmtAlternative node,
			String question) {
		// TODO Auto-generated method stub
		super.caseATixeStmtAlternative(node, question);
	}


	@Override
	public void defaultPClause(PClause node, String question) {
		// TODO Auto-generated method stub
		super.defaultPClause(node, question);
	}


	@Override
	public void caseAExternalClause(AExternalClause node, String question) {
		// TODO Auto-generated method stub
		super.caseAExternalClause(node, question);
	}


	@Override
	public void defaultPCase(PCase node, String question) {
		// TODO Auto-generated method stub
		super.defaultPCase(node, question);
	}


	@Override
	public void caseAErrorCase(AErrorCase node, String question) {
		// TODO Auto-generated method stub
		super.caseAErrorCase(node, question);
	}


	@Override
	public void defaultINode(INode node, String question) {
		// TODO Auto-generated method stub
		super.defaultINode(node, question);
	}


	@Override
	public void defaultIToken(IToken node, String question) {
		// TODO Auto-generated method stub
		super.defaultIToken(node, question);
	}
	
}
