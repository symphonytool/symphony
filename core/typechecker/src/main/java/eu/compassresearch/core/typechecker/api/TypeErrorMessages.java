package eu.compassresearch.core.typechecker.api;

public enum TypeErrorMessages {
    SEQ_TYPE_EXPECTED(
	    "The expresion %s has type %s which is not a sequence type as expected."), PARAGRAPH_HAS_TYPES_ERRORS(
	    "Paragraph \"%s\" has type errors."),

    COULD_NOT_DETERMINE_TYPE("Could not determine type for %s."),

    INCOMPATIBLE_TYPE("Expected an entity of type %s but %s was found."),

    EXPECTED_SUBTYPE_RELATION("%s is not a sub type of %s."),

    MISSING_CONSTRUCTOR("No constructor found for class %s and arguments %s"),
    
    NAMED_TYPE_UNDEFINED("The type %s is undefined."),

    SET_TYPE_EXPECTED(
	    "The expression %s has type %s which is not a set type as expected."),

    UNDEFINED_SYMBOL("The Symbol \"%s\" is undefined."),

    UNDEFINED_TYPE("The Type \"%s\" is undefined."),

    EXPECTED_AN_ACTION(
	    "Symbol \"%s\" is not an action. Only actions are allowed in this context."),

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
	WRONG_NUMBER_OF_ARGUMENTS("Expected %s args, %s were found.")
	

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
