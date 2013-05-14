package eu.compassresearch.core.interpreter;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;
import org.overture.interpreter.assistant.pattern.PPatternAssistantInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.PatternMatchException;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;
import org.overture.interpreter.values.VoidValue;

import eu.compassresearch.ast.actions.AAssignmentCallStatementAction;
import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.AElseIfStatementAction;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.ALetStatementAction;
import eu.compassresearch.ast.actions.ANewStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.core.interpreter.ActionVisitorHelper;
import eu.compassresearch.core.interpreter.CmlContextFactory;
import eu.compassresearch.core.interpreter.Pair;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.core.CmlBehaviour;
import eu.compassresearch.core.interpreter.values.CmlOperationValue;

@SuppressWarnings("serial")
class CmlStatementEvaluationVisitor extends AbstractEvaluationVisitor {

	public CmlStatementEvaluationVisitor(AbstractEvaluationVisitor parentVisitor, CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(parentVisitor,owner,visitorAccess);
	}
	
	/**
	 * This methods splits the assignment call statement into the call and 
	 * the assignment statements and.
	 */
	@Override
	public Pair<INode,Context> caseAAssignmentCallStatementAction(
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
						node.getDesignator().clone(),
						varExp);

		//We now compose the call statement and assignment statement into sequential composition
		INode seqComp = new ASequentialCompositionAction(node.getLocation(), node.getCall().clone(), assignmentNode.clone());
		return new Pair<INode, Context>(seqComp,resultContext);
	}
	
	@Override
	public Pair<INode,Context> caseABlockStatementAction(
			ABlockStatementAction node, Context question)
			throws AnalysisException {
		
		Context blockContext = CmlContextFactory.newContext(node.getLocation(), "block context", question);
		
		//add the assignment definitions to the block context
		if(node.getDeclareStatement() != null)
		{
			for(PDefinition def : node.getDeclareStatement().getAssignmentDefs())
			{
				NameValuePair nvp = def.apply(cmlDefEvaluator,question).get(0);
				blockContext.put(nvp.name, nvp.value.getUpdatable(null));
			}
		}
		
		return new Pair<INode, Context>(node.getAction(), blockContext); 
	}
	
	/*
	 * 
	 */
	@Override
	public Pair<INode,Context> caseACallStatementAction(
			ACallStatementAction node, Context question)
					throws AnalysisException {
		
		if(!owner.hasChildren()){
			
			//first find the operation value in the context
			CmlOperationValue opVal = (CmlOperationValue)question.check(node.getName()); 

			//evaluate all the arguments
			ValueList argValues = new ValueList();
			for (PExp arg: node.getArgs())
			{
				argValues.add(arg.apply(cmlExpressionVisitor,question));
			}

			// Note args cannot be Updateable, so we convert them here. This means
			// that TransactionValues pass the local "new" value to the far end.
			ValueList constValues = argValues.getConstant();

			//Create a new object context to perform the operation call 
			Context callContext = CmlContextFactory.newObjectContext(node.getLocation(), "CML Operation Call", question, question.getSelf());

			if (argValues.size() != opVal.getParamPatterns().size())
			{
				opVal.abort(4068, "Wrong number of arguments passed to " + node.getName(), question);
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

			if (opVal.getBody() == null)
			{
				opVal.abort(4066, "Cannot call implicit operation: " + node.getName(), question);
			}

			//push the pre condition on the execution stack so it gets executed first
			if(opVal.getPrecondition() != null){

				PExp preExpNode = opVal.getPrecondition();
				Context preConditionContext = CmlContextFactory.newContext(preExpNode.getLocation(), 
						"Operation " + node.getName() + " precondition context", callContext);
				
				callContext.setPrepost(0, "precondition violated for call " + node.getName());

				if(!preExpNode.apply(this.cmlExpressionVisitor, preConditionContext).boolValue(question))
				{
					throw new ValueException(4061, "precondition violated for call " + node.getName(), question);
				}

			}

			if(opVal.getPostcondition() != null){

				PExp postExpNode = opVal.getPostcondition();

				Context postConditionContext = CmlContextFactory.newContext(postExpNode.getLocation(), 
						"Operation " + node.getName() + " postcondition context", callContext);

				//set the old values
				for(NameValuePair nvp : postConditionContext.getSelf().getMemberValues().asList())
					if(UpdatableValue.class.isAssignableFrom(nvp.value.getClass()))
					{
						//FIXME it does not work when the module is there
						LexNameToken oldName = new LexNameToken("",(ILexIdentifierToken)nvp.name.getOldName().getIdentifier().clone());
						postConditionContext.putNew(new NameValuePair(oldName, nvp.value.getConstant()));
					}

				postConditionContext.setPrepost(0, "postcondition violated for " + node.getName());

				setRightChild(VanillaInterpreterFactory.newCmlBehaviour(postExpNode, postConditionContext, owner));

				//We now compose the call statement and post condition check into sequential composition
				//INode seqComp = new ASequentialCompositionAction(node.getLocation(), opVal.getBody(), assignmentNode);
				//return new Pair<INode, Context>(seqComp,resultContext);
			}
			
			setLeftChild(VanillaInterpreterFactory.newCmlBehaviour(opVal.getBody(), callContext, owner));
			return new Pair<INode, Context>(node, question);
		}
		
//		//first find the operation value in the context
//		CmlOperationValue opVal = (CmlOperationValue)question.check(node.getName()); 
//
//		//evaluate all the arguments
//		ValueList argValues = new ValueList();
//		for (PExp arg: node.getArgs())
//		{
//			argValues.add(arg.apply(cmlExpressionVisitor,question));
//		}
//
//		// Note args cannot be Updateable, so we convert them here. This means
//		// that TransactionValues pass the local "new" value to the far end.
//		ValueList constValues = argValues.getConstant();
//
//		//Create a new object context to perform the operation call 
//		Context callContext = CmlContextFactory.newObjectContext(node.getLocation(), "CML Operation Call", question, question.getSelf());
//
//		if (argValues.size() != opVal.getParamPatterns().size())
//		{
//			opVal.abort(4068, "Wrong number of arguments passed to " + name.name, question);
//		}
//
//		ListIterator<Value> valIter = argValues.listIterator();
//		Iterator<PType> typeIter = opVal.getType().getParameters().iterator();
//		NameValuePairMap args = new NameValuePairMap();
//
//		for (PPattern p : opVal.getParamPatterns())
//		{
//			try
//			{
//				// Note values are assumed to be constant, as enforced by eval()
//				Value pv = valIter.next().convertTo(typeIter.next(), question);
//
//				for (NameValuePair nvp : PPatternAssistantInterpreter.getNamedValues(p,pv, question))
//				{
//					Value v = args.get(nvp.name);
//
//					if (v == null)
//					{
//						args.put(nvp);
//					}
//					else	// Names match, so values must also
//					{
//						if (!v.equals(nvp.value))
//						{
//							opVal.abort(4069,	"Parameter patterns do not match arguments", question);
//						}
//					}
//				}
//			}
//			catch (PatternMatchException e)
//			{
//				opVal.abort(e.number, e, question);
//			}
//		}
//
//		// Note: arg name/values hide member values
//		callContext.putAll(args);
//
//		if (opVal.getBody() == null)
//		{
//			opVal.abort(4066, "Cannot call implicit operation: " + name, question);
//		}
//		
//		//push the pre condition on the execution stack so it gets executed first
//		if(opVal.getPrecondition() != null){
//
//			PExp preExpNode = opVal.getPrecondition();
//			Context preConditionContext = CmlContextFactory.newContext(preExpNode.getLocation(), 
//					"Operation " + node.getName() + " precondition context", callContext);
//
//			if(!preExpNode.apply(this.cmlExpressionVisitor, preConditionContext).boolValue(question))
//			{
//				throw new ValueException(4061, "precondition violated for call " + node.getName(), question);
//			}
//			
//		}
//		
//		if(opVal.getPostcondition() != null){
//
//			PExp postExpNode = opVal.getPostcondition();
//
//			Context postConditionContext = CmlContextFactory.newContext(postExpNode.getLocation(), 
//					"Operation " + node.getName() + " postcondition context", callContext);
//
//			//set the old values
//			for(NameValuePair nvp : postConditionContext.getSelf().getMemberValues().asList())
//				if(UpdatableValue.class.isAssignableFrom(nvp.value.getClass()))
//				{
//					//FIXME it does not work when the module is there
//					LexNameToken oldName = new LexNameToken("",(LexIdentifierToken)nvp.name.getOldName().getIdentifier().clone());
//					postConditionContext.putNew(new NameValuePair(oldName, nvp.value.getConstant()));
//				}
//
//			postConditionContext.setPrepost(0, "postcondition violated for " + node.getName());
//
//			//ownerThread().setLeftChild(VanillaInterpreterFactory.newCmlBehaviour(opVal.getBody(), callContext, ownerThread()));
//			
//			//We now compose the call statement and post condition check into sequential composition
//			//INode seqComp = new ASequentialCompositionAction(node.getLocation(), opVal.getBody(), assignmentNode);
//			//return new Pair<INode, Context>(seqComp,resultContext);
//			
//		}
//		//else
//		return new Pair<INode, Context>(opVal.getBody(), callContext);
		
		else if(!owner.getLeftChild().finished())
		{
			owner.getLeftChild().execute(supervisor());
			return new Pair<INode, Context>(node, question);
		}
		else if(owner.getRightChild() != null)
		{
			owner.getRightChild().execute(supervisor());
			setLeftChild(null);
			setRightChild(null);
			return new Pair<INode, Context>(new ASkipAction(), question);
		}
		else
		{
			setLeftChild(null);
			return new Pair<INode, Context>(new ASkipAction(), question);
		}
	}
	
	@Override
	public Pair<INode,Context> caseAIfStatementAction(AIfStatementAction node,
			Context question) throws AnalysisException {

		if (node.getIfExp().apply(cmlExpressionVisitor,question).boolValue(question))
		{
			return new Pair<INode, Context>(node.getThenStm(), question);
		}
		else
		{
			for (AElseIfStatementAction elseif: node.getElseIf())
			{
				if(elseif.getElseIf().apply(cmlExpressionVisitor,question).boolValue(question))
				{
					return new Pair<INode, Context>(elseif.getThenStm(), question);
				}
			}

			if (node.getElseStm() != null)
			{
				return new Pair<INode, Context>(node.getElseStm(), question);
			}
		}
		
		throw new InterpreterRuntimeException("Should not happen");
	}
	
	@Override
	public Pair<INode,Context> caseALetStatementAction(ALetStatementAction node,
			Context question) throws AnalysisException {
	
		//Create a new context for the let statement
		Context letContext = CmlContextFactory.newContext(node.getLocation(), "let action context", question);

		for(PDefinition localDef :node.getLocalDefinitions())
			letContext.putList(localDef.apply(cmlDefEvaluator,letContext));
		
		return new Pair<INode, Context>(node.getAction(), letContext);
	}
	
	@Override
	public Pair<INode,Context> caseANewStatementAction(ANewStatementAction node,
			Context question) throws AnalysisException {

		ObjectValue classValue = CmlValueFactory.createClassValue(node, question);
		Context ctorContext = CmlContextFactory.newObjectContext(node.getLocation(), "Class Constructor context", question, classValue);
		ILexNameToken name = node.getClassdef().getName().clone();//new LexNameToken(node.getClassdef().getName().getName(), node.getClassName().getIdentifier().getName(), node.getLocation());
		ACallStatementAction callStm = new ACallStatementAction(name.getLocation(), name, node.getArgs());
		Value oldVal = node.getDestination().apply(cmlExpressionVisitor,question);
		
		oldVal.set(node.getLocation(), classValue, question);

		return new Pair<INode, Context>(callStm, ctorContext);
	}
	
	/**
	 * Non deterministic if randomly chooses between options whose guard are evaluated to true
	 */
	@Override
	public Pair<INode,Context> caseANonDeterministicIfStatementAction(
			ANonDeterministicIfStatementAction node, Context question)
			throws AnalysisException {

		List<ANonDeterministicAltStatementAction> availableAlts = ActionVisitorHelper.findAllTrueAlternatives(
				node.getAlternatives(),question,cmlExpressionVisitor);
		//if we got here we already now that the must at least be one available action
		//so this should pose no risk of exception
		return new Pair<INode,Context>(availableAlts.get(rnd.nextInt(availableAlts.size())).getAction(),question);
		 
	}

	/**
	 * 
	 * //TODO no semantics defined, resolve this!
	 */
	@Override
	public Pair<INode,Context> caseANonDeterministicDoStatementAction(
			ANonDeterministicDoStatementAction node, Context question)
			throws AnalysisException {

		List<ANonDeterministicAltStatementAction> availableAlts = ActionVisitorHelper.findAllTrueAlternatives(
				node.getAlternatives(),question,cmlExpressionVisitor);
		
		
		if(availableAlts.size() > 0)
		{
			//first we push the do node on the execution stack to get it sequentially composed with the
			//picked alternative
			//if we got here we already now that the must at least be one available action
			//so this should pose no risk of exception
			INode nextNode = new ASequentialCompositionAction(node.getLocation(), 
					availableAlts.get(rnd.nextInt(availableAlts.size())).getAction().clone(), 
						node.clone());
			return new Pair<INode, Context>(nextNode,question);
		}
		else
			return new Pair<INode,Context>(new ASkipAction(), question);
			
	}
	
	@Override
	public Pair<INode,Context> caseAReturnStatementAction(
			AReturnStatementAction node, Context question)
			throws AnalysisException {

		Context nameContext = (Context)question.locate(CmlOperationValue.ReturnValueName());
		if(nameContext != null)
		{
			if(node.getExp() != null)
				nameContext.put(CmlOperationValue.ReturnValueName(), node.getExp().apply(cmlExpressionVisitor,question));
			else
				nameContext.put(CmlOperationValue.ReturnValueName(),new VoidValue());
		}
		
		return new Pair<INode,Context>(new ASkipAction(),question);
	}
	
	/**
	 * Assignment - section 7.5.1 D23.2
	 * 
	 */
	@Override
	public Pair<INode,Context> caseASingleGeneralAssignmentStatementAction(
			ASingleGeneralAssignmentStatementAction node, Context question)
					throws AnalysisException {
//		question.putNew(new NameValuePair(new LexNameToken("", new LexIdentifierToken("a", false, new LexLocation())), new IntegerValue(2)));
		Value expValue = node.getExpression().apply(cmlExpressionVisitor,question);
		
		//TODO Change this to deal with it in general
		//LexNameToken stateDesignatorName = CmlActionAssistant.extractNameFromStateDesignator(node.getStateDesignator(),question);

		Value oldVal = node.getStateDesignator().apply(cmlExpressionVisitor,question);
		
		oldVal.set(node.getLocation(), expValue, question);
		
		//System.out.println(stateDesignatorName + " = " + expValue);
		
		//now this process evolves into Skip
		return new Pair<INode,Context>(new ASkipAction(node.getLocation(),new AActionType()), question);
	}
	
	/**
	 * 
	 * //TODO no semantics defined, resolve this!
	 */
	@Override
	public Pair<INode,Context> caseAWhileStatementAction(
			AWhileStatementAction node, Context question)
			throws AnalysisException {
				
		if(node.getCondition().apply(cmlExpressionVisitor,question).boolValue(question))
		{
			//the next step is a sequential composition of the action and this node
			return new Pair<INode,Context>(
					new ASequentialCompositionAction(node.getAction().getLocation(), node.getAction().clone(), node.clone()),question);
		}
		else
		{
			//if the condition is false then the While evolves into Skip
			return new Pair<INode,Context>(new ASkipAction(), question);
		}
		
	}
}
