package eu.compassresearch.ide.theoremprover;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;

import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;
import eu.compassresearch.core.analysis.theoremprover.visitors.TPVisitor;

public class TPBasicAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
//	private TPListener tpListener = null;

	@Override
	public void run(IAction action) {
		try {
//			Isabelle isabelle = IsabelleCore.isabelle();
//			Session session = null;
//
//			if (isabelle.session().isDefined()) {
//				session = isabelle.session().get();
//			} else {
//				popErrorMessage("Isabelle is not started");
//				return;
//			}
//
//			if (tpListener == null) { 
//				tpListener = new TPListener(isabelle.session().get());
//			}
//				
//			Registry registry = RegistryFactory.getInstance(
//					POConstants.PO_REGISTRY_ID).getRegistry();

			IProject proj = TPPluginUtils.getCurrentlySelectedProject();
			if (proj == null) {
				popErrorMessage("No project is selected.");
				return;
			}

			
			//Get the cml project
			ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);
			
			//Check there are no type errors.
			if (!CmlProjectUtil.typeCheck(this.window.getShell(), cmlProj)) {
				popErrorMessage("Errors in model.");
				return;
			}
			//Grab the model from the project
			final ICmlModel model = cmlProj.getModel();

//			ICmlProject cp = (ICmlProject) p.getAdapter(ICmlProject.class);
//			
//			IFolder output = (IFolder) cmlProj.getModelBuildPath().getOutput();
//			output = output.getFolder("Isabelle").getFolder("989");
//			cp.getModel().backup(output);
//			 
			
//			IFile thyfile = new IFile();
//			thyfile = translateCmltoThy(model, thyfile);
				
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
			
			
			
			for (ICmlSourceUnit sourceUnit : model.getSourceUnits())
			{
				String name = sourceUnit.getFile().getName();
				String thyFileName = name.substring(0,name.length()-sourceUnit.getFile().getFileExtension().length())+"thy";
				translateCmltoThy(model, isaFolder.getFile(thyFileName), thyFileName);
				//Create empty thy file which imports generated file
				String userThyFileName = "User"+ thyFileName;
				createEmptyThy(isaFolder.getFile(userThyFileName), userThyFileName, thyFileName);
			}
			
			//TODO: Should this really be fetching POs at this point?? Don't think we should... (RJP)
//			new FetchPosUtil(this.window.getShell(),cmlProject).fetchPOs();

//			if (vdmProject == null) {
//				return;
//			}
//
//			final IVdmModel model = vdmProject.getModel();
//			if (model.isParseCorrect()) {
//
//				if (!model.isParseCorrect()) {
//					return;
//					// return new Status(Status.ERROR,
//					// IPoviewerConstants.PLUGIN_ID,
//					// "Project contains parse errors");
//				}
//
//				if (model == null || !model.isTypeCorrect()) {
//					VdmTypeCheckerUi.typeCheck(this.window.getShell(),
//							vdmProject);
//				}
//
//				if (model.isTypeCorrect()) {
//
//					ArrayList<IResource> cmlFiles = TPPluginUtils
//							.getAllCFilesInProject(proj);
//
//					for (IResource cmlFile : cmlFiles) {
//						// May return a null if the adapter fails to convert
//						ICmlSourceUnit cmlSource = (ICmlSourceUnit) cmlFile
//								.getAdapter(ICmlSourceUnit.class);
//						CMLProofObligationList poList = registry.lookup(
//								cmlSource.getSourceAst(),
//								CMLProofObligationList.class);
//
//						getThyFromCML(cmlFile);
//						
//						IsabelleTheory ithy = registry.lookup(cmlSource.getSourceAst(), IsabelleTheory.class);
//
//						if (ithy == null) {
//							String cmlLoc = cmlFile.getLocation().toString();
//							String poFile = cmlLoc.replaceAll("\\.cml",
//									"-POs.thy");
//							ithy = new IsabelleTheory(session, poFile, proj
//									.getLocation().toString());
//							ithy.init();
//							registry.store(cmlSource.getSourceAst(), ithy);
//						} 
//
//						for (ProofObligation po : poList) {
//							ithy.addThm(ithy.new IsabelleTheorem(po.name, "True",
//									"by auto\n"));
//						}
//
//					}
//
//				}
//
//			}
//
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

	private void popErrorMessage(String message) {
		MessageDialog.openInformation(window.getShell(), "COMPASS",
				"Could not generate THY.\n\n" + message);
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
