package eu.compassresearch.security;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.security.SecurityEnvironment.Judgement;
import eu.compassresearch.security.SecurityEnvironment.SecurityJudgement;
import eu.compassresearch.security.SecurityEnvironment.SubtreeJudgement;

public class SecurityAnalysisOnFileTestCase {

	
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
	
	//@Test
	public void run() throws IOException, RecognitionException, AnalysisException {

		FileInputStream inStream = new FileInputStream("/Users/rwl/phd/compass/Common/CaseStudies/SoSMpc/protocol/BeDOZa.cml");
		
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
		
		HashSet<String> res = new HashSet<String>();
		for(Judgement j : judgements.getInvolvedJudgements()) {
			if (j.getJudgement() == SecurityJudgement.Secret || j.getJudgement() == SecurityJudgement.Random) {
				res.add(j.toString());
			}
		}
		
		for(String s : res) {
			System.out.println(s);
		}
	}

}
