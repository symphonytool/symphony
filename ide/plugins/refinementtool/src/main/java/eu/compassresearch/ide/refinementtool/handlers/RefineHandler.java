package eu.compassresearch.ide.refinementtool.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import eu.compassresearch.ide.ui.editor.core.CmlEditor;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RefineHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public RefineHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		CmlEditor editor = (CmlEditor) window.getActivePage().getActiveEditor();

		
		MessageDialog.openInformation(
				window.getShell(),
				"Symphony IDE Refinement Plugin",
				"We will refine");
		return null;
	}
}
