package eu.compassresearch.core.typechecker;

import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.ast.expressions.*;
import org.overturetool.vdmj.lex.LexBooleanToken;
import org.overturetool.vdmj.lex.LexCharacterToken;
import org.overturetool.vdmj.lex.LexIdentifierToken;
import org.overturetool.vdmj.lex.LexIntegerToken;

public class TCExpressionVisitor extends DepthFirstAnalysisAdaptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509187123701383525L;

	@Override
	public void caseAAbsoluteUnaryExp(AAbsoluteUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseAAbsoluteUnaryExp(node);
	}

	@Override
	public void caseAApplyExp(AApplyExp arg0) {
		// TODO Auto-generated method stub
		super.caseAApplyExp(arg0);
	}
	
	@Override
	public void caseABracketedExp(ABracketedExp node) {
		// TODO Auto-generated method stub
		super.caseABracketedExp(node);
	}

	@Override
	public void caseACasesExp(ACasesExp arg0) {
		// TODO Auto-generated method stub
		super.caseACasesExp(arg0);
	}

	@Override
	public void caseACompBinaryExp(ACompBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseACompBinaryExp(node);
	}

	@Override
	public void caseACompChansetSetExp(ACompChansetSetExp arg0) {
		// TODO Auto-generated method stub
		super.caseACompChansetSetExp(arg0);
	}

	@Override
	public void caseADefExp(ADefExp arg0) {
		// TODO Auto-generated method stub
		super.caseADefExp(arg0);
	}

	@Override
	public void caseADistConcatUnaryExp(ADistConcatUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseADistConcatUnaryExp(node);
	}

	@Override
	public void caseADistIntersectUnaryExp(ADistIntersectUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseADistIntersectUnaryExp(node);
	}

	@Override
	public void caseADistMergeUnaryExp(ADistMergeUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseADistMergeUnaryExp(node);
	}

	@Override
	public void caseADistUnionUnaryExp(ADistUnionUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseADistUnionUnaryExp(node);
	}

	@Override
	public void caseADivNumericBinaryExp(ADivNumericBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseADivNumericBinaryExp(node);
	}

	@Override
	public void caseADivideNumericBinaryExp(ADivideNumericBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseADivideNumericBinaryExp(node);
	}

	@Override
	public void caseADomainResByBinaryExp(ADomainResByBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseADomainResByBinaryExp(node);
	}

	@Override
	public void caseADomainResToBinaryExp(ADomainResToBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseADomainResToBinaryExp(node);
	}

	@Override
	public void caseAElementsUnaryExp(AElementsUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseAElementsUnaryExp(node);
	}

	@Override
	public void caseAElseIfExp(AElseIfExp node) {
		// TODO Auto-generated method stub
		super.caseAElseIfExp(node);
	}

	@Override
	public void caseAEnumChansetSetExp(AEnumChansetSetExp node) {
		// TODO Auto-generated method stub
		super.caseAEnumChansetSetExp(node);
	}

	@Override
	public void caseAEqualsBinaryExp(AEqualsBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseAEqualsBinaryExp(node);
	}

	@Override
	public void caseAEquivalentBooleanBinaryExp(AEquivalentBooleanBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseAEquivalentBooleanBinaryExp(node);
	}

	@Override
	public void caseAExists1Exp(AExists1Exp node) {
		// TODO Auto-generated method stub
		super.caseAExists1Exp(node);
	}

	@Override
	public void caseAExistsExp(AExistsExp arg0) {
		// TODO Auto-generated method stub
		super.caseAExistsExp(arg0);
	}

	@Override
	public void caseAFieldExp(AFieldExp node) {
		// TODO Auto-generated method stub
		super.caseAFieldExp(node);
	}

	@Override
	public void caseAFieldNumberExp(AFieldNumberExp node) {
		// TODO Auto-generated method stub
		super.caseAFieldNumberExp(node);
	}

	@Override
	public void caseAFloorUnaryExp(AFloorUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseAFloorUnaryExp(node);
	}

	@Override
	public void caseAForAllExp(AForAllExp arg0) {
		// TODO Auto-generated method stub
		super.caseAForAllExp(arg0);
	}

	@Override
	public void caseAFuncInstatiationExp(AFuncInstatiationExp arg0) {
		// TODO Auto-generated method stub
		super.caseAFuncInstatiationExp(arg0);
	}

	@Override
	public void caseAHeadUnaryExp(AHeadUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseAHeadUnaryExp(node);
	}

	@Override
	public void caseAHistoryExp(AHistoryExp node) {
		// TODO Auto-generated method stub
		super.caseAHistoryExp(node);
	}

	@Override
	public void caseAIdentifierChansetSetExp(AIdentifierChansetSetExp node) {
		// TODO Auto-generated method stub
		super.caseAIdentifierChansetSetExp(node);
	}

	@Override
	public void caseAIfExp(AIfExp arg0) {
		// TODO Auto-generated method stub
		super.caseAIfExp(arg0);
	}

	@Override
	public void caseAImpliesBooleanBinaryExp(AImpliesBooleanBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseAImpliesBooleanBinaryExp(node);
	}

	@Override
	public void caseAInSetBinaryExp(AInSetBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseAInSetBinaryExp(node);
	}

	@Override
	public void caseAIndicesUnaryExp(AIndicesUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseAIndicesUnaryExp(node);
	}

	@Override
	public void caseAIotaExp(AIotaExp node) {
		// TODO Auto-generated method stub
		super.caseAIotaExp(node);
	}

	@Override
	public void caseAIsExp(AIsExp node) {
		// TODO Auto-generated method stub
		super.caseAIsExp(node);
	}

	@Override
	public void caseAIsOfBaseClassExp(AIsOfBaseClassExp node) {
		// TODO Auto-generated method stub
		super.caseAIsOfBaseClassExp(node);
	}

	@Override
	public void caseAIsOfClassExp(AIsOfClassExp node) {
		// TODO Auto-generated method stub
		super.caseAIsOfClassExp(node);
	}

	@Override
	public void caseALambdaExp(ALambdaExp arg0) {
		// TODO Auto-generated method stub
		super.caseALambdaExp(arg0);
	}

	@Override
	public void caseALenUnaryExp(ALenUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseALenUnaryExp(node);
	}

	@Override
	public void caseALessEqualNumericBinaryExp(ALessEqualNumericBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseALessEqualNumericBinaryExp(node);
	}

	@Override
	public void caseALessNumericBinaryExp(ALessNumericBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseALessNumericBinaryExp(node);
	}

	@Override
	public void caseALetBeStExp(ALetBeStExp node) {
		// TODO Auto-generated method stub
		super.caseALetBeStExp(node);
	}

	@Override
	public void caseALetDefExp(ALetDefExp arg0) {
		// TODO Auto-generated method stub
		super.caseALetDefExp(arg0);
	}

	@Override
	public void caseAMapCompMapExp(AMapCompMapExp arg0) {
		// TODO Auto-generated method stub
		super.caseAMapCompMapExp(arg0);
	}

	@Override
	public void caseAMapDomainUnaryExp(AMapDomainUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseAMapDomainUnaryExp(node);
	}

	@Override
	public void caseAMapEnumMapExp(AMapEnumMapExp arg0) {
		// TODO Auto-generated method stub
		super.caseAMapEnumMapExp(arg0);
	}

	@Override
	public void caseAMapInverseUnaryExp(AMapInverseUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseAMapInverseUnaryExp(node);
	}

	@Override
	public void caseAMapUnionBinaryExp(AMapUnionBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseAMapUnionBinaryExp(node);
	}

	@Override
	public void caseAMapletExp(AMapletExp node) {
		// TODO Auto-generated method stub
		super.caseAMapletExp(node);
	}

	@Override
	public void caseAMkBasicExp(AMkBasicExp node) {
		// TODO Auto-generated method stub
		super.caseAMkBasicExp(node);
	}

	@Override
	public void caseAMkTypeExp(AMkTypeExp arg0) {
		// TODO Auto-generated method stub
		super.caseAMkTypeExp(arg0);
	}

	@Override
	public void caseAModNumericBinaryExp(AModNumericBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseAModNumericBinaryExp(node);
	}

	@Override
	public void caseAModifyBinaryExp(AModifyBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseAModifyBinaryExp(node);
	}

	@Override
	public void caseAMuExp(AMuExp arg0) {
		// TODO Auto-generated method stub
		super.caseAMuExp(arg0);
	}

	@Override
	public void caseANameExp(ANameExp node) {
		// TODO Auto-generated method stub
		super.caseANameExp(node);
	}

	@Override
	public void caseANewExp(ANewExp arg0) {
		// TODO Auto-generated method stub
		super.caseANewExp(arg0);
	}

	@Override
	public void caseANilExp(ANilExp node) {
		// TODO Auto-generated method stub
		super.caseANilExp(node);
	}

	@Override
	public void caseANotEqualBinaryExp(ANotEqualBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseANotEqualBinaryExp(node);
	}

	@Override
	public void caseANotInSetBinaryExp(ANotInSetBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseANotInSetBinaryExp(node);
	}

	@Override
	public void caseANotUnaryExp(ANotUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseANotUnaryExp(node);
	}

	@Override
	public void caseANotYetSpecifiedExp(ANotYetSpecifiedExp node) {
		// TODO Auto-generated method stub
		super.caseANotYetSpecifiedExp(node);
	}

	@Override
	public void caseAOrBooleanBinaryExp(AOrBooleanBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseAOrBooleanBinaryExp(node);
	}

	@Override
	public void caseAPlusNumericBinaryExp(APlusNumericBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseAPlusNumericBinaryExp(node);
	}

	@Override
	public void caseAPostOpExp(APostOpExp arg0) {
		// TODO Auto-generated method stub
		super.caseAPostOpExp(arg0);
	}

	@Override
	public void caseAPreExp(APreExp arg0) {
		// TODO Auto-generated method stub
		super.caseAPreExp(arg0);
	}

	@Override
	public void caseAPreOpExp(APreOpExp arg0) {
		// TODO Auto-generated method stub
		super.caseAPreOpExp(arg0);
	}

	@Override
	public void caseAQuoteLiteralSymbolicLiteralExp(
			AQuoteLiteralSymbolicLiteralExp node) {
		// TODO Auto-generated method stub
		super.caseAQuoteLiteralSymbolicLiteralExp(node);
	}

	@Override
	public void caseARangeResByBinaryExp(ARangeResByBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseARangeResByBinaryExp(node);
	}

	@Override
	public void caseARangeResToBinaryExp(ARangeResToBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseARangeResToBinaryExp(node);
	}

	@Override
	public void caseARealLiteralSymbolicLiteralExp(
			ARealLiteralSymbolicLiteralExp node) {
		// TODO Auto-generated method stub
		super.caseARealLiteralSymbolicLiteralExp(node);
	}

	@Override
	public void caseARecordExp(ARecordExp arg0) {
		// TODO Auto-generated method stub
		super.caseARecordExp(arg0);
	}

	@Override
	public void caseARemNumericBinaryExp(ARemNumericBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseARemNumericBinaryExp(node);
	}

	@Override
	public void caseAReverseUnaryExp(AReverseUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseAReverseUnaryExp(node);
	}

	@Override
	public void caseASameBaseClassExp(ASameBaseClassExp node) {
		// TODO Auto-generated method stub
		super.caseASameBaseClassExp(node);
	}

	@Override
	public void caseASameClassExp(ASameClassExp node) {
		// TODO Auto-generated method stub
		super.caseASameClassExp(node);
	}

	@Override
	public void caseASelfExp(ASelfExp node) {
		// TODO Auto-generated method stub
		super.caseASelfExp(node);
	}

	@Override
	public void caseASeqCompSeqExp(ASeqCompSeqExp node) {
		// TODO Auto-generated method stub
		super.caseASeqCompSeqExp(node);
	}

	@Override
	public void caseASeqConcatBinaryExp(ASeqConcatBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseASeqConcatBinaryExp(node);
	}

	@Override
	public void caseASeqEnumSeqExp(ASeqEnumSeqExp arg0) {
		// TODO Auto-generated method stub
		super.caseASeqEnumSeqExp(arg0);
	}

	@Override
	public void caseASetCompSetExp(ASetCompSetExp arg0) {
		// TODO Auto-generated method stub
		super.caseASetCompSetExp(arg0);
	}

	@Override
	public void caseASetDifferenceBinaryExp(ASetDifferenceBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseASetDifferenceBinaryExp(node);
	}

	@Override
	public void caseASetEnumSetExp(ASetEnumSetExp arg0) {
		// TODO Auto-generated method stub
		super.caseASetEnumSetExp(arg0);
	}

	@Override
	public void caseASetIntersectBinaryExp(ASetIntersectBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseASetIntersectBinaryExp(node);
	}

	@Override
	public void caseASetRangeSetExp(ASetRangeSetExp node) {
		// TODO Auto-generated method stub
		super.caseASetRangeSetExp(node);
	}

	@Override
	public void caseASetUnionBinaryExp(ASetUnionBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseASetUnionBinaryExp(node);
	}

	@Override
	public void caseAStarStarBinaryExp(AStarStarBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseAStarStarBinaryExp(node);
	}

	@Override
	public void caseAStateInitExp(AStateInitExp node) {
		// TODO Auto-generated method stub
		super.caseAStateInitExp(node);
	}

	@Override
	public void caseASubseqExp(ASubseqExp node) {
		// TODO Auto-generated method stub
		super.caseASubseqExp(node);
	}

	@Override
	public void caseASubsetBinaryExp(ASubsetBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseASubsetBinaryExp(node);
	}

	@Override
	public void caseASubstractNumericBinaryExp(ASubstractNumericBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseASubstractNumericBinaryExp(node);
	}

	@Override
	public void caseATailUnaryExp(ATailUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseATailUnaryExp(node);
	}

	@Override
	public void caseATextLiteralSymbolicLiteralExp(
			ATextLiteralSymbolicLiteralExp node) {
		// TODO Auto-generated method stub
		super.caseATextLiteralSymbolicLiteralExp(node);
	}

	@Override
	public void caseATimesNumericBinaryExp(ATimesNumericBinaryExp node) {
		// TODO Auto-generated method stub
		super.caseATimesNumericBinaryExp(node);
	}

	@Override
	public void caseATupleExp(ATupleExp arg0) {
		// TODO Auto-generated method stub
		super.caseATupleExp(arg0);
	}

	@Override
	public void caseATupleSelectExp(ATupleSelectExp node) {
		// TODO Auto-generated method stub
		super.caseATupleSelectExp(node);
	}

	@Override
	public void caseAUnaryMinusUnaryExp(AUnaryMinusUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseAUnaryMinusUnaryExp(node);
	}

	@Override
	public void caseAUnaryPlusUnaryExp(AUnaryPlusUnaryExp node) {
		// TODO Auto-generated method stub
		super.caseAUnaryPlusUnaryExp(node);
	}

	@Override
	public void caseAUndefinedExp(AUndefinedExp node) {
		// TODO Auto-generated method stub
		super.caseAUndefinedExp(node);
	}

	@Override
	public void caseAVariableExp(AVariableExp node) {
		// TODO Auto-generated method stub
		super.caseAVariableExp(node);
	}

	@Override
	public void caseInteger(Integer node) {
		// TODO Auto-generated method stub
		super.caseInteger(node);
	}

	@Override
	public void caseLexBooleanToken(LexBooleanToken node) {
		// TODO Auto-generated method stub
		super.caseLexBooleanToken(node);
	}

	@Override
	public void caseLexCharacterToken(LexCharacterToken node) {
		// TODO Auto-generated method stub
		super.caseLexCharacterToken(node);
	}

	@Override
	public void caseLexIdentifierToken(LexIdentifierToken node) {
		// TODO Auto-generated method stub
		super.caseLexIdentifierToken(node);
	}

	@Override
	public void caseLexIntegerToken(LexIntegerToken node) {
		// TODO Auto-generated method stub
		super.caseLexIntegerToken(node);
	}

	
	
}
