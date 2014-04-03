package eu.compassresearch.core.typechecker;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.parser.messages.VDMError;
import org.overture.parser.messages.VDMWarning;

import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeWarningMessages;

/**
 * Very simple LinkedList based TypeIssueHandler that simply collects errors and warnings for future retrieval.
 * 
 * @author rwl
 */
public class CollectingIssueHandler implements ITypeIssueHandler
{

	/**
	 * Creating a collecting issue handler assumes you are going to do a fresh type checking round.
	 */
	public CollectingIssueHandler()
	{
		errors = new LinkedList<CMLTypeError>();
		warnings = new LinkedList<CMLTypeWarning>();
	}

	private final List<CMLTypeError> errors;
	private final List<CMLTypeWarning> warnings;

	@Override
	public List<CMLTypeError> getTypeErrors() throws IllegalStateException
	{
		return errors;
	}

	@Override
	public List<CMLTypeWarning> getTypeWarnings() throws IllegalStateException
	{
		return warnings;
	}

	@Override
	public void addTypeError(INode offendingSubtree, TypeErrorMessages message,
			String... arguments)
	{
		CMLTypeError error = new CMLTypeError(offendingSubtree, message.number, message.customizeMessage(arguments));
		this.errors.add(error);
		// addIssueToRegistryForNode(offendingSubtree, registry, error);
	}

	@Override
	public void addTypeWarning(INode hazardousSubtree,
			TypeWarningMessages message)
	{
		addTypeWarning(hazardousSubtree, message, "");
	}

	@Override
	public boolean hasErrors()
	{
		return !errors.isEmpty();
	}

	@Override
	public boolean hasWarnings()
	{
		return !warnings.isEmpty();
	}

	@Override
	public boolean hasIssues()
	{
		return hasErrors() || hasWarnings();
	}

	@Override
	public void addTypeError(INode parent, ILexLocation location, String message)
	{
		CMLTypeError typeError = new CMLTypeError(parent, 0, location, message);
		typeError.setLocation(location);
		this.errors.add(typeError);
	}

	@Override
	public void report(VDMError error)
	{
		CMLTypeError typeError = new CMLTypeError(null, error);
		this.errors.add(typeError);
	}

	@Override
	public void warning(VDMWarning warning)
	{
		CMLTypeWarning typeError = new CMLTypeWarning(null, warning.number, warning.toProblemString());
		typeError.setLocation(warning.location);
		this.warnings.add(typeError);
	}

	public void printErrors(PrintWriter out)
	{
		for (CMLTypeError e : errors)
		{
			out.println(e.toString());
		}
	}

	public void printWarnings(PrintWriter out)
	{
		for (CMLTypeWarning w : warnings)
		{
			out.println(w.toString());
		}
	}

	@Override
	public void addTypeWarning(INode hazardousSubtree,
			TypeWarningMessages message, String... arguments)
	{
		CMLTypeWarning warning = new CMLTypeWarning(hazardousSubtree, 0, message.customizeMessage(arguments));
		this.warnings.add(warning);
	}

}
