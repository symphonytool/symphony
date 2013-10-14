package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.modules.AModuleModules;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.statements.PStm;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationUtilities;

/**
 * The main MC visitor. It obtains other visitors from a factory.
 * @author Adalberto
 *
 */
public class MCVisitor extends
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> {

	private List<PSource> sources;
	private final static String ANALYSIS_NAME = "Model Checker Visitor";
	private String propertyToCheck = Utilities.DEADLOCK_PROPERTY;
	private StringBuilder basicContent;
	
	private MCActionVisitor actionVisitor;
	private MCDeclarationAndDefinitionVisitor declAndDefVisitor;
	private MCEmptyVisitor emptyVisitor;
	private MCExpressionVisitor expressionVisitor;
	private MCProcessVisitor processVisitor;
	private MCTypeAndValueVisitor typeAndValueVisitor;

	public MCVisitor(List<PSource> sources) {
		this.sources = sources;
		this.initialise();
	}
	public MCVisitor() {
		this.sources = new LinkedList<PSource>();
		this.initialise();
	}
	public MCVisitor(PSource singleSource) {
		this.sources = new LinkedList<PSource>();
		this.sources.add(singleSource);
		this.initialise();
	}

	private void initialise(){
		basicContent = new StringBuilder();
		this.actionVisitor = new MCActionVisitor(this);
		this.declAndDefVisitor = new MCDeclarationAndDefinitionVisitor(this);
		this.emptyVisitor = new MCEmptyVisitor();
		this.expressionVisitor = new MCExpressionVisitor(this);
		this.processVisitor = new MCProcessVisitor(this);
		this.typeAndValueVisitor = new MCTypeAndValueVisitor(this);
	}
	
	@Override
	public StringBuilder defaultINode(INode node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		
		return  node.apply(emptyVisitor, question);
	}
	
	@Override
	public StringBuilder defaultPDefinition(PDefinition node,
			CMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(this.declAndDefVisitor, question);
	}

	@Override
	public StringBuilder defaultPSingleDeclaration(
			PSingleDeclaration node, CMLModelcheckerContext question)
			throws AnalysisException
	{
		return node.apply(this.declAndDefVisitor, question);
	}

	@Override
	public StringBuilder defaultPProcess(PProcess node,
			CMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(this.processVisitor, question);
	}

	@Override
	public StringBuilder defaultPAction(PAction node,
			CMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(this.actionVisitor, question);
	}

	/*@Override
	public StringBuilder defaultPStm(PStm node,
			CMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(this.statementVisitor, question);
	}
	*/
	
	@Override
	public StringBuilder caseAValParametrisation(AValParametrisation node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append("Int(");
		question.getScriptContent().append(node.getDeclaration().getIdentifiers().getFirst().toString());
		question.getScriptContent().append(")");
		return question.getScriptContent();
	}



	@Override
	public StringBuilder defaultPExp(PExp node,
			CMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(this.expressionVisitor, question);
	}
	
	@Override
	public StringBuilder defaultPType(PType node,
			CMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(this.typeAndValueVisitor, question);
	}
	
	@Override
	public StringBuilder defaultPStateDesignator(
			PStateDesignator node, CMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(emptyVisitor, question);
	}
	
	public String getAnalysisName() {
		return ANALYSIS_NAME;
	}

	
	public String[] generateFormulaCodeForAll(String propertyToCheck) throws IOException,AnalysisException {

		String[] codes = new String[0];
		if (sources.size() > 0) {
			codes = new String[sources.size()];
		}
		for (PSource source : sources) {
			CMLModelcheckerContext context = new CMLModelcheckerContext();
			context.setPropertyToCheck(propertyToCheck);
			this.basicContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.BASIC_FORMULA_SCRIPT);
			for (PDefinition paragraph : source.getParagraphs()) {
				paragraph.apply(this, context);
			}
			codes[sources.indexOf(source)] = basicContent + "\n" + context.getScriptContent()
					.toString();
		}
		return codes;
	}
	
	public String generateFormulaScript(String basicContent, List<PDefinition> definitions, String propertyToCheck) throws IOException, AnalysisException{
		
		CMLModelcheckerContext content = new CMLModelcheckerContext();
		//adds the basic content (semantics embedding) to the generated script
		
		setPropertyToCheck(propertyToCheck);
		getBasicContent().append(basicContent);
		content.getScriptContent().append(basicContent);
		for (PDefinition paragraph : definitions) {
			paragraph.apply(this, content);
		}
		return content.getScriptContent().toString();
	}
	
	public List<PSource> getSources() {
		return sources;
	}
	public void setSources(List<PSource> sources) {
		this.sources = sources;
	}
	public String getPropertyToCheck() {
		return propertyToCheck;
	}
	public void setPropertyToCheck(String propertyToCheck) {
		this.propertyToCheck = propertyToCheck;
	}
	public StringBuilder getBasicContent() {
		return basicContent;
	}
	public void setBasicContent(StringBuilder basicContent) {
		this.basicContent = basicContent;
	}

	public static void main(String[] args) throws Throwable {
		//String cml_example = "src/test/resources/action-operationcall-noargs.cml";
		//String cml_example = "src/test/resources/replicated-externalchoice.cml";
		//String cml_example = "src/test/resources/replicated-generalised-parallelism.cml";
		//String cml_example = "src/test/resources/action-seq-comp2.cml";
		//String cml_example = "src/test/resources/action-vardecl.cml";
		//String cml_example = "src/test/resources/action-vardecl2.cml";
		//String cml_example = "src/test/resources/action-reference-parametrised.cml";
		//String cml_example = "src/test/resources/action-guard-stateVar.cml";
		//String cml_example = "src/test/resources/action-guard.cml";
		//String cml_example = "src/test/resources/minimondex.cml";
		//String cml_example = "src/test/resources/action-stop.cml";
		String cml_example = "src/test/resources/action-chaos.cml";
		//String cml_example = "src/test/resources/replicated-seqcomp.cml";
		//String cml_example = "src/test/resources/Dphils.cml";
		//String cml_example = "src/test/resources/action-not-implemented.cml";
		System.out.println("Testing on " + cml_example);
		// List<PSource> sources = new LinkedList<PSource>();
		PSource source = Utilities.makeSourceFromFile(cml_example);
		// sources.add(source);
		//String basic = Utilities.readScriptFromFile(Utilities.BASIC_FORMULA_SCRIPT).toString();
		//String code = CMLModelcheckerVisitor.generateFormulaScript(basic, source.getParagraphs(), Utilities.DEADLOCK_PROPERTY);
		//System.out.println(code);
		MCVisitor visitor = new MCVisitor(source);
		visitor.setPropertyToCheck(Utilities.DEADLOCK_PROPERTY);
		String[] codes = visitor.generateFormulaCodeForAll(Utilities.DEADLOCK_PROPERTY);
		for (int i = 0; i < codes.length; i++) {
			System.out.println(codes[i]);
		}
		
	}
	
	
	@Override
	public StringBuilder createNewReturnValue(INode arg0,
			CMLModelcheckerContext arg1) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public StringBuilder createNewReturnValue(Object arg0,
			CMLModelcheckerContext arg1) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
