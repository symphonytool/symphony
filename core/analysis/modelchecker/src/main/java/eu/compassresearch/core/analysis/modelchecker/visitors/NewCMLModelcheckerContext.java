package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCAssignDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCChannel;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCondition;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCLieInFact;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCNegGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCPosGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCValueDef;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelNameDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAUntypedDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCPCMLDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.NullBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.SingleBind;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public class NewCMLModelcheckerContext {
	
	public static int ASSIGN_COUNTER;
	public static int GUARD_COUNTER;
	
	public String propertyToCheck = Utilities.DEADLOCK_PROPERTY;
	public NewSetStack<MCPVarsetExpression> setStack;
	public ArrayList<MCLieInFact> lieIn;
	public ArrayList<MCAActionDefinition> localActions;
	public ArrayList<MCCondition> conditions;
	public Binding maximalBinding = new NullBinding();
	public HashMap<MCPCMLExp, MCPosGuardDef> positiveGuardDefs;
	public HashMap<MCPCMLExp, MCNegGuardDef> negativeGuardDefs;
	public HashMap<MCPCMLExp, MCGuardDef> guardDefs;
	public ArrayList<MCAssignDef> assignDefs;
	public LinkedList<MCAChannelNameDefinition> channelDefs;
	public ArrayList<MCSCmlOperationDefinition> operations;
	public LinkedList<MCAValueDefinition> valueDefinitions;
	public LinkedList<MCATypeDefinition> typeDefinitions;
	public LinkedList<MCAProcessDefinition> processDefinitions;
	
	
	protected StringBuilder basicContent = new StringBuilder(); 
	
	protected HashMap<Object, Object> info; 
	
	public Set<SingleBind> stateVariables;
	
	public Set<String> communicationVariables;
		
	private ArrayList<String> states;
	
	
	
	public static int IOCOMM_COUNTER;
	
	public static int CHANTYPE_COUNTER;
	
	protected StringBuilder scriptContent;
	protected LinkedList<ChannelTypeDefinition> channelDefinitions;
	 
	
	
	
	
	protected ArrayList<String> channelDependencies;
	protected ArrayList<String> ioCommDefs;
	
	
	
	
	protected int numberOfFetchFacts = 1;
	protected int numberOfUpdFacts = 1;
	protected int numberOfDelFacts = 1;
	
	private static NewCMLModelcheckerContext instance;
	
	public synchronized static NewCMLModelcheckerContext getInstance(){
		if(instance == null){
			instance = new NewCMLModelcheckerContext();
		}
		
		return instance;
	}
	
	public MCCondition getConditionByExpression(MCPCMLExp expression){
		MCCondition result = null;
		for (MCCondition condition : this.conditions) {
			if(condition.getExpression().equals(expression)){
				result = condition;
				break;
			}
		}
		
		return result;
	}
	
	public NewCMLModelcheckerContext() {
		info = new HashMap<Object,Object>();
		scriptContent = new StringBuilder();
		stateVariables = new LinkedHashSet<SingleBind>();
		states = new ArrayList<String>();
		setStack = new NewSetStack<MCPVarsetExpression>();
		lieIn = new ArrayList<MCLieInFact>();
		operations = new ArrayList<MCSCmlOperationDefinition>(); 
		localActions = new ArrayList<MCAActionDefinition>();
		conditions = new ArrayList<MCCondition>();
		channelDependencies = new ArrayList<String>();
		ioCommDefs = new ArrayList<String>();
		positiveGuardDefs = new HashMap<MCPCMLExp, MCPosGuardDef>();
		negativeGuardDefs = new HashMap<MCPCMLExp, MCNegGuardDef>();
		valueDefinitions = new LinkedList<MCAValueDefinition>();
		typeDefinitions = new LinkedList<MCATypeDefinition>();
		channelDefinitions = new LinkedList<ChannelTypeDefinition>();
		guardDefs = new HashMap<MCPCMLExp, MCGuardDef>();
		assignDefs = new ArrayList<MCAssignDef>();
		channelDefs = new LinkedList<MCAChannelNameDefinition>();
		processDefinitions = new LinkedList<MCAProcessDefinition>();
		ASSIGN_COUNTER = 0;
		GUARD_COUNTER = 0;
		IOCOMM_COUNTER = 0;
		CHANTYPE_COUNTER = 0;
	}
	
	public NewCMLModelcheckerContext(int i) {
		info = new HashMap<Object,Object>();
		scriptContent = new StringBuilder();
		stateVariables = new LinkedHashSet<SingleBind>();
		states = new ArrayList<String>();
		setStack = new NewSetStack<MCPVarsetExpression>();
		lieIn = new ArrayList<MCLieInFact>();
		operations = new ArrayList<MCSCmlOperationDefinition>();
		localActions = new ArrayList<MCAActionDefinition>();
		conditions = new ArrayList<MCCondition>();
		channelDependencies = new ArrayList<String>();
		ioCommDefs = new ArrayList<String>();
		positiveGuardDefs = new HashMap<MCPCMLExp, MCPosGuardDef>();
		negativeGuardDefs = new HashMap<MCPCMLExp, MCNegGuardDef>();
		valueDefinitions = new LinkedList<MCAValueDefinition>();
		typeDefinitions = new LinkedList<MCATypeDefinition>();
		channelDefinitions = new LinkedList<ChannelTypeDefinition>();
		guardDefs = new HashMap<MCPCMLExp, MCGuardDef>();
		channelDefs = new LinkedList<MCAChannelNameDefinition>();
		processDefinitions = new LinkedList<MCAProcessDefinition>();
		ASSIGN_COUNTER = i;
		GUARD_COUNTER = 0;
		IOCOMM_COUNTER = 0;
		CHANTYPE_COUNTER = 0;
		assignDefs = new ArrayList<MCAssignDef>();
	}
	
	public NewCMLModelcheckerContext copy(){
		NewCMLModelcheckerContext result = new NewCMLModelcheckerContext();
		result.info = new HashMap<Object,Object>(this.info);
		result.scriptContent = new StringBuilder(this.scriptContent.toString());
		result.stateVariables = new LinkedHashSet<SingleBind>(this.stateVariables);
		result.states = new ArrayList<String>(this.states);
		result.setStack = this.setStack.copy();
		result.lieIn = new ArrayList<MCLieInFact>(this.lieIn);
		result.operations = new ArrayList<MCSCmlOperationDefinition>(this.operations);
		result.localActions = new ArrayList<MCAActionDefinition>(this.localActions);
		result.conditions = new ArrayList<MCCondition>(this.conditions);
		result.channelDependencies = new ArrayList<String>(this.channelDependencies);
		result.ioCommDefs = new ArrayList<String>(this.ioCommDefs);
		positiveGuardDefs = new HashMap<MCPCMLExp, MCPosGuardDef>(this.positiveGuardDefs);
		negativeGuardDefs = new HashMap<MCPCMLExp, MCNegGuardDef>(this.negativeGuardDefs);
		result.valueDefinitions = new LinkedList<MCAValueDefinition>(this.valueDefinitions);
		result.typeDefinitions = new LinkedList<MCATypeDefinition>(this.typeDefinitions);
		result.channelDefinitions = new LinkedList<ChannelTypeDefinition>(this.channelDefinitions);
		guardDefs = new HashMap<MCPCMLExp, MCGuardDef>(this.guardDefs);
		channelDefs = new LinkedList<MCAChannelNameDefinition>(this.channelDefs);
		processDefinitions = new LinkedList<MCAProcessDefinition>(this.processDefinitions);
		result.ASSIGN_COUNTER = this.ASSIGN_COUNTER;
		result.GUARD_COUNTER = this.GUARD_COUNTER;
		result.IOCOMM_COUNTER = this.IOCOMM_COUNTER;
		CHANTYPE_COUNTER = 0;
		assignDefs = new ArrayList<MCAssignDef>(this.assignDefs);
		
		return result;
	}
	
	public MCAValueDefinition getValueDefinition(String valueName){
		MCAValueDefinition result = null;
		
		boolean found = false;
		
		for (MCAValueDefinition valueDef : valueDefinitions) {
			if(valueDef.getName().equals(valueName)){
				result = valueDef;
				break;
			}
			//for (MCPCMLDefinition pcmlDef : valueDef.getDefinitions()) {
			//	if(pcmlDef instanceof MCAUntypedDefinition){
			//		if(((MCAUntypedDefinition) pcmlDef).getName().equals(valueName)){
			//			found = true;
			//			break;
			//		}
			//	}
			//}
			//if(found){
			//	result = valueDef;
			//	break;
			//}
		}
		
		return result;
	}
	public Binding getMaxBinding(){
		return this.maximalBinding;
		/*Binding maximalBinding = new NullBinding();
		for (SingleBind currVar : stateVariables) {
			maximalBinding = maximalBinding.addBinding("np", currVar.getVariableName(), currVar.getVariableValue());
		}
		return maximalBinding;
		*/
	}
	
	public Binding getMaxBindingWithStates(){
		Binding maximalBinding = new NullBinding();
		for (SingleBind currVar : stateVariables) {
			String varName = currVar.getVariableName();
			Type value = currVar.getVariableValue();
			if(value instanceof Int){
				((Int) value).setS(currVar.getVariableName());
			}
			maximalBinding = maximalBinding.addBinding("np", varName, value);
		}
		return maximalBinding;
	}
	
	//public void updateStateVariable(String s){
	//	states.add(s);
	//}
	
	public Object putVarInBinding(Object key, Object value){
		StringBuilder s =  (StringBuilder) info.get(key);
		s.append(value);
		return info.put(key, s);
	}
	
	public Object putVarAttInBinding(Object key, Object key1, Object key2, Object value){
		Map<String,  Map <String, String>> s;
		Map<String, String> assign;
		if(info.get(key) != null){
			s = (HashMap<String, Map<String, String>>) info.get(key);
			if(s.get(key1) != null){
				assign = s.get(key1);
				assign.put((String) key2, (String) value);
				s.put((String) key1, assign);
			} else{
				assign = new HashMap<String, String>();
				assign.put((String) key2, (String) value);
				s.put((String) key1, assign);
			}
		} else{
			s = new HashMap<String,  Map <String, String>>();
			assign = new HashMap<String, String>();
			assign.put((String) key2, (String) value);
			s.put((String) key1, assign);
		}
		return info.put(key, s);
	}
	
	public Object putStates(String key, StringBuilder s){
		ArrayList<StringBuilder> state;
		if(info.get(key) != null){
			state = (ArrayList<StringBuilder>) info.get(key);
		} else{
			state = new ArrayList<StringBuilder>();
		}
		state.add(s);
		return info.put(key, state);
	}
	
	public Set<SingleBind> getVariables(){
		return stateVariables;
	}
	
	public ArrayList<String> getStates(){
		return states;
	}
	
	public StringBuilder getScriptContent() {
		return scriptContent;
	}
	
	public void setVariables(Set<SingleBind> vars){
		this.stateVariables = vars;
	}
	
	public void setScriptContent(StringBuilder scriptContent) {
		this.scriptContent = scriptContent;
	}
	
	public SingleBind getBindByVariable(String varName){
		SingleBind result = null;
		
		for (SingleBind bind : this.stateVariables) {
			if(bind.getVariableName().equals(varName)){
				result = bind;
				break;
			}
		}
		
		return result;
	}
	
	public void reset(){
		basicContent = new StringBuilder();
		info = new HashMap<Object,Object>();
		scriptContent = new StringBuilder();
		stateVariables = new LinkedHashSet<SingleBind>();
		states = new ArrayList<String>();
		setStack = new NewSetStack<MCPVarsetExpression>();
		lieIn = new ArrayList<MCLieInFact>();
		operations = new ArrayList<MCSCmlOperationDefinition>(); 
		localActions = new ArrayList<MCAActionDefinition>();
		conditions = new ArrayList<MCCondition>();
		channelDependencies = new ArrayList<String>();
		ioCommDefs = new ArrayList<String>();
		positiveGuardDefs = new HashMap<MCPCMLExp, MCPosGuardDef>();
		negativeGuardDefs = new HashMap<MCPCMLExp, MCNegGuardDef>();
		valueDefinitions = new LinkedList<MCAValueDefinition>();
		typeDefinitions = new LinkedList<MCATypeDefinition>();
		channelDefinitions = new LinkedList<ChannelTypeDefinition>();
		guardDefs = new HashMap<MCPCMLExp, MCGuardDef>();
		channelDefs = new LinkedList<MCAChannelNameDefinition>();
		processDefinitions = new LinkedList<MCAProcessDefinition>();
		ASSIGN_COUNTER = 0;
		GUARD_COUNTER = 0;
		IOCOMM_COUNTER = 0;
		CHANTYPE_COUNTER = 0;
		assignDefs = new ArrayList<MCAssignDef>();
	}
	public void copyVarDeclarationInfo(NewCMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.VAR_DECLARATIONS_KEY)){
			this.info.put(Utilities.VAR_DECLARATIONS_KEY, otherContext.info.get(Utilities.VAR_DECLARATIONS_KEY));
		}
	}
	
	public void copyVarDelInfo(NewCMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.DEL_BBINDING)){
			this.info.put(Utilities.DEL_BBINDING, otherContext.info.get(Utilities.DEL_BBINDING));
		}
	}
	
	public void copyAssignmentDefInfo(NewCMLModelcheckerContext otherContext){
		if (otherContext.info.containsKey(Utilities.ASSIGNMENT_DEFINITION_KEY)) {
			if(!this.info.containsKey(Utilities.ASSIGNMENT_DEFINITION_KEY)){
				this.info.put(Utilities.ASSIGNMENT_DEFINITION_KEY, otherContext.info.get(Utilities.ASSIGNMENT_DEFINITION_KEY));
			} else {
				Map<String, Map<String, String>> aux = (Map<String, Map<String, String>>) this.info.get(Utilities.ASSIGNMENT_DEFINITION_KEY);
				Map<String, Map<String, String>> other = (Map<String, Map<String, String>>) otherContext.info.get(Utilities.ASSIGNMENT_DEFINITION_KEY);
				
				for(Iterator<String> key = other.keySet().iterator(); key.hasNext();){
					aux.put(key.next(), other.get(key));
				}
				this.info.put(Utilities.ASSIGNMENT_DEFINITION_KEY,aux);
			}
		}
	}
	
	public void copyChannelInfo(NewCMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
			this.info.put(Utilities.CHANNEL_DEFINITIONS_KEY, otherContext.info.get(Utilities.CHANNEL_DEFINITIONS_KEY));
		}
	}
	public void copyIOCommDefInfo(NewCMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.IOCOMM_DEFINITIONS_KEY)){
			this.info.put(Utilities.IOCOMM_DEFINITIONS_KEY, otherContext.info.get(Utilities.IOCOMM_DEFINITIONS_KEY));
		}
	}
	
	public ChannelTypeDefinition getChannelDefinition(String channelName){
		ChannelTypeDefinition result = null;
		
		for (ChannelTypeDefinition channelDef : this.channelDefinitions) {
			for (ILexIdentifierToken token : channelDef.getChanDef().getSingleType().getIdentifiers()) {
				if(token.toString().equals(channelName)){
					result = channelDef;
					return result;
				}
			}
		}
		
		return result;
	}

	public String getPropertyToCheck() {
		return propertyToCheck;
	}

	public void setPropertyToCheck(String propertyToCheck) {
		this.propertyToCheck = propertyToCheck;
	}
	
}
