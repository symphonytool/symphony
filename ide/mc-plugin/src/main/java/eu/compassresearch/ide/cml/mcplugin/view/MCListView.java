package eu.compassresearch.ide.cml.mcplugin.view;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.part.ViewPart;

import eu.compassresearch.ide.cml.mcplugin.Activator;

public class MCListView extends ViewPart {

	//private IVdmProject project;
	private TableViewer viewer;
	final Display display = Display.getCurrent();

	/**
	 * The constructor.
	 */
	public MCListView(){
	}
	
	public void createPartControl(Composite parent){
		viewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.H_SCROLL
				| SWT.V_SCROLL);
		// test setup columns...
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(80, true));
		layout.addColumnData(new ColumnWeightData(50, false));
		layout.addColumnData(new ColumnWeightData(40, false));
		viewer.getTable().setLayout(layout);
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setSortDirection(SWT.NONE);
		viewer.setSorter(null);
		
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport(); 
				try {
					IWebBrowser browser = support.createBrowser(IWorkbenchBrowserSupport.AS_EDITOR, null, "COMPASS", "COMPASS");
					browser.openURL(new URL("http://www.eclipse.org")); 
				} catch (PartInitException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		});

		TableColumn column = new TableColumn(viewer.getTable(), SWT.LEFT);
		column.setText("File");
		column.setToolTipText("File");
		
		TableColumn column2 = new TableColumn(viewer.getTable(), SWT.LEFT);
		column2.setText("Property");
		column2.setToolTipText("Property");

		TableColumn column3 = new TableColumn(viewer.getTable(), SWT.CENTER);
		column3.setText("SAT");
		column3.setToolTipText("Satisfiable");
		
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		
		makeActions();
	}

	public void setData(final MCUIResult data) {
		//it has to be a model check element
		display.asyncExec(new Runnable() {
			public void run(){
				viewer.getTable().removeAll();
				TableItem ti = new TableItem(viewer.getTable(), SWT.NONE);
				ti.setText(new String[] {data.getFile().getName(), getProperty(data), getSat(data)});
				//ti.setText(0, data.getFile().getName());
				//ti.setImage(1, new ViewLabelProvider().getImage(data));
				viewer.setInput(data);
				for (TableColumn col : viewer.getTable().getColumns()){
					col.pack();
				}
			}
		});
	}
	
	private String getProperty(MCUIResult data){
		if(data.getProperty().equalsIgnoreCase("deadlock")){
			return "Deadlock";
		} else if(data.getProperty().equalsIgnoreCase("livelock")){
			return "Livelock";
		} else if(data.getProperty().equalsIgnoreCase("Nondeterminism")){
			return "Nondeterminism";
		}
		return "";
	}
	
	private String getSat(MCUIResult data){
		if(data.getFormulaResult().getResult().isSatisfiable())
			return "YES";
		return "NO";
	}

	protected void makeActions(){
		Action doubleClickAction = new Action() {
			@Override
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
			}

		};
	}
	
	class ViewContentProvider implements IStructuredContentProvider{

		public void inputChanged(Viewer v, Object oldInput, Object newInput){
		}

		public void dispose(){
		}

		public Object[] getElements(Object inputElement){
			if (inputElement instanceof List){
				@SuppressWarnings("rawtypes")
				List list = (List) inputElement;
				return list.toArray();
			}
			return new Object[0];
		}
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {

		public void resetCounter(){
			count = 0;
		}

		private Integer count = 0;

		public String getColumnText(Object element, int columnIndex){
			MCUIResult data = (MCUIResult) element;
			String columnText;
			switch (columnIndex){
				case 0:
					count++;
					columnText = data.file.getName();// count.toString();
					break;
				default:
					columnText = "not set";
			}
			return columnText;
		}
		
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			Image img = null;
			if (columnIndex == 1){
				img = getImage(element);
			}
			return img;
		}


		@Override
		public Image getImage(Object obj)
		{
			MCUIResult data = (MCUIResult) obj;
			String imgPath = "icons/sat.png";
			if (!data.formulaResult.getResult().isSatisfiable()){
				imgPath = "icons/unsat.png";
			}
			return Activator.getImageDescriptor(imgPath).createImage();
		}

	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
