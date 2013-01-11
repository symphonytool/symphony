package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;

public class CmlTreeContentProvider implements ITreeContentProvider {

	private static final String SMILING_ERROR_STRING = "P = NP ? Well we are working on it.";
	@SuppressWarnings("unused")
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
				Wrapper<?> w = (Wrapper<?>) n;

				if (w.isClass(AClassDefinition.class)) {
					return handleClassParagraphDefinition(
							(AClassDefinition) w.value).toArray();
				}

				if (w.isClass(AChannelsDefinition.class))
					return handleChannelParagraphDefinition(
							(AChannelsDefinition) w.value).toArray();

				if (w.isClass(AChansetsDefinition.class))
					return handleChansetParagraphDefinition(
							(AChansetsDefinition) w.value).toArray();

				if (w.isClass(AValuesDefinition.class)) {
					return handleValueParagraphDefinition(
							(AValuesDefinition) w.value).toArray();
				}

				if (w.isClass(AProcessDefinition.class)) {
					return handleProcessParagraphDefinition(
							(AProcessDefinition) w.value).toArray();
				}

				if (w.isClass(AActionProcess.class)) {
					return handleReferenceProcess((AActionProcess) w.value)
							.toArray();
				}

				if (w.isClass(ATypesDefinition.class)) {
					List<Wrapper<? extends PDefinition>> res = new LinkedList<Wrapper<? extends PDefinition>>();
					ATypesDefinition td = (ATypesDefinition) w.value;
					res = DefinitionMap.getDelegate(td.getClass())
							.extractSubdefinition(td);
					return res.toArray();
				}

				if (w.isClass(AFunctionsDefinition.class)) {
					List<Wrapper<? extends PDefinition>> res = new LinkedList<Wrapper<? extends PDefinition>>();
					AFunctionsDefinition fd = (AFunctionsDefinition) w.value;
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
			AChansetsDefinition cspdef) {
		List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
		for (AChansetDefinition cdef : cspdef.getChansets()) {
			r.add(Wrapper.newInstance(cdef, cdef.getIdentifier().toString()));
		}
		return r;
	}

	private List<Wrapper<? extends PDefinition>> handleClassParagraphDefinition(
			AClassDefinition cpdef) {
		List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
		for (PDefinition pdef : cpdef.getBody()) {
			r.addAll(DefinitionMap.getDelegate(pdef.getClass())
					.extractSubdefinition(pdef));
		}
		return r;
	}

	private List<Wrapper<? extends PDefinition>> handleValueParagraphDefinition(
			AValuesDefinition cast) {
		return DefinitionMap.getDelegate(cast.getClass()).extractSubdefinition(
				cast);
	}

	@Override
	public Object getParent(Object element) {
		try {
			if (element instanceof Wrapper) {
				@SuppressWarnings("unchecked")
				Wrapper<Object> w = (Wrapper<Object>) element;
				if (w.value instanceof INode) {
					INode in = (INode) w.value;
					return Wrapper.newInstance(in.parent(), in.parent().toString());
					// return ((INode) w.value).parent();}
				}
			}
		} catch (Exception e)
		{		
			e.printStackTrace();
			// be quiet !
		}
		catch (StackOverflowError sofe)
		{
		    sofe.printStackTrace();
			// be quiet !
		}
		return null;
	}

	// TODO - correct hasChildren method for reference processes at the highest
	// level
	@SuppressWarnings("unchecked")
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Wrapper) {
			Wrapper<Object> w = (Wrapper<Object>) element;
			if (w.value instanceof AReferenceProcess)
				return false;
			return true;
		}

		return false;
	}

	private List<Wrapper<? extends INode>> handleReferenceProcess(PProcess pp) {
		if (ProcessMap.getDelegate(pp.getClass()) != null)
			return ProcessMap.getDelegate(pp.getClass()).makeEntries(pp);
		return new LinkedList<Wrapper<? extends INode>>();
	}

	private List<Wrapper<? extends INode>> handleProcessParagraphDefinition(
			AProcessDefinition ppdef) {
		PProcess pp = ppdef.getProcess();
		if (ProcessMap.getDelegate(pp.getClass()) != null)
			return ProcessMap.getDelegate(pp.getClass()).makeEntries(pp);
		return new LinkedList<Wrapper<? extends INode>>();
	}

	private List<Wrapper<AChannelNameDefinition>> handleChannelParagraphDefinition(
			AChannelsDefinition cpdef) {
		List<Wrapper<AChannelNameDefinition>> r = new LinkedList<Wrapper<AChannelNameDefinition>>();
		for (AChannelNameDefinition dec : cpdef.getChannelNameDeclarations()) {
			r.add(Wrapper.newInstance(dec, dec.getSingleType().getIdentifiers()
					.toString()
					+ ": " + dec.getSingleType().getType()));
		}
		return r;
	}
}
