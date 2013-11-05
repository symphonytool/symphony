package eu.compassresearch.core.analysis.modelchecker;

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
import eu.compassresearch.core.analysis.modelchecker.visitors.CMLModelcheckerVisitor;
import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;

public class TestCMLModelchecker {

	List<PSource> sources;
	
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
		
		File f = new File(Utilities.CML_EXAMPLES_DIRECTORY);
		if(f.isDirectory()){
			File[] formulaFiles = f.listFiles();
			FormulaResult currentResult = null;
			for (int i = 0; i < formulaFiles.length; i++) {
				String currentFileName = formulaFiles[i].getPath();
				if(currentFileName.endsWith(".4ml")){
					try {
						currentResult = formulaIntegrator.analyse(currentFileName);
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
			CMLModelcheckerVisitor visitor = new CMLModelcheckerVisitor(sources);
			visitor.generateFormulaCodeForAll();
			IFormulaIntegrator invoker = FormulaIntegrator.getInstance();
			this.analyseAllSources(invoker);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AnalysisException e) {
			e.printStackTrace();
		} catch (FormulaIntegrationException e) {
			e.printStackTrace();
		} 
	}

}
