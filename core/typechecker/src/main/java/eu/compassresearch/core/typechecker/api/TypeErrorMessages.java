package eu.compassresearch.core.typechecker.api;

//start inde 3400
public enum TypeErrorMessages
{
	DUPLICATE_DEFINITION(3400,
			"Definition \"%s\" is conflicting with definition \"%s\"."),

	TYPE_CHECK_INTERNAL_FAILURE(3401,
			"Subtree failed type checking with an internal error: %s."),

	INPROPER_STATE_DEFINITION(3402,
			"Expected an assignment definition on the form <name> ':' <type> {:= exp}."),

	SEQ_TYPE_EXPECTED(3403,
			"The expresion %s has type %s which is not a sequence type as expected."),

	// PARAGRAPH_HAS_TYPES_ERRORS("Paragraph \"%s\" has type errors."),

	EXPECTED_OPERATION_DEFINITION(3404,
			"Operation definition expected. %s is not an operation definition. "),

	REACTIVE_CONSTRUCTS_IN_OP_NOT_ALLOWED(
			3405,
			"Body construct \"%s\" is not allowed in operations. Operations must not contain reactive constructs."),

	// COULD_NOT_DETERMINE_TYPE("Could not determine type for %s."),

	INCOMPATIBLE_TYPE(3406, "Expected an entity of type %s but %s was found."),

	EXPECTED_A_CHANNEL(3407, "Expected entity \"%s\" to be a channel. "),

	EXPECTED_A_NAMESET(3408, "Expected a name set but %s was found."),

	EXPECTED_SUBTYPE_RELATION(3409, "%s is not a sub type of %s."),

	// \item[3134:] \texttt{"Class has no constructor with these parameter types"}
	// MISSING_CONSTRUCTOR("No constructor found for class %s and arguments %s"),

	// \item[3113:] \texttt{"Unknown type name <name>"}
	NAMED_TYPE_UNDEFINED(3113, "Unknown type name %s"),

	// \item[3206:] \texttt{"Matching expression is not a set type"}
	SET_TYPE_EXPECTED(3206,
			"Matching expression \"%s\" is not a set type. Actual \"%s\""),
	// "The expression %s has type %s which is not a set type as expected."),

	UNDEFINED_SYMBOL(3410, "The Symbol \"%s\" is undefined."),

	// UNDEFINED_TYPE("The Type \"%s\" is undefined."),

	EXPECTED_AN_ACTION_OR_OPERATION(
			3411,
			"Symbol \"%s\" is neither an action nor an operation which are allowed in this context."),

	EXPECTED_A_CHANNELSET(3412, "Symbol \"%s\" is not a Channel Set."),

	TIME_UNIT_EXPRESSION_MUST_BE_NAT(
			3413,
			"The time unit expression type is not a nat. Expression \"%s\" Actual type: \"%s\""),

	EXPECTED_TYPE_DEFINITION(3414,
			"The symbol \"%s\" is not a type definition."),

	EXPECTED_CLASS(3415, "The symbol \"%s\" is not a class."),

	EXPECTED_PROCESS_DEFINITION(3415,
			"The symbol %s is not a process definition."),

	// ILLEGAL_CONTEXT(
	// "The symbol \"%s\" requires a CML context to be resolved. No such context is present."),

	// PATTERN_TYPE_MISSING(
	// "The pattern \"%s\" should already have a type from its context."),

	// \item[3211:] \texttt{"Expecting <n> arguments"}
	WRONG_NUMBER_OF_ARGUMENTS(3211, "Expecting %s arguments. Actual %s."),

	PATTERN_MISMATCH(3416,
			"Pattern mismatch. Required by type %s, given pattern %s."),

	MIXING_STATE_AND_CHANNEL_IN_SET(
			3417,
			"Mixing channel and state names not allowed. %s contains both channel and state names."),

//	EXPECTED_CHANNEL_OR_STATE(3418,
//			"The symbol \"%s\" is not a channel or state definition."),

	// CONSTRUCTOR_HAS_WRONG_TYPE(
	// "Operation %s has identical name to encloding class, but has wrong type: %s."),

	EXPECTED_LVALUE(3419, "The value \"%s\" is not an assignable expression."),

	EXPECTED_AN_ACTION(3420, "Expecting an action but found %s."),

	ILLEGAL_ACCESS(3421, "Cannot access %s in this context."),

	TOO_MANY_PARAM_PATTERNS(3422,
			"Too many parameter patterns. Type params %s. Patterns %s"),

	TOO_FEW_PARAM_PATTERNS(3423,
			"Too few parameter patterns. Type params %s. Patterns %s"),

	DEFINITION_X_BUT_FOUND_Y(4324,
			"Expected %s definition but found %s definition. Identifier: %s"),

	// 4325 Operation '" %s "' cannot be called in an expression
	COMMUNICATION_NOT_ALLOWED_OVER_UNTYPED_CHANNEL(3425,
			"Communication not allowed over untyped channel: %s."),

	IDENTIFIER_IS_MISSING_ACTION_DEFINITION(3427,
			"Identifier \"%s\" is missing action definition"),

	UNREACHABLE_DEFINITION(3428, "Unreacable definition"),

	CONSTRAINT_MUST_BE_A_BOOLEAN_EXPRESSION(3429,
			"Constraint must be a boolean expression: %s"),

	COMMUNICATION_PARAMETER_TYPE_NOT_COMPATIBLE(
			3430,
			"Non-compatible type %s detected for communication parameter index %s expected %s"),

	CHANNEL_NOT_DECLARED(3431, "Channel %s is not declared"),

	COMMUNICATION_PARAMETER_MISSING(3432,
			"Communication parameter missing at index %s for type %s"),

	COMMUNICATION_TOO_FEW_ARGUMENTS(3433,
			"Communication \"%s\" declared with too few arguments. Actual %s, Expected %s"),

	COMMUNICATION_TOO_MANY_ARGUMENTS(3434,
			"Communication \"%s\" declared with too many arguments. Actual %s, Expected %s"),

	ACTIONS_NOT_ALLOWED_IN_OPERATION(3435, "Actions not allowed in operations"),

	PROCESSES_NOT_ALLOWED_IN_OPERATION(3436,
			"Processes not allowed in operations"), 
			
			//3437 reserved: Action is not in scope
			
			//3438 reserved: Name is not an action
			
			TOO_MANY_CHANNEL_PARAMETERS(3439,"Too many channel parameters for %s"), 
			
			IDENTIFIER_X_NOT_IN_SCOPE(3440,"Identifier %s is not in scope"), 
			
			EXPECTING_A_CHANNEL_OR_CHANSET_DEFINITION(3441,"Expecting a channel or chanset definition but found a %s definition"), 
			
			EXPECTED_STATE_OR_NAMESET_DEFINITION(3442,"Expecting a state or nameset definition but found a %s definition"),
			
			PARALLEL_NAMESETS_MUST_BE_DISJOINT_REPEATED_STATE(3443,"Namesets for parallel actions must be disjoint. Repeated identifiers: %s");

	;
	

	public final int number;
	private String template;

	private TypeErrorMessages(int number, String templateString)
	{
		this.number = number;
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
		return template;
	}
}
