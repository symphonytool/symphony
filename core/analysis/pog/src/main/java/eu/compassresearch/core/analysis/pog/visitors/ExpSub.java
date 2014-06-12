package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.*;
import org.overture.ast.node.INode;
import org.overture.ast.statements.ACasesStm;
import org.overture.ast.types.AFieldField;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;

public class ExpSub extends QuestionAnswerCMLAdaptor<Substitutions, PExp> {

	@Override
	public PExp caseABracketedExp(ABracketedExp node, Substitutions question)
			throws AnalysisException {
		PExp sub = node.getExpression().apply(this, question);
		node.setExpression(sub);
		return node;
	}

	@Override
	public PExp caseAAbsoluteUnaryExp(AAbsoluteUnaryExp node,
			Substitutions question) throws AnalysisException {
		PExp sub = node.getExp().apply(this, question);
		node.setExp(sub);
		return node;
	}

	@Override
	public PExp caseAAndBooleanBinaryExp(AAndBooleanBinaryExp node,
			Substitutions question) throws AnalysisException {
		PExp subl = node.getLeft().apply(this,question);
		PExp subr = node.getRight().apply(this,question);
		node.setLeft(subl);
		node.setRight(subr);
		return node;

	}

	@Override
	public PExp caseAApplyExp(AApplyExp node, Substitutions question)
			throws AnalysisException {

		// List<PExp> rs = new LinkedList<PExp>();
		// for (PExp p : node.getArgs()){
		// rs.add(p.apply(this,question));
		// }
		//
		// PExp combi = question.combine(rs);
		//
		//
		// node.get
		// TODO Auto-generated method stub
		return super.caseAApplyExp(node, question);
	}

	@Override
	public PExp caseABooleanConstExp(ABooleanConstExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseABooleanConstExp(node, question);
	}

	@Override
	public PExp caseACardinalityUnaryExp(ACardinalityUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseACardinalityUnaryExp(node, question);
	}

	@Override
	public PExp caseACasesExp(ACasesExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseACasesExp(node, question);
	}

	@Override
	public PExp caseACasesStm(ACasesStm node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseACasesStm(node, question);
	}

	@Override
	public PExp caseACharLiteralExp(ACharLiteralExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseACharLiteralExp(node, question);
	}

	@Override
	public PExp caseACompBinaryExp(ACompBinaryExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseACompBinaryExp(node, question);
	}

	@Override
	public PExp caseADefExp(ADefExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseADefExp(node, question);
	}

	@Override
	public PExp caseADistConcatUnaryExp(ADistConcatUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseADistConcatUnaryExp(node, question);
	}

	@Override
	public PExp caseADistIntersectUnaryExp(ADistIntersectUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseADistIntersectUnaryExp(node, question);
	}

	@Override
	public PExp caseADistMergeUnaryExp(ADistMergeUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseADistMergeUnaryExp(node, question);
	}

	@Override
	public PExp caseADistUnionUnaryExp(ADistUnionUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseADistUnionUnaryExp(node, question);
	}

	@Override
	public PExp caseADivNumericBinaryExp(ADivNumericBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseADivNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseADivideNumericBinaryExp(ADivideNumericBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseADivideNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseADomainResByBinaryExp(ADomainResByBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseADomainResByBinaryExp(node, question);
	}

	@Override
	public PExp caseADomainResToBinaryExp(ADomainResToBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseADomainResToBinaryExp(node, question);
	}

	@Override
	public PExp caseAElementsUnaryExp(AElementsUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAElementsUnaryExp(node, question);
	}

	@Override
	public PExp caseAElseIfExp(AElseIfExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAElseIfExp(node, question);
	}

	@Override
	public PExp caseAEqualsBinaryExp(AEqualsBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAEqualsBinaryExp(node, question);
	}

	@Override
	public PExp caseAEquivalentBooleanBinaryExp(
			AEquivalentBooleanBinaryExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAEquivalentBooleanBinaryExp(node, question);
	}

	@Override
	public PExp caseAExists1Exp(AExists1Exp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAExists1Exp(node, question);
	}

	@Override
	public PExp caseAExistsExp(AExistsExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAExistsExp(node, question);
	}

	@Override
	public PExp caseAFieldExp(AFieldExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAFieldExp(node, question);
	}

	@Override
	public PExp caseAFieldField(AFieldField node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAFieldField(node, question);
	}

	@Override
	public PExp caseAFieldNumberExp(AFieldNumberExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAFieldNumberExp(node, question);
	}

	@Override
	public PExp caseAFloorUnaryExp(AFloorUnaryExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAFloorUnaryExp(node, question);
	}

	@Override
	public PExp caseAForAllExp(AForAllExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAForAllExp(node, question);
	}

	@Override
	public PExp caseAGreaterEqualNumericBinaryExp(
			AGreaterEqualNumericBinaryExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAGreaterEqualNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseAGreaterNumericBinaryExp(AGreaterNumericBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAGreaterNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseAHeadUnaryExp(AHeadUnaryExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAHeadUnaryExp(node, question);
	}

	@Override
	public PExp caseAHistoryExp(AHistoryExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAHistoryExp(node, question);
	}

	@Override
	public PExp caseAIfExp(AIfExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAIfExp(node, question);
	}

	@Override
	public PExp caseAImpliesBooleanBinaryExp(AImpliesBooleanBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAImpliesBooleanBinaryExp(node, question);
	}

	@Override
	public PExp caseAInSetBinaryExp(AInSetBinaryExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAInSetBinaryExp(node, question);
	}

	@Override
	public PExp caseAIndicesUnaryExp(AIndicesUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAIndicesUnaryExp(node, question);
	}

	@Override
	public PExp caseAIntLiteralExp(AIntLiteralExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAIntLiteralExp(node, question);
	}

	@Override
	public PExp caseAIotaExp(AIotaExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAIotaExp(node, question);
	}

	@Override
	public PExp caseAIsExp(AIsExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAIsExp(node, question);
	}

	@Override
	public PExp caseAIsOfBaseClassExp(AIsOfBaseClassExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAIsOfBaseClassExp(node, question);
	}

	@Override
	public PExp caseAIsOfClassExp(AIsOfClassExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAIsOfClassExp(node, question);
	}

	@Override
	public PExp caseALambdaExp(ALambdaExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseALambdaExp(node, question);
	}

	@Override
	public PExp caseALenUnaryExp(ALenUnaryExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseALenUnaryExp(node, question);
	}

	@Override
	public PExp caseALessEqualNumericBinaryExp(ALessEqualNumericBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseALessEqualNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseALessNumericBinaryExp(ALessNumericBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseALessNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseALetBeStExp(ALetBeStExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseALetBeStExp(node, question);
	}

	@Override
	public PExp caseALetDefExp(ALetDefExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseALetDefExp(node, question);
	}

	@Override
	public PExp caseAMapCompMapExp(AMapCompMapExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMapCompMapExp(node, question);
	}

	@Override
	public PExp caseAMapDomainUnaryExp(AMapDomainUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMapDomainUnaryExp(node, question);
	}

	@Override
	public PExp caseAMapEnumMapExp(AMapEnumMapExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMapEnumMapExp(node, question);
	}

	@Override
	public PExp caseAMapInverseUnaryExp(AMapInverseUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMapInverseUnaryExp(node, question);
	}

	@Override
	public PExp caseAMapRangeUnaryExp(AMapRangeUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMapRangeUnaryExp(node, question);
	}

	@Override
	public PExp caseAMapUnionBinaryExp(AMapUnionBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMapUnionBinaryExp(node, question);
	}

	@Override
	public PExp caseAMapletExp(AMapletExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMapletExp(node, question);
	}

	@Override
	public PExp caseAMkBasicExp(AMkBasicExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMkBasicExp(node, question);
	}

	@Override
	public PExp caseAMkTypeExp(AMkTypeExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMkTypeExp(node, question);
	}

	@Override
	public PExp caseAModNumericBinaryExp(AModNumericBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAModNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseANarrowExp(ANarrowExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseANarrowExp(node, question);
	}

	@Override
	public PExp caseANewExp(ANewExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseANewExp(node, question);
	}

	@Override
	public PExp caseANilExp(ANilExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseANilExp(node, question);
	}

	@Override
	public PExp caseANotEqualBinaryExp(ANotEqualBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseANotEqualBinaryExp(node, question);
	}

	@Override
	public PExp caseANotInSetBinaryExp(ANotInSetBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseANotInSetBinaryExp(node, question);
	}

	@Override
	public PExp caseANotUnaryExp(ANotUnaryExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseANotUnaryExp(node, question);
	}

	@Override
	public PExp caseANotYetSpecifiedExp(ANotYetSpecifiedExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseANotYetSpecifiedExp(node, question);
	}

	@Override
	public PExp caseAOrBooleanBinaryExp(AOrBooleanBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAOrBooleanBinaryExp(node, question);
	}

	@Override
	public PExp caseAPlusNumericBinaryExp(APlusNumericBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAPlusNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseAPlusPlusBinaryExp(APlusPlusBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAPlusPlusBinaryExp(node, question);
	}

	@Override
	public PExp caseAPostOpExp(APostOpExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAPostOpExp(node, question);
	}

	@Override
	public PExp caseAPowerSetUnaryExp(APowerSetUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAPowerSetUnaryExp(node, question);
	}

	@Override
	public PExp caseAPreExp(APreExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAPreExp(node, question);
	}

	@Override
	public PExp caseAPreOpExp(APreOpExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAPreOpExp(node, question);
	}

	@Override
	public PExp caseAProperSubsetBinaryExp(AProperSubsetBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAProperSubsetBinaryExp(node, question);
	}

	@Override
	public PExp caseAQuoteLiteralExp(AQuoteLiteralExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAQuoteLiteralExp(node, question);
	}

	@Override
	public PExp caseARangeResByBinaryExp(ARangeResByBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseARangeResByBinaryExp(node, question);
	}

	@Override
	public PExp caseARangeResToBinaryExp(ARangeResToBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseARangeResToBinaryExp(node, question);
	}

	@Override
	public PExp caseARealLiteralExp(ARealLiteralExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseARealLiteralExp(node, question);
	}

	@Override
	public PExp caseARemNumericBinaryExp(ARemNumericBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseARemNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseAReverseUnaryExp(AReverseUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAReverseUnaryExp(node, question);
	}

	@Override
	public PExp caseASameBaseClassExp(ASameBaseClassExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASameBaseClassExp(node, question);
	}

	@Override
	public PExp caseASameClassExp(ASameClassExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASameClassExp(node, question);
	}

	@Override
	public PExp caseASelfExp(ASelfExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASelfExp(node, question);
	}

	@Override
	public PExp caseASeqCompSeqExp(ASeqCompSeqExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASeqCompSeqExp(node, question);
	}

	@Override
	public PExp caseASeqConcatBinaryExp(ASeqConcatBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASeqConcatBinaryExp(node, question);
	}

	@Override
	public PExp caseASeqEnumSeqExp(ASeqEnumSeqExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASeqEnumSeqExp(node, question);
	}

	@Override
	public PExp caseASetCompSetExp(ASetCompSetExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASetCompSetExp(node, question);
	}

	@Override
	public PExp caseASetDifferenceBinaryExp(ASetDifferenceBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASetDifferenceBinaryExp(node, question);
	}

	@Override
	public PExp caseASetEnumSetExp(ASetEnumSetExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASetEnumSetExp(node, question);
	}

	@Override
	public PExp caseASetIntersectBinaryExp(ASetIntersectBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASetIntersectBinaryExp(node, question);
	}

	@Override
	public PExp caseASetRangeSetExp(ASetRangeSetExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASetRangeSetExp(node, question);
	}

	@Override
	public PExp caseASetUnionBinaryExp(ASetUnionBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASetUnionBinaryExp(node, question);
	}

	@Override
	public PExp caseAStarStarBinaryExp(AStarStarBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAStarStarBinaryExp(node, question);
	}

	@Override
	public PExp caseAStateInitExp(AStateInitExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAStateInitExp(node, question);
	}

	@Override
	public PExp caseAStringLiteralExp(AStringLiteralExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAStringLiteralExp(node, question);
	}

	@Override
	public PExp caseASubclassResponsibilityExp(ASubclassResponsibilityExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASubclassResponsibilityExp(node, question);
	}

	@Override
	public PExp caseASubseqExp(ASubseqExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASubseqExp(node, question);
	}

	@Override
	public PExp caseASubsetBinaryExp(ASubsetBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASubsetBinaryExp(node, question);
	}

	@Override
	public PExp caseASubtractNumericBinaryExp(ASubtractNumericBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASubtractNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseATailUnaryExp(ATailUnaryExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseATailUnaryExp(node, question);
	}

	@Override
	public PExp caseAThreadIdExp(AThreadIdExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAThreadIdExp(node, question);
	}

	@Override
	public PExp caseATimeExp(ATimeExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseATimeExp(node, question);
	}

	@Override
	public PExp caseATimesNumericBinaryExp(ATimesNumericBinaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseATimesNumericBinaryExp(node, question);
	}

	@Override
	public PExp caseATupleExp(ATupleExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseATupleExp(node, question);
	}

	@Override
	public PExp caseAUnaryMinusUnaryExp(AUnaryMinusUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAUnaryMinusUnaryExp(node, question);
	}

	@Override
	public PExp caseAUnaryPlusUnaryExp(AUnaryPlusUnaryExp node,
			Substitutions question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAUnaryPlusUnaryExp(node, question);
	}

	@Override
	public PExp caseAUndefinedExp(AUndefinedExp node, Substitutions question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAUndefinedExp(node, question);
	}

	@Override
	public PExp caseAVariableExp(AVariableExp node, Substitutions question)
			throws AnalysisException {
		if (question.containsKey(node)) {
			return question.get(node);
		}
		return node;
	}
	
	@Override
	public PExp defaultPExp(PExp node, Substitutions question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PExp createNewReturnValue(Object arg0, Substitutions arg1)
			throws AnalysisException {
		throw new AnalysisException(
				"Substitution visitor applied to non-expression object "
						+ arg0.toString());
	}

	@Override
	public PExp createNewReturnValue(INode arg0, Substitutions arg1)
			throws AnalysisException {
		throw new AnalysisException(
				"Substitution visitor applied to non-expression object "
						+ arg0.toString());
	}

}
