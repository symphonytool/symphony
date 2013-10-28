package eu.compassresearch.core.analysis.theoremprover.visitors.string;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;

@SuppressWarnings("serial")
public class ThmStringVisitor extends
QuestionAnswerCMLAdaptor<ThmVarsContext, String> {
	
	private ThmTypeStringVisitor typeStringVisitor;
	private ThmExpStringVisitor expStringVisitor;
	private ThmProcessStringVisitor processStringVisitor;
	private ThmActionStringVisitor actionStringVisitor;
	
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
	}

	public String defaultPType(PType node, ThmVarsContext bvars)
			throws AnalysisException {		
		return node.apply(this.typeStringVisitor, bvars);
	}	

	public String defaultPExp(PExp node, ThmVarsContext bvars)
			throws AnalysisException {		
		return node.apply(this.expStringVisitor, bvars);
	}	
	
	public String defaultPProcess(PProcess node, ThmVarsContext bvars)
			throws AnalysisException {		
		return node.apply(this.processStringVisitor, bvars);
	}	
	
	public String defaultPAction(PAction node, ThmVarsContext bvars)
			throws AnalysisException {		
		return node.apply(this.actionStringVisitor, bvars);
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
