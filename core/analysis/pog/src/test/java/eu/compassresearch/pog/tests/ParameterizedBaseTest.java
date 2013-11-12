package eu.compassresearch.pog.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligation;
import org.overture.pog.pub.IProofObligationList;
import org.overture.pog.utility.POException;

import eu.compassresearch.core.analysis.pog.obligations.CmlPOContextStack;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.analysis.pog.visitors.ProofObligationGenerator;
import eu.compassresearch.pog.tests.utils.TestInputHelper;
import eu.compassresearch.pog.tests.utils.TestResultHelper;
/**
 * Main class for the new POG test framework.
 * 
 * It's responsible for running the base tests of the POG. These tests consists
 * of micro models that exercise 1 PO at a time (or when that's impossible, 
 * the bare minimum).
 * 
 * Results files should also be supplied and so as to make the tests useful.
 * @author ldc
 *
 */
@RunWith(Parameterized.class)
public class ParameterizedBaseTest
{
//FIXME Add POG test results
	private String micromodel;
	private String poresult;
	private String testfolder;
/**
 * Constructor for the test. Initialized with parameters from {@link #testData()}.
 * @param testParameter filename for the model to test
 * @param resultParameter test result file
 */
	
	public ParameterizedBaseTest(String testFolder, String testParameter, String resultParameter)
	{
		this.testfolder = testFolder;
		this.micromodel = testParameter;
		this.poresult = resultParameter;
	}

	/**
	 * Generate the test data. Actually just fetches it from {@link BaseInputProvider}.
	 * @return test data.
	 */
	@Parameters(name="{index} : {1}")
	public static Collection<Object[]> testData()
	{
		return BaseInputProvider.files();
	}
/**
 * A very simple test. Just prints the names of generated test inputs and results.
 */
	//@Test
	public void printFileNames()
	{
		System.out.println("Testing " + micromodel);
		System.out.println("Result at " + poresult);
		// put the test here!
	}
/**
 * A poor test. Runs the POG but just prints the results. Needs manual
 * checking
 * @throws IOException Test files not found, etc.
 * @throws AnalysisException POG crashes
 */
	@Test
	public void printPOs() throws IOException, AnalysisException
	{
		String modelpath = testfolder+micromodel;
		String resultpath = testfolder+poresult;
		List<INode> ast = TestInputHelper.getAstFromName(modelpath);
		System.out.println("Testing " + micromodel);
		
		String testResult;
		
		//FIXME Remove catch of absent RESULT files
		try{
		testResult = TestResultHelper.getResultAsString(resultpath);
		}
		catch (FileNotFoundException e){
			testResult="N/A yet";
		}
		
		System.out.println("Result: " + testResult);
		
		
		
		try {IProofObligationList polist = generatePOs(ast);
		
		if (polist.isEmpty()){
			System.out.println("No proof obligations.");
		}
		
		for (IProofObligation po : polist){
			printPo(po);
		}
		}
		catch (POException e){
			Assert.fail("POG crashed on " + micromodel);
			throw e;
		}

		System.out.println("========================================");	
		
	
	}

	/**
	 * Auxiliary method for printing a PO. It's quite poor as it just
	 * uses {@link #toString()}. Need to replace it with the PrettyPrinter
	 * when it comes.
	 * 
	 * @param po The PO to be printed.
	 */
	private void printPo(IProofObligation po)
	{
		//TODO Replace toString with PrettyPrinter
		System.out.println("------------------------");	
		
		StringBuilder sb = new StringBuilder();
		if (po instanceof CmlProofObligation)
		{
			sb.append(((CmlProofObligation) po).cmltype.toString());
		}
		else {
			sb.append(po.getKind().toString());
		}
		
		sb.append(": " + po.getValueTree().toString());
	
		System.out.println(sb.toString());
	}

	/**
	 * Auxiliary method for running the POG on an AST.
	 * 
	 * @param ast
	 *            The AST of the model to Analyze
	 * @return The list of Proof Obligations
	 * @throws AnalysisException something went wrong with the pog analysis
	 */
	private IProofObligationList generatePOs(List<INode> ast)
			throws AnalysisException
	{

		CmlProofObligationList poList = new CmlProofObligationList();
		for (INode node : ast)
		{
			poList.addAll(node.apply(new ProofObligationGenerator(), new CmlPOContextStack()));
		}
		return poList;
	}

}
