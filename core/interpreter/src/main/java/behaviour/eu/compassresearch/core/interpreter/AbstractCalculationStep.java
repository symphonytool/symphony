package eu.compassresearch.core.interpreter;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
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
	public abstract Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
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
	
	protected Pair<Context,Context> getChildContexts(Context context)
	{
		return visitorAccess.getChildContexts(context);
	}
	
	protected ILexNameToken name()
	{
		return owner.name();
	}
		
	protected CmlSupervisorEnvironment supervisor()
	{
		return owner.supervisor();
	}
	
	protected List<CmlBehaviour> children()
	{
		return owner.children();
	}
			
	protected void caseParallelNonSync() throws AnalysisException
	{
		CmlBehaviour leftChild =  owner.getLeftChild();
		CmlAlphabet leftChildAlpha = owner.getLeftChild().inspect(); 
		CmlBehaviour rightChild = owner.getRightChild();
		CmlAlphabet rightChildAlpha = rightChild.inspect();

		if(leftChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			leftChild.execute(supervisor());
		}
		else if(rightChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			rightChild.execute(supervisor());
		}
		else
		{
			throw new InterpreterRuntimeException("");
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
