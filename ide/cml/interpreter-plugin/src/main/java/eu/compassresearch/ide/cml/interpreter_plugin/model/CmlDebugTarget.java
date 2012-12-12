package eu.compassresearch.ide.cml.interpreter_plugin.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import eu.compassresearch.core.interpreter.api.CmlProcessInfo;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.debug.messaging.CmlDbgCommandMessage;
import eu.compassresearch.core.interpreter.debug.messaging.CmlDbgStatusMessage;
import eu.compassresearch.core.interpreter.debug.messaging.CmlDbgpStatus;
import eu.compassresearch.core.interpreter.debug.messaging.CmlDebugCommand;
import eu.compassresearch.core.interpreter.debug.messaging.CmlMessage;
import eu.compassresearch.core.interpreter.debug.messaging.CmlMessageCommunicator;
import eu.compassresearch.core.interpreter.debug.messaging.CmlMessageContainer;
import eu.compassresearch.core.interpreter.debug.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.debug.messaging.CmlRequestMessage;

public class CmlDebugTarget extends CmlDebugElement implements IDebugTarget {

	private ILaunch launch;
	private IProcess process;

	// threads
	private CmlThread cmlThread;
	private List<IThread> threads;

	// socket to communicate with VM
	private Socket fRequestSocket;
	private OutputStream requestOutputStream;
	private BufferedReader fRequestReader;
	private Socket fEventSocket;

	// event dispatch job
	private EventDispatchJob fEventDispatch;

	interface MessageEventHandler<T extends CmlMessage>
	{
		public boolean handleMessage(T message);
	};
	
	/**
	 * Listens to events from the CML VM and fires corresponding 
	 * debug events.
	 */
	class EventDispatchJob extends Job {

		private Map<String,MessageEventHandler<CmlRequestMessage>> requestHandlers;
		private Map<String,MessageEventHandler<CmlDbgStatusMessage>> statusHandlers;
		
		public EventDispatchJob() {
			super("CML Event Dispatch");
			setSystem(true);
			initializeHandlers();
		}
		
		/**
		 * Initialisation methods
		 */
		private void initializeHandlers()
		{
			requestHandlers = initializeRequestHandlers();
			statusHandlers = initializeStatusHandlers();
			
		}
		
		/**
		 * Initialises all the request message handlers
		 * @return
		 */
		private Map<String, CmlDebugTarget.MessageEventHandler<CmlRequestMessage>> initializeRequestHandlers()
		{
			Map<String, CmlDebugTarget.MessageEventHandler<CmlRequestMessage>> handlers = 
					new HashMap<String, CmlDebugTarget.MessageEventHandler<CmlRequestMessage>>();
			
			//Handler for the Choice request
			handlers.put(CmlRequest.CHOICE.toString(), new MessageEventHandler<CmlRequestMessage>() {

				@Override
				public boolean handleMessage(CmlRequestMessage message) {
					Type listType = new TypeToken<List<String>>(){}.getType();
					final List<String> events = message.<List<String>>getContent(listType);
					new CmlChoiceMediator(cmlDebugTarget).setChoiceOptions(events,message);
					return true;
				}
			});
			
			return handlers;
		}
		
		
		/**
		 * Initialises all the status message handlers
		 * @return
		 */
		private Map<String, CmlDebugTarget.MessageEventHandler<CmlDbgStatusMessage>> initializeStatusHandlers()
		{
			Map<String, CmlDebugTarget.MessageEventHandler<CmlDbgStatusMessage>> handlers = 
					new HashMap<String, CmlDebugTarget.MessageEventHandler<CmlDbgStatusMessage>>();
			
			handlers.put(CmlDbgpStatus.STARTING.toString(), new MessageEventHandler<CmlDbgStatusMessage>() {
				@Override
				public boolean handleMessage(CmlDbgStatusMessage message) {
					return true;
				}
			} );
			
			handlers.put(CmlDbgpStatus.RUNNING.toString(), new MessageEventHandler<CmlDbgStatusMessage>() {
				@Override
				public boolean handleMessage(CmlDbgStatusMessage message) {
					started(message.getInterpreterStatus());
					return true;
				}
			} );
			

			handlers.put(CmlDbgpStatus.STOPPING.toString(), new MessageEventHandler<CmlDbgStatusMessage>() {
				@Override
				public boolean handleMessage(CmlDbgStatusMessage message) {
					stopping();
					return true;
				}
			} );
			
			handlers.put(CmlDbgpStatus.STOPPED.toString(), new MessageEventHandler<CmlDbgStatusMessage>() {
				@Override
				public boolean handleMessage(CmlDbgStatusMessage message) {
					return false;
				}
			} );
			
			handlers.put(CmlDbgpStatus.CONNECTION_CLOSED.toString(), new MessageEventHandler<CmlDbgStatusMessage>() {
				@Override
				public boolean handleMessage(CmlDbgStatusMessage message) {
					connectionClosed();
					return false;
				}
			} );
			
			return handlers;
		}

		private CmlMessageContainer receiveMessage() throws IOException
		{
			return CmlMessageCommunicator.receiveMessage(fRequestReader);
		}

		/**
		 * Dispatches the message to the corresponding message handler
		 * @param handlers The corresponding message handler map for this message type
		 * @param message The message to be processed
		 * @return true if the event loop should continue otherwise false
		 */
		private <H extends CmlMessage> boolean dispatchMessageHandler(Map<String,CmlDebugTarget.MessageEventHandler<H>> handlers, H message)
		{
			boolean result = false;

			if(handlers.containsKey(message.getKey()))
				result = handlers.get(message.getKey()).handleMessage(message);
			
			return result;
		}

		private boolean processMessage(CmlMessageContainer messageContainer)
		{
			boolean result = false;
			
			switch(messageContainer.getType())
			{
			case STATUS:
				return dispatchMessageHandler(statusHandlers,messageContainer.getMessage(CmlDbgStatusMessage.class));
			case REQUEST:
				return dispatchMessageHandler(requestHandlers,messageContainer.getMessage(CmlRequestMessage.class));
			default:
				break;
			}

			return result;
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
				//terminated();
			}
			finally
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
//			cmlThread = new CmlThread(this);
			threads = new LinkedList<IThread>();
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
			requestOutputStream = fRequestSocket.getOutputStream();
			fRequestReader = new BufferedReader(new InputStreamReader(fRequestSocket.getInputStream()));
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
		fireResumeEvent(0);
	}

	@Override
	public void suspend() throws DebugException {
		fireSuspendEvent(0);
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
		return !fRequestSocket.isClosed();
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
		return threads.toArray(new IThread[threads.size()]);
	}

	@Override
	public boolean hasThreads() throws DebugException {
		return threads.size() > 0;
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

	public void sendMessage(CmlMessage message)
	{
		CmlMessageCommunicator.sendMessage(requestOutputStream, message);
	}


	/**
	 * Notification we have connected to the VM and it has started.
	 * Resume the VM.
	 */
	private void started(InterpreterStatus status) {
		
		//cmlThread = new CmlThread(this,status.getToplevelProcessInfo());
		threads.clear();
		for(CmlProcessInfo t : status.getAllProcessInfos())
		{
			threads.add(new CmlThread(this,t));
		}
		threads.add(cmlThread);
		//fireSuspendEvent(0);
		fireResumeEvent(0);		
		
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
