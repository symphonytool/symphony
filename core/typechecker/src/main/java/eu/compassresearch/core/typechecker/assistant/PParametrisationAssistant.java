package eu.compassresearch.core.typechecker.assistant;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.typechecker.NameScope;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;

import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;

public class PParametrisationAssistant
{

	/**
	 * Creates a new scoped environment with the parameterizations added
	 * 
	 * @param base
	 *            the base environment
	 * @param pDecls
	 *            the parameterizations
	 * @return the new environment
	 */
	public static Environment updateEnvironment(Environment base,
			Collection<PParametrisation> pDecls)
	{
		return updateEnvironment(base, pDecls.toArray(new PParametrisation[] {}));
	}

	/**
	 * Creates a new scoped environment with the parameterizations added
	 * 
	 * @param base
	 *            the base environment
	 * @param pDecls
	 *            the parameterizations
	 * @return the new environment
	 */
	public static Environment updateEnvironment(Environment base,
			PParametrisation... pDecls)
	{
		List<PDefinition> definitions = new Vector<PDefinition>();

		for (PParametrisation tDecl : pDecls)
		{
			ALocalDefinition localDecl = tDecl.getDeclaration();
			ILexNameToken name = new CmlLexNameToken("", localDecl.getName().clone());
			PExp exp = AstFactory.newAUndefinedExp(name.getLocation());
			AAssignmentDefinition def = AstFactory.newAAssignmentDefinition(name, localDecl.getType(), exp);
			AInstanceVariableDefinition ivd = AstFactory.newAInstanceVariableDefinition(def.getName(), def.getType(), def.getExpression());
			def.getType().parent(ivd);
			definitions.add(ivd);
		}
		return new FlatCheckedEnvironment(base.af, definitions, base, NameScope.NAMES);
	}

	/**
	 * Creates a new scoped environment with the parameterizations added from the process that is an ancester to the
	 * class
	 * 
	 * @param base
	 *            the base env
	 * @param c
	 *            the class
	 * @return the newly scoped env with the parameterizations added
	 */
	public static Environment updateEnvironment(Environment base,
			SClassDefinition c)
	{
		Environment env = base;
		if (c instanceof AActionClassDefinition)
		{
			AProcessDefinition process = c.getAncestor(AProcessDefinition.class);
			env = updateEnvironment(base, process);
		}
		return env;
	}

	/**
	 * Creates a new scoped environment with the parameterizations added from the process
	 * 
	 * @param base
	 *            the base env
	 * @param c
	 *            the process
	 * @return the newly scoped env with the parameterizations added
	 */
	public static Environment updateEnvironment(Environment base,
			AProcessDefinition process)
	{

		Environment env = base;

		if (process != null && !process.getLocalState().isEmpty())
		{
			env = updateEnvironment(base, process.getLocalState());
		}
		return env;
	}
}
