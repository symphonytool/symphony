package eu.compassresearch.core.analysis.modelchecker.test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationException;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.visitors.NameContent;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewMCVisitor;
import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;

public class TestCMLModelchecker {

	private List<PSource> sources;
	private String[]  formulaCodes = new String[0];
	
	@Before
	public void setUp() throws Exception {
		this.sources = new LinkedList<PSource>();
		loadAllSources();
	}
	private void loadAllSources() throws IOException, AnalysisException{
		
		File f = new File(Utilities.CML_EXAMPLES_DIRECTORY);
		if(f.isDirectory()){
			File[] cmlFiles = f.listFiles();
			PSource source = null;
			for (int i = 0; i < cmlFiles.length; i++) {
				String currentFileName = cmlFiles[i].getPath();
				if(currentFileName.endsWith(".cml")){
					source = Utilities.makeSourceFromFile(currentFileName);
					sources.add(source);
				}
			}
		}
		
	} 
	
	private List<FormulaResult> analyseAllSources(IFormulaIntegrator formulaIntegrator) throws IOException, AnalysisException, FormulaIntegrationException{
		
		List<FormulaResult> results = new LinkedList<FormulaResult>();
		File f = new File(Utilities.FORMULA_TMP_DIRECTORY);
		if(f.isDirectory()){
			File[] formulaFiles = f.listFiles();
			FormulaResult currentResult = null;
			for (int i = 0; i < formulaFiles.length; i++) {
				String currentFileName = formulaFiles[i].getAbsolutePath();
				if(currentFileName.endsWith(".4ml")){
					try {
						System.out.println("Analysing file: " + currentFileName);
						currentResult = formulaIntegrator.analyseFile(currentFileName);
						results.add(currentResult);
					} catch (FormulaIntegrationException e) {
						try {
							((FormulaIntegrator)formulaIntegrator).finalize();
						} catch (Throwable e1) {
							System.out.println("Error in finilize()!");
							e1.printStackTrace();
						}
						e.printStackTrace();
						throw e;
					}
					
				}
			}
			try {
				((FormulaIntegrator)formulaIntegrator).finalize();
			} catch (Throwable e1) {
				System.out.println("Error in finilize()!");
				e1.printStackTrace();
			}
		}
		return results;
		
	} 

	@Test
	public void testAllExamples() {
		try {
			NewMCVisitor visitor = new NewMCVisitor(sources);
			LinkedList<NameContent> formulaScripts = visitor.generateFormulaCodeForAll(Utilities.DEADLOCK_PROPERTY);
			saveAllFormulaScripts(formulaScripts);
			IFormulaIntegrator invoker = FormulaIntegrator.getInstance();
			this.analyseAllSources(invoker);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AnalysisException e) {
			e.printStackTrace();
		} catch (FormulaIntegrationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAllFormulaScripts(LinkedList<NameContent> formulaScripts) throws IOException{
		//it first cleans up the temporary directory
		File folder = new File(Utilities.FORMULA_TMP_DIRECTORY);
		if(!folder.exists()){
			folder.mkdir();
			System.out.println("Created folder: " + folder.getAbsolutePath());
		}else{
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
		for (NameContent nameContent : formulaScripts) {
			String formulaFileName = Utilities.generateFormulaFileName(nameContent.getFileName());
			String path = Utilities.FORMULA_TMP_DIRECTORY + File.separator + formulaFileName;
			//System.out.println("Saving file: " + path);
			Utilities.writeScriptToFile(path,  nameContent.getContent());
		}
	}
	
	
}
