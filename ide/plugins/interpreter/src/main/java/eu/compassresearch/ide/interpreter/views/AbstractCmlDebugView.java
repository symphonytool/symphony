package eu.compassresearch.ide.interpreter.views;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.ide.interpreter.model.CmlDebugTarget;

public abstract class AbstractCmlDebugView extends ViewPart implements
		IDebugContextListener
{

	protected ListViewer viewer;
	protected CmlDebugTarget target;

	public AbstractCmlDebugView()
	{
		super();
	}

	@Override
	public void debugContextChanged(DebugContextEvent event)
	{
		if ((event.getFlags() & DebugContextEvent.ACTIVATED) > 0)
		{
			contextActivated(event.getContext());
		}

	}

	private void contextActivated(ISelection context)
	{
		if (!isAvailable())
		{
			return;
		}

		if (context instanceof ITreeSelection && !context.isEmpty())
		{
			ITreeSelection tCtxt = (ITreeSelection) context;
			for (TreePath tp : tCtxt.getPathsFor(tCtxt.getFirstElement()))
			{
				for (int i = 0; i < tp.getSegmentCount(); i++)
				{
					Object elem = tp.getSegment(i);
					if (elem instanceof CmlDebugTarget)
					{
						refreshViewer((CmlDebugTarget) elem);
						break;
					}
				}

			}
		}

	}

	private void refreshViewer(CmlDebugTarget selectedTarget)
	{
		if (this.target == selectedTarget)
		{
			return;
		}

		this.target = selectedTarget;

		internalViewerUpdate();
	}

	abstract void internalViewerUpdate();

	public void createPartControl(final org.eclipse.swt.widgets.Composite parent)
	{
		DebugUITools.addPartDebugContextListener(getSite(), this);
	}

	/**
	 * Returns whether this view's viewer is currently available.
	 * 
	 * @return whether this view's viewer is currently available
	 */
	public boolean isAvailable()
	{
		return !(getViewer() == null || getViewer().getControl() == null || getViewer().getControl().isDisposed());
	}

	private ListViewer getViewer()
	{
		return this.viewer;
	}

}