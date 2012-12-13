package eu.compassresearch.core.typechecker;

import java.util.List;

import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeComparator;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

/**
 * Class to create instances of the Vanilla type checker package.
 * 
 * 
 * @author rwl
 * 
 */
public final class VanillaFactory {

	/**
	 * create an instance of the Vanilla type checker.
	 * 
	 * @param cmlSource
	 *            - List of parsed CML source to type check
	 * @param issueHandler
	 *            - Optional parameter can be null in which case the type
	 *            checker will collect any type errors to be retrieved later.
	 *            Otherwise an instance that handles errors when reported by the
	 *            type checker.
	 */
	public static CmlTypeChecker newTypeChecker(List<PSource> cmlSources,
			TypeIssueHandler issueHandler) {
		VanillaCmlTypeChecker result = new VanillaCmlTypeChecker(cmlSources,
				issueHandler);
		return result;
	}

	/**
	 * Create a Declaration and Definition visitor
	 * 
	 * @param parentChecker
	 *            - A Root CML Type Checker to resolve other kinds of
	 *            constructs;
	 * 
	 * @param compareTypes
	 *            - A strategy for comparing CML types and determining the sub
	 *            type relation.
	 * 
	 * @param issueHandler
	 *            - A Error handler. The VanillaCmlTypeChecker is also a
	 *            TypeIssueHandler.
	 */
	public static IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> newCmlDefinitionAndDeclarationVisitor(
			CmlTypeChecker parentChecker, TypeComparator compareTypes,
			TypeIssueHandler issueHandler) {
		TCDeclAndDefVisitor v = new TCDeclAndDefVisitor(
				(VanillaCmlTypeChecker) parentChecker, compareTypes,
				issueHandler);
		return v;
	}

	/**
	 * Create a statement visitor
	 * 
	 * @param parentChecker
	 *            - A Root CML Type Checker to resolve other kinds of
	 *            constructs;
	 * 
	 * @param issueHandler
	 *            - A Error handler. The VanillaCmlTypeChecker is also a
	 *            TypeIssueHandler.
	 */
	public static IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> newCmStatementVisitor(
			CmlTypeChecker parentChecker, TypeIssueHandler issueHandler,
			TypeComparator typeComparator) {
		TCActionVisitor v = new TCActionVisitor(parentChecker, issueHandler,
				typeComparator);
		return v;
	}

	/**
	 * Create an expression visitor
	 * 
	 * @param parentChecker
	 *            - A Root CML Type Checker to resolve other kinds of
	 *            constructs;
	 * 
	 * @param issueHandler
	 *            - A Error handler. The VanillaCmlTypeChecker is also a
	 *            TypeIssueHandler.
	 */
	public static IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> newCmlTypeVisitor(
			CmlTypeChecker parentChecker, TypeIssueHandler issueHandler) {
		TCTypeVisitor exprVisitor = new TCTypeVisitor(
				(VanillaCmlTypeChecker) parentChecker, issueHandler);
		return exprVisitor;
	}

	/**
	 * Create an expression visitor
	 * 
	 * @param parentChecker
	 *            - A Root CML Type Checker to resolve other kinds of
	 *            constructs;
	 * 
	 * @param issueHandler
	 *            - A Error handler. The VanillaCmlTypeChecker is also a
	 *            TypeIssueHandler.
	 */
	public static IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> newCmlExpressionVisitor(
			CmlTypeChecker parentChecker, TypeIssueHandler issueHandler) {
		TCExpressionVisitor exprVisitor = new TCExpressionVisitor(
				(VanillaCmlTypeChecker) parentChecker, issueHandler);
		return exprVisitor;
	}

	/**
	 * 
	 * Get a fresh type checking question.
	 * 
	 * @param issueHandler
	 *            - A place to add errors.
	 * 
	 * @return
	 */
	public static TypeCheckQuestion newTopLevelTypeCheckQuestion(
			TypeIssueHandler issueHandler) {
		return CmlTypeCheckInfo.getNewTopLevelInstance(issueHandler, null);
	}

	/**
	 * Returns an instance of the default issue handle in the package.
	 * 
	 * @return
	 */
	public static TypeIssueHandler newCollectingIssueHandle() {
		return new CollectingIssueHandler();
	}
}
