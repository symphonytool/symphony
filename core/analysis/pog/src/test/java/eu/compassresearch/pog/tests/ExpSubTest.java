package eu.compassresearch.pog.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIntegerToken;

import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.analysis.pog.visitors.ExpSub;
import eu.compassresearch.core.analysis.pog.visitors.Substitutions;
import eu.compassresearch.pog.tests.utils.TestInputHelper;

public class ExpSubTest {

	ExpSub es;
	Substitutions subs;

	public void aux(String input, String expected) throws AnalysisException,
			IOException, RecognitionException {

		PExp expInput = TestInputHelper.string2Pexp(input);
		String cleaned_actual = expInput.apply(es, subs).toString()
				.replaceAll("[\\(\\)]", "");
		String cleaned_expected = expected.replaceAll("[\\(\\)]", "");
		assertEquals(cleaned_expected, cleaned_actual);
	}

	@Before
	public void setUp() throws Exception {
		es = new ExpSub();
		subs = new Substitutions();

		AVariableExp var = new AVariableExp();
		var.setName(new CmlLexNameToken("", "a", null));

		AIntLiteralExp lit = new AIntLiteralExp();
		lit.setValue(new LexIntegerToken(1, null));
		subs.put(var, lit);

	}

	@Test
	public void testCaseAAbsoluteUnaryExp() throws AnalysisException,
			IOException, RecognitionException {
		aux("abs 1", "(abs 1)");
	}

	@Test
	public void testCaseAAndBooleanBinaryExp_01() throws AnalysisException,
			IOException, RecognitionException {
		aux("a and b", "1 and b)");
	}

	@Test
	public void testCaseAAndBooleanBinaryExp_02() throws AnalysisException,
			IOException, RecognitionException {
		aux("b and c", "(b and c)");
	}

}
