package eu.compassresearch.ide.refinementtool;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.expressions.ASetCompSetExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AUnionPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.ALetStm;
import org.overture.ast.statements.AReturnStm;
import org.overture.ast.statements.ASpecificationStm;
import org.overture.ast.statements.AWhileStm;
import org.overture.ast.statements.PStm;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.statements.AActionStm;

public class RefinePrettyPrinter extends QuestionAnswerCMLAdaptor<Integer, String> {
	public static <A> List<A> delimit(List<A> list, A dl) {
		List<A> nl = new LinkedList<A>();
		if (list.size() > 0) {
			nl.add(nl.get(0));
			for (int i = 1; i < nl.size(); i++) {
				nl.add(dl);
				nl.add(nl.get(i));
			}
		}
		return nl;
	}
	public static String printFrame(List<AExternalClause> exts) {
		StringBuilder sb = new StringBuilder();
		if (exts.size() > 0) {
			sb.append("frame ");
			
			for (AExternalClause e: exts) {
				sb.append(e.getMode().toString()+" ");
				List<String> names = new LinkedList<String>();
				for (ILexNameToken n: e.getIdentifiers()) {
					names.add(n.toString());
				}
				if (names.size() > 0) {
					sb.append(names.get(0));
					for (int i = 1; i < names.size(); i++) {
						sb.append(", "+names.get(i));
					}
				}
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	public static String tabs(Integer n) {
		String s = "";
		for (int i = 0; i < n; i++) {
			s+="\t";
		}
		return s;
	}
	
	protected String eol = System.getProperty("line.separator");
	
	protected CmlPExprPrettyPrinter cmlpp = new CmlPExprPrettyPrinter();

	@Override
	public String caseAActionDefinition(AActionDefinition node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append(tabs(question));
		sb.append(node.getName().getSimpleName());
		sb.append(" = ");
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
	public String caseAActionProcess(AActionProcess node, Integer question)
			throws AnalysisException {
		return node.getAction().apply(this, question);
	}

	@Override
	public String caseAActionStm(AActionStm node, Integer question)
			throws AnalysisException {
		return node.getAction().apply(this, question);
	}

	@Override
	public String caseAAssignmentDefinition(AAssignmentDefinition node,
			Integer question) throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(tabs(question));
		sb.append(node.getName().getSimpleName());
		sb.append(" : ");
		sb.append(node.getType());

		if (node.getExpression() != null) {
			sb.append(" := ");
			sb.append(node.getExpression().apply(cmlpp));
		}
		
		return sb.toString();
	}

	@Override
	public String caseAAssignmentStm(AAssignmentStm node, Integer question)
			throws AnalysisException {
		return node.getTarget().toString() + " := " + node.getExp().toString();
	}

	@Override
	public String caseABlockSimpleBlockStm(ABlockSimpleBlockStm node,
			Integer question) throws AnalysisException {
		
		StringBuilder sb = new StringBuilder();
		
		List<AAssignmentDefinition> defs = node.getAssignmentDefs();
		
		sb.append("(");
		
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
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(a.getIdentifier().toString());
		
		for (PCommunicationParameter p : a.getCommunicationParameters()) {
			sb.append(p.apply(this, question));
		}
	
		sb.append(" -> ");
		sb.append(a.getAction().apply(this, question));
		
		return sb.toString();
	}
	@Override
	public String caseAExplicitOperationDefinition(
			AExplicitOperationDefinition n, Integer question)
			throws AnalysisException {
		
		AExplicitOperationDefinition node = n.clone();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(tabs(question));
		
		if (node.getAccess() != null && !(node.getAccess().getAccess() instanceof APrivateAccess)) {
			sb.append(node.getAccess().toString());
			sb.append(" ");
		}
		
		sb.append(node.getName().getSimpleName().toString());
		sb.append(": ");
		
		String type = node.getType().toString(); 
		
		sb.append(type.substring(1, type.length()-1));
		sb.append(eol);
		sb.append(tabs(question));
		sb.append(node.getName().getSimpleName().toString());
		sb.append("(");
				
		if (node.getParameterPatterns().size() > 0) {
			PPattern p = node.getParameterPatterns().get(0);
			sb.append(node.getParameterPatterns().get(0).toString());
			for (int i = 1; i < node.getParameterPatterns().size(); i++) {
				sb.append(", ");
				sb.append(node.getParameterPatterns().get(i));
			}
		}

		if (node.getParamDefinitions().size() > 0) {
			sb.append(node.getParamDefinitions().get(0).toString());
			for (int i = 1; i < node.getParamDefinitions().size(); i++) {
				sb.append(", ");
				sb.append(node.getParamDefinitions().get(i));
			}
		}
		
	
		
		
		sb.append(") == ");
		
		sb.append(node.getBody().apply(this, 0));
		
		return sb.toString();
		
	}
	@Override
	public String caseAExternalChoiceAction(AExternalChoiceAction node,
			Integer q) throws AnalysisException {
		return node.getLeft().apply(this, q) + " [] " + node.getRight().apply(this, 0);
	}

	@Override
	public String caseAGuardedAction(AGuardedAction node, Integer q)
			throws AnalysisException {
		return "[" + node.getExpression().apply(cmlpp) + "] & " + node.getAction().apply(this, q); 
	}
	@Override
	public String caseALetStm(ALetStm node, Integer question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseALetStm(node, question);
	}
	@Override
	public String caseAReadCommunicationParameter(
			AReadCommunicationParameter node, Integer question)
			throws AnalysisException {
		return "?"+node.getPattern().toString();
	}

	@Override
	public String caseAReturnStm(AReturnStm node, Integer question)
			throws AnalysisException {
		return "return " + node.getExpression().toString();
	}
	

	@Override
	public String caseASetCompSetExp(ASetCompSetExp node, Integer question)
			throws AnalysisException {
		return super.caseASetCompSetExp(node, question);
	}

	@Override
	public String caseASignalCommunicationParameter(
			ASignalCommunicationParameter node, Integer question)
			throws AnalysisException {
		return ".("+node.getExpression().apply(this,question)+")";
	}
	@Override
	public String caseASkipAction(ASkipAction node, Integer question)
			throws AnalysisException {
		return tabs(question)+"Skip";
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

	@Override
	public String caseAStmAction(AStmAction node, Integer question)
			throws AnalysisException {
		return node.getStatement().apply(this, question);
	}
	@Override
	public String caseAStopAction(AStopAction node, Integer question)
			throws AnalysisException {
		return tabs(question)+"Stop";
	}
	/* (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#caseAUnionPattern(org.overture.ast.patterns.AUnionPattern, java.lang.Object)
	 */
	@Override
	public String caseAUnionPattern(AUnionPattern node, Integer question)
			throws AnalysisException {
		return tabs(question) + node.toString();
	}
	
	/* (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#caseAUnionType(org.overture.ast.types.AUnionType, java.lang.Object)
	 */
	@Override
	public String caseAUnionType(AUnionType node, Integer question)
			throws AnalysisException {
		LinkedList<PType> types = node.getTypes();
		if (types.size() > 0) {
			String s = types.get(0).apply(this,0);
			for (int i = 1; i < types.size(); i++) {
				s += " | " + types.get(i).apply(this,0);
			}
			return s;
		} else {
			throw new AnalysisException("Union type of size 0");
		}
	}
	
	/* (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseAUntimedTimeoutAction(eu.compassresearch.ast.actions.AUntimedTimeoutAction, java.lang.Object)
	 */
	@Override
	public String caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
			Integer question) throws AnalysisException {
		return tabs(question)+node.getLeft().apply(this,0)+" [_> "+node.getRight().apply(this,0);
	}

	/* (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseAUntimedTimeoutProcess(eu.compassresearch.ast.process.AUntimedTimeoutProcess, java.lang.Object)
	 */
	@Override
	public String caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
			Integer question) throws AnalysisException {
		return tabs(question)+node.getLeft().apply(this,0)+" [_> "+node.getRight().apply(this,0);
	}

	/* (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseAValParametrisation(eu.compassresearch.ast.actions.AValParametrisation, java.lang.Object)
	 */
	@Override
	public String caseAValParametrisation(AValParametrisation node,
			Integer question) throws AnalysisException {
		return "val "+node.getDeclaration().apply(this,0);
	}

	/* (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#caseAValueDefinition(org.overture.ast.definitions.AValueDefinition, java.lang.Object)
	 */
	@Override
	public String caseAValueDefinition(AValueDefinition node, Integer question)
			throws AnalysisException {
		String s = tabs(question);
		if (node.getAccess() != null)
			s += node.getAccess().apply(this,0)+" ";
		
		s += node.getPattern().apply(this,0);
		
		if (node.getType() != null)
			s += ": "+node.getType().apply(this,0);
		
		s += " = "+node.getExpression().apply(this,0);
		return s;
	}

	/* (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseAVresParametrisation(eu.compassresearch.ast.actions.AVresParametrisation, java.lang.Object)
	 */
	@Override
	public String caseAVresParametrisation(AVresParametrisation node,
			Integer question) throws AnalysisException {
		return "vres "+node.getDeclaration().apply(this,0);
	}

	/* (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseAWaitAction(eu.compassresearch.ast.actions.AWaitAction, java.lang.Object)
	 */
	@Override
	public String caseAWaitAction(AWaitAction node, Integer question)
			throws AnalysisException {
		return tabs(question)+"Wait "+node.getExpression().apply(this,0);
	}

	/* (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#caseAWhileStm(org.overture.ast.statements.AWhileStm, java.lang.Object)
	 */
	@Override
	public String caseAWhileStm(AWhileStm node, Integer question)
			throws AnalysisException {
		return tabs(question)+"while "+node.getExp().apply(this,0)+" do \n"+node.getStatement().apply(this,question+1);
	}
	public String caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node, Integer question)
			throws AnalysisException {
		return "!("+node.getExpression().apply(this,0)+")";
	}
	@Override
	public String createNewReturnValue(INode arg0, Integer arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String createNewReturnValue(Object arg0, Integer arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String caseASequentialCompositionAction(
			ASequentialCompositionAction node, Integer question)
			throws AnalysisException {
		return tabs(question)+node.getLeft().apply(this,0)+" ; "+node.getRight().apply(this,0);
	}
	
	@Override
	public String caseALocalDefinition(ALocalDefinition node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append(node.getName().getSimpleName());
		sb.append(": ");
		sb.append(node.getType().toString());
		return sb.toString();
	}
	
	
	
	
	
	@Override
	public String caseAReferenceAction(AReferenceAction node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append(node.getName());
		if (node.getArgs().size() > 0) {
			sb.append("(");
			for (Iterator<PExp> it = node.getArgs().iterator(); it.hasNext(); ) {
				PExp exp = it.next();
				sb.append(exp.apply(this,0));
				if (it.hasNext()) {
					sb.append(", ");
				}
			}
			sb.append(")");
		}
		return sb.toString();
	}
	/* (non-Javadoc)
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#defaultPExp(org.overture.ast.expressions.PExp, java.lang.Object)
	 */
	@Override
	public String defaultPExp(PExp node, Integer question)
			throws AnalysisException {
		return tabs(question)+node.apply(cmlpp);
	}
	
	@Override
	public String caseAEnumVarsetExpression(AEnumVarsetExpression node,
			Integer question) throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		for (Iterator<ANameChannelExp> it = node.getChannelNames().iterator(); it.hasNext(); ) {
			ANameChannelExp e = it.next();
			sb.append(e.apply(this,0));
			if (it.hasNext()) sb.append(", ");
		}
		sb.append(" }");
		return sb.toString();
	}

	@Override
	public String caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node,
			Integer question) throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append("{| ");
		for (Iterator<ANameChannelExp> it = node.getChannelNames().iterator(); it.hasNext(); ) {
			ANameChannelExp e = it.next();
			sb.append(e.apply(this,0));
			if (it.hasNext()) sb.append(", ");
		}
		sb.append(" |}");
		return sb.toString();
	}
	@Override
	public String caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append("( ");
		sb.append(node.getLeftAction().apply(this,0));
		sb.append(" [| ");
		sb.append(node.getLeftNamesetExpression().apply(this,0));
		sb.append(" | ");
		sb.append(node.getChansetExpression().apply(this,0));
		sb.append(" | ");
		sb.append(node.getRightNamesetExpression().apply(this,0));
		sb.append(" |] ");
		sb.append(node.getRightAction().apply(this,0));
		sb.append(" )");
		return sb.toString();
	}
	@Override
	public String caseAMuAction(AMuAction node, Integer question)
			throws AnalysisException {
		StringBuilder sb = new StringBuilder();
		sb.append("mu ");
		for (Iterator<ILexIdentifierToken> it = node.getIdentifiers().iterator(); it.hasNext();) {
			ILexIdentifierToken id = it.next();
			sb.append(id.getName());
			if (it.hasNext())
				sb.append(", ");
		}
		sb.append("(");
		for (Iterator<PAction> it = node.getActions().iterator(); it.hasNext();) {
			PAction a = it.next();
			sb.append(a.apply(this,0));
			if (it.hasNext()) 
				sb.append(", ");
		}
		sb.append(")");
		return sb.toString();
	}	

	
	
}
