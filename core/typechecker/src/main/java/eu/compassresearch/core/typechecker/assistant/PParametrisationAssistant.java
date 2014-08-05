package eu.compassresearch.core.typechecker.assistant;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.PType;
import org.overture.parser.messages.VDMError;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeChecker;

import eu.compassresearch.ast.actions.AResParametrisation;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;

public class PParametrisationAssistant
{

	/**
	 * Creates a new scoped environment with the parameterizations added
	 * 
	 * @param typeChecker
	 * @param base
	 *            the base environment
	 * @param pDecls
	 *            the parameterizations
	 * @return the new environment
	 * @throws AnalysisException
	 */
	public static Environment updateEnvironment(
			IQuestionAnswer<TypeCheckInfo, PType> typeChecker,
			Environment base, Collection<PParametrisation> pDecls)
			throws AnalysisException
	{
		return updateEnvironment(typeChecker, base, pDecls.toArray(new PParametrisation[] {}));
	}

	/**
	 * Creates a new scoped environment with the parameterizations added
	 * 
	 * @param typeChecker
	 *            the type checker
	 * @param base
	 *            the base environment
	 * @param pDecls
	 *            the parameterizations
	 * @return the new environment
	 * @throws AnalysisException
	 */
	public static Environment updateEnvironment(
			IQuestionAnswer<TypeCheckInfo, PType> typeChecker,
			Environment base, PParametrisation... pDecls)
			throws AnalysisException
	{
		List<PDefinition> definitions = new Vector<PDefinition>();

		for (PParametrisation tDecl : pDecls)
		{
			ALocalDefinition localDecl = tDecl.getDeclaration();
			ILexNameToken name = new CmlLexNameToken("", localDecl.getName().clone());
			PExp exp = AstFactory.newAUndefinedExp(name.getLocation());
			AAssignmentDefinition def = AstFactory.newAAssignmentDefinition(name, localDecl.getType(), exp);
			//
			PDefinition ivd = null;
			boolean isValue = true;
			if (tDecl instanceof AResParametrisation
					|| tDecl instanceof AVresParametrisation)
			{
				ivd = AstFactory.newAInstanceVariableDefinition(def.getName(), def.getType(), def.getExpression());
				isValue = false;

				if (tDecl.parent() instanceof AProcessDefinition)
				{
					String type = tDecl instanceof AResParametrisation ? "res"
							: "vres";

					boolean alreadyReported = false;
					for (VDMError err : TypeChecker.getErrors())
					{
						if (err.number == TypeErrorMessages.RES_VRES_NOT_ALLOWED_IN_PROCESS_DEFINITION.number
								&& err.location == tDecl.getLocation())
						{
							alreadyReported = true;
							break;
						}
					}
					if (!alreadyReported)
					{
						TypeChecker.report(TypeErrorMessages.RES_VRES_NOT_ALLOWED_IN_PROCESS_DEFINITION.number, TypeErrorMessages.RES_VRES_NOT_ALLOWED_IN_PROCESS_DEFINITION.customizeMessage(type), tDecl.getLocation());
					}
				}
			} else
			{

				PPattern p = AstFactory.newAIdentifierPattern(def.getName());
				ivd = AstFactory.newAValueDefinition(p, NameScope.GLOBAL, def.getType(), def.getExpression());
			}

			ivd.setAccess(AstFactory.newAAccessSpecifierAccessSpecifier(new APrivateAccess(), isValue, false));

			if (ivd instanceof AValueDefinition)
			{
				for (PDefinition pDefinition : ((AValueDefinition) ivd).getDefs())
				{
					pDefinition.setAccess(ivd.getAccess().clone());
				}
			}

			ivd.parent(tDecl.parent());
			def.getType().parent(ivd);
			definitions.add(ivd);
			ivd.apply(typeChecker, new TypeCheckInfo(base.af, base));
		}
		return new FlatCheckedEnvironment(base.af, definitions, base, NameScope.NAMES);
	}

	/**
	 * Creates a new scoped environment with the parameterizations added from the process that is an ancester to the
	 * class
	 * 
	 * @param typeChecker
	 *            the type checker
	 * @param base
	 *            the base env
	 * @param c
	 *            the class
	 * @return the newly scoped env with the parameterizations added
	 * @throws AnalysisException
	 */
	public static Environment updateEnvironment(
			IQuestionAnswer<TypeCheckInfo, PType> typeChecker,
			Environment base, SClassDefinition c) throws AnalysisException
	{
		Environment env = base;
		if (c instanceof AActionClassDefinition)
		{
			AProcessDefinition process = c.getAncestor(AProcessDefinition.class);
			env = updateEnvironment(typeChecker, base, process);
		}
		return env;
	}

	/**
	 * Creates a new scoped environment with the parameterizations added from the process
	 * 
	 * @param typeChecker
	 *            the type checker
	 * @param base
	 *            the base env
	 * @param process
	 * @return the newly scoped env with the parameterizations added
	 * @throws AnalysisException
	 */
	public static Environment updateEnvironment(
			IQuestionAnswer<TypeCheckInfo, PType> typeChecker,
			Environment base, AProcessDefinition process)
			throws AnalysisException
	{

		Environment env = base;

		if (process != null && !process.getLocalState().isEmpty())
		{
			env = updateEnvironment(typeChecker, base, process.getLocalState());
		}
		return env;
	}
}
