package eu.compassresearch.core.interpreter.cml;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.QuoteValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;

public class ConsoleSelectionStrategy implements
CmlCommunicationSelectionStrategy {
	
	Scanner scanIn = new Scanner(System.in);

	@Override
	public ObservableEvent select(CmlAlphabet availableChannelEvents) {

		System.out.println("Available events : ");
		List<ObservableEvent> events = new ArrayList<ObservableEvent>(availableChannelEvents.getObservableEvents());

		for(int i = 0; i <  events.size();i++)
		{
			ObservableEvent obsEvent = events.get(i);
			System.out.println( "[" + i + "]" + obsEvent.toString());
		}
		
		ObservableEvent chosenEvent = events.get(scanIn.nextInt());
		

		if(!chosenEvent.isValuePrecise())
		{
			System.out.println("Enter value : "); 
			Value val;
			try {
				val = ((AChannelType)chosenEvent.getChannel().getType()).getType().apply(new ValueParser());
				chosenEvent.setValue(val);
			} catch (AnalysisException e) {
				e.printStackTrace();
			}
			
		}
		
		return chosenEvent;
	}

	class ValueParser extends AnswerCMLAdaptor<Value>
	{
		@Override
		public Value caseAIntNumericBasicType(AIntNumericBasicType node)
				throws AnalysisException {

			return new IntegerValue(scanIn.nextInt());
		}
		
		@Override
		public Value caseANamedInvariantType(ANamedInvariantType node)
				throws AnalysisException {

			return node.getType().apply(this);
		}
		
		@Override
		public Value caseAUnionType(AUnionType node) throws AnalysisException {
			
			PType type;

			for(int i = 0; i <  node.getTypes().size();i++)
			{
				type = node.getTypes().get(i);
				System.out.println( "[" + i + "]" + type.toString());
			}

			type = node.getTypes().get(scanIn.nextInt());

			return type.apply(this);
		}
		
		@Override
		public Value caseAQuoteType(AQuoteType node) throws AnalysisException {
			
			return new QuoteValue(node.getValue().value);
		}
	}
}
