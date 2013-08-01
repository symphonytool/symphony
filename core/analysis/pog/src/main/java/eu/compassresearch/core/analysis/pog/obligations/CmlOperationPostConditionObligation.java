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


import org.overture.pog.pub.IPOContextStack;

import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
public class CmlOperationPostConditionObligation extends CmlProofObligation{
	
//	private final PExp preexp;
//	private final PExp postexp;
//	private final List<AErrorCase> errs;
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CmlOperationPostConditionObligation(AExplicitCmlOperationDefinition op,
				IPOContextStack ctxt)
	{
		super(op, CmlPOType.OP_POST_CONDITION, ctxt);
		//FIXME implement ast based PO predicate
	
	}

	public CmlOperationPostConditionObligation(AImplicitCmlOperationDefinition op,
				IPOContextStack ctxt)
	{
		super(op, CmlPOType.OP_POST_CONDITION, ctxt);
		//FIXME implement ast based PO predicate

	}

}
