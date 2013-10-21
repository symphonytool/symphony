package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationUtilities;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;

/**
 * The main MC visitor. It obtains other visitors from a factory.
 * @author Adalberto
 *
 */
public class NewMCVisitor extends
		QuestionAnswerCMLAdaptor<NewCMLModelcheckerContext, MCNode> {

	private List<PSource> sources;
	private final static String ANALYSIS_NAME = "Model Checker Visitor";
	private String propertyToCheck = Utilities.DEADLOCK_PROPERTY;
	private StringBuilder basicContent;
	
	private NewMCActionVisitor actionVisitor;
	private NewMCDeclarationAndDefinitionVisitor declAndDefVisitor;
	private NewMCEmptyVisitor emptyVisitor;
	//private NewMCExpressionVisitor expressionVisitor;
	private NewMCProcessVisitor processVisitor;
	//private NewMCTypeAndValueVisitor typeAndValueVisitor;

	public NewMCVisitor(List<PSource> sources) {
		this.sources = sources;
		this.initialise();
	}
	public NewMCVisitor() {
		this.sources = new LinkedList<PSource>();
		this.initialise();
	}
	public NewMCVisitor(PSource singleSource) {
		this.sources = new LinkedList<PSource>();
		this.sources.add(singleSource);
		this.initialise();
	}

	private void initialise(){
		basicContent = new StringBuilder();
		this.actionVisitor = new NewMCActionVisitor(this);
		this.processVisitor = new NewMCProcessVisitor(this);
		this.declAndDefVisitor = new NewMCDeclarationAndDefinitionVisitor(this);
		this.emptyVisitor = new NewMCEmptyVisitor();
	}
	
	@Override
	public MCNode defaultINode(INode node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		
		return  node.apply(emptyVisitor, question);
	}
	
	
	@Override
	public MCNode defaultPDefinition(PDefinition node,
			NewCMLModelcheckerContext question) throws AnalysisException{
		return node.apply(this.declAndDefVisitor, question);
	}

	@Override
	public MCNode defaultPSingleDeclaration(
			PSingleDeclaration node, NewCMLModelcheckerContext question)
			throws AnalysisException{
		
		return node.apply(this.declAndDefVisitor, question);
	}

	@Override
	public MCNode defaultPProcess(PProcess node,
			NewCMLModelcheckerContext question) throws AnalysisException{
		
		return node.apply(this.processVisitor, question);
	}
	
	@Override
	public MCNode defaultPAction(PAction node,
			NewCMLModelcheckerContext question) throws AnalysisException{
		
		return node.apply(this.actionVisitor, question);
	}
	
	/*
	@Override
	public StringBuilder defaultPVarsetExpression(PVarsetExpression node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		return node.apply(this.expressionVisitor, question);
	}
	

	

	@Override
	public StringBuilder defaultPStm(PStm node,
			CMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(this.statementVisitor, question);
	}
	
	
	@Override
	public MCNode caseAValParametrisation(AValParametrisation node,
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

	*/
	public String[] generateFormulaCodeForAll(String propertyToCheck) throws IOException,AnalysisException {

		String[] codes = new String[0];
		if (sources.size() > 0) {
			codes = new String[sources.size()];
		}
		for (PSource source : sources) {
			NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
			context.setPropertyToCheck(propertyToCheck);
			String dependentCode = "";
			this.basicContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.BASIC_FORMULA_SCRIPT);
			for (PDefinition paragraph : source.getParagraphs()) {
				if (paragraph instanceof AProcessDefinition){
					MCAProcessDefinition mcNode =  (MCAProcessDefinition) paragraph.apply(this, context);
					dependentCode = mcNode.toFormula(MCNode.DEFAULT);
				}
			}
			codes[sources.indexOf(source)] = basicContent + "\n" + dependentCode;
		}
		return codes;
	}
	
	public String generateFormulaScript(String basicContent, List<PDefinition> definitions, String propertyToCheck) throws IOException, AnalysisException{
		
		NewCMLModelcheckerContext content = new NewCMLModelcheckerContext();
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
		String cml_folder_name = "src/test/resources";
		String cml_folder = "D:\\COMPASS\\compassresearch-code\\core\\analysis\\modelchecker\\src\\test\\resources";
		File folder = new File(cml_folder);
		File[] files = new File[0];
		if(folder.isDirectory()){
			files = folder.listFiles();
		}
		
		String cml_file = "src/test/resources/action-div.cml";
		System.out.println("Testing on " + cml_file);
		PSource source1 = Utilities.makeSourceFromFile(cml_file);
		NewMCVisitor visitor1 = new NewMCVisitor(source1);
		visitor1.setPropertyToCheck(Utilities.DEADLOCK_PROPERTY);
		String[] codes1 = visitor1.generateFormulaCodeForAll(Utilities.DEADLOCK_PROPERTY);
		for (int j = 0; j < codes1.length; j++) {
			System.out.println(codes1[j]);
		}
		
		for (int i = 0; i < files.length; i++) {
			String cml_example = cml_folder_name + "/" + files[i].getName();
			System.out.println("Testing on " + cml_example);
			PSource source = Utilities.makeSourceFromFile(cml_example);
			NewMCVisitor visitor = new NewMCVisitor(source);
			visitor.setPropertyToCheck(Utilities.DEADLOCK_PROPERTY);
			String[] codes = visitor.generateFormulaCodeForAll(Utilities.DEADLOCK_PROPERTY);
			for (int j = 0; j < codes.length; j++) {
				System.out.println(codes[j]);
			}
		}
	}
	
	
	@Override
	public MCNode createNewReturnValue(INode node,
			NewCMLModelcheckerContext question) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public MCNode createNewReturnValue(Object node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
