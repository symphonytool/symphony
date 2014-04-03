package eu.compassresearch.ide.interpreter.model;

import org.overture.ide.debug.core.VdmDebugPlugin;
import org.overture.ide.debug.core.dbgp.IDbgpStackLevel;
import org.overture.ide.debug.core.model.DebugEventHelper;

public class CmlStack
{
	public static final CmlStackFrame[] NO_STACK_FRAMES = new CmlStackFrame[0];

	private CmlStackFrame[] frames;
	private final Object framesLock = new Object();

	private final CmlThread thread;

	public CmlStack(CmlThread thread)
	{
		this.thread = thread;
		this.frames = NO_STACK_FRAMES;
	}

	public void update(boolean logErrors)
	{
		try
		{
			readFrames();
			// updateFrames();
		} catch (Exception e)
		{
			if (logErrors)
			{
				VdmDebugPlugin.log(e);
			}
		}
	}

	protected IDbgpStackLevel[] requrestStackLevels()
	{
		try
		{
			return thread.getCommunicationManager().getStackLevels(thread);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	protected void readFrames()
	{
		// thread.getVdmDebugTarget().printLog(new LogItem(((CmlThread) thread).getDbgpSession().getInfo(), "REQUEST",
		// true, "Stack Levels"));
		final IDbgpStackLevel[] levels = requrestStackLevels();
		// thread.getVdmDebugTarget().printLog(new LogItem(((CmlThread) thread).getDbgpSession().getInfo(), "RESPONSE",
		// false, "Stack Levels"));

		synchronized (framesLock)
		{
			final int newSize = levels.length;
			final int oldSize = frames.length;
			final int numToRebind = Math.min(newSize, oldSize);
			final CmlStackFrame[] newFrames = new CmlStackFrame[newSize];
			for (int depth = 0; depth < numToRebind; ++depth)
			{
				final CmlStackFrame oldFrame = (CmlStackFrame) frames[oldSize
						- depth - 1];
				newFrames[newSize - depth - 1] = oldFrame.bind(levels[newSize
						- depth - 1]);
			}
			final int newCount = newSize - oldSize;
			for (int i = 0; i < newCount; ++i)
			{
				newFrames[i] = new CmlStackFrame(this, levels[i]);
			}
			frames = newFrames;
			DebugEventHelper.fireChangeEvent(getThread());// todo FOLLOWUP added for debug view
		}
	}

	public CmlThread getThread()
	{
		return thread;
	}

	public int size()
	{
		synchronized (framesLock)
		{
			return frames.length;
		}
	}

	public boolean hasFrames()
	{
		synchronized (framesLock)
		{
			return frames.length > 0;
		}
	}

	public CmlStackFrame[] getFrames()
	{
		synchronized (framesLock)
		{
			return frames;
		}
	}

	public CmlStackFrame getTopFrame()
	{
		synchronized (framesLock)
		{
			return frames.length > 0 ? frames[0] : null;
		}
	}

	public void updateFrames()
	{
		synchronized (framesLock)
		{
			for (int i = 0; i < frames.length; i++)
			{
				((CmlStackFrame) frames[i]).updateVariables();
			}
		}
	}

	/**
	 * @return
	 */
	public boolean isInitialized()
	{
		synchronized (framesLock)
		{
			return frames != NO_STACK_FRAMES;
		}
	}
}
