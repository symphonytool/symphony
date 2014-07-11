package eu.compassresearch.ide.modelchecker;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.unsupported.UnsupportedElementInfo;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;

public class MCCollectorHandler extends AbstractHandler
{

	public static final String UNSUPPORTED_ELEMENTS_MSG = "This model contains unsupported CML elements. Check the warnings for more information.";


	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		try
		{
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
			IProject proj = MCPluginUtility.getCurrentlySelectedProject();

			ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);

			if (!CmlProjectUtil.typeCheck(window.getShell(), cmlProj))
			{
				MessageDialog.openError(null, "Symphony", "Errors in model.");
				return null;
			}

			List<UnsupportedElementInfo> uns = new MCUnsupportedCollector().getUnsupporteds(cmlProj.getModel().getAst());

			if (uns.isEmpty())
			{
				MessageDialog.openInformation(null, "Symphony", "No unsupported elements detected.");
			}
			else
			{
				cmlProj.addUnsupportedMarkers(uns);
				MessageDialog.openError(null, "Symphony", UNSUPPORTED_ELEMENTS_MSG);
			}
			
		} catch (Exception e)
		{
			MessageDialog.openError(null, "Symphony", "Internal error");
		}
		return null;
	}


}
