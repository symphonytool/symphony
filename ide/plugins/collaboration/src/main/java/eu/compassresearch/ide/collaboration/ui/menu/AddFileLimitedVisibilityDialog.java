package eu.compassresearch.ide.collaboration.ui.menu;

import java.util.Arrays;

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

import eu.compassresearch.ide.collaboration.datamodel.CollaborationGroup;
import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.datamodel.User;

public class AddFileLimitedVisibilityDialog extends TitleAreaDialog
{
	private List listCollaborators;
	private String fileName;
	private CollaborationGroup collaboratorGroup;
	private String[] selectedCollaborators;
	
	public AddFileLimitedVisibilityDialog(String fileName, CollaborationProject collabProject, Shell parentShell)
	{
		super(parentShell);
		this.fileName = fileName;
		
		collaboratorGroup = collabProject.getCollaboratorGroup();
		this.setHelpAvailable(false);
	}
	
	@Override
	public void create()
	{
		super.create();
		
		setTitle("Add file to collaboration with controlled visibility");
		String msg = "Limit file visibility to selected collaborators. Select the collaborators that may see the file: " +  fileName + "";
		
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
		lblTitleLabel.setText("File: ");
		
		Label lblTitle = new Label(container, SWT.NONE);
		lblTitle.setText(fileName);
		
		Label lblTxtProject= new Label(container, SWT.NONE);
		lblTxtProject.setText("Select collaborators:");
		
		GridData gdProject = new GridData();
		gdProject.grabExcessHorizontalSpace = true;
		gdProject.horizontalAlignment = GridData.FILL;
		gdProject.grabExcessVerticalSpace = true;
		gdProject.verticalAlignment = GridData.FILL_VERTICAL;
		gdProject.minimumHeight = 150;
		listCollaborators = new List (container, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		listCollaborators.setLayoutData(gdProject);
		
		for (User user : collaboratorGroup.getCollaborators())
		{
			listCollaborators.add(user.getName());	
		}
		
		listCollaborators.addSelectionListener(new SelectionAdapter()
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

		if(listCollaborators.getSelectionCount() != 0){
				okButton.setEnabled(true);
		}
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID,"Add file",true);
		createButton(parent, IDialogConstants.CANCEL_ID,"Cancel",true); 
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}
	
	@Override
	protected void okPressed()
	{
		selectedCollaborators = listCollaborators.getSelection();

		super.okPressed();
	}

	public java.util.List<String> getSelectedCollaborator()
	{
		return Arrays.asList(selectedCollaborators);
	}
}
