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
******************************************************************************
*REUSED FOR NOW
******************************************************************************/

package eu.compassresearch.core.analysis.pog.obligations;


import java.util.Vector;

@SuppressWarnings("serial") //msg to compiler to ignore 'serial' warnings
public class CMLProofObligationList extends Vector<CMLProofObligation>
{
	// Convenience class to hold lists of POs.
	@Override //override superclass method
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (CMLProofObligation po: this)
		{
			po.toString();
		}

		return sb.toString();
	}
}

