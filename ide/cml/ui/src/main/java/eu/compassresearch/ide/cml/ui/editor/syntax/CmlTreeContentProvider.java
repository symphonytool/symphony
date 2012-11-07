package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.declarations.AChannelNameDeclaration;
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
		if (inputElement instanceof CmlSourceUnit) {
			// Get current source tree
			current = ((CmlSourceUnit) inputElement).getSourceAst();
			if (current == null)
				return new Object[0];
			// If there are any declarations lets see them
			List<Wrapper<PDefinition>> res = new LinkedList<Wrapper<PDefinition>>();
			for (PDefinition def : current.getParagraphs()) {

				//Get the entry names for the global declarations
				String dscr = TopLevelDefinitionMap.getDescription(def.getClass());
				if (dscr == null)
					res.add( Wrapper.newInstance(def, def.getName().name));
				else res.add( Wrapper.newInstance(def, dscr));
				

			}
			return res.toArray();
		}
		return new Object[0];
	}



	@Override
	public Object[] getChildren(Object parentElement) {

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

//			if (w.isClass(AProcessParagraphDefinition.class)) {
//				AProcessParagraphDefinition processDecl = (AProcessParagraphDefinition) w.value;
//				AProcessDefinition def = processDecl.getProcessDefinition();
//				PProcess process = def.getProcess();
//
//				List<Object> res = handleProcess(process);
//
//				return res.toArray();
//			}
//
//			if (w.isClass(ASequentialCompositionProcess.class)) {
//				List<Object> res = new LinkedList<Object>();
//				ASequentialCompositionProcess comp = (ASequentialCompositionProcess) w.value;
//				handleSequentialCompisitionProcess(res, comp);
//				return res.toArray();
//			}

			if (((Wrapper) n).isClass(ATypesParagraphDefinition.class)) {
			    List<Wrapper<? extends PDefinition>> res = new LinkedList<Wrapper<? extends PDefinition>>();
				ATypesParagraphDefinition td = (ATypesParagraphDefinition) w.value;
				res = DefinitionMap.getDelegate(td.getClass())
						.extractSubdefinition(td);
				return res.toArray();
			}

			if (((Wrapper) n).isClass(AFunctionParagraphDefinition.class)) {
			    List<Wrapper<? extends PDefinition>> res = new LinkedList<Wrapper<? extends PDefinition>>();
				AFunctionParagraphDefinition fd = (AFunctionParagraphDefinition) w.value;
				res = DefinitionMap.getDelegate(fd.getClass())
						.extractSubdefinition(fd);
				return res.toArray();
			}

			// if (((Wrapper) n).isClass(AOperationParagraphDefinition.class)) {
			// List<String> res = new LinkedList<String>();
			// AOperationParagraphDefinition od =
			// (AOperationParagraphDefinition) w.value;
			// for (SOperationDefinition sod : od.getOperations()) {
			// res.add("[O] " + notNullName(sod.getName()));
			// }
			// return res.toArray();
			// }
			//
			// if (((Wrapper) n).isClass(AActionParagraphDefinition.class)) {
			// List<String> res = new LinkedList<String>();
			// AActionParagraphDefinition ad = (AActionParagraphDefinition)
			// w.value;
			// for (AActionDefinition a : ad.getActions()) {
			// res.add("[A] " + notNullName(a.getName()));
			// }
			// return res.toArray();
			//
			// }
		}

		return new String[0];
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

//
//	private List<Object> handleProcess(PProcess process) {
//		List<Object> res = new LinkedList<Object>();
//		if (process instanceof AStateProcess) {
//			AStateProcess sp = (AStateProcess) process;
//			PAction a = sp.getAction();
//			addActions(res, a);
//			return res;
//		}
//
//		if (process instanceof ASequentialCompositionProcess) {
//			ASequentialCompositionProcess p = (ASequentialCompositionProcess) process;
//			PProcess left = p.getLeft();
//			PProcess right = p.getRight();
//			res.add(Wrapper.newInstance(left, "left"));
//			res.add(Wrapper.newInstance(right, "right"));
//			return res;
//		}
//
//		res.add("Not implemented yet \"" + process.getClass().getName() + "\"");
//		return res;
//	}
//
//	private void handleSequentialCompisitionProcess(List<Object> res,
//			ASequentialCompositionProcess comp) {
//		PProcess left = comp.getLeft();
//		PProcess right = comp.getRight();
//
//		if (left instanceof ASequentialCompositionProcess)
//			handleSequentialCompisitionProcess(res,
//					(ASequentialCompositionProcess) left);
//		else
//			handleOtherProcess(res, left);
//
//		if (right instanceof ASequentialCompositionProcess)
//			handleSequentialCompisitionProcess(res,
//					(ASequentialCompositionProcess) right);
//		else
//			handleOtherProcess(res, right);
//	}

//	private void handleOtherProcess(List<Object> res, PProcess p) {
//		if (p instanceof AStateProcess)
//			res.add("State Process");
//		else
//			res.add("** Process **");
//	}

//	private void addActions(List<Object> res, PAction a) {
//		if (a instanceof ASkipAction) {
//			res.add("Skip");
//			return;
//		}
//		if (a instanceof AStopAction) {
//			res.add("Stop");
//			return;
//		}
//		if (a instanceof AChaosAction) {
//			res.add("Chaos");
//			return;
//		}
//		if (a instanceof ASequentialCompositionAction) {
//			ASequentialCompositionAction seqa = (ASequentialCompositionAction) a;
//			PAction left = seqa.getLeft();
//			PAction right = seqa.getRight();
//			addActions(res, left);
//			addActions(res, right);
//			return;
//		}
//	}

	private List<Wrapper<AChannelNameDeclaration>> handleChannelParagraphDefinition(
			AChannelParagraphDefinition cpdef) {
		List<Wrapper<AChannelNameDeclaration>> r = new LinkedList<Wrapper<AChannelNameDeclaration>>();
		for (AChannelNameDeclaration dec : cpdef.getChannelNameDeclarations()) {
		    	r.add(Wrapper.newInstance(dec, dec.getSingleType().getIdentifiers().toString() + ": "
					+ dec.getSingleType().getType()));
		}
		return r;
	}

	private List<Wrapper<? extends PDefinition>> handleValueParagraphDefinition(
			AValueParagraphDefinition cast) {
		return DefinitionMap.getDelegate(cast.getClass()).extractSubdefinition(
				cast);
	}

	//FIXME This must return the propper parent<
	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof Wrapper;
	}

}
