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
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;

	public class CMLPOOperationDefinitionContext implements CMLPOContext
	{
		public final ILexNameToken name;
		public final AOperationType deftype;
//		public final List<PPattern> paramPatternList;
		public final boolean addPrecond;
		public final PExp precondition;
		public final PDefinition stateDefinition;

		public CMLPOOperationDefinitionContext(AImplicitCmlOperationDefinition definition,
			boolean precond, PDefinition stateDefinition)
		{
			this.name = definition.getName();
			this.deftype = (AOperationType) definition.getType();
			this.addPrecond = precond;
//			this.paramPatternList = AImplicitOperationDefinitionAssistantTC.getParamPatternList(definition);
			this.precondition = definition.getPrecondition();
			this.stateDefinition = stateDefinition;
		}

		@Override
		public String getContext()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PExp getContextNode(PExp stitch)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isScopeBoundary()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void noteType(PExp exp, PType type)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public PType checkType(PExp exp)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getGUIContext()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getIsabelleContext()
		{
			// TODO Auto-generated method stub
			return null;
		}

		//FIXME implement ast based Context


	}
