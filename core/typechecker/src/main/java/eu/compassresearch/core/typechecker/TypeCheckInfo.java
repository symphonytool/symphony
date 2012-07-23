package eu.compassresearch.core.typechecker;

import java.util.List;

import eu.compassresearch.ast.types.PType;

public class TypeCheckInfo {

	public final Environment env;
	public List<PType> qualifiers;

	public TypeCheckInfo(Environment env, List<PType> qualifiers)
	{
		this.env = env;
		this.qualifiers = qualifiers;
	}
	
	public TypeCheckInfo()
	{
		env = new Environment();
	}
}
