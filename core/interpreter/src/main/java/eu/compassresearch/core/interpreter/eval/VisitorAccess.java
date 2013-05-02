package eu.compassresearch.core.interpreter.eval;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public interface VisitorAccess
{
	void setLeftChild(CmlBehaviour child);
	void setRightChild(CmlBehaviour child);
}