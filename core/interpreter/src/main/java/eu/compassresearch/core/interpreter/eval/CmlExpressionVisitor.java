package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.eval.DelegateExpressionEvaluator;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.VdmRuntime;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.scheduler.InitThread;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AIdentifierVarsetExpression;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.events.CmlTransition;
import eu.compassresearch.core.interpreter.cml.events.CmlEventFactory;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlExpressionVisitor extends QuestionAnswerCMLAdaptor<Context, Value>
{
	static{
		VdmRuntime.initialize(new CmlExpressionVisitor());
	}
	
	class VdmExpressionEvaluator extends DelegateExpressionEvaluator{
		
		@Override
		public Value defaultPExp(PExp node, Context question)
				throws AnalysisException {
		
			if(node instanceof PCMLExp)
				//FIXME if the burger is ever a real case then this context is wrong!
				//throw new RuntimeException("We are now in the  (CML | VDM | CML) burger, decide what to do!");
				return defaultPCMLExp((PCMLExp)node,question);
			else
				return node.apply(this,question);
		}
	}
	
	private VdmExpressionEvaluator vdmExpEvaluator =  new VdmExpressionEvaluator();

	public CmlExpressionVisitor()
	{
		InitThread initThread = new InitThread(Thread.currentThread());
        BasicSchedulableThread.setInitialThread(initThread);
        
     // TODO For now we diable VDM typechecking, since something is not
//      // working
//      Settings.dynamictypechecks = false;
	}
	
	@Override
	public Value defaultPExp(PExp node, Context question)
			throws AnalysisException {
		return vdmExpEvaluator.defaultPExp(node,question);
	}
	
	@Override
	public Value caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node,
			Context question) throws AnalysisException {
		
		return caseEnumVarSetExp(node.getIdentifiers(), question);
	}
	
	@Override
	public Value caseAEnumVarsetExpression(AEnumVarsetExpression node,
			Context question) throws AnalysisException {

		return caseEnumVarSetExp(node.getIdentifiers(), question);

	}
	
	private Value caseEnumVarSetExp(List<LexIdentifierToken> ids, Context question)
	{
		Set<CmlTransition> coms = new HashSet<CmlTransition>();

		for(LexIdentifierToken id : ids)
		{
			coms.add(createEvent((LexIdentifierToken)id.clone(), question));
		}

		return new CmlAlphabet(coms);
	}
	
	private ObservableEvent createEvent(LexIdentifierToken id, Context question )
	{
		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("|CHANNELS|",id);
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);

		AChannelType chanType = (AChannelType)chanValue.getType(); 
		if(chanType.getType() == null)
		{		
			return CmlEventFactory.newPrefixEvent(chanValue);
		}
		else
		{
			return CmlEventFactory.newCmlCommunicationEvent(chanValue, null);
		}
	}
	
	@Override
	public Value caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node, Context question)
			throws AnalysisException {

		return new CmlAlphabet(createEvent(node.getIdentifier(), question));
	}
	
	@Override
	public Value caseABracketedExp(ABracketedExp node, Context question)
			throws AnalysisException {
	
		return node.getExpression().apply(this,question);
	}
	
	
	@Override
	public Value caseAUnresolvedPathExp(AUnresolvedPathExp node,
			Context question) throws AnalysisException {
	
		//FIXME This is just for testing, this should be done in a more generic way.
		
		Iterator<LexIdentifierToken> iter = node.getIdentifiers().iterator();
		
		Value val = question.check(new LexNameToken("",iter.next())); 
		
		if(val.deref() instanceof RecordValue)
		{
			RecordValue recordVal = val.recordValue(question);
			Value fieldValue = recordVal.fieldmap.get(iter.next().getName());
			
			return fieldValue;
		}
		else if(val.deref() instanceof ObjectValue)
		{
			ObjectValue objectVal = val.objectValue(question);
			return objectVal.get(new LexNameToken("",(LexIdentifierToken)iter.next().clone()), false) ;
		}
		
		return val;
	}
	
}
