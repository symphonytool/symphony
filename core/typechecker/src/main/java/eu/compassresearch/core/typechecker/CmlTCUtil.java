package eu.compassresearch.core.typechecker;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.typechecker.NameScope;
import org.overture.typechecker.assistant.definition.PDefinitionAssistantTC;

public class CmlTCUtil {

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
	
}
