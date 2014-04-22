package eu.compassresearch.core.typechecker.analysis;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SOperationDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.ACallStm;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeWarningMessages;

/**
 * Utility class for checking namesets against actual assignments made by actions
 * 
 * @author kel
 */
public class AssignmentCollector extends DepthFirstAnalysisCMLAdaptor
{

	Set<ILexNameToken> names = new HashSet<ILexNameToken>();

	protected AssignmentCollector()
	{

	}

	@Override
	public void caseAInterleavingParallelAction(AInterleavingParallelAction node)
			throws AnalysisException
	{
		for (PDefinition def : node.getLeftNamesetExpression().getType().getDefinitions())
		{
			if (def instanceof AInstanceVariableDefinition)
			{
				names.add(def.getName());
			}
		}

		for (PDefinition def : node.getRightNamesetExpression().getType().getDefinitions())
		{
			if (def instanceof AInstanceVariableDefinition)
			{
				names.add(def.getName());
			}
		}
	}

	@Override
	public void caseAReferenceAction(AReferenceAction node)
			throws AnalysisException
	{
		PDefinition def = node.getActionDefinition();

		if (def != null)
		{
			def.apply(THIS);
		}
	}

	@Override
	public void caseAClassClassDefinition(AClassClassDefinition node)
			throws AnalysisException
	{
	}

	@Override
	public void caseAActionClassDefinition(AActionClassDefinition node)
			throws AnalysisException
	{
	}

	@Override
	public void caseAAssignmentStm(AAssignmentStm node)
			throws AnalysisException
	{
		if (node.getTarget() instanceof AIdentifierStateDesignator)
		{
			names.add(((AIdentifierStateDesignator) node.getTarget()).getName());
		}
	}

	public Set<ILexNameToken> getNames()
	{
		return names;
	}

	@Override
	public void caseACallStm(ACallStm node) throws AnalysisException
	{
		// This is properly not the best way to find the operation. Just want to avoid the lookup
		if (node.getType().parent() instanceof AOperationType)
		{
			AOperationType t = (AOperationType) node.getType().parent();

			SOperationDefinition def = (SOperationDefinition) t.parent();

			if (def.getBody() != null)
			{
				def.getBody().apply(THIS);
			}
		}
	}

	public static void checkAssignments(PType nameSetType, PAction action,
			ITypeIssueHandler issueHandler) throws AnalysisException
	{
		AssignmentCollector assignmentCollector = new AssignmentCollector();
		action.apply(assignmentCollector);

		Set<ILexNameToken> nameSetNames = new HashSet<ILexNameToken>();

		// no definitions exists if the symbold inside the type are undefined
		if (nameSetType != null && !nameSetType.getDefinitions().isEmpty())
		{
			for (PDefinition def : nameSetType.getDefinitions())
			{
				nameSetNames.add(def.getName());
			}
		}

		for (ILexNameToken name : assignmentCollector.getNames())
		{
			if (!nameSetNames.contains(name))
			{
				issueHandler.addTypeWarning(action, TypeWarningMessages.ASSIGNMENT_TO_UNDECLARED_STATE, name.getName());
			}
		}

	}
}
