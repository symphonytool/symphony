package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class RttMbtNewProject extends RttMbtPopupMenuAction  {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IWizard wizard = new RttMbtComponentWizard();
		if (shell != null && wizard != null) {
			WizardDialog dialog = new WizardDialog(shell, wizard);
			System.out.println("executing dialog.open()");
			dialog.open();
		} else {
			System.out.println("unable to get a shell");			
		}
		return null;
	}
}
