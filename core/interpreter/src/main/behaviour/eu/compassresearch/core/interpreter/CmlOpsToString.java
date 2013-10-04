package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;

class CmlOpsToString
{

	public static String toString(INode node)
	{
		try
		{
			return node.apply(new OpsVisitor());
		} catch (AnalysisException e)
		{
			e.printStackTrace();
		}

		return "Error!";
	}

	private static class OpsVisitor extends AnswerCMLAdaptor<String>
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String defaultPProcess(PProcess node) throws AnalysisException
		{
			return "NA";
		}

		@Override
		public String defaultPAction(PAction node) throws AnalysisException
		{
			return "NA";
		}

		@Override
		public String caseAExternalChoiceAction(AExternalChoiceAction node)
				throws AnalysisException
		{
			return "[]";
		}

		@Override
		public String caseAInterleavingParallelAction(
				AInterleavingParallelAction node) throws AnalysisException
		{

			return "|||";
		}

		@Override
		public String caseAGeneralisedParallelismParallelAction(
				AGeneralisedParallelismParallelAction node)
				throws AnalysisException
		{

			return "[|" + node.getChansetExpression() + "|]";
		}
	}

}
