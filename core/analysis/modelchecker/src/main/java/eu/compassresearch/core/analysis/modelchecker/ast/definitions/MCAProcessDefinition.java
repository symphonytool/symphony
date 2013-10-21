package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.process.MCPProcess;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAProcessDefinition implements MCPCMLDefinition {

	private String name;
	private LinkedList<MCATypeSingleDeclaration> localState = new LinkedList<MCATypeSingleDeclaration>();
	private MCPProcess process;

	
	
	public MCAProcessDefinition(String name,
			LinkedList<MCATypeSingleDeclaration> localState, MCPProcess process) {
		super();
		this.name = name;
		this.localState = localState;
		this.process = process;
	}



	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		//generateUserTypeDefinitions(question);
		//generateChannelTypes(question);
		
		result.append("domain StartProcDomain includes CML_PropertiesSpec {\n");
		
		//generate auxiliary definitions (actions, operationDefs, iocommdefs, guardDefs, etc.) 
		generateAuxiliaryActions(result,option);
		
		result.append("  ProcDef(\"");
		result.append(this.name);
		result.append("\",");
		//parameters
		result.append("nopar,");
		result.append(this.process.toFormula(option));
		result.append(").\n");
		
		/*
		if(question.info.containsKey(Utilities.ASSIGNMENT_DEFINITION_KEY)){
			Map<String, Map<String, String>> assigns = (Map<String, Map<String, String>>) question.info.get(Utilities.ASSIGNMENT_DEFINITION_KEY);

			for(Iterator<String> it = assigns.keySet().iterator(); it.hasNext();){
				String key = it.next();
				Map<String, String> aux = assigns.get(key);
				for(Iterator<String> iterator = aux.keySet().iterator(); iterator.hasNext();){
					String key2 = iterator.next();
					String values = aux.get(key2);
					Integer val = new Integer(values);
					Binding max;
					question.getScriptContent().append("  assignDef(0, "+ key +", st, st_)  :- State(0,st,name,assign("+key+")), st = ");
					//if(question.info.containsKey(Utilities.STATES_KEY)){
					if(question.stateVariables.size() > 0){
						//max = question.getMaxBindingWithStates();
						max = question.getMaxBinding();
						max.setProcName("name");
						//question.getScriptContent().append(max.toFormulaWithState());
						question.getScriptContent().append(max.toFormulaWithState());
						question.getScriptContent().append(", st_ = ");
						max.updateBinding(key2, new Int(key2+"_"));
						question.getScriptContent().append(max.toFormulaWithState());
						question.getScriptContent().append(", "+key2+"_ = "+ val.toString());
					} else{
						max = question.getMaxBinding();
						max.setProcName("name");
						//question.getScriptContent().append(max.toFormula());
						question.getScriptContent().append(max.toFormulaWithUnderscore());
						question.getScriptContent().append(", st_ = ");
						max.updateBinding(key2, new Int(val.intValue()));
						//question.getScriptContent().append(max.toFormula());
						question.getScriptContent().append(max.toFormula());
					}
					question.getScriptContent().append(".\n");
				}
			}
		}

		generateOperationsDefinitions(question);

		generateIOCommDefs(question);

		generateGuardDefinitions(question);

		*/
		//GENERATION OF THE PARTIAL MODEL
		
		result.append("  conforms := CML_PropertiesSpec." + context.propertyToCheck + ".\n");
		result.append("}\n\n");
		result.append("partial model StartProcModel of StartProcDomain{\n");

		//generateValueDefinitions(question);



		//if(question.info.containsKey(Utilities.STATES_KEY)){
		/*
				if(question.stateVariables.size() > 0){
					//pppppppp
					ArrayList<StringBuilder> states = (ArrayList<StringBuilder>) question.info.get(Utilities.STATES_KEY);
					StringBuilder s;
					for (int i = 0; i < states.size(); i++) {
						s = states.get(i);
						String currentState = question.getStates().get(i);
						StringBuilder aux = new StringBuilder(question.getMaxBindingWithStates().toFormulaWithState());
						aux.replace(aux.indexOf("Int("), aux.indexOf(")")+1, currentState);
						s.replace(s.indexOf("_"), s.indexOf("_")+1, aux.toString());
						s.replace(s.indexOf("Int("), s.length(), currentState+")");
						s.append("\n");
						question.getScriptContent().append(" "+s);
					}
				}
		 */
		/* generate del biind facts
				if(question.info.containsKey(Utilities.DEL_BBINDING)){
					if(question.getVariables().size() != 0){
						Set<SingleBind> varToDel = question.getVariables();
						for(Iterator<SingleBind> var = varToDel.iterator(); var.hasNext();){
							String s = var.next().getVariableName();
							Binding b = question.getMaxBinding();
							StringBuilder del = new StringBuilder("  del(");
							del.append(question.getMaxBinding().toFormulaWithUnderscore());
							del.append(",\""+s+"\",");
							b = b.deleteBinding(s);
							del.append(b.toFormulaWithUnderscore()+")\n");
							question.getScriptContent().append(del);
						}
					} else{
						ArrayList<String> varToDel = question.getStates();
						for(String s: varToDel){
							Binding b = question.getMaxBinding();
							StringBuilder del = new StringBuilder("  del(");
							del.append(question.getMaxBindingWithStates().toFormulaWithUnderscore());
							del.append(",\""+s+"\",");
							b = b.deleteBinding(s);
							del.append(b.toFormulaWithUnderscore()+")\n");
							question.getScriptContent().append(del);
						}
					}
				}
		 */

		//generateLieInFacts(question);

		//pppp
		//generate fetch facts
		//generate del facts
		//generate upd facts (if necessary)

		//generateBindingFacts(question);

		//generateChannelDefinitions(question);

		result.append("  GivenProc(\"" + this.name + "\")\n");
		result.append("}");


		
		//String content = question.getScriptContent().toString();
		//Binding maxBinding = question.getMaxBinding();
		//content = content.replaceAll(Utilities.MAX_BIND, maxBinding.toFormula());
		//StringBuilder newContent = new StringBuilder();
		//newContent.append(content);
		//question.setScriptContent(newContent);

		//return question.getScriptContent();
		
		return result.toString();
	}


	private void generateAuxiliaryActions(StringBuilder content, String option){
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (MCAActionDefinition localAction : context.localActions) {
			content.append(localAction.toFormula(option));
			content.append("\n");
		}
	}

	public LinkedList<MCATypeSingleDeclaration> getLocalState() {
		return localState;
	}



	public void setLocalState(LinkedList<MCATypeSingleDeclaration> localState) {
		this.localState = localState;
	}



	public MCPProcess getProcess() {
		return process;
	}



	public void setProcess(MCPProcess process) {
		this.process = process;
	}

	
}
