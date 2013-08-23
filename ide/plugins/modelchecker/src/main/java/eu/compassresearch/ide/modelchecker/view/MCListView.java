package eu.compassresearch.ide.modelchecker.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
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












import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;
import eu.compassresearch.ide.modelchecker.Activator;
import eu.compassresearch.ide.modelchecker.MCConstants;
import eu.compassresearch.ide.modelchecker.MCPluginUtility;

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
				
				//if (data.getFormulaResult().getResult().isSatisfiable()){
					if(data.getProperty().equals(MCConstants.DEADLOCK_PROPERTY)){
						try {
							//generateSVGFile();
							IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();
							IWebBrowser browser = support.createBrowser(IWorkbenchBrowserSupport.AS_EDITOR, null, "COMPASS", "COMPASS");
							IFile counterExample = data.getFormulaResult().getSvgFile();
							URL url = counterExample.getLocationURI().toURL();
							browser.openURL(url); 
						} catch (PartInitException e) {
							e.printStackTrace();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch(IOException e){
							MessageDialog.openInformation(null, "COMPASS","IOException: " + e.getMessage());
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							MessageDialog.openInformation(null, "COMPASS","IOException: " + e.getMessage());
						}
					}else{
						popErrorMessage("Counterexample construction available only for deadlock property!");
					}
				//}else{
				//	popErrorMessage("Counterexample construction available only for satisfiable models!");
				//}
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

	private String generateSVGFile() throws IOException, CoreException{
		String path = "";
		if(this.data != null){
			String facts = data.getFormulaResult().getResult().getFacts();
			IResource file = data.getFile();
			String filePath = file.getFullPath().toString();
			IProject project = data.getFile().getProject();
			
			IPath projectPath = project.getLocation();
			
			IContainer container = file.getParent();
			projectPath = projectPath.append(IPath.SEPARATOR + "generated");
			
			IFolder folder = container.getFolder(projectPath);
			
			if(!folder.exists()){
				folder.create(true, true, null);
			}
			//MessageDialog.openInformation(null, "COMPASS","project.getRawLocation().toPortableString():" + projectPath.toPortableString());
			String generatedDotPath = projectPath.toPortableString() + IPath.SEPARATOR + "generated" + IPath.SEPARATOR + file.getName() + ".facts";
			File generatedDotPath2 = projectPath.append(IPath.SEPARATOR + "generated" + IPath.SEPARATOR + file.getName() + ".facts").toFile();
			
			
			MCPluginUtility.writeToFile(projectPath.toFile(), new StringBuilder(facts));
			
			
			MessageDialog.openInformation(null, "COMPASS","getFullPath:" + filePath);
			filePath = data.getFile().getLocation().toOSString();
			MessageDialog.openInformation(null, "COMPASS","getLocation:" + filePath);
			filePath = data.getFile().getLocationURI().toString();
			MessageDialog.openInformation(null, "COMPASS","getLocationURI:" + filePath);
			filePath = data.getFile().getRawLocation().toOSString();
			MessageDialog.openInformation(null, "COMPASS","getRawLocation:" + filePath);
			filePath = data.getFile().getRawLocationURI().toString();
			MessageDialog.openInformation(null, "COMPASS","getRawLocationURI:" + filePath);
		}
		return path;
	}
	public void setData(final MCUIResult data) {
		this.data = data;
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

	private void popErrorMessage(Throwable e) {
		MessageDialog.openInformation(null, "COMPASS",
				"Counterexample is available only for satisfiable models!\n" + e.getClass() + "\n" + e.getMessage());
	}
	private void popErrorMessage(String message) {
		MessageDialog.openInformation(null, "COMPASS", message);
	}
	
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
