package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.AClassType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.OperationValue;

import eu.compassresearch.ast.statements.ANewStm;
import eu.compassresearch.core.interpreter.api.values.ActionValue;

class CmlValueFactory
{

	public static ObjectValue createClassValue(ANewStm node, Context question)
			throws AnalysisException
	{
		// ACmlClassDefinition classDefinition = (ACmlClassDefinition) node.getClassdef();
		// return createClassValue(classDefinition, question);
		return null;
	}

	private static ObjectValue createClassValue(
			SClassDefinition classDefinition, Context question)
			throws AnalysisException
	{
		AClassType classType = (AClassType) classDefinition.getType();
		NameValuePairMap members = new NameValuePairMap();
		for (PDefinition pdef : classDefinition.getDefinitions())
		{
			for (NameValuePair nvp : pdef.apply(new CmlDefinitionVisitor(), question))
			{
				ILexNameToken name = nvp.name.getModifiedName(classDefinition.getName().getName());
				if (nvp.value instanceof FunctionValue
						|| nvp.value instanceof OperationValue
						|| nvp.value instanceof ActionValue)
					members.put(new NameValuePair(name, nvp.value));
				else
					members.put(new NameValuePair(name, nvp.value.getUpdatable(null)));

				// NameValuePair newNvp = new NameValuePair(new
				// LexNameToken(classDefinition.getName().getName(),(ILexIdentifierToken)nvp.name.getIdentifier().clone()),nvp.value);
				// members.put(newNvp);
			}
		}

		List<ObjectValue> supers = new LinkedList<ObjectValue>();

		for (SClassDefinition cd : classDefinition.getSuperDefs())
			supers.add(createClassValue(cd, question));

		return new ObjectValue(classType, members, supers, CPUValue.vCPU, question.getSelf());
	}

//	public static OperationValue createOperationValue(
//			SOperationDefinition node, Context question)
//	{
//		OperationValue ret = null;
//
//		if (node instanceof AExplicitOperationDefinition)
//		{
//			AExplicitOperationDefinition def = (AExplicitOperationDefinition) node;
//
//			 FunctionValue prefunc =
//			 (node.getPredef() == null) ? null : new
//			 FunctionValue(node.getPredef(), null, null, null);
//
//			 FunctionValue postfunc =
//			 (node.getPostdef() == null) ? null : new
//			 FunctionValue(node.getPostdef(), null, null, null);
//
//			ret = new CmlOperationValue(def,prefunc,postfunc, null);
//
//		} else if (node instanceof AImplicitOperationDefinition)
//		{
//			AImplicitOperationDefinition def = (AImplicitOperationDefinition) node;
//			ret = new CmlOperationValue(def, null);
//		}
//
//		return ret;
//	}
}
