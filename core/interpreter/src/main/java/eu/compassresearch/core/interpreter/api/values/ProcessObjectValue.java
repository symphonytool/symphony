package eu.compassresearch.core.interpreter.api.values;

import java.util.LinkedList;

import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.types.AClassType;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.IRuntimeState;
import org.overture.interpreter.runtime.VdmRuntime;
import org.overture.interpreter.runtime.state.SClassDefinitionRuntime;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AActionProcess;

public class ProcessObjectValue extends ObjectValue
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3419179270463020830L;
	private AProcessDefinition processDefinition = null;
	private PExp invariantExpression = null;

	public ProcessObjectValue(IInterpreterAssistantFactory af,
			AProcessDefinition processDefinition, NameValuePairMap members,
			ObjectValue creator)
	{
		super(CmlToVdmConverter.createClassType(processDefinition), members, new LinkedList<ObjectValue>(), CPUValue.vCPU, creator);
		this.processDefinition = processDefinition;
		configureRuntime(af);
	}

	public ProcessObjectValue(IInterpreterAssistantFactory af,
			AProcessDefinition processDefinition, NameValuePairMap members,
			ObjectValue creator, PExp invExp)
	{
		this(af, processDefinition, members, creator);
		this.setInvariantExpression(invExp);
		configureRuntime(af);
	}

	public ProcessObjectValue(IInterpreterAssistantFactory af,
			AProcessDefinition processDefinition,
			SClassDefinition classDefinition, NameValuePairMap members,
			ObjectValue creator, PExp invExp)
	{
		super((AClassType) classDefinition.getType(), members, new LinkedList<ObjectValue>(), CPUValue.vCPU, creator);
		this.setInvariantExpression(invExp);
		this.processDefinition = processDefinition;
		configureRuntime(af);
	}

	public ProcessObjectValue(IInterpreterAssistantFactory af,
			AProcessDefinition processDefinition, ObjectValue creator)
	{
		super(CmlToVdmConverter.createClassType(processDefinition), new NameValuePairMap(), new LinkedList<ObjectValue>(), CPUValue.vCPU, creator);
		this.processDefinition = processDefinition;
		configureRuntime(af);
	}

	private void configureRuntime(IInterpreterAssistantFactory af)
	{
		String name = processDefinition.getName().getName();
		SClassDefinition def = null;

		if (processDefinition.getProcess() instanceof AActionProcess)
		{
			AActionProcess actionProcess = (AActionProcess) processDefinition.getProcess();
			def = actionProcess.getActionDefinition();
			SClassDefinitionRuntime state = new ProcessDefinitionRuntime(af, def, name);
			VdmRuntime.setNodeState(processDefinition, state);
		}

	}

	public AProcessDefinition getProcessDefinition()
	{
		return processDefinition;
	}

	public PExp getInvariantExpression()
	{
		return invariantExpression;
	}

	public boolean hasInvariant()
	{
		return invariantExpression != null;
	}

	private void setInvariantExpression(PExp invariantExpression)
	{
		this.invariantExpression = invariantExpression;
	}

	//@Override
	public boolean hasDelegate()
	{

		IRuntimeState state = VdmRuntime.getNodeState(processDefinition);
		return state != null && ((SClassDefinitionRuntime) state).hasDelegate();
	}

	@Override
	public Value invokeDelegate(Context ctxt)
	{
		return ((SClassDefinitionRuntime) VdmRuntime.getNodeState(processDefinition)).invokeDelegate(ctxt);
	}

}
