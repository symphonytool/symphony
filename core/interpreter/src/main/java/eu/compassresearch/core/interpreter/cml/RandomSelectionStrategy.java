package eu.compassresearch.core.interpreter.cml;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.util.AbstractValueInterpreter;
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
	
	private class ValueHelper extends AnswerCMLAdaptor<Value>
	{
		@Override
		public Value defaultPType(PType node) throws AnalysisException {
			return new UndefinedValue();
		}
		
		@Override
		public Value caseAIntNumericBasicType(AIntNumericBasicType node)
				throws AnalysisException {

			
			return new IntegerValue(rndValue.nextInt());
		}
		
	}
	
	private Value getRandomValueFromType(PType type)
	{
		try {
			return type.apply(new ValueHelper());
		} catch (AnalysisException e) {
			e.printStackTrace();
		}
		
		return new UndefinedValue();
	}

}
