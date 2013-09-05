package eu.compassresearch.core.analysis.theoremprover.utils;

import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;

/***
 * Class to represent a ThmNode sorting problem - doesn't have any behaviour itself - simply
 * to identify exception beng thrown/caught for correct error handling.
 * @author nrjp6
 *
 */

@SuppressWarnings("serial")
public class ThySortException extends Exception {

	private ThmNodeList sorted;
	private ThmNodeList unsorted;
	
	public ThySortException(ThmNodeList sortedNodes, ThmNodeList unsortedNodes) {
		this.sorted = sortedNodes;
		this.unsorted = unsortedNodes;		
	}
	
	public String getSortErrorStatus()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("(****** SORTED NODES ******)\n\n");
		sb.append(sorted.toString());
		
		sb.append("(****** UNSORTED NODES ******)\n\n");
		for(ThmNode n : unsorted)
		{
			sb.append("(* Node ID: " + n.getId().getName() + " *)\n");
			sb.append("(* Node deps: " + n.getDepIds().toString() + " *)\n");
			//sb.append("(* Node string: *)");
			//sb.append(n.toString());
		}
		sb.append("(****** END OF SORT ERROR ******)\n\n");
		return sb.toString();
	}
	

}
