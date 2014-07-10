package eu.compassresearch.ide.modelchecker;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewMCVisitor;
import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.unsupported.UnsupportedElementInfo;
import eu.compassresearch.ide.ui.utility.CmlProjectUtil;


public class MCHandler extends AbstractHandler {

	private static IWorkbenchWindow window;
	//private MessageConsoleStream console;
	private NewMCVisitor adaptor;
	private IFormulaIntegrator mc;
	private IProject proj;
	private String mainProcessName;

	
	public MCHandler() {
		//RegistryFactory factory = eu.compassresearch.core.common.RegistryFactory.getInstance(MCConstants.MC_REGISTRY_ID);
		//this.registry = factory.getRegistry();
	}
	
	public IWorkbenchWindow getWindow(){
		return this.window;
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		this.window = HandlerUtil.getActiveWorkbenchWindow(event);
		this.proj = MCPluginUtility.getCurrentlySelectedProject();
		if (proj == null) {
			MCPluginUtility.popErrorMessage(new RuntimeException("No project is selected."));
			return null;
		}
		
		if(!CmlMCPlugin.FORMULA_OK){
			MCPluginUtility.popErrorMessage(CmlMCPlugin.formulaNotInstalledMsg);
		}else{
			try {
				//Get the cml project
				ICmlProject cmlProj = (ICmlProject) proj.getAdapter(ICmlProject.class);
				
				//Check there are no type errors.
				if (!CmlProjectUtil.typeCheck(window.getShell(), cmlProj)){
					MCPluginUtility.popErrorMessage(new RuntimeException("There are errors in model."));
					return null;
				}
				
				// Check compatibility 
				List<UnsupportedElementInfo> uns = new MCUnsupportedCollector().getUnsupporteds(cmlProj.getModel().getAst());
				if (!uns.isEmpty()){
					cmlProj.addUnsupportedMarkers(uns);
					MessageDialog.openError(null, "Symphony", MCCollectorHandler.UNSUPPORTED_ELEMENTS_MSG);
					return null;
				}

				
				AProcessDefinition selectedProcess = GlobalProcessSelectorDialog.chooseProcess(cmlProj, window.getShell());
				if(selectedProcess != null){
					updateNumberOfInstances();
					CmlMCPlugin.getDefault().checkAuxiliarySoftware();
					String propertyToCheck = this.getProperty(event.getParameter("eu.compassresearch.ide.modelchecker.property"));
					IFolder mcFolder = cmlProj.getModelBuildPath().getOutput().getFolder(new Path("modelchecker"));
					if(!mcFolder.exists()){
						//if generated folder doesn't exist
						if (!mcFolder.getParent().exists()){
							((IFolder) mcFolder.getParent()).create(true, true, new NullProgressMonitor());
						}
						//if the model checker folder does not exist
						if (!mcFolder.exists()){
							mcFolder.create(true, true, new NullProgressMonitor());
							mcFolder.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
						}
						
					}
					IFile outputFile = translateCmlToFormula(cmlProj,selectedProcess,mcFolder,propertyToCheck);
					
					if(outputFile != null){
						MCProgressView p = new MCProgressView(outputFile, propertyToCheck, mcFolder, event, mainProcessName, this);
						p.execute();
					}
				}
			} catch(Exception e){
				//logStackTrace(e);
				MCPluginUtility.popErrorMessage(e);
			}
			
		}
		return null;
	}
	
	private void updateNumberOfInstances(){
		int instancesPref = CmlMCPlugin.getDefault().getPreferenceStore().getInt(MCConstants.INSTANCES_NUMBER);
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		context.setNumberOfInstances(instancesPref);
	}  
	
	private IFile translateCmlToFormula(ICmlProject cmlProj, AProcessDefinition selectedProcess, 
			IFolder mcFolder, String propertyToCheck) 
					throws IOException,AnalysisException{

		List<PDefinition> definitions = cmlProj.getModel().getDefinitions();
		mainProcessName = selectedProcess.getName().getSimpleName();
		
		IFile outputFile = null;
		if(selectedProcess != null){
			mainProcessName = selectedProcess.getName().getSimpleName();
			String name = mainProcessName;
			String formulaFileName = name +".4ml";
			outputFile = mcFolder.getFile(formulaFileName);

			this.adaptor =  new NewMCVisitor();
			String specificationContent = "";

			try{
				specificationContent = this.adaptor.generateFormulaScript(definitions,propertyToCheck,mainProcessName);
				if(!outputFile.exists()){
					outputFile.create(new ByteArrayInputStream(specificationContent.toString().getBytes()), true, new NullProgressMonitor());
				}else{
					outputFile.setContents(new ByteArrayInputStream(specificationContent.toString().getBytes()), true, true, new NullProgressMonitor());
				}
			}catch(NullPointerException e){
				throw new AnalysisException("Internal error when accessing some null object during FORMULA script generation.", e);
			}catch(ClassCastException e){
				throw new AnalysisException("Internal error when casting some object during FORMULA script generation.");
			}catch(CoreException e){
				CmlMCPlugin.log(e);
			}

		}
		return outputFile;
	}

	/*
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
	*/
	
	private String getProperty(String parameterValue){
		String property = Utilities.DEADLOCK_PROPERTY;
		
		if(parameterValue.equals("LIVELOCK")){
			property = Utilities.LIVELOCK_PROPERTY;
		} else if(parameterValue.equals("NONDETERMINISM")){
			property = Utilities.NONDETERMINISM_PROPERTY;
		}
		
		return property;
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		if(mc != null){
			try {
				mc.finalize();
			} catch (Throwable e) {
				MCPluginUtility.popErrorMessage(e);
			}
		}
	}
	
	
	
}
