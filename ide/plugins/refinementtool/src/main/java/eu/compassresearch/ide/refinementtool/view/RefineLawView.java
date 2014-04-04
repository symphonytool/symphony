package eu.compassresearch.ide.refinementtool.view;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.overture.ast.node.INode;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.plugins.poviewer.IPoviewerConstants;
import org.overture.ide.ui.utility.EditorUtility;
import org.overture.pog.pub.IProofObligation;

import eu.compassresearch.ide.refinementtool.IRefineLaw;
import eu.compassresearch.ide.refinementtool.RefConstants;

public class RefineLawView extends ViewPart implements ISelectionListener
{

	ITextSelection selection = null;
	INode node = null;
	List<IRefineLaw> laws = new LinkedList<IRefineLaw>();
	protected TableViewer viewer;
	protected Action doubleClickAction;
	protected Display display = Display.getCurrent();
	protected IVdmProject project;

	public INode getNode() {
		return node;
	}

	public void setNode(INode node) {
		this.node = node;
	}

	public ITextSelection getSelection() {
		return selection;
	}

	public void setSelection(ITextSelection selection) {
		this.selection = selection;
	}
		
	public void addRefineLaw(IRefineLaw law) {
		laws.add(law);
		
		display.asyncExec(new Runnable() {

			public void run() {
				viewer.setInput(laws);
			}});
		
	}
	
	public void clearLaws() {
		selection = null;
		node = null;
		laws.clear();
		display.asyncExec(new Runnable() {

			public void run() {
				viewer.setInput(laws);
			}});
	
	}
	
	public IRefineLaw getCurrentlySelectedLaw() {
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		if (obj instanceof IRefineLaw) {
			return (IRefineLaw) obj;
		}
		return null;
	}
	
	@Override
	public void selectionChanged(IWorkbenchPart arg0, ISelection arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		viewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.H_SCROLL
				| SWT.V_SCROLL);
		// test setup columns...
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(100,  true));
		viewer.getTable().setLayout(layout);
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setSortDirection(SWT.NONE);
		viewer.setSorter(null);

		TableColumn column = new TableColumn(viewer.getTable(), SWT.LEFT);
		column.setText("Law Name");
		column.setToolTipText("Law Name");

		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(laws);

	    viewer.addDoubleClickListener(new IDoubleClickListener() {
	        public void doubleClick(DoubleClickEvent event) {
	          IHandlerService handlerService = (IHandlerService) getSite()
	              .getService(IHandlerService.class);
	          try {
				handlerService.executeCommand(RefConstants.REF_APPLY, null);
			} catch (ExecutionException | NotDefinedException
					| NotEnabledException | NotHandledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        }
	      });
        
        
		
		// viewer.addDoubleClickListener(listener);

	}
	
	protected class ViewContentProvider implements IStructuredContentProvider
	{
		// List<IRefineLaw> laws = new LinkedList<IRefineLaw>();
		
		
		public void inputChanged(Viewer v, Object oldInput, Object newInput)
		{
			// laws = (List<IRefineLaw>) newInput;
		}

		public void dispose()
		{
		}

		public Object[] getElements(Object inputElement)
		{
			if (inputElement instanceof List && inputElement == laws)
			{
				@SuppressWarnings("rawtypes")
				List list = (List) inputElement;
				return list.toArray();
			}
			return new Object[0];
		}

	}

	class ViewLabelProvider extends LabelProvider implements
	ITableLabelProvider
	{

		public void resetCounter()
		{
			count = 0;
		}

		private Integer count = 0;

		public String getColumnText(Object element, int columnIndex)
		{
			return ((IRefineLaw) element).getName();
		}

		public Image getColumnImage(Object obj, int index)
		{
			return null;
		}

		@Override
		public Image getImage(Object obj)
		{
			return null;
		}

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	protected void makeActions() {
		doubleClickAction = new Action() {
			@Override
			public void run() {

			}

		};

	}

}
