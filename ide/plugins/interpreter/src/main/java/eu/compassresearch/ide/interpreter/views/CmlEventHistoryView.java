package eu.compassresearch.ide.interpreter.views;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;

import eu.compassresearch.core.interpreter.debug.CmlInterpreterStateDTO;

public class CmlEventHistoryView extends AbstractCmlDebugView implements
		IDebugEventSetListener
{

	@Override
	public void handleDebugEvents(final DebugEvent[] events)
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			@Override
			public void run()
			{
				for (DebugEvent e : events)
				{
					if ((e.getKind() == DebugEvent.BREAKPOINT
							|| e.getKind() == DebugEvent.SUSPEND || e.getKind() == DebugEvent.TERMINATE)
							&& e.getSource() == target)
					{
						fillHistoryList();
					}
				}
			}
		});
	}

	@Override
	public String getTitle()
	{
		return "Event History";
	}

	@Override
	public void setFocus()
	{
	}

	@Override
	public void createPartControl(final org.eclipse.swt.widgets.Composite parent)
	{
		viewer = new ListViewer(parent);
		viewer.setContentProvider(new IStructuredContentProvider()
		{

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput)
			{
				// System.out.println("Input changed: old=" + oldInput + ", new="
				// + newInput);
			}

			@Override
			public void dispose()
			{

			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object[] getElements(Object inputElement)
			{
				return ((List) inputElement).toArray();
			}
		});

		// add this view to the debugevent listener, this is what causes the updates in the view
		DebugPlugin.getDefault().addDebugEventListener(this);

		// this action copies the list into the clipboard
		Action copyAction = new Action()
		{
			public void run()
			{
				@SuppressWarnings("unchecked")
				List<String> events = (List<String>) viewer.getInput();
				StringSelection selection = new StringSelection(events.toString());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, selection);
			}
		};

		IActionBars bars = this.getViewSite().getActionBars();
		bars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);

		super.createPartControl(parent);
	}

	private void fillHistoryList()
	{
		if (isAvailable() && target != null)
		{
			CmlInterpreterStateDTO lastState = target.getLastState();
			if (lastState != null && lastState.getToplevelProcess()!=null)
			{
				viewer.setInput(lastState.getToplevelProcess().getTrace());
			}
		}
	}

	@Override
	void internalViewerUpdate()
	{
		fillHistoryList();

	}
}
