package eu.compassresearch.ide.theoremprover;

import isabelle.Session;
import isabelle.eclipse.core.IsabelleCore;
import isabelle.eclipse.core.text.EditDocumentModel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.pub.IProofObligation;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.analysis.pog.utility.PogPubUtil;
import eu.compassresearch.core.analysis.theoremprover.utils.UnhandledSyntaxException;
import eu.compassresearch.core.analysis.theoremprover.visitors.TPVisitor;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.core.unsupported.UnsupportedElementInfo;
import eu.compassresearch.ide.pog.PogPluginRunner;
import eu.compassresearch.ide.pog.PogPluginUtils;
import eu.compassresearch.ide.theoremprover.utils.TheoryLoader;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;

public class TPPluginDoStuff {
	private IWorkbenchWindow window;
	private IWorkbenchSite site;

	public static final String UNSUPPORTED_ELEMENTS_MSG = "This model contains unsupported CML elements. Theorem proving will continue, however errors may occur in the resultant theory file. Check the warnings for more information.";
	public static final String ISABELLE_KEYWORDS_MSG = "This model contains Isabelle reserved words. Check the warnings for more information.";

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

	public void runTP() {
		try {
			IProject proj = TPPluginUtils.getCurrentlySelectedProject();
			if (proj == null) {
				popErrorMessage("Can not produce theory file for theorem Proving.\n\n No CML project is selected.");
				return;
			}

			
			// Test for unsupportted
			checkUnsupporteds(proj);
			
			// Get the cml project
			ICmlProject cmlProj = (ICmlProject) proj
					.getAdapter(ICmlProject.class);

			// Check there are no type errors.
			if (!CmlProjectUtil.typeCheck(this.window.getShell(), cmlProj)) {
				popErrorMessage("Can not produce theory file for theorem Proving.\n\n There are type errors in model.");
				return;
			}
			// Grab the model from the project
			final ICmlModel model = cmlProj.getModel();

			// Translate CML specification files to Isabelle
			// Create project folder (needs to be timestamped)
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();
			// Using DateFormat format method we can create a string
			// representation of a date with the defined format.
			String date = df.format(today);

			IFolder isaFolder = cmlProj.getModelBuildPath().getOutput()
					.getFolder(new Path("Isabelle/" + date));
			IFolder modelBk = isaFolder.getFolder("model");
			if (!isaFolder.exists()) {
				// if generated folder doesn't exist
				if (!isaFolder.getParent().getParent().exists()) {
					// create 'generated' folder
					((IFolder) isaFolder.getParent().getParent()).create(true,
							true, new NullProgressMonitor());
					// create 'Isabelle' folder
					((IFolder) isaFolder.getParent()).create(true, true,
							new NullProgressMonitor());

				}
				// if 'generated' folder does exist and Isabelle folder doesn't
				// exist
				else if (!isaFolder.getParent().exists()) {

					((IFolder) isaFolder.getParent()).create(true, true,
							new NullProgressMonitor());

				}
				// Create timestamped folder
				isaFolder.create(true, true, new NullProgressMonitor());
				isaFolder.refreshLocal(IResource.DEPTH_ZERO,
						new NullProgressMonitor());

				// Create model backup folder
				modelBk.create(true, true, new NullProgressMonitor());
				modelBk.refreshLocal(IResource.DEPTH_ZERO,
						new NullProgressMonitor());
			}

			// Save the original model to the Isabelle folder for reference
			cmlProj.getModel().backup(modelBk);

			LinkedList<IFile> thyFiles = new LinkedList<IFile>();

			for (ICmlSourceUnit sourceUnit : model.getSourceUnits()) {
				// create a generated thy file for the model
				String name = sourceUnit.getFile().getName();
				String fileName = name.substring(0,
						name.length()
								- (sourceUnit.getFile().getFileExtension()
										.length() + 1));
				String thyFileName = fileName + ".thy";
				IFile thyFile = isaFolder.getFile(thyFileName);
				translateCmltoThy(model, thyFile, thyFileName);
				thyFiles.add(thyFile);

				// Create empty thy file which imports generated file
				String userThyFileName = fileName + "_User.thy";
				IFile userThyFile = isaFolder.getFile(userThyFileName);
				createEmptyThy(userThyFile, thyFileName);
				thyFiles.add(userThyFile);
			}

			// Switch to the Isabelle perspective and open thy files
			showIsabelle(cmlProj, model, thyFiles);

			// TODO: start Isabelle

		} catch (Exception e) {
			e.printStackTrace();
			popErrorMessage(e.getMessage());
		}
	}
	
	public void dischargeClassic(ICmlProject cmlProj) {
		try {
			// Check there are no type errors.
			if (!CmlProjectUtil.typeCheck(this.window.getShell(), cmlProj)) {
				popErrorMessage("Can not produce theory file for theorem Proving. \n There are type errors in model.");
				return;
			}
			// Grab the model from the project
			final ICmlModel model = cmlProj.getModel();

			IProofObligationList pol = PogPubUtil
					.generateProofObligations(model.getAst());
			if (pol.isEmpty()) {
				popErrorMessage("There are no Proof Oligations to discharge.");
				return;
			}

			// Check is PO elements are supported
			IProofObligationList goodPol = new ProofObligationList();
			IProofObligationList badPol = new ProofObligationList();
			for (IProofObligation po : pol) {
				TPUnsupportedCollector tpu = new TPUnsupportedCollector();
				// check if the po is supported
				List<UnsupportedElementInfo> unsupports = tpu
						.getUnsupporteds(po.getValueTree().getPredicate());
				if (unsupports.isEmpty()) {
					goodPol.add(po);
				} else {
					badPol.add(po);
				}
			}

			if (goodPol.isEmpty()) {
				popBadPol(
						"PO generation and export failed.", "None of the Proof Obligations are currently supported by the theorem prover",
						badPol);
				return;
			}

			// Create project folder (needs to be timestamped)
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();
			// Using DateFormat format method we can create a string
			// representation of a date with the defined format.
			String date = df.format(today);

			IFolder pogFolder = cmlProj.getModelBuildPath().getOutput()
					.getFolder(new Path("POG/" + date));
			IFolder modelBk = pogFolder.getFolder("model");
			if (!pogFolder.exists()) {
				// if generated folder doesn't exist
				if (!pogFolder.getParent().getParent().exists()) {
					// create 'generated' folder
					((IFolder) pogFolder.getParent().getParent()).create(true,
							true, new NullProgressMonitor());
					// create 'POG' folder
					((IFolder) pogFolder.getParent()).create(true, true,
							new NullProgressMonitor());

				}
				// if 'generated' folder does exist and POG folder doesn't exist
				else if (!pogFolder.getParent().exists()) {

					((IFolder) pogFolder.getParent()).create(true, true,
							new NullProgressMonitor());

				}
				// Create timestamped folder
				pogFolder.create(true, true, new NullProgressMonitor());
				pogFolder.refreshLocal(IResource.DEPTH_ZERO,
						new NullProgressMonitor());

				// Create model backup folder
				modelBk.create(true, true, new NullProgressMonitor());
				modelBk.refreshLocal(IResource.DEPTH_ZERO,
						new NullProgressMonitor());
			}
			// Save the original model to the Isabelle folder for reference
			cmlProj.getModel().backup(modelBk);

			IFile lastPogThy=null;
			
			for (ICmlSourceUnit sourceUnit : model.getSourceUnits()) {
				// create a generated thy file for the model
				String name = sourceUnit.getFile().getName();
				String fileName = name.substring(0,
						name.length()
								- (sourceUnit.getFile().getFileExtension()
										.length() + 1));
				String thyFileName = fileName + ".thy";
				IFile thyFile = pogFolder.getFile(thyFileName);
				translateCmltoThy(model, thyFile, thyFileName);

				// Create empty thy file which imports generated file
				IFile pogThyFile = pogFolder.getFile(fileName + "_PO.thy");
				createPogThyClassic(model, pogThyFile, thyFileName, goodPol);
				lastPogThy = pogThyFile;
			}
			
			if (badPol.isEmpty()) {
				MessageDialog.openInformation(null, "Symphony",
						"PO generation and export complete.");
				
				// open Isabelle perspective and file
				try {
					PlatformUI.getWorkbench().showPerspective(
							TPConstants.ISABELLE_PERSPECTIVE_ID, site.getWorkbenchWindow());
					if (lastPogThy !=null){
						IEditorDescriptor desc = PlatformUI.getWorkbench().
						        getEditorRegistry().getDefaultEditor(lastPogThy.getName());
						site.getPage().openEditor(new FileEditorInput(lastPogThy), desc.getId());
					}
	
				} catch (WorkbenchException e) {

					e.printStackTrace();
				}
				
				
			} else {
				popBadPol(
						"PO generation and export incomplete.", "Some POs are currently not supported by the theorem prover.",
						badPol);

			}
		} catch (Exception e) {
			e.printStackTrace();
			popErrorMessage(e.getMessage());
			CmlTPPlugin.log(e);
		}
	}
	
	/****
	 * Method to create a new THY file for a model's proof obligations.
	 * 
	 * @param model
	 * @param pogThyFile
	 * @param thyFileName
	 * @param pol
	 * @return
	 */
	private IFile createPogThyClassic(ICmlModel model, IFile pogThyFile,
			String thyFileName, IProofObligationList pol) {

		// Get the thy string for a given model and it's proof obligations
		StringBuilder sb = new StringBuilder();
		
		
		sb.append(this.generateThyHeader(thyFileName));
		
		String postring;
		
		for (IProofObligation ipo : pol){
			postring = TPVisitor.generatePoStr(model.getAst(), ipo);
			sb.append(postring+"\n\n");
		}
		
		
		sb.append(generateThyFooter());

		// create the file
		try {
			pogThyFile.delete(true, null);
			pogThyFile.create(new ByteArrayInputStream(sb.toString()
					.getBytes()), true, new NullProgressMonitor());
		} catch (CoreException e) {
			CmlTPPlugin.log(e);
		}

		return pogThyFile;
	}



	private boolean checkUnsupporteds(IProject proj) throws AnalysisException {

		ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);

		if (!CmlProjectUtil.typeCheck(window.getShell(), cmlProj)) {
			MessageDialog.openError(null, "COMPASS", "Errors in model.");
			return true;
		}

		List<UnsupportedElementInfo> uns = new TPUnsupportedCollector()
				.getUnsupporteds(cmlProj.getModel().getAst());

		if (uns.isEmpty()) {

			return false;
		} else {
			cmlProj.addUnsupportedMarkers(uns);
			MessageDialog.openInformation(null, "COMPASS", UNSUPPORTED_ELEMENTS_MSG);
			return true;
		}
	}
	

	

	public void explicitCheckUnsupported(IProject proj)
			throws AnalysisException {
		if (!checkUnsupporteds(proj)) {
			MessageDialog.openInformation(null, "COMPASS",
					"No unsupported elements detected.");
		}

	}

	private void createEmptyThy(IFile file, String modelThyName) {
		String thmString = TPVisitor.generateEmptyThyStr(modelThyName);

		try {
			file.delete(true, null);
			file.create(new ByteArrayInputStream(thmString.toString()
					.getBytes()), true, new NullProgressMonitor());

		} catch (CoreException e) {
			CmlTPPlugin.log(e);
		}
	}

	private IFile translateCmltoThy(ICmlModel model, IFile outputFile,
			String thyFileName) throws IOException, AnalysisException {
		String thmString = "";
		try {
			List<INode> ast = model.getAst();
			thmString = TPVisitor.generateThyStr(ast, thyFileName);
		} catch (UnhandledSyntaxException use) {
			thmString = use.getString();
			popErrorMessage(use.getErrorString());
		} finally {
			try {
				outputFile.delete(true, null);
				outputFile.create(new ByteArrayInputStream(thmString.toString()
						.getBytes()), true, new NullProgressMonitor());

				// set .thy file to be read only
				ResourceAttributes attributes = new ResourceAttributes();
				attributes.setReadOnly(true);
				outputFile.setResourceAttributes(attributes);

			} catch (CoreException e) {
				CmlTPPlugin.log(e);
			}
		}

		return outputFile;
	}

	// Switch to the Isabelle perspective automatically
	private void showIsabelle(final ICmlProject project, ICmlModel model,
			final LinkedList<IFile> files) {
		site.getPage().getWorkbenchWindow().getShell().getDisplay()
				.asyncExec(new Runnable() {

					public void run() {
						TPPluginUtils tpu = new TPPluginUtils(site);
						tpu.openTPPerspective();
						for (IFile file : files) {
							tpu.openThyFile(file);
						}
					}

				});

	}
	
	public void enqueuePO(IProofObligation ipo, IDocument document, List<INode> ast) throws BadLocationException, CoreException{
		String isaPO = TPVisitor.generatePoStr(ast, ipo);
		addLine(document, isaPO);
	}
	
	private void addLine(IDocument doc, String line) throws BadLocationException{
		 int offset = doc.getLineOffset(doc.getNumberOfLines()-1);
		 doc.replace(offset, 0, line+"\n");
	}
	
	

	/*****
	 * PLACEHOLDER FOR NOW - SHOULD TIE IN WITH COMMAND, FUNCTIONALITY NEEDS
	 * INTERTWINING WITH TP STUFF BETTER, TOO.
	 */
	public void genPOsDev(ICmlProject cmlProj) {
		try {
			// Check there are no type errors.
			if (!CmlProjectUtil.typeCheck(this.window.getShell(), cmlProj)) {
				popErrorMessage("Can not produce theory file for theorem Proving. \n There are type errors in model.");
				return;
			}
			// Grab the model from the project
			final ICmlModel model = cmlProj.getModel();

			IProofObligationList pol = PogPubUtil
					.generateProofObligations(model.getAst());
			if (pol.isEmpty()) {
				popErrorMessage("There are no Proof Oligations to discharge.");
				return;
			}

			// Check is PO elements are supported
			IProofObligationList goodPol = new ProofObligationList();
			IProofObligationList badPol = new ProofObligationList();
			for (IProofObligation po : pol) {
				TPUnsupportedCollector tpu = new TPUnsupportedCollector();
				// check if the po is supported
				PExp pred = po.getValueTree().getPredicate();
				List<UnsupportedElementInfo> unsupports = tpu
						.getUnsupporteds(pred);
				if (unsupports.isEmpty()) {
					goodPol.add(po);
				} else {
					badPol.add(po);
				}
			}

			if (goodPol.isEmpty()) {
				popBadPol(
						"PO generation and export failed.", "All Proof Obligations contain elements not currently supported by the theorem prover.",
						badPol);
				return;
			}
			
			if (!badPol.isEmpty()) {popBadPol(
					"PO generation and export incomplete.", "Some POs contain elements not currently supported  by the theorem prover. Only the fully-supported "
							+ "POs were generated.",
					badPol);
			}
			
			// we know we have a session because we checked for it outside
			Session sess = IsabelleCore.isabelle().session().get();
			

			IDocumentProvider prov = new TextFileDocumentProvider();
			
			// Create project folder (needs to be timestamped)
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();
			// Using DateFormat format method we can create a string
			// representation of a date with the defined format.
			String date = df.format(today);

			IFolder pogFolder = cmlProj.getModelBuildPath().getOutput()
					.getFolder(new Path("POG/" + date));
			IFolder modelBk = pogFolder.getFolder("model");
			if (!pogFolder.exists()) {
				// if generated folder doesn't exist
				if (!pogFolder.getParent().getParent().exists()) {
					// create 'generated' folder
					((IFolder) pogFolder.getParent().getParent()).create(true,
							true, new NullProgressMonitor());
					// create 'POG' folder
					((IFolder) pogFolder.getParent()).create(true, true,
							new NullProgressMonitor());

				}
				// if 'generated' folder does exist and POG folder doesn't exist
				else if (!pogFolder.getParent().exists()) {

					((IFolder) pogFolder.getParent()).create(true, true,
							new NullProgressMonitor());

				}
				// Create timestamped folder
				pogFolder.create(true, true, new NullProgressMonitor());
				pogFolder.refreshLocal(IResource.DEPTH_ZERO,
						new NullProgressMonitor());

				// Create model backup folder
				modelBk.create(true, true, new NullProgressMonitor());
				modelBk.refreshLocal(IResource.DEPTH_ZERO,
						new NullProgressMonitor());
			}
			// Save the original model to the Isabelle folder for reference
			cmlProj.getModel().backup(modelBk);

			for (ICmlSourceUnit sourceUnit : model.getSourceUnits()) {
				// create a generated thy file for the model
				String name = sourceUnit.getFile().getName();
				String fileName = name.substring(0,
						name.length()
								- (sourceUnit.getFile().getFileExtension()
										.length() + 1));
				String thyFileName = fileName + ".thy";
				IFile thyFile = pogFolder.getFile(thyFileName);
				translateCmltoThy(model, thyFile, thyFileName);
				
				// submit the file to isabelle
				String dirName = modelBk.getLocationURI().toString();
				String fullName = thyFile.getLocationURI().toString();
				TheoryLoader.addTheory(fullName, dirName, fileName, sess, thyFile, prov);

				
				// Create empty thy file which imports generated file
				IFile pogThyFile = pogFolder.getFile(fileName + "_PO.thy");
				createPogThy(pogThyFile, generateEmptyThy(thyFileName));
				
				String pogFullName = pogThyFile.getLocationURI().toString();
			
				EditDocumentModel pogEDM = TheoryLoader.addTheory(pogFullName ,dirName, fileName + "_PO", sess, pogThyFile, prov);
				
				PogPluginUtils.openPoviewPerspective(site);
				PogPluginUtils.redrawPOs(cmlProj, goodPol);
			    
				// Start Proof Session			
				ProofSess ps = new ProofSess(pogEDM, cmlProj, goodPol, cmlProj.getModel().getAst(), prov, sess);
                ps.init();
				cmlProj.getModel().setAttribute(TPConstants.PROOF_SESSION_ID, ps);
				
				
				
			}
	
				MessageDialog.openInformation(null, "Symphony",
						"POs generated and ready for discharge.");

		} catch (Exception e) {
			e.printStackTrace();
			popErrorMessage(e.getMessage());
			CmlTPPlugin.log(e);
		}
	}

	private String generateThyHeader(String thyFileName) {
		//retrieve the file name without the .thy file extension
		String thyName = thyFileName.substring(0, thyFileName.lastIndexOf('.'));
		String poThyName = thyName+ "_PO";

		StringBuilder sb = new StringBuilder();
		//Add thy header 
		sb.append("theory " + poThyName + " \n" + "  imports utp_cml " + thyName +"\n"
				+ "begin \n" + "\n");
		sb.append("text {* Auto-generated THY file for proof obligations generated for "+  thyName + ".cml *}\n\n");
		
		return sb.toString();
	}
	
	private String generateThyFooter(){
		return "\n" + "end";
	}
	
	public String generateEmptyThy(String thyFileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(this.generateThyHeader(thyFileName));	
		sb.append(this.generateThyFooter());
		return sb.toString();
	}
	
	
	
private 	void popBadPol(String msg, String reason, IProofObligationList badPol) {
		MultiStatus bads = new MultiStatus(TPConstants.PLUGIN_ID, 1, reason,null);
		for (IProofObligation po : badPol) {
			bads.add(sFromPo(po));
		}

		ErrorDialog.openError(null, "Symphony", msg, bads);
	}


	private Status sFromPo(IProofObligation po) {
		String s = po.toString();
		
		Status r = new Status(IStatus.ERROR, TPConstants.PLUGIN_ID, s);
		return r;
	}

	/****
	 * Method to create a new THY file for a model's proof obligations.
	 * 
	 * @param model
	 * @param pogThyFile
	 * @param thyFileName
	 * @param pol
	 * @return
	 */
	private IFile createPogThy(IFile pogThyFile,
			String header) {



		// create the file
		try {
			pogThyFile.delete(true, null);
			pogThyFile.create(new ByteArrayInputStream(header
					.getBytes()), true, new NullProgressMonitor());
		} catch (CoreException e) {
			CmlTPPlugin.log(e);
		}

		return pogThyFile;
	}

	// REMOVING SCALA STUFF FOR NOW - GET PROOF OF CONCEPT THY GEN SORTED FIRST.
	// Isabelle isabelle = IsabelleCore.isabelle();
	// Session session = null;
	//
	// if (isabelle.session().isDefined())
	// {
	// session = isabelle.session().get();
	// } else
	// {
	// popErrorMessage("Isabelle is not started. See http://www.cl.cam.ac.uk/research/hvg/Isabelle/");
	// return;
	// }
	//
	// if (tpListener == null)
	// {
	// tpListener = new TPListener(isabelle.session().get(), new
	// IPoStatusChanged() {
	//
	// @Override
	// public void statusChanges(IProofObligation po) {
	// CmlProofObligationList poList =
	// project.getModel().getAttribute(POConstants.PO_REGISTRY_ID,
	// CmlProofObligationList.class);
	//
	// PogPluginRunner.redrawPos(project, poList);
	//
	// }
	// });
	// tpListener.init();
	// }
	//
	//
	// if (project == null)
	// {
	// popErrorMessage("No project selected.");
	// return;
	// }
	//
	// if (CmlProjectUtil.typeCheck(shell, project))
	// {
	// ICmlModel model = project.getModel();
	//
	// CmlProofObligationList poList =
	// model.getAttribute(POConstants.PO_REGISTRY_ID,
	// CmlProofObligationList.class);
	//
	// if (poList == null)
	// {
	// popErrorMessage("There are no Proof Oligations to discharge.");
	// return;
	// }
	//
	// //Translate CML specification files to Isabelle
	// IFolder output = project.getModelBuildPath().getOutput().getFolder(new
	// Path("Isabelle"));
	// if(!output.exists())
	// {
	// if (!output.getParent().exists())
	// {
	// ((IFolder) output.getParent()).create(true, true, new
	// NullProgressMonitor());
	//
	// }
	// output.create(true, true, new NullProgressMonitor());
	// output.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
	// }
	//
	// for (ICmlSourceUnit sourceUnit : model.getSourceUnits())
	// {
	// String name = sourceUnit.getFile().getName();
	// String thyFileName =
	// name.substring(0,name.length()-sourceUnit.getFile().getFileExtension().length())+".ity";
	// translateCmltoThy(sourceUnit,output.getFile(thyFileName));
	// }
	//
	// IsabelleTheory ithy = model.getAttribute(TPConstants.PLUGIN_ID,
	// IsabelleTheory.class);
	//
	// if (ithy == null )
	// {
	// IProject p = ((IProject) project.getAdapter(IProject.class));
	// String thyName = p.getName()+"_POs";
	// ithy = new IsabelleTheory(session,
	// thyName,output.getLocation().toString());
	// ithy.init();
	// TPPluginUtils2.addThyToListener(ithy, tpListener, model);
	//
	// model.setAttribute(TPConstants.PLUGIN_ID, ithy);
	// Object bob = model.getAttribute(TPConstants.PLUGIN_ID,
	// IsabelleTheory.class);
	// System.out.println(bob.toString());
	// }
	//
	// for (IProofObligation po : poList)
	// {
	// ithy.addThm(ithy.new IsabelleTheorem("po" + po.getUniqueName(), "True",
	// "auto"));
	// }
	// }
	//
	// } catch (Exception e)
	// {
	// e.printStackTrace();
	// popErrorMessage(e.getMessage());
	// Activator.log(e);
	// }

	private void popErrorMessage(String message) {
		MessageDialog.openError(window.getShell(), "Symphony", message);
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
