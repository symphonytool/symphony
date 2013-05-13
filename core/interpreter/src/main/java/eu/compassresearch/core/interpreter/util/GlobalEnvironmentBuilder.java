package eu.compassresearch.core.interpreter.util;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexLocation;
import org.overture.interpreter.runtime.StateContext;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.CmlContextFactory;
import eu.compassresearch.core.interpreter.values.ProcessObjectValue;
import eu.compassresearch.core.interpreter.visitors.CmlDefinitionVisitor;

public class GlobalEnvironmentBuilder extends AnalysisCMLAdaptor
  {
    /**
	 * 
	 */
    private static final long        serialVersionUID   = 493918199975006733L;
    private AProcessDefinition       lastDefinedProcess = null;
    private StateContext          globalState        = null;
    private List<AProcessDefinition> globalProcesses	= null;
    
    private CmlDefinitionVisitor cmlDefEval = new CmlDefinitionVisitor(); 
    
    public GlobalEnvironmentBuilder(List<PSource> sources)
    {
    	BuildGlobalEnvironment(sources);
    }

    private void BuildGlobalEnvironment(List<PSource> sources)
      {
    	//Make a state
    	globalState = CmlContextFactory.newStateContext(new LexLocation(), "Global Context");
    	globalProcesses = new LinkedList<AProcessDefinition>();
    	
    	/*
    	 * Go through all the top level paragraphs in all the source files
    	 * And add them to the global state
    	 * FIXME: access modifier are not considered, should they?
    	 */
        for (PSource source : sources)
          {
            for (PDefinition def : source.getParagraphs())
              {
                try
                  {
                	globalState.putAllNew(def.apply(cmlDefEval,globalState));
                  }
                // this should not happen when typechecked!!
                catch (AnalysisException ex)
                  {
                    ex.printStackTrace();
                  }
              }
          }
        
        //Search though all the found processes and add them to the process list
        for(Value val : globalState.values())
    	{
    		if( val instanceof ProcessObjectValue)
    		{
    			AProcessDefinition pdef = ((ProcessObjectValue) val).getProcessDefinition(); 
    			
    			globalProcesses.add(pdef);
    			
    			if(lastDefinedProcess == null)
    				lastDefinedProcess = pdef;
    			else if(pdef.getLocation().getStartLine() > lastDefinedProcess.getLocation().getStartLine())
    				lastDefinedProcess = pdef;
    		}
    	}
    	
      }
    
    /**
     * Returns the last defined process definition in the sources
     * FIXME: For more than one source file this is not well defined
     * @return
     */
    public AProcessDefinition getLastDefinedProcess()
    {
    	return lastDefinedProcess;
    }

    public StateContext getGlobalContext()
    {
    	return globalState;
    }
    
    public List<AProcessDefinition> getGlobalProcesses()
    {
    	return globalProcesses;
    }

  }
