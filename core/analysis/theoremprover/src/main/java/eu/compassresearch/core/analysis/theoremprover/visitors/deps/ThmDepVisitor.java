package eu.compassresearch.core.analysis.theoremprover.visitors.deps;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

@SuppressWarnings("serial")
public class ThmDepVisitor extends
QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList> {
	
	
	private ThmTypeDepVisitor typeDepVisitor;
	private ThmExpDepVisitor expDepVisitor;
	private ThmValueDepVisitor valDepVisitor;
//	private ThmDeclAndDefVisitor declAndDefVisitor;
	private ThmChannelDepVisitor chanDepVisitor;
	private ThmStateDepVisitor stateDepVisitor;

	/**
	 * Construct a ThmVisitor with no source indicated
	 */
	public ThmDepVisitor() {
		initialize();
	}
	
	private void initialize()
	{
		typeDepVisitor = new ThmTypeDepVisitor(this);
		expDepVisitor = new ThmExpDepVisitor(this);
		chanDepVisitor = new ThmChannelDepVisitor(this);
//		declAndDefVisitor = new ThmDeclAndDefVisitor(this);
		valDepVisitor = new ThmValueDepVisitor(this);
		stateDepVisitor = new ThmStateDepVisitor(this);
	}

	public  NodeNameList caseAValueDefinition(AValueDefinition node, NodeNameList bvars)
			throws AnalysisException {
		return node.apply(this.valDepVisitor, bvars);
	}
	
	@Override
	public NodeNameList caseAChannelNameDefinition(AChannelNameDefinition node, NodeNameList bvars)
			throws AnalysisException {		
		return node.apply(this.chanDepVisitor, bvars);
	}

	@Override
	public NodeNameList caseAChansetDefinition(AChansetDefinition node, NodeNameList bvars)
			throws AnalysisException {		
		return node.apply(this.chanDepVisitor, bvars);
	}	
	
	public NodeNameList caseAAssignmentDefinition(AAssignmentDefinition node, NodeNameList bvars)
			throws AnalysisException {		
		return node.apply(this.stateDepVisitor, bvars);
	}	

	public NodeNameList defaultPExp(PExp node, NodeNameList bvars)
			throws AnalysisException {		
		return node.apply(this.expDepVisitor, bvars);
	}	

	public NodeNameList defaultPType(PType node, NodeNameList bvars)
			throws AnalysisException {		
		return node.apply(this.typeDepVisitor, bvars);
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
