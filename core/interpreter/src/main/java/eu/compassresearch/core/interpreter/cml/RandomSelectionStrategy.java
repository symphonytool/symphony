package eu.compassresearch.core.interpreter.cml;

import java.util.Random;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
/**
 * This class implements a random selection CMLCommunicaiton of the alphabet 
 * @author akm
 *
 */
public class RandomSelectionStrategy implements
		CmlCommunicationSelectionStrategy {

	@Override
	public ObservableEvent select(CmlAlphabet availableChannelEvents) {
		
		Set<ObservableEvent> comms = availableChannelEvents.getObservableEvents();
		ObservableEvent selectedComm = null;
		
		if(!comms.isEmpty())
		{
			selectedComm = availableChannelEvents.getObservableEvents().iterator().next();
			
//			//it has undefined values
//			if(!selectedComm.isResolved())
//			{
//				AChannelType t = (AChannelType)selectedComm.getChannel().getType();
//				
//				final Value value = getRandomValueFromType(t.getType());
//				
//				selectedComm.resolve(new EventResolver() {
//					
//					@Override
//					public void resolve(List<CommunicationParameter> params) {
//						for(CommunicationParameter param : params)
//							if(param instanceof InputParameter)
//									param.setValue(value);
//					}
//				});
//			}
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

			return new IntegerValue(new Random().nextInt());
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
