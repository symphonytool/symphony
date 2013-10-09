package eu.compassresearch.ide.collaboration.treeview.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Contracts extends Model {
	protected List contracts;

	public Contracts() {
		contracts = new ArrayList();
		this.name = "Contracts";
	}

	public void addContract(Contract contract) {
		contracts.add(contract);
		contract.addListener(listener);
		contract.parent = this;
		fireAdd(contract);
	}

	protected void removeContract(Contract contract) {
		contracts.remove(contract);
		contract.addListener(NullDeltaListener.getSoleInstance());
		fireRemove(contract);
	}

	public List getContracts() {
		return contracts;
	}

	public int size() {
		return contracts.size();
	}

	public void accept(IModelVisitor visitor, Object passAlongArgument) {
		// visitor.visitContracts(this, passAlongArgument);
	}

	@Override
	public void addListener(IDeltaListener listener) {

		for (Iterator iterator = contracts.iterator(); iterator.hasNext();) {
			Contract contract = (Contract) iterator.next();
			contract.addListener(listener);
		}

		super.addListener(listener);
	}
}
