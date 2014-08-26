package eu.compassresearch.core.interpreter.runtime;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.runtime.ContextException;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.Value;

public class DelayedWriteObjectValue extends ObjectValue
{
	/**
	 * serial
	 */
	private static final long serialVersionUID = 903752290806114039L;
	private DelayedWriteContext delayedCtxt;
	private final ObjectValue originalSelf;

	// public DelayedWriteObjectValue(AClassType type, NameValuePairMap members,
	// List<ObjectValue> superobjects, CPUValue cpu, ObjectValue creator)
	// {
	// super(type, members, superobjects, cpu, creator);
	// }

	public DelayedWriteObjectValue(ObjectValue self,
			DelayedWriteContext delayedCtxt)
	{
		super(self.type, self.members, self.superobjects, self.getCPU(), self.creator);
		this.delayedCtxt = delayedCtxt;
		this.originalSelf = self;
	}

	@Override
	public synchronized Value get(ILexNameToken field, boolean explicit)
	{
		// try to lookup the field in the delayed context first, this may give the wrong result if the field name
		// already exists in the context
		try
		{
			Value val = this.delayedCtxt.lookup(field,getOriginalSelf().objectReference);
			if (val != null)
			{
				return val;
			}
		} catch (ContextException e)
		{
			// ignore if it wasnt there
		}
		Value v = super.get(field, explicit);
		return delayedCtxt.wrapField(v, field,getOriginalSelf().objectReference);
	}

	public ObjectValue getOriginalSelf()
	{
		if (originalSelf instanceof DelayedWriteObjectValue)
		{
			return ((DelayedWriteObjectValue) originalSelf).getOriginalSelf();
		}
		return originalSelf;
	}
}
