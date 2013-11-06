package eu.compassresearch.core.analysis.modelchecker.ast.statements;

import org.overture.ast.statements.AUnresolvedStateDesignator;
import org.overture.ast.statements.PStateDesignator;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAUnresolvedStateDesignator implements MCPStateDesignator {

	private MCPCMLExp path;
	
	
	public MCAUnresolvedStateDesignator(MCPCMLExp path) {
		this.path = path;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPCMLExp getPath() {
		return path;
	}


	public void setPath(MCPCMLExp path) {
		this.path = path;
	}

	
}
