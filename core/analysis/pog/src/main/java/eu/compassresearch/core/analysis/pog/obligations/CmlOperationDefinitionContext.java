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

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.ATypeMultipleBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AOperationType;
import org.overture.pog.contexts.POOperationDefinitionContext;

public class CmlOperationDefinitionContext extends POOperationDefinitionContext
{

	List<AInstanceVariableDefinition> psdefs;

	public CmlOperationDefinitionContext(
			AImplicitOperationDefinition definition, boolean precond,
			PDefinition stateDefinition)
	{
		super(definition.getName(), (AOperationType) definition.getType(), getParamPatternList(definition), precond, definition.getPrecondition(), stateDefinition, (AImplicitOperationDefinition) null);
	}

	public CmlOperationDefinitionContext(
			AExplicitOperationDefinition definition, boolean precond,
			PDefinition stateDefinition)
	{
		super(definition, precond, stateDefinition);
	}

	public CmlOperationDefinitionContext(AExplicitOperationDefinition node,
			boolean precond, List<AInstanceVariableDefinition> stateDefs)
	{
		super(node.getName(), (AOperationType) node.getType(), node.getParameterPatterns(), precond, node.getPrecondition(), null, node);

		psdefs = new LinkedList<AInstanceVariableDefinition>();

		for (AInstanceVariableDefinition def : stateDefs)
		{
			psdefs.add(def.clone());
		}

		psdefs = stateDefs;
	}

	public CmlOperationDefinitionContext(AImplicitOperationDefinition node,
			boolean precond, List<AInstanceVariableDefinition> stateDefs)
	{
		super(node.getName(), (AOperationType) node.getType(), getParamPatternList(node), precond, node.getPrecondition(), null, node);

		psdefs = new LinkedList<AInstanceVariableDefinition>();

		for (AInstanceVariableDefinition def : stateDefs)
		{
			psdefs.add(def.clone());
		}

		psdefs = stateDefs;
	}

	private static LinkedList<PPattern> getParamPatternList(
			AImplicitOperationDefinition definition)
	{
		LinkedList<PPattern> plist = new LinkedList<PPattern>();

		for (APatternListTypePair pl : definition.getParameterPatterns())
		{
			plist.addAll(pl.getPatterns());
		}

		return plist;
	}

	@Override
	protected void addStateBinds(LinkedList<PMultipleBind> r)
	{
		if (psdefs == null)
		{
			super.addStateBinds(r);
		} else
		{
			// FIXME filter variables according to rd/wr frames
			for (AInstanceVariableDefinition pdef : psdefs)
			{
				ATypeMultipleBind tmBind2 = new ATypeMultipleBind();
				tmBind2.setType(pdef.getType().clone());
				AIdentifierPattern pattern = new AIdentifierPattern();
				pattern.setName(pdef.getName().clone());

				List<PPattern> plist = new LinkedList<PPattern>();
				plist.add(pattern);
				tmBind2.setPlist(plist);
				r.add(tmBind2);
			}
		}
	}

	@Override
	protected boolean anyBinds()
	{
		if (psdefs == null)
			return super.anyBinds();
		return (!psdefs.isEmpty()) || super.anyBinds();
	}

}
