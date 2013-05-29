package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.csstudio.swt.xygraph.dataprovider.CircularBufferDataProvider;
import org.csstudio.swt.xygraph.figures.ToolbarArmedXYGraph;
import org.csstudio.swt.xygraph.figures.Trace;
import org.csstudio.swt.xygraph.figures.XYGraph;
import org.csstudio.swt.xygraph.figures.Trace.PointStyle;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RttMbtSignalViewEditor extends EditorPart {

	private Composite viewParent;
	private ScrolledComposite graphContainer;
	private Composite graphChild;
	private IFile iFile;
	private JSONArray jsonSignals;
	private XYGraph graph;
	private CheckedTreeSelectionDialog signalSelection = null;
	private TreeNode[] signalNodes;
	private String[] selectedSignals;
	private String[] signalNames;
	private RttMbtSignalViewOutlinePage outline;
	private double xmax, ymax;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (! (input instanceof IFileEditorInput) ) {
			throw new PartInitException("Invalid input! Expected file input");
		} else {
			IFileEditorInput iFileInput = (IFileEditorInput) input;
			iFile = iFileInput.getFile();
			try {
				// parse JSON input
		    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
				File workspaceDirectory = workspace.getRoot().getLocation().toFile();
				File inputFile = new File(workspaceDirectory.getAbsolutePath() + iFile.getFullPath().toString());
				FileReader reader = new FileReader(inputFile);
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(reader);
				jsonSignals = (JSONArray) obj;
			} catch (ParseException e) {
				System.err.println("*** error: unable to parse signals.json file!");					
			} catch (FileNotFoundException e) {
				System.err.println("*** error: unable to open '" + iFile.getFullPath().toString() + "'!");					
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setSite(site);
		setInput(input);		
	}

	public Boolean isSelected(String signalName) {
		if (selectedSignals == null) return false;
		if (signalName == null) return false;
		for (int idx = 0; idx < selectedSignals.length; idx++) {
			if ((selectedSignals[idx] != null) &&
				(signalName.compareTo(selectedSignals[idx]) == 0)) {
				return true;
			}
		}
		return false;
	}
	
	public void getSelectedSignals() {
		Object[] selectedObjects = signalSelection.getResult();
		selectedSignals = new String[selectedObjects.length];
		for (int idx = 0; idx < selectedObjects.length; idx++) {
			selectedSignals[idx] = ((TreeNode)selectedObjects[idx]).getValue().toString();
		}
	}

	public void openSignalSelectionDialog(SelectionEvent e) {
		signalSelection.open();
		getSelectedSignals();
		createSignalGraphs();
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IContentOutlinePage.class)) {
			if (outline == null) {
				outline = new RttMbtSignalViewOutlinePage(signalNames);
			}
			return outline;
		}
		return super.getAdapter(adapter);
	}

	public Trace getTraceforSignal(String signalName) {
		
		// find signalName
		Boolean found = false;
		JSONArray values = null;
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonSignals.iterator();
		while (iterator.hasNext()) {
			JSONObject entry = iterator.next();
			String name = (String) entry.get("name");
			if (signalName.compareTo(name) == 0) {
				found = true;
				values = (JSONArray) entry.get("data");
			}
		}
		if (!found) {
			return null;
		}

		// create trace data
		CircularBufferDataProvider traceDataProvider = new CircularBufferDataProvider(false);
		traceDataProvider.setBufferSize(values.size());
		double[] xvalues = new double[values.size()];
		double[] yvalues = new double[values.size()];
		xmax = 0; ymax = 0;
		@SuppressWarnings("unchecked")
		Iterator<JSONArray> valueIterator = values.iterator();
		int idx = 0;
		while (valueIterator.hasNext()) {
			JSONArray valuesEntry = (JSONArray) valueIterator.next();
			Long x = (Long) valuesEntry.get(0);
			Long y = (Long) valuesEntry.get(1);
			xvalues[idx] = x.doubleValue();
			yvalues[idx] = y.doubleValue();
			if (x.doubleValue() > xmax) xmax = x.doubleValue();
			if (y.doubleValue() > ymax) ymax = y.doubleValue();
			idx++;
		}
		traceDataProvider.setCurrentXDataArray(xvalues);
		traceDataProvider.setCurrentYDataArray(yvalues);

		// create trace
		Trace trace = new Trace(signalName, graph.primaryXAxis, graph.primaryYAxis, traceDataProvider);			
		trace.setPointStyle(PointStyle.POINT);
		trace.setPointSize(8);
		trace.setLineWidth(2);
		trace.setVisible(true);
		return trace;
	}
	
	public void createSignalGraphs() {
        // Create a child composite to hold the controls
        graphChild = new Composite(graphContainer, SWT.NONE);
        graphChild.setLayout(new FillLayout(SWT.VERTICAL));

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonSignals.iterator();
		while (iterator.hasNext()) {

			// get JSONArray entry for the current signal
			JSONObject entry = iterator.next();
			String signalName = (String) entry.get("name");
			if (isSelected(signalName)) {

				// create graph widget
				Canvas canvas = new Canvas(graphChild, SWT.NONE);
				final LightweightSystem lws = new LightweightSystem(canvas);
				graph = new XYGraph();
				graph.setTitle(signalName);

				// without toolbar
				// lws.setContents(graph);

				// with toolbar
				ToolbarArmedXYGraph toolbar = new ToolbarArmedXYGraph(graph);
				lws.setContents(toolbar);

				// create the trace for *_exp signal (if it exists)
				Trace trace = getTraceforSignal(signalName + "_exe");
				if (trace != null) {
					trace.setTraceColor(new Color(Display.getDefault(), new RGB(0,0,255)));
					// add trace to graph
					graph.addTrace(trace);
					graph.primaryXAxis.setRange(0, xmax + (xmax / 100));
					graph.primaryYAxis.setRange(0, ymax + (ymax / 20));
					graph.primaryYAxis.setShowMajorGrid(true);
					graph.primaryXAxis.setAutoScale(true);
					graph.primaryYAxis.setAutoScale(true);
				}

				// create the trace for signal
				trace = getTraceforSignal(signalName);
				if (trace != null) {
					trace.setTraceColor(new Color(Display.getDefault(), new RGB(255,0,0)));
					// add trace to graph
					graph.addTrace(trace);
					graph.primaryXAxis.setRange(0, xmax + (xmax / 100));
					graph.primaryYAxis.setRange(0, ymax + (ymax / 20));
					graph.primaryYAxis.setShowMajorGrid(true);
					graph.primaryXAxis.setAutoScale(true);
					graph.primaryYAxis.setAutoScale(true);
				}
			}
		}

		// set content to scroll-able container
		graphChild.pack();
		graphContainer.setContent(graphChild);
		
        // calculate the minimum size according to the number of signals displayed.
		if (selectedSignals != null) {
			graphContainer.setMinSize(500, selectedSignals.length * 200);
		} else {
			graphContainer.setMinSize(500, 200);			
		}
		graphContainer.setExpandHorizontal(true);
		graphContainer.setExpandVertical(true);
	}

	public Boolean hasSignalEntry(String signalName) {
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonSignals.iterator();
		while (iterator.hasNext()) {
			JSONObject entry = iterator.next();
			String name = (String) entry.get("name");
			if (signalName.compareTo(name) == 0) {
				return true;
			}
		}
		return false;
	}

	public void createSignalSelectionDialog() {
		signalSelection = new CheckedTreeSelectionDialog(viewParent.getShell(),
				 new RttMbtTreeNodeLabelProvider(), 
				 new TreeNodeContentProvider());
		signalSelection.setTitle("Signal Selection");
		signalSelection.setMessage("Select signals to be displayed");
		// create signal tree root node
		TreeNode rootNode = new TreeNode("Signals");
		// create list of all signals
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		List<String> names = new ArrayList<String>();
		// select all signals
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonSignals.iterator();
		while (iterator.hasNext()) {
			// get JSONArray entry for the current signal
			JSONObject entry = iterator.next();
			String signalName = (String) entry.get("name");

			// check if signal is <signalName>_exe signal
			if ((signalName.lastIndexOf("_exe") == signalName.length() - 4) &&
				(hasSignalEntry(signalName.substring(0, signalName.length() - 4)))) {
				continue;
			} else {
				// add signal to signal selection dialog
				TreeNode signalNode = new TreeNode(signalName);
				signalNode.setParent(rootNode);
				nodes.add(signalNode);
				names.add(signalName);
			}
		}
		// prepare signal selection dialog content
        signalNames = new String[names.size()];
        for (int i = 0; i < names.size(); i++) { signalNames[i] = names.get(i); }
        signalNodes = new TreeNode[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) { signalNodes[i] = nodes.get(i); }
		rootNode.setChildren(signalNodes);
		signalSelection.setInput(signalNodes);
		signalSelection.setContainerMode(true);
		signalSelection.setBlockOnOpen(true);
		getSelectedSignals();
	}
	
	public void createPartControl(Composite parent) {
		if (jsonSignals == null) {
			return;
		}

		if (parent == null) {
			return;
		} else {
			viewParent = parent;
		}
		
		// @todo:
		// - add selection list to the top of other signals
		// - create composite holding the selection list
		// - add further control elements for signal viewer functionality

		// define layout of parent
    	GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        parent.setLayout(layout);

        // create composite for all signal graphs
        graphContainer = new ScrolledComposite(parent, SWT.V_SCROLL);
        graphContainer.setLayout(new FillLayout(SWT.VERTICAL));
    	GridData scrollGridData = new GridData();
    	scrollGridData.horizontalAlignment = GridData.FILL;
    	scrollGridData.verticalAlignment = GridData.FILL;
    	scrollGridData.grabExcessHorizontalSpace = true;
    	scrollGridData.grabExcessVerticalSpace = true;
    	graphContainer.setLayoutData(scrollGridData);

        // create grid data
    	GridData gridData = new GridData();
    	gridData.horizontalAlignment = GridData.FILL;
    	gridData.grabExcessHorizontalSpace = true;

        // create signal selection button
        Button openSignalDialog = new Button(parent, SWT.PUSH);
        openSignalDialog.setText("signal selection");
        openSignalDialog.setLayoutData(gridData);
        openSignalDialog.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
        		System.out.println("calling openSignalSelectionDialog()");
            	openSignalSelectionDialog(e);
            }
        }); 

        // create signal selection dialog
        createSignalSelectionDialog();

        // Create a child composite to hold the controls
        createSignalGraphs();
        
        /*
        Composite child = new Composite(graphContainer, SWT.NONE);
        child.setLayout(new FillLayout(SWT.VERTICAL));

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonSignals.iterator();
		while (iterator.hasNext()) {

			// get JSONArray entry for the current signal
			JSONObject entry = iterator.next();
			String signalName = (String) entry.get("name");
			JSONArray values = (JSONArray) entry.get("data");

			// create graph widget
			Canvas canvas = new Canvas(child, SWT.NONE);
			final LightweightSystem lws = new LightweightSystem(canvas);
			graph = new XYGraph();
			graph.setTitle(signalName);
			lws.setContents(graph);

			//create a trace data provider, which will provide the data to the trace.
			CircularBufferDataProvider traceDataProvider = new CircularBufferDataProvider(false);
			traceDataProvider.setBufferSize(values.size());
			double[] xvalues = new double[values.size()];
			double[] yvalues = new double[values.size()];
			@SuppressWarnings("unchecked")
			Iterator<JSONArray> valueIterator = values.iterator();
			int idx = 0;
			while (valueIterator.hasNext()) {
				JSONArray valuesEntry = (JSONArray) valueIterator.next();
				System.out.println("addind values " + valuesEntry.toString());
				Long x = (Long) valuesEntry.get(0);
				Long y = (Long) valuesEntry.get(1);
				xvalues[idx] = x.doubleValue();
				yvalues[idx] = y.doubleValue();
				System.out.println("x-value[" + idx + "]: " + xvalues[idx]);
				System.out.println("y-value[" + idx + "]: " + yvalues[idx]);
				idx++;
			}
			traceDataProvider.setCurrentXDataArray(xvalues);
			traceDataProvider.setCurrentYDataArray(yvalues);

			//create the trace
			Trace trace = new Trace(signalName, graph.primaryXAxis, graph.primaryYAxis, traceDataProvider);			
			trace.setPointStyle(PointStyle.POINT);
			trace.setVisible(true);
			graph.addTrace(trace);
			graph.primaryXAxis.setAutoScale(true);
			graph.primaryYAxis.setAutoScale(true);
		}

		graphContainer.setContent(child);
        // calculate the minimum size according to the number of signals displayed.
		graphContainer.setMinSize(500, jsonSignals.size() * 200);
		graphContainer.setExpandHorizontal(true);
		graphContainer.setExpandVertical(true);
		*/
	}
	
	@Override
	public void dispose() {
		// @todo: cleanup
	}

	@Override
	public String getTitle() {
		return "Signal Values";
	}

	@Override
	public String getTitleToolTip() {
		return "Shows signal values";
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void doSave(IProgressMonitor arg0) {

	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSaveAs() {
	}
}
