package eu.compassresearch.ide.modelchecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationException;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerVisitor;

public class MCJob extends Job{

	private FormulaResult result;
	private IFormulaIntegrator mc;
	private IFile formulaScript;
	
	public MCJob(String name, IFile formulaScript){
		super(name);
		this.formulaScript = formulaScript;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask("Analysing in FORMULA...", 100);
		try {
			this.mc = FormulaIntegrator.getInstance();
			String absolutePath = formulaScript.getLocation().toPortableString();
			this.result = mc.analyseFile(absolutePath);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		monitor.done();
		return Status.OK_STATUS;
	}
	
	private StringBuilder readScriptFromFile(String filePath, boolean useAbsolutePath) throws IOException {
		 StringBuilder text = new StringBuilder();
		 
			 //InputStream is = Utilities.class.getResourceAsStream(filePath);
			 //InputStreamReader isr = new InputStreamReader(is);
			 File f = new File(filePath);
			 FileReader fr = new FileReader(f);
			 BufferedReader br = new BufferedReader(fr);
			 String line = "";
			 while((line=br.readLine()) != null ){
				 text.append(line);
				 text.append("\n");
			 }
			 fr.close();
			 br.close();
			 text.append("\n");
		 
		 return text;
	 }

	public FormulaResult getFormulaResult(){
		IProgressMonitor i = new MCMonitor();
		run(i);
		return this.result;
	}

}
