package eu.compassresearch.core.analysis.modelchecker.ast.pattern;

import java.util.LinkedList;

import org.overture.ast.patterns.ATypeMultipleBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PMultipleBindBase;

import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCATypeMultipleBind implements MCPMultipleBind {

	private LinkedList<MCPCMLPattern> pList;
	private MCPCMLType type;
	
	
	public MCATypeMultipleBind(LinkedList<MCPCMLPattern> pList, MCPCMLType type) {
		this.pList = pList;
		this.type = type;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public LinkedList<MCPCMLPattern> getpList() {
		return pList;
	}


	public void setpList(LinkedList<MCPCMLPattern> pList) {
		this.pList = pList;
	}


	public MCPCMLType getType() {
		return type;
	}


	public void setType(MCPCMLType type) {
		this.type = type;
	}
	
	

}
