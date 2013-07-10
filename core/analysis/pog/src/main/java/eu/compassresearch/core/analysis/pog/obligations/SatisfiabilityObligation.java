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

package eu.compassresearch.core.analysis.pog.obligations;

import org.overture.ast.definitions.PDefinition;
import org.overture.pog.POContextStack;

import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;

public class SatisfiabilityObligation extends CMLProofObligation {
	private String separator = "";

	public SatisfiabilityObligation(AImplicitCmlOperationDefinition op,
			PDefinition stateDefinition, POContextStack ctxt) {
		super(op, CMLPOType.OP_SATISFIABILITY, ctxt);

//FIXME getPostdef() is null...

		// FIXME Implement ast-based PO predicate method


	}

	
}
