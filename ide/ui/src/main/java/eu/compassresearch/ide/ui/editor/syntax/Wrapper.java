package eu.compassresearch.ide.ui.editor.syntax;

import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IAnalysis;
import org.overture.ast.analysis.intf.IAnswer;
import org.overture.ast.analysis.intf.IQuestion;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.node.INode;
import org.overture.ast.node.Node;

public class Wrapper<T> extends Node
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static <T> Wrapper<T> newInstance(T value, String str)
	{
		return new Wrapper<T>(value, str);
	}

	public Wrapper(T value, String str)
	{
		this.description = str;
		this.value = value;
	}

	public T value;
	private String description;

	public boolean isClass(Class<?> cls)
	{
		return cls.isInstance(value);
	}

	public String toString()
	{
		return description;
	}

	public String getDescription()
	{
		return description;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Wrapper other = (Wrapper) obj;
		if (value == null)
		{
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public void apply(IAnalysis arg0) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <A> A apply(IAnswer<A> arg0) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <Q> void apply(IQuestion<Q> arg0, Q arg1) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <Q, A> A apply(IQuestionAnswer<Q, A> arg0, Q arg1)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object clone()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public INode clone(Map<INode, INode> arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String kindNode()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeChild(INode arg0)
	{
		// TODO Auto-generated method stub

	}

}