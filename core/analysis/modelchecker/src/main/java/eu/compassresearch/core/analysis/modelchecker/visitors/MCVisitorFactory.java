package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.HashMap;

import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.types.PCMLType;

public class MCVisitorFactory {

	public static final String ACTION_VISITOR_NAME = MCActionVisitor.class.getSimpleName();
	public static final String EXPRESSION_VISITOR_NAME = MCExpressionVisitor.class.getSimpleName();
	public static final String PROCESS_VISITOR_NAME = MCProcessVisitor.class.getSimpleName();
	public static final String MAIN_VISITOR_NAME = MCVisitor.class.getSimpleName();
	public static final String DECL_AND_DEF_VISITOR_NAME = MCDeclarationAndDefinitionVisitor.class.getSimpleName();
	public static final String TYPE_VISITOR_NAME = MCTypeAndValueVisitor.class.getSimpleName();
	
	private MCExpressionVisitor expressionVisitor;
	private MCActionVisitor actionVisitor;
	private MCProcessVisitor processVisitor;
	private MCDeclarationAndDefinitionVisitor declAndDefVisitor;
	private MCTypeAndValueVisitor typeVisitor;
	private MCVisitor mainVisitor;
	private HashMap<String, QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder>> visitors; 
	
	private static MCVisitorFactory instance;

	private MCVisitorFactory(){
		this.mainVisitor = new MCVisitor();
		this.expressionVisitor = new MCExpressionVisitor(mainVisitor);
		this.actionVisitor = new MCActionVisitor(mainVisitor);
		this.processVisitor = new MCProcessVisitor(mainVisitor);
		this.declAndDefVisitor = new MCDeclarationAndDefinitionVisitor(mainVisitor);
		this.typeVisitor = new MCTypeAndValueVisitor(mainVisitor);
		
		this.visitors = new HashMap<>();
		this.visitors.put(MAIN_VISITOR_NAME, mainVisitor);
		this.visitors.put(ACTION_VISITOR_NAME, actionVisitor);
		this.visitors.put(PROCESS_VISITOR_NAME, processVisitor);
		this.visitors.put(EXPRESSION_VISITOR_NAME, expressionVisitor);
		this.visitors.put(DECL_AND_DEF_VISITOR_NAME, declAndDefVisitor);
		this.visitors.put(TYPE_VISITOR_NAME, typeVisitor);
	}
	
	public synchronized static MCVisitorFactory getInstance(){
		if(instance == null){
			instance = new MCVisitorFactory();
		}
		return instance;
	}
	
	public QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> getVisitor(String visitorName){
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> result = mainVisitor;
		
		if(visitors.containsKey(visitorName)){
			result = visitors.get(visitorName);
		}
			
		return result;
	}
	
	public QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> getMainVisitor(){
		return this.visitors.get(MAIN_VISITOR_NAME);
	}
	
	/*
	 * It returns a visitor that is suitable for a specific INode.
	 */
	public QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> getVisitor(INode node){
		QuestionAnswerCMLAdaptor<CMLModelcheckerContext, StringBuilder> result = null;
		
		if(node instanceof AProcessDefinition || node instanceof AActionProcess){
			result = this.visitors.get(PROCESS_VISITOR_NAME);
		}
		if(node instanceof AActionDefinition || node instanceof PSingleDeclaration){
			result = this.visitors.get(ACTION_VISITOR_NAME);
		}
		if(node instanceof PCMLExp){
			result = this.visitors.get(EXPRESSION_VISITOR_NAME);
		}
		if(node instanceof PCMLType){
			result = this.visitors.get(TYPE_VISITOR_NAME);
		}
		
		//what if node is operation definition, state definition, channel definition
		
		if(result == null){
			result = new MCEmptyVisitor();
		}
		
		return result;
	}
}
