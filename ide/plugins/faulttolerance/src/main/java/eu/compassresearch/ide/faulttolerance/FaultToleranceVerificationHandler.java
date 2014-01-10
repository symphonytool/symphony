/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
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

	@Override
	public void addHandlerListener(IHandlerListener listener) {
	}

	@Override
	public void dispose() {
	}

	private static IProject getCurrentlySelectedProject(ExecutionEvent event)
			throws UnableToRunFaultToleranceVerificationException {

		IFileEditorInput fileEditorInput = (IFileEditorInput) HandlerUtil
				.getActiveEditorInput(event);

		if (fileEditorInput == null || fileEditorInput.getFile() == null) {
			throw new UnableToRunFaultToleranceVerificationException(
					Messages.NO_PROJECT_SELECTED);
		}

		return fileEditorInput.getFile().getProject();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			ISelection selection = HandlerUtil.getCurrentSelection(event);
			IEditorPart editor = HandlerUtil.getActiveEditor(event);

			if (selection instanceof ITextSelection
					&& editor instanceof CmlEditor) {
				IProject proj = getCurrentlySelectedProject(event);
				verify((ITextSelection) selection, (CmlEditor) editor, proj);
			}
			return null;
		} catch (UnableToRunFaultToleranceVerificationException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
	}

	private void verify(ITextSelection selection, CmlEditor editor,
			IProject proj) {
		INode o = editor.getElementAt(selection.getOffset());

		if (o instanceof AProcessDefinition) {
			AProcessDefinition apd = (AProcessDefinition) o;
			verifyProcess(proj, apd);
		}

	}

	private void verifyProcess(IProject proj, AProcessDefinition apd) {
		ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);

		boolean done = false;
		for (ICmlSourceUnit su : cmlProj.getModel().getSourceUnits()) {
			for (PDefinition def : su.getParseListDefinitions()) {
				if (def.equals(apd)) {
					verifyProcess(su, apd);
					done = true;
					break;
				}
			}
			if (done) {
				break;
			}
		}
	}

	private void verifyProcess(ICmlSourceUnit su, AProcessDefinition apd) {
		// TODO Select Resource

		FaultToleranceVerificationResults results = new FaultToleranceVerificationResults();
		// TODO: limit expression
		results.setLimitExpression("TODO: limit expression");
		results.setLocation(apd.getLocation());
		results.setResource(su.getFile());

		results.setProcessName(apd.getName().getFullName());
		final FullFaultToleranceVerificationJob fftj = new FullFaultToleranceVerificationJob(
				results);
		final LimitedFaultToleranceVerificationJob lftj = new LimitedFaultToleranceVerificationJob(
				results);

		fftj.add(this);
		lftj.add(this);

		clearMarkers(results.getProcessName(), results.getResource());

		fftj.schedule();
		lftj.schedule();
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

	private void renewMarker(int severity, String message, String processName,
			String limitProcess, IResource selectedResource,
			ILexLocation location) {
		try {
			if (selectedResource != null) {

				IMarker marker = selectedResource
						.createMarker(Activator.MARKERS_ID);
				marker.setAttribute(IMarker.LOCATION, Messages.MARKER_LOCATION
						.getText(location.getStartLine(),
								location.getStartPos()));
				marker.setAttribute(IMarker.SEVERITY, severity);
				marker.setAttribute(IMarker.MESSAGE, message);
				marker.setAttribute(IMarker.LINE_NUMBER,
						location.getStartLine());
				marker.setAttribute(IMarker.CHAR_START,
						location.getStartOffset());
				marker.setAttribute(IMarker.CHAR_END,
						location.getEndOffset() + 1);
				if (processName != null) {
					marker.setAttribute("processName", processName);
				}
				if (limitProcess != null) {
					marker.setAttribute("limitProcess", limitProcess);
				}
			}
		} catch (CoreException e) {
			//
		}
	}

	private void clearMarkers(String processName, IResource selectedResource) {
		try {
			IMarker[] markers = selectedResource.findMarkers(
					Activator.MARKERS_ID, true, IResource.DEPTH_INFINITE);
			for (IMarker marker : markers) {
				if (processName.equals(marker.getAttribute("processName"))) {
					marker.delete();
				}
			}
		} catch (CoreException e) {
			//
		}
	}

	@Override
	public void fullFaultToleranceVerificationFinished(
			FaultToleranceVerificationEvent event) {

		int severity;
		String message;

		if (event.isSuccess()) {
			severity = IMarker.SEVERITY_WARNING;
			message = Messages.FULL_FAULT_TOLERANCE_SUCCESS.getText();
		} else {
			severity = IMarker.SEVERITY_INFO;
			message = Messages.FULL_FAULT_TOLERANCE_ERROR.getText(event
					.getResults().getProcessName());
		}
		renewMarker(severity, message, event.getResults().getProcessName(),
				null, event.getResults().getResource(), event.getResults()
						.getLocation());
	}

	@Override
	public void limitedFaultToleranceVerificationStarted() {
	}

	@Override
	public void limitedFaultToleranceVerificationFinished(
			FaultToleranceVerificationEvent event) {
		int severity;
		String message;

		if (event.isSuccess()) {
			severity = IMarker.SEVERITY_INFO;
			message = Messages.LIMITED_FAULT_TOLERANCE_SUCCESS.getText(event
					.getResults().getProcessName(), event.getResults()
					.getLimitExpression());
		} else {
			severity = IMarker.SEVERITY_ERROR;
			message = Messages.LIMITED_FAULT_TOLERANCE_ERROR.getText(event
					.getResults().getProcessName(), event.getResults()
					.getLimitExpression());
		}

		renewMarker(severity, message, event.getResults().getProcessName(),
				event.getResults().getLimitExpression(), event.getResults()
						.getResource(), event.getResults().getLocation());

	}

}
