package eu.compassresearch.core.interpreter.api;

import java.util.ArrayList;
import java.util.Random;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.values.AbstractValueInterpreter;
/**
 * This class implements a random selection CMLCommunicaiton of the alphabet 
 * @author akm
 *
 */
public class RandomSelectionStrategy implements
		SelectionStrategy {

	private static final long randomSeed = 675674345;
	private static final Random rndChoice = new Random(randomSeed);
	
	@Override
	public CmlTransition select(CmlAlphabet availableChannelEvents) {
		
		CmlTransition selectedComm = null;
		
		//If nothing is available then we do nothing
		//TODO This is probably not the best thing to do
		if(!availableChannelEvents.isEmpty())
		{
			int nElems = availableChannelEvents.getAllEvents().size();
			
			//if the number of possible transitions are greater than one then the
			//CMlTock events are filtered out
//			if(nElems > 1)
//			{
//				for(CmlTransition t : availableChannelEvents.g)
//			}			
			
			//pick a random but deterministic choice
			selectedComm = new ArrayList<CmlTransition>(
					availableChannelEvents.getAllEvents()).get(rndChoice.nextInt(nElems));
			
			//If the selected transition contains a value that are not precise then we
			//need to resolve this and let the environment take a random choice
			if(selectedComm instanceof ChannelEvent && !((ChannelEvent)selectedComm).isPrecise())
			{
				AChannelType t = (AChannelType)((ChannelEvent)selectedComm).getChannelName().getChannel().getType();
				
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
			return type.apply(new RandomVDMValueGenerator(randomSeed),chosenEvent);
		} catch (AnalysisException e) {
			e.printStackTrace();
		}
		
		return new UndefinedValue();
	}
	
}
