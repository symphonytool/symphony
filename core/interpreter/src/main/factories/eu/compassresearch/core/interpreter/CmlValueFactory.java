package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.types.AClassType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;

import eu.compassresearch.ast.actions.ANewStatementAction;
import eu.compassresearch.ast.definitions.ACmlClassDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.core.interpreter.api.values.CmlOperationValue;

class CmlValueFactory {

	public static ObjectValue createClassValue(ANewStatementAction node, Context question) throws AnalysisException
	{
		ACmlClassDefinition classDefinition = (ACmlClassDefinition)node.getClassdef();
		return createClassValue(classDefinition,question);
	}
	
	private static ObjectValue createClassValue(ACmlClassDefinition classDefinition, Context question) throws AnalysisException
	{
		AClassType classType = (AClassType)classDefinition.getType();
		NameValuePairMap members = new NameValuePairMap();
		for(PDefinition pdef : classDefinition.getDefinitions())
		{
			for(NameValuePair nvp : pdef.apply(new CmlDefinitionVisitor() ,question))
			{
				NameValuePair newNvp = new NameValuePair(new LexNameToken(classDefinition.getName().getName(),(ILexIdentifierToken)nvp.name.getIdentifier().clone()),nvp.value);
				members.put(newNvp);
			}
		}
		
		List<ObjectValue> supers = new LinkedList<ObjectValue>();
		
		for(SClassDefinition cd : classDefinition.getSuperDefs())
		{
			ACmlClassDefinition superClass = (ACmlClassDefinition)cd;
			supers.add(createClassValue(superClass, question));
		}
		
		return new ObjectValue(classType, members, supers, CPUValue.vCPU, question.getSelf());
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
