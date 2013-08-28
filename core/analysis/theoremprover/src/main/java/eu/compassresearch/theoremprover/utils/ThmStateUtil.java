package eu.compassresearch.theoremprover.utils;


import org.overture.ast.definitions.AAssignmentDefinition;

import eu.compassresearch.theoremprover.thms.NodeNameList;

public class ThmStateUtil {

	public static String stateEquiv = " \\<equiv> ";
	public static String isaState= "definition";
	public static String mkvar = "MkVarD";
	public static String typeDelim = "\\<parallel>";;

	public static NodeNameList getIsabelleStateDeps(AAssignmentDefinition node){
		NodeNameList nodeDeps = new NodeNameList();
			
		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(node.getType()));

		NodeNameList vars = new NodeNameList();
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(vars, node.getExpression()));

		return nodeDeps;
	}
}
