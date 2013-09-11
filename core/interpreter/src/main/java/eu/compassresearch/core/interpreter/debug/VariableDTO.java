package eu.compassresearch.core.interpreter.debug;

import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.SeqValue;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;

public class VariableDTO
{

	private final String name;

	private final String fullName;

	private final String type;

	private final String value;

	private final boolean constant;

	private final int childrenCount;

	private final List<VariableDTO> availableChildren;

	private final boolean hasChildren;

	/**
	 * Dummy constructor for serialization
	 */
	@SuppressWarnings("unused")
	private VariableDTO()
	{
		this.name = null;
		this.fullName = null;
		this.type = null;
		this.value = null;
		this.childrenCount = -1;
		this.availableChildren = null;
		this.hasChildren = false;
		this.constant = false;
	}

	public VariableDTO(String name, String fullName, String type, String value,
			int childrenCount, boolean hasChildren, boolean constant,
			List<VariableDTO> availableChildren)
	{
		this.name = name;
		this.fullName = fullName;
		this.type = type;
		this.value = value;
		this.childrenCount = childrenCount;
		this.availableChildren = availableChildren;
		this.hasChildren = hasChildren;
		this.constant = constant;
	}

	public String getName()
	{
		return name;
	}

	public String getFullName()
	{
		return fullName;
	}

	public String getType()
	{
		return type;
	}

	public String getValue()
	{
		return value;
	}

	public boolean isConstant()
	{
		return constant;
	}

	public int getChildrenCount()
	{
		return childrenCount;
	}

	public List<VariableDTO> getAvailableChildren()
	{
		return availableChildren;
	}

	public boolean hasChildren()
	{
		return hasChildren;
	}
	
	
	public static List<VariableDTO> extractVariables(Context context)
	{
		List<VariableDTO> variables = new Vector<VariableDTO>();
		
		for (Entry<ILexNameToken, Value> var : context.getVisibleVariables().entrySet())
		{
			
			ILexNameToken name = var.getKey();
			Value val = var.getValue();
			
			if(val instanceof FunctionValue || val instanceof OperationValue){
				continue;
			}
			
			variables.add(extractVariable(name.getName(),name.getFullName(),val));
		}
		
		return variables;
	}
	
	public static VariableDTO extractVariable(String name, String fullName, Value val)
	{
		List<VariableDTO> children = new Vector<VariableDTO>();
		Value derefedVal = val.deref();
		if(derefedVal instanceof SetValue)
		{
			SetValue v = (SetValue) derefedVal;
			int i = 0;
			for (Value vv : v.values)
			{
				i++;
				children.add(extractVariable("["+i+"]", "["+i+"]", vv));
			}
		}else if(derefedVal instanceof SeqValue)
		{
			SeqValue v = (SeqValue) derefedVal;
			int i = 0;
			for (Value vv : v.values)
			{
				i++;
				children.add(extractVariable("["+i+"]", "["+i+"]", vv));
			}	
		}else
		{
			//TODO
		}
		return new VariableDTO(name, fullName, val.kind(), val.toString(), children.size(), !children.isEmpty(),!( val instanceof UpdatableValue), children);
	}
}
