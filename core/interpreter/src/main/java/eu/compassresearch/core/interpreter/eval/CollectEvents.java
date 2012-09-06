package eu.compassresearch.core.interpreter.eval;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisAdaptor;

@SuppressWarnings("serial")
public class CollectEvents extends DepthFirstAnalysisAdaptor {

	public void caseACommunicationAction(ACommunicationAction node) throws AnalysisException {
		
		System.out.println(node.getIdentifier());
		
	}
}
