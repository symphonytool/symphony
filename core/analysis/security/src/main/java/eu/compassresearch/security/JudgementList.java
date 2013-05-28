package eu.compassresearch.security;

import java.util.LinkedList;

import eu.compassresearch.security.SecurityEnvironment.Judgement;

public class JudgementList extends LinkedList<Judgement>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JudgementList(Judgement... some) {
		for(Judgement j : some) this.add(j);
	}
	
}
