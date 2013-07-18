package eu.compassresearch.ide.plugins.interpreter.launch;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.plugins.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.plugins.interpreter.CmlUtil;
import eu.compassresearch.ide.plugins.interpreter.ICmlDebugConstants;

//import eu.compassresearch.core.interpreter.debug.CmlInterpreterLaunchConfigurationConstants;

public class CmlApplicationLaunchShortcut implements ILaunchShortcut2
{

	@Override
	public void launch(ISelection selection, String mode)
	{

		if (selection instanceof TreeSelection)
		{
			TreeSelection treeSelection = (TreeSelection) selection;
			// find the associated CmlSourceUnit for this selected file.
			searchAndLaunch(treeSelection.getFirstElement(), mode);
		}

	}

	@Override
	public void launch(IEditorPart editor, String mode)
	{
		System.out.println(editor.toString());
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection)
	{

		if (selection instanceof TreeSelection)
		{
			TreeSelection treeSelection = (TreeSelection) selection;
			// find the associated CmlSourceUnit for this selected file.
			IFile file = (IFile) treeSelection.getFirstElement();

			ICmlSourceUnit source = (ICmlSourceUnit) file.getAdapter(ICmlSourceUnit.class);
			List<ILaunchConfiguration> foundConfs = findLaunchConfigurationsByFile(source);

			return foundConfs.toArray(new ILaunchConfiguration[foundConfs.size()]);
		} else
			return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart)
	{
		return null;
	}

	@Override
	public IResource getLaunchableResource(ISelection selection)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart)
	{
		return null;
	}

	/**
	 * Protected Methods
	 */
	protected void searchAndLaunch(Object file, String mode)
	{

		IFile ifile = (IFile) file;
		ICmlSourceUnit source = (ICmlSourceUnit) ifile.getAdapter(ICmlSourceUnit.class);

		// Open the file that are going being debugged
		try
		{
			IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), ifile);
		} catch (PartInitException e1)
		{
			e1.printStackTrace();
		}

		IVdmProject vdmProject = (IVdmProject) ifile.getProject().getAdapter(IVdmProject.class);
		if (VdmTypeCheckerUi.typeCheck(getShell(), vdmProject))
		{
			if (vdmProject != null && vdmProject.getModel().isParseCorrect()
					&& source != null) // && vdmProject.getModel().isTypeCorrect())
			{
				PSource ast = source.getSourceAst();

				List<PSource> sourceList = new LinkedList<PSource>();
				sourceList.add(ast);
				List<AProcessDefinition> defsInFile = CmlUtil.GetGlobalProcessesFromSource(sourceList);

				if (defsInFile.size() == 1)
				{
					String processName = defsInFile.get(0).getName().getName();
					launch(source, processName, mode);
				} else if (defsInFile.size() > 1)
				{

					GlobalProcessSelectorDialog dialog = new GlobalProcessSelectorDialog(getShell(), vdmProject);

					AProcessDefinition selectedProcess = dialog.showDialog();

					if (selectedProcess != null)
					{
						String processName = selectedProcess.getName().getName();
						launch(source, processName, mode);
					}

				}
			}
		} else
		// If no ast is attached then there are either parser or type errors
		{
			if (!vdmProject.getModel().isParseCorrect())
				MessageDialog.openError(null, "Launch failure", "The Cml model is not parsed correctly and therefore cannot be launched. This could be a glitch, try to close and open the source.");
			else if (!vdmProject.getModel().isTypeCorrect())
				MessageDialog.openError(null, "Launch failure", "The Cml model is not typecheck correctly and therefore cannot be launched. This could be a glitch, try to close and open the source.");
			else
				MessageDialog.openError(null, "Launch failure", "The Cml model is not loaded correctly and therefore cannot be launched");
		}

	}

	protected void launch(ICmlSourceUnit sourceUnit, String processName,
			String mode)
	{
		try
		{
			ILaunchConfiguration config = findLaunchConfiguration(sourceUnit, processName, mode);

			if (config != null)
			{
				config.launch(mode, null);
			}
		} catch (CoreException e)
		{
			/* Handle exceptions */
			e.printStackTrace();
		}
	}

	protected List<ILaunchConfiguration> findLaunchConfigurationsByFile(
			ICmlSourceUnit file)

	{
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType ctype = launchManager.getLaunchConfigurationType(ICmlDebugConstants.ATTR_LAUNCH_CONFIGURATION_TYPE);

		// Get the current project which this file lives in

		List<ILaunchConfiguration> result = new LinkedList<ILaunchConfiguration>();

		try
		{
			for (ILaunchConfiguration lc : launchManager.getLaunchConfigurations(ctype))
			{
				String projectName = lc.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, "");
				if (file.getFile().getProject().getName().equals(projectName))
					result.add(lc);
			}
		} catch (CoreException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	protected ILaunchConfiguration findLaunchConfiguration(
			ICmlSourceUnit sourceUnit, String processName, String mode)
			throws CoreException
	{
		List<ILaunchConfiguration> confs = findLaunchConfigurationsByFile(sourceUnit);

		ILaunchConfiguration result = null;

		for (ILaunchConfiguration lc : confs)
		{
			String foundProcessName = lc.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, "");
			if (foundProcessName.equals(processName))
				result = lc;
		}

		// create a new one
		if (result == null)
		{
			ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
			ILaunchConfigurationType ctype = launchManager.getLaunchConfigurationType(ICmlDebugConstants.ATTR_LAUNCH_CONFIGURATION_TYPE);

			ILaunchConfigurationWorkingCopy lcwc = ctype.newInstance(null, launchManager.generateLaunchConfigurationName("Quick Launch"));

			lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, processName);
			lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, sourceUnit.getFile().getProject().getName());
			lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_FILE_PATH, sourceUnit.getSystemFile().getAbsolutePath());
			lcwc.doSave();
			result = lcwc;
		}

		return result;
	}

	/**
	 * Convenience method to return the active workbench window shell.
	 * 
	 * @return active workbench window shell
	 */
	protected Shell getShell()
	{
		return CmlDebugPlugin.getActiveWorkbenchShell();
	}
}
