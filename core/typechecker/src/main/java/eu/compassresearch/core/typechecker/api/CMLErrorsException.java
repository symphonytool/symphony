package eu.compassresearch.core.typechecker.api;

import java.util.List;

import org.overture.ast.util.Utils;

import eu.compassresearch.core.typechecker.api.ITypeIssueHandler.CMLTypeError;

@SuppressWarnings("serial")
public class CMLErrorsException extends Exception
{
	public final List<CMLTypeError> errors;

	public CMLErrorsException(List<CMLTypeError> errors)
	{
		super(Utils.listToString(errors, "\n"));
		this.errors = errors;
	}

	@Override
	public String toString()
	{
		return getMessage();
	}
}
