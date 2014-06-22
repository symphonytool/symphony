package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.visitors.ILocaleExtractVisitor;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AProcessDefinition;

public class CmlLocaleExtractionVisitor extends AnswerCMLAdaptor<String> implements ILocaleExtractVisitor
{
	@Override
	public String defaultINode(INode node) throws AnalysisException
	{
		AProcessDefinition proc = node.getAncestor(AProcessDefinition.class);
		if (proc == null)
		{
			return null;
		} else
		{
			return proc.apply(this);
		}
	}
	
	public String caseAProcessDefinition(AProcessDefinition node) throws AnalysisException {
		return node.getName().getName();
	}
	
	@Override
	public String createNewReturnValue(INode node) throws AnalysisException
	{
		return null;
	}

	@Override
	public String createNewReturnValue(Object node) throws AnalysisException
	{
		return null;
	}

}