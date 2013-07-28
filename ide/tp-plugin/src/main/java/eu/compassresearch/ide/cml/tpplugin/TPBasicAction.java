package eu.compassresearch.ide.cml.tpplugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import eu.compassresearch.ide.core.resources.ICmlProject;

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
				popErrorMessage("No project selected.");
				return;
			}

			ICmlProject cmlProject = (ICmlProject) proj
					.getAdapter(ICmlProject.class);
			
			new FetchPosUtil(this.window.getShell(),cmlProject).fetchPOs();

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

//	private void getThyFromCML(IResource cmlFile) throws IOException,
//			AnalysisException {
//
//		ICmlSourceUnit source = (ICmlSourceUnit) cmlFile
//				.getAdapter(ICmlSourceUnit.class);
//
//		String cmlLoc = cmlFile.getLocation().toString();
//		String thyFile = cmlLoc.replaceAll("\\.cml", ".thy");
//
//		TPVisitor tpv = new TPVisitor();
//		source.getSourceAst().apply(tpv);
//
//		String name = cmlFile.getName();
//
//		String thyName = name.substring(0, name.lastIndexOf("."));
//		StringBuilder sb = new StringBuilder();
//
//		sb.append("theory " + thyName + " \n" + "  imports utp_vdm \n"
//				+ "begin \n" + "\n");
//
//		sb.append("text {* VDM value declarations *}\n\n");
//
//		for (ThmValue tv : tpv.getValueList()) {
//			sb.append(tv.toString());
//			sb.append("\n");
//		}
//
//		sb.append("\n");
//		sb.append("text {* VDM type declarations *}\n\n");
//
//		for (ThmType ty : tpv.getTypeList()) {
//			sb.append(ty.toString());
//			sb.append("\n");
//		}
//
//		sb.append("\n");
//
//		sb.append("\n" + "end");
//
//		File thy = new File(thyFile);
//		FileWriter fw = new FileWriter(thy);
//		fw.write(sb.toString());
//		fw.flush();
//		fw.close();
//
//		return;
//
//	}
	
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
