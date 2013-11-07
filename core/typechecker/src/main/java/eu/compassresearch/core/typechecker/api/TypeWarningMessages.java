package eu.compassresearch.core.typechecker.api;

/**
 * Warning messages that can arise from the Type Checker.
 * 
 * @author rwl
 */
public enum TypeWarningMessages
{

	INCOMPLETE_TYPE_CHECKING("Potentially incomplete type checking at \"%s\"."),

	EXPECTED_AN_IDENTIFIER_PATTERN(
			"Expected \"%s\" to be an identifier pattern, it was \"%s\". Potentially incomplete type checking."), CYCLE_ACTION_REFERENCE(
			"Action reference cycle detected: \"%s\"."),
			UNREACHABLE_DEFINITION(/*3428,*/ "Unreacable definition"),

	;

	private String fmt;
	public final int number=0;

	TypeWarningMessages(String fmt)
	{
		this.fmt = fmt;
	}

	public String customizeMessage(String... args)
	{
		return String.format(fmt, (Object[]) args);
	}

}
