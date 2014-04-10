package eu.compassresearch.ide.refinementtool;

import java.util.LinkedList;

import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.ASpecificationStm;

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
		
		ASpecificationStm spec = new ASpecificationStm();
		
		spec.setPrecondition(oo.getPrecondition());
		spec.setPostcondition(oo.getPostcondition());
		spec.setExternals(new LinkedList<AExternalClause>());
		
		AExplicitOperationDefinition no = new AExplicitOperationDefinition();
				
		no.setName(oo.getName());
		no.setPrecondition(oo.getPrecondition());
		no.setPostcondition(oo.getPostcondition());
		no.setBody(spec);
		no.setType(oo.getType());
		
		String s = spec.toString();
		
		// TODO Auto-generated method stub
		return new Refinement(no.toString(), new LinkedList<PExp>());		
	}

}
