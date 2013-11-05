package eu.compassresearch.ide.theoremprover;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.overture.ast.analysis.AnalysisException;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;

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

	/**
	 * This operation is called by the POG perspective and needs some attention... Currently it is repeating some of the thy gen code 
	 * and this can be cut - needs to consider how best to ensure thy file is available, and then gen a new thy file for the POs. 
	 */
	public void fetchPOs()
	{
//		try
//		{
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

	}

	private void popErrorMessage(String message)
	{
		MessageDialog.openInformation(shell, "Symphony", "Could not generate THY.\n\n"
				+ message);
	}

	private void translateCmltoThy(ICmlSourceUnit source, IFile outputFile) throws IOException,
			AnalysisException
	{
//		TPVisitor tpv = new TPVisitor();
//		source.getParseNode().apply(tpv);
//
//		String name = source.getFile().getName();
//
//		String thyName = name.substring(0, name.lastIndexOf("."));
//		StringBuilder sb = new StringBuilder();
//
//		sb.append("theory " + thyName + " \n" + "  imports utp_vdm \n"
//				+ "begin \n" + "\n");
//
//		sb.append("text {* VDM value declarations *}\n\n");
//
//		for (ThmValue tv : tpv.getValueList())
//		{
//			sb.append(tv.toString());
//			sb.append("\n");
//		}
//
//		sb.append("\n");
//		sb.append("text {* VDM type declarations *}\n\n");
//
//		for (ThmType ty : tpv.getTypeList())
//		{
//			sb.append(ty.toString());
//			sb.append("\n");
//		}
//
//		sb.append("\n");
//
//		sb.append("\n" + "end");
//
//		try{
//		outputFile.delete(true, null);
//		outputFile.create( new ByteArrayInputStream(sb.toString().getBytes()), true, new NullProgressMonitor());
//		}catch(CoreException e)
//		{
//			Activator.log(e);
//		}
//		
//		return;

	}

}
