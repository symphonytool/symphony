package eu.compassresearch.core.parser;

public enum ParserErrorMessage
{
//  COULD_NOT_DETERMINE_TYPE("Could not determine type for %s."),
//  
//  INCOMPATIBLE_TYPE("Expected an entity of type %s but %s was found."),
//  
//  EXPECTED_SUBTYPE_RELATION(
//      "This code assumes that %s is a sub type of %s, which is not true."),
//  
//  NAMED_TYPE_UNDEFINED("The type %s is undefined.")
	
	OPERATION_NAMES_ARE_NOT_EQUAL("The operation definition name %s does not match the body name %s")
  
  ;
  
  private String template;
  
  private ParserErrorMessage(String templateString)
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