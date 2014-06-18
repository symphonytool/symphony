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
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.handlers.HandlerUtil;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ide.core.resources.IVdmProject;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationException;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewMCVisitor;
import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;
import eu.compassresearch.ide.core.resources.ICmlModel;
import eu.compassresearch.ide.core.resources.ICmlProject;
import eu.compassresearch.ide.core.resources.ICmlSourceUnit;
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
		
		//IProject proj = MCPluginUtility.getCurrentlySelectedProject();
		//if (proj == null){
		//	popErrorMessage("No project selected.");
		//	return null;
		//}
		if(!CmlMCPlugin.FORMULA_OK){
			MCPluginUtility.popErrorMessage(CmlMCPlugin.formulaNotInstalledMsg);
		}else{
			try {
				this.window = HandlerUtil.getActiveWorkbenchWindow(event);
				this.proj = MCPluginUtility.getCurrentlySelectedProject();
				if (proj == null) {
					MCPluginUtility.popErrorMessage(new RuntimeException("No project is selected."));
					return null;
				}
				
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
				
				//Grab the model from the project
				final ICmlModel model = cmlProj.getModel();
				
				updateNumberOfInstances();
				
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
						//it proceeds only if FORMULA is installed
						CmlMCPlugin.getDefault().checkAuxiliarySoftware();
						if(CmlMCPlugin.FORMULA_OK){
							if("cml".equalsIgnoreCase(cmlFile.getFileExtension())){
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
								ICmlSourceUnit selectedUnit = getSelectedSourceUnit(model, (IFile)cmlFile);
								
								IFile outputFile = translateCmlToFormula(model, (IFile)cmlFile, mcFolder, propertyToCheck);
							
								if(outputFile != null){
									MCProgressView p = new MCProgressView(outputFile, propertyToCheck, mcFolder, selectedUnit, cmlFile, event, mainProcessName, this);
									p.execute();
								}
							}else{
								MessageDialog.openInformation(
										window.getShell(),
										"Symphony",
										"Only CML files can be analysed!");
							}
						}else{
							MessageDialog.openInformation(window.getShell(), "Symphony", CmlMCPlugin.formulaNotInstalledMsg);
						}
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
	
	private boolean hasMultipleProcessDefinitions(List<PDefinition> projectSources){
		boolean result = false;
		int i = 0;
		for (PDefinition pDefinition : projectSources) {
			if(pDefinition instanceof AProcessDefinition){
				i++;
				if(i > 1){
					result = true;
					break;
				}
			}
		}
		return result;
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
		
		List<PDefinition> definitions = selectedCmlSourceUnit.getParseListDefinitions();
		
		if(hasMultipleProcessDefinitions(definitions)){
			IVdmProject vdmProject = (IVdmProject) proj.getAdapter(IVdmProject.class);
			GlobalProcessSelectorDialog processSelector = new GlobalProcessSelectorDialog(window.getShell(), vdmProject,selectedCmlSourceUnit);
			AProcessDefinition selectedProcess = processSelector.showDialog();
			if(selectedProcess != null){
				mainProcessName = selectedProcess.getName().getSimpleName();
			}
			
		}else{
			for (PDefinition pDefinition : definitions) {
				if(pDefinition instanceof AProcessDefinition){
					mainProcessName = pDefinition.getName().getSimpleName();
					break;
				}
			}
		}
		IFile outputFile = null;
		if(mainProcessName != null){
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
	
	/*
	public void popErrorMessage(final Throwable e) {
		Display.getDefault().asyncExec(new Runnable() {
		    @Override
		    public void run() {
		    	popErrorMessage(e,"Symphony");
		    }
		  });
		
	}
	private void popErrorMessage(String message) {
		MessageDialog.openInformation(null, "Symphony",message);
	}
	
	private void popErrorMessage(final Throwable e, String tittle) {
		if(e instanceof FormulaIntegrationException){
			Activator.logErrorMessage(e.getMessage());
			MessageDialog.openInformation(null, tittle,"Could not analyse the specification.\n\n Internal error in FORMULA. \n\n" + e.getMessage());
		}else{
			MessageDialog.openInformation(null, tittle,"Could not analyse the specification.\n\n" + e.getMessage());
		}
	}
	*/
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
