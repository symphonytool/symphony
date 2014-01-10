/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceHelper;
import eu.compassresearch.ide.faulttolerance.jobs.FaultToleranceVerificationResults;
import eu.compassresearch.ide.faulttolerance.marker.MarkerManager;
import eu.compassresearch.ide.ui.editor.core.CmlEditor;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class FaultToleranceVerificationHandler"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class FaultToleranceVerificationHandler extends AbstractHandler {

	private MarkerManager markerManager;

	public FaultToleranceVerificationHandler() {
		markerManager = new MarkerManager();
	}

	@Override
	public void dispose() {
		super.dispose();
		markerManager = null;
	}

	private static IProject getCurrentlySelectedProject(ExecutionEvent event)
			throws UnableToRunFaultToleranceVerificationException {

		IFileEditorInput fileEditorInput = (IFileEditorInput) HandlerUtil
				.getActiveEditorInput(event);

		if (fileEditorInput == null || fileEditorInput.getFile() == null) {
			throw new UnableToRunFaultToleranceVerificationException(
					Message.NO_PROJECT_SELECTED);
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
				verify((ITextSelection) selection, (CmlEditor) editor, proj,
						HandlerUtil.getActiveShell(event));
			}
			return null;
		} catch (UnableToRunFaultToleranceVerificationException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
	}

	private void verify(ITextSelection selection, CmlEditor editor,
			IProject proj, Shell shell) {
		INode o = editor.getElementAt(selection.getOffset());

		if (o instanceof AProcessDefinition) {
			AProcessDefinition apd = (AProcessDefinition) o;
			verifyProcess(proj, apd, shell);
		}

	}

	private void verifyProcess(IProject proj, AProcessDefinition apd,
			Shell shell) {
		ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);

		boolean done = false;
		for (ICmlSourceUnit su : cmlProj.getModel().getSourceUnits()) {
			for (PDefinition def : su.getParseListDefinitions()) {
				if (def.equals(apd)) {
					verifyProcess(su, apd, shell);
					done = true;
					break;
				}
			}
			if (done) {
				break;
			}
		}
	}

	private void verifyProcess(ICmlSourceUnit su, AProcessDefinition apd,
			Shell shell) {
		FaultToleranceVerificationResults results = new FaultToleranceVerificationResults();
		results.setLocation(apd.getLocation());
		results.setResource(su.getFile());
		results.setProcessName(apd.getName().getFullName());

		InputDialog id = new InputDialog(shell,
				Message.LIMIT_EXPRESSION_DIALOG_TITLE.format(results
						.getProcessName()),
				Message.LIMIT_EXPRESSION_DIALOG_MESSAGE.format(results
						.getProcessName()), Message.LIMIT_EXPRESSION.format(),
				null);
		id.open();

		results.setLimitExpression(id.getValue());
		if (id.getReturnCode() == InputDialog.CANCEL) {
			return;
		}

		MarkerManager.clearMarkers(results.getProcessName(),
				results.getResource());
		FaultToleranceHelper.schedule(results, markerManager);
	}

	@Override
	public boolean isEnabled() {
		return Activator.getDefault().isModelCheckerOk();
	}

	@Override
	public boolean isHandled() {
		return true;
	}

}
