/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationEvent;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationResults;
import eu.compassresearch.ide.faulttolerance.jobs.FullFaultToleranceVerificationJob;
import eu.compassresearch.ide.faulttolerance.jobs.IFaultToleranceVerificationListener;
import eu.compassresearch.ide.faulttolerance.jobs.LimitedFaultToleranceVerificationJob;
import eu.compassresearch.ide.ui.editor.core.CmlEditor;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class FaultToleranceVerificationHandler"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationHandler implements IHandler,
		IFaultToleranceVerificationListener {

	private IResource selectedResource;

	@Override
	public void addHandlerListener(IHandlerListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof ITreeSelection) {
			FaultToleranceVerificationResults results = new FaultToleranceVerificationResults();
			// TODO: limit expression
			results.setLimitExpression("TODO: limit expression");

			// ICmlModel model = (ICmlModel)
			// rooList.getAdapter(ICmlModel.class);
			Object o = ((ITreeSelection) selection).getFirstElement();

			IEditorPart editor = HandlerUtil.getActiveEditor(event);

			selectedResource = (IResource) Platform.getAdapterManager()
					.getAdapter(o, IResource.class);

			// ICmlProject cmlProj = (ICmlProject) adapter.getProject();

			if (o instanceof AProcessDefinition) {
				AProcessDefinition apd = (AProcessDefinition) o;

				if (editor instanceof CmlEditor) {
					CmlEditor cmle = (CmlEditor) editor;
					cmle.selectAndReveal(apd);
				}
				results.setProcessName(apd.getName().getFullName());
				final FullFaultToleranceVerificationJob fftj = new FullFaultToleranceVerificationJob(
						results);
				final LimitedFaultToleranceVerificationJob lftj = new LimitedFaultToleranceVerificationJob(
						results);

				fftj.add(this);
				lftj.add(this);

				fftj.schedule();
				lftj.schedule();
			}

		}
		return null;
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
	public void removeHandlerListener(IHandlerListener listener) {
	}

	@Override
	public void divergenceFreeVerificationStarted() {
	}

	@Override
	public void divergenceFreeVerificationFinished(
			FaultToleranceVerificationEvent event) {
	}

	@Override
	public void semifairnessVerificationStarted() {
	}

	@Override
	public void semifairnessVerificationFinished(
			FaultToleranceVerificationEvent event) {
	}

	@Override
	public void fullFaultToleranceVerificationStarted() {
	}

	@Override
	public void fullFaultToleranceVerificationFinished(
			FaultToleranceVerificationEvent event) {
		try {
			if (selectedResource != null) {
				IMarker marker = selectedResource
						.createMarker("eu.compassresearch.ide.faulttolerance.verification.problem");
				if (event.isSuccess()) {
					marker.setAttribute(IMarker.SEVERITY,
							IMarker.SEVERITY_WARNING);
					marker.setAttribute(IMarker.MESSAGE,
							Messages.FULL_FAULT_TOLERANCE_SUCCESS.getText());
				} else {
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
					marker.setAttribute(IMarker.MESSAGE,
							Messages.FULL_FAULT_TOLERANCE_ERROR.getText());
				}
				marker.setAttribute("processName", event.getResults()
						.getProcessName());

			}
		} catch (CoreException e) {
			//
		}
	}

	@Override
	public void limitedFaultToleranceVerificationStarted() {
	}

	@Override
	public void limitedFaultToleranceVerificationFinished(
			FaultToleranceVerificationEvent event) {
		try {
			if (selectedResource != null) {
				IMarker marker = selectedResource
						.createMarker("eu.compassresearch.ide.faulttolerance.verification.problem");
				if (event.isSuccess()) {
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
					marker.setAttribute(IMarker.MESSAGE,
							Messages.LIMITED_FAULT_TOLERANCE_SUCCESS.getText());
				} else {
					marker.setAttribute(IMarker.SEVERITY,
							IMarker.SEVERITY_ERROR);
					marker.setAttribute(IMarker.MESSAGE,
							Messages.LIMITED_FAULT_TOLERANCE_ERROR.getText());
				}
				marker.setAttribute("limitProcess", event.getResults()
						.getLimitExpression());
				marker.setAttribute("processName", event.getResults()
						.getProcessName());
			}
		} catch (CoreException e) {
			//
		}
	}

}
