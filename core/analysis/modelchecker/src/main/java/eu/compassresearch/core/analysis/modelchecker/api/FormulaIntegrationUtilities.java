package eu.compassresearch.core.analysis.modelchecker.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import eu.compassresearch.core.analysis.modelchecker.visitors.Utilities;

public class FormulaIntegrationUtilities {

	public static final String AUXILIARY_DEFINITIONS_SCRIPT = "/cml_aux_definitions.fml";
	public static final String SYNTAX_DOMAIN_SCRIPT = "/cml_syntax.fml";
	public static final String SEMANTICS_DOMAIN_SCRIPT = "/cml_semantics.fml";
	public static final String SEMANTICS_TIMED_DOMAIN_SCRIPT = "/cml_semantics_timed.fml";
	public static final String PROPERTIES_DOMAIN_SCRIPT = "/cml_properties.fml";
	
	public static void buildSpecification(String specificContent){
		//it obtains the basic content of the semantics
		
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
	
	public static void writeScriptToFile(String filePath, StringBuilder content) throws IOException {
		 FileWriter fw = new FileWriter(filePath);
		 BufferedWriter bw = new BufferedWriter(fw);
		 bw.write(content.toString());
		 bw.flush();
		 fw.close();
		 bw.close();
	 }
	
}
