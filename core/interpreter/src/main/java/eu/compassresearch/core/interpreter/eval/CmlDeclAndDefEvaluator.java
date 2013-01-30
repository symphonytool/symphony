package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePair;
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
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.core.interpreter.runtime.CmlContext;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.values.CmlValueFactory;

@SuppressWarnings("serial")
public class CmlDeclAndDefEvaluator extends
		QuestionAnswerCMLAdaptor<CmlContext, Value> {

	private CmlEvaluator parentInterpreter; 

	public CmlDeclAndDefEvaluator(CmlEvaluator parentInterpreter)
	{
		this.parentInterpreter = parentInterpreter;
	}
	
	@Override
    public Value caseAProcessDefinition(AProcessDefinition node,CmlContext question)
        throws AnalysisException
      {
		question.putNew(new NameValuePair(node.getName(), 
        		CmlValueFactory.createProcessObjectValue(node)));
		
		return null;
      }
	
	@Override
	public Value caseAStateDefinition(AStateDefinition node,
			CmlContext question) throws AnalysisException {
		
		for(PDefinition def : node.getStateDefs())
		{
			def.apply(this,question);
		}
		
		return null;
	}
	
	@Override
	public Value caseAActionsDefinition(AActionsDefinition node,
			CmlContext question) throws AnalysisException {

		for(AActionDefinition actionDef : node.getActions())
		{
			actionDef.apply(this,question);
		}
		
		return null;
	}
	
	@Override
	public Value caseAActionDefinition(AActionDefinition node,
			CmlContext question) throws AnalysisException {

		question.putNew(new NameValuePair(node.getName(), 
				CmlValueFactory.createActionValue(node)));
		
		return null;
	}
	
	@Override
	public Value caseAFunctionsDefinition(AFunctionsDefinition node,
			CmlContext question) throws AnalysisException {

		
		for(PDefinition funcDefs : node.getFunctionDefinitions())
		{
			funcDefs.apply(this,question);
		}
		
		return null;
	}
	
	@Override
	public Value caseAExplicitFunctionDefinition(
			AExplicitFunctionDefinition node, CmlContext question)
			throws AnalysisException {

		node.setIsTypeInvariant(false);
		FunctionValue funcValue = new FunctionValue(node,null ,null,null);
		
		question.putNew(new NameValuePair(node.getName(),funcValue));
		
		
		return null;
	}
	
	
	@Override
	public Value caseAOperationsDefinition(AOperationsDefinition node,
			CmlContext question) throws AnalysisException {

		for(SCmlOperationDefinition operationDef : node.getOperations())
		{
			operationDef.apply(this,question);
		}
		
		return null;
	}
	
	@Override
	public Value caseAExplicitCmlOperationDefinition(
			AExplicitCmlOperationDefinition node, CmlContext question)
			throws AnalysisException {
	
		question.putNew(new NameValuePair(node.getName(), CmlValueFactory.createOperationValue(node)));
		
		return null;
	}

	@Override
	public Value caseAAssignmentDefinition(AAssignmentDefinition node,
			CmlContext question) throws AnalysisException {
		
		Value expValue = null;
		if(node.getExpression() != null)
			expValue = node.getExpression().apply(parentInterpreter,question);
		else
			expValue = new UndefinedValue();
		
		question.put(node.getName(), expValue);
		
		return null;
	}
	
	@Override
    public Value caseAChannelsDefinition(AChannelsDefinition node,
			CmlContext question) throws AnalysisException
    {
    	for (AChannelNameDefinition cnd : node.getChannelNameDeclarations())
    	{
    		for (LexIdentifierToken channelName : cnd.getSingleType().getIdentifiers())
    		{
    			LexNameToken name = new LexNameToken("|CHANNELS|", channelName);
    			question.putNew(new NameValuePair(name, new CMLChannelValue(cnd.getSingleType().getType(),name)));
    		}
    	}
    	
    	return null;
    }

}

