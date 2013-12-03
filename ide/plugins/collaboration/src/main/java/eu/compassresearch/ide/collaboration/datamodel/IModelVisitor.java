package eu.compassresearch.ide.collaboration.datamodel;



public interface IModelVisitor {

	void visitContract(Contract contract, Object passAlongArgument);
//	public void visitContracts(Contracts contracts, Object passAlongArgument);
//	public void visitContract(Contract contract, Object passAlongArgument);
}
