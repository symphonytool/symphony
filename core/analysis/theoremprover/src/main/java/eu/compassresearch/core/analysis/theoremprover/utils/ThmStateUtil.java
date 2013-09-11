package eu.compassresearch.core.analysis.theoremprover.utils;


import org.overture.ast.definitions.AAssignmentDefinition;

import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

public class ThmStateUtil {

	public static String stateEquiv = " \\<equiv> ";
	public static String isaState= "definition";
	public static String mkvar = "MkVarD";
	public static String typeDelim = "\\<parallel>";;

	/***
	 * Method to return a list of dependencies for a state variable
	 * @param node - the assignment AST node
	 * @return a list of node dependencies
	 */
	public static NodeNameList getIsabelleStateDeps(AAssignmentDefinition node){
		NodeNameList nodeDeps = new NodeNameList();
			
		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(node.getType()));

		NodeNameList vars = new NodeNameList();
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(vars, node.getExpression()));

		return nodeDeps;
	}
}
