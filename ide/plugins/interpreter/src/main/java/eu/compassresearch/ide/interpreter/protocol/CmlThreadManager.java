package eu.compassresearch.ide.interpreter.protocol;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.core.interpreter.api.CmlProcessInfo;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.interpreter.model.CmlDebugTarget;
import eu.compassresearch.ide.interpreter.model.CmlThread;
import eu.compassresearch.ide.interpreter.views.CmlEventHistoryView;

public class CmlThreadManager
{
	// threads
	private List<IThread> threads = new LinkedList<IThread>();
	private CmlDebugTarget target;

	public CmlThreadManager(CmlDebugTarget target)
	{
		this.target = target;
	}

	public void updateDebuggerInfo(final InterpreterStatus status)
	{
		// cmlThread = new CmlThread(this,status.getToplevelProcessInfo());
		threads.clear();
		for (CmlProcessInfo t : status.getAllProcessInfos())
		{
			threads.add(new CmlThread(target, t));
		}
		// fireSuspendEvent(0);

		final List<String> trace = status.getToplevelProcessInfo().getTrace();

		Display.getDefault().asyncExec(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					CmlEventHistoryView view = (CmlEventHistoryView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ICmlDebugConstants.ID_CML_HISTORY_VIEW.toString());
					view.getListViewer().setInput(trace);
				} catch (PartInitException e)
				{
					e.printStackTrace();
				}

				if (status.hasErrors())
					MessageDialog.openError(null, "Simulation Error", status.getErrors().get(0).getErrorMessage());
			}
		});

		target.fireResumeEvent(0);
	}

	/**
	 * Notification we have connected to the VM and it has started. Resume the VM.
	 */
	public void started(InterpreterStatus status)
	{

		updateDebuggerInfo(status);
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

	public List<IThread> getThreads()
	{
		return this.threads;
	}
}