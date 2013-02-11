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

import org.overture.ast.expressions.PExp;
import org.overture.ast.statements.AErrorCase;

import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
public class CMLOperationPostConditionObligation extends CMLProofObligation{
	
	private final PExp preexp;
	private final PExp postexp;
	private final List<AErrorCase> errs;
			
	public CMLOperationPostConditionObligation(AExplicitCmlOperationDefinition op,
				CMLPOContextStack ctxt)
	{
		super(op.getLocation(), CMLPOType.OP_POST_CONDITION, ctxt);
		this.preexp = op.getPrecondition();
		this.postexp = op.getPostcondition();
		this.errs = null;
		this.guiString = toGUIString(ctxt);
		this.isabelleString = toIsabelleString(ctxt);
	}

	public CMLOperationPostConditionObligation(AImplicitCmlOperationDefinition op,
				CMLPOContextStack ctxt)
	{
		super(op.getLocation(), CMLPOType.OP_POST_CONDITION, ctxt);
		this.preexp = op.getPrecondition();
		this.postexp = op.getPostcondition();
		this.errs = op.getErrors();
		this.guiString = toGUIString(ctxt);
		this.isabelleString = toIsabelleString(ctxt);
	}

	public String toGUIString(CMLPOContextStack ctxt)
	{
		return ctxt.getGUIString(getGUIExp());
	}
	
	public String toIsabelleString(CMLPOContextStack ctxt)
	{
		//TODO: need to determine Isabelle format 
		return ctxt.getIsabelleString("");
	}
	
	private String getGUIExp()
	{
		if (errs == null || errs.isEmpty())
		{
			return postexp.toString();
		} else
		{
			StringBuilder sb = new StringBuilder();

			if (preexp != null)
			{
				sb.append("(");
				sb.append(preexp);
				sb.append(" and ");
				sb.append(postexp);
				sb.append(")");
			} else
			{
				sb.append(postexp);
			}

			for (AErrorCase err : errs)
			{
				sb.append(" or (");
				sb.append(err.getLeft());
				sb.append(" and ");
				sb.append(err.getRight());
				sb.append(")");
			}

			return sb.toString();
		}
	}
}
