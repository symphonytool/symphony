package eu.compassresearch.core.typechecker.api;

public enum TypeWarningMessages {

	INCOMPLETE_TYPE_CHECKING("Potentially incomplete type checking at \"%s\"."),
	
	EXPECTED_AN_IDENTIFIER_PATTERN("Expected \"%s\" to be an identifier pattern, it was \"%s\". Potentially incomplete type checking."), CYCLE_ACTION_REFERENCE("Action reference cycle detected: \"%s\".")
	
	;
	
	private String fmt;
	TypeWarningMessages(String fmt)
	{
		this.fmt = fmt;
	}
	
	public String customizeMessage(String ...args)
	{
		return String.format(fmt, (Object[])args);
	}

	
}
