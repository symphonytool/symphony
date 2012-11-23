package eu.compassresearch.core.typechecker.api;

public enum TypeErrorMessages {
	COULD_NOT_DETERMINE_TYPE("Could not determine type for %s."),

	INCOMPATIBLE_TYPE("Expected an entity of type %s but %s was found."),

	EXPECTED_SUBTYPE_RELATION(
			"This code assumes that %s is a sub type of %s, which is not true."),

	NAMED_TYPE_UNDEFINED("The type %s is undefined."),

	SET_TYPE_EXPECTED("The expression %s has type %s which is not a set type."),

	UNDEFINED_SYMBOL("The Symbol \"%s\" is undefined."),

	EXPECTED_AN_ACTION(
			"Symbol \"%s\" is not an action. Only actions are allowed in this context."),

	EXPECTED_A_CHANNELSET("Symbol \"%s\" is not a Channel Set."),

	TIME_UNIT_EXPRESSION_MUST_BE_NAT(
			"In it's context \"%s\" is a time unit expression and must be a subtype of nat, which \"%s\" is not.");

	;

	private String template;

	private TypeErrorMessages(String templateString) {
		this.template = templateString;
	}

	public String customizeMessage(String... strs) {
		// Check Arity
		int arity = 0;
		for (Character c : template.toCharArray())
			if ('%' == c)
				arity++;
		if (arity != strs.length)
			throw new RuntimeException(
					"Error in the error-message. Template requires exactly "
							+ arity + " arguments but " + strs.length
							+ " were given.");

		// Format String
		return String.format(template, (Object[]) strs);
	}
}
