package eu.compassresearch.ide.collaboration.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contracts extends Model {

	private static final long serialVersionUID = -7710832265326698319L;
	
	protected Map<String, Contract> contracts;

	public Contracts() {
		contracts = new HashMap<String, Contract>();
		this.name = "Contracts";
	}

	public void addContract(Contract contract) {
		contracts.put(contract.getName(), contract);
		contract.addListener(listener);
		contract.setParent(this);
		fireObjectAddedEvent(contract);
	}

	protected void removeContract(Contract contract) {
		contracts.remove(contract);
		contract.removeListener(listener);
		fireObjectRemovedEvent(contract);
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
