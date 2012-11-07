package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.overture.ast.lex.LexLocation;

import eu.compassresearch.ide.cml.ui.editor.core.CmlEditor;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlContentPageOutliner extends ContentOutlinePage implements
	IContentOutlinePage {

    private CmlSourceUnit input;
    private CmlEditor editor;
    private CmlTreeContentProvider provider;
    private OutlineLabelProvider labelprovider;
    private TreeViewer viewer;
    
    // public static final int ALL_LEVELS = -1;

    public void setTreeSelection(ISelection selection){
	System.out.println("Setting outline selection to "+selection.toString());
	viewer.setSelection(selection, true);
    }
    
    public void refresh() {
	final Display curDisp = Display.getDefault();
	if (curDisp != null)
	    curDisp.syncExec(new Runnable() {
		public void run() {
		    getTreeViewer().refresh();
		    getTreeViewer().expandAll();
		}
	    });

    }

    public CmlContentPageOutliner(CmlEditor editor) {
	provider = new CmlTreeContentProvider(this.getControl());
	this.editor = editor;
	// editor.getEditorInput().

    }

    @Override
    public void createControl(Composite parent) {
	super.createControl(parent);
	viewer = getTreeViewer();
	viewer.setContentProvider(provider);
	viewer.setUseHashlookup(true);
	viewer.addSelectionChangedListener(new ISelectionChangedListener() {
	    public void selectionChanged(SelectionChangedEvent event) {
		// if the selection is empty clear the label
		if (event.getSelection().isEmpty()) {
		    System.out.println("Empty Selection");
		    return;
		}
		if (event.getSelection() instanceof IStructuredSelection) {
		    IStructuredSelection selection = (IStructuredSelection) event
			    .getSelection();
		    StringBuffer toShow = new StringBuffer();
		    for (Iterator iterator = selection.iterator(); iterator
			    .hasNext();) {
			// FIXME Find a better system to extract locations
			Object o = iterator.next();
			if (o instanceof Wrapper) {
			    Wrapper w = (Wrapper) o;
			    for (Method m : w.value.getClass().getMethods()) {
				if ("getLocation".equals(m.getName())) {
				    try {
					LexLocation loc = (LexLocation) m
						.invoke(w.value, new Object[0]);
					editor.setHighlightRange(
						loc.startOffset, 0, true);
				    } catch (Exception e) {
					e.printStackTrace();
				    }
				}
			    }
			}
		    }
		    // remove the trailing comma space pair
		    if (toShow.length() > 0) {
			toShow.setLength(toShow.length() - 2);
		    }
		}
	    }
	});

	labelprovider = new OutlineLabelProvider();
	viewer.setLabelProvider(labelprovider);
	viewer.addSelectionChangedListener(this);
	viewer.setInput(input);
	// FIXME ldc -Need to add proper filters
	viewer.expandAll();
    }

    public void setInput(CmlSourceUnit input) {
	this.input = input;
    }


}
