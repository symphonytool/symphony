package eu.compassresearch.ide.modelchecker.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.GraphBuilder;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.GraphViz;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
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
					if(Activator.DOT_OK){
						try {
							//generate the dot file and compile it to svg, and show in the browser
								//we build the counterexample
							GraphBuilder gb = new GraphBuilder();
							FormulaResult formulaOutput = data.getFormulaResult().getResult();
							String propertyToCheck = data.getProperty();
							String dotContent = gb.generateDot(new StringBuilder(formulaOutput.getFacts()), propertyToCheck);
							
							//save the graphviz code to a file
							IContainer mcFolder = data.getFormulaResult().getMcFolder();
							ICmlSourceUnit selectedUnit = data.getFormulaResult().getSelectedUnit();
							//IFile dotFile = writeDotContentToFile(mcFolder,selectedUnit,dotContent);
							String name = selectedUnit.getFile().getName();
							String dotFileName = name.substring(0,name.length()-selectedUnit.getFile().getFileExtension().length())+"gv";
							IFile dotFile = ((IFolder)mcFolder).getFile(dotFileName);
							
							try{
								if(!dotFile.exists()){
									dotFile.create(new ByteArrayInputStream(dotContent.toString().getBytes()), true, new NullProgressMonitor());
								}else{
									dotFile.setContents(new ByteArrayInputStream(dotContent.toString().getBytes()), true, true, new NullProgressMonitor());
								}
								
							}catch(CoreException e){
								Activator.log(e);
							}
							
							//compile the generated graphviz
							GraphViz gv = new GraphViz();
							File file = dotFile.getRawLocation().toFile();
							String fileName = file.getName();
							gv.runDot(file);
							IFile svgFile = ((IFolder)mcFolder).getFile(fileName+".svg");
							data.getFormulaResult().setSvgFile(svgFile);
							IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();
							IWebBrowser browser = support.createBrowser(IWorkbenchBrowserSupport.AS_EDITOR, Activator.PLUGIN_ID, "COMPASS", "Model checker counterexample");
							IFile counterExample = data.getFormulaResult().getSvgFile();
							URL url = counterExample.getLocationURI().toURL();
							browser.openURL(url); 
						} catch (PartInitException e) {
							e.printStackTrace();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch(Exception e){
							//log stack trace
							StackTraceElement[] trace = e.getStackTrace();
							for (int i = 0; i < trace.length; i++) {
								Activator.logErrorMessage(trace[i].toString());
							}
							popErrorMessage(e.getMessage());
						}
					}else{
						popErrorMessage(Activator.dotNotInstalledMsg);
					}
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
				Table table = viewer.getTable();
				table.removeAll();
				table.getColumn(0).setWidth(120);
				table.getColumn(1).setWidth(100);
				table.getColumn(2).setWidth(40);
				TableItem ti = new TableItem(table, SWT.NONE);
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
