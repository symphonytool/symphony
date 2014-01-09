package eu.compassresearch.ide.collaboration.ui.menu;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
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

import eu.compassresearch.ide.collaboration.CollaborationPluginUtils;

public class CollaborationRequestedDialog extends TitleAreaDialog
{
	private List listProject;

	private String project;

	private String sender;
	private String title;
	private String message;

	public CollaborationRequestedDialog(String sender, String title, String message, Shell parentShell)
	{
		super(parentShell);
		this.sender = sender;
		this.title = title;
		this.message = message;
		
		this.setHelpAvailable(false);
	}

	@Override
	public void create()
	{
		super.create();
		
		setTitle("Collaboration request received");
		String msg = "Collaboration request received from: " +  sender + ". \n\n Join Collaboration group?";
		
		setMessage(msg, IMessageProvider.NONE);
		
		
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

		Label lblTitleLabel = new Label(container, SWT.NONE);
		lblTitleLabel.setText("Title: ");
		
		Label lblTitle = new Label(container, SWT.NONE);
		lblTitle.setText(title);
		
		Label lblMessageLabel = new Label(container, SWT.NONE);
		lblMessageLabel.setText("Message from \nsender: ");
		
		Label lblMessage = new Label(container, SWT.NONE);
		lblMessage.setText(message);
		
		Label lblTxtProject= new Label(container, SWT.NONE);
		lblTxtProject.setText("Attach collaborative work \nto this project:");
		
		GridData gdProject = new GridData();
		gdProject.grabExcessHorizontalSpace = true;
		gdProject.horizontalAlignment = GridData.FILL;
		listProject = new List (container, SWT.BORDER | SWT.V_SCROLL);
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

		if(listProject.getSelectionCount() != 0){
				okButton.setEnabled(true);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID,"Yes, Collaborate",true);
		createButton(parent, IDialogConstants.CANCEL_ID,"No, Don't collaborate",true); 
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}
	

	@Override
	protected void okPressed()
	{
		project	= listProject.getSelection()[0];

		super.okPressed();
	}
	
	public String getProject()
	{
		return project;
	}
}
