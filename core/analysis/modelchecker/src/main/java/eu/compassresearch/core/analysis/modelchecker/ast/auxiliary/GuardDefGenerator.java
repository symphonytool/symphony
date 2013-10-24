package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAGuardedAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAIfStatementAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCAEqualsBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class GuardDefGenerator {

	public static LinkedList<MCGuardDef> generateGuardDefs(MCPCMLExp guardExp, int counterId, MCAGuardedAction parentAction){
		LinkedList<MCGuardDef> result = new LinkedList<MCGuardDef>();
		IExpressionEvaluator expEvaluator = ExpressionEvaluator.getInstance(); 
		if(expEvaluator.canEvaluate(guardExp)){
			MCGuardDef guardDef = new MCPosGuardDef(counterId, guardExp, parentAction);
			if(!expEvaluator.evaluate(guardExp)){
				guardDef = new MCNegGuardDef(counterId, guardExp, parentAction);
			}
			result.add(guardDef);
		}else{
			result.add(new MCPosGuardDef(counterId, guardExp, parentAction));
			result.add(new MCNegGuardDef(counterId, guardExp, parentAction));
		}
		
		return result;
	}

	public static LinkedList<MCGuardDef> generateGuardDefs(MCPCMLExp guardExp, int counterId, MCAIfStatementAction parentAction){
		LinkedList<MCGuardDef> result = new LinkedList<MCGuardDef>();
		IExpressionEvaluator expEvaluator = ExpressionEvaluator.getInstance(); 
		if(expEvaluator.canEvaluate(guardExp)){
			MCGuardDef guardDef = new MCPosGuardDef(counterId, guardExp, parentAction);
			if(!expEvaluator.evaluate(guardExp)){
				guardDef = new MCNegGuardDef(counterId, guardExp, parentAction);
			}
			result.add(guardDef);
		}else{
			result.add(new MCPosGuardDef(counterId, guardExp, parentAction));
			result.add(new MCNegGuardDef(counterId, guardExp, parentAction));
		}
		
		return result;
	}
	
}
