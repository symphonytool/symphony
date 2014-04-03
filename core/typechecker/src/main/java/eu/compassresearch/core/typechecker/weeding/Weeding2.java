package eu.compassresearch.core.typechecker.weeding;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.ANotYetSpecifiedExp;
import org.overture.ast.node.INode;
import org.overture.ast.node.Node;
import org.overture.ast.types.ABracketType;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.core.typechecker.DefinitionList;

/**
 * @author kel & cb
 */
public class Weeding2 extends DepthFirstAnalysisCMLAdaptor
{

	public static void apply(DefinitionList sourceForest)
	{

		Weeding2 lv = new Weeding2();
		for (PDefinition s : sourceForest)
		{
			if (s != null)
			{
				try
				{
					s.apply(lv);
				} catch (AnalysisException e)
				{
				}
			}
		}
	}

	public static void apply(INode node)
	{

		Weeding2 lv = new Weeding2();
		if (node != null)
		{
			try
			{
				node.apply(lv);
			} catch (AnalysisException e)
			{
			}
		}
	}

	private Weeding2()
	{
	}

	@Override
	public void caseABracketType(ABracketType node) throws AnalysisException
	{
		node.parent().replaceChild(node, node.getType());
		node.getType().apply(this);
	}

	/**
	 * Correcting the module used in locations which is used in the interpreter for Delegation
	 */
	@Override
	public void caseANotYetSpecifiedExp(ANotYetSpecifiedExp node)
			throws AnalysisException
	{
		node.setLocation(node.getLocation().clone());// new instance

		SClassDefinition cDef = node.getAncestor(SClassDefinition.class);
		if (cDef != null)
		{
			setModule(node.getLocation(), cDef.getName().getName());
		} else
		{
			AProcessDefinition pDef = node.getAncestor(AProcessDefinition.class);
			if (pDef != null)
			{
				setModule(node.getLocation(), pDef.getName().getName());
			}
		}
		super.caseANotYetSpecifiedExp(node);
	}

	private void setModule(Object node, String module)
	{
		Field nameField = getDeclaredField(node.getClass(), "module");
		if (nameField != null)
		{
			nameField.setAccessible(true);
			try
			{
				nameField.set(node, module);
			} catch (Exception e)
			{
			}
		}
	}

	private static Field getDeclaredField(
			@SuppressWarnings("rawtypes") Class c, String name)
	{
		List<Field> fields = Node.getAllFields(new LinkedList<Field>(), c);
		for (Field field : fields)
		{
			if (field.getName().equals(name))
			{
				return field;
			}
		}
		return null;
	}
}
