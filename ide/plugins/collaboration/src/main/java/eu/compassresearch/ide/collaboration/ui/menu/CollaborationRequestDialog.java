package eu.compassresearch.ide.collaboration.ui.menu;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.compassresearch.ide.collaboration.notifications.Notification;

public class CollaborationRequestDialog extends TitleAreaDialog
{
	private Text txtTitle;
	private Text txtDescription;

	private String receiver;
	private String title;
	private String description;

	public CollaborationRequestDialog(String projectTitle, String receiverName, Shell parentShell)
	{
		super(parentShell);
		receiver = receiverName;
		title = projectTitle;
		
		this.setHelpAvailable(false);
	}

	@Override
	public void create()
	{
		super.create();
		setTitle(Notification.Collab_Dialog_COLLAB_REQUEST_TITLE);
		setMessage(Notification.Collab_Dialog_COLLAB_REQUEST_MSG + " " + receiver, IMessageProvider.NONE);
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
		txtTitle.setText(title);
		txtTitle.setEnabled(false);
		
		Label lbtMessage = new Label(container, SWT.NONE);
		lbtMessage.setText("Message");

		GridData gdDescription = new GridData();
		gdDescription.grabExcessHorizontalSpace = true;
		gdDescription.grabExcessVerticalSpace = true;
		gdDescription.horizontalAlignment = GridData.FILL;
		txtDescription = new Text(container, SWT.BORDER | SWT.MULTI);
		gdDescription.heightHint = 5 * txtDescription.getLineHeight();
		txtDescription.setLayoutData(gdDescription);;

		txtDescription.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent arg0)
			{
				validate();
			}
		});
		
		return area;
	}

	private void validate()
	{
		Button okButton = getButton(IDialogConstants.OK_ID);

		if (txtDescription.getText().isEmpty())
		{
			setErrorMessage("Please enter a description");
			okButton.setEnabled(false);
		} else {
			setErrorMessage(null);
			okButton.setEnabled(true);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID, Notification.Collab_Dialog_BTN_Send, true);
		createButton(parent, IDialogConstants.CANCEL_ID, Notification.Collab_Dialog_BTN_CANCEL, true);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	@Override
	protected void okPressed()
	{
		description = txtDescription.getText();

		super.okPressed();
	}

	public String getDescription()
	{
		return description;
	}
}
