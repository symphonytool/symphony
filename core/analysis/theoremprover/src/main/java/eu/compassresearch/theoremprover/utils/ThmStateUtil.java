package eu.compassresearch.theoremprover.utils;

import java.util.LinkedList;

import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.intf.lex.ILexNameToken;

public class ThmStateUtil {

	public static String stateEquiv = " \\<equiv> ";
	public static String isaState= "definition";
	public static String mkvar = "MkVarD";
	public static String typeDelim = "\\<parallel>";;

	public static LinkedList<ILexNameToken> getIsabelleStateDeps(AAssignmentDefinition node){
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
			
		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(node.getType()));

		LinkedList<ILexNameToken> vars = new LinkedList<ILexNameToken>();
		nodeDeps.addAll(ThmExprUtil.getIsabelleExprDeps(vars, node.getExpression()));

		return nodeDeps;
	}
}
