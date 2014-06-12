package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class PartialModelBuilder {
	

	public String buildPartialModelContent(){
		
		StringBuilder content = new StringBuilder();
		String option = MCNode.DEFAULT;
		
		generateLieInFacts(content,option);

		//generateChannels(content,option);
		
		generateClocks(content,option);
		
		generateGivenProc(content,option);
		
		return content.toString();
	}
	
	private void generateLieInFacts(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCLieInFact lieIn : context.lieIn) {
			//content.append(lieIn.toFormula(option) + "\n");
			lieIn.prepareLieInFact();
		}
		for (MCLieInFact lieIn :  context.realLieInFacts) {
			String lieInFactStr = lieIn.toFormula(option);
			if(content.indexOf(lieInFactStr) == -1){
				content.append(lieIn.toFormula(option) + "\n");
			}
		}
	}
	
	private void generateChannels(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCAChannelDefinition chanDef : context.channelDefs) {
			content.append(chanDef.toFormula(option));
			if(chanDef.isTyped()){
				content.append("\n");
			}
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
		content.append(mainProc.getName());
		content.append("\"");
		content.append(")");
		content.append("\n");

	}
}
