package eu.compassresearch.core.typechecker.weeding;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.AApplyExp;
import org.overture.ast.expressions.AFieldExp;
import org.overture.ast.expressions.AFuncInstatiationExp;
import org.overture.ast.expressions.AIsExp;
import org.overture.ast.expressions.AMkBasicExp;
import org.overture.ast.expressions.AMuExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.node.Node;
import org.overture.ast.statements.ACallObjectStm;
import org.overture.ast.statements.ACallStm;
import org.overture.ast.types.ABracketType;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.core.typechecker.DefinitionList;

/**
 * @author kel & cb
 */
public class WeedingUnresolvedPathReplacement extends
		DepthFirstAnalysisCMLAdaptor
{

	public static void apply(DefinitionList lp)
	{

		WeedingUnresolvedPathReplacement lv = new WeedingUnresolvedPathReplacement(lp.getAllClasses());
		try
		{
			lp.apply(lv);
		} catch (AnalysisException e)
		{
		}
	}

	private List<SClassDefinition> globalClasses;

	private WeedingUnresolvedPathReplacement(
			List<SClassDefinition> globalClasses)
	{
		this.globalClasses = globalClasses;
	}

	@Override
	public void caseABracketType(ABracketType node) throws AnalysisException
	{
		node.parent().replaceChild(node, node.getType());
		node.getType().apply(this);
	}

	@Override
	public void caseAUnresolvedPathExp(AUnresolvedPathExp node)
			throws AnalysisException
	{

		if (node.getIdentifiers().isEmpty())
		{
			assert false : "Oops missing identifiers";

		}

		if (/* node.parent() instanceof PStm || */node.parent() instanceof PAction)
		{
			// state designatores
			return;
		} else
		{
			PExp exp = null;
			Iterator<ILexIdentifierToken> itr = new ArrayList<ILexIdentifierToken>(node.getIdentifiers()).iterator();

			ILexIdentifierToken id = itr.next();

			String module = "";
			boolean isExplicit = false;
			if (isClassName(id))
			{
				module = id.getName();
				isExplicit = true;
				if (itr.hasNext())
				{
					id = itr.next();
				} else
				{
					if(node.parent().parent() instanceof ACallObjectStm)
					{
						//this should have been a CallStatement with the correct full name
						ACallObjectStm co = (ACallObjectStm) node.parent().parent();
						
						ILexNameToken name = new CmlLexNameToken(module, co.getFieldname().getName(), id.getLocation(), false, true);
						
						ACallStm call = AstFactory.newACallStm(name, co.getArgs());
						
						for (PExp a : call.getArgs())
						{
							a.parent(call);
						}
						
						co.parent().replaceChild(co, call);
						call.apply(this);
						return;
						
					}
					assert false : "Missing identifier for static reference";
					return;
				}
			}

			ILexLocation location = id.getLocation();
			LexLocation loc = new LexLocation(location.getFile(), id.getName(), location.getStartLine(), location.getStartPos(), location.getEndLine(), location.getEndPos(), location.getStartOffset(), location.getEndOffset());

			ILexNameToken name = new CmlLexNameToken(module, id.getName(), loc, false, isExplicit);

			exp = AstFactory.newAVariableExp(name);

			while (itr.hasNext())
			{
				exp = AstFactory.newAFieldExp(exp, itr.next());
			}
			node.parent().replaceChild(node, exp);
			// exp.parent(node.parent());
			exp.apply(this);
		}
	}

	private boolean isClassName(ILexIdentifierToken id)
	{
		for (SClassDefinition cDef : globalClasses)
		{
			if (cDef.getName().getName().equals(id.getName()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void caseILexNameToken(ILexNameToken node) throws AnalysisException
	{
		// System.out.println(node.getName() + " from 		"
		// + node.parent().toString().replace('\n', ' '));

		if (node.parent() instanceof SClassDefinition
				|| node.parent() instanceof AProcessDefinition)
		{
			return;
		}

		if (node.parent().parent() instanceof AActionClassDefinition)
		{
			// these classes have generated names so we cannot refer explicitly to them. If the module was set is would
			// work for the actions in the action block but not for the action references in the @action in the process
			// definition.
			// This handles the action definitions
			return;
		}

		SClassDefinition cDef = node.getAncestor(SClassDefinition.class);

		if (cDef == null)
		{
			AActionProcess p = node.getAncestor(AActionProcess.class);
			if (p != null)
			{
				cDef = p.getActionDefinition();
			}
		}

		if (cDef != null)
		{
			try
			{
				Field nameField = getDeclaredField(node.getClass(), "module");
				if (nameField != null)
				{
					nameField.setAccessible(true);

					if (node.getModule() != null && !node.getModule().isEmpty())
					{
						// This name is already set and is explicit so leave it in place. This is the case where Class A
						// has a B`v refering to Class B explicitly
						return;
					} else if (cDef instanceof AActionClassDefinition)
					{
						// we cannot refer to this generated class so it makes no sence to set the module. If we do then
						// references to these (vdm definitions, like values) cannot be referenced in the @action of the
						// defining process
						return;
					} else
					{
						nameField.set(node, cDef.getName().getName());
					}
				}

				Field explicitField = getDeclaredField(node.getClass(), "explicit");
				if (explicitField != null)
				{
					explicitField.setAccessible(true);

					@SuppressWarnings("rawtypes")
					Class[] search = new Class[] { AFieldExp.class,
							AFuncInstatiationExp.class, AApplyExp.class,
							AMuExp.class, AMkBasicExp.class, AIsExp.class };

					INode parent = node.parent().parent();
					boolean isExplicit = true;
					for (@SuppressWarnings("rawtypes")
					Class c : search)
					{
						if (c.isInstance(parent) || !(parent instanceof PExp))
						{
							isExplicit = false;
							break;
						}
					}

					explicitField.set(node, isExplicit);
				}
			} catch (SecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
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
