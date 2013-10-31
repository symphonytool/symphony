package eu.compassresearch.core.typechecker.environment;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.typechecker.Environment;
import org.overture.typechecker.PrivateClassEnvironment;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;

public class PrivateActionClassEnvironment extends PrivateClassEnvironment
{

	public PrivateActionClassEnvironment(ITypeCheckerAssistantFactory af,
			SClassDefinition classdef, Environment env)
	{
		super(af, classdef, env);
	}

	@Override
	public PDefinition find(ILexIdentifierToken name)
	{
		if (outer != null)
		{

			PDefinition def = outer.find(name);
			if (def instanceof AChannelDefinition
					|| def instanceof AChansetDefinition)
			{
				return def;
			}

		}
		return null;
	}
}
