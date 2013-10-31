package eu.compassresearch.core.analysis.modelchecker.visitors;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AEqualsBinaryExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.program.PSource;


@SuppressWarnings("serial")
public class CMLModelCheckerAdaptor extends QuestionAnswerCMLAdaptor<StringBuilder, StringBuilder> {

	private List<PSource> sources;
	private final static String ANALYSIS_NAME = "Model Checker Visitor";
	
	public CMLModelCheckerAdaptor(List<PSource> sources) {
		this.sources = sources;
	}

	public CMLModelCheckerAdaptor(PSource singleSource) {
		this.sources = new LinkedList<PSource>();
		this.sources.add(singleSource);
	}
	
	@Override
	public StringBuilder caseAProcessDefinition(AProcessDefinition node,
			StringBuilder question) throws AnalysisException {
		
		question.append("domain StartProcDomain includes CSP_Properties at \"../resources/basic_formula_script.fml\" {\n");

		//it converts all structures defined in the process body (auxiliary actions, etc).
		node.getProcess().apply(this, question);

		int startIndex = question.indexOf("#");
		int endIndex = question.lastIndexOf("#");
		question.replace(startIndex, endIndex+1, "\"" + node.getName().toString() + "\"");		
		//question.re
		//it converts the top level process
		//question.append("  ProcDef(");
		//question.append("\"" + node.getName() + "\", ");
		/////PPPPPPPPPPPPPPPP
		//it applies recursivelly in the internal structure (the process body)  
		//question.append(").\n");
		

		question.append("  conforms := CSP_Semantics.conforms.\n");
		question.append("}\n\n");
		question.append("partial model StartProcModel of StartProcDomain{\n");
		question.append("  ProcHasDeadlock(\"" + node.getName() + "\")\n");
		question.append("}");
		
		//System.out.println("AProcessDefinition: " + node.toString());
		//System.out.println("name: " + node.getName());
		//System.out.println("_process: " + node.getProcess());
		
		//System.out.println(": " + node.getProcess().);
		
		//return node.getName() .apply(this, question);
		return question;
	}

	
	@Override
	public StringBuilder caseAActionProcess(AActionProcess node,
			StringBuilder question) throws AnalysisException {
		
		//it applies to each definition of this action process
		for (PDefinition definition : node.getDefinitionParagraphs()) {
			definition.apply(this,question);
		}
		
		//it converts the top level process
		question.append("  ProcDef(");
		question.append("#PROCNAME#");
		question.append(",");
		//parameters 
		question.append("nopar");
		question.append(",");
		//the body
		node.getAction().apply(this, question);
		question.append(").\n");
		return question;
	}
	
	//Visitor methods for actions
	
	@Override
	public StringBuilder caseAActionsDefinition(AActionsDefinition node,
			StringBuilder question) throws AnalysisException {
		
		//it applies to each internal definition
		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition currentActionDefinition : actions) {
			currentActionDefinition.apply(this, question);
		}
		return question;
	}

	
	@Override
	public StringBuilder caseAHidingAction(AHidingAction node,
			StringBuilder question) throws AnalysisException {
		//"acoes \ {ev}" hide(acoes,{ev})
		question.append("hide(");
		node.getLeft().apply(this, question);
		question.append(",");
		node.getChansetExpression().apply(this, question);
		question.append(")");
		//gerar os lieIn. fixo tem que mudar
		//question.append("lieIn(Sigma(\"b\"),\"[b]\").\n");
		return question;
	}



	@Override
	public StringBuilder caseAEnumVarsetExpression(AEnumVarsetExpression node,
			StringBuilder question) throws AnalysisException {
		
		//question.append("\"" + node.toString() + "\"");
		question.append("\"X\"");
		
		return question;
	}



	@Override
	public StringBuilder caseAActionDefinition(AActionDefinition node,
			StringBuilder question) throws AnalysisException {
		
		question.append("  ProcDef(");
		question.append("\"" + node.getName() + "\",");
		//parameters
		question.append("nopar");
		question.append(",");
		//it converts the internal action (body)
		node.getAction().apply(this, question);
		question.append(").\n");
		
		return question;
	}



	@Override
	public StringBuilder caseASkipAction(ASkipAction node,
			StringBuilder question) throws AnalysisException {
		
		question.append(node.toString());
		
		return question;
	}
	



	@Override
	public StringBuilder caseAStopAction(AStopAction node,
			StringBuilder question) throws AnalysisException {
		
		question.append(node.toString());
		
		return question;
	}
	
	
	@Override
	public StringBuilder caseACommunicationAction(ACommunicationAction node,
			StringBuilder question) throws AnalysisException {
		
		question.append("Prefix(Sigma(\"" + node.getIdentifier() + "\"), ");

		//it applies recursivelly in the internal structure
		node.getAction().apply(this, question);
		
		question.append(")");
		
		return question;
	}
	
	@Override
	public StringBuilder caseAExternalChoiceAction(AExternalChoiceAction node,
			StringBuilder question) throws AnalysisException {
		//it writes the left process into the buffer
		question.append("eChoice(");
		node.getLeft().apply(this, question);
		question.append(",");
		//it writes the right process into the buffer
		node.getRight().apply(this, question);
		question.append(")");
		
		return question;
	}

	@Override
	public StringBuilder caseAInternalChoiceAction(AInternalChoiceAction node,
			StringBuilder question) throws AnalysisException {
		//it writes the left process into the buffer
		question.append("iChoice(");
		node.getLeft().apply(this, question);
		question.append(",");
		//it writes the right process into the buffer
		node.getRight().apply(this, question);
		question.append(")");

		return question;
	}

	@Override
	public StringBuilder caseABlockStatementAction(ABlockStatementAction node,
			StringBuilder question) throws AnalysisException {
		
		node.getAction().apply(this,question);
		
		return question;
	}

	@Override
	public StringBuilder caseAGuardedAction(AGuardedAction node,
			StringBuilder question) throws AnalysisException {
		//it writes the conditional choice constructor
		question.append("bChoice(");
		//it writes the condition
		node.getExpression().apply(this, question);
		question.append(",");
		//it writes the behaviour in the if-true branch
		node.getAction().apply(this, question);
		question.append(",Stop)"); //the else branch of a guarded action is stop
		
		return question;
	}

	@Override
	public StringBuilder caseASequentialCompositionAction(
			ASequentialCompositionAction node, StringBuilder question)
			throws AnalysisException {
		
		//it writes the sequential composition constructor
		question.append("seqC(");
		
		//it writes the first action
		node.getLeft().apply(this, question);
		question.append(",");
		
		//it writes the second action
		node.getRight().apply(this, question);
		question.append(")");
		
		return question;
	}

	@Override
	public StringBuilder caseAReferenceAction(AReferenceAction node,
			StringBuilder question) throws AnalysisException {
		
		//the parameters also need to be written  
		question.append("proc(\"" + node.getName() + "\", nopar)");
		return question;
	}
    
	//Visitor methods for expressions
	@Override
	public StringBuilder caseAEqualsBinaryExp(AEqualsBinaryExp node,
			StringBuilder question) throws AnalysisException {
		
		question.append("EQ(");
		node.getLeft().apply(this,question);
		question.append(",");
		node.getRight().apply(this,question);
		question.append(")");
		
		return question;
	}
	
	@Override
	public StringBuilder caseAIntLiteralExp(AIntLiteralExp node,
			StringBuilder question) throws AnalysisException {
		
		question.append("Nat(");
		question.append(node.getValue());
		question.append(")");
		
		return question;
	}

	//to be used by the command line tool
	public String getAnalysisName() {
		return ANALYSIS_NAME;
	}

	
	/*
	public void readFromFileAndGenerate() throws IOException, AnalysisException{
		PSource source = Utilities.makeSourceFromFile(cmlFilePath);
		content = Utilities.readScriptFromFile(Utilities.BASIC_FORMULA_SCRIPT);
		generateFormulaCode();
		Utilities.writeScriptToFile(generatedFormulaFilePath,content);
	}
	
	public void generateFormulaCode(PSource source) throws IOException, AnalysisException {
		// for each CML paragraph
		for (PDefinition paragraph : source.getParagraphs()) {
			try {
				//System.out.println("Class: " + paragraph.getClass().getName());
				//System.out.println("String: " + paragraph.toString());
			    
				// process paragraph:
			    paragraph.apply(this,content);
			    
			} catch (Exception e) {
			    System.out.println("Error: " + e.getMessage());
			    //e.printStackTrace();
			}
		}
	}
	*/
	public String[] generateFormulaCodeForAll() throws IOException, AnalysisException {
		StringBuilder basicContent = new StringBuilder();
		String[] codes = new String[0];
		if(sources.size() > 0){
			codes = new String[sources.size()];
			basicContent = Utilities.readScriptFromFile(Utilities.BASIC_FORMULA_SCRIPT);
		}

		for (PSource source : sources) {
			StringBuilder content = new StringBuilder();
			content.append(basicContent.toString());
			for (PDefinition paragraph : source.getParagraphs()) {
				try {
					paragraph.apply(this,content);
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
			codes[sources.indexOf(source)] = content.toString();
			//it saves the current generated formula file
			//String formulaFileName = Utilities.generateFormulaFileName(((AFileSource)source).getFile().getPath());
			//System.out.println(formulaFileName);
			//Utilities.writeScriptToFile(formulaFileName,content);
			
		} 
		return codes;
	}

	public static void main(String[] args) throws IOException, AnalysisException{
		String cml_example = "src/test/resources/action-skip.cml";
		System.out.println("Testing on " + cml_example);
		List<PSource> sources = new LinkedList<PSource>();
		PSource source = Utilities.makeSourceFromFile(cml_example);
		sources.add(source);
		CMLModelCheckerAdaptor visitor = new CMLModelCheckerAdaptor(sources);
		String[] codes = visitor.generateFormulaCodeForAll();
		for (int i = 0; i < codes.length; i++) {
			System.out.println(codes[i]);
		}
		
	}

	@Override
	public StringBuilder createNewReturnValue(INode node, StringBuilder question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder createNewReturnValue(Object node,
			StringBuilder question) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
