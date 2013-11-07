package eu.compassresearch.core.analysis.theoremprover.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNode;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmNodeList;
import eu.compassresearch.core.analysis.theoremprover.thms.ThmValue;
import eu.compassresearch.core.analysis.theoremprover.visitors.deps.ThmDepVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmStringVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmVarsContext;

@SuppressWarnings("serial")
public class ThmValueVisitor extends QuestionAnswerCMLAdaptor<ThmVarsContext, ThmNodeList> {
	
	private ThmDepVisitor depVisitor;
	private ThmStringVisitor stringVisitor;
	
	
	public ThmValueVisitor(QuestionAnswerCMLAdaptor<ThmVarsContext, ThmNodeList> parent, ThmDepVisitor depVis, ThmStringVisitor stringVis)
	{
		depVisitor = depVis;
		stringVisitor = stringVis;
	}

	@Override
	public ThmNodeList caseAValuesDefinition(AValuesDefinition node, ThmVarsContext vars)
			throws AnalysisException {
		ThmNodeList tnl = new ThmNodeList();
		
		for(PDefinition t : node.getValueDefinitions())
		{
			tnl.addAll(t.apply(this, vars));
		}
		return tnl;
	}
	

	@Override
	public ThmNodeList caseAValueDefinition(AValueDefinition node, ThmVarsContext vars)
			throws AnalysisException {
		ThmNodeList tnl = new ThmNodeList();
		
		ILexNameToken valName =  ((AIdentifierPattern) node.getPattern()).getName();
		String nameStr = valName.toString();
		
		String typeStr = node.getType().apply(stringVisitor, new ThmVarsContext()); //ThmTypeUtil.getIsabelleType(node.getType());

		String exprStr = node.getExpression().apply(stringVisitor, new ThmVarsContext()); //ThmExprUtil.getIsabelleExprStr(svars, evars, node.getExpression());

		NodeNameList nodeDeps = node.apply(depVisitor, new NodeNameList());//ThmValueUtil.getIsabelleValueDeps(node);
		
		ThmNode tn = new ThmNode(valName, nodeDeps, new ThmValue(nameStr, typeStr, exprStr));
		tnl.add(tn);
		
		return tnl;
	}


	@Override
	public ThmNodeList createNewReturnValue(INode arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThmNodeList createNewReturnValue(Object arg0, ThmVarsContext arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
}