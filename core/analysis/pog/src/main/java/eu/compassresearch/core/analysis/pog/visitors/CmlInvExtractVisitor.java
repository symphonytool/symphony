package eu.compassresearch.core.analysis.pog.visitors;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.pog.visitors.IInvExpGetVisitor;
import org.overture.pog.visitors.VdmInvExpGetVisitor;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.expressions.ABracketedExp;

public class CmlInvExtractVisitor extends AnswerCMLAdaptor<List<PExp>>
		implements IInvExpGetVisitor
{

	IInvExpGetVisitor ovtVisitor = new VdmInvExpGetVisitor(this);

	@Override
	public List<PExp> createNewReturnValue(INode node) throws AnalysisException
	{
		return new LinkedList<PExp>();
	}

	@Override
	public List<PExp> createNewReturnValue(Object node)
			throws AnalysisException
	{
		return new LinkedList<PExp>();
	}

	@Override
	public List<PExp> caseABracketedExp(ABracketedExp node)
			throws AnalysisException
	{
		return node.getExpression().apply(ovtVisitor);
	}
	
	@Override
	public List<PExp> caseAActionClassDefinition(AActionClassDefinition node)
			throws AnalysisException
	{
		List<PExp> r = new LinkedList<PExp>();
		for (PDefinition p : node.getDefinitions()){
			r.addAll(p.apply(ovtVisitor));
		}
		return r;
	}

}
