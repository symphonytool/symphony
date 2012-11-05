package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.AStateProcess;
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
				List<Wrapper<PDefinition>> res = new LinkedList<CmlTreeContentProvider.Wrapper<PDefinition>>();
				for (PDefinition def : current.getParagraphs()) {
					res.add(wrapParagraphDefinition(def));

				}
				return res.toArray();
			}
			return new Object[0];
		} catch (Exception e) {
			return new String[] { SMILING_ERROR_STRING };
		}
	}

	private static class Wrapper<T> extends FieldDecoration {
		private String str;

		public static <T> Wrapper<T> newInstance(T value, String str) {
			return new Wrapper<T>(null, value, str);
		}

		public Wrapper(Image img, T value, String str) {
			super(img, str);
			this.value = value;
			this.str = str;
		}

		public T value;

		public boolean isClass(Class<?> clz) {
			return clz.isInstance(value);
		}

		public String toString() {
			return str;
		}

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

				if (w.isClass(AProcessParagraphDefinition.class)
						|| w.isClass(PProcess.class)) {
					AProcessParagraphDefinition processDecl = (AProcessParagraphDefinition) w.value;
					AProcessDefinition def = processDecl.getProcessDefinition();
					PProcess process = def.getProcess();

					List<Object> res = handleProcess(process);

					return res.toArray();
				}

				if (w.isClass(ASequentialCompositionProcess.class)) {
					List<Object> res = new LinkedList<Object>();
					ASequentialCompositionProcess comp = (ASequentialCompositionProcess) w.value;
					handleSequentialCompisitionProcess(res, comp);
					return res.toArray();
				}

				if (((Wrapper) n).isClass(ATypesParagraphDefinition.class)) {
					List<String> res = new LinkedList<String>();
					ATypesParagraphDefinition td = (ATypesParagraphDefinition) w.value;
					res = DefinitionMap.getDelegate(td.getClass())
							.extractSubdefinition(td);
					return res.toArray();
				}

				if (((Wrapper) n).isClass(AFunctionParagraphDefinition.class)) {
					List<String> res = new LinkedList<String>();
					AFunctionParagraphDefinition fd = (AFunctionParagraphDefinition) w.value;
					res = DefinitionMap.getDelegate(fd.getClass())
							.extractSubdefinition(fd);
					return res.toArray();
				}

				// if (((Wrapper)
				// n).isClass(AOperationParagraphDefinition.class)) {
				// List<String> res = new LinkedList<String>();
				// AOperationParagraphDefinition od =
				// (AOperationParagraphDefinition) w.value;
				// for (SOperationDefinition sod : od.getOperations()) {
				// res.add("[O] " + notNullName(sod.getName()));
				// }
				// return res.toArray();
				// }
				//
				// if (((Wrapper) n).isClass(AActionParagraphDefinition.class))
				// {
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
		} catch (Exception e) {
			return new String[] { SMILING_ERROR_STRING };
		}

		return new String[0];
	}

	private List<String> handleChansetParagraphDefinition(
			AChansetParagraphDefinition cspdef) {
		List<String> r = new LinkedList<String>();
		for (AChansetDefinition cdef : cspdef.getChansets()) {
			r.add(cdef.getIdentifier().toString());
		}
		return r;
	}

	private List<String> handleClassParagraphDefinition(
			AClassParagraphDefinition cpdef) {
		List<String> r = new LinkedList<String>();

		for (PDefinition pdef : cpdef.getDefinitions()) {
			r.addAll(DefinitionMap.getDelegate(pdef.getClass())
					.extractSubdefinition(pdef));
		}
		return r;
	}

	private List<String> handleProcessParagraphDefinition(
			AProcessParagraphDefinition ppdef) {
		PProcess pp = ppdef.getProcessDefinition().getProcess();
		return ProcessMap.getProcessMap().get(pp.getClass()).makeEntries(pp);
	}

	private Wrapper<PDefinition> wrapParagraphDefinition(PDefinition pdef) {
		if (pdef instanceof AValueParagraphDefinition)
			return Wrapper.newInstance(pdef, "global value declarations");
		if (pdef instanceof AFunctionParagraphDefinition)
			return Wrapper.newInstance(pdef, "global function declarations");
		if (pdef instanceof AOperationParagraphDefinition)
			return Wrapper.newInstance(pdef, "Operations");
		if (pdef instanceof ATypesParagraphDefinition)
			return Wrapper.newInstance(pdef, "global type declarations");
		if (pdef instanceof AChannelParagraphDefinition)
			return Wrapper.newInstance(pdef, "channel declarations");
		if (pdef instanceof AChansetParagraphDefinition)
			return Wrapper.newInstance(pdef, "chanset declarations");

		return Wrapper.newInstance(pdef, pdef.getName().name);
	}

	private List<Object> handleProcess(PProcess process) {
		List<Object> res = new LinkedList<Object>();
		if (process instanceof AStateProcess) {
			AStateProcess sp = (AStateProcess) process;
			PAction a = sp.getAction();
			addActions(res, a);
			return res;
		}

		if (process instanceof ASequentialCompositionProcess) {
			ASequentialCompositionProcess p = (ASequentialCompositionProcess) process;
			PProcess left = p.getLeft();
			PProcess right = p.getRight();
			res.add(Wrapper.newInstance(left, "left"));
			res.add(Wrapper.newInstance(right, "right"));
			return res;
		}

		res.add("Not implemented yet \"" + process.getClass().getName() + "\"");
		return res;
	}

	private void handleSequentialCompisitionProcess(List<Object> res,
			ASequentialCompositionProcess comp) {
		PProcess left = comp.getLeft();
		PProcess right = comp.getRight();

		if (left instanceof ASequentialCompositionProcess)
			handleSequentialCompisitionProcess(res,
					(ASequentialCompositionProcess) left);
		else
			handleOtherProcess(res, left);

		if (right instanceof ASequentialCompositionProcess)
			handleSequentialCompisitionProcess(res,
					(ASequentialCompositionProcess) right);
		else
			handleOtherProcess(res, right);
	}

	private void handleOtherProcess(List<Object> res, PProcess p) {
		if (p instanceof AStateProcess)
			res.add("State Process");
		else
			res.add("** Process **");
	}

	private void addActions(List<Object> res, PAction a) {
		if (a instanceof ASkipAction) {
			res.add("Skip");
			return;
		}
		if (a instanceof AStopAction) {
			res.add("Stop");
			return;
		}
		if (a instanceof AChaosAction) {
			res.add("Chaos");
			return;
		}
		if (a instanceof ASequentialCompositionAction) {
			ASequentialCompositionAction seqa = (ASequentialCompositionAction) a;
			PAction left = seqa.getLeft();
			PAction right = seqa.getRight();
			addActions(res, left);
			addActions(res, right);
			return;
		}
	}

	private List<String> handleChannelParagraphDefinition(
			AChannelParagraphDefinition cpdef) {
		List<String> r = new LinkedList<String>();
		for (AChannelNameDefinition dec : cpdef.getChannelNameDeclarations()) {
			r.add(dec.getSingleType().getIdentifiers().toString() + ": "
					+ dec.getSingleType().getType());
		}
		return r;
	}

	private List<String> handleValueParagraphDefinition(
			AValueParagraphDefinition cast) {
		return DefinitionMap.getDelegate(cast.getClass()).extractSubdefinition(
				cast);
	}

	// Why does this return null?
	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof Wrapper;
	}

}
