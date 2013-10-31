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
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.pub.IProofObligation;

import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.pog.POConstants;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.utils.UnhandledSyntaxException;
import eu.compassresearch.core.analysis.theoremprover.visitors.TPVisitor;

public class TPPluginDoStuff {
	private IWorkbenchWindow window;
	private IWorkbenchSite site;
	
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
	
	public void runTP()
	{
		try 
		{
			IProject proj = TPPluginUtils.getCurrentlySelectedProject();
			if (proj == null) {
				popErrorMessage("Can not produce theory file for theorem Proving.\n\n No CML project is selected.");
				return;
			}
			
			//Get the cml project
			ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);
			
			//Check there are no type errors.
			if (!CmlProjectUtil.typeCheck(this.window.getShell(), cmlProj)) {
				popErrorMessage("Can not produce theory file for theorem Proving.\n\n There are type errors in model.");
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

				//Create model backup folder
				modelBk.create(true, true, new NullProgressMonitor());
				modelBk.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
			}
			
			//Save the original model to the Isabelle folder for reference
			cmlProj.getModel().backup(modelBk);
			
			LinkedList<IFile> thyFiles = new LinkedList<IFile>();
			
			for (ICmlSourceUnit sourceUnit : model.getSourceUnits())
			{
				//create a generated thy file for the model
				String name = sourceUnit.getFile().getName();
				String fileName = name.substring(0,name.length()-(sourceUnit.getFile().getFileExtension().length()+1));
				String thyFileName = fileName+".thy";
				IFile thyFile = isaFolder.getFile(thyFileName);
				translateCmltoThy(model, thyFile, thyFileName);
				thyFiles.add(thyFile);
				
				//Create empty thy file which imports generated file
				String userThyFileName = fileName + "_User.thy";
				IFile userThyFile = isaFolder.getFile(userThyFileName);
				createEmptyThy(userThyFile, thyFileName);
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
	
	private void createEmptyThy(IFile file, String modelThyName) 
	{
		String thmString = TPVisitor.generateEmptyThyStr(modelThyName);
		
		try{
			file.delete(true, null);
			file.create(new ByteArrayInputStream(thmString.toString().getBytes()), true, new NullProgressMonitor());

		}catch(CoreException e)
		{
			Activator.log(e);
		}
	}

	private IFile translateCmltoThy(ICmlModel model, IFile outputFile, String thyFileName) 
			throws IOException, AnalysisException
	{
		String thmString = "";
		try
		{
			thmString = TPVisitor.generateThyStr(model.getAst(), thyFileName);
		}
		catch (UnhandledSyntaxException use)
		{
			thmString = use.getString();
			popErrorMessage(use.getErrorString());
		}
		finally
		{			
			try
			{
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

	/*****
	 * PLACEHOLDER FOR NOW - SHOULD TIE IN WITH COMMAND, FUNCTIONALITY NEEDS INTERTWINING 
	 * WITH TP STUFF BETTER, TOO.
	 */
	public void fetchPOs()
	{
		try
		{
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
			
			final ProofObligationList pol = new ProofObligationList();

			pol.addAll(model.getAttribute(POConstants.PO_REGISTRY_ID, CmlProofObligationList.class));
				
			if (pol == null)
			{
				popErrorMessage("There are no Proof Oligations to discharge.");
				return;
			}
			
			
			//Create project folder (needs to be timestamped)
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();        
			// Using DateFormat format method we can create a string 
			// representation of a date with the defined format.
			String date = df.format(today);

			IFolder isaFolder = cmlProj.getModelBuildPath().getOutput().getFolder(new Path("POG/" + date));
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
				//if 'generated' folder does exist and POG folder doesn't exist
				else if (!isaFolder.getParent().exists())
				{
					
					((IFolder) isaFolder.getParent()).create(true, true, new NullProgressMonitor());
						
				}
				//Create timestamped folder
				isaFolder.create(true, true, new NullProgressMonitor());
				isaFolder.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());

				//Create model backup folder
				modelBk.create(true, true, new NullProgressMonitor());
				modelBk.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
			}
			//Save the original model to the Isabelle folder for reference
			cmlProj.getModel().backup(modelBk);
			
			
			for (ICmlSourceUnit sourceUnit : model.getSourceUnits())
			{
				//create a generated thy file for the model
				String name = sourceUnit.getFile().getName();
				String fileName = name.substring(0,name.length()-(sourceUnit.getFile().getFileExtension().length()+1));
				String thyFileName = fileName+".thy";
				IFile thyFile = isaFolder.getFile(thyFileName);
				translateCmltoThy(model, thyFile, thyFileName);
				
				//Create empty thy file which imports generated file
				IFile pogThyFile = isaFolder.getFile(fileName + "_PO.thy");
				createPogThy(model, pogThyFile, thyFileName, pol);
			}
	
		} catch (Exception e)
		{
			e.printStackTrace();
			popErrorMessage(e.getMessage());
			Activator.log(e);
		}
	}
		
	/****
	 * Method to create a new THY file for a model's proof obligations.
	 * @param model
	 * @param pogThyFile
	 * @param thyFileName
	 * @param pol
	 * @return
	 */
	private IFile createPogThy(ICmlModel model, IFile pogThyFile, String thyFileName, ProofObligationList pol) {
		
		//Get the thy string for a given model and it's proof obligations
		String thmString = TPVisitor.generatePogThyStr(model.getAst(), pol, thyFileName);
		
		//create the file
		try
		{
			pogThyFile.delete(true, null);
			pogThyFile.create(new ByteArrayInputStream(thmString.toString().getBytes()), true, new NullProgressMonitor());
		}catch(CoreException e)
		{
			Activator.log(e);
		}
		
		return pogThyFile;		
	}
		
		
		
		
//REMOVING SCALA STUFF FOR NOW - GET PROOF OF CONCEPT THY GEN SORTED FIRST.		
//			Isabelle isabelle = IsabelleCore.isabelle();
//			Session session = null;
//
//			if (isabelle.session().isDefined())
//			{
//				session = isabelle.session().get();
//			} else
//			{
//				popErrorMessage("Isabelle is not started. See http://www.cl.cam.ac.uk/research/hvg/Isabelle/");
//				return;
//			}
//
//			if (tpListener == null)
//			{
//				tpListener = new TPListener(isabelle.session().get(), new IPoStatusChanged() {
//					
//					@Override
//					public void statusChanges(IProofObligation po) {
//						CmlProofObligationList poList = project.getModel().getAttribute(POConstants.PO_REGISTRY_ID, CmlProofObligationList.class);
//						
//						PogPluginRunner.redrawPos(project, poList);
//						
//					}
//				});
//				tpListener.init();
//			}
//
//
//			if (project == null)
//			{
//				popErrorMessage("No project selected.");
//				return;
//			}
//
//			if (CmlProjectUtil.typeCheck(shell, project))
//			{
//				ICmlModel model = project.getModel();
//				
//				CmlProofObligationList poList = model.getAttribute(POConstants.PO_REGISTRY_ID, CmlProofObligationList.class);
//				
//				if (poList == null)
//				{
//					popErrorMessage("There are no Proof Oligations to discharge.");
//					return;
//				}
//				
//				//Translate CML specification files to Isabelle
//				IFolder output = project.getModelBuildPath().getOutput().getFolder(new Path("Isabelle"));
//				if(!output.exists())
//				{
//					if (!output.getParent().exists())
//					{
//						((IFolder) output.getParent()).create(true, true, new NullProgressMonitor());
//							
//					}
//					output.create(true, true, new NullProgressMonitor());
//					output.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
//				}
//				
//				for (ICmlSourceUnit sourceUnit : model.getSourceUnits())
//				{
//					String name = sourceUnit.getFile().getName();
//					String thyFileName = name.substring(0,name.length()-sourceUnit.getFile().getFileExtension().length())+".ity";
//					translateCmltoThy(sourceUnit,output.getFile(thyFileName));
//				}
//
//				IsabelleTheory ithy = model.getAttribute(TPConstants.PLUGIN_ID, IsabelleTheory.class);
//
//					if (ithy == null )
//					{
//						IProject p = ((IProject) project.getAdapter(IProject.class));
//						String thyName = p.getName()+"_POs";
//						ithy = new IsabelleTheory(session, thyName,output.getLocation().toString());
//						ithy.init();
//						TPPluginUtils2.addThyToListener(ithy, tpListener, model);
//
//						model.setAttribute(TPConstants.PLUGIN_ID, ithy);
//					    Object bob = model.getAttribute(TPConstants.PLUGIN_ID, IsabelleTheory.class);
//					    System.out.println(bob.toString());
//					}
//
//					for (IProofObligation po : poList)
//					{
//						ithy.addThm(ithy.new IsabelleTheorem("po" + po.getUniqueName(), "True", "auto"));
//					}
//			}
//
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//			popErrorMessage(e.getMessage());
//			Activator.log(e);
//		}
	
	
	

	private void popErrorMessage(String message) {
		MessageDialog.openInformation(window.getShell(), "COMPASS", message);
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

}

