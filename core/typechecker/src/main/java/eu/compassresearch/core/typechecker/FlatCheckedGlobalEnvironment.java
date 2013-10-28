package eu.compassresearch.core.typechecker;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.typechecker.NameScope;
import org.overture.typechecker.EnvironmentSearchStrategy;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

public class FlatCheckedGlobalEnvironment extends FlatCheckedEnvironment
{

	public FlatCheckedGlobalEnvironment(ITypeCheckerAssistantFactory af,
			List<PDefinition> definitions, NameScope scope,
			EnvironmentSearchStrategy ess)
	{
		super(af, definitions, scope, ess);
	}

	@Override
	public PDefinition findType(ILexNameToken name, String fromModule)
	{
		PDefinition def = super.findType(name, fromModule);

		if (def == null)
		{
			def = super.findType(cloneToGlobal(name), fromModule);
		}

		return def;
	}

	public PDefinition findName(ILexNameToken name, NameScope scope)
	{
		PDefinition def = super.findName(name, scope);

		if (def == null)
		{
			def = super.findName(cloneToGlobal(name), scope);
		}

		return def;
	};

	public PDefinition find(ILexIdentifierToken name)
	{
		PDefinition def = super.find(name);

		if (def == null)
		{
			System.err.println("should something happen here");
			// def = super.find(cloneToGlobal(name));
		}

		return def;
	};

	public Set<PDefinition> findMatches(ILexNameToken name)
	{
		Set<PDefinition> defs = super.findMatches(name);

		if (defs == null || defs.isEmpty())
		{
			defs = super.findMatches(cloneToGlobal(name));
		}

		return defs;
	};

	/**
	 * Rewrites the name as if the module was empties in the original
	 * 
	 * @param name
	 * @return
	 */
	ILexNameToken cloneToGlobal(ILexNameToken name)
	{
		ILexNameToken globalName = name.clone();

		Field nameField;
		try
		{
			nameField = globalName.getClass().getDeclaredField("module");
			if (nameField != null)
			{
				nameField.setAccessible(true);
				nameField.set(globalName, ""/* "$global" */);
			}
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e)
		{

		}

		return globalName;
	}

}
