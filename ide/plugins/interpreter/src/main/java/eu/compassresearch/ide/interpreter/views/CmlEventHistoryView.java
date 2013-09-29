package eu.compassresearch.ide.interpreter.views;

import java.util.List;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.ide.interpreter.CmlUtil;
import eu.compassresearch.ide.interpreter.model.CmlDebugTarget;

public class CmlEventHistoryView extends ViewPart implements IDebugEventSetListener
{
	ListViewer viewer;

	public CmlEventHistoryView()
	{
		DebugPlugin.getDefault().addDebugEventListener(this);
	}
	
	@Override
	public void handleDebugEvents(final DebugEvent[] events)
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			@Override
			public void run()
			{
				for(DebugEvent e : events)
					if((e.getKind() == DebugEvent.BREAKPOINT || e.getKind() == DebugEvent.SUSPEND) && e.getSource() instanceof CmlDebugTarget)
					{
						fillHistoryList((CmlDebugTarget) e.getSource());
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

	public ListViewer getListViewer()
	{
		return viewer;
	}

	@Override
	public void createPartControl(final org.eclipse.swt.widgets.Composite parent)
	{
		// Composite composite = new Composite(parent, SWT.NONE);
		viewer = new ListViewer(parent);
		viewer.setContentProvider(new IStructuredContentProvider()
		{

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput)
			{
//				System.out.println("Input changed: old=" + oldInput + ", new="
//						+ newInput);
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
		CmlDebugTarget target = CmlUtil.findCmlDebugTarget();
		if(target != null)
			fillHistoryList(target);

		// viewer.addDoubleClickListener(new IDoubleClickListener() {
		//
		// @Override
		// public void doubleClick(DoubleClickEvent event) {
		// selectSync.offer(event.getSelection().toString());
		// //MessageDialog.openInformation(null, "New selction", event.getSelection().toString());
		// }
		// });

		// viewer.setContentProvider(new ListCon)

		// org.eclipse.swt.widgets.Canvas canvas = new Canvas(parent, SWT.NONE);
		// canvas.addPaintListener(new PaintListener()
		// {
		// @Override
		// public void paintControl(PaintEvent e)
		// {
		// Canvas canvas = ((Canvas) e.widget);
		// Rectangle rect = canvas.getBounds();
		// e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_RED));
		// e.gc.drawFocus(5, 5, rect.width - 10, rect.height - 10);
		// e.gc.drawText("You can draw text directly on a canvas", 60, 60);
		// e.gc.drawText("Visit: http://www.compassresearch.eu/", 60, 90);
		// canvas.setBackground(new Color(parent.getDisplay(), 0, 213, 220));
		// }
		// });
	}
	
	private void fillHistoryList(CmlDebugTarget target)
	{
		viewer.setInput(target.getLastState().getToplevelProcess().getTrace());
	}
}
