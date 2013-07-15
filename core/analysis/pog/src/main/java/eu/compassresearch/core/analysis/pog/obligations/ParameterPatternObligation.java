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

import org.overture.pog.pub.IPOContextStack;

import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;

public class ParameterPatternObligation extends CMLProofObligation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PDefinition predef;



		public ParameterPatternObligation(
			AExplicitCmlOperationDefinition def, IPOContextStack ctxt)
		{
			super(def, CMLPOType.OPERATION_PATTERNS, ctxt);
			this.predef = def.getPredef();
			//FIXME implement ast based PO predicate
		}

		public ParameterPatternObligation(
			AImplicitCmlOperationDefinition def, IPOContextStack ctxt)
		{
			super(def, CMLPOType.OPERATION_PATTERNS, ctxt);
			this.predef = def.getPredef();
			//FIXME implement ast based PO predicate

		}

	
	}
