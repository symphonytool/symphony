package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;

@SuppressWarnings("serial")
public class EventCollector extends DepthFirstAnalysisCMLAdaptor {

	private Set<String> channelSet = new HashSet<String>();
	
	public void caseACommunicationAction(ACommunicationAction node) throws AnalysisException {
		
		channelSet.add(node.getIdentifier().getName());
		node.getAction().apply(this);
	}
	
	public Set<String> getChannelSet()
	{
		return channelSet;
		
	}
}

