package eu.compassresearch.core.typechecker;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.intf.IAnalysis;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.declarations.AClassDeclaration;
import org.overture.ast.declarations.PDeclaration;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AClassbodyDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;

import org.overture.ast.program.ASourcefileSourcefile;
import org.overture.ast.statements.PStm;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.PType;
import org.overturetool.vdmj.lex.LexLocation;
import org.overturetool.vdmj.typechecker.NameScope;

import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("serial")
public class CmlTypeChecker extends QuestionAnswerAdaptor<TypeCheckInfo, PType> {

	// --------------------------------------------- 
	// To be refactored, issues, warnings and errors
	// ---------------------------------------------
	static abstract class CMLIssue
	{
		// the position for the least common ancestor node spanning the nodes making up an error.  
		protected final LexLocation location;
		public CMLIssue(LexLocation location) {  this.location = location;}
	}

	static class CMLTypeWarning extends CMLIssue {
		protected final String description;

		public CMLTypeWarning(LexLocation location, String description) {
			super(location);
			this.description = description;
		}
	}

	static class CMLTypeError extends CMLTypeWarning {
		private boolean canBeIgnored = false;
		public CMLTypeError(LexLocation location, String message) {
			super(location, message);
		}

		public boolean isCanBeIgnored() {
			return canBeIgnored;
		}
		public void setCanBeIgnored(boolean canBeIgnored) {
			this.canBeIgnored = canBeIgnored;
		}
		
		@Override
		public String toString()
		{
			return "ERROR: "+location+" : "+description;
		}
	}


	// ---------------------------------------------
	// -- Type Checker State
	// ---------------------------------------------
	private final List<ASourcefileSourcefile> sourceForest;
	private final List<CMLTypeError> errors;
	private final List<CMLTypeWarning> warnings;

	// subcheckers
	private final IQuestionAnswer<TypeCheckInfo, PType> exp;
	private final IQuestionAnswer<TypeCheckInfo, PType> stm;
	private final IQuestionAnswer<TypeCheckInfo, PType> dad;


	// ---------------------------------------------
	// -- Dispatch to sub-checkers
	// ---------------------------------------------
	@Override
	public PType defaultPDeclaration(PDeclaration node, TypeCheckInfo question) {
		return node.apply(this.dad, question);
	}

	@Override
	public PType defaultPDefinition(PDefinition node, TypeCheckInfo question) {
		return node.apply(this.dad, question);
	}

	@Override
	public PType defaultPExp(PExp node, TypeCheckInfo question) {
		return node.apply(exp, question);
	}

	@Override
	public PType defaultPStm(PStm node, TypeCheckInfo question) {
		return node.apply(stm,question);
	}

	// ---------------------------------------------
	// -- Top Level Rule dispatching to sub checkers
	// ---------------------------------------------
	@Override
	public PType caseASourcefileSourcefile(ASourcefileSourcefile node,
			TypeCheckInfo question) {
	
		
		for(PDeclaration decl : node.getDecls())
			decl.apply(this, question);
		
		// Source file has no type
		return null;
	}











	public String getAnalysisName() { return "The CML Type Checker"; }

	public CmlTypeChecker()
	{
		exp = new TCExpressionVisitor(this);
		stm = new TCStatementVisitor();
		dad = new TCDeclAndDefVisitor(this);
		this.sourceForest = new LinkedList<ASourcefileSourcefile>();
		this.errors = new LinkedList<CMLTypeError>();
		this.warnings = new LinkedList<CMLTypeWarning>();
	}



	private static void runOnFile(File f) throws IOException
	{
		
		Reader r = null;
		if (f == null)
		{
			r = new BufferedReader(new InputStreamReader(System.in));
		}
		else
		{
			r = new FileReader(f);
		}
		CmlLexer lexer = new CmlLexer(r);
		CmlParser parser = new CmlParser(lexer);
		ASourcefileSourcefile tree = new ASourcefileSourcefile();
		parser.setDocument(tree);
		if (parser.parse())
		{	
			TypeCheckInfo tci = new TypeCheckInfo();
			CmlTypeChecker analysis = new CmlTypeChecker();
			tree.apply(analysis, tci);
			if (analysis.getErrors().size() > 0)
			{
				System.out.println("\n\nTYPE ERRORS EXIST:\n ");
				for(CMLTypeError e : analysis.getErrors())
					System.out.println(e);
			}
		}
	}


	public static void main(String[] args) throws IOException
	{
		File cml_examples = new File("../../docs/cml-examples");
		int failures = 0;
		int successes = 0;
		// runOnFile(null);
		
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





	public List<CMLTypeError> getErrors() {
		return errors;
	}





	public List<CMLTypeWarning> getWarnings() {
		return warnings;
	}

}