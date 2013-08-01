package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.ASetEnumSetExp;
import org.overture.ast.expressions.PExp;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PActionBase;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrationException;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaIntegrator;
import eu.compassresearch.core.analysis.modelchecker.api.FormulaResult;
import eu.compassresearch.core.analysis.modelchecker.api.IFormulaIntegrator;

@SuppressWarnings("serial")
public class CMLModelcheckerVisitor extends
	QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> {

	private List<PSource> sources;
	private final static String ANALYSIS_NAME = "Model Checker Visitor";
	private String propertyToCheck = Utilities.DEADLOCK_PROPERTY;

	public CMLModelcheckerVisitor(List<PSource> sources) {
		this.sources = sources;
	}

	public CMLModelcheckerVisitor(PSource singleSource) {
		this.sources = new LinkedList<PSource>();
		this.sources.add(singleSource);
	}
	
	public String getPropertyToCheck() {
		return propertyToCheck;
	}

	public void setPropertyToCheck(String propertyToCheck) {
		this.propertyToCheck = propertyToCheck;
	}

	@Override
	public StringBuilder caseAProcessDefinition(AProcessDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent()
				.append("domain StartProcDomain includes CSP_Properties {\n");
		        //.append("domain StartProcDomain includes CSP_Properties at \"./basic_formula_script.fml\" {\n");
				//.append("domain StartProcDomain includes CSP_Properties at \"../resources/basic_formula_script.fml\" {\n");

		// it converts all structures defined in the process body (auxiliary
		// actions, etc).
		node.getProcess().apply(this, question);

		int startIndex = question.getScriptContent().indexOf("#");
		int endIndex = question.getScriptContent().lastIndexOf("#");
		question.getScriptContent().replace(startIndex, endIndex + 1,
				"\"" + node.getName().toString() + "\"");
		// question.re
		// it converts the top level process
		// question.append("  ProcDef(");
		// question.append("\"" + node.getName() + "\", ");
		// ///PPPPPPPPPPPPPPPP
		// it applies recursivelly in the internal structure (the process body)
		// question.append(").\n");
		
		question.getScriptContent().append(
				"  conforms := CSP_Properties." + this.propertyToCheck+ ".\n");
		question.getScriptContent().append("}\n\n");
		question.getScriptContent().append(
				"partial model StartProcModel of StartProcDomain{\n");
		question.getScriptContent().append(
				"  GivenProc(\"" + node.getName() + "\")\n");
		question.getScriptContent().append("}");

		// System.out.println("AProcessDefinition: " + node.toString());
		// System.out.println("name: " + node.getName());
		// System.out.println("_process: " + node.getProcess());

		// System.out.println(": " + node.getProcess().);

		// return node.getName() .apply(this, question);
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAActionProcess(AActionProcess node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it applies to each definition of this action process
		//for (PDefinition definition : node.getDefinitionParagraphs()) {
		//	definition.apply(this, question);
		//}
		//question.info.put(node.getAction(), node.getDefinitionParagraphs());
		question.info.put(Utilities.LOCAL_DEFINITIONS_KEY, node.getDefinitionParagraphs());

		//Auxiliary processes will be written here
		question.getScriptContent().append("#AUXILIARY_PROCESSES#");
				
		// it converts the top level process
		question.getScriptContent().append("  ProcDef(");
		question.getScriptContent().append("#PROCNAME#");
		question.getScriptContent().append(",");
		// parameters
		question.getScriptContent().append("nopar");
		question.getScriptContent().append(",");
		// the body
		node.getAction().apply(this, question);
		question.getScriptContent().append(").\n");
		
		//if there is no auxiliary definitions, then remove the TAG
		int index = question.getScriptContent().indexOf("#AUXILIARY_PROCESSES#");
		if(index != -1){
			question.getScriptContent().replace(index, index + "#AUXILIARY_PROCESSES#".length() , "");
		}
		
		return question.getScriptContent();
	}

	// Visitor methods for actions
	@Override
	public StringBuilder caseAActionsDefinition(AActionsDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it applies to each internal definition
		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition currentActionDefinition : actions) {
			currentActionDefinition.apply(this, question);
		}
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if(sDecl instanceof AExpressionSingleDeclaration){
			 PExp pExp = ((AExpressionSingleDeclaration)sDecl).getExpression();
			 if(pExp instanceof ASetEnumSetExp){
				 indexes = ((ASetEnumSetExp) pExp).getMembers();
			 }
		}
		//building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction(node.getReplicatedAction(), Utilities.EXTERNAL_CHOICE, indexes.size());
		question.getScriptContent().append(replicatedAction.toString());
		
		//for (PExp indexValue : indexes) {
		//	CMLModelcheckerContext ctxt = new CMLModelcheckerContext();
		//	StringBuilder txtToBeReplicated = actionToBeReplicated.apply(this,ctxt);
			//String variableToBeReplaced = new String((String)ctxt.info.get(actionToBeReplicated.getName()));
					
		//	question.getScriptContent().append(txtToBeReplicated.toString().replace(variableToBeReplaced, indexValue.toString()));
		//}
		
		//Adding auxiliary definitions
		//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info.get(node);
		LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info.get(Utilities.LOCAL_DEFINITIONS_KEY);
		CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();		
		for (PDefinition pDefinition : localDefinitions) {
			pDefinition.apply(this, auxCtxt);
		}
		int auxIndex = question.getScriptContent().indexOf("#AUXILIARY_PROCESSES#");
		if(auxIndex != -1){
			question.getScriptContent().replace(auxIndex, auxIndex + "#AUXILIARY_PROCESSES#".length(), auxCtxt.getScriptContent().toString());
		}
		
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if(sDecl instanceof AExpressionSingleDeclaration){
			 PExp pExp = ((AExpressionSingleDeclaration)sDecl).getExpression();
			 if(pExp instanceof ASetEnumSetExp){
				 indexes = ((ASetEnumSetExp) pExp).getMembers();
			 }
		}
		//building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction(node.getReplicatedAction(), Utilities.SEQUENTIAL_COMPOSITION, indexes.size());
		question.getScriptContent().append(replicatedAction.toString());
		
		//for (PExp indexValue : indexes) {
		//	CMLModelcheckerContext ctxt = new CMLModelcheckerContext();
		//	StringBuilder txtToBeReplicated = actionToBeReplicated.apply(this,ctxt);
			//String variableToBeReplaced = new String((String)ctxt.info.get(actionToBeReplicated.getName()));
					
		//	question.getScriptContent().append(txtToBeReplicated.toString().replace(variableToBeReplaced, indexValue.toString()));
		//}
		
		//Adding auxiliary definitions
		//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info.get(node);
		LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info.get(Utilities.LOCAL_DEFINITIONS_KEY);
		CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();		
		for (PDefinition pDefinition : localDefinitions) {
			pDefinition.apply(this, auxCtxt);
		}
		int auxIndex = question.getScriptContent().indexOf("#AUXILIARY_PROCESSES#");
		if(auxIndex != -1){
			question.getScriptContent().replace(auxIndex, auxIndex + "#AUXILIARY_PROCESSES#".length(), auxCtxt.getScriptContent().toString());
		}
		
		
		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		PSingleDeclaration sDecl = node.getReplicationDeclaration().getFirst();
		LinkedList<PExp> indexes = new LinkedList<PExp>();
		if(sDecl instanceof AExpressionSingleDeclaration){
			 PExp pExp = ((AExpressionSingleDeclaration)sDecl).getExpression();
			 if(pExp instanceof ASetEnumSetExp){
				 indexes = ((ASetEnumSetExp) pExp).getMembers();
			 }
		}
		//building combination of processses based on simple constructs
		StringBuilder replicatedAction = buildReplicatedAction(node.getReplicatedAction(), Utilities.INTERNAL_CHOICE, indexes.size());
		question.getScriptContent().append(replicatedAction.toString());
		
		//for (PExp indexValue : indexes) {
		//	CMLModelcheckerContext ctxt = new CMLModelcheckerContext();
		//	StringBuilder txtToBeReplicated = actionToBeReplicated.apply(this,ctxt);
			//String variableToBeReplaced = new String((String)ctxt.info.get(actionToBeReplicated.getName()));
					
		//	question.getScriptContent().append(txtToBeReplicated.toString().replace(variableToBeReplaced, indexValue.toString()));
		//}
		
		//Adding auxiliary definitions
		//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info.get(node);
		LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info.get(Utilities.LOCAL_DEFINITIONS_KEY);
		CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();		
		for (PDefinition pDefinition : localDefinitions) {
			pDefinition.apply(this, auxCtxt);
		}
		int auxIndex = question.getScriptContent().indexOf("#AUXILIARY_PROCESSES#");
		if(auxIndex != -1){
			question.getScriptContent().replace(auxIndex, auxIndex + "#AUXILIARY_PROCESSES#".length(), auxCtxt.getScriptContent().toString());
		}
		
		
		return question.getScriptContent();
	}
	
	private StringBuilder buildReplicatedAction(PAction action,int kindOfAction, int times)
			throws AnalysisException{
		
		
		CMLModelcheckerContext localCtxt = new CMLModelcheckerContext();
		if(times == 1){
			action.apply(this,localCtxt);
		} else if (times > 1){
			if(kindOfAction == Utilities.EXTERNAL_CHOICE){
				localCtxt.getScriptContent().append("eChoice(");
			} else if (kindOfAction == Utilities.INTERNAL_CHOICE){
				localCtxt.getScriptContent().append("iChoice(");
			} else if (kindOfAction == Utilities.SEQUENTIAL_COMPOSITION){
				localCtxt.getScriptContent().append("seqC(");
			}
			action.apply(this,localCtxt);
			localCtxt.getScriptContent().append(",");
			StringBuilder rest = buildReplicatedAction(action, kindOfAction, times-1);
			localCtxt.getScriptContent().append(rest.toString());
			localCtxt.getScriptContent().append(")");
		}
		return localCtxt.getScriptContent();
	}
	
	@Override
	public StringBuilder caseAHidingAction(AHidingAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// "acoes \ {ev}" hide(acoes,{ev})
		question.getScriptContent().append("hide(");
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		node.getChansetExpression().apply(this, question);
		question.getScriptContent().append(")");
		// gerar os lieIn. fixo tem que mudar
		// question.append("lieIn(Sigma(\"b\"),\"[b]\").\n");
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAEnumVarsetExpression(AEnumVarsetExpression node,
			CMLModelcheckerContext question) throws AnalysisException {
		// question.append("\"" + node.toString() + "\"");
		question.getScriptContent().append("\"X\"");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAActionDefinition(AActionDefinition node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append("  ProcDef(");
		question.getScriptContent().append("\"" + node.getName() + "\",");
		// parameters
		question.getScriptContent().append("nopar");
		question.getScriptContent().append(",");
		// it converts the internal action (body)
		node.getAction().apply(this, question);
		question.getScriptContent().append(").\n");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAStopAction(AStopAction node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append(node.toString());

		return question.getScriptContent();
	}
	
	

	@Override
	public StringBuilder caseAChaosAction(AChaosAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.getScriptContent().append(node.toString());

		return question.getScriptContent();
	}

	
	@Override
	public StringBuilder caseADivAction(ADivAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		
		question.getScriptContent().append("Div");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseACommunicationAction(ACommunicationAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append(
				"Prefix(BasicEv(\"" + node.getIdentifier() + "\"), ");

		// it applies recursivelly in the internal structure
		node.getAction().apply(this, question);

		question.getScriptContent().append(")");

		return question.getScriptContent();
	}
	
	@Override
	public StringBuilder caseASkipAction(ASkipAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append(node.toString());
		
		return question.getScriptContent();

	}

	@Override
	public StringBuilder caseAExternalChoiceAction(AExternalChoiceAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it writes the left process into the buffer
		question.getScriptContent().append("eChoice(");
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		// it writes the right process into the buffer
		node.getRight().apply(this, question);
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAInternalChoiceAction(AInternalChoiceAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it writes the left process into the buffer
		question.getScriptContent().append("iChoice(");
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		// it writes the right process into the buffer
		node.getRight().apply(this, question);
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseABlockStatementAction(ABlockStatementAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		node.getAction().apply(this, question);

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAGuardedAction(AGuardedAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// it writes the conditional choice constructor
		question.getScriptContent().append("bChoice(");
		// it writes the condition
		node.getExpression().apply(this, question);
		question.getScriptContent().append(",");
		// it writes the behaviour in the if-true branch
		node.getAction().apply(this, question);
		question.getScriptContent().append(",Stop)"); // the else branch of a
														// guarded action is
														// stop

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseASequentialCompositionAction(
			ASequentialCompositionAction node, CMLModelcheckerContext question)
			throws AnalysisException {
		// it writes the sequential composition constructor
		question.getScriptContent().append("seqC(");

		// it writes the first action
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");

		// it writes the second action
		node.getRight().apply(this, question);
		question.getScriptContent().append(")");

					
		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAReferenceAction(AReferenceAction node,
			CMLModelcheckerContext question) throws AnalysisException {
		// the parameters also need to be written
		question.getScriptContent().append(
				"proc(\"" + node.getName() + "\", nopar)");
		
		//Adding auxiliary definitions
		//LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info.get(node);
		LinkedList<PDefinition> localDefinitions = (LinkedList<PDefinition>) question.info.get(Utilities.LOCAL_DEFINITIONS_KEY);
		
		CMLModelcheckerContext auxCtxt = new CMLModelcheckerContext();
		if(localDefinitions != null){
			for (PDefinition pDefinition : localDefinitions) {
				pDefinition.apply(this, auxCtxt);
			}
		}
		int auxIndex = question.getScriptContent().indexOf("#AUXILIARY_PROCESSES#");
		if(auxIndex != -1){
			question.getScriptContent().replace(auxIndex, auxIndex + "#AUXILIARY_PROCESSES#".length(), auxCtxt.getScriptContent().toString());
		}
		return question.getScriptContent();
	}

	// Visitor methods for expressions
	@Override
	public StringBuilder caseAEqualsBinaryExp(AEqualsBinaryExp node,
			CMLModelcheckerContext question) throws AnalysisException {
		question.getScriptContent().append("EQ(");
		node.getLeft().apply(this, question);
		question.getScriptContent().append(",");
		node.getRight().apply(this, question);
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}

	@Override
	public StringBuilder caseAIntLiteralExp(AIntLiteralExp node,
			CMLModelcheckerContext question) throws AnalysisException {

		question.getScriptContent().append("Nat(");
		question.getScriptContent().append(node.getValue());
		question.getScriptContent().append(")");

		return question.getScriptContent();
	}

	// to be used by the command line tool
	public String getAnalysisName() {
		return ANALYSIS_NAME;
	}
	
	public String[] generateFormulaCodeForAll() throws IOException, AnalysisException {
		//StringBuilder basicContent = new StringBuilder();
		String[] codes = new String[0];
		if(sources.size() > 0){
			codes = new String[sources.size()];
			//basicContent = Utilities.readScriptFromFile(Utilities.BASIC_FORMULA_SCRIPT);
		}

		for (PSource source : sources) {
			CMLModelcheckerContext content = new CMLModelcheckerContext();
			//content.getScriptContent().append(basicContent.toString());
			for (PDefinition paragraph : source.getParagraphs()) {
				try {
					paragraph.apply(this,content);
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
			codes[sources.indexOf(source)] = content.getScriptContent().toString();
			//it saves the current generated formula file
			//String formulaFileName = Utilities.generateFormulaFileName(((AFileSource)source).getFile().getPath());
			//System.out.println(formulaFileName);
			//Utilities.writeScriptToFile(formulaFileName,content);
			
		} 
		return codes;
	}
	
	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable{
		String cml_example = "src/test/resources/replicated-seqcomp.cml";
		System.out.println("Testing on " + cml_example);
		//List<PSource> sources = new LinkedList<PSource>();
		PSource source = Utilities.makeSourceFromFile(cml_example);
		//sources.add(source);
		CMLModelcheckerVisitor visitor = new CMLModelcheckerVisitor(source);
		visitor.setPropertyToCheck(Utilities.DEADLOCK_PROPERTY);
		String[] codes = visitor.generateFormulaCodeForAll();
		for (int i = 0; i < codes.length; i++) {
			System.out.println(codes[i]);
		}
		System.out.println("Analysing generated FORMULA specification");
		IFormulaIntegrator mc = FormulaIntegrator.getInstance();
		FormulaResult mcResult = mc.analyse(codes[0]);
		
		System.out.println("File " + cml_example + " is " + (mcResult.isSatisfiable()?"SAT":"UNSAT") + "\n");
		double loadTime = mcResult.getElapsedTimeLoad();
		double solveTime = mcResult.getElapsedTimeSolve();
		System.out.println("Analysis time (load + solve) = " + "(" + loadTime + " + " + solveTime + ") = " + (loadTime+solveTime) + " seconds\n");
		System.out.println("Base of Facts: \n");
		System.out.println(mcResult.getFacts());
		mc.finalize();
	}
		

}
