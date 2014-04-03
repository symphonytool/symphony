package eu.compassresearch.core.interpreter.utility;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.ATimeExp;

import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;

public class TimedSpeckChecker extends DepthFirstAnalysisCMLAdaptor
{
	private boolean hasTimedConstruct = false;

	public static boolean containsTimeConstructs(List<PDefinition> definitions)
			throws AnalysisException
	{
		TimedSpeckChecker checker = new TimedSpeckChecker();
		for (PDefinition def : definitions)
		{
			def.apply(checker);
		}
		return checker.hasTimedConstruct;
	}

	@Override
	public void caseATimedInterruptAction(ATimedInterruptAction node)
			throws AnalysisException
	{
		hasTimedConstruct = true;
	}

	@Override
	public void caseATimedInterruptProcess(ATimedInterruptProcess node)
			throws AnalysisException
	{
		hasTimedConstruct = true;
	}

	@Override
	public void caseATimeExp(ATimeExp node) throws AnalysisException
	{
		hasTimedConstruct = true;
	}

	@Override
	public void caseATimeoutAction(ATimeoutAction node)
			throws AnalysisException
	{
		hasTimedConstruct = true;
	}

	@Override
	public void caseATimeoutProcess(ATimeoutProcess node)
			throws AnalysisException
	{
		hasTimedConstruct = true;
	}
	
	@Override
	public void caseAWaitAction(AWaitAction node) throws AnalysisException
	{
		hasTimedConstruct = true;
	}

}
