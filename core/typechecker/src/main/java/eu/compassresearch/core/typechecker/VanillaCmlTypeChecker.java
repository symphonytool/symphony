package eu.compassresearch.core.typechecker;

import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.Dialect;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.typechecker.NameScope;
import org.overture.config.Release;
import org.overture.config.Settings;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeCheckException;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeChecker;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.messages.InternalException;
import eu.compassresearch.core.typechecker.analysis.CollectGlobalStateClass;
import eu.compassresearch.core.typechecker.analysis.OperationBodyValidater;
import eu.compassresearch.core.typechecker.analysis.UniquenessChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.visitors.CmlClassTypeChecker;
import eu.compassresearch.core.typechecker.visitors.CmlCspTypeChecker;
import eu.compassresearch.core.typechecker.visitors.CmlVdmTypeCheckVisitor;
import eu.compassresearch.core.typechecker.weeding.Weeding1;
import eu.compassresearch.core.typechecker.weeding.Weeding2;
import eu.compassresearch.core.typechecker.weeding.Weeding5RemoveInitialDefinitions;
import eu.compassresearch.core.typechecker.weeding.WeedingAccessCorrector;
import eu.compassresearch.core.typechecker.weeding.WeedingCallToCallActionReplacer;
import eu.compassresearch.core.typechecker.weeding.WeedingSkipActionToStmCleaner;
import eu.compassresearch.core.typechecker.weeding.WeedingStmCleaner;
import eu.compassresearch.core.typechecker.weeding.WeedingUnresolvedPathReplacement;

public class VanillaCmlTypeChecker extends AbstractTypeChecker
{

	private static class VdmTypeCheckResult
	{
		public final Environment globalEnv;
		public final ITypeCheckerAssistantFactory af;

		public VdmTypeCheckResult(Environment globalEnv,

		ITypeCheckerAssistantFactory af)
		{
			this.globalEnv = globalEnv;
			this.af = af;
		}
	}

	/**
	 * Simple assigning constructor with a bit of logic in initialize.
	 * 
	 * @param cmlSource
	 * @param issueHandler
	 */
	public VanillaCmlTypeChecker(List<? extends PDefinition> cmlSource,
			ITypeIssueHandler issueHandler)
	{
		this.sourceForest = new DefinitionList();
		sourceForest.addAll(cmlSource);
		initialize(issueHandler);
	}

	private void initialize(ITypeIssueHandler issueHandler)
	{
		if (issueHandler != null)
		{
			this.issueHandler = issueHandler;
		} else
		{
			this.issueHandler = new CollectingIssueHandler();
		}

		Settings.release = Release.VDM_10;
		Settings.dialect = Dialect.VDM_PP;
		TypeChecker.clearErrors();

		TypeChecker.addStatusListner(this.issueHandler);
	}

	/**
	 * This method is invoked by the command line tool when pretty printing the analysis name.
	 * 
	 * @return Pretty short name for this analysis.
	 */
	@Override
	public String getAnalysisName()
	{
		return "The CML Type Checker";
	}

	@Override
	void clear()
	{
		sourceForest = null;
	}

	@Override
	public boolean typeCheck()
	{
		try
		{
			// Force setup of the legacy static access assistants in Overture
			CmlTypeCheckerAssistantFactory.init(new CmlTypeCheckerAssistantFactory());

			// Transform the AST before analysis
			// W: Stage 1 remove intermediate product types in functions
			Weeding1.apply(sourceForest);
			// W: Stage 2 remove all bracket types
			Weeding2.apply(sourceForest);
			// W: Stage 5 remove any initial definitions
			Weeding5RemoveInitialDefinitions.apply(sourceForest);

			WeedingCallToCallActionReplacer.apply(sourceForest);
			WeedingSkipActionToStmCleaner.apply(sourceForest);
			WeedingStmCleaner.apply(sourceForest);
			WeedingAccessCorrector.apply(sourceForest);

			// DotUtil.dot(sourceForest.iterator().next());

			// Collect all Top-level entities
			DefinitionList globalDefs = CollectGlobalStateClass.getGlobalRoot(sourceForest, issueHandler);
			UniquenessChecker.apply(globalDefs, issueHandler);

			VdmTypeCheckResult result = overtureClassTc(globalDefs);
			cmlCspTc(sourceForest, result);

		} catch (AnalysisException e)
		{
			// An expected anomaly was found
			e.printStackTrace();
			throw new InternalException(0, e.getMessage());
		} catch (AbortTypecheck e)
		{
			if (e.number != -1)
			{
				throw e;
			}

		} catch (Exception e)
		{
			if (e instanceof TypeCheckException)
			{
				throw e;// new InternalException(0,e.getMessage()+" "+ ((TypeCheckException)e).location);
			}
			e.printStackTrace();
			throw new InternalException(0, e.getMessage());
		}

		return !issueHandler.hasErrors();
	}

	private VdmTypeCheckResult overtureClassTc(DefinitionList globals)
	{
		final List<SClassDefinition> classes = globals.getAllClasses();// exstractClasses(globalDefs2);

		final List<PDefinition> globalVdmDefs = globals.getGlobalVdmDefinitions();// filterCSP(globalDefinitions);

		ILexLocation location = null;
		AClassClassDefinition globalClass = AstFactory.newAClassClassDefinition(new CmlLexNameToken("$global", new LexIdentifierToken("$global", false, location)), new LexNameList(), globalVdmDefs);

		// insert global class first, it must be checked first do to the environment linking
		classes.add(0, globalClass);

		// resolve AUnresolvedPathExp. Each resolved path will be replaced with a AVariableExp
		WeedingUnresolvedPathReplacement.apply(globals);

		// check that operation bodies only contain the allowed subset
		if (!OperationBodyValidater.apply(globals, issueHandler))
		{
			abort(-1, "Stopped due to type errors in operatuib bodies");
		}

		// DotUtil.dot(classes);

		CmlClassTypeChecker typeChecker = new CmlClassTypeChecker(classes, globalVdmDefs);// new
																							// ClassTypeChecker(classes,new
		typeChecker.typeCheck();
		return new VdmTypeCheckResult(typeChecker.getAllClassesEnvronment(), typeChecker.getAssistantFactory());
	}

	private void cmlCspTc(DefinitionList sourceForest,
			VdmTypeCheckResult vdmResult)
	{

		List<PDefinition> globalCmlDefinition = new Vector<PDefinition>();

		for (PDefinition def : sourceForest)
		{
			{
				if (def instanceof AChannelDefinition
						|| def instanceof AChansetDefinition
						|| def instanceof AProcessDefinition)
				{
					globalCmlDefinition.add(def);
				}
			}
		}

		Environment env = new FlatCheckedEnvironment(vdmResult.af, globalCmlDefinition, vdmResult.globalEnv, NameScope.NAMES);

		for (PDefinition def : globalCmlDefinition)
		{
			if (def instanceof AChannelDefinition)
			{
				try
				{
					vdmResult.af.createPTypeAssistant().typeResolve(def.getType(), null, new CmlVdmTypeCheckVisitor(), new TypeCheckInfo(vdmResult.af, env, NameScope.NAMES));
				} catch (TypeCheckException te)
				{
					TypeChecker.report(3427, te.getMessage(), te.location);
				}
			}
		}

		for (PDefinition source : sourceForest)
		{
			try
			{
				source.apply(new CmlCspTypeChecker(issueHandler), new TypeCheckInfo(vdmResult.af, env, NameScope.NAMES));
			} catch (AnalysisException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
