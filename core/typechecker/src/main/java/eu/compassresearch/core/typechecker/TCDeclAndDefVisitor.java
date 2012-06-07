package eu.compassresearch.core.typechecker;

import org.overture.ast.actions.ADeclareStatementDeclareStatement;
import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.ast.declarations.AActionDeclaration;
import org.overture.ast.declarations.AChannelDeclaration;
import org.overture.ast.declarations.AChansetDeclaration;
import org.overture.ast.declarations.AClassDeclaration;
import org.overture.ast.declarations.AFunctionDeclaration;
import org.overture.ast.declarations.AOperationDeclaration;
import org.overture.ast.declarations.ASingleTypeDeclaration;
import org.overture.ast.declarations.AStateDeclaration;
import org.overture.ast.declarations.ATypeDeclaration;
import org.overture.ast.declarations.AValueDeclaration;
import org.overture.ast.definitions.AActionDefinition;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AChannelDefinition;
import org.overture.ast.definitions.AChansetDefinition;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AClassbodyDefinition;
import org.overture.ast.definitions.AEqualsDefinition;
import org.overture.ast.definitions.AExplicitFunctionFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationOperationDefinition;
import org.overture.ast.definitions.AExternalDefinition;
import org.overture.ast.definitions.AImplicitFunctionFunctionDefinition;
import org.overture.ast.definitions.AImplicitOperationOperationDefinition;
import org.overture.ast.definitions.AInheritedDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AUntypedDefinition;
import org.overture.ast.definitions.AValueDefinition;

public class TCDeclAndDefVisitor extends DepthFirstAnalysisAdaptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3595335302756624612L;

	@Override
	public void caseAActionDeclaration(AActionDeclaration node) {
		// TODO Auto-generated method stub
		super.caseAActionDeclaration(node);
	}

	@Override
	public void caseAActionDefinition(AActionDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAActionDefinition(arg0);
	}

	@Override
	public void caseAAssignmentDefinition(AAssignmentDefinition node) {
		// TODO Auto-generated method stub
		super.caseAAssignmentDefinition(node);
	}

	@Override
	public void caseAChannelDeclaration(AChannelDeclaration node) {
		// TODO Auto-generated method stub
		super.caseAChannelDeclaration(node);
	}

	@Override
	public void caseAChannelDefinition(AChannelDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAChannelDefinition(arg0);
	}

	@Override
	public void caseAChansetDeclaration(AChansetDeclaration node) {
		// TODO Auto-generated method stub
		super.caseAChansetDeclaration(node);
	}

	@Override
	public void caseAChansetDefinition(AChansetDefinition node) {
		// TODO Auto-generated method stub
		super.caseAChansetDefinition(node);
	}

	@Override
	public void caseAClassClassDefinition(AClassClassDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAClassClassDefinition(arg0);
	}

	@Override
	public void caseAClassDeclaration(AClassDeclaration node) {
		// TODO Auto-generated method stub
		super.caseAClassDeclaration(node);
	}

	@Override
	public void caseAClassInvariantDefinition(AClassInvariantDefinition node) {
		// TODO Auto-generated method stub
		super.caseAClassInvariantDefinition(node);
	}

	@Override
	public void caseAClassbodyDefinition(AClassbodyDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAClassbodyDefinition(arg0);
	}

	@Override
	public void caseADeclareStatementDeclareStatement(
			ADeclareStatementDeclareStatement arg0) {
		// TODO Auto-generated method stub
		super.caseADeclareStatementDeclareStatement(arg0);
	}

	@Override
	public void caseAEqualsDefinition(AEqualsDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAEqualsDefinition(arg0);
	}

	@Override
	public void caseAExplicitFunctionFunctionDefinition(
			AExplicitFunctionFunctionDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAExplicitFunctionFunctionDefinition(arg0);
	}

	@Override
	public void caseAExplicitOperationOperationDefinition(
			AExplicitOperationOperationDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAExplicitOperationOperationDefinition(arg0);
	}

	@Override
	public void caseAExternalDefinition(AExternalDefinition node) {
		// TODO Auto-generated method stub
		super.caseAExternalDefinition(node);
	}

	@Override
	public void caseAFunctionDeclaration(AFunctionDeclaration arg0) {
		// TODO Auto-generated method stub
		super.caseAFunctionDeclaration(arg0);
	}

	@Override
	public void caseAImplicitFunctionFunctionDefinition(
			AImplicitFunctionFunctionDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAImplicitFunctionFunctionDefinition(arg0);
	}

	@Override
	public void caseAImplicitOperationOperationDefinition(
			AImplicitOperationOperationDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAImplicitOperationOperationDefinition(arg0);
	}

	@Override
	public void caseAInheritedDefinition(AInheritedDefinition node) {
		// TODO Auto-generated method stub
		super.caseAInheritedDefinition(node);
	}

	@Override
	public void caseALocalDefinition(ALocalDefinition node) {
		// TODO Auto-generated method stub
		super.caseALocalDefinition(node);
	}

	@Override
	public void caseAOperationDeclaration(AOperationDeclaration arg0) {
		// TODO Auto-generated method stub
		super.caseAOperationDeclaration(arg0);
	}

	@Override
	public void caseASingleTypeDeclaration(ASingleTypeDeclaration node) {
		// TODO Auto-generated method stub
		super.caseASingleTypeDeclaration(node);
	}

	@Override
	public void caseAStateDeclaration(AStateDeclaration node) {
		// TODO Auto-generated method stub
		super.caseAStateDeclaration(node);
	}

	@Override
	public void caseAStateDefinition(AStateDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAStateDefinition(arg0);
	}

	@Override
	public void caseATypeDeclaration(ATypeDeclaration arg0) {
		// TODO Auto-generated method stub
		super.caseATypeDeclaration(arg0);
	}

	@Override
	public void caseATypeDefinition(ATypeDefinition node) {
		// TODO Auto-generated method stub
		super.caseATypeDefinition(node);
	}

	@Override
	public void caseAUntypedDefinition(AUntypedDefinition node) {
		// TODO Auto-generated method stub
		super.caseAUntypedDefinition(node);
	}

	@Override
	public void caseAValueDeclaration(AValueDeclaration arg0) {
		// TODO Auto-generated method stub
		super.caseAValueDeclaration(arg0);
	}

	@Override
	public void caseAValueDefinition(AValueDefinition arg0) {
		// TODO Auto-generated method stub
		super.caseAValueDefinition(arg0);
	}

	
	
}
