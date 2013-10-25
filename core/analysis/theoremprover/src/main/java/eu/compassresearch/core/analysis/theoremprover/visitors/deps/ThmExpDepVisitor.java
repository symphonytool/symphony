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
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmExprUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;

public class ThmExpDepVisitor extends
QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList>{
	
	final private QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList> thmDepVisitor;
	
	public ThmExpDepVisitor(ThmDepVisitor thmDepVisitor) {
		this.thmDepVisitor = thmDepVisitor;
	}

		
	public NodeNameList caseABooleanConstExp(ABooleanConstExp node, NodeNameList bvars){
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
	
	public NodeNameList caseANilExp(ANilExp node, NodeNameList bvars){
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
	
	public NodeNameList caseAIfExp(AIfExp node, NodeNameList bvars){
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(ifExp.getTest().apply(thmDepVisitor, bvars)));
		nodeDeps.addAll(ifExp.getThen().apply(thmDepVisitor, bvars)));	
		nodeDeps.addAll(ifExp.getElse().apply(thmDepVisitor, bvars)));	
		
		return nodeDeps;
	}
	
	
	public NodeNameList caseACharLiteralExp(ACharLiteralExp node, NodeNameList bvars){
		NodeNameList nodeDeps = new NodeNameList();
		
		return nodeDeps;
	}
	
	else if(ex instanceof ACharLiteralExp){
	}
	else if(ex instanceof AElseIfExp){
		AElseIfExp ifExp = (AElseIfExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ifExp.getElseIf()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ifExp.getThen()));	
	}
	else if(ex instanceof AExistsExp){
			
		AExistsExp exists = (AExistsExp) ex;
		LinkedList<PMultipleBind> binds = exists.getBindList();
		NodeNameList boundvars = new NodeNameList();
		boundvars.addAll(bvars);
		
		for(PMultipleBind b: binds)
		{
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					boundvars.add(p.getName());
				}
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
			}
			else if (b instanceof ASetMultipleBind)
			{
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					boundvars.add(p.getName());
				}
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
			}
		}
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, exists.getPredicate()));
		
	}
	else if(ex instanceof AExists1Exp){
		AExists1Exp exists = (AExists1Exp) ex;

		NodeNameList boundvars = new NodeNameList();
		boundvars.addAll(bvars);
		PBind b = exists.getBind();
		
		if (b instanceof ATypeBind)
		{
			ATypeBind tmb = (ATypeBind) b;
			boundvars.add(((AIdentifierPattern) tmb.getPattern()).getName());
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
		}
		else if (b instanceof ASetBind)
		{
			ASetBind smb = (ASetBind) b;
			boundvars.add(((AIdentifierPattern) smb.getPattern()).getName());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
		}
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, exists.getPredicate()));
	} 
	else if(ex instanceof AForAllExp){
		AForAllExp forall = (AForAllExp) ex;
		LinkedList<PMultipleBind> binds = forall.getBindList();
		NodeNameList boundvars = new NodeNameList();
		boundvars.addAll(bvars);
		
		for(PMultipleBind b: binds)
		{
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					boundvars.add(p.getName());
				}
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
			}
			else if (b instanceof ASetMultipleBind)
			{
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					boundvars.add(p.getName());
				}
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
			}
		}
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, forall.getPredicate()));
	}
	else if(ex instanceof AIntLiteralExp){
	}
	else if(ex instanceof AApplyExp){
		AApplyExp app = (AApplyExp) ex;
		for (PExp a : app.getArgs())
		{
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, a));
		}
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, app.getRoot()));
	}		
	else if(ex instanceof ACasesExp){
//		ACasesExp c = (ACasesExp) ex;
//		PExp exp = c.getExpression();
//		LinkedList<ACaseAlternative> cases = c.getCases();
//		PExp others = c.getOthers();
		//TODO: Handle cases
	}
	else if(ex instanceof AFieldExp){
		AFieldExp fe = (AFieldExp) ex;
		//Don't actually depend on the field name - just the object!
		//if(fe.getMemberName() != null)
		//{	
		//	nodeDeps.add(fe.getMemberName());
		//}
		//nodeDeps.add(new LexNameToken("", fe.getField().getName(), fe.getLocation()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, fe.getObject()));
	}
	else if(ex instanceof AFieldNumberExp){}
	else if(ex instanceof AIotaExp){
		AIotaExp i = (AIotaExp) ex;

		PBind b = i.getBind();
		NodeNameList boundvars = new NodeNameList();
		boundvars.addAll(bvars);
		
		if (b instanceof ATypeBind)
		{
			ATypeBind tmb = (ATypeBind) b;
			boundvars.add(((AIdentifierPattern) tmb.getPattern()).getName());
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
		}
		else if (b instanceof ASetBind)
		{
			ASetBind smb = (ASetBind) b;
			boundvars.add(((AIdentifierPattern) smb.getPattern()).getName());
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
		}
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, i.getPredicate()));
		
	}
	else if(ex instanceof ALambdaExp){
		ALambdaExp l = (ALambdaExp) ex;
	
		LinkedList<ATypeBind> b = l.getBindList();
		NodeNameList boundvars = new NodeNameList();
		boundvars.addAll(bvars);
		
		for (Iterator<ATypeBind> itr = b.listIterator(); itr.hasNext(); ) {
			ATypeBind p = itr.next();
				
			boundvars.add(((AIdentifierPattern) p.getPattern()).getName());
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(p.getType()));

		}
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, l.getExpression()));
		
	}
	else if(ex instanceof ALetDefExp){
		ALetDefExp l = (ALetDefExp) ex;
		NodeNameList boundvars = new NodeNameList();
		boundvars.addAll(bvars);
		
		LinkedList<PDefinition> ldefs = l.getLocalDefs();
		for (PDefinition d : ldefs)
		{
			boundvars.add(((AIdentifierPattern) d).getName());
			nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(d.getType()));
		}
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, l.getExpression()));
		
	}
	else if(ex instanceof AMapletExp){
		AMapletExp m = (AMapletExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, m.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, m.getRight()));		
		
	}
	else if(ex instanceof AMkBasicExp){
		AMkBasicExp mk = (AMkBasicExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, mk.getArg()));	
	}
	else if(ex instanceof AMkTypeExp){
		AMkTypeExp mk = (AMkTypeExp) ex;
		mk.getRecordType();
		mk.getArgs();
		for (PExp e : mk.getArgs())
		{
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e));	
		}
		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(mk.getRecordType()));
	}
	
	else if(ex instanceof ANotYetSpecifiedExp){}
	else if(ex instanceof APostOpExp){
//		APostOpExp i = (APostOpExp) ex;
		//TODO: Handle postop exp
	}
	else if(ex instanceof APreExp){
//		APreExp i = (APreExp) ex;
		//TODO: Handle pre exp
	}
	else if(ex instanceof AQuoteLiteralExp){}
	else if(ex instanceof ARealLiteralExp){}
	else if(ex instanceof AStringLiteralExp){}
	else if(ex instanceof ASubseqExp){
		ASubseqExp sub = (ASubseqExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sub.getSeq()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sub.getFrom()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sub.getTo()));
	}
	else if(ex instanceof ATupleExp){
		ATupleExp tup = (ATupleExp) ex;
		
		for (PExp p: tup.getArgs()) {
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, p));
		}
	}
	else if(ex instanceof AUndefinedExp){}
	else if(ex instanceof AVariableExp){
		AVariableExp v = (AVariableExp) ex;
		
		boolean boundV = false;
		ILexNameToken varName = v.getName();

		if(!(varName.getName().equals("RESULT"))){
			for(ILexNameToken var : bvars){
				if (!boundV && varName.toString().equals(var.getName()))
				{
					boundV = true;
				}
			}
			if(!boundV)
			{
				nodeDeps.add(varName);
			}
		}
		
	}
	else if(ex instanceof AUnresolvedPathExp){
		AUnresolvedPathExp e = (AUnresolvedPathExp) ex;

		//Get the first part of the path (this is assuming it is a record.field expression...
		LinkedList<ILexIdentifierToken> ids = e.getIdentifiers();
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
	}
	else if (ex instanceof ABracketedExp){
		ABracketedExp e = (ABracketedExp) ex;

		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getExpression()));
	}
	return nodeDeps;
}

private static NodeNameList getBinaryExpDeps(
		NodeNameList bvars, PExp ex) {
	NodeNameList nodeDeps = new NodeNameList();
	
	if(ex instanceof ACompBinaryExp){
		ACompBinaryExp comp = (ACompBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, comp.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, comp.getRight()));		
	}
	else if(ex instanceof ADomainResByBinaryExp){
		ADomainResByBinaryExp dres = (ADomainResByBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dres.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dres.getRight()));		
	}
	else if(ex instanceof ADomainResToBinaryExp){
		ADomainResToBinaryExp dres = (ADomainResToBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dres.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dres.getRight()));		
	}
	else if(ex instanceof AEqualsBinaryExp){
		AEqualsBinaryExp eq = (AEqualsBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, eq.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, eq.getRight()));		
	}
	else if(ex instanceof AInSetBinaryExp){
		AInSetBinaryExp ins = (AInSetBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ins.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, ins.getRight()));		
	}
	else if(ex instanceof AMapUnionBinaryExp){
		AMapUnionBinaryExp mun = (AMapUnionBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, mun.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, mun.getRight()));		
	}
	else if(ex instanceof ANotEqualBinaryExp){
		ANotEqualBinaryExp neq = (ANotEqualBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, neq.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, neq.getRight()));		
	}
	else if(ex instanceof ANotInSetBinaryExp){
		ANotInSetBinaryExp nins = (ANotInSetBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, nins.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, nins.getRight()));		
	}
	else if(ex instanceof APlusPlusBinaryExp){
		APlusPlusBinaryExp plpl = (APlusPlusBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, plpl.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, plpl.getRight()));		
	}
	else if(ex instanceof AProperSubsetBinaryExp){
		AProperSubsetBinaryExp psub = (AProperSubsetBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, psub.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, psub.getRight()));		
	}
	else if(ex instanceof ARangeResByBinaryExp){
		ARangeResByBinaryExp rres = (ARangeResByBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rres.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rres.getRight()));		
	}
	else if(ex instanceof ARangeResToBinaryExp){
		ARangeResToBinaryExp rres = (ARangeResToBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rres.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rres.getRight()));		
	}
	else if(ex instanceof ASeqConcatBinaryExp){
		ASeqConcatBinaryExp scon = (ASeqConcatBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, scon.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, scon.getRight()));		
	}
	else if(ex instanceof ASetDifferenceBinaryExp){
		ASetDifferenceBinaryExp sdiff = (ASetDifferenceBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sdiff.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sdiff.getRight()));		
	}
	else if(ex instanceof ASetIntersectBinaryExp){
		ASetIntersectBinaryExp inter = (ASetIntersectBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, inter.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, inter.getRight()));		
	}
	else if(ex instanceof ASetUnionBinaryExp){
		ASetUnionBinaryExp union = (ASetUnionBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, union.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, union.getRight()));		
	}
	else if(ex instanceof AStarStarBinaryExp){
		AStarStarBinaryExp star = (AStarStarBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, star.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, star.getRight()));		
	}
	else if(ex instanceof ASubsetBinaryExp){
		ASubsetBinaryExp subs = (ASubsetBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, subs.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, subs.getRight()));		
	}
	return nodeDeps;
}

private static NodeNameList getUnaryExpDeps(
		NodeNameList bvars, PExp ex) {
	NodeNameList nodeDeps = new NodeNameList();

	if(ex instanceof AAbsoluteUnaryExp){
		AAbsoluteUnaryExp abs = (AAbsoluteUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, abs.getExp()));
	}
	else if(ex instanceof ACardinalityUnaryExp){
		ACardinalityUnaryExp card = (ACardinalityUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, card.getExp()));
	}
	else if(ex instanceof ADistConcatUnaryExp){
		ADistConcatUnaryExp conc = (ADistConcatUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, conc.getExp()));
	}
	else if(ex instanceof ADistIntersectUnaryExp){
		ADistIntersectUnaryExp dinter = (ADistIntersectUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dinter.getExp()));
	}
	else if(ex instanceof ADistMergeUnaryExp){
		ADistMergeUnaryExp dmerge = (ADistMergeUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dmerge.getExp()));
	}
	else if(ex instanceof ADistUnionUnaryExp){
		ADistUnionUnaryExp dunion = (ADistUnionUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dunion.getExp()));
	}
	else if(ex instanceof AElementsUnaryExp){
		AElementsUnaryExp e = (AElementsUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getExp()));
	}
	else if(ex instanceof AFloorUnaryExp){
		AFloorUnaryExp fl = (AFloorUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, fl.getExp()));
	}
	else if(ex instanceof AHeadUnaryExp){
		AHeadUnaryExp hd = (AHeadUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, hd.getExp()));
	}
	else if(ex instanceof AIndicesUnaryExp){
		AIndicesUnaryExp inds = (AIndicesUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, inds.getExp()));
	}
	else if(ex instanceof ALenUnaryExp){
		ALenUnaryExp len = (ALenUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, len.getExp()));
	}
	else if(ex instanceof AMapDomainUnaryExp){
		AMapDomainUnaryExp dom = (AMapDomainUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, dom.getExp()));
	}
	else if(ex instanceof AMapInverseUnaryExp){
		AMapInverseUnaryExp inverse = (AMapInverseUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, inverse.getExp()));
	}
	else if(ex instanceof AMapRangeUnaryExp){
		AMapRangeUnaryExp rng = (AMapRangeUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rng.getExp()));
	}
	else if(ex instanceof ANotUnaryExp){
		ANotUnaryExp not = (ANotUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, not.getExp()));
	}
	else if(ex instanceof APowerSetUnaryExp){
		APowerSetUnaryExp pwr = (APowerSetUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, pwr.getExp()));
	}
	else if(ex instanceof AReverseUnaryExp){
		AReverseUnaryExp rev = (AReverseUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, rev.getExp()));
	}
	else if(ex instanceof ATailUnaryExp){
		ATailUnaryExp tl = (ATailUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, tl.getExp()));
	}
	else if(ex instanceof AUnaryMinusUnaryExp){
		AUnaryMinusUnaryExp minus = (AUnaryMinusUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, minus.getExp()));
	}
	else if(ex instanceof AUnaryPlusUnaryExp){
		AUnaryPlusUnaryExp plus = (AUnaryPlusUnaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, plus.getExp()));
	}
	return nodeDeps;
}

private static NodeNameList getSetExpDeps(
		NodeNameList bvars, PExp ex) {
	NodeNameList nodeDeps = new NodeNameList();
	
	if(ex instanceof ASetCompSetExp){
		ASetCompSetExp comp = (ASetCompSetExp) ex;

		LinkedList<PMultipleBind> binds = comp.getBindings();
		NodeNameList boundvars = new NodeNameList();
		boundvars.addAll(bvars);
		
		for(PMultipleBind b: binds)
		{
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					boundvars.add(p.getName());
				}
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
			}
			else if (b instanceof ASetMultipleBind)
			{
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					boundvars.add(p.getName());
				}
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
			}
		}
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, comp.getPredicate()));
	}
	else if(ex instanceof ASetEnumSetExp){
		ASetEnumSetExp en = (ASetEnumSetExp) ex;

		for (PExp m : en.getMembers()){
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, m));
		}
	}
	else if(ex instanceof ASetRangeSetExp){
		ASetRangeSetExp en = (ASetRangeSetExp) ex;

		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, en.getFirst()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, en.getLast()));
	}
	
	return nodeDeps;
}

private static NodeNameList getSeqExpDeps(
		NodeNameList bvars, PExp ex) {
	NodeNameList nodeDeps = new NodeNameList();

	if(ex instanceof ASeqCompSeqExp){
		ASeqCompSeqExp comp = (ASeqCompSeqExp) ex;
		ASetBind binds = comp.getSetBind();
		NodeNameList boundvars = new NodeNameList();
		boundvars.addAll(bvars);			
		boundvars.add(((AIdentifierPattern) binds.getPattern()).getName());
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, binds.getSet()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, comp.getPredicate()));
	}
	else if(ex instanceof ASeqEnumSeqExp){
		ASeqEnumSeqExp en = (ASeqEnumSeqExp) ex;
		
		for (PExp m : en.getMembers()){
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, m));
		}
	}

	return nodeDeps;
}

private static NodeNameList getMapExpDeps(
		NodeNameList bvars, PExp ex) {
	
	NodeNameList nodeDeps = new NodeNameList();
	
	if(ex instanceof AMapCompMapExp){
		AMapCompMapExp comp = (AMapCompMapExp) ex;
		LinkedList<PMultipleBind> binds = comp.getBindings();
		NodeNameList boundvars = new NodeNameList();
		boundvars.addAll(bvars);
		
		for(PMultipleBind b: binds)
		{
			if (b instanceof ATypeMultipleBind)
			{
				ATypeMultipleBind tmb = (ATypeMultipleBind) b;					
				for (Iterator<PPattern> itr = tmb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					boundvars.add(p.getName());
				}
				nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(tmb.getType()));
			}
			else if (b instanceof ASetMultipleBind)
			{
				ASetMultipleBind smb = (ASetMultipleBind) b;
				for (Iterator<PPattern> itr = smb.getPlist().listIterator(); itr.hasNext(); ) {
					AIdentifierPattern p = (AIdentifierPattern) itr.next();
					
					boundvars.add(p.getName());
				}
				nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, smb.getSet()));
			}
		}
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(boundvars, comp.getPredicate()));
	}
	else if(ex instanceof AMapEnumMapExp){
		AMapEnumMapExp en = (AMapEnumMapExp) ex;
		for (PExp m : en.getMembers()){
			nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, m));
		}
	}
	return nodeDeps;
}

private static NodeNameList getNumericBinaryDeps(
		NodeNameList bvars, PExp ex) {
	NodeNameList nodeDeps = new NodeNameList();
	
	if(ex instanceof ADivideNumericBinaryExp){
		ADivideNumericBinaryExp e = (ADivideNumericBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
	}
	else if(ex instanceof ADivNumericBinaryExp){
		ADivNumericBinaryExp e = (ADivNumericBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
	}
	else if(ex instanceof AGreaterEqualNumericBinaryExp){
		AGreaterEqualNumericBinaryExp e = (AGreaterEqualNumericBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
	}
	else if(ex instanceof AGreaterNumericBinaryExp){
		AGreaterNumericBinaryExp e = (AGreaterNumericBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
	}
	else if(ex instanceof ALessEqualNumericBinaryExp){
		ALessEqualNumericBinaryExp e = (ALessEqualNumericBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
	}
	else if(ex instanceof ALessNumericBinaryExp){
		ALessNumericBinaryExp e = (ALessNumericBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
	}
	else if(ex instanceof AModNumericBinaryExp){
		AModNumericBinaryExp e = (AModNumericBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
	}
	else if(ex instanceof APlusNumericBinaryExp){
		APlusNumericBinaryExp pl = (APlusNumericBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, pl.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, pl.getRight()));		
	}
	else if(ex instanceof ASubtractNumericBinaryExp){
		ASubtractNumericBinaryExp sub = (ASubtractNumericBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sub.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, sub.getRight()));		
	}
	else if(ex instanceof ATimesNumericBinaryExp){
		ATimesNumericBinaryExp times = (ATimesNumericBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, times.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, times.getRight()));		
	}

	return nodeDeps;
}



private static NodeNameList  getBooleanBinaryDeps(NodeNameList bvars,
		PExp ex) {
	NodeNameList nodeDeps = new NodeNameList();
	if(ex instanceof AAndBooleanBinaryExp){
		AAndBooleanBinaryExp e = (AAndBooleanBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
	}
	else if(ex instanceof AEquivalentBooleanBinaryExp){
		AEquivalentBooleanBinaryExp e = (AEquivalentBooleanBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));	
	}
	else if(ex instanceof AImpliesBooleanBinaryExp){
		AImpliesBooleanBinaryExp e = (AImpliesBooleanBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
	}
	else if(ex instanceof AOrBooleanBinaryExp){
		AOrBooleanBinaryExp e = (AOrBooleanBinaryExp) ex;
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(bvars, e.getRight()));		
	}
	return nodeDeps;
}


//Method to return VARSET expression. This is new to COMPASS AST
public static NodeNameList getIsabelleVarsetExprDeps(PVarsetExpression vExpr) {
	NodeNameList nodeDeps = new NodeNameList();

	if (vExpr instanceof AFatEnumVarsetExpression)
	{
		AFatEnumVarsetExpression e = (AFatEnumVarsetExpression) vExpr;
		
		for (Iterator<ANameChannelExp> itr = e.getChannelNames().listIterator(); itr.hasNext(); ) {
			ANameChannelExp chan = itr.next();
			ILexIdentifierToken i = chan.getIdentifier();
			nodeDeps.add(new LexNameToken("", i.toString(), i.getLocation()));
			
			
		}
	}
	else if (vExpr instanceof AFatCompVarsetExpression)
	{
		//AFatCompVarsetExpression e = (AFatCompVarsetExpression) vExpr;

		// TODO COMPLETE
	}
	else if (vExpr instanceof AIdentifierVarsetExpression)
	{
		//AIdentifierVarsetExpression e = (AIdentifierVarsetExpression) vExpr;

		// TODO COMPLETE
	}
	else if (vExpr instanceof AEnumVarsetExpression)
	{
		AEnumVarsetExpression e = (AEnumVarsetExpression) vExpr;
		
		for (Iterator<ANameChannelExp> itr = e.getChannelNames().listIterator(); itr.hasNext(); ) {
			ANameChannelExp chan = itr.next();
			ILexIdentifierToken i = chan.getIdentifier();
			nodeDeps.add(new LexNameToken("", i.toString(), i.getLocation()));
		}
	}
	else if (vExpr instanceof AInterVOpVarsetExpression)
	{
		AInterVOpVarsetExpression e = (AInterVOpVarsetExpression) vExpr;

		nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getRight()));
	}
	else if (vExpr instanceof ASubVOpVarsetExpression)
	{
		ASubVOpVarsetExpression e = (ASubVOpVarsetExpression) vExpr;

		nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getRight()));
	}
	else if (vExpr instanceof AUnionVOpVarsetExpression)
	{
		AUnionVOpVarsetExpression e = (AUnionVOpVarsetExpression) vExpr;

		nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getLeft()));
		nodeDeps.addAll(ThmExprUtil.getIsabelleVarsetExprDeps(e.getRight()));
	}

	return nodeDeps;
}

//Method to return VARSET expression. This is new to COMPASS AST
	public static String getIsabelleVarsetExpr(PVarsetExpression vExpr) {
		if (vExpr instanceof AFatEnumVarsetExpression)
		{
			AFatEnumVarsetExpression e = (AFatEnumVarsetExpression) vExpr;
			StringBuilder sb = new StringBuilder();


			for (Iterator<ANameChannelExp> itr = e.getChannelNames().listIterator(); itr.hasNext(); ) {
				ANameChannelExp chan = itr.next();
				
				//ILexIdentifierToken
				sb.append(chan.getIdentifier().getName().toString());
				sb.append(ThmExprUtil.isaDown );
				//If there are remaining channels, add a ","
				if(itr.hasNext()){	
					sb.append(", ");
				}
			}
			return "{" + sb.toString() +"}";
		}
		else if (vExpr instanceof AFatCompVarsetExpression)
		{
			//AFatCompVarsetExpression e = (AFatCompVarsetExpression) vExpr;

			// TODO COMPLETE
			return "varset comp not handled";
		}
		else if (vExpr instanceof AIdentifierVarsetExpression)
		{
			//AIdentifierVarsetExpression e = (AIdentifierVarsetExpression) vExpr;

			// TODO COMPLETE
			return "varset id not handled";
		}
		else if (vExpr instanceof AEnumVarsetExpression)
		{
			AEnumVarsetExpression e = (AEnumVarsetExpression) vExpr;
			StringBuilder sb = new StringBuilder();

			for (Iterator<ANameChannelExp> itr = e.getChannelNames().listIterator(); itr.hasNext(); ) {
				ANameChannelExp chan = itr.next();
				sb.append(chan.getIdentifier().getName().toString());
				sb.append(ThmExprUtil.isaDown );
				//If there are remaining channels, add a ","
				if(itr.hasNext()){	
					sb.append(", ");
				}
			}
			return "{" + sb.toString() +"}";
		}
		else if (vExpr instanceof AInterVOpVarsetExpression)
		{
			AInterVOpVarsetExpression e = (AInterVOpVarsetExpression) vExpr;
			
			String left = ThmExprUtil.getIsabelleVarsetExpr(e.getLeft());
			String right = ThmExprUtil.getIsabelleVarsetExpr(e.getRight());

			return left + " inter " + right;
		}
		else if (vExpr instanceof ASubVOpVarsetExpression)
		{
			ASubVOpVarsetExpression e = (ASubVOpVarsetExpression) vExpr;

			String left = ThmExprUtil.getIsabelleVarsetExpr(e.getLeft());
			String right = ThmExprUtil.getIsabelleVarsetExpr(e.getRight());

			return left + " setminus " + right;
		}
		else if (vExpr instanceof AUnionVOpVarsetExpression)
		{
			AUnionVOpVarsetExpression e = (AUnionVOpVarsetExpression) vExpr;

			String left = ThmExprUtil.getIsabelleVarsetExpr(e.getLeft());
			String right = ThmExprUtil.getIsabelleVarsetExpr(e.getRight());

			return left + " union " + right;
		}
		return ThmExprUtil.notHandled;
		
	}

public static NodeNameList getIsabelleRenamingExprDeps(SRenameChannelExp rnExp) {
	return new NodeNameList();
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
