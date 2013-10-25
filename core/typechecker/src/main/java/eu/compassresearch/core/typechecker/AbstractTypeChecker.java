package eu.compassresearch.core.typechecker;

import java.util.Collection;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

/**
 * @author rwl Abstract type checker place holder for errors and warnings. Also a place holder for the sourceForest is
 *         provided. However, it is up to the implementation to actually load and set the sourceForest. The constructor
 *         is kept package scoped as this class is intimate to the eu.compassresearch.core.typechecker package.
 */
abstract class AbstractTypeChecker implements ICmlTypeChecker
{
	protected ITypeIssueHandler issueHandler;
	protected Collection<PSource> sourceForest;

	AbstractTypeChecker()
	{
		clear();
	}

	/**
	 * Clear out all warnings and errors and resets the type checker.
	 */
	abstract void clear();
	
	
	public static void abort(int number, String reason)
	{
		throw new AbortTypecheck(number, reason);
	}
}
