package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStm;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.PProcess;

@SuppressWarnings("serial")
public class ThmStringVisitor extends
QuestionAnswerCMLAdaptor<ThmVarsContext, String> {
	
	private ThmTypeStringVisitor typeStringVisitor;
	private ThmExpStringVisitor expStringVisitor;
	private ThmProcessStringVisitor processStringVisitor;
	private ThmActionStringVisitor actionStringVisitor;
	private ThmStmStringVisitor stmStringVisitor;
	
	/**
	 * Construct a ThmStringVisitor
	 */
	public ThmStringVisitor() {
		initialize();
	}
	
	private void initialize()
	{
		typeStringVisitor = new ThmTypeStringVisitor(this);
		expStringVisitor = new ThmExpStringVisitor(this);
		processStringVisitor = new ThmProcessStringVisitor(this);
		actionStringVisitor = new ThmActionStringVisitor(this);
		stmStringVisitor = new ThmStmStringVisitor(this);
	}


	public String defaultINode(INode node, ThmVarsContext vc)
			throws AnalysisException {		
		return "(*Syntax not handled*)";
	}	
	
	public String defaultPType(PType node, ThmVarsContext vc)
			throws AnalysisException {		
		return node.apply(this.typeStringVisitor, vc);
	}	

	public String defaultPExp(PExp node, ThmVarsContext vc)
			throws AnalysisException {		
		return node.apply(this.expStringVisitor, vc);
	}	
	
	public String defaultPProcess(PProcess node, ThmVarsContext vc)
			throws AnalysisException {		
		return node.apply(this.processStringVisitor, vc);
	}	
	
	public String defaultPAction(PAction node, ThmVarsContext vc)
			throws AnalysisException {		
		return node.apply(this.actionStringVisitor, vc);
	}	
	
	public String defaultPVarsetExpression(PVarsetExpression node, ThmVarsContext vc)
			throws AnalysisException {		
		return node.apply(this.expStringVisitor, vc);
	}	

	
	public String defaultPStm(PStm node, ThmVarsContext vc)
			throws AnalysisException {		
		return node.apply(this.stmStringVisitor, vc);
	}	
	
	
	public String defaultPCMLExp(PCMLExp node, ThmVarsContext vc)
			throws AnalysisException {		
		return node.apply(this.expStringVisitor, vc);
	}	
	

	@Override
	public String createNewReturnValue(INode arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewReturnValue(Object arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}	
}
