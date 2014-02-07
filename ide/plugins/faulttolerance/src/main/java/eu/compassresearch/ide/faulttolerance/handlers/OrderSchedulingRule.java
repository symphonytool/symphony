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
	private final int order;

	public OrderSchedulingRule(int order) {
		this.order = order;
	}

	@Override
	public boolean contains(ISchedulingRule rule) {
		if (rule instanceof OrderSchedulingRule) {
			OrderSchedulingRule other = (OrderSchedulingRule) rule;
			return this.order <= other.order;
		}
		return false;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		if (rule instanceof OrderSchedulingRule) {
			OrderSchedulingRule other = (OrderSchedulingRule) rule;
			return this.order <= other.order;
		}
		return false;
	}
}
