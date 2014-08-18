package eu.compassresearch.ide.refinementtool.maude;

import java.util.Iterator;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
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

	
	
	
	@Override
	public String caseACommunicationAction(ACommunicationAction a,
			Integer question) throws AnalysisException {
		String channel = a.getIdentifier().getName();
		List<PCommunicationParameter> params = a.getCommunicationParameters();
		PAction action = a.getAction();
		
		StringBuilder sb = new StringBuilder();
		sb.append("#c(\""+channel+"\")");
		for (PCommunicationParameter p: params) {
			sb.append(p.apply(this,0));
		}
		sb.append(" -> ");
		sb.append(action.apply(this,0));
		
		return sb.toString();
	}
	
	

	@Override
	public String caseAReadCommunicationParameter(
			AReadCommunicationParameter node, Integer question)
			throws AnalysisException {
		PPattern p = node.getPattern();
		PExp e = node.getExpression();
	
		if (p instanceof AIdentifierPattern) {
			StringBuilder sb = new StringBuilder();
			sb.append("?(nm(\"");
			sb.append(((AIdentifierPattern)p).getName());
			sb.append("\"))");
			return sb.toString();
		} else
			return ""; // only treating the case where the input is an identifier, not a pattern.
	}

	@Override
	public String caseASignalCommunicationParameter(
			ASignalCommunicationParameter node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append(".(");
		sb.append(node.getExpression().apply(cmlpp));
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append("!(");
		sb.append(node.getExpression().apply(cmlpp));
		sb.append(")");
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
