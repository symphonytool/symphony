/**
 * 
 */
package eu.compassresearch.ide.faulttolerance.jobs;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.ui.progress.IProgressConstants;

import eu.compassresearch.ide.faulttolerance.Activator;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationRequest;
import eu.compassresearch.ide.faulttolerance.IFaultToleranceVerificationResponse;
import eu.compassresearch.ide.faulttolerance.Image;
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
	private final List<IFaultToleranceVerificationPreRequisite> preRequisites;

	public FaultToleranceVerificationJobBase(Message jobNameMessage,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(jobNameMessage.format(request.getSystemName()));
		this.request = request;
		this.response = response;
		this.preRequisites = new LinkedList<>();
		init();
	}

	private void init() {
		addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				setProperty(IProgressConstants.ICON_PROPERTY,
						Image.FAULT_TOLERANCE_JOB_ICON.getImageDescriptor());
			}

			@Override
			public void scheduled(IJobChangeEvent event) {
				setProperty(IProgressConstants.ICON_PROPERTY,
						Image.FAULT_TOLERANCE_CLOCK_ICON.getImageDescriptor());
			}

			@Override
			public void done(IJobChangeEvent event) {
				setProperty(IProgressConstants.ICON_PROPERTY,
						Image.FAULT_TOLERANCE_TICK_ICON.getImageDescriptor());
			}
		});
	}

	public void add(IFaultToleranceVerificationPreRequisite preRequisite) {
		preRequisites.add(preRequisite);
	}

	public FaultToleranceVerificationJobBase(String name,
			IFaultToleranceVerificationRequest request,
			IFaultToleranceVerificationResponse response) {
		super(name);
		this.request = request;
		this.response = response;
		this.preRequisites = new LinkedList<>();
		init();
	}

	private void updateSchedulingRules() {
		setRule(updateSchedulingRules(createBaseRule(), request, response));
	}

	@Override
	public boolean shouldSchedule() {
		updateSchedulingRules();
		return super.shouldSchedule();
	}

	private ISchedulingRule createBaseRule() {
		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace()
				.getRuleFactory();
		ISchedulingRule fileRule = ruleFactory.createRule(request
				.getSourceUnit().getFile());
		ISchedulingRule combinedRule = MultiRule.combine(fileRule, getRule());
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

	@Override
	public final boolean shouldRun() {
		boolean should = super.shouldRun();

		for (IFaultToleranceVerificationPreRequisite pr : preRequisites) {
			should &= pr.checkPreRequisite();
			if (!should) {
				break;
			}
		}

		handleShouldRun(should);

		return should;
	}

	protected void handleShouldRun(boolean should) {
	}

	@Override
	public boolean belongsTo(Object family) {
		if (family instanceof String) {
			return Activator.FAULT_TOLERANCE_JOB_FAMILY.equals(family);
		}
		return super.belongsTo(family);
	}
}
