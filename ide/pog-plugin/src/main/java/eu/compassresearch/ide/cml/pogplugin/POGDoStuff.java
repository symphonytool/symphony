package eu.compassresearch.ide.cml.pogplugin;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.plugins.poviewer.Activator;
import org.overture.ide.plugins.poviewer.view.PoOverviewTableView;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;
import org.overture.pog.obligation.ProofObligation;
import org.overture.pog.obligation.ProofObligationList;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.visitors.ProofObligationGenerator;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.ide.cml.ui.editor.core.dom.ICmlSourceUnit;

/**
 * Our sample action implements workbench action delegate. The action proxy will be created by the workbench and shown
 * in the UI. When the user tries to use the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class POGDoStuff implements IWorkbenchWindowActionDelegate
{
	private IWorkbenchWindow window;
	private IWorkbenchSite site;
 

	/**
	 * The constructor.
	 */
	public POGDoStuff()
	{
	}

	/**
	 * The action has been activated. The argument of the method represents the 'real' action sitting in the workbench
	 * UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action)
	{

		try
		{

			IProject proj = PogPluginUtility.getCurrentlySelectedProject();
			if (proj == null)
			{
				popErrorMessage("No project selected.");
				return;
			}

			// Check project is built
			IVdmProject vdmProject = (IVdmProject) proj.getAdapter(IVdmProject.class);

			if (vdmProject == null)
			{
				return;
			}

			final IVdmModel model = vdmProject.getModel();
			if (model.isParseCorrect())
			{

				if (!model.isParseCorrect())
				{
					return;
					// return new Status(Status.ERROR, IPoviewerConstants.PLUGIN_ID,
					// "Project contains parse errors");
				}

				if (model == null || !model.isTypeCorrect())
				{
					VdmTypeCheckerUi.typeCheck(this.window.getShell(), vdmProject);
				}

				if (model.isTypeCorrect())
				{

					ArrayList<IResource> cmlfiles = PogPluginUtility.getAllCFilesInProject(proj);

					for (IResource cmlfile : cmlfiles)
					{
						ICmlSourceUnit source = (ICmlSourceUnit) cmlfile.getAdapter(ICmlSourceUnit.class);
						if (!CmlTypeChecker.Utils.isWellType(source.getSourceAst()))
						{
							popErrorMessage("There were type errors in "
									+ source.getFile().getName());
							return;
						}
					}

					ProofObligationList pol = getPoListFromSource(cmlfiles);
					
					showPOs(vdmProject, pol);

				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			popErrorMessage(e.getMessage());
		}

	}

	private void popErrorMessage(String message)
	{
		MessageDialog.openInformation(window.getShell(), "COMPASS", "Could not generate POs.\n\n"
				+ message);
	}

	private ProofObligationList getPoListFromSource(ArrayList<IResource> cmlfiles) throws Exception
	{

		ProofObligationList pol = new ProofObligationList();
		
		for (IResource cmlfile : cmlfiles)
		{
			ICmlSourceUnit source = (ICmlSourceUnit) cmlfile.getAdapter(ICmlSourceUnit.class);			
//			CmlSourceUnit source = CmlSourceUnit.getFromFileResource((IFile) cmlfile);
			PSource psAux = source.getSourceAst();
			ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
			try
			{
				ProofObligationList innerpol = pog.generatePOs();
				for (ProofObligation po : innerpol){
					pol.add(po);
				}
			}

			catch (Exception e)
			{
				throw e;
			}
		}
		return pol;
	}

	
	private char[] getPoStringsfromSource(ArrayList<IResource> cmlfiles,
			String projName) throws Exception
	{

		StringBuilder sb = new StringBuilder();
		sb.append("-- Auto-Generated Proof Obligations for " + projName
				+ " project \n");
		sb.append("-- CAUTION: this file is temporary!\n");
		sb.append("=================================================================\n\n");
		for (IResource cmlfile : cmlfiles)
		{
			CmlSourceUnit source = CmlSourceUnit.getFromFileResource((IFile) cmlfile);
			PSource psAux = source.getSourceAst();
			ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
			ProofObligationList pol = new ProofObligationList();
			sb.append("-- File: " + cmlfile.getName() + ": \n\n");
			try
			{
				pol = pog.generatePOs();
				for (ProofObligation po : pol)
					sb.append(po.toString() + "\n");
				sb.append("-----------------------------------------------\n\n");
			}

			catch (Exception e)
			{
				throw e;
			}
		}
		return sb.toString().toCharArray();
	}

	private void showPOs(final IVdmProject project,
			final ProofObligationList pos)
	{
		site.getPage().getWorkbenchWindow().getShell().getDisplay().asyncExec(new Runnable()
		{

			public void run()
			{
				IViewPart v;
				try
				{
					v = site.getPage().showView("eu.compassresearch.ide.OvtPoOTable");
					if (v instanceof PoOverviewTableView)
					{
						((PoOverviewTableView) v).setDataList(project, pos);

					}	

		//			openPoviewPerspective();
				} catch (PartInitException e)
				{
					if (Activator.DEBUG)
					{
						e.printStackTrace();
					}
				}

			}

		});
	}

	
	
	
	/**
	 * We will cache window object in order to be able to provide parent shell for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window)
	{
		this.window = window;
		this.site=window.getActivePage().getActivePart().getSite();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}
}