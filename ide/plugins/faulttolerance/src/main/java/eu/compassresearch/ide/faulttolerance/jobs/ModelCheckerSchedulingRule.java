/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class ModelCheckerSchedulingRule"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public class ModelCheckerSchedulingRule implements ISchedulingRule {

	@Override
	public boolean contains(ISchedulingRule rule) {
		return rule instanceof ModelCheckerSchedulingRule;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		return contains(rule);
	}

}
