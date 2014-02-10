package eu.compassresearch.core.analysis.theoremprover.thms;

public abstract class ThmDecl implements ThmArtifact{

	private static final String decl = "declare ";
	private static final String theoremAttr = " [eval,evalp]";
	protected static final String operation = " [cmlop_defs]";
	protected static final String action = " [cmlact_defs]";
	protected static final String state = " [cmlproc_defs]";

	/***
	 * Method to return a declare statement for any definition
	 * @param name
	 * @return
	 */
	protected String tacHook(String name)
	{
		return decl + name + "_def" + theoremAttr;
	}
	

	/***
	 * Method to return a declare statement for any definition
	 * @param name
	 * @return
	 */
	protected String tactic(String name, String tacType)
	{
		return decl + name + "_def" + tacType;
	}
	
}
