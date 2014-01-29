/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.handlers;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.faulttolerance.Activator;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationJob;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationResults;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationStatus;
import eu.compassresearch.ide.faulttolerance.markers.MarkerManager;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class FaultToleranceVerificationHandler"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationHandler extends SelectProcessHandler {

	private MarkerManager markerManager;

	public FaultToleranceVerificationHandler() {
		markerManager = new MarkerManager();
	}

	@Override
	public void dispose() {
		super.dispose();
		markerManager = null;
	}

	@Override
	public boolean isEnabled() {
		return Activator.getDefault().isModelCheckerOk();
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	protected void doOnSelectedProcess(ICmlSourceUnit su,
			PDefinition processDefinition, Shell shell) {
		FaultToleranceVerificationResults results = new FaultToleranceVerificationResults();
		results.setLocation(processDefinition.getLocation());
		results.setResource(su.getFile());
		results.setProcessName(processDefinition.getName().getFullName());
		results.setOutputContainer(su.getProject().getModelBuildPath()
				.getOutput());
		results.setCmlProject(su.getProject());

		/*
		 * InputDialog id = new InputDialog(shell,
		 * Message.LIMIT_EXPRESSION_DIALOG_TITLE.format(results
		 * .getProcessName()),
		 * Message.LIMIT_EXPRESSION_DIALOG_MESSAGE.format(results
		 * .getProcessName()), Message.LIMIT_EXPRESSION.format(), null);
		 * id.open();
		 * 
		 * 
		 * results.setLimitExpression(id.getValue()); if (id.getReturnCode() ==
		 * InputDialog.CANCEL) { return; }
		 */

		MarkerManager.clearMarkers(results.getProcessName(),
				results.getResource());
		try {
			Activator.getDefault().getWorkbench().getActiveWorkbenchWindow()
					.getActivePage()
					.showView("org.eclipse.ui.views.ProgressView");
		} catch (PartInitException e) {
			// ok, keep focus on current window.
		}

		FaultToleranceVerificationJob j = new FaultToleranceVerificationJob(
				results);
		j.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult() instanceof FaultToleranceVerificationStatus) {
					FaultToleranceVerificationResults r = ((FaultToleranceVerificationStatus) event
							.getResult()).getResults();
					markerManager.faultToleranceVerificationsFinished(r);
				}
			}
		});

		j.schedule();
	}

}
