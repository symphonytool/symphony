package eu.compassresearch.core.typechecker.analysis;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.APublicAccess;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.typechecker.PublicClassEnvironment;
import org.overture.typechecker.assistant.definition.AExplicitFunctionDefinitionAssistantTC;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;
import org.overture.typechecker.utilities.ImplicitDefinitionFinder;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.ACmlClassDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.CmlTypeCheckerAssistantFactory;
import eu.compassresearch.core.typechecker.TCDeclAndDefVisitor;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

@SuppressWarnings("serial")
public class CollectGlobalStateClass extends AnalysisCMLAdaptor
{

	private final Collection<PDefinition> members;
	private final Collection<PDefinition> channels;
	private final CmlTypeCheckerAssistantFactory af = new CmlTypeCheckerAssistantFactory();
//	private ITypeIssueHandler issueHandler;

	public static class GlobalDefinitions
	{
		public final Collection<PDefinition> definitions;
		public final Collection<PDefinition> channels;

		private GlobalDefinitions(Collection<PDefinition> defs,
				Collection<PDefinition> chns)
		{
			this.definitions = defs;
			this.channels = chns;
		}
	}

	public static GlobalDefinitions getGlobalRoot(Collection<PSource> sources,
			ITypeIssueHandler issueHandler) throws AnalysisException
	{

		// Create visitor and visit each source collecting global definitions
		List<PDefinition> members = new LinkedList<PDefinition>();
		List<PDefinition> channels = new LinkedList<PDefinition>();
		CollectGlobalStateClass me = new CollectGlobalStateClass(members, channels, issueHandler);
		for (PSource source : sources)
		{
			source.apply(me);
		}

		// That's it
		return new GlobalDefinitions(members, channels);
	}

	@Override
	public void caseAChannelNameDefinition(AChannelNameDefinition node)
			throws AnalysisException
	{
		channels.add(node);
	}

	@Override
	public void caseAChannelsDefinition(AChannelsDefinition node)
			throws AnalysisException
	{

		LinkedList<AChannelNameDefinition> channels = node.getChannelNameDeclarations();
		for (AChannelNameDefinition channel : channels)
			channel.apply(this);
	}

	@Override
	public void defaultPSource(PSource node) throws AnalysisException
	{
		LinkedList<PDefinition> paragraphs = node.getParagraphs();
		for (PDefinition paragraph : paragraphs)
		{
			paragraph.apply(this);
		}
	}

	private CollectGlobalStateClass(List<PDefinition> members,
			Collection<PDefinition> channels, ITypeIssueHandler issueHandler)
	{
		this.members = members;
		this.channels = channels;
//		this.issueHandler = issueHandler;
	}

	@Override
	public void caseACmlClassDefinition(ACmlClassDefinition node)
			throws AnalysisException
	{
		// this will generate all the pre and post defs for functions
		SClassDefinitionAssistantTC.implicitDefinitions(node, new PublicClassEnvironment(af, null));// FIXME last
																									// argument is wrong
		/*
		 * Overture sets the invariant field on a CmlClassDefinition. This field is not used in CML because it uses the
		 * ExplicitCmlOperationDefinition instead of the ExplicitOperationDefinition in VDM. So we reset it to null
		 */
		node.setInvariant(null);
		members.add(node);
	}

	@Override
	public void caseATypesDefinition(ATypesDefinition node)
			throws AnalysisException
	{

		List<PDefinition> defs = TCDeclAndDefVisitor.handleDefinitionsForOverture(node);

		for (PDefinition tdef : defs)
		{
			tdef.apply(new ImplicitDefinitionFinder(af), null);

			if (tdef instanceof ATypeDefinition)
			{
				if (!(tdef.getAccess().getAccess() instanceof APublicAccess))
				{
					// TODO when getAccess gets a location then report it here. We also need it to have a token so we
					// can see the difference from non parse from default
					// issueHandler.addTypeWarning(tdef, "Access specifier ignored. Global types are default public.");
				}
				tdef.getAccess().setAccess(new APublicAccess());
			}
		}

		members.addAll(defs);
	}

	@Override
	public void caseAValuesDefinition(AValuesDefinition node)
			throws AnalysisException
	{
		List<PDefinition> defs = TCDeclAndDefVisitor.handleDefinitionsForOverture(node);
		members.addAll(defs);
	}

	@Override
	public void caseAFunctionsDefinition(AFunctionsDefinition node)
			throws AnalysisException
	{

		List<PDefinition> defs = TCDeclAndDefVisitor.handleDefinitionsForOverture(node);

		for (PDefinition fdef : defs)
		{

			PDefinition predef = null;
			// PDefinition postdef = null;
			if (fdef instanceof AExplicitFunctionDefinition)
			{
				// this will generate all the pre and post defs
				AExplicitFunctionDefinitionAssistantTC.implicitDefinitions((AExplicitFunctionDefinition) fdef, null);
				predef = ((AExplicitFunctionDefinition) fdef).getPredef();
				// postdef = ((AExplicitFunctionDefinition) fdef).getPostdef();
			}

			if (fdef instanceof AImplicitFunctionDefinition)
			{
				// this will generate all the pre and post defs
				// AImplicitFunctionDefinitionAssistantTC.implicitDefinitions((AImplicitFunctionDefinition) fdef, null);
				fdef.apply(af.getImplicitDefinitionFinder(), null);

				predef = ((AImplicitFunctionDefinition) fdef).getPredef();
				// postdef = ((AImplicitFunctionDefinition) fdef).getPostdef();
			}

			if (predef != null)
				members.addAll(TCDeclAndDefVisitor.handleDefinitionsForOverture(predef));
			// if (postdef != null)
			// members.addAll(TCDeclAndDefVisitor.handleDefinitionsForOverture(postdef));

		}

		members.addAll(defs);
	}

	@Override
	public void caseAProcessDefinition(AProcessDefinition node)
			throws AnalysisException
	{
		members.add(node);
	}

	@Override
	public void caseAChansetDefinition(AChansetDefinition node)
			throws AnalysisException
	{
		channels.add(node);
	}

	@Override
	public void caseAChansetsDefinition(AChansetsDefinition node)
			throws AnalysisException
	{
		for (PDefinition d : node.getChansets())
			d.apply(this);
	}

}
