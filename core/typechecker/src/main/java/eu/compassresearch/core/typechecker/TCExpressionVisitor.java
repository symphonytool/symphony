package eu.compassresearch.core.typechecker;

import java.util.List;

import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AMultiBindListDefinition;
import org.overture.ast.expressions.AExists1Exp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.patterns.ATypeBind;
import org.overture.ast.patterns.PBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.assistants.ATypeBindAssistantTC;
import org.overture.ast.patterns.assistants.PBindAssistantTC;
import org.overture.ast.types.PType;

public class TCExpressionVisitor extends QuestionAnswerAdaptor<TypeCheckInfo, PType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509187123701383525L;

	final private QuestionAnswerAdaptor<TypeCheckInfo, PType> parent;

	public TCExpressionVisitor(CmlTypeChecker parentChecker)
	{
		parent = parentChecker;
	}

	@Override
	public PType caseAExists1Exp(AExists1Exp node, TypeCheckInfo question) {
		PBind bind = node.getBind();
		List<PMultipleBind> bindList = PBindAssistantTC.getMultipleBindList(bind);
		node.setDef(AstFactory.newAMultiBindListDefinition(bind.getLocation(), bindList));
		
		if (node.getBind() instanceof ATypeBind)
		{
			ATypeBind tb = (ATypeBind)node.getBind();
			ATypeBindAssistantTC.typeResolve(tb, parent, question);
		}
		
		return super.caseAExists1Exp(node, question);
	}


	
	
}
