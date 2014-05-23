package eu.compassresearch.ide.collaboration.ui.menu;

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.swt.widgets.Shell;

import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationConfiguration;
import eu.compassresearch.ide.collaboration.distributedsimulation.DistributedSimulationManager;

public class DistributedSimulationInitialisationDialog extends TitleAreaDialog
{
	private Button btnSimulate;
	private Button btnRequestSimulation;
	private List<DistributedSimulationEntry> simulationEntries;
	private List<String> processes;
	private List<String> collaborators;
	private DistributedSimulationManager distributedSimulationManager;

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

		createTitle(area);

		for (String p : processes)
		{
			createLine(p, collaborators, area);
		}

		return area;
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
		}

		btnRequestSimulation.setEnabled(false);
		btnRequestSimulation.setText("Request sent ..");

		distributedSimulationManager.requestSimulationFromCollaborators();
	}

	private void createTitle(Composite area)
	{
		Composite container = new Composite(area, SWT.FILL);
		container.setLayout(new GridLayout(3, true));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label lblTitleExternalProcess = new Label(container, SWT.FILL);
		lblTitleExternalProcess.setText("External Process");

		Label lblTitleCollaborator = new Label(container, SWT.FILL);
		lblTitleCollaborator.setText("To be simulated by");
		GridData gd = new GridData(GridData.CENTER);
		gd.widthHint = 220;
		lblTitleCollaborator.setLayoutData(gd);

		Label lblTitleStatus = new Label(container, SWT.FILL);
		lblTitleStatus.setText("Status");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.widthHint = 45;
		lblTitleStatus.setLayoutData(gd);
	}

	private void createLine(String process, List<String> collaboratorList,
			Composite area)
	{
		DistributedSimulationEntry distributedSimulationEntry = new DistributedSimulationEntry(process, collaboratorList, area, SWT.NONE);
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
}
