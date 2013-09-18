package eu.compassresearch.core.interpreter;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.utility.Pair;

abstract class AbstractCalculationStep implements CmlCalculationStep {

	/**
	 * Interface that gives access to methods that access protected parts of a CmlBehaviour
	 */
	private VisitorAccess 													visitorAccess;
	protected final CmlBehaviour 											owner;			
	
	public AbstractCalculationStep(CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		//this.parentVisitor = parentVisitor;
		this.owner = owner;
		this.visitorAccess = visitorAccess;
	}
	
	@Override
	public abstract Pair<INode, Context> execute(CmlTransition selectedTransition)
			throws AnalysisException;
	
	
	/*
	 * Protected Helper Methods 
	 */

	protected void setLeftChild(CmlBehaviour child)
	{
		this.visitorAccess.setLeftChild(child);
	}
	
	protected void setRightChild(CmlBehaviour child)
	{
		this.visitorAccess.setRightChild(child);
	}
	
	protected Pair<INode,Context> replaceWithChild(CmlBehaviour child)
	{
		this.visitorAccess.setLeftChild(child.getLeftChild());
		this.visitorAccess.setRightChild(child.getRightChild());
		return child.getNextState();
	}
	
	protected Pair<Context,Context> getChildContexts(Context context)
	{
		return visitorAccess.getChildContexts(context);
	}
	
	protected ILexNameToken name()
	{
		return owner.name();
	}
		
	protected List<CmlBehaviour> children()
	{
		return owner.children();
	}
			
	protected void caseParallelNonSync(CmlTransition selectedTransition) throws AnalysisException
	{
		CmlBehaviour leftChild =  owner.getLeftChild();
		CmlTransitionSet leftChildAlpha = owner.getLeftChild().inspect(); 
		CmlBehaviour rightChild = owner.getRightChild();
		CmlTransitionSet rightChildAlpha = rightChild.inspect();

		if(leftChildAlpha.contains(selectedTransition))
		{
			leftChild.execute(selectedTransition);
		}
		else if(rightChildAlpha.contains(selectedTransition))
		{
			rightChild.execute(selectedTransition);
		}
		else
		{
			throw new CmlInterpreterException("A selected event that should have affected either left or right");
		}
	}

	/**
	 * Finds the first finished child if any
	 * @return The first finished child, if none then null is returned
	 */
	protected CmlBehaviour findFinishedChild()
	{
		for(CmlBehaviour child : children())
		{
			if(child.finished())
				return child;
		}
		
		return null;
	}
}
