package eu.compassresearch.core.typechecker;
import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.ast.analysis.intf.IAnalysis;
import org.overture.ast.program.ASourcefileSourcefile;

import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


@SuppressWarnings("serial")
public class CmlTypeChecker extends DepthFirstAnalysisAdaptor {

	private IAnalysis exp;
	private IAnalysis stm;
	private IAnalysis dad;
	
	
	
	@Override
	public void caseASourcefileSourcefile(ASourcefileSourcefile arg0) {
		System.out.println("The beginning.");
		super.caseASourcefileSourcefile(arg0);
		
		
	}

	public CmlTypeChecker()
	{
		exp = new TCExpressionVisitor();
		stm = new TCStatementVisitor();
		dad = new TCDeclAndDefVisitor();
	}


	public static void main(String[] args) throws IOException
	{
		CmlLexer lexer = new CmlLexer(new FileReader(args[0]));
		CmlParser parser = new CmlParser(lexer);
		ASourcefileSourcefile tree = new ASourcefileSourcefile();
		parser.setDocument(tree);
		if (parser.parse())
		{
			IAnalysis analysis = new CmlTypeChecker();
			tree.apply(analysis);
		}
	}
	
}