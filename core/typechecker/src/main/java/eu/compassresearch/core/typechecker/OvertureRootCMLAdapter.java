//package eu.compassresearch.core.typechecker;
//
//import org.overture.ast.analysis.AnalysisException;
//import org.overture.ast.definitions.AAssignmentDefinition;
//import org.overture.ast.definitions.PDefinition;
//import org.overture.ast.expressions.AApplyExp;
//import org.overture.ast.expressions.ANilExp;
//import org.overture.ast.expressions.AVariableExp;
//import org.overture.ast.expressions.PExp;
//import org.overture.ast.node.INode;
//import org.overture.ast.patterns.PMultipleBind;
//import org.overture.ast.types.PType;
//import org.overture.typechecker.TypeCheckException;
//import org.overture.typechecker.TypeCheckInfo;
//import org.overture.typechecker.visitor.TypeCheckerDefinitionVisitor;
//import org.overture.typechecker.visitor.TypeCheckerExpVisitor;
//import org.overture.typechecker.visitor.TypeCheckerPatternVisitor;
//
//import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
//import eu.compassresearch.ast.expressions.ABracketedExp;
//import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
//import eu.compassresearch.ast.expressions.AEnumerationRenameChannelExp;
//import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
//import eu.compassresearch.core.typechecker.api.ICmlRootVisitor;
//import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
//import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
//
///**
// * The OvertureRootCMLAdapter functions as a root visitor for Overture Visitor. This visitor delegates all typecal
// * Overture type checking to Overture and diverts control to the VanillaCmlTypeChecker for cases that are special to
// * CML.
// * 
// * @author rwl
// */
//@SuppressWarnings("serial")
//public class OvertureRootCMLAdapter extends
//		QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType>
//{
//
//	private final TypeCheckerExpVisitor overtureExpressionVisitor;
//	private final TypeCheckerDefinitionVisitor overtureDefinitionVisitor;
//	private final TypeCheckerPatternVisitor overturePatternVisitor;
//
//	private final ICmlRootVisitor parent;
//	private final ITypeIssueHandler issueHandler;
//
//	public OvertureRootCMLAdapter(ICmlRootVisitor cmlTC,
//			ITypeIssueHandler issueHandler)
//	{
//		overtureDefinitionVisitor = new TypeCheckerDefinitionVisitor(this);
//		overtureExpressionVisitor = new TypeCheckerExpVisitor(this);
//		overturePatternVisitor = new TypeCheckerPatternVisitor(this);
//		parent = cmlTC;
//		this.issueHandler = issueHandler;
//	}
//
//	@Override
//	public PType caseAEnumerationRenameChannelExp(
//			AEnumerationRenameChannelExp node, TypeCheckInfo question)
//			throws AnalysisException
//	{
//		return escapeFromOvertureContext(node, question);
//	}
//
//	static void pushQuestion(TypeCheckInfo question)
//	{
//		if (question instanceof CmlTypeCheckInfo)
//		{
//			CmlTypeCheckInfo info = (CmlTypeCheckInfo) question;
//			question.contextSet(CmlTypeCheckInfo.class, info);
//		}
//	}
//
//	static void popQuestion(TypeCheckInfo question)
//	{
//		if (question instanceof CmlTypeCheckInfo)
//		{
//			CmlTypeCheckInfo info = (CmlTypeCheckInfo) question;
//			question.contextRem(info.getClass());
//		}
//	}
//
//	@Override
//	public PType caseAAssignmentDefinition(AAssignmentDefinition node,
//			TypeCheckInfo question) throws AnalysisException
//	{
//		return escapeFromOvertureContext(node, question);
//	}
//
//	private PType escapeFromOvertureContext(INode node,
//			org.overture.typechecker.TypeCheckInfo question)
//	{
//		try
//		{
//			pushQuestion(question);
//			PType res = node.apply(parent, question);
//			popQuestion(question);
//			return res;
//		} catch (AnalysisException e)
//		{
//			e.printStackTrace();
//			return issueHandler.addTypeError(node, TypeErrorMessages.TYPE_CHECK_INTERNAL_FAILURE.toString());
//		}
//
//	}
//
//	@Override
//	public PType defaultPExp(PExp node, TypeCheckInfo question)
//			throws AnalysisException
//	{
//		PType type = null;
//		pushQuestion(question);
//		try
//		{
//			type = node.apply(overtureExpressionVisitor, question);
//		} catch (TypeCheckException tce)
//		{
//			popQuestion(question);
//			return issueHandler.addTypeError(node, tce.getMessage());
//		}
//		popQuestion(question);
//		return type;
//	}
//
//	@Override
//	public PType caseAEnumVarsetExpression(AEnumVarsetExpression node,
//			TypeCheckInfo question) throws AnalysisException
//	{
//		return escapeFromOvertureContext(node, question);
//	}
//
//	@Override
//	public PType caseAApplyExp(AApplyExp node, TypeCheckInfo question)
//			throws AnalysisException
//	{
//		return escapeFromOvertureContext(node, question);
//	}
//
//	@Override
//	public PType defaultPDefinition(PDefinition node, TypeCheckInfo question)
//			throws AnalysisException
//	{
//		try
//		{
//			pushQuestion(question);
//			PType type = node.apply(overtureDefinitionVisitor, question);
//			popQuestion(question);
//			return type;
//		} catch (TypeCheckException e)
//		{
//			return issueHandler.addTypeError(node, e.getMessage());
//		}
//	}
//
//	@Override
//	public PType caseAVariableExp(AVariableExp node, TypeCheckInfo question)
//			throws AnalysisException
//	{
//		return escapeFromOvertureContext(node, question);
//	}
//
//	@Override
//	public PType caseANilExp(ANilExp node, TypeCheckInfo question)
//			throws AnalysisException
//	{
//		return escapeFromOvertureContext(node, question);
//	}
//
//	@Override
//	public PType caseAUnresolvedPathExp(AUnresolvedPathExp node,
//			TypeCheckInfo question) throws AnalysisException
//	{
//
//		return escapeFromOvertureContext(node, question);
//	}
//
//	@Override
//	public PType caseABracketedExp(ABracketedExp node, TypeCheckInfo question)
//			throws AnalysisException
//	{
//		return escapeFromOvertureContext(node, question);
//	}
//
//	@Override
//	public PType defaultINode(INode node, TypeCheckInfo question)
//			throws AnalysisException
//	{
//		return escapeFromOvertureContext(node, question);
//	}
//
//	@Override
//	public PType defaultPMultipleBind(PMultipleBind node, TypeCheckInfo question)
//			throws AnalysisException
//	{
//
//		try
//		{
//			pushQuestion(question);
//			PType type = node.apply(overturePatternVisitor, question);
//			popQuestion(question);
//			return type;
//		} catch (TypeCheckException e)
//		{
//			return issueHandler.addTypeError(node, e.getMessage());
//		}
//	}
//
//	@Override
//	public PType createNewReturnValue(INode node, TypeCheckInfo question)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public PType createNewReturnValue(Object node, TypeCheckInfo question)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
// }
