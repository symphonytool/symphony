package eu.compassresearch.core.interpreter.eval;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public interface VisitorAccess
{
	void mergeState(CmlBehaviour other);
	void setLeftChild(CmlBehaviour child);
	void setRightChild(CmlBehaviour child);
	CmlAlphabet getHidingAlphabet();
	void setHidingAlphabet(CmlAlphabet alpha);
}