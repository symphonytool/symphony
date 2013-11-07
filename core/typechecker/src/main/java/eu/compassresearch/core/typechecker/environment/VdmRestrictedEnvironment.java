package eu.compassresearch.core.typechecker.environment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AEqualsDefinition;
import org.overture.ast.definitions.AExternalDefinition;
import org.overture.ast.definitions.AImportedDefinition;
import org.overture.ast.definitions.AInheritedDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.AMultiBindListDefinition;
import org.overture.ast.definitions.AMutexSyncDefinition;
import org.overture.ast.definitions.ANamedTraceDefinition;
import org.overture.ast.definitions.APerSyncDefinition;
import org.overture.ast.definitions.ARenamedDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.AThreadDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AUntypedDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.definitions.SFunctionDefinition;
import org.overture.ast.definitions.SOperationDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.typechecker.NameScope;
import org.overture.typechecker.Environment;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

public class VdmRestrictedEnvironment extends Environment
{
	private final static Class<?> VDM_DEFINITIONS[] = { ATypeDefinition.class,
			AUntypedDefinition.class, AValueDefinition.class,
			AAssignmentDefinition.class, AInstanceVariableDefinition.class,
			SClassDefinition.class, AClassInvariantDefinition.class,
			AEqualsDefinition.class, SFunctionDefinition.class,SOperationDefinition.class,
			AExternalDefinition.class, AImportedDefinition.class,
			AInheritedDefinition.class, ALocalDefinition.class,
			AMultiBindListDefinition.class, AMutexSyncDefinition.class,
			ANamedTraceDefinition.class, APerSyncDefinition.class,
			ARenamedDefinition.class, AStateDefinition.class,
			AThreadDefinition.class };

	public VdmRestrictedEnvironment(ITypeCheckerAssistantFactory af, Environment outer)
	{
		super(af,  outer);
	}

	public static boolean isVdm(PDefinition def)
	{
		for (Class<?> c : VDM_DEFINITIONS)
		{
			if (c.isAssignableFrom(def.getClass()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public PDefinition findType(ILexNameToken name, String fromModule)
	{
		PDefinition def = outer.findType(name, fromModule);

		if (def != null && !isVdm(def))
		{
			return null;
		}

		return def;
	}

	public PDefinition findName(ILexNameToken name, NameScope scope)
	{
		PDefinition def = outer.findName(name, scope);

		if (def != null && !isVdm(def))
		{
			return null;
		}

		return def;
	};

	public Set<PDefinition> findMatches(ILexNameToken name)
	{
		Set<PDefinition> defs = outer.findMatches(name);

		Set<PDefinition> inaccessiable = new HashSet<PDefinition>();

		for (PDefinition def : inaccessiable)
		{
			if (def == null || !isVdm(def))
			{
				inaccessiable.add(def);
			}
		}

		defs.removeAll(inaccessiable);

		return defs;
	};

	@Override
	public boolean isVDMPP()
	{
		return outer.isVDMPP();
	}

	@Override
	protected List<PDefinition> getDefinitions()
	{
		return new Vector<PDefinition>();
	}

	@Override
	public AStateDefinition findStateDefinition()
	{
		return outer.findStateDefinition();
	}

	@Override
	public SClassDefinition findClassDefinition()
	{
		return outer.findClassDefinition();
	}

	@Override
	public boolean isStatic()
	{
		return outer.isStatic();
	}

	@Override
	public void unusedCheck()
	{
		 outer.unusedCheck();
		
	}

	@Override
	public boolean isSystem()
	{
		return outer.isSystem();
	}
}
