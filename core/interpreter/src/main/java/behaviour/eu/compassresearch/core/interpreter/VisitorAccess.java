package eu.compassresearch.core.interpreter;

import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.utility.Pair;


/**
 * Interface that enables a visitor to set private/protected state in a CmlBehaviour.
 * This is needed because the visitors associated with a CmlBehavior instance need to be able to 
 * set the children, this however should not be done elsewhere. 
 */
interface VisitorAccess
{
	void setLeftChild(CmlBehaviour child);
	void setRightChild(CmlBehaviour child);
	Pair<Context,Context> getChildContexts(Context context);
	void setChildContexts(Pair<Context,Context> contexts);
}