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
import org.overture.ast.lex.LexLocation;
import org.overture.pog.obligation.NonZeroObligation;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.POType;


public class CMLNonZeroObligation extends CMLProofObligation
{
	public CMLNonZeroObligation(
		LexLocation location, PExp exp, CMLPOContextStack ctxt)
	{
		super(location, CMLPOType.NON_ZERO, ctxt);
		value = ctxt.getObligation(exp + " <> 0");
		System.out.println(toString());
	}
}