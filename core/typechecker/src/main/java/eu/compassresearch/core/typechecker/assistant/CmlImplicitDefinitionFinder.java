package eu.compassresearch.core.typechecker.assistant;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.APostOpExp;
import org.overture.ast.expressions.APreOpExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AActionStm;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AVoidType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.PAccessSpecifierAssistantTC;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;
import org.overture.typechecker.assistant.type.AOperationTypeAssistantTC;

import eu.compassresearch.ast.definitions.AProcessDefinition;

public class CmlImplicitDefinitionFinder extends
		AbstractCmlImplicitDefinitionFinder
{

	public CmlImplicitDefinitionFinder(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	@Override
//	protected AStateDefinition findStateDefinition(Environment question,INode node)
//	{
//		return findDummyStateDefinition(node);
//	}

//	@Override
//	public void caseAImplicitCmlOperationDefinition(
//			AImplicitCmlOperationDefinition node, Environment question)
//			throws AnalysisException
//	{
//		// FIXME node.setState(question.findStateDefinition());
//		node.setState(findDummyStateDefinition(node));
//
//		if (node.getPrecondition() != null)
//		{
//			node.setPredef(getPreDefinition(node, question));
//			PDefinitionAssistantTC.markUsed(node.getPredef());
//		}
//
//		if (node.getPostcondition() != null)
//		{
//			node.setPostdef(getPostDefinition(node, question));
//			PDefinitionAssistantTC.markUsed(node.getPostdef());
//		}
//	}
//
	private static AStateDefinition findDummyStateDefinition(INode node)
	{

		INode parent = null;

		while ((parent = node.parent()) != null)
		{

			ILexNameToken name = null;
			if (parent instanceof SClassDefinition)
			{
				name = ((SClassDefinition) parent).getName();
			} else if (parent instanceof AProcessDefinition)
			{
				name = ((AProcessDefinition) parent).getName();
			}

			if (name != null)
			{
				return AstFactory.newAStateDefinition(name, new Vector<AFieldField>(), null, null, null, null);
			}
			node = parent;
		}
		return null;

	}
//
//	@Override
//	public void caseAExplicitCmlOperationDefinition(
//			AExplicitCmlOperationDefinition node, Environment question)
//			throws AnalysisException
//	{
//		// FIXME node.setState(question.findStateDefinition());
//		node.setState(findDummyStateDefinition(node));
//
//		if (node.getPrecondition() != null)
//		{
//			node.setPredef(getPreDefinition(node, question));
//			PDefinitionAssistantTC.markUsed(node.getPredef()); // ORIGINAL CODE
//
//		}
//
//		if (node.getPostcondition() != null)
//		{
//			node.setPostdef(getPostDefinition(node, question));
//			PDefinitionAssistantTC.markUsed(node.getPostdef());
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public static AExplicitFunctionDefinition getPostDefinition(
//			AImplicitCmlOperationDefinition d, Environment base)
//	{
//
//		List<List<PPattern>> parameters = new Vector<List<PPattern>>();
//		List<PPattern> plist = new Vector<PPattern>();
//
//		for (APatternListTypePair pl : (LinkedList<APatternListTypePair>) d.getParameterPatterns())
//		{
//			plist.addAll((Collection<PPattern>) pl.getPatterns().clone());
//		}
//
//		if (d.getResult() != null)
//		{
//			plist.add(d.getResult().getPattern().clone());
//		}
//
//		AStateDefinition state = d.getState();
//
//		if (state != null)
//		{
//			plist.add(AstFactory.newAIdentifierPattern(state.getName().getOldName()));
//			plist.add(AstFactory.newAIdentifierPattern(state.getName().clone()));
//		} else if (base.isVDMPP()
//				&& !PAccessSpecifierAssistantTC.isStatic(d.getAccess()))
//		{
//			plist.add(AstFactory.newAIdentifierPattern(d.getName().getSelfName().getOldName()));
//			plist.add(AstFactory.newAIdentifierPattern(d.getName().getSelfName()));
//		}
//
//		parameters.add(plist);
//		PExp postop = AstFactory.newAPostOpExp(d.getName().clone(), d.getPrecondition(), d.getPostcondition(), d.getErrors(), d.getState());
//
//		AExplicitFunctionDefinition def = AstFactory.newAExplicitFunctionDefinition(d.getName().getPostName(d.getPostcondition().getLocation()), NameScope.GLOBAL, null, AOperationTypeAssistantTC.getPostType((AOperationType) d.getType(), state, d.getClassDefinition(), PAccessSpecifierAssistantTC.isStatic(d.getAccess())), parameters, postop, null, null, false, null);
//
//		// Operation postcondition functions are effectively not static as
//		// their expression can directly refer to instance variables, even
//		// though at runtime these are passed via a "self" parameter.
//
//		def.setAccess(PAccessSpecifierAssistantTC.getStatic(d, false));
//		def.setClassDefinition(d.getClassDefinition());
//		return def;
//	}
//
//	@SuppressWarnings("unchecked")
//	public static AExplicitFunctionDefinition getPreDefinition(
//			AImplicitCmlOperationDefinition d, Environment base)
//	{
//
//		List<List<PPattern>> parameters = new Vector<List<PPattern>>();
//		List<PPattern> plist = new Vector<PPattern>();
//
//		for (APatternListTypePair pl : (LinkedList<APatternListTypePair>) d.getParameterPatterns())
//		{
//			plist.addAll((Collection<PPattern>) pl.getPatterns().clone());
//		}
//
//		AStateDefinition state = d.getState();
//
//		if (state != null)
//		{
//			plist.add(AstFactory.newAIdentifierPattern(state.getName().clone()));
//		} else if (base.isVDMPP()
//				&& !PAccessSpecifierAssistantTC.isStatic(d.getAccess()))
//		{
//			plist.add(AstFactory.newAIdentifierPattern(d.getName().getSelfName()));
//		}
//
//		parameters.add(plist);
//		PExp preop = AstFactory.newAPreOpExp(d.getName().clone(), d.getPrecondition(), d.getErrors(), d.getState());
//
//		AExplicitFunctionDefinition def = AstFactory.newAExplicitFunctionDefinition(d.getName().getPreName(d.getPrecondition().getLocation()), NameScope.GLOBAL, null, AOperationTypeAssistantTC.getPreType((AOperationType) d.getType(), state, d.getClassDefinition(), PAccessSpecifierAssistantTC.isStatic(d.getAccess())), parameters, preop, null, null, false, null);
//
//		// Operation precondition functions are effectively not static as
//		// their expression can directly refer to instance variables, even
//		// though at runtime these are passed via a "self" parameter.
//
//		def.setAccess(PAccessSpecifierAssistantTC.getStatic(d, false));
//		def.setClassDefinition(d.getClassDefinition());
//		return def;
//	}
//
//	@SuppressWarnings("unchecked")
//	public static AExplicitFunctionDefinition getPreDefinition(
//			AExplicitCmlOperationDefinition d, Environment base)
//	{
//
//		List<List<PPattern>> parameters = new Vector<List<PPattern>>();
//		List<PPattern> plist = new Vector<PPattern>();
//		plist.addAll((List<PPattern>) d.getParameterPatterns().clone());
//
//		if (d.getState() != null)
//		{
//			plist.add(AstFactory.newAIdentifierPattern(d.getState().getName().clone()));
//		} else if (base.isVDMPP()
//				&& !PAccessSpecifierAssistantTC.isStatic(d.getAccess()))
//		{
//			plist.add(AstFactory.newAIdentifierPattern(d.getName().getSelfName()));
//		}
//
//		parameters.add(plist);
//		APreOpExp preop = AstFactory.newAPreOpExp(d.getName().clone(), d.getPrecondition(), null, d.getState());
//
//		AExplicitFunctionDefinition def = AstFactory.newAExplicitFunctionDefinition(d.getName().getPreName(d.getPrecondition().getLocation()), NameScope.GLOBAL, null, AOperationTypeAssistantTC.getPreType((AOperationType) d.getType(), d.getState(), d.getClassDefinition(), PAccessSpecifierAssistantTC.isStatic(d.getAccess())), parameters, preop, null, null, false, null);
//		// new AExplicitFunctionDefinition(d.getPrecondition().getLocation(),
//		// d.getName().getPreName(d.getPrecondition().getLocation()),
//		// NameScope.GLOBAL, false, null, PAccessSpecifierAssistant.getDefault(),
//		// null, parameters, AOperationTypeAssistantTC.getPreType(d.getType(),d.getState(), d.getClassDefinition(),
//		// PAccessSpecifierAssistantTC.isStatic(d.getAccess())),
//		// preop, null, null, null, null, null, null, null, false, false, null, null, null, null, parameters.size() > 1,
//		// null);
//
//		// ;
//
//		// Operation precondition functions are effectively not static as
//		// their expression can directly refer to instance variables, even
//		// though at runtime these are passed via a "self" parameter.
//
//		def.setAccess(PAccessSpecifierAssistantTC.getStatic(def, false));
//		def.setClassDefinition(def.getClassDefinition());
//		return def;
//	}
//
//	@SuppressWarnings("unchecked")
//	public static AExplicitFunctionDefinition getPostDefinition(
//			AExplicitCmlOperationDefinition d, Environment base)
//	{
//
//		List<List<PPattern>> parameters = new Vector<List<PPattern>>();
//		List<PPattern> plist = new Vector<PPattern>();
//		plist.addAll((List<PPattern>) d.getParameterPatterns().clone());
//
//		if (!(((AOperationType) d.getType()).getResult() instanceof AVoidType))
//		{
//			LexNameToken result = new LexNameToken(d.getName().getModule(), "RESULT", d.getLocation());
//			plist.add(AstFactory.newAIdentifierPattern(result));
//		}
//
//		AStateDefinition state = d.getState();
//
//		if (state != null) // Two args, called Sigma~ and Sigma
//		{
//			plist.add(AstFactory.newAIdentifierPattern(state.getName().getOldName()));
//			plist.add(AstFactory.newAIdentifierPattern(state.getName().clone()));
//		} else if (base.isVDMPP()
//				&& !PAccessSpecifierAssistantTC.isStatic(d.getAccess()))
//		{
//			// Two arguments called "self~" and "self"
//			plist.add(AstFactory.newAIdentifierPattern(d.getName().getSelfName().getOldName()));
//			plist.add(AstFactory.newAIdentifierPattern(d.getName().getSelfName()));
//		}
//
//		parameters.add(plist);
//		APostOpExp postop = AstFactory.newAPostOpExp(d.getName().clone(), d.getPrecondition(), d.getPostcondition(), null, d.getState());
//
//		AExplicitFunctionDefinition def = AstFactory.newAExplicitFunctionDefinition(d.getName().getPostName(d.getPostcondition().getLocation()), NameScope.GLOBAL, null, AOperationTypeAssistantTC.getPostType((AOperationType) d.getType(), state, d.getClassDefinition(), PAccessSpecifierAssistantTC.isStatic(d.getAccess())), parameters, postop, null, null, false, null);
//
//		// new AExplicitFunctionDefinition(d.getLocation(),
//		// d.getName().getPostName(d.getPostcondition().getLocation()),
//		// NameScope.GLOBAL, false,
//		// null, PAccessSpecifierAssistant.getDefault(),
//		// null, parameters,
//		// AOperationTypeAssistantTC.getPostType(d.getType(),state, d.getClassDefinition(),
//		// PAccessSpecifierAssistantTC.isStatic(d.getAccess())),
//		// postop, null, null, null, null, null, null,
//		// null, false, false, null, null, null, null, parameters.size() > 1, null);
//
//		// Operation postcondition functions are effectively not static as
//		// their expression can directly refer to instance variables, even
//		// though at runtime these are passed via a "self" parameter.
//
//		def.setAccess(PAccessSpecifierAssistantTC.getStatic(d, false));
//		def.setClassDefinition(d.getClassDefinition());
//		return def;
//
//	}

	

}
