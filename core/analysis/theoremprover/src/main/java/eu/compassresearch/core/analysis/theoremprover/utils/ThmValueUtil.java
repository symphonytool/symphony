package eu.compassresearch.core.analysis.theoremprover.utils;

public class ThmValueUtil {
	public static String valDelimLeft = "+|";
	public static String valDelimRight = "|+";
	public static String isaValue = "definition";

//	/**
//	 * Return the dependencies of a value AST node
//	 * @param node - the value definition
//	 * @return the list of dependencies
//	 */
//	public static NodeNameList getIsabelleValueDeps(AValueDefinition node){
//		NodeNameList nodeDeps = new NodeNameList();
//			
//		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(node.getType()));
//
//		NodeNameList vars = new NodeNameList();
//		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(vars, node.getExpression()));
//
//		return nodeDeps;
//	}
}
