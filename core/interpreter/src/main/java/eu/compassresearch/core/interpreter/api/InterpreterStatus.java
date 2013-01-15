package eu.compassresearch.core.interpreter.api;

import java.util.Arrays;
import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlProcess;

public class InterpreterStatus {

	final private CmlProcessInfo[] processInfos;
	final private int topLevelProcessIndex;
	
	public InterpreterStatus(List<CmlBehaviourThread> processes)
	{
		this.processInfos = new CmlProcessInfo[processes.size()];
		int toplevelIndex = -1;
		for(int i = 0; i < processes.size() ; i++ )
		{
			this.processInfos[i] = new CmlProcessInfo(processes.get(i).name().getName(),
					processes.get(i).getTraceModel(),
					processes.get(i).level(),
					processes.get(i) instanceof CmlProcess,
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
			
}
