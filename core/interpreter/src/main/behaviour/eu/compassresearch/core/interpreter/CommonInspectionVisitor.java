package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTock;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.HiddenEvent;
import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;
import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.utility.Pair;

@SuppressWarnings("serial")
class CommonInspectionVisitor extends AbstractInspectionVisitor {

	interface parallelCompositionHelper
	{
		void caseParallelBegin() throws AnalysisException;
	}

	public CommonInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor) {
		super(ownerProcess, visitorAccess, parentVisitor);
	}

	protected CmlTransitionSet syncOnTockAndJoinChildren() throws AnalysisException
	{
		//even though they are external choice/interleaving they should always sync on tock
		CmlTransitionSet cs =  new CmlTransitionSet(new CmlTock());

		//Get all the child alphabets 
		CmlTransitionSet leftChildAlphabet = owner.getLeftChild().inspect();
		CmlTransitionSet rightChildAlphabet = owner.getRightChild().inspect();

		//Find the intersection between the child alphabets and the channel set and join them.
		//Then if both left and right have them the next step will combine them.
		CmlTransitionSet syncAlpha = leftChildAlphabet.intersectImprecise(cs).union(rightChildAlphabet.intersectImprecise(cs));

		//combine all the tock events 
		if(syncAlpha.getObservableEvents().size() == 2)
		{
			Iterator<ObservableEvent> it = syncAlpha.getObservableEvents().iterator(); 
			return leftChildAlphabet.union(rightChildAlphabet).subtract(syncAlpha).union(it.next().synchronizeWith(it.next()));
		}
		else
		{
			return leftChildAlphabet.union(rightChildAlphabet);
		}
	}

	/**
	 * Handles the external choice end rule
	 * @return
	 */
	protected Pair<INode,Context> caseExternalChoiceEnd(CmlBehaviour theChoosenOne, Context context) throws ValueException
	{
		//FIXME the children contexts also needs to be replaced!!!!!!
		Context copyContext = theChoosenOne.getNextState().second;
		Context newCurrentContext = CmlBehaviourUtility.mergeState(context,copyContext);

		if(theChoosenOne.getLeftChild() != null)
			theChoosenOne.getLeftChild().replaceState(newCurrentContext);

		visitorAccess.setLeftChild(theChoosenOne.getLeftChild());

		if(theChoosenOne.getRightChild() != null)
			theChoosenOne.getRightChild().replaceState(newCurrentContext);

		visitorAccess.setRightChild(theChoosenOne.getRightChild());

		return new Pair<INode, Context>(theChoosenOne.getNextState().first,newCurrentContext);
	}

	protected Inspection caseAExternalChoice(final INode node, final INode leftNode, final INode rightNode,
			final Context question) throws AnalysisException {

		//if no children are present we make a silent transition to represent the
		//external choice begin
		if(!owner.hasChildren())
		{
			return newInspection(createSilentTransition(node, node,"Begin"),
					new AbstractCalculationStep(owner,visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					Pair<Context,Context> childContexts = visitorAccess.getChildContexts(question);
					CmlBehaviour leftInstance = new ConcreteCmlBehaviour(leftNode, childContexts.first.deepCopy(), new LexNameToken(name().getModule(),name().getIdentifier().getName() + "[]" , new LexLocation()),this.owner);
					setLeftChild(leftInstance);

					CmlBehaviour rightInstance = new ConcreteCmlBehaviour(rightNode, childContexts.second.deepCopy(), new LexNameToken(name().getModule(),"[]" + name().getIdentifier().getName(), new LexLocation()),this.owner); 
					setRightChild(rightInstance);

					return new Pair<INode, Context>(node, question);
				}
			});
		}
		//if one child is finished external choice must end
		//If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
		//with the state from the skip. After this all the children processes are terminated
		else if (CmlBehaviourUtility.finishedChildExists(owner))
			return newInspection(createSilentTransition(node, node,"end"), 
					new AbstractCalculationStep(owner,visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					return caseExternalChoiceEnd(findFinishedChild(),question);
				}
			});
		//else we join the childrens alphabets 
		else
		{
			return newInspection(syncOnTockAndJoinChildren(),
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {

					for(CmlBehaviour child : children())
					{
						if(child.inspect().containsImprecise(supervisor().selectedObservableEvent()))
						{
							if(supervisor().selectedObservableEvent() instanceof ChannelEvent)
							{
								//first we execute the child
								child.execute(supervisor());
								return caseExternalChoiceEnd(child,question);
							}
							else
							{
								child.execute(supervisor());
								return new Pair<INode, Context>(node, question);
							}
						}
					}

					throw new AnalysisException("should not happen");
				}
			});
		}
	}
	/**
	 * 
	 * Private common helpers for Generalised Parallelism
	 *
	 */

	protected Inspection caseGeneralisedParallelismParallel(final INode node,final parallelCompositionHelper helper, 
			PVarsetExpression chansetExp, final Context question) throws AnalysisException
			{
		//TODO: This only implements the "A [| cs |] B (no state)" and not "A [| ns1 | cs | ns2 |] B"

		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!owner.hasChildren()){
			return newInspection(createSilentTransition(node, node, "Begin"),

					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					helper.caseParallelBegin();
					//We push the current state, since this process will control the child processes created by it
					return new Pair<INode,Context>(node, question);
				}
			});
		}
		//The process has children and they have all evolved into Skip so now the parallel end rule will be invoked 
		else if (CmlBehaviourUtility.isAllChildrenFinished(owner))
		{
			return newInspection(createSilentTransition(node, new ASkipAction(), "End"), 
					caseParallelEnd(question));
		}
		else
		{
			return caseParallelSyncOrNonsync(node,chansetExp, question);
		}
			}

	private Inspection caseParallelSyncOrNonsync(final INode node, PVarsetExpression chansetExp, final Context question) throws AnalysisException
	{
		
		//convert the channel set of the current node to a alphabet
		ChannelNameSetValue cs = (ChannelNameSetValue)chansetExp.apply(cmlExpressionVisitor,question);
			
		//CmlTransitionSet cs =  ((CmlTransitionSet)chansetExp.apply(cmlExpressionVisitor,question)).union(new CmlTock());

		//Get all the child alphabets and add the events that are not in the channelset
		final CmlBehaviour leftChild = owner.getLeftChild();
		final CmlTransitionSet leftChildAlphabet = leftChild.inspect();
		final CmlBehaviour rightChild = owner.getRightChild();
		final CmlTransitionSet rightChildAlphabet = rightChild.inspect();
		
		//combine all the common channel events that are in the channel set
		CmlTransitionSet leftSync = leftChildAlphabet.retainByChannelNameSet(cs);
		CmlTransitionSet rightSync = rightChildAlphabet.retainByChannelNameSet(cs);
		Set<CmlTransition> syncEvents = new HashSet<CmlTransition>();
		//Find the intersection between the child alphabets and the channel set and join them.
//		//Then if both left and right have them the next step will combine them.
		for(ObservableEvent leftTrans : leftSync.getObservableChannelEvents())
		{
			for(ObservableEvent rightTrans : rightSync.getObservableChannelEvents())
			{
				if(leftTrans.isComparable(rightTrans))
				{

					ChannelEvent leftChannelEvent = (ChannelEvent)leftTrans;
					ChannelEvent rightChannelEvent = (ChannelEvent)rightTrans;

					if(leftChannelEvent.getChannelName().isGTEQPrecise(rightChannelEvent.getChannelName()) ||
							rightChannelEvent.getChannelName().isGTEQPrecise(leftChannelEvent.getChannelName()))
						syncEvents.add(leftTrans.synchronizeWith(rightTrans));
				}
			}
		}
		
		CmlTock leftTock = leftChildAlphabet.getTockEvent();
		CmlTock rightTock = rightChildAlphabet.getTockEvent();
		if(leftTock != null && rightTock != null)
			syncEvents.add(leftTock.synchronizeWith(rightTock));
		
		/*
		 *	Finally we create the returned alphabet by joining all the 
		 *  Synchronized events together with all the event of the children 
		 *  that are not in the channel set.
		 */
		CmlTransitionSet resultAlpha = new CmlTransitionSet(syncEvents).union(leftChildAlphabet.removeByChannelNameSet(cs));
		resultAlpha = resultAlpha.union(rightChildAlphabet.removeByChannelNameSet(cs));

		return newInspection(resultAlpha, 
				new AbstractCalculationStep(owner, visitorAccess) {

			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				//if both contains the selected event it must be a sync event
				if(leftChildAlphabet.containsImprecise(owner.supervisor().selectedObservableEvent()) &&
						rightChildAlphabet.containsImprecise(owner.supervisor().selectedObservableEvent()))
				{
					leftChild.execute(sve);
					rightChild.execute(sve);
				}
				else if(leftChildAlphabet.containsImprecise(owner.supervisor().selectedObservableEvent()))
				{
					leftChild.execute(sve);
				}
				else if(rightChildAlphabet.containsImprecise(owner.supervisor().selectedObservableEvent()))
				{
					rightChild.execute(sve);
				}
				else
					//Something went wrong here
					throw new InterpreterRuntimeException("");

				//We push the current state,
				return new Pair<INode,Context>(node, question);
			}
		});

	}

	protected CmlCalculationStep caseParallelEnd(final Context question)
	{
		return new AbstractCalculationStep(owner, visitorAccess) {

			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {

				setLeftChild(null);
				setRightChild(null);

				//now this process evolves into Skip
				return new Pair<INode,Context>(new ASkipAction(), question);
			}
		};
	}
	
	/**
	 * Common Hiding handler methods
	 * @throws AnalysisException 
	 */
	protected Inspection caseHiding(final INode node, PVarsetExpression chansetExpression, final Context question) throws AnalysisException
	{
		//We do the hiding behavior as long as the Action is not terminated
		if(!owner.getLeftChild().finished())
		{
			//first we convert the channelset expression into a channelNameSetValue
			ChannelNameSetValue cs = (ChannelNameSetValue)chansetExpression.apply(cmlExpressionVisitor,question);
			//next we inspect the action to get the current available transitions
			CmlTransitionSet alpha = owner.getLeftChild().inspect();
			//Intersect the two to find which transitions should be converted to silents transitions
			CmlTransitionSet hiddenEvents = alpha.retainByChannelNameSet(cs);
			//remove the events that has to be silent
			CmlTransitionSet resultAlpha = alpha.subtract(hiddenEvents);
			//convert them into silent events and add the again
			for(ObservableEvent obsEvent : hiddenEvents.getObservableEvents())
				if(obsEvent instanceof ChannelEvent)
					resultAlpha = resultAlpha.union(new HiddenEvent(owner,(ChannelEvent)obsEvent));	

			return newInspection(resultAlpha,
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					owner.getLeftChild().execute(supervisor());
					return new Pair<INode,Context>(node, question);
				}
			});
		}
		//If the Action is terminated then it evolves into Skip
		else
			return newInspection(createSilentTransition(node, new ASkipAction()),
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					setLeftChild(null);
					return new Pair<INode,Context>(new ASkipAction(), question);
				}
			});
	}
	
	
	/**
	 * 
	 */
	protected AFatEnumVarsetExpression getAllChannelsAsFatEnum(
			ILexLocation location, Context question)
					throws AnalysisException {
		
		//TODO Change AFatEnumVarsetExpression expects List of ANameChannelExp instead of List of ILexNameToken
		Context globalContext = question.getGlobal();
		List<ANameChannelExp> channelNames = new LinkedList<ANameChannelExp>();


		//Get all the channel name exps objects
		for(Entry<ILexNameToken,Value> entry : globalContext.entrySet())
			if(entry.getValue() instanceof CMLChannelValue)
				channelNames.add(new ANameChannelExp(entry.getKey().getLocation(), entry.getKey().clone(), new LinkedList<PExp>())  );

		return new AFatEnumVarsetExpression(location, channelNames);
	}
	
	/**
	 * Common Sequential composition handler methods
	 */
	protected Inspection caseASequentialComposition(final INode node, INode leftNode, 
			final INode rightNode, final Context question) throws AnalysisException {
		//we execute the left action until it successfully terminates
		if(!owner.getLeftChild().finished())
		{
			return newInspection(owner.getLeftChild().inspect(), 
					new AbstractCalculationStep(owner,visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					owner.getLeftChild().execute(supervisor());
					return new Pair<INode, Context>(node, question);
				}
			});


		}
		//if the left action is successfully finished then this node becomes the right action
		else 
		{
			return newInspection(createSilentTransition(node,leftNode),
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {

					setLeftChild(null);
					setRightChild(null);	

					return new Pair<INode, Context>(rightNode,question);
				}
			});

		}
	}

}
