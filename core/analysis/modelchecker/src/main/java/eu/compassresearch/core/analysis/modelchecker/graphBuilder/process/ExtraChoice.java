package eu.compassresearch.core.analysis.modelchecker.graphBuilder.process;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;

public class ExtraChoice extends Choice {
	
	public Binding getBindingLeft() {
		return bindingLeft;
	}

	public void setBindingLeft(Binding bindingLeft) {
		this.bindingLeft = bindingLeft;
	}

	public Binding getBindingRight() {
		return bindingRight;
	}

	public void setBindingRight(Binding bindingRight) {
		this.bindingRight = bindingRight;
	}

	private Binding bindingLeft;
	private Binding bindingRight;
	
	
	public ExtraChoice(Binding bindingLeft, Process left,Binding bindingRight, Process right) {
		super(left, right);
		this.bindingLeft = bindingLeft;
		this.bindingRight = bindingRight;
	}
	
	@Override
	public String toString() {
		//it needs to include bindings
		return "(" + firstProcess.toString() + ")" + " [+] " + "(" + secondProcess.toString() + ")";
	}


	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof ExtraChoice) {
			result =  super.equals(obj);
		}
		return result;
	}
	
	@Override
	public boolean isDeadlock(){
		return this.getFirstProcess().isDeadlock() && this.getSecondProcess().isDeadlock();
	}

}
