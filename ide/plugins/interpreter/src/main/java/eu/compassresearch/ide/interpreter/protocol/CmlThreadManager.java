package eu.compassresearch.ide.interpreter.protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.compassresearch.core.interpreter.api.CmlInterpretationStatus;
import eu.compassresearch.core.interpreter.debug.CmlInterpreterStateDTO;
import eu.compassresearch.core.interpreter.debug.CmlProcessDTO;
import eu.compassresearch.ide.interpreter.CmlDebugPlugin;
import eu.compassresearch.ide.interpreter.CmlUtil;
import eu.compassresearch.ide.interpreter.ICmlDebugConstants;
import eu.compassresearch.ide.interpreter.model.CmlDebugTarget;
import eu.compassresearch.ide.interpreter.model.CmlThread;
import eu.compassresearch.ide.interpreter.views.CmlEventHistoryView;

public class CmlThreadManager
{
	// threads
	private List<IThread> threads = new LinkedList<IThread>();
	private CmlDebugTarget target;

	CmlInterpreterStateDTO status = null;

	public CmlThreadManager(CmlDebugTarget target)
	{
		this.target = target;
	}

	public void updateDebuggerInfo(final CmlInterpreterStateDTO status)
	{
		this.status = status;
		// cmlThread = new CmlThread(this,status.getToplevelProcessInfo());
		threads.clear();
		for (CmlProcessDTO t : status.getAllProcesses())
		{
			threads.add(new CmlThread(target, t));
		}
		// fireSuspendEvent(0);

		final List<String> trace = status.getToplevelProcess().getTrace();

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
					CmlDebugPlugin.logError("Failed to update the event history view", e);
				}

				if (status.hasErrors()){
					Map<StyledText,List<StyleRange>> map = new HashMap<StyledText, List<StyleRange>>();
					if(status.getErrors().get(0).getLocation() != null)
						CmlUtil.setSelectionFromLocation(status.getErrors().get(0).getLocation(), map);
					MessageDialog.openError(null, "Simulation Error", status.getErrors().get(0).getErrorMessage());
					CmlUtil.clearSelections(map);
				}
			}
		});

		target.fireResumeEvent(0);
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

	public List<IThread> getThreads()
	{
		return this.threads;
	}

	public boolean isSuspended()
	{
		return status != null
				&& status.getInterpreterState() == CmlInterpretationStatus.SUSPENDED;
	}

	public boolean isRunning()
	{
		return status != null
				&& status.getInterpreterState() == CmlInterpretationStatus.RUNNING;
	}
}
