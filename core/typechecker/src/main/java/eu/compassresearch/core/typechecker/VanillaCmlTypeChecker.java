package eu.compassresearch.core.typechecker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PAlternative;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PBind;
import org.overture.ast.patterns.PMultipleBind;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.patterns.PPatternBind;
import org.overture.ast.statements.PAlternativeStm;
import org.overture.ast.statements.PClause;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.PType;
import org.overture.typechecker.FlatEnvironment;
import org.overture.typechecker.TypeCheckInfo;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PAlternativeAction;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.api.TypeComparator;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeError;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeWarning;

@SuppressWarnings("serial")
class VanillaCmlTypeChecker extends AbstractTypeChecker {


	@Override
	public PType defaultPAlternativeAction(PAlternativeAction node,
			TypeCheckInfo question) throws AnalysisException {
		return addErrorForMissingType(node, node.apply(this.act, question));	}

	// ---------------------------------------------
	// -- Type Checker State
	// ---------------------------------------------m
	// subcheckers
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> exp; // expressions
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> act; // actions
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> dad; // definition and decls
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> typ; // basic
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> prc; // process
	private IQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> bnd; // bind
	// type
	// checker
	private boolean lastResult;
	private final TypeComparator typeComparator;
	private AClassClassDefinition globalRoot;

	private void initialize(TypeIssueHandler issueHandler) {
		if (issueHandler != null)
			this.issueHandler = issueHandler;
		else
			this.issueHandler = new CollectingIssueHandler();

		TCActionVisitor actionVisitor = new TCActionVisitor(this, this.issueHandler, typeComparator); 
		exp = new TCExpressionVisitor(this, this.issueHandler, typeComparator);
		act = actionVisitor;
		dad = new TCDeclAndDefVisitor(this, typeComparator, this.issueHandler, actionVisitor);
		typ = new TCTypeVisitor(this, this.issueHandler);
		prc = new TCProcessVisitor(this, this.issueHandler, typeComparator);
		bnd = new TCBindVisitor(this,this.issueHandler);

	}

	// ---------------------------------------------
	// -- Dispatch to sub-checkers
	// ---------------------------------------------

	private PType addErrorForMissingType(INode node, PType type) {
		if (type == null) {
			// addTypeError(node, "Insufficient type checker implementation.");
			return new AErrorType();
		} else
			return type;

	}

	@Override
	public PType defaultPMultipleBind(PMultipleBind node, TypeCheckInfo question)
			throws AnalysisException {
		return addErrorForMissingType(node, node.apply(bnd, question));
	}

	
	
	@Override
	public PType defaultPVarsetExpression(PVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException {
		return addErrorForMissingType(node, node.apply(exp, question));
	}

	@Override
	public PType defaultPBind(PBind node, TypeCheckInfo question)
			throws AnalysisException {
		return addErrorForMissingType(node, node.apply(bnd, question));
	}

	@Override
	public PType defaultPType(PType node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		return addErrorForMissingType(node, node.apply(typ, question));
	}



	@Override
	public PType defaultPPattern(PPattern node, TypeCheckInfo question)
			throws AnalysisException {
		return addErrorForMissingType(node,node.apply(bnd,question));
	}

	@Override
	public PType defaultPPatternBind(PPatternBind node, TypeCheckInfo question)
			throws AnalysisException {
		return addErrorForMissingType(node, node.apply(this.bnd, question));
	}



	@Override
	public PType defaultINode(INode node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		return addErrorForMissingType(node, super.defaultINode(node, question));
	}




	@Override
	public PType defaultPSingleDeclaration(PSingleDeclaration node,
			TypeCheckInfo question) throws AnalysisException {
		return addErrorForMissingType(node, node.apply(this.dad, question));
	}


	@Override
	public PType defaultPAlternative(PAlternative node, TypeCheckInfo question)
			throws AnalysisException {
		return addErrorForMissingType(node, node.apply(this.act, question));
	}

	@Override
	public PType defaultPAlternativeStm(PAlternativeStm node,
			TypeCheckInfo question) throws AnalysisException {
		return addErrorForMissingType(node, node.apply(this.act, question));
	}

	@Override
	public PType defaultPDefinition(PDefinition node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		return addErrorForMissingType(node, node.apply(this.dad, question));
	}

	@Override
	public PType defaultPExp(PExp node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		return addErrorForMissingType(node, node.apply(exp, question));
	}

	@Override
	public PType defaultPProcess(PProcess node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		return node.apply(prc, question);
	}

	@Override
	public PType defaultPAction(PAction node,
			org.overture.typechecker.TypeCheckInfo question)
					throws AnalysisException {
		return node.apply(act, question);
	}




	@Override
	public PType defaultPClause(PClause node, TypeCheckInfo question)
			throws AnalysisException {
		return addErrorForMissingType(node, node.apply(act,question));
	}

	// ---------------------------------------------
	// -- Public API to CML Type Checker
	// ---------------------------------------------
	/**
	 * This method is invoked by the command line tool when pretty printing the
	 * analysis name.
	 * 
	 * @return Pretty short name for this analysis.
	 */
	public String getAnalysisName() {
		return "The CML Type Checker";
	}

	/**
	 * Construct a CmlTypeChecker with the intension of checking a list of
	 * PSources. These source may refer to each other.
	 * 
	 * 
	 * @param cmlSources
	 *            - Source containing CML Paragraphs for type checking.
	 */
	public VanillaCmlTypeChecker(Collection<PSource> cmlSources,
			TypeIssueHandler issueHandler) {

		this.sourceForest = cmlSources;
		typeComparator = SimpleTypeComparator.newInstance();
		initialize(issueHandler);
	}

	void clear() {
		cleared = true;
		sourceForest = null;

	}

	public VanillaCmlTypeChecker(Collection<PSource> cmlSource,
			TypeComparator typeComparator, TypeIssueHandler issueHandler) {
		this.sourceForest = new LinkedList<PSource>();
		sourceForest.addAll(cmlSource);
		this.typeComparator = typeComparator;
		initialize(issueHandler);
	}

	/**
	 * Construct a CmlTypeChecker with the intension of checking a single
	 * source.
	 * 
	 * @param singleSource
	 */
	public VanillaCmlTypeChecker(PSource singleSource,
			TypeIssueHandler issueHandler) {

		this.sourceForest = new LinkedList<PSource>();
		this.sourceForest.add(singleSource);
		typeComparator = SimpleTypeComparator.newInstance();
		initialize(issueHandler);

	}

	/**
	 * Run the type checker. This will update the source(s) this type checker
	 * instance was constructed with.
	 * 
	 * @return - Returns true if the entire tree could be type checked without
	 *         errors.
	 */
	public boolean typeCheck() {

		try {
		eu.compassresearch.core.typechecker.CmlTypeCheckInfo info = eu.compassresearch.core.typechecker.CmlTypeCheckInfo
				.getNewTopLevelInstance(this.issueHandler, globalRoot);


		if (!cleared)
			return lastResult;

		try {
			// Collect classes, processes, global values, global types and global functions
			globalRoot = CollectGlobalStateClass.getGlobalRoot(
					this.sourceForest, issueHandler, info);


			// Add all global definitions to the environment
			for (PDefinition def : globalRoot.getDefinitions()) {

				List<PDefinition> l = TCDeclAndDefVisitor.handleDefinitionsForOverture(def);
				if (l != null)
					for(PDefinition dd : l)
					{
						if (dd instanceof ATypeDefinition)
							info.addType(dd.getName(), dd);
						else
							info.addVariable(dd.getName(),dd);
					}
			}
			info.env.setEnclosingDefinition(globalRoot);
			info.scope = NameScope.GLOBAL;
			PType globalRootType = ((TCDeclAndDefVisitor) dad)
					.typeCheckOvertureClass(globalRoot, info);
			if (!TCDeclAndDefVisitor.successfulType(globalRootType)) {
				issueHandler.addTypeError(globalRoot,
						TypeErrorMessages.PARAGRAPH_HAS_TYPES_ERRORS
						.customizeMessage("Global Definitions"));
				return false;
			}

		} catch (AnalysisException e) {
			e.printStackTrace();
		}

		// Check the channels
		for(PSource s : sourceForest)
		{
			for(PDefinition paragraph : s.getParagraphs())
			{
				if (paragraph instanceof AChannelsDefinition || paragraph instanceof AChansetsDefinition)
				{
					try {
						PType type = paragraph.apply(this, info);
						if (!TCDeclAndDefVisitor.successfulType(type))
							paragraph.setType(issueHandler.addTypeError(paragraph, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(paragraph+"")));
					} catch (AnalysisException e) {
						issueHandler.addTypeError(paragraph, e.getMessage());
					}
				}
			}
		}
		if (issueHandler.hasErrors()) return false;

		// for each source type check classes and processes in depth
		for (PSource s : sourceForest) {
			try {
			for (PDefinition paragraph : s.getParagraphs()) {
				if (paragraph instanceof AClassDefinition
						|| paragraph instanceof AProcessDefinition)

					try {
						PType topType = paragraph.apply(this, info);
						if (topType == null || topType instanceof AErrorType) {
							issueHandler
							.addTypeError(
									paragraph,
									TypeErrorMessages.PARAGRAPH_HAS_TYPES_ERRORS
									.customizeMessage(paragraph
											.getName()
											.toString()));
						}
					} catch (AnalysisException ae) {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ae.printStackTrace(new PrintStream(baos));
						issueHandler
						.addTypeError(
								s,
								"The COMPASS Type checker failed on this cml-source. Please submit it for investigation to rala@iha.dk.\n"
										+ new String(baos.toByteArray()));
						// This means we have a bug in the type checker
						return false;
					} catch (ClassCastException e) {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						PrintWriter out = new PrintWriter(baos);
						e.printStackTrace(out);
						out.flush();
						issueHandler
						.addTypeError(
								paragraph,
								"Ill defined ast definition. Check that the implied AST-node is not defined in both cml.ast and in overtureII.astv2. Naturally, if this is the case the visitor has an ambigouos choice.\n"
										+ e.getMessage()
										+ "\n"
										+ new String(baos.toByteArray()));
					}
			}
			} catch (RuntimeException e)
			{
				issueHandler.addTypeError(s, TypeErrorMessages.TYPE_CHECK_INTERNAL_FAILURE.customizeMessage(CmlTCUtil.getErrorMessages(e)));			
			}
		}
		super.cleared = false;

		return !super.issueHandler.hasErrors();
		} catch (RuntimeException e)

		{
			PSource first = null;
			if(!sourceForest.isEmpty())
			{
				first = sourceForest.iterator().next();
				issueHandler.addTypeError(first, TypeErrorMessages.TYPE_CHECK_INTERNAL_FAILURE.customizeMessage(CmlTCUtil.getErrorMessages(e)));
			}
			return false;
		}
	}

	/**
	 * Get errors that occurred while type checking.
	 * 
	 * @return list of CMLTypeErrors
	 */
	public List<CMLTypeError> getTypeErrors() {
		return issueHandler.getTypeErrors();
	}

	/**
	 * Get warnings that occurred while type checking. The type check method
	 * will return true even though this returns an non-empty list.
	 * 
	 * @return list of CMLTypeWarnings
	 */
	public List<CMLTypeWarning> getTypeWarnings() {
		return issueHandler.getTypeWarnings();
	}

	// ---------------------------------------
	// Static stuff for running the TypeChecker from Eclipse
	// ---------------------------------------

	// setting the file on AFileSource allows the CmlParser factory method
	// to create both parser and lexer.
	private static PSource prepareSource(File f) {
		if (f == null) {
			AInputStreamSource iss = new AInputStreamSource();
			iss.setStream(System.in);
			iss.setOrigin("stdin");
			return iss;
		} else {
			AFileSource fs = new AFileSource();
			fs.setName(f.getName());
			fs.setFile(f);
			return fs;
		}
	}

	private static void runOnFile(File f) throws IOException {
		// set file name
		PSource source = prepareSource(f);

		ANTLRInputStream in = new ANTLRInputStream(new FileInputStream(f));
		CmlLexer lexer = new CmlLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmlParser parser = new CmlParser(tokens);


		try {
			source.setParagraphs(parser.source());
		} catch (RecognitionException e) {
			e.printStackTrace();
			return;
		}

		// Type check
		VanillaCmlTypeChecker cmlTC = new VanillaCmlTypeChecker(source,
				VanillaFactory.newCollectingIssueHandle());

		// Print result and report errors if any
		if (!cmlTC.typeCheck()) {
			System.out.println("Failed to type check" + source.toString());
		}
		;

		// Report success
		System.out.println("The given CML Program is type checked.");
	}

	public static void main(String[] args) throws IOException {
		File cml_examples = new File("../../docs/cml-examples");
		int failures = 0;
		int successes = 0;
		// runOnFile(null);

		if (cml_examples.isDirectory()) {
			for (File example : cml_examples.listFiles()) {
				System.out.print("Typechecking example: " + example.getName()
						+ " \t\t...: ");
				System.out.flush();
				try {
					runOnFile(example);
					System.out.println("done");
					successes++;
				} catch (Exception e) {
					System.out.println("exception");
					failures++;
				}
			}
		}

		System.out.println(successes + " was successful, " + failures
				+ " was failures.");

	}

	public boolean hasErrors() {
		return issueHandler.hasErrors();
	}

	public boolean hasWarnings() {
		return issueHandler.hasWarnings();
	}

	public boolean hasIssues() {
		return issueHandler.hasIssues();
	}

}