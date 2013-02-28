package eu.compassresearch.core.interpreter.api;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;

public class InterpreterStatus {

	final private CmlProcessInfo[] processInfos;
	final private int topLevelProcessIndex;
	private InterpreterError[] errors = null;
	
	public InterpreterStatus(List<CmlBehaviourThread> processes)
	{
		this.processInfos = new CmlProcessInfo[processes.size()];
		int toplevelIndex = -1;
		for(int i = 0; i < processes.size() ; i++ )
		{
			this.processInfos[i] = new CmlProcessInfo(processes.get(i).name().getName(),
					processes.get(i).getTraceModel(),
					processes.get(i).level(),
					processes.get(i) instanceof CmlBehaviourThread,
					processes.get(i).getState());
			
			if(this.processInfos[i].level() == 0)
				toplevelIndex = i;
		}
		
		topLevelProcessIndex = toplevelIndex;
	}
			
	public List<CmlProcessInfo> getAllProcessInfos()
	{
		return Arrays.asList(this.processInfos);
	}
	
	public CmlProcessInfo getToplevelProcessInfo()
	{
		return processInfos[topLevelProcessIndex];
	}
	
	public List<InterpreterError> getErrors() {
		return Arrays.asList(errors);
	}

	public void AddError(InterpreterError error) {
		
		if(errors == null)
			errors = new InterpreterError[]{error};
		else
		{
			errors = Arrays.copyOf(errors, errors.length + 1);
		}
	}

	public boolean hasErrors()
	{
		return errors != null;
	}
			
}
