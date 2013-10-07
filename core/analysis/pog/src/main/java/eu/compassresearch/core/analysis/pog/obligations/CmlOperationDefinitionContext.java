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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AForAllExp;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.ATypeMultipleBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.PType;
import org.overture.pog.obligation.POOperationDefinitionContext;

import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;

public class CmlOperationDefinitionContext extends POOperationDefinitionContext
{

	List<AAssignmentDefinition> psdefs;

	public CmlOperationDefinitionContext(
			AImplicitCmlOperationDefinition definition, boolean precond,
			PDefinition stateDefinition)
	{
		super(definition.getName(), (AOperationType) definition.getType(), getParamPatternList(definition), precond, definition.getPrecondition(), stateDefinition, (AImplicitOperationDefinition) null);
	}

	// FIXME State Definition Handling for processes is broken

	public CmlOperationDefinitionContext(AImplicitCmlOperationDefinition node,
			boolean precond, List<AAssignmentDefinition> stateDefs)
	{
		super(node.getName(), (AOperationType) node.getType(), getParamPatternList(node), precond, node.getPrecondition(), null, (AImplicitOperationDefinition) null);

		psdefs= new LinkedList<AAssignmentDefinition>();
		
		for (AAssignmentDefinition def : stateDefs){
			psdefs.add(def.clone());
		}
		
		psdefs = stateDefs;
	}

	public static List<PPattern> getParamPatternList(
			AImplicitCmlOperationDefinition definition)
	{
		List<PPattern> plist = new ArrayList<PPattern>();

		for (APatternListTypePair pl : definition.getParameterPatterns())
		{
			plist.addAll(pl.getPatterns());
		}

		return plist;
	}

	@Override
	public PExp getContextNode(PExp stitch)
	{
		if (psdefs == null)
		{
			// not a process. call super
			return super.getContextNode(stitch);
		} else
		{
			if (!deftype.getParameters().isEmpty())
			{
				AForAllExp forAllExp = new AForAllExp();
				forAllExp.setBindList(makeBinds());

				if (addPrecond && precondition != null)
				{
					AImpliesBooleanBinaryExp impliesExp = new AImpliesBooleanBinaryExp();
					impliesExp.setLeft(precondition);
					impliesExp.setRight(stitch);
					forAllExp.setPredicate(impliesExp);
				} else
				{
					forAllExp.setPredicate(stitch);
				}

				return forAllExp;

			}
			return stitch;
		}
	}

	private List<? extends PMultipleBind> makeBinds()
	{
		LinkedList<PMultipleBind> r = new LinkedList<PMultipleBind>();

		Iterator<PType> types = deftype.getParameters().iterator();
		for (PPattern p : paramPatternList)
		{
			ATypeMultipleBind tmBind = new ATypeMultipleBind();
			List<PPattern> pats = new LinkedList<PPattern>();

			pats.add(p.clone());
			tmBind.setType(types.next().clone());
			tmBind.setPlist(pats);
			r.add(tmBind);
		}



			// AIdentifierPattern pattern = new AIdentifierPattern();

			// TODO Fix state definition handling here

			for (AAssignmentDefinition pdef : psdefs)
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

		

		return r;

	}

}
