package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.AEnumChansetSetExp;
import eu.compassresearch.core.interpreter.api.CMLContext;
import eu.compassresearch.core.interpreter.runtime.ChannelEvent;
import eu.compassresearch.core.interpreter.values.ProcessValue;

public class ActionEvaluator extends
    QuestionAnswerCMLAdaptor<CMLContext, Value>
  {
    
    private CmlEvaluator parentInterpreter;
    
    public ActionEvaluator(CmlEvaluator parentInterpreter)
      {
        this.parentInterpreter = parentInterpreter;
      }
    
    @Override
    public Value caseASingleGeneralAssignmentStatementAction(
        ASingleGeneralAssignmentStatementAction node, CMLContext question)
        throws AnalysisException
      {
        
        Value expValue = node.getExpression()
            .apply(parentInterpreter, question);
        // TODO Change this to deal with it in general
        AIdentifierStateDesignator id = (AIdentifierStateDesignator) node
            .getStateDesignator();
        
        CMLContext nameContext = (CMLContext) question.locate(id.getName());
        
        if (nameContext == null)
          nameContext = new CMLContext(node.getLocation(), "");
        
        nameContext.put(id.getName(), expValue);
        
        System.out.println(id.getName() + " := " + expValue);
        
        return new ProcessValue(null);
      }
    
    @Override
    public Value caseACommunicationAction(ACommunicationAction node,
        CMLContext question) throws AnalysisException
      {
        
        ChannelEvent ev = question.getCurrentEvent();
        
        if (ev != null
            && ev.getChannelName().equals(node.getIdentifier().getName()))
          {
            // question.resetEvent();
            CMLContext newQuestion = new CMLContext(node.getLocation(), "",
                question);
            newQuestion.setCurrentEvent(new ChannelEvent(""));
            ProcessValue v = (ProcessValue) node.getAction().apply(
                parentInterpreter, newQuestion);
            
            ProcessValue retV = null;
            
            if (v.isReduced())
              retV = v;
            else
              {
                retV = new ProcessValue(v.getOfferedEvents(), null);
                retV.setReduced(true);
                retV.setReducedAction(node.getAction());
              }
            
            return retV;
          } else
          return new ProcessValue(node, question);
        
      }
    
    @Override
    public Value caseASkipAction(ASkipAction node, CMLContext question)
        throws AnalysisException
      {
        
        // System.out.print("<Skip>");
        
        // reduce into null, which is interpret as Skip
        return new ProcessValue(null);
      }
    
    @Override
    public Value caseASequentialCompositionAction(
        ASequentialCompositionAction node, CMLContext question)
        throws AnalysisException
      {
        
        ProcessValue retValue = null;
        
        // First we execute the left action of ';'. We do this until it evolves
        // into Skip ()
        if (node.getLeft() != null)
          {
            ProcessValue leftValue = (ProcessValue) node.getLeft().apply(this,
                question);
            PAction nextAction = getNextAction(leftValue, node.getLeft());
            node.setLeft(nextAction);
            
            // Set retValue with the possible events if leftValue is not Skip.
            if (!leftValue.isSkip())
              retValue = new ProcessValue(leftValue.getOfferedEvents(), null);
          }
        
        /*
         * If retValue == null, then this means that the left side is Skip and
         * the action is now the right action
         */
        if (retValue == null)
          {
            CMLContext newQuestion = new CMLContext(node.getLocation(), "",
                question);
            newQuestion.setCurrentEvent(new ChannelEvent(""));
            ProcessValue rightValue = (ProcessValue) node.getRight().apply(
                this, newQuestion);
            
            PAction nextAction = getNextAction(rightValue, node.getRight());
            // Reduce the sequentialcomposition node to the left action
            retValue = new ProcessValue(nextAction);
          }
        
        return retValue;
      }
    
    /*
     * The internal choice action 'A |~| B' behaves nondeterministically as
     * either A or B. Operational semantics: A silent transition (tau) into
     * either A or B
     */
    @Override
    public Value caseAInternalChoiceAction(AInternalChoiceAction node,
        CMLContext question) throws AnalysisException
      {
        
        /*
         * Since we can pick a random action to continue with we can just always
         * pick the right one. This of course need to be more "randomish"
         * deterministic
         */
        
        return node.getRight().apply(this, question);
      }
    
    @Override
    public Value caseABlockStatementAction(ABlockStatementAction node,
        CMLContext question) throws AnalysisException
      {
        
        // TODO handle declare statements
        // node.getDeclareStatement();
        
        return node.getAction().apply(this, question);
      }
    
    @Override
    public Value caseAGeneralisedParallelismParallelAction(
        AGeneralisedParallelismParallelAction node, CMLContext question)
        throws AnalysisException
      {
        
        ProcessValue processValue = null;
        ProcessValue leftValue = null;
        ProcessValue rightValue = null;
        
        // Evaluate left and right action in parallel. Here parallel means left
        // and then right
        if (node.getLeftAction() != null)
          {
            
            leftValue = (ProcessValue) node.getLeftAction().apply(this,
                question);
            PAction nextAction = getNextAction(leftValue, node.getLeftAction());
            node.setLeftAction(nextAction);
            
          }
        if (node.getRightAction() != null)
          {
            
            rightValue = (ProcessValue) node.getRightAction().apply(this,
                question);
            PAction nextAction = getNextAction(rightValue,
                node.getRightAction());
            node.setRightAction(nextAction);
          }
        
        if ((leftValue != null && !leftValue.isSkip())
            || (rightValue != null && !rightValue.isSkip()))
          {
            // Create the channelset
            PExp exp = node.getChanSetExpression();
            
            // TODO This should not be done like this. This is only a test
            if (!(exp instanceof AEnumChansetSetExp))
              throw new AnalysisException(
                  "For now the the expression here can only be a channelset enumeration");
            
            AEnumChansetSetExp chansetExp = (AEnumChansetSetExp) exp;
            Set<String> chanset = new HashSet<String>();
            for (LexIdentifierToken id : chansetExp.getIdentifiers())
              chanset.add(id.getName());
            
            /*
             * Find all the events that are allowed Event is cs: Both left and
             * right must be able to sync on this Event is not in cs: the event
             * can be carried out independent of the other action
             */
            
            Set<String> leftSyncSet = new HashSet<String>();
            List<ACommunicationAction> goForItEvents = new LinkedList<ACommunicationAction>();
            
            for (ACommunicationAction e : leftValue.getOfferedEvents())
              {
                // Check for event in channelset
                if (chanset.contains(e.getIdentifier().getName()))
                  {
                    leftSyncSet.add(e.getIdentifier().getName());
                  } else
                  { // not in channelset so go ahead
                    goForItEvents.add(e);
                  }
              }
            
            for (ACommunicationAction e : rightValue.getOfferedEvents())
              {
                // Check for event in channelset and leftSync, if not in both
                // its a no go
                if (chanset.contains(e.getIdentifier().getName())
                    && leftSyncSet.contains(e.getIdentifier().getName()))
                  {
                    goForItEvents.add(e);
                  } else
                  { // not in channelset so go ahead
                    goForItEvents.add(e);
                  }
              }
            
            processValue = new ProcessValue(goForItEvents, null);
          } else
          {
            // The operator has reduced into Skip
            processValue = new ProcessValue(null);
          }
        
        return processValue;
      }
    
    // @Override
    // public Value caseAExternalChoiceAction(AExternalChoiceAction node,
    // CMLContext question) throws AnalysisException {
    //
    // ProcessValue retValue = null;
    // if(node.getLeft() != null)
    // {
    // ProcessValue leftValue =
    // (ProcessValue)node.getLeft().apply(this,question);
    // PAction nextAction = getNextAction(leftValue, node.getLeft());
    // node.setLeft(nextAction);
    // if(!leftValue.isSkip())
    // retValue = new ProcessValue(leftValue.getOfferedEvents(),null);
    // }
    //
    //
    //
    // return retValue;
    // }
    
    /*
     * Private Helper Methods
     */
    private PAction getNextAction(ProcessValue processValue,
        PAction currentAction)
      {
        PAction nextAction = null;
        
        if (processValue.isReduced())
          nextAction = processValue.getReducedAction();
        else
          nextAction = currentAction;
        
        return nextAction;
      }
  }
