package eu.compassresearch.core.typechecker.version2;

//import static eu.compassresearch.core.typechecker.util.CmlTCUtil.successfulType;

//import static eu.compassresearch.core.typechecker.util.CmlTCUtil.successfulType;

//import static eu.compassresearch.core.typechecker.util.CmlTCUtil.successfulType;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.types.AChansetType;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

public class CmlChannelTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionAnswerAdaptor<TypeCheckInfo, PType> tc;
	private final ITypeIssueHandler issueHandler;// = VanillaFactory.newCollectingIssueHandle();

	@SuppressWarnings("deprecation")
	public CmlChannelTypeChecker(
			QuestionAnswerAdaptor<TypeCheckInfo, PType> tc2,
			IQuestionAnswer<TypeCheckInfo, PType> root,
			ITypeIssueHandler issueHandler)
	{
		super(root);
		this.tc = tc2;
		this.issueHandler = issueHandler;
	}

	@Override
	public PType caseAChannelDefinition(AChannelDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{


//		PType declType = decl.apply(THIS, question);
//
//		List<PDefinition> typeDefs = new LinkedList<PDefinition>();
//		for (PDefinition def : declType.getDefinitions())
//		{
//			typeDefs.add(def);
//			def.setType(decl.getType());
//		}
//
//		node.getType().getDefinitions().addAll(typeDefs);
		
//		question.assistantFactory.createPTypeAssistant().typeResolve(node.getType(),null,THIS,question);
		
		return node.getType();
	}

	@Override
	public PType caseAChansetDefinition(AChansetDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{
		PVarsetExpression chansetExp = node.getChansetExpression();

		PType chansetExpType = chansetExp.apply(THIS, question);
		// CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		// cmlEnv.addChannel(node.getIdentifier(), node);

		AChansetType res = new AChansetType(node.getLocation(), true);
		node.setType(res);
		return node.getType();
	}

	@Override
	public PType createNewReturnValue(INode node, TypeCheckInfo question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PType createNewReturnValue(Object node, TypeCheckInfo question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
