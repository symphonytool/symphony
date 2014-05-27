package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;


public class BBinding implements Binding {
	protected String procName;
	protected SingleBind head;
	protected Binding tail;
	
	public BBinding(String procName, SingleBind head, Binding tail) {
		super();
		this.procName = procName;
		this.head = head;
		this.tail = tail;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		//result.append(head.toString());
		LinkedList<SingleBind> bindList = new LinkedList<SingleBind>(); 
		getSingleBindings(bindList, this);
		for (Iterator<SingleBind> iterator = bindList.iterator(); iterator.hasNext();) {
			SingleBind singleBind = (SingleBind) iterator.next();
			result.append(singleBind.toString());
			if(iterator.hasNext()){
				result.append(",");
			}
		}
		result.append("}");
		
		return result.toString();
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		LinkedList<SingleBind> bindList = new LinkedList<SingleBind>(); 
		getSingleBindings(bindList, this);
		for (Iterator<SingleBind> iterator = bindList.iterator(); iterator.hasNext();) {
			result.append("BBinding(");
			SingleBind singleBind = (SingleBind) iterator.next();
			result.append(singleBind.toFormula(option));
			if(iterator.hasNext()){
				result.append(",");
			}
		}
		result.append(",nBind");
		for (int i = 0; i < bindList.size(); i++) {
			result.append(")");
		}
		return result.toString();
	}

	@Override
	public boolean containsVariable(String name){
		boolean result = false;
		if(this.head.getVariableName().equals(name)){
			result = true;
		}else{
			result = this.tail.containsVariable(name);
		}
		return result;
	}
	
	@Override
	public BBinding copy(){
		return new BBinding(this.procName,this.head.copy(),this.tail.copy());
	}
	public Binding addBinding(String procName, String varName, MCPCMLExp varValue){
		Binding result = this;
		
		if(head.getVariableName().equals(varName)){
			head.setVariableValue(varValue);
		} else{
			tail = tail.addBinding(procName, varName, varValue);
		}
		return result;
	}
	public void updateBinding(String varName, MCPCMLExp varValue){
		if(head.getVariableName().equals(varName)){
			head.setVariableValue(varValue);
		} else{
			tail.updateBinding(varName, varValue);
		}
	}
	public Binding deleteBinding(String varName){
		Binding result = this;
		
		if(head.getVariableName().equals(varName)){
			result = this.tail;
		} else{
			tail = tail.deleteBinding(varName);
		}
		return result;
	}
	
	
	private void getSingleBindings(LinkedList<SingleBind> bindList, Binding binding){
		if(binding instanceof BBinding){
			bindList.add(((BBinding) binding).getHead());
			getSingleBindings(bindList, ((BBinding) binding).getTail());
		}
	}
	@Override
	public StringBuilder generateAllFetchFacts(int number){
		StringBuilder result = new StringBuilder();
		
		LinkedList<SingleBind> bindList = new LinkedList<SingleBind>(); 
		getSingleBindings(bindList, this);
		for (Iterator<SingleBind> iterator = bindList.iterator(); iterator.hasNext();) {
			SingleBind singleBind = (SingleBind) iterator.next();
			result.append("fetch(\"" + singleBind.variableName + "\"," + this.toFormula(MCNode.NAMED) + ","+ singleBind.variableValue.toFormula(MCNode.NAMED) + ")\n");
		}
		
		return result;
	}
	
	public StringBuilder generateAllUpdFacts(int number){
		StringBuilder result = new StringBuilder();
		
		//TODO
		
		return result;
	}
	public StringBuilder generateAllDelFacts(int number){
		StringBuilder result = new StringBuilder();
		
		//TODO
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof BBinding){
			BBinding other = (BBinding) obj;
			result = this.head.equals(other.getHead()) 
					&& this.getTail().equals(other.getTail());
		}
		return result;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public SingleBind getHead() {
		return head;
	}

	public void setHead(SingleBind head) {
		this.head = head;
	}

	public Binding getTail() {
		return tail;
	}

	public void setTail(Binding tail) {
		this.tail = tail;
	}

	

}
