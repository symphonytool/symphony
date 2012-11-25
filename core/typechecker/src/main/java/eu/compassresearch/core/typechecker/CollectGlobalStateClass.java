package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.lex.LexNameToken;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@SuppressWarnings("serial")
public class CollectGlobalStateClass extends AnalysisCMLAdaptor {

	private TypeCheckQuestion question;
	private List<PDefinition> members;

	public static AClassClassDefinition getGlobalRoot(List<PSource> sources,
			TypeIssueHandler issueHandler, TypeCheckInfo info)
			throws AnalysisException {

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

		// That's it
		return globalRoot;
	}

	@Override
	public void defaultPSource(PSource node) throws AnalysisException {
		LinkedList<SParagraphDefinition> paragraphs = node.getParagraphs();
		for (SParagraphDefinition paragraph : paragraphs) {
			paragraph.apply(this);
		}
	}

	private CollectGlobalStateClass(List<PDefinition> members,
			TypeCheckQuestion question) {
		this.members = members;
		this.question = question;

	}

	@Override
	public void caseATypesParagraphDefinition(ATypesParagraphDefinition node)
			throws AnalysisException {

		LinkedList<ATypeDefinition> types = node.getTypes();
		for (ATypeDefinition typeDef : types) {
			members.add(typeDef);
		}
	}

	@Override
	public void caseAFunctionParagraphDefinition(
			AFunctionParagraphDefinition node) throws AnalysisException {

		LinkedList<PDefinition> funDefs = node.getFunctionDefinitions();
		for (PDefinition fdef : funDefs) {
			members.add(fdef);
		}
	}

	@Override
	public void caseAValueParagraphDefinition(AValueParagraphDefinition node)
			throws AnalysisException {
		LinkedList<PDefinition> valDefs = node.getValueDefinitions();
		for (PDefinition valDef : valDefs)
			members.add(valDef);
	}

	@Override
	public void caseAChansetParagraphDefinition(AChansetParagraphDefinition node)
			throws AnalysisException {

		LinkedList<AChansetDefinition> chanSets = node.getChansets();
		for (AChansetDefinition chanSetDef : chanSets) {
			question.addChannel(chanSetDef.getName(), chanSetDef);
		}

		super.caseAChansetParagraphDefinition(node);
	}

	@Override
	public void caseAChannelParagraphDefinition(AChannelParagraphDefinition node)
			throws AnalysisException {
		LinkedList<AChannelNameDefinition> channels = node
				.getChannelNameDeclarations();
		for (AChannelNameDefinition channel : channels)
			question.addChannel(channel.getName(), channel);
	}

}
