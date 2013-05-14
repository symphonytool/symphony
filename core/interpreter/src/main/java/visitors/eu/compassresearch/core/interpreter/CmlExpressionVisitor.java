package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
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
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.cml.core.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.transitions.CmlTransitionFactory;
import eu.compassresearch.core.interpreter.cml.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.cml.transitions.ObservableEvent;
import eu.compassresearch.core.interpreter.cml.values.CMLChannelValue;

class CmlExpressionVisitor extends QuestionAnswerCMLAdaptor<Context, Value>
{
	static{
      VdmRuntime.initialize(new CmlExpressionVisitor());
	}
	
	class VdmExpressionEvaluator extends DelegateExpressionEvaluator{
		
		@Override
		public Value defaultPExp(PExp node, Context question)
				throws AnalysisException {
		
			if(node instanceof PCMLExp)
				return defaultPCMLExp((PCMLExp)node,question);
			else
				return node.apply(this,question);
		}
	}
	
	private VdmExpressionEvaluator vdmExpEvaluator =  new VdmExpressionEvaluator();

	public CmlExpressionVisitor()
	{
		//To be able to work together with the VDM interpreter we need to set this
		//to the current thread.
		InitThread initThread = new InitThread(Thread.currentThread());
        BasicSchedulableThread.setInitialThread(initThread);
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
	
	private Value caseEnumVarSetExp(List<ILexIdentifierToken> ids, Context question)
	{
		Set<CmlTransition> coms = new HashSet<CmlTransition>();

		for(ILexIdentifierToken id : ids)
		{
			coms.add(createEvent((ILexIdentifierToken)id.clone(), question));
		}

		return new CmlAlphabet(coms);
	}
	
	private ObservableEvent createEvent(ILexIdentifierToken id, Context question )
	{
		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("|CHANNELS|",id);
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);

		AChannelType chanType = (AChannelType)chanValue.getType(); 
		if(chanType.getType() == null)
		{		
			return CmlTransitionFactory.newSynchronizationEvent(chanValue);
		}
		else
		{
			return CmlTransitionFactory.newCmlCommunicationEvent(chanValue, null);
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
		
		Iterator<ILexIdentifierToken> iter = node.getIdentifiers().iterator();
		
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
			return objectVal.get(new LexNameToken("",(ILexIdentifierToken)iter.next().clone()), false) ;
		}
		
		return val;
	}
	
}
