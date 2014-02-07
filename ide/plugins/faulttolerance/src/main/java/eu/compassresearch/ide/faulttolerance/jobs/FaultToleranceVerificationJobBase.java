/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;

import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse;
import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceVerificationJobBase"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public abstract class FaultToleranceVerificationJobBase extends WorkspaceJob {

	private final IFaultToleranceVerificationRequest request;
	private final IFaultToleranceVerificationResponse response;

	public FaultToleranceVerificationJobBase(Message jobNameMessage,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(jobNameMessage.format(request.getSystemName()));
		this.request = request;
		this.response = response;
		updateSchedulingRules();

	}

	private void updateSchedulingRules() {
		setRule(updateSchedulingRules(createBaseRule(), request, response));
	}

	private ISchedulingRule createBaseRule() {
		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace()
				.getRuleFactory();
		ISchedulingRule fileRule = ruleFactory.createRule(request
				.getSourceUnit().getFile());
		ISchedulingRule combinedRule = fileRule;
		ISchedulingRule folderRule = ruleFactory.createRule(response
				.getFolder());
		combinedRule = MultiRule.combine(folderRule, combinedRule);
		ISchedulingRule parentFolderRule = ruleFactory.createRule(response
				.getFolder().getParent());
		combinedRule = MultiRule.combine(parentFolderRule, combinedRule);
		return combinedRule;
	}

	/**
	 * 
	 * @param baseRule
	 *            Contains rules for:
	 *            <ul>
	 *            <li>System name (request)</li>
	 *            <li>Folder (response)</li>
	 *            <li>Parent folder (response)</li>
	 *            </ul>
	 * @param request
	 * @param response
	 */
	protected ISchedulingRule updateSchedulingRules(ISchedulingRule baseRule,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		return baseRule;
	}

	@Override
	public final IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		return runInWorkspace(request, response, monitor);
	}

	protected abstract IStatus runInWorkspace(
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response,
			IProgressMonitor monitor) throws CoreException;

	protected final String formatSystemName(Message message) {
		return message.format(request.getSystemName());
	}
}
