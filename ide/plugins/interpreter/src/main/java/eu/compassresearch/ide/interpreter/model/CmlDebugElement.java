package eu.compassresearch.ide.interpreter.model;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;

import eu.compassresearch.ide.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;

public class CmlDebugElement extends PlatformObject implements IDebugElement
{
	protected CmlDebugTarget cmlDebugTarget;

	public CmlDebugElement(CmlDebugTarget cmlDebugTarget)
	{
		this.cmlDebugTarget = cmlDebugTarget;
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModelIdentifier()
	{
		return ICmlDebugConstants.ID_CML_DEBUG_MODEL.toString();
	}

	@Override
	public IDebugTarget getDebugTarget()
	{
		return cmlDebugTarget;
	}

	@Override
	public ILaunch getLaunch()
	{
		return getDebugTarget().getLaunch();
	}

	protected void abort(String message, Throwable e) throws DebugException
	{
		CmlDebugPlugin.getDefault();
		throw new DebugException(new Status(IStatus.ERROR, CmlDebugPlugin.PLUGIN_ID, DebugPlugin.INTERNAL_ERROR, message, e));
	}

	/**
	 * Fires a debug event
	 * 
	 * @param event
	 *            the event to be fired
	 */
	protected void fireEvent(DebugEvent event)
	{
		DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] { event });
	}

	/**
	 * Fires a <code>CREATE</code> event for this element.
	 */
	public void fireCreationEvent()
	{
		fireEvent(new DebugEvent(this, DebugEvent.CREATE));
	}

	/**
	 * Fires a <code>CREATE</code> event for this element.
	 */
	protected void fireChangeEvent()
	{
		fireEvent(new DebugEvent(this, DebugEvent.CHANGE));
	}

	/**
	 * Fires a <code>RESUME</code> event for this element with the given detail.
	 * 
	 * @param detail
	 *            event detail code
	 */
	public void fireResumeEvent(int detail)
	{
		fireEvent(new DebugEvent(this, DebugEvent.RESUME, detail));
	}

	/**
	 * Fires a <code>SUSPEND</code> event for this element with the given detail.
	 * 
	 * @param detail
	 *            event detail code
	 */
	public void fireSuspendEvent(int detail)
	{
		fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, detail));
	}

	/**
	 * Fires a <code>TERMINATE</code> event for this element.
	 */
	protected void fireTerminateEvent()
	{
		fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
	}
}
