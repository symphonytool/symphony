package eu.compassresearch.ide.collaboration.treeview.model;



public interface IModelVisitor {

	void visitContract(Contract contract, Object passAlongArgument);
//	public void visitContracts(Contracts contracts, Object passAlongArgument);
//	public void visitContract(Contract contract, Object passAlongArgument);
}
