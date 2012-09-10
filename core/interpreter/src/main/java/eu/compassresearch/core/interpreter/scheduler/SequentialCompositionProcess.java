package eu.compassresearch.core.interpreter.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import eu.compassresearch.ast.actions.ACommunicationAction;

public class SequentialCompositionProcess implements CMLProcess {

	private Stack<CMLProcess> processStack = new Stack<CMLProcess>();
	
	public SequentialCompositionProcess(CMLProcess leftProcess, CMLProcess rightProcess)
	{
		//push in reverse order
		processStack.push(rightProcess);
		processStack.push(leftProcess);
	}

	@Override
	synchronized public List<ACommunicationAction> WaitForEventOffer() {

		List<ACommunicationAction> events = new LinkedList<ACommunicationAction>();

		CMLProcess nextProcess = processStack.pop();

		events = nextProcess.WaitForEventOffer();
		
		
		if(!events.isEmpty())
		{
			processStack.push(nextProcess);
		}
		else if(events.isEmpty() && !processStack.isEmpty())
		{
			processStack.peek().start();
			events = processStack.peek().WaitForEventOffer();
		}
		
		//Check if the current process has evolved into Skip
//		if(!nextProcess.isSkip())
//		{
//			events = nextProcess.WaitForEventOffer();
//			processStack.push(nextProcess);
//		}
//		else if(!processStack.isEmpty())
//		{
//			processStack.peek().start();
//			events = processStack.peek().WaitForEventOffer();
//		}

		return events;
	}

	@Override
	synchronized public void eventOccured(ACommunicationAction event) {
		
		processStack.peek().eventOccured(event);

	}

	@Override
	public Set<String> getChannelSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		
		processStack.peek().start();

	}

	@Override
	synchronized public boolean isSkip() {
						
		return processStack.size() == 1 && processStack.peek().isSkip();
	}

	@Override
	public String getRemainingInterpretationState(boolean expand) {
		
		StringBuilder strBuilder = new StringBuilder();
				
		for(int i = processStack.size()-1 ; i >= 0 ; i--)
		{
			CMLProcess p = processStack.get(i);
		
			if(i == processStack.size()-1 && processStack.size() > 1)
				strBuilder.append(p.getRemainingInterpretationState(expand) + ";");
			else if(i == processStack.size()-1 && processStack.size() == 1)
				strBuilder.append(p.getRemainingInterpretationState(expand));
			else
				strBuilder.append(p.getRemainingInterpretationState(false));
		}
						
		return strBuilder.toString();
	}

}
