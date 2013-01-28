package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.eval.ExpressionEvaluator;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.scheduler.InitThread;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AIdentifierVarsetExpression;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.cml.events.PrefixEvent;
import eu.compassresearch.core.interpreter.runtime.CmlContext;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlExpressionEvaluator extends QuestionAnswerCMLAdaptor<CmlContext, Value>
{
	class VdmExpressionEvaluator extends ExpressionEvaluator {
		
		@Override
		public Value defaultPExp(PExp node, Context question)
				throws AnalysisException {
		
			if(node instanceof PCMLExp)
				//FIXME if the burger is ever a real case then this context is wrong!
				throw new RuntimeException("We are now in the  (CML | VDM | CML) burger, decide what to do!");
				//return defaultPCMLExp((PCMLExp)node,cmlContext);
			else
				return node.apply(this,question);
		}
	}
	
	private VdmExpressionEvaluator vdmExpEvaluator =  new VdmExpressionEvaluator();
	private CmlContext cmlContext = null;

	public CmlExpressionEvaluator()
	{
		InitThread initThread = new InitThread(Thread.currentThread());
        BasicSchedulableThread.setInitialThread(initThread);
        
     // TODO For now we diable VDM typechecking, since something is not
//      // working
//      Settings.dynamictypechecks = false;
	}
	
	@Override
	public Value defaultPExp(PExp node, CmlContext question)
			throws AnalysisException {
		cmlContext = question;
		return vdmExpEvaluator.defaultPExp(node,question.getVdmContext());
	}
	
	@Override
	public Value caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node,
			CmlContext question) throws AnalysisException {
		
		return caseEnumVarSetExp(node.getIdentifiers(), question);
	}
	
	@Override
	public Value caseAEnumVarsetExpression(AEnumVarsetExpression node,
			CmlContext question) throws AnalysisException {

		return caseEnumVarSetExp(node.getIdentifiers(), question);

	}
	
	private Value caseEnumVarSetExp(List<LexIdentifierToken> ids, CmlContext question)
	{
		Set<CmlEvent> coms = new HashSet<CmlEvent>();

		for(LexIdentifierToken id : ids)
		{
			coms.add(createEvent(id, question));
		}

		return new CmlAlphabet(coms);
	}
	
	private ObservableEvent createEvent(LexIdentifierToken id, CmlContext question )
	{
		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("|CHANNELS|",id);
		CMLChannelValue chanValue = question.<CMLChannelValue>lookup(channelName);

		AChannelType chanType = (AChannelType)chanValue.getType(); 
		if(chanType.getType() == null)
		{		
			return new PrefixEvent(null,chanValue);
		}
		else
		{
			return new CmlCommunicationEvent(null, chanValue, null);
		}
	}
	
	@Override
	public Value caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node, CmlContext question)
			throws AnalysisException {

		return new CmlAlphabet(createEvent(node.getIdentifier(), question));
	}
	
	
}
