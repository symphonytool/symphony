package eu.compassresearch.core.analysis.theoremprover.visitors.deps;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
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
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.ASetBind;
import org.overture.ast.patterns.ASetMultipleBind;
import org.overture.ast.patterns.ATypeBind;
import org.overture.ast.patterns.ATypeMultipleBind;
import org.overture.ast.patterns.PBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;

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
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

@SuppressWarnings("serial")
public class ThmExpDepVisitor extends
QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList>{
	
	final private QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList> thmDepVisitor;
	
	public ThmExpDepVisitor(ThmDepVisitor thmDepVisitor) {
		this.thmDepVisitor = thmDepVisitor;
	}


	public NodeNameList caseAIsExp(AIsExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getTest().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(ex.getBasicType().apply(thmDepVisitor, bvars));	
		return nodeDeps;
	}
		
	public NodeNameList caseABooleanConstExp(ABooleanConstExp ex, NodeNameList bvars){
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
	
	
	public NodeNameList caseANilExp(ANilExp ex, NodeNameList bvars){
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
	
	
	public NodeNameList caseAIfExp(AIfExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getTest().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(ex.getThen().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getElse().apply(thmDepVisitor, bvars));	
		
		return nodeDeps;
	}
	
	
	public NodeNameList caseACharLiteralExp(ACharLiteralExp ex, NodeNameList bvars){
		NodeNameList nodeDeps = new NodeNameList();
		
		return nodeDeps;
	}
	
	
	public NodeNameList caseAElseIfExp(AElseIfExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(ex.getElseIf().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getThen().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}
	
	
	public NodeNameList caseAExistsExp(AExistsExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
			
		LinkedList<PMultipleBind> binds = ex.getBindList();

		
		for(PMultipleBind b: binds)
		{
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bvars.add(p.getName());
				}
				nodeDeps.addAll(tmb.getType().apply(thmDepVisitor, bvars));
			}
			else if (b instanceof ASetMultipleBind)
			{
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bvars.add(p.getName());
				}
				nodeDeps.addAll(smb.getSet().apply(thmDepVisitor, bvars));
			}
		}
		nodeDeps.addAll(ex.getPredicate().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}

	
	
	public NodeNameList caseAExists1Exp(AExists1Exp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		PBind b = ex.getBind();
		
		if (b instanceof ATypeBind)
		{
			ATypeBind tmb = (ATypeBind) b;
			bvars.add(((AIdentifierPattern) tmb.getPattern()).getName());
			nodeDeps.addAll(tmb.getType().apply(thmDepVisitor, bvars));
		}
		else if (b instanceof ASetBind)
		{
			ASetBind smb = (ASetBind) b;
			bvars.add(((AIdentifierPattern) smb.getPattern()).getName());
			nodeDeps.addAll(smb.getSet().apply(thmDepVisitor, bvars));
		}
		nodeDeps.addAll(ex.getPredicate().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	

	public NodeNameList caseAForAllExp(AForAllExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		LinkedList<PMultipleBind> binds = ex.getBindList();

		for(PMultipleBind b: binds)
		{
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bvars.add(p.getName());
				}
				nodeDeps.addAll(tmb.getType().apply(thmDepVisitor, bvars));
			}
			else if (b instanceof ASetMultipleBind)
			{
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bvars.add(p.getName());
				}
				nodeDeps.addAll(smb.getSet().apply(thmDepVisitor, bvars));
			}
		}
		nodeDeps.addAll(ex.getPredicate().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	

	public NodeNameList caseAIntLiteralExp(AIntLiteralExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		return nodeDeps;
	}

	public NodeNameList caseAApplyExp(AApplyExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		for (PExp a : ex.getArgs())
		{
			nodeDeps.addAll(a.apply(thmDepVisitor, bvars));
		}
		nodeDeps.addAll(ex.getRoot().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	

	public NodeNameList caseACasesExp(ACasesExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

//		ACasesExp c = (ACasesExp) ex;
//		PExp exp = c.getExpression();
//		LinkedList<ACaseAlternative> cases = c.getCases();
//		PExp others = c.getOthers();
		//TODO: Handle cases

		return nodeDeps;
	}
	

	public NodeNameList caseAFieldExp(AFieldExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		//Don't actually depend on the field name - just the object!
		//if(fe.getMemberName() != null)
		//{	
		//	nodeDeps.add(fe.getMemberName());
		//}
		//nodeDeps.add(new LexNameToken("", fe.getField().getName(), fe.getLocation()));
		nodeDeps.addAll(ex.getObject().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	

	public NodeNameList caseAFieldNumberExp(AFieldNumberExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}
	

	public NodeNameList caseAIotaExp(AIotaExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		PBind b = ex.getBind();

		
		if (b instanceof ATypeBind)
		{
			ATypeBind tmb = (ATypeBind) b;
			bvars.add(((AIdentifierPattern) tmb.getPattern()).getName());
			nodeDeps.addAll(tmb.getType().apply(thmDepVisitor, bvars));
		}
		else if (b instanceof ASetBind)
		{
			ASetBind smb = (ASetBind) b;
			bvars.add(((AIdentifierPattern) smb.getPattern()).getName());
			nodeDeps.addAll(smb.getSet().apply(thmDepVisitor, bvars));
		}
		nodeDeps.addAll(ex.getPredicate().apply(thmDepVisitor, bvars));
		

		return nodeDeps;
	}
	

	public NodeNameList caseALambdaExp(ALambdaExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
	
		LinkedList<ATypeBind> b = ex.getBindList();

		
		for (Iterator<ATypeBind> itr = b.listIterator(); itr.hasNext(); ) {
			ATypeBind p = itr.next();
				
			bvars.add(((AIdentifierPattern) p.getPattern()).getName());
			nodeDeps.addAll(p.getType().apply(thmDepVisitor, bvars));

		}
		nodeDeps.addAll(ex.getExpression().apply(thmDepVisitor, bvars));
		

		return nodeDeps;
	}
	

	public NodeNameList caseALetDefExp(ALetDefExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		LinkedList<PDefinition> ldefs = ex.getLocalDefs();
		for (PDefinition d : ldefs)
		{
			bvars.add(((AIdentifierPattern) d).getName());
			nodeDeps.addAll(d.getType().apply(thmDepVisitor, bvars));
		}
		nodeDeps.addAll(ex.getExpression().apply(thmDepVisitor, bvars));
		

		return nodeDeps;
	}
	

	public NodeNameList caseAMapletExp(AMapletExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}
	

	public NodeNameList caseAMkBasicExp(AMkBasicExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getArg().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	

	public NodeNameList caseAMkTypeExp(AMkTypeExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		for (PExp e : ex.getArgs())
		{
			nodeDeps.addAll(e.apply(thmDepVisitor, bvars));
		}
		nodeDeps.addAll(ex.getRecordType().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}

	public NodeNameList caseANotYetSpecifiedExp(ANotYetSpecifiedExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}

	public NodeNameList caseAPostOpExp(APostOpExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		//TODO: Handle postop exp

		return nodeDeps;
	}

	public NodeNameList caseAPreExp(APreExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		//TODO: Handle pre exp

		return nodeDeps;
	}
	
	public NodeNameList caseAQuoteLiteralExp(AQuoteLiteralExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}

	public NodeNameList caseARealLiteralExp(ARealLiteralExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}

	public NodeNameList caseAStringLiteralExp(AStringLiteralExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}

	public NodeNameList caseASubseqExp(ASubseqExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getSeq().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(ex.getFrom().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(ex.getTo().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}

	public NodeNameList caseATupleExp(ATupleExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		for (PExp p: ex.getArgs()) {
			nodeDeps.addAll(p.apply(thmDepVisitor, bvars));
		}

		return nodeDeps;
	}

	public NodeNameList caseAUndefinedExp(AUndefinedExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}

	public NodeNameList caseAVariableExp(AVariableExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		boolean boundV = false;
		ILexNameToken varName = ex.getName();

		if(!(varName.getName().equals("RESULT"))){
			for(ILexNameToken var : bvars){
				if (!boundV && varName.getName().equals(var.getName()))
				{
					boundV = true;
				}
			}
			if(!boundV)
			{
				nodeDeps.add(varName);
			}
		}
		
		return nodeDeps;
	}
	

	public NodeNameList caseAUnresolvedPathExp(AUnresolvedPathExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		//Get the first part of the path (this is assuming it is a record.field expression...
		LinkedList<ILexIdentifierToken> ids = ex.getIdentifiers();
		ILexIdentifierToken fId = ids.getFirst();
				
		//check that the record part isn't a bound variable
		boolean boundV = false;
		LexNameToken lnt = new LexNameToken("", fId.getName().toString(), fId.getLocation());

		for(ILexNameToken var : bvars){
			if (!boundV && lnt.toString().equals(var.getName()))
			{
				boundV = true;
			}
		}
		if(!boundV)
		{
			nodeDeps.add(lnt);
		}

		return nodeDeps;
	}
	

	public NodeNameList caseABracketedExp(ABracketedExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}



	public NodeNameList caseACompBinaryExp(ACompBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseADomainResByBinaryExp(ADomainResByBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));		

		return nodeDeps;
	}

	public NodeNameList caseADomainResToBinaryExp(ADomainResToBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));		

		return nodeDeps;
	}

	public NodeNameList caseAEqualsBinaryExp(AEqualsBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));		

		return nodeDeps;
	}

	public NodeNameList caseAInSetBinaryExp(AInSetBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAMapUnionBinaryExp(AMapUnionBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));		

		return nodeDeps;
	}

	public NodeNameList caseANotEqualBinaryExp(ANotEqualBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));			

		return nodeDeps;
	}

	public NodeNameList caseANotInSetBinaryExp(ANotInSetBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));		

		return nodeDeps;
	}

	public NodeNameList caseAPlusPlusBinaryExp(APlusPlusBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));		
	
		return nodeDeps;
	}

	public NodeNameList caseAProperSubsetBinaryExp(AProperSubsetBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));		

		return nodeDeps;
	}

	public NodeNameList caseARangeResByBinaryExp(ARangeResByBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));		

		return nodeDeps;
	}

	public NodeNameList caseARangeResToBinaryExp(ARangeResToBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));		

		return nodeDeps;
	}
	
	public NodeNameList caseASeqConcatBinaryExp(ASeqConcatBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));			

		return nodeDeps;
	}

	public NodeNameList caseASetDifferenceBinaryExp(ASetDifferenceBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseASetIntersectBinaryExp(ASetIntersectBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseASetUnionBinaryExp(ASetUnionBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAStarStarBinaryExp(AStarStarBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseASubsetBinaryExp(ASubsetBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}



	public NodeNameList caseAAbsoluteUnaryExp(AAbsoluteUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseACardinalityUnaryExp(ACardinalityUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	
		
		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseADistConcatUnaryExp(ADistConcatUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseADistIntersectUnaryExp(ADistIntersectUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	
		
		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseADistMergeUnaryExp(ADistMergeUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseADistUnionUnaryExp(ADistUnionUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAElementsUnaryExp(AElementsUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAFloorUnaryExp(AFloorUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAHeadUnaryExp(AHeadUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAIndicesUnaryExp(AIndicesUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseALenUnaryExp(ALenUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	
	

		return nodeDeps;
	}

	public NodeNameList caseAMapDomainUnaryExp(AMapDomainUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}
	

	public NodeNameList caseAMapInverseUnaryExp(AMapInverseUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAMapRangeUnaryExp(AMapRangeUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseANotUnaryExp(ANotUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAPowerSetUnaryExp(APowerSetUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAReverseUnaryExp(AReverseUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseATailUnaryExp(ATailUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}
	

	public NodeNameList caseAUnaryMinusUnaryExp(AUnaryMinusUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAUnaryPlusUnaryExp(AUnaryPlusUnaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getExp().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseASetCompSetExp(ASetCompSetExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();


		LinkedList<PMultipleBind> binds = ex.getBindings();
		
		for(PMultipleBind b: binds)
		{
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bvars.add(p.getName());
				}
				nodeDeps.addAll(tmb.getType().apply(thmDepVisitor, bvars));	
			}
			else if (b instanceof ASetMultipleBind)
			{
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bvars.add(p.getName());
				}
				nodeDeps.addAll(smb.getSet().apply(thmDepVisitor, bvars));	
			}
		}
		nodeDeps.addAll(ex.getPredicate().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseASetEnumSetExp(ASetEnumSetExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();


		for (PExp m : ex.getMembers()){
			nodeDeps.addAll(m.apply(thmDepVisitor, bvars));	
		}

		return nodeDeps;
	}

	public NodeNameList caseASetRangeSetExp(ASetRangeSetExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getFirst().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getLast().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}


	public NodeNameList caseASeqCompSeqExp(ASeqCompSeqExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		ASetBind binds = ex.getSetBind();
		
		bvars.add(((AIdentifierPattern) binds.getPattern()).getName());
		
		nodeDeps.addAll(binds.getSet().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getPredicate().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}
	
	public NodeNameList caseASeqEnumSeqExp(ASeqEnumSeqExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		for (PExp m : ex.getMembers()){
			nodeDeps.addAll(m.apply(thmDepVisitor, bvars));	
		}

		return nodeDeps;
	}


	public NodeNameList caseAMapCompMapExp(AMapCompMapExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		LinkedList<PMultipleBind> binds = ex.getBindings();
		
		for(PMultipleBind b: binds)
		{
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bvars.add(p.getName());
				}
				nodeDeps.addAll(tmb.getType().apply(thmDepVisitor, bvars));	
			}
			else if (b instanceof ASetMultipleBind)
			{
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					bvars.add(p.getName());
				}
				nodeDeps.addAll(smb.getSet().apply(thmDepVisitor, bvars));	
			}
		}
		nodeDeps.addAll(ex.getPredicate().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAMapEnumMapExp(AMapEnumMapExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		for (PExp m : ex.getMembers()){
			nodeDeps.addAll(m.apply(thmDepVisitor, bvars));
		}

		return nodeDeps;
	}

	public NodeNameList caseADivideNumericBinaryExp(ADivideNumericBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
		
		return nodeDeps;
	}

	public NodeNameList caseADivNumericBinaryExp(ADivNumericBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
	
		return nodeDeps;
	}

	public NodeNameList caseAGreaterEqualNumericBinaryExp(AGreaterEqualNumericBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
	
		return nodeDeps;
	}

	public NodeNameList caseAGreaterNumericBinaryExp(AGreaterNumericBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
		
		return nodeDeps;
	}

	public NodeNameList caseALessEqualNumericBinaryExp(ALessEqualNumericBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseALessNumericBinaryExp(ALessNumericBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
		
		return nodeDeps;
	}

	public NodeNameList caseAModNumericBinaryExp(AModNumericBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
	
		return nodeDeps;
	}
	
	public NodeNameList caseAPlusNumericBinaryExp(APlusNumericBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
	
		return nodeDeps;
	}
	
	public NodeNameList caseASubtractNumericBinaryExp(ASubtractNumericBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
	
		return nodeDeps;
	}

	public NodeNameList caseATimesNumericBinaryExp(ATimesNumericBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
		
		return nodeDeps;
	}



	public NodeNameList caseAAndBooleanBinaryExp(AAndBooleanBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAEquivalentBooleanBinaryExp(AEquivalentBooleanBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAImpliesBooleanBinaryExp(AImpliesBooleanBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseAOrBooleanBinaryExp(AOrBooleanBinaryExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();	

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
	
		return nodeDeps;
	}


//Method to return VARSET expression. This is new to COMPASS AST

	public NodeNameList caseAFatEnumVarsetExpression(AFatEnumVarsetExpression ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		for (Iterator<ANameChannelExp> itr = ex.getChannelNames().listIterator(); itr.hasNext(); ) {
			ANameChannelExp chan = itr.next();
			ILexIdentifierToken i = chan.getIdentifier();
			nodeDeps.add(new LexNameToken("", i.toString(), i.getLocation()));
		}

		return nodeDeps;
	}
	
	public NodeNameList caseAFatCompVarsetExpression(AFatCompVarsetExpression ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		// TODO COMPLETE

		return nodeDeps;
	}

	public NodeNameList caseAIdentifierVarsetExpression(AIdentifierVarsetExpression ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		// TODO COMPLETE

		return nodeDeps;
	}

	public NodeNameList caseAEnumVarsetExpression(AEnumVarsetExpression ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		for (Iterator<ANameChannelExp> itr = ex.getChannelNames().listIterator(); itr.hasNext(); ) {
			ANameChannelExp chan = itr.next();
			ILexIdentifierToken i = chan.getIdentifier();
			nodeDeps.add(new LexNameToken("", i.toString(), i.getLocation()));
		}

		return nodeDeps;
	}

	public NodeNameList caseAInterVOpVarsetExpression(AInterVOpVarsetExpression ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}

	public NodeNameList caseASubVOpVarsetExpression(ASubVOpVarsetExpression ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	
		
		return nodeDeps;
	}
	
	public NodeNameList caseAUnionVOpVarsetExpression(AUnionVOpVarsetExpression ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(ex.getLeft().apply(thmDepVisitor, bvars));	
		nodeDeps.addAll(ex.getRight().apply(thmDepVisitor, bvars));	

		return nodeDeps;
	}


	public NodeNameList caseSRenameChannelExp(SRenameChannelExp ex, NodeNameList bvars) throws AnalysisException{
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}

	
	public NodeNameList defaultPVarsetExpression(PVarsetExpression node, NodeNameList bvars)
			throws AnalysisException {		
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}	
	
	
	public NodeNameList defaultPCMLExp(PCMLExp node, NodeNameList bvars)
			throws AnalysisException {		
		NodeNameList nodeDeps = new NodeNameList();

		return nodeDeps;
	}	
	
	@Override
	public NodeNameList createNewReturnValue(INode arg0, NodeNameList arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeNameList createNewReturnValue(Object arg0, NodeNameList arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

}
