package eu.compassresearch.core.interpreter;

import eu.compassresearch.core.interpreter.api.CmlBehaviour;

/**
 * Utility class for {@link CmlBehaviour} objects
 * @author kel
 *
 */
class CmlBehaviourUtility
{

	public static boolean isAllChildrenFinished(CmlBehaviour process)
	{
		boolean isAllFinished = true;
		for (CmlBehaviour child : process.children())
		{
			isAllFinished &= child.finished();
		}
		return isAllFinished;
	}

	public static boolean finishedChildExists(CmlBehaviour process)
	{
		for (CmlBehaviour child : process.children())
		{
			if (child.finished())
			{
				return true;
			}
		}

		return false;
	}


	/**
	 * Debug method for displaying a {@link ConcreteCmlBehaviour} with its {@code next} field containing the node and
	 * context. It also displays the two children left and right.
	 * 
	 * @param tabs
	 *            initial value should be 0
	 * @param behaviour
	 *            the behavior to display
	 * @return the display string. Can be ignored by the caller since it is also printed to std out.
	 */
	@SuppressWarnings("javadoc")
	public static String printBehaviour(int tabs, CmlBehaviour behaviour)
	{
		String t = "";
		int i = tabs;
		while (i > 0)
		{
			t += "\t";
			i--;
		}

		if (behaviour == null)
		{
			return (t + "null");
		}
		StringBuilder sb = new StringBuilder();
		if (behaviour instanceof ConcreteCmlBehaviour)
		{

			ConcreteCmlBehaviour cb = (ConcreteCmlBehaviour) behaviour;

			sb.append(t + "##############-START-" + tabs + "-###########\n");
			if(cb.next!=null)
			{
			sb.append(t + "Node    = "
					+ (cb.next.first + "").replace('\n', ' ') + "\n");
			sb.append(t + "Context = "
					+ (cb.next.second + "").replace('\n', ' ') + "\n");
			sb.append(t + "State   = "
					+ (cb.next.second.getSelf() + "").replace('\n', ' ') + "\n");
			}
			sb.append(t + "Pre-constructed child ctxt = "+(cb.preConstructedChildContexts+"").replace('\n', ' ')+"\n");
			sb.append(t + "------------LEFT-----------\n");
			sb.append(printBehaviour(tabs + 1, cb.getLeftChild()) + "\n");
			sb.append(t + "------------RIGHT----------\n");
			sb.append(printBehaviour(tabs + 1, cb.getRightChild()) + "\n");
			sb.append(t + "############-END-" + tabs + "-##############\n");

		}

		if (tabs == 0)
		{
			System.out.println(sb);
		}
		return sb.toString();
	}
}
