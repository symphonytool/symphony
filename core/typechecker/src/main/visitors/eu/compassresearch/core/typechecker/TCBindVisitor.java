package eu.compassresearch.core.typechecker;

import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.intf.ICMLQuestionAnswer;

@SuppressWarnings({ "serial", "unused" })
class TCBindVisitor extends QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
		implements ICMLQuestionAnswer<TypeCheckInfo, PType> {

	private VanillaCmlTypeChecker parent;

	public TCBindVisitor(VanillaCmlTypeChecker vanillaCmlTypeChecker) {

		this.parent = vanillaCmlTypeChecker;
	}
}
