package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class NonEmptySet implements Set {

	private MCPCMLExp head;
	private Set tail;
	
	
	
	public NonEmptySet(MCPCMLExp head, Set tail) {
		this.head = head;
		this.tail = tail;
	}



	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		result.append("NonEmptySet(");
		result.append(this.head.toFormula(option));
		result.append(",");
		result.append(this.tail.toFormula(option));
		result.append(")");
		/*
		LinkedList<MCPCMLExp> allElements = new LinkedList<MCPCMLExp>(); 
		 
		getAllElements(allElements, this);
		for (Iterator<MCPCMLExp> iterator = allElements.iterator(); iterator.hasNext();) {
			result.append("NonEmptySet(");
			MCPCMLExp element = (MCPCMLExp) iterator.next();
			result.append(element.toFormula(option));
			if(iterator.hasNext()){
				result.append(",");
			}
		}
		result.append(",emptySet");
		for (int i = 0; i < allElements.size(); i++) {
			result.append(")");
		}
		*/
		
		return result.toString();
	}

	private void getAllElements(LinkedList<MCPCMLExp> allElements, Set set){
		if(set instanceof NonEmptySet){
			allElements.add(((NonEmptySet) set).getHead());
			getAllElements(allElements, ((NonEmptySet) set).getTail());
		}
	}

	@Override
	public Set addElement(MCPCMLExp elem) {
		if(!head.equals(elem)){
			tail = tail.addElement(elem);
		} 
		return this;
	}

	
	public MCPCMLExp getHead() {
		return head;
	}



	public void setHead(MCPCMLExp head) {
		this.head = head;
	}



	public Set getTail() {
		return tail;
	}



	public void setTail(Set tail) {
		this.tail = tail;
	}



	
	
}
