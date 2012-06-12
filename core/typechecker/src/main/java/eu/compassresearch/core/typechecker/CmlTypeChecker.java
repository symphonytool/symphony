package eu.compassresearch.core.typechecker;
import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.ast.analysis.intf.IAnalysis;
import org.overture.ast.declarations.PDeclaration;

import org.overture.ast.program.ASourcefileSourcefile;

import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@SuppressWarnings("serial")
public class CmlTypeChecker extends DepthFirstAnalysisAdaptor {

	private IAnalysis exp;
	private IAnalysis stm;
	private IAnalysis dad;
	
	
	
	@Override
	public void caseASourcefileSourcefile(ASourcefileSourcefile sourceUnit) {
		super.caseASourcefileSourcefile(sourceUnit);
		
		
		// Type check all declarations
		for(PDeclaration d : sourceUnit.getDecls())
		{
			d.apply(this);
		}
		
	}









	public String getAnalysisName() { return "The CML Type Checker"; }
	
	public CmlTypeChecker()
	{
		exp = new TCExpressionVisitor();
		stm = new TCStatementVisitor();
		dad = new TCDeclAndDefVisitor();
	}


	
	private static void runOnFile(File f) throws IOException
	{
		CmlLexer lexer = new CmlLexer(new FileReader(f));
		CmlParser parser = new CmlParser(lexer);
		ASourcefileSourcefile tree = new ASourcefileSourcefile();
		parser.setDocument(tree);
		if (parser.parse())
		{
			IAnalysis analysis = new CmlTypeChecker();
			tree.apply(analysis);
		}
	}
	
	
	public static void main(String[] args) throws IOException
	{
		File cml_examples = new File("../../docs/cml-examples");
		int failures = 0;
		int successes = 0;
		if (cml_examples.isDirectory())
		{
			for(File example : cml_examples.listFiles())
			{
				System.out.print("Typechecking example: "+example.getName()+" \t\t...: ");System.out.flush();
				try {
				runOnFile(example);
				System.out.println("done"); successes++;
				} catch (Exception e)
				{
					System.out.println("exception");failures++;
				}
				
				
			}
		}
		System.out.println(successes+" was successful, "+failures+" was failures.");
		
	}
	
}