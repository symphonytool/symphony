package eu.compassresearch.core.analysis.modelchecker.visitors;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.PExp;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Void;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

public class Utilities {
	public static final String CML_EXAMPLES_DIRECTORY = "src/test/resources";
	//public static final String BASIC_FORMULA_SCRIPT = "src/test/resources/basic_formula_script.fml";
	public static final String BASIC_FORMULA_SCRIPT = "/basic_formula_script.fml";
	//public static final String BASIC_FORMULA_SCRIPT = "/basic_formula_script-new.fml";
	public static final String DEADLOCK_PROPERTY = "Deadlock";
	public static final String LIVELOCK_PROPERTY = "Livelock";
	public static final String NONDETERMINISM_PROPERTY = "Nondeterminism";
	public static final int EXTERNAL_CHOICE = 1;
	public static final int INTERNAL_CHOICE = 2;
	public static final int SEQUENTIAL_COMPOSITION = 3;
	public static final int GEN_PARALLELISM = 4;
	public static final String LOCAL_DEFINITIONS_KEY = "LOCAL_DEFINITIONS";
	public static final String CHANNEL_DEFINITIONS_KEY = "CHANNEL_DEFINITIONS";
	public static final String IOCOMM_DEFINITIONS_KEY = "IOCOMM_DEF";
	public static final String TYPE_USER_DEFINITION = "USER_DEF_TYPES";
	public static final String CHAN_TYPE_DEFINITION = "CHAN_TYPES";
	public static final String CHAN_TYPES_UNION = "UNION_CHAN_TYPES";
	public static final String CONDITION_KEY = "CONDITION";
	public static int OCCUR_COUNT = 1;
	public static String VALUE_DEFINITIONS_KEY = "VALUES_DEFINITION";
	public static String ASSIGNMENT_DEFINITION_KEY = "ASSIGNMENT_DEFINITION";
	public static String VAR_DECLARATIONS_KEY = "VAR_DECLARATIONS";
	public static String DEL_BBINDING = "DEL_BB";
	public static String STATES_KEY = "STATES_DEFINITION";
	public static String LIEIN_SETS = "LIEIN_SETS";
	public static String LIEIN_FACTS = "LIEIN_FACTS";
	public static String MAX_BIND = "MAX_BIND";
	public static String CHANTYPE_STRING = "ChanType";
	
	public static String STATE = "state";
	public static String UNDERSCORE = "underscore";
	public static String DEFAULT = "default";
	
	private static boolean parseWithANTLR(PSource sourceIn) throws IOException
	{
		ANTLRInputStream in = null;
		
		if (sourceIn instanceof AInputStreamSource)
			in = new ANTLRInputStream(((AInputStreamSource)sourceIn).getStream());
		
		if (sourceIn instanceof AFileSource)
			in = new ANTLRInputStream( new FileInputStream(((AFileSource)sourceIn).getFile()));
		
		CmlLexer lexer = new CmlLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmlParser parser = new CmlParser(tokens);

		try {
			sourceIn.setParagraphs(parser.source());
			return true;
		} catch (RecognitionException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public static Type createValue(PExp expression){
		Type result = null;
		
		if(expression == null){
			result = new Void();
		}
		if(expression instanceof AIntLiteralExp){
			result = new Int(Integer.valueOf(((AIntLiteralExp) expression).getValue().toString()));
		}
		
		return result;
	}
	
	 public static PSource makeSourceFromFile(String filePath)
			    throws IOException, AnalysisException {

		    File f = new File(filePath);
			AFileSource ast = new AFileSource();
			ast.setName(f.getName());
			ast.setFile(f);
			parseWithANTLR(ast);
			// Call factory method to build parser and lexer
			//assertTrue("Test failed on parser", parseWithANTLR(ast));

			return ast;
	 }
	 
	 public static StringBuilder readScriptFromFile(String filePath) throws IOException {
		 StringBuilder text = new StringBuilder();
		 InputStream is = Utilities.class.getResourceAsStream(filePath);
		 InputStreamReader isr = new InputStreamReader(is);
		 BufferedReader br = new BufferedReader(isr);
		 String line = "";
		 while((line=br.readLine()) != null ){
			 text.append(line);
			 text.append("\n");
		 }
		 is.close();
		 br.close();
		 text.append("\n");
		 return text;
	 }
	 
	 public static StringBuilder readScriptFromFile(String filePath, boolean useAbsolutePath) throws IOException {
		 StringBuilder text = new StringBuilder();
		 if(!useAbsolutePath){
			 text = readScriptFromFile(filePath);
		 }else{
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
		 }
		 
		 return text;
	 }
	 
	 public static void writeScriptToFile(String filePath, StringBuilder content) throws IOException {
		 FileWriter fw = new FileWriter(filePath);
		 BufferedWriter bw = new BufferedWriter(fw);
		 bw.write(content.toString());
		 bw.flush();
		 fw.close();
		 bw.close();
	 }
	 
	 public static String generateFormulaFileName(String cmlFileName){
		 String result = new String(cmlFileName);
		 int indexOfDot = result.lastIndexOf('.');
		 result = result.substring(0, indexOfDot);
		 result = result + ".4ml";
		 return result;
	 }

	
	 public static void main(String[] args) throws IOException{
		System.out.println("Original: " + Utilities.CML_EXAMPLES_DIRECTORY);
		File f = new File(Utilities.CML_EXAMPLES_DIRECTORY);
		if(f.isDirectory()){
			File[] cmlFiles = f.listFiles();
			for (int i = 0; i < cmlFiles.length; i++) {
				String currentFileName = cmlFiles[i].getCanonicalPath();
				if(currentFileName.endsWith(".cml")){
					System.out.println(currentFileName);
				}
			}
		}
		//System.out.println("Formula: " + Utilities.generateFormulaFileName(Utilities.CML_EXAMPLES_DIRECTORY));
	}
}
