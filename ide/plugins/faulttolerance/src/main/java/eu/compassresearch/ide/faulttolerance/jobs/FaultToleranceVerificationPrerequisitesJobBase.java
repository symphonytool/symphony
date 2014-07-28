/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;

import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse;
import eu.compassresearch.ide.faulttolerance.Message;

/**
 * @author Andr&eacute; Didier (<a href=
 *         "mailto:alrd@cin.ufpe.br?Subject=Package eu.compassresearch.ide.faulttolerance.jobs, class FaultToleranceVerificationPrerequisitesJobBase"
 *         >alrd@cin.ufpe.br</a>)
 * 
 */
public abstract class FaultToleranceVerificationPrerequisitesJobBase extends
		FaultToleranceVerificationJobBase {

	private final List<IFaultToleranceVerificationPreRequisite> preRequisites;

	public FaultToleranceVerificationPrerequisitesJobBase(
			Message jobNameMessage, IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(jobNameMessage, request, response);
		this.preRequisites = new LinkedList<>();
	}

	public FaultToleranceVerificationPrerequisitesJobBase(String name,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(name, request, response);
		this.preRequisites = new LinkedList<>();
	}

	public void add(IFaultToleranceVerificationPreRequisite preRequisite) {
		preRequisites.add(preRequisite);
	}

	private void updateSchedulingRules() {
		setRule(updateSchedulingRules(createBaseRule(), getRequest(),
				getResponse()));
	}

	@Override
	public boolean shouldSchedule() {
		updateSchedulingRules();
		return super.shouldSchedule();
	}

	private ISchedulingRule createBaseRule() {
		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace()
				.getRuleFactory();
		ISchedulingRule fileRule = ruleFactory.createRule(getRequest()
				.getSourceUnit().getFile());
		ISchedulingRule combinedRule = MultiRule.combine(fileRule, getRule());
		ISchedulingRule folderRule = ruleFactory.createRule(getResponse()
				.getFolder());
		combinedRule = MultiRule.combine(folderRule, combinedRule);
		ISchedulingRule parentFolderRule = ruleFactory.createRule(getResponse()
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
	public final boolean shouldRun() {
		boolean should = super.shouldRun();

		synchronized (getResponse()) {
			for (IFaultToleranceVerificationPreRequisite pr : preRequisites) {
				boolean preRequisite = pr.checkPreRequisite();
				should &= preRequisite;
				pr.postCheckPreRequisite(preRequisite);
				if (!should) {
					break;
				}
			}
		}

		handleShouldRun(should);

		return should;
	}

	protected void handleShouldRun(boolean should) {
	}
}
