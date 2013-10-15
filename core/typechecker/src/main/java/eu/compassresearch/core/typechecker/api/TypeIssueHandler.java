package eu.compassresearch.core.typechecker.api;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.core.common.AnalysisArtifact;

/**
 * All error reporting from the type checker is handled by a TypeIssueHandler. Consider to generalize this for all
 * analysis.
 * 
 * @author rwl
 */
public interface TypeIssueHandler
{
	/**
	 * @author rwl The result from the CML type checker is a set of CMLIssues. These can be warnings or error. A CML
	 *         Issue points the a subtree node that is the least upper bound node in the AST spanning causing the issue.
	 */
	public static abstract class CMLIssue implements AnalysisArtifact
	{
		protected final INode subtree;
		private ILexLocation location;

		/**
		 * Return the node that generated this error.
		 * 
		 * @return
		 */
		public INode getOffendingNode()
		{
			return subtree;
		}

		public CMLIssue(INode subtree)
		{
			this.subtree = subtree;
			setFromNode();
		}

		public CMLIssue(LexLocation location)
		{
			subtree = null;
			this.location = location;
		}

		// temporary method goes away when astCreator is updated. ( INode should
		// have getLocation method )
		public ILexLocation getLocation()
		{
			return location;
		}

		public void setLocation(ILexLocation location)
		{
			this.location = location;
		}

		private void setFromNode()
		{
			if (subtree != null)
			{
				try
				{
					Method getLocation = subtree.getClass().getMethod("getLocation", new Class<?>[0]);
					location = (LexLocation) getLocation.invoke(subtree, new Object[0]);
				} catch (Exception e)
				{
					// no location :(
				}
			}
		}
	}

	/**
	 * @author rwl CML Type Warnings can be ignored as the properties of CML special preservation and CML special
	 *         progress should still hold in the presents of warnings. However, the model may be ill shaped for e.g.
	 *         simulation: a set of classes with no processes can never run. Such issues are not directly errors but
	 *         will lead to limited exploration with later phases of the COMPASS tools.
	 */
	public static class CMLTypeWarning extends CMLIssue
	{
		protected final String description;

		public String getDescription()
		{
			return description;
		}

		public CMLTypeWarning(INode subtree, String description)
		{
			super(subtree);
			this.description = description;
		}

		@Override
		public String toString()
		{
			ILexLocation location = super.getLocation();
			return "TypeWarning: " + location + " : " + description;
		}

	}

	public static class CMLIssueList extends LinkedList<CMLIssue> implements
			AnalysisArtifact
	{
		private static final long serialVersionUID = 7238951452951163635L;
	}

	/**
	 * @author rwl CML Type Errors means that the CML model leads to an AST that cannot be given a proper semantics.
	 */
	public static class CMLTypeError extends CMLTypeWarning
	{

		private StackTraceElement[] stackTrace;

		private void buildStack()
		{
			this.stackTrace = Thread.currentThread().getStackTrace();
		}

		public CMLTypeError(INode subtree, String message)
		{
			super(subtree, message);
			buildStack();
		}

		@Override
		public String toString()
		{
			ILexLocation location = super.getLocation();
			return String.format("%s %s", description, location);
		}

		public String getStackTrace()
		{
			int i = 0;
			StringBuilder sb = new StringBuilder();
			sb.append(toString() + "\n\n\nOffending node: "
					+ (subtree == null ? "null" : subtree) + "\n");
			for (i = 4; i < stackTrace.length && i < 20; i++)
			{
				StackTraceElement e = stackTrace[i];
				sb.append("\t" + e.toString() + "\n");
			}
			return sb.toString();
		}

		@Override
		public boolean equals(Object obj)
		{

			if (obj instanceof CMLTypeError)
			{
				CMLTypeError error = (CMLTypeError) obj;
				boolean sameSubTree = (error.subtree == null && subtree == null)
						|| (error.subtree != null && subtree != null && subtree == error.subtree);
				boolean sameDescription = (description == null && error.description == null)
						|| (description != null && description.equals(error.description));

				return sameSubTree && sameDescription;
			}

			return false;
		}

		@Override
		public int hashCode()
		{
			int subtreeHash = subtree == null ? 0 : subtree.hashCode();
			int descriptionHash = description == null ? 0
					: description.hashCode();
			return subtreeHash + descriptionHash;
		}

	}

	/**
	 * Returns the list of type errors found after type checking. If the type check method has not been invoked yet an
	 * IllegalStateException will be thrown.
	 * 
	 * @return - List with CML type errors.
	 * @throws IllegalStateException
	 */
	public List<CMLTypeError> getTypeErrors() throws IllegalStateException;

	/**
	 * Returns the list of type warnings found during type checking. See {@link CMLTypeWarning}. An
	 * IllegalStateException will be thrown is invoked before the type checker has completed a full type check.
	 * 
	 * @return
	 * @throws IllegalStateException
	 */
	public List<CMLTypeWarning> getTypeWarnings() throws IllegalStateException;

	/**
	 * Report a type error has been found for the given location.
	 * 
	 * @param offendingSubtree
	 *            - The offending AST node
	 * @param message
	 *            - A message detailing the nature of the the error and preferably hinting how to fix it.
	 */
	public AErrorType addTypeError(INode offendingSubtree, String message);

	public AErrorType addTypeError(INode parent, ILexLocation pos,
			String message);

	/**
	 * Return a type warning.
	 * 
	 * @param hazardousSubtree
	 *            - The subtree found to be inhibiting an ill shape.
	 * @param message
	 *            - A message detailing the kind of check made to trigger this warning.
	 */
	public void addTypeWarning(INode hazardousSubtree, String message);

	/**
	 * Returns true if one or more errors has been added.
	 * 
	 * @return
	 */
	public boolean hasErrors();

	/**
	 * Returns true if one or more warnings has been added.
	 * 
	 * @return
	 */
	public boolean hasWarnings();

	/**
	 * Returns true if one or more warnings or errors has been added.
	 * 
	 * @return
	 */
	public boolean hasIssues();
}
