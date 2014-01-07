/**
 * 
 */
package eu.compassresearch.ide.recoverymechanismsverification.view;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.ide.recoverymechanismsverification.Image;
import eu.compassresearch.ide.recoverymechanismsverification.Messages;
import eu.compassresearch.ide.recoverymechanismsverification.jobs.DivergenceFreeJob;
import eu.compassresearch.ide.recoverymechanismsverification.jobs.FullFaultToleranceJob;
import eu.compassresearch.ide.recoverymechanismsverification.jobs.LimitedFaultToleranceJob;
import eu.compassresearch.ide.recoverymechanismsverification.jobs.SemifairnessJob;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.recoverymechanismsverification.view, class VerificationResultsView"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class VerificationResultsView extends ViewPart {
	private static final String ID = "eu.compassresearch.ide.recoverymechanismsverification.view.VerificationResultsView";

	private Label processName;
	private Label divergenceFreeLabel;
	private Label semifairnessLabel;
	private Label fullFaultToleranceLabel;
	private Label limitedFaultToleranceLabel;
	private Action runRMV;

	@Override
	public void createPartControl(Composite parent) {
		GridLayout grid = new GridLayout(1, false);
		parent.setLayout(grid);

		createLabels(parent);
		createActions(parent);
		createToolBar(parent);
	}

	private void createActions(final Composite parent) {
		runRMV = new Action(Messages.RUN_RMV.getName()) {
			@Override
			public void run() {
				DivergenceFreeJob dfj = new DivergenceFreeJob();
				SemifairnessJob sj = new SemifairnessJob();
				FullFaultToleranceJob fftj = new FullFaultToleranceJob();
				LimitedFaultToleranceJob lftj = new LimitedFaultToleranceJob(
						parent);

				dfj.addJobChangeListener(new LabelJobStatusListener(
						divergenceFreeLabel));
				sj.addJobChangeListener(new LabelJobStatusListener(
						semifairnessLabel));
				fftj.addJobChangeListener(new LabelJobStatusListener(
						fullFaultToleranceLabel));
				lftj.addJobChangeListener(new LabelJobStatusListener(
						limitedFaultToleranceLabel));

				IProgressMonitor pm = Job.getJobManager().createProgressGroup();
				dfj.setProgressGroup(pm, 100);
				sj.setProgressGroup(pm, 100);
				fftj.setProgressGroup(pm, 100);
				lftj.setProgressGroup(pm, 100);

				sj.schedule();
				dfj.schedule();

				fftj.schedule();
				lftj.schedule();
			}
		};

		runRMV.setImageDescriptor(Image.RELOAD.getImageDescriptor());
	}

	private void createToolBar(Composite parent) {
		IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
		mgr.add(runRMV);
	}

	private void createLabels(Composite parent) {
		processName = new Label(parent, 0);
		divergenceFreeLabel = new Label(parent, 0);
		semifairnessLabel = new Label(parent, 0);
		fullFaultToleranceLabel = new Label(parent, 0);
		limitedFaultToleranceLabel = new Label(parent, 0);

		processName.setText("System");
		divergenceFreeLabel.setText(Messages.DIVERGENCE_FREE_VERIFICATION
				.getName());
		semifairnessLabel.setText(Messages.SEMIFAIRNESS_VERIFICATION.getName());
		fullFaultToleranceLabel
				.setText(Messages.FULL_FAULT_TOLERANCE_VERIFICATION.getName());
		limitedFaultToleranceLabel
				.setText(Messages.LIMITED_FAULT_TOLERANCE_VERIFICATION
						.getName());
	}

	@Override
	public void setFocus() {
	}

}
