package eu.compassresearch.core.typechecker;

public enum TypeErrorMessages
  {
    COULD_NOT_DETERMINE_TYPE("Could not determine type for %s."),
    
    INCOMPATIBLE_TYPE("Expected an entity of type %s but %s was found."),
    
    EXPECTED_SUBTYPE_RELATION(
        "Expected to subtype relation to hold for %s and %s but it did not.");
    
    private String template;
    
    private TypeErrorMessages(String templateString)
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
