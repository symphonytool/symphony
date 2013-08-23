package eu.compassresearch.ide.modelchecker;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.GraphBuilder;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.util.GraphViz;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerVisitor;
import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;
import eu.compassresearch.core.common.Registry;
import eu.compassresearch.core.common.RegistryFactory;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;


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
			this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IProject proj = MCPluginUtility.getCurrentlySelectedProject();
			if (proj == null) {
				popErrorMessage(new RuntimeException("No project is selected."));
				return null;
			}
			
			//Get the cml project
			ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);
			
			//Check there are no type errors.
			final Shell shell = this.window.getShell();
			if (!CmlProjectUtil.typeCheck(shell, cmlProj)) {
				popErrorMessage(new RuntimeException("Errors in model."));
				return null;
			}
			
			//Grab the model from the project
			final ICmlModel model = cmlProj.getModel();
			
			//get the selected cmlfile and analyse it
			
			ISelection selection = window.getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ssel = (IStructuredSelection) selection;
				Object obj = ssel.getFirstElement();
				IResource cmlFile = (IResource) Platform.getAdapterManager().getAdapter(obj,IResource.class);
				if (cmlFile == null) {
					if (obj instanceof IAdaptable) {
						cmlFile = (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
					}
				}
				if (cmlFile != null) {
					if("cml".equalsIgnoreCase(cmlFile.getFileExtension())){
						String propertyToCheck = this.getProperty(event.getParameter("eu.compassresearch.ide.modelchecker.property"));
					    
						//Date date = new Date();
						IFolder mcFolder = cmlProj.getModelBuildPath().getOutput().getFolder(new Path("modelchecker"));
						if(!mcFolder.exists()){
							//if generated folder doesn't exist
							IContainer mcParent = mcFolder.getParent();
							if (!mcFolder.getParent().exists()){
								//create 'generated' folder
								//((IFolder) mcFolder.getParent().getParent()).create(true, true, new NullProgressMonitor());
								//create 'model checker' folder
								//((IFolder) mcFolder.getParent()).create(true, true, new NullProgressMonitor());
								((IFolder) mcFolder.getParent()).create(true, true, null);
							}
							//if 'generated' folder does exist and Isabelle folder doesn't exist
							//else if (!mcFolder.getParent().exists()){
							//	((IFolder) mcFolder.getParent()).create(true, true, new NullProgressMonitor());
									
							//}
							//if the model checker folder does not exist
							if (!mcFolder.exists()){
								mcFolder.create(true, true, new NullProgressMonitor());
								//mcFolder.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
								mcFolder.refreshLocal(IResource.DEPTH_ZERO, null);
							}
							
						}
						ICmlSourceUnit selectedUnit = getSelectedSourceUnit(model, (IFile)cmlFile);
						IFile outputFile = translateCmlToFormula(model, (IFile)cmlFile, mcFolder, propertyToCheck);
					
						//PSource sourceAst = Utilities.makeSourceFromFile(cmlFile.getLocation().toFile().getAbsolutePath());
					        	
						FormulaResult formulaOutput = new FormulaResult();
						MCJob job = new MCJob("Model checker progress", outputFile);
						formulaOutput = job.getFormulaResult();
						job.schedule();

						FormulaResultWrapper frw = new FormulaResultWrapper(formulaOutput, null, propertyToCheck);
						
						//if the model is satisfiable then we save the formula output and 
						//to build the graph of the counterexample on demand.
						if(formulaOutput.isSatisfiable()){
							//we build the counterexample
							GraphBuilder gb = new GraphBuilder();
							String dotContent = gb.generateDot(new StringBuilder(formulaOutput.getFacts()));
							//save the graphviz code to a file
							IFile dotFile = writeDotContentToFile(mcFolder,selectedUnit,dotContent);
							//compile the generated graphviz
							GraphViz gv = new GraphViz();
							File file = dotFile.getRawLocation().toFile();
							String fileName = file.getName();
							gv.runDot(file);
							IFile svgFile = mcFolder.getFile(fileName+".svg");
							frw.setSvgFile(svgFile);
						}else{
							throw new Exception("Counterexample is available only for satisfiable models.");
						}
						
						//writeToConsole(cmlFile.getName(), formulaOutput);
					
						
						MCPluginDoStuff mcp = new MCPluginDoStuff(window.getActivePage().getActivePart().getSite(), cmlFile, frw);
						mcp.run();
						registry.store(selectedUnit.getParseNode(), frw);
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
			popErrorMessage(e);
			//if(mc != null){
			//	try {
			//		mc.finalize();
			//	} catch (Throwable e1) {
			//		popErrorMessage(e1.getMessage());
			//	}
			//}
			//popErrorMessage(e.getMessage());
		}
		return null;
	}
	private IFile writeDotContentToFile(IFolder mcFolder,
			ICmlSourceUnit selectedUnit, String dotContent) {
		
		String name = selectedUnit.getFile().getName();
		String dotFileName = name.substring(0,name.length()-selectedUnit.getFile().getFileExtension().length())+"gv";
		IFile outputFile = mcFolder.getFile(dotFileName);
		
		try{
			if(outputFile.exists()){
				outputFile.delete(true, new NullProgressMonitor());
			}
			outputFile.create(new ByteArrayInputStream(dotContent.toString().getBytes()), true, new NullProgressMonitor());
		}catch(CoreException e){
			Activator.log(e);
		}
		return outputFile;
	}
	private ICmlSourceUnit getSelectedSourceUnit(ICmlModel model, IFile selectedFile){
		ICmlSourceUnit selectedCmlSourceUnit = null;
		
		for (ICmlSourceUnit cmlSourceUnit : model.getSourceUnits()) {
			String name = cmlSourceUnit.getFile().getName();
			if(name.equals(selectedFile.getName())){
				selectedCmlSourceUnit = cmlSourceUnit;
				break;
			}
		}
		
		return selectedCmlSourceUnit;
	}
	private IFile translateCmlToFormula(ICmlModel model, IFile selectedFile, 
										IFolder mcFolder, String propertyToCheck) 
				throws IOException,AnalysisException{

		
		ICmlSourceUnit selectedCmlSourceUnit = getSelectedSourceUnit(model,selectedFile);
		
		if(selectedCmlSourceUnit == null){
			throw new AnalysisException("No selected file to convert!");
		}
		String name = selectedCmlSourceUnit.getFile().getName();
		String formulaFileName = name.substring(0,name.length()-selectedCmlSourceUnit.getFile().getFileExtension().length())+"4ml";
		IFile outputFile = mcFolder.getFile(formulaFileName);
		
		List<PDefinition> definitions = selectedCmlSourceUnit.getParseListDefinitions();
		String basicContent = Utilities.readScriptFromFile(Utilities.BASIC_FORMULA_SCRIPT).toString();
		String specificationContent = CMLModelcheckerVisitor.generateFormulaScript(basicContent, definitions,propertyToCheck);
		//String specificationContent = "";
		try{
			if(outputFile.exists()){
				outputFile.delete(true, new NullProgressMonitor());
			}
			outputFile.create(new ByteArrayInputStream(specificationContent.toString().getBytes()), true, new NullProgressMonitor());
			
			//set .fml file to be read only
			//ResourceAttributes attributes = new ResourceAttributes();
			//attributes.setReadOnly(true);
			//outputFile.setResourceAttributes(attributes); 
			

		}catch(CoreException e){
			Activator.log(e);
		}
		
		return outputFile;
	}
	private void writeToConsole(String fileName, FormulaResult formulaOutput)
			throws IOException {
		
		MessageConsole mc = new MessageConsole("Model checker console", null);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { mc });
		this.console = mc.newMessageStream();
		this.console.setActivateOnWrite(true);
		this.console.write("File: "+ fileName +"\n");
		this.console.write("Result: " + (formulaOutput.isSatisfiable()?"SAT":"UNSAT") + "\n");
		this.console.write("Base of Facts: \n");
		this.console.write(formulaOutput.getFacts());
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
	private void popErrorMessage(Throwable e) {
		MessageDialog.openInformation(null, "COMPASS",
				"Could not analyse the specification.\n\n" + e.getClass() + "\n" + e.getMessage());
	}
	private void popErrorMessage(String message) {
		MessageDialog.openInformation(null, "COMPASS",message);
	}
	@Override
	public void dispose() {
		super.dispose();
		if(mc != null){
			try {
				mc.finalize();
			} catch (Throwable e) {
				popErrorMessage(e);
			}
		}
	}
	
	
	
}
