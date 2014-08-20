package eu.compassresearch.ide.refinementtool.maude;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.expressions.APlusNumericBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.ASpecificationStm;
import org.overture.ast.statements.PStm;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.statements.PCMLStateDesignator;
import eu.compassresearch.ide.refinementtool.RefinePrettyPrinter;

public class MaudePrettyPrinter extends RefinePrettyPrinter {

	public MaudePrettyPrinter() {
		super();
		cmlpp = new MaudePExprPrettyPrinter();
	}
	
	@Override
	public String caseAAssignmentStm(AAssignmentStm node, Integer question)
			throws AnalysisException {
		return "#nm(\""+node.getTarget().toString() + "\") := " + node.getExp().apply(this,0);
	}
	
	
	
	
	@Override
	public String caseAHidingAction(AHidingAction node, Integer question)
			throws AnalysisException {
		return node.getLeft().apply(this,0) + " \\\\ "+ node.getChansetExpression().apply(this,0);
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
	public String caseABlockSimpleBlockStm(ABlockSimpleBlockStm node,
			Integer question) throws AnalysisException {
		
		StringBuilder sb = new StringBuilder();
		
		List<AAssignmentDefinition> defs = node.getAssignmentDefs();
		
		sb.append("#paren(");
		
		if (defs.size() > 0) {
			sb.append("dcl ");
			sb.append(defs.get(0).apply(this, 0));
			for (int i = 1; i < defs.size(); i ++) {
				sb.append(", ");
				sb.append(defs.get(i).apply(this, 0));
			}
			sb.append(" @ ");
		}
		
		List<PStm> stats = node.getStatements();
		
		if (stats.size() > 0) {
			sb.append(stats.get(0).apply(this, 0));
			for (int i = 1; i < stats.size(); i ++) {
				sb.append("; ");
				sb.append(stats.get(i).apply(this, 0));
			}
		}
			
		sb.append(")");
		
		return sb.toString();
	}
	
	
	@Override
	public String caseACommunicationAction(ACommunicationAction a,
			Integer question) throws AnalysisException {
		String channel = a.getIdentifier().getName();
		List<PCommunicationParameter> params = a.getCommunicationParameters();
		PAction action = a.getAction();
		
		StringBuilder sb = new StringBuilder();
		sb.append("#nm(\""+channel+"\")");
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
			sb.append("?(#nm(\"");
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
		sb.append("mu #anm(\""+name+"\") @ #paren(");
		sb.append(node.getActions().get(0).apply(this,0));
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String caseANameChannelExp(ANameChannelExp node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append("#nm(\""+node.getIdentifier().getName()+"\")");
		for (PExp e: node.getExpressions()) {
			sb.append(".("+e.apply(this,0)+")");
		}
		return sb.toString();
	}

	public static String printFrame(List<AExternalClause> exts) {
		StringBuilder sb = new StringBuilder();
		if (exts.size() > 0) {
			sb.append("frame ");
			
			for (AExternalClause e: exts) {
				sb.append(e.getMode().toString()+" [$ ");
				List<String> names = new LinkedList<String>();
				for (ILexNameToken n: e.getIdentifiers()) {
					names.add(n.toString());
				}
				if (names.size() > 0) {
					sb.append("#nm(\""+names.get(0)+"\")");
					for (int i = 1; i < names.size(); i++) {
						sb.append(", #nm(\""+names.get(i)+"\")");
					}
				}
				sb.append(" $] ");
			}
		}
		return sb.toString();
	}
	
	
	@Override
	public String caseASpecificationStm(ASpecificationStm node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		
		List<AExternalClause> exts = node.getExternals();
		
		sb.append(printFrame(exts));
		
		if (node.getPrecondition() != null) {
			sb.append("pre ");
			sb.append(node.getPrecondition().apply(cmlpp));
			sb.append(" ");
		}
		
		if (node.getPostcondition() != null) {
			sb.append("post ");
			sb.append(node.getPostcondition().apply(cmlpp));
		} else {
			sb.append("post true");
		}
		
		sb.append("]");
		
		return sb.toString();
	}

	

}
