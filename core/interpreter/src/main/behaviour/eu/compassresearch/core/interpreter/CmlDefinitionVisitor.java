package eu.compassresearch.core.interpreter;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.interpreter.assistant.definition.PDefinitionAssistantInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.interpreter.api.values.ActionValue;
import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;

class CmlDefinitionVisitor extends
		QuestionAnswerCMLAdaptor<Context, NameValuePairList>
{

	private QuestionAnswerCMLAdaptor<Context, Value> cmlExpressionVisitor = new CmlExpressionVisitor();

	/*
	 * Private helper methods
	 */

	/**
	 * Generic method for handling a list of definitions
	 * 
	 * @param defs
	 * @param location
	 * @param question
	 * @return
	 * @throws AnalysisException
	 */
	private NameValuePairList definitionListHelper(
			List<? extends PDefinition> defs, ILexLocation location,
			Context question) throws AnalysisException
	{
		NameValuePairList vpl = new NameValuePairList();
		Context defEvalContext = CmlContextFactory.newContext(location, "Definition Eval context", question);

		for (PDefinition def : defs)
		{
			NameValuePairList tmpNvl = def.apply(this, defEvalContext);
			defEvalContext.putList(tmpNvl);
			vpl.addAll(tmpNvl);
		}

		return vpl;
	}

	// /*
	// * Visitor parametrisation case methods
	// */
	//
	// @Override
	// public NameValuePairList caseAValParametrisation(AValParametrisation node,
	// Context question) throws AnalysisException {
	// // TODO Auto-generated method stub
	// return super.caseAValParametrisation(node, question);
	// }
	//
	// @Override
	// public NameValuePairList caseAVresParametrisation(
	// AVresParametrisation node, Context question)
	// throws AnalysisException {
	// // TODO Auto-generated method stub
	// return super.caseAVresParametrisation(node, question);
	// }
	//
	// @Override
	// public NameValuePairList caseAResParametrisation(AResParametrisation node,
	// Context question) throws AnalysisException {
	// // TODO Auto-generated method stub
	// return super.caseAResParametrisation(node, question);
	// }

	/*
	 * Visitor Definition case methods
	 */

	@Override
	public NameValuePairList caseAProcessDefinition(AProcessDefinition node,
			Context question) throws AnalysisException
	{
		NameValuePairList vpl = new NameValuePairList();
		vpl.add(new NameValuePair(node.getName(), new ProcessObjectValue(node, null)));
		return vpl;
	}

	@Override
	public NameValuePairList caseAStateDefinition(AStateDefinition node,
			Context question) throws AnalysisException
	{
		return definitionListHelper(node.getStateDefs(), node.getLocation(), question);
	}

	@Override
	public NameValuePairList caseAInstanceVariableDefinition(
			AInstanceVariableDefinition node, Context question)
			throws AnalysisException
	{
		return PDefinitionAssistantInterpreter.getNamedValues(node, question);
	}

	@Override
	public NameValuePairList caseAClassInvariantDefinition(
			AClassInvariantDefinition node, Context question)
			throws AnalysisException
	{
		return new NameValuePairList();
	}

	@Override
	public NameValuePairList caseAActionDefinition(AActionDefinition node,
			Context question) throws AnalysisException
	{

		NameValuePairList vpl = new NameValuePairList();
		vpl.add(new NameValuePair(node.getName(), new ActionValue(node)));
		return vpl;
	}

	@Override
	public NameValuePairList caseAClassClassDefinition(
			AClassClassDefinition node, Context question)
			throws AnalysisException
	{
		return new NameValuePairList();
	}

	@Override
	public NameValuePairList caseAChansetDefinition(AChansetDefinition node,
			Context question) throws AnalysisException
	{
		NameValuePairList nvpl = new NameValuePairList();
		ILexNameToken name = NamespaceUtility.createChansetName(node.getIdentifier());
		Value val = node.getChansetExpression().apply(cmlExpressionVisitor, question);
		nvpl.add(new NameValuePair(name, val));

		return nvpl;
	}

	/*
	 * Function
	 */

	@Override
	public NameValuePairList caseAExplicitFunctionDefinition(
			AExplicitFunctionDefinition node, Context question)
			throws AnalysisException
	{

		NameValuePairList vpl = new NameValuePairList();
		if (node.parent() instanceof ATypeDefinition)
		{
			node.setIsTypeInvariant(true);
		} else
		{
			node.setIsTypeInvariant(false);
		}

		// make the pre and post functions
		FunctionValue postFuncValue = null;
		FunctionValue preFuncValue = null;

		if (node.getPredef() != null)
		{
			preFuncValue = new FunctionValue(node.getPredef(), null, null, null);
		}
		if (node.getPostdef() != null)
		{
			postFuncValue = new FunctionValue(node.getPostdef(), null, null, null);
		}

		FunctionValue funcValue = new FunctionValue(node, preFuncValue, postFuncValue, null);

		vpl.add(new NameValuePair(node.getName(), funcValue));

		return vpl;
	}

	@Override
	public NameValuePairList caseAImplicitFunctionDefinition(
			AImplicitFunctionDefinition node, Context question)
			throws AnalysisException
	{

		// make the pre and post functions
		FunctionValue postFuncValue = null;
		FunctionValue preFuncValue = null;

		if (node.getPredef() != null)
		{
			preFuncValue = new FunctionValue(node.getPredef(), null, null, null);
		}
		if (node.getPostdef() != null)
		{
			postFuncValue = new FunctionValue(node.getPostdef(), null, null, null);
		}

		FunctionValue funcValue = new FunctionValue(node, preFuncValue, postFuncValue, null);
		NameValuePairList vpl = new NameValuePairList();
		vpl.add(new NameValuePair(node.getName(), funcValue));

		return vpl;
		// throw new AnalysisException("The function " + node.toString() +
		// " is implicit. This is not supported by the simulator at the moment");
	}

	/*
	 * Operations
	 */

	@Override
	public NameValuePairList caseAExplicitOperationDefinition(
			AExplicitOperationDefinition node, Context question)
			throws AnalysisException
	{

		// NameValuePairList vpl = new NameValuePairList();
		// vpl.add(new NameValuePair(node.getName().clone(), CmlValueFactory.createOperationValue(node, question)));

		return PDefinitionAssistantInterpreter.getNamedValues(node, question);
	}

	@Override
	public NameValuePairList caseAImplicitOperationDefinition(
			AImplicitOperationDefinition node, Context question)
			throws AnalysisException
	{

		// NameValuePairList vpl = new NameValuePairList();
		// vpl.add(new NameValuePair(node.getName(), CmlValueFactory.createOperationValue(node, question)));
		//
		// return vpl;
		return PDefinitionAssistantInterpreter.getNamedValues(node, question);
	}

	@Override
	public NameValuePairList caseAAssignmentDefinition(
			AAssignmentDefinition node, Context question)
			throws AnalysisException
	{

		NameValuePairList vpl = new NameValuePairList();

		Value expValue = null;
		if (node.getExpression() != null)
		{
			expValue = node.getExpression().apply(cmlExpressionVisitor, question);
		} else
		{
			expValue = new UndefinedValue();
		}

		vpl.add(new NameValuePair(node.getName(), expValue));

		return vpl;
	}

	// @Override
	// public NameValuePairList caseAChannelsDefinition(AChannelsDefinition node,
	// Context question) throws AnalysisException
	// {
	// NameValuePairList vpl = new NameValuePairList();
	//
	// for (AChannelDefinition cnd : node.getChannelDeclarations())
	// {
	// ILexNameToken name = NamespaceUtility.createChannelName(cnd.getName());
	// vpl.add(new NameValuePair(name, new CMLChannelValue(cnd.getType(), name)));
	// }
	//
	// return vpl;
	// }

	@Override
	public NameValuePairList caseAChannelDefinition(AChannelDefinition node,
			Context question) throws AnalysisException
	{
		NameValuePairList vpl = new NameValuePairList();
		ILexNameToken name = NamespaceUtility.createChannelName(node.getName());
		vpl.add(new NameValuePair(name, new CMLChannelValue(node.getType(), name)));
		return vpl;
	}

	/*
	 * Types
	 */

	@Override
	public NameValuePairList caseATypeDefinition(ATypeDefinition node,
			Context question) throws AnalysisException
	{

		NameValuePairList vpl = new NameValuePairList();
		if (node.getInvdef() != null)
		{
			vpl.addAll(node.getInvdef().apply(this, question));
		}

		return vpl;
	}

	/*
	 * Values
	 */

	@Override
	public NameValuePairList caseAValueDefinition(AValueDefinition node,
			Context question) throws AnalysisException
	{

		NameValuePairList vpl = new NameValuePairList();

		Value val = node.getExpression().apply(cmlExpressionVisitor, question);

		if (node.getPattern() instanceof AIdentifierPattern)
		{
			vpl.add(new NameValuePair(((AIdentifierPattern) node.getPattern()).getName(), val));
		}

		return vpl;
	}

	/*
	 * Replication declarations
	 */

	@Override
	public NameValuePairList caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node, Context question)
			throws AnalysisException
	{
		NameValuePairList vpl = new NameValuePairList();
		Value value = node.getExpression().apply(cmlExpressionVisitor, question);
		vpl.add(new NameValuePair(new CmlLexNameToken("", node.getIdentifier().clone()), value));

		return vpl;
	}

	@Override
	public NameValuePairList caseATypeSingleDeclaration(
			ATypeSingleDeclaration node, Context question)
			throws AnalysisException
	{
		NameValuePairList vpl = new NameValuePairList();
		Value value = new LatticeTopValue(node.getType());
		vpl.add(new NameValuePair(new CmlLexNameToken("", node.getIdentifier().clone()), value));

		return vpl;
	}

	@Override
	public NameValuePairList createNewReturnValue(INode node, Context question)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NameValuePairList createNewReturnValue(Object node, Context question)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
