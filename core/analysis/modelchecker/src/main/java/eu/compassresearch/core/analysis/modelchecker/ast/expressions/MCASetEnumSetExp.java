package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.EmptySet;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PatternValue;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.Set;

public class MCASetEnumSetExp implements MCPCMLExp {

	private LinkedList<MCPCMLExp> members;

	
	public MCASetEnumSetExp(LinkedList<MCPCMLExp> members) {
		super();
		this.members = members;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();

		if(option.equals(MCNode.EXTENSION)){
			result.append("{");
			 
			for (Iterator<MCPCMLExp> iterator = members.iterator(); iterator.hasNext();) {
				MCPCMLExp member = (MCPCMLExp) iterator.next();
				result.append(member.toFormula(option));
				if(iterator.hasNext()){
					result.append(",");
				}
			}
			result.append("}");	
		}else{
		//set of values can be converted explicitely or in terms of formula sets (embedding).
			if(members.size() == 0){
				result.append("emptySet");
			} else{
				Set set = new EmptySet(); 
				for (MCPCMLExp elem : members) {
					set = set.addElement(elem);
				}
				result.append(set.toFormula(option));
			}
		}
		
		
		
		return result.toString();
	}

	

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("{");
		 
		for (Iterator<MCPCMLExp> iterator = members.iterator(); iterator.hasNext();) {
			MCPCMLExp member = (MCPCMLExp) iterator.next();
			result.append(member.toString());
			if(iterator.hasNext()){
				result.append(",");
			}
		}
		result.append("}");
		
		return result.toString();
	}


	public LinkedList<MCPCMLExp> getMembers() {
		return members;
	}


	public void setMembers(LinkedList<MCPCMLExp> members) {
		this.members = members;
	}


	@Override
	public MCPCMLExp copy() {
		LinkedList<MCPCMLExp> membersCopy = new LinkedList<MCPCMLExp>();
		for (MCPCMLExp member : members) {
			membersCopy.add(member.copy());
		}
		return new MCASetEnumSetExp(membersCopy);
	}


	@Override
	public void replacePatternWithValue(LinkedList<PatternValue> mapping) {
		for (MCPCMLExp member : members) {
			member.replacePatternWithValue(mapping);
		}
	}

	
}
