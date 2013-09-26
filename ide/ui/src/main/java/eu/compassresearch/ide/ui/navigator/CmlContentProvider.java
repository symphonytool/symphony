package eu.compassresearch.ide.ui.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.overture.ast.node.INode;
import org.overture.ide.core.ElementChangedEvent;
import org.overture.ide.core.ElementChangedEvent.DeltaType;
import org.overture.ide.core.IElementChangedListener;
import org.overture.ide.core.IVdmElement;
import org.overture.ide.core.IVdmElementDelta;
import org.overture.ide.core.VdmCore;
import org.overture.ide.core.resources.IVdmSourceUnit;

import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.ui.adapters.AdapterFactoryWorkbenchAdapter;

public class CmlContentProvider extends WorkbenchContentProvider
{
	private Viewer viewer;
	private static final Object[] NO_CHILDREN = new Object[0];

	private IElementChangedListener vdmlistner = new IElementChangedListener()
	{

		@Override
		public void elementChanged(ElementChangedEvent event)
		{

			if (event.getType() == DeltaType.POST_BUILD) // happens when the project is build
			{
				if (event.getDelta().getKind() == IVdmElementDelta.CHANGED)
				{
					IVdmElement source = event.getDelta().getElement();
					if (source instanceof IVdmSourceUnit)
					{
						refreshView();
					}
				}
			} else if (event.getType() == DeltaType.POST_RECONCILE) // happens when a source unit is parsed for the
																	// first time
			{
				if (event.getDelta().getKind() == IVdmElementDelta.ADDED)
				{
					IVdmElement source = event.getDelta().getElement();
					if (source instanceof IVdmSourceUnit)
					{
						refreshView();
					}
				}
			}
		}

		private void refreshView()
		{
			if (viewer != null && viewer.getControl() != null
					&& viewer.getControl().getDisplay() != null)
				viewer.getControl().getDisplay().asyncExec(new Runnable()
				{
					/*
					 * (non-Javadoc)
					 * @see java.lang.Runnable#run()
					 */
					public void run()
					{
						viewer.refresh();
					}
				});
		}
	};

	public CmlContentProvider()
	{
		VdmCore.addElementChangedListener(vdmlistner);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		VdmCore.removeElementChangedListener(vdmlistner);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.model.WorkbenchContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 * java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		super.inputChanged(viewer, oldInput, newInput);
		this.viewer = viewer;
	}

	@Override
	protected IWorkbenchAdapter getAdapter(Object element)
	{
		if (element instanceof IFile)
		{
			ICmlSourceUnit source = (ICmlSourceUnit) Util.getAdapter(((IFile) element), ICmlSourceUnit.class);
			IWorkbenchAdapter adapter = getAdapter(source);
			return adapter;
		} else if (element instanceof INode)
		{
			Object wa = new AdapterFactoryWorkbenchAdapter().getAdapter(element, IWorkbenchAdapter.class);
			if (wa != null && wa instanceof IWorkbenchAdapter)
			{
				return (IWorkbenchAdapter) wa;
			}
		}
		return super.getAdapter(element);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.model.BaseWorkbenchContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object element)
	{
		if (element instanceof IResource || element instanceof INode)
		{
			IWorkbenchAdapter adapter = getAdapter(element);
			if (adapter != null)
			{
				return adapter.getChildren(element);
			}
			return NO_CHILDREN;
		}
		return NO_CHILDREN;
	}
}
