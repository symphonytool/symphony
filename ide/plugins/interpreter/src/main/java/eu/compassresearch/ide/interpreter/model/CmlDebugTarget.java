package eu.compassresearch.ide.interpreter.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.core.interpreter.api.CmlInterpretationStatus;
import eu.compassresearch.core.interpreter.debug.Breakpoint;
import eu.compassresearch.core.interpreter.debug.Choice;
import eu.compassresearch.core.interpreter.debug.CmlDbgStatusMessage;
import eu.compassresearch.core.interpreter.debug.CmlDebugCommand;
import eu.compassresearch.core.interpreter.debug.CmlProcessDTO;
import eu.compassresearch.core.interpreter.debug.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.debug.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.debug.messaging.ResponseMessage;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.interpreter.CmlUtil;
import eu.compassresearch.ide.interpreter.debug.ui.model.CmlLineBreakpoint;
import eu.compassresearch.ide.interpreter.protocol.CmlCommunicationManager;
import eu.compassresearch.ide.interpreter.protocol.CmlThreadManager;
import eu.compassresearch.ide.interpreter.protocol.MessageEventHandler;
import eu.compassresearch.ide.ui.editor.core.CmlEditor;

public class CmlDebugTarget extends CmlDebugElement implements IDebugTarget
{

	private ILaunch launch;
	private IProcess process;
	private ICmlProject project;
	private List<StyleRange> lastSelectedRanges = new LinkedList<StyleRange>();

	CmlCommunicationManager communicationManager;
	CmlThreadManager threadManager;

	public CmlDebugTarget(ILaunch launch, IProcess process,
			ICmlProject project, int communicationPort) throws CoreException,
			IOException
	{
		super(null);
		this.launch = launch;
		this.process = process;
		this.project = project;
		cmlDebugTarget = this;

		threadManager = new CmlThreadManager(this);
		communicationManager = new CmlCommunicationManager(this, threadManager, initializeRequestHandlers(), initializeStatusHandlers(), communicationPort);
		communicationManager.connect();
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
	}

	// public void initializeHandlers()
	// {
	// requestHandlers = initializeRequestHandlers();
	// statusHandlers = initializeStatusHandlers();
	//
	// }

	/**
	 * Initialises all the request message handlers
	 * 
	 * @return
	 */
	private Map<String, MessageEventHandler<RequestMessage>> initializeRequestHandlers()
	{
		Map<String, MessageEventHandler<RequestMessage>> handlers = new HashMap<String, MessageEventHandler<RequestMessage>>();

		// Handler for the Choice request
		handlers.put(CmlRequest.CHOICE.toString(), new MessageEventHandler<RequestMessage>()
		{

			@Override
			public boolean handleMessage(RequestMessage message)
			{
				// Type listType = new TypeToken<List<String>>(){}.getType();

				final List<Choice> events = message.getContent();
				new CmlChoiceMediator(CmlDebugTarget.this, communicationManager).setChoiceOptions(events, message);
				return true;
			}
		});

		handlers.put(CmlRequest.SETUP.toString(), new MessageEventHandler<RequestMessage>()
		{
			@Override
			public boolean handleMessage(RequestMessage message)
			{

				for (IBreakpoint bp : getBreakpoints())
				{
					if (bp instanceof CmlLineBreakpoint)
					{
						try
						{
							Breakpoint cmlBP = new Breakpoint(System.identityHashCode(bp), ((CmlLineBreakpoint) bp).getResourceURI().toString(), ((ILineBreakpoint) bp).getLineNumber());
							System.out.println("Debug target: " + cmlBP);
							communicationManager.sendCommandMessage(CmlDebugCommand.SET_BREAKPOINT, cmlBP);
						} catch (CoreException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				communicationManager.sendMessage(new ResponseMessage(message.getRequestId(), CmlRequest.SETUP, ""));
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
		Map<String, MessageEventHandler<CmlDbgStatusMessage>> handlers = new HashMap<String, MessageEventHandler<CmlDbgStatusMessage>>();

		handlers.put(CmlInterpretationStatus.INITIALIZED.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				for (IBreakpoint b : getBreakpoints())
				{
					try
					{
						if (b.isEnabled())
						{
							System.out.println("Adding breakpoint: " + b);
							// TODO communnicate the setting of the breakpoint to the interpreter
						}
					} catch (CoreException e)
					{
						CmlDebugPlugin.logError("Failed to set breakpoint", e);
					}
				}
				threadManager.started(message.getInterpreterStatus());
				
				Display.getDefault().syncExec(new Runnable()
				{
					@Override
					public void run()
					{
						CmlUtil.clearAllSelections();
					}
				});
				return true;
			}
		});

		handlers.put(CmlInterpretationStatus.RUNNING.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				Display.getDefault().syncExec(new Runnable()
				{
					@Override
					public void run()
					{
						CmlEditor cmlEditor = (CmlEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
						if (cmlEditor != null)
						{
							StyledText styledText = (StyledText) cmlEditor.getAdapter(Control.class);
							CmlUtil.clearSelections(styledText, lastSelectedRanges);
						}

					}
				});
				return true;
			}
		});

		handlers.put(CmlInterpretationStatus.WAITING_FOR_ENVIRONMENT.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				threadManager.updateDebuggerInfo(message.getInterpreterStatus());
				return true;
			}
		});

		handlers.put(CmlInterpretationStatus.SUSPENDED.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{

			@Override
			public boolean handleMessage(final CmlDbgStatusMessage message)
			{
				// threadManager.stopping();
				threadManager.updateDebuggerInfo(message.getInterpreterStatus());

				Display.getDefault().syncExec(new Runnable()
				{
					@Override
					public void run()
					{
						if (message.getInterpreterStatus().hasActiveBreakpoint())
						{
							Breakpoint bp = message.getInterpreterStatus().getActiveBreakpoint();
							for (CmlProcessDTO pi : message.getInterpreterStatus().getAllProcesses())
								if (pi.getLocation().getStartLine() == bp.getLine())
								{
									CmlUtil.setSelectionFromLocation(pi.getLocation(), lastSelectedRanges);
									break;
								}
						}
					}
				});

				try
				{
					suspend();
				} catch (DebugException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		});

		handlers.put(CmlInterpretationStatus.FAILED.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				// threadManager.stopping();
				threadManager.updateDebuggerInfo(message.getInterpreterStatus());
				CmlDebugPlugin.logWarning(message + " : " + message.getInterpreterStatus().getErrors());
				return false;
			}
		});

		handlers.put(CmlInterpretationStatus.FINISHED.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				//threadManager.stopping();
				threadManager.updateDebuggerInfo(message.getInterpreterStatus());
				return false;
			}
		});

		handlers.put(CmlInterpretationStatus.TERMINATED.toString(), new MessageEventHandler<CmlDbgStatusMessage>()
		{
			@Override
			public boolean handleMessage(CmlDbgStatusMessage message)
			{
				// communicationManager.connectionClosed();
				return false;
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
		return threadManager.isSuspended();
	}

	@Override
	public boolean canSuspend()
	{
		return threadManager.isRunning();
	}

	@Override
	public boolean isSuspended()
	{
		return threadManager.isSuspended();
	}

	@Override
	public void resume() throws DebugException
	{
		this.communicationManager.sendCommandMessage(CmlDebugCommand.RESUME);
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
		if (breakpoint instanceof CmlLineBreakpoint)
		{
			CmlLineBreakpoint cb = (CmlLineBreakpoint) breakpoint;
			try
			{
				communicationManager.addBreakpoint(cb.getResourceURI(), ((CmlLineBreakpoint) breakpoint).getLineNumber(), breakpoint.isEnabled());
			} catch (CoreException e)
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
			} catch (CoreException e)
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
			} catch (CoreException e)
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
		return (breakpoint instanceof CmlLineBreakpoint);
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
