package eu.compassresearch.core.analysis.theoremprover.visitors.deps;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

@SuppressWarnings("serial")
public class ThmChannelDepVisitor extends
QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList>{
	
	final private QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList> thmDepVisitor;
	
	public ThmChannelDepVisitor(ThmDepVisitor thmDepVisitor) {
		this.thmDepVisitor = thmDepVisitor;
	}

	/***
	 * Get the dependencies for a chanset - currently non. 
	 * @param node - the chanset CML AST node
	 * @return the list of dependencies
	 */
	public NodeNameList caseAChansetDefinition(
			AChansetDefinition node, NodeNameList bvars) {
		NodeNameList nodeDeps = new NodeNameList();
		
		return nodeDeps;
	}

	/****
	 * Get the dependencies for a channel definition 
	 * @param node - the channel CML AST node
	 * @return the list of dependencies
	 * @throws AnalysisException 
	 */
	public NodeNameList caseAChannelNameDefinition(
			AChannelNameDefinition node, NodeNameList bvars) throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		LinkedList<PDefinition> chandefs = node.getSingleType().getType().getDefinitions();
		PDefinition chan = chandefs.getFirst();
		PType chanTp = (((AChannelType) chan.getType()).getType());
		
		nodeDeps.addAll(chanTp.apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}
			
	@Override
	public NodeNameList createNewReturnValue(INode arg0, NodeNameList arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeNameList createNewReturnValue(Object arg0, NodeNameList arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

}
