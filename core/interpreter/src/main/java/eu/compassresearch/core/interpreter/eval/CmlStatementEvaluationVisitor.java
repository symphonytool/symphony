package eu.compassresearch.core.interpreter.eval;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;
import org.overture.interpreter.assistant.pattern.PPatternAssistantInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.PatternMatchException;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;
import org.overture.interpreter.values.VoidValue;

import eu.compassresearch.ast.actions.AAssignmentCallStatementAction;
import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.AElseIfStatementAction;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.runtime.CmlContextFactory;
import eu.compassresearch.core.interpreter.util.CmlActionAssistant;
import eu.compassresearch.core.interpreter.values.CmlOperationValue;

@SuppressWarnings("serial")
public class CmlStatementEvaluationVisitor extends AbstractEvaluationVisitor {

	
	@Override
	public CmlBehaviourSignal caseAReturnStatementAction(
			AReturnStatementAction node, Context question)
			throws AnalysisException {

		LexNameToken callReturnName = new LexNameToken("|CALL|","|CALLRETURN|",new LexLocation());
		
		Context nameContext = (Context)question.locate(callReturnName);
		
		if(node.getExp() != null)
			nameContext.put(callReturnName, node.getExp().apply(cmlEvaluator,question));
		else
			nameContext.put(callReturnName,new VoidValue());
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * The action inside a block is executed directly, since it has no semantic meaning.
	 */
	@Override
	public CmlBehaviourSignal caseABlockStatementAction(
			ABlockStatementAction node, Context question)
			throws AnalysisException {
		
		Context blockContext = CmlContextFactory.newContext(node.getLocation(), "block context", question);
		
		//add the assignments defs to the context
		if(node.getDeclareStatement() != null)
		{
			for(PDefinition def : node.getDeclareStatement().getAssignmentDefs())
					def.apply(cmlEvaluator,blockContext);
		}
		
		//FIXME this should be done differently. The whole CmlEvalutaor structure is bad
		for(Entry<LexNameToken,Value> p : blockContext.entrySet())
		{
			blockContext.put(p.getKey(),p.getValue().getUpdatable(null));
		}
		
		
		pushNext(node.getAction(), blockContext); 
		return CmlBehaviourSignal.EXEC_SUCCESS;
		//return node.getAction().apply(this,blockContext);
	}
	
	@Override
	public CmlBehaviourSignal caseAIfStatementAction(AIfStatementAction node,
			Context question) throws AnalysisException {

		try
		{
    		if (node.getIfExp().apply(cmlEvaluator,question).boolValue(question))
    		{
    			pushNext(node.getThenStm(), question);
    		}
    		else
    		{
    			boolean foundElseIf = false;
    			for (AElseIfStatementAction elseif: node.getElseIf())
    			{
    				if(elseif.getElseIf().apply(cmlEvaluator,question).boolValue(question))
    				{
    					pushNext(elseif.getThenStm(), question);
    					foundElseIf = true;
    					break;
    				}
    			}

    			if (node.getElseStm() != null && !foundElseIf)
    			{
    				pushNext(node.getElseStm(), question);
    			}

    			return CmlBehaviourSignal.EXEC_SUCCESS;
    		}
        }
        catch (ValueException e)
        {
        	//TODO find a better way to report errors
        	e.printStackTrace();
        	//return VdmRuntimeError.abort(node.getLocation(),e);
        }
		
		return CmlBehaviourSignal.FATAL_ERROR;
	}
	
	/* 
	 * FIXME This is a first attempt, arguments and returns are still not supported on void functions.
	 * (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseACallStatementAction(eu.compassresearch.ast.actions.ACallStatementAction, java.lang.Object)
	 */
	@Override
	public CmlBehaviourSignal caseACallStatementAction(
			ACallStatementAction node, Context question)
			throws AnalysisException {

		//first find the operation value in the context
		CmlOperationValue opVal = (CmlOperationValue)question.check(node.getName()); 
		
		//put return value in upper context
		question.putNew(new NameValuePair(new LexNameToken("|CALL|","|CALLRETURN|",new LexLocation()), new UndefinedValue()));
		
		ValueList argValues = new ValueList();

		//evaluate the arguments
		for (PExp arg: node.getArgs())
		{
			argValues.add(arg.apply(cmlEvaluator,question));
		}
		
		// Note args cannot be Updateable, so we convert them here. This means
		// that TransactionValues pass the local "new" value to the far end.
		ValueList constValues = argValues.getConstant();

		if (opVal.getBody() == null)
		{
			opVal.abort(4066, "Cannot call implicit operation: " + name, question);
		}
		
		//TODO maybe this context should be a different one
		Context callContext = CmlContextFactory.newObjectContext(node.getLocation(), "CML Operation Call", question, question.getSelf());
		
		if (argValues.size() != opVal.getParamPatterns().size())
		{
			opVal.abort(4068, "Wrong number of arguments passed to " + name.name, question);
		}
		
		ListIterator<Value> valIter = argValues.listIterator();
		Iterator<PType> typeIter = opVal.getType().getParameters().iterator();
		NameValuePairMap args = new NameValuePairMap();

		for (PPattern p : opVal.getParamPatterns())
		{
			try
			{
				// Note values are assumed to be constant, as enforced by eval()
				Value pv = valIter.next().convertTo(typeIter.next(), question);

				for (NameValuePair nvp : PPatternAssistantInterpreter.getNamedValues(p,pv, question))
				{
					Value v = args.get(nvp.name);

					if (v == null)
					{
						args.put(nvp);
					}
					else	// Names match, so values must also
					{
						if (!v.equals(nvp.value))
						{
							opVal.abort(4069,	"Parameter patterns do not match arguments", question);
						}
					}
				}
			}
			catch (PatternMatchException e)
			{
				opVal.abort(e.number, e, question);
			}
		}
		
		// Note: arg name/values hide member values
		callContext.putAll(args);
		
		//TODO add the arg patterns with the results to the context here
		
		
		pushNext(opVal.getBody(), callContext);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
		
	}
	
	/**
	 * Assignment - section 7.5.1 D23.2
	 * 
	 */
	@Override
	public CmlBehaviourSignal caseASingleGeneralAssignmentStatementAction(
			ASingleGeneralAssignmentStatementAction node, Context question)
					throws AnalysisException {
//		question.putNew(new NameValuePair(new LexNameToken("", new LexIdentifierToken("a", false, new LexLocation())), new IntegerValue(2)));
		Value expValue = node.getExpression().apply(cmlEvaluator,question);
		
		//TODO Change this to deal with it in general
		LexNameToken stateDesignatorName = CmlActionAssistant.extractNameFromStateDesignator(node.getStateDesignator(),question);

		Value oldVal = question.check(stateDesignatorName);
		oldVal.set(node.getLocation(), expValue, question);
		
		//System.out.println(stateDesignatorName + " = " + expValue);
		
		//now this process evolves into Skip
		pushNext(new ASkipAction(node.getLocation(),new AActionType()), question);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * Non deterministic if randomly chooses between options whose guard are evaluated to true
	 */
	@Override
	public CmlBehaviourSignal caseANonDeterministicIfStatementAction(
			ANonDeterministicIfStatementAction node, Context question)
			throws AnalysisException {

		List<ANonDeterministicAltStatementAction> availableAlts = CmlActionAssistant.findAllTrueAlts(
				node.getAlternatives(),question,cmlEvaluator);
		//if we got here we already now that the must at least be one available action
		//so this should pose no risk of exception
		pushNext(availableAlts.get(rnd.nextInt(availableAlts.size())).getAction(),question);
		 
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}

	/**
	 * 
	 * //TODO no semantics defined, resolve this!
	 */
	@Override
	public CmlBehaviourSignal caseANonDeterministicDoStatementAction(
			ANonDeterministicDoStatementAction node, Context question)
			throws AnalysisException {

		List<ANonDeterministicAltStatementAction> availableAlts = CmlActionAssistant.findAllTrueAlts(
				node.getAlternatives(),question,cmlEvaluator);
		
		
		if(availableAlts.size() > 0)
		{
			//first we push the do node on the execution stack to get it sequentially composed with the
			//picked alternative
			pushNext(node, question);		
			//if we got here we already now that the must at least be one available action
			//so this should pose no risk of exception
			pushNext(availableAlts.get(rnd.nextInt(availableAlts.size())).getAction(),question);
		}
		else
			pushNext(new ASkipAction(), question);
			
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * 
	 * //TODO no semantics defined, resolve this!
	 */
	@Override
	public CmlBehaviourSignal caseAWhileStatementAction(
			AWhileStatementAction node, Context question)
			throws AnalysisException {

		if(node.getCondition().apply(cmlEvaluator,question).boolValue(question))
		{
			//first we push the while node so that we get back to this point
			pushNext(node, question);
			//then we push the first action of the loop
			pushNext(node.getAction(), question);
		}
		else
		{
			//if the condition is false then the While evolves into Skip
			pushNext(new ASkipAction(), question);
		}
		
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public CmlBehaviourSignal caseAAssignmentCallStatementAction(
			AAssignmentCallStatementAction node, Context question)
			throws AnalysisException {
	
		//put return value in upper context
		Value retValue = question.check(new LexNameToken("|CALL|","|CALLRETURN|",new LexLocation()));
		
		//the call must be made
		if(retValue == null || retValue instanceof UndefinedValue)
		{
			pushNext(node, question);
			pushNext(node.getCall(), question);
			
		}
		else
		{
			//TODO Change this to deal with it in general
			LexNameToken stateDesignatorName = CmlActionAssistant.extractNameFromStateDesignator(node.getDesignator(),question);
			
			Value oldVal = question.check(stateDesignatorName);
			oldVal.set(node.getLocation(), retValue, question);
		}
		
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
}
