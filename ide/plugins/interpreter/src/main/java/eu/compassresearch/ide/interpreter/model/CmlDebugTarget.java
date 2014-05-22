package eu.compassresearch.ide.interpreter.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.ITerminate;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.overture.ide.debug.core.model.DebugEventHelper;

import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.debug.CmlDbgCommandMessage;
import eu.compassresearch.core.interpreter.debug.CmlDbgStatusMessage;
import eu.compassresearch.core.interpreter.debug.CmlDebugCommand;
import eu.compassresearch.core.interpreter.debug.CmlInterpreterStateDTO;
import eu.compassresearch.core.interpreter.debug.TransitionDTO;
import eu.compassresearch.core.interpreter.debug.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.debug.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.debug.messaging.ResponseMessage;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.interpreter.debug.ui.model.CmlLineBreakpoint;
import eu.compassresearch.ide.interpreter.protocol.CmlCommunicationManager;
import eu.compassresearch.ide.interpreter.protocol.CmlThreadManager;
import eu.compassresearch.ide.interpreter.protocol.MessageEventHandler;

public class CmlDebugTarget extends CmlDebugElement implements IDebugTarget
{
	private static final int THREAD_TERMINATION_TIMEOUT = 5000; // 5 seconds
	private ILaunch launch;
	private IProcess process;
	public final ICmlProject project;
	CmlCommunicationManager communicationManager;
	CmlThreadManager threadManager;
	CmlInterpreterStateDTO lastState = null;
	private boolean suspendedForSelection;
	private boolean expectConsoleRead;
	private final boolean autoTerminate;

	public CmlDebugTarget(ILaunch launch, IProcess process,
			ICmlProject project, int communicationPort, boolean autoTerminate) throws CoreException,
			IOException
	{
		this.launch = launch;
		this.process = process;
		this.project = project;
		this.autoTerminate = autoTerminate;

		threadManager = new CmlThreadManager(this);
		communicationManager = new CmlCommunicationManager(this, threadManager, initializeRequestHandlers(), initializeStatusHandlers(), communicationPort);
		communicationManager.connect();

		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
	}

	/**
	 * Initialises all the request message handlers
	 * 
	 * @return
	 */
	private Map<String, MessageEventHandler<RequestMessage>> initializeRequestHandlers()
	{
		Map<String, MessageEventHandler<RequestMessage>> handlers = new HashMap<String, MessageEventHandler<RequestMessage>>();

		handlers.put(CmlRequest.SETUP.toString(), new MessageEventHandler<RequestMessage>()
		{
			@Override
			public boolean handleMessage(RequestMessage message)
			{
				if (getBreakpointManager().isEnabled())
				{
					for (IBreakpoint bp : getBreakpoints())
					{
						if (bp instanceof CmlLineBreakpoint)
						{
							CmlLineBreakpoint cmlBp = (CmlLineBreakpoint) bp;
							try
							{
								communicationManager.addBreakpoint(cmlBp.getResourceURI(), cmlBp.getLineNumber(), cmlBp.isEnabled());
							} catch (Exception e)
							{
								CmlDebugPlugin.logError("Failed to set breakpoint", e);
							}
						} else
						{
							System.err.println("unknown type of breakpoint found: "
									+ bp);
						}
					}
				}

				try
				{
					communicationManager.sendMessage(new ResponseMessage(message.getRequestId(), CmlRequest.SETUP, ""));
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		});

		return handlers;
	}

	/**
	 * Initializes all the status message handlers
	 * 
	 * @return
	 */
	private Map<String, MessageEventHandler<CmlDbgStatusMessage>> initializeStatusHandlers()
	{
		final Map<String, MessageEventHandler<CmlDbgStatusMessage>> handlers = new HashMap<String, MessageEventHandler<CmlDbgStatusMessage>>();
		final Boolean CONTINUE_PROCESSING = true;
		final Boolean STOP_PROCESSING_AND_TERMINATE = true;

		handlers.put(CmlInterpreterState.INITIALIZED.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				lastState = message.getInterpreterStatus();
				threadManager.started(message.getInterpreterStatus());

				// Display.getDefault().syncExec(new Runnable()
				// {
				// @Override
				// public void run()
				// {
				// CmlUtil.clearAllSelections();
				// }
				// });
				return CONTINUE_PROCESSING;
			}
		});

		handlers.put(CmlInterpreterState.RUNNING.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				lastState = message.getInterpreterStatus();
				suspendedForSelection = false;
				expectConsoleRead = false;
				return CONTINUE_PROCESSING;
			}
		});

		handlers.put(CmlInterpreterState.WAITING_FOR_ENVIRONMENT.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(final CmlDbgStatusMessage message)
			{
				lastState = message.getInterpreterStatus();
				Job setupThreads = new Job("setup cml threads")
				{

					@Override
					protected IStatus run(IProgressMonitor monitor)
					{
						threadManager.updateThreads(message.getInterpreterStatus(), communicationManager);
						try
						{
							suspendForSelection();
						} catch (DebugException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Status.OK_STATUS;
					}

				};
				setupThreads.setSystem(true);
				setupThreads.schedule();

				return CONTINUE_PROCESSING;
			}
		});

		handlers.put(CmlInterpreterState.SUSPENDED.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{

			@Override
			public boolean handleMessage(final CmlDbgStatusMessage message)
			{
				lastState = message.getInterpreterStatus();
				Job setupThreads = new Job("setup cml threads")
				{

					@Override
					protected IStatus run(IProgressMonitor monitor)
					{
						threadManager.updateThreads(message.getInterpreterStatus(), communicationManager);
						DebugEventHelper.fireChangeEvent(CmlDebugTarget.this);
						DebugEventHelper.fireSuspendEvent(CmlDebugTarget.this, DebugEvent.BREAKPOINT);
						return Status.OK_STATUS;
					}
				};
				setupThreads.setSystem(true);
				setupThreads.schedule();

				Display.getDefault().syncExec(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							suspend();
						} catch (DebugException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

				return CONTINUE_PROCESSING;
			}
		});

		handlers.put(CmlInterpreterState.FAILED.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				lastState = message.getInterpreterStatus();
				return STOP_PROCESSING_AND_TERMINATE;
			}
		});

		handlers.put(CmlInterpreterState.FINISHED.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				lastState = message.getInterpreterStatus();
				return STOP_PROCESSING_AND_TERMINATE;
			}
		});

		handlers.put(CmlInterpreterState.DEADLOCKED.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				lastState = message.getInterpreterStatus();
				handlers.get(CmlInterpreterState.SUSPENDED.toString()).handleMessage(message);
				return CONTINUE_PROCESSING;
			}
		});

		handlers.put(CmlInterpreterState.TERMINATED_BY_USER.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				lastState = message.getInterpreterStatus();
				return STOP_PROCESSING_AND_TERMINATE;
			}
		});

		return handlers;
	}

	@Override
	public ILaunch getLaunch()
	{
		return launch;
	}

	@Override
	public boolean canTerminate()
	{
		return process != null && process.canTerminate();
	}

	@Override
	public boolean isTerminated()
	{
		return process != null && process.isTerminated();
	}

	public void terminate() throws DebugException
	{
		terminate(true);
	}

	protected void terminate(boolean waitTermination) throws DebugException
	{
		// fireTargetTerminating();

		try
		{
			communicationManager.terminate();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (waitTermination)
		{
			final IProcess p = getProcess();
			final int CHUNK = 500;
			if (!(waitTerminated(threadManager, CHUNK, THREAD_TERMINATION_TIMEOUT) && (p == null || waitTerminated(p, CHUNK, THREAD_TERMINATION_TIMEOUT))))
			{
				// Debugging process is not answering, so terminating it
				if (p != null && p.canTerminate())
				{
					// p.terminate();
				}
			}
		}

		DebugEventHelper.fireTerminateEvent(this);
	}

	protected static boolean waitTerminated(ITerminate terminate, int chunk,
			long timeout)
	{
		final long start = System.currentTimeMillis();
		while (!terminate.isTerminated())
		{
			if (System.currentTimeMillis() - start > timeout)
			{
				return false;
			}
			try
			{
				Thread.sleep(chunk);
			} catch (InterruptedException e)
			{
				// interrupted
			}
		}
		return true;
	}

	@Override
	public boolean canResume()
	{
		return isSuspended() && !suspendedForSelection;
	}

	@Override
	public boolean canSuspend()
	{
		return lastState != null
				&& lastState.getInterpreterState() == CmlInterpreterState.RUNNING;
	}

	@Override
	public boolean isSuspended()
	{
		return lastState != null
				&& (lastState.getInterpreterState() == CmlInterpreterState.SUSPENDED
						|| lastState.getInterpreterState() == CmlInterpreterState.WAITING_FOR_ENVIRONMENT || lastState.getInterpreterState() == CmlInterpreterState.DEADLOCKED);
	}

	@Override
	public void resume() throws DebugException
	{
		try
		{
			this.communicationManager.sendCommandMessage(CmlDebugCommand.RESUME);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fireResumeEvent(0);
	}

	public void select(TransitionDTO choice, boolean expectConsoleRead)
	{
		try
		{
			this.communicationManager.sendMessage(new CmlDbgCommandMessage(CmlDebugCommand.SET_CHOICE, choice));
			this.suspendedForSelection = expectConsoleRead;
			this.expectConsoleRead = expectConsoleRead;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fireResumeEvent(0);
	}

	private void suspendForSelection() throws DebugException
	{
		this.suspendedForSelection = true;
		suspend();
	}

	public boolean isSuspendedForSelection()
	{
		return this.suspendedForSelection;
	}

	@Override
	public void suspend() throws DebugException
	{
		fireSuspendEvent(0);
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint)
	{
		if (breakpoint instanceof CmlLineBreakpoint)
		{
			CmlLineBreakpoint cb = (CmlLineBreakpoint) breakpoint;
			try
			{
				communicationManager.addBreakpoint(cb.getResourceURI(), ((CmlLineBreakpoint) breakpoint).getLineNumber(), breakpoint.isEnabled());
			} catch (Exception e)
			{
				CmlDebugPlugin.logError("Faild to add breakpoint", e);
			}
		}
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta)
	{
		if (breakpoint instanceof CmlLineBreakpoint)
		{
			CmlLineBreakpoint cb = (CmlLineBreakpoint) breakpoint;
			try
			{
				communicationManager.removeBreakpoint(cb.getResourceURI(), ((CmlLineBreakpoint) breakpoint).getLineNumber());
			} catch (Exception e)
			{
				CmlDebugPlugin.logError("Faild to remove breakpoint", e);
			}
		}
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta)
	{
		if (breakpoint instanceof CmlLineBreakpoint)
		{
			CmlLineBreakpoint cb = (CmlLineBreakpoint) breakpoint;
			try
			{
				communicationManager.updateBreakpoint(cb.getResourceURI(), ((CmlLineBreakpoint) breakpoint).getLineNumber(), breakpoint.isEnabled());
			} catch (Exception e)
			{
				CmlDebugPlugin.logError("Faild to update breakpoint", e);
			}
		}
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
		try
		{
			communicationManager.disconnect();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isDisconnected()
	{
		return !communicationManager.isConnected();
	}

	@Override
	public boolean supportsStorageRetrieval()
	{
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException
	{
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
		return threadManager.getThreads().toArray(new IThread[threadManager.getThreads().size()]);
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
		return breakpoint instanceof CmlLineBreakpoint;
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
		if (p != null && p.canTerminate() && autoTerminate)
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

	@Override
	public IDebugTarget getDebugTarget()
	{
		return this;
	}

	public CmlInterpreterStateDTO getLastState()
	{
		return lastState;
	}

	public boolean isSuspendedForConsoleRead()
	{
		return expectConsoleRead;
	}

}
