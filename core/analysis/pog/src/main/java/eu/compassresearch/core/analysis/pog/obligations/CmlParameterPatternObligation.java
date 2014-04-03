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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AExistsExp;
import org.overture.ast.expressions.AForAllExp;
import org.overture.ast.expressions.AImpliesBooleanBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexKeywordToken;
import org.overture.ast.lex.VDMToken;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.PType;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.pub.IPogAssistantFactory;

public class CmlParameterPatternObligation extends CmlProofObligation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CmlParameterPatternObligation(IPogAssistantFactory af,
		AExplicitOperationDefinition def, IPOContextStack ctxt) throws AnalysisException
	{
		super(def, CmlPOType.OPERATION_PATTERNS, ctxt, def.getLocation());
		valuetree.setPredicate(ctxt.getPredWithContext(generate(af,def.getPredef(), getParamPatternList(def), ((AOperationType) def.getType()).getParameters(), ((AOperationType) def.getType()).getResult())));

	}

	public CmlParameterPatternObligation(IPogAssistantFactory af,
		AImplicitOperationDefinition def, IPOContextStack ctxt) throws AnalysisException
	{
		super(def, CmlPOType.OPERATION_PATTERNS, ctxt, def.getLocation());
		valuetree.setPredicate(ctxt.getPredWithContext(generate(af,def.getPredef(), getListParamPatternList(def), ((AOperationType) def.getType()).getParameters(), ((AOperationType) def.getType()).getResult())));

	}
	
	public static List<List<PPattern>> getParamPatternList(AExplicitOperationDefinition func) {
		List<List<PPattern>> parameters = new ArrayList<List<PPattern>>();
		List<PPattern> plist = new ArrayList<PPattern>();

		for (PPattern p: func.getParameterPatterns())
		{
			plist.add(p);
		}

		parameters.add(plist);
		return parameters;
	}
	
	public static List<List<PPattern>> getListParamPatternList(AImplicitOperationDefinition func) {
		List<List<PPattern>> parameters = new ArrayList<List<PPattern>>();
		List<PPattern> plist = new ArrayList<PPattern>();

		for (APatternListTypePair pl: func.getParameterPatterns())
		{
			plist.addAll(pl.getPatterns());
		}

		parameters.add(plist);
		return parameters;
	}
	
	
	private PExp generate(IPogAssistantFactory af, PDefinition predef, List<List<PPattern>> plist, List<PType> params, PType result) throws AnalysisException
	{
		AForAllExp forallExp = new AForAllExp();
		List<PMultipleBind> forallBindList = new Vector<PMultipleBind>();
		List<PExp> arglist = new Vector<PExp>();
		PExp forallPredicate = null;

		for (List<PPattern> paramList: plist)
		{
			Iterator<PType> titer = params.iterator();
			
			if (!paramList.isEmpty())
			{
				AExistsExp existsExp = new AExistsExp();
				List<PMultipleBind> existsBindList = new Vector<PMultipleBind>();
				PExp existsPredicate = null;
				
				Set<ILexNameToken> previousBindings = new HashSet<ILexNameToken>();
	
				for (PPattern param: paramList)
				{
					ILexNameToken aname = getUnique("arg");
					ILexNameToken bname = getUnique("bind");
						
					PType atype = titer.next();
					PExp pmatch = patternToExp(param);
					arglist.add(pmatch.clone());
					
					forallBindList.add(getMultipleTypeBind(atype, aname));
					existsBindList.add(getMultipleTypeBind(atype, bname));

					for (PDefinition def: af.createPPatternAssistant().getDefinitions(param, atype, NameScope.LOCAL))
					{
						if (def.getName() != null && !previousBindings.contains(def.getName()))
						{
							existsBindList.add(getMultipleTypeBind(def.getType(), def.getName()));
							previousBindings.add(def.getName());
						}
					}
	
					AEqualsBinaryExp eq1 = getEqualsExp(getVarExp(aname), getVarExp(bname));
					AEqualsBinaryExp eq2 = getEqualsExp(pmatch, getVarExp(bname));
					existsPredicate = makeAnd(existsPredicate, makeAnd(eq1, eq2));
				}
				
				existsExp.setBindList(existsBindList);
				existsExp.setPredicate(existsPredicate);
				
				forallPredicate = makeAnd(forallPredicate, existsExp);
			}

			if (result instanceof AFunctionType)
			{
				AFunctionType ft = (AFunctionType)result;
				result = ft.getResult();
				params = ft.getParameters();
			}
			else
			{
				break;
			}
		}
		
		forallExp.setBindList(forallBindList);
		
		if (predef != null)
		{
			AImpliesBooleanBinaryExp implies = new AImpliesBooleanBinaryExp();
			implies.setLeft(getApplyExp(getVarExp(predef.getName()), arglist));
			implies.setOp(new LexKeywordToken(VDMToken.IMPLIES, null));
			implies.setRight(forallPredicate);
			forallExp.setPredicate(implies);
		}
		else
		{
			forallExp.setPredicate(forallPredicate);
		}

		return forallExp;
	}
}
