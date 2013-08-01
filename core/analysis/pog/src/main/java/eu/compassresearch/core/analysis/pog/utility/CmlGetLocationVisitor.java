package eu.compassresearch.core.analysis.pog.utility;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.pog.utility.GetLocationHelper;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.actions.SGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.SInstantiatedAction;
import eu.compassresearch.ast.actions.SParallelAction;
import eu.compassresearch.ast.actions.SReplicatedAction;
import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.PCMLDefinition;
import eu.compassresearch.ast.definitions.SCMLClassDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SChannelExp;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.ast.expressions.SVOpVarsetExpression;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.process.SReplicatedProcess;
import eu.compassresearch.ast.types.PCMLType;

class CmlGetLocationVisitor extends AnswerCMLAdaptor<ILexLocation>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ILexLocation defaultPSingleDeclaration(PSingleDeclaration node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSCmlOperationDefinition(
			SCmlOperationDefinition node) throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSChannelExp(SChannelExp node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSRenameChannelExp(SRenameChannelExp node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultPVarsetExpression(PVarsetExpression node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSVOpVarsetExpression(SVOpVarsetExpression node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultPProcess(PProcess node) throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSReplicatedProcess(SReplicatedProcess node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultPAction(PAction node) throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSParallelAction(SParallelAction node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSInstantiatedAction(SInstantiatedAction node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSReplicatedAction(SReplicatedAction node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSStatementAction(SStatementAction node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultPCommunicationParameter(
			PCommunicationParameter node) throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultPParametrisation(PParametrisation node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSGeneralAssignmentStatementAction(
			SGeneralAssignmentStatementAction node) throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultPCMLType(PCMLType node) throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultPCMLDefinition(PCMLDefinition node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultSCMLClassDefinition(SCMLClassDefinition node)
			throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultPCMLExp(PCMLExp node) throws AnalysisException
	{
		return node.getLocation();
	}

	@Override
	public ILexLocation defaultINode(INode node) throws AnalysisException {
		return GetLocationHelper.findLocation(node);
	}
	
	

}
