package eu.compassresearch.core.typechecker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.typechecker.NameScope;

import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.lex.LexLocation;
import eu.compassresearch.transformation.CmlAstToOvertureAst;

public class Environment
  {
    
    /**
     * The enclosing Environment to search if a symbol is not found in this
     */
    protected final Environment                  outer;
    
    // private state
    private Map<LexIdentifierToken, PDefinition> map;
    
    /**
     * Create an Empty top level Environment.
     */
    public Environment()
      {
        this(null);
      }
    
    /**
     * Create an enclosed Environment.
     * 
     * @param outer
     *          - The enclosing environment
     */
    public Environment(Environment outer)
      {
        this.outer = outer;
        this.map = new HashMap<LexIdentifierToken, PDefinition>();
      }
    
    /**
     * Lookup the given identifier/name to find its definition.
     * 
     * @param name
     *          - The name of the identifier for which we need its definition.
     * 
     * @return null if the name is unresolved.
     */
    public PDefinition lookupName(LexIdentifierToken name)
      {
        PDefinition def = map.get(name);
        if (def == null)
          if (outer != null)
            def = outer.lookupName(name);
        return def;
      }
    
    /**
     * Associate a declaration with a type in this environment.
     * 
     * 
     * @param delc
     *          - Declaration to add type for
     * @param type
     *          - The type for decl in this environment.
     */
    public void put(LexIdentifierToken name, PDefinition type)
      {
        map.put(name, type);
      }
    
    private static org.overture.ast.definitions.PDefinition translateDefinition(
        PDefinition cmlDef)
      {
        CmlAstToOvertureAst transform = new CmlAstToOvertureAst();
        try
          {
            org.overture.ast.definitions.PDefinition ovtDef = (org.overture.ast.definitions.PDefinition) transform
                .defaultPDefinition(cmlDef);
            return ovtDef;
          } catch (eu.compassresearch.ast.analysis.AnalysisException ae)
          {
            ae.printStackTrace();
            return null;
          }
      }
    
    private PDefinition lookupFromOvtName(LexNameToken name)
      {
        boolean old = false;
        LexLocation location = null;
        String stringName = name.getName();
        LexIdentifierToken id = new LexIdentifierToken(stringName, old,
            location);
        PDefinition cmlDef = lookupName(id);
        return cmlDef;
      }
    
    /**
     * This method creates an Overture Typing environment backed by this Cml
     * environment.
     * 
     * Whenever a VDM/Overture name is to be resolved we find the symbol with an
     * identical name in this Cml Environment and translates the definition to
     * Overture. The translated definition is returned.
     * 
     * @return An Overture typing environment Back by this instance of a Cml
     *         environment.
     */
    public org.overture.typechecker.Environment getOvertureEnv()
      {
        class CmlOvertureEnvironment extends
            org.overture.typechecker.Environment
          {
            
            private CmlOvertureEnvironment()
              {
                super(null);
              }
            
            @Override
            public org.overture.ast.definitions.PDefinition findName(
                LexNameToken name, NameScope scope)
              {
                PDefinition cmlDef = lookupFromOvtName(name);
                
                if (cmlDef == null)
                  return null;
                
                return translateDefinition(cmlDef);
              }
            
            @Override
            public org.overture.ast.definitions.PDefinition findType(
                LexNameToken name, String fromModule)
              {
                PDefinition cmlDef = lookupFromOvtName(name);
                
                if (cmlDef == null)
                  return null;
                
                return translateDefinition(cmlDef);
              }
            
            @Override
            public AStateDefinition findStateDefinition()
              {
                throw new UnsupportedOperationException("This we cannot do");
              }
            
            @Override
            public SClassDefinition findClassDefinition()
              {
                throw new UnsupportedOperationException("This we cannot do");
              }
            
            @Override
            public boolean isStatic()
              {
                return false;
              }
            
            @Override
            public void unusedCheck()
              {
                
              }
            
            @Override
            public boolean isVDMPP()
              {
                return true;
              }
            
            @Override
            public boolean isSystem()
              {
                return false;
              }
            
            @Override
            public Set<org.overture.ast.definitions.PDefinition> findMatches(
                LexNameToken name)
              {
                Set<org.overture.ast.definitions.PDefinition> matchesFound = new HashSet<org.overture.ast.definitions.PDefinition>();
                
                PDefinition cmlDef = lookupFromOvtName(name);
                if (cmlDef != null)
                  {
                    matchesFound.add(translateDefinition(cmlDef));
                  }
                
                return matchesFound;
              }
            
          }
        ;
        
        return new CmlOvertureEnvironment();
      }
  }
