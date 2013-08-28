package eu.compassresearch.theoremprover.utils;

import org.overture.ast.definitions.AValueDefinition;
import eu.compassresearch.theoremprover.thms.NodeNameList;

public class ThmValueUtil {
	public static String valDelimLeft = "+|";
	public static String valDelimRight = "|+";
	public static String isaValue = "definition";

	public static NodeNameList getIsabelleValueDeps(AValueDefinition node){
		NodeNameList nodeDeps = new NodeNameList();
			
		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(node.getType()));

		NodeNameList vars = new NodeNameList();
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(vars, node.getExpression()));

		return nodeDeps;
	}
}
