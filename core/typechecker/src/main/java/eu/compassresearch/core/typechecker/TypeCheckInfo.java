package eu.compassresearch.core.typechecker;

import java.util.List;

import org.overture.ast.types.PType;
import org.overturetool.vdmj.typechecker.NameScope;

public class TypeCheckInfo {

	public final Environment env;
	public NameScope currentScope;
	public List<PType> qualifiers;

	public TypeCheckInfo(Environment env, NameScope scope, List<PType> qualifiers)
	{
		this.env = env;
		this.currentScope = scope;
		this.qualifiers = qualifiers;
	}
	
	public TypeCheckInfo()
	{
		env = new Environment();
	}
}
