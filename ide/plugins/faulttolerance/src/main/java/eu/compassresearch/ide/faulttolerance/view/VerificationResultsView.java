/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.ide.faulttolerance.Image;
import eu.compassresearch.ide.faulttolerance.Messages;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationEvent;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationResults;
import eu.compassresearch.ide.faulttolerance.jobs.FullFaultToleranceVerificationJob;
import eu.compassresearch.ide.faulttolerance.jobs.IFaultToleranceVerificationListener;
import eu.compassresearch.ide.faulttolerance.jobs.LimitedFaultToleranceVerificationJob;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.view, class VerificationResultsView"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class VerificationResultsView extends ViewPart implements
		IFaultToleranceVerificationListener {
	public static final String ID = "eu.compassresearch.ide.faulttolerance.view.VerificationResultsView";

	private Label processName;
	private Label divergenceFreeLabel;
	private Label semifairnessLabel;
	private Label fullFaultToleranceLabel;
	private Label limitedFaultToleranceLabel;
	private Text limitExpression;
	private Combo comboProcesses;
	private Action runRMV;

	@Override
	public void createPartControl(Composite parent) {
		GridLayout grid = new GridLayout(1, false);
		parent.setLayout(grid);

		createForm(parent);
		createActions(parent);
		createToolBar(parent);
	}

	private void createActions(final Composite parent) {
		runRMV = new Action(Messages.RUN_RMV.getName()) {
			@Override
			public void run() {
				FaultToleranceVerificationResults results = new FaultToleranceVerificationResults();
				results.setLimitExpression(limitExpression.getText());
				final FullFaultToleranceVerificationJob fftj = new FullFaultToleranceVerificationJob(
						results);
				final LimitedFaultToleranceVerificationJob lftj = new LimitedFaultToleranceVerificationJob(
						results);

				fftj.add(VerificationResultsView.this);
				lftj.add(VerificationResultsView.this);

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

	private void createForm(Composite parent) {
		comboProcesses = new Combo(parent, 0);
		processName = new Label(parent, 0);
		divergenceFreeLabel = new Label(parent, 0);
		semifairnessLabel = new Label(parent, 0);
		fullFaultToleranceLabel = new Label(parent, 0);
		limitedFaultToleranceLabel = new Label(parent, 0);
		limitExpression = new Text(parent, 0);

		processName.setText("System");
		comboProcesses.add("System");
		comboProcesses.add("Recovery");

		divergenceFreeLabel.setText(Messages.DIVERGENCE_FREE_VERIFICATION
				.getName());
		semifairnessLabel.setText(Messages.SEMIFAIRNESS_VERIFICATION.getName());
		fullFaultToleranceLabel
				.setText(Messages.FULL_FAULT_TOLERANCE_VERIFICATION.getName());
		limitedFaultToleranceLabel
				.setText(Messages.LIMITED_FAULT_TOLERANCE_VERIFICATION
						.getName());
		limitExpression.setText(Messages.LIMIT_EXPRESSION.getText());
	}

	@Override
	public void setFocus() {
	}

	private void start(final Label label) {
		final Display d = getViewSite().getShell().getDisplay();
		d.syncExec(new Runnable() {
			@Override
			public void run() {
				label.setForeground(d.getSystemColor(SWT.COLOR_GRAY));
			}
		});

	}

	private void finish(final Label label, final boolean success) {
		final Display d = getViewSite().getShell().getDisplay();
		d.syncExec(new Runnable() {
			@Override
			public void run() {
				int colorCode = success ? SWT.COLOR_DARK_GREEN : SWT.COLOR_RED;
				label.setForeground(d.getSystemColor(colorCode));
			}
		});
	}

	@Override
	public void divergenceFreeVerificationStarted() {
		start(divergenceFreeLabel);
	}

	@Override
	public void divergenceFreeVerificationFinished(
			FaultToleranceVerificationEvent event) {
		finish(divergenceFreeLabel, event.isSuccess());
	}

	@Override
	public void semifairnessVerificationStarted() {
		start(semifairnessLabel);
	}

	@Override
	public void semifairnessVerificationFinished(
			FaultToleranceVerificationEvent event) {
		finish(semifairnessLabel, event.isSuccess());
	}

	@Override
	public void fullFaultToleranceVerificationStarted() {
		start(fullFaultToleranceLabel);
	}

	@Override
	public void fullFaultToleranceVerificationFinished(
			final FaultToleranceVerificationEvent event) {
		final Display d = getViewSite().getShell().getDisplay();
		d.syncExec(new Runnable() {
			@Override
			public void run() {
				int colorCode = SWT.COLOR_DARK_GREEN;
				if (!event.getResults().isPreRequisitesOk()) {
					colorCode = SWT.COLOR_DARK_GRAY;
				} else if (event.isSuccess()) {
					colorCode = SWT.COLOR_DARK_YELLOW;
				}
				fullFaultToleranceLabel.setForeground(d
						.getSystemColor(colorCode));
			}
		});
	}

	@Override
	public void limitedFaultToleranceVerificationStarted() {
		start(limitedFaultToleranceLabel);
	}

	@Override
	public void limitedFaultToleranceVerificationFinished(
			final FaultToleranceVerificationEvent event) {
		final Display d = getViewSite().getShell().getDisplay();
		d.syncExec(new Runnable() {
			@Override
			public void run() {
				int colorCode = SWT.COLOR_DARK_GREEN;
				if (!event.getResults().isPreRequisitesOk()) {
					colorCode = SWT.COLOR_DARK_GRAY;
				} else if (!event.isSuccess()) {
					colorCode = SWT.COLOR_RED;
				}
				limitedFaultToleranceLabel.setForeground(d
						.getSystemColor(colorCode));
			}
		});
	}

}
