package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.pog.pub.IPOContextStack;

import eu.compassresearch.core.analysis.pog.visitors.CmlPogAssistantFactory;

public class CmlStateInvariantObligation extends CmlProofObligation {

	private static final long serialVersionUID = 1L;

	public CmlStateInvariantObligation(CmlPogAssistantFactory af, AExplicitOperationDefinition def,
			IPOContextStack ctxt) {
		
		super(def, CmlPOType.STATE_INVARIANT, ctxt, def.getLocation());
		valuetree.setPredicate(ctxt.getPredWithContext(invDefs(af,def.getClassDefinition().clone())));

	}
	
	private PExp invDefs(CmlPogAssistantFactory af, SClassDefinition def)
	{
		PExp root = null;
		
		for (PDefinition d: af.createSClassDefinitionAssistant().getInvDefs(def))
		{
			AClassInvariantDefinition cid = (AClassInvariantDefinition)d;
			root = makeAnd(root, cid.getExpression());
		}

    	return root;
	}

}
