package eu.compassresearch.ide.plugins.interpreter.debug.ui.model;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStm;
import org.overture.ide.debug.utils.ExecutableAnalysisException;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;

public class ExecutableAnalysis extends DepthFirstAnalysisCMLAdaptor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5353696074294132014L;
	private final int searchLine;

	private String module = null;

	private ExecutableAnalysis(int searchLine, String module)
	{
		this.searchLine = searchLine;
		this.module = module;
	}

	public static boolean isExecutable(INode node, int line, boolean findModule)
	{

		String nodeModule = findModule ? searchForModule(node) : null;

		ExecutableAnalysis analysis = new ExecutableAnalysis(line, nodeModule);

		try
		{
			node.apply(analysis);
		} catch (ExecutableAnalysisException e)
		{
			return e.isExecutable();

		} catch (AnalysisException e)
		{
			e.printStackTrace();
			return false;
		}

		return false;
	}

	private static String searchForModule(INode node)
	{

		String nodeModule = null;

		if (node instanceof PDefinition)
		{
			nodeModule = ((PDefinition) node).getLocation().getFile().getName();
		} else
		{

			SClassDefinition classDef = node.getAncestor(SClassDefinition.class);

			if (classDef != null)
			{
				nodeModule = classDef.getLocation().getFile().getName();
			} else
			{
				PProcess process = node.getAncestor(PProcess.class);

				if (process != null)
				{
					nodeModule = process.getLocation().getFile().getName();
				} else
				{
					PAction action = node.getAncestor(PAction.class);

					if (action != null)
					{
						nodeModule = action.getLocation().getFile().getName();
					}
				}
			}
		}
		return nodeModule;
	}

	private boolean isValidModule(PStm node)
	{

		return module == null
				|| (node.getLocation().getStartLine() == searchLine && module.equals(node.getLocation().getFile().getName()));
	}

	private boolean isValidModule(PExp node)
	{

		return module == null
				|| (node.getLocation().getStartLine() == searchLine && module.equals(node.getLocation().getFile().getName()));
	}

	private boolean isValidModule(PProcess node)
	{

		return module == null
				|| (node.getLocation().getStartLine() == searchLine && module.equals(node.getLocation().getFile().getName()));
	}

	private boolean isValidModule(PAction node)
	{

		return module == null
				|| (node.getLocation().getStartLine() == searchLine && module.equals(node.getLocation().getFile().getName()));
	}

	public void defaultInPStm(PStm node) throws ExecutableAnalysisException
	{
		if (isValidModule(node))
		{
			throw new ExecutableAnalysisException(true);
		}
	}

	public void defaultInPExp(PExp node) throws ExecutableAnalysisException
	{
		if (isValidModule(node))
		{
			throw new ExecutableAnalysisException(true);
		}
	}

	@Override
	public void defaultInPProcess(PProcess node) throws AnalysisException
	{
		if (isValidModule(node))
		{
			throw new ExecutableAnalysisException(true);
		}
	}

	@Override
	public void defaultInPAction(PAction node) throws AnalysisException
	{
		if (isValidModule(node))
		{
			throw new ExecutableAnalysisException(true);
		}
	}
}
