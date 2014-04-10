package eu.compassresearch.ide.refinementtool;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.AReturnStm;
import org.overture.ast.statements.ASpecificationStm;
import org.overture.ast.statements.PStm;

public class ImplicitOperationRefineLaw implements IRefineLaw {

	@Override
	public String getName() {
		return "Implicit Operation Refinement";
	}

	@Override
	public boolean isApplicable(INode node) {
		return (node instanceof AImplicitOperationDefinition);
	}

	@Override
	public Refinement apply(INode node) {
		AImplicitOperationDefinition oo = (AImplicitOperationDefinition) node; 
		
		// Create a specification statement to act as the body
		
//		String spec = "[";
//		if (oo.getExternals().size() > 0) {
//			spec += "frame ";
//			for (AExternalClause e: oo.getExternals()) {
//				spec += e.getMode().toString()+" ";
//				List<String> names = new LinkedList<String>();
//				for (ILexNameToken n: e.getIdentifiers()) {
//					names.add(n.toString());
//				}
//				if (names.size() > 0) {
//					spec += names.get(0);
//					for (int i = 1; i < names.size(); i++) {
//						spec += ", "+names.get(i);
//					}
//				}
//				spec += " ";
//			}
//		}
//		if (oo.getPrecondition() != null) {
//			spec += "pre "+oo.getPrecondition().toString()+"\n";
//		}
//		if (oo.getPostcondition() != null) {
//			spec += "post "+oo.getPostcondition().toString()+"\n";
//		}
//		spec += "]";
//		
//		String ret = "";
//		if (oo.getResult() != null) {
//			ret += "return "+oo.getResult().getPattern().toString();
//		}
//		
//		String var = "";
//		if (oo.getResult() != null) {
//			var += "(dcl "+oo.getResult().toString()+" @ ";
//		}
//		var += spec+"; "+ret;
//		
//		if (oo.getResult() != null) {
//			var += ")";
//		}
//		
//		String op = "";
//		op += oo.getAccess().toString();
//		op += oo.getName().getSimpleName().toString();
//		op += ":"+oo.getType().toString()+"\n";
//		op += oo.getName().getSimpleName().toString();
//		op += "(";
//		List<String> names = new LinkedList<String>();
//		for (APatternListTypePair list: oo.getParameterPatterns()) {
//			for (PPattern pat: list.getPatterns()) {
//				names.add(pat.toString());
//			}
//		}
//		if (names.size() > 0) {
//			op += names.get(0);
//			for (int i = 1; i < names.size(); i++) {
//				op += "; "+names.get(i);
//			}
//		}
//		op += ") == "+var;
//		
//		
//		
//		return new Refinement(op, new LinkedList<PExp>());
		
		ASpecificationStm spec = new ASpecificationStm();
		
		spec.setPrecondition(oo.getPrecondition().clone());
		spec.setPostcondition(oo.getPostcondition().clone());
		spec.setExternals(((List<AExternalClause>)(oo.getExternals().clone())));//new LinkedList<AExternalClause>());
		
		AReturnStm ret = new AReturnStm();
		AVariableExp v = new AVariableExp();
		APatternTypePair result = oo.getResult();
		AIdentifierPattern pat = (AIdentifierPattern)result.getPattern();
		v.setName(pat.getName().clone());
		ret.setExpression(v);
		
//		ASequentialCompositionAction seq = new ASequentialCompositionAction();
//		
//		AStmAction specA = new AStmAction();
//		specA.setStatement(spec);
//		
//		AStmAction retA = new AStmAction();
//		retA.setStatement(ret);
//		
//		seq.setLeft(specA);
//		seq.setRight(retA);
		
		ABlockSimpleBlockStm block = new ABlockSimpleBlockStm();
		List<PStm> seq = new LinkedList<PStm>();
		seq.add(spec);
		seq.add(ret);
		block.setStatements(seq);
		
		List<AAssignmentDefinition> assigns = new LinkedList<AAssignmentDefinition>();
		
		AAssignmentDefinition assign = new AAssignmentDefinition();
		ILexNameToken name = pat.getName().clone();
		assign.setName(name);
		assign.setType(result.getType().clone());
		assign.setUsed(false);
		
		assigns.add(assign);
		block.setAssignmentDefs(assigns);
		
		AExplicitOperationDefinition no = new AExplicitOperationDefinition();
				
		no.setName(oo.getName().clone());
		no.setPrecondition(oo.getPrecondition().clone());
		no.setPostcondition(oo.getPostcondition().clone());
		no.setBody(block);
		no.setType(oo.getType().clone());
		
		// TODO Auto-generated method stub
		return new Refinement(no.toString(), new LinkedList<PExp>());		
	}

}
