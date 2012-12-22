package eu.compassresearch.core.typechecker;

import java.util.List;

import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.typechecker.NameScope;
import org.overture.typechecker.Environment;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;

import eu.compassresearch.ast.definitions.AClassParagraphDefinition;

public class CmlTCUtil {


	/**
	 * 
	 * Type Checking relies on every PDefinition has a non-null name. This method allows us
	 * to generate a LexNameToken with an empty string and only a location quickly for setting the
	 * name of definitions that lack a name.
	 * 
	 * @param loc
	 * @return
	 */
	public static LexNameToken newEmptyStringLexName(LexLocation loc)
	{
		return new LexNameToken("", new LexIdentifierToken("",false,loc));
	}

	/**
	 * Given an overture env find the above cml env
	 * @param question
	 * @return
	 */
	public static CmlTypeCheckInfo getCmlEnv(org.overture.typechecker.TypeCheckInfo question)
	{
		if (question instanceof CmlTypeCheckInfo)
			return (CmlTypeCheckInfo)question;
		return question.contextGet(CmlTypeCheckInfo.class);
	}


	/**
	 * Search from the given environment and outwards towards the top-level
	 * environment. The first definition in the nearest environment is returned.
	 * 
	 * 
	 * @param name
	 * @param overtureEnv
	 * @return
	 */
	public static PDefinition findNearestFunctionOrOperationInEnvironment(LexNameToken name, Environment overtureEnv)
	{
		PDefinition result = null;
		org.overture.typechecker.Environment cur = overtureEnv;
		while(cur != null && result == null)
		{
			List<PDefinition> defs = cur.getDefinitions();
			if (defs != null)
			{
				for(PDefinition def : defs)
				{
					LexNameToken defName = def.getName();
					if (defName != null && defName.getName() != null && defName.getName().startsWith(name.getName()))
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
	 * Look everywhere for the given name in order:
	 * 1) Locally in the present questions's definition list (env.definitions)
	 * 2) The anywhere in the enclosingDefinition 
	 * 3) If still not found expand to global scope if we have an Cml environemt
	 * 
	 * 
	 * @param question
	 * @return
	 */
	public static PDefinition findDefByAllMeans(org.overture.typechecker.TypeCheckInfo question, LexIdentifierToken id)
	{
		PDefinition res = null;
		LexNameToken sought = null;
		if (id instanceof LexNameToken)
			sought = (LexNameToken)id;
		else
			sought = new LexNameToken("",id);

		// search locally names
		res = question.env.findName(sought, NameScope.NAMESANDANYSTATE);
		if (res != null) return res;

		// search locally types
		res = question.env.findType(sought,"");
		if (res != null) return res;

		// search enclosing definition
		PDefinition enclosingDef = question.env.getEnclosingDefinition();
		for(NameScope scope : NameScope.values())
		{
			try {
				res = PDefinitionAssistantTC.findName(enclosingDef, sought, scope);
				if (res != null) return res;
			} catch (Exception e)
			{
				// silently ignore.
			}
		}

		// search globally
		CmlTypeCheckInfo cmlEnv = getCmlEnv(question);
		if (cmlEnv == null) return null; // no global scope :(

		PDefinition globalDef = cmlEnv.getGlobalClassDefinitions();
		for(NameScope scope : NameScope.values())
		{
			try {
				res = PDefinitionAssistantTC.findName(globalDef, sought, scope);
				if (res != null) return res;
			} catch (Exception e)
			{
				// silently ignore.
			}
		}


		return null;
	}


	/**
	 * Run through the definitions constituting the given class node and flatten paragraph sections
	 * and add the definitions to the environment.
	 * 
	 * @param info
	 * @param node
	 * @return
	 */
	static CmlTypeCheckInfo createCmlClassEnvironment(CmlTypeCheckInfo info,
			AClassParagraphDefinition node) {

		CmlTypeCheckInfo cmlClassEnv = info.newScope();
		info.addType(node.getName(), node);

		for(PDefinition def : node.getDefinitions())
		{
			List<PDefinition> l = TCDeclAndDefVisitor.handleDefinitionsForOverture(def);
			if (l != null)
				for(PDefinition dd : l)
				{
					if (dd instanceof ATypeDefinition)
						info.addType(dd.getName(), dd);
					else
						info.addVariable(dd.getName(),dd);
				}		
		}

		return cmlClassEnv;

	}

}
