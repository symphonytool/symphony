package eu.compassresearch.core.interpreter.api.values;

import java.util.List;
import java.util.Vector;

import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.ASkipStm;
import org.overture.ast.types.AOperationType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class CmlOperationValue extends OperationValue {

	//The name of the special return value in a assignment call context
	//This is used to retrieve the result of a operation
	public static ILexNameToken ReturnValueName()
	{
		return new LexNameToken("|CALL|","RETURN",new LexLocation());
	}
	
	private static final long serialVersionUID = 1L;
	public final AExplicitCmlOperationDefinition expldef;
	public final AImplicitCmlOperationDefinition impldef;
	private final PExp precondition;
	private final PExp postcondition;
	public final AStateDefinition state;
	private PAction body;
	private CmlBehaviour currentlyExecutingThread = null;

	public CmlOperationValue(AExplicitCmlOperationDefinition def,
							AStateDefinition state)
	{
		super(convertToVDMDefinition(def),null,null,null);
		this.expldef = def;
		this.impldef = null;
		this.setBody(def.getBody());
		this.precondition = def.getPrecondition();
		this.postcondition = def.getPostcondition();
		this.state = state;
	}
	
	private static AExplicitOperationDefinition convertToVDMDefinition(AExplicitCmlOperationDefinition def)
	{
		return new AExplicitOperationDefinition(def.getLocation(),def.getName().clone(), def.getNameScope(), 
				def.getUsed(),def.getClassDefinition() != null ? def.getClassDefinition().clone() : null,def.getAccess().clone(), def.getPass(), 
				def.getParameterPatterns(), new ASkipStm(), def.getPrecondition() != null ? def.getPrecondition().clone() : null, 
						def.getPostcondition() != null ? def.getPostcondition().clone() : null,def.getType().clone(),null,null,
				(List<? extends PDefinition>) def.getParamDefinitions().clone(),null,def.getActualResult(), 
				def.getIsConstructor());
	}
	
	private static AImplicitOperationDefinition convertToVDMDefinition(AImplicitCmlOperationDefinition def)
	{
		//return new AImplicitOperationDefinition(def.getLocation(), def.getNameScope(), def.getUsed(),def.getAccess().clone(), def.getPass(), def.getParameterPatterns(), new ASkipStm(), def.getPrecondition().clone(), def.getPostcondition().clone(), def.getIsConstructor());
		return null;
	}

//	public CmlOperationValue(AImplicitCmlOperationDefinition def,
//			AStateDefinition state)
//	{
//		this.impldef = def;
//		this.expldef = null;
//		this.name = def.getName();
//		this.type = (AOperationType)def.getType();
//		this.paramPatterns = new Vector<PPattern>();
//
//		for (APatternListTypePair ptp : def.getParameterPatterns())
//		{
//			getParamPatterns().addAll(ptp.getPatterns());
//		}
//
//		this.precondition = impldef.getPrecondition();
//		this.postcondition = impldef.getPostcondition();
//		this.state = state;
//	}
	
	public PAction getBody() {
		return body;
	}

	private void setBody(PAction body) {
		this.body = body;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof Value)
		{
			Value val = ((Value)other).deref();

			if (val instanceof CmlOperationValue)
			{
				CmlOperationValue ov = (CmlOperationValue)val;
				return ov.type.equals(type);
			}
		}

		return false;
	}
	
	@Override
	public String kind() {
		return "CML operation";
	}

	@Override
	public Object clone() {
		if (expldef != null)
		{
			return new CmlOperationValue(expldef,state);
		}
		else
		{
			return null;
//			return new CmlOperationValue(impldef, state);
		}
	}

	public List<PPattern> getParamPatterns() {
		return paramPatterns;
	}

	public AOperationType getType() {
		return type;
	}

	public void setCurrentlyExecutingThread(CmlBehaviour currentlyExecutingThread) {
		this.currentlyExecutingThread = currentlyExecutingThread;
	}
	
	public PExp getPrecondition()
	{
		return this.precondition;
	}
	
	public PExp getPostcondition()
	{
		return this.postcondition;
	}

	@Override
	public Value eval(ILexLocation from, ValueList argValues, Context ctxt)
			throws ValueException
	{
		throw new ValueException(0,"An operation call occur in an expression!, is this allowed?",ctxt);
	}
//	public RootContext newContext(LexLocation from, String title, Context ctxt)
//	{
//		RootContext argContext;
//
//		if (self != null)
//		{
//			argContext = new ObjectContext(from, title, ctxt, self);
//		}
//		else if (classdef != null)
//		{
//			argContext = new ProcessContext(from, title, ctxt, classdef);
//		}
//		else
//		{
//			argContext = new StateContext(from, title, ctxt, stateContext);
//		}
//
//		return argContext;
//	}
	
}
