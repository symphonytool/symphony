package eu.compassresearch.core.interpreter.cml;

/**
 * Defines a object that is able to perform transactions
 * @author akm
 *
 */
public interface Transactable {

	void beginTransaction();
	
	void cancelTransaction();
	
	//void commitTransaction();
	
	boolean inTransaction();
	
}
