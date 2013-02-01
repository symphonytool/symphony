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

import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStateDesignator;
import org.overture.pog.obligation.POType;


// Not being used anywhere so far. Can we extend the overture one?
public class CMLMapApplyObligation extends CMLProofObligation
{
	public final INode root;
	public final PExp arg;
				
	public CMLMapApplyObligation(PExp root, PExp arg, CMLPOContextStack ctxt)
	{
		super(root.getLocation(), CMLPOType.MAP_APPLY, ctxt);
		this.arg = arg;
		this.root = root;
		this.guiString = toGUIString(ctxt);
		this.isabelleString = toIsabelleString(ctxt);
	}

	public CMLMapApplyObligation(PStateDesignator root,
		PExp arg, CMLPOContextStack ctxt)
	{
		super(root.getLocation(), CMLPOType.MAP_APPLY, ctxt);
		this.arg = arg;
		this.root = root;
	}
	
	public String toGUIString(CMLPOContextStack ctxt)
	{
		return ctxt.getGUIString(arg + " in set dom " + root);
	}
	
	public String toIsabelleString(CMLPOContextStack ctxt)
	{
		//TODO: need to determine Isabelle format 
		return ctxt.getIsabelleString("");
	}
}
