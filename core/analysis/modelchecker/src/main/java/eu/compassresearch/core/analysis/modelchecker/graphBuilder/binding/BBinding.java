package eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding;

import java.util.Iterator;
import java.util.LinkedList;


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
	public BBinding() {
		// TODO Auto-generated constructor stub
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
		for (Iterator iterator = bindList.iterator(); iterator.hasNext();) {
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
		result.append("BBinding("+this.procName+",");
		//result.append(head.toString());
		LinkedList<SingleBind> bindList = new LinkedList<SingleBind>(); 
		getSingleBindings(bindList, this);
		for (Iterator iterator = bindList.iterator(); iterator.hasNext();) {
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
	
	private void getSingleBindings(LinkedList<SingleBind> bindList, Binding binding){
		if(binding instanceof BBinding){
			bindList.add(((BBinding) binding).getHead());
			getSingleBindings(bindList, ((BBinding) binding).getTail());
		}
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


}
