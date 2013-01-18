package eu.compassresearch.core.interpreter.values;

import java.util.List;
import java.util.Vector;

import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.AOperationType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.Value;
import org.overture.typechecker.assistant.definition.PAccessSpecifierAssistantTC;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;

public class CmlOperationValue extends CmlValue {

	private static final long serialVersionUID = 1L;
	public final AExplicitCmlOperationDefinition expldef;
	public final AImplicitCmlOperationDefinition impldef;
	public final LexNameToken name;
	public final AOperationType type;
	public final List<PPattern> paramPatterns;

	public final FunctionValue precondition;
	public final FunctionValue postcondition;
	public final AStateDefinition state;

	private PAction body;
	private LexNameToken stateName = null;
	private Context stateContext = null;
	private CmlObjectValue self = null;

	public boolean isConstructor = false;
	public boolean isStatic = false;

	public CmlOperationValue(AExplicitCmlOperationDefinition def,
		FunctionValue precondition, FunctionValue postcondition,
		AStateDefinition state)
	{
		this.expldef = def;
		this.impldef = null;
		this.name = def.getName();
		this.type = (AOperationType)def.getType();
		this.paramPatterns = def.getParameterPatterns();
		this.setBody(def.getBody());
		this.precondition = precondition;
		this.postcondition = postcondition;
		this.state = state;
	}

	public CmlOperationValue(AImplicitCmlOperationDefinition def,
		FunctionValue precondition, FunctionValue postcondition,
		AStateDefinition state)
	{
		this.impldef = def;
		this.expldef = null;
		this.name = def.getName();
		this.type = (AOperationType)def.getType();
		this.paramPatterns = new Vector<PPattern>();

		for (APatternListTypePair ptp : def.getParameterPatterns())
		{
			paramPatterns.addAll(ptp.getPatterns());
		}

		this.precondition = precondition;
		this.postcondition = postcondition;
		this.state = state;
	}
	
	public PAction getBody() {
		return body;
	}

	private void setBody(PAction body) {
		this.body = body;
	}

	@Override
	public String toString()
	{
		return type.toString();
	}

	public void setSelf(CmlObjectValue self)
	{
		if (!isStatic)
		{
			this.self = self;
		}
	}

	public CmlObjectValue getSelf()
	{
		return self;
	}
	
	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String kind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
