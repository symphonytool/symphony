package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.QuestionCMLAdaptor;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionClassDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAActionProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCAReferenceProcess;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

/**
 * Visitor that filters definitions in a list of definitions to contain 
 * as less as possible definitions to be translated in FORMULA. This visitor must 
 * be called before the visitor to translate the AST to FORMULA. Teh first parameter 
 * is the original list of definitions (all definitions in a project) and the second definition
 * is intented to contain the necessary definitions to be translated. 
 * @author Adalberto
 *
 */
public class MCDependenciesVisitor extends
		AnswerCMLAdaptor<List<PDefinition>> {

	private List<PDefinition> originalDefinitions;
	private List<PDefinition> filteredDefinitions;
	
	
	
	public MCDependenciesVisitor(List<PDefinition> originalDefinitions) {
		this.originalDefinitions = originalDefinitions;
		this.filteredDefinitions = new ArrayList<PDefinition>();
		this.copyBasicDefinitions();
	}

	/**
	 * This method copies the basic definitions (all definitions except process definitions).
	 */
	private void copyBasicDefinitions(){
		for (PDefinition pDefinition : originalDefinitions) {
			if(!(pDefinition instanceof AProcessDefinition)){
				filteredDefinitions.add(pDefinition);
			}
		}
	}

	@Override
	public List<PDefinition> caseAProcessDefinition(AProcessDefinition node) throws AnalysisException {
			
		//if(!filteredDefinitions.contains(node)){
			this.filteredDefinitions.add(node);
		//}
		
		node.getProcess().apply(this); 

		return this.filteredDefinitions;
	}
	
	
	

		
	@Override
	public List<PDefinition> caseAExternalChoiceProcess(
			AExternalChoiceProcess node) throws AnalysisException {

		node.getLeft().apply(this);
		node.getRight().apply(this);
		
		return this.filteredDefinitions;
	}

	
	
	@Override
	public List<PDefinition> caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node) throws AnalysisException {
		
		node.getLeft().apply(this);
		node.getRight().apply(this);
		
		return this.filteredDefinitions;
	}

	@Override
	public List<PDefinition> caseAReferenceProcess(AReferenceProcess node)
			throws AnalysisException {
		
		String name = node.getProcessName().getName();
		//it gets the reference to the process from the original paragraphs and visits it
		AProcessDefinition referenced = this.getProcessDefinitionByName(name);
		if(referenced != null){
			referenced.apply(this);
		}
		
		return this.filteredDefinitions;
	}

	private AProcessDefinition getProcessDefinitionByName(String name) {
		AProcessDefinition result = null;
		
		for (PDefinition pDef : originalDefinitions) {
			if(pDef instanceof AProcessDefinition){
				if(((AProcessDefinition) pDef).getName().toString().equals(name)){
					result = (AProcessDefinition) pDef;
					break;
				}
			}
		}
		
		return result;
	}
	
	@Override
	public List<PDefinition> createNewReturnValue(INode arg0)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PDefinition> createNewReturnValue(Object arg0)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
	

	public static void main(String[] args) throws Throwable {
		String cml_folder = "F:\\Copy\\ProjetoCompass\\compassresearch-code\\core\\analysis\\modelchecker\\src\\test\\resources";
		File folder = new File(cml_folder);
		

		//String cml_file = "src/test/resources/action-condchoice.cml";
		String cml_file = "src/test/resources/BEO_StreamingSoS_MC.cml";		
		
		PSource source = Utilities.makeSourceFromFile(cml_file);
		// Type check
		ITypeIssueHandler errors = VanillaFactory.newCollectingIssueHandle();
		ICmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(source.getParagraphs(), errors);

		//String mainProcessName = "T";
		String mainProcessName = "TestTPCS1";
		

		
		MCDependenciesVisitor visitor = new MCDependenciesVisitor(source.getParagraphs());
		AProcessDefinition mainDef = visitor.getProcessDefinitionByName(mainProcessName);
		
		List<PDefinition> filtered = mainDef.apply(visitor);
		
		NewCMLModelcheckerContext.getInstance().setNumberOfInstances(1);
		NewMCVisitor visitor1 = new NewMCVisitor();
		String formulaCode = visitor1.generateFormulaScript(filtered,Utilities.DEADLOCK_PROPERTY,mainProcessName);
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
	

	
}
