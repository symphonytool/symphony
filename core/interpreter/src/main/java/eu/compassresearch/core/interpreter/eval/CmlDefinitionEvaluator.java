package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.values.CmlValueFactory;

@SuppressWarnings("serial")
public class CmlDefinitionEvaluator extends
		QuestionAnswerCMLAdaptor<Context, NameValuePairList> {

	private CmlValueEvaluator parentInterpreter = new CmlValueEvaluator(); 

	@Override
    public NameValuePairList caseAProcessDefinition(AProcessDefinition node,Context question)
        throws AnalysisException
      {
		NameValuePairList vpl = new NameValuePairList();
		
		vpl.add(new NameValuePair(node.getName(), 
        		CmlValueFactory.createProcessObjectValue(node,null)));
		
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
	public NameValuePairList caseAActionsDefinition(AActionsDefinition node,
			Context question) throws AnalysisException {

		NameValuePairList vpl = new NameValuePairList();
		
		for(AActionDefinition actionDef : node.getActions())
		{
			vpl.addAll(actionDef.apply(this,question));
		}
		
		return vpl;
	}
	
	@Override
	public NameValuePairList caseAActionDefinition(AActionDefinition node,
			Context question) throws AnalysisException {

		NameValuePairList vpl = new NameValuePairList();
		
		vpl.add(new NameValuePair(node.getName(), 
				CmlValueFactory.createActionValue(node)));
		
		return vpl;
	}
	
	@Override
	public NameValuePairList caseAFunctionsDefinition(AFunctionsDefinition node,
			Context question) throws AnalysisException {

		NameValuePairList vpl = new NameValuePairList();
		
		for(PDefinition funcDefs : node.getFunctionDefinitions())
		{
			vpl.addAll(funcDefs.apply(this,question));
		}
		
		return vpl;
	}
	
	@Override
	public NameValuePairList caseAExplicitFunctionDefinition(
			AExplicitFunctionDefinition node, Context question)
			throws AnalysisException {

		NameValuePairList vpl = new NameValuePairList();
		
		node.setIsTypeInvariant(false);
		FunctionValue funcValue = new FunctionValue(node,null ,null,null);
		
		vpl.add(new NameValuePair(node.getName(),funcValue));
		
		return vpl;
	}
	
	
	@Override
	public NameValuePairList caseAOperationsDefinition(AOperationsDefinition node,
			Context question) throws AnalysisException {

		NameValuePairList vpl = new NameValuePairList();
		
		for(SCmlOperationDefinition operationDef : node.getOperations())
		{
			vpl.addAll(operationDef.apply(this,question));
		}
		
		return vpl;
	}
	
	@Override
	public NameValuePairList caseAExplicitCmlOperationDefinition(
			AExplicitCmlOperationDefinition node, Context question)
			throws AnalysisException {
	
		NameValuePairList vpl = new NameValuePairList();
		
		vpl.add(new NameValuePair(node.getName(), CmlValueFactory.createOperationValue(node)));
		
		return vpl;
	}

	@Override
	public NameValuePairList caseAAssignmentDefinition(AAssignmentDefinition node,
			Context question) throws AnalysisException {
		
		NameValuePairList vpl = new NameValuePairList();
		
		Value expValue = null;
		if(node.getExpression() != null)
			expValue = node.getExpression().apply(parentInterpreter,question);
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
    		for (LexIdentifierToken channelName : cnd.getSingleType().getIdentifiers())
    		{
    			LexNameToken name = new LexNameToken("|CHANNELS|", channelName);
    			vpl.add(new NameValuePair(name, new CMLChannelValue(cnd.getSingleType().getType(),name)));
    		}
    	}
    	
    	return vpl;
    }
	
	@Override
	public NameValuePairList caseATypesDefinition(ATypesDefinition node,
			Context question) throws AnalysisException {

		NameValuePairList vpl = new NameValuePairList();
		
		for (ATypeDefinition typeDef : node.getTypes())
			vpl.addAll(typeDef.apply(this,question));
		
		return vpl;
	}
	
	@Override
	public NameValuePairList caseATypeDefinition(ATypeDefinition node,
			Context question) throws AnalysisException {
	
		NameValuePairList vpl = new NameValuePairList();
		if(node.getInvdef() != null)
			vpl.addAll(node.getInvdef().apply(this,question));

		return vpl;
	}

}

