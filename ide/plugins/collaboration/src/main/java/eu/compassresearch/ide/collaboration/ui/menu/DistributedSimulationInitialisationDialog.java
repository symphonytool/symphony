package eu.compassresearch.ide.collaboration.ui.menu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationConfiguration;
import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationManager;

public class DistributedSimulationInitialisationDialog extends TitleAreaDialog
{
	private Button btnSimulate;
	private Button btnRequestSimulation;
	private Button btnSimulationRadio;
	private List<String> processes;
	private List<String> collaborators;
	private String selectedTopProcess;
	private List<DistributedSimulationEntry> simulationEntries;
	private DistributedSimulationManager distributedSimulationManager;
	private Composite containerMain;

	public DistributedSimulationInitialisationDialog(List<String> processes,
			List<String> collaborators,
			DistributedSimulationManager distributedSimulationManager,
			Shell parentShell)
	{
		super(parentShell);
		this.processes = processes;
		this.collaborators = collaborators;
		this.distributedSimulationManager = distributedSimulationManager;
		this.setHelpAvailable(false);

		simulationEntries = new ArrayList<>();
		selectedTopProcess = "";
	}

	@Override
	public void create()
	{
		super.create();

		String msg = "Select the process that will act as the coordinator of the simulation.\n"
				+ "Select external processes and attach them to a collaborator";

		setTitle("Initialise Distributed Simulation");
		setMessage(msg, IMessageProvider.NONE);
	}

	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite area = (Composite) super.createDialogArea(parent);
		
		Composite topProcessComposite = new Composite(area, SWT.NULL);
		topProcessComposite.setLayout(new RowLayout());
		
		Label lblTitleExternalProcess = new Label(topProcessComposite, SWT.FILL);
		lblTitleExternalProcess.setText("Select Coordinating process");
		
		final ComboViewer viewer = new ComboViewer(topProcessComposite, SWT.READ_ONLY);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(processes);
		viewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			@Override
			public void selectionChanged(SelectionChangedEvent event)
			{
				 IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				 
				 Object selectedValue = selection.getFirstElement();
				 
				 selectedTopProcess = selectedValue.toString();
			}
		});
		
		Composite radioComposite = new Composite(area, SWT.NULL);
	    radioComposite.setLayout(new RowLayout());
	    
	    btnSimulationRadio = new Button (radioComposite, SWT.RADIO);
	    btnSimulationRadio.setText ("Simulate");
	    btnSimulationRadio.setSelection(true);
		
		Button btnAnimateRadio = new Button (radioComposite, SWT.RADIO);
		btnAnimateRadio.setText ("Animate");
		
		final Composite listComposite = new Composite(area, SWT.NONE);
		listComposite.setLayout(GridLayoutFactory.fillDefaults().create());
		
		createTitle(listComposite);
		
		final ScrolledComposite sc = new ScrolledComposite(listComposite, SWT.BORDER | SWT.V_SCROLL);
		sc.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).hint(600, 400).create());
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		
		containerMain = new Composite(sc, SWT.FILL);
		containerMain.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());

		for (String p : processes)
		{
			createLine(p, collaborators, containerMain);
		}		
		sc.setContent(containerMain);
		sc.setMinSize(containerMain.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return area;
	}

	private void createTitle(Composite area)
	{
		Composite container = new Composite(area, SWT.FILL);
		container.setLayout(new GridLayout(3, true));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label lblTitleExternalProcess = new Label(container, SWT.FILL);
		lblTitleExternalProcess.setText("External Process");
		GridData gd = new GridData(GridData.CENTER);
		gd.widthHint = 230;
		lblTitleExternalProcess.setLayoutData(gd);

		Label lblTitleCollaborator = new Label(container, SWT.FILL);
		lblTitleCollaborator.setText("To be simulated by");
		gd = new GridData(GridData.CENTER);
		gd.widthHint = 210;
		lblTitleCollaborator.setLayoutData(gd);

		Label lblTitleStatus = new Label(container, SWT.FILL);
		lblTitleStatus.setText("Status");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.widthHint = 45;
		lblTitleStatus.setLayoutData(gd);
	}

	private void createLine(String process, List<String> collaboratorList,
			Composite parent)
	{
		DistributedSimulationEntry distributedSimulationEntry = new DistributedSimulationEntry(process, collaboratorList, parent, SWT.NONE);
		simulationEntries.add(distributedSimulationEntry);

		distributedSimulationEntry.addSelectionChangedListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				entriesSelectionChanged();
			}
		});
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", false);
		btnRequestSimulation = createButton(parent, IDialogConstants.PROCEED_ID, "Request Simulation", true);
		btnRequestSimulation.setEnabled(false);
		btnRequestSimulation.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				requestSimulation();
			}
		});

		btnSimulate = createButton(parent, IDialogConstants.OK_ID, "Start Simulatation", true);
		btnSimulate.setEnabled(false);
	}

	protected void entriesSelectionChanged()
	{
		boolean entrySelected = false;

		for (DistributedSimulationEntry entry : simulationEntries)
		{
			if (entry.hasSelection())
			{
				entrySelected = true;
				break;
			}
		}

		if (entrySelected)
		{
			btnRequestSimulation.setEnabled(true);
		} else
		{
			btnRequestSimulation.setEnabled(false);
		}
	}
	
	public void readyForSimulation()
	{
		btnRequestSimulation.setText("Simulation Ready");
		btnSimulate.setEnabled(true);
	}
	
	protected void requestSimulation()
	{
		for (DistributedSimulationEntry entry : simulationEntries)
		{
			if (entry.hasSelection())
			{
				DistributedSimulationConfiguration distConfig = new DistributedSimulationConfiguration(entry.getProcess(), entry.getSelectedCollaborator());

				distributedSimulationManager.addConfiguration(distConfig, entry);
			}
			entry.disableSelection();
			
			if(btnSimulationRadio.getSelection()) {
				distributedSimulationManager.setLaunchMode(false);
			} else {
				distributedSimulationManager.setLaunchMode(true);
			}
		}

		btnRequestSimulation.setEnabled(false);
		btnRequestSimulation.setText("Request sent ..");

		distributedSimulationManager.requestSimulationFromCollaborators();
		distributedSimulationManager.setTopProcess(selectedTopProcess);
	}

	@Override
	protected Point getInitialSize()
	{
		return new Point(700, 600);
	}
}
