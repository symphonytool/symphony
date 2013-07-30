package eu.compassresearch.ide.ui.utility;

import org.eclipse.swt.widgets.Shell;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;

import eu.compassresearch.ide.core.resources.ICmlProject;

public class CmlProjectUtil
{
	/**
	 * Type checks the project
	 * 
	 * @param shell
	 *            a shell to display a graphical progress dialog
	 * @param project
	 *            the project to check
	 * @return true if type checked with no type errors otherwise false
	 */
	public static boolean typeCheck(Shell shell, ICmlProject project)
	{
		final IVdmProject p = (IVdmProject) project.getAdapter(IVdmProject.class);
		return VdmTypeCheckerUi.typeCheck(shell, p);
	}
}
