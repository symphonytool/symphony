/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.handlers;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.handlers, class ProcessNameSchedulingRule"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class ProcessNameSchedulingRule implements ISchedulingRule {

	private final String processName;

	public ProcessNameSchedulingRule(String processName) {
		this.processName = processName;
	}

	@Override
	public boolean contains(ISchedulingRule rule) {
		return this.equals(rule);
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		if (rule instanceof ProcessNameSchedulingRule) {
			ProcessNameSchedulingRule other= (ProcessNameSchedulingRule)rule;
			return processName.equals(other.getProcessName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((processName == null) ? 0 : processName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessNameSchedulingRule other = (ProcessNameSchedulingRule) obj;
		if (processName == null) {
			if (other.processName != null)
				return false;
		} else if (!processName.equals(other.processName))
			return false;
		return true;
	}

	public String getProcessName() {
		return processName;
	}

}
