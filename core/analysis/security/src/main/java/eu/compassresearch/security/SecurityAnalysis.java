package eu.compassresearch.security;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.security.SecurityEnvironment.SubtreeJudgement;

public class SecurityAnalysis {

	public SubtreeJudgement runAnalysis(PSource s) throws AnalysisException {
		SubtreeJudgement result = new SubtreeJudgement(s);
		SecurityVisitor sv = new SecurityVisitor();
		for(PDefinition topDef : s.getParagraphs()) {
			SubtreeJudgement sourceList = topDef.apply(sv,new SecurityEnvironment());
			if (sourceList != null)
			  result.getInvolvedJudgements().addAll(sourceList.getInvolvedJudgements());
		}
		return result;
	}
	
}
