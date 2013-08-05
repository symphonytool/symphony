package eu.compassresearch.ide.securityanalysis;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.handlers.HandlerUtil;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.core.resources.IVdmSourceUnit;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.security.SecurityAnalysis;
import eu.compassresearch.security.SecurityEnvironment.Judgement;
import eu.compassresearch.security.SecurityEnvironment.SubtreeJudgement;


public class SecurityAnalysisCommandHandler extends AbstractHandler implements
		IHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection)
		{
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof IResource)
			{
				IResource resource = (IResource) firstElement;
				// TODO: this needs to be updated
				IVdmProject vdmProject =(IVdmProject) resource.getProject().getAdapter(IVdmProject.class);

				if (vdmProject == null)
				{
					return null;
				}

				final IVdmModel model = vdmProject.getModel();
				if (model.isParseCorrect())
				{

					if (!model.isParseCorrect())
					{
						return null;
					}

					if (!model.isTypeCorrect())
					{
						VdmTypeCheckerUi.typeCheck(HandlerUtil.getActiveShell(event), vdmProject);
					}

					if (model.isTypeCorrect())
					{
						// ready
						analyse(model);
					}
				}
			}
		}
		return null;
	}

	
	
	public static void analyse(IVdmModel model)
	{
		MessageConsoleStream console = null;
		try
		{

			MessageConsole mc = new MessageConsole("Security Console", null);
			ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { mc });
			console = mc.newMessageStream();
			console.setActivateOnWrite(true);
			try
			{
				console.write("~-~-~- Security Console -~-~-~\n");
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			console.write("Model has " + model.getSourceUnits().size() + " source unit(s).\n");
			SecurityAnalysis sa = new SecurityAnalysis();
			for (IVdmSourceUnit source : model.getSourceUnits())
			{
				console.write("Analysing: " + source.getFile().getName() + "\n");
				ICmlSourceUnit cmlSource = (ICmlSourceUnit) source.getAdapter(ICmlSourceUnit.class);
				if (cmlSource != null)
				{
					PSource ast = cmlSource.getParseNode();
					SubtreeJudgement judgements = sa.runAnalysis(ast);
					for (Judgement j : judgements.getInvolvedJudgements())
					{
						console.write(j.toString() + "\n");

					}
				}
			}
		} catch (Exception e)
		{
			try
			{
				console.write("An instance of Chaos occured.\n");
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
