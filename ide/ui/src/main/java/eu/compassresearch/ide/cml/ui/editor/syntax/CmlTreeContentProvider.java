package eu.compassresearch.ide.cml.ui.editor.syntax;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmSourceUnit;

import eu.compassresearch.ast.actions.PActionBase;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.ACmlClassDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.ide.cml.ui.editor.syntax.DefinitionMap.DefinitionHandler;

public class CmlTreeContentProvider implements ITreeContentProvider
{

	private static final String SMILING_ERROR_STRING = "P = NP ? Well we are working on it.";
	@SuppressWarnings("unused")
	private final Control parentControl;

	// private Map<String, Object> _wrapperCache = new HashMap<String,
	// Object>();

	public CmlTreeContentProvider(Control control)
	{
		parentControl = control;
	}

	@Override
	public void dispose()
	{

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		// TODO figure out ast deltas for the outline preservation
		// if (newInput instanceof CmlSourceUnit)
		// current = ((CmlSourceUnit) newInput).getSourceAst();
	}

	private PSource current;

	// public Object[] getElements(Object inputElement)
	// {
	// if (inputElement instanceof IVdmSourceUnit)
	// {
	// IVdmSourceUnit node = (IVdmSourceUnit) inputElement;
	// return node.getParseList().toArray();
	//
	// } else if (inputElement instanceof IVdmModel)
	// {
	// return (((IVdmModel) inputElement).getRootElementList()).toArray();
	// }
	// return new Object[0];
	// }

	@Override
	public Object[] getElements(Object inputElement)
	{

		try
		{
Object[] elements = null;
			if (inputElement instanceof IVdmSourceUnit)
			{
				IVdmSourceUnit node = (IVdmSourceUnit) inputElement;
				elements= node.getParseList().toArray();

			} else if (inputElement instanceof IVdmModel)
			{
				elements= (((IVdmModel) inputElement).getRootElementList()).toArray();
			}
			
			//unwrap
			if(elements!=null && elements.length>0)
			{
				inputElement = elements[0];
			}

			// old from here
			if (inputElement instanceof CmlSourceUnit)
			{
				// Get current source tree
				current = ((CmlSourceUnit) inputElement).getSourceAst();
				if (current == null)
				{
					String[] r = { "Rebuild project to generate outline." };
					return r;
				}
				if (current.getParagraphs().isEmpty())
				{
					String[] r = { "Fix project errors and save to generate outline." };
					return r;
				}

				// If there are any declarations lets see them
				List<Object> res = new LinkedList<Object>();
				for (PDefinition def : current.getParagraphs())
				{

					// Get the entry names for the global declarations
					String dscr = TopLevelDefinitionMap.getDescription(def.getClass());
					if (dscr == null)
						res.add(Wrapper.newInstance(def, def.getName().getName()));
					else
						res.add(Wrapper.newInstance(def, dscr));

				}
				return res.toArray();
			}else if (inputElement instanceof AFileSource)
			{
				// If there are any declarations lets see them
				List<Object> res = new LinkedList<Object>();
				for (PDefinition def : ((AFileSource)inputElement).getParagraphs())
				{

					// Get the entry names for the global declarations
					String dscr = TopLevelDefinitionMap.getDescription(def.getClass());
					if (dscr == null)
						res.add(Wrapper.newInstance(def, def.getName().getName()));
					else
						res.add(Wrapper.newInstance(def, dscr));

				}
				return res.toArray();
//				return ((AFileSource)inputElement).getParagraphs().toArray();
			}
		} catch (Exception e)
		{
			return new String[] { SMILING_ERROR_STRING };
		}
		return new String[0];
	}

	@Override
	public Object[] getChildren(Object parentElement)
	{
		try
		{
			Object n = parentElement;

			if (n instanceof Wrapper)
			{
				Wrapper<?> w = (Wrapper<?>) n;

				if (w.isClass(ACmlClassDefinition.class))
				{
					return handleClassParagraphDefinition((ACmlClassDefinition) w.value).toArray();
				}

				if (w.isClass(AChannelsDefinition.class))
					return handleChannelParagraphDefinition((AChannelsDefinition) w.value).toArray();

				if (w.isClass(AChansetsDefinition.class))
					return handleChansetParagraphDefinition((AChansetsDefinition) w.value).toArray();

				if (w.isClass(AValuesDefinition.class))
				{
					return handleValueParagraphDefinition((AValuesDefinition) w.value).toArray();
				}

				if (w.isClass(AProcessDefinition.class))
				{
					return handleProcessParagraphDefinition((AProcessDefinition) w.value).toArray();
				}

				if (w.isClass(AActionProcess.class))
				{
					return handleReferenceProcess((AActionProcess) w.value).toArray();
				}

				if (w.isClass(ATypesDefinition.class))
				{
					List<Wrapper<? extends PDefinition>> res = new LinkedList<Wrapper<? extends PDefinition>>();
					ATypesDefinition td = (ATypesDefinition) w.value;
					res = DefinitionMap.getDelegate(td.getClass()).extractSubdefinition(td);
					return res.toArray();
				}

				if (w.isClass(AFunctionsDefinition.class))
				{
					List<Wrapper<? extends PDefinition>> res = new LinkedList<Wrapper<? extends PDefinition>>();
					AFunctionsDefinition fd = (AFunctionsDefinition) w.value;
					res = DefinitionMap.getDelegate(fd.getClass()).extractSubdefinition(fd);
					return res.toArray();
				}
			}
			return new String[0];
		} catch (Exception e)
		{
			return new String[] { SMILING_ERROR_STRING };
		}
	}

	private List<Wrapper<? extends PDefinition>> handleChansetParagraphDefinition(
			AChansetsDefinition cspdef)
	{
		List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
		for (AChansetDefinition cdef : cspdef.getChansets())
		{
			r.add(Wrapper.newInstance(cdef, cdef.getIdentifier().toString()));
		}
		return r;
	}

	private List<Wrapper<? extends PDefinition>> handleClassParagraphDefinition(
			ACmlClassDefinition cpdef)
	{
		List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
		for (PDefinition pdef : cpdef.getDefinitions())
		{
			DefinitionHandler dh = DefinitionMap.getDelegate(pdef.getClass());
			if (dh != null)
				r.addAll(dh.extractSubdefinition(pdef));
		}
		return r;
	}

	private List<Wrapper<? extends PDefinition>> handleValueParagraphDefinition(
			AValuesDefinition cast)
	{
		return DefinitionMap.getDelegate(cast.getClass()).extractSubdefinition(cast);
	}

	@Override
	public Object getParent(Object element)
	{
		try
		{
			if (element instanceof Wrapper)
			{
				@SuppressWarnings("unchecked")
				Wrapper<Object> w = (Wrapper<Object>) element;
				if (w.value instanceof INode)
				{
					INode in = (INode) w.value;
					if (in.parent() == null)
						return null;

					// test the parent type loop here
					if (in.parent() instanceof PType)
						return null;

					String dscr = TopLevelDefinitionMap.getDescription(in.getClass());
					if (dscr == null)
						dscr = in.parent().toString();

					return Wrapper.newInstance(in.parent(), dscr);
					// return ((INode) w.value).parent();}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			// be quiet !
		} catch (StackOverflowError sofe)
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
	public boolean hasChildren(Object element)
	{
		if (element instanceof Wrapper)
		{
			Wrapper<Object> w = (Wrapper<Object>) element;
			if (w.value instanceof AExplicitFunctionDefinition)
				return false;
			if (w.value instanceof AImplicitFunctionDefinition)
				return false;
			if (w.value instanceof AValueDefinition)
				return false;
			if (w.value instanceof ATypeDefinition)
				return false;
			if (w.value instanceof AReferenceProcess)
				return false;
			if (w.value instanceof PActionBase)
				return false;
			if (w.value instanceof AActionDefinition)
				return false;
			if (w.value instanceof SCmlOperationDefinition)
				return false;
			if (w.value instanceof AChannelNameDefinition)
				return false;
			if (w.value instanceof AChansetDefinition)
				return false;

			return true;
		}

		return false;
	}

	private List<Wrapper<? extends INode>> handleReferenceProcess(PProcess pp)
	{
		if (ProcessMap.getDelegate(pp.getClass()) != null)
			return ProcessMap.getDelegate(pp.getClass()).makeEntries(pp);
		return new LinkedList<Wrapper<? extends INode>>();
	}

	private List<Wrapper<? extends INode>> handleProcessParagraphDefinition(
			AProcessDefinition ppdef)
	{
		PProcess pp = ppdef.getProcess();
		if (ProcessMap.getDelegate(pp.getClass()) != null)
			return ProcessMap.getDelegate(pp.getClass()).makeEntries(pp);
		return new LinkedList<Wrapper<? extends INode>>();
	}

	private List<Wrapper<AChannelNameDefinition>> handleChannelParagraphDefinition(
			AChannelsDefinition cpdef)
	{
		List<Wrapper<AChannelNameDefinition>> r = new LinkedList<Wrapper<AChannelNameDefinition>>();
		for (AChannelNameDefinition dec : cpdef.getChannelNameDeclarations())
		{
			r.add(Wrapper.newInstance(dec, dec.getSingleType().getIdentifiers().toString()
					+ ": " + dec.getSingleType().getType()));
		}
		return r;
	}
}
