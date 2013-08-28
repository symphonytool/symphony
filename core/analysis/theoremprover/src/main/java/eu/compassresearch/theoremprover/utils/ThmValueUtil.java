package eu.compassresearch.theoremprover.utils;

import java.util.LinkedList;

import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.intf.lex.ILexNameToken;

public class ThmValueUtil {
	public static String valDelimLeft = "+|";
	public static String valDelimRight = "|+";
	public static String isaValue = "definition";

	public static LinkedList<ILexNameToken> getIsabelleValueDeps(AValueDefinition node){
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
			
		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(node.getType()));

		LinkedList<ILexNameToken> vars = new LinkedList<ILexNameToken>();
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(vars, node.getExpression()));

		return nodeDeps;
	}
}
