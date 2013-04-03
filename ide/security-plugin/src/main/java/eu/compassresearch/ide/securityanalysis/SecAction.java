package eu.compassresearch.ide.securityanalysis;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.common.RegistryFactory;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.security.SecurityAnalysis;
import eu.compassresearch.security.SecurityEnvironment.Judgement;
import eu.compassresearch.security.SecurityEnvironment.SubtreeJudgement;

public class SecAction implements IWorkbenchWindowActionDelegate{

	private Registry registry;
	private MessageConsoleStream console;
	private IWorkbenchWindow window;

	private IProject getCurrentProject() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();

		if (window != null) {
			IStructuredSelection selection = (IStructuredSelection) window
					.getSelectionService().getSelection(
							"org.overture.ide.ui.VdmExplorer");
			IResource res = extractSelection(selection);
			if (res != null ) {
				IProject project = res.getProject();
				return project;
			}
		}
		return null;
	}

	static IResource extractSelection(ISelection sel) {
		if (!(sel instanceof IStructuredSelection))
			return null;
		IStructuredSelection ss = (IStructuredSelection) sel;
		Object element = ss.getFirstElement();
		if (element instanceof IResource)
			return (IResource) element;
		if (!(element instanceof IAdaptable))
			return null;
		IAdaptable adaptable = (IAdaptable) element;
		Object adapter = adaptable.getAdapter(IResource.class);
		return (IResource) adapter;
	}


	public static ArrayList<IResource> getAllCFilesInProject(IProject project) {
		ArrayList<IResource> allCFiles = new ArrayList<IResource>();
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace()
				.getRoot();

		IPath path = project.getLocation();

		recursiveFindCMLFiles(allCFiles, path, myWorkspaceRoot);
		return allCFiles;
	}

	private static void recursiveFindCMLFiles(ArrayList<IResource> allCMLFiles,
			IPath path, IWorkspaceRoot myWorkspaceRoot) {
		IContainer container = myWorkspaceRoot.getContainerForLocation(path);

		try {
			IResource[] iResources;
			iResources = container.members();
			for (IResource iR : iResources) {
				// for c files
				if ("cml".equalsIgnoreCase(iR.getFileExtension()))
					allCMLFiles.add(iR);
				if (iR.getType() == IResource.FOLDER) {
					IPath tempPath = iR.getLocation();
					recursiveFindCMLFiles(allCMLFiles, tempPath,
							myWorkspaceRoot);
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void run(IAction arg0) {
		try {

			MessageConsole mc = new MessageConsole("Security Console", null);
			ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { mc });
			this.console = mc.newMessageStream();
			this.console.setActivateOnWrite(true);
			try {
				this.console.write("~-~-~- Security Console -~-~-~\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

			IProject proj = getCurrentProject();
			if (proj == null) { console.write("No project selected.\n");return; }
			ArrayList<IResource> files = getAllCFilesInProject(proj);
			console.write("Model has "+files.size()+" source unit(s).\n");
			SecurityAnalysis sa = new SecurityAnalysis();
			for(IResource file: files) {
				console.write("Analysing: "+file.getName()+"\n");
				PSource ast = CmlSourceUnit.getFromFileResource((IFile)file).getSourceAst();
				SubtreeJudgement judgements = sa.runAnalysis(ast);
				for(Judgement j : judgements.getInvolvedJudgements()) {
					console.write(j.toString()+"\n");

				} 
			}
		}
		catch (Exception e) {
			try {
				console.write("An instance of Chaos occured.\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}


@Override
public void selectionChanged(IAction arg0, ISelection arg1) {

}

@Override
public void dispose() {

}

@Override
public void init(IWorkbenchWindow arg0) {
	RegistryFactory factory = eu.compassresearch.core.common.RegistryFactory.getInstance();
	this.registry = factory.getRegistry();
	this.window = arg0;
}

}
