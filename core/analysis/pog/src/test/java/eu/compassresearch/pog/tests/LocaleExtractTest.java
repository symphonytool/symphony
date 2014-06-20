package eu.compassresearch.pog.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IPogAssistantFactory;

import eu.compassresearch.core.analysis.pog.visitors.CmlPogAssistantFactory;
import eu.compassresearch.pog.tests.utils.TestInputHelper;

public class LocaleExtractTest
{

	private final static String testmodel = "src/test/resources/basic/other/locale.cml";
	private final static String result = "Locale";
	IPogAssistantFactory af = new CmlPogAssistantFactory();

	@Test
	public void test_ActionProc() throws IOException, AnalysisException
	{
		List<INode> ast = TestInputHelper.getAstFromName(testmodel);
		List<String> actual = new LinkedList<String>();
		for (INode n : ast)
		{
			actual.add(n.apply(af.getLocaleExtractVisitor()));
		}
		List<String> expected = new LinkedList<String>();
		expected.add(result);
		assertEquals(expected, actual);
		
		
	}
}
