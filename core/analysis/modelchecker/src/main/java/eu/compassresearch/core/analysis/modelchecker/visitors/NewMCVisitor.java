package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.expressions.SBinaryExp;
import org.overture.ast.expressions.SSeqExp;
import org.overture.ast.expressions.SSetExp;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.statements.PStm;
import org.overture.ast.types.PType;
import org.overture.ast.types.SNumericBasicType;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.statements.PCMLStateDesignator;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.FormulaSpecification;
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
	
	private FormulaSpecification formulaSpecification;
	
	private NewMCActionVisitor actionVisitor;
	private NewMCDeclarationAndDefinitionVisitor declAndDefVisitor;
	private NewMCEmptyVisitor emptyVisitor;
	private NewMCExpressionVisitor expressionVisitor;
	private NewMCProcessVisitor processVisitor;
	private NewMCTypeAndValueVisitor typeAndValueVisitor;
	private NewMCParameterAndPatternVisitor paramAndPatternVisitor;
	private NewMCStmVisitor stmVisitor;
	
	public NewMCVisitor(List<PSource> sources) throws IOException {
		this.sources = sources;
		this.initialise();
	}
	public NewMCVisitor() throws IOException {
		this.sources = new LinkedList<PSource>();
		this.initialise();
	}
	public NewMCVisitor(PSource singleSource) throws IOException {
		this.sources = new LinkedList<PSource>();
		this.sources.add(singleSource);
		this.initialise();
	}

	private void initialise() throws IOException{
		this.actionVisitor = new NewMCActionVisitor(this);
		this.processVisitor = new NewMCProcessVisitor(this);
		this.declAndDefVisitor = new NewMCDeclarationAndDefinitionVisitor(this);
		this.emptyVisitor = new NewMCEmptyVisitor();
		this.expressionVisitor = new NewMCExpressionVisitor(this);
		this.typeAndValueVisitor = new NewMCTypeAndValueVisitor(this);
		this.paramAndPatternVisitor = new NewMCParameterAndPatternVisitor(this);
		this.formulaSpecification = new FormulaSpecification();
		this.stmVisitor = new NewMCStmVisitor(this);
	}
	
	@Override
	public MCNode defaultINode(INode node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		
		return  node.apply(emptyVisitor, question);
	}
	
	@Override
	public MCNode defaultPExp(PExp node, NewCMLModelcheckerContext question)
			throws AnalysisException {
		return node.apply(this.expressionVisitor, question);
	}
	
	@Override
	public MCNode defaultSBinaryExp(SBinaryExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		return node.apply(this.expressionVisitor, question);
	}
	
	@Override
	public MCNode defaultPCMLExp(PCMLExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		return node.apply(this.expressionVisitor, question);
	}
	
	@Override
	public MCNode defaultPVarsetExpression(PVarsetExpression node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		return node.apply(this.expressionVisitor, question);
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
	public MCNode defaultSSeqExp(SSeqExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		return node.apply(this.expressionVisitor, question);
	}
	
	@Override
	public MCNode defaultSSetExp(SSetExp node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		return node.apply(this.expressionVisitor, question);
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
	
	@Override
	public MCNode defaultPType(PType node,
			NewCMLModelcheckerContext question) throws AnalysisException{
		
		return node.apply(this.typeAndValueVisitor, question);
	}
	
	@Override
	public MCNode defaultPStateDesignator(
			PStateDesignator node, NewCMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(emptyVisitor, question);
	}
	
	@Override
	public MCNode defaultSNumericBasicType(SNumericBasicType node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		return node.apply(this.typeAndValueVisitor, question);
	}
	
	
	@Override
	public MCNode defaultPCommunicationParameter(PCommunicationParameter node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		return node.apply(this.paramAndPatternVisitor, question);
	}
	@Override
	public MCNode defaultPParametrisation(PParametrisation node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		return node.apply(this.paramAndPatternVisitor, question);
	}
	@Override
	public MCNode defaultPPattern(PPattern node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		return node.apply(this.paramAndPatternVisitor, question);
	}
	
	 @Override
	public MCNode defaultPStm(PStm node,
		NewCMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(this.stmVisitor, question);
	}
	 
	 
	
	@Override
	public MCNode defaultPCMLStateDesignator(PCMLStateDesignator node,
			NewCMLModelcheckerContext question) throws AnalysisException {
		
		return node.apply(stmVisitor, question);
	}
	/*
	 *
	
	@Override
	public StringBuilder defaultPExp(PExp node,
			CMLModelcheckerContext question) throws AnalysisException
	{
		return node.apply(this.expressionVisitor, question);
	}
	
	
	
	public String getAnalysisName() {
		return ANALYSIS_NAME;
	}

	*/
	public LinkedList<NameContent> generateFormulaCodeForAll(String propertyToCheck) throws IOException,AnalysisException {
		 
		LinkedList<NameContent> codes = new LinkedList<NameContent>();
		for (PSource source : sources) {
			if(source instanceof AFileSource){
				//System.out.println("Analysing file: " + ((AFileSource) source).getName());
				String currentScriptContent = this.generateFormulaScript(source.getParagraphs(), propertyToCheck);
				NameContent element = new NameContent(((AFileSource) source).getName(), currentScriptContent);
				codes.add(element);
			}
		}
		
		return codes;
	}
	
	private  MCAProcessDefinition getMainProcess(){
		MCAProcessDefinition result = null;
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		//for the moment we assume that only process is defined in a cml file 
		result = context.processDefinitions.getFirst();
		
		return result;
	}
	
	private MCAProcessDefinition findMainProcessDefinition(String mainProcessName){
		MCAProcessDefinition result = null;
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCAProcessDefinition pDefinition : context.processDefinitions) {
				if(pDefinition.getName().equals(mainProcessName)){
					result = pDefinition;
					break;
				}
			
		}
		return result;
	}
	
	
	public String generateFormulaScript(List<PDefinition> definitions, String propertyToCheck) throws IOException, AnalysisException{
		
		NewCMLModelcheckerContext.resetInstance();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		context.propertyToCheck = propertyToCheck;

		for (PDefinition paragraph : definitions) {
			paragraph.apply(this, context);
		}
		
		context.mainProcess = this.getMainProcess();
		
		String script = this.formulaSpecification.buildFormulaScript();
		
		return script;
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
	
	
	public static void main(String[] args) throws Throwable {
		String cml_folder_name = "src/test/resources";
		String cml_folder = "F:\\Copy\\ProjetoCompass\\compassresearch-code\\core\\analysis\\modelchecker\\src\\test\\resources";
		File folder = new File(cml_folder);
		File[] files = new File[0];
		if(folder.isDirectory()){
			files = folder.listFiles();
		}
		
		String cml_file = "src/test/resources/minimondex-incomplete.cml";
		System.out.println("Testing on " + cml_file);
		PSource source1 = Utilities.makeSourceFromFile(cml_file);
		NewMCVisitor visitor1 = new NewMCVisitor(source1);
		String formulaCode = visitor1.generateFormulaScript(source1.getParagraphs(),Utilities.DEADLOCK_PROPERTY);
		//String[] codes1 = visitor1.generateFormulaCodeForAll(Utilities.DEADLOCK_PROPERTY);
		//for (int j = 0; j < codes1.length; j++) {
		//	System.out.println(codes1[j]);
			
		//}
		System.out.println(formulaCode);
		
		/*for (int i = 0; i < files.length; i++) {
			String cml_example = cml_folder_name + "/" + files[i].getName();
			System.out.println("Testing on " + cml_example);
			PSource source = Utilities.makeSourceFromFile(cml_example);
			NewMCVisitor visitor = new NewMCVisitor(source);
			visitor.setPropertyToCheck(Utilities.DEADLOCK_PROPERTY);
			String[] codes = visitor.generateFormulaCodeForAll(Utilities.DEADLOCK_PROPERTY);
			for (int j = 0; j < codes.length; j++) {
				System.out.println(codes[j]);
			}
		}*/
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
