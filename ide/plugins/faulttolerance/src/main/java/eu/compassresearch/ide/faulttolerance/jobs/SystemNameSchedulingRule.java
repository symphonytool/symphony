/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class SystemNameSchedulingRule"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class SystemNameSchedulingRule implements ISchedulingRule {

	private final String systemName;

	public SystemNameSchedulingRule(String systemName) {
		this.systemName = systemName;
	}

	@Override
	public boolean contains(ISchedulingRule rule) {
		if (rule instanceof SystemNameSchedulingRule) {
			SystemNameSchedulingRule other = (SystemNameSchedulingRule) rule;
			return this.systemName.equals(other.systemName);
		}
		return false;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		return contains(rule);
	}

}
