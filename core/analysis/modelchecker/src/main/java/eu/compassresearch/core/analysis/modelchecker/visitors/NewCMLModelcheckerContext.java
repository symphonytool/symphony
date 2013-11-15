package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import org.overture.ast.definitions.SFunctionDefinition;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.Binding;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.Domain;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCAssignDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCCondition;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCIOCommDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCLieInFact;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCNegGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.MCPosGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NewMCGuardDef;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.NullBinding;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAActionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChannelDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAExplicitCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAProcessDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSCmlOperationDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCSFunctionDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;

public class NewCMLModelcheckerContext {
	
	public static int ASSIGN_COUNTER;
	public static int GUARD_COUNTER;
	public static int IOCOMM_COUNTER;
	
	public MCAProcessDefinition mainProcess;
	public Domain propertiesDomain;
	public String propertyToCheck = Utilities.DEADLOCK_PROPERTY;
	public NewSetStack<MCPVarsetExpression> setStack;
	public ArrayList<MCLieInFact> lieIn;
	public ArrayList<MCAActionDefinition> localActions;
	public ArrayList<MCCondition> conditions;
	public Binding maximalBinding = new NullBinding();
	public HashMap<MCPCMLExp, LinkedList<MCGuardDef>> guardDefs;
	public HashMap<MCPCMLExp, LinkedList<NewMCGuardDef>> stmGuardDefs;
	public ArrayList<MCAssignDef> assignDefs;
	public LinkedList<MCAChannelDefinition> channelDefs;
	public ArrayList<MCSCmlOperationDefinition> operations;
	public ArrayList<MCSFunctionDefinition> functions;
	public LinkedList<MCAValueDefinition> valueDefinitions;
	public LinkedList<MCATypeDefinition> typeDefinitions;
	public LinkedList<MCAProcessDefinition> processDefinitions;
	public ArrayList<MCIOCommDef> ioCommDefs;
	public Stack<INode> actionOrProcessDefStack;
	public ArrayList<ActionChannelDependency> channelDependencies;
	
	
	
	
	public static int CHANTYPE_COUNTER;
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
	
	public synchronized static void resetInstance(){
		instance = new NewCMLModelcheckerContext();
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
	
	public LinkedList<ActionChannelDependency> getActionChannelDependendies(String actionName){
		LinkedList<ActionChannelDependency> result = new LinkedList<ActionChannelDependency>();
		for (ActionChannelDependency actionChannelDependency : this.channelDependencies) {
			if(actionChannelDependency.getActionName().equals(actionName)){
				result.add(actionChannelDependency);
			}
		}
		return result;
	}
	
	public LinkedList<ActionChannelDependency> getActionChannelDependendiesByChannelName(String channelName){
		LinkedList<ActionChannelDependency> result = new LinkedList<ActionChannelDependency>();
		for (ActionChannelDependency actionChannelDependency : this.channelDependencies) {
			if(actionChannelDependency.getChannelName().equals(channelName)){
				result.add(actionChannelDependency);
			}
		}
		return result;
	}
	
	public MCAChannelDefinition getChannelDefinition(String channelName){
		MCAChannelDefinition result = null;
		for (MCAChannelDefinition chanDef : this.channelDefs) {
			if(chanDef.getName().equals(channelName)){
				result = chanDef;
				break;
			}
		}
		return result;
	}
	
	public MCSCmlOperationDefinition getOperationDefinition(String name){
		MCSCmlOperationDefinition result = null;
		for (MCSCmlOperationDefinition opDef : this.operations) {
			if(opDef instanceof MCAExplicitCmlOperationDefinition){
				if(((MCAExplicitCmlOperationDefinition) opDef).getName().equals(name)){
					result = opDef;
				}
			}
		}
		return result;
	}
	public NewCMLModelcheckerContext() {
		setStack = new NewSetStack<MCPVarsetExpression>();
		lieIn = new ArrayList<MCLieInFact>();
		operations = new ArrayList<MCSCmlOperationDefinition>(); 
		localActions = new ArrayList<MCAActionDefinition>();
		conditions = new ArrayList<MCCondition>();
		channelDependencies = new ArrayList<ActionChannelDependency>();
		ioCommDefs = new ArrayList<MCIOCommDef>();
		valueDefinitions = new LinkedList<MCAValueDefinition>();
		typeDefinitions = new LinkedList<MCATypeDefinition>();
		guardDefs = new HashMap<MCPCMLExp, LinkedList<MCGuardDef>>();
		stmGuardDefs = new HashMap<MCPCMLExp, LinkedList<NewMCGuardDef>>();
		assignDefs = new ArrayList<MCAssignDef>();
		channelDefs = new LinkedList<MCAChannelDefinition>();
		processDefinitions = new LinkedList<MCAProcessDefinition>();
		actionOrProcessDefStack = new Stack<INode>(); 
		functions = new ArrayList<MCSFunctionDefinition>();
		ASSIGN_COUNTER = 0;
		GUARD_COUNTER = 0;
		IOCOMM_COUNTER = 0;
		CHANTYPE_COUNTER = 0;
	}
	
	
	public MCAValueDefinition getValueDefinition(String valueName){
		MCAValueDefinition result = null;
		
		for (MCAValueDefinition valueDef : valueDefinitions) {
			if(valueDef.getName().equals(valueName)){
				result = valueDef;
				break;
			}
		}
		
		return result;
	}
	
	public MCATypeDefinition getTypeDefinition(String typeName){
		MCATypeDefinition result = null;
		for (MCATypeDefinition typeDef : typeDefinitions) {
			if(typeDef.getName().equals(typeName)){
				result = typeDef;
				break;
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
