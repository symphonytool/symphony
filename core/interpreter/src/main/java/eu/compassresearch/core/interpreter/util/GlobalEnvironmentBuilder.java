package eu.compassresearch.core.interpreter.util;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexLocation;
import org.overture.interpreter.runtime.StateContext;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.eval.CmlDefinitionEvaluator;
import eu.compassresearch.core.interpreter.eval.CmlValueEvaluator;
import eu.compassresearch.core.interpreter.values.ProcessObjectValue;

public class GlobalEnvironmentBuilder extends AnalysisCMLAdaptor
  {
    /**
	 * 
	 */
    private static final long        serialVersionUID   = 493918199975006733L;
    private AProcessDefinition       lastDefinedProcess = null;
    private StateContext          globalState        = null;
    private List<AProcessDefinition> globalProcesses	= null;
    
    private CmlDefinitionEvaluator cmlDefEval = new CmlDefinitionEvaluator(); 
    
    public GlobalEnvironmentBuilder(List<PSource> sources)
    {
    	BuildGlobalEnvironment(sources);
    }

    private void BuildGlobalEnvironment(List<PSource> sources)
      {
    	globalState = new StateContext(new LexLocation(), "GlobalContext");
    	globalState.setThreadState(null, CPUValue.vCPU);
    	globalProcesses = new LinkedList<AProcessDefinition>();
    	
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
        
        for(Value val : globalState.values())
    	{
    		if( val instanceof ProcessObjectValue)
    		{
    			AProcessDefinition pdef = ((ProcessObjectValue) val).getProcessDefinition(); 
    			
    			globalProcesses.add(pdef);
    			
    			if(lastDefinedProcess == null)
    				lastDefinedProcess = pdef;
    			else if(pdef.getLocation().startLine > lastDefinedProcess.getLocation().startLine)
    				lastDefinedProcess = pdef;
    		}
    	}
    	
      }
    
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
