package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlTreeContentProvider implements ITreeContentProvider {

    private static final String SMILING_ERROR_STRING = "P = NP ? Well we are working on it.";
    private final Control parentControl;

    public CmlTreeContentProvider(Control control) {
	parentControl = control;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    private PSource current;

    @Override
    public Object[] getElements(Object inputElement) {

	try {
	    if (inputElement instanceof CmlSourceUnit) {
		// Get current source tree
		current = ((CmlSourceUnit) inputElement).getSourceAst();
		if (current == null)
		    return new Object[0];
		// If there are any declarations lets see them
		List<Wrapper<PDefinition>> res = new LinkedList<Wrapper<PDefinition>>();
		for (PDefinition def : current.getParagraphs()) {

		    // Get the entry names for the global declarations
		    String dscr = TopLevelDefinitionMap.getDescription(def
			    .getClass());
		    if (dscr == null)
			res.add(Wrapper.newInstance(def, def.getName().name));
		    else
			res.add(Wrapper.newInstance(def, dscr));

		}
		return res.toArray();
	    }
	} catch (Exception e) {
	    return new String[] { SMILING_ERROR_STRING };
	}
	return new String[0];
    }

    @Override
    public Object[] getChildren(Object parentElement) {
	try {
	    Object n = parentElement;

	    if (n instanceof Wrapper) {
		Wrapper w = (Wrapper) n;

		if (w.isClass(AClassParagraphDefinition.class)) {
		    return handleClassParagraphDefinition(
			    (AClassParagraphDefinition) w.value).toArray();
		}

		if (w.isClass(AChannelParagraphDefinition.class))
		    return handleChannelParagraphDefinition(
			    (AChannelParagraphDefinition) w.value).toArray();

		if (w.isClass(AChansetParagraphDefinition.class))
		    return handleChansetParagraphDefinition(
			    (AChansetParagraphDefinition) w.value).toArray();

		if (w.isClass(AValueParagraphDefinition.class)) {
		    return handleValueParagraphDefinition(
			    (AValueParagraphDefinition) w.value).toArray();
		}

		if (w.isClass(AProcessParagraphDefinition.class)) {
		    return handleProcessParagraphDefinition(
			    (AProcessParagraphDefinition) w.value).toArray();
		}

		if (w.isClass(ATypesParagraphDefinition.class)) {
		    List<Wrapper<? extends PDefinition>> res = new LinkedList<Wrapper<? extends PDefinition>>();
		    ATypesParagraphDefinition td = (ATypesParagraphDefinition) w.value;
		    res = DefinitionMap.getDelegate(td.getClass())
			    .extractSubdefinition(td);
		    return res.toArray();
		}

		if (w.isClass(AFunctionParagraphDefinition.class)) {
		    List<Wrapper<? extends PDefinition>> res = new LinkedList<Wrapper<? extends PDefinition>>();
		    AFunctionParagraphDefinition fd = (AFunctionParagraphDefinition) w.value;
		    res = DefinitionMap.getDelegate(fd.getClass())
			    .extractSubdefinition(fd);
		    return res.toArray();
		}
	    }
	    return new String[0];
	} catch (Exception e) {
	    return new String[] { SMILING_ERROR_STRING };
	}
    }

    private List<Wrapper<? extends PDefinition>> handleChansetParagraphDefinition(
	    AChansetParagraphDefinition cspdef) {
	List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
	for (AChansetDefinition cdef : cspdef.getChansets()) {
	    r.add(Wrapper.newInstance(cdef, cdef.getIdentifier().toString()));
	}
	return r;
    }

    private List<Wrapper<? extends PDefinition>> handleClassParagraphDefinition(
	    AClassParagraphDefinition cpdef) {
	List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
	for (PDefinition pdef : cpdef.getDefinitions()) {
	    r.addAll(DefinitionMap.getDelegate(pdef.getClass())
		    .extractSubdefinition(pdef));
	}
	return r;
    }

    private List<Wrapper<? extends INode>> handleProcessParagraphDefinition(
	    AProcessParagraphDefinition ppdef) {
	PProcess pp = ppdef.getProcessDefinition().getProcess();
	return ProcessMap.getDelegate(pp.getClass()).makeEntries(pp);
    }


    private List<Wrapper<AChannelNameDefinition>> handleChannelParagraphDefinition(
	    AChannelParagraphDefinition cpdef) {
	List<Wrapper<AChannelNameDefinition>> r = new LinkedList<Wrapper<AChannelNameDefinition>>();
	for (AChannelNameDefinition dec : cpdef.getChannelNameDeclarations()) {
	    r.add(Wrapper.newInstance(dec, dec.getSingleType().getIdentifiers()
		    .toString()
		    + ": " + dec.getSingleType().getType()));
	}
	return r;
    }

    private List<Wrapper<? extends PDefinition>> handleValueParagraphDefinition(
	    AValueParagraphDefinition cast) {
	return DefinitionMap.getDelegate(cast.getClass()).extractSubdefinition(
		cast);
    }

    @Override
    public Object getParent(Object element) {
	if (element instanceof INode)
	    return ((INode)element).parent();
	return null;
    }

    @Override
    public boolean hasChildren(Object element) {
	return element instanceof Wrapper;
    }

}
