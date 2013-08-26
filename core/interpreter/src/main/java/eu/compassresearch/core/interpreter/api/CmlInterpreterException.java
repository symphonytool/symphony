package eu.compassresearch.core.interpreter.api;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;

public class CmlInterpreterException extends AnalysisException {

	final private INode errorNode;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8020387717433469239L;

	public CmlInterpreterException(String message)
	{
		super(message);
		errorNode = null;
	}
	
	public CmlInterpreterException(INode errorNode, String message)
	{
		super(message);
		this.errorNode = errorNode;
	}
	
	public CmlInterpreterException(INode errorNode, String message, Throwable cause)
	{
		super(message,cause);
		this.errorNode = errorNode;
	}

	public INode getErrorNode() {
		return errorNode;
	}
	
	public boolean hasErrorNode()
	{
		return errorNode != null;
	}
}
