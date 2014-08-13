package eu.compassresearch.ide.refinementtool.maude;

import java.util.Iterator;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;

import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.definitions.AActionDefinition;
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
		sb.append("#anm(\""+node.getName()+"\")");
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
	
	@Override
	public String caseAActionDefinition(AActionDefinition node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append("#anm(\""+node.getName().getSimpleName()+"\")");
		sb.append(" =def= ");
		List<PParametrisation> params = node.getDeclarations();
		for (Iterator<PParametrisation> it = params.iterator(); it.hasNext(); ) {
			PParametrisation param = it.next();
			sb.append(param.apply(this,0));
			if (it.hasNext()) sb.append(", ");
			else sb.append(" @ ");
		}
		sb.append(node.getAction().apply(this,0));
		return sb.toString();
	}

	// assuming a single recursion
	@Override
	public String caseAMuAction(AMuAction node, Integer question)
			throws AnalysisException {
		String name = node.getIdentifiers().get(0).getName();
		StringBuilder sb = new StringBuilder();
		sb.append("mu #anm(\""+name+"\") @ ");
		sb.append(node.getActions().get(0).apply(this,0));
		return sb.toString();
	}
	
	

}
