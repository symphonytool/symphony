package eu.compassresearch.core.interpreter;

import java.io.IOException;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;
import org.overture.test.framework.ConditionalIgnoreMethodRule;
import org.overture.test.framework.ConditionalIgnoreMethodRule.ConditionalIgnore;
import eu.compassresearch.core.interpreter.utility.ProbNotInstalledCondition;

import eu.compassresearch.core.interpreter.api.CmlInterpreterException;

@RunWith(Parameterized.class)
public class ImplicitTest extends InterpretAllCmlFilesTest
{

	public ImplicitTest(String filePath, String name)
	{
		super(filePath, name);
	}
	
	@Parameters(name = "{index} : {1}")
	public static Collection<Object[]> getCmlfilePaths()
	{
		final String initialPath = "src/test/resources/implicit";
		return collectTests(initialPath);
	}
	
	@Rule
	public ConditionalIgnoreMethodRule rule = new ConditionalIgnoreMethodRule();


	@Test
	@ConditionalIgnore(condition = ProbNotInstalledCondition.class)
	@Override
	public void testInterpretCmlFile() throws IOException, AnalysisException,
			CmlInterpreterException
	{
		super.testInterpretCmlFile();
	}
}
