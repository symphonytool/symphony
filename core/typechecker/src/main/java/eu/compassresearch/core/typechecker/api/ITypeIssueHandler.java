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
		final protected String message;
		final protected int number;

		public int getNumber()
		{
			return number;
		}

		public String getMessage()
		{
			return message;
		}

		/**
		 * Return the node that generated this error.
		 * 
		 * @return
		 */
		public INode getOffendingNode()
		{
			return reportedAt;
		}

		public CMLIssue(INode reportedAt, int number, String description)
		{
			this.reportedAt = reportedAt;
			this.number = number;
			this.message = description;
			setFromNode();
		}

		public CMLIssue(INode reportedAt, ILexLocation location, int number,
				String description)
		{
			this.reportedAt = reportedAt;
			this.number = number;
			this.message = description;
			this.location = location;
		}

		public CMLIssue(ILexLocation location, int number, String description)
		{
			reportedAt = null;
			this.number = number;
			this.location = location;
			this.message = description;
		}

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
			return String.format("%04d: %s %s", number, getMessage(), location);
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

		public CMLTypeWarning(INode subtree, int number, String description)
		{
			super(subtree, number, description);
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
			return message;
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

		public CMLTypeError(INode subtree, int number, String description)
		{
			super(subtree, number, description);
			buildStack();
		}

		public CMLTypeError(INode subtree, int number, ILexLocation location,
				String description)
		{
			super(subtree, location, number, description);
			buildStack();
		}

		public CMLTypeError(INode subtree, VDMError error)
		{
			super(subtree, error.location, error.number, null);
			this.error = error;
			buildStack();
		}

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();

			sb.append("Error ");
			sb.append(super.toString());

			return sb.toString();
		}

		public String getStackTrace()
		{
			int i = 0;
			StringBuilder sb = new StringBuilder();
			sb.append(toString());
			sb.append(reportedAt == null ? "\n\n" : "\nOffending node: "
					+ reportedAt);
			for (i = 4; i < stackTrace.length && i < 20; i++)
			{
				StackTraceElement e = stackTrace[i];
				sb.append("\t" + e.toString() + "\n");
			}
			return sb.toString();
		}

		public String getMessage()
		{
			if (error == null)
			{
				return message;
			} else
			{
				return error.toProblemString();
			}

		}

		@Override
		public boolean equals(Object obj)
		{

			if (obj instanceof CMLTypeError)
			{
				CMLTypeError errorEqualTo = (CMLTypeError) obj;
				boolean sameSubTree = errorEqualTo.reportedAt == null
						&& reportedAt == null
						|| errorEqualTo.reportedAt != null
						&& reportedAt != null
						&& reportedAt == errorEqualTo.reportedAt;
				boolean sameDescription = message == null
						&& errorEqualTo.message == null || message != null
						&& message.equals(errorEqualTo.message)
						|| error == null && errorEqualTo.error == null
						|| error != null && error.equals(errorEqualTo.error);

				return sameSubTree && sameDescription;
			}

			return false;
		}
		
		@Override
		public int hashCode()
		{
			return super.hashCode();
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
	public void addTypeError(INode offendingSubtree, ILexLocation location,
			String message);

	public void addTypeError(INode parent, TypeErrorMessages message,
			String... arguments);

	/**
	 * Return a type warning.
	 * 
	 * @param hazardousSubtree
	 *            - The subtree found to be inhibiting an ill shape.
	 * @param message
	 *            - A message detailing the kind of check made to trigger this warning.
	 */
	public void addTypeWarning(INode hazardousSubtree,
			TypeWarningMessages message, String... arguments);

	/**
	 * Return a type warning.
	 * 
	 * @param hazardousSubtree
	 *            - The subtree found to be inhibiting an ill shape.
	 * @param message
	 *            - A message detailing the kind of check made to trigger this warning.
	 */
	public void addTypeWarning(INode hazardousSubtree,
			TypeWarningMessages message);

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
