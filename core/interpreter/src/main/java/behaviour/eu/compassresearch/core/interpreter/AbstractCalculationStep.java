package eu.compassresearch.core.interpreter;

import java.util.Iterator;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
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
	
	protected void removeTheChildren()
	{
		for(Iterator<CmlBehaviour> iterator = children().iterator(); iterator.hasNext(); )
		{
			CmlBehaviour child = iterator.next();
			owner.supervisor().removePupil(child);
			iterator.remove();
		}
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
			
	protected void error(INode errorNode, String message)
	{
		throw new InterpreterRuntimeException(message);
	}

}
