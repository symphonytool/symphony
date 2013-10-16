package eu.compassresearch.core.interpreter.api.values;

import java.util.List;

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
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class CmlOperationValue extends OperationValue
{

	// The name of the special return value in a assignment call context
	// This is used to retrieve the result of a operation
	public static ILexNameToken ReturnValueName()
	{
		return new LexNameToken("|CALL|", "RETURN", new LexLocation());
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
		super(convertToVDMDefinition(def), null, null, null);
		this.expldef = def;
		this.impldef = null;
		this.setBody(def.getBody());
		this.precondition = def.getPrecondition();
		this.postcondition = def.getPostcondition();
		this.state = state;
	}

	public CmlOperationValue(AImplicitCmlOperationDefinition def,
			AStateDefinition state)
	{
		super(convertToVDMDefinition(def), null, null, null);
		this.expldef = null;
		this.impldef = def;
		this.setBody(null);
		this.precondition = def.getPrecondition();
		this.postcondition = def.getPostcondition();
		this.state = state;
	}

	@SuppressWarnings("unchecked")
	private static AExplicitOperationDefinition convertToVDMDefinition(
			AExplicitCmlOperationDefinition d)
	{
		AExplicitOperationDefinition o = new AExplicitOperationDefinition();
		o.setLocation(d.getLocation());
		o.setName(d.getName().clone());
		o.setNameScope(d.getNameScope());
		o.setUsed(d.getUsed());
		o.setClassDefinition(d.getClassDefinition() != null ? d.getClassDefinition().clone()
				: null);
		o.setAccess(d.getAccess().clone());
		o.setType(d.getType().clone());
		o.setPass(d.getPass());
		o.setBody(new ASkipStm());
		o.setPrecondition(d.getPrecondition() != null ? d.getPrecondition().clone()
				: null);
		o.setPostcondition(d.getPostcondition() != null ? d.getPostcondition().clone()
				: null);
		o.setPredef(d.getPredef());
		o.setPostdef(d.getPostdef());
		o.setState(d.getState());
		o.setActualResult(d.getActualResult());
		o.setIsConstructor(d.getIsConstructor());
		o.setParameterPatterns((List<? extends PPattern>) d.getParameterPatterns().clone());
		o.setParamDefinitions((List<? extends PDefinition>) d.getParamDefinitions().clone());

		return o;
		// AExplicitOperationDefinition node = new AExplicitOperationDefinition(
		// cloneNode(_location, oldToNewMap),
		// _name,
		// _nameScope,
		// _used,
		// _classDefinition,
		// cloneNode(_access, oldToNewMap),
		// _type,
		// _pass,
		// cloneNode(_body, oldToNewMap),
		// cloneNode(_precondition, oldToNewMap),
		// cloneNode(_postcondition, oldToNewMap),
		// _predef,
		// _postdef,
		// _state,
		// _actualResult,
		// _isConstructor,
		// cloneList(_parameterPatterns, oldToNewMap),
		// _paramDefinitions
		// );

		// return new AExplicitOperationDefinition(
		// def.getLocation(),
		// def.getName().clone(),
		// def.getNameScope(),
		// def.getUsed(),
		// def.getClassDefinition() != null ? def.getClassDefinition().clone() : null,
		// def.getAccess().clone(),
		// def.getPass(),
		// (List<? extends PPattern>) def.getParameterPatterns().clone(),
		// new ASkipStm(),
		// def.getPrecondition() != null ? def.getPrecondition().clone(): null,
		// def.getPostcondition() != null ? def.getPostcondition().clone() : null,
		// def.getType().clone(),
		// null,
		// null,
		// (List<? extends PDefinition>) def.getParamDefinitions().clone(),
		// null,
		// def.getActualResult(),
		// def.getIsConstructor());
	}

	@SuppressWarnings("unchecked")
	private static AImplicitOperationDefinition convertToVDMDefinition(
			AImplicitCmlOperationDefinition d)
	{
		AImplicitOperationDefinition o = new AImplicitOperationDefinition();
		o.setLocation(d.getLocation());

		o.setName(d.getName().clone());
		o.setNameScope(d.getNameScope());
		o.setUsed(d.getUsed());
		o.setClassDefinition(d.getClassDefinition() != null ? d.getClassDefinition().clone()
				: null);
		o.setAccess(d.getAccess().clone());
		o.setPass(d.getPass());
		o.setParameterPatterns((List<? extends APatternListTypePair>) d.getParameterPatterns().clone());
		o.setResult(d.getResult());
		o.setBody(null); // body
		o.setExternals(d.getExternals());
		o.setPrecondition(d.getPrecondition() != null ? d.getPrecondition().clone()
				: null);
		o.setPostcondition(d.getPostcondition() != null ? d.getPostcondition().clone()
				: null);
		o.setErrors(d.getErrors());
		o.setType(d.getType());
		o.setPredef(d.getPredef());
		o.setPostdef(d.getPostdef());
		o.setState(d.getState());
		o.setActualResult(d.getActualResult());
		o.setStateDefinition(d.getStateDefinition());
		o.setIsConstructor(d.getIsConstructor());
		return o;
	}

	// public CmlOperationValue(AImplicitCmlOperationDefinition def,
	// AStateDefinition state)
	// {
	// this.impldef = def;
	// this.expldef = null;
	// this.name = def.getName();
	// this.type = (AOperationType)def.getType();
	// this.paramPatterns = new Vector<PPattern>();
	//
	// for (APatternListTypePair ptp : def.getParameterPatterns())
	// {
	// getParamPatterns().addAll(ptp.getPatterns());
	// }
	//
	// this.precondition = impldef.getPrecondition();
	// this.postcondition = impldef.getPostcondition();
	// this.state = state;
	// }

	public PAction getBody()
	{
		return body;
	}

	private void setBody(PAction body)
	{
		this.body = body;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other instanceof Value)
		{
			Value val = ((Value) other).deref();

			if (val instanceof CmlOperationValue)
			{
				CmlOperationValue ov = (CmlOperationValue) val;
				return ov.type.equals(type);
			}
		}

		return false;
	}

	@Override
	public String kind()
	{
		return "CML operation";
	}

	@Override
	public Object clone()
	{
		if (expldef != null)
		{
			return new CmlOperationValue(expldef, state);
		} else
		{
			return null;
			// return new CmlOperationValue(impldef, state);
		}
	}

	public List<PPattern> getParamPatterns()
	{
		return paramPatterns;
	}

	public AOperationType getType()
	{
		return type;
	}

	public void setCurrentlyExecutingThread(
			CmlBehaviour currentlyExecutingThread)
	{
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
		throw new ValueException(0, "An operation call occur in an expression!, is this allowed?", ctxt);
	}
	// public RootContext newContext(LexLocation from, String title, Context ctxt)
	// {
	// RootContext argContext;
	//
	// if (self != null)
	// {
	// argContext = new ObjectContext(from, title, ctxt, self);
	// }
	// else if (classdef != null)
	// {
	// argContext = new ProcessContext(from, title, ctxt, classdef);
	// }
	// else
	// {
	// argContext = new StateContext(from, title, ctxt, stateContext);
	// }
	//
	// return argContext;
	// }

}
