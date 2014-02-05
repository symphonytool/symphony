package eu.compassresearch.ide.collaboration.ui.menu;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import eu.compassresearch.ide.collaboration.distributedsimulation.ISimulationListener;

public class DistributedSimulationEntry implements ISimulationListener
{
	private Group container;
	private Button btnConfirm;
	private Combo cmbCollaborator;
	private Label lblaccepted;
	private String selectedCollaborator;
	private String process;
	private boolean processSelected;
	private List<String> collaborators;

	public DistributedSimulationEntry(String process, List<String> collaborators,Composite parent, int style)
	{
		container = new Group(parent, style);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 1;
		container.setLayoutData(gd);
		container.setLayout(new GridLayout(3, true));
		
		this.process = process;
		this.collaborators = collaborators;
		selectedCollaborator = "";
		
	    createElements();
	}

	private void createElements()
	{
		btnConfirm = new Button(container, SWT.CHECK);
	    btnConfirm.setText(process);
	    GridData gd = new GridData(GridData.CENTER);
		gd.widthHint = 180;
		btnConfirm.setLayoutData(gd);
		
	    btnConfirm.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	
	        	processSelected();
	        }
	    });
	    
	    cmbCollaborator = new Combo(container, SWT.READ_ONLY);
	    cmbCollaborator.setBounds(50, 50, 150, 50);
	    cmbCollaborator.setItems(collaborators.toArray(new String[0]));
	    cmbCollaborator.setVisible(false);
	    cmbCollaborator.setLayoutData(new GridData(GridData.CENTER));
	    cmbCollaborator.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	       
	        	collaboratorSelected();
	        }
		});

	    
		lblaccepted = new Label(container, SWT.NONE);
		lblaccepted.setText("Approve: ");
		lblaccepted.setVisible(false);
		lblaccepted.setSize(100, 50);
		lblaccepted.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
	}
	
	private void collaboratorSelected()
	{        	
    	selectedCollaborator = cmbCollaborator.getItem(cmbCollaborator.getSelectionIndex());	
	}

	private void processSelected(){
		
		if(btnConfirm.getSelection()){
			cmbCollaborator.setVisible(true);
			processSelected = true;
			
		} else {
			cmbCollaborator.setVisible(false);
			processSelected = false;
		}
	}
	
	private void setAccepted(boolean isAccepted){
		
		if(isAccepted){
			lblaccepted.setText("Ready");
			lblaccepted.setForeground(lblaccepted.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));
			
		} else {
			lblaccepted.setText("Rejected");
			lblaccepted.setForeground(lblaccepted.getDisplay().getSystemColor(SWT.COLOR_RED));
		}	
		lblaccepted.setVisible(true);
	}
	
	public String getSelectedCollaborator()
	{
		return selectedCollaborator;
	}

	public String getProcess()
	{
		return process;
	}

	public boolean hasSelection()
	{
		return processSelected && !selectedCollaborator.isEmpty();
	}

	@Override
	public void onRequestUpdate()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMsgUpdated(String requestedProcess, boolean accepted)
	{
		if(requestedProcess.equals(process)) {
			setAccepted(accepted);
		}
	}
}
