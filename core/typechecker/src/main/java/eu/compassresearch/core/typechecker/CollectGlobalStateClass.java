package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.lex.LexNameToken;

import eu.compassresearch.ast.analysis.AnalysisCMLAdaptor;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@SuppressWarnings("serial")
public class CollectGlobalStateClass extends AnalysisCMLAdaptor {

	private TypeCheckQuestion question;
	private List<PDefinition> members;

	public static AClassClassDefinition getGlobalRoot(List<PSource> sources,
			TypeIssueHandler issueHandler, CmlTypeCheckInfo info)
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
		this.question = question;

	}


	@Override
	public void caseAValuesDefinition(AValuesDefinition node)
			throws AnalysisException {
		List<PDefinition> defs = TCDeclAndDefVisitor.handleDefinitionsForOverture(node);
		members.addAll(defs);
	}

}
