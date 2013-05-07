package eu.compassresearch.core.interpreter.cml;

import java.util.ArrayList;
import java.util.Random;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.cml.events.ChannelEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlTransition;
import eu.compassresearch.core.interpreter.values.AbstractValueInterpreter;
/**
 * This class implements a random selection CMLCommunicaiton of the alphabet 
 * @author akm
 *
 */
public class RandomSelectionStrategy implements
		CmlEventSelectionStrategy {

	private static final long randomSeed = 675674345;
	private static final Random rndChoice = new Random(randomSeed);
	private static final Random rndValue = new Random(randomSeed);
	
	@Override
	public CmlTransition select(CmlAlphabet availableChannelEvents) {
		
		CmlTransition selectedComm = null;
		
		if(!availableChannelEvents.isEmpty())
		{
			int nElems = availableChannelEvents.getAllEvents().size();
			
			//pick a random but deterministic choice
			selectedComm = new ArrayList<CmlTransition>(
					availableChannelEvents.getAllEvents()).get(rndChoice.nextInt(nElems));
			
			if(selectedComm instanceof ChannelEvent && !((ChannelEvent)selectedComm).isPrecise())
			{
				AChannelType t = (AChannelType)((ChannelEvent)selectedComm).getChannel().getType();
				
				((ChannelEvent)selectedComm).setValue(
						AbstractValueInterpreter.meet(
						((ChannelEvent)selectedComm).getValue(),
						getRandomValueFromType(t.getType(),(ChannelEvent)selectedComm)));
			}
		}
		//CmlRuntime.logger().fine("Available events " + availableChannelEvents.getObservableEvents());
		CmlRuntime.logger().fine("The supervisor environment picks : " + selectedComm);
		
		return selectedComm;
	}
	
	private Value getRandomValueFromType(PType type, ChannelEvent chosenEvent)
	{
		try {
			return type.apply(new RandomValueGenerator(),chosenEvent);
		} catch (AnalysisException e) {
			e.printStackTrace();
		}
		
		return new UndefinedValue();
	}
	
	class RandomValueGenerator extends QuestionAnswerCMLAdaptor<ChannelEvent,Value>
	{
		@Override
		public Value caseAIntNumericBasicType(AIntNumericBasicType node, ChannelEvent chosenEvent)
				throws AnalysisException {

			return new IntegerValue(rndValue.nextInt());
		}
		
		@Override
		public Value caseANamedInvariantType(ANamedInvariantType node, ChannelEvent chosenEvent)
				throws AnalysisException {

//			if(node.getInvDef() != null)
//			{
//				StateContext stateContext = new StateContext(node.getLocation(), "invaraint function context");
//				NameValuePairList nvpl = node.getInvDef().apply(new CmlDefinitionEvaluator(),stateContext);
//				FunctionValue func  = nvpl.get(0).value.functionValue(stateContext);
//				func.e
//				
//			}
			
			return node.getType().apply(this,chosenEvent);
		}
		
		@Override
		public Value caseAUnionType(AUnionType node, ChannelEvent chosenEvent) throws AnalysisException {
			
			PType type = node.getTypes().get(rndValue.nextInt(node.getTypes().size()));

			return type.apply(this,chosenEvent);
		}
		
		@Override
		public Value caseAQuoteType(AQuoteType node, ChannelEvent chosenEvent) throws AnalysisException {
			
			return new QuoteValue(node.getValue().value);
		}
		
		@Override
		public Value caseAProductType(AProductType node, ChannelEvent chosenEvent)
				throws AnalysisException {

			ValueList argvals = new ValueList();
			
			for(int i = 0 ; i < node.getTypes().size();i++)
			{
				Value val = ((TupleValue)chosenEvent.getValue()).values.get(i);
				if(AbstractValueInterpreter.isValueMostPrecise(val))
					argvals.add(val);
				else
					argvals.add(node.getTypes().get(i).apply(this,chosenEvent));
			}
			return new TupleValue(argvals);
		}
	}
}
