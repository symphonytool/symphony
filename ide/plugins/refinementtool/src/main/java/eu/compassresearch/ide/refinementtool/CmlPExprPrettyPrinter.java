package eu.compassresearch.ide.refinementtool;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.*;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PMultipleBind;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;

public class CmlPExprPrettyPrinter extends AnswerCMLAdaptor<String> {
	
	@Override
	public String createNewReturnValue(INode arg0) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewReturnValue(Object arg0) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	private String unaryApply(String op, SUnaryExp expr)
			throws AnalysisException {
		return op + "(" + expr.getExp().apply(this) + ")";
	}

	private String binaryApply(String op, SBinaryExp expr)
			throws AnalysisException {
		return expr.getLeft().apply(this) + " " + op + " " + expr.getRight().apply(this);
	}
	
	private String exprList(List<PExp> list) 
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		
		if (list.size() > 0) {
			sb.append(list.get(0).apply(this));
			for (int i = 1; i < list.size(); i ++) {
				sb.append(", ");
				sb.append(list.get(i).apply(this));
			}
		}
		
		return sb.toString();
	}
	
	private String multiBindList(List<PMultipleBind> list) 
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		
		if (list.size() > 0) {
			sb.append(list.get(0).toString());
			for (int i = 1; i < list.size(); i ++) {
				sb.append("; ");
				sb.append(list.get(i).toString());
			}
		}
		
		return sb.toString();
		
	}
		
	@Override
	public String caseAAbsoluteUnaryExp(AAbsoluteUnaryExp node)
			throws AnalysisException {
		return unaryApply("abs", node); 
	}

	@Override
	public String caseAApplyExp(AApplyExp node) throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append(node.getRoot().apply(this));
		sb.append("(");
		if (node.getArgs().size() > 0) {
			sb.append(node.getArgs().get(0).apply(this));
			for (int i = 1; i < node.getArgs().size(); i ++) {
				sb.append(", ");
				sb.append(node.getArgs().get(i).apply(this));
			}
		}
		sb.append(")");
		
		return sb.toString();
	
	}

	@Override
	public String caseABooleanConstExp(ABooleanConstExp node)
			throws AnalysisException {
		return node.toString();
	}

	@Override
	public String caseACardinalityUnaryExp(ACardinalityUnaryExp node)
			throws AnalysisException {
		return unaryApply("card", node);
	}

	@Override
	public String caseACasesExp(ACasesExp node) throws AnalysisException {
		// TODO Do this properly
		return node.toString();
	}

	@Override
	public String caseACharLiteralExp(ACharLiteralExp node)
			throws AnalysisException {
		return "\'" + node.getValue().toString() + "\'";
	}

	@Override
	public String caseACompBinaryExp(ACompBinaryExp node)
			throws AnalysisException {
		return binaryApply("comp", node);
	}

	@Override
	public String caseADefExp(ADefExp node) throws AnalysisException {
		// TODO Do this properly
		return node.toString();
	}

	@Override
	public String caseADistConcatUnaryExp(ADistConcatUnaryExp node)
			throws AnalysisException {
		return unaryApply("conc", node);
	}

	@Override
	public String caseADistIntersectUnaryExp(ADistIntersectUnaryExp node)
			throws AnalysisException {
		return unaryApply("dinter", node);
	}

	@Override
	public String caseADistMergeUnaryExp(ADistMergeUnaryExp node)
			throws AnalysisException {
		return unaryApply("merge", node);
	}

	@Override
	public String defaultSNumericBinaryExp(SNumericBinaryExp node)
			throws AnalysisException {
		return node.getLeft().apply(this) + " " + node.getOp().toString() + " " + node.getRight().apply(this);
	}

	@Override
	public String caseADistUnionUnaryExp(ADistUnionUnaryExp node)
			throws AnalysisException {
		return unaryApply("dunion", node);
	}

	@Override
	public String caseADomainResByBinaryExp(ADomainResByBinaryExp node)
			throws AnalysisException {
		return binaryApply("<-:", node); 
	}

	@Override
	public String caseADomainResToBinaryExp(ADomainResToBinaryExp node)
			throws AnalysisException {
		return binaryApply("<:", node);
	}

	@Override
	public String caseAElementsUnaryExp(AElementsUnaryExp node)
			throws AnalysisException {
		return unaryApply("elems", node);
	}

	@Override
	public String caseAElseIfExp(AElseIfExp node) throws AnalysisException {
		return "elseif " + node.getElseIf().apply(this) + " then " + node.getThen().apply(this);
	}

	@Override
	public String caseAEqualsBinaryExp(AEqualsBinaryExp node)
			throws AnalysisException {
		return binaryApply("=", node);
	}

	@Override
	public String caseAExists1Exp(AExists1Exp node) throws AnalysisException {
		return super.caseAExists1Exp(node);
	}

	@Override
	public String caseAExistsExp(AExistsExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAExistsExp(node);
	}

	@Override
	public String caseAFieldExp(AFieldExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return node.getObject().apply(this) + "." + node.getField().toString(); 
	}

	@Override
	public String caseAFieldNumberExp(AFieldNumberExp node)
			throws AnalysisException {
		return node.getTuple().apply(this) + ".#" + node.getField().toString();
	}

	@Override
	public String caseAFloorUnaryExp(AFloorUnaryExp node)
			throws AnalysisException {
		return unaryApply("floor", node);
	}

	@Override
	public String caseAForAllExp(AForAllExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAForAllExp(node);
	}

	@Override
	public String caseAFuncInstatiationExp(AFuncInstatiationExp node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAFuncInstatiationExp(node);
	}

	@Override
	public String caseAHeadUnaryExp(AHeadUnaryExp node)
			throws AnalysisException {
		return unaryApply("hd", node);
	}

	@Override
	public String caseAIfExp(AIfExp node) throws AnalysisException {
		return "if " + node.getTest().apply(this) 
	         + " then " + node.getThen().apply(this) 
	         + " else " + node.getElse().apply(this); 
	}

	@Override
	public String caseAAndBooleanBinaryExp(AAndBooleanBinaryExp node)
			throws AnalysisException {
		return binaryApply("and", node);
	}

	@Override
	public String caseAEquivalentBooleanBinaryExp(
			AEquivalentBooleanBinaryExp node) throws AnalysisException {
		return binaryApply("<=>", node);
	}

	@Override
	public String caseAImpliesBooleanBinaryExp(AImpliesBooleanBinaryExp node)
			throws AnalysisException {
		return binaryApply("=>", node);
	}

	@Override
	public String caseAOrBooleanBinaryExp(AOrBooleanBinaryExp node)
			throws AnalysisException {
		return binaryApply("or", node);
	}

	@Override
	public String caseAInSetBinaryExp(AInSetBinaryExp node)
			throws AnalysisException {
		return binaryApply("in set", node);
	}

	@Override
	public String caseAIndicesUnaryExp(AIndicesUnaryExp node)
			throws AnalysisException {
		return unaryApply("inds", node);
	}

	@Override
	public String caseAIntLiteralExp(AIntLiteralExp node)
			throws AnalysisException {
		return node.toString();
	}

	@Override
	public String caseAIotaExp(AIotaExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAIotaExp(node);
	}

	@Override
	public String caseAIsExp(AIsExp node) throws AnalysisException {
		return "is_" + node.getBasicType().toString() + "(" + node.getTest().apply(this) + ")";
	}

	@Override
	public String caseALambdaExp(ALambdaExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseALambdaExp(node);
	}

	@Override
	public String caseALenUnaryExp(ALenUnaryExp node) throws AnalysisException {
		return unaryApply("len", node);
	}

	@Override
	public String caseALetBeStExp(ALetBeStExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseALetBeStExp(node);
	}

	@Override
	public String caseALetDefExp(ALetDefExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseALetDefExp(node);
	}

	@Override
	public String caseAMapCompMapExp(AMapCompMapExp node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMapCompMapExp(node);
	}

	@Override
	public String caseAMapDomainUnaryExp(AMapDomainUnaryExp node)
			throws AnalysisException {
		return unaryApply("dom", node);
	}

	@Override
	public String caseAMapEnumMapExp(AMapEnumMapExp node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMapEnumMapExp(node);
	}

	@Override
	public String caseAMapInverseUnaryExp(AMapInverseUnaryExp node)
			throws AnalysisException {
		return unaryApply("inverse", node);
	}

	@Override
	public String caseAMapRangeUnaryExp(AMapRangeUnaryExp node)
			throws AnalysisException {
		return unaryApply("rng", node);
	}

	@Override
	public String caseAMapUnionBinaryExp(AMapUnionBinaryExp node)
			throws AnalysisException {
		return binaryApply("union", node);
	}

	@Override
	public String caseAMapletExp(AMapletExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMapletExp(node);
	}

	@Override
	public String caseAMkBasicExp(AMkBasicExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMkBasicExp(node);
	}

	@Override
	public String caseAMkTypeExp(AMkTypeExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAMkTypeExp(node);
	}

	@Override
	public String caseAModNumericBinaryExp(AModNumericBinaryExp node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAModNumericBinaryExp(node);
	}

	@Override
	public String caseANilExp(ANilExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseANilExp(node);
	}

	@Override
	public String caseANotEqualBinaryExp(ANotEqualBinaryExp node)
			throws AnalysisException {
		return binaryApply("<>", node);
	}

	@Override
	public String caseANotInSetBinaryExp(ANotInSetBinaryExp node)
			throws AnalysisException {
		return binaryApply("not in set", node);
	}

	@Override
	public String caseANotUnaryExp(ANotUnaryExp node) throws AnalysisException {
		return unaryApply("not", node);
	}

	@Override
	public String caseANotYetSpecifiedExp(ANotYetSpecifiedExp node)
			throws AnalysisException {
		return node.toString();
	}

	@Override
	public String caseAPlusPlusBinaryExp(APlusPlusBinaryExp node)
			throws AnalysisException {
		return binaryApply("++", node);
	}

	@Override
	public String caseAPostOpExp(APostOpExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAPostOpExp(node);
	}

	@Override
	public String caseAPreExp(APreExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAPreExp(node);
	}

	@Override
	public String caseAPreOpExp(APreOpExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAPreOpExp(node);
	}

	@Override
	public String caseAProperSubsetBinaryExp(AProperSubsetBinaryExp node)
			throws AnalysisException {
		return binaryApply("psubset", node);
	}

	@Override
	public String caseAQuoteLiteralExp(AQuoteLiteralExp node)
			throws AnalysisException {
		return node.toString();
	}

	@Override
	public String caseARangeResByBinaryExp(ARangeResByBinaryExp node)
			throws AnalysisException {
		return binaryApply(":->", node);
	}

	@Override
	public String caseARangeResToBinaryExp(ARangeResToBinaryExp node)
			throws AnalysisException {
		return binaryApply(":>", node);
	}

	@Override
	public String caseARealLiteralExp(ARealLiteralExp node)
			throws AnalysisException {
		return node.toString();
	}

	@Override
	public String caseAReverseUnaryExp(AReverseUnaryExp node)
			throws AnalysisException {
		return unaryApply("reverse", node);
	}

	@Override
	public String caseASameBaseClassExp(ASameBaseClassExp node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASameBaseClassExp(node);
	}

	@Override
	public String caseASameClassExp(ASameClassExp node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASameClassExp(node);
	}

	@Override
	public String caseASelfExp(ASelfExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASelfExp(node);
	}

	@Override
	public String caseASeqCompSeqExp(ASeqCompSeqExp node) // Sequence comprehension
			throws AnalysisException {
		return super.caseASeqCompSeqExp(node);
	}

	@Override
	public String caseASeqConcatBinaryExp(ASeqConcatBinaryExp node)
			throws AnalysisException {
		return binaryApply("^", node);
	}

	@Override
	public String caseASeqEnumSeqExp(ASeqEnumSeqExp node)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		sb.append(exprList(node.getMembers()));		
		sb.append("]");

		return sb.toString();
	}

	@Override
	public String caseASetCompSetExp(ASetCompSetExp node) // Set comprehension
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		sb.append(node.getFirst().apply(this));
		sb.append(" | ");
		
		sb.append(multiBindList(node.getBindings()));
		
		sb.append(" @ ");
		sb.append(node.getPredicate().apply(this));
		sb.append("}");
		
		return sb.toString();
	}

	@Override
	public String caseASetDifferenceBinaryExp(ASetDifferenceBinaryExp node)
			throws AnalysisException {
		return binaryApply("\\", node);
	}

	@Override
	public String caseASetEnumSetExp(ASetEnumSetExp node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASetEnumSetExp(node);
	}

	@Override
	public String caseASetIntersectBinaryExp(ASetIntersectBinaryExp node)
			throws AnalysisException {
		return binaryApply("inter", node);
	}

	@Override
	public String caseASetRangeSetExp(ASetRangeSetExp node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASetRangeSetExp(node);
	}

	@Override
	public String caseASetUnionBinaryExp(ASetUnionBinaryExp node)
			throws AnalysisException {
		return binaryApply("union", node);
	}

	@Override
	public String caseAStarStarBinaryExp(AStarStarBinaryExp node)
			throws AnalysisException {
		return binaryApply("**", node);
	}

	@Override
	public String caseAStateInitExp(AStateInitExp node)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAStateInitExp(node);
	}

	@Override
	public String caseAStringLiteralExp(AStringLiteralExp node)
			throws AnalysisException {
		return node.toString();
	}

	@Override
	public String caseASubseqExp(ASubseqExp node) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseASubseqExp(node);
	}

	@Override
	public String caseASubsetBinaryExp(ASubsetBinaryExp node)
			throws AnalysisException {
		return binaryApply("subset", node);
	}

	@Override
	public String caseATailUnaryExp(ATailUnaryExp node)
			throws AnalysisException {
		return unaryApply("tl", node);
	}

	@Override
	public String caseATupleExp(ATupleExp node) throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		sb.append(exprList(node.getArgs()));
		sb.append("]");

		return sb.toString();
	}

	@Override
	public String caseAUnaryMinusUnaryExp(AUnaryMinusUnaryExp node)
			throws AnalysisException {
		return "- " + node.getExp().apply(this); 
	}

	@Override
	public String caseAUnaryPlusUnaryExp(AUnaryPlusUnaryExp node)
			throws AnalysisException {
		return "+ " + node.getExp().apply(this);
	}

	@Override
	public String caseAUndefinedExp(AUndefinedExp node)
			throws AnalysisException {
		return node.toString();
	}

	@Override
	public String caseAVariableExp(AVariableExp node) throws AnalysisException {
		return node.toString();
	}

	
	
}
