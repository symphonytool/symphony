package eu.compassresearch.core.analysis.theoremprover.visitors.deps;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.AAlphabetisedParallelismProcess;
import eu.compassresearch.ast.process.AEndDeadlineProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AInterruptProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.AStartDeadlineProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

@SuppressWarnings("serial")
public class ThmProcessDepVisitor  extends
QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList>{
	
	final private QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList> thmDepVisitor;
	
	public ThmProcessDepVisitor(ThmDepVisitor thmDepVisitor) {
		this.thmDepVisitor = thmDepVisitor;
	}
	
	/**
	 * Return the dependencies of a value AST node
	 * @param node - the value definition
	 * @return the list of dependencies
	 */
	public NodeNameList caseASequentialCompositionProcess(ASequentialCompositionProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}

	public NodeNameList caseAExternalChoiceProcess(AExternalChoiceProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}

	public NodeNameList caseAInternalChoiceProcess(AInternalChoiceProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}

	public NodeNameList caseAInterleavingProcess(AInterleavingProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
			
		return nodeDeps;
	}

	public NodeNameList caseASynchronousParallelismProcess(ASynchronousParallelismProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}

	public NodeNameList caseAGeneralisedParallelismProcess(AGeneralisedParallelismProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getChansetExpression().apply(thmDepVisitor, bvars));
	
		return nodeDeps;
	}

	public NodeNameList caseAAlphabetisedParallelismProcess(AAlphabetisedParallelismProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getLeftChansetExpression().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRightChansetExpression().apply(thmDepVisitor, bvars));
	
		return nodeDeps;
	}

	public NodeNameList caseAInterruptProcess(AInterruptProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
		
	
		return nodeDeps;
	}

	public NodeNameList caseAValueDefinition(ATimedInterruptProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getTimeExpression().apply(thmDepVisitor, bvars));
	
		return nodeDeps;
	}

	public NodeNameList caseAUntimedTimeoutProcess(AUntimedTimeoutProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
		
	
		return nodeDeps;
	}

	public NodeNameList caseATimeoutProcess(ATimeoutProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getRight().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getTimeoutExpression().apply(thmDepVisitor, bvars));
	
		return nodeDeps;
	}

	public NodeNameList caseAHidingProcess(AHidingProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getChansetExpression().apply(thmDepVisitor, bvars));
	
		return nodeDeps;
	}
	
	public NodeNameList caseAStartDeadlineProcess(AStartDeadlineProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getExpression().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}
	
	public NodeNameList caseAEndDeadlineProcess(AEndDeadlineProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(p.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(p.getExpression().apply(thmDepVisitor, bvars));
	
		return nodeDeps;
	}
	
	public NodeNameList caseAReferenceProcess(AReferenceProcess p, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		LinkedList<PExp> args = p.getArgs();
		nodeDeps.add(p.getProcessName());
		if (args.size() != 0)
		{
			for (PExp e: p.getArgs()) 
			{
				nodeDeps.addAll(e.apply(thmDepVisitor, bvars));	
			}
		}
	
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
