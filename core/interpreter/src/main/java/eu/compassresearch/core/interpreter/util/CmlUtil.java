package eu.compassresearch.core.interpreter.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.types.AClassType;

import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

public class CmlUtil {

	public static boolean parseSource(PSource source) throws IOException
	{
		boolean parseOK = false;
		
		ANTLRInputStream in = null;
		if (source instanceof AFileSource)
			in = new ANTLRInputStream(new FileInputStream(((AFileSource)source).getFile()));

		if (source instanceof AInputStreamSource)
			in = new ANTLRInputStream(((AInputStreamSource)source).getStream());

		if (in == null)
			return false;
		
		CmlLexer lexer = new CmlLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmlParser parser = new CmlParser(tokens);

		try {
			List<PDefinition> sourceDefs = parser.source();
			source.setParagraphs(new LinkedList<PDefinition>());
			if (sourceDefs != null)
			{
				for (PDefinition def : sourceDefs)
					if (def != null)
						source.getParagraphs().add(def);
				parseOK = true;
			}
			else
				parseOK = false;
		} catch (RecognitionException e) {
			e.printStackTrace();
			parseOK = false;
		}
		
		
		return parseOK;
	}
	
	public static AClassType createClassType(AProcessDefinition processDefinition)
	{
		AClassType classType = new AClassType();
		classType.setResolved(true);
		classType.setName(processDefinition.getName());
		classType.setClassdef(createClassClassDefinition(processDefinition));
		return classType;
	}
	
	public static AClassClassDefinition createClassClassDefinition(AProcessDefinition processDefinition)
	{
		AClassClassDefinition surrogateProcessDef = new AClassClassDefinition();
		surrogateProcessDef.setLocation(processDefinition.getLocation());
		surrogateProcessDef.setName(processDefinition.getName());
		
		return surrogateProcessDef;
	}
	
}
