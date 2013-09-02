package eu.compassresearch.ide.theoremprover;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;
import eu.compassresearch.theoremprover.visitors.TPVisitor;

public class TPPluginDoStuff {
	private IWorkbenchWindow window;
	private IWorkbenchSite site;

	public void runTP()
	{
		try {

			IProject proj = TPPluginUtils.getCurrentlySelectedProject();
			if (proj == null) {
				popErrorMessage("No CML project is selected.");
				return;
			}
			
			//Get the cml project
			ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);
			
			//Check there are no type errors.
			if (!CmlProjectUtil.typeCheck(this.window.getShell(), cmlProj)) {
				popErrorMessage("Can not produce theory file for theorem Proving. \n There are type errors in model.");
				return;
			}
			//Grab the model from the project
			final ICmlModel model = cmlProj.getModel();
				
			//Translate CML specification files to Isabelle
			//Create project folder (needs to be timestamped)
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();        
			// Using DateFormat format method we can create a string 
			// representation of a date with the defined format.
			String date = df.format(today);

			IFolder isaFolder = cmlProj.getModelBuildPath().getOutput().getFolder(new Path("Isabelle/" + date));
			IFolder modelBk = isaFolder.getFolder("model");
			if(!isaFolder.exists())
			{
				//if generated folder doesn't exist
				if (!isaFolder.getParent().getParent().exists())
				{
					//create 'generated' folder
					((IFolder) isaFolder.getParent().getParent()).create(true, true, new NullProgressMonitor());
					//create 'Isabelle' folder
					((IFolder) isaFolder.getParent()).create(true, true, new NullProgressMonitor());

				}
				//if 'generated' folder does exist and Isabelle folder doesn't exist
				else if (!isaFolder.getParent().exists())
				{
					
					((IFolder) isaFolder.getParent()).create(true, true, new NullProgressMonitor());
						
				}
				//Create timestamped folder
				isaFolder.create(true, true, new NullProgressMonitor());
				isaFolder.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());

				//Create timestamped folder
				modelBk.create(true, true, new NullProgressMonitor());
				modelBk.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
			}
			
			//Save the original model to the Isabelle folder for reference
			cmlProj.getModel().backup(modelBk);
			
			LinkedList<IFile> thyFiles = new LinkedList<IFile>();
			
			for (ICmlSourceUnit sourceUnit : model.getSourceUnits())
			{
				String name = sourceUnit.getFile().getName();
				String thyFileName = name.substring(0,name.length()-sourceUnit.getFile().getFileExtension().length())+"thy";
				IFile thyFile = isaFolder.getFile(thyFileName);
				translateCmltoThy(model, thyFile, thyFileName);
				thyFiles.add(thyFile);
				
				//Create empty thy file which imports generated file
				String userThyFileName = "User"+ thyFileName;
				IFile userThyFile = isaFolder.getFile(userThyFileName);
				createEmptyThy(userThyFile, userThyFileName, thyFileName);
				thyFiles.add(userThyFile);
			}
			
			//Switch to the Isabelle perspective and open thy files
			showIsabelle(cmlProj, model, thyFiles);
			
			//TODO: start Isabelle

		} catch (Exception e) {
			e.printStackTrace();
			popErrorMessage(e.getMessage());
		}

	}
	
	private void createEmptyThy(IFile file, String userThyFileName, String modelThyName) {
		StringBuilder sb = new StringBuilder();
		
		String usrthyName = userThyFileName.substring(0, userThyFileName.lastIndexOf('.'));
		String modelthyName = modelThyName.substring(0, modelThyName.lastIndexOf('.'));

		//Add thy header 
		sb.append("theory " + usrthyName + " \n" + "  imports utp_cml " + modelthyName +"\n"
				+ "begin \n" + "\n");
		sb.append("text {* Auto-generated THY file for user created proof with "+  modelthyName + ".thy *}\n\n");
		
			
		sb.append("\n\n\n" + "end");
		
		try{
			file.delete(true, null);
			file.create(new ByteArrayInputStream(sb.toString().getBytes()), true, new NullProgressMonitor());

		}catch(CoreException e)
		{
			Activator.log(e);
		}
	}

	private IFile translateCmltoThy(ICmlModel model, IFile outputFile, String thyFileName) throws IOException,
	AnalysisException
	{

		String thmString = TPVisitor.generateThyStr(model.getAst(), thyFileName);
		
		try{
			outputFile.delete(true, null);
			outputFile.create(new ByteArrayInputStream(thmString.toString().getBytes()), true, new NullProgressMonitor());
			
			//set .thy file to be read only
			ResourceAttributes attributes = new ResourceAttributes();
			attributes.setReadOnly(true);
			outputFile.setResourceAttributes(attributes); 

		}catch(CoreException e)
		{
			Activator.log(e);
		}
		
		return outputFile;
	}
	
	//Switch to the Isabelle perspective automatically
	private void showIsabelle(final ICmlProject project, ICmlModel model, final LinkedList<IFile> files) {
		site.getPage().getWorkbenchWindow().getShell().getDisplay()
				.asyncExec(new Runnable() {

					public void run() {
						TPPluginUtils tpu = new TPPluginUtils(site);
						tpu.openTPPerspective();
						for(IFile file : files)
						{
							tpu.openThyFile(file);
						}
					}

				});
	}

	private void popErrorMessage(String message) {
		MessageDialog.openInformation(window.getShell(), "COMPASS",
				"Could not generate THY.\n\n" + message);
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
	public TPPluginDoStuff(IWorkbenchWindow window, IWorkbenchSite site) {
		this.window = window;
		this.site = window.getActivePage().getActivePart().getSite();
	}
}

