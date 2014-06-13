package eu.compassresearch.ide.collaboration.ui.menu;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.UIJob;

import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationManager;
import eu.compassresearch.ide.collaboration.distributedsimulation.IDistributedSimulationListener;

public class DistributedSimulationRequestDialog extends TitleAreaDialog
		implements IDistributedSimulationListener
{
	private String requestedByUser;
	private CollaborationProject collaborationProject;
	private String process;
	private Button btnRequestSimulation;
	private DistributedSimulationManager distributedSimulationManager;
	private Label lblSimulationStatus;
	private Button btnAbort;
	private Label lblTitleStatus;

	public DistributedSimulationRequestDialog(String requestedByUser,
			CollaborationProject collaborationProject, String process,
			DistributedSimulationManager distributedSimulationManager,
			Shell parentShell)
	{
		super(parentShell);
		this.requestedByUser = requestedByUser;
		this.collaborationProject = collaborationProject;
		this.process = process;
		this.distributedSimulationManager = distributedSimulationManager;

		this.setHelpAvailable(false);
	}

	@Override
	public void create()
	{
		super.create();

		String msg = requestedByUser
				+ " requested this symphony instance to collaborate in a distributed simulation. Participate?";

		setTitle("Distributed Simulation requested.");
		setMessage(msg, IMessageProvider.NONE);
	}

	@Override
	protected Point getInitialSize()
	{
		return new Point(620, 300);
	}

	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite area = (Composite) super.createDialogArea(parent);

		Composite container = new Composite(area, SWT.FILL);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label lblTitleCollaborationProject = new Label(container, SWT.FILL);
		lblTitleCollaborationProject.setText("Collaboration Project: ");

		Label lblCollaborationProject = new Label(container, SWT.FILL);
		lblCollaborationProject.setText(collaborationProject.getName());
		lblCollaborationProject.setForeground(lblCollaborationProject.getDisplay().getSystemColor(SWT.COLOR_DARK_BLUE));

		Label lblTitleProcess = new Label(container, SWT.FILL);
		lblTitleProcess.setText("Simulation of process: ");

		Label lblProcess = new Label(container, SWT.FILL);
		lblProcess.setText(process);
		lblProcess.setForeground(lblProcess.getDisplay().getSystemColor(SWT.COLOR_DARK_BLUE));

		lblTitleStatus = new Label(container, SWT.FILL);
		lblTitleStatus.setText("Status: ");

		lblSimulationStatus = new Label(container, SWT.FILL);
		lblSimulationStatus.setText("Approve?");
		GridData gd = new GridData(GridData.END);
		gd.widthHint = 200;
		lblSimulationStatus.setLayoutData(gd);
		return area;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		btnAbort = createButton(parent, IDialogConstants.ABORT_ID, "No, decline", false);
		btnAbort.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				declinedSimulation();
			}
		});

		btnRequestSimulation = createButton(parent, IDialogConstants.PROCEED_ID, "Yes, participate", true);

		btnRequestSimulation.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				approveSimulation();
			}
		});
	}

	protected void declinedSimulation()
	{
		distributedSimulationManager.declineSimulation(requestedByUser, collaborationProject, process);
		this.close();
	}

	protected void approveSimulation()
	{
		distributedSimulationManager.approveSimulation(requestedByUser, collaborationProject, process, this);
		btnRequestSimulation.setEnabled(false);
		btnAbort.setEnabled(false);
		lblSimulationStatus.setText("Pending simulation start");
		btnRequestSimulation.setText("Pending...");
	}

	@Override
	public void onRequestUpdated(String process, boolean accepted)
	{
		// nothing to do
	}

	@Override
	protected void setShellStyle(int newShellStyle)
	{
		super.setShellStyle(SWT.CLOSE | SWT.MODELESS | SWT.BORDER | SWT.TITLE);
		setBlockOnOpen(false);
	}

	@Override
	public void onSimluationStarting()
	{
		btnAbort.setEnabled(false);
		lblSimulationStatus.setText("Simulation Starting");
		lblSimulationStatus.setForeground(lblSimulationStatus.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));

		final DistributedSimulationRequestDialog dialog = this;

		new UIJob("Close Simulation Request dialog")
		{
			public IStatus runInUIThread(IProgressMonitor monitor)
			{
				dialog.okPressed();
				return Status.OK_STATUS;
			}
		}.schedule(2500);
	}
}
