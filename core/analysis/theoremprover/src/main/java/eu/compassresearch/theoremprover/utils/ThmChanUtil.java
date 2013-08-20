package eu.compassresearch.theoremprover.utils;

import java.util.LinkedList;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;

import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.types.AChannelType;

public class ThmChanUtil {

	public static String chanTpDelim = "\\<parallel>";
	public static String isaEquiv = "\\<equiv>";
	public static String isaChan = "definition";
	public static String isaChanset = "definition";
	public static String isaMkChan = "MkChanD";
	public static String isaChanQuote = " \'\' ";
	public static String isaChSetLeft = "{";
	public static String isaChSetRight = "{";

	public static LinkedList<ILexNameToken> getIsabelleChansetDeps(
			AChansetDefinition node) {
		// TODO Auto-generated method stub
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		return nodeDeps;
	}
	
	public static LinkedList<ILexNameToken> getIsabelleChanDeps(
			AChannelNameDefinition node) {
		// TODO Auto-generated method stub
		LinkedList<ILexNameToken> nodeDeps = new LinkedList<ILexNameToken>();
		
		LinkedList<PDefinition> chandefs = node.getSingleType().getType().getDefinitions();
		PDefinition chan = chandefs.getFirst();
		nodeDeps.addAll(ThmTypeUtil.getIsabelleTypeDeps(((AChannelType) chan.getType()).getType()));
		
		return nodeDeps;
	}
}