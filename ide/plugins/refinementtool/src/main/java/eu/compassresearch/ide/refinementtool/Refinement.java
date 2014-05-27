package eu.compassresearch.ide.refinementtool;

import java.util.List;

import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;

public class Refinement {
	private String result;
	private List<CmlProofObligation> provisos;
	
	public String getResult() {
		return result;
	}
	public List<CmlProofObligation> getProvisos() {
		return provisos;
	}
	public Refinement(String result, List<CmlProofObligation> provisos) {
		super();
		this.result = result;
		this.provisos = provisos;
	}
	
	
	
}
