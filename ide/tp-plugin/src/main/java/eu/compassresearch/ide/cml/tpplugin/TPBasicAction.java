package eu.compassresearch.ide.cml.tpplugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;

import eu.compassresearch.ide.cml.ui.editor.core.dom.ICmlSourceUnit;
import eu.compassresearch.theoremprover.TPVisitor;
import eu.compassresearch.theoremprover.ThmType;

public class TPBasicAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	
	@Override
	public void run(IAction action) {
		try
		{

			IProject proj = TPPluginUtils.getCurrentlySelectedProject();
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

					ArrayList<IResource> cmlFiles = TPPluginUtils.getAllCFilesInProject(proj);

					for (IResource cmlFile : cmlFiles)
					{						
						getThyFromCML(cmlFile);
					}
		
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			popErrorMessage(e.getMessage());
		}

	}

	private void popErrorMessage(String message)
	{
		MessageDialog.openInformation(window.getShell(), "COMPASS", "Could not generate THY.\n\n"
				+ message);
	}
	
	private void getThyFromCML(IResource cmlFile) throws IOException, AnalysisException {

		ICmlSourceUnit source = (ICmlSourceUnit) cmlFile.getAdapter(ICmlSourceUnit.class);
		
		String cmlLoc = cmlFile.getLocation().toString();
		String thyFile = cmlLoc.replaceAll("\\.cml", "\\.thy");

		TPVisitor tpv = new TPVisitor();
		source.getSourceAst().apply(tpv);
		
		String name = cmlFile.getName();
		
		String thyName = name.substring(0, name.lastIndexOf("."));
		StringBuilder sb = new StringBuilder();

		sb.append("theory "+ thyName +" \n" +
				  "  imports utp_vdm \n" +
				  "begin \n" +
				  "\n");
				 		
		sb.append("text {* VDM type declarations *}\n\n");
		
		for (ThmType ty : tpv.getTypeList()) {
			sb.append(ty.toString());
		}
		
		sb.append("\n");
		
		sb.append("\n" + "end");

		File thy = new File(thyFile);
		FileWriter fw = new FileWriter(thy);
		fw.write(sb.toString());
		fw.flush();
		fw.close();

		return;

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

}
