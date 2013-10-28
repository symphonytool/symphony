package eu.compassresearch.core.typechecker.weeding;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.statements.PStm;
import org.overture.ast.types.ABracketType;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.program.PSource;

/**
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class WeedingUnresolvedPathReplacement extends
		DepthFirstAnalysisCMLAdaptor
{

	public static void apply(Collection<PSource> lp,
			List<SClassDefinition> globalClasses)
	{

		WeedingUnresolvedPathReplacement lv = new WeedingUnresolvedPathReplacement(globalClasses);
		for (PSource s : lp)
		{
			if (s != null)
				try
				{
					s.apply(lv);
				} catch (AnalysisException e)
				{
				}
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

		if (/*node.parent() instanceof PStm ||*/ node.parent() instanceof PAction)
		{
			// state designatores
			return;
		} else
		{
			PExp exp = null;
			Iterator<ILexIdentifierToken> itr = node.getIdentifiers().iterator();

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
					assert false : "Missing identifier for static reference";
					return;
				}
			}

			ILexLocation location = id.getLocation();
			LexLocation loc = new LexLocation(location.getFile(), id.getName(), location.getStartLine(), location.getStartPos(), location.getEndLine(), location.getEndPos(), location.getStartOffset(), location.getEndOffset());

			ILexNameToken name = new LexNameToken(module, id.getName(), loc,false,isExplicit);
			

			exp = AstFactory.newAVariableExp(name);

			while (itr.hasNext())
			{
				exp = AstFactory.newAFieldExp(exp, itr.next());
			}
			node.parent().replaceChild(node, exp);
//			exp.parent(node.parent());
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
		if((node.getModule()!=null && !node.getModule().isEmpty()) ||node.parent() instanceof SClassDefinition /*|| node.parent() instanceof PExp*/)
		{
			if((node.getModule()==null || node.getModule().trim().isEmpty()) && !(node.parent() instanceof SClassDefinition))
			{
//				System.err.println("No module set for: " +node);
			}
			return;
		}
		
		SClassDefinition cDef = node.getAncestor(SClassDefinition.class);
		if(cDef!=null)
		{
			try
			{
				Field nameField = node.getClass().getDeclaredField("module");
				if(nameField!=null && !cDef.getName().getName().equals("$global"))
				{
					nameField.setAccessible(true);
//					nameField.set(node, cDef.getName().getName());
					nameField.set(node, "");
				}
				
				
				Field explicitField = node.getClass().getDeclaredField("explicit");
				if(explicitField!=null)
				{
					explicitField.setAccessible(true);
					explicitField.set(node, false);
				}
			} catch (NoSuchFieldException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		if(node.getModule()==null || node.getModule().trim().isEmpty())
		{
//		System.err.println("-No module set for: " +node);
		}
	}
}
