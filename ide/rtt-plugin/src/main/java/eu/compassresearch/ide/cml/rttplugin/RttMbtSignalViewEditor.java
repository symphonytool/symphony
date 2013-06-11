package eu.compassresearch.ide.cml.rttplugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
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
	private String[] selectedSignals;
	private RttMbtSignalViewOutlinePage outline;
	private double xmax, ymax;

	private ISelectionChangedListener outlineListener = new ISelectionChangedListener() {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			selectedSignals = outline.getSelectedSignals();
			createSignalGraphs();
		}
    };
	
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
				createOutlinePage();
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
	
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IContentOutlinePage.class)) {
			if (outline == null) {
				createOutlinePage();
				outline.setInput(jsonSignals);
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
			if (valuesEntry.get(1) instanceof Double) {
				yvalues[idx] = (double) valuesEntry.get(1);
			} else {
				Long y = (Long)valuesEntry.get(1);
				yvalues[idx] = y.doubleValue();
			}
			xvalues[idx] = x.doubleValue();
			xvalues[idx] = xvalues[idx]/1000;
			if (xvalues[idx] > xmax) xmax = xvalues[idx];
			if (yvalues[idx] > ymax) ymax = yvalues[idx];
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
					graph.primaryXAxis.setFormatPattern("0.####");
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
					graph.primaryXAxis.setFormatPattern("0.####");
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

	void createOutlinePage() {
		outline = new RttMbtSignalViewOutlinePage(viewParent,
                                                  jsonSignals,
                                                  new RttMbtTreeNodeLabelProvider(), 
                                                  new TreeNodeContentProvider());
		outline.addSelectionChangedListener(outlineListener);
		System.out.println("createOutlinePage");
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

        // Create a child composite to hold the controls
        createSignalGraphs();
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
