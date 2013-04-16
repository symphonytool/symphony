package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ObjectContext;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.NameValuePairMap;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.CmlContextFactory;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.eval.ActionEvaluationVisitor.parallelCompositionHelper;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.util.Pair;
import eu.compassresearch.core.interpreter.values.ActionValue;
import eu.compassresearch.core.interpreter.values.CmlOperationValue;
import eu.compassresearch.core.interpreter.values.ProcessObjectValue;

public class ProcessEvaluationVisitor extends CommonEvaluationVisitor {

	public ProcessEvaluationVisitor(AbstractEvaluationVisitor parentVisitor)
	{
		super(parentVisitor);
	}
	
	/**
	 * Private helper methods
	 */
	
	private CmlBehaviourSignal caseParallelBegin(PProcess node, PProcess left, PProcess right, Context question)
	{
		if(left == null || right == null)
			throw new InterpreterRuntimeException(
					InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
		
		//TODO: create a local copy of the question state for each of the actions
		CmlBehaviour leftInstance = 
				VanillaInterpreterFactory.newCmlBehaviourThread(left,question,new LexNameToken(name.module,name.getIdentifier().getName() + "|||" ,left.getLocation()),ownerThread());
		
		CmlBehaviour rightInstance = 
				VanillaInterpreterFactory.newCmlBehaviourThread(right,question,new LexNameToken(name.module,"|||" + name.getIdentifier().getName() ,right.getLocation()),ownerThread());
		
		return caseParallelBeginGeneral(leftInstance,rightInstance,question);
	}
	
	/**
	 * Transition methods
	 */
	
	@Override
	public CmlBehaviourSignal defaultPProcess(PProcess node, Context question)
			throws AnalysisException {
		
		throw new InterpreterRuntimeException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
		
	}
	
//	@Override
//	public CmlBehaviourSignal caseASkipProcess(ASkipProcess node, Context question)
//			throws AnalysisException {
//
//		//if hasNext() is true then Skip is in sequential composition with next
//		if(!hasNext())
//			setState(CmlProcessState.FINISHED);
//		return CmlBehaviourSignal.EXEC_SUCCESS;
//	}
	
	@Override
	public CmlBehaviourSignal caseAActionProcess(AActionProcess node, Context question) throws AnalysisException
	{
		AProcessDefinition processDef;
		//We have a named process
		if(node.parent() instanceof AProcessDefinition)
		{
			processDef = (AProcessDefinition)node.parent();
		}
		//Unnamed process
		else
		{
			processDef = new AProcessDefinition();
			processDef.setLocation(node.getLocation());
			processDef.setName(new LexNameToken("", "Unnamed Process",node.getLocation()));
		}
		
		//Create a temporary context to evaluate the definitions in
		Context tmpContext = CmlContextFactory.newContext(node.getLocation(),"tmp",null);
		
		//Evaluate and add paragraph definitions and add the result to the state
		NameValuePairMap valueMap = new NameValuePairMap();
		for (PDefinition def : node.getDefinitionParagraphs())
		{
			NameValuePairList nvps = def.apply(cmlDefEvaluator, tmpContext);
			tmpContext.putList(nvps);
			
			for(NameValuePair nvp : nvps)
			{
				LexNameToken name = nvp.name.getModifiedName(processDef.getName().getSimpleName());
				
				//This makes sure that operations and functions cannot be updated, while
				//everything else can.
				//TODO This might be incomplete
				if(nvp.value instanceof FunctionValue ||
						nvp.value instanceof CmlOperationValue ||
						nvp.value instanceof ActionValue)
					valueMap.put(new NameValuePair(name,nvp.value));
				else
					valueMap.put(new NameValuePair(name,nvp.value.getUpdatable(null)));
			}
		}
		
		ProcessObjectValue self = new ProcessObjectValue(processDef,valueMap,question.getSelf());

		ObjectContext processObjectContext = CmlContextFactory.newObjectContext(node.getLocation(), "Action Process Context", question, self);
		
		//push this node onto the execution stack again since this should execute
		//the action behaviour until it terminates
		pushNext(node.getAction(), processObjectContext);
		return CmlBehaviourSignal.EXEC_SUCCESS; 
	}
	
	/**
	 * This implements the 7.5.10 Action Reference transition rule in D23.2. 
	 * (Even though this is a process I assume something similar will happen)
	 */
	@Override
	public CmlBehaviourSignal caseAReferenceProcess(AReferenceProcess node,
			Context question) throws AnalysisException {

		//initials this process with the global context since this should see any of creators members
//		CmlProcess childProcess = new CmlProcess(node.getProcessDefinition(), this, question.getGlobal());
//		this.children.add(childProcess);
//		return CmlBehaviourSignal.EXEC_SUCCESS;
		
		//ProcessObjectValue processValue = (ProcessObjectValue)question.lookup(node.getProcessName());
		//name = new LexNameToken(name.module,name.getIdentifier().getName() + " = " + processValue.getProcessDefinition().getName().getSimpleName(),name.location);
		
		//CmlStateContext processContext = new CmlStateContext(node.getLocation(), "Referenced Process context", question,null, processValue.getProcessDefinition());
		
		pushNext( node.getProcessDefinition().getProcess(), question); 
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
		
	}
	
	
	@Override
	public CmlBehaviourSignal caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
			throws AnalysisException {
		
		return caseASequentialComposition(node.getLeft(),node.getRight(),question);
	}
	
	
	/**
	 * External Choice D23.2 7.5.4
	 * 
	 *  There four transition rules for external choice:
	 *  
	 *  * External Choice Begin
	 *  
	 *  * External Choice Silent
	 *  
	 *  * External Choice SKIP
	 *  
	 *  * External Choice End
	 *  
	 */
	
	@Override
	public CmlBehaviourSignal caseAExternalChoiceProcess(
			AExternalChoiceProcess node, Context question)
			throws AnalysisException {
		
		return caseAExternalChoice(node,node.getLeft(),new LexNameToken(name.module,name.getIdentifier().getName() + "[]" ,node.getLeft().getLocation()),
				node.getRight(),new LexNameToken(name.module,"[]" + name.getIdentifier().getName(),node.getRight().getLocation()),question);
		
		//return null;
	}
			
	/**
	 * There are no actual transition rule for this. The rule for interleaving action is that they evolve 
	 * into Skip. However, this will just terminate successfully when all its children terminates successfully.
	 */
	@Override
	public CmlBehaviourSignal caseAInterleavingProcess(
			AInterleavingProcess node, Context question)
			throws AnalysisException {
		
		//TODO: This only implements the "A ||| B (no state)" and not "A [|| ns1 | ns2 ||] B"
		CmlBehaviourSignal result = null;

		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!ownerThread().hasChildren()){
			caseParallelBegin(node,node.getLeft(),node.getRight(),question);
			//We push the current state, since this process will control the child processes created by it
			pushNext(node, question);

		}
		//the process has children and must now handle either termination or event sync
		else if (CmlBehaviourThreadUtility.isAllChildrenFinished(ownerThread()))
		{
			result = caseParallelEnd(question); 
		}
		else
		{
			//At least one child is not finished and waiting for event, this will invoke the Parallel Non-sync 
			result = caseParallelNonSync();
			//We push the current state, 
			pushNext(node, question);
		}

		return result;
	}
	
	@Override
	public Pair<INode,Context> caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node, Context question)
			throws AnalysisException {
		
		final AGeneralisedParallelismProcess finalNode = node;
		final Context finalQuestion = question;
		
		return caseGeneralisedParallelismParallel(node,new parallelCompositionHelper() {
			
			@Override
			public Pair<INode,Context> caseParallelBegin() {
				return ProcessEvaluationVisitor.this.caseParallelBegin(finalNode,finalNode.getLeft(),finalNode.getRight(), finalQuestion);
			}
		}, node.getChansetExpression(),question);
	}
	
	@Override
	public Pair<INode,Context> caseAInternalChoiceProcess(
			AInternalChoiceProcess node, Context question)
			throws AnalysisException {
		
		if(rnd.nextInt(2) == 0)
			return new Pair<INode,Context>(node.getLeft(), question);
		else
			return new Pair<INode,Context>(node.getRight(), question);
	}
	
	
}
