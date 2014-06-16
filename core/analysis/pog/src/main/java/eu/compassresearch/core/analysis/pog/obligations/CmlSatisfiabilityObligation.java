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
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AAndBooleanBinaryExp;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AExistsExp;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstExpressionFactory;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexKeywordToken;
import org.overture.ast.lex.VDMToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.statements.AExternalClause;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.pub.IPogAssistantFactory;
import org.overture.pog.utility.Substitution;

public class CmlSatisfiabilityObligation extends CmlProofObligation
{

	private static final long serialVersionUID = 1L;

	private IPogAssistantFactory af;

	// FIXME collect only the frame variables
	public CmlSatisfiabilityObligation(AImplicitOperationDefinition op,
			List<AInstanceVariableDefinition> procState,
			List<PDefinition> invDefs, IPOContextStack ctxt,
			IPogAssistantFactory af) throws AnalysisException
	{
		super(op, CmlPOType.OP_SATISFIABILITY, ctxt, op.getLocation());
		this.af = af;
		PExp predExp = buildPredicate(op, null, procState, invDefs);
		valuetree.setPredicate(ctxt.getPredWithContext(predExp));
	}

	public CmlSatisfiabilityObligation(AImplicitOperationDefinition op,
			PDefinition stateDefinition, IPOContextStack ctxt,
			IPogAssistantFactory af) throws AnalysisException
	{
		super(op, CmlPOType.OP_SATISFIABILITY, ctxt, op.getLocation());
		this.af = af;

		/**
		 * op: A * B ==> R [pre ...] post ... [pre_op(a, b, state) =>] exists r:R, state:Sigma & post_op(a, b, r,
		 * state~, state) The state argument is either a Sigma(SL) or self(PP).
		 */

		PExp predExp = buildPredicate(op, stateDefinition, null, null);

		valuetree.setPredicate(ctxt.getPredWithContext(predExp));
		// valuetree.setContext(ctxt.getContextNodeList());
	}

	PExp buildPredicate(AImplicitOperationDefinition op,
			PDefinition stateDefinition,
			List<AInstanceVariableDefinition> procState,
			List<PDefinition> invDefs) throws AnalysisException
	{

		PExp preApply = null;
		PExp postApply = null;

		postApply = op.getPostcondition().clone();
		if (invDefs.size() > 0)
		{
			for (PDefinition d : invDefs)
			{
				if (d instanceof AClassInvariantDefinition)
				{
					AClassInvariantDefinition i = (AClassInvariantDefinition) d;
					postApply = AstExpressionFactory.newAAndBooleanBinaryExp(postApply.clone(), i.getExpression().clone());
				}
			}
		}

		if (op.getPredef() != null)
		{
			preApply = op.getPrecondition().clone();
		}

		PExp mainExp;

		// Operation Has a Result. Add it in the post condition.
		if (op.getResult() != null)
		{

			AExistsExp existsExp = new AExistsExp();
			List<PExp> postArglist = new Vector<PExp>();

			APatternTypePair res = op.getResult();

			// *****Making assumption result should be single identifier pattern*****
			if (res.getPattern() instanceof AIdentifierPattern)
			{
				AIdentifierPattern ip = (AIdentifierPattern) res.getPattern();
				postArglist.add(patternToExp(res.getPattern()));

				List<PMultipleBind> exists_binds = new LinkedList<PMultipleBind>();
				postApply = stateInPost(procState, postApply, exists_binds, op);

				exists_binds.add(getMultipleTypeBind(res.getType(), ip.getName()));

				existsExp.setBindList(exists_binds);
			} else
			{
				throw new RuntimeException("Expecting single identifier pattern in operation result");
			}

			existsExp.setPredicate(postApply);
			mainExp = existsExp;
		}

		// No Result. Just add new state to post condition
		else
		{

			AExistsExp exists_exp = new AExistsExp();

			List<PMultipleBind> exists_binds = new LinkedList<PMultipleBind>();
			postApply = stateInPost(procState, postApply, exists_binds, op);

			exists_exp.setBindList(exists_binds);
			exists_exp.setPredicate(postApply);
			mainExp = exists_exp;
		}

		if (preApply != null)
		{
			AImpliesBooleanBinaryExp implies = new AImpliesBooleanBinaryExp();
			implies.setLeft(preApply);
			implies.setOp(new LexKeywordToken(VDMToken.IMPLIES, null));
			implies.setRight(mainExp);
			return implies;
		} else
		{
			return mainExp;
		}
	}

	PExp stateInPost(List<AInstanceVariableDefinition> procState,
			PExp post_exp, List<PMultipleBind> exists_binds,
			AImplicitOperationDefinition op) throws AnalysisException
	{
		if (procState != null)
		{
			if (op.getExternals().size() > 0)
			{
				for (AInstanceVariableDefinition var : procState)
				{
					for (AExternalClause e : op.getExternals())
					{
						for (ILexNameToken i : e.getIdentifiers())
						{
							if (i.equals(var.getName()))
							{
								AVariableExp newVar = getVarExp(getUnique(var.getName().getName()));
								Substitution sub = new Substitution(var.getName().clone(), newVar);
								post_exp = post_exp.apply(af.getVarSubVisitor(), sub);

								if (e.getMode().is(VDMToken.READ))
								{ // x~ = x
									AEqualsBinaryExp unchanged_exp = AstExpressionFactory.newAEqualsBinaryExp(getVarExp(var.getName().clone()), newVar.clone());
									AAndBooleanBinaryExp and_exp = AstExpressionFactory.newAAndBooleanBinaryExp(post_exp, unchanged_exp);
									post_exp = and_exp;
								}
								PMultipleBind pmb = getMultipleTypeBind(var.getType().clone(), newVar.getName().clone());
								exists_binds.add(pmb);
							}
						}
					}
				}
			} else
			{

				for (AInstanceVariableDefinition var : procState)
				{
					AVariableExp newVar = getVarExp(getUnique(var.getName().getName()));
					Substitution sub = new Substitution(var.getName().clone(), newVar);
					post_exp = post_exp.apply(af.getVarSubVisitor(), sub);
					PMultipleBind pmb = getMultipleTypeBind(var.getType().clone(), newVar.getName().clone());
					exists_binds.add(pmb);
				}
			}
		}
		return post_exp;
	}

}