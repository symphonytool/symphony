package eu.compassresearch.ide.interpreter.launching;

import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.debug.core.VdmDebugPlugin;
import org.overture.ide.debug.ui.launching.AbstractVdmMainLaunchConfigurationTab;
import org.overture.ide.debug.utils.JarClassSelector;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.core.ICmlCoreConstants;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;

public class CmlMainLaunchConfigurationTab extends
		AbstractLaunchConfigurationTab
{

	class WidgetListener implements ModifyListener, SelectionListener
	{
		public void modifyText(ModifyEvent e)
		{
			updateLaunchConfigurationDialog();
		}

		public void widgetDefaultSelected(SelectionEvent e)
		{
			/* do nothing */
		}

		public void widgetSelected(SelectionEvent e)
		{
			updateLaunchConfigurationDialog();
		}
	}

	private Text fProjectText;
	private Text fTopProcessText;
	private Button rbAnimate;
	private Button rbSimulate;
	private Button  rbRemoteControl;
	private Text fRemoteControlClassText;
	private WidgetListener fListener = new WidgetListener();
	private Button fRemoteControlnButton;

	@Override
	public void createControl(Composite parent)
	{

		Composite comp = new Composite(parent, SWT.NONE);

		setControl(comp);
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
		// IDebugHelpContextIds.LAUNCH_CONFIGURATION_DIALOG_COMMON_TAB);
		comp.setLayout(new GridLayout(1, true));
		comp.setFont(parent.getFont());

		createProjectSelection(comp);
		createProcessSelection(comp);
		createAnimateSimulateSelection(comp);

	}

	protected IProject getProject()
	{
		if (fProjectText != null && fProjectText.getText().length() > 0)
		{
			return ResourcesPlugin.getWorkspace().getRoot().getProject(fProjectText.getText());
		} else
		{
			setErrorMessage("Project not set");
			return null;
		}

	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig)
	{
		setErrorMessage(null);
		try
		{
			String projectName = launchConfig.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, "");
			IProject project = null;

			if (projectName.length() == 0)
			{
				setErrorMessage(CmlLaunchConfigurationErrorMessages.PROJECT_NOT_SET.toString());
				return false;
			}

			project = getProject();

			if (!project.exists())
			{
				setErrorMessage(CmlLaunchConfigurationErrorMessages.NO_PROJECT_WITH_NAME_EXIST.customizeMessage(projectName));
				return false;
			} else if (!project.isOpen())
			{
				setErrorMessage(CmlLaunchConfigurationErrorMessages.PROJECT_NOT_OPEN.customizeMessage(projectName));
				return false;
			}

			if (!fRemoteControlClassText.getText().isEmpty()
					&& !AbstractVdmMainLaunchConfigurationTab.isFullyQualifiedClassname(fRemoteControlClassText.getText()))
			{
				setErrorMessage("Remote Control class name is not a well-formed fully-qualified Java classname");
				return false;
			}

			return super.isValid(launchConfig)
					&& isProcessValid(project, launchConfig);
		} catch (CoreException e)
		{

			setErrorMessage(e.getMessage());
			return false;
			// e.printStackTrace();
		}
	}

	private boolean isProcessValid(IProject project,
			ILaunchConfiguration launchConfig) throws CoreException
	{
		String processName = launchConfig.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, "");

		if (processName.length() == 0)
		{
			setErrorMessage("Process not set");
			return false;
		} else
		{
			return true;
		}

	}


	private void createProcessSelection(Composite parent)
	{
		Group group = new Group(parent, parent.getStyle());
		group.setText("Top Process");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		group.setLayoutData(gd);

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 3;
		group.setLayout(layout);

		// editParent = group;

		Label label = new Label(group, SWT.MIN);
		label.setText("Process:");
		gd = new GridData(GridData.BEGINNING);
		label.setLayoutData(gd);

		fTopProcessText = new Text(group, SWT.SINGLE | SWT.BORDER);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		fTopProcessText.setLayoutData(gd);
		fTopProcessText.addModifyListener(fListener);

		Button selectProjectButton = createPushButton(group, "Search...", null);

		selectProjectButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				final IProject project = getProject();
				ICmlProject cmlProject = (ICmlProject) project.getAdapter(ICmlProject.class);

				AProcessDefinition selectedProcess = GlobalProcessSelectorDialog.chooseProcess( cmlProject,getShell());

				if (selectedProcess != null)
				{
					fTopProcessText.setText(selectedProcess.getName().getName());
				}
			}
		});
	}

	private void createProjectSelection(Composite parent)
	{
		Group group = new Group(parent, parent.getStyle());
		group.setText("Project");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		group.setLayoutData(gd);

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 3;
		group.setLayout(layout);

		// editParent = group;

		Label label = new Label(group, SWT.MIN);
		label.setText("Project:");
		gd = new GridData(GridData.BEGINNING);
		label.setLayoutData(gd);

		fProjectText = new Text(group, SWT.SINGLE | SWT.BORDER);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		fProjectText.setLayoutData(gd);
		fProjectText.addModifyListener(fListener);

		Button selectProjectButton = createPushButton(group, "Browse...", null);

		selectProjectButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				class ProjectContentProvider extends
						BaseWorkbenchContentProvider
				{
					@Override
					public boolean hasChildren(Object element)
					{
						if (element instanceof IProject)
						{
							return false;
						} else
						{
							return super.hasChildren(element);
						}
					}

					@Override
					public Object[] getElements(Object element)
					{
						List<IProject> elements = new Vector<IProject>();
						Object[] arr = super.getElements(element);
						if (arr != null)
						{
							for (Object object : arr)
							{
								try
								{
									if (object instanceof IProject
									// && (((IProject) object).getAdapter(IVdmProject.class) != null
											&& isCmlProject((IProject) object)
									// )
									// && isSupported((IProject) object)
									)
									{
										elements.add((IProject) object);
									}
								} catch (CoreException e)
								{
									if (VdmDebugPlugin.DEBUG)
									{
										e.printStackTrace();
									}
								}
							}
							return elements.toArray();
						}
						return null;
					}

					private boolean isCmlProject(IProject p)
							throws CoreException
					{
						// FIXME this should be replaced by the adapter check that is commented

						for (String nature : p.getDescription().getNatureIds())
						{
							if (nature.equals(ICmlCoreConstants.NATURE))
							{
								return true;
							}
						}
						return false;
					}

				}
				;
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(), new ProjectContentProvider());
				dialog.setTitle("Project Selection");
				dialog.setMessage("Select a project:");
				dialog.setComparator(new ViewerComparator());

				dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());

				if (dialog.open() == Window.OK)
				{
					if (dialog.getFirstResult() != null
							&& dialog.getFirstResult() instanceof IProject
							&& ((IProject) dialog.getFirstResult()).getAdapter(IVdmProject.class) != null)
					{
						fProjectText.setText(((IProject) dialog.getFirstResult()).getName());
					}

				}
			}
		});
	}
	
	protected void chooseRemoteControlClass() throws CoreException
	{
		final IProject project = getProject();
		IVdmProject vdmProject = (IVdmProject) project.getAdapter(IVdmProject.class);

		String selection = JarClassSelector.selectClass(getShell(), vdmProject.getModelBuildPath().getLibrary());

		if (selection != null)
		{
			fRemoteControlClassText.setText(selection);
		}
	}

	private void createAnimateSimulateSelection(Composite parent)
	{
		Group group = new Group(parent, parent.getStyle());
		group.setText("Execution Mode");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		group.setLayoutData(gd);

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 3;
		group.setLayout(layout);

		rbAnimate = createRadioButton(group, "Animate");
		rbAnimate.addSelectionListener(fListener);
		// rbAnimate.setSelection(true);
		rbSimulate = createRadioButton(group, "Simulate");
		rbSimulate.addSelectionListener(fListener);
		
		rbRemoteControl = createRadioButton(group, "Remote Control");
		rbRemoteControl.addSelectionListener(fListener);
		rbRemoteControl.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				fRemoteControlClassText.setEnabled(rbRemoteControl.getSelection());
				fRemoteControlnButton.setEnabled(rbRemoteControl.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});

		
		fRemoteControlClassText = new Text(group, SWT.SINGLE | SWT.BORDER);
		

		gd = new GridData(GridData.FILL_HORIZONTAL);
		fRemoteControlClassText.setLayoutData(gd);
		fRemoteControlClassText.addModifyListener(fListener);
		fRemoteControlClassText.setEnabled(true);
		
		fRemoteControlnButton = createPushButton(group, "Browse...", null);
		fRemoteControlnButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				try
				{
					chooseRemoteControlClass();
				} catch (CoreException e1)
				{
					if (VdmDebugPlugin.DEBUG)
					{
						e1.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
	{
		// TODO Auto-generated method stub
		// rbAnimate.setSelection(true);
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_IS_ANIMATION, true);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration)
	{
		try
		{
			fProjectText.setText(configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, ""));

			fTopProcessText.setText(configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, ""));
			// fTopProcessFilePath = configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_FILE_PATH,
			// "");
			fRemoteControlClassText.setEnabled(false);
			fRemoteControlnButton.setEnabled(false);
			
			if (configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_IS_ANIMATION, true))
			{
				rbAnimate.setSelection(true);
				rbSimulate.setSelection(false);
				rbRemoteControl.setSelection(false);
			} else
			{
				rbAnimate.setSelection(false);
				rbSimulate.setSelection(true);
				rbRemoteControl.setSelection(false);
			}

			fRemoteControlClassText.setText(configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_REMOTE_INTERPRETER_CLASS, ""));
			
			if(!fRemoteControlClassText.getText().isEmpty())
			{
				rbRemoteControl.setSelection(true);
				fRemoteControlClassText.setEnabled(true);
				fRemoteControlnButton.setEnabled(true);
				rbSimulate.setSelection(false);
				rbAnimate.setSelection(false);
			}
		} catch (CoreException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration)
	{
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, fProjectText.getText());
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, fTopProcessText.getText());
		if (rbAnimate.getSelection())
		{
			configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_IS_ANIMATION, true);
		} else
		{
			configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_IS_ANIMATION, false);
		}
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_REMOTE_INTERPRETER_CLASS, fRemoteControlClassText.getText());

	}

	@Override
	public String getName()
	{
		return "Main";
	}

}
