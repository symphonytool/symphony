/**
 * 
 */
package eu.compassresearch.ide.faulttolerance;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance, class ClearFaultToleranceHandler"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class ClearFaultToleranceHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IEditorInput input = HandlerUtil.getActiveEditorInput(event);

			IResource res = (IResource) input.getAdapter(IResource.class);

			res.deleteMarkers(Activator.MARKERS_ID, true,
					IResource.DEPTH_INFINITE);
			return null;
		} catch (CoreException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener listener) {
	}

}
