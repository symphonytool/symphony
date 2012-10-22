package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.Set;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.PDefinition;
import eu.compassresearch.ast.expressions.AEnumChansetSetExp;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AInstantiationProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.core.interpreter.api.CMLContext;
import eu.compassresearch.core.interpreter.scheduler.CMLProcess;
import eu.compassresearch.core.interpreter.scheduler.InstantiatedProcess;
import eu.compassresearch.core.interpreter.scheduler.ProcessThread;
import eu.compassresearch.core.interpreter.scheduler.SequentialCompositionProcess;
import eu.compassresearch.core.interpreter.scheduler.SynchronousParallelismProcess;

@SuppressWarnings("serial")
public class ProcessEvaluator extends
    QuestionAnswerAdaptor<CMLContext, CMLProcess>
  {
    
    private CmlEvaluator parentInterpreter;
    
    public ProcessEvaluator(CmlEvaluator parentInterpreter)
      {
        this.parentInterpreter = parentInterpreter;
      }
    
    @Override
    public CMLProcess caseAInstantiationProcess(AInstantiationProcess node,
        CMLContext question) throws AnalysisException
      {
        
        // Grab the definition from the name
        AProcessDefinition processDefinition = node.getProcessDefinition();
        
        // TODO Initialize the process state
        CMLContext inner = new CMLContext(node.getLocation(), question);
        // TODO Add the process arguments
        CMLProcess instantiatedProcess = processDefinition.getProcess().apply(
            this, inner);
        CMLProcess process = new InstantiatedProcess(processDefinition,
            instantiatedProcess);
        return process;
      }
    
    @Override
    public CMLProcess caseAStateProcess(AStateProcess node, CMLContext question)
        throws AnalysisException
      {
        
        // TODO Add state, value, etc to the corresponding processValue and
        // context
        
        for (PDefinition def : node.getDefinitionParagraphs())
          {
            def.apply(this.parentInterpreter, question);
            // question.put(def.getName(), def.getType().g);
          }
        ProcessThread pt = new ProcessThread(question, node);
        
        return pt;
      }
    
    @Override
    public CMLProcess caseASynchronousParallelismProcess(
        ASynchronousParallelismProcess node, CMLContext question)
        throws AnalysisException
      {
        
        // question.getProcessThread().waitForSchedule();
        
        Set<String> allChannels = new HashSet<String>();// CmlRuntime.getGlobalEnvironment().getGlobalChannelNames();
        
        if (allChannels == null)
          throw new AnalysisException(
              "No channels are defined to synchronize on");
        
        CMLProcess leftProcess = node.getLeft().apply(this, question);
        CMLProcess rightProcess = node.getRight().apply(this, question);
        
        return new SynchronousParallelismProcess(leftProcess, rightProcess,
            allChannels);
      }
    
    @Override
    public CMLProcess caseAGeneralisedParallelismProcess(
        AGeneralisedParallelismProcess node, CMLContext question)
        throws AnalysisException
      {
        
        PExp exp = node.getChansetExpression();
        
        // TODO This should not be done like this. This is only a test
        if (!(exp instanceof AEnumChansetSetExp))
          throw new AnalysisException(
              "For now the the expression here can only be a channelset enumeration");
        
        AEnumChansetSetExp chansetExp = (AEnumChansetSetExp) exp;
        
        Set<String> chanset = new HashSet<String>();
        
        for (LexIdentifierToken id : chansetExp.getIdentifiers())
          chanset.add(id.getName());
        
        CMLProcess leftProcess = node.getLeft().apply(this, question);
        CMLProcess rightProcess = node.getRight().apply(this, question);
        
        return new SynchronousParallelismProcess(leftProcess, rightProcess,
            chanset);
      }
    
    @Override
    public CMLProcess caseASequentialCompositionProcess(
        ASequentialCompositionProcess node, CMLContext question)
        throws AnalysisException
      {
        
        CMLProcess leftProcess = node.getLeft().apply(this, question);
        CMLProcess rightProcess = node.getRight().apply(this, question);
        
        CMLProcess process = new SequentialCompositionProcess(leftProcess,
            rightProcess);
        
        return process;
      }
  }
