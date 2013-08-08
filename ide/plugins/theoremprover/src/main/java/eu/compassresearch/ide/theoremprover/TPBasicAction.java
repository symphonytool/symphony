package eu.compassresearch.ide.theoremprover;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
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
import eu.compassresearch.theoremprover.visitors.TPVisitor;

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

			
			 
			
//			IFile thyfile = new IFile();
//			thyfile = translateCmltoThy(model, thyfile);
				
			//Translate CML specification files to Isabelle
			//TODO:Create project folder (needs to be timestamped)
			IFolder output = cmlProj.getModelBuildPath().getOutput().getFolder(new Path("Isabelle"));
			if(!output.exists())
			{
				if (!output.getParent().exists())
				{
					((IFolder) output.getParent()).create(true, true, new NullProgressMonitor());
						
				}
				output.create(true, true, new NullProgressMonitor());
				output.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
			}
			
			for (ICmlSourceUnit sourceUnit : model.getSourceUnits())
			{
				//TODO:Copy .cml file into folder
				String name = sourceUnit.getFile().getName();
				String thyFileName = name.substring(0,name.length()-sourceUnit.getFile().getFileExtension().length())+"thy";
				translateCmltoThy(model,output.getFile(thyFileName), thyFileName);
//				translateCmltoThy(sourceUnit,output.getFile(thyFileName));
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

	private void popErrorMessage(String message) {
		MessageDialog.openInformation(window.getShell(), "COMPASS",
				"Could not generate THY.\n\n" + message);
	}
	
	private IFile translateCmltoThy(ICmlModel model, IFile outputFile, String thyFileName) throws IOException,
	AnalysisException
	{
		
//		TPVisitor tp = new TPVisitor(model.getAstSource());
//		StringBuilder sb = new StringBuilder();

		String thmString = TPVisitor.generateThyStr(model.getAst(), thyFileName);
//		String thmString = "test thy";
		
		try{
			outputFile.delete(true, null);
			outputFile.create(new ByteArrayInputStream(thmString.toString().getBytes()), true, new NullProgressMonitor());
		}catch(CoreException e)
		{
			Activator.log(e);
		}
		
		return outputFile;
		
		
//		 r = new ProofObligationList();
//			for (INode node : ast) {
//				r.addAll(node.apply(new ProofObligationGenerator(),
//						new CmlPOContextStack()));
//			}
//			r.renumber();
//			return r;
			
			

		


			
			
		
		//
		//sb.append("theory " + thyName + " \n" + "  imports utp_vdm \n"
		//		+ "begin \n" + "\n");
		//
		//sb.append("text {* VDM value declarations *}\n\n");
		//
		//for (ThmValue tv : tpv.getValueList())
		//{
		//	sb.append(tv.toString());
		//	sb.append("\n");
		//}
		//
		//sb.append("\n");
		//sb.append("text {* VDM type declarations *}\n\n");
		//
		//for (ThmType ty : tpv.getTypeList())
		//{
		//	sb.append(ty.toString());
		//	sb.append("\n");
		//}
		//
		//sb.append("\n");
		//
		//sb.append("\n" + "end");

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
