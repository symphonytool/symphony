package eu.compassresearch.core.analysis.theoremprover.utils;

import java.util.LinkedList;

import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

public class ThmChanUtil {

	public static String chanTpDelim = "\\<parallel>";
	public static String isaEquiv = "\\<equiv>";
	public static String isaChan = "definition";
	public static String isaChanset = "definition";
	public static String isaMkChan = "MkChanD";
	public static String isaChanQuote = " \'\' ";
	public static String isaChSetLeft = "{";
	public static String isaChSetRight = "{";

//	/***
//	 * Get the dependencies for a chanset - currently non. 
//	 * @param node - the chanset CML AST node
//	 * @return the list of dependencies
//	 */
//	public static NodeNameList getIsabelleChansetDeps(
//			AChansetDefinition node) {
//		NodeNameList nodeDeps = new NodeNameList();
//		
//		return nodeDeps;
//	}
//
//	/****
//	 * Get the dependencies for a channel definition 
//	 * @param node - the channel CML AST node
//	 * @return the list of dependencies
//	 */
//	public static NodeNameList getIsabelleChanDeps(
//			AChannelNameDefinition node) {
//		NodeNameList nodeDeps = new NodeNameList();
//		
//		LinkedList<PDefinition> chandefs = node.getSingleType().getType().getDefinitions();
//		PDefinition chan = chandefs.getFirst();
//		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(((AChannelType) chan.getType()).getType()));
//		
//		return nodeDeps;
//	}
}