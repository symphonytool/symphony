package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.PType;
import org.overture.ast.util.definitions.ClassList;
import org.overture.interpreter.eval.DelegateExpressionEvaluator;
import org.overture.interpreter.runtime.ClassInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.VdmRuntime;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.scheduler.InitThread;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AIdentifierVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionFactory;
import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;
import eu.compassresearch.core.interpreter.api.values.AnyValue;
import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

@SuppressWarnings("serial")
public class CmlExpressionVisitor extends QuestionAnswerCMLAdaptor<Context, Value>
{
	static{
		VdmRuntime.initialize(new CmlExpressionVisitor());
		try {
			new ClassInterpreter(new ClassList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	protected ChannelNameValue createChannelNameValue(ILexIdentifierToken id, Context question) throws AnalysisException
	{
		//find the channel value
		//TODO this might change if channel renaming does not 
		//require the renamed channel name to be defined
		ILexNameToken channelName = NamespaceUtility.createChannelName(id);
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
		
		return new ChannelNameValue(chanValue);
	}
	
	protected ChannelNameValue createChannelNameValue(ANameChannelExp chanNameExp, Context question) throws AnalysisException
	{
		//find the channel value
		//TODO this might change if channel renaming does not 
		//require the renamed channel name to be defined
		ILexNameToken channelName = NamespaceUtility.createChannelName(chanNameExp.getIdentifier());
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
		
		//extract the values
		List<Value> values = new LinkedList<Value>();
		for(int i = 0 ; i < chanValue.getValueTypes().size();i++)
		{
			//if the index is less than the number of expressions its defined
			//else we put an anyValue
			if(i < chanNameExp.getExpressions().size())
				values.add(chanNameExp.getExpressions().get(i).apply(this,question));
			else
				values.add(new AnyValue(chanValue.getValueTypes().get(i)));
		}
		
		return new ChannelNameValue(chanValue, values);
	}
	
	@Override
	public ChannelNameSetValue caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node,
			Context question) throws AnalysisException {
		
		Set<ChannelNameValue> coms = new HashSet<ChannelNameValue>();
		
		for (ANameChannelExp chanNameExp : node.getChannelNames())
		{
			ChannelNameValue channelName = createChannelNameValue(chanNameExp, question);
			coms.add(channelName);
		}
		
		return new ChannelNameSetValue(coms);
	}
	
	@Override
	public ChannelNameSetValue caseAEnumVarsetExpression(AEnumVarsetExpression node,
			Context question) throws AnalysisException {
		
		Set<ChannelNameValue> coms = new HashSet<ChannelNameValue>();
		for (ANameChannelExp chanNameExp : node.getChannelNames())
		{
			ChannelNameValue channelName = createChannelNameValue(chanNameExp, question);
			coms.add(channelName);
		}
		
		return new ChannelNameSetValue(coms);
	}
	
	@Override
	public Value caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node, Context question)
			throws AnalysisException {

		ILexNameToken name = NamespaceUtility.createChansetName(node.getIdentifier());
		return question.lookup(name);
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
