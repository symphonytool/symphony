package eu.compassresearch.ide.collaboration.menu;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

public class CollaborationRequestDialog extends TitleAreaDialog
{
	private Text txtTitle;
	private Text txtDescription;
	private List listProject;

	private String receiver;
	private String title;
	private String description;
	private String project;

	public CollaborationRequestDialog(String receiverName, Shell parentShell)
	{
		super(parentShell);
		receiver = receiverName;

		this.setHelpAvailable(false);
	}

	@Override
	public void create()
	{
		super.create();
		setTitle("Send collaboration request");
		setMessage("Send to: " + receiver, IMessageProvider.NONE);
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
		
		
		Label lbtMessage = new Label(container, SWT.NONE);
		lbtMessage.setText("Message");

		GridData gdDescription = new GridData();
		gdDescription.grabExcessHorizontalSpace = true;
		gdDescription.grabExcessVerticalSpace = true;
		gdDescription.horizontalAlignment = GridData.FILL;
		txtDescription = new Text(container, SWT.BORDER | SWT.MULTI);
		gdDescription.heightHint = 5 * txtDescription.getLineHeight();
		txtDescription.setLayoutData(gdDescription);

		Label lblTxtProject = new Label(container, SWT.NONE);
		lblTxtProject.setText("Attach collaborative work \nto this project:");

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

		if (txtTitle.getText().isEmpty())
		{
			setErrorMessage("Please enter a title");
		} else {
			setErrorMessage(null);
			
			if(listProject.getSelectionCount() != 0){
				okButton.setEnabled(true);
			}
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID, "Send", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", true);
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
