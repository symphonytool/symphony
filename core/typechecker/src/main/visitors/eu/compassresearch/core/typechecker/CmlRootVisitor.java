package eu.compassresearch.core.typechecker;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PAlternative;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.patterns.PPatternBind;
import org.overture.ast.statements.PAlternativeStm;
import org.overture.ast.statements.PClause;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PAlternativeAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.ASourceType;
import eu.compassresearch.core.typechecker.api.ITypeComparator;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.util.CmlTCUtil;

class CmlRootVisitor extends
		QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType>
		implements eu.compassresearch.core.typechecker.api.ICmlRootVisitor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3231597110935151449L;
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> exp; // expressions
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> act; // actions
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> dad; // definition
																				// and
																				// decls
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> typ; // basic
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> prc; // process
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> bnd; // bind
	private final ITypeComparator typeComparator;
	private final ITypeIssueHandler issueHandler;

	private void initialise()
	{
		act = new TCActionVisitor(this, this.issueHandler, typeComparator);
		exp = new TCExpressionVisitor(this, this.issueHandler, typeComparator);
		dad = new TCDeclAndDefVisitor(this, typeComparator, this.issueHandler);
		typ = new TCTypeVisitor(this, this.issueHandler);
		prc = new TCProcessVisitor(this, this.issueHandler, typeComparator);
		bnd = new TCBindVisitor(this, this.issueHandler);
	}

	CmlRootVisitor(ITypeIssueHandler issueHandler, ITypeComparator comparator)
	{
		this.issueHandler = issueHandler;
		this.typeComparator = comparator;
		initialise();
	}

	// Utility methods
	private PType addErrorForMissingType(INode node, PType type)
	{
		if (type == null)
		{
			// addTypeError(node, "Insufficient type checker implementation.");
			return new AErrorType();
		} else
			return type;

	}

	// Default cases
	@Override
	public PType defaultPCommunicationParameter(PCommunicationParameter node,
			TypeCheckInfo question) throws AnalysisException
	{
		return super.defaultPCommunicationParameter(node, question);
	}

	@Override
	public PType defaultPParametrisation(PParametrisation node,
			TypeCheckInfo question) throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(this.act, question));

	}

	@Override
	public PType defaultPAlternativeAction(PAlternativeAction node,
			TypeCheckInfo question) throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(this.act, question));
	}

	@Override
	public PType defaultPMultipleBind(PMultipleBind node, TypeCheckInfo question)
			throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(bnd, question));
	}

	@Override
	public PType defaultPVarsetExpression(PVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(exp, question));
	}

	@Override
	public PType defaultPBind(PBind node, TypeCheckInfo question)
			throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(bnd, question));
	}

	@Override
	public PType defaultPType(PType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(typ, question));
	}

	@Override
	public PType defaultPPattern(PPattern node, TypeCheckInfo question)
			throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(bnd, question));
	}

	@Override
	public PType defaultPSource(PSource node, TypeCheckInfo question)
			throws AnalysisException
	{
		for (PDefinition def : node.getParagraphs())
		{
			PType defType = def.apply(this, question);
			if (!CmlTCUtil.successfulType(defType))
				return defType;
		}
		node.setType(new ASourceType());
		return new ASourceType();
	}

	@Override
	public PType defaultPPatternBind(PPatternBind node, TypeCheckInfo question)
			throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(this.bnd, question));
	}

	@Override
	public PType defaultINode(INode node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return addErrorForMissingType(node, super.defaultINode(node, question));
	}

	@Override
	public PType defaultPSingleDeclaration(PSingleDeclaration node,
			TypeCheckInfo question) throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(this.dad, question));
	}

	@Override
	public PType defaultPAlternative(PAlternative node, TypeCheckInfo question)
			throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(this.act, question));
	}

	@Override
	public PType defaultPAlternativeStm(PAlternativeStm node,
			TypeCheckInfo question) throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(this.act, question));
	}

	@Override
	public PType defaultPDefinition(PDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(this.dad, question));
	}

	@Override
	public PType defaultPExp(PExp node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(exp, question));
	}

	@Override
	public PType defaultPProcess(PProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(prc, question);
	}

	@Override
	public PType defaultPAction(PAction node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(act, question);
	}

	@Override
	public PType defaultPClause(PClause node, TypeCheckInfo question)
			throws AnalysisException
	{
		return addErrorForMissingType(node, node.apply(act, question));
	}

	@Override
	public PType createNewReturnValue(INode node, TypeCheckInfo question)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PType createNewReturnValue(Object node, TypeCheckInfo question)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
