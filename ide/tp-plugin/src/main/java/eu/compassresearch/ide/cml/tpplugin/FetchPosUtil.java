package eu.compassresearch.ide.cml.tpplugin;

import isabelle.Session;
import isabelle.eclipse.core.IsabelleCore;
import isabelle.eclipse.core.app.Isabelle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;
import org.overture.pog.obligation.ProofObligation;

import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.common.RegistryFactory;
import eu.compassresearch.ide.cml.pogplugin.POConstants;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.theoremprover.IsabelleTheory;
import eu.compassresearch.theoremprover.TPVisitor;
import eu.compassresearch.theoremprover.ThmType;
import eu.compassresearch.theoremprover.ThmValue;

public class FetchPosUtil 
{
	private IWorkbenchWindow window =null;
	private TPListener tpListener = null;

	

	
	public FetchPosUtil (IWorkbenchWindow window){
		this.window = window;	
	}
	
	public void fetchPOs(IProject proj) {
		try {
			Isabelle isabelle = IsabelleCore.isabelle();
			Session session = null;

			if (isabelle.session().isDefined()) {
				session = isabelle.session().get();
			} else {
				popErrorMessage("Isabelle is not started");
				return;
			}

				
			
				
			Registry registry = RegistryFactory.getInstance(
					POConstants.PO_REGISTRY_ID).getRegistry();

	//		IProject proj = TPPluginUtils.getCurrentlySelectedProject();
			if (proj == null) {
				popErrorMessage("No project available.");
				return;
			}
			// Check project is built
			IVdmProject vdmProject = (IVdmProject) proj
					.getAdapter(IVdmProject.class);

			if (vdmProject == null) {
				return;
			}

			final IVdmModel model = vdmProject.getModel();
			if (model.isParseCorrect()) {

				if (!model.isParseCorrect()) {
					return;
					// return new Status(Status.ERROR,
					// IPoviewerConstants.PLUGIN_ID,
					// "Project contains parse errors");
				}

				if (model == null || !model.isTypeCorrect()) {
					VdmTypeCheckerUi.typeCheck(this.window.getShell(),
							vdmProject);
				}

				if (model.isTypeCorrect()) {

					if (tpListener == null) { 
						tpListener = new TPListener(isabelle.session().get(), vdmProject);
						tpListener.init();
					}
					
					ArrayList<IResource> cmlFiles = TPPluginUtils
							.getAllCFilesInProject(proj);

					for (IResource cmlFile : cmlFiles) {
						// May return a null if the adapter fails to convert
						ICmlSourceUnit cmlSource = (ICmlSourceUnit) cmlFile
								.getAdapter(ICmlSourceUnit.class);
						CMLProofObligationList poList = registry.lookup(
								cmlSource.getSourceAst(),
								CMLProofObligationList.class);
						if (poList == null)
						{
							popErrorMessage("There are no Proof Oligations to discharge.");
							return;
						}
						getThyFromCML(cmlFile);
						
						IsabelleTheory ithy = registry.lookup(cmlSource.getSourceAst(), IsabelleTheory.class);

						if (ithy == null) {							
							String cmlName = cmlFile.getName();
							String thyName = cmlName.replaceAll("\\.cml",
									"_POs");
							ithy = new IsabelleTheory(session, thyName, proj
									.getLocation().toString());
							ithy.init();
							TPPluginUtils2.addThyToListener(ithy, tpListener, cmlSource.getSourceAst());
							registry.store(cmlSource.getSourceAst(), ithy);
						} 

						for (ProofObligation po : poList) {
							ithy.addThm(ithy.new IsabelleTheorem("po" + po.name, "True","auto"));
						}

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			popErrorMessage(e.getMessage());
		}

	}
	
	private void popErrorMessage(String message) {
		MessageDialog.openInformation(window.getShell(), "COMPASS",
				"Could not generate THY.\n\n" + message);
	}

	private void getThyFromCML(IResource cmlFile) throws IOException,
			AnalysisException {

		ICmlSourceUnit source = (ICmlSourceUnit) cmlFile
				.getAdapter(ICmlSourceUnit.class);

		String cmlLoc = cmlFile.getLocation().toString();
		String thyFile = cmlLoc.replaceAll("\\.cml", ".thy");

		TPVisitor tpv = new TPVisitor();
		source.getSourceAst().apply(tpv);

		String name = cmlFile.getName();

		String thyName = name.substring(0, name.lastIndexOf("."));
		StringBuilder sb = new StringBuilder();

		sb.append("theory " + thyName + " \n" + "  imports utp_vdm \n"
				+ "begin \n" + "\n");

		sb.append("text {* VDM value declarations *}\n\n");

		for (ThmValue tv : tpv.getValueList()) {
			sb.append(tv.toString());
			sb.append("\n");
		}

		sb.append("\n");
		sb.append("text {* VDM type declarations *}\n\n");

		for (ThmType ty : tpv.getTypeList()) {
			sb.append(ty.toString());
			sb.append("\n");
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
	

	

}
