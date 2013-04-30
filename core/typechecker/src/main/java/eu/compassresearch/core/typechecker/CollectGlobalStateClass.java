package eu.compassresearch.core.typechecker;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameList;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@SuppressWarnings("serial")
public class CollectGlobalStateClass extends AnalysisCMLAdaptor {

	private final Collection<PDefinition> members;

	public static AClassClassDefinition getGlobalRoot(
			Collection<PSource> sources, TypeIssueHandler issueHandler,
			CmlTypeCheckInfo info) throws AnalysisException {

		// Create visitor and visit each source collecting global definitions
		List<PDefinition> members = new LinkedList<PDefinition>();
		CollectGlobalStateClass me = new CollectGlobalStateClass(members, info);
		for (PSource source : sources) {
			source.apply(me);
		}

		// Create surrogate global root class
		LexNameToken className = new LexNameToken("CML",
				new LexIdentifierToken("Global Definitions", false,
						new LexLocation()));
		AClassClassDefinition globalRoot = AstFactory.newAClassClassDefinition(
				className, new LexNameList(), members);

		info.setGlobalClassDefinitions(globalRoot);
		// That's it
		return globalRoot;
	}

	@Override
	public void defaultPSource(PSource node) throws AnalysisException {
		LinkedList<PDefinition> paragraphs = node.getParagraphs();
		for (PDefinition paragraph : paragraphs) {
			paragraph.apply(this);
		}
	}

	private CollectGlobalStateClass(List<PDefinition> members,
			TypeCheckQuestion question) {
		this.members = members;

	}

	/*
	 * @Override public void caseAChannelsDefinition(AChannelsDefinition node)
	 * throws AnalysisException {
	 * 
	 * LinkedList<AChannelNameDefinition> channels =
	 * node.getChannelNameDeclarations(); for(AChannelNameDefinition chanDef:
	 * channels) { if (chanDef.getSingleType() != null) { ATypeSingleDeclaration
	 * typeDecl = chanDef.getSingleType(); LinkedList<LexIdentifierToken> ids =
	 * typeDecl.getIdentifiers(); for (LexIdentifierToken id : ids)
	 * question.addChannel(id, chanDef); } }
	 * 
	 * }
	 */

	@Override
	public void caseATypesDefinition(ATypesDefinition node)
			throws AnalysisException {

		List<PDefinition> defs = TCDeclAndDefVisitor
				.handleDefinitionsForOverture(node);
		members.addAll(defs);
		super.caseATypesDefinition(node);
	}

	@Override
	public void caseAValuesDefinition(AValuesDefinition node)
			throws AnalysisException {
		List<PDefinition> defs = TCDeclAndDefVisitor
				.handleDefinitionsForOverture(node);
		members.addAll(defs);
	}

	@Override
	public void caseAFunctionsDefinition(AFunctionsDefinition node)
			throws AnalysisException {

		List<PDefinition> defs = TCDeclAndDefVisitor
				.handleDefinitionsForOverture(node);

		for (PDefinition fdef : defs) {

			PDefinition predef = null;
			PDefinition postdef = null;
			if (fdef instanceof AExplicitFunctionDefinition) {
				predef = ((AExplicitFunctionDefinition) fdef).getPredef();
				postdef = ((AExplicitFunctionDefinition) fdef).getPostdef();
			}

			if (fdef instanceof AImplicitFunctionDefinition) {
				predef = ((AImplicitFunctionDefinition) fdef).getPredef();
				postdef = ((AImplicitFunctionDefinition) fdef).getPostdef();
			}

			if (predef != null)
				members.addAll(TCDeclAndDefVisitor
						.handleDefinitionsForOverture(predef));
			// if (postdef != null)
			// members.addAll(TCDeclAndDefVisitor.handleDefinitionsForOverture(postdef));

		}

		members.addAll(defs);
	}

}
