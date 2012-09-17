package eu.compassresearch.core.typechecker;


public class TypeCheckInfo
  {
    
    public final Environment env;
    
    public TypeCheckInfo(Environment env)
      {
        this.env = env;
      }
    
    public TypeCheckInfo()
      {
        env = new Environment();
      }
  }
