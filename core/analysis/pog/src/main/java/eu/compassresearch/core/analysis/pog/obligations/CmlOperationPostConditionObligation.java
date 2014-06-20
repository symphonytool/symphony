package eu.compassresearch.core.analysis.pog.obligations;
/*******************************************************************************
*
*	Copyright (C) 2008 Fujitsu Services Ltd.
*
*	Author: Nick Battle
*
*	This file is part of VDMJ.
*
*	VDMJ is free software: you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation, either version 3 of the License, or
*	(at your option) any later version.
*
*	VDMJ is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU General Public License for more details.
*
*	You should have received a copy of the GNU General Public License
*	along with VDMJ.  If not, see <http://www.gnu.org/licenses/>.
*
******************************************************************************/


import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.expressions.AAndBooleanBinaryExp;
import org.overture.ast.expressions.AOrBooleanBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.statements.AErrorCase;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.pub.IPogAssistantFactory;

public class CmlOperationPostConditionObligation extends CmlProofObligation{
	

	private static final long serialVersionUID = 1L;

	public CmlOperationPostConditionObligation(AExplicitOperationDefinition op,
				IPOContextStack ctxt, IPogAssistantFactory af) throws AnalysisException
	{
		super(op, CmlPOType.OP_POST_CONDITION, ctxt, op.getLocation(), af);
		valuetree.setPredicate(ctxt.getPredWithContext(buildExp(op.getPrecondition(), op.getPostcondition(), null)));
	}

	public CmlOperationPostConditionObligation(AImplicitOperationDefinition op,
				IPOContextStack ctxt, IPogAssistantFactory af) throws AnalysisException
	{
		super(op, CmlPOType.OP_POST_CONDITION, ctxt, op.getLocation(), af);
		valuetree.setPredicate(ctxt.getPredWithContext(buildExp(op.getPrecondition(), op.getPostcondition(), op.getErrors())));
	}

	private PExp buildExp(PExp preexp, PExp postexp, List<AErrorCase> errs)
	{
		if (errs == null || errs.isEmpty())
		{
			return postexp.clone();
		} else
		{// handled prepost or errors
			AOrBooleanBinaryExp orExp = new AOrBooleanBinaryExp();
			orExp.setLeft(handlePrePost(preexp, postexp, errs));
			PExp errorsExp = (buildErrsExp(errs));
			orExp.setRight(errorsExp);
	
			return orExp;
		}
	}

	private PExp handlePrePost(PExp preexp, PExp postexp, List<AErrorCase> errs)
	{
		if (preexp != null)
		{
			// (preexp and postexp)
			AAndBooleanBinaryExp andExp = new AAndBooleanBinaryExp();
			andExp.setLeft(preexp.clone());
			andExp.setRight(postexp.clone());
			return andExp;
		} else
		{
			return postexp.clone();
		}
	}

	private PExp handleErrorCase(AErrorCase err)
	{
		// (errlet and errright)
		AAndBooleanBinaryExp andExp = new AAndBooleanBinaryExp();
		andExp.setLeft(err.getLeft().clone());
		andExp.setRight(err.getRight().clone());
		return andExp;
	}
	
	private PExp buildErrsExp(List<AErrorCase> errs)
	{
		if (errs.size() == 1)
		{ // termination case
			return handleErrorCase(errs.get(0));
		} else
		{ // recurse on error list
			AOrBooleanBinaryExp orExp = new AOrBooleanBinaryExp();
			orExp.setLeft(handleErrorCase(errs.get(0)));
			orExp.setRight(buildErrsExp(errs.subList(1, errs.size() - 1)));
			return orExp;
		}
	}
}
