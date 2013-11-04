package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.pog.pub.IPOContextStack;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;

public class CmlStateInvariantObligation extends CmlProofObligation {

	private static final long serialVersionUID = 1L;

	public CmlStateInvariantObligation(AExplicitOperationDefinition def,
			IPOContextStack ctxt) {
		
		super(def, CmlPOType.STATE_INVARIANT, ctxt, def.getLocation());
		valuetree.setPredicate(ctxt.getPredWithContext(invDefs(def.getClassDefinition().clone())));

	}
	
	private PExp invDefs(SClassDefinition def)
	{
		PExp root = null;
		
		for (PDefinition d: SClassDefinitionAssistantTC.getInvDefs(def))
		{
			AClassInvariantDefinition cid = (AClassInvariantDefinition)d;
			root = makeAnd(root, cid.getExpression());
		}

    	return root;
	}

}
