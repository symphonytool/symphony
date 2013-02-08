package eu.compassresearch.core.interpreter.values;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.definitions.AActionDefinition;

public class ActionValue extends Value {

	private final AActionDefinition actionDefinition;
	
	public ActionValue(AActionDefinition actionDefinition)
	{
		this.actionDefinition = actionDefinition;
	}
	
	public AActionDefinition getActionDefinition()
	{
		return actionDefinition;
	}
	
	@Override
	public String toString() {

		return "Action Value : " + actionDefinition.getName();
	}

	@Override
	public boolean equals(Object other) {

		if(!(other instanceof ActionValue))
			return false;
		
		return actionDefinition.equals(((ActionValue)other).actionDefinition);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String kind() {
		return "Action Value";
	}

	@Override
	public Object clone() {
		return new ActionValue(actionDefinition);
	}

}
