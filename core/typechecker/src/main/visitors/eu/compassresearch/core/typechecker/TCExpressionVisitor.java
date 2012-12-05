package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.ANilExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.PType;
import org.overture.parser.messages.VDMError;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeChecker;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

class TCExpressionVisitor extends
QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> {

	@Override
	public PType caseANilExp(ANilExp node, TypeCheckInfo question)
			throws AnalysisException {
		return new AUnknownType(node.getLocation(), true);
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -6509187123701383525L;

	// A parent checker may actually not be necessary on this
	final private CmlTypeChecker parent;
	private final TypeIssueHandler issueHandler;
	private CmlOvertureAssistant assist;

	TCExpressionVisitor(CmlTypeChecker parentChecker, TypeIssueHandler issueHandler, CmlOvertureAssistant assistant)
	{
		this.parent=parentChecker;
		this.issueHandler = issueHandler;
		this.assist = assistant;

	}

	TCExpressionVisitor(VanillaCmlTypeChecker parentChecker,
			TypeIssueHandler issueHandler) {
		this(parentChecker,issueHandler,new CmlOvertureAssistant());
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
	public PType caseAUnresolvedPathExp(AUnresolvedPathExp node,
			TypeCheckInfo question) throws AnalysisException {

		// So we are going to look up a path of the form <class>.<member> or <identifier>.<member>
		// To find that class there must be a CML Environment as Classes are 
		// top-level and CML Specific.
		// 
		eu.compassresearch.core.typechecker.TypeCheckInfo 
		cmlQuestion = question.contextGet(eu.compassresearch.core.typechecker.TypeCheckInfo.class);

		if (cmlQuestion == null)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node+"")));
			return node.getType();
		}

		// All right lets get all the identifiers used in this path
		LinkedList<LexIdentifierToken> identifiers = node.getIdentifiers();

		// Get parent identifier
		LexNameToken rootName = new LexNameToken("",identifiers.get(0));
		
		// is it a type like a class or global type
		PDefinition root = question.env.findType(rootName,"");
		
		// no then it may be a variable 
		if (root == null)
			root = question.env.findName(rootName, NameScope.GLOBAL);
		
		// RWL: UGLY Re-factor some day
		if (root instanceof AAssignmentDefinition)
		{
			AAssignmentDefinition adef = (AAssignmentDefinition)root;
			PType type = adef.getType();
			if (type instanceof AClassType)
			{
				AClassType clzType = (AClassType)type;
				root = question.env.findName(clzType.getName(), NameScope.GLOBAL);
			}
		}
		
		// last option it is not in something else then in must be in this class
		if (root == null)
		{
			root = question.env.getEnclosingDefinition();
			root = assist.findMemberName(root, rootName, cmlQuestion);
		}

		
		// did we find the top-level
		if (root == null)
		{
			node.setType(issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(""+rootName)));
			return node.getType();
		}
		
		// Now the root identifier is resolved, lets look for the first member
		// We assume the identifiers are given in order with the outer most 
		// definitions coming first
		PType leafType = null;
		PDefinition prevRoot = null;
		for(int i = 1; i < identifiers.size();i++)
		{
			LexIdentifierToken id = identifiers.get(i);
			LexNameToken idName = new LexNameToken("",id);
			PDefinition def = assist.findMemberName(root, idName,cmlQuestion, prevRoot);
			if (def == null)
			{
				node.setType(issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(id+" in "+ node)));
				return node.getType();
			}
			leafType = def.getType();
		}
	
		node.setType(leafType);
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
