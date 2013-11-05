package eu.compassresearch.core.typechecker.api;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.parser.messages.VDMError;
import org.overture.typechecker.TypeChecker.IStatusListener;

/**
 * All error reporting from the type checker is handled by a TypeIssueHandler. Consider to generalize this for all
 * analysis.
 * 
 * @author rwl
 */
public interface ITypeIssueHandler extends IStatusListener
{
	/**
	 * @author rwl The result from the CML type checker is a set of CMLIssues. These can be warnings or error. A CML
	 *         Issue points the a subtree node that is the least upper bound node in the AST spanning causing the issue.
	 */
	public static abstract class CMLIssue
	{
		protected final INode reportedAt;
		private ILexLocation location;
		final protected String description;

		public String getDescription()
		{
			return description;
		}

		public abstract String getMessage();

		/**
		 * Return the node that generated this error.
		 * 
		 * @return
		 */
		public INode getOffendingNode()
		{
			return reportedAt;
		}

		public CMLIssue(INode reportedAt, String description)
		{
			this.reportedAt = reportedAt;
			this.description = description;
			setFromNode();
		}

		public CMLIssue(LexLocation location, String description)
		{
			reportedAt = null;
			this.location = location;
			this.description = description;
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
			if (reportedAt != null)
			{
				try
				{
					Method getLocation = reportedAt.getClass().getMethod("getLocation", new Class<?>[0]);
					location = (LexLocation) getLocation.invoke(reportedAt, new Object[0]);
				} catch (Exception e)
				{
					// no location :(
				}
			}
		}

		@Override
		public String toString()
		{
			return String.format("%s %s", getDescription(), getLocation());
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

		public CMLTypeWarning(INode subtree, String description)
		{
			super(subtree, description);
		}

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Warning ");
			sb.append(super.toString());

			return sb.toString();
		}

		@Override
		public String getMessage()
		{
			return description;
		}

	}

	public static class CMLIssueList extends LinkedList<CMLIssue>
	{
		private static final long serialVersionUID = 7238951452951163635L;
	}

	/**
	 * @author rwl CML Type Errors means that the CML model leads to an AST that cannot be given a proper semantics.
	 */
	public static class CMLTypeError extends CMLIssue
	{

		private StackTraceElement[] stackTrace;

		private VDMError error;

		private void buildStack()
		{
			this.stackTrace = Thread.currentThread().getStackTrace();
		}

		public CMLTypeError(INode subtree, String description)
		{
			super(subtree, description);
			buildStack();
		}

		public CMLTypeError(INode subtree, VDMError error)
		{
			super(subtree, null);
			this.error = error;
			buildStack();
		}

		// @Override
		// public String toString()
		// {
		// return String.format("%s %s", getDescription(), getLocation());
		// }

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();

			if (error != null)
			{
				sb.append(error.toString());
			} else
			{
				sb.append("Error ");
				sb.append(super.toString());
			}

			return sb.toString();
		}

		public String getStackTrace()
		{
			int i = 0;
			StringBuilder sb = new StringBuilder();
			sb.append(toString());
			sb.append((reportedAt == null ? "\n\n" : "\nOffending node: "
					+ reportedAt));
			for (i = 4; i < stackTrace.length && i < 20; i++)
			{
				StackTraceElement e = stackTrace[i];
				sb.append("\t" + e.toString() + "\n");
			}
			return sb.toString();
		}

		public String getDescription()
		{
			return (error != null ? error.toString() : description);
		}

		@Override
		public ILexLocation getLocation()
		{
			return (error != null ? error.location : super.getLocation());
		}

		@Override
		public boolean equals(Object obj)
		{

			if (obj instanceof CMLTypeError)
			{
				CMLTypeError errorEqualTo = (CMLTypeError) obj;
				boolean sameSubTree = (errorEqualTo.reportedAt == null && reportedAt == null)
						|| (errorEqualTo.reportedAt != null
								&& reportedAt != null && reportedAt == errorEqualTo.reportedAt);
				boolean sameDescription = (description == null && errorEqualTo.description == null)
						|| (description != null && description.equals(errorEqualTo.description))
						|| (error == null && errorEqualTo.error == null)
						|| (error != null && error.equals(errorEqualTo.error));

				return sameSubTree && sameDescription;
			}

			return false;
		}

		@Override
		public int hashCode()
		{
			int subtreeHash = reportedAt == null ? 0 : reportedAt.hashCode();
			int descriptionHash = description == null ? 0
					: description.hashCode();
			return subtreeHash + descriptionHash;
		}

		@Override
		public String getMessage()
		{
			return (error != null ? error.message : description);
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
	public void addTypeError(INode offendingSubtree, String message);

	public void addTypeError(INode parent, ILexLocation pos, String message);

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

	public void printErrors(PrintWriter out);

	public void printWarnings(PrintWriter out);
}
