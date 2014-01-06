package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.pog.pub.IPOContextStack;

import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatCompVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AIdentifierVarsetExpression;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;

public class ChansetVisitor extends VarSetVisitor {

	
	public ChansetVisitor (ProofObligationGenerator parentPog){
		super(parentPog);
	}
	
	@Override
	public CmlProofObligationList caseAEnumVarsetExpression(
			AEnumVarsetExpression node, IPOContextStack question)
			throws AnalysisException {
		return new CmlProofObligationList();
	}

	@Override
	public CmlProofObligationList caseAFatEnumVarsetExpression(
			AFatEnumVarsetExpression node, IPOContextStack question)
			throws AnalysisException {
		return new CmlProofObligationList();
	}

	@Override
	public CmlProofObligationList caseAFatCompVarsetExpression(
			AFatCompVarsetExpression node, IPOContextStack question)
			throws AnalysisException {
		return new CmlProofObligationList();
	}

	@Override
	public CmlProofObligationList caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node, IPOContextStack question)
			throws AnalysisException {
		return new CmlProofObligationList();
	}

	
	
	
	

}
