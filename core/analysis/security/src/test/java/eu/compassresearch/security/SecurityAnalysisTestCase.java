package eu.compassresearch.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.security.SecurityEnvironment.SecurityJudgement;
import eu.compassresearch.security.SecurityEnvironment.SubtreeJudgement;


/**
 * Author: Rasmus Lauritsen.
 */
@RunWith(Parameterized.class)
public class SecurityAnalysisTestCase {
	
	private static void addTest(List<Object[]> td, String src, SecurityJudgement... judgements) {
		Object[] args = new Object[2];
		args[0] = src;
		args[1] = judgements;
		td.add(args);
	}
	
	@Parameters
	public static Collection<Object[]> testData() {
		List<Object[]> td = new LinkedList<Object[]>();
		addTest(td,"process P = val a1 : int @ begin @ Skip end process System = P(20)", SecurityJudgement.Public);
		addTest(td,"process P = val R_a1 : int @ begin @ Skip end", SecurityJudgement.Random);
		addTest(td,"process P = val S_a1 : int @ begin @ Skip end", SecurityJudgement.Secret);
		addTest(td,"channels c:int process P = val S_a1 : int @ begin actions A = c!S_a1 -> A @ Skip end",SecurityJudgement.Public, SecurityJudgement.Public);
		addTest(td,"channels c:int process P = val S_a1, R_a2 : int @ begin actions A = c!(S_a1-R_a2) -> A @ A end",SecurityJudgement.Public, SecurityJudgement.Secret, SecurityJudgement.Secret);
		addTest(td,"channels c:int process P = val S_a1, S_a2, R_a3 : int @ begin actions A = c!(S_a1-S_a2-R_a3) -> A @ A end",SecurityJudgement.Public, SecurityJudgement.Secret, SecurityJudgement.Secret, SecurityJudgement.Secret);
		addTest(td,"channels c:int process P = val R_singles: seq of int @ begin actions A = [| {c} |] i in set {1,2,3} @ [{ R_singles }] c!(R_singles(0)) -> Skip @ A end",SecurityJudgement.Public, SecurityJudgement.Public);
		addTest(td,"channels c:int types Rec :: a : int b : int process P = val R_in : Rec @ begin actions A = c!(R_in.a) -> Skip @ A end", SecurityJudgement.Public, SecurityJudgement.Public);
		addTest(td,"channels c:int process P = val R_1, S_1 : int @ begin actions A = c!(R_1 + S_1) -> A B = c!R_1 -> B @ Skip end",SecurityJudgement.Public, SecurityJudgement.Public, SecurityJudgement.Public);
		return td;
	}

	private String source;
	private SecurityJudgement[] judgements;
	
	public SecurityAnalysisTestCase(String source, SecurityJudgement[] judgements) {
		this.source = source;
		this.judgements = judgements;
	}
	
	
	public List<PDefinition> parse(InputStream is, List<String> parseErrors) throws IOException {
		CmlLexer lexer = new CmlLexer(new ANTLRInputStream(is));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmlParser parser = new CmlParser(tokens);

		try {
			return parser.source();
		} catch (RecognitionException e) {
			String expectedToken = "";
			CommonToken ct = null;
			if (e instanceof MismatchedTokenException)
			{
				ct = (CommonToken)e.token;
				MismatchedTokenException ee = (MismatchedTokenException)e;
				expectedToken= CmlParser.tokenNames[ee.expecting];
				parseErrors.add("Syntax error expecting '"+expectedToken+"' near '"+ct.getText()+"' at line "+e.line+" - "+ct.getStartIndex()+":"+ ct.getStopIndex());
				return new LinkedList<PDefinition>();
			}
			
			if (e.token != null)
			{
				ct = (org.antlr.runtime.CommonToken)e.token;
				parseErrors.add("Syntax error near '"+ct.getText()+"'. Error at line "+e.line + " - "+ct.getStartIndex()+":"+ ct.getStopIndex());	
			}
			else
				parseErrors.add("Syntax error, expecting at line at line "+e.line+".");
			return new LinkedList<PDefinition>();
		}

	}
	
	@Test
	public void run() throws IOException, RecognitionException, AnalysisException {
		ByteArrayInputStream inStream = new ByteArrayInputStream(source.getBytes());

		List<PSource> cmlSources = new LinkedList<PSource>();
		AInputStreamSource top = new AInputStreamSource();
		top.setStream(inStream);
		top.setOrigin("Input Stream");
		cmlSources.add(top);

		List<String> parseErrors = new LinkedList<String>();
		List<PDefinition> defs = parse(inStream, parseErrors);
		Assert.assertSame(parseErrors+"",0,parseErrors.size());
		top.setParagraphs(defs);
		
		TypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(cmlSources, issueHandler);
		boolean isTyped = cmlTC.typeCheck();
		Assert.assertTrue("Type Checking Failed: "+issueHandler.getTypeErrors(),isTyped);
		
		SecurityAnalysis sa = new SecurityAnalysis();
		SubtreeJudgement judgements = sa.runAnalysis(top);
		Assert.assertEquals(SecurityJudgement.Public, judgements.getJudgement());
		Assert.assertEquals(this.judgements.length, judgements.getInvolvedJudgements().size());
		for(int i = 0; i < judgements.getInvolvedJudgements().size();i++) {
			Assert.assertEquals(this.judgements[i], judgements.getInvolvedJudgements().get(i).getJudgement());
		}
	}
	
}
