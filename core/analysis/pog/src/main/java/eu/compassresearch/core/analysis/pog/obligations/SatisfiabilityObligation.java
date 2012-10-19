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

import java.util.List;

import eu.compassresearch.ast.definitions.AImplicitFunctionDefinition;
import eu.compassresearch.ast.definitions.PDefinition;
//import eu.compassresearch.ast.definitions.SClassDefinition;
import eu.compassresearch.ast.expressions.PExp;
//import eu.compassresearch.ast.patterns.APatternListTypePair;
//import eu.compassresearch.ast.patterns.APatternTypePair;
//import eu.compassresearch.ast.patterns.assistants.PPatternAssistantTC;
import eu.compassresearch.util.Utils;

public class SatisfiabilityObligation extends ProofObligation
{
	private String separator = "";

	public SatisfiabilityObligation(AImplicitFunctionDefinition func,
			POContextStack ctxt)
	{
		super(func.getLocation(), POType.FUNC_SATISFIABILITY, ctxt);
		StringBuilder sb = new StringBuilder();

		if (func.getPredef() != null)
 		{
 			sb.append(func.getPredef().getName().name);
 			sb.append("(");
 			separator = "";
 			//appendParamPatterns(sb, func.getParamPatterns());
 			sb.append(")");
 			sb.append(" => ");
 		}
 
 		sb.append("exists ");
		System.out.println("Function Result: " + func.getResult());
 		sb.append(func.getResult());
 		sb.append(" & ");
		System.out.println("Function Post Def: " + func.getPostdef().getName().name);
 		//sb.append(func.getPostdef().getName().name);
 		sb.append("(");
 		separator = "";
		System.out.println("Funtion Param Patterns: " + func.getParamPatterns());
 		appendParamPatterns(sb, func.getParamPatterns());
 		sb.append(separator);
		//System.out.println("Funtion Param Patterns: " + func.getResult().getPattern());
 		//sb.append(func.getResult().getPattern());
 		sb.append(")");
 
 		value = ctxt.getObligation(sb.toString());
		System.out.println(toString());
	}

	
// 	private void appendResult(StringBuilder sb, APatternTypePair ptp)
// 	{
// 		if (ptp != null)
// 		{
// 			sb.append(separator);
// 			sb.append(ptp);
// 			separator = ", ";
// 		}
// 	}
// 
// 	private void appendResultPattern(StringBuilder sb, APatternTypePair ptp)
// 	{
// 		if (ptp != null)
// 		{
// 			sb.append(separator);
// 			sb.append(ptp.getPattern());
// 			separator = ", ";
// 		}
// 	}
// 
// 	private void appendStatePatterns(StringBuilder sb, PDefinition state,
// 			boolean old, boolean typed)
// 	{
// 		if (state == null)
// 		{
// 			return;
// 		} else if (state instanceof AStateDefinition)
// 		{
// 			if (old)
// 			{
// 				sb.append(separator);
// 				sb.append("oldstate");
// 			} else
// 			{
// 				sb.append(separator);
// 				sb.append("newstate");
// 			}
// 
// 			if (typed)
// 			{
// 				AStateDefinition def = (AStateDefinition) state;
// 				sb.append(":");
// 				sb.append(def.getName().name);
// 			}
// 		} else
// 		{
// 			if (old)
// 			{
// 				sb.append(separator);
// 				sb.append("oldself");
// 			} else
// 			{
// 				sb.append(separator);
// 				sb.append("newself");
// 			}
// 
// 			if (typed)
// 			{
// 				SClassDefinition def = (SClassDefinition) state;
// 				sb.append(":");
// 				sb.append(def.getName().name);
// 			}
// 		}
// 
// 		separator = ", ";
// 	}
// 
 	private void appendParamPatterns(StringBuilder sb,
 			List<APatternListTypePair> params)
 	{
 		for (APatternListTypePair pltp : params)
		{
 			List<PExp> expList = PPatternAssistantTC.getMatchingExpressionList(pltp.getPatterns());
 			sb.append(separator);
 			sb.append(Utils.listToString(expList));
 			separator = ", ";
 		}
 	}
}
