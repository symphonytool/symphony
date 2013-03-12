package eu.compassresearch.core.interpreter.eval;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AVariableExp;
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
import org.overture.interpreter.values.NameValuePairList;
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
import eu.compassresearch.ast.actions.ALetStatementAction;
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

		Context nameContext = (Context)question.locate(CmlOperationValue.ReturnValueName());
		if(nameContext != null)
		{
			if(node.getExp() != null)
				nameContext.put(CmlOperationValue.ReturnValueName(), node.getExp().apply(cmlValueEvaluator,question));
			else
				nameContext.put(CmlOperationValue.ReturnValueName(),new VoidValue());
		}
		
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
			{
				NameValuePair nvp = def.apply(cmlDefEvaluator,question).get(0);
				blockContext.put(nvp.name, nvp.value.getUpdatable(null));
			}
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
    		if (node.getIfExp().apply(cmlValueEvaluator,question).boolValue(question))
    		{
    			pushNext(node.getThenStm(), question);
    			
    			return CmlBehaviourSignal.EXEC_SUCCESS;
    		}
    		else
    		{
    			boolean foundElseIf = false;
    			for (AElseIfStatementAction elseif: node.getElseIf())
    			{
    				if(elseif.getElseIf().apply(cmlValueEvaluator,question).boolValue(question))
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
				
		//evaluate all the arguments
		ValueList argValues = new ValueList();
		for (PExp arg: node.getArgs())
		{
			argValues.add(arg.apply(cmlValueEvaluator,question));
		}
		
		// Note args cannot be Updateable, so we convert them here. This means
		// that TransactionValues pass the local "new" value to the far end.
		ValueList constValues = argValues.getConstant();
						
		//TODO maybe this context should be a different one
		//Create a new context to perform the operation call 
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
		
		
		
		
		//invoke the pre condition
		if(opVal.precondition != null)
		{
			ValueList preArgs = new ValueList(argValues);
			
			preArgs.add(question.getSelf());
			
			//Context preconditionContext = CmlContextFactory.newContext(node.getLocation(), "Precondition context", question);
			
			opVal.precondition.eval(node.getLocation(), preArgs, question).boolValue(question);
			opVal.abort(4060, "precondition violated for " + node.getName(), question);
		}
		if (opVal.getBody() == null)
		{
			opVal.abort(4066, "Cannot call implicit operation: " + name, question);
		}
		
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
		Value expValue = node.getExpression().apply(cmlValueEvaluator,question);
		
		//TODO Change this to deal with it in general
		//LexNameToken stateDesignatorName = CmlActionAssistant.extractNameFromStateDesignator(node.getStateDesignator(),question);

		Value oldVal = node.getStateDesignator().apply(cmlValueEvaluator,question);
		
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
				node.getAlternatives(),question,cmlValueEvaluator);
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
				node.getAlternatives(),question,cmlValueEvaluator);
		
		
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

		if(node.getCondition().apply(cmlValueEvaluator,question).boolValue(question))
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
	
		//put return value in a new context
		Context resultContext = CmlContextFactory.newContext(node.getLocation(), "Call Result Context", question);
		//put return value in upper context if the parent is a AAssignmentCallStatementAction
		resultContext.putNew(new NameValuePair(CmlOperationValue.ReturnValueName(), new UndefinedValue()));
				
		//To access the result we put it in a Value named "|CALL|.|CALLRETURN|" this can never be created
		//in a cml model. This is a little ugly but it works and statys until something better comes up.
		AVariableExp varExp = new AVariableExp(node.getType(), 
				node.getCall().getLocation(),
				CmlOperationValue.ReturnValueName(), 
				"", 
				null);
		//Next we create the assignment statement with the expressions that graps the result 
		ASingleGeneralAssignmentStatementAction assignmentNode =
				new ASingleGeneralAssignmentStatementAction(node.getLocation(),	
						node.getType(),
						node.getDesignator(),
						varExp);

		//We now compose the call statement and assignment statement into sequential composition
		pushNext(assignmentNode, resultContext);
		pushNext(node.getCall(), resultContext);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	
	@Override
	public CmlBehaviourSignal caseALetStatementAction(ALetStatementAction node,
			Context question) throws AnalysisException {
	
		//Create a new context for the let statement
		Context letContext = CmlContextFactory.newContext(node.getLocation(), "let action context", question);

		for(PDefinition localDef :node.getLocalDefinitions())
			letContext.putList(localDef.apply(cmlDefEvaluator,letContext));
		
		pushNext(node.getAction(), letContext);

		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
}
