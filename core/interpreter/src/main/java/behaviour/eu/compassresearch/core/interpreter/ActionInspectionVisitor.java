package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTock;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionFactory;
import eu.compassresearch.core.interpreter.api.transitions.CommunicationParameter;
import eu.compassresearch.core.interpreter.api.transitions.InputParameter;
import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;
import eu.compassresearch.core.interpreter.api.transitions.OutputParameter;
import eu.compassresearch.core.interpreter.api.transitions.SignalParameter;
import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.utility.Pair;

public class ActionInspectionVisitor extends CommonInspectionVisitor {

	private QuestionAnswerCMLAdaptor<Context, Inspection> statementInspectionVisitor;
	
	public ActionInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor) {
		super(ownerProcess, visitorAccess, parentVisitor);
		
		this.statementInspectionVisitor = new StatementInspectionVisitor(ownerProcess, visitorAccess, this);
	}
	
	/**
	 * Transition methods
	 */
	
	@Override
	public Inspection defaultSStatementAction(SStatementAction node,
			Context question) throws AnalysisException {

		return node.apply(statementInspectionVisitor,question);
	}
	
	@Override
	public Inspection defaultPAction(PAction node, Context question)
			throws AnalysisException {

		throw new InterpreterRuntimeException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
	
	/**
	 * Synchronization and Communication D23.2 7.5.2
	 * 
	 * This transition can either be
	 * Simple prefix   	: a -> A
	 * Synchronization 	: a.1 -> A
	 * Output			: a!2 -> A
	 * Input			: a?x -> A
	 * As defined in 7.5.2 in D23.2
	 */
	@Override
	public Inspection caseACommunicationAction(
			final ACommunicationAction node, final Context question)
					throws AnalysisException {

		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("|CHANNELS|",node.getIdentifier().getName(),node.getIdentifier().getLocation(),false,true);
		//find the channel value
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);

		Set<CmlTransition> comset = new HashSet<CmlTransition>();

		//if there are no com params then we have a prefix event
		if(node.getCommunicationParameters().isEmpty())
		{
			comset.add(CmlTransitionFactory.newSynchronizationEvent(owner, chanValue));
		}
		//otherwise we convert the com params
		else
		{
			List<CommunicationParameter> params = new LinkedList<CommunicationParameter>();
			for(PCommunicationParameter p : node.getCommunicationParameters())
			{
				CommunicationParameter param = null;
				if(p instanceof ASignalCommunicationParameter)
				{
					ASignalCommunicationParameter signal = (ASignalCommunicationParameter)p;
					Value valueExp = signal.getExpression().apply(cmlExpressionVisitor,question);
					param = new SignalParameter((ASignalCommunicationParameter)p, valueExp);
				}
				else if(p instanceof AWriteCommunicationParameter)
				{
					AWriteCommunicationParameter signal = (AWriteCommunicationParameter)p;
					Value valueExp = signal.getExpression().apply(cmlExpressionVisitor,question);
					param = new OutputParameter((AWriteCommunicationParameter)p, valueExp);
				}
				else if(p instanceof AReadCommunicationParameter)
				{
					//TODO: At this point the 'in set exp' is not supported
					AReadCommunicationParameter readParam = (AReadCommunicationParameter)p;
					param = new InputParameter(readParam);
				}

				params.add(param);
			}

			ObservableEvent observableEvent = CmlTransitionFactory.newCmlCommunicationEvent(owner, chanValue, params);
			comset.add(observableEvent);
		}
		//TODO: do the rest here

		return newInspection(new CmlAlphabet(comset).union(new CmlTock(owner)),
				new AbstractCalculationStep(owner, visitorAccess) {

			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				//At this point the supervisor has already given go to the event, or the event is hidden
				Value value = ((ChannelEvent)sve.selectedObservableEvent()).getValue();

				if(node.getCommunicationParameters() != null)
				{
					//FIXME this should be more general. It only support one com param at the moment
					for(PCommunicationParameter param : node.getCommunicationParameters())
					{
						if(param instanceof AReadCommunicationParameter)
						{
							PPattern pattern = ((AReadCommunicationParameter) param).getPattern();

							if(pattern instanceof AIdentifierPattern)
							{
								ILexNameToken name = ((AIdentifierPattern) pattern).getName();

								question.putNew(new NameValuePair(name, value));
							}

						}
					}
				}
				return new Pair<INode,Context>(node.getAction(), question);
			}
		});
	}
	
	/**
	 * External Choice section 7.5.4 D23.2
	 * 
	 * In terms of the alphabet, we have the following situations:
	 * 
	 *  External Choice Begin:
	 *  When no children exists, the External Choice Begin transition rule must be executed.
	 *  This is a silent transition and therefore the alphabet contains only tau event
	 *  
	 *  External Choice Silent:
	 *  If any of the actions can take a silent transition they will do it before getting here again. 
	 *  We therefore don't take this situation into account
	 *  
	 *  External Choice Skip:
	 *  If one the children is Skip we make a silent transition of the whole choice into skip.
	 *  We therefore just return the tau event
	 *  
	 *  External Choice End:
	 *  The alphabet contains an observable event for every child that can engaged in one.
	 *  
	 */
	@Override
	public Inspection caseAExternalChoiceAction(
			AExternalChoiceAction node, Context question)
			throws AnalysisException {
		
		return caseAExternalChoice(node,node.getLeft(),node.getRight(), question);
		
	}

	/**
	 * State-based Choice - section 7.5.5 D23.2
	 * Guard
	 * Guarded actions are stuck, unless the guard is true.
	 * So If we ever execute this transition, the guard expression would already
	 * have been checked for being true.
	 */
	@Override
	public Inspection caseAGuardedAction(final AGuardedAction node,
			final Context question) throws AnalysisException {

		//First we evaluate the guard expression
		Value guardExp = node.getExpression().apply(cmlExpressionVisitor,question);
		
		CmlAlphabet alpha = null;
		
		//if the gaurd is true then we return the silent transition to the guarded action
		if(guardExp.boolValue(question))
			alpha = createSilentTransition(node, node.getAction());
		//else we return the empty alphabet since no transition is possible
		else
			alpha = new CmlAlphabet(new CmlTock(owner));
		
		return newInspection(alpha, 
				new AbstractCalculationStep(owner, visitorAccess) {
					
					@Override
					public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
							throws AnalysisException {
						return new Pair<INode,Context>(node.getAction(), question); 
					}
				});
	}
	
	/**
	 * This implements the 7.5.6 Sequential Composition transition rules in D23.2.
	 */
	@Override
	public Inspection caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
			throws AnalysisException {

		return caseASequentialComposition(node,node.getLeft(),node.getRight(),question);
	}
	
	@Override
	public Inspection caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException {
		
		return newInspection(new CmlAlphabet(new CmlTock(owner)),null);
	}
	
	@Override
	public Inspection caseAStopAction(AStopAction node, Context question)
			throws AnalysisException {
		//return the empty alphabet
		return newInspection(new CmlAlphabet(new CmlTock(owner)),null);
	}
}
