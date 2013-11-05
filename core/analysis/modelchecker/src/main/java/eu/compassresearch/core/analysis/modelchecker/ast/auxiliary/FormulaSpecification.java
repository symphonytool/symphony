package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.io.IOException;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationUtilities;
import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class FormulaSpecification {

	private Domain auxiliaryDomain;
	private Domain syntaxDomain;
	private Domain semanticsDomain;
	private Domain propertiesDomain;
	private Domain problemDomain;
	private PartialModel problemModel;
	
	private void basicLoad() throws IOException{
		
		StringBuilder auxDefContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.AUXILIARY_DEFINITIONS_SCRIPT);
		this.auxiliaryDomain = new Domain("AuxiliaryDefinitions", null, auxDefContent.toString());
		
		StringBuilder cmlSyntaxContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.SYNTAX_DOMAIN_SCRIPT);
		this.syntaxDomain = new Domain("CMLSyntax", auxiliaryDomain, cmlSyntaxContent.toString());
		
		StringBuilder cmlSemanticsContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.SEMANTICS_DOMAIN_SCRIPT);
		this.semanticsDomain = new Domain("CMLSemantics", syntaxDomain, cmlSemanticsContent.toString());
		
		StringBuilder cmlPropertiesContent = FormulaIntegrationUtilities.readScriptFromFile(FormulaIntegrationUtilities.PROPERTIES_DOMAIN_SCRIPT);
		this.propertiesDomain = new Domain("CMLProperties", semanticsDomain, cmlPropertiesContent.toString());
		
		//initially the problem domain and partial model have no content
		this.problemDomain = new Domain("DependentModel", propertiesDomain,"");
		this.problemModel = new PartialModel(problemDomain);
		
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		context.semanticsDomain = this.semanticsDomain;
		
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
	
	public String buildFormulaScript() throws IOException, AnalysisException{
		
		StringBuilder result = new StringBuilder();
		
		//loads all domains to build the entire script
		this.basicLoad();
		
		
		//it adds user type definitions to the auxiliary domain
		handleUserTypeDefinitions();
		
		//it writes the auxiliary domain
		result.append(this.auxiliaryDomain.toFormula(MCNode.DEFAULT));
		
		//it writes the syntax domain
		result.append(this.syntaxDomain.toFormula(MCNode.DEFAULT));
		
		//it writes the semantic domain
		result.append(this.semanticsDomain.toFormula(MCNode.DEFAULT));
		
		//it writes the property
		result.append(this.propertiesDomain.toFormula(MCNode.DEFAULT));
		
		
		//it builds the problem domain for the problem to be analysed 
		ProblemDomainBuilder domainBuilder = new ProblemDomainBuilder();
		this.problemDomain.setContent(domainBuilder.buildProblemDomain());
		result.append(problemDomain.toFormula(MCNode.DEFAULT));
		
		//it builds the partial model for the problem to be analysed
		PartialModelBuilder partialModelBuilder = new PartialModelBuilder();
		this.problemModel.setContent(partialModelBuilder.buildPartialModelContent());
		result.append(this.problemModel.toFormula(MCNode.DEFAULT));
		
		return result.toString();
	}
}
