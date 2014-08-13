package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AAbsoluteUnaryExp;
import org.overture.ast.expressions.AAndBooleanBinaryExp;
import org.overture.ast.expressions.AApplyExp;
import org.overture.ast.expressions.ABooleanConstExp;
import org.overture.ast.expressions.ACardinalityUnaryExp;
import org.overture.ast.expressions.ACasesExp;
import org.overture.ast.expressions.ACharLiteralExp;
import org.overture.ast.expressions.ACompBinaryExp;
import org.overture.ast.expressions.ADistConcatUnaryExp;
import org.overture.ast.expressions.ADistIntersectUnaryExp;
import org.overture.ast.expressions.ADistMergeUnaryExp;
import org.overture.ast.expressions.ADistUnionUnaryExp;
import org.overture.ast.expressions.ADivNumericBinaryExp;
import org.overture.ast.expressions.ADivideNumericBinaryExp;
import org.overture.ast.expressions.ADomainResByBinaryExp;
import org.overture.ast.expressions.ADomainResToBinaryExp;
import org.overture.ast.expressions.AElementsUnaryExp;
import org.overture.ast.expressions.AElseIfExp;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AEquivalentBooleanBinaryExp;
import org.overture.ast.expressions.AExists1Exp;
import org.overture.ast.expressions.AExistsExp;
import org.overture.ast.expressions.AFieldExp;
import org.overture.ast.expressions.AFieldNumberExp;
import org.overture.ast.expressions.AFloorUnaryExp;
import org.overture.ast.expressions.AForAllExp;
import org.overture.ast.expressions.AGreaterEqualNumericBinaryExp;
import org.overture.ast.expressions.AGreaterNumericBinaryExp;
import org.overture.ast.expressions.AHeadUnaryExp;
import org.overture.ast.expressions.AIfExp;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.expressions.AInSetBinaryExp;
import org.overture.ast.expressions.AIndicesUnaryExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.AIotaExp;
import org.overture.ast.expressions.AIsExp;
import org.overture.ast.expressions.ALambdaExp;
import org.overture.ast.expressions.ALenUnaryExp;
import org.overture.ast.expressions.ALessEqualNumericBinaryExp;
import org.overture.ast.expressions.ALessNumericBinaryExp;
import org.overture.ast.expressions.ALetDefExp;
import org.overture.ast.expressions.AMapCompMapExp;
import org.overture.ast.expressions.AMapDomainUnaryExp;
import org.overture.ast.expressions.AMapEnumMapExp;
import org.overture.ast.expressions.AMapInverseUnaryExp;
import org.overture.ast.expressions.AMapRangeUnaryExp;
import org.overture.ast.expressions.AMapUnionBinaryExp;
import org.overture.ast.expressions.AMapletExp;
import org.overture.ast.expressions.AMkBasicExp;
import org.overture.ast.expressions.AMkTypeExp;
import org.overture.ast.expressions.AModNumericBinaryExp;
import org.overture.ast.expressions.ANilExp;
import org.overture.ast.expressions.ANotEqualBinaryExp;
import org.overture.ast.expressions.ANotInSetBinaryExp;
import org.overture.ast.expressions.ANotUnaryExp;
import org.overture.ast.expressions.ANotYetSpecifiedExp;
import org.overture.ast.expressions.AOrBooleanBinaryExp;
import org.overture.ast.expressions.APlusNumericBinaryExp;
import org.overture.ast.expressions.APlusPlusBinaryExp;
import org.overture.ast.expressions.APostOpExp;
import org.overture.ast.expressions.APowerSetUnaryExp;
import org.overture.ast.expressions.APreExp;
import org.overture.ast.expressions.AProperSubsetBinaryExp;
import org.overture.ast.expressions.AQuoteLiteralExp;
import org.overture.ast.expressions.ARangeResByBinaryExp;
import org.overture.ast.expressions.ARangeResToBinaryExp;
import org.overture.ast.expressions.ARealLiteralExp;
import org.overture.ast.expressions.AReverseUnaryExp;
import org.overture.ast.expressions.ASeqCompSeqExp;
import org.overture.ast.expressions.ASeqConcatBinaryExp;
import org.overture.ast.expressions.ASeqEnumSeqExp;
import org.overture.ast.expressions.ASetCompSetExp;
import org.overture.ast.expressions.ASetDifferenceBinaryExp;
import org.overture.ast.expressions.ASetEnumSetExp;
import org.overture.ast.expressions.ASetIntersectBinaryExp;
import org.overture.ast.expressions.ASetRangeSetExp;
import org.overture.ast.expressions.ASetUnionBinaryExp;
import org.overture.ast.expressions.AStarStarBinaryExp;
import org.overture.ast.expressions.AStringLiteralExp;
import org.overture.ast.expressions.ASubseqExp;
import org.overture.ast.expressions.ASubsetBinaryExp;
import org.overture.ast.expressions.ASubtractNumericBinaryExp;
import org.overture.ast.expressions.ATailUnaryExp;
import org.overture.ast.expressions.ATimesNumericBinaryExp;
import org.overture.ast.expressions.ATupleExp;
import org.overture.ast.expressions.AUnaryMinusUnaryExp;
import org.overture.ast.expressions.AUnaryPlusUnaryExp;
import org.overture.ast.expressions.AUndefinedExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.ASetBind;
import org.overture.ast.patterns.ASetMultipleBind;
import org.overture.ast.patterns.ATypeBind;
import org.overture.ast.patterns.ATypeMultipleBind;
import org.overture.ast.patterns.PBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AMapMapType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.ASeq1SeqType;
import org.overture.ast.types.ASeqSeqType;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatCompVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AIdentifierVarsetExpression;
import eu.compassresearch.ast.expressions.AInterVOpVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.ASubVOpVarsetExpression;
import eu.compassresearch.ast.expressions.AUnionVOpVarsetExpression;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmExprUtil;

@SuppressWarnings("serial")
public class ThmExpStringVisitor extends
QuestionAnswerCMLAdaptor<ThmVarsContext, String> {

	private boolean isPost = false;
	
	final private QuestionAnswerCMLAdaptor<ThmVarsContext, String> thmStringVisitor;
	
	public ThmExpStringVisitor(ThmStringVisitor thmStringVisitor) {
		this.thmStringVisitor = thmStringVisitor;
	}

	public String caseACompBinaryExp(ACompBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.comp +  ex.getRight().apply(thmStringVisitor, vars) + ")";
	}

	public String caseADomainResByBinaryExp(ADomainResByBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.domainResBy +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseADomainResToBinaryExp(ADomainResToBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.domainResTo +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAEqualsBinaryExp(AEqualsBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.equals +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAInSetBinaryExp(AInSetBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.inSet +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAMapUnionBinaryExp(AMapUnionBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.mapUnion +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseANotEqualBinaryExp(ANotEqualBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.notEqual +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseANotInSetBinaryExp(ANotInSetBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.notInSet +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAPlusPlusBinaryExp(APlusPlusBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.plusPlus +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAProperSubsetBinaryExp(AProperSubsetBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.properSubset  +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseARangeResByBinaryExp(ARangeResByBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.rangeResBy +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseARangeResToBinaryExp(ARangeResToBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.rangeResTo +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseASeqConcatBinaryExp(ASeqConcatBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.seqConcat +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseASetDifferenceBinaryExp(ASetDifferenceBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.setDifference +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseASetIntersectBinaryExp(ASetIntersectBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.setIntersect +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseASetUnionBinaryExp(ASetUnionBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.setUnion +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAStarStarBinaryExp(AStarStarBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.starStar +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseASubsetBinaryExp(ASubsetBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + ThmExprUtil.subset +  ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAAbsoluteUnaryExp(AAbsoluteUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.absolute + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseACardinalityUnaryExp(ACardinalityUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.cardinality + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseADistConcatUnaryExp(ADistConcatUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.distConcat + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseADistIntersectUnaryExp(ADistIntersectUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.distIntersect + "(" +ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

//	public String caseADistMergeUnaryExp(ADistMergeUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
//		return "("+ ThmExprUtil.distMerge + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
//	}

	public String caseADistUnionUnaryExp(ADistUnionUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.distUnion + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseAElementsUnaryExp(AElementsUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.elements + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseAFloorUnaryExp(AFloorUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.floor + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseAHeadUnaryExp(AHeadUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.head + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseAIndicesUnaryExp(AIndicesUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.indices + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseALenUnaryExp(ALenUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.len + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseAMapDomainUnaryExp(AMapDomainUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.mapDomain + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseAMapInverseUnaryExp(AMapInverseUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.mapInverse + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseAMapRangeUnaryExp(AMapRangeUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.mapRange + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseANotUnaryExp(ANotUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.not + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseAPowerSetUnaryExp(APowerSetUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.powerSet + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseAReverseUnaryExp(AReverseUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.reverse + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseATailUnaryExp(ATailUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.tail + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
	}

	public String caseAUnaryMinusUnaryExp(AUnaryMinusUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "("+ ThmExprUtil.unaryMinus + ex.getExp().apply(thmStringVisitor, vars)+ ")";
	}

//	public String caseAUnaryPlusUnaryExp(AUnaryPlusUnaryExp ex, ThmVarsContext vars) throws AnalysisException{
//		return "("+ ThmExprUtil.unaryPlus + "(" + ex.getExp().apply(thmStringVisitor, vars)+ "))";
//	}

	public String caseABooleanConstExp(ABooleanConstExp ex, ThmVarsContext vars) throws AnalysisException{
		return ex.getValue().toString();
	}

	public String caseANilExp(ANilExp ex, ThmVarsContext vars) throws AnalysisException{
		return "nil";
	}

	public String caseAIfExp(AIfExp ex, ThmVarsContext vars) throws AnalysisException{
		return "if " + ex.getTest().apply(thmStringVisitor, vars) + " then " + ex.getThen().apply(thmStringVisitor, vars) + " else " + ex.getElse().apply(thmStringVisitor, vars);
	}

	public String caseACharLiteralExp(ACharLiteralExp ex, ThmVarsContext vars) throws AnalysisException{
		return "<<CHR ''" + ex.getValue().getValue() +"''>>";
	}

	public String caseAElseIfExp(AElseIfExp ex, ThmVarsContext vars) throws AnalysisException{
		return "else if " + ex.getElseIf().apply(thmStringVisitor, vars) + " then " + ex.getThen().apply(thmStringVisitor, vars);
	}

	public String caseAExistsExp(AExistsExp ex, ThmVarsContext vars) throws AnalysisException{

		LinkedList<PMultipleBind> binds = ex.getBindList();
		StringBuilder sb = new StringBuilder();
		StringBuilder endBrackets = new StringBuilder();
//		NodeNameList boundvars = new NodeNameList();
//		boundvars.addAll(bvars);
		
		for(PMultipleBind b: binds)
		{
			endBrackets.append(")");
			if (b instanceof ATypeMultipleBind)
			{
				sb.append("exists ");
				
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					sb.append(p.getName());
					vars.addBVar(p.getName());
					//If there are remaining patterns, add a ","
					if(itr.hasNext()){	
						sb.append(", ");
					}
				}
				sb.append(" : ");

				sb.append(tmb.getType().apply(thmStringVisitor, vars) + " @ ("); //USE VISITOR FOR TYPE
			}
			else if (b instanceof ASetMultipleBind)
			{
				sb.append("exists ");
				
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					sb.append(p.getName());
					vars.addBVar(p.getName());
					//If there are remaining patterns, add a ","
					if(itr.hasNext()){	
						sb.append(", ");
					}
				}
			//	sb.append(smb.getPlist().toString());
				sb.append(" in @set ");
				sb.append(smb.getSet().apply(thmStringVisitor, vars));
				sb.append(" @ (");
			}
		}
		return "(" + sb.toString() + ex.getPredicate().apply(thmStringVisitor, vars) + endBrackets.toString() + ")"; 
	}

	public String caseAExists1Exp(AExists1Exp ex, ThmVarsContext vars) throws AnalysisException{

		StringBuilder sb = new StringBuilder();
		PBind b = ex.getBind();
		if (b instanceof ATypeBind)
		{
			ATypeBind tmb = (ATypeBind) b;
			sb.append(tmb.getPattern().toString());
			vars.addBVar(((AIdentifierPattern) tmb.getPattern()).getName());
			sb.append(" : ");
			sb.append("@" + tmb.getType().toString());//USE VISITOR FOR TYPE
		}
		else if (b instanceof ASetBind)
		{
			ASetBind smb = (ASetBind) b;
			sb.append(smb.getPattern().toString());	
			vars.addBVar(((AIdentifierPattern) smb.getPattern()).getName());
			sb.append(" in @set ");
			sb.append(smb.getSet().apply(thmStringVisitor, vars));
		}
		
		return "exists1 " + sb.toString() + " @ " + ex.getPredicate().apply(thmStringVisitor, vars); 
	} 

	public String caseAForAllExp(AForAllExp ex, ThmVarsContext vars) throws AnalysisException{

		LinkedList<PMultipleBind> binds = ex.getBindList();
		StringBuilder sb = new StringBuilder();
		StringBuilder endBrackets = new StringBuilder();
			
		for(PMultipleBind b: binds)
		{
			endBrackets.append(")");
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;

				sb.append("forall ");
				
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					sb.append(p.getName());
					vars.addBVar(p.getName());
					//If there are remaining patterns, add a ","
					if(itr.hasNext()){	
						sb.append(", ");
					}
				}
				sb.append(" : ");
				sb.append(tmb.getType().apply(thmStringVisitor, vars) + " @ (");//USE VISITOR FOR TYPE
			}
			else if (b instanceof ASetMultipleBind)
			{
				sb.append("forall ");
				
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					sb.append(p.getName());
					vars.addBVar(p.getName());
					//If there are remaining patterns, add a ","
					if(itr.hasNext()){	
						sb.append(", (");
					}
				}
			//	sb.append(smb.getPlist().toString());
				sb.append(" in @set ");
				sb.append(smb.getSet().apply(thmStringVisitor, vars));
				sb.append(" @ ");
			}
		}
		return "(" + sb.toString() + ex.getPredicate().apply(thmStringVisitor, vars) + endBrackets.toString() + ")"; 
	}

	public String caseAIntLiteralExp(AIntLiteralExp ex, ThmVarsContext vars) throws AnalysisException{
		return ex.getValue().toString(); //"<<''" + i.getValue().toString() + "''>>";
	}

	public String caseAApplyExp(AApplyExp ex, ThmVarsContext vars) throws AnalysisException{
		StringBuilder sb = new StringBuilder();
		
		for (Iterator<PExp> itr = ex.getArgs().listIterator(); itr.hasNext(); ) {
			PExp e = itr.next();
			
			sb.append(e.apply(thmStringVisitor, vars));
			//If there are remaining expressions, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		
		PExp appRoot = ex.getRoot();
		String root = appRoot.apply(thmStringVisitor, vars);
		StringBuilder rootsb = new StringBuilder();
		rootsb.append(root);
		
		if (!(appRoot.getType() instanceof ANamedInvariantType || appRoot.getType() instanceof ARecordInvariantType))
		{
			//Need to remove ^ decoration from root, as can't currently determine
			//if root expression is a function variable.
			if (root.contains("^"))
			{
				int count = 0;
				while(count <2){
					int lastHat = rootsb.lastIndexOf("^");
					rootsb.deleteCharAt(lastHat);
					count++;
				}
			}	
		}
		if (appRoot.getType() instanceof AOperationType)
		{
			return rootsb.toString() + "[" + sb.toString() + "]";
		}
		else if (appRoot.getType() instanceof ASeqSeqType || appRoot.getType() instanceof ASeq1SeqType)
		{
			return rootsb.toString() + "<" + sb.toString() + ">";
		}
		else if (appRoot.getType() instanceof AMapMapType)
		{
			return rootsb.toString() + "[" + sb.toString() + "]";
		}
		else
		return rootsb.toString() + "(" + sb.toString() + ")";
	}	

	public String caseACasesExp(ACasesExp ex, ThmVarsContext vars) throws AnalysisException{	
//			PExp exp = ex.getExpression();
//			LinkedList<ACaseAlternative> cases = ex.getCases();
//			PExp others = ex.getOthers();
		//TODO: Handle cases
		return ThmExprUtil.notHandled;
	}

	public String caseAFieldExp(AFieldExp ex, ThmVarsContext vars) throws AnalysisException{
		return ex.getObject().apply(thmStringVisitor, vars) + "." + ex.getField().toString();
	}

	public String caseAFieldNumberExp(AFieldNumberExp ex, ThmVarsContext vars) throws AnalysisException{
		return ex.getTuple().apply(thmStringVisitor, vars) + ".#" + ex.getField().toString();
	}

	public String caseAIotaExp(AIotaExp ex, ThmVarsContext vars) throws AnalysisException{
		StringBuilder sb = new StringBuilder();
		PBind b = ex.getBind();
		
		if (b instanceof ATypeBind)
		{
			ATypeBind tmb = (ATypeBind) b;
			sb.append(tmb.getPattern().toString());
			vars.addBVar(((AIdentifierPattern) tmb.getPattern()).getName());
			sb.append(" : ");
			sb.append("@" + tmb.getType().toString()); //SHOULD USE TYPE VISITOR?
		}
		else if (b instanceof ASetBind)
		{
			ASetBind smb = (ASetBind) b;
			sb.append(smb.getPattern().toString());
			vars.addBVar(((AIdentifierPattern) smb.getPattern()).getName());
			sb.append(" in @set ");
			sb.append(smb.getSet().apply(thmStringVisitor, vars));
		}
		
		return "iota " + sb.toString() + " @ " + ex.getPredicate().apply(thmStringVisitor, vars);
	}
	public String caseALambdaExp(ALambdaExp ex, ThmVarsContext vars) throws AnalysisException{
		
		StringBuilder sb = new StringBuilder();
		LinkedList<ATypeBind> b = ex.getBindList();
		
		for (Iterator<ATypeBind> itr = b.listIterator(); itr.hasNext(); ) {
			ATypeBind p = itr.next();
				
			sb.append(p.getPattern().toString());
			vars.addBVar(((AIdentifierPattern) p.getPattern()).getName());
			sb.append(" : ");
			sb.append("@" + p.getType().toString()); //SHOULD USE TYPE VISITOR?
			//If there are remaining patterns, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		
		return "lambda " + sb.toString() + " @ " + ex.getExpression().apply(thmStringVisitor, vars);
	}

	public String caseALetDefExp(ALetDefExp ex, ThmVarsContext vars) throws AnalysisException{

		StringBuilder sb = new StringBuilder();
		
		LinkedList<PDefinition> ldefs = ex.getLocalDefs();
		for (PDefinition d : ldefs)
		{
			if(d.getName() != null){
				vars.addBVar(d.getName());
				sb.append(d.getName().toString());
			}	
			else if(d instanceof AValueDefinition){
				LinkedList<PDefinition> vdefs = ((AValueDefinition) d).getDefs();
				for (PDefinition v: vdefs)
				{
					vars.addBVar(v.getName());
					sb.append(v.getName().toString());
				}
			}
		}
		
		return "let " + sb + " in " + ex.getExpression().apply(thmStringVisitor, vars);
	}

	public String caseAMapletExp(AMapletExp ex, ThmVarsContext vars) throws AnalysisException{		
		return ex.getLeft().apply(thmStringVisitor, vars) + " |-> " + ex.getRight().apply(thmStringVisitor, vars);
	}

	public String caseAMkBasicExp(AMkBasicExp ex, ThmVarsContext vars) throws AnalysisException{
		
		return "mk_token(" + ex.getArg().apply(thmStringVisitor, vars) + ")";
	}

	public String caseAMkTypeExp(AMkTypeExp ex, ThmVarsContext vars) throws AnalysisException{
		StringBuilder sb = new StringBuilder();
		
		for (Iterator<PExp> itr = ex.getArgs().listIterator(); itr.hasNext(); ) {
			PExp p = itr.next();
				
			sb.append(p.apply(thmStringVisitor, vars));
			//If there are remaining patterns, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		
		return "mk_" + ex.getRecordType().getName().toString() + "(" + sb.toString() + ")";
	}


	public String caseANotYetSpecifiedExp(ANotYetSpecifiedExp ex, ThmVarsContext vars) throws AnalysisException{
		return ThmExprUtil.undefined;
	}

	public String caseAPostOpExp(APostOpExp ex, ThmVarsContext vars) throws AnalysisException{
		//TODO: Handle postop exp
		return ThmExprUtil.notHandled;
	}

	public String caseAPreExp(APreExp ex, ThmVarsContext vars) throws AnalysisException{
		//TODO: Handle pre exp
		return ThmExprUtil.notHandled;
	}

	public String caseAQuoteLiteralExp(AQuoteLiteralExp ex, ThmVarsContext vars) throws AnalysisException{
		return "<" + ex.getValue().getValue() + ">";
	}

	public String caseARealLiteralExp(ARealLiteralExp ex, ThmVarsContext vars) throws AnalysisException{
		return ex.getValue().toString();
	}

	public String caseAStringLiteralExp(AStringLiteralExp ex, ThmVarsContext vars) throws AnalysisException{
		String value = ex.getValue().toString();
		value = value.replaceAll("\"","");
		
		return "''" + value + "''";
	}

	public String caseASubseqExp(ASubseqExp ex, ThmVarsContext vars) throws AnalysisException{
		
		return ex.getSeq().apply(thmStringVisitor, vars) + "(" + ex.getFrom().apply(thmStringVisitor, vars) + ",...," + ex.getTo().apply(thmStringVisitor, vars) + ")";
	}

	public String caseATupleExp(ATupleExp ex, ThmVarsContext vars) throws AnalysisException{
		StringBuilder sb = new StringBuilder();
		
		for (Iterator<PExp> itr = ex.getArgs().listIterator(); itr.hasNext(); ) {
			PExp p = itr.next();
					
			sb.append(p.apply(thmStringVisitor, vars));
			//If there are remaining exprs, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		return "mk_(" + sb.toString() + ")";
	}

	public String caseAUndefinedExp(AUndefinedExp ex, ThmVarsContext vars) throws AnalysisException{
		return ThmExprUtil.undefined;
	}

	public String caseAVariableExp(AVariableExp ex, ThmVarsContext vars) throws AnalysisException{
		ILexNameToken varName = ex.getName();
		//Replace trailing $ that is added by the POG 
		String variableName =varName.getName().replace("$", "");
		
		
		for(ILexNameToken var : vars.getSVars()){
			if (variableName.equals(var.getName()))
			{
				if (isPost)
				{
					if (varName.isOld())
						return  "($" + variableName + ")";
					else
						return "($" + variableName + ThmExprUtil.isaAcute + ")";
				}
				else
				{
					return  "($" + variableName + ")";
				}
			}
		}
		for(ILexNameToken var : vars.getBVars()){
			if (variableName.equals(var.getName()))
			{
				return  "^" + variableName + "^";
			}
		}
		//assume is value?
		return "^" + variableName + "^";
	}

	public String caseAUnresolvedPathExp(AUnresolvedPathExp ex, ThmVarsContext vars) throws AnalysisException{
		//As this is a slightly iffy bit of syntax, we hack the Isabelle string a bit.
		//First need to check if the first element is a state variable. If it is, then use
		//$, otherwise if a bound value, use ^^, otherwise, ??
		StringBuilder sb = new StringBuilder();
		
		Iterator<ILexIdentifierToken> itr = ex.getIdentifiers().listIterator();
		ILexIdentifierToken first = itr.next();
		boolean isState = false;
//			boolean isBound = false;
		for(ILexNameToken var : vars.getSVars()){
			//Need to do getName on the LexIdentToken, as toString directly may incorporate the 
			//~ in before state
			if (first.getName().toString().equals(var.getName()))
			{
				if (isPost)
				{
					if (first.isOld())
						sb.append("($" + first.getName().toString() + ").");
					else
						sb.append("($" + first.getName().toString() + ThmExprUtil.isaAcute +").");
				}
				else
				{
					sb.append("($" + first.getName().toString()+ ").");
				}
				isState = true;
				break;
			}
		}
		if(!isState)
		{
			sb.append("(^" + first.toString() + "^).");
		}
		
		while( itr.hasNext() ) {
			ILexIdentifierToken i = itr.next();
					
			sb.append(i.getName().toString());
			//If there are remaining exprs, add a ","
			if(itr.hasNext()){	
				sb.append(".");
			}
		}
		return sb.toString();
	}

	public String caseABracketedExp(ABracketedExp ex, ThmVarsContext vars) throws AnalysisException{
		
		return "(" + ex.getExpression().apply(thmStringVisitor, vars) + ")";
	}

	public String caseAIsExp(AIsExp ex, ThmVarsContext vars) throws AnalysisException{
		
		return "(" + ex.getTest().apply(thmStringVisitor, vars) + " hasType " + ex.getBasicType().apply(thmStringVisitor, vars) + ")";
	}
	
	public String defaultPExp(PExp node, ThmVarsContext bvars)
			throws AnalysisException {		
		return ThmExprUtil.notHandled;
	}	
	



	public String caseAMapCompMapExp(AMapCompMapExp ex, ThmVarsContext vars) throws AnalysisException{

		StringBuilder bindstr = new StringBuilder();
		
		LinkedList<PMultipleBind> binds = ex.getBindings();
//		NodeNameList boundvars = new NodeNameList();
//		boundvars.addAll(bvars);
		
		for(PMultipleBind b: binds)
		{
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bindstr.append(p.getName());
					vars.addBVar(p.getName());
					//If there are remaining patterns, add a ","
					if(itr.hasNext()){	
						bindstr.append(", ");
					}
				}
				bindstr.append(" : ");
				bindstr.append("@" + tmb.getType().toString()); //SHOULD USE VISITOR TO GET THIS?
			}
			else if (b instanceof ASetMultipleBind)
			{
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bindstr.append(p.getName());
					vars.addBVar(p.getName());
					//If there are remaining patterns, add a ","
					if(itr.hasNext()){	
						bindstr.append(", ");
					}
				}
			//	sb.append(smb.getPlist().toString());
				bindstr.append(" in @set ");
				bindstr.append(smb.getSet().apply(thmStringVisitor, vars));
			}
		}
		String firstString =  ex.getFirst().apply(thmStringVisitor, vars);
		String predString =  ex.getPredicate().apply(thmStringVisitor, vars);
	//	String firstString = ThmExprUtil.getIsabelleExprStr(svars, boundvars, comp.getFirst()); 
	//	String predString = ThmExprUtil.getIsabelleExprStr(svars, boundvars, comp.getPredicate()); 
		return "{" + firstString + " | " + bindstr + " @ " + predString + "}";
	}

	public String caseAMapEnumMapExp(AMapEnumMapExp ex, ThmVarsContext vars) throws AnalysisException{

		StringBuilder sb = new StringBuilder();
		LinkedList<AMapletExp> mem = ex.getMembers();
		
		if (mem.isEmpty()) {
			sb.append("|->");
		}
		
		for (Iterator<AMapletExp> itr = mem.listIterator(); itr.hasNext(); ) {
			PExp m = itr.next();
			sb.append(m.apply(thmStringVisitor, vars));
			//If there are remaining types, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		
		return "{" + sb.toString() +"}";
		
	}
	
	public String caseASeqCompSeqExp(ASeqCompSeqExp ex, ThmVarsContext vars) throws AnalysisException{
		StringBuilder bindstr = new StringBuilder();
		
		ASetBind bind = ex.getSetBind();
	//	NodeNameList boundvars = new NodeNameList();
	//	boundvars.addAll(bvars);
		
		bindstr.append(bind.getPattern().toString());
		vars.addBVar(((AIdentifierPattern) bind.getPattern()).getName());
		bindstr.append(" in @set ");
		bindstr.append(bind.getSet().apply(thmStringVisitor, vars));
		//bindstr.append(ThmExprUtil.getIsabelleExprStr(svars, bvars, bind.getSet()));

		String firstString =  ex.getFirst().apply(thmStringVisitor, vars);
		String predString = "true";
		if (ex.getPredicate() != null) {
			predString =  ex.getPredicate().apply(thmStringVisitor, vars);
		}
	//	String firstString = ThmExprUtil.getIsabelleExprStr(svars, boundvars, ex.getFirst()); 
	//	String predString = ThmExprUtil.getIsabelleExprStr(svars, boundvars, ex.getPredicate()); 
		return "[" + firstString + " | " + bindstr.toString() + " @ " + predString + "]";
	}

	public String caseASeqEnumSeqExp(ASeqEnumSeqExp ex, ThmVarsContext vars) throws AnalysisException{
		
		StringBuilder sb = new StringBuilder();
		LinkedList<PExp> mem = ex.getMembers();

		for (Iterator<PExp> itr = mem.listIterator(); itr.hasNext(); ) {
			PExp m = itr.next();
			sb.append(m.apply(thmStringVisitor, vars));
			//If there are remaining types, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		
		return "[" + sb.toString() +"]";
	}

	public String caseASetCompSetExp(ASetCompSetExp ex, ThmVarsContext vars) throws AnalysisException{
		StringBuilder bindstr = new StringBuilder();
		
		LinkedList<PMultipleBind> binds = ex.getBindings();
		
		for(PMultipleBind b: binds)
		{
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bindstr.append(p.getName());
					vars.addBVar(p.getName());
					//If there are remaining patterns, add a ","
					if(itr.hasNext()){	
						bindstr.append(", ");
					}
				}
				bindstr.append(" : ");
				bindstr.append("@" + tmb.getType().toString()); //SHOULD USE TYPE VISITOR?
			}
			else if (b instanceof ASetMultipleBind)
			{
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					PPattern pattern = itr.next();
					if(pattern instanceof AIdentifierPattern){
						AIdentifierPattern p = (AIdentifierPattern) pattern;
						bindstr.append(p.getName());
						vars.addBVar(p.getName());
					}else if (pattern instanceof ARecordPattern){
						ARecordPattern p = (ARecordPattern) pattern;
						
						bindstr.append("mk_" + p.getType().toString() + "(");
						for (Iterator<PPattern> itr2 = p.getPlist().listIterator(); itr2.hasNext(); ) {

							PPattern fpatt = itr2.next();
							if(fpatt instanceof AIdentifierPattern){
								AIdentifierPattern fpattid = (AIdentifierPattern) fpatt;
								bindstr.append(fpattid.getName());
								vars.addBVar(fpattid.getName());
							}
							//If there are remaining patterns, add a ","
							if(itr2.hasNext()){	
								bindstr.append(", ");
							}
						}
						bindstr.append(")");
					}
					
					//If there are remaining patterns, add a ","
					if(itr.hasNext()){	
						bindstr.append(", ");
					}
				}
				bindstr.append(" in @set ");
				bindstr.append(smb.getSet().apply(thmStringVisitor, vars));
			}
		}
		
		String firstString =  ex.getFirst().apply(thmStringVisitor, vars);
		String predString =  "true";
	    if (ex.getPredicate() != null) {
	    	predString = ex.getPredicate().apply(thmStringVisitor, vars);
	    }

		return "{" + firstString + " | " + bindstr.toString() + " @ " + predString + "}";
	}

	public String caseASetEnumSetExp(ASetEnumSetExp ex, ThmVarsContext vars) throws AnalysisException{
		StringBuilder sb = new StringBuilder();
		LinkedList<PExp> mem = ex.getMembers();

		for (Iterator<PExp> itr = mem.listIterator(); itr.hasNext(); ) {
			PExp m = itr.next();
			sb.append(m.apply(thmStringVisitor, vars));
			//If there are remaining types, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		
		return "{" + sb.toString() +"}";
	}

	public String caseASetRangeSetExp(ASetRangeSetExp ex, ThmVarsContext vars) throws AnalysisException{
		String first = ex.getFirst().apply(thmStringVisitor, vars);
		String last = ex.getLast().apply(thmStringVisitor, vars);
		
		return "{" + first + ", ..., " + last + "}";
	}

	public String caseADivideNumericBinaryExp(ADivideNumericBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " / " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseADivNumericBinaryExp(ADivNumericBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " div " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAGreaterEqualNumericBinaryExp(AGreaterEqualNumericBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " >= " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAGreaterNumericBinaryExp(AGreaterNumericBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " > " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseALessEqualNumericBinaryExp(ALessEqualNumericBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " <= " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseALessNumericBinaryExp(ALessNumericBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " < " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAModNumericBinaryExp(AModNumericBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " mod " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAPlusNumericBinaryExp(APlusNumericBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " + " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseASubtractNumericBinaryExp(ASubtractNumericBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " - " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseATimesNumericBinaryExp(ATimesNumericBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " * " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}

	public String caseAAndBooleanBinaryExp(AAndBooleanBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + " and " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}
	
	public String caseAEquivalentBooleanBinaryExp(AEquivalentBooleanBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " <=> " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}
	
	public String caseAImpliesBooleanBinaryExp(AImpliesBooleanBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" +ex.getLeft().apply(thmStringVisitor, vars) + " => " + ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}
	
	public String caseAOrBooleanBinaryExp(AOrBooleanBinaryExp ex, ThmVarsContext vars) throws AnalysisException{
		return "(" + ex.getLeft().apply(thmStringVisitor, vars) + " or " +ex.getRight().apply(thmStringVisitor, vars)+ ")";
	}
	
	public String caseAFatEnumVarsetExpression(AFatEnumVarsetExpression ex, ThmVarsContext vars) throws AnalysisException{
		StringBuilder sb = new StringBuilder();

		for (Iterator<ANameChannelExp> itr = ex.getChannelNames().listIterator(); itr.hasNext(); ) {
			ANameChannelExp chan = itr.next();
					
			//ILexIdentifierToken
			sb.append(chan.getIdentifier().getName().toString());
			// sb.append(ThmExprUtil.isaDown );
			//If there are remaining channels, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		return "{|" + sb.toString() +"|}";
	}

		
	public String caseAFatCompVarsetExpression(AFatCompVarsetExpression ex, ThmVarsContext vars) throws AnalysisException{
		// TODO COMPLETE
		return "(*varset comp not handled*)";
	}

		
	public String caseAIdentifierVarsetExpression(AIdentifierVarsetExpression ex, ThmVarsContext vars) throws AnalysisException{
		// TODO COMPLETE
		return "(*varset id not handled*)";
	}

		
	public String caseAEnumVarsetExpression(AEnumVarsetExpression ex, ThmVarsContext vars) throws AnalysisException{
		StringBuilder sb = new StringBuilder();

		for (Iterator<ANameChannelExp> itr = ex.getChannelNames().listIterator(); itr.hasNext(); ) {
			ANameChannelExp chan = itr.next();
			sb.append(chan.getIdentifier().getName().toString());
			// sb.append(ThmExprUtil.isaDown );
			//If there are remaining channels, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		return "{|" + sb.toString() +"|}";
	}

		
	public String caseAInterVOpVarsetExpression(AInterVOpVarsetExpression ex, ThmVarsContext vars) throws AnalysisException{
		String left = ex.getLeft().apply(thmStringVisitor, vars);
		String right = ex.getRight().apply(thmStringVisitor, vars);

		return left + " inter " + right;
	}

		
	public String caseASubVOpVarsetExpression(ASubVOpVarsetExpression ex, ThmVarsContext vars) throws AnalysisException{
		String left = ex.getLeft().apply(thmStringVisitor, vars);
		String right = ex.getRight().apply(thmStringVisitor, vars);
		return left + " setminus " + right;
	}

		
	public String caseAUnionVOpVarsetExpression(AUnionVOpVarsetExpression ex, ThmVarsContext vars) throws AnalysisException{
		String left = ex.getLeft().apply(thmStringVisitor, vars);
		String right = ex.getRight().apply(thmStringVisitor, vars);

		return left + " union " + right;	
	}
		


//	else if(ex instanceof ADefExp){
//		ADefExp i = (ADefExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AFuncInstatiationExp){
//		AFuncInstatiationExp i = (AFuncInstatiationExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AHistoryExp){
//		AHistoryExp i = (AHistoryExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ANarrowExp){
//		ANarrowExp i = (ANarrowExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AStateInitExp){
//		AStateInitExp i = (AStateInitExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AIsExp){
//		AIsExp i = (AIsExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AIsOfBaseClassExp){
//		AIsOfBaseClassExp i = (AIsOfBaseClassExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AIsOfClassExp){
//		AIsOfClassExp i = (AIsOfClassExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ALetBeStExp){
//		ALetBeStExp i = (ALetBeStExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AMuExp){
//		AMuExp i = (AMuExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ASameBaseClassExp){
//		ASameBaseClassExp i = (ASameBaseClassExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ASameClassExp){
//		ASameClassExp i = (ASameClassExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ASelfExp){
//		ASelfExp i = (ASelfExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ASubclassResponsibilityExp){
//		ASubclassResponsibilityExp i = (ASubclassResponsibilityExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof AThreadIdExp){
//		AThreadIdExp i = (AThreadIdExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ATimeExp){
//		ATimeExp i = (ATimeExp) ex;
//		return "expr not yet handled";
//	}
//	else if(ex instanceof ANewExp){
//		ANewExp i = (ANewExp) ex;
//		return "expr not yet handled";
//	}

	
	public String defaultPVarsetExpression(PVarsetExpression node, ThmVarsContext bvars)
			throws AnalysisException {		
		return "(*Expression not handled*)";
	}	
	
	
	public String defaultPCMLExp(PCMLExp node, ThmVarsContext bvars)
			throws AnalysisException {		
		return "(*Expression not handled*)";
	}	
	
	@Override
	public String createNewReturnValue(INode arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewReturnValue(Object arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPostExpr(boolean b) {
		this.isPost = b;
	}
}
