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

import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;

import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;

public class CMLSatisfiabilityObligation extends CMLProofObligation
{
	private String separator = "";
	private final AExplicitFunctionDefinition preexp;
	private final LinkedList<APatternListTypePair> paramPatterns;
	private final PDefinition stateDefinition;
	private final LinkedList<APatternTypePair> res;
	private final AExplicitFunctionDefinition postexp;
	

		public CMLSatisfiabilityObligation(AImplicitCmlOperationDefinition op,
				PDefinition stateDefinition, CMLPOContextStack ctxt)
		{
			super(op.getLocation(), CMLPOType.OP_SATISFIABILITY, ctxt);

			this.preexp = op.getPredef();
			this.paramPatterns = op.getParameterPatterns();
			this.stateDefinition = stateDefinition;
			this.res = op.getResult();
			this.postexp = op.getPostdef();
			this.guiString = toGUIString(ctxt);
		}

		@Override
		public String toGUIString(CMLPOContextStack ctxt) {
			return ctxt.getGUIString(getExp());
		}
		
		@Override
		public String toIsabelleString(CMLPOContextStack ctxt) {
			// TODO Auto-generated method stub
			return null;
		}
		
		private String getExp()
		{
			StringBuilder sb = new StringBuilder();
			
			if (preexp != null)
			{
				sb.append(preexp.getName().getName());
				sb.append("(");
				separator = "";
				appendParamPatterns(sb, paramPatterns);
				appendStatePatterns(sb, stateDefinition, true, false);
				sb.append(")");
				sb.append(" =>\n");
			}

			if (res != null)
			{
				sb.append("exists ");
				separator = "";
				for(APatternTypePair r: res)
				{
					appendResult(sb, r);
				}
				appendStatePatterns(sb, stateDefinition, false, true);
				sb.append(" & ");
			}

//			sb.append(op.getPostdef().getName().name);
			sb.append("(");
			separator = "";
			appendParamPatterns(sb, paramPatterns);
			for(APatternTypePair r: res)
			{
				appendResultPattern(sb, r);
			}
			appendStatePatterns(sb, stateDefinition, true, false);
			appendStatePatterns(sb, stateDefinition, false, false);
			sb.append(")");

			return sb.toString();
		}
		
		
		private void appendResult(StringBuilder sb, APatternTypePair ptp)
		{
			if (ptp != null)
			{
				sb.append(separator);
				sb.append(ptp);
				separator = ", ";
			}
		}

		private void appendResultPattern(StringBuilder sb, APatternTypePair ptp)
		{
			if (ptp != null)
			{
				sb.append(separator);
				sb.append(ptp.getPattern());
				separator = ", ";
			}
		}

		private void appendStatePatterns(StringBuilder sb, PDefinition state,
				boolean old, boolean typed)
		{
			if (state == null)
			{
				return;
			} else if (state instanceof AStateDefinition)
			{
				if (old)
				{
					sb.append(separator);
					sb.append("oldstate");
				} else
				{
					sb.append(separator);
					sb.append("newstate");
				}

				if (typed)
				{
					AStateDefinition def = (AStateDefinition) state;
					sb.append(":");
					sb.append(def.getName().getName());
				}
			} else
			{
				if (old)
				{
					sb.append(separator);
					sb.append("oldself");
				} else
				{
					sb.append(separator);
					sb.append("newself");
				}

				if (typed)
				{
					SClassDefinition def = (SClassDefinition) state;
					sb.append(":");
					sb.append(def.getName().getName());
				}
			}

			separator = ", ";
		}

		private void appendParamPatterns(StringBuilder sb,
				List<APatternListTypePair> params)
		{
			for (APatternListTypePair pltp : params)
			{
//				List<PExp> expList = PPatternAssistantTC.getMatchingExpressionList(pltp.getPatterns());
//				sb.append(separator);
//				sb.append(Utils.listToString(expList));
//				separator = ", ";
			}
		}
	}