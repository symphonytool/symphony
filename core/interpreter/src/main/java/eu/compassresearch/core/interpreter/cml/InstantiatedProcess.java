package eu.compassresearch.core.interpreter.cml;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.core.interpreter.eval.ProcessAlphabetCollector;

public class InstantiatedProcess implements CMLProcessOld
  {
    
    private AProcessDefinition processDefinition;
    private CMLProcessOld         process;
    private Set<String>        channelSet;
    
    public InstantiatedProcess(AProcessDefinition processDefinition,
        CMLProcessOld process)
      {
        this.processDefinition = processDefinition;
        this.process = process;
        
        try
          {
            ProcessAlphabetCollector ec = new ProcessAlphabetCollector();
            this.processDefinition.apply(ec);
            channelSet = ec.getChannelSet();
          } catch (AnalysisException e)
          {
            e.printStackTrace();
          }
      }
    
    @Override
    public List<ACommunicationAction> WaitForEventOffer()
      {
        
        return this.process.WaitForEventOffer();
      }
    
    @Override
    public void eventOccured(CMLChannelEvent event)
      {
        
        // System.out.println("Event on instance of process " +
        // processDefinition.getName().getName());
        this.process.eventOccured(event);
        
      }
    
    @Override
    public Set<String> getChannelSet()
      {
        
        return new HashSet<String>(this.channelSet);
      }
    
    @Override
    public void start(CMLSupervisorEnvironment sve)
      {
        
        this.process.start(sve);
        // System.out.println("Instance of process " +
        // processDefinition.getName().getName() + " started");
      }
    
    @Override
    public boolean isSkip()
      {
        
        return this.process.isSkip();
      }
    
    @Override
    public String getRemainingInterpretationState(boolean expand)
      {
        if (expand)
          return this.processDefinition.getName() + " = ("
              + this.process.getRemainingInterpretationState(expand) + ")";
        else
          return this.processDefinition.getName() + "";
      }
    
  }
