package eu.compassresearch.ide.modelchecker;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerVisitor;
import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.common.RegistryFactory;


public class MCHandler extends AbstractHandler {

	private Registry registry;
	private IWorkbenchWindow window;
	private MessageConsoleStream console;
	private CMLModelcheckerVisitor adaptor;
	private IFormulaIntegrator mc;
	
	public MCHandler() {
		RegistryFactory factory = eu.compassresearch.core.common.RegistryFactory.getInstance(MCConstants.MC_REGISTRY_ID);
		this.registry = factory.getRegistry();
	}
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		//IProject proj = MCPluginUtility.getCurrentlySelectedProject();
		//if (proj == null){
		//	popErrorMessage("No project selected.");
		//	return null;
		//}
		
		try {
					//get the selected cmlfile and analyse it
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					//MessageDialog.openInformation(
					//		window.getShell(),
					//		"Test command",
					//		"window " + window);
					ISelection selection = window.getSelectionService().getSelection();
					//MessageDialog.openInformation(
					//		window.getShell(),
					//		"Test command",
					//		"selection " + selection);
					if (selection instanceof IStructuredSelection) {
					    IStructuredSelection ssel = (IStructuredSelection) selection;
					    Object obj = ssel.getFirstElement();
					    //IFile cmlFile = (IFile) Platform.getAdapterManager().getAdapter(obj,IFile.class);
					    IResource cmlFile = (IResource) Platform.getAdapterManager().getAdapter(obj,IResource.class);
					    if (cmlFile == null) {
					        if (obj instanceof IAdaptable) {
					            cmlFile = (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
					        }
					    }
					    if (cmlFile != null) {
					        if("cml".equalsIgnoreCase(cmlFile.getFileExtension())){
					        	//MessageDialog.openInformation(
					    		//		window.getShell(),
					    		//		"Test command",
					    		//		"Model checker will analyse " + event.getParameter("eu.compassresearch.ide.modelchecker.property") + " for " + cmlFile.getLocation().toFile().getAbsolutePath());
					        	String propertyToCheck = this.getProperty(event.getParameter("eu.compassresearch.ide.modelchecker.property"));
					        	//CmlSourceUnit source = CmlSourceUnit.getFromFileResource((IFile) cmlFile);
					        	//ICmlSourceUnit source = (ICmlSourceUnit) cmlFile.getAdapter(ICmlSourceUnit.class);
					        	
					        	// the code bellow work but it is dangerous for demonstrations
					        	//if (!CmlTypeChecker.Utils.isWellType(source.getSourceAst())){
								//	popErrorMessage("There were type errors in "
								//			+ source.getFile().getName());
								//	return null;
								//}
					        	
					        	PSource sourceAst = Utilities.makeSourceFromFile(cmlFile.getLocation().toFile().getAbsolutePath());
					        	
								FormulaResult formulaOutput = new FormulaResult();
								MCJob job = new MCJob("Model checker progress", sourceAst, propertyToCheck);
								formulaOutput = job.getFormulaResult();
								job.schedule();
								
								MessageConsole mc = new MessageConsole("Model checker console", null);
								ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { mc });
								this.console = mc.newMessageStream();
								this.console.setActivateOnWrite(true);
								this.console.write("File: "+sourceAst.toString()+"\n");
								this.console.write("Result: " + (formulaOutput.isSatisfiable()?"SAT":"UNSAT") + "\n");
								this.console.write("Base of Facts: \n");
								this.console.write(formulaOutput.getFacts());
								FormulaResultWrapper frw = new FormulaResultWrapper(formulaOutput, propertyToCheck);
								MCPluginDoStuff mcp = new MCPluginDoStuff(window.getActivePage().getActivePart().getSite(), cmlFile, frw);
								mcp.run();
								registry.store(sourceAst, frw);
					        }else{
					        	MessageDialog.openInformation(
					    				window.getShell(),
					    				"Test command",
					    				"Only CML files can be analysed!");
					        }
					    }
					}
			
		} catch (Exception e) {
			e.printStackTrace();
			popErrorMessage(e.getMessage());
			//if(mc != null){
			//	try {
			//		mc.finalize();
			//	} catch (Throwable e1) {
			//		popErrorMessage(e1.getMessage());
			//	}
			//}
			popErrorMessage(e.getMessage());
		}
		return null;
	}
	
	private String getProperty(String parameterValue){
		String property = Utilities.DEADLOCK_PROPERTY;
		
		if(parameterValue.equals("LIVELOCK")){
			property = Utilities.LIVELOCK_PROPERTY;
		} else if(parameterValue.equals("NONDETERMINISM")){
			property = Utilities.NONDETERMINISM_PROPERTY;
		}
		
		return property;
	}
	/*private FormulaResult getMCOutputFromSource(PSource source, String propertyToCheck) throws Exception {

		FormulaResult mcResult;
		//StringBuilder sb = new StringBuilder();
		//PSource psAux = source.getSourceAst();
		//this.adaptor = new CMLModelcheckerVisitor(psAux);
		this.adaptor = new CMLModelcheckerVisitor(source);
		this.adaptor.setPropertyToCheck(propertyToCheck);
		//this.mc = StandAloneFormulaIntegrationFactory.getInstance().createFormulaIntegrator();
		this.mc = FormulaIntegrator.getInstance();
		String[] codes = adaptor.generateFormulaCodeForAll();
		//for (int i = 0; i < codes.length; i++) {
				//save to a temp file, run formula, get and show the result for each file 
				
		mcResult = mc.analyse(codes[0]);
				//mcResult = mc.analyse(codes[i]);
				//sb.append("********************************\n");
				//sb.append("Result: " + (mcResult.isSatisfiable()?"SAT":"UNSAT") + "\n");
				//double loadTime = mcResult.getElapsedTimeLoad();
				//double solveTime = mcResult.getElapsedTimeSolve();
				//sb.append("Analysis time (load + solve) = " + "(" + loadTime + " + " + solveTime + ") = " + (loadTime+solveTime) + " seconds\n"); 
				//sb.append("Base of Facts: \n");
				//sb.append(mcResult.getFacts());
		//}
		//return sb.toString();
		return mcResult;
	}*/
	private void popErrorMessage(String message) {
		MessageDialog.openInformation(null, "COMPASS",
				"Could not generate Formula files.\n\n" + message);
	}
	@Override
	public void dispose() {
		super.dispose();
		if(mc != null){
			try {
				mc.finalize();
			} catch (Throwable e) {
				popErrorMessage(e.getMessage());
			}
		}
	}
	
}
