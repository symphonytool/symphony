package eu.compassresearch.ide.cml.interpreter_plugin.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;

public class CmlDebugTarget extends CmlDebugElement implements IDebugTarget {

	private ILaunch launch;
	private IProcess process;
	
	// threads
	private CmlThread cmlThread;
	private IThread[] threads;
	
	// sockets to communicate with VM
	private Socket fRequestSocket;
	private PrintWriter fRequestWriter;
	private BufferedReader fRequestReader;
	private Socket fEventSocket;
	private BufferedReader fEventReader;
	
	// event dispatch job
		private EventDispatchJob fEventDispatch;
		
		/**
		 * Listens to events from the PDA VM and fires corresponding 
		 * debug events.
		 */
		class EventDispatchJob extends Job {
			
			public EventDispatchJob() {
				super("CML Event Dispatch");
				setSystem(true);
			}

			/* (non-Javadoc)
			 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
			 */
			protected IStatus run(IProgressMonitor monitor) {
				String event = "";
				while (!isTerminated() && event != null) {
					try {
						event = fRequestReader.readLine();
						System.out.println(event);
						if (event != null) {
//							cmlThread.setBreakpoints(null);
//							fThread.setStepping(false);
							if (event.equals("started")) {
								started();
							} 
//							else if (event.equals("terminated")) {
//								terminated();
//							} else if (event.startsWith("resumed")) {
//								if (event.endsWith("step")) {
//									fThread.setStepping(true);
//									resumed(DebugEvent.STEP_OVER);
//								} else if (event.endsWith("client")) {
//									resumed(DebugEvent.CLIENT_REQUEST);
//								}
//							} else if (event.startsWith("suspended")) {
//								if (event.endsWith("client")) {
//									suspended(DebugEvent.CLIENT_REQUEST);
//								} else if (event.endsWith("step")) {
//									suspended(DebugEvent.STEP_END);
//								} else if (event.indexOf("breakpoint") >= 0) {
//									breakpointHit(event);
//								}
//							}
						}
					} catch (IOException e) {
						//terminated();
					}
				}
				return Status.OK_STATUS;
			}
			
		}
	
	public CmlDebugTarget(ILaunch launch, IProcess process,int requestPort,int eventPort) throws CoreException
	{
		super(null);
		this.launch = launch;
		this.process = process;
		cmlDebugTarget = this;
		
		ServerSocket requestAcceptor = null;
		
		try {
			requestAcceptor = new ServerSocket(requestPort);
			requestAcceptor.setSoTimeout(5000);
			try{
				fRequestSocket = requestAcceptor.accept();
			}
			catch(SocketTimeoutException e)
			{
				throw e;
			}
			finally{
				requestAcceptor.close();
			}
			
			//fRequestSocket = new Socket("localhost", requestPort);
			//fRequestWriter = new PrintWriter(fRequestSocket.getOutputStream());
			fRequestReader = new BufferedReader(new InputStreamReader(fRequestSocket.getInputStream()));
			//fEventSocket = new Socket("localhost", eventPort);
			//fEventReader = new BufferedReader(new InputStreamReader(fEventSocket.getInputStream()));
			
		} catch (UnknownHostException e) {
			abort("Unable to connect to CML VM", e);
		} catch (IOException e) {
			abort("Unable to connect to CML VM", e);
		}
		
		cmlThread = new CmlThread(this);
		threads = new IThread[] {cmlThread};
		fEventDispatch = new EventDispatchJob();
		fEventDispatch.schedule();
		//Add the target to listen to any breakpoints added
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
		
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canTerminate() {
		return process.canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return process.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		process.terminate();
	}

	@Override
	public boolean canResume() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSuspend() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resume() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspend() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canDisconnect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disconnect() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDisconnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsStorageRetrieval() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProcess getProcess() {
		return process;
	}

	@Override
	public IThread[] getThreads() throws DebugException {
		return threads;
	}

	@Override
	public boolean hasThreads() throws DebugException {
		return threads.length > 0;
	}

	@Override
	public String getName() throws DebugException {
		return null;
	}

	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Notification we have connected to the VM and it has started.
	 * Resume the VM.
	 */
	private void started() {
		fireCreationEvent();
		//installDeferredBreakpoints();
		try {
			resume();
		} catch (DebugException e) {
		}
	}

}
