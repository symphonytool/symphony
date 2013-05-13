package eu.compassresearch.core.interpreter.visitors;

import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

/**
 * Interface that enables a visitor to set private/protected state in a CmlBehaviour.
 * This is needed because the visitors associated with a CmlBehavior instance need to be able to 
 * set the children, this however should not be done elsewhere. 
 */
public interface VisitorAccess
{
	void setLeftChild(CmlBehaviour child);
	void setRightChild(CmlBehaviour child);
}