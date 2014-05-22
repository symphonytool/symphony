package eu.compassresearch.ide.theoremprover.commands;

import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ide.theoremprover.CmlTPPlugin;
import eu.compassresearch.ide.theoremprover.isabellelaunch.IIsabelleConstants;

public class LaunchIsabelleHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (!CmlTPPlugin.getDefault().getPreferenceStore()
				.getBoolean(IIsabelleConstants.Z3_NON_COMMERCIAL)) {
			MessageDialog
					.openInformation(
							HandlerUtil.getActiveWorkbenchWindow(event)
									.getShell(),
							"Symphony",
							"The Symphony Theorem Prover requires non-commercial use of the Z3 Theorem Prover. Please "
									+ "select this option in the Theorem Prover Setup preferences.");
			return null;
		}

		if (CmlTPPlugin.getDefault().getPreferenceStore()
				.getString(IIsabelleConstants.ATTR_LOCATION).equals("")) {
			MessageDialog
					.openInformation(
							HandlerUtil.getActiveWorkbenchWindow(event)
									.getShell(),
							"Symphony",
							"The Symphony Theorem Prover requires Isabelle. Please install it and configure the"
									+ "location.");
			return null;
		}

		if (!LaunchIsabelleHandler.isWindowsPlatform() // The theory files are
														// not set through the
														// preferece pane in
														// windows so we must
														// skip the check
				&& CmlTPPlugin.getDefault().getPreferenceStore()
						.getString(IIsabelleConstants.ATTR_SESSION_DIRS)
						.equals("")) {
			MessageDialog
					.openInformation(
							HandlerUtil.getActiveWorkbenchWindow(event)
									.getShell(),
							"Symphony",
							"The Symphony Theorem Prover requires the HOL-UTP-CML theory files. Please install them and configure their"
									+ "location.");
			return null;
		}

		ILaunchConfigurationType configType;
		if (isWindowsPlatform()) {
			configType = getConfigurationType(IIsabelleConstants.LAUNCH_ID_WIN);
		} else if (isMacPlatform()) {
			configType = getConfigurationType(IIsabelleConstants.LAUNCH_ID_MAC);
		} else {
			configType = getConfigurationType(IIsabelleConstants.LAUNCH_ID_LINUX);
		}

		ILaunchConfigurationWorkingCopy wc;
		try {
			wc = configType.newInstance(null, getLaunchManager()
					.generateLaunchConfigurationName("Isabelle"));
			wc.setAttribute(
					IIsabelleConstants.ATTR_LOCATION,
					CmlTPPlugin.getDefault().getPreferenceStore()
							.getString(IIsabelleConstants.ATTR_LOCATION));
			wc.setAttribute(IIsabelleConstants.ATTR_SESSION,
					IIsabelleConstants.ATTR_SESSION_NAME);
			wc.setAttribute(IIsabelleConstants.ATTR_BUILD_RUN, true);
			wc.setAttribute(IIsabelleConstants.ATTR_BUILD_TO_SYSTEM, false);

			LinkedList<String> sessionDir = new LinkedList<String>();
			if (!LaunchIsabelleHandler.isWindowsPlatform()) {

				sessionDir.add(CmlTPPlugin.getDefault().getPreferenceStore()
						.getString(IIsabelleConstants.ATTR_SESSION_DIRS));
			}
			wc.setAttribute(IIsabelleConstants.ATTR_SESSION_DIRS, sessionDir);

			if (LaunchIsabelleHandler.isWindowsPlatform()) {
				String defaultCygwinLoc = CmlTPPlugin.getDefault()
						.getPreferenceStore()
						.getString(IIsabelleConstants.ATTR_LOCATION)
						+ "\\contrib\\cygwin";
				wc.setAttribute(IIsabelleConstants.ATTR_CYGWIN_LOCATION,
						defaultCygwinLoc);
			}

			HashMap<String, String> env = new HashMap<String, String>();
			if (CmlTPPlugin.getDefault().getPreferenceStore()
					.getBoolean(IIsabelleConstants.Z3_NON_COMMERCIAL)) {
				if (isMacPlatform()) {
					env.put(IIsabelleConstants.Z3_NON_COMMERCIAL, "true");
				} else {
					env.put(IIsabelleConstants.Z3_NON_COMMERCIAL, "yes");
				}
				wc.setAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, env);
			}

			ISelection s = HandlerUtil.getCurrentSelection(event);
			if (s instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) s;
				if (ss.getFirstElement() instanceof IResource) {
					IProject project = ((IResource) ss.getFirstElement())
							.getProject();
					wc.setContainer(project);
					// wc.doSave(); // no need to save. it's always the same and would just spawn endless dupes
				}
			}

			// The line below will launch the launch config
			DebugUITools.launch(wc, "run"); // mode is run or debug
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}

	protected ILaunchConfigurationType getConfigurationType(String type) {
		return getLaunchManager().getLaunchConfigurationType(type);
	}

	public static boolean isMacPlatform() {
		return Platform.getOS().equalsIgnoreCase(Platform.OS_MACOSX);
	}

	public static boolean isWindowsPlatform() {
		return System.getProperty("os.name").toLowerCase().contains("win");
	}

}
