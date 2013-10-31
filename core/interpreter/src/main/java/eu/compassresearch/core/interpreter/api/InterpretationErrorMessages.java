package eu.compassresearch.core.interpreter.api;

public enum InterpretationErrorMessages
{

	NO_PROCESS_WITH_DEFINED_NAME_FOUND("No process identified by '%s' exists"), 
	FATAL_ERROR("A fatal unrecoverable error has occured, please report this error to akm@iha.dk"), CASE_NOT_IMPLEMENTED(
			"%s case is not yet implemented"), 
	EVAL_OF_UNDEFINED_VALUE("You are trying to evaluate an undefined value : %s %s"), 
	EVAL_OF_IMPLICIT_OP("The operation '%s' is implicit, this is not possible to interpret"), 
	SYNC_OF_NONCOMPARABLE_EVENTS("Cannot synchronize '%s' with '%s', since they are not comparable"),
	UNBOUNDED_REPLICATION("Unbounded replication is not allowed");
	

	private String template;

	private InterpretationErrorMessages(String templateString)
	{
		this.template = templateString;
	}

	public String customizeMessage(String... strs)
	{
		// Check Arity
		int arity = 0;
		for (Character c : template.toCharArray())
			if ('%' == c)
				arity++;
		if (arity != strs.length)
			throw new RuntimeException("Error in the error-message. Template requires exactly "
					+ arity + " arguments but " + strs.length + " were given.");

		// Format String
		return String.format(template, (Object[]) strs);
	}

}
