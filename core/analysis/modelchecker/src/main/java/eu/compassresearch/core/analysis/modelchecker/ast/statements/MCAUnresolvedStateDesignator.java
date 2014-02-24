package eu.compassresearch.core.analysis.modelchecker.ast.statements;

import org.overture.ast.statements.PStateDesignator;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAUnresolvedStateDesignator implements MCPStateDesignator {

	private MCPCMLExp path;
	
	
	public MCAUnresolvedStateDesignator(MCPCMLExp path) {
		this.path = path;
	}


	@Override
	public String toFormula(String option) {
		
		return path.toFormula(option);
	}

	@Override
	public String toString() {
		return path.toFormula(MCNode.DEFAULT);
	
	}

	public MCPCMLExp getPath() {
		return path;
	}


	public void setPath(MCPCMLExp path) {
		this.path = path;
	}

	
}
