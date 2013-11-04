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
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationUtilities;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.Domain;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.PartialModel;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.pattern.MCAIdentifierPattern;

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
	
	private Domain auxiliaryDomain;
	private Domain syntaxDomain;
	private Domain semanticsDomain;
	private Domain propertiesDomain;
	private Domain problemDomain;
	private PartialModel problemModel;
	
	private NewMCActionVisitor actionVisitor;
	private NewMCDeclarationAndDefinitionVisitor declAndDefVisitor;
	private NewMCEmptyVisitor emptyVisitor;
	private NewMCExpressionVisitor expressionVisitor;
	private NewMCProcessVisitor processVisitor;
	private NewMCTypeAndValueVisitor typeAndValueVisitor;
	private NewMCParameterAndPatternVisitor paramAndPatternVisitor;
	
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
		//loadDomains();
	}
	
	private void loadDomains() throws IOException{
		
		Domain parentDomain = null;
		
		StringBuilder auxDefContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.AUXILIARY_DEFINITIONS_SCRIPT);
		this.auxiliaryDomain = new Domain("AuxiliaryDefinitions", parentDomain, auxDefContent.toString());
		
		StringBuilder cmlSyntaxContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.SYNTAX_DOMAIN_SCRIPT);
		this.syntaxDomain = new Domain("CMLSyntax", auxiliaryDomain, cmlSyntaxContent.toString());
		
		StringBuilder cmlSemanticsContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.SEMANTICS_DOMAIN_SCRIPT);
		this.semanticsDomain = new Domain("CMLSemantics", syntaxDomain, cmlSemanticsContent.toString());
		
		StringBuilder cmlPropertiesContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.PROPERTIES_DOMAIN_SCRIPT);
		this.propertiesDomain = new Domain("CMLProperties", semanticsDomain, cmlPropertiesContent.toString());
		
		//initially the problem domain and partial model have no content
		this.problemDomain = new Domain("DependentModel", semanticsDomain,"");
		this.problemModel = new PartialModel(problemDomain);
		
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
	/*
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
	
	
	
	public String getAnalysisName() {
		return ANALYSIS_NAME;
	}

	*/
	public String[] generateFormulaCodeForAll(String propertyToCheck) throws IOException,AnalysisException {
		 
		String[] codes = new String[0];
		if (sources.size() > 0) {
			codes = new String[sources.size()];
		}
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		context.setPropertyToCheck(propertyToCheck);
		
		for (PSource source : sources) {
			String dependentCode = "";
			for (PDefinition paragraph : source.getParagraphs()) {
				paragraph.apply(this, context);
			}
			
			//codes[sources.indexOf(source)] = basicContent + "\n" + dependentCode;
		}
		
		handleUserTypeDefinitions();
		
		MCAProcessDefinition mainProcessDef = getMainProcess();
		String content = mainProcessDef.toFormula(MCNode.DEFAULT);
		
		codes[0] = content;
		
		return codes;
	}
	
	private void handleUserTypeDefinitions(){
		StringBuilder userTypeDefs = new StringBuilder();
		StringBuilder userTypeNames = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		if(context.typeDefinitions.size() > 0){
			for (MCATypeDefinition typeDef : context.typeDefinitions) {
				userTypeDefs.append(typeDef.toFormula(MCNode.DEFAULT));
				userTypeDefs.append("\n");
				userTypeNames.append(" + " + typeDef.getName());
			}
			this.auxiliaryDomain.replace("//USER_DEF_TYPES", userTypeDefs.toString());
			this.auxiliaryDomain.replace("/*INCLUDE USER_DEF_TYPES*/", userTypeNames.toString());
		}
		
		
		
		
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
	
	private String mountFormulaScript(List<PDefinition> definitions, String mainProcessName) throws IOException, AnalysisException{
		
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		StringBuilder result = new StringBuilder();
		
		//loads all domains to build the entire script
		this.loadDomains();
		
		//it visits all paragraphs
		for (PDefinition paragraph : definitions) {
			paragraph.apply(this, context);
		}
		
		context.mainProcess = findMainProcessDefinition(mainProcessName);
		
		//it adds user type definitions to the auxiliary domain
		handleUserTypeDefinitions();
		
		//it writes the auxiliary domain
		result.append(this.auxiliaryDomain.toFormula(MCNode.DEFAULT));
		
		//it writes the syntax domain
		result.append(this.syntaxDomain.toFormula(MCNode.DEFAULT));
		
		//it writes the semantic domain
		result.append(this.semanticsDomain.toFormula(MCNode.DEFAULT));
		
		//it writes the property
		result.append(this.semanticsDomain.toFormula(MCNode.DEFAULT));
		
		//it builds the problem domain for the problem to be analysed 
		
		//it builds the partial model for the problem to be analysed
		
		return result.toString();
	}
	public String generateFormulaScript(String basicContent, List<PDefinition> definitions, String propertyToCheck) throws IOException, AnalysisException{
		
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		context.propertyToCheck = propertyToCheck;
		
		for (PDefinition paragraph : definitions) {
			paragraph.apply(this, context);
		}
		//return content.getScriptContent().toString();
		return "empty Script";
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
		visitor1.setPropertyToCheck(Utilities.DEADLOCK_PROPERTY);
		String[] codes1 = visitor1.generateFormulaCodeForAll(Utilities.DEADLOCK_PROPERTY);
		for (int j = 0; j < codes1.length; j++) {
			System.out.println(codes1[j]);
			
		}
		
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
