package eu.compassresearch.core.interpreter.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.NaturalValue;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.values.AbstractValueInterpreter;

public class ConsoleSelectionStrategy implements
SelectionStrategy {
	
	Scanner scanIn = new Scanner(System.in);
	private RandomSelectionStrategy rndSelect = new RandomSelectionStrategy();
	
	private boolean isSystemSelect(CmlAlphabet availableChannelEvents)
	{
		return availableChannelEvents.getSilentTransitions().size() > 0;
	}
	
	private CmlTransition systemSelect(CmlAlphabet availableChannelEvents)
	{
		return rndSelect.select(new CmlAlphabet((Set)availableChannelEvents.getSilentTransitions()));
	}
	
	private CmlTransition userSelect(CmlAlphabet availableChannelEvents)
	{
		System.out.println("Available events : ");
		List<CmlTransition> events = new ArrayList<CmlTransition>(availableChannelEvents.getAllEvents());

		for(int i = 0; i <  events.size();i++)
		{
			CmlTransition obsEvent = events.get(i);
			System.out.println( "[" + i + "]" + obsEvent.toString());
		}
		
		CmlTransition chosenEvent = events.get(scanIn.nextInt());
		

		if(chosenEvent instanceof ChannelEvent && !((ChannelEvent)chosenEvent).isPrecise())
		{
			System.out.println("Enter value : "); 
			Value val;
			try {
				val = ((AChannelType)((ChannelEvent)chosenEvent).getChannel().getType()).getType().
						apply(new ValueParser(),(ChannelEvent)chosenEvent);
				((ChannelEvent)chosenEvent).setValue(val);
			} catch (AnalysisException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
		}
		
		return chosenEvent;
	}

	@Override
	public CmlTransition select(CmlAlphabet availableChannelEvents) {

		//At this point we don't want the internal transition to propagate 
		//to the user, so we randomly choose all the possible internal transitions
		//before we let anything through to the user
		if(isSystemSelect(availableChannelEvents))
			return systemSelect(availableChannelEvents);
		else
			return userSelect(availableChannelEvents);
	}
	
	class ValueParser extends QuestionAnswerCMLAdaptor<ChannelEvent,Value>
	{
		@Override
		public Value defaultINode(INode node, ChannelEvent chosenEvent) throws AnalysisException {

			throw new AnalysisException(node + " is not supported by the console");
		}
		
		@Override
		public Value caseAIntNumericBasicType(AIntNumericBasicType node, ChannelEvent chosenEvent)
				throws AnalysisException {

			return new IntegerValue(scanIn.nextInt());
		}
		
		@Override
		public Value caseANatNumericBasicType(ANatNumericBasicType node,
				ChannelEvent question) throws AnalysisException {

			try {
				return new NaturalValue(scanIn.nextLong());
			} catch (Exception e) {
				throw new AnalysisException(e.getMessage(),e);
			}
		}
		
		@Override
		public Value caseANamedInvariantType(ANamedInvariantType node, ChannelEvent chosenEvent)
				throws AnalysisException {

			return node.getType().apply(this,chosenEvent);
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
		
		@Override
		public Value caseAUnionType(AUnionType node, ChannelEvent chosenEvent) throws AnalysisException {
			
			PType type;

			for(int i = 0; i <  node.getTypes().size();i++)
			{
				type = node.getTypes().get(i);
				System.out.println( "[" + i + "]" + type.toString());
			}

			type = node.getTypes().get(scanIn.nextInt());

			return type.apply(this, chosenEvent);
		}
		
		@Override
		public Value caseAQuoteType(AQuoteType node, ChannelEvent chosenEvent) throws AnalysisException {
			
			return new QuoteValue(node.getValue().getValue());
		}
	}
}
