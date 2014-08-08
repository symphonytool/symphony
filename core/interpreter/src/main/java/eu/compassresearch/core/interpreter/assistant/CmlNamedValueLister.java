package eu.compassresearch.core.interpreter.assistant;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.interpreter.CmlContextFactory;
import eu.compassresearch.core.interpreter.CmlExpressionVisitor;
import eu.compassresearch.core.interpreter.NamespaceUtility;
import eu.compassresearch.core.interpreter.api.values.ActionValue;
import eu.compassresearch.core.interpreter.api.values.CmlChannel;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;

public class CmlNamedValueLister extends AbstractNameValueLister
{

	public CmlNamedValueLister(IInterpreterAssistantFactory af)
	{
		super(af);
	}

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

	/*
	 * Visitor Definition case methods
	 */

	@Override
	public NameValuePairList caseAProcessDefinition(AProcessDefinition node,
			Context question) throws AnalysisException
	{
		NameValuePairList vpl = new NameValuePairList();
		vpl.add(new NameValuePair(node.getName(), new ProcessObjectValue(question.assistantFactory, node, null)));
		return vpl;
	}

	@Override
	public NameValuePairList caseAStateDefinition(AStateDefinition node,
			Context question) throws AnalysisException
	{
		return definitionListHelper(node.getStateDefs(), node.getLocation(), question);
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
		// FIXME not sure what this if is about, but the rest is duplicated
		if (node.parent() instanceof ATypeDefinition)
		{
			node.setIsTypeInvariant(true);
		} else
		{
			node.setIsTypeInvariant(false);
		}

		NameValuePairList list = super.caseAExplicitFunctionDefinition(node, question);

		return duplicateNamesWithModuleSet(list, node);
	}

	/**
	 * Since CML in places has a slidly different way of representing names. i.e. it actually dont have static stuff. We
	 * need to handle this since VDM requires static (functions) to have the module set for function-to-function calls
	 * but not for op-to-fun calls so we add both.
	 * 
	 * @param list
	 * @param node
	 * @return
	 */
	private static NameValuePairList duplicateNamesWithModuleSet(
			NameValuePairList list, INode node)
	{
		NameValuePairList dupList = new NameValuePairList();
		dupList.addAll(list);

		if (!list.isEmpty())
		{
			for (NameValuePair nvp : list)
			{
				if (nvp.name.getModule().isEmpty())
				{
					SClassDefinition def = node.getAncestor(SClassDefinition.class);
					if (def != null)
					{
						CmlLexNameToken name2 = new CmlLexNameToken(def.getName().getName(), nvp.name.getName(), nvp.name.getLocation(), nvp.name.isOld(), nvp.name.getExplicit());
						name2.setTypeQualifier(nvp.name.getTypeQualifier());
						dupList.add(new NameValuePair(name2, nvp.value));
					}
				}
			}
		}

		return dupList;
	}

	@Override
	public NameValuePairList caseAChannelDefinition(AChannelDefinition node,
			Context question) throws AnalysisException
	{
		NameValuePairList vpl = new NameValuePairList();
		ILexNameToken name = NamespaceUtility.createChannelName(node.getName());
		vpl.add(new NameValuePair(name, new CmlChannel(node.getType(), name)));
		return vpl;
	}

	/*
	 * Types
	 */

	@Override
	public NameValuePairList caseATypeDefinition(ATypeDefinition node,
			Context question) throws AnalysisException
	{
		// FIXME this is differnt a new function value is created in overture
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
		// fixme the overture version is different and handled more than idenfitiers, something with constsntized
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
}
