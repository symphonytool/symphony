package eu.compassresearch.core.typechecker.secondedition;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class RunCmlExamplesTestCase extends
		AbstractResultBasedCmlTypeCheckerTestCase
{

	private static final String path = "../../docs/cml-examples/";

	public RunCmlExamplesTestCase(File file, String name, TestType type)
	{
		super(file, name, type);
	}

	@Parameters(name = "{1}")
	public static Collection<Object[]> getData()
	{
		return filter(collectTestData(path, TestType.ANY, TestType.ANY));
	}

	private static Collection<Object[]> filter(
			Collection<Object[]> collectTestData)
	{
		List<Object[]> r = new Vector<Object[]>();
		for (Object[] o : collectTestData)
		{
			String name = o[0].toString();
			if (name.contains("fail.cml")
					|| name.contains("process-action-alphabetisedParallelism.cml"))
			{
				r.add(o);
			}
		}

		collectTestData.removeAll(r);
		return collectTestData;
	}

	@Override
	protected String getPropertyId()
	{
		return "run";
	}

	@Override
	protected File createResultFile(String filename)
	{
		return new File(filename.replace(path.replace('/', File.separatorChar), SRC_TEST_RESOURCES
				+ "GitExamples/").replace(".cml", "")
				+ ".result");
	}

	@Override
	protected File getResultFile(String filename)
	{
		return new File(filename.replace(path.replace('/', File.separatorChar), SRC_TEST_RESOURCES
				+ "GitExamples/").replace(".cml", "")
				+ ".result");
	}

}
