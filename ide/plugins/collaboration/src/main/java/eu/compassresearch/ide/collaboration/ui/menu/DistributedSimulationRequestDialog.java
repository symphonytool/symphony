package eu.compassresearch.ide.collaboration.ui.menu;

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

import eu.compassresearch.ide.collaboration.datamodel.CollaborationProject;
import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationManager;

public class DistributedSimulationRequestDialog extends TitleAreaDialog
{

	private String user;
	private CollaborationProject collaborationProject;
	private String process;
	private Button btnRequestSimulation;
	private Button btnSimulate;
	private DistributedSimulationManager distributedSimulationManager;

	public DistributedSimulationRequestDialog(String user, CollaborationProject collaborationProject, String process, DistributedSimulationManager distributedSimulationManager, Shell parentShell)
	{
		super(parentShell);
		this.user = user;
		this.collaborationProject = collaborationProject;
		this.process = process;
		this.distributedSimulationManager = distributedSimulationManager;
		
		this.setHelpAvailable(false);
	}

	@Override
	public void create()
	{
		super.create();
		
		String msg = user + " requested this symphony instance to collaborate in a distributed simulation. Participate?";
			
		setTitle("Distributed Simulation requested.");
		setMessage(msg, IMessageProvider.NONE);
	}
	
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 220);
	}
	
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite area = (Composite) super.createDialogArea(parent);
		
		Composite container = new Composite(area, SWT.FILL);
		container.setLayout(new GridLayout(2, true));
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
		
		return area;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.CANCEL_ID,"No, decline",false); 
		btnRequestSimulation = createButton(parent, IDialogConstants.PROCEED_ID,"Yes, participate",true);
		
		btnRequestSimulation.addSelectionListener(new SelectionAdapter()
		{
		        @Override
		        public void widgetSelected(SelectionEvent e) {
		        	approveSimulation();
		        }
	    });
		
		btnSimulate = createButton(parent, IDialogConstants.OK_ID,"Start Simulatation",true);
		btnSimulate.setEnabled(false);
	}

	protected void approveSimulation()
	{
		distributedSimulationManager.approveSimulation(user, collaborationProject, process);
		//TODO disable approve button
		//TODO add waiting text
	}
}
