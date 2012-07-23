package eu.compassresearch.transformation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.QuestionAdaptor;
import eu.compassresearch.ast.types.PType;

public class CopyTypesFromOvtToCmlAst extends AnswerAdaptor<eu.compassresearch.ast.node.INode >{

	public CopyTypesFromOvtToCmlAst(Map<org.overture.ast.node.INode, eu.compassresearch.ast.node.INode> nodeMap)
	{
		this.nodeMap=nodeMap;
	}
	
	private Map<org.overture.ast.node.INode, eu.compassresearch.ast.node.INode> nodeMap;

	@Override
	public eu.compassresearch.ast.node.INode defaultINode(INode node)
			throws AnalysisException {
		
		eu.compassresearch.ast.node.INode cmlNode = nodeMap.get(node);
		if (cmlNode == null) throw new AnalysisException("Tree structure not the same");

		org.overture.ast.types.PType ovtType = null;
		try {
			Class<?> clazz = node.getClass();
			Method getType = clazz.getMethod("getType", new Class<?>[0]);
			ovtType = (org.overture.ast.types.PType) getType.invoke(node, new Object[0]);
		} catch (Exception e) { return cmlNode; }

		
		PType type = typeToType(ovtType);
		try {
			Class<?> clazz = node.getClass();
			Method setType = clazz.getMethod("setType", new Class<?>[] { type.getClass() });
			setType.invoke(cmlNode,type);
		}catch (Exception e) { return cmlNode; }
		
		return super.defaultINode(node);
	}


	private PType typeToType(org.overture.ast.types.PType ovtType) {
		try {
			String cmlName = ovtType.getClass().getName().replace("org.overture.","eu.compassresearch");


			//create cml class
			ClassLoader cl = this.getClass().getClassLoader();
			Class<?> cmlClz = cl.loadClass(cmlName);
			Constructor<?> ctor = cmlClz.getConstructor(new Class<?>[0]);
			PType cmlType = (PType)ctor.newInstance(new Object[0]);

			// Copy getter to setter 
			mirror(cmlType, ovtType);


			return cmlType;
		}
		catch (Exception e) { return null; }
	}

	/**
	 * Just even try starting to comprehend the preconditions 
	 * for this functions makes you stop thinking at all. But loosely put:
	 * The to-object-tree must have set methods with exactly one argument for 
	 * their state. The from-object-tree must have get methods with no arguments 
	 * for all of their state.
	 *
	 * If anything goes wrong the deep-copy stops for that subtree.
	 * 
	 * @param to
	 * @param from
	 */
	private void deepCopy(Object to, Object from)
	{
		try {
			Class<?> toClz = to.getClass();
			Class<?> fromClz = from.getClass(); 
			for(Method getter : fromClz.getMethods())
			{
				if (getter.getName().startsWith("get"))
				{
					String wantedName = getter.getName().replace("get", "set");
					for(Method setter : toClz.getMethods())
					{
						if (setter.getName().equals(wantedName))
						{
							Object getterResult = getter.invoke(from, new Object[0]);
							Class<?> getterRet = getter.getReturnType();
							if (isBasicJavaType(getterRet))
							{
								
								setter.invoke(to, getterResult);
							}
							else
							{
								Constructor<?> argClassCtor = setter.getParameterTypes()[0].getConstructor(new Class<?>[0]);
								Object copyedArd = argClassCtor.newInstance(new Object[0]);
								deepCopy(copyedArd, getterResult);
								setter.invoke(to, copyedArd);
							}

						}
					}
				}

			}
		} catch (Exception e) {return;}

	}

	private boolean isBasicJavaType(Class<?> getterRet) {
		boolean res = getterRet.getName().startsWith("java.lang") ||
				getterRet.getName().startsWith("java.util") ||
				getterRet.getName().startsWith("java.io") ||
				getterRet.getName().startsWith("java.math") ||
				getterRet.getName().startsWith("java.nio") ||	
				getterRet.getName().startsWith("java.net")||
				getterRet.getName().startsWith("java.security") ||
				getterRet.getName().startsWith("java.sql") ||
				getterRet.getName().startsWith("java.awt") ||
				getterRet.getName().startsWith("javax.swing")||
				getterRet.getName().startsWith("java.applet");
		return res;
	}

	private void mirror(PType cmlType, org.overture.ast.types.PType ovtType) {
		deepCopy(cmlType, ovtType);
	}



}
