package eu.compassresearch.core.typechecker.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.overture.ast.assistant.InvocationAssistantException;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;
import org.overture.typechecker.assistant.pattern.PPatternAssistantTC;

import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.core.typechecker.CmlTypeCheckInfo;
import eu.compassresearch.core.typechecker.TCDeclAndDefVisitor;

public class CmlTCUtil
{

	/**
	 * Determine whether a type is successful or not.
	 * 
	 * @param type
	 * @return
	 */
	public static boolean successfulType(PType type)
	{
		return !(type == null || type instanceof AErrorType);
	}

	public static String getErrorMessages(Exception e)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(b);
		e.printStackTrace(pw);
		pw.flush();
		try
		{
			b.flush();
		} catch (IOException e1)
		{
		}
		return new String(b.toByteArray());
	}

	/**
	 * Type Checking relies on every PDefinition has a non-null name. This method allows us to generate a CmlLexNameToken
	 * with an empty string and only a location quickly for setting the name of definitions that lack a name.
	 * 
	 * @param loc
	 * @return
	 */
	public static CmlLexNameToken newEmptyStringLexName(LexLocation loc)
	{
		return new CmlLexNameToken("", new LexIdentifierToken("", false, loc));
	}

	/**
	 * Given an overture env find the above cml env
	 * 
	 * @param question
	 * @return
	 */
	public static CmlTypeCheckInfo getCmlEnv(
			org.overture.typechecker.TypeCheckInfo question)
	{
		if (question instanceof CmlTypeCheckInfo)
			return (CmlTypeCheckInfo) question;
		return question.contextGet(CmlTypeCheckInfo.class);
	}

	/**
	 * Search from the given environment and outwards towards the top-level environment. The first definition in the
	 * nearest environment is returned.
	 * 
	 * @param name
	 * @param overtureEnv
	 * @return
	 */
	public static PDefinition findNearestFunctionOrOperationInEnvironment(
			ILexNameToken name, Environment overtureEnv)
	{
		PDefinition result = null;
		org.overture.typechecker.Environment cur = overtureEnv;
		while (cur != null && result == null)
		{
			List<PDefinition> defs = cur.getDefinitions();
			if (defs != null)
			{
				for (PDefinition def : defs)
				{
					ILexNameToken defName = def.getName();
					if (defName != null && defName.getFullName() != null
							&& defName.getFullName().equals(name.getFullName()))
					{
						if (PDefinitionAssistantTC.isFunctionOrOperation(def))
						{
							return result = def;
						}

					}
				}
			}
			cur = cur.getOuter();
		}
		return result;
	}

	/**
	 * Look everywhere for the given name in order: 1) Locally in the present questions's definition list
	 * (env.definitions) 2) The anywhere in the enclosingDefinition 3) If still not found expand to global scope if we
	 * have an Cml environemt
	 * 
	 * @param question
	 * @return
	 */
	public static PDefinition findDefByAllMeans(
			org.overture.typechecker.TypeCheckInfo question,
			ILexIdentifierToken id)
	{
		PDefinition res = null;
		CmlLexNameToken sought = null;
		if (id instanceof CmlLexNameToken)
			sought = (CmlLexNameToken) id;
		else
			sought = new CmlLexNameToken("", id);

		// search locally names
		res = question.env.findName(sought, NameScope.NAMESANDANYSTATE);
		if (res != null)
			return res;

		// search locally types
		res = question.env.findType(sought, "");
		if (res != null)
			return res;

		// search enclosing definition
		PDefinition enclosingDef = question.env.getEnclosingDefinition();
		if (enclosingDef != null)
		{
			for (NameScope scope : NameScope.values())
			{
				try
				{
					res = PDefinitionAssistantTC.findName(enclosingDef, sought, scope);
					if (res != null)
						return res;
				} catch (Exception e)
				{
					// silently ignore.
				}
			}
		}

		// search globally
		CmlTypeCheckInfo cmlEnv = getCmlEnv(question);
		if (cmlEnv == null)
			return null; // no global scope :(

		return cmlEnv.lookup(sought, PDefinition.class);
	}

	/**
	 * Run through the definitions constituting the given class node and flatten paragraph sections and add the
	 * definitions to the environment.
	 * 
	 * @param info
	 * @param node
	 * @return
	 */
	public static CmlTypeCheckInfo createCmlClassEnvironment(
			CmlTypeCheckInfo info, AClassClassDefinition node)
			throws InvocationAssistantException
	{

		CmlTypeCheckInfo cmlClassEnv = info.newScope();

		for (PDefinition def : node.getDefinitions())
		{
			List<PDefinition> l = TCDeclAndDefVisitor.handleDefinitionsForOverture(def);
			if (l != null)
				for (PDefinition dd : l)
				{
					if (dd instanceof ATypeDefinition)
						cmlClassEnv.addType(dd.getName(), dd);
					// else if (dd instanceof AExplicitCmlOperationDefinition)
					// continue;
					// else if (dd instanceof AAssignmentDefinition)
					// continue;
					else
					{

						if (dd instanceof AExplicitOperationDefinition
								&& dd.getName().getSimpleName().equals(node.getName().getSimpleName()))
						{

							((AExplicitOperationDefinition) dd).setIsConstructor(true);
						}

						ILexNameToken name = dd.getName();
						if ("".equals(name + ""))
						{
							if (dd instanceof AValueDefinition)
							{
								AValueDefinition ddVd = (AValueDefinition) dd;
								PPattern p = ddVd.getPattern();
								List<ILexNameToken> names = PPatternAssistantTC.getAllVariableNames(p);
								for (ILexNameToken n : names)
									cmlClassEnv.addVariable(n, dd);
							}
						} else
							cmlClassEnv.addVariable(name, dd);
					}
				}
		}

		return cmlClassEnv;

	}

	/**
	 * An initial strategy for determining access. There is code for this in Overture we should use instead.
	 * 
	 * @param definition
	 * @param scope
	 * @return
	 */
	public static boolean checkAccessInScope(PDefinition definition,
			NameScope scope)
	{

		if (scope == null)
			scope = NameScope.NAMESANDANYSTATE;

		// if(definition.getNameScope() == null)
		// return true;

		if (scope == NameScope.LOCAL)
			return definition.getNameScope() == scope;

		return true;
	}

}
