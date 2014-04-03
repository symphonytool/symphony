package eu.compassresearch.ide.refinementtool;

import java.util.List;

import org.overture.ast.expressions.PExp;

public class Refinement {
	private String result;
	private List<PExp> provisos;
	
	public String getResult() {
		return result;
	}
	public List<PExp> getProvisos() {
		return provisos;
	}
	public Refinement(String result, List<PExp> provisos) {
		super();
		this.result = result;
		this.provisos = provisos;
	}
	
	
	
}
