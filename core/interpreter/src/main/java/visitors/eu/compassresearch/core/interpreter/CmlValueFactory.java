package eu.compassresearch.core.interpreter;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.types.AClassType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;

import eu.compassresearch.ast.actions.ANewStatementAction;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.core.interpreter.values.CmlOperationValue;

class CmlValueFactory {

	public static ObjectValue createClassValue(ANewStatementAction node, Context question) throws AnalysisException
	{
		AClassType classType = (AClassType)node.getClassdef().getType();
		ILexNameToken classname = classType.getName();
		NameValuePairMap members = new NameValuePairMap();
		for(PDefinition pdef : node.getClassdef().getBody())
		{
			for(NameValuePair nvp : pdef.apply(new CmlDefinitionVisitor() ,question))
			{
				NameValuePair newNvp = new NameValuePair(new LexNameToken(classname.getName(),(ILexIdentifierToken)nvp.name.getIdentifier().clone()),nvp.value);
				members.put(newNvp);
			}
		}
		
		return new ObjectValue(classType, members, new LinkedList<ObjectValue>(), CPUValue.vCPU, question.getSelf());
	}
	
	public static CmlOperationValue createOperationValue(SCmlOperationDefinition node, Context question)
	{
		CmlOperationValue ret = null;
		
		if(node instanceof AExplicitCmlOperationDefinition)
		{
			AExplicitCmlOperationDefinition def = (AExplicitCmlOperationDefinition)node;
			
			//FunctionValue prefunc =
			//		(((AExplicitCmlOperationDefinition) node).getPredef() == null) ? null : new FunctionValue(((AExplicitCmlOperationDefinition) node).getPredef(), null, null, null);

			//FunctionValue postfunc =
			//		(((AExplicitCmlOperationDefinition) node).getPostdef() == null) ? null : new FunctionValue(((AExplicitCmlOperationDefinition) node).getPostdef(), null, null, null);
			
			ret = new CmlOperationValue(def, null);
			
		}
		
		
		return ret;
	}
}
