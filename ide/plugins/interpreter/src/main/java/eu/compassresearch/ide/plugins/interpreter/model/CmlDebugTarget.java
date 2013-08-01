package eu.compassresearch.ide.plugins.interpreter.model;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.osgi.util.NLS;

import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.plugins.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.plugins.interpreter.protocol.CmlCommunicationManager;
import eu.compassresearch.ide.plugins.interpreter.protocol.CmlThreadManager;

public class CmlDebugTarget extends CmlDebugElement implements IDebugTarget
{

	private ILaunch launch;
	private IProcess process;
	private ICmlProject project;

	CmlCommunicationManager communicationManager;
	CmlThreadManager threadManager;

	public CmlDebugTarget(ILaunch launch, IProcess process,
			ICmlProject project, int communicationPort) throws CoreException
	{
		super(null);
		this.launch = launch;
		this.process = process;
		this.project = project;
		cmlDebugTarget = this;

		threadManager = new CmlThreadManager(this);
		communicationManager = new CmlCommunicationManager(this, threadManager, communicationPort);
		try
		{
			communicationManager.connect();
			DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
		} catch (IOException e)
		{
			CmlDebugPlugin.logError("Failed to connect to debugger", e);
		}

	}

	@Override
	public ILaunch getLaunch()
	{
		return launch;
	}

	@Override
	public boolean canTerminate()
	{
		return process.canTerminate();
	}

	@Override
	public boolean isTerminated()
	{
		return process.isTerminated();
	}

	@Override
	public void terminate() throws DebugException
	{

		communicationManager.terminate();

	}

	@Override
	public boolean canResume()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSuspend()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSuspended()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resume() throws DebugException
	{
		fireResumeEvent(0);
	}

	@Override
	public void suspend() throws DebugException
	{
		fireSuspendEvent(0);
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta)
	{
		// TODO Auto-generated method stub

	}

	public List<IBreakpoint> getBreakpoints()
	{
		IBreakpoint[] breakpoints = getBreakpointManager().getBreakpoints(getModelIdentifier());

		List<IBreakpoint> targetBreakpoints = new Vector<IBreakpoint>();

		for (int i = 0; i < breakpoints.length; i++)
		{
			try
			{
				final IBreakpoint breakpoint = breakpoints[i];
				if (breakpoint.getMarker().getResource().getProject().getName().equals(project.getName()))
				{
					targetBreakpoints.add(breakpoint);
				}

			} catch (Exception e)
			{
				CmlDebugPlugin.logWarning(NLS.bind("ErrorSetupDeferredBreakpoints", e.getMessage()), e);
				if (CmlDebugPlugin.DEBUG)
				{
					e.printStackTrace();
				}
			}
		}
		return targetBreakpoints;
	}

	// Utility methods
	protected static IBreakpointManager getBreakpointManager()
	{
		return DebugPlugin.getDefault().getBreakpointManager();
	}

	@Override
	public boolean canDisconnect()
	{
		return communicationManager.isConnected();
	}

	@Override
	public void disconnect() throws DebugException
	{
		communicationManager.disconnect();
	}

	@Override
	public boolean isDisconnected()
	{
		return !communicationManager.isConnected();
	}

	@Override
	public boolean supportsStorageRetrieval()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProcess getProcess()
	{
		return process;
	}

	@Override
	public IThread[] getThreads() throws DebugException
	{
		return threadManager.getThreads().toArray(new IThread[] {});
	}

	@Override
	public boolean hasThreads() throws DebugException
	{
		return !threadManager.getThreads().isEmpty();
	}

	@Override
	public String getName() throws DebugException
	{
		return "CML Debug Target";
	}

	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Called when this debug target terminates.
	 */
	public void terminated()
	{
		// terminated = true;
		// suspended = false;
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
		fireTerminateEvent();

		// take the process down
		final IProcess p = getProcess();
		// Debugging process is not answering, so terminating it
		if (p != null && p.canTerminate())
		{
			try
			{
				p.terminate();
			} catch (DebugException e)
			{
				CmlDebugPlugin.logError("Failed to take down the interpreter process", e);
			}
		}
	}

}
