package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.NullBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.SingleBind;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.UndefinedValue;

public class CMLModelcheckerContext {
	
	protected String propertyToCheck = Utilities.DEADLOCK_PROPERTY;
	
	protected StringBuilder basicContent = new StringBuilder(); 
	
	protected HashMap<Object, Object> info; 
	
	public Set<SingleBind> stateVariables;
	
	public Set<String> communicationVariables;
		
	private ArrayList<String> states;
	
	public static int ASSIGN_COUNTER;
	
	public static int GUARD_COUNTER;
	
	public static int IOCOMM_COUNTER;
	
	public static int CHANTYPE_COUNTER;
	
	protected StringBuilder scriptContent;

	protected SetStack setStack; 
	
	protected ArrayList<String> lieIn;
	
	protected ArrayList<SCmlOperationDefinition> operations;
	
	protected ArrayList<AActionDefinition> localActions;
	
	protected ArrayList<Condition> guards;
	
	protected HashMap<PExp, String> positiveGuardExps;
	
	protected HashMap<PExp, String> negativeGuardExps;
	
	protected ArrayList<String> channelDependencies;
	
	protected ArrayList<String> ioCommDefs;
	
	protected LinkedList<UserDefinedValue> valueDefinitions;
	
	protected LinkedList<UserTypeDefinition> typeDefinitions;
	
	protected LinkedList<ChannelTypeDefinition> channelDefinitions;
	
	protected int numberOfFetchFacts = 1;
	protected int numberOfUpdFacts = 1;
	protected int numberOfDelFacts = 1;
	
	public CMLModelcheckerContext() {
		info = new HashMap<Object,Object>();
		scriptContent = new StringBuilder();
		stateVariables = new LinkedHashSet<SingleBind>();
		states = new ArrayList<String>();
		setStack = new SetStack();
		lieIn = new ArrayList<String>();
		operations = new ArrayList<SCmlOperationDefinition>(); 
		localActions = new ArrayList<AActionDefinition>();
		guards = new ArrayList<Condition>();
		channelDependencies = new ArrayList<String>();
		ioCommDefs = new ArrayList<String>();
		positiveGuardExps = new HashMap<PExp, String>();
		negativeGuardExps = new HashMap<PExp, String>();
		valueDefinitions = new LinkedList<UserDefinedValue>();
		typeDefinitions = new LinkedList<UserTypeDefinition>();
		channelDefinitions = new LinkedList<ChannelTypeDefinition>();
		ASSIGN_COUNTER = 0;
		GUARD_COUNTER = 0;
		IOCOMM_COUNTER = 0;
		CHANTYPE_COUNTER = 0;
	}
	
	public CMLModelcheckerContext(int i) {
		info = new HashMap<Object,Object>();
		scriptContent = new StringBuilder();
		stateVariables = new LinkedHashSet<SingleBind>();
		states = new ArrayList<String>();
		setStack = new SetStack();
		lieIn = new ArrayList<String>();
		operations = new ArrayList<SCmlOperationDefinition>();
		localActions = new ArrayList<AActionDefinition>();
		guards = new ArrayList<Condition>();
		channelDependencies = new ArrayList<String>();
		ioCommDefs = new ArrayList<String>();
		positiveGuardExps = new HashMap<PExp, String>();
		negativeGuardExps = new HashMap<PExp, String>();
		valueDefinitions = new LinkedList<UserDefinedValue>();
		typeDefinitions = new LinkedList<UserTypeDefinition>();
		channelDefinitions = new LinkedList<ChannelTypeDefinition>();
		ASSIGN_COUNTER = i;
		GUARD_COUNTER = 0;
		IOCOMM_COUNTER = 0;
		CHANTYPE_COUNTER = 0;
	}
	
	public CMLModelcheckerContext copy(){
		CMLModelcheckerContext result = new CMLModelcheckerContext();
		result.info = new HashMap<Object,Object>(this.info);
		result.scriptContent = new StringBuilder(this.scriptContent.toString());
		result.stateVariables = new LinkedHashSet<SingleBind>(this.stateVariables);
		result.states = new ArrayList<String>(this.states);
		result.setStack = this.setStack.copy();
		result.lieIn = new ArrayList<String>(this.lieIn);
		result.operations = new ArrayList<SCmlOperationDefinition>(this.operations);
		result.localActions = new ArrayList<AActionDefinition>(this.localActions);
		result.guards = new ArrayList<Condition>(this.guards);
		result.channelDependencies = new ArrayList<String>(this.channelDependencies);
		result.ioCommDefs = new ArrayList<String>(this.ioCommDefs);
		result.positiveGuardExps = new HashMap<PExp, String>(this.positiveGuardExps);
		result.negativeGuardExps = new HashMap<PExp, String>(this.negativeGuardExps);
		result.valueDefinitions = new LinkedList<UserDefinedValue>(this.valueDefinitions);
		result.typeDefinitions = new LinkedList<UserTypeDefinition>(this.typeDefinitions);
		result.channelDefinitions = new LinkedList<ChannelTypeDefinition>(this.channelDefinitions);
		result.ASSIGN_COUNTER = this.ASSIGN_COUNTER;
		result.GUARD_COUNTER = this.GUARD_COUNTER;
		result.IOCOMM_COUNTER = this.IOCOMM_COUNTER;
		CHANTYPE_COUNTER = 0;
		
		return result;
	}
	
	public Binding getMaxBinding(){
		Binding maximalBinding = new NullBinding();
		for (SingleBind currVar : stateVariables) {
			maximalBinding = maximalBinding.addBinding("np", currVar.getVariableName(), currVar.getVariableValue());
		}
		return maximalBinding;
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
		setStack = new SetStack();
		lieIn = new ArrayList<String>();
		operations = new ArrayList<SCmlOperationDefinition>(); 
		localActions = new ArrayList<AActionDefinition>();
		guards = new ArrayList<Condition>();
		channelDependencies = new ArrayList<String>();
		ioCommDefs = new ArrayList<String>();
		positiveGuardExps = new HashMap<PExp, String>();
		negativeGuardExps = new HashMap<PExp, String>();
		valueDefinitions = new LinkedList<UserDefinedValue>();
		typeDefinitions = new LinkedList<UserTypeDefinition>();
		channelDefinitions = new LinkedList<ChannelTypeDefinition>();
		ASSIGN_COUNTER = 0;
		GUARD_COUNTER = 0;
		IOCOMM_COUNTER = 0;
		CHANTYPE_COUNTER = 0;
	}
	public void copyVarDeclarationInfo(CMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.VAR_DECLARATIONS_KEY)){
			this.info.put(Utilities.VAR_DECLARATIONS_KEY, otherContext.info.get(Utilities.VAR_DECLARATIONS_KEY));
		}
	}
	
	public void copyVarDelInfo(CMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.DEL_BBINDING)){
			this.info.put(Utilities.DEL_BBINDING, otherContext.info.get(Utilities.DEL_BBINDING));
		}
	}
	
	public void copyAssignmentDefInfo(CMLModelcheckerContext otherContext){
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
	
	public void copyChannelInfo(CMLModelcheckerContext otherContext){
		if(otherContext.info.containsKey(Utilities.CHANNEL_DEFINITIONS_KEY)){
			this.info.put(Utilities.CHANNEL_DEFINITIONS_KEY, otherContext.info.get(Utilities.CHANNEL_DEFINITIONS_KEY));
		}
	}
	public void copyIOCommDefInfo(CMLModelcheckerContext otherContext){
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
