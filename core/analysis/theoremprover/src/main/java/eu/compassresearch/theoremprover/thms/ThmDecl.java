package eu.compassresearch.theoremprover.thms;

public abstract class ThmDecl implements ThmArtifact{

	private static final String decl = "declare ";
	private static final String theoremAttr = " [eval,evalp]";

	protected String tacHook(String name)
	{
		return decl + name + "_def" + theoremAttr;
	}
}
