package eu.compassresearch.ide.interpreter.launching;

public enum CmlLaunchConfigurationErrorMessages
{

	NO_PROJECT_WITH_NAME_EXIST("No project identified by '%s' exists"),

	PROJECT_NOT_OPEN("Project '%s' is not open"),

	PROJECT_NOT_SET("Project not set"),
	
	URL_NOT_VALID("The url '%s' is not valid");
	// FATAL_ERROR("A fatal unrecoverable error has occured, please report this error to akm@iha.dk")
	;

	private String template;

	private CmlLaunchConfigurationErrorMessages(String templateString)
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

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return template;
	}

}
