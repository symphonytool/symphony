package eu.compassresearch.ide.theoremprover.commands;

import isabelle.eclipse.core.text.EditDocumentModel;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligation;

import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.pog.PogPluginUtils;
import eu.compassresearch.ide.theoremprover.ProofSess;
import eu.compassresearch.ide.theoremprover.TPConstants;
import eu.compassresearch.ide.theoremprover.TPPluginDoStuff;
import eu.compassresearch.ide.theoremprover.TPPluginUtils;

public class EnqPoHandler extends AbstractHandler
{	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		
		
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		
		
		TPPluginDoStuff doer = new TPPluginDoStuff(HandlerUtil.getActiveWorkbenchWindow(event), page.getActivePart().getSite());
		
		IProofObligation po = null;
		try {
			po = PogPluginUtils.getMainView().getCurrentlySelectedPO();
			
			IProject proj = TPPluginUtils.getCurrentlySelectedProject();
			
			ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);

			ProofSess ps = cmlProj.getModel().getAttribute(TPConstants.PROOF_SESSION_ID, ProofSess.class);
			
			EditDocumentModel edm = ps.poEDM();
			List<INode> ast = ps.ast();
			
			ps.enqueuePO(po);
			// doer.enqueuePO(po, edm.document(), ast);
/*
			edm.submitFullPerspective(new NullProgressMonitor());
		    ps.thyProvider().saveDocument(new NullProgressMonitor(), null, edm.document(), true);
		    */
		    
//		    prov.connect();
//		    prov.
//		    ps.getPoEDM().document().
			
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return null;
	}


}
