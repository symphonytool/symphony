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

import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.POType;
import org.overture.pog.obligation.ProofObligation;

import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;

public class SatisfiabilityObligation extends ProofObligation {
	private String separator = "";

	public SatisfiabilityObligation(AImplicitCmlOperationDefinition op,
			PDefinition stateDefinition, POContextStack ctxt) {
		super(((PDefinition )op).getLocation(), POType.OP_SATISFIABILITY, ctxt);
		StringBuilder sb = new StringBuilder();

		if (op.getPredef() != null) {
			sb.append(op.getPredef().getName().getName());
			sb.append("(");
			separator = "";
			appendParamPatterns(sb, op.getParameterPatterns());
			appendStatePatterns(sb, stateDefinition, true, false);
			sb.append(")");
			sb.append(" =>\n");
		}

		if (op.getResult() != null) {
			sb.append("exists ");
			separator = "";
			for (APatternTypePair r : op.getResult()) {
				appendResult(sb, r);
			}
			appendStatePatterns(sb, stateDefinition, false, true);
			sb.append(" & ");
		}
		sb.append("true");
		//FIXME getPostdef() is null...
//		sb.append(op.getPostdef().getName().getName());
//		sb.append("(");
//		separator = "";
//		appendParamPatterns(sb, op.getParameterPatterns());
//		for (APatternTypePair r : op.getResult()) {
//			appendResultPattern(sb, r);
//		}
//		appendStatePatterns(sb, stateDefinition, true, false);
//		appendStatePatterns(sb, stateDefinition, false, false);
//		sb.append(")");

		value = ctxt.getObligation(sb.toString());
	}

	private void appendResult(StringBuilder sb, APatternTypePair ptp) {
		if (ptp != null) {
			sb.append(separator);
			sb.append(ptp);
			separator = ", ";
		}
	}

	private void appendResultPattern(StringBuilder sb, APatternTypePair ptp) {
		if (ptp != null) {
			sb.append(separator);
			sb.append(ptp.getPattern());
			separator = ", ";
		}
	}

	private void appendStatePatterns(StringBuilder sb, PDefinition state,
			boolean old, boolean typed) {
		if (state == null) {
			return;
		} else if (state instanceof AStateDefinition) {
			if (old) {
				sb.append(separator);
				sb.append("oldstate");
			} else {
				sb.append(separator);
				sb.append("newstate");
			}

			if (typed) {
				AStateDefinition def = (AStateDefinition) state;
				sb.append(":");
				sb.append(def.getName().getName());
			}
		} else {
			if (old) {
				sb.append(separator);
				sb.append("oldself");
			} else {
				sb.append(separator);
				sb.append("newself");
			}

			if (typed) {
				SClassDefinition def = (SClassDefinition) state;
				sb.append(":");
				sb.append(def.getName().getName());
			}
		}

		separator = ", ";
	}

	private void appendParamPatterns(StringBuilder sb,
			List<APatternListTypePair> params) {
		for (APatternListTypePair pltp : params) {
			// List<PExp> expList =
			// PPatternAssistantTC.getMatchingExpressionList(pltp.getPatterns());
			// sb.append(separator);
			// sb.append(Utils.listToString(expList));
			// separator = ", ";
		}
	}
}