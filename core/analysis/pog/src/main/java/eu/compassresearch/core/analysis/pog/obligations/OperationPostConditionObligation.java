package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.pog.pub.IPOContextStack;

import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;

public class OperationPostConditionObligation extends CMLProofObligation
{

	public OperationPostConditionObligation(AExplicitCmlOperationDefinition op,
			IPOContextStack ctxt)
	{
		super(op, CMLPOType.OP_POST_CONDITION, ctxt);
		//FIXME implement ast based PO predicate

	}

	public OperationPostConditionObligation(AImplicitCmlOperationDefinition op,
			IPOContextStack ctxt)
	{
		super(op, CMLPOType.OP_POST_CONDITION, ctxt);
		//FIXME implement ast based PO predicate

	}

}
