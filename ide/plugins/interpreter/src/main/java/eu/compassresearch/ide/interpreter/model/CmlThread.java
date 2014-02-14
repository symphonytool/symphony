package eu.compassresearch.ide.interpreter.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.overture.ide.debug.core.model.DebugEventHelper;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorState;
import eu.compassresearch.core.interpreter.debug.CmlProcessDTO;
import eu.compassresearch.ide.interpreter.protocol.CmlCommunicationManager;
import eu.compassresearch.ide.interpreter.protocol.CmlThreadManager;

public class CmlThread extends CmlDebugElement implements IThread
{

	private CmlProcessDTO info;
	// private CmlStackFrame cmlStackFrame = null;
	private final CmlDebugTarget debugTarget;

	private final CmlStack stack;
	private final CmlThreadManager manager;
	private final CmlCommunicationManager communication;
	public final int id;

	public CmlThread(CmlDebugTarget debugTarget, CmlThreadManager manager,
			CmlCommunicationManager communication, CmlProcessDTO info)
	{
		this.debugTarget = debugTarget;
		this.stack = new CmlStack(this);
		this.manager = manager;
		this.communication = communication;
		this.info = info;
		this.id = info.getId();
	}

	@Override
	public boolean canResume()
	{
		return isSuspended();
	}

	@Override
	public boolean canSuspend()
	{
		return !isSuspended();
	}

	@Override
	public boolean isSuspended()
	{
		return getDebugTarget().isSuspended();
	}

	@Override
	public void resume() throws DebugException
	{
		getDebugTarget().resume();

	}

	@Override
	public void suspend() throws DebugException
	{
		getDebugTarget().suspend();
	}

	@Override
	public boolean canStepInto()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStepOver()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStepReturn()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStepping()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stepInto() throws DebugException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void stepOver() throws DebugException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void stepReturn() throws DebugException
	{
		// TODO Auto-generated method stub

	}

	/**
	 * It should not be posible to terminate the thread from the debugger
	 */
	@Override
	public boolean canTerminate()
	{
		return false;
	}

	@Override
	public boolean isTerminated()
	{
		return this.info.getState() == CmlBehaviorState.FINISHED
				|| getDebugTarget().isTerminated();
	}

	@Override
	public void terminate() throws DebugException
	{
	}

	@Override
	public IStackFrame[] getStackFrames() throws DebugException
	{
		if (!isSuspended())
		{
			try
			{
				Thread.sleep(100);
			} catch (Exception e)
			{
			}
			if (!isSuspended())
			{
				return CmlStack.NO_STACK_FRAMES;
			}
		}

		return stack.getFrames();
	}

	@Override
	public boolean hasStackFrames() throws DebugException
	{
		return isSuspended() && !isTerminated();
	}

	public IStackFrame getTopStackFrame()
	{
		return stack.getTopFrame();
	}

	@Override
	public int getPriority() throws DebugException
	{
		return 0;
	}

	@Override
	public String getName() throws DebugException
	{
		return info.getName()+" (at line "+info.getLocation().getStartLine()+") #"+id;
	}

	@Override
	public IBreakpoint[] getBreakpoints()
	{
		return DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(getModelIdentifier());
	}

	@Override
	public CmlDebugTarget getDebugTarget()
	{
		return this.debugTarget;
	}

	public CmlThreadManager getThreadManager()
	{
		return this.manager;
	}

	public void initialize()
	{
		stack.update(true);

	}

	public CmlCommunicationManager getCommunicationManager()
	{
		return this.communication;
	}

	public void updateInfo(CmlProcessDTO t)
	{
		this.info = t;
		stack.update(false);
		DebugEventHelper.fireChangeEvent(this);
	}

	public CmlProcessDTO getInfo()
	{
		return this.info;
	}
}
