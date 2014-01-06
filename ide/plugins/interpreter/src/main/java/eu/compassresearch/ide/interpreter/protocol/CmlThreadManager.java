package eu.compassresearch.ide.interpreter.protocol;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.ITerminate;
import org.eclipse.debug.core.model.IThread;

import eu.compassresearch.core.interpreter.debug.CmlInterpreterStateDTO;
import eu.compassresearch.core.interpreter.debug.CmlProcessDTO;
import eu.compassresearch.ide.interpreter.model.CmlDebugTarget;
import eu.compassresearch.ide.interpreter.model.CmlThread;

public class CmlThreadManager implements ITerminate
{
	// threads
	private List<CmlThread> threads = new LinkedList<CmlThread>();
	private CmlDebugTarget target;

	public CmlThreadManager(CmlDebugTarget target)
	{
		this.target = target;
	}

	public void updateThreads(final CmlInterpreterStateDTO status,
			CmlCommunicationManager communication)
	{
		List<CmlThread> deadThreads = new LinkedList<CmlThread>();
		List<CmlProcessDTO> dtos = new Vector<CmlProcessDTO>(status.getAllLeafProcesses());
		for (CmlThread debugThread : threads)
		{
			boolean found = false;
			for (CmlProcessDTO t : dtos)
			{
				if(debugThread.id== t.getId())
				{
					found = true;
					debugThread.updateInfo(t);
				}
				
			}
			
			if(!found)
			{
				deadThreads.add(debugThread);
			}else
			{
				dtos.remove(debugThread.getInfo());
			}
		}
		
		threads.removeAll(deadThreads);
		
		for (CmlProcessDTO t : dtos)
		{
			CmlThread thread = new CmlThread(target, this, communication, t);
			thread.initialize();
			threads.add(thread);
		}
	}

	/**
	 * Notification we have connected to the VM and it has started. Resume the VM.
	 */
	public void started(CmlInterpreterStateDTO status)
	{
		// updateDebuggerInfo(status);
		target.fireCreationEvent();

		// installDeferredBreakpoints();
		try
		{
			target.resume();
		} catch (DebugException e)
		{
		}
	}

	/**
	 * Called when interpreter is about to stop
	 */
	public void stopping()
	{

	}

	public void terminated()
	{
		target.terminated();
	}

	public List<? extends IThread> getThreads()
	{
		return this.threads;
	}

	@Override
	public boolean canTerminate()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isTerminated()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void terminate() throws DebugException
	{
		target.terminate();

	}
}
