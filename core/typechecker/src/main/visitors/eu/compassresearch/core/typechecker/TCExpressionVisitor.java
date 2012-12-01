package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.PType;
import org.overture.parser.messages.VDMError;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeChecker;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

class TCExpressionVisitor extends
		QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509187123701383525L;

	// A parent checker may actually not be necessary on this
	final private VanillaCmlTypeChecker parent;
	private final TypeIssueHandler issueHandler;

	public TCExpressionVisitor(VanillaCmlTypeChecker parentChecker,
			TypeIssueHandler issueHandler) {
		parent = parentChecker;
		this.issueHandler = issueHandler;
	}

	/**
	 * Translate a CML expression into an equivalent Overture VDM expression and
	 * type check that. Afterwards use the CopyTypesFromOvtToCmlAst to copy over
	 * the Overture VDM types.
	 * 
	 * 
	 * @param node
	 *            - the expression to type check
	 * @param question
	 *            - environmental stuff, green trees whatever
	 * @return A type checked cml expression
	 * @throws AnalysisException
	 *             - if anythings goes wrong that is not just a type error.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public PType defaultPExp(PExp node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		org.overture.typechecker.TypeChecker.clearErrors();

		INode ovtNode = node;

		OvertureRootCMLAdapter overtureExpVisitor = new OvertureRootCMLAdapter(
				parent, issueHandler);

		org.overture.typechecker.TypeCheckInfo quest = new org.overture.typechecker.TypeCheckInfo(
				question.env);
		quest.scope = NameScope.NAMES;
		quest.qualifiers = new LinkedList<PType>();
		try {
			ovtNode.apply(overtureExpVisitor, quest);
		} catch (org.overture.ast.analysis.AnalysisException e1) {
			e1.printStackTrace();
		}

		if (org.overture.typechecker.TypeChecker.getErrorCount() > 0) {
			List<VDMError> errorList = TypeChecker.getErrors();
			for (VDMError err : errorList) {
				issueHandler.addTypeError(node, err.toProblemString());
			}
			return new AErrorType(node.getLocation(), true);
		}

		return node.getType();
	}

	@Override
	public PType caseABracketedExp(ABracketedExp node, TypeCheckInfo question)
			throws AnalysisException {

		PType type = node.getExpression().apply(this, question);
		node.setType(type);
		return type;
	}

}
