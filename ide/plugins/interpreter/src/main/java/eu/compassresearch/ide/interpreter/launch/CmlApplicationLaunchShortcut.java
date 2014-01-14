package eu.compassresearch.ide.interpreter.launch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.progress.IProgressService;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.debug.ui.launchconfigurations.LauncherMessages;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.interpreter.CmlUtil;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.interpreter.launching.GlobalProcessSelectorDialog;


public class CmlApplicationLaunchShortcut implements ILaunchShortcut2
{

	private IProject project;

	@Override
	public void launch(ISelection selection, String mode)
	{

		if (selection instanceof IStructuredSelection)
		{
			IStructuredSelection treeSelection = (IStructuredSelection) selection;
			// find the associated CmlSourceUnit for this selected file.
			searchAndLaunch(treeSelection.toArray(), mode);
		}

	}

	@Override
	public void launch(IEditorPart editor, String mode)
	{
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut2#getLaunchConfigurations(org.eclipse.ui.IEditorPart)
	 */
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart)
	{
		// let the framework resolve configurations based on resource mapping
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut2#getLaunchConfigurations(org.eclipse.jface.viewers.ISelection)
	 */
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection)
	{
		// let the framework resolve configurations based on resource mapping
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

	protected IProject findProject(Object[] scope,
			IProgressService progressService) throws InterruptedException,
			CoreException
	{
		for (Object object : scope)
		{
			if (object instanceof IProject)
			{
				IProject project = (IProject) object;
				return project;

			} else if (object instanceof IResource)
			{
				return ((IResource) object).getProject();
			}
		}
		return null;
	}

	/**
	 * Finds and returns an <b>existing</b> configuration to re-launch for the given type, or <code>null</code> if there
	 * is no existing configuration.
	 * 
	 * @return a configuration to use for launching the given type or <code>null</code> if none
	 */
	protected ILaunchConfiguration findLaunchConfiguration(String projectName,
			ILaunchConfigurationType configType)
	{
		List<ILaunchConfiguration> candidateConfigs = Collections.emptyList();
		try
		{
			ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(configType);
			candidateConfigs = new ArrayList<ILaunchConfiguration>(configs.length);
			for (int i = 0; i < configs.length; i++)
			{
				ILaunchConfiguration config = configs[i];

				// String defaultModule = config.getAttribute(IDebugConstants.VDM_LAUNCH_CONFIG_DEFAULT, "");
				String pName = config.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, "");
				// String operation = config.getAttribute(IDebugConstants.VDM_LAUNCH_CONFIG_OPERATION, "");

				if (// defaultModule.equals(getModuleName(type).toString())
					// &&
				pName.equalsIgnoreCase(projectName)
				// && operation.equals(getOperationName(type) + "()")
				)
				{ //$NON-NLS-1$
					candidateConfigs.add(config);
				}
			}

		} catch (CoreException e)
		{
			// JDIDebugUIPlugin.log(e);
		}
		int candidateCount = candidateConfigs.size();
		if (candidateCount == 1)
		{
			return (ILaunchConfiguration) candidateConfigs.get(0);
		} else if (candidateCount > 1)
		{
			return chooseConfiguration(candidateConfigs);
			// return candidateConfigs.get(0);
		}
		return null;
	}

	/**
	 * Returns a configuration from the given collection of configurations that should be launched, or <code>null</code>
	 * to cancel. Default implementation opens a selection dialog that allows the user to choose one of the specified
	 * launch configurations. Returns the chosen configuration, or <code>null</code> if the user cancels.
	 * 
	 * @param configList
	 *            list of configurations to choose from
	 * @return configuration to launch or <code>null</code> to cancel
	 */
	protected ILaunchConfiguration chooseConfiguration(
			List<ILaunchConfiguration> configList)
	{
		IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setElements(configList.toArray());
		dialog.setTitle("Choose CML configuration");
		dialog.setMessage(LauncherMessages.VdmLaunchShortcut_2);
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK)
		{
			return (ILaunchConfiguration) dialog.getFirstResult();
		}
		return null;
	}

	private void launch(ILaunchConfiguration config, String mode)
	{
		if (config != null)
		{
			DebugUITools.launch(config, mode);
		}
	}

	/**
	 * Protected Methods
	 */
	protected void searchAndLaunch(Object[] scope, String mode)
	{
		try
		{
			project = findProject(scope, PlatformUI.getWorkbench().getProgressService());

			ILaunchConfiguration config = findLaunchConfiguration(project.getName(), getConfigurationType());
			if (config != null)
			{
				// config already exists for the project.
				launch(config, mode);
				return;
			}

			IVdmProject vdmProject = (IVdmProject) project.getAdapter(IVdmProject.class);
			if (VdmTypeCheckerUi.typeCheck(getShell(), vdmProject))
			{
				if (vdmProject != null
						&& vdmProject.getModel().isParseCorrect()) // &&
																	// vdmProject.getModel().isTypeCorrect())
				{
					ICmlProject cmlProject = (ICmlProject) vdmProject.getAdapter(ICmlProject.class);

					List<AProcessDefinition> defsInFile = CmlUtil.getGlobalProcessesFromSource(cmlProject.getModel().getDefinitions());

					ILaunchConfiguration launchConfig = null;
					if (defsInFile.size() == 1)
					{
						String processName = defsInFile.get(0).getName().getName();
						launchConfig = createConfiguration(processName, mode);
					} else if (defsInFile.size() > 1)
					{

						AProcessDefinition selectedProcess = GlobalProcessSelectorDialog.chooseProcess(cmlProject, getShell());

						if (selectedProcess != null)
						{
							String processName = selectedProcess.getName().getName();
							launchConfig = createConfiguration(processName, mode);
						}

					}

					launch(launchConfig, mode);
				}
			} else
			// If no ast is attached then there are either parser or type errors
			{
				if (!vdmProject.getModel().isParseCorrect())
				{
					MessageDialog.openError(null, "Launch failure", "The Cml model is not parsed correctly and therefore cannot be launched. This could be a glitch, try to close and open the source.");
				} else if (!vdmProject.getModel().isTypeCorrect())
				{
					MessageDialog.openError(null, "Launch failure", "The Cml model is not typecheck correctly and therefore cannot be launched. This could be a glitch, try to close and open the source.");
				} else
				{
					MessageDialog.openError(null, "Launch failure", "The Cml model is not loaded correctly and therefore cannot be launched");
				}
			}
		} catch (InterruptedException | CoreException e)
		{
			MessageDialog.openError(getShell(), LauncherMessages.VdmLaunchShortcut_0, e.getMessage());
			return;
		}
	}

	private ILaunchConfiguration createConfiguration(String processName,
			String mode) throws CoreException
	{
		// create a new one
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType ctype = launchManager.getLaunchConfigurationType(ICmlDebugConstants.ATTR_LAUNCH_CONFIGURATION_TYPE);

		ILaunchConfigurationWorkingCopy lcwc = ctype.newInstance(null, launchManager.generateLaunchConfigurationName(project.getName()));

		lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, processName);
		lcwc.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, project.getName());
		lcwc.doSave();
		return lcwc;
	}

	private ILaunchConfigurationType getConfigurationType()
	{
		return getLaunchManager().getLaunchConfigurationType(ICmlDebugConstants.ATTR_LAUNCH_CONFIGURATION_TYPE);
	}

	protected ILaunchManager getLaunchManager()
	{
		return DebugPlugin.getDefault().getLaunchManager();
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
