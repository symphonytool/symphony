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
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AApplyExp;
import org.overture.ast.expressions.AExistsExp;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexKeywordToken;
import org.overture.ast.lex.LexNameToken; //OR FROM COMPASS?
import org.overture.ast.lex.VDMToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.pog.pub.IPOContextStack;

import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;

public class CmlSatisfiabilityObligation extends CmlProofObligation
{
	// private final AExplicitFunctionDefinition preexp;
	// private final LinkedList<APatternListTypePair> paramPatterns;
	// private final PDefinition stateDefinition;
	// private final LinkedList<APatternTypePair> res;
	// private final AExplicitFunctionDefinition postexp;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ILexNameToken OLD_STATE_ARG = new LexNameToken(null, "oldstate", null);
	private static final ILexNameToken OLD_SELF_ARG = new LexNameToken(null, "oldself", null);
	private static final ILexNameToken NEW_STATE_ARG = new LexNameToken(null, "newstate", null);
	private static final ILexNameToken NEW_SELF_ARG = new LexNameToken(null, "newself", null);

	public CmlSatisfiabilityObligation(AImplicitCmlOperationDefinition op,
			PDefinition stateDefinition, IPOContextStack ctxt)
			throws AnalysisException
	{
		super(op, CmlPOType.OP_SATISFIABILITY, ctxt, op.getLocation());

		/**
		 * op: A * B ==> R [pre ...] post ... [pre_op(a, b, state) =>] exists r:R, state:Sigma & post_op(a, b, r,
		 * state~, state) The state argument is either a Sigma(SL) or self(PP).
		 */

		List<PExp> arglist = new Vector<PExp>();

		for (APatternListTypePair pltp : op.getParameterPatterns())
		{
			for (PPattern pattern : pltp.getPatterns())
			{
				arglist.add(patternToExp(pattern));
			}
		}

		if (stateDefinition instanceof AStateDefinition)
		{
			arglist.add(getVarExp(OLD_STATE_ARG));
		} else
		{
			arglist.add(getVarExp(OLD_SELF_ARG));
		}

		AApplyExp preApply = null;

		if (op.getPredef() != null)
		{
			preApply = getApplyExp(getVarExp(op.getPredef().getName()), arglist);
		}

		PExp mainExp;

		// Operation Has a Result. Add it in the post condition.
		if (op.getResult() != null && !op.getResult().isEmpty())
		{

			AExistsExp existsExp = new AExistsExp();
			List<PExp> postArglist = new Vector<PExp>(arglist);

			LinkedList<APatternTypePair> res = op.getResult();

			// *****Making assumption result should be single identifier pattern*****
			if (res.size() == 1
					& res.getFirst().getPattern() instanceof AIdentifierPattern)
			{
				AIdentifierPattern ip = (AIdentifierPattern) res.getFirst().getPattern();
				postArglist.add(patternToExp(res.getFirst().getPattern()));

				List<PMultipleBind> exists_binds;

				if (stateDefinition instanceof AStateDefinition)
				{
					postArglist.add(getVarExp(NEW_STATE_ARG));
					exists_binds = getMultipleTypeBindList(stateDefinition.getType(), NEW_STATE_ARG);
				} else
				{
					postArglist.add(getVarExp(NEW_SELF_ARG));
					exists_binds = getMultipleTypeBindList(stateDefinition.getType(), NEW_SELF_ARG);
				}
				exists_binds.add(getMultipleTypeBind(res.getFirst().getType(), ip.getName()));

				existsExp.setBindList(exists_binds);
			} else
			{
				throw new RuntimeException("Expecting single identifier pattern in operation result");
			}

			AApplyExp postApply = getApplyExp(getVarExp(op.getPostdef().getName()), postArglist);
			existsExp.setPredicate(postApply);
			mainExp = existsExp;
		}

		// No Result. Just add new state to post condition
		else
		{

			AExistsExp exists_exp = new AExistsExp();
			List<PExp> postArglist = new Vector<PExp>(arglist);
			if (stateDefinition instanceof AStateDefinition)
			{
				postArglist.add(getVarExp(NEW_STATE_ARG));
				exists_exp.setBindList(getMultipleTypeBindList(stateDefinition.getType(), NEW_STATE_ARG));
			} else
			{
				postArglist.add(getVarExp(NEW_SELF_ARG));
				exists_exp.setBindList(getMultipleTypeBindList(stateDefinition.getType(), NEW_SELF_ARG));
			}

			exists_exp.setPredicate(getApplyExp(getVarExp(op.getPostdef().getName()), new Vector<PExp>(postArglist)));
			mainExp = exists_exp;
		}

		if (preApply != null)
		{
			AImpliesBooleanBinaryExp implies = new AImpliesBooleanBinaryExp();
			implies.setLeft(preApply);
			implies.setOp(new LexKeywordToken(VDMToken.IMPLIES, null));
			implies.setRight(mainExp);
			valuetree.setPredicate(ctxt.getPredWithContext(implies));
		} else
		{
			valuetree.setPredicate(ctxt.getPredWithContext(mainExp));
		}

		// valuetree.setContext(ctxt.getContextNodeList());
	}

}