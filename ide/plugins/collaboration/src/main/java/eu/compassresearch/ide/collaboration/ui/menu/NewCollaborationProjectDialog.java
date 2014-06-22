package eu.compassresearch.ide.collaboration.ui.menu;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.compassresearch.ide.collaboration.CollaborationPluginUtils;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public class NewCollaborationProjectDialog extends TitleAreaDialog
{
	private Text txtTitle;
	private Text txtDescription;
	private List listProject;

	private String title;
	private String description;
	private String project;
	java.util.List<CollaborationProject> collabProjects;

	public NewCollaborationProjectDialog(java.util.List<CollaborationProject> collabProjects, Shell parentShell)
	{
		super(parentShell);
		this.collabProjects = collabProjects;
		this.setHelpAvailable(false);
	}

	@Override
	public void create()
	{
		super.create();
		setTitle(Notification.Collab_Dialog_NEW_COLLAB_TITLE);
		setMessage(Notification.Collab_Dialog_NEW_COLLAB_MSG, IMessageProvider.NONE);
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(750, 350);
	} 

	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(layout);

		Label lblTxtTitle = new Label(container, SWT.NONE);
		lblTxtTitle.setText("Collaboration Title");

		GridData gdTitle = new GridData();
		gdTitle.grabExcessHorizontalSpace = true;
		gdTitle.horizontalAlignment = GridData.FILL;

		txtTitle = new Text(container, SWT.BORDER);
		txtTitle.setLayoutData(gdTitle);

		txtTitle.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent arg0)
			{
				validate();
			}
		});

		Label lbtDesc = new Label(container, SWT.NONE);
		lbtDesc.setText("Description");

		GridData gdDescription = new GridData();
		gdDescription.grabExcessHorizontalSpace = true;
		gdDescription.grabExcessVerticalSpace = true;
		gdDescription.horizontalAlignment = GridData.FILL;
		txtDescription = new Text(container, SWT.BORDER | SWT.MULTI);
		gdDescription.heightHint = 5 * txtDescription.getLineHeight();
		txtDescription.setLayoutData(gdDescription);

		Label lblTxtProject = new Label(container, SWT.NONE);
		lblTxtProject.setText(Notification.Collab_Dialog_NEW_COLLAB_ATTACH);

		GridData gdProject = new GridData();
		gdProject.grabExcessHorizontalSpace = true;
		gdProject.horizontalAlignment = GridData.FILL;
		listProject = new List(container, SWT.BORDER | SWT.V_SCROLL);
		listProject.setLayoutData(gdProject);

		for (IProject project : CollaborationPluginUtils.getProjectsInWorkbench())
		{
			listProject.add(project.getName());
		}

		listProject.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				validate();
			}
		});

		return area;
	}

	private void validate()
	{
		Button okButton = getButton(IDialogConstants.OK_ID);

		String title = txtTitle.getText().trim();

		// check existing collaborations
		boolean configurationExists = false;
		for (CollaborationProject project : collabProjects)
		{
			if (project.getTitle().equals(title))
			{
				setErrorMessage(Notification.Collab_Dialog_NEW_COLLAB_ERROR_TITLE_EXISTS);
				configurationExists = true;
				break;
			}

			String[] selection = listProject.getSelection();
			if (selection.length > 0 && project.getProjectWorkspaceName().equals(selection[0]))
			{
				setErrorMessage(Notification.Collab_Dialog_NEW_COLLAB_ERROR_COLLABORATION_ALREADY_ATTACHED
						+ " "  + project.getTitle());
				configurationExists = true;
				break;
			}
		}

		if (configurationExists)
		{
			okButton.setEnabled(false);
		} else if (title.isEmpty())
		{
			setErrorMessage("Please enter a title");
			okButton.setEnabled(false);
		} else
		{
			setErrorMessage(null);

			if (listProject.getSelectionCount() != 0 && !configurationExists)
			{
				okButton.setEnabled(true);
			}
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID, Notification.Collab_Dialog_BTN_OK, true);
		createButton(parent, IDialogConstants.CANCEL_ID, Notification.Collab_Dialog_BTN_CANCEL, true);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	@Override
	protected void okPressed()
	{
		title = txtTitle.getText();
		description = txtDescription.getText();
		project = listProject.getSelection()[0];

		super.okPressed();
	}

	public String getTitle()
	{
		return title;
	}

	public String getDescription()
	{
		return description;
	}

	public String getProject()
	{
		return project;
	}
}
