package eu.compassresearch.core.analysis.modelchecker.ast.actions;


public class MCABlockStatementAction implements MCPAction {

	private MCADeclareStatementAction declareStatement;
	private MCPAction action;
	
	
	public MCABlockStatementAction(MCADeclareStatementAction declareStatement,
			MCPAction action) {
		super();
		this.declareStatement = declareStatement;
		this.action = action;
	}

	@Override
	public String toFormula(String option) {
		StringBuffer result = new StringBuffer();
		//if(node.getDeclareStatement() != null){
		//	node.getDeclareStatement().apply(rootVisitor, question);
		//}
		result.append(this.action.toFormula(option));
		
		if(declareStatement != null){
			result.append(")");
		}
		
		return result.toString();

	}

	public MCADeclareStatementAction getDeclareStatement() {
		return declareStatement;
	}

	public void setDeclareStatement(MCADeclareStatementAction declareStatement) {
		this.declareStatement = declareStatement;
	}

	public MCPAction getAction() {
		return action;
	}

	public void setAction(MCPAction action) {
		this.action = action;
	}

	
	
}
