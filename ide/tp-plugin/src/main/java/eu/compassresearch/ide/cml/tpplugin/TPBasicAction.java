package eu.compassresearch.ide.cml.tpplugin;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.theoremprover.TPVisitor;

public class TPBasicAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub

		try {

			IProject proj = TPPluginUtils.getCurrentlySelectedProject();
			if (proj == null) {
				popErrorMessage("No project selected.");
				return;
			}

			ArrayList<IResource> cmlFiles = TPPluginUtils
					.getAllCFilesInProject(proj);

			for (IResource cmlFile : cmlFiles) {
				CmlSourceUnit source = CmlSourceUnit
						.getFromFileResource((IFile) cmlFile);
				if (!CmlTypeChecker.Utils.isWellType(source.getSourceAst())) {
					popErrorMessage("There were type errors in "
							+ source.getFile().getName());
					return;
				}

				String cmlLoc = cmlFile.getLocation().toString();
				String thyFile = cmlLoc.replaceAll("\\.cml", "\\.thy");

				TPVisitor tpv = new TPVisitor();
				source.getSourceAst().apply(tpv);

				File thy = new File(thyFile);
				FileWriter fw = new FileWriter(thy);
				fw.write(getThyFromCML(cmlFile));
				fw.flush();
				fw.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
			popErrorMessage(e.getMessage());
		}

	}

	private void popErrorMessage(String message) {
		MessageDialog.openInformation(window.getShell(), "COMPASS",
				"Could not generate POs.\n\n" + message);
	}

	private String getThyFromCML(IResource cmlFile) {

		StringBuilder sb = new StringBuilder();

		return "";

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
		// TODO Auto-generated method stub

	}

}
