package eu.compassresearch.security;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.node.INode;

public class SecurityEnvironment {

	private SecurityEnvironment outer;

	public SecurityEnvironment getOuter() { return outer; }

	public static class SubtreeJudgement {
		private SecurityJudgement judgement;
		private JudgementList involedJudgements;
		private INode subtree;

		public SubtreeJudgement(INode subtree) {
			this.judgement = SecurityJudgement.Public;
			this.subtree=subtree;
			this.involedJudgements = new JudgementList();
		}

		public void setJudgement(SecurityJudgement judgement) { this.judgement = judgement; }

		public SubtreeJudgement(INode subtree,SecurityJudgement judgement, JudgementList involvedJudgements) {
			this.judgement = judgement;
			this.involedJudgements = involvedJudgements;
			this.subtree=subtree;
		}

		public SecurityJudgement getJudgement() {
			return judgement;
		}

		public JudgementList getInvolvedJudgements() {
			return involedJudgements;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(subtree+":"+judgement+" conditioned on: "+involedJudgements);
			return sb.toString();
		}
	}

	public enum SecurityJudgement {
		Random, Public, Secret;

		public static SecurityJudgement fromId(ILexIdentifierToken id) {
			if (id == null) return Public;
			if (id.getName() == null) return Public;
			if (id.getName().startsWith("R_")) return Random;
			if (id.getName().startsWith("S_")) return Secret;
			return Public;
		}
	}

	public static class Judgement {

		private List<Judgement> randomizes;
		private List<Judgement> hides;

		private final ILexIdentifierToken definition;
		private SecurityJudgement judgement;
		public Judgement(ILexIdentifierToken def, SecurityJudgement judgement) {
			this.definition = def;
			this.judgement = judgement;
			this.randomizes  = new LinkedList<Judgement>();
			this.hides = new LinkedList<Judgement>();
		}

		public void updateJudgement(SecurityJudgement judgement) {
			if (this.judgement != SecurityJudgement.Random && 
					judgement == SecurityJudgement.Random) 
				throw new IllegalArgumentException(definition + " is "+judgement+" cannot make something random again."); 

			// Classify 
			if (judgement == SecurityJudgement.Secret){
				if (this.judgement == SecurityJudgement.Random) {
					// Special behaviour if Random
				}
				this.judgement = judgement;
			}

			// De-classify
			if (judgement == SecurityJudgement.Public) {
				for(Judgement j : hides)
					j.judgement = SecurityJudgement.Public;
				this.judgement = judgement;
			}
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(definition+":"+judgement);
			return sb.toString();
		}

		public SecurityJudgement getJudgement() { return judgement; }
		public List<Judgement> getHides() { return hides; }
	}

	private Map<String,Judgement> env;

	public SecurityEnvironment() {
		this.env = new HashMap<String,Judgement>();
	}

	public Judgement getJudgement(ILexIdentifierToken id) {
		if (id == null) return null;
		return env.get(id.toString());
	}

	public Judgement getRecursive(LexIdentifierToken id) {
		if (id == null) return null;
		SecurityEnvironment current = this;
		while(current != null) {
			if (env.containsKey(id.toString())) return env.get(id.toString());
			current = current.outer;
		}
		return null;
	}

	public Judgement addJudgement(ILexIdentifierToken def, SecurityJudgement judgement) {
		if (def == null) return null;
		if (judgement == null) return null;

		if (env.containsKey(def.toString())) {
			Judgement j = env.get(def.toString());
			j.updateJudgement(judgement);
			return j;
		}
		else {
			Judgement newJudgement = new Judgement(def,judgement);
			env.put(def.toString(),newJudgement);
			return newJudgement;
		}
	}

	public SecurityEnvironment newScope() {
		SecurityEnvironment result = new SecurityEnvironment();
		result.outer = this;
		return result;
	}
}
