package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASBinaryExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCASetEnumSetExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class SetFact {
	private MCASBinaryExp setExpression;
	
	
	public SetFact(MCASBinaryExp setExpression) {
		super();
		this.setExpression = setExpression;
	}


	public String toFormula(String option){
		StringBuilder result = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		for (MCASBinaryExp setExp : context.setExpressioFacts) {
			EmptySet resultSet = new EmptySet();
			MCPCMLExp set = setExp.getRight();
			if (set instanceof MCASetEnumSetExp){
				LinkedList<MCPCMLExp> members = ((MCASetEnumSetExp) set).getMembers();
				if(members.size() > 0){
					resultSet.addElement(members.removeFirst());
					EmptySet tail = new EmptySet();
					if(members.size() > 0){
						for (MCPCMLExp mcpcmlExp : members) {
							tail.addElement(mcpcmlExp);
						}
					}else{
						
					}
				}
			}
		}
		return result.toString();
	}


	public MCASBinaryExp getSetExpression() {
		return setExpression;
	}


	public void setSetExpression(MCASBinaryExp setExpression) {
		this.setExpression = setExpression;
	}
	
	
}
