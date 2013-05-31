package eu.compassresearch.ide.cml.tpplugin;

import isabelle.Protocol;
import isabelle.Session;
import isabelle.eclipse.core.IsabelleCore;
import isabelle.eclipse.core.app.Isabelle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;
import org.overture.pog.obligation.ProofObligation;

import scala.Option;
import scala.util.Either;

import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.ide.cml.pogplugin.POConstants;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.common.RegistryFactory;
import eu.compassresearch.ide.cml.ui.editor.core.dom.CmlSourceUnit;
import eu.compassresearch.ide.cml.ui.editor.core.dom.ICmlSourceUnit;
import eu.compassresearch.theoremprover.TPVisitor;
import eu.compassresearch.theoremprover.ThmType;
import eu.compassresearch.theoremprover.ThmValue;

public class TPBasicAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	// private IsabelleTheory ithy = null;
	private int thmCount = 0;
	// private CMLProofObligationList poList;
	private TPListener tpListener;

	@Override
	public void run(IAction action) {
		try {
			Isabelle isabelle = IsabelleCore.isabelle();
			Session session = null;

			if (isabelle.session().isDefined()) {
				session = isabelle.session().get();
			} else {
				popErrorMessage("Isabelle is not started");
				return;
			}

			// if (tpListener = null) tpListener = new
			// TPListener(isabelle.session().get(), ithy);

			/*
			 * else { ithy.addThm(new IsabelleTheorem("simpleLemma" + thmCount,
			 * "True", "by simp\n")); thmCount++; }
			 */
			Registry registry = RegistryFactory.getInstance(
					POConstants.PO_REGISTRY_ID).getRegistry();

			IProject proj = TPPluginUtils.getCurrentlySelectedProject();
			if (proj == null) {
				popErrorMessage("No project selected.");
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

					ArrayList<IResource> cmlFiles = TPPluginUtils
							.getAllCFilesInProject(proj);

					for (IResource cmlFile : cmlFiles) {
						// May return a null if the adapter fails to convert
						ICmlSourceUnit cmlSource = (ICmlSourceUnit) cmlFile
								.getAdapter(ICmlSourceUnit.class);
						CMLProofObligationList poList = registry.lookup(
								cmlSource.getSourceAst(),
								CMLProofObligationList.class);

						JIsabelleTheory jthy = registry
								.lookup(cmlSource.getSourceAst(),
										JIsabelleTheory.class);
						IsabelleTheory ithy = null;

						if (jthy == null) {
							String cmlLoc = cmlFile.getLocation().toString();
							String poFile = cmlLoc.replaceAll("\\.cml",
									"-POs.thy");
							ithy = new IsabelleTheory(session, poFile, proj
									.getLocation().toString());
							jthy = new JIsabelleTheory(ithy);
							ithy.init();
							registry.store(cmlSource.getSourceAst(), jthy);
						} else {
							ithy = jthy.getIsabelleTheory();
						}

						for (ProofObligation po : poList) {
							ithy.addThm(new IsabelleTheorem(po.name, "True",
									"by auto\n"));
						}

						getThyFromCML(cmlFile);
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
