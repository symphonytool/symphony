package eu.compassresearch.core.interpreter.api;

import java.util.Arrays;
import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlProcess;

public class InterpreterStatus {

	final private CmlProcessSnapshot[] processInfos;
	final private int topLevelProcessIndex;
	
	public InterpreterStatus(List<CmlProcess> processes)
	{
		this.processInfos = new CmlProcessSnapshot[processes.size()];
		int toplevelIndex = -1;
		for(int i = 0; i < processes.size() ; i++ )
		{
			this.processInfos[i] = new CmlProcessSnapshot(processes.get(i).name().getName(),
					processes.get(i).getTraceModel(),
					processes.get(i).level());
			
			if(this.processInfos[i].level() == 0)
				toplevelIndex = i;
		}
		
		topLevelProcessIndex = toplevelIndex;
	}
			
	public List<CmlProcessSnapshot> getAllProcessInfos()
	{
		return Arrays.asList(this.processInfos);
	}
	
	public CmlProcessSnapshot getToplevelProcessInfo()
	{
		return processInfos[topLevelProcessIndex];
	}
			
}
