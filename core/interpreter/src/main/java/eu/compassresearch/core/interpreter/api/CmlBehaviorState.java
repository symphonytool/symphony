package eu.compassresearch.core.interpreter.api;

/**
 * This possible states that a CmlBehavior can be in at any given time.
 * 
 * @author akm
 */
public enum CmlBehaviorState
{
	/**
	 * Waiting for an event to occur
	 */
	WAITING_EVENT
	{

		@Override
		public CmlBehaviorState next(TransitionEvent event)
		{
			if (event == TransitionEvent.CHANNEL_EVENT)
			{
				return RUNNING;
			} else
			{
				return ERROR;
			}
		}

	},

	WAITING_CHILD
	{

		@Override
		public CmlBehaviorState next(TransitionEvent event)
		{
			if (event == TransitionEvent.CHILD_EVENT)
			{
				return RUNNING;
			} else
			{
				return ERROR;
			}
		}
	},

	/**
	 * Is ready to get executed
	 */
	RUNNING
	{

		@Override
		public CmlBehaviorState next(TransitionEvent event)
		{

			if (event == TransitionEvent.WAIT_EVENT)
			{
				return WAITING_EVENT;
			} else if (event == TransitionEvent.WAIT_CHILD)
			{
				return WAITING_CHILD;
			} else if (event == TransitionEvent.CHILD_EVENT)
			{
				return this;
			} else if (event == TransitionEvent.SKIP)
			{
				return FINISHED;
			} else if (event == TransitionEvent.STOP)
			{
				return STOPPED;
			} else if (event == TransitionEvent.DIV)
			{
				return DIVERGED;
			} else
			{
				return ERROR;
			}
		}

	},

	/**
	 * The process is deadlocked
	 */
	STOPPED
	{

		@Override
		public CmlBehaviorState next(TransitionEvent event)
		{
			return ERROR;
		}
	},
	/**
	 * An error occurred
	 */
	ERROR
	{

		@Override
		public CmlBehaviorState next(TransitionEvent event)
		{
			return ERROR;
		}

	},

	/**
	 * The behavior is diverged
	 */
	DIVERGED
	{

		@Override
		public CmlBehaviorState next(TransitionEvent event)
		{
			return DIVERGED;
		}
	},

	/**
	 * Successfully terminated
	 */
	FINISHED;

	public CmlBehaviorState next(TransitionEvent event)
	{
		// this should not be executed after its finished
		return ERROR;
	}

}
