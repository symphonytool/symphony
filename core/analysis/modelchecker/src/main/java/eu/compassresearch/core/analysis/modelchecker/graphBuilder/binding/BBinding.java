package eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;


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

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
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
	
	public String toFormula() {
		StringBuilder result = new StringBuilder();
		LinkedList<SingleBind> bindList = new LinkedList<SingleBind>(); 
		getSingleBindings(bindList, this);
		for (Iterator<SingleBind> iterator = bindList.iterator(); iterator.hasNext();) {
			result.append("BBinding("+this.procName+",");
			SingleBind singleBind = (SingleBind) iterator.next();
			result.append(singleBind.toFormula());
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
	
	public String toFormulaWithUnderscore(){
		StringBuilder result = new StringBuilder();
		LinkedList<SingleBind> bindList = new LinkedList<SingleBind>(); 
		getSingleBindings(bindList, this);
		for (Iterator<SingleBind> iterator = bindList.iterator(); iterator.hasNext();) {
			result.append("BBinding(" + this.procName + ",");
			SingleBind singleBind = (SingleBind) iterator.next();
			result.append(singleBind.toFormulaWithUnderscore());
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
	
	public String toFormulaGeneric(){
		StringBuilder result = new StringBuilder();
		LinkedList<SingleBind> bindList = new LinkedList<SingleBind>(); 
		getSingleBindings(bindList, this);
		for (Iterator<SingleBind> iterator = bindList.iterator(); iterator.hasNext();) {
			result.append("BBinding(" + this.procName + ",");
			SingleBind singleBind = (SingleBind) iterator.next();
			result.append(singleBind.toFormulaGeneric());
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
	
	public String toFormulaWithState() {
		StringBuilder result = new StringBuilder();
		LinkedList<SingleBind> bindList = new LinkedList<SingleBind>(); 
		getSingleBindings(bindList, this);
		for (Iterator<SingleBind> iterator = bindList.iterator(); iterator.hasNext();) {
			result.append("BBinding("+this.procName+",");
			SingleBind singleBind = (SingleBind) iterator.next();
			result.append(singleBind.toFormulaWithState());
			if(iterator.hasNext()){
				result.append(",");
			}
		}
		result.append(","+new NullBinding().toFormulaWithState());
		for (int i = 0; i < bindList.size(); i++) {
			result.append(")");
		}
		return result.toString();
	}
	@Override
	public BBinding copy(){
		return new BBinding(this.procName,this.head.copy(),this.tail.copy());
	}
	public Binding addBinding(String procName, String varName, Type varValue){
		Binding result = this;
		
		if(head.getVariableName().equals(varName)){
			head.setVariableValue(varValue);
		} else{
			tail = tail.addBinding(procName, varName, varValue);
		}
		return result;
	}
	public void updateBinding(String varName, Type varValue){
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
			result.append("fetch(\"" + singleBind.variableName + "\"," + this.toFormulaWithState() + ","+ singleBind.variableValue.toFormulaWithState() + ")\n");
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
	@Override
	public String toFormula(String option) {
		if(option.equals(MCNode.DEFAULT)){
			return toFormula();
		} else if(option.equals(MCNode.GENERIC)){
			return toFormulaGeneric();
		}
		return toFormulaWithState();
	}


}
