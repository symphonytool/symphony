package eu.compassresearch.core.analysis.modelchecker.ast.types;


public class MCNaturalTypeWrapper extends MCTypeWrapper {

	public MCNaturalTypeWrapper(MCPCMLType internalType) {
		super(internalType);
	}

	@Override
	public MCTypeWrapper copy() {
		return new MCNaturalTypeWrapper(internalType.copy());
	}

}
