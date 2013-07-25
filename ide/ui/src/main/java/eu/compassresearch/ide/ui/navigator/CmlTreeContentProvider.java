package eu.compassresearch.ide.ui.navigator;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ide.core.resources.IVdmSourceUnit;

import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

public class CmlTreeContentProvider implements ITreeContentProvider
{

	@Override
	public void dispose()
	{

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
	}

	@Override
	public Object[] getElements(Object inputElement)
	{
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
//				List<PDefinition> defs = PDefinitionListAssistantTC.singleDefinitions(((SClassDefinition) parentElement).getDefinitions());
				return getDefinitions((SClassDefinition) n).toArray();
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

			return new String[0];
		} catch (Exception e)
		{
			return new String[] { "Unsupported node" };
		}
	}

	
	private static List<PDefinition> getDefinitions(SClassDefinition c)
	{
		List<PDefinition> defs = new Vector<PDefinition>();
		
		for (PDefinition d : c.getDefinitions())
		{
			if(d instanceof AExplicitCmlOperationDefinition ||d instanceof  AImplicitCmlOperationDefinition ||d instanceof AImplicitOperationDefinition ||d instanceof AExplicitOperationDefinition ||d instanceof AImplicitFunctionDefinition ||d instanceof AExplicitFunctionDefinition
					||d instanceof ATypeDefinition||d instanceof AValuesDefinition||d instanceof AStateDefinition)
			{
				defs.add(d);
			}
			
			if(d instanceof AOperationsDefinition)
			{
				defs.addAll(((AOperationsDefinition) d).getOperations());
			}
		}
		
		return defs;
	}

	@Override
	public Object getParent(Object element)
	{
		return null;
	}

	// TODO - correct hasChildren method for reference processes at the highest
	@Override
	public boolean hasChildren(Object element)
	{
		if (element instanceof SClassDefinition)
		{
			return getDefinitions(((SClassDefinition) element)).size() > 0;
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

}
