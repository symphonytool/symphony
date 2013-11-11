package eu.compassresearch.ide.pog;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.plugins.poviewer.view.PoOverviewTableView;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.analysis.pog.utility.PogPubUtil;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.pog.view.PoDetailView;
import eu.compassresearch.ide.pog.view.PoListView;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;

public class PogPluginRunner {
	private IWorkbenchWindow window;
	private IWorkbenchSite site;
	private ICmlProject cmlProj;

	public void runPog() {

		clearPoViews();

		if (!CmlProjectUtil.typeCheck(this.window.getShell(), cmlProj)) {
			PogPluginUtils.popErrorMessage(window, "Errors in model.");
			return;
		}

		final ICmlModel model = cmlProj.getModel();

		IProofObligationList polist = genPos(model);

		if (!polist.isEmpty()) {
			showPos(cmlProj,polist);
		}

	}

	private void clearPoViews() {
		IViewPart v;
		try {
			v = site.getPage().showView(POConstants.PO_OVERVIEW_TABLE);
			if (v instanceof PoListView) {
				((PoListView) v).clearPos();
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}

		IViewPart v2;
		try {
			v2 = site.getPage().showView(POConstants.PO_DETAIL_VIEW);
			if (v2 instanceof PoDetailView) {
				((PoDetailView) v2).clearPoView();
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}

	}

	private IProofObligationList genPos(ICmlModel model) {

		IProofObligationList poList = new CmlProofObligationList();

		try {
			poList = PogPubUtil.generateProofObligations(model.getAst());
			return poList;
		} catch (Exception e) {
			PogPluginUtils
					.popErrorMessage(
							window,
							"Internal POG error. Please submit a bug report at: \n\nhttp://sourceforge.net/p/compassresearch/tickets/new/");
			e.printStackTrace();
			return poList;
		}
	}

	public static void redrawPos(ICmlProject proj, IProofObligationList polist) {
		// FIXME Check if the data in the viewer table is still not being
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
					e.printStackTrace();
				}
			}
		});

	}

	private void showPos(final ICmlProject project, IProofObligationList pos) {
		final IProofObligationList pol = pos;

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

							PogPluginUtils.openPoviewPerspective(site);
						} catch (PartInitException e) {
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
	public PogPluginRunner(IWorkbenchWindow window, IWorkbenchSite site,
			ICmlProject cmlproj) {
		this.cmlProj = cmlproj;
		this.window = window;
		this.site = window.getActivePage().getActivePart().getSite();
	}

}
