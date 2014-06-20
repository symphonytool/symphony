package eu.compassresearch.core.interpreter.runtime;

import java.util.List;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.AClassType;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.Value;

public class DelayedWriteObjectContext extends ObjectValue
{
	/**
	 * serial
	 */
	private static final long serialVersionUID = 903752290806114039L;
	private DelayedWriteContext delayedCtxt;

	public DelayedWriteObjectContext(AClassType type, NameValuePairMap members,
			List<ObjectValue> superobjects, CPUValue cpu, ObjectValue creator)
	{
		super(type, members, superobjects, cpu, creator);
	}

	public DelayedWriteObjectContext(ObjectValue self,
			DelayedWriteContext delayedCtxt)
	{
		this(self.type,self.members,self.superobjects,self.getCPU(),self.creator);
		this.delayedCtxt = delayedCtxt;
	}


@Override
public synchronized Value get(ILexNameToken field, boolean explicit)
{
	Value v = super.get(field, explicit);
	return delayedCtxt.wrap(v, field);
}
}
