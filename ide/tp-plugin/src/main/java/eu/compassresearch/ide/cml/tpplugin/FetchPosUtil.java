package eu.compassresearch.ide.cml.tpplugin;

import isabelle.Session;
import isabelle.eclipse.core.IsabelleCore;
import isabelle.eclipse.core.app.Isabelle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ide.core.IVdmModel;
import org.overture.ide.core.resources.IVdmProject;
import org.overture.ide.ui.utility.VdmTypeCheckerUi;
import org.overture.pog.obligation.ProofObligation;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.common.RegistryFactory;
import eu.compassresearch.ide.cml.pogplugin.POConstants;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;
import eu.compassresearch.theoremprover.IsabelleTheory;
import eu.compassresearch.theoremprover.TPVisitor;
import eu.compassresearch.theoremprover.ThmType;
import eu.compassresearch.theoremprover.ThmValue;

public class FetchPosUtil
{
//	private IWorkbenchWindow window = null;
	private TPListener tpListener = null;
	private Shell shell;
	private ICmlProject project;

	public FetchPosUtil(Shell shell, ICmlProject project)
	{
		this.shell = shell;
		this.project = project;
	}

	public void fetchPOs()
	{
		try
		{
			Isabelle isabelle = IsabelleCore.isabelle();
			Session session = null;

			if (isabelle.session().isDefined())
			{
				session = isabelle.session().get();
			} else
			{
				popErrorMessage("Isabelle is not started. See http://www.cl.cam.ac.uk/research/hvg/Isabelle/");
				return;
			}

			if (tpListener == null)
			{
				tpListener = new TPListener(isabelle.session().get());
				tpListener.init();
			}

			Registry registry = RegistryFactory.getInstance(POConstants.PO_REGISTRY_ID).getRegistry();

//			IProject proj = TPPluginUtils.getCurrentlySelectedProject();
			if (project == null)
			{
				popErrorMessage("No project selected.");
				return;
			}
			// Check project is built
//			ICmlProject cmlProject = (ICmlProject) proj.getAdapter(ICmlProject.class);

//			if (cmlProject == null)
//			{
//				return;
//			}

			if (CmlProjectUtil.typeCheck(shell, project))
			{

				ICmlModel model = project.getModel();
				
				CMLProofObligationList poList = model.getAttribute(POConstants.PO_REGISTRY_ID, new CMLProofObligationList());
				
				if (poList == null)
				{
					popErrorMessage("There are no Proof Oligations to discharge.");
					return;
				}
				
				//Translate CML specification files to Isabelle
				IFolder output = project.getModelBuildPath().getOutput().getFolder(new Path("Isabelle"));
				if(!output.exists())
				{
					output.create(true, true, new NullProgressMonitor());
					output.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
				}
				
				for (ICmlSourceUnit sourceUnit : model.getSourceUnits())
				{
					String name = sourceUnit.getFile().getName();
					String thyFileName = name.substring(0,name.length()-sourceUnit.getFile().getFileExtension().length())+".ity";
					translateCmltoThy(sourceUnit,output.getFile(thyFileName));
				}
//				ArrayList<IResource> cmlFiles = TPPluginUtils.getAllCFilesInProject(proj);

//				for (IResource cmlFile : cmlFiles)
//				{
//					// May return a null if the adapter fails to convert
//					ICmlSourceUnit cmlSource = (ICmlSourceUnit) cmlFile.getAdapter(ICmlSourceUnit.class);
//					CMLProofObligationList poList = registry.lookup(cmlSource.getSourceAst(), CMLProofObligationList.class);
//					if (poList == null)
//					{
//						popErrorMessage("There are no Proof Oligations to discharge.");
//						return;
//					}
//					getThyFromCML(cmlFile);

//					IsabelleTheory ithy = registry.lookup(cmlSource.getSourceAst(), IsabelleTheory.class);
				IsabelleTheory ithyDefault = null;
				IsabelleTheory ithy = model.getAttribute(ITpConstants.PLUGIN_ID, ithyDefault);

					if (ithy == null )
					{
						IProject p = ((IProject) project.getAdapter(IProject.class));
//						String cmlName = cmlFile.getName();
						String thyName = p.getName()+"_POs";
						ithy = new IsabelleTheory(session, thyName,output.getLocation().toString());
						ithy.init();
						for (PSource source : model.getAstSource())
						{
							TPPluginUtils2.addThyToListener(ithy, tpListener, source);
						}
						
//						registry.store(cmlSource.getSourceAst(), ithy);
						model.setAttribute(ITpConstants.PLUGIN_ID, ithy);
					}

					for (ProofObligation po : poList)
					{
						ithy.addThm(ithy.new IsabelleTheorem("po" + po.name, "True", "auto"));
					}

//				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
			popErrorMessage(e.getMessage());
			Activator.log(e);
		}

	}

	private void popErrorMessage(String message)
	{
		MessageDialog.openInformation(shell, "COMPASS", "Could not generate THY.\n\n"
				+ message);
	}

	private void translateCmltoThy(ICmlSourceUnit source, IFile outputFile) throws IOException,
			AnalysisException
	{
//		ICmlSourceUnit source = (ICmlSourceUnit) cmlFile.getAdapter(ICmlSourceUnit.class);

//		String cmlLoc = cmlFile.getLocation().toString();
//		String thyFile = cmlLoc.replaceAll("\\.cml", ".thy");

		TPVisitor tpv = new TPVisitor();
		source.getParseNode().apply(tpv);

		String name = source.getFile().getName();

		String thyName = name.substring(0, name.lastIndexOf("."));
		StringBuilder sb = new StringBuilder();

		sb.append("theory " + thyName + " \n" + "  imports utp_vdm \n"
				+ "begin \n" + "\n");

		sb.append("text {* VDM value declarations *}\n\n");

		for (ThmValue tv : tpv.getValueList())
		{
			sb.append(tv.toString());
			sb.append("\n");
		}

		sb.append("\n");
		sb.append("text {* VDM type declarations *}\n\n");

		for (ThmType ty : tpv.getTypeList())
		{
			sb.append(ty.toString());
			sb.append("\n");
		}

		sb.append("\n");

		sb.append("\n" + "end");

//		File thy = new File(thyFile);
//		FileWriter fw = new FileWriter(thy);
//		fw.write(sb.toString());
//		fw.flush();
//		fw.close();
//		outputFile.getContents().
		try{
		outputFile.create( new ByteArrayInputStream(sb.toString().getBytes()), true, new NullProgressMonitor());
		}catch(CoreException e)
		{
			Activator.log(e);
		}
		
		return;

	}

}
