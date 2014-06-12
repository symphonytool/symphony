package eu.compassresearch.ide.interpreter.launching;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
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

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.core.ICmlCoreConstants;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.interpreter.CmlUtil;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.interpreter.handlers.CreateExternalSystemHandler;

public class CmlCoSimMainLaunchConfigurationTab extends
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
	private WidgetListener fListener = new WidgetListener();
	private Button coordinatorRadio;
	private Button externalSysRadio;
	private Text hostText;
	private org.eclipse.swt.widgets.List externalProcessesList;
	private Button selectExternalProcessesButton;
	private Button configureExternalSystemButton;

	@Override
	public void createControl(Composite parent)
	{

		Composite comp = new Composite(parent, SWT.NONE);

		setControl(comp);
		comp.setLayout(new GridLayout(1, true));
		comp.setFont(parent.getFont());

		createProjectSelection(comp);
		createProcessSelection(comp);
		createAnimateSimulateSelection(comp);
		createCoSimGroup(comp);

	}

	private void createCoSimGroup(Composite parent)
	{
		Group group = new Group(parent, parent.getStyle());
		group.setText("Co-Simulation");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		group.setLayoutData(gd);

		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 3;
		layout.horizontalSpacing = SWT.FILL;
		group.setLayout(layout);

		// editParent = group;

		Label label = new Label(group, SWT.MIN);
		label.setText("Mode:");
		gd = new GridData(GridData.BEGINNING);
		label.setLayoutData(gd);

		coordinatorRadio = createRadioButton(group, "Coordinator");
		coordinatorRadio.addSelectionListener(fListener);
		externalSysRadio = createRadioButton(group, "External System");
		externalSysRadio.addSelectionListener(fListener);

		externalSysRadio.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				externalProcessesList.setEnabled(!externalSysRadio.getSelection());
				selectExternalProcessesButton.setEnabled(!externalSysRadio.getSelection());
				configureExternalSystemButton.setEnabled(externalSysRadio.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{

			}
		});

		label = new Label(group, SWT.MIN);
		label.setText("Host:");
		gd = new GridData(GridData.BEGINNING);
		label.setLayoutData(gd);

		hostText = new Text(group, SWT.SINGLE | SWT.BORDER | SWT.FILL);
		gd = new GridData(GridData.BEGINNING);
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		hostText.setLayoutData(gd);
		hostText.addModifyListener(fListener);

		label = new Label(group, SWT.MIN);
		label.setText("External Processes:");
		gd = new GridData(GridData.BEGINNING);
		label.setLayoutData(gd);

		externalProcessesList = new org.eclipse.swt.widgets.List(group, SWT.MULTI
				| SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(GridData.CENTER);
		gd.verticalSpan = 8;
		gd.horizontalSpan = 2;
		gd.verticalAlignment = SWT.FILL;
		gd.horizontalAlignment = SWT.FILL;
		externalProcessesList.setLayoutData(gd);

		selectExternalProcessesButton = createPushButton(group, "Select...", null);
		gd = new GridData(GridData.END);
		selectExternalProcessesButton.setLayoutData(gd);

		selectExternalProcessesButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				final IProject project = getProject();
				ICmlProject cmlProject = (ICmlProject) project.getAdapter(ICmlProject.class);

				typeCheckModel(cmlProject);

				List<AProcessDefinition> selectedProcess = GlobalProcessSelectorDialog.chooseProcesses(cmlProject, getShell(), getExternalProcesses());

				if (selectedProcess != null)
				{
					externalProcessesList.setItems(encodeProcessNameList(selectedProcess));
					fListener.widgetSelected(null);
				}
			}

		});

		configureExternalSystemButton = createPushButton(parent, "Configure External System", null);
		gd = new GridData(GridData.END);
		configureExternalSystemButton.setLayoutData(gd);

		configureExternalSystemButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				createExternalSystemConfig();
			}
		});

	}

	protected String encodeArrayAsCoommaSeperatedString(String... item)
	{
		StringBuffer sb = new StringBuffer();
		for (Iterator<String> iterator = Arrays.asList(item).iterator(); iterator.hasNext();)
		{
			sb.append(iterator.next());
			if (iterator.hasNext())
			{
				sb.append(",");
			}

		}
		return sb.toString();
	}

	private void createExternalSystemConfig()
	{
		IProject proj = getProject();
		if (proj != null)
		{

			String host = hostText.getText();

			if (host.contains(":"))
			{
				String[] tmp = host.split(":");
				host = tmp[0];
				String port = tmp[1];
				String config = "#define SYMPHONY_HOST \""
						+ host
						+ "\"\n#define SYMPHONY_PORT "+port+"\n#define EXTERNAL_PROCESS \""
						+ fTopProcessText.getText() + "\"";
				CreateExternalSystemHandler.createExternalSystemStructure(proj, config);
			}
		}
	}

	private String[] encodeProcessNameList(
			List<AProcessDefinition> selectedProcess)
	{
		List<String> names = new Vector<String>();

		for (AProcessDefinition aProcessDefinition : selectedProcess)
		{
			names.add(aProcessDefinition.getName().getName());
		}

		return names.toArray(new String[] {});
	}

	private List<AProcessDefinition> getExternalProcesses()
	{
		List<AProcessDefinition> defs = new Vector<AProcessDefinition>();

		if (externalProcessesList.getItemCount() != 0)
		{
			String[] processes = externalProcessesList.getItems();

			for (String p : processes)
			{
				ICmlProject cmlProject = getCmlProject();
				if (cmlProject != null)
				{
					for (AProcessDefinition def : CmlUtil.getGlobalProcessesFromSource(cmlProject.getModel().getDefinitions()))
					{
						if (def.getName().getName().equals(p))
						{
							defs.add(def);
						}
					}
				}
			}
		}
		return defs;

	}

	private ICmlProject getCmlProject()
	{
		IProject p = getProject();
		if (p != null)
		{
			return (ICmlProject) p.getAdapter(ICmlProject.class);
		}
		return null;

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

			try
			{
				if (hostText.getText().isEmpty()
						|| !hostText.getText().contains(":")
						|| 0 >= Integer.valueOf(hostText.getText().split("\\:")[1]))
				{
					setErrorMessage(CmlLaunchConfigurationErrorMessages.URL_NOT_VALID.customizeMessage(hostText.getText()));
				}
			} catch (NumberFormatException e)
			{
				setErrorMessage(CmlLaunchConfigurationErrorMessages.URL_NOT_VALID.customizeMessage(hostText.getText()));
			}

			return super.isValid(launchConfig)
					&& isProcessValid(project, launchConfig);
		} catch (CoreException e)
		{

			setErrorMessage(e.getMessage());
			return false;
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
				ICmlProject cmlProject = getCmlProject();

				typeCheckModel(cmlProject);

				AProcessDefinition selectedProcess = GlobalProcessSelectorDialog.chooseProcess(cmlProject, getShell());

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
											&& isCmlProject((IProject) object))
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

		rbSimulate = createRadioButton(group, "Simulate");
		rbSimulate.addSelectionListener(fListener);

	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
	{
		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_IS_ANIMATION, false);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration)
	{
		try
		{
			fProjectText.setText(configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROJECT, ""));

			fTopProcessText.setText(configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_PROCESS_NAME, ""));

			hostText.setText(configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_HOST, "localhost:8882"));

			if (configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_IS_ANIMATION, true))
			{
				rbAnimate.setSelection(true);
				rbSimulate.setSelection(false);
			} else
			{
				rbAnimate.setSelection(false);
				rbSimulate.setSelection(true);
			}

			if (configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_IS_COORDINATOR, true))
			{
				coordinatorRadio.setSelection(true);
				externalSysRadio.setSelection(false);
				configureExternalSystemButton.setEnabled(externalSysRadio.getSelection());
				String externalProcesses = configuration.getAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_EXTERNAL_PROCESSES, "");
				if (!externalProcesses.isEmpty())
				{
					externalProcessesList.setItems(externalProcesses.split(","));
				}
				externalProcessesList.setEnabled(!externalSysRadio.getSelection());
				selectExternalProcessesButton.setEnabled(!externalSysRadio.getSelection());
			} else
			{
				coordinatorRadio.setSelection(false);
				externalSysRadio.setSelection(true);
				configureExternalSystemButton.setEnabled(externalSysRadio.getSelection());
				externalProcessesList.setData(null);
				externalProcessesList.setEnabled(false);
				selectExternalProcessesButton.setEnabled(false);
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

		if (coordinatorRadio.getSelection())
		{
			configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_IS_COORDINATOR, true);
			configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_EXTERNAL_PROCESSES, encodeArrayAsCoommaSeperatedString(externalProcessesList.getItems()));
		} else
		{
			configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_IS_COORDINATOR, false);
			configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_EXTERNAL_PROCESSES, "");
		}

		configuration.setAttribute(ICmlDebugConstants.CML_LAUNCH_CONFIG_COSIM_HOST, hostText.getText());

	}

	@Override
	public String getName()
	{
		return "Main";
	}

	private void typeCheckModel(ICmlProject cmlProject)
	{
		if (!cmlProject.getModel().isTypeChecked())
		{
			try
			{
				cmlProject.typeCheck(new NullProgressMonitor());
			} catch (CoreException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
