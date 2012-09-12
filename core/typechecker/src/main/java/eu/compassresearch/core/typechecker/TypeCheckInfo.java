package eu.compassresearch.core.typechecker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.typechecker.NameScope;

import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.types.AIntNumericBasicType;
import eu.compassresearch.ast.types.ANatNumericBasicType;
import eu.compassresearch.ast.types.ANatOneNumericBasicType;
import eu.compassresearch.ast.types.PType;

/**
 * TypeCheckInfo tracks the three scopes we care about in CML. These are
 * Variables - mapping from variable name to their Definition (and type) Types -
 * mapping from type-name to the actual PType Channels - mapping from
 * channel-name to its declaration (and carried type of any)
 * 
 * Creating a new scope is the responsibility of this class.
 * 
 * @author rwl
 * 
 */
public class TypeCheckInfo implements TypeCheckQuestion
  {
    
    private final Environment<PDefinition>  variables;
    private final Environment<PType>        types;
    private final Environment<PDeclaration> channels;
    
    private TypeCheckInfo(Environment<PDefinition> vars,
        Environment<PType> types, Environment<PDeclaration> channels)
      {
        this.variables = new Environment<PDefinition>(vars);
        this.types = new Environment<PType>(types);
        this.channels = new Environment<PDeclaration>(channels);
      }
    
    public TypeCheckInfo()
      {
        variables = new Environment<PDefinition>();
        types = new Environment<PType>();
        channels = new Environment<PDeclaration>();
      }
    
    @Override
    public PType lookupType(LexIdentifierToken ident)
      {
        return types.lookupName(ident);
      }
    
    @Override
    public void addType(LexIdentifierToken ident, PType type)
      {
        this.types.put(ident, type);
      }
    
    @Override
    public PDeclaration lookupChannel(LexIdentifierToken ident)
      {
        return channels.lookupName(ident);
      }
    
    @Override
    public void addChannel(LexIdentifierToken ident, PDeclaration channel)
      {
        channels.put(ident, channel);
      }
    
    @Override
    public PDefinition lookupVariable(LexIdentifierToken ident)
      {
        return variables.lookupName(ident);
      }
    
    @Override
    public void addVariable(LexIdentifierToken ident, PDefinition variable)
      {
        variables.put(ident, variable);
      }
    
    @Override
    public TypeCheckQuestion newScope()
      {
        // Variables are scoped, types and channels are global (for now at
        // least)
        TypeCheckInfo res = new TypeCheckInfo(this.variables, types, channels);
        return res;
      }
    
    @Override
    public org.overture.typechecker.Environment getOvertureEnvironment()
    
      {
        final org.overture.typechecker.Environment ve = variables
            .getOvertureEnv();
        final org.overture.typechecker.Environment te = types.getOvertureEnv();
        
        class CombinedEnv extends org.overture.typechecker.Environment
          {
            
            public CombinedEnv()
              {
                super(null);
              }
            
            @Override
            public org.overture.ast.definitions.PDefinition findName(
                LexNameToken name, NameScope scope)
              {
                return ve.findName(name, scope);
              }
            
            @Override
            public org.overture.ast.definitions.PDefinition findType(
                LexNameToken name, String fromModule)
              {
                return te.findType(name, fromModule);
              }
            
            @Override
            public AStateDefinition findStateDefinition()
              {
                return null;
              }
            
            @Override
            public SClassDefinition findClassDefinition()
              {
                return null;
              }
            
            @Override
            public boolean isStatic()
              {
                return false;
              }
            
            @Override
            public void unusedCheck()
              {
                // TODO: Check this guy is invoked and make appropriate action
                throw new RuntimeException(
                    "Somebody better do something about this.");
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
                return ve.findMatches(name);
              }
            
          }
        ;
        return new CombinedEnv();
      }
    
    private static final Map<Class<?>, List<Class<?>>> fixedSubTypeRelations;
    static
      {
        fixedSubTypeRelations = new HashMap<Class<?>, List<Class<?>>>();
        fixedSubTypeRelations.put(AIntNumericBasicType.class,
            Arrays.asList(new Class<?>[] { ANatNumericBasicType.class }));
        fixedSubTypeRelations.put(ANatNumericBasicType.class,
            Arrays.asList(new Class<?>[] { ANatOneNumericBasicType.class }));
      }
    
    private static boolean checkClosureOnFixedTypeRelation(Class<?> top,
        Class<?> bottom)
      {
        if (top == bottom)
          return true;
        
        if (fixedSubTypeRelations.containsKey(top))
          {
            boolean f = false;
            for (Class<?> candidate : fixedSubTypeRelations.get(top))
              f |= checkClosureOnFixedTypeRelation(candidate, bottom);
            return f;
          }
        
        return false;
      }
    
    @Override
    public boolean isFirstSubTypeOfSecond(PType first, PType second)
      {
        return first.getClass() == second.getClass()
            || checkClosureOnFixedTypeRelation(second.getClass(),
                first.getClass());
      }
    
  }
