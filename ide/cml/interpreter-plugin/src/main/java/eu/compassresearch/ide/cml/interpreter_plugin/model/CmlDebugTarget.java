package eu.compassresearch.ide.cml.interpreter_plugin.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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

import com.google.gson.reflect.TypeToken;

import eu.compassresearch.ide.cml.interpreter_plugin.CmlDbgCommandMessage;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlDbgStatusMessage;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlDebugCommand;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlDialogMessage;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlMessage;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlMessageCommunicator;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlMessageContainer;
import eu.compassresearch.ide.cml.interpreter_plugin.CmlRequest;

public class CmlDebugTarget extends CmlDebugElement implements IDebugTarget {

	private ILaunch launch;
	private IProcess process;
	
	// threads
	private CmlThread cmlThread;
	private IThread[] threads;
	
	// sockets to communicate with VM
	private Socket fRequestSocket;
	private OutputStream requestOutputStream;
	private BufferedReader fRequestReader;
	private Socket fEventSocket;
	private BufferedReader fEventReader;
	
		
	// event dispatch job
		private EventDispatchJob fEventDispatch;
		
		/**
		 * Listens to events from the CML VM and fires corresponding 
		 * debug events.
		 */
		class EventDispatchJob extends Job {
			
			public EventDispatchJob() {
				super("CML Event Dispatch");
				setSystem(true);
			}
			
			private CmlMessageContainer receiveMessage() throws IOException
			{
				return CmlMessageCommunicator.receiveMessage(fRequestReader);
			}

			
			private boolean processStatusMessage(CmlDbgStatusMessage message)
			{
				boolean result = true;
				switch(message.getStatus())
				{
				case CONNECTION_CLOSED:
					result = false;
					break;
				case STARTING:
					started();
					break;
				case RUNNING:
					break;
				case STOPPING:
					stopping();
					break;
				case STOPPED:
					terminated();
					result = false;
					break;
					
				default:
					result = false;
				}
				
				return result;
			}
			
			private boolean processRequest(CmlDialogMessage message)
			{
				switch(message.getRequest())
				{
				case CHOICE:
					Type listType = new TypeToken<List<String>>(){}.getType();
					List<String> events = message.<List<String>>getValue(listType);
					
					sendMessage(new CmlDialogMessage(message.getRequestId(),CmlRequest.CHOICE,events.get(0)));
					break;
				}
				
				
				
				return true;
			}
			
			private boolean processMessage(CmlMessageContainer messageContainer)
			{
				switch(messageContainer.getType())
				{
				case STATUS:
					return processStatusMessage(messageContainer.<CmlDbgStatusMessage>getMessage(CmlDbgStatusMessage.class));
				case REQUEST:
					return processRequest(messageContainer.<CmlDialogMessage>getMessage(CmlDialogMessage.class));
				default:
					break;
				}
				
				return false;
			}
			
			/* (non-Javadoc)
			 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
			 */
			protected IStatus run(IProgressMonitor monitor) {
				//CmlDebugStatusMessage event = new CmlDebugStatusMessage(CmlDbgpStatus.WAITING_FOR_CONNECTION);
				CmlMessageContainer message = null;
				
				try
				{
					do{
						message = receiveMessage(); 
						System.out.println(message);
					}
					while (!isTerminated() && processMessage(message));
				}
				catch(IOException e)
				{
					terminated();
				}
		
				return Status.OK_STATUS;
			}
			
		}
	
	public CmlDebugTarget(ILaunch launch, IProcess process,int communicationPort) throws CoreException
	{
		super(null);
		this.launch = launch;
		this.process = process;
		cmlDebugTarget = this;
		
		initialize(communicationPort);
		
	}
	
	private void initialize(int communicationPort) throws CoreException
	{
		try {
			
			waitForConnect(communicationPort); 
			cmlThread = new CmlThread(this);
			threads = new IThread[] {cmlThread};
			fEventDispatch = new EventDispatchJob();
			fEventDispatch.schedule();
			//Add the target to listen to any breakpoints added
			DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);

		} catch (IOException e) {
			abort("Unable to connect to CML VM", e);
		}
	}
	
	private void waitForConnect(int requestPort) throws IOException
	{
		ServerSocket requestAcceptor = null;
		try
		{
			requestAcceptor = new ServerSocket(requestPort);
			requestAcceptor.setSoTimeout(5000);
			fRequestSocket = requestAcceptor.accept();
			//fRequestSocket = new Socket("localhost", requestPort);
			requestOutputStream = fRequestSocket.getOutputStream();
			fRequestReader = new BufferedReader(new InputStreamReader(fRequestSocket.getInputStream()));
			//fEventSocket = new Socket("localhost", eventPort);
			//fEventReader = new BufferedReader(new InputStreamReader(fEventSocket.getInputStream()));
		}
		finally{
			if(requestAcceptor != null)
				requestAcceptor.close();
		}
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
		
		synchronized (fRequestSocket) {
			sendCommandMessage(CmlDebugCommand.STOP);
		}	
		
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
		return true;
	}

	@Override
	public void disconnect() throws DebugException {
		// TODO Auto-generated method stub

		sendCommandMessage(CmlDebugCommand.DISCONNECT);
	}

	@Override
	public boolean isDisconnected() {
		return fRequestSocket.isClosed();
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
		return "CML Debug Target";
	}

	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Private methods
	 */
	/**
	 * Sends a command to the running interpreter
	 * @param cmd
	 */
	private void sendCommandMessage(CmlDebugCommand cmd)
	{
		CmlDbgCommandMessage message = new CmlDbgCommandMessage(cmd);
		CmlMessageCommunicator.sendMessage(requestOutputStream, message);
	}
	
	private void sendMessage(CmlMessage message)
	{
		CmlMessageCommunicator.sendMessage(requestOutputStream, message);
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
	
	/**
	 * Called when interpreter is about to stop
	 */
	private void stopping() {
	
		
	}
	
	private void connectionClosed()
	{
		try
		{
			this.fEventSocket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			
		}
	}
	
	/**
	 * Called when this debug target terminates.
	 */
	private void terminated() {
		//terminated = true;
		//suspended = false;
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
		fireTerminateEvent();
	}

}
