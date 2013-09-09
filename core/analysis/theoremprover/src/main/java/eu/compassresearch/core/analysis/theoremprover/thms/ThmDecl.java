package eu.compassresearch.core.analysis.theoremprover.thms;

public abstract class ThmDecl implements ThmArtifact{

	private static final String decl = "declare ";
	private static final String theoremAttr = " [eval,evalp]";

	/***
	 * Method to return a declare statement for any definition
	 * @param name
	 * @return
	 */
	protected String tacHook(String name)
	{
		return decl + name + "_def" + theoremAttr;
	}
}
