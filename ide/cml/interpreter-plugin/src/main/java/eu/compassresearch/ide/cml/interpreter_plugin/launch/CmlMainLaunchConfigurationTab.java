package eu.compassresearch.ide.cml.interpreter_plugin.launch;

import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.viewers.LabelProvider;
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

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.core.interpreter.debug.CmlInterpreterLaunchConfigurationConstants;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlUtil;

public class CmlMainLaunchConfigurationTab extends
		AbstractLaunchConfigurationTab {

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
	private WidgetListener fListener = new WidgetListener();
	
	@Override
	public void createControl(Composite parent) {
		
		Composite comp = new Composite(parent, SWT.NONE);

		setControl(comp);
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
		// IDebugHelpContextIds.LAUNCH_CONFIGURATION_DIALOG_COMMON_TAB);
		comp.setLayout(new GridLayout(1, true));
		comp.setFont(parent.getFont());

		createProjectSelection(comp);
		createProcessSelection(comp);

	}
	
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		try {
			String projectName = launchConfig.getAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_NAME.toString(), "");
			IProject project = null;

			if (projectName.length() == 0)
			{
				setErrorMessage(CmlLaunchConfigurationErrorMessages.PROJECT_NOT_SET.toString());
				return false;
			}

			project = getProjectByName(projectName);
			
			if(!project.exists())
			{
				setErrorMessage(CmlLaunchConfigurationErrorMessages.NO_PROJECT_WITH_NAME_EXIST.customizeMessage(projectName));
				return false;
			}
			else if (!project.isOpen())
			{
				setErrorMessage(CmlLaunchConfigurationErrorMessages.PROJECT_NOT_OPEN.customizeMessage(projectName));
				return false;
			}
			
			return super.isValid(launchConfig) && isProcessValid(project,launchConfig);
		} catch (CoreException e) {
			
			setErrorMessage(e.getMessage());
			return false;
			//e.printStackTrace();
		}
	}
	
	private boolean isProcessValid(IProject project, ILaunchConfiguration launchConfig) throws CoreException
	{
		String processName = launchConfig.getAttribute(CmlInterpreterLaunchConfigurationConstants.PROCESS_NAME.toString(), "");
		
		if(processName.length() == 0)
		{
			setErrorMessage("Process not set");
			return false;
		}
		
		List<AProcessDefinition> globalProcess = CmlUtil.GetGlobalProcessesFromProject(project);
		
		if(globalProcess.isEmpty())
		{
			setErrorMessage("No Processes defined or CML sources are loaded!");
			return false;
		}
		
		for(AProcessDefinition processDef : globalProcess)
		{
			if(processName.equals(processDef.getName().getName()))
				return true;
		}
		
		setErrorMessage("Process '" + processName + "' is not defined");
		return false;
	}
	
	
//	private List<CmlSourceUnit> getCmlSourcesFromProject(IProject project) throws CoreException
//	{
//		List<CmlSourceUnit> sources = new LinkedList<CmlSourceUnit>();
//		
//		for(IResource res : project.members())
//		{
//			if(res instanceof IFile && ((IFile)res).getFileExtension().toLowerCase().equals(".cml"))
//				sources.add(CmlSourceUnit.getFromFileResource((IFile)res));
//		}
//		
//		return sources;
//	}

	
	
	protected IProject getProjectByName(String projectName)
	{
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
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
				// ListSelectionDialog dlg = new ListSelectionDialog(getShell(),
				// ResourcesPlugin.getWorkspace().getRoot(), new
				// BaseWorkbenchContentProvider(), new
				// WorkbenchLabelProvider(), "Select the Project:");
				// dlg.setTitle("Project Selection");
				// dlg.open();
				class ProcessContentProvider extends
						BaseWorkbenchContentProvider
				{
					@Override
					public boolean hasChildren(Object element)
					{
						if (element instanceof AProcessDefinition)
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
						List<AProcessDefinition> pdefs = (List<AProcessDefinition>)element;
						return pdefs.toArray();
						
						
					}

				};
				
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new LabelProvider()
				{
					@Override
					public String getText(Object element) {

						if(element instanceof AProcessDefinition)
							return ((AProcessDefinition)element).getName().getName();
						else			
							return null;
					}
				
				}, new ProcessContentProvider());
				dialog.setTitle("Process Selection");
				dialog.setMessage("Select a process:");
				dialog.setComparator(new ViewerComparator());

				dialog.setInput( CmlUtil.GetGlobalProcessesFromProject(getProjectByName(fProjectText.getText())));

				if (dialog.open() == Window.OK)
				{
					if (dialog.getFirstResult() != null
							&& dialog.getFirstResult() instanceof AProcessDefinition)
							//&& ((IProject) dialog.getFirstResult()).getAdapter(IVdmProject.class) != null)
					{
						fTopProcessText.setText(((AProcessDefinition) dialog.getFirstResult()).getName().getName());
					}

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
				// ListSelectionDialog dlg = new ListSelectionDialog(getShell(),
				// ResourcesPlugin.getWorkspace().getRoot(), new
				// BaseWorkbenchContentProvider(), new
				// WorkbenchLabelProvider(), "Select the Project:");
				// dlg.setTitle("Project Selection");
				// dlg.open();
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
								//TODO Should only return cml projects. At the point every project is returned!
//								try
//								{
									if (object instanceof IProject)
											//&& (((IProject) object).getAdapter(IVdmProject.class) != null)
											//&& isSupported((IProject) object))
									{
										elements.add((IProject) object);
									}
//								} 
//								catch (CoreException e)
//								{
////									if (VdmDebugPlugin.DEBUG)
////									{
////										e.printStackTrace();
////									}
//								}
							}
							return elements.toArray();
						}
						return null;
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
							&& dialog.getFirstResult() instanceof IProject)
							//&& ((IProject) dialog.getFirstResult()).getAdapter(IVdmProject.class) != null)
					{
						fProjectText.setText(((IProject) dialog.getFirstResult()).getName());
					}

				}
			}
		});
	}
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {

		try {
			String projectName = configuration.getAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_NAME.toString(), "");
			fProjectText.setText(projectName);
			
			String processName = configuration.getAttribute(CmlInterpreterLaunchConfigurationConstants.PROCESS_NAME.toString(), "");
			fTopProcessText.setText(processName);
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		
		configuration.setAttribute(CmlLaunchConfigurationConstants.ATTR_PROJECT_NAME.toString(), 
				fProjectText.getText());
		
		configuration.setAttribute(CmlInterpreterLaunchConfigurationConstants.PROCESS_NAME.toString(),
				fTopProcessText.getText());
						
		if(fProjectText.getText().length() > 0)
		{
			
			IProject project = getProjectByName(fProjectText.getText());
			//Set the project src path
			configuration.setAttribute(CmlInterpreterLaunchConfigurationConstants.CML_SOURCES_PATH.toString(),CmlUtil.getProjectPath(project));
		
		
//			List<AProcessDefinition> globalProcess = CmlUtil.GetGlobalProcessesFromProject(project);
//			
//			for(AProcessDefinition processDef : globalProcess)
//			{
//				if(fTopProcessText.getText().equals(processDef.getName().getName()))
//					configuration.setAttribute(CmlInterpreterLaunchConfiguration.PROCESS_FILE_PATH.toString(),
//							processDef.getLocation().file.getAbsolutePath());
//			}
			
		}
		
		
		
		
		//updateLaunchConfigurationDialog();
	}

	@Override
	public String getName() {
		return "Main";
	}

}
