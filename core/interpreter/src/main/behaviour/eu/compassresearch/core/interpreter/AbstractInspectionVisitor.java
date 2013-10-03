package eu.compassresearch.core.interpreter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.api.transitions.TauTransition;

public class AbstractInspectionVisitor extends
		QuestionAnswerCMLAdaptor<Context, Inspection> {

	//The process that contains this instance
	protected final CmlBehaviour 									owner;
	protected final CmlExpressionVisitor							cmlExpressionVisitor = new CmlExpressionVisitor();
	
	/**
	 * Used for making random but deterministic decisions
	 */
	protected final Random											rnd = new Random(9784345);
	
	/**
	 * Evaluator for definitions
	 */
	protected QuestionAnswerCMLAdaptor<Context, NameValuePairList>	cmlDefEvaluator = new CmlDefinitionVisitor();
	
	/**
	 * Interface that gives access to methods that access protected parts of a CmlBehaviour
	 */
	protected final VisitorAccess 									visitorAccess;
	
	protected final QuestionAnswerCMLAdaptor<Context, Inspection>	parentVisitor;
	/**
	 * 
	 * @param ownerProcess
	 */
	public AbstractInspectionVisitor(CmlBehaviour ownerProcess, VisitorAccess visitorAccess, QuestionAnswerCMLAdaptor<Context, Inspection>	parentVisitor)
	{
		this.owner = ownerProcess;
		this.visitorAccess = visitorAccess;
		this.parentVisitor = parentVisitor;
	}
	
	/**
	 * Common Helpers
	 */
	
	protected CmlTransitionSet createTauTransitionWithTime(INode dstNode, String transitionText)
	{
		return new CmlTransitionSet(new TimedTransition(owner),new TauTransition(owner,dstNode,transitionText));
	}
	
	protected CmlTransitionSet createTauTransitionWithTime(INode dstNode)
	{
		return createTauTransitionWithTime(dstNode,null);
	}
	
	protected CmlTransitionSet createTauTransitionWithoutTime(INode dstNode)
	{
		return createTauTransitionWithoutTime(dstNode, null);
	}
	
	protected CmlTransitionSet createTauTransitionWithoutTime(INode dstNode, String transitionText)
	{
		return new CmlTransitionSet(new TauTransition(owner,dstNode,transitionText));
	}
	
	protected Inspection newInspection(CmlTransitionSet transitions,CmlCalculationStep step)
	{
		return new Inspection(new CmlTrace(owner.getTraceModel()), transitions,step);
	}
	
	protected Value lookupName(ILexNameToken name, Context question) throws ValueException
	{
		if(!name.getName().contains("."))
		{
			return question.lookup(name);
		}
		else
		{
			String[] tokens = name.getName().split("\\.");
			List<String> ids = Arrays.asList(tokens);
			Value val = question.lookup(new LexNameToken("", ids.get(0),name.getLocation()));
			return lookupName(val,ids.subList(1, ids.size()),question);
		}
	}
	
	protected Value lookupName(Value val, List<String> ids, Context question) throws ValueException
	{
		Value retVal = null;
		
		if(ids.size() == 0)
			retVal = val;
		else
		{

			String nextId = ids.get(0);
			//Value nextVal = question.check(new LexNameToken("",nextId,new LexLocation())); 
			if(val.deref() instanceof RecordValue)
			{
				RecordValue recordVal = val.recordValue(question);
				Value fieldValue = recordVal.fieldmap.get(nextId);
				retVal = lookupName(fieldValue,ids.subList(1, ids.size()),question);;
			}
			else if(val.deref() instanceof ObjectValue)
			{
				ObjectValue objectVal = val.objectValue(question);
				retVal = lookupName(objectVal.get(new LexNameToken("",nextId,new LexLocation()), false), ids.subList(1, ids.size()) ,question) ;
			}
		}
		
		return retVal;
	}

	@Override
	public Inspection createNewReturnValue(INode node, Context question)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inspection createNewReturnValue(Object node, Context question)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}