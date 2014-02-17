/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.handlers;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.handlers, class OrderSchedulingRule"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class OrderSchedulingRule implements ISchedulingRule {
	private final int group;
	private final int index;

	public OrderSchedulingRule(int group, int index) {
		this.group = group;
		this.index = index;
	}

	@Override
	public boolean contains(ISchedulingRule rule) {
		if (rule instanceof OrderSchedulingRule) {
			OrderSchedulingRule other = (OrderSchedulingRule) rule;
			if (this.group == other.group) {
				return this.index == other.index;
			}

			return this.group < other.group;
		}
		return false;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		return contains(rule);
	}

	@Override
	public String toString() {
		return "Order " + group + "." + index;
	}
}
