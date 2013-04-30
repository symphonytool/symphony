package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

/**
 * 
 * @author rwl
 * 
 *         Abstract type checker place holder for errors and warnings.
 * 
 *         Additionally, the cleared state is facilitated by providing a cleared
 *         flag. It is up to an implementation to enforce the cleared flag
 *         throwing an IllegalStateException if getTypeErrors or getTypeWarnings
 *         are invoked inappropriately.
 * 
 *         Also a place holder for the sourceForest is provided. However, it is
 *         up to the implementation to actually load and set the sourceForest.
 * 
 *         The constructor is kept package scoped as this class is intimate to
 *         the eu.compassresearch.core.typechecker package.
 */
@SuppressWarnings("serial")
abstract class AbstractTypeChecker extends
		QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType>
		implements CmlTypeChecker {
	protected TypeIssueHandler issueHandler;
	protected boolean cleared;
	protected Collection<PSource> sourceForest;

	AbstractTypeChecker() {
		clear();
	}

	/**
	 * Clear out all warnings and errors and resets the type checker.
	 */
	abstract void clear();
}
