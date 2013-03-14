package eu.compassresearch.core.interpreter.cml;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.StateContext;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.eval.CmlDefinitionVisitor;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.values.AbstractValueInterpreter;
/**
 * This class implements a random selection CMLCommunicaiton of the alphabet 
 * @author akm
 *
 */
public class RandomSelectionStrategy implements
		CmlCommunicationSelectionStrategy {

	private static final long randomSeed = 675674345;
	private static final Random rndChoice = new Random(randomSeed);
	private static final Random rndValue = new Random(randomSeed);
	
	@Override
	public ObservableEvent select(CmlAlphabet availableChannelEvents) {
		
		Set<ObservableEvent> comms = availableChannelEvents.getObservableEvents();
		ObservableEvent selectedComm = null;
		
		if(!comms.isEmpty())
		{
			int nElems = availableChannelEvents.getObservableEvents().size();
			
			//pick a random but deterministic choice
			selectedComm = new ArrayList<ObservableEvent>(
					availableChannelEvents.getObservableEvents()).get(rndChoice.nextInt(nElems));
			
			if(!selectedComm.isValuePrecise())
			{
				AChannelType t = (AChannelType)selectedComm.getChannel().getType();
				
				selectedComm.setValue(
						AbstractValueInterpreter.meet(
						selectedComm.getValue(),
						getRandomValueFromType(t.getType())));
			}
		}
		//CmlRuntime.logger().fine("Available events " + availableChannelEvents.getObservableEvents());
		CmlRuntime.logger().fine("The supervisor environment picks : " + selectedComm);
		
		return selectedComm;
	}
	
	class RandomValueGenerator extends AnswerCMLAdaptor<Value>
	{
		@Override
		public Value caseAIntNumericBasicType(AIntNumericBasicType node)
				throws AnalysisException {

			return new IntegerValue(rndValue.nextInt());
		}
		
		@Override
		public Value caseANamedInvariantType(ANamedInvariantType node)
				throws AnalysisException {

//			if(node.getInvDef() != null)
//			{
//				StateContext stateContext = new StateContext(node.getLocation(), "invaraint function context");
//				NameValuePairList nvpl = node.getInvDef().apply(new CmlDefinitionEvaluator(),stateContext);
//				FunctionValue func  = nvpl.get(0).value.functionValue(stateContext);
//				func.e
//				
//			}
			
			return node.getType().apply(this);
		}
		
		@Override
		public Value caseAUnionType(AUnionType node) throws AnalysisException {
			
			PType type = node.getTypes().get(rndValue.nextInt(node.getTypes().size()));

			return type.apply(this);
		}
		
		@Override
		public Value caseAQuoteType(AQuoteType node) throws AnalysisException {
			
			return new QuoteValue(node.getValue().value);
		}
	}
	
	private Value getRandomValueFromType(PType type)
	{
		try {
			return type.apply(new RandomValueGenerator());
		} catch (AnalysisException e) {
			e.printStackTrace();
		}
		
		return new UndefinedValue();
	}

}
