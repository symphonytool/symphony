package eu.compassresearch.core.interpreter;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.ACmlClassDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AImplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.values.ActionValue;
import eu.compassresearch.core.interpreter.api.values.AnyValue;
import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;

@SuppressWarnings("serial")
class CmlDefinitionVisitor extends
		QuestionAnswerCMLAdaptor<Context, NameValuePairList> {

	private QuestionAnswerCMLAdaptor<Context, Value> cmlExpressionVisitor = new CmlExpressionVisitor(); 

	/*
	 * Private helper methods
	 */
	
	/**
	 * Generic method for handling a list of definitions
	 * @param defs
	 * @param location
	 * @param question
	 * @return
	 * @throws AnalysisException
	 */
	private NameValuePairList definitionListHelper(List<? extends PDefinition> defs, ILexLocation location, Context question) throws AnalysisException
	{
		NameValuePairList vpl = new NameValuePairList();
		Context defEvalContext = CmlContextFactory.newContext(location, "Definition Eval context", question);
		
		for(PDefinition def : defs)
		{
			NameValuePairList tmpNvl = def.apply(this,defEvalContext);  
			defEvalContext.putList(tmpNvl);
			vpl.addAll(tmpNvl);
		}
		
		return vpl;
	}
	
//	/*
//	 * Visitor parametrisation case methods
//	 */
//	
//	@Override
//	public NameValuePairList caseAValParametrisation(AValParametrisation node,
//			Context question) throws AnalysisException {
//		// TODO Auto-generated method stub
//		return super.caseAValParametrisation(node, question);
//	}
//	
//	@Override
//	public NameValuePairList caseAVresParametrisation(
//			AVresParametrisation node, Context question)
//			throws AnalysisException {
//		// TODO Auto-generated method stub
//		return super.caseAVresParametrisation(node, question);
//	}
//	
//	@Override
//	public NameValuePairList caseAResParametrisation(AResParametrisation node,
//			Context question) throws AnalysisException {
//		// TODO Auto-generated method stub
//		return super.caseAResParametrisation(node, question);
//	}
	
	/*
	 * Visitor Definition case methods
	 */
	
	@Override
    public NameValuePairList caseAProcessDefinition(AProcessDefinition node,Context question)
        throws AnalysisException
      {
		NameValuePairList vpl = new NameValuePairList();
		
		vpl.add(new NameValuePair(node.getName(), 
        		new ProcessObjectValue(node,null)));
		
		return vpl;
      }
	
	@Override
	public NameValuePairList caseAStateDefinition(AStateDefinition node,
			Context question) throws AnalysisException {
		
		NameValuePairList vpl = new NameValuePairList();
		
		for(PDefinition def : node.getStateDefs())
		{
			vpl.addAll(def.apply(this,question));
		}
		
		return vpl;
	}
	
	@Override
	public NameValuePairList caseAClassInvariantDefinition(
			AClassInvariantDefinition node, Context question)
			throws AnalysisException {
		return super.caseAClassInvariantDefinition(node, question);
	}
	
	@Override
	public NameValuePairList caseAActionsDefinition(AActionsDefinition node,
			Context question) throws AnalysisException {

		return definitionListHelper(node.getActions(),node.getLocation(),question);
				
	}
	
	@Override
	public NameValuePairList caseAActionDefinition(AActionDefinition node,
			Context question) throws AnalysisException {

		NameValuePairList vpl = new NameValuePairList();
		
		vpl.add(new NameValuePair(node.getName(), new ActionValue(node)));
		
		return vpl;
	}
	
	@Override
	public NameValuePairList caseACmlClassDefinition(ACmlClassDefinition node,
			Context question) throws AnalysisException {

		
		return new NameValuePairList();
	}
	
	/*
	 * Function  
	 */
	
	@Override
	public NameValuePairList caseAFunctionsDefinition(AFunctionsDefinition node,
			Context question) throws AnalysisException {
			
		return definitionListHelper(node.getFunctionDefinitions(),node.getLocation(),question);
	}
			
	@Override
	public NameValuePairList caseAExplicitFunctionDefinition(
			AExplicitFunctionDefinition node, Context question)
			throws AnalysisException {

		NameValuePairList vpl = new NameValuePairList();
		if(node.parent() instanceof ATypeDefinition)
			node.setIsTypeInvariant(true);
		else
			node.setIsTypeInvariant(false);
		
		//make the pre and post functions
		FunctionValue postFuncValue = null;
		FunctionValue preFuncValue = null;
	
		if(node.getPredef() != null)
			preFuncValue =  new FunctionValue(node.getPredef(),null,null,null);
		if(node.getPostdef() != null)
			postFuncValue = new FunctionValue(node.getPostdef(),null,null,null);	
				
		FunctionValue funcValue = new FunctionValue(node,preFuncValue ,postFuncValue,null);
		
		vpl.add(new NameValuePair(node.getName(),funcValue));
		
		return vpl;
	}
	
	@Override
	public NameValuePairList caseAImplicitFunctionDefinition(
			AImplicitFunctionDefinition node, Context question)
			throws AnalysisException {
		
		//make the pre and post functions
		FunctionValue postFuncValue = null;
		FunctionValue preFuncValue = null;

		if(node.getPredef() != null)
			preFuncValue =  new FunctionValue(node.getPredef(),null,null,null);
		if(node.getPostdef() != null)
			postFuncValue = new FunctionValue(node.getPostdef(),null,null,null);
		
		FunctionValue funcValue = new FunctionValue(node,preFuncValue,postFuncValue,null);
		NameValuePairList vpl = new NameValuePairList();
		vpl.add(new NameValuePair(node.getName(),funcValue));
		
		return vpl;
//		throw new AnalysisException("The function " + node.toString() + 
//				" is implicit. This is not supported by the simulator at the moment");
	}
	
	/*
	 * Operations
	 */
	
	@Override
	public NameValuePairList caseAOperationsDefinition(AOperationsDefinition node,
			Context question) throws AnalysisException {

		return definitionListHelper(node.getOperations(),node.getLocation(),question);
	}
	
	@Override
	public NameValuePairList caseAExplicitCmlOperationDefinition(
			AExplicitCmlOperationDefinition node, Context question)
			throws AnalysisException {
	
		NameValuePairList vpl = new NameValuePairList();
		
		vpl.add(new NameValuePair(node.getName(), CmlValueFactory.createOperationValue(node,question)));
		
		return vpl;
	}
	
	@Override
	public NameValuePairList caseAImplicitCmlOperationDefinition(
			AImplicitCmlOperationDefinition node, Context question)
			throws AnalysisException {
		//throw new CmlInterpreterException(node,"Implicit operations cannot be interpreted. This feature might be implemented later");
		return new NameValuePairList();
	}

	@Override
	public NameValuePairList caseAAssignmentDefinition(AAssignmentDefinition node,
			Context question) throws AnalysisException {
		
		NameValuePairList vpl = new NameValuePairList();
		
		Value expValue = null;
		if(node.getExpression() != null)
			expValue = node.getExpression().apply(cmlExpressionVisitor,question);
		else
			expValue = new UndefinedValue();
		
		vpl.add(new NameValuePair(node.getName(), expValue));
		
		return vpl;
	}
	
	@Override
    public NameValuePairList caseAChannelsDefinition(AChannelsDefinition node,
			Context question) throws AnalysisException
    {
		NameValuePairList vpl = new NameValuePairList();
		
    	for (AChannelNameDefinition cnd : node.getChannelNameDeclarations())
    	{
    		for (ILexIdentifierToken channelName : cnd.getSingleType().getIdentifiers())
    		{
    			ILexNameToken name = new LexNameToken("|CHANNELS|", channelName);
    			vpl.add(new NameValuePair(name, new CMLChannelValue(cnd.getSingleType().getType(),name)));
    		}
    	}
    	
    	return vpl;
    }
	
	/*
	 * Types
	 */
	
	@Override
	public NameValuePairList caseATypesDefinition(ATypesDefinition node,
			Context question) throws AnalysisException {

		return definitionListHelper(node.getTypes(),node.getLocation(),question);
	}
	
	@Override
	public NameValuePairList caseATypeDefinition(ATypeDefinition node,
			Context question) throws AnalysisException {
	
		NameValuePairList vpl = new NameValuePairList();
		if(node.getInvdef() != null)
			vpl.addAll(node.getInvdef().apply(this,question));

		return vpl;
	}
	
	/*
	 * Values
	 */
	
	@Override
	public NameValuePairList caseAValuesDefinition(AValuesDefinition node,
			Context question) throws AnalysisException {

		return definitionListHelper(node.getValueDefinitions(),node.getLocation(),question);
	}
	
	@Override
	public NameValuePairList caseAValueDefinition(AValueDefinition node,
			Context question) throws AnalysisException {

		NameValuePairList vpl = new NameValuePairList();
		
		Value val = node.getExpression().apply(cmlExpressionVisitor,question);
		
		if(node.getPattern() instanceof AIdentifierPattern)
			vpl.add(new NameValuePair(((AIdentifierPattern)node.getPattern()).getName(), val));
		
		return vpl;
	}
	
	/*
	 * Replication declarations
	 */
	
	@Override
	public NameValuePairList caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node, Context question)
			throws AnalysisException {
		
		NameValuePairList vpl = new NameValuePairList();
		
		Value value = node.getExpression().apply(cmlExpressionVisitor, question);
		for(ILexIdentifierToken id : node.getIdentifiers())
			vpl.add(new NameValuePair(new LexNameToken("", id.clone()), value));
		
		return vpl;
	}
	
	@Override
	public NameValuePairList caseATypeSingleDeclaration(
			ATypeSingleDeclaration node, Context question)
			throws AnalysisException {
		
		NameValuePairList vpl = new NameValuePairList();
		
		Value value = new AnyValue(node.getType());
		for(ILexIdentifierToken id : node.getIdentifiers())
			vpl.add(new NameValuePair(new LexNameToken("", id.clone()), value));
		
		return vpl;
	}
}

