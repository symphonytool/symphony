package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class PartialModelBuilder {
	

	public String buildPartialModelContent(){
		
		StringBuilder content = new StringBuilder();
		String option = MCNode.DEFAULT;
		
		//generateLieInFacts(content,option);

		//generateChannels(content,option);
		
		generateClocks(content,option);
		
		generateGivenProc(content,option);
		
		generateParameterFacts(content,option);
		
		return content.toString();
	}
	
	private void generateParameterFacts(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (ParameterFact paramFact : context.parameterFacts) {
			content.append(paramFact.toFormula(option) + "\n");
		}
	}
	
	private void generateClocks(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (int i = context.maxClock; i > 0 ; i--) {
			content.append("  Clock(" + i + ")\n");
		}
	}
	private void generateGivenProc(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCAProcessDefinition mainProc = context.getMainProcessDefinition();
		
		content.append("  GivenProc(");
		content.append("\"");
		content.append(mainProc.getName() + "MAIN");
		content.append("\"");
		content.append(")");
		content.append("\n");

	}
}
