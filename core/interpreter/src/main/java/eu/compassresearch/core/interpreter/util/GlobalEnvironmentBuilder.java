package eu.compassresearch.core.interpreter.util;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.StateContext;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatEnvironment;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.runtime.CmlStateContext;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.values.CmlValueFactory;

public class GlobalEnvironmentBuilder extends AnalysisCMLAdaptor
  {
    /**
	 * 
	 */
    private static final long        serialVersionUID   = 493918199975006733L;
    private List<PDefinition>        globalDefs	        = new LinkedList<PDefinition>();
    private AProcessDefinition       lastDefinedProcess = null;
    private CmlStateContext          globalState        = null;
    
    public GlobalEnvironmentBuilder(List<PSource> sources)
    {
    	BuildGlobalEnvironment(sources);
    }

    private void BuildGlobalEnvironment(List<PSource> sources)
      {
    	globalState = new CmlStateContext(new LexLocation(), "GlobalContext");
    	
        for (PSource source : sources)
          {
            for (PDefinition def : source.getParagraphs())
              {
                try
                  {
                    def.apply(this);
                  }
                // this should not happen when typechecked!!
                catch (AnalysisException ex)
                  {
                    ex.printStackTrace();
                  }
              }
          }
      }
    
    public AProcessDefinition getLastDefinedProcess()
    {
    	return lastDefinedProcess;
    }

    public Environment getGlobalEnvironment()
    {
    	return new FlatEnvironment(globalDefs);
    }

    public CmlStateContext getGlobalContext()
    {
    	return globalState;
    }
    
    public List<AProcessDefinition> getGlobalProcesses()
    {
    	List<AProcessDefinition> processes = new LinkedList<AProcessDefinition>();
    	
    	for(PDefinition def : globalDefs)
    	{
    		if(def instanceof AProcessDefinition)
    			processes.add((AProcessDefinition)def);
    	}
    		
    	return processes;
    }
    
    
    @Override
    public void caseAClassDefinition(AClassDefinition node)
        throws AnalysisException
      {
        
        // TODO: check access specifier
    	globalDefs.add(node);
        //env..put(node.getName(), );
        
      }
    
    @Override
    public void caseAProcessDefinition(AProcessDefinition node)
        throws AnalysisException
      {
        globalDefs.add(node);
        globalState.putNew(new NameValuePair(node.getName(), 
        		CmlValueFactory.createProcessObjectValue(node)));
        lastDefinedProcess = node;
      }
    
    @Override
    public void caseAChansetDefinition(AChansetDefinition node)
        throws AnalysisException
      {
    	globalDefs.add(node);
      }
    
    @Override
    public void caseATypesDefinition(ATypesDefinition node)
        throws AnalysisException
      {
        
        for (PDefinition typeDef : node.getTypes())
          {
        	globalDefs.add(typeDef);
            //env.put(typeDef.getName(), (ATypeDefinition)typeDef);
          }
      }
    
    @Override
    public void caseAFunctionsDefinition(
        AFunctionsDefinition node) throws AnalysisException
      {
        
        for (PDefinition functionDef : node.getFunctionDefinitions())
          {
        	//TODO add functions here to the context
        	//globalState.putNew(new NameValuePair(functionDef.getName(),new FunctionValue(def, precondition, postcondition, freeVariables)));
          }
      }

    @Override
    public void caseAChannelsDefinition(AChannelsDefinition node) throws AnalysisException
    {

    	for (AChannelNameDefinition cnd : node.getChannelNameDeclarations())
    	{
    		for (LexIdentifierToken channelName : cnd.getSingleType().getIdentifiers())
    		{
    			LexNameToken name = new LexNameToken("|CHANNELS|", channelName);
    			globalState.putNew(new NameValuePair(name, new CMLChannelValue(cnd.getSingleType().getType(),name)));
    			//globalDefs.add(e) addChannel(channelName, cnd.getSingleType().getType());
    		}
    	}
    }

  }
