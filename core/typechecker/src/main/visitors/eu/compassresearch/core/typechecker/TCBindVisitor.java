package eu.compassresearch.core.typechecker;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.patterns.ADefPatternBind;
import org.overture.ast.patterns.PBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.assistant.pattern.PPatternBindAssistantTC;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.intf.ICMLQuestionAnswer;
import eu.compassresearch.ast.types.AErrorType;

@SuppressWarnings({ "serial", "unused" })
class TCBindVisitor extends QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
		implements ICMLQuestionAnswer<TypeCheckInfo, PType> {

	private VanillaCmlTypeChecker parent;

	public TCBindVisitor(VanillaCmlTypeChecker vanillaCmlTypeChecker) {

		this.parent = vanillaCmlTypeChecker;
	}

	@Override
	public PType caseADefPatternBind(ADefPatternBind node,
			TypeCheckInfo question) throws AnalysisException {
		// Deprecated the ADefPattern is so simple only carries one identifier
		// and thats like it ! (RWL)
		return new AErrorType(node.getLocation(), true);
	}
	
	
	
}
