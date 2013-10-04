package eu.compassresearch.core.interpreter.api.behaviour;

/**
 * Implementors of this are able to perform transactions
 * 
 * @author akm
 */
public interface Transactable
{

	/**
	 * Begins a transaction
	 */
	void beginTransaction();

	void rollback();

	// void commit();

	boolean inTransaction();

}
