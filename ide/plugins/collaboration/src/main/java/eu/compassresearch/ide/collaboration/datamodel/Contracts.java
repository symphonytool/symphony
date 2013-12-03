package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contracts extends Model {
	protected Map<String, Contract> contracts;

	public Contracts() {
		contracts = new HashMap<String, Contract>();
		this.name = "Contracts";
	}

	public void addContract(Contract contract) {
		contracts.put(contract.getName(), contract);
		contract.addListener(listener);
		contract.setParent(this);
		fireAdd(contract);
	}

	protected void removeContract(Contract contract) {
		contracts.remove(contract);
		contract.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(contract);
	}

	public List<Contract> getContracts() {
		return new ArrayList<Contract>(contracts.values());
	}

	public int size() {
		return contracts.size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		// visitor.visitContracts(this, passAlongArgument);
	}

	@Override
	public void addListener(IDeltaListener listener) {

		for (Contract c : contracts.values())
		{
			c.addListener(listener);
		}

		super.addListener(listener);
	}

	public Contract getContract(String name)
	{
		return contracts.get(name);
	}
}
