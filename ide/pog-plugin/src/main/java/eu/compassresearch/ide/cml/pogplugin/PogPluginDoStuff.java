package eu.compassresearch.ide.cml.pogplugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.plugins.poviewer.view.PoOverviewTableView;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.analysis.pog.utility.PogPubUtil;
import eu.compassresearch.ide.cml.pogplugin.view.PoListView;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;

public class PogPluginDoStuff {
	private IWorkbenchWindow window;
	private IWorkbenchSite site;
	private boolean posInRegistry = false;

	public void runPog() {

		IProject proj = PogPluginUtils.getCurrentlySelectedProject();

		if (proj == null) {
			popErrorMessage("No project selected.");
			return;
		}

		ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);

		if (!CmlProjectUtil.typeCheck(this.window.getShell(), cmlProj)) {
			popErrorMessage("Errors in model.");
			return;
		}

		final ICmlModel model = cmlProj.getModel();

		addPOsToRegistry(model);
		if (posInRegistry) {
			showPOs(cmlProj, model);
		}

	}

	private void popErrorMessage(String message) {
		MessageDialog.openError(window.getShell(), "COMPASS POG",
				"Could not generate Proof Obligations.\n\n" + message);
	}

	private void addPOsToRegistry(ICmlModel model) {

		IProofObligationList poList = new CmlProofObligationList();

		try {
			poList = PogPubUtil.generateProofObligations(model.getAst());

		} catch (AnalysisException e) {
			// FIXME ADD Submit to bug tracker.
			popErrorMessage("Internal POG error. Please submit a bug report at: \n\nhttp://sourceforge.net/p/compassresearch/tickets/new/");
			e.printStackTrace();
			return;
		}
		model.setAttribute(POConstants.PO_REGISTRY_ID, poList);
		posInRegistry = true;
	}

	public static void redrawPos(ICmlProject proj, IProofObligationList polist) {
		// FIXME Check if the data in the viewer table is still not being
		// updated!
		final IProofObligationList pol = polist;
		final ICmlProject project = proj;
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				IViewPart v;

				try {
					v = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage()
							.findView(POConstants.PO_OVERVIEW_TABLE);
					if (v instanceof PoOverviewTableView) {
						((PoOverviewTableView) v).setDataList(
								(IVdmProject) project
										.getAdapter(IVdmProject.class), pol);

					}

					// PogPluginUtility ppu = new PogPluginUtility(site);
					// ppu.openPoviewPerspective();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

	}

	private void showPOs(final ICmlProject project, ICmlModel model) {
		final ProofObligationList pol = new ProofObligationList();

		pol.addAll(model.getAttribute(POConstants.PO_REGISTRY_ID,
				CmlProofObligationList.class));

		site.getPage().getWorkbenchWindow().getShell().getDisplay()
				.asyncExec(new Runnable() {

					public void run() {
						IViewPart v;

						try {
							v = site.getPage().showView(
									POConstants.PO_OVERVIEW_TABLE);
							if (v instanceof PoListView) {
								((PoListView) v).setDataList(project, pol);

							}

							PogPluginUtils ppu = new PogPluginUtils(site);
							ppu.openPoviewPerspective();
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public PogPluginDoStuff(IWorkbenchWindow window, IWorkbenchSite site) {
		this.window = window;
		this.site = window.getActivePage().getActivePart().getSite();
	}

}
