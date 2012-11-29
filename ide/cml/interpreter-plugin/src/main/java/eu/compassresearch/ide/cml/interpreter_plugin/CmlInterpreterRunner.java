package eu.compassresearch.ide.cml.interpreter_plugin;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

public class CmlInterpreterRunner {

	private CmlInterpreter cmlInterpreter;
	
	public CmlInterpreterRunner(List<PSource> cmlSources) throws InterpreterException
	{
		cmlInterpreter = VanillaInterpreterFactory.newInterpreter(cmlSources);
	}
	
	public void run() throws AnalysisException
	{
		cmlInterpreter.execute();
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterpreterException 
	 * @throws AnalysisException 
	 */
	public static void main(String[] args) throws IOException, InterpreterException, AnalysisException {

		//Index 0 of args is the JSON config
		Object obj=JSONValue.parse(args[0]);
		JSONObject config =(JSONObject)obj;
		//retrieve the paths for the cml sources of the project
		List<String> cmlfilePaths = getCmlfilePaths((String)config.get(CmlLaunchConfigurationConstants.ATTR_CML_SOURCES_PATH.toString()));
		System.out.println(cmlfilePaths);
	
		List<PSource> sourceForest = new LinkedList<PSource>();
		
		// build the forest
		for (String path : cmlfilePaths) {
			
			File source = new File(path);
			System.out.println("Parsing file: " + source);
			AFileSource currentTree = new AFileSource();
			currentTree.setName(source.getName());
			FileReader input = new FileReader(source);
			CmlLexer lexer = new CmlLexer(input);
			CmlParser parser = new CmlParser(lexer);
			parser.setDocument(currentTree);
			if (!parser.parse()) {
				//handleError(lexer, source);
				return;
			} else
				sourceForest.add(currentTree);
		}
		
		CmlInterpreterRunner runner = new CmlInterpreterRunner(sourceForest);
		
		runner.run();
		
		
		System.out.println(sourceForest);
	
	}
	
	/**
	 * Find all the CML files in the specified path
	 * @return
	 */
	public static List<String> getCmlfilePaths(String path) {

		File dir = new File(path);
		List<String> paths = new LinkedList<String>();

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".cml");
			}
		};

		String[] children = dir.list(filter);
		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				paths.add(dir.getPath() + "/" + children[i]);
			}
		}

		return paths;
	}

}
