package eu.compassresearch.core.interpreter.cml;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.cml.events.AbstractChannelEvent;
import eu.compassresearch.core.interpreter.cml.events.ChannelEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.values.AbstractValueInterpreter;

public class ConsoleSelectionStrategy implements
CmlCommunicationSelectionStrategy {
	
	Scanner scanIn = new Scanner(System.in);

	@Override
	public CmlEvent select(CmlAlphabet availableChannelEvents) {

		System.out.println("Available events : ");
		List<CmlEvent> events = new ArrayList<CmlEvent>(availableChannelEvents.getAllEvents());

		for(int i = 0; i <  events.size();i++)
		{
			CmlEvent obsEvent = events.get(i);
			System.out.println( "[" + i + "]" + obsEvent.toString());
		}
		
		CmlEvent chosenEvent = events.get(scanIn.nextInt());
		

		if(chosenEvent instanceof ChannelEvent && !((ChannelEvent)chosenEvent).isPrecise())
		{
			System.out.println("Enter value : "); 
			Value val;
			try {
				val = ((AChannelType)((AbstractChannelEvent)chosenEvent).getChannel().getType()).getType().
						apply(new ValueParser(),(AbstractChannelEvent)chosenEvent);
				((AbstractChannelEvent)chosenEvent).setValue(val);
			} catch (AnalysisException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
		}
		
		return chosenEvent;
	}

	class ValueParser extends QuestionAnswerCMLAdaptor<AbstractChannelEvent,Value>
	{
		@Override
		public Value defaultINode(INode node, AbstractChannelEvent chosenEvent) throws AnalysisException {

			throw new AnalysisException(node + " is not supported by the console");
		}
		
		@Override
		public Value caseAIntNumericBasicType(AIntNumericBasicType node, AbstractChannelEvent chosenEvent)
				throws AnalysisException {

			return new IntegerValue(scanIn.nextInt());
		}
		
		@Override
		public Value caseANamedInvariantType(ANamedInvariantType node, AbstractChannelEvent chosenEvent)
				throws AnalysisException {

			return node.getType().apply(this,chosenEvent);
		}
		
		@Override
		public Value caseAProductType(AProductType node, AbstractChannelEvent chosenEvent)
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
		public Value caseAUnionType(AUnionType node, AbstractChannelEvent chosenEvent) throws AnalysisException {
			
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
		public Value caseAQuoteType(AQuoteType node, AbstractChannelEvent chosenEvent) throws AnalysisException {
			
			return new QuoteValue(node.getValue().value);
		}
	}
}
