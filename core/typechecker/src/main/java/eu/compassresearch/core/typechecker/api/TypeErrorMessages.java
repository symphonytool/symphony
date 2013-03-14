package eu.compassresearch.core.typechecker.api;

public enum TypeErrorMessages {
	DUPLICATE_DEFINITION("Definition \"%s\" is conflicting with definition \"%s\"."),

	TYPE_CHECK_INTERNAL_FAILURE("Subtree failed type checking with an internal error: %s."),

	INPROPER_STATE_DEFINITION("Expected an assignment definition on the form <name> ':' <type> {:= exp}."),
	
	SEQ_TYPE_EXPECTED(
			"The expresion %s has type %s which is not a sequence type as expected."), 

			PARAGRAPH_HAS_TYPES_ERRORS("Paragraph \"%s\" has type errors."),

			EXPECTED_OPERATION_DEFINITION("Operation definition expected. %s is not an operation definition. "),

			COULD_NOT_DETERMINE_TYPE("Could not determine type for %s."),

			INCOMPATIBLE_TYPE("Expected an entity of type %s but %s was found."),

			EXPECTED_A_CHANNEL("Expected entity \"%s\" to be a channel. "),
			
			EXPECTED_A_NAMESET("Expected a name set but %s was found."),
			
			EXPECTED_SUBTYPE_RELATION("%s is not a sub type of %s."),

			MISSING_CONSTRUCTOR("No constructor found for class %s and arguments %s"),

			NAMED_TYPE_UNDEFINED("The type %s is undefined."),

			SET_TYPE_EXPECTED(
					"The expression %s has type %s which is not a set type as expected."),

					UNDEFINED_SYMBOL("The Symbol \"%s\" is undefined."),

					UNDEFINED_TYPE("The Type \"%s\" is undefined."),

					EXPECTED_AN_ACTION_OR_OPERATION(
							"Symbol \"%s\" is neither an action nor an operation which are allowed in this context."),

							EXPECTED_A_CHANNELSET("Symbol \"%s\" is not a Channel Set."),

							TIME_UNIT_EXPRESSION_MUST_BE_NAT(
									"In it's context \"%s\" is a time unit expression and must be a subtype of nat, which \"%s\" is not."),

									EXPECTED_TYPE_DEFINITION(
											"Expected symbol \"%s\" to be a type definition. But it was not."), EXPECTED_CLASS(
													"Expected symbol \"%s\" to be a class. But it was not."),

													EXPECTED_PROCESS_DEFINITION("%s is expected to be a process definition."),

													ILLEGAL_CONTEXT(
															"The symbol \"%s\" requires a CML context to be resolved. No such context is present."),
															PATTERN_TYPE_MISSING("The pattern \"%s\" should already have a type from its context."),
															WRONG_NUMBER_OF_ARGUMENTS("Expected %s args, %s were found."), PATTERN_MISMATCH("Pattern mismatch. Required by type %s, given pattern %s."), 
															MIXING_STATE_AND_CHANNEL_IN_SET("Mixing channel and state names not allowed. %s contains both channel and state names."), 
															EXPECTED_CHANNEL_OR_STATE("Expected a channel or state definition. %s is neither."),


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

	public String toString() {
		return template;
	}
}
