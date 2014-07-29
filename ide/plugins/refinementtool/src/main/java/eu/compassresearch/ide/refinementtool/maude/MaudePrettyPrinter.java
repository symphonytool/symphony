package eu.compassresearch.ide.refinementtool.maude;

import java.util.Iterator;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;

import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ide.refinementtool.RefinePrettyPrinter;

public class MaudePrettyPrinter extends RefinePrettyPrinter {

	public MaudePrettyPrinter() {
		super();
		cmlpp = new MaudePExprPrettyPrinter();
	}
	
	@Override
	public String caseAReferenceAction(AReferenceAction node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append("#ref ");
		sb.append("#nm(\""+node.getName()+"\")");
		if (node.getArgs().size() > 0) {
			sb.append("(");
			for (Iterator<PExp> it = node.getArgs().iterator(); it.hasNext(); ) {
				PExp exp = it.next();
				sb.append(exp.apply(cmlpp));
				if (it.hasNext()) {
					sb.append(", ");
				}
			}
			sb.append(")");
		}
		return sb.toString();
	}

}
