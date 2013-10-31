package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCChannel;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAChannelType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCAChannelNameDefinition implements MCPCMLDefinition {

	private String name;
	private MCATypeSingleDeclaration typeSingle;
	//private LinkedList<MCChannel> channelDefs;

	public MCAChannelNameDefinition(String name, MCATypeSingleDeclaration typeSingle) {
		this.name = name;
		this.typeSingle = typeSingle;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		//the default option for generating channels is generic (with underscore)
		//channel facts generation makes sense only for typed channels
		result.append("Channel(0,\"");
		result.append(this.name);
		result.append("\"");
		result.append(",");
		result.append("_");
		result.append(")");
		return result.toString();
	}

	public boolean isTyped(){
		boolean result = false;
		MCPCMLType realType = this.getSingleType().getType();
		if(realType instanceof MCAChannelType){
			realType = ((MCAChannelType) realType).getType();
		}
		result = realType != null;
				
		return result;
	}
	
	public MCATypeSingleDeclaration getSingleType() {
		return typeSingle;
	}

	public void setSingleType(MCATypeSingleDeclaration singleType) {
		this.typeSingle = singleType;
	}

	public MCATypeSingleDeclaration getTypeSingle() {
		return typeSingle;
	}

	public void setTypeSingle(MCATypeSingleDeclaration typeSingle) {
		this.typeSingle = typeSingle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
