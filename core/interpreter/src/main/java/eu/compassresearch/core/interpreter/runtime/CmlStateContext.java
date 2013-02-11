package eu.compassresearch.core.interpreter.runtime;

import org.overture.ast.lex.LexLocation;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.StateContext;
import org.overture.interpreter.values.CPUValue;

import eu.compassresearch.ast.definitions.AProcessDefinition;

public class CmlStateContext extends StateContext {

	private final AProcessDefinition processDefinition;
	
	/**
	 * Create a RootContext from the values passed.
	 *
	 * @param location The location of the context.
	 * @param title The name of the location.
	 * @param outer The context chain (not searched).
	 * @param sctxt Any state context.
	 */

	public CmlStateContext(LexLocation location, String title,
		Context freeVariables, Context outer, Context sctxt,AProcessDefinition processDefinition)
	{
		super(location,title,freeVariables,outer,sctxt);
		this.processDefinition = processDefinition;
		setThreadState(null, CPUValue.vCPU);
	}

	public CmlStateContext(LexLocation location, String title,
		Context outer, Context sctxt,AProcessDefinition processDefinition)
	{
		this(location, title,null, outer, sctxt,processDefinition);
	}

	/**
	 * Create a RootContext with no outer context or state.
	 * @param location The location of the context.
	 * @param title The name of the location.
	 */

	public CmlStateContext(LexLocation location, String title,AProcessDefinition processDefinition)
	{
		this(location, title,null,null,processDefinition);
	}

	public AProcessDefinition getProcessDefinition() {
		return processDefinition;
	}
	
}
