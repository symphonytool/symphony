package eu.compassresearch.ide.interpreter.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.overture.ast.intf.lex.ILexLocation;

public class CmlStackFrame extends CmlDebugElement implements IStackFrame
{

	private final CmlThread thread;
	private ILexLocation location = null;

	public CmlStackFrame(CmlDebugTarget cmlDebugTarget, CmlThread thread,
			ILexLocation location)
	{
		super(cmlDebugTarget);
		this.location = location;
		this.thread = thread;
	}

	@Override
	public IVariable[] getVariables() throws DebugException
	{
		return new IVariable[] {};
	}

	@Override
	public boolean hasVariables() throws DebugException
	{
		return false;
	}

	@Override
	public int getLineNumber() throws DebugException
	{
		return location.getStartLine();
	}

	@Override
	public int getCharStart() throws DebugException
	{
		return location.getStartPos();
	}

	@Override
	public int getCharEnd() throws DebugException
	{
		return location.getEndPos();
	}

	@Override
	public String getName() throws DebugException
	{
		return "NA";
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException
	{
		return false;
	}

	@Override
	public boolean canStepInto()
	{
		return this.thread.canStepInto();
	}

	@Override
	public boolean canStepOver()
	{
		return this.thread.canStepOver();
	}

	@Override
	public boolean canStepReturn()
	{
		return this.thread.canStepReturn();
	}

	@Override
	public boolean isStepping()
	{
		return this.thread.isStepping();
	}

	@Override
	public void stepInto() throws DebugException
	{
		this.thread.stepInto();
	}

	@Override
	public void stepOver() throws DebugException
	{
		this.thread.stepOver();
	}

	@Override
	public void stepReturn() throws DebugException
	{
		this.thread.stepReturn();
	}

	@Override
	public boolean canResume()
	{
		return this.thread.canResume();
	}

	@Override
	public boolean canSuspend()
	{
		return this.thread.canSuspend();
	}

	@Override
	public boolean isSuspended()
	{
		return this.thread.isSuspended();
	}

	@Override
	public void resume() throws DebugException
	{
		this.thread.resume();
	}

	@Override
	public void suspend() throws DebugException
	{
		this.thread.suspend();
	}

	@Override
	public boolean canTerminate()
	{
		return this.thread.canTerminate();
	}

	@Override
	public boolean isTerminated()
	{
		return this.thread.isTerminated();
	}

	@Override
	public void terminate() throws DebugException
	{
		this.thread.terminate();
	}

	@Override
	public IThread getThread()
	{
		return thread;
	}

	public ILexLocation getLocation()
	{
		return location;
	}

}
