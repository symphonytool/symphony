package eu.compassresearch.ide.modelchecker.view;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import eu.compassresearch.ide.modelchecker.Activator;
import eu.compassresearch.ide.modelchecker.MCConstants;

public class MCListView extends ViewPart {

	//private IVdmProject project;
	private TableViewer viewer;
	final Display display = Display.getCurrent();
	private MCUIResult data;

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
		layout.addColumnData(new ColumnWeightData(20, true));
		layout.addColumnData(new ColumnWeightData(10, true));
		layout.addColumnData(new ColumnWeightData(5, true));
		viewer.getTable().setLayout(layout);
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setSortDirection(SWT.NONE);
		viewer.setSorter(null);
		
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				
				if (data.getFormulaResult().getResult().isSatisfiable()){
					//if(data.getProperty().equals(MCConstants.DEADLOCK_PROPERTY)){
						try {
							IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();
							IWebBrowser browser = support.createBrowser(IWorkbenchBrowserSupport.AS_EDITOR, Activator.PLUGIN_ID, "COMPASS", "Model checker counterexample");
							IFile counterExample = data.getFormulaResult().getSvgFile();
							URL url = counterExample.getLocationURI().toURL();
							browser.openURL(url); 
						} catch (PartInitException e) {
							e.printStackTrace();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} 
					//}else{
					//	popErrorMessage("Counterexample construction available only for deadlock property!");
					//}
				}else{
					popErrorMessage("Counterexample construction available only for satisfiable models!");
				}
			}
		});

		TableColumn column = new TableColumn(viewer.getTable(), SWT.CENTER);
		column.setText("File");
		column.setToolTipText("Selected file");
		//column.setWidth(60);
		column.setAlignment(SWT.LEFT);
		
			
		
		TableColumn column2 = new TableColumn(viewer.getTable(), SWT.CENTER);
		column2.setText("Property");
		column2.setToolTipText("Checked property");
		//column2.setWidth(20);
		column2.setAlignment(SWT.CENTER);

		TableColumn column3 = new TableColumn(viewer.getTable(), SWT.CENTER);
		column3.setText("SAT");
		column3.setToolTipText("Satisfiable");
		//column3.setWidth(8);
		
		
		//viewer.setContentProvider(new ViewContentProvider());
		//viewer.setLabelProvider(new ViewLabelProvider());
		
		//makeActions();
	}

	
	public void setData(final MCUIResult data) {
		this.data = data;
		//it has to be a model check element
		display.asyncExec(new Runnable() {
			public void run(){
				viewer.getTable().removeAll();
				TableItem ti = new TableItem(viewer.getTable(), SWT.NONE);
				if(data != null){
					ti.setText(0, data.getFile().getName());
					//ti.setText(new String[] {data.getFile().getName(), getProperty(data), getSat(data)});
					ti.setText(1, getProperty(data));
					ti.setImage(2, getImage(data));
				}
			}
			private Image getImage(MCUIResult data){
				
				AbstractUIPlugin plugin = Activator.getDefault();
				
				ImageRegistry imageRegistry = plugin.getImageRegistry();
				Image image = imageRegistry.get(Activator.SAT_IMG_ID);;
				if (!data.formulaResult.getResult().isSatisfiable()){
					image = imageRegistry.get(Activator.UNSAT_IMG_ID);
				}
			
				return image; 
			}
		});
	}
	
	private String getProperty(MCUIResult data){
		if(data.getProperty().equalsIgnoreCase("deadlock")){
			return MCConstants.DEADLOCK_PROPERTY;
		} else if(data.getProperty().equalsIgnoreCase("livelock")){
			return MCConstants.LIVELOCK_PROPERTY;
		} else if(data.getProperty().equalsIgnoreCase("Nondeterminism")){
			return MCConstants.NONDETERMINISM_PROPERTY;
		}
		return "";
	}
	
	
	private void popErrorMessage(String message) {
		MessageDialog.openInformation(null, "COMPASS", message);
	}
	
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
