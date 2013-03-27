package eu.compassresearch.core.interpreter.cml;

public enum CmlBehaviourSignal {

	EXEC_SUCCESS(""),
	FATAL_ERROR("");
	
	private String template;
    
	private CmlBehaviourSignal(String templateString)
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
			throw new RuntimeException(
					"Error in the error-message. Template requires exactly " + arity
					+ " arguments but " + strs.length + " were given.");

		// Format String
		return String.format(template, (Object[]) strs);
	}
	
}
