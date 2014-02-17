/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.views.markers.MarkerItem;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.faulttolerance.Message;
import eu.compassresearch.ide.faulttolerance.UnableToRunFaultToleranceVerificationException;
import eu.compassresearch.ide.faulttolerance.jobs.IMarkerModifier;
import eu.compassresearch.ide.ui.editor.core.CmlEditor;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.handlers, class SelectProcessHandler"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public abstract class SelectProcessHandler extends AbstractHandler implements
		IMarkerModifier {

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
	public final Object execute(ExecutionEvent event) throws ExecutionException {

		try {
			ISelection selection = HandlerUtil.getCurrentSelection(event);
			IEditorPart editor = HandlerUtil.getActiveEditor(event);

			if (editor instanceof CmlEditor) {
				IProject proj = getCurrentlySelectedProject(event);
				ICmlProject cmlProj = (ICmlProject) proj
						.getAdapter(ICmlProject.class);
				if (cmlProj == null) {
					return null;
				}
				doOnCmlEditor(selection, (CmlEditor) editor, cmlProj,
						HandlerUtil.getActiveShell(event));
			}

			return null;
		} catch (UnableToRunFaultToleranceVerificationException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
	}

	private void doOnCmlEditor(ISelection selection, CmlEditor editor,
			ICmlProject proj, Shell shell) throws ExecutionException {
		if (selection instanceof ITextSelection) {
			doOnSelectedProject((ITextSelection) selection, editor, proj, shell);
		} else if (selection instanceof ITreeSelection) {
			doOnSelectedProject((ITreeSelection) selection, editor, proj, shell);
		}
	}

	private void doOnSelectedProject(ITreeSelection selection,
			CmlEditor editor, ICmlProject proj, Shell shell)
			throws ExecutionException {
		Object o = selection.getFirstElement();
		if (o instanceof MarkerItem) {
			doOnSelectedProject((MarkerItem) o, editor, proj, shell);
		} else if (o instanceof AProcessDefinition) {
			doOnSelectedProcess(proj, (AProcessDefinition) o, shell);
		}
	}

	private void doOnSelectedProject(MarkerItem markerItem, CmlEditor editor,
			ICmlProject proj, Shell shell) throws ExecutionException {
		String systemName = markerItem.getAttributeValue(ATTRIBUTE_SYSTEM_NAME,
				null);
		if (systemName == null) {
			return;
		}
		boolean done = false;
		for (ICmlSourceUnit su : proj.getModel().getSourceUnits()) {
			for (PDefinition def : su.getParseListDefinitions()) {
				if (def.getName().getFullName().equals(systemName)) {
					doOnSelectedProcess(su, def, shell);
					done = true;
					break;
				}
			}
			if (done) {
				break;
			}
		}
	}

	private void doOnSelectedProject(ITextSelection selection,
			CmlEditor editor, ICmlProject proj, Shell shell)
			throws ExecutionException {
		INode o = editor.getElementAt(selection.getOffset());

		if (o instanceof AProcessDefinition) {
			AProcessDefinition apd = (AProcessDefinition) o;
			doOnSelectedProcess(proj, apd, shell);
		}
	}

	private void doOnSelectedProcess(ICmlProject cmlProj,
			AProcessDefinition apd, Shell shell) throws ExecutionException {

		boolean done = false;
		for (ICmlSourceUnit su : cmlProj.getModel().getSourceUnits()) {
			for (PDefinition def : su.getParseListDefinitions()) {
				if (def.getName().equals(apd.getName())) {
					doOnSelectedProcess(su, apd, shell);
					done = true;
					break;
				}
			}
			if (done) {
				break;
			}
		}
	}

	protected abstract void doOnSelectedProcess(ICmlSourceUnit sourceUnit,
			PDefinition processDefinition, Shell shell)
			throws ExecutionException;

}
