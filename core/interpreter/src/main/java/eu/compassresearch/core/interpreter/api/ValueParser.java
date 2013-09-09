package eu.compassresearch.core.interpreter.api;

import java.util.Scanner;

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

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;

public class ValueParser extends AnswerCMLAdaptor<Value>
{
	Scanner scanIn = new Scanner(System.in);
	
	@Override
	public Value defaultINode(INode node) throws AnalysisException {

		throw new AnalysisException(node + " is not supported by the console");
	}
	
	@Override
	public Value caseAIntNumericBasicType(AIntNumericBasicType node)
			throws AnalysisException {

		return new IntegerValue(scanIn.nextInt());
	}
	
	@Override
	public Value caseANatNumericBasicType(ANatNumericBasicType node) throws AnalysisException {

		try {
			return new NaturalValue(scanIn.nextLong());
		} catch (Exception e) {
			throw new AnalysisException(e.getMessage(),e);
		}
	}
	
	@Override
	public Value caseANamedInvariantType(ANamedInvariantType node)
			throws AnalysisException {

		return node.getType().apply(this);
	}
	
	@Override
	public Value caseAProductType(AProductType node)
			throws AnalysisException {

		ValueList argvals = new ValueList();
		
		for(int i = 0 ; i < node.getTypes().size();i++)
		{
//			Value val = ((TupleValue)chosenEvent.getValue()).values.get(i);
//			if(AbstractValueInterpreter.isValueMostPrecise(val))
//				argvals.add(val);
//			else
				argvals.add(node.getTypes().get(i).apply(this));
		}
		return new TupleValue(argvals);
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
		
		return new QuoteValue(node.getValue().getValue());
	}
}