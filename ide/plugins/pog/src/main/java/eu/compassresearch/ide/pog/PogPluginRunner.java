package eu.compassresearch.ide.pog;

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
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.plugins.poviewer.view.PoOverviewTableView;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.analysis.pog.utility.PogPubUtil;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.pog.view.PoDetailView;
import eu.compassresearch.ide.pog.view.PoListView;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;

public class PogPluginRunner
{
	private IWorkbenchWindow window;
	private IWorkbenchSite site;
	private boolean posInRegistry = false;
	private ICmlProject cmlProj;

	
	
	public void runPog()
	{

		clearPoViews();



		
		if (!CmlProjectUtil.typeCheck(this.window.getShell(), cmlProj))
		{
			popErrorMessage("Errors in model.");
			return;
		}

		final ICmlModel model = cmlProj.getModel();

		addPOsToRegistry(model);
		if (posInRegistry)
		{
			showPOs(cmlProj, model);
		}

	}

	private void clearPoViews()
	{
		IViewPart v;
		try
		{
			v = site.getPage().showView(POConstants.PO_OVERVIEW_TABLE);
			if (v instanceof PoListView)
			{
				((PoListView) v).clearPos();
			}
		} catch (PartInitException e)
		{
			e.printStackTrace();
		}

		IViewPart v2;
		try
		{
			v2 = site.getPage().showView(POConstants.PO_DETAIL_VIEW);
			if (v2 instanceof PoDetailView)
			{
				((PoDetailView) v2).clearPoView();
			}
		} catch (PartInitException e)
		{
			e.printStackTrace();
		}

	}

	private void popErrorMessage(String message)
	{
		MessageDialog.openError(window.getShell(), "Symphony POG", "Could not generate Proof Obligations.\n\n"
				+ message);
	}

	private void addPOsToRegistry(ICmlModel model)
	{

		IProofObligationList poList = new CmlProofObligationList();

		try
		{
			poList = PogPubUtil.generateProofObligations(model.getAst());

		} catch (Exception e)
		{
			popErrorMessage("Internal POG error. Please submit a bug report at: \n\nhttp://sourceforge.net/p/compassresearch/tickets/new/");
			e.printStackTrace();
			return;
		}
		model.setAttribute(POConstants.PO_REGISTRY_ID, poList);
		posInRegistry = true;
	}

	public static void redrawPos(ICmlProject proj, IProofObligationList polist)
	{
		// FIXME Check if the data in the viewer table is still not being
		// updated!
		final IProofObligationList pol = polist;
		final ICmlProject project = proj;
		Display.getDefault().asyncExec(new Runnable()
		{

			public void run()
			{
				IViewPart v;

				try
				{
					v = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(POConstants.PO_OVERVIEW_TABLE);
					if (v instanceof PoOverviewTableView)
					{
						((PoOverviewTableView) v).setDataList((IVdmProject) project.getAdapter(IVdmProject.class), pol);

					}

					// PogPluginUtility ppu = new PogPluginUtility(site);
					// ppu.openPoviewPerspective();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}

		});

	}

	private void showPOs(final ICmlProject project, ICmlModel model)
	{
		final ProofObligationList pol = new ProofObligationList();

		pol.addAll(model.getAttribute(POConstants.PO_REGISTRY_ID, CmlProofObligationList.class));

		site.getPage().getWorkbenchWindow().getShell().getDisplay().asyncExec(new Runnable()
		{

			public void run()
			{
				IViewPart v;

				try
				{
					v = site.getPage().showView(POConstants.PO_OVERVIEW_TABLE);
					if (v instanceof PoListView)
					{
						((PoListView) v).setDataList(project, pol);

					}

					PogPluginUtils ppu = new PogPluginUtils(site);
					ppu.openPoviewPerspective();
				} catch (PartInitException e)
				{
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of the 'real' action here if we want, but
	 * this can only happen after the delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection)
	{
	}

	/**
	 * We will cache window object in order to be able to provide parent shell for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public PogPluginRunner(IWorkbenchWindow window, IWorkbenchSite site, ICmlProject cmlproj)
	{
		this.cmlProj = cmlproj;
		this.window = window;
		this.site = window.getActivePage().getActivePart().getSite();
	}

}
