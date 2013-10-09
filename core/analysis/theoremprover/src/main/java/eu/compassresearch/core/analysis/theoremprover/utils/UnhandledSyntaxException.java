package eu.compassresearch.core.analysis.theoremprover.utils;

import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

public class UnhandledSyntaxException extends Exception {

	private String thyString;
	private NodeNameList errorNodes;
	
	public UnhandledSyntaxException(String thyString, NodeNameList errorNodes)
	{
		this.thyString = thyString;
		this.errorNodes = errorNodes;
	}
	
	public String getString()
	{
		return thyString;
	}
	
	public NodeNameList getErrorNodes(){
		return errorNodes;
	}
	
	public String getErrorString(){
		return "WARNING!\n\n"+
				"The source CML model contains syntax which isn't supported by Isabelle. "
				+ "A thy file for the model has been genenerated however it contain "
				+ "errors and we advise users to proceed with caution.\n\n"
				+ "The unsupported syntax is located in the following parts of the CML model:"
				+ "\n\n" 
				+ errorNodes.nameAndLocs() + "\n\n"
				+ "For a list of syntax which is supported and unsupported by the theorem "
				+ "prover plugin, see ??";
	}
}
