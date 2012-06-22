package eu.compassresearch.core.typechecker;

import org.overture.ast.actions.ADeclareStatementDeclareStatement;
import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.intf.IQuestionAnswer;
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
import org.overture.ast.declarations.PDeclaration;
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
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.program.ASourcefileSourcefile;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.PType;
import org.overturetool.vdmj.typechecker.NameScope;

import eu.compassresearch.core.typechecker.CmlTypeChecker.CMLTypeError;

public class TCDeclAndDefVisitor extends QuestionAnswerAdaptor<TypeCheckInfo, PType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3595335302756624612L;
	
	private CmlTypeChecker parentChecker;
	




	@Override
	public PType caseAClassClassDefinition(AClassClassDefinition node,
			TypeCheckInfo question) {
		
		
		PType res = null;
		
		
		
		return res;
	}


	
	@Override
	public PType caseAClassDeclaration(AClassDeclaration node,
			TypeCheckInfo question) {

		
		question.currentScope = NameScope.CLASSNAME;
		ASourcefileSourcefile s;

		AClassbodyDefinition def = node.getClassBody();
		PType type = new AClassType(def.getLocation(), true, node.getIdentifier().getClassName());
		def.setType(type);
		question.env.put(node, type);
		// Build local environment
		for( PDeclaration decl : def.getDeclarations())
		{

			PType t = decl.apply(this,question);
			question.env.put(decl, t);
		}

		return type;
	}

	
	public TCDeclAndDefVisitor(CmlTypeChecker parent)
	{
		this.parentChecker = parent;
	}

	@Override
	public PType caseAValueDeclaration(AValueDeclaration node,
			TypeCheckInfo question) {

		for(PDefinition def : node.getDefinitions())
		{
			PType type = def.apply(this,question);
			question.env.put(node, type);
		}
		
		
		return super.caseAValueDeclaration(node, question);
	}

	@Override
	public PType caseAValueDefinition(AValueDefinition node,
			TypeCheckInfo question) {
		System.out.println("Look I get a value definition.");
		PType expType = node.getExpression().apply(parentChecker, question);
		if (!expType.equals(node.getType())){
			// TODO: Do type hierarchy checking
			parentChecker.getErrors().add(new CMLTypeError(node.getLocation(), "Type mismatch, expression has type: "+expType+" but the declared type is: "+node.getType()));
		}
		node.setExpType(expType);
		return expType;
	}
	
	
}