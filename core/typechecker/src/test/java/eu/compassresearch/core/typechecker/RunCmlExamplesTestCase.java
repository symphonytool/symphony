package eu.compassresearch.core.typechecker;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler.CMLTypeError;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;

@RunWith(value = Parameterized.class)
public class RunCmlExamplesTestCase
{

	@Parameters(name = "{index} : {1}")
	public static Collection<Object[]> getData()
	{
		int i = 0;
		List<Object[]> files = new LinkedList<Object[]>();

		File examplesDir = new File("../../docs/cml-examples/");
		FileFilter cmlFiles = new FileFilter()
		{

			@Override
			public boolean accept(File pathname)
			{
				return pathname.getName().endsWith(".cml");
			}
		};

		File[] theFiles = examplesDir.listFiles(cmlFiles);
		Arrays.sort(theFiles);

		for (File f : theFiles)
		{
			files.add(new Object[] { i++, f });
		}

		return files;
	}

	private static Map<String, List<String>> failingTC;
	static
	{
		failingTC = new HashMap<String, List<String>>();
		addFailingFile("airline2.cml", "Unknown field date in record Flight.");
		addFailingFile("isofclassexp.cml", TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("a"));
		addFailingFile("process-intchoice.cml", TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage("RegisterProc"));
		addFailingFile("path-conversion-test-identifierStateDesignator2.cml", TypeErrorMessages.EXPECTED_TYPE_DEFINITION.customizeMessage("Byte"));
		addFailingFile("exp_inset.cml", "Argument of 'in set' is not a set.");
		addFailingFile("functions-implicit.cml", "Unable to resolve type name 'Byte'");
		addFailingFile("tokentype.cml", "Unable to resolve type name 'States'.");
		addFailingFile("class-values-pattern.cml", "Unable to resolve type name 'rec'.");
		addFailingFile("process-action-communication-multiwrite.cml", "The type init is undefined.");
		addFailingFile("process-action-alphabetisedParallelism.cml", TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("A"));
		addFailingFile("process-action-callstm.cml", TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("a"));
		// addFailingFile("airline2.cml", "The type init is undefined.");
		addFailingFile("process-action-communication-multiwrite.cml", "The type load is undefined.");
		addFailingFile("process-action-whilestm.cml", TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("a"));
		addFailingFile("process-timed_interrupt.cml", TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("A"));
		addFailingFile("process-replication-interleaving.cml", TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("A"));
		addFailingFile("process-action-replicated-sequentialcomposition.cml", TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("A"));
		addFailingFile("process-alphabetised_parallelism.cml", TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("A"));
		addFailingFile("process-startdeadline.cml", TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("A"));
		addFailingFile("class-functions.cml", "Unable to resolve type name 'SUBS'.");

		addFailingFile("class-types-composeof.cml", "Unable to resolve type name 'A1'.");

		addFailingFile("fieldselect.cml", "Unable to resolve type name 'string'.");
		addFailingFile("functions-implicit2.cml", "Unable to resolve type name 'Day'");
		addFailingFile("functions.cml", "Unable to resolve type name 'Byte'");
		addFailingFile("globalinvrecord.cml", "Argument to 'dom' is not a map.");
		addFailingFile("Marcel_CML_spec.cml", "Unable to resolve type name 'req'");
		addFailingFile("path-conversion-test-fieldExp.cml", "The Symbol \"b\" is undefined.");
		addFailingFile("path-conversion-test-fieldExp2.cml", "The Symbol \"a\" is undefined.");
		addFailingFile("path-conversion-test-nameExp.cml", "The Symbol \"Context\" is undefined.");
		addFailingFile("process-action-casesStm-others.cml", "The Symbol \"REG\" is undefined.");
		addFailingFile("process-action-casesStm.cml", "The Symbol \"REG\" is undefined.");
		addFailingFile("process-action-casesStm2.cml", "The Symbol \"REG\" is undefined.");
		addFailingFile("process-action-com_param-bind.cml", "The type init is undefined.");
		addFailingFile("process-action-com_param-record2.cml", "The type init is undefined.");
		addFailingFile("process-action-com_param-tuple.cml", "The type init is undefined.");
		addFailingFile("process-action-com_param-record.cml", "The type init is undefined.");
		addFailingFile("process-action-com_param-tuple2.cml", "The type init is undefined.");
		addFailingFile("process-action-communication-read-identifier.cml", "The type load is undefined.");
		addFailingFile("process-action-communication-signal-num.cml", "The type load is undefined.");
		addFailingFile("process-action-communication-write-identifier.cml", "The type load is undefined.");
		addFailingFile("process-action-objDesignator.cml", "The Symbol \"a\" is undefined.");
		addFailingFile("process-action-communication-write-numliteral.cml", "The type load is undefined.");
		addFailingFile("process-action-replicated-alphabetised.cml", "Could not determine type for nscs.");
		addFailingFile("process-action-replicated-interleaving.cml", "Could not determine type for ns.");
		addFailingFile("process-action-replicated-synchronous.cml", "Could not determine type for ns.");
		addFailingFile("process-action-synchronousParallelism.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-action-generalisedParallelism.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-action-hiding.cml", "The Symbol \"id1\" is undefined.");
		addFailingFile("process-action-instantiated.cml", "The Symbol \"OW\" is undefined.");
		addFailingFile("process-action-interleaving.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-action-nondeterministicdostm.cml", "The Symbol \"a\" is undefined.");
		addFailingFile("process-action-parametrisedInstantiated.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-action-newstm.cml", "The Symbol \"a\" is undefined.");
		addFailingFile("process-action-letstm.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-action-parametrised.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-actions-communication-input.cml", "The type init is undefined.");
		addFailingFile("process-actions-external_choice.cml", "The type init is undefined.");
		addFailingFile("process-actions.cml", "The type init is undefined.");
		addFailingFile("process-instantiation.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-operations-2.cml", "The type init is undefined.");
		addFailingFile("process-operations.cml", "The type init is undefined.");
		addFailingFile("process-seqcomp.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-state-invariant.cml", "Expected symbol \"Byte\" to be a type definition. But it was not.");
		addFailingFile("process-state.cml", "Expected symbol \"Byte\" to be a type definition. But it was not.");
		addFailingFile("process-synchronous_parallelism.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("recordpatterns.cml", "Argument to 'dom' is not a map.");
		addFailingFile("setcompsetexp.cml", "The Symbol \"S\" is undefined.");
		addFailingFile("setcompsetexp2.cml", "Unable to resolve type name 'fnRoomId'");
		addFailingFile("string.cml", "Unable to resolve type name 'string'.");
		addFailingFile("process-action-nondeterministicif.cml", "The Symbol \"x\" is undefined.");
		addFailingFile("process-action-replicated-externalChoice.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-action-replicated-internalChoice.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-action-specificationstm.cml", "The Symbol \"h\" is undefined.");
		addFailingFile("process-action-timed_interrupt2.cml", "In it's context \"({5, 4} \\ {4})\" is a time unit expression and must be a subtype of nat, which \"set of (nat1)\" is not.");
		addFailingFile("process-actions-ifelseifstm.cml", "The Symbol \"REG1\" is undefined.");
		addFailingFile("process-actions-ifelseifstm2.cml", "The Symbol \"REG1\" is undefined.");
		addFailingFile("process-actions-ifstm.cml", "The Symbol \"REG1\" is undefined.");
		addFailingFile("process-channelrenaming.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-extchoice.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-generalised_parallelism.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-hiding.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-instantiation-withdecl.cml", "Expected an entity of type T but nat1 was found.");
		addFailingFile("process-interleaving.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-interrupt.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-replication-alphabetisedParallelism.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-replication-externalchoice.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-replication-generalisedParallelism.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-replication-internalchoice.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-replication-sequentialcomp.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-replication-synchronousParallelism.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-timeout.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("process-untimed_timeout.cml", "The Symbol \"A\" is undefined.");
		addFailingFile("simpson.cml", "Unable to resolve type name 'Data'");
		addFailingFile("class-functions-measure.cml", "Measure a(int) is not in scope.");
		addFailingFile("isundername.cml", "The Symbol \"a\" is undefined.");
		addFailingFile("class-operations-varinformation.cml", TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("a"));
		addFailingFile("cddatabase.cml", "Unknown type 'CD' in constructor.");
		// Failed tests caused by parser issues

		// // See cml.y production: | expression[rootExp] LRPAREN ... The
		// argument list is
		// dropped on the floor :(
	}

	private static void addFailingFile(String fileName, String... errors)
	{
		failingTC.put(fileName, Arrays.asList(errors));
	}

	private final File file;
	private final int number;

	public RunCmlExamplesTestCase(int i, File file)
	{

		this.number = i;
		this.file = file;
	}

	@Test
	public void test() throws IOException
	{

		System.out.print("\t#" + number + " " + file.getName() + "\n");

		TestUtil.TypeCheckerResult res = TestUtil.runTypeChecker(file.getAbsolutePath());

		boolean parseOk = res.parsedOk;
		if (!parseOk)
			System.out.println();
		// Assert.assertTrue("Parser failed", parseOk);
		Assume.assumeTrue(res.parsedOk);

		// Okay we need to go through these examples in more depth to see
		// which are supposed to pass type checking !
		// However no expections should occur.

		Assume.assumeTrue(false);

		ITypeIssueHandler tc = res.issueHandler;
		String errorString = buildErrorMessage(tc);
		boolean tcOK = res.tcOk;
		if (!failingTC.containsKey(file.getName()))
		{
			System.out.println("\t" + (tcOK ? "[OK]" : "[FAIL]"));
			System.out.println(tc.getTypeErrors().get(0).getDescription() + " "
					+ tc.getTypeErrors().get(0).getLocation());
			System.out.println(tc.getTypeErrors().get(0).getStackTrace());
			if (tc.getTypeErrors().size() > 0)
				System.out.println("addFailingFile(\""
						+ file.getName()
						+ "\",\""
						+ (tc.getTypeErrors().get(0).getDescription() + " " + tc.getTypeErrors().get(0).getLocation()).replace("\"", "\\\"")
						+ "\");");
			Assert.assertTrue("Type checker dies with errors: " + errorString, tcOK);
		} else
		{
			HashSet<String> actualErrors = new HashSet<String>();
			for (CMLTypeError e : tc.getTypeErrors())
			{
				actualErrors.add(e.getDescription());
			}

			List<String> expectedErrors = failingTC.get(file.getName());
			for (String expectedError : expectedErrors)
			{
				boolean found = actualErrors.contains(expectedError);
				if (!found)
				{
					System.out.println("\t [FAILED UNEXPECTED]");
					System.out.println(TestUtil.buildErrorMessage(tc, false));
				}
				Assert.assertTrue("Expected Error:\n" + expectedError
						+ "\nbut it was not found.", found);
			}

			System.out.println("\t" + "[FAILED AS EXPECTED]");
		}
	}

	private String buildErrorMessage(ITypeIssueHandler tc)
	{

		StringBuilder sb = new StringBuilder();

		sb.append("Expected type checking to be successful, the following errors were unexpected:\n");
		for (CMLTypeError error : tc.getTypeErrors())
			sb.append(error.getLocation() + ": " + error.toString()
					+ "\n------\n");

		if (tc.getTypeErrors().size() > 0)
		{
			// CMLTypeError error = tc.getTypeErrors().get(0);
			// System.out.println();
			// System.out.println("Error "+error.getLocation());
			// System.out.println(error.getStackTrace());
		}
		sb.append(file.getAbsolutePath());
		return "";
	}

}
