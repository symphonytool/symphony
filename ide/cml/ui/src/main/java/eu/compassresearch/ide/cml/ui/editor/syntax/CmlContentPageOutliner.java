package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWTException;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;

import eu.compassresearch.ide.cml.ui.editor.core.CmlEditor;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlContentPageOutliner extends ContentOutlinePage implements

IContentOutlinePage {

    private CmlSourceUnit input;
    private CmlEditor editor;
    private CmlTreeContentProvider provider;
    private OutlineLabelProvider labelprovider;
    // private TreeViewer viewer;

    // TODO remove the flag hack once we get proper sync from the editor
    private static boolean loopAvoidanceFlag = true;

    @Override
    public void dispose() {
	input.clearListeners();
	if (labelprovider != null) {
	    labelprovider.dispose();
	}

	super.dispose();
    }


    public void setTreeSelection(INode element) {
	if (null == element)
	    return;
	if (!(element instanceof PDefinition))
	    return;

	Wrapper<PDefinition> w;
	PDefinition pdef = (PDefinition) element;

	String dscr = TopLevelDefinitionMap.getDescription(pdef.getClass());
	if (dscr == null) {
	    LexNameToken name = pdef.getName();
	    w = Wrapper.newInstance(pdef, name == null ? "null" : name.name);
	} else
	    w = Wrapper.newInstance(pdef, dscr);
	System.out.println("Setting outline selection to " + w.toString());
	loopAvoidanceFlag = false;
	getTreeViewer().setSelection(new StructuredSelection(w), true);
    }

    public void refresh() {
	TreePath[] oldPaths = getTreeViewer().getExpandedTreePaths();
	getTreeViewer().refresh();
	getTreeViewer().setExpandedTreePaths(oldPaths);
	getTreeViewer().setAutoExpandLevel(2);
    }

    public CmlContentPageOutliner(CmlEditor editor) {
	provider = new CmlTreeContentProvider(this.getControl());
	this.editor = editor;
    }

    @Override
    public void createControl(Composite parent) {
	super.createControl(parent);
	getTreeViewer().setContentProvider(provider);
	getTreeViewer().setUseHashlookup(true);
	getTreeViewer().addSelectionChangedListener(
		new ISelectionChangedListener() {

		    public void selectionChanged(SelectionChangedEvent event) {
			// if the selection is empty clear the label

			if (event.getSelection().isEmpty()) {
			    System.out.println("Empty Selection");
			    return;
			}
			if ((event.getSelection() instanceof IStructuredSelection)
				&& loopAvoidanceFlag) {
			    IStructuredSelection selection = (IStructuredSelection) event
				    .getSelection();
			    for (@SuppressWarnings("unchecked")
			    Iterator<Object> iterator = selection.iterator(); iterator
				    .hasNext();) {

				Object o = iterator.next();
				System.out.println("Selected " + o.toString());
				if (o instanceof Wrapper) {
				    Wrapper<?> w = (Wrapper<?>) o;
				    // FIXME Find a better system to extract
				    // locations
				    for (Method m : w.value.getClass()
					    .getMethods()) {
					if ("getLocation".equals(m.getName())) {
					    try {
						LexLocation loc = (LexLocation) m
							.invoke(w.value,
								new Object[0]);
						if (loc == null)
						    return;
						editor.setHighlightRange(
							loc.startOffset, 0,
							true);
					    } catch (Exception e) {
						e.printStackTrace();
					    }
					}
				    }
				}
			    }
			} else
			    loopAvoidanceFlag = true;
		    }
		});

	labelprovider = new OutlineLabelProvider();
	getTreeViewer().setLabelProvider(labelprovider);
	getTreeViewer().setAutoExpandLevel(2);
	getTreeViewer().setInput(input);
	
//	Thread astSnooze = new Thread(new Runnable() {
//	    public void run() {
//
////		while (true)
////		    if (input.getSourceAst() != null){
////			if (!input.getSourceAst().getParagraphs().isEmpty())
////			    break;
////		    }
////		    else Thread.yield();
////		
//
//
//	    }
//	});
//	astSnooze.run();


    }

    public void setInput(CmlSourceUnit input) {
	this.input = input;
    }

}
