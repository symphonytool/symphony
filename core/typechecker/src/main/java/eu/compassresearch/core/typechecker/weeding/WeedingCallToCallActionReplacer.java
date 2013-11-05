package eu.compassresearch.core.typechecker.weeding;

import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.statements.AActionStm;
import org.overture.ast.statements.ACallStm;
import org.overture.typechecker.FlatEnvironment;

import eu.compassresearch.ast.actions.ACallAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.core.typechecker.CmlTypeCheckerAssistantFactory;
import eu.compassresearch.core.typechecker.DefinitionList;

/**
 * @author kel & cb
 */
@SuppressWarnings("serial")
public class WeedingCallToCallActionReplacer extends
		DepthFirstAnalysisCMLAdaptor
{

	// public static void apply(DefinitionList lp)
	// {
	//
	// WeedingCallToCallActionReplacer lv = new WeedingCallToCallActionReplacer();
	// try
	// {
	// lp.apply(lv);
	// } catch (AnalysisException e)
	// {
	// }
	// }

	public static void apply(DefinitionList sourceForest)
	{

		WeedingCallToCallActionReplacer lv = new WeedingCallToCallActionReplacer();
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

	private WeedingCallToCallActionReplacer()
	{
	}

	@SuppressWarnings("deprecation")
	public void caseACallStm(ACallStm node) throws AnalysisException
	{
		PDefinition def = node.getAncestor(AActionClassDefinition.class);
		if (def == null)
		{
			AActionProcess pDef = node.getAncestor(AActionProcess.class);
			if (pDef != null)
			{
				def = pDef.getActionDefinition();
			}
		}

		if (def != null && def instanceof SClassDefinition)
		{
			SClassDefinition cDef = (SClassDefinition) def;
			ILexNameToken name = node.getName();

			FlatEnvironment env = new FlatEnvironment(new CmlTypeCheckerAssistantFactory(), cDef.getDefinitions());

			Set<PDefinition> defs = env.findMatches(name);
			if (!defs.isEmpty())
			{
				for (PDefinition d : defs)
				{
					if (d instanceof AActionDefinition
							&& d.getName().getName().equals(name.getName()))
					{
						// replace and return
						ACallAction ac = new ACallAction();
						ac.setArgs(node.getArgs());
						ac.setLocation(node.getLocation());
						ac.setName(node.getName());
						ac.setType(node.getType());
						node.parent().replaceChild(node, new AActionStm(node.getLocation(), ac));
						return;
					}
				}
			}
		}
	};
}
