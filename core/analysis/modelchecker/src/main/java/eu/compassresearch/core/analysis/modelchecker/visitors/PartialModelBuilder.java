package eu.compassresearch.core.analysis.modelchecker.visitors;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCLieInFact;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PartialModel;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelNameDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;

public class PartialModelBuilder {
	

	public void buildPartialModelContent(PartialModel model){
		//TODO
		StringBuilder content = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		String option = MCNode.DEFAULT;
		
		generateLieInFacts(content,option);

		generateChannels(content,option);
		
		generateGivenProc(content,option);
	}
	
	private void generateLieInFacts(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		if(context.lieIn.size() != 0){
			for (MCLieInFact lieIn : context.lieIn) {
				content.append(lieIn.toFormula(option) + "\n");
			}
		}
	}
	
	private void generateChannels(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		if(context.channelDefs.size() != 0){
			for (MCAChannelNameDefinition chanDef : context.channelDefs) {
				if(chanDef.isTyped()){
					content.append(chanDef.toFormula(option) + "\n");
				}
			}
		}
	}
	
	private void generateGivenProc(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCAProcessDefinition mainProc = context.mainProcess;
		
		content.append("GivenProc(");
		content.append("\"");
		content.append(mainProc.getName());
		content.append("\"");
		content.append(")");
		content.append("\n");

	}
}
