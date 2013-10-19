package eu.compassresearch.core.analysis.modelchecker.ast.process;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;

public class MCAActionProcess implements MCPProcess {

	private LinkedList<MCPCMLDefinition> definitionParagraphs = new LinkedList<MCPCMLDefinition>();
	private MCPAction action;
	
	
	public MCAActionProcess(LinkedList<MCPCMLDefinition> definitionParagraphs,
			MCPAction action) {
		super();
		this.definitionParagraphs = definitionParagraphs;
		this.action = action;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public LinkedList<MCPCMLDefinition> getDefinitionParagraphs() {
		return definitionParagraphs;
	}


	public void setDefinitionParagraphs(
			LinkedList<MCPCMLDefinition> definitionParagraphs) {
		this.definitionParagraphs = definitionParagraphs;
	}


	public MCPAction getAction() {
		return action;
	}


	public void setAction(MCPAction action) {
		this.action = action;
	}

	
}
