package eu.compassresearch.ide.ui.navigator;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ide.core.resources.IVdmSourceUnit;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;

import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

public class CmlTreeContentProvider implements ITreeContentProvider
{

	private static final String SMILING_ERROR_STRING = "P = NP ? Well we are working on it.";

	// private Map<String, Object> _wrapperCache = new HashMap<String,
	// Object>();


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

//		try
//		{
//			inputElement = Platform.getAdapterManager().getAdapter(inputElement, ICmlSourceUnit.class);
//
//			// old from here
//			if (inputElement != null && inputElement instanceof ICmlSourceUnit)
//			{
//				// Get current source tree
//				ICmlSourceUnit source = ((ICmlSourceUnit) inputElement);
//				if (source.hasParseErrors())
//				{
//					String[] r = { "Rebuild project to generate outline." };
//					return r;
//				}
//				if (!source.hasParseTree())
//				{
//					String[] r = { "Fix project errors and save to generate outline." };
//					return r;
//				}
//
//				// If there are any declarations lets see them
//				List<Object> res = new LinkedList<Object>();
//				for (PDefinition def : source.getParseListDefinitions())
//				{
//
//					// Get the entry names for the global declarations
//					String dscr = TopLevelDefinitionMap.getDescription(def.getClass());
//					if (dscr == null)
//						res.add(Wrapper.newInstance(def, def.getName().getName()));
//					else
//						res.add(Wrapper.newInstance(def, dscr));
//
//				}
//				return res.toArray();
//			} else if (inputElement instanceof PDefinition)
//			{
//				// If there are any declarations lets see them
//				List<Object> res = new LinkedList<Object>();
//				// for (PDefinition def : ((AFileSource) inputElement).getParagraphs())
//				// {
//				//
//				PDefinition def = (PDefinition) inputElement;
//				// Get the entry names for the global declarations
//				String dscr = TopLevelDefinitionMap.getDescription(def.getClass());
//				if (dscr == null)
//					res.add(Wrapper.newInstance(def, def.getName().getName()));
//				else
//					res.add(Wrapper.newInstance(def, dscr));
//				//
//				// }
//				return res.toArray();
//				// return ((AFileSource)inputElement).getParagraphs().toArray();
//			}
//		} catch (Exception e)
//		{
//			return new String[] { SMILING_ERROR_STRING };
//		}
//		return new String[0];
		if(inputElement instanceof IVdmSourceUnit)
		{
			inputElement = Platform.getAdapterManager().getAdapter(inputElement, ICmlSourceUnit.class);
		}
		
		
		if (inputElement instanceof ICmlSourceUnit)
		{
			ICmlSourceUnit node = (ICmlSourceUnit) inputElement;
			return node.getParseList().toArray();

		} else if (inputElement instanceof ICmlModel)
		{
			return (((ICmlModel) inputElement).getAst()).toArray();
		}
		return new Object[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getChildren(Object parentElement)
	{
		try
		{
			Object n = parentElement;

			if(n instanceof SClassDefinition)
			{
				//TODO
				List<PDefinition> defs = PDefinitionListAssistantTC.singleDefinitions(((SClassDefinition) parentElement).getDefinitions());
				return defs.toArray();
			}else if (n instanceof AChannelsDefinition)
			{
				return ((AChannelsDefinition)n).getChannelNameDeclarations().toArray();
			}else if ( n instanceof AChansetsDefinition)
			{
				return ((AChansetsDefinition)n).getChansets().toArray();
			}else if ( n instanceof AValuesDefinition)
			{
				return ((AValuesDefinition)n).getValueDefinitions().toArray();
			}else if ( n instanceof AProcessDefinition)
			{
				return getChildren(((AProcessDefinition)n).getProcess());
			}else if ( n instanceof AActionProcess)
			{
				@SuppressWarnings("rawtypes")
				List defs = new Vector();
				for (Object d : ((AActionProcess)n).getDefinitionParagraphs())
				{
					Object[] children = getChildren(d);
					if(children.length==0)
					{
						defs.add(d);
					}else
					{
						defs.addAll(Arrays.asList(children));
					}
				}
				return defs.toArray();
			}else if (n instanceof ATypesDefinition)
			{
				return ((ATypesDefinition) n).getTypes().toArray();

			}else if ( n instanceof AFunctionsDefinition)
			{
				return ((AFunctionsDefinition)n).getFunctionDefinitions().toArray();
			}else if ( n instanceof AOperationsDefinition)
			{
				return ((AOperationsDefinition)n).getOperations().toArray();
			}else if ( n instanceof AActionsDefinition)
			{
				return ((AActionsDefinition)n).getActions().toArray();
			}else if( n instanceof AStateDefinition)
			{
				//a cml state def is just a list of instance variable defs
				return ((AStateDefinition) n).getStateDefs().toArray();
			}

//			return null;
//			if (n instanceof Wrapper)
//			{
//				Wrapper<?> w = (Wrapper<?>) n;

//				if (w.isClass(SClassDefinition.class))
//				{
//					return handleClassParagraphDefinition((SClassDefinition) w.value).toArray();
//				}



//				if (w.isClass(AValuesDefinition.class))
//				{
//					return handleValueParagraphDefinition((AValuesDefinition) w.value).toArray();
//				}

//				if (w.isClass(AProcessDefinition.class))
//				{
//					return handleProcessParagraphDefinition((AProcessDefinition) w.value).toArray();
//				}

//				if (w.isClass(AActionProcess.class))
//				{
//					return handleReferenceProcess((AActionProcess) w.value).toArray();
//				}

//				if (w.isClass(ATypesDefinition.class))
//				{
//					List<Wrapper<? extends PDefinition>> res = new LinkedList<Wrapper<? extends PDefinition>>();
//					ATypesDefinition td = (ATypesDefinition) w.value;
//					res = DefinitionMap.getDelegate(td.getClass()).extractSubdefinition(td);
//					return res.toArray();
//				}

//				if (w.isClass(AFunctionsDefinition.class))
//				{
//					List<Wrapper<? extends PDefinition>> res = new LinkedList<Wrapper<? extends PDefinition>>();
//					AFunctionsDefinition fd = (AFunctionsDefinition) w.value;
//					res = DefinitionMap.getDelegate(fd.getClass()).extractSubdefinition(fd);
//					return res.toArray();
//				}
//			}
			return new String[0];
		} catch (Exception e)
		{
			return new String[] { SMILING_ERROR_STRING };
		}
	}


//	private List<Wrapper<? extends PDefinition>> handleClassParagraphDefinition(
//			SClassDefinition cpdef)
//	{
//		List<Wrapper<? extends PDefinition>> r = new LinkedList<Wrapper<? extends PDefinition>>();
//		for (PDefinition pdef : cpdef.getDefinitions())
//		{
//			DefinitionHandler dh = DefinitionMap.getDelegate(pdef.getClass());
//			if (dh != null)
//				r.addAll(dh.extractSubdefinition(pdef));
//		}
//		return r;
//	}


	@Override
	public Object getParent(Object element)
	{
//		try
//		{
//			if (element instanceof Wrapper)
//			{
//				@SuppressWarnings("unchecked")
//				Wrapper<Object> w = (Wrapper<Object>) element;
//				if (w.value instanceof INode)
//				{
//					INode in = (INode) w.value;
//					if (in.parent() == null)
//						return null;
//
//					// test the parent type loop here
//					if (in.parent() instanceof PType)
//						return null;
//
//					String dscr = TopLevelDefinitionMap.getDescription(in.getClass());
//					if (dscr == null)
//						dscr = in.parent().toString();
//
//					return Wrapper.newInstance(in.parent(), dscr);
//					// return ((INode) w.value).parent();}
//				}
//			}
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//			// be quiet !
//		} catch (StackOverflowError sofe)
//		{
//			sofe.printStackTrace();
//			// be quiet !
//		}
		return null;
	}

	// TODO - correct hasChildren method for reference processes at the highest
	@Override
	public boolean hasChildren(Object element)
	{
//		if (element instanceof Wrapper)
//		{
		if (element instanceof SClassDefinition)
		{
			return ((SClassDefinition) element).getDefinitions().size() > 0;
		} else if (element instanceof ATypeDefinition)
		{
			ATypeDefinition typeDef = (ATypeDefinition) element;
			if (typeDef.getType() instanceof ARecordInvariantType)
			{
				return ((ARecordInvariantType) typeDef.getType()).getFields().size() > 0;
			}
		}else
		{
			Object[] children = getChildren(element);
			if(children!=null && children.length>0)
			{
				return true;
			}
		}

		return false;
	}

//	private List<Wrapper<? extends INode>> handleReferenceProcess(PProcess pp)
//	{
//		if (ProcessMap.getDelegate(pp.getClass()) != null)
//			return ProcessMap.getDelegate(pp.getClass()).makeEntries(pp);
//		return new LinkedList<Wrapper<? extends INode>>();
//	}
//
//	private List<Wrapper<? extends INode>> handleProcessParagraphDefinition(
//			AProcessDefinition ppdef)
//	{
//		PProcess pp = ppdef.getProcess();
//		if (ProcessMap.getDelegate(pp.getClass()) != null)
//			return ProcessMap.getDelegate(pp.getClass()).makeEntries(pp);
//		return new LinkedList<Wrapper<? extends INode>>();
//	}

}
