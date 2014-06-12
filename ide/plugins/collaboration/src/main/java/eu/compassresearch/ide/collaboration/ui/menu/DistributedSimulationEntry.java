package eu.compassresearch.ide.collaboration.ui.menu;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import eu.compassresearch.ide.collaboration.distributedsimulation.IDistributedSimulationListener;

public class DistributedSimulationEntry implements IDistributedSimulationListener
{
	private Group container;
	private Button btnProcessSelected;
	private Combo cmbCollaborator;
	private Label lblaccepted;
	private String selectedCollaborator;
	private String process;
	private boolean processSelected;
	private List<String> collaborators;
	private List<SelectionListener> listeners;

	public DistributedSimulationEntry(String process, List<String> collaborators, Composite parent, int style)
	{
		container = new Group(parent, style);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 1;
		container.setLayoutData(gd);
		container.setLayout(new GridLayout(3, false));
		
		this.process = process;
		this.collaborators = collaborators;
		selectedCollaborator = "";
		listeners = new ArrayList<>();
		
	    createElements();
	}
	
	private void createElements()
	{
		btnProcessSelected = new Button(container, SWT.CHECK);
	    btnProcessSelected.setText(process);
	    GridData gd = new GridData(GridData.BEGINNING);
		gd.widthHint = 220;
		btnProcessSelected.setLayoutData(gd);
		
	    btnProcessSelected.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	        	
	        	processSelected();
	        	fireSelectionChanged(e);
	        }
	    });
	    
	    cmbCollaborator = new Combo(container, SWT.READ_ONLY);
	    cmbCollaborator.setBounds(50, 50, 150, 50);
	    cmbCollaborator.add("                                    ");
	    cmbCollaborator.setItems(collaborators.toArray(new String[0]));
	    cmbCollaborator.setVisible(false);
	    gd = new GridData(GridData.BEGINNING);
	    gd.widthHint = 220;
	    cmbCollaborator.setLayoutData(gd);
	    cmbCollaborator.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	       
	        	collaboratorSelected();
	        	fireSelectionChanged(e);
	        }
		});

	    Composite labelCell = new Composite(container, SWT.NONE);
	    GridData gridData = new GridData();
	    gridData.widthHint = 260;
	    labelCell.setLayoutData(gridData);
	    labelCell.setLayout(new GridLayout());
	    
		lblaccepted = new Label(labelCell, SWT.NONE);
		lblaccepted.setText("Approve: ");
		lblaccepted.setVisible(false);
		lblaccepted.setSize(100, 50);
		lblaccepted.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
	}
	
	private void collaboratorSelected()
	{        	
    	selectedCollaborator = cmbCollaborator.getItem(cmbCollaborator.getSelectionIndex());	
	}

	private void processSelected(){
		
		if(btnProcessSelected.getSelection()){
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
	
	public void addSelectionChangedListener(SelectionListener listener){
		
		listeners.add(listener);
	}
	
	private void fireSelectionChanged(SelectionEvent e){
		for (SelectionListener listener : listeners)
		{
			listener.widgetSelected(e);
		}
	}
	
	@Override
	public void onRequestUpdated(String requestedProcess, boolean accepted)
	{		
		if(requestedProcess.equals(process)) {
			setAccepted(accepted);
		}
	}

	public void disableSelection()
	{
		if(selectedCollaborator.isEmpty()){
			btnProcessSelected.setSelection(false);
		}
		btnProcessSelected.setEnabled(false);
		cmbCollaborator.setEnabled(false);
	}

	@Override
	public void onSimluationStarting()
	{
		// TODO Auto-generated method stub
	}
}
